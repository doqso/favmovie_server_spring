package com.favmovie.repository;

import com.favmovie.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface MoviesRepository extends JpaRepository<Movie, Long> {
}
