package com.favmovie.repository;

import com.favmovie.entities.Movie;
import com.favmovie.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByUserIdAndMovieId(Long movieId, Long userId);

    List<Status> findAllByIsFavoriteIsTrueAndUserId(Long userId);

    List<Status> findAllByIsWatchlistIsTrueAndUserId(Long userId);

    Long countAllByMovie(Movie movie);

    void deleteByMovieId(Long movieId);


}
