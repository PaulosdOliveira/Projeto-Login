package io.github.paulosdoliveira.LoginUsuario.infra.Repository;

import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
}
