package com.favmovie.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class UserMoviesRepositoryImpl implements UserMoviesRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addToFavorites(Long movieId, Long userId) {
        Query query = em.createNativeQuery("insert into favorites (user_id, movie_id) values (?1, ?2)");
        makeQuery(query, movieId, userId);
    }

    @Override
    @Transactional
    public void removeFromFavorites(Long movieId, Long userId) {
        Query query = em.createNativeQuery("delete from favorites where user_id=?1 and movie_id=?2");
        makeQuery(query, movieId, userId);
    }

    @Override
    @Transactional
    public void addToWatchlist(Long movieId, Long userId) {
        Query query = em.createNativeQuery("insert into watchlist (user_id, movie_id) values (?1, ?2)");
        makeQuery(query, movieId, userId);

    }

    @Override
    @Transactional
    public void removeFromWatchlist(Long movieId, Long userId) {
        Query query = em.createNativeQuery("delete from watchlist where user_id=?1 and movie_id=?2");
        makeQuery(query, movieId, userId);

    }

    private void makeQuery(Query query, Long movieId, Long userId) {
        query.setParameter(1, userId);
        query.setParameter(2, movieId);
        query.executeUpdate();
    }
}
