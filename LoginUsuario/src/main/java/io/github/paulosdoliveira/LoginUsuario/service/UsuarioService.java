package io.github.paulosdoliveira.LoginUsuario.service;

import io.github.paulosdoliveira.LoginUsuario.infra.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.ConsultaUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.validation.UsuarioValidator;
import io.github.paulosdoliveira.LoginUsuario.model.dto.LoginUsuarioDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import io.github.paulosdoliveira.LoginUsuario.jwt.JwtService;
import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import io.github.paulosdoliveira.LoginUsuario.token.Token;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    private final UsuarioValidator validator;

    private final JwtService jwtService;

    private final PasswordEncoder encoder;

    public void cadastrarUsuario(CadastroUsuarioDTO dados) {
        validator.validar(dados.getEmail());
        Usuario usuario = new Usuario(dados);
        usuario.setSenha(encoder.encode(dados.getSenha()));
        repository.save(usuario);
    }

    public ConsultaUsuarioDTO buscarUsuario(Long id) {
        System.out.println(id + "id **********************************");
        var usuario = repository.findById(id);
        if(usuario.isPresent()) {
            System.out.println("Antes de construir *********************************");
            ConsultaUsuarioDTO usuarioEncontrado = new ConsultaUsuarioDTO(usuario.get());
            return usuarioEncontrado;
        }
        return null;
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Token logarUsuario(LoginUsuarioDTO dadosLogin) {
        var usuario = findByEmail(dadosLogin.getEmail());
        String senhaSalva = usuario.getSenha();
        String senhaDigitada = dadosLogin.getSenha();
        if (encoder.matches(senhaDigitada, senhaSalva)) return jwtService.gerarToken(usuario);
        throw new UsernameNotFoundException("Email e/ou senha incorretos");
    }

    @Transactional
    public void salvarFoto(Long id, MultipartFile arquivo) throws IOException {
        var usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setFoto(arquivo.getBytes());
    }
}
