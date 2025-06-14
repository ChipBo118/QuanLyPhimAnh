package com.example.quanlyphimdienanh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Màn hình chính của ứng dụng
 * Hiển thị logo và các nút điều hướng đến các màn hình khác
 */
public class MainActivity extends AppCompatActivity {
    // Khai báo các thành phần UI
    private Button btnHome;    // Nút chuyển đến trang chủ
    private Button btnLogin;   // Nút chuyển đến trang đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bật chế độ hiển thị edge-to-edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo các button từ layout
        btnHome = findViewById(R.id.home);
        btnLogin = findViewById(R.id.login);

        // Xử lý sự kiện click nút Trang chủ
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình Home
                Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
                // Thêm animation khi chuyển màn hình
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Xử lý sự kiện click nút Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình Login
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                // Thêm animation khi chuyển màn hình
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Xử lý edge-to-edge display
        // Điều chỉnh padding cho các thành phần UI để tránh bị che bởi thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        // Hiển thị dialog xác nhận khi nhấn nút Back
        new android.app.AlertDialog.Builder(this)
                .setTitle("Thoát ứng dụng")
                .setMessage("Bạn có chắc chắn muốn thoát?")
                .setPositiveButton("Có", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("Không", null)
                .show();
    }
}