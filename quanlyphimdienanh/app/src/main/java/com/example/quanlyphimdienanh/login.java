package com.example.quanlyphimdienanh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {

    private EditText editUsername;
    private EditText editPassword;
    private Button buttonLogin;
    private TextView textRegisterNow;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editUsername = findViewById(R.id.edit_tenlogin);
        editPassword = findViewById(R.id.edit_password);
        buttonLogin = findViewById(R.id.button_login);
        textRegisterNow = findViewById(R.id.text_register_now);
        buttonBack = findViewById(R.id.button_back);

        buttonLogin.setOnClickListener(v -> loginUser());
        textRegisterNow.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, RegisterActivity.class);
            startActivity(intent);
        });
        
        buttonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void loginUser() {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("user_accounts", Context.MODE_PRIVATE);
        String savedPassword = sharedPreferences.getString(username, null);

        if (savedPassword != null && savedPassword.equals(password)) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            // Lưu trạng thái đăng nhập
            SharedPreferences loginPrefs = getSharedPreferences("login_state", Context.MODE_PRIVATE);
            loginPrefs.edit().putString("logged_in_user", username).apply();
            // Chuyển hướng đến màn hình chính hoặc màn hình tiếp theo
            Intent intent = new Intent(login.this, home.class); // Ví dụ: chuyển đến màn hình home
            startActivity(intent);
            finish(); // Đóng màn hình đăng nhập
        } else {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
        }
    }
}