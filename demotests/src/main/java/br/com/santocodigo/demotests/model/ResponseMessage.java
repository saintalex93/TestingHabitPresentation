package br.com.santocodigo.demotests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ResponseMessage
{

    private boolean success;
    private String message;
    private String token;
    
    public static ResponseMessage success(final String token)
    {
        return new ResponseMessage( true, "Usu�rio encontrado!", token );
    }

    public static ResponseMessage error()
    {
        return new ResponseMessage( false, "Usu�rio n�o encontrado.", null );
    }
}
