package com.example.notes.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notes.Managers.NotesHandler;
import com.example.notes.Models.Note;
import com.example.notes.R;
import com.google.android.material.textfield.TextInputLayout;

public class Creationnote extends AppCompatActivity {
    private NotesHandler notesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creationnote);
        notesHandler = new NotesHandler(this);
        Intent intent = getIntent();
        TextInputLayout titre = findViewById(R.id.edittexttitre);
        TextInputLayout contenu = findViewById(R.id.edittextcontenu);
        if (intent.hasExtra("titre") && intent.hasExtra("content") && intent.hasExtra("id")){
            titre.getEditText().setText(intent.getStringExtra("titre"));
            contenu.getEditText().setText(intent.getStringExtra("content"));
            Button enregistrer = findViewById(R.id.valider);

            enregistrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifiernote(intent);
                }
            });
        }
        else {
            Button enregistrer = findViewById(R.id.valider);
            enregistrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enregistrernote();
                }
            });
        }
        }




    private void enregistrernote() {
        TextInputLayout textInputLayoutTitre = findViewById(R.id.edittexttitre);
        String titresaisi = textInputLayoutTitre.getEditText().getText().toString().trim();

        TextInputLayout textInputLayoutContenu = findViewById(R.id.edittextcontenu);
        String contenusaisi = textInputLayoutContenu.getEditText().getText().toString().trim();

        if (!titresaisi.isEmpty() && !contenusaisi.isEmpty()) {
            notesHandler.addNote(new Note(titresaisi, contenusaisi));
        } else {

        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void modifiernote(Intent intent){
        int noteId = intent.getIntExtra("id", -1);
        Note note = notesHandler.getNote(noteId);
        TextInputLayout textInputLayoutTitre = findViewById(R.id.edittexttitre);
        String titresaisi = textInputLayoutTitre.getEditText().getText().toString().trim();

        TextInputLayout textInputLayoutContenu = findViewById(R.id.edittextcontenu);
        String contenusaisi = textInputLayoutContenu.getEditText().getText().toString().trim();

        if (!titresaisi.isEmpty() && !contenusaisi.isEmpty()) {
            note.setTitre(titresaisi);
            note.setContenu(contenusaisi);
            notesHandler.updateNote(note);
        } else {

        }
        Intent intent2= new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}
