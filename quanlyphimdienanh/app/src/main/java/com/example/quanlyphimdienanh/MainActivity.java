package com.example.quanlyphimdienanh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlyphimdienanh.utils.LanguageManager;

/**
 * Màn hình chính của ứng dụng
 * Hiển thị logo và các nút điều hướng đến các màn hình khác
 */
public class MainActivity extends AppCompatActivity {
    // Khai báo các thành phần UI
    private Button btnLogin;   // Nút chuyển đến trang đăng nhập
    private LanguageManager languageManager;
    private TextView textViewCurrentLanguage;
    private Button buttonChangeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kiểm tra trạng thái đăng nhập
        SharedPreferences loginPrefs = getSharedPreferences("login_state", MODE_PRIVATE);
        String loggedInUser = loginPrefs.getString("logged_in_user", null);
        if (loggedInUser != null) {
            // Đã đăng nhập, chuyển thẳng vào Home
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
            finish();
            return;
        }
        // Bật chế độ hiển thị edge-to-edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo LanguageManager
        languageManager = new LanguageManager(this);
        languageManager.updateResources(languageManager.getCurrentLanguage()); // Áp dụng ngôn ngữ đã lưu

        // Khởi tạo các button từ layout
        btnLogin = findViewById(R.id.login);
        textViewCurrentLanguage = findViewById(R.id.textViewCurrentLanguage);
        buttonChangeLanguage = findViewById(R.id.buttonChangeLanguage);

        // Cập nhật hiển thị ngôn ngữ hiện tại
        updateLanguageDisplay();

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

        // Xử lý sự kiện nút chuyển đổi ngôn ngữ
        buttonChangeLanguage.setOnClickListener(v -> {
            languageManager.toggleLanguage();
            recreate(); // Tạo lại activity để áp dụng ngôn ngữ mới
        });

        // Xử lý edge-to-edge display
        // Điều chỉnh padding cho các thành phần UI để tránh bị che bởi thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateLanguageDisplay() {
        String currentLanguage = languageManager.getCurrentLanguage();
        if (currentLanguage.equals("vi")) {
            textViewCurrentLanguage.setText(getString(R.string.current_language));
        } else {
            textViewCurrentLanguage.setText(getString(R.string.current_language)); // Sẽ lấy từ values-en/strings.xml
        }
    }

    @Override
    public void onBackPressed() {
        // Hiển thị dialog xác nhận khi nhấn nút Back
        new android.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.delete_confirmation))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    finish();
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }
}