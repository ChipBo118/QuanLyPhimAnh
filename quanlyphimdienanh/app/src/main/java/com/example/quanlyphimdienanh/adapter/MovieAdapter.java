package com.example.quanlyphimdienanh.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlyphimdienanh.EditMovieActivity;
import com.example.quanlyphimdienanh.R;
import com.example.quanlyphimdienanh.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private final OnMovieClickListener listener;
    private final ActivityResultLauncher<Intent> editMovieLauncher;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener, ActivityResultLauncher<Intent> editMovieLauncher) {
        this.movies = movies;
        this.listener = listener;
        this.editMovieLauncher = editMovieLauncher;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void updateMovies(List<Movie> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView moviePoster;
        private final TextView movieTitle;
        private final TextView movieGenre;
        private final TextView movieYear;
        private final TextView movieDirector;
        private final RatingBar movieRating;
        private final ImageButton btnEdit;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieGenre = itemView.findViewById(R.id.movieGenre);
            movieYear = itemView.findViewById(R.id.movieYear);
            movieDirector = itemView.findViewById(R.id.movieDirector);
            movieRating = itemView.findViewById(R.id.movieRating);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onMovieClick(movies.get(position));
                }
            });

            btnEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && editMovieLauncher != null) {
                    Movie movie = movies.get(position);
                    Intent intent = new Intent(itemView.getContext(), EditMovieActivity.class);
                    intent.putExtra(EditMovieActivity.EXTRA_MOVIE, movie);
                    intent.putExtra(EditMovieActivity.EXTRA_POSITION, position);
                    editMovieLauncher.launch(intent);
                }
            });
        }

        public void bind(Movie movie) {
            if (movie == null) return;

            movieTitle.setText(movie.getTitle());
            movieGenre.setText(movie.getGenreDisplayName());
            movieYear.setText(movie.getYear());
            movieDirector.setText(movie.getDirector());
            movieRating.setRating((float) movie.getRating());

            // Load poster image using Glide
            if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(movie.getPosterUrl())
                        .placeholder(R.drawable.default_movie_poster)
                        .error(R.drawable.default_movie_poster)
                        .into(moviePoster);
            } else {
                moviePoster.setImageResource(R.drawable.default_movie_poster);
            }
        }
    }
} 