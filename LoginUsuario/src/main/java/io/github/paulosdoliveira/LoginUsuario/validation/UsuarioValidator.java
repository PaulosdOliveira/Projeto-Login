package io.github.paulosdoliveira.LoginUsuario.validation;

import io.github.paulosdoliveira.LoginUsuario.ex.EmailDuplicadoException;
import io.github.paulosdoliveira.LoginUsuario.infra.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UsuarioValidator {

    private final UsuarioRepository repository;


    public void validar(String email){
        if(repository.existsByEmail(email)) throw new EmailDuplicadoException();
    }

}
