package br.com.santocodigo.demotests.service;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.santocodigo.demotests.model.ResponseMessage;
import br.com.santocodigo.demotests.model.User;
import br.com.santocodigo.demotests.repository.UserRepository;
import io.qameta.allure.Epic;

@ExtendWith( MockitoExtension.class )
@Epic( "User Unit Test" )
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

        final ResponseMessage responseMessage = subject.login( createUser( EMAIL ) );
        assertFalse( responseMessage.isSuccess() );
        assertNull( responseMessage.getToken() );
        assertEquals( "Usuário não encontrado.", responseMessage.getMessage() );
    }

    @Test
    public void shouldLoginSuccessfully()
    {
        when( userRepository.existsByEmailAndPassword( EMAIL, PASSWORD ) ).thenReturn( true );

        final ResponseMessage responseMessage = subject.login( createUser( EMAIL ) );
        assertTrue( responseMessage.isSuccess() );
        assertNotNull( responseMessage.getToken() );
        assertEquals( "Usuário encontrado!", responseMessage.getMessage() );
    }

    @Test
    public void shouldCreateUser()
    {
        final User user = createUser( EMAIL );
        subject.create( user );
        verify( userRepository ).save( user );
    }

    @Test
    public void shouldThrowExceptionWhenCreateUserWithNullArgument()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.create( null );
        } );

        assertEquals( "User is null", exception.getMessage() );
    }

    @Test
    public void shouldRetrieveUser()
    {
        final User expectedUser = createUser( EMAIL );
        when( userRepository.findAll() ).thenReturn( singletonList( expectedUser ) );

        final List<User> users = subject.findAll();

        verify( userRepository ).findAll();
        assertTrue( users.contains( expectedUser ) );
    }

    @Test
    public void shouldRetrieveEmptyListOfUser()
    {
        when( userRepository.findAll() ).thenReturn( Collections.emptyList() );

        final List<User> users = subject.findAll();

        verify( userRepository ).findAll();
        assertTrue( users.isEmpty() );
    }

    private User createUser(
        final String email )
    {
        return User.builder().email( email ).password( PASSWORD ).build();
    }

}
