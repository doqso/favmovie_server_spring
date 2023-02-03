package com.favmovie.controller;

import com.favmovie.models.MoviesListPage;
import com.favmovie.entities.Genre;
import com.favmovie.util.ExternalApiUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/genres")
    public List<Genre> index() throws IOException {
        return ExternalApiUtil.getGenres();
    }

    @GetMapping("/movies")
    public MoviesListPage movies() throws IOException {
        return ExternalApiUtil.getMoviesByGenre(28);
    }
//
//    @GetMapping("/movies/{id}")
//    public String moviesById(@PathVariable("id") int id) throws IOException {
//        return TmdbApiUtil.getMovieById(id);
//    }

    //xFJHb43ZAnnuiDztxZYsmyopweb.jpg
}
