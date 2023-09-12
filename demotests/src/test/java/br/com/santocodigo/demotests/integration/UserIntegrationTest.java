package br.com.santocodigo.demotests.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.santocodigo.demotests.model.ResponseMessage;
import br.com.santocodigo.demotests.model.User;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
@AutoConfigureMockMvc
@ActiveProfiles( "test" )
public class UserIntegrationTest
{

    private static final String EMAIL_TEST = "teste@totvs.com.br";
    private static final String PASSWORD_TEST = "Teste@123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldRetrieveAllUsers()
        throws Exception
    {
        final MvcResult result = mockMvc.perform( get( "/user/find-all" ) )
            .andExpect( status().isOk() )
            .andReturn();

        final List<User> users = objectMapper.readValue( result.getResponse().getContentAsString( StandardCharsets.UTF_8 ),
            new TypeReference<List<User>>() {} );
        assertFalse( users.isEmpty() );
        final User user = users.get( 0 );
        assertEquals( EMAIL_TEST, user.getEmail() );
        assertEquals( PASSWORD_TEST, user.getPassword() );
    }

    @Test
    public void shouldLogin()
        throws Exception
    {
        final ResponseMessage responseMessage = login( EMAIL_TEST, PASSWORD_TEST, status().isOk() );
        assertTrue( responseMessage.isSuccess() );
        assertNotNull( responseMessage.getToken() );
        assertEquals( "Usuário encontrado!", responseMessage.getMessage() );
    }

    @Test
    public void shouldLoginError()
        throws Exception
    {
        final ResponseMessage responseMessage = login( EMAIL_TEST, "invalidPassword", status().isBadRequest() );
        assertFalse( responseMessage.isSuccess() );
        assertNull( responseMessage.getToken() );
        assertEquals( "Usuário não encontrado.", responseMessage.getMessage() );
    }

    @Test
    public void shouldCreateUser()
        throws Exception
    {
        final User createdUser = createUser( "newuser@totvs.com.br", PASSWORD_TEST );

        assertEquals( "newuser@totvs.com.br", createdUser.getEmail() );
        assertEquals( PASSWORD_TEST, createdUser.getPassword() );
    }

    private User createUser(
        final String email,
        final String password )
        throws Exception,
            JsonProcessingException
    {
        final MvcResult result = mockMvc.perform( post( "/user/create" )
            .contentType( MediaType.APPLICATION_JSON )
            .content( objectMapper.writeValueAsString( User.builder().email( email ).password( password ).build() ) ) )
            .andExpect( status().isOk() )
            .andReturn();
        return objectMapper.readValue( result.getResponse().getContentAsString( StandardCharsets.UTF_8 ), User.class );
    }

    private ResponseMessage login(
        final String email,
        final String password,
        final ResultMatcher status )
        throws Exception
    {
        final MvcResult result = mockMvc.perform( post( "/user/login" )
            .contentType( MediaType.APPLICATION_JSON )
            .content( objectMapper.writeValueAsString( User.builder().email( email ).password( password ).build() ) ) )
            .andExpect( status )
            .andReturn();

        return objectMapper.readValue( result.getResponse().getContentAsString( StandardCharsets.UTF_8 ), ResponseMessage.class );
    }

}
