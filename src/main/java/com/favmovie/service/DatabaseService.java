package com.favmovie.service;

import com.favmovie.entities.Movie;
import com.favmovie.entities.User;
import com.favmovie.repository.MoviesRepository;
import com.favmovie.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    MoviesRepository moviesRepository;
    UserRepository userRepository;

    public DatabaseService(MoviesRepository moviesRepository, UserRepository userRepository) {
        this.moviesRepository = moviesRepository;
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Agrega una película a favoritos y la guarda en la base de datos
     * @param movie Película a agregar
     * @param userId Id del usuario
     */
    public void addMovieToFavorite(Movie movie, Long userId) {
        // primero guarda la pelicula en la base de datos
        moviesRepository.save(movie);
        // posteriormente crea la relacion entre el usuario y la pelicula en la tabla de favoritos
        moviesRepository.addToFavorites(movie.getId(), userId);
    }

    public void removeMovieFromFavorite(Movie movie, Long userId) {
        // primero elimina la relacion entre el usuario y la pelicula en la tabla de favoritos
        moviesRepository.removeFromFavorites(movie.getId(), userId);
        // posteriormente elimina la pelicula de la base de datos
        moviesRepository.deleteById(movie.getId());
    }

    public void addMovieToWatchlist(Movie movie, Long userId) {
        moviesRepository.save(movie);
        moviesRepository.addToWatchlist(movie.getId(), userId);
    }

    public void removeMovieFromWatchlist(Movie movie, Long userId) {
        moviesRepository.removeFromWatchlist(movie.getId(), userId);
        moviesRepository.deleteById(movie.getId());
    }
}
