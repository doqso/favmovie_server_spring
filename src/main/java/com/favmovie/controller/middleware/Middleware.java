package com.favmovie.controller.middleware;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.favmovie.util.AuthCookie;
import com.favmovie.util.CustomClaims;
import com.favmovie.util.JWTHandler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * Clase que contiene los métodos para manejar las solicitudes entrantes.
 */
public class Middleware {
    /**
     * Verifica el token de autenticación en la cookie del usuario.
     *
     * @param request  La petición HTTP del usuario.
     * @param response La respuesta HTTP a enviar al usuario.
     * @return `true` si el token es válido, `false` en caso contrario.
     * @throws IOException Si ocurre un error al redirigir al usuario.
     */
    public static boolean checkAuthenticationCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(AuthCookie.NAME))
                .findFirst().orElse(null);
        if (cookie == null) {
            System.err.println("No token : " + LocalTime.now());
            response.setStatus(401); // Unauthorized
            response.setHeader("Location", "/login");
            return false;
        }
        DecodedJWT token = JWTHandler.verifyToken(cookie.getValue());
        if (token == null) {
            System.err.println("Invalid token : " + LocalTime.now());
            response.setStatus(401); // Unauthorized
            return false;
        }
        return true;
    }
}
