package com.example.note.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note.MainActivity;
import com.example.note.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class ShowNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;

    private EditText editTitle, editDescription, editTime;
    private Button back, save, delete;
    private String Title, Description, Time, NoteId;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");

        editTitle = findViewById(R.id.showTitle);
        editDescription = findViewById(R.id.showDescription);
        editTime = findViewById(R.id.showTime);

        Bundle bundle = getIntent().getExtras();
        Note note = (Note) bundle.getParcelable("note");
        editTitle.setText(note.getTitle());
        editDescription.setText(note.getDescription());
        editTime.setText(note.getTime());
        NoteId = note.getNoteID();

        myRef = database.getReference( "Notes");
        save = findViewById(R.id.btnSaveEdit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = java.util.Calendar.getInstance().getTime();
                Time = now.toString();

                Title = editTitle.getText().toString();
                Description = editDescription.getText().toString();

//                Note noteUpdate = new Note(Title, Description, Time, NoteId);

                HashMap hashMap = new HashMap<>();
                hashMap.put("title", Title);
                hashMap.put("description", Description);
                hashMap.put("time", Time);
                hashMap.put("noteID", NoteId);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        myRef.child(fAuth.getCurrentUser().getUid()).child(NoteId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                myRef.child(fAuth.getCurrentUser().getUid()).child(NoteId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(ShowNoteActivity.this, "Note Updated!", Toast.LENGTH_SHORT).show();
                                        Intent startIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                                        startActivity(startIntent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ShowNoteActivity.this, "Error to update!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ShowNoteActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        delete = findViewById(R.id.btnDeleteNote);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(fAuth.getCurrentUser().getUid()).child(NoteId).removeValue();
                Toast.makeText(ShowNoteActivity.this, "Deleted!" + NoteId, Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                startActivity(startIntent);
            }
        });


        back =findViewById(R.id.btnBackMain);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                startActivity(startIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
//            case R.id.edit_note:
//                break;

//            case R.id.delete_note:
//                fAuth = FirebaseAuth.getInstance();
//                FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");
//                myRef = database.getReference( "Notes");
//                myRef.child(fAuth.getCurrentUser().getUid()).child(NoteId).removeValue();
//                Toast.makeText(ShowNoteActivity.this, "Deleted!" + NoteId, Toast.LENGTH_SHORT).show();
//                Intent removeNoteIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
//                startActivity(removeNoteIntent);
//                break;
        }
        return true;
    }
}