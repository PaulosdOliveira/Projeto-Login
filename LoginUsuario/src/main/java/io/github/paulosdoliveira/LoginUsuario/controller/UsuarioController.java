package io.github.paulosdoliveira.LoginUsuario.controller;


import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.ConsultaUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDTO dados) {
        service.cadastrarUsuario(dados);
    }

    @PostMapping
    public void logarUsuario(){

    }

    @GetMapping(params = "id")
    public ResponseEntity<Object> buscarUsuario(@RequestParam("id") Long id) {
        ConsultaUsuarioDTO usuarioEncontrado = service.buscarUsuario(id);
        return  ResponseEntity.ok(usuarioEncontrado);
    }

    @GetMapping
    public String teste() {

        return "Bom dia";
    }
}
