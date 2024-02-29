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
        //on génère au lancement du code les différentes cartes en récupérant les notes contenues dans la base
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

    //fonction qui ouvre une nouvelle page pour créer une note
    private void creernote(View view){
        Intent intent = new Intent(this, Creationnote.class);
        startActivity(intent);
    }

    //fonction qui ouvre une nouvelle page pour créer une checklist
    private void creerchecklist(View viewById) {
        Intent intent = new Intent(this, CreationCheckList.class);
        startActivity(intent);
    }

    @Override
    //fonction qui génère le menu en haut de page
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;

    }
    @Override
    //message d'avertissement si la personne clique sur le bouton supprimer toutes les notes
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


    //on affiche toutes les notes en créant de nouvelles cardview
    private void affichernotes(){
        List<Note> notes = notesHandler.getAllNotes();
        LinearLayout layout = findViewById(R.id.layout_notes);
        for (Note note : notes){
            View cardview = creercard(note);
            layout.addView(cardview);
        }
    }
    //fonction pour générer une card
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
                alertenote(cardView);
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

    //fonction qui affiche un message d'alerte quand on appuie sur le bouton supprimer d'une note
    private void alertenote(View card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment supprimer cette note ?")
                .setTitle("Confirmation de suppression");
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                supprimernote(card);
            }
        });

        // Créer et afficher la boîte de dialogue
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //fonction de supression d'une note
    private void supprimernote(View card){;
        notesHandler.deleteNote(notesHandler.getNote((Integer) card.getTag()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    //fonction qui ouvre une new intent pour modifier une note déja existante
    private void modifiernote(View card) {
        Intent intent = new Intent(this, Creationnote.class);
        TextView textViewTitle = card.findViewById(R.id.textViewTitle);
        TextView textViewContent = card.findViewById(R.id.textViewContent);
        if (textViewTitle != null && textViewContent != null) {
            String title = textViewTitle.getText().toString();
            String content = textViewContent.getText().toString();
            int id = (int)card.getTag();
            intent.putExtra("id",id);
            intent.putExtra("titre", title);
            intent.putExtra("content", content);
            startActivity(intent);
        }
    }

}