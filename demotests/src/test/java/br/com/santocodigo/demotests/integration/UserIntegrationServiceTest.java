package br.com.santocodigo.demotests.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import br.com.santocodigo.demotests.model.User;
import br.com.santocodigo.demotests.service.UserService;
import io.qameta.allure.Epic;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "test" )
@Epic( "User Integration Service Test" )
public class UserIntegrationServiceTest
{
    private static final String EMAIL_TEST = "teste@totvs.com.br";
    private static final String PASSWORD_TEST = "Teste@123";

    @Autowired
    private UserService subject;

    @Test
    public void shouldRetrieveTestUser()
    {
        final List<User> users = subject.findAll();
        assertFalse( users.isEmpty() );
        final User user = users.get( 0 );
        assertEquals( EMAIL_TEST, user.getEmail() );
        assertEquals( PASSWORD_TEST, user.getPassword() );
    }

}
