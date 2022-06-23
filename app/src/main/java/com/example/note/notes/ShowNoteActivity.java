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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShowNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;

    private EditText showTitle, showDescription, showTime;
    private Button back, save;
    private String Title, Description, Time, NoteId;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");

        showTitle = findViewById(R.id.showTitle);
        Title = getIntent().getExtras().getString("Title");
        showTitle.setText(Title);

        showDescription = findViewById(R.id.showDescription);
        Description = getIntent().getExtras().getString("Description");
        showDescription.setText(Description);

        showTime = findViewById(R.id.showTime);
        Time = getIntent().getExtras().getString("Time");
        showTime.setText(Time);

        note = getIntent().getParcelableExtra("note");
        if(note!=null){
            showTitle.setText(note.getTitle());
            showDescription.setText(note.getDescription());
            showTime.setText(note.getTime());
            NoteId = getIntent().getExtras().getString("Title");
        }

        myRef = database.getReference( "Notes").child(fAuth.getCurrentUser().getUid());

        save = findViewById(R.id.btnSaveEdit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = java.util.Calendar.getInstance().getTime();
                String Time = now.toString();

                Title = showTitle.getText().toString();
                Description = showDescription.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("title", Title);
                map.put("description", Description);
                map.put("time", Time);
                map.put("NoteID", NoteId);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.child(Title).updateChildren(map);
                        Intent startIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                        startActivity(startIntent);
                        Toast.makeText(ShowNoteActivity.this, "Note added!" + Time, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

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

    public void removeNote(){
        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference( "Notes");
        myRef.child(fAuth.getCurrentUser().getUid()).child(Title).removeValue();

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
        EditText getTitle = findViewById(R.id.showTitle);
        Title = getTitle.getText().toString();
        EditText getDescription = findViewById(R.id.showDescription);
        Description = getDescription.getText().toString();
        switch (item.getItemId()){
            case R.id.edit_note:
                break;

            case R.id.delete_note:
                removeNote();
                Intent removeNoteIntent = new Intent(ShowNoteActivity.this, MainActivity.class);
                startActivity(removeNoteIntent);
                finish();
                break;
        }
        return true;
    }
}