package com.example.quanlyphimdienanh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.quanlyphimdienanh.model.Movie;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    private static final String PREF_NAME = "movie_prefs";
    private static final String KEY_MOVIES = "movies";
    private final SharedPreferences preferences;
    private final Gson gson;

    public MovieManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveMovies(List<Movie> movies) {
        String json = gson.toJson(movies);
        preferences.edit().putString(KEY_MOVIES, json).apply();
    }

    public List<Movie> loadMovies() {
        String json = preferences.getString(KEY_MOVIES, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Movie>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void addMovie(Movie movie) {
        List<Movie> movies = loadMovies();
        movies.add(0, movie); // Thêm phim mới vào đầu danh sách
        saveMovies(movies);
    }

    public void updateMovie(int position, Movie updatedMovie) {
        List<Movie> movies = loadMovies();
        if (position >= 0 && position < movies.size()) {
            movies.set(position, updatedMovie);
            saveMovies(movies);
        }
    }

    public void deleteMovie(int position) {
        List<Movie> movies = loadMovies();
        if (position >= 0 && position < movies.size()) {
            movies.remove(position);
            saveMovies(movies);
        }
    }
} 