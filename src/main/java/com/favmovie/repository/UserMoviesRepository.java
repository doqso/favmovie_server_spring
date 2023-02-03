package com.favmovie.repository;

public interface UserMoviesRepository {
    void addToFavorites(Long movieId, Long userId);

    void removeFromFavorites(Long movieId, Long userId);

    void addToWatchlist(Long movieId, Long userId);

    void removeFromWatchlist(Long movieId, Long userId);
}
