package com.favmovie.controller;

import com.favmovie.util.ExternalApiUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/genres")
    public String index(Model model) throws IOException {
        model.addAttribute("genres", ExternalApiUtil.getGenres());
        model.addAttribute("img", ExternalApiUtil.IMAGE_BASE_URL);
        return "genres";
    }

    @GetMapping("/genres/{id}")
    public String moviesByGenre(@PathVariable("id") int id, Model model) throws IOException {
        model.addAttribute("movies", ExternalApiUtil.getMoviesByGenre(id).getResults());
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
