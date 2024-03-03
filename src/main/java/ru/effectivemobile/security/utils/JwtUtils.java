package ru.effectivemobile.security.utils;

import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import ru.effectivemobile.model.JwtAuthentication;
import ru.effectivemobile.model.Role;

import java.util.Set;

@Configuration
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(Set.of(Role.valueOf(claims.get("role", String.class))));
        jwtInfoToken.setEmail(claims.get("email", String.class));
        jwtInfoToken.setId(claims.get("id", String.class));

        return jwtInfoToken;
    }
}
