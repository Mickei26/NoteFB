package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note.more.ListNoteAdapter;
import com.example.note.notes.NewNoteActivity;
import com.example.note.notes.Note;
import com.example.note.notes.ShowNoteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;

    private ListView lView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference("Notes");

        lView = findViewById(R.id.listView);

        updateUI();

    }

    private void updateUI(){
        if(fAuth.getCurrentUser() != null){
            Log.i("MainActivity", "fAuth != null");
            showNote();
        }else {
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }
    }

    private void showNote(){
        ArrayList<Note> list = new ArrayList<>();
        ListNoteAdapter adapter = new ListNoteAdapter(this, list);

        myRef.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Note note = dataSnapshot.getValue(Note.class);
                    adapter.add(note);
                    lView.setAdapter(adapter);

                    lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, ShowNoteActivity.class);
                            String title = list.get(position).getTitle();
                            String description = list.get(position).getDescription();
                            String time = list.get(position).getTime();
                            intent.putExtra("Position", position);
                            intent.putExtra("Title", title);
                            intent.putExtra("Description", description);
                            intent.putExtra("Time", time);
                            startActivity(intent);
                            finish();
//                            Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.add_note:
                Intent startIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(startIntent);
                break;
            case R.id.sign_out:
                fAuth.signOut();
                Intent signOutIntent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(signOutIntent);
                finish();
                break;
        }
        return true;
    }
}