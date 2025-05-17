package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText loginEditText, passwordEditText;
    private Button loginButton, registerButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout для авторизации

        loginEditText = findViewById(R.id.editTextLogin);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);

        db = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = db.getUser(login);
                if (user != null) {
                    try {
                        String decryptedPassword = CryptoUtils.decrypt(user.getEncryptionKey(), user.getPassword());
                        if (decryptedPassword.equals(password)) {
                        } else {
                            Toast.makeText(MainActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Ошибка при проверке пароля", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}