package br.com.santocodigo.demotests.service;

import static org.apache.logging.log4j.util.Strings.isEmpty;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.santocodigo.demotests.model.ResponseMessage;
import br.com.santocodigo.demotests.model.User;
import br.com.santocodigo.demotests.repository.UserRepository;

@Component
public class UserService
{

    @Autowired
    private UserRepository repository;

    public ResponseMessage login(
        final User user )
    {
        checkUser( user );

        if( repository.existsByEmailAndPassword( user.getEmail(), user.getPassword() ) ) {
            return ResponseMessage.success( UUID.randomUUID().toString() );
        }
        return ResponseMessage.error();
    }

    private void checkUser(
        final User user )
    {
        if( user == null ) {
            throw new IllegalArgumentException( "O usuário está inválido." );
        }
        if( isEmpty( user.getEmail() ) || isEmpty( user.getPassword() ) ) {
            throw new IllegalArgumentException( "Email ou senha inválidos." );
        }

    }

    public User create(
        final User user )
    {
        checkUser( user );

        return repository.save( user );
    }

    public List<User> findAll()
    {
        return (List<User>) repository.findAll();
    }

}
