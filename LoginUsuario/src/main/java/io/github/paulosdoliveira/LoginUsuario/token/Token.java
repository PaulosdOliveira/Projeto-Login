package io.github.paulosdoliveira.LoginUsuario.token;

import lombok.Data;

@Data
public class Token {

    private  String token;

    public Token(String token){
        this.token = token;
    }


}
