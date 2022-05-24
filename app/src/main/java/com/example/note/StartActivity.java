package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.note.users.LoginActivity;
import com.example.note.users.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;

    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnHaveAcc);
        btnRegister = findViewById(R.id.btnCreatAcc);

        updateUI();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void Login(){
        Intent startIntent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void Register(){
        Intent startIntent = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void updateUI(){
        if(fAuth.getCurrentUser() != null){
            Log.i("StartActivity", "fAuth != null");
            Intent startIntent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }else {
            Log.i("StartActivity", "fAuth == null");
        }
    }
}