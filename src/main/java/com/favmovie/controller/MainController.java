package com.favmovie.controller;

import com.favmovie.entities.Movie;
import com.favmovie.entities.User;
import com.favmovie.models.MoviesListPage;
import com.favmovie.entities.Genre;
import com.favmovie.service.DatabaseService;
import com.favmovie.util.ExternalApiUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    DatabaseService databaseService;

    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

//    @GetMapping
//    public User index() throws IOException {
//        databaseService.addMovieToFavorite(movie, 1L);
//        return databaseService.getUserById(1L);
//    }
}
