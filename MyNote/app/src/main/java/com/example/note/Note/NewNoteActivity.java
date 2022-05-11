package com.example.note.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.note.MainActivity;
import com.example.note.R;
import com.example.note.StartActivity;
import com.example.note.usersign.RegisterActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class NewNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;

    private EditText editTit, editDesc;
    private Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        fAuth = FirebaseAuth.getInstance();

        editTit = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDescription);

        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBackMain);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTit.getText().toString();
                String description = editDesc.getText().toString();

                if (!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(description)){
                    SaveNote(title, description);
                }else {
//                    Snackbar.make(view, "Fill all", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        });

    }

    private void SaveNote(String Title, String Description){

    }
}