package io.github.paulosdoliveira.LoginUsuario.service;


import io.github.paulosdoliveira.LoginUsuario.infra.Repository.UsuarioRepository;
import io.github.paulosdoliveira.LoginUsuario.jwt.JwtService;
import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.ConsultaUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.LoginUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.validation.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import token.Token;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    private final UsuarioValidator validator;

    private final JwtService jwtService;

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

    public Token logarUsuario( LoginUsuarioDTO dadosLogin){
        var usuario = repository.findByEmail(dadosLogin.getEmail());
        if(usuario != null){
           return  jwtService.gerarToken(usuario);
        }

        throw new RuntimeException("Erro login");
    }

    @Transactional
    public void salvarFoto(Long id, MultipartFile arquivo) throws IOException {
        var usuario = repository.findById(id).orElseThrow( () -> new RuntimeException("Usuário não encontrado"));
        usuario.setFoto(arquivo.getBytes());
    }
}
