package com.favmovie.repository;

import com.favmovie.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movie, Long>, UserMoviesRepository {

}
