package com.example.note.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.MainActivity;
import com.example.note.R;
import com.example.note.StartActivity;
import com.example.note.users.LoginActivity;
import com.example.note.users.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;

    private EditText showTitle, showDescription, showTime;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);




        showTitle = findViewById(R.id.showTitle);
        String title = getIntent().getExtras().getString("Title");
        showTitle.setText(title);

        showDescription = findViewById(R.id.showDescription);
        String description = getIntent().getExtras().getString("Description");
        showDescription.setText(description);

        showTime = findViewById(R.id.showTime);
        String time = getIntent().getExtras().getString("Time");
        showTime.setText(time);

        back =findViewById(R.id.btnBackMain);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        });
    }

    public void editNote(){

        if(titleChanged() || descriptionChanged()){
            Toast.makeText(ShowNoteActivity.this, "Note updated!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ShowNoteActivity.this, "ERROR!", Toast.LENGTH_SHORT).show();
        }

//        myRef.child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("Failed to edit value.", error.toException());
//            }
//        });
    }


    private boolean descriptionChanged() {
        String key = myRef.push().getKey();
        Note note = new Note();
        if(!note.getDescription().equals(showDescription.getText().toString())){
            myRef.child(key).child("description").setValue(showDescription.getText().toString());
//            note.getDescription() = showDescription.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean titleChanged() {
        String key = myRef.push().getKey();
        Note note = new Note();
        if(!note.getTitle().equals(showTitle.getText().toString())){
            myRef.child(key).child("title").setValue(showTitle.getText().toString());
            return true;
        }else {
            return false;
        }
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
            case R.id.edit_note:
                fAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");
                myRef = database.getReference( "Notes");
                editNote();
                Intent startIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                startActivity(startIntent);
                break;
            case R.id.delete_note:

                Intent signOutIntent = new Intent(ShowNoteActivity.this, StartActivity.class);
                startActivity(signOutIntent);
                finish();
                break;
        }
        return true;
    }
}