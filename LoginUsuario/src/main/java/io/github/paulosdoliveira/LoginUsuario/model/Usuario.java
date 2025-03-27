package io.github.paulosdoliveira.LoginUsuario.model;

import io.github.paulosdoliveira.LoginUsuario.model.dto.CadastroUsuarioDTO;
import io.github.paulosdoliveira.LoginUsuario.model.unums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@NoArgsConstructor
@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 50)
    private String nome;

    @Column(nullable = false , length = 300)
    private String senha;

    @Column(nullable = false)
    private PerfilUsuario perfil;
    private byte[] foto;


    public Usuario(CadastroUsuarioDTO dados){
        BeanUtils.copyProperties(dados, this);
    }

}
