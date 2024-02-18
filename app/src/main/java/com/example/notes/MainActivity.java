package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabButton = findViewById(R.id.floating_action_button);

        fabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                creernote(findViewById(android.R.id.content));
            }
        });
    }
    private void creernote(View view){
        Intent intent = new Intent(this, Creationnote.class);
        startActivity(intent);
    }

    private void creerchecklist(View viewById) {
        Intent intent = new Intent(this, CreationCheckList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addNote) {
            creernote(findViewById(android.R.id.content));
            return true;
        }
        else if (id == R.id.addCheckList) {
            creerchecklist(findViewById(android.R.id.content));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}