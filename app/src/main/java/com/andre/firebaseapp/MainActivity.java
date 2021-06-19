package com.andre.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button btnLogout,btnStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.main_btn_logout);
        btnStorage.findViewById(R.id.main_btn_storage);

        btnStorage.setOnClickListener(v -> {
            //abrir storage activity

            Intent intent = new Intent(getApplicationContext(),StorageActivity.class);
            startActivity(intent);

        });

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            finish();
            ;
        });

        TextView textEmail = findViewById(R.id.main_text_email);
        textEmail.setText(auth.getCurrentUser().getEmail());

        TextView textNome = findViewById(R.id.main_test_user);
        textNome.setText(auth.getCurrentUser().getDisplayName());

    }
    }


