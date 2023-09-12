package br.com.santocodigo.demotests.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "users" )
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class User
{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private long id;
    private String email;
    private String password;

}