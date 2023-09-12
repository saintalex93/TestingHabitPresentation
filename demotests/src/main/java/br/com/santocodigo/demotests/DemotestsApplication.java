package br.com.santocodigo.demotests;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import br.com.santocodigo.demotests.model.User;
import br.com.santocodigo.demotests.model.User.UserBuilder;
import br.com.santocodigo.demotests.repository.UserRepository;

@SpringBootApplication
public class DemotestsApplication
{

    @Autowired
    private UserRepository userRepository;

    public static void main(
        final String[] args )
    {
        SpringApplication.run( DemotestsApplication.class, args );
    }

    @Bean
    @Profile( "!test" )
    CommandLineRunner init()
    {
        final List<String> users = asList( "alex.santos", "cristine.fogaca", "nichollas.santaella", "willian.garbo" );
        return createDefaultUsers( users, "Teste@123" );
    }

    @Bean
    @Profile( "test" )
    CommandLineRunner initOnTest()
    {
        final List<String> users = asList( "teste" );
        return createDefaultUsers( users, "Teste@123" );
    }

    private CommandLineRunner createDefaultUsers(
        final List<String> users,
        final String password )
    {
        return args -> {
            users.forEach( email -> {
                final UserBuilder userBuilder = User.builder().email( email + "@totvs.com.br" ).password( password );
                userRepository.save( userBuilder.build() );
            } );
            userRepository.findAll().forEach( System.out::println );
        };
    }

}
