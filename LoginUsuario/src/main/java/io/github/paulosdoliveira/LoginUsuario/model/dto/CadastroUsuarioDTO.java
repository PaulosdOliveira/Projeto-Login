package io.github.paulosdoliveira.LoginUsuario.model.dto;

import io.github.paulosdoliveira.LoginUsuario.model.unums.PerfilUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CadastroUsuarioDTO {

    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @NotBlank(message = "Campo obrigatório")
    private String senha;

    @NotNull(message = "Campo obrigatório")
    private PerfilUsuario perfil;
    private byte[] foto;

}
