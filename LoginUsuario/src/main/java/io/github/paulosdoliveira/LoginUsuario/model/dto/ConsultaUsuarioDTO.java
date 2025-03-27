package io.github.paulosdoliveira.LoginUsuario.model.dto;

import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ConsultaUsuarioDTO {

    private String nome;
    private String urlFoto;

    public ConsultaUsuarioDTO(Usuario usuario){
        this.nome = usuario.getNome();
        this.urlFoto = "http://localhost:8080/usuario/foto?id=" + usuario.getId();
    }
}
