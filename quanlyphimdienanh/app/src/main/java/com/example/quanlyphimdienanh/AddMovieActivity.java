package com.example.quanlyphimdienanh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import com.example.quanlyphimdienanh.model.Movie;
import com.example.quanlyphimdienanh.model.MovieGenre;
import com.google.android.material.textfield.TextInputEditText;

public class AddMovieActivity extends AppCompatActivity {
    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDescription;
    private RadioGroup radioGroupGenre;
    private TextInputEditText editTextReleaseDate;
    private TextInputEditText editTextDirector;
    private TextInputEditText editTextPosterUrl;
    private RatingBar ratingBar;
    private Button buttonAdd;
    private Button buttonBack;
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private ImageView imageViewPoster;
    private Button buttonSelectImage;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Khởi tạo các view
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        radioGroupGenre = findViewById(R.id.radioGroupGenre);
        editTextReleaseDate = findViewById(R.id.editTextReleaseDate);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextPosterUrl = findViewById(R.id.editTextPosterUrl);
        ratingBar = findViewById(R.id.ratingBar);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonBack = findViewById(R.id.buttonBack);
        imageViewPoster = findViewById(R.id.imageViewPoster);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);

        // Thiết lập sự kiện click cho nút thêm phim
        buttonAdd.setOnClickListener(v -> addMovie());

        // Thiết lập sự kiện click cho nút quay lại
        buttonBack.setOnClickListener(v -> finish());

        // Thiết lập sự kiện click cho nút chọn ảnh
        buttonSelectImage.setOnClickListener(v -> openGallery());
    }

    private void addMovie() {
        // Lấy thông tin từ các trường nhập liệu
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String releaseDate = editTextReleaseDate.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String posterUriString = (selectedImageUri != null) ? selectedImageUri.toString() : "";
        float rating = ratingBar.getRating();

        // Kiểm tra dữ liệu nhập vào
        if (title.isEmpty() || description.isEmpty() || releaseDate.isEmpty() || director.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thể loại phim được chọn
        MovieGenre genre = getSelectedGenre();
        if (genre == null) {
            Toast.makeText(this, getString(R.string.select_genre), Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng phim mới
        Movie newMovie = new Movie(title, description, genre, releaseDate, director, posterUriString, rating);

        // Trả về kết quả cho màn hình chính
        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_movie", newMovie);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private MovieGenre getSelectedGenre() {
        int selectedId = radioGroupGenre.getCheckedRadioButtonId();
        if (selectedId == R.id.radioAction) {
            return MovieGenre.ACTION;
        } else if (selectedId == R.id.radioComedy) {
            return MovieGenre.COMEDY;
        } else if (selectedId == R.id.radioHorror) {
            return MovieGenre.HORROR;
        } else if (selectedId == R.id.radioRomance) {
            return MovieGenre.ROMANCE;
        }
        return null;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageViewPoster.setImageURI(selectedImageUri);
            }
        }
    }
} 