package com.example.quanlyphimdienanh;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.quanlyphimdienanh.model.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Lấy thông tin phim từ Intent
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie == null) {
            finish();
            return;
        }

        // Hiển thị thông tin phim
        ImageView imageViewPoster = findViewById(R.id.imageViewPoster);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewGenre = findViewById(R.id.textViewGenre);
        TextView textViewDirector = findViewById(R.id.textViewDirector);
        TextView textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        FloatingActionButton fabEdit = findViewById(R.id.fabEdit);

        // Thiết lập toolbar title
        getSupportActionBar().setTitle(movie.getTitle());

        // Hiển thị thông tin
        textViewTitle.setText(movie.getTitle());
        textViewGenre.setText("Thể loại: " + movie.getGenre());
        textViewDirector.setText("Đạo diễn: " + movie.getDirector());
        textViewReleaseDate.setText("Ngày phát hành: " + movie.getReleaseDate());
        textViewDescription.setText(movie.getDescription());
        ratingBar.setRating((float) movie.getRating());

        // Load ảnh poster
        if (!movie.getPosterUrl().isEmpty()) {
            Glide.with(this)
                    .load(movie.getPosterUrl())
                    .centerCrop()
                    .into(imageViewPoster);
        }

        // Xử lý sự kiện click nút Edit
        fabEdit.setOnClickListener(v -> {
            // TODO: Mở màn hình chỉnh sửa phim
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 