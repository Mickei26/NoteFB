package com.example.note.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import com.example.note.R;

public class ShowNoteActivity extends AppCompatActivity {

    EditText editTitle, editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        editTitle = findViewById(R.id.showTitle);
        String title = getIntent().getExtras().getString("Title");
        editTitle.setText(title);

        editDescription = findViewById(R.id.showDescription);
        String description = getIntent().getExtras().getString("Description");
        editDescription.setText(description);
    }
}