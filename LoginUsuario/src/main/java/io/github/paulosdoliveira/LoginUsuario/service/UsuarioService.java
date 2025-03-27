package io.github.paulosdoliveira.LoginUsuario.service;


import io.github.paulosdoliveira.LoginUsuario.infra.Repository.UsuarioRepository;
import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public void cadastrarUsuario(CadastroUsuarioDTO dados){
        repository.save(new Usuario(dados));
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email);
    }
}
