package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText loginEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // layout для регистрации

        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        registerButton = findViewById(R.id.buttonRegister);

        db = new DatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.getUser(login) != null) {
                Toast.makeText(this, "Логин уже занят", Toast.LENGTH_SHORT).show();
                return;
            }

            String encryptionKey = CryptoUtils.generateKey(); // Генерация ключа для шифрования
            String encryptedPassword;
            try {
                encryptedPassword = CryptoUtils.encrypt(encryptionKey, password);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка при сохранении пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(login, encryptedPassword, encryptionKey);
            boolean success = db.insertUser(newUser);
            if (success) {
                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                finish(); // Вернуться к экрану авторизации
            } else {
                Toast.makeText(this, "Ошибка при регистрации", Toast.LENGTH_SHORT).show();
            }
        });
    }
}