package io.github.paulosdoliveira.LoginUsuario.jwt;

import io.github.paulosdoliveira.LoginUsuario.model.Usuario;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import token.Token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtService {


    private final SecretKeyGenerator keyGenerator;

    public Token gerarToken(Usuario usuario) {
        var token = Jwts.builder()
                .signWith(keyGenerator.getSecretKey())
                .subject(usuario.getEmail())
                .expiration(getExpiration())
                .claims(gerarClaims(usuario.getPerfil().toString()))
                .compact();

        return new Token(token);
    }


    public Date getExpiration() {
        var expiration = LocalDateTime.now().atZone(ZoneId.systemDefault()).plusMinutes(60).toInstant();
        return Date.from(expiration);
    }

    private Map<String, Object> gerarClaims(String perfil) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Perfil", perfil);
        return claims;
    }

    public String getEmailByToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(keyGenerator.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "";
    }

}
