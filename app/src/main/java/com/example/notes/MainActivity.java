package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button créernote = findViewById(R.id.ajouterNote);
        créernote.setOnClickListener(this::créernote);
    }
    private void créernote(View view){
        Intent intent = new Intent(this, Creationnote.class);
        startActivity(intent);
    }
}