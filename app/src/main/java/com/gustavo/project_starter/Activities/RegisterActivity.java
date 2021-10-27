package com.gustavo.project_starter.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.String;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gustavo.project_starter.Activities.Model.User;
import com.gustavo.project_starter.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etName;
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViewsRegister();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoveryData();
                singIn();
            }
        });

    }

    private void setupViewsRegister() {
        etEmail = findViewById(R.id.editTextTextEmailAddressToRegister);
        etName = findViewById(R.id.editTextName);
        etPhoneNumber = findViewById(R.id.editTextPhone);
        etPassword = findViewById(R.id.editTextTextPasswordToRegister);
        etConfirmPassword = findViewById(R.id.editTextTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
    }

    private void recoveryData() {
        if(etEmail.getText().toString() == ""
                || etName.getText().toString() == ""
                || etPhoneNumber.getText().toString() == ""
                || etPassword.getText().toString() == ""
                || etConfirmPassword.getText().toString() == ""){

            Toast.makeText(this, "Please fill all blank spaces", Toast.LENGTH_LONG);
        } else {
            user = new User();
            user.setEmail(etEmail.getText().toString());
            user.setName(etName.getText().toString());
            user.setPassword(etPassword.getText().toString());
        }

    }

    private void singIn() {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fireBaseUser = mAuth.getCurrentUser();
                            user.setId(fireBaseUser.getUid());
                            user.saveData();
                            Intent intent = new Intent(getApplicationContext(), UserSpaceActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error to create login", Toast.LENGTH_LONG);
                        }
                    }
                });
    }

}