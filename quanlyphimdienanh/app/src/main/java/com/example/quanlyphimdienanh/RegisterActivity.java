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

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editRegisterUsername;
    private EditText editRegisterPassword;
    private Button buttonRegister;
    private TextView textGoToLogin;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editRegisterUsername = findViewById(R.id.edit_register_username);
        editRegisterPassword = findViewById(R.id.edit_register_password);
        buttonRegister = findViewById(R.id.button_register);
        textGoToLogin = findViewById(R.id.text_go_to_login);
        buttonBack = findViewById(R.id.button_back);

        buttonRegister.setOnClickListener(v -> registerUser());
        textGoToLogin.setOnClickListener(v -> finish());
        
        buttonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void registerUser() {
        String username = editRegisterUsername.getText().toString().trim();
        String password = editRegisterPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("user_accounts", Context.MODE_PRIVATE);
        if (sharedPreferences.contains(username)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username, password); // Lưu tên đăng nhập và mật khẩu
            editor.apply();
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình đăng nhập sau khi đăng ký thành công
        }
    }
} 