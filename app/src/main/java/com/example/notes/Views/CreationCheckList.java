package com.example.notes.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.notes.R;

public class CreationCheckList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_check_list);

        FloatingActionButton add = findViewById(R.id.addCheck);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout horizontal = new LinearLayout(CreationCheckList.this);


                CheckBox checkBox = new CheckBox(CreationCheckList.this);
                EditText text = new EditText(CreationCheckList.this);
                text.setEms(10);
                ImageButton delete = new ImageButton(CreationCheckList.this);
                delete.setImageDrawable(getDrawable(R.drawable.baseline_close_24));

                horizontal.addView(checkBox);
                horizontal.addView(text);
                horizontal.addView(delete);
                horizontal.setDividerPadding(3);

                LinearLayout layout = findViewById(R.id.vertical);
                layout.addView(horizontal);
            }
        });
    }
}