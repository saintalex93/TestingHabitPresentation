package br.com.santocodigo.demotests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.santocodigo.demotests.model.ResponseMessage;
import br.com.santocodigo.demotests.model.User;
import br.com.santocodigo.demotests.repository.UserRepository;

@ExtendWith( MockitoExtension.class )
class UserServiceTest
{

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    
    @InjectMocks
    private UserService subject;
    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldLoginFailWhenUserNotFound()
    {
        when( userRepository.existsByEmailAndPassword( EMAIL, PASSWORD ) ).thenReturn( false );

        final ResponseMessage responseMessage = subject.login( createUser( EMAIL, PASSWORD ) );
        assertFalse( responseMessage.isSuccess() );
        assertNull( responseMessage.getToken() );
        assertEquals( "Usuário não encontrado.", responseMessage.getMessage() );
    }

    @Test
    public void shouldLoginSuccessfully()
    {
        when( userRepository.existsByEmailAndPassword( EMAIL, PASSWORD ) ).thenReturn( true );

        final ResponseMessage responseMessage = subject.login( createUser( EMAIL, PASSWORD ) );
        assertTrue( responseMessage.isSuccess() );
        assertNotNull( responseMessage.getToken() );
        assertEquals( "Usuário encontrado!", responseMessage.getMessage() );
    }

    @Test
    public void shouldCreateUser()
    {
        final User user = createUser( EMAIL, PASSWORD );
        subject.create( user );
        verify( userRepository ).save( user );
    }
    
    @Test
    public void shouldThrowExceptionWhenCreateUserWithNullArgument()
    {
        final Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subject.create( null );
        });
       
        assertEquals("User is null", exception.getMessage());
    }

    private User createUser(
        final String email,
        final String password )
    {
        return User.builder().email( email ).password( password ).build();
    }

}
