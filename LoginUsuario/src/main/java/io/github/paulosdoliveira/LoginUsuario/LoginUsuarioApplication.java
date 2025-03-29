package io.github.paulosdoliveira.LoginUsuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@SpringBootApplication
public class LoginUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginUsuarioApplication.class, args);
	}

}
