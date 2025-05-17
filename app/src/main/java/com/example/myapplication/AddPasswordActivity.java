package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText serviceNameEditText, loginEditText, passwordEditText, notesEditText;
    private Button saveButton;
    private DatabaseHelper db;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        userId = getIntent().getLongExtra("userId", -1);
        db = new DatabaseHelper(this);

        serviceNameEditText = findViewById(R.id.editTextServiceName);
        loginEditText = findViewById(R.id.editTextLogin);
        passwordEditText = findViewById(R.id.editTextPassword);
        notesEditText = findViewById(R.id.editTextNotes);
        saveButton = findViewById(R.id.buttonSavePassword);

        saveButton.setOnClickListener(v -> {
            String serviceName = serviceNameEditText.getText().toString();
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String notes = notesEditText.getText().toString();

            if (serviceName.isEmpty() || login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все обязательные поля", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                String encryptedPassword = CryptoUtils.encrypt("default_key", password); // Можно использовать ключ пользователя
                Password pw = new Password(userId, serviceName, login, encryptedPassword, notes);
                boolean success = db.insertPassword(pw);
                if (success) {
                    Toast.makeText(this, "Пароль сохранён", Toast.LENGTH_SHORT).show();
                    finish(); // Вернуться к списку
                } else {
                    Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка шифрования", Toast.LENGTH_SHORT).show();
            }
        });
    }
}