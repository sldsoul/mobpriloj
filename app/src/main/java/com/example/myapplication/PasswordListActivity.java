package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PasswordListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Password> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        // Инициализация списка паролей
        passwordList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}