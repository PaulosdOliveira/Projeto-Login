package io.github.paulosdoliveira.LoginUsuario.controller;


import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.ConsultaUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.dto.LoginUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.github.paulosdoliveira.LoginUsuario.token.Token;

import java.io.IOException;


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

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void salvarFoto(@RequestParam() Long id, @RequestParam MultipartFile arquivo) throws IOException {
        service.salvarFoto(id, arquivo);
    }

    @PostMapping("/login")
    public Token logarUsuario(@RequestBody LoginUsuarioDTO dadosLogin) {
        return service.logarUsuario(dadosLogin);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Object> buscarUsuario(@RequestParam() Long id) {
        ConsultaUsuarioDTO usuarioEncontrado = service.buscarUsuario(id);
        return ResponseEntity.ok(usuarioEncontrado);
    }

    @GetMapping("/foto/{id}")
    public ResponseEntity<Object> carregarFoto(@PathVariable("id") Long id) {
        var foto = service.buscarFoto(id);
        if(foto == null) return ResponseEntity.notFound().build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(foto.length);
        //headers.setContentDispositionFormData("inline; fileName=\"" + "foto" + "\"", id.toString());
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    }

    @GetMapping
    public String teste() {

        return "Bom dia";
    }
}
