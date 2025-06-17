package com.example.quanlyphimdienanh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlyphimdienanh.model.Movie;
import com.example.quanlyphimdienanh.model.MovieGenre;
import com.google.android.material.textfield.TextInputEditText;

public class EditMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_POSITION = "position";

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDescription;
    private RadioGroup radioGroupGenre;
    private TextInputEditText editTextYear;
    private TextInputEditText editTextDirector;
    private TextInputEditText editTextPosterUrl;
    private RatingBar ratingBar;
    private Button buttonSave;
    private Button buttonDelete;
    private Button buttonBack;

    private Movie movie;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        // Khởi tạo các view
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        radioGroupGenre = findViewById(R.id.radioGroupGenre);
        editTextYear = findViewById(R.id.editTextYear);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextPosterUrl = findViewById(R.id.editTextPosterUrl);
        ratingBar = findViewById(R.id.ratingBar);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonBack = findViewById(R.id.buttonBack);

        // Lấy thông tin phim từ intent
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        position = getIntent().getIntExtra(EXTRA_POSITION, -1);

        if (movie != null) {
            // Hiển thị thông tin phim hiện tại
            editTextTitle.setText(movie.getTitle());
            editTextDescription.setText(movie.getDescription());
            editTextYear.setText(movie.getYear());
            editTextDirector.setText(movie.getDirector());
            editTextPosterUrl.setText(movie.getPosterUrl());
            ratingBar.setRating((float) movie.getRating());

            // Chọn thể loại phim
            switch (movie.getGenre()) {
                case ACTION:
                    radioGroupGenre.check(R.id.radioAction);
                    break;
                case COMEDY:
                    radioGroupGenre.check(R.id.radioComedy);
                    break;
                case HORROR:
                    radioGroupGenre.check(R.id.radioHorror);
                    break;
                case ROMANCE:
                    radioGroupGenre.check(R.id.radioRomance);
                    break;
            }
        }

        // Xử lý sự kiện nút Lưu
        buttonSave.setOnClickListener(v -> saveMovie());

        // Xử lý sự kiện nút Xóa
        buttonDelete.setOnClickListener(v -> showDeleteConfirmationDialog());

        // Xử lý sự kiện nút Quay lại
        buttonBack.setOnClickListener(v -> finish());
    }

    private void saveMovie() {
        // Lấy thông tin từ các trường nhập liệu
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String posterUrl = editTextPosterUrl.getText().toString().trim();
        float rating = ratingBar.getRating();

        // Kiểm tra dữ liệu
        if (title.isEmpty() || description.isEmpty() || year.isEmpty() || director.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thể loại phim được chọn
        MovieGenre genre = getSelectedGenre();
        if (genre == null) {
            Toast.makeText(this, getString(R.string.select_genre), Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật thông tin phim
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setDirector(director);
        movie.setPosterUrl(posterUrl);
        movie.setRating(rating);

        // Trả về kết quả
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_MOVIE, movie);
        resultIntent.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private MovieGenre getSelectedGenre() {
        int selectedId = radioGroupGenre.getCheckedRadioButtonId();
        if (selectedId == -1) return null;

        if (selectedId == R.id.radioAction) return MovieGenre.ACTION;
        if (selectedId == R.id.radioComedy) return MovieGenre.COMEDY;
        if (selectedId == R.id.radioHorror) return MovieGenre.HORROR;
        if (selectedId == R.id.radioRomance) return MovieGenre.ROMANCE;
        return null;
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_movie))
                .setMessage(getString(R.string.delete_confirmation))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    // Trả về kết quả xóa
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_FIRST_USER, resultIntent);
                    finish();
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }
} 