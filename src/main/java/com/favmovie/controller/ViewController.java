package com.favmovie.controller;

import com.favmovie.service.DatabaseService;
import com.favmovie.util.ExternalApiUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class ViewController {
    DatabaseService databaseService;

    public ViewController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/genres")
    public String index(Model model) throws IOException {
        model.addAttribute("genres", ExternalApiUtil.getGenres());
        model.addAttribute("img", ExternalApiUtil.IMAGE_BASE_URL);
        return "genres";
    }

    @RequestMapping(value = {"/genre/{id}"})
    public String moviesByGenre(@PathVariable("id") Integer id,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                Model model) throws IOException {
        model.addAttribute("movies", ExternalApiUtil.getMoviesByGenre(id, page).getResults());
        model.addAttribute("img", ExternalApiUtil.IMAGE_BASE_URL);
        return "movies";
    }

    @GetMapping("/movies")
    public String trendingMovies(Model model) throws IOException {
        model.addAttribute("movies", ExternalApiUtil.getTrendingMovies().getResults());
        model.addAttribute("img", ExternalApiUtil.IMAGE_BASE_URL);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String movieById(@PathVariable("id") int id, Model model) throws IOException {
        model.addAttribute("movie", ExternalApiUtil.getMovieById(id));
        model.addAttribute("img", ExternalApiUtil.IMAGE_BASE_URL);
        return "movie";
    }
}
