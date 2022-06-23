package com.example.note.notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note.MainActivity;
import com.example.note.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;
    private EditText editTit, editDesc;
    private Button btnSave, btnBack;
    private String Title, Description, NoteId, Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference("Notes");

        editTit = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDescription);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBackMain);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = editTit.getText().toString();
                Description = editDesc.getText().toString();
                if (!TextUtils.isEmpty(Title)&&!TextUtils.isEmpty(Description)){
                    Date now = java.util.Calendar.getInstance().getTime();
                    Time = now.toString();
                    NoteId = Title;
                    Note note = new Note(Title, Description, Time, NoteId);

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myRef.child(fAuth.getCurrentUser().getUid()).child(NoteId).setValue(note);
                            Toast.makeText(NewNoteActivity.this, "Note added!", Toast.LENGTH_SHORT).show();
                            Intent startIntent = new Intent(NewNoteActivity.this, MainActivity.class);
                            startActivity(startIntent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(NewNoteActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
            }
        });
    }
}