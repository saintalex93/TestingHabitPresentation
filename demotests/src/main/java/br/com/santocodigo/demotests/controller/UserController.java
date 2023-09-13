package br.com.santocodigo.demotests.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.santocodigo.demotests.model.ResponseMessage;
import br.com.santocodigo.demotests.model.User;
import br.com.santocodigo.demotests.service.UserService;

@RestController
@RequestMapping( "user" )
@CrossOrigin( origins = "http://localhost:4200" )
public class UserController
{

    @Autowired
    private UserService service;

    @GetMapping( "/find-all" )
    private List<User> findAll()
    {
        return service.findAll();
    }

    @PostMapping( "/login" )
    private ResponseEntity<ResponseMessage> login(
        @RequestBody final User user )
    {
        final ResponseMessage responseMessage = service.login( user );
        if( responseMessage.isSuccess() ) {
            return ResponseEntity.ok( responseMessage );
        }
        return ResponseEntity.badRequest().body( responseMessage );
    }

    @PostMapping( "/create" )
    private User create(
        @RequestBody final User user )
    {
        return service.create( user );

    }
}