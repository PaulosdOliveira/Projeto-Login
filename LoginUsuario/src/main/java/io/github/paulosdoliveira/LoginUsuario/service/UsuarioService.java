package io.github.paulosdoliveira.LoginUsuario.service;


import io.github.paulosdoliveira.LoginUsuario.infra.Repository.UsuarioRepository;
import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.ConsultaUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.validation.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    private final UsuarioValidator validator;

    public void cadastrarUsuario(CadastroUsuarioDTO dados){
        validator.validar(dados.getEmail());
        repository.save(new Usuario(dados));
    }

    public ConsultaUsuarioDTO buscarUsuario(Long id){
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Página indisponível"));
        ConsultaUsuarioDTO usuarioEncontrado = new ConsultaUsuarioDTO(usuario);
        return usuarioEncontrado;
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email);
    }
}
