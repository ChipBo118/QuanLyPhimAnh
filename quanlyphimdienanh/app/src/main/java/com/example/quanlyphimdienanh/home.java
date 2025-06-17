package com.example.quanlyphimdienanh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphimdienanh.adapter.MovieAdapter;
import com.example.quanlyphimdienanh.model.Movie;
import com.example.quanlyphimdienanh.model.MovieGenre;
import com.example.quanlyphimdienanh.utils.MovieManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Màn hình chính của ứng dụng quản lý phim
 * Hiển thị danh sách phim, cho phép tìm kiếm và lọc theo thể loại
 */
public class home extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {
    // Các thành phần UI
    private RecyclerView recyclerView;        // Hiển thị danh sách phim
    private MovieAdapter adapter;             // Adapter cho RecyclerView
    private List<Movie> allMovies;            // Danh sách tất cả phim
    private SearchView searchView;            // Ô tìm kiếm
    private ChipGroup genreChipGroup;         // Nhóm các chip thể loại phim
    private FloatingActionButton fabAddMovie; // Nút thêm phim mới
    private MovieManager movieManager;

    private final ActivityResultLauncher<Intent> addMovieLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Movie newMovie = result.getData().getParcelableExtra("new_movie");
                    if (newMovie != null) {
                        // Lưu phim mới
                        movieManager.addMovie(newMovie);
                        // Cập nhật danh sách phim
                        allMovies = movieManager.loadMovies();
                        adapter.updateMovies(allMovies);
                        // Hiển thị thông báo
                        Toast.makeText(this, "Đã thêm phim mới", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> editMovieLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Cập nhật phim
                    Movie updatedMovie = result.getData().getParcelableExtra(EditMovieActivity.EXTRA_MOVIE);
                    int position = result.getData().getIntExtra(EditMovieActivity.EXTRA_POSITION, -1);
                    if (updatedMovie != null && position != -1) {
                        movieManager.updateMovie(position, updatedMovie);
                        allMovies = movieManager.loadMovies();
                        adapter.updateMovies(allMovies);
                        Toast.makeText(this, "Đã cập nhật phim", Toast.LENGTH_SHORT).show();
                    }
                } else if (result.getResultCode() == RESULT_FIRST_USER && result.getData() != null) {
                    // Xóa phim
                    int position = result.getData().getIntExtra(EditMovieActivity.EXTRA_POSITION, -1);
                    if (position != -1) {
                        movieManager.deleteMovie(position);
                        allMovies = movieManager.loadMovies();
                        adapter.updateMovies(allMovies);
                        Toast.makeText(this, "Đã xóa phim", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo MovieManager
        movieManager = new MovieManager(this);

        // Khởi tạo các view từ layout
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        genreChipGroup = findViewById(R.id.genreChipGroup);
        fabAddMovie = findViewById(R.id.fabAddMovie);

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // Load danh sách phim từ bộ nhớ
        allMovies = movieManager.loadMovies();
        if (allMovies.isEmpty()) {
            // Nếu chưa có phim nào, thêm dữ liệu mẫu
            allMovies = getDummyMovies();
            movieManager.saveMovies(allMovies);
        }
        
        adapter = new MovieAdapter(allMovies, this, editMovieLauncher);
        recyclerView.setAdapter(adapter);

        // Thiết lập SearchView để tìm kiếm phim
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterMovies(query, getSelectedGenre());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovies(newText, getSelectedGenre());
                return true;
            }
        });

        // Thiết lập ChipGroup để lọc phim theo thể loại
        genreChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != View.NO_ID) {
                Chip selectedChip = findViewById(checkedId);
                String selectedGenre = selectedChip.getText().toString();
                filterMovies(searchView.getQuery().toString(), selectedGenre);
            } else {
                // Nếu không có chip nào được chọn, hiển thị tất cả phim
                filterMovies(searchView.getQuery().toString(), "Tất cả");
            }
        });

        // Thiết lập nút thêm phim mới
        fabAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMovieActivity.class);
            addMovieLauncher.launch(intent);
        });
    }

    /**
     * Lấy thể loại phim đang được chọn từ ChipGroup
     * @return Tên thể loại phim được chọn, hoặc "Tất cả" nếu không có thể loại nào được chọn
     */
    private String getSelectedGenre() {
        int selectedChipId = genreChipGroup.getCheckedChipId();
        if (selectedChipId == View.NO_ID) {
            return "Tất cả";
        }
        Chip selectedChip = findViewById(selectedChipId);
        return selectedChip.getText().toString();
    }

    /**
     * Lọc danh sách phim dựa trên từ khóa tìm kiếm và thể loại
     * @param query Từ khóa tìm kiếm
     * @param genreDisplayName Tên thể loại phim
     */
    private void filterMovies(String query, String genreDisplayName) {
        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> {
                    boolean matchesQuery = query.isEmpty() || 
                            movie.getTitle().toLowerCase().contains(query.toLowerCase());
                    
                    boolean matchesGenre = genreDisplayName.equals("Tất cả") || 
                            movie.getGenreDisplayName().equals(genreDisplayName);
                    
                    return matchesQuery && matchesGenre;
                })
                .collect(Collectors.toList());
        adapter.updateMovies(filteredMovies);
    }

    /**
     * Tạo dữ liệu mẫu cho danh sách phim
     * @return Danh sách các phim mẫu
     */
    private List<Movie> getDummyMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Avengers: Endgame", "Phim siêu anh hùng", MovieGenre.ACTION, "2019", "Russo Brothers", "", 4.5));
        movies.add(new Movie("The Hangover", "Phim hài", MovieGenre.COMEDY, "2009", "Todd Phillips", "", 4.0));
        movies.add(new Movie("The Conjuring", "Phim kinh dị", MovieGenre.HORROR, "2013", "James Wan", "", 4.2));
        movies.add(new Movie("Titanic", "Phim tình cảm", MovieGenre.ROMANCE, "1997", "James Cameron", "", 4.8));
        return movies;
    }

    /**
     * Xử lý sự kiện khi người dùng click vào một phim
     * @param movie Phim được chọn
     */
    @Override
    public void onMovieClick(Movie movie) {
        // Mở màn hình chi tiết phim
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}