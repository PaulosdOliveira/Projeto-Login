package io.github.paulosdoliveira.LoginUsuario.infra.Repository;

import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    @Query("Select u.foto from Usuario u where id = :id")
    byte[] buscarFoto(@Param("id") Long id);
}
