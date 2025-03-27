package io.github.paulosdoliveira.LoginUsuario.controller;

import io.github.paulosdoliveira.LoginUsuario.ex.EmailDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestControllerAdvice
public class ControllerException {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailDuplicadoException.class)
    public String handlerEmailDuplicadoException(EmailDuplicadoException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  List<FieldError> handlerCampoInvalidoException(MethodArgumentNotValidException e) {
        List<FieldError> listaDeErros = e.getFieldErrors();
        return listaDeErros;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String handlerErroInterno(RuntimeException e) {
        System.out.println(e);
        return "Algo deu errado";
    }
}
