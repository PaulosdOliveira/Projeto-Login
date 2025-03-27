package io.github.paulosdoliveira.LoginUsuario.ex;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException() {
        super("Este email já está cadastrado");
    }

}
