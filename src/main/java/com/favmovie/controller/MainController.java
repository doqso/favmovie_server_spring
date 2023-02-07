package com.favmovie.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.favmovie.entities.*;
import com.favmovie.models.MoviesListPage;
import com.favmovie.service.DatabaseService;
import com.favmovie.util.ExternalApiUtil;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin
public class MainController {
    DatabaseService databaseService;

    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
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
    public Status movieById(@PathVariable("id") long id) throws IOException {
        Status status = databaseService.getMovieStatus(id, 1L);
        if (status == null) {
            status = new Status();
            status.setMovie(ExternalApiUtil.getMovieById(id));
            User user = databaseService.getUserById(1L);
            status.setUser(user);
        }
        return status;
    }

    @GetMapping("/favorites")
    public List<Status> getFavorites() {
        return databaseService.getFavoritesByUserId(1L);
    }

    @GetMapping("/watchlist")
    public List<Status> getWatchlist() {
        return databaseService.getWatchlistByUserId(1L);
    }

    @PostMapping("/movie/update")
    public void updateMovieStatus(@RequestBody String movieWrapperJson) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Status parsed = om.readValue(movieWrapperJson, Status.class);
        parsed.setUser(databaseService.getUserById(1L));
        parsed.setId(new StatusId(1L, parsed.getMovie().getId()));
        databaseService.updateupdateMovieStatusForUser(parsed);
    }
}
