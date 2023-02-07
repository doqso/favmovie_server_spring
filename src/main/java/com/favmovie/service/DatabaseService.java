package com.favmovie.service;

import com.favmovie.entities.Genre;
import com.favmovie.entities.Movie;
import com.favmovie.entities.Status;
import com.favmovie.entities.User;
import com.favmovie.repository.GenreRepository;
import com.favmovie.repository.MoviesRepository;
import com.favmovie.repository.StatusRepository;
import com.favmovie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    MoviesRepository moviesRepository;
    UserRepository userRepository;
    GenreRepository genreRepository;
    StatusRepository statusRepository;

    public DatabaseService(MoviesRepository moviesRepository, UserRepository userRepository, GenreRepository genreRepository, StatusRepository statusRepository) {
        this.moviesRepository = moviesRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.statusRepository = statusRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Movie getMovieById(Long id) {
        return moviesRepository.findById(id).orElse(null);
    }

    public List<Status> getFavoritesByUserId(Long userId) {
        return statusRepository.findAllByIsFavoriteIsTrueAndUserId(userId);
    }

    public List<Status> getWatchlistByUserId(Long userId) {
        return statusRepository.findAllByIsWatchlistIsTrueAndUserId(userId);
    }
    public Status getMovieStatus(Long movieId, Long userId) {
        return statusRepository.findByUserIdAndMovieId(userId, movieId);
    }

    public void updateupdateMovieStatusForUser(Status status) {
        boolean movieIsInDatabase = moviesRepository.existsById(status.getMovie().getId());
        if (!movieIsInDatabase) moviesRepository.save(status.getMovie());
        statusRepository.save(status);
    }
}
