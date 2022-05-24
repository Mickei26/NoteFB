package com.example.note.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.MainActivity;
import com.example.note.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class NewNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;

    private EditText editTit, editDesc;
    private Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        fAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Notes");

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
                    Snackbar.make(v, "Fill all", Snackbar.LENGTH_SHORT).show();
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
        if(fAuth.getCurrentUser()!=null){
            DatabaseReference newNoteRef = myRef;

            HashMap NoteMap = new HashMap();
            NoteMap.put("title", Title);
            NoteMap.put("description", Description);

            Date now = java.util.Calendar.getInstance().getTime();
            String Time = now.toString();
            Note newNote = new Note(Title, Description, Time);

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    newNoteRef.child(fAuth.getCurrentUser().getUid()).push().setValue(newNote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(NewNoteActivity.this, "Note added!", Toast.LENGTH_SHORT).show();
                                Intent startIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                                startActivity(startIntent);
                                finish();
                            }else {
                                Toast.makeText(NewNoteActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            mainThread.start();
        }else {
            Toast.makeText(NewNoteActivity.this, "User not sign in!", Toast.LENGTH_SHORT).show();
        }
    }
}