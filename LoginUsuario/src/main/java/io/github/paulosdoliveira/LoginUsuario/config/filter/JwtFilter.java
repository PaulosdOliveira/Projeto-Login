package io.github.paulosdoliveira.LoginUsuario.config.filter;

import io.github.paulosdoliveira.LoginUsuario.jwt.JwtService;
import io.github.paulosdoliveira.LoginUsuario.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@NoArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    public JwtFilter(JwtService jwtService, UsuarioService usuarioService){
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        System.out.println(token);
        if(token != null){
            String email = jwtService.getEmailByToken(token);
            var usuario = usuarioService.findByEmail(email);
            UserDetails user = User.withUsername(usuario.getEmail())
                    .password(usuario.getSenha())
                    .roles(usuario.getPerfil().toString())
                    .build();
            UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(username);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requisicao = request.getRequestURI();
        String metodo = request.getMethod();
        return requisicao.contains("/usuario") && metodo.equals(HttpMethod.POST.toString());
    }

    private String getToken(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        if(token != null){
            String[] tokenDividido = token.split(" ");
            if(tokenDividido.length == 2) return tokenDividido[1];
        }
        return null;
    }
}
