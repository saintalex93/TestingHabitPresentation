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
    private UserRepository repository;

    @Test
    public void shouldLoginSuccessfully()
    {
        when( repository.existsByEmailAndPassword( EMAIL, PASSWORD ) ).thenReturn( true );

        final ResponseMessage responseMessage = subject.login( buildUser( EMAIL ) );
        assertTrue( responseMessage.isSuccess() );
        assertNotNull( responseMessage.getToken() );
        assertEquals( "Usu�rio encontrado!", responseMessage.getMessage() );
    }

    @Test
    public void shouldLoginFailWhenUserNotFound()
    {
        when( repository.existsByEmailAndPassword( EMAIL, PASSWORD ) ).thenReturn( false );

        final ResponseMessage responseMessage = subject.login( buildUser( EMAIL ) );
        assertFalse( responseMessage.isSuccess() );
        assertNull( responseMessage.getToken() );
        assertEquals( "Usu�rio n�o encontrado.", responseMessage.getMessage() );
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithNullUser()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.login( null );
        } );

        assertEquals( "O usu�rio est� inv�lido.", exception.getMessage() );
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithNullEmail()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.login( buildUser( null ) );
        } );

        assertEquals( "Email ou senha inv�lidos.", exception.getMessage() );
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithNullPassword()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.login( buildUser( EMAIL, null ) );
        } );

        assertEquals( "Email ou senha inv�lidos.", exception.getMessage() );
    }

    @Test
    public void shouldCreateUser()
    {
        final User user = buildUser( EMAIL );
        subject.create( user );
        verify( repository ).save( user );
    }

    @Test
    public void shouldThrowExceptionWhenCreateUserWithNullArgument()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.create( null );
        } );

        assertEquals( "O usu�rio est� inv�lido.", exception.getMessage() );
    }

    @Test
    public void shouldThrowExceptionWhenCreateWithNullEmail()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.create( buildUser( null ) );
        } );

        assertEquals( "Email ou senha inv�lidos.", exception.getMessage() );
    }

    @Test
    public void shouldThrowExceptionWhenCreateWithNullPassword()
    {
        final Exception exception = assertThrows( IllegalArgumentException.class, () -> {
            subject.create( buildUser( EMAIL, null ) );
        } );

        assertEquals( "Email ou senha inv�lidos.", exception.getMessage() );
    }

    @Test
    public void shouldRetrieveUser()
    {
        final User expectedUser = buildUser( EMAIL );
        when( repository.findAll() ).thenReturn( singletonList( expectedUser ) );

        final List<User> users = subject.findAll();

        verify( repository ).findAll();
        assertTrue( users.contains( expectedUser ) );
    }

    @Test
    public void shouldRetrieveEmptyListOfUser()
    {
        when( repository.findAll() ).thenReturn( Collections.emptyList() );

        final List<User> users = subject.findAll();

        verify( repository ).findAll();
        assertTrue( users.isEmpty() );
    }

    private User buildUser(
        final String email )
    {
        return User.builder().email( email ).password( PASSWORD ).build();
    }

    private User buildUser(
        final String email,
        final String password )
    {
        return User.builder().email( email ).password( password ).build();
    }

}
