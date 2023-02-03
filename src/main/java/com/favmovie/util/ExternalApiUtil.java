package com.favmovie.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.favmovie.models.MoviesListPage;
import com.favmovie.models.Genres;
import com.favmovie.entities.Movie;
import com.favmovie.entities.Genre;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

public class ExternalApiUtil {
    private static final String API_KEY = "ae569d541647c6f761dbc54697fd6097";

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private ExternalApiUtil() {
    }

    public static List<Genre> getGenres() throws IOException {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=es-ES";
        Genres genres = makeRequest(url, Genres.class);
        return genres.getGenres();
    }

    public static MoviesListPage getMoviesByGenre(int genreId) throws IOException {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY +
                "&language=es-ES&sort_by=popularity.desc&include_adult=false&include_video=false&" +
                "page=1&with_genres=" + genreId;
        return makeRequest(url, MoviesListPage.class);
    }

    public static MoviesListPage getTrendingMovies() throws IOException {
        String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + API_KEY;
        return makeRequest(url, MoviesListPage.class);
    }

    public static Movie getMovieById(int movieId) throws IOException {
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
