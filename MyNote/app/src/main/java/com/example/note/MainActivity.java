package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.Note.NewNoteActivity;
import com.example.note.Note.Note;
import com.example.note.Note.NoteViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ui.database.FirebaseRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView viewList;
    private GridLayoutManager gridLayoutManager;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        viewList = findViewById(R.id.viewList);
        viewList.setHasFixedSize(true);
        viewList.setLayoutManager(gridLayoutManager);

        updateUI();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Note, NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Note, NoteViewHolder>;

    }

    private void updateUI(){
        if(fAuth.getCurrentUser() != null){
            Log.i("MainActivity", "fAuth != null");
        }else {
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

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