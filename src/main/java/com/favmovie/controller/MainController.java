package com.favmovie.controller;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.favmovie.entities.*;
import com.favmovie.models.MoviesListPage;
import com.favmovie.service.DatabaseService;
import com.favmovie.util.AuthCookie;
import com.favmovie.util.CustomClaims;
import com.favmovie.util.ExternalApiUtil;
import com.favmovie.util.JWTHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
public class MainController {
    DatabaseService databaseService;

    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response) throws NoSuchAlgorithmException {
        User userFound = databaseService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userFound == null) {
            response.setStatus(401); // Unauthorized
            return null;
        }
        String token = JWTHandler.createToken(userFound.getId(), userFound.getUsername());
        AuthCookie cookie = new AuthCookie(token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return token;
    }

    @GetMapping("/genres")
    public List<Genre> genres() {
        return databaseService.getAllGenres();
    }

    @GetMapping("/genre/{id}")
    public MoviesListPage moviesByGenre(@PathVariable("id") Integer id,
                                        @RequestParam(required = false, defaultValue = "1") Integer page)
            throws IOException {
        return ExternalApiUtil.getMoviesByGenre(id, page);
    }

    @GetMapping("/trending")
    public List<Movie> trendingMovies() throws IOException {
        return ExternalApiUtil.getTrendingMovies().getResults();
    }

    @GetMapping("/movie/{id}")
    public Status movieById(@PathVariable("id") long id, @CookieValue(value = AuthCookie.NAME) String token) throws IOException {
        final long userId = JWT.decode(token).getClaim(CustomClaims.USER_ID.getValue()).asLong();
        Status status = databaseService.getMovieStatus(id, userId);
        if (status == null) {
            status = new Status();
            status.setMovie(ExternalApiUtil.getMovieById(id));
            User user = databaseService.getUserById(userId);
            status.setUser(user);
        }
        return status;
    }

    @GetMapping("/favorites")
    public List<Status> getFavorites(@CookieValue(value = AuthCookie.NAME) String token) {
        final long userId = JWT.decode(token).getClaim(CustomClaims.USER_ID.getValue()).asLong();
        return databaseService.getFavoritesByUserId(userId);
    }

    @GetMapping("/watchlist")
    public List<Status> getWatchlist(@CookieValue(value = AuthCookie.NAME) String token) {
        final long userId = JWT.decode(token).getClaim(CustomClaims.USER_ID.getValue()).asLong();
        return databaseService.getWatchlistByUserId(userId);
    }

    @PostMapping("/movie/update")
    public void updateMovieStatus(@RequestBody String movieWrapperJson, @CookieValue(value = AuthCookie.NAME) String token) throws IOException {
        final long userId = JWT.decode(token).getClaim(CustomClaims.USER_ID.getValue()).asLong();
        ObjectMapper om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Status parsed = om.readValue(movieWrapperJson, Status.class);
        parsed.setUser(databaseService.getUserById(userId));
        parsed.setId(new StatusId(userId, parsed.getMovie().getId()));
        databaseService.updateupdateMovieStatusForUser(parsed);
    }
}
