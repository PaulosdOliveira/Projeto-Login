package io.github.paulosdoliveira.LoginUsuario.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

@Component
public class SecretKeyGenerator {

    private SecretKey key;

    public SecretKey getSecretKey() {
        if (this.key == null) {
            this.key = Jwts.SIG.HS256.key().build();
        }
        return this.key;
    }
}
