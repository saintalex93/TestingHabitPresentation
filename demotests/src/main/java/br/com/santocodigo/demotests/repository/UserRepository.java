package br.com.santocodigo.demotests.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.santocodigo.demotests.model.User;

@Repository
public interface UserRepository
    extends
        CrudRepository<User,Long>
{

    boolean existsByEmailAndPassword(
        String email,
        String password );
}