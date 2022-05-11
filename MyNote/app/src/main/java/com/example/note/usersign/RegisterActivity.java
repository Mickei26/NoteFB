package com.example.note.usersign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.MainActivity;
import com.example.note.R;
import com.example.note.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference fUsersDatabase;

    private Button btnRegister, btnBack;
    private EditText editName, editEmail, editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPass = findViewById(R.id.editPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String pass = editPass.getText().toString().trim();

                registerUser(name, email, pass);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(startIntent);
                finish();
            }
        });
    }

    private void registerUser(String name, String email, String password){
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent startIntent = new Intent(RegisterActivity.this, StartActivity.class);
                    startActivity(startIntent);
                    finish();
                    Toast.makeText(RegisterActivity.this, "User created!", Toast.LENGTH_SHORT).show();
                    fAuth.signOut();
//                    hàm lưu name user vào trong Realtime Database
//                    fUsersDatabase.child(fAuth.getCurrentUser().getUid()).child("basic").child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Intent startIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                                startActivity(startIntent);
//                                finish();
//                                Toast.makeText(RegisterActivity.this, "User created!", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(RegisterActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}