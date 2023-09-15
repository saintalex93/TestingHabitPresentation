package br.com.santocodigo.demotests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import io.qameta.allure.Epic;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "test" )
@Epic( "Main Integration Test" )
class DemotestsApplicationTest
{

    @Test
    public void main()
    {
        DemotestsApplication.main( new String[] {} );
    }

}
