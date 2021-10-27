package com.gustavo.project_starter.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gustavo.project_starter.Activities.Model.User;
import com.gustavo.project_starter.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnCreateAccount;
    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyBlackSpaces();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void setupView() {
        user = new User();

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void verifyBlackSpaces() {
        if (etEmail.getText().toString() == null || etPassword.getText().toString() == null) {
            Toast.makeText(getApplicationContext(), "Ops, something is missing, please complete all spaces", Toast.LENGTH_SHORT).show();
        } else {
            receiveData();
        }
    }

    private void receiveData() {
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());

        logIn();
    }

    private void logIn() {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            //updateUI(firebaseUser);
                            Intent intent = new Intent(getApplicationContext(), UserSpaceActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


}