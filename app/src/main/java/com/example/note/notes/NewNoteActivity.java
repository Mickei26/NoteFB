package com.example.note.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference myRef;
    private EditText editTit, editDesc;
    private Button btnSave, btnBack, btnChoosePIC;
    private String Title, Description, NoteId, Time;
    private ImageView imagePIC;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://note-2606-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference("Notes");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        editTit = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDescription);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBackMain);
        btnChoosePIC = findViewById(R.id.btnChoose);

        imagePIC = findViewById(R.id.imgAnhChon);

        btnChoosePIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
            }
        });

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data)
//        {
//            super.onActivityResult(requestCode, resultCode, data);
//            // checking request code and result code
//            // if request code is PICK_IMAGE_REQUEST and
//            // resultCode is RESULT_OK
//            // then set image in the image view
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//                // Get the Uri of data
//                filePath = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                    imagePIC.setImageBitmap(bitmap);
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = editTit.getText().toString();
                Description = editDesc.getText().toString();
                if (!TextUtils.isEmpty(Title)&&!TextUtils.isEmpty(Description)){
                    Date now = java.util.Calendar.getInstance().getTime();
                    Time = now.toString();
                    NoteId = Title;
                    Note note = new Note(Title, Description, Time, NoteId, filePath);

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