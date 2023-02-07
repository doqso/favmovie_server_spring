package com.favmovie.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.favmovie.entities.Movie;
import com.favmovie.models.MoviesListPage;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

public class ExternalApiUtil {
    private static final String API_KEY = "ae569d541647c6f761dbc54697fd6097";

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private ExternalApiUtil() {
    }

    public static MoviesListPage getMoviesByGenre(int genreId, int page) throws IOException {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY +
                "&language=es-ES&sort_by=popularity.desc&include_adult=false&include_video=false&" +
                "page=" + page + "&with_genres=" + genreId;
        return makeRequest(url, MoviesListPage.class);
    }

    public static MoviesListPage getTrendingMovies() throws IOException {
        String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + API_KEY;
        return makeRequest(url, MoviesListPage.class);
    }

    public static Movie getMovieById(long movieId) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY + "&language=es-ES";
        return makeRequest(url, Movie.class);

    }

    /**
     * Make request to url and return response as object
     *
     * @param url         url to make request
     * @param entityClass class of object to return
     * @param <T>         type of object to return
     *                    (e.g. Movie.class, Genres.class, DiscoverMoviesPage.class)
     * @return response as object
     */
    private static <T> T makeRequest(String url, Class<T> entityClass) throws IOException {
        // make request
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        // read response
        InputStream is = con.getInputStream();
        try (var stringReader = new StringReader(new String(is.readAllBytes()))) {
            ObjectMapper om = new ObjectMapper();
            om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return om.readValue(stringReader, entityClass);
        }
    }
}
