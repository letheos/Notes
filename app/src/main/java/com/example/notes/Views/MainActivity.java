package com.example.notes.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.notes.Managers.NotesHandler;
import com.example.notes.Models.Note;
import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotesHandler notesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesHandler = new NotesHandler(this);
        affichernotes();
        LinearLayout layout = findViewById(R.id.layout_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabButton = findViewById(R.id.floating_action_button);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, fabButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_ajouter, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.note) {
                            creernote(findViewById(android.R.id.content));
                            return true;
                        } else if (id == R.id.checklist) {
                            creerchecklist(findViewById(android.R.id.content));
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
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
        if (id == R.id.delete) {
            return true;
        }
        else if (id == R.id.deleteAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Voulez-vous vraiment supprimer toutes vos notes ?")
                    .setTitle("Avertissement");
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    notesHandler.deleteAllNotes();
                    MainActivity.this.recreate();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void affichernotes(){
        List<Note> notes = notesHandler.getAllNotes();
        LinearLayout layout = findViewById(R.id.layout_notes);
        for (Note note : notes){
            View cardview = creercard(note);
            layout.addView(cardview);
        }
    }

    private View creercard(Note note){
        View cardView = LayoutInflater.from(this).inflate(R.layout.note_card_layout, null);
        TextView textViewTitle = cardView.findViewById(R.id.textViewTitle);
        TextView textViewContent = cardView.findViewById(R.id.textViewContent);
        textViewTitle.setText(note.getTitre());
        textViewContent.setText(note.getContenu());
        cardView.setTag(note.getId());
        Button buttonSupprimer = cardView.findViewById(R.id.supprimernote);
        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimernote(cardView);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiernote(v);
            }
        });

        return cardView;
    }
    private void supprimernote(View card){;
        notesHandler.deleteNote(notesHandler.getNote((Integer) card.getTag()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    private void modifiernote(View card) {
        Intent intent = new Intent(this, Creationnote.class);
        TextView textViewTitle = card.findViewById(R.id.textViewTitle);
        TextView textViewContent = card.findViewById(R.id.textViewContent);
        if (textViewTitle != null && textViewContent != null) {
            String title = textViewTitle.getText().toString();
            String content = textViewContent.getText().toString();
            intent.putExtra("titre", title);
            intent.putExtra("content", content);
            startActivity(intent);
        }
    }

}