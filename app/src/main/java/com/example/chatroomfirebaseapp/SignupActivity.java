package com.example.chatroomfirebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String TAG = "Signup";
    private EditText fname, lname, email, pass, retypePass;
    private Button cancel, signup;
    private String sFName,sLName,sEmail,sPass,sRetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = findViewById(R.id.editTextFirstName);
        lname = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmail2);
        pass = findViewById(R.id.editTextPass1);
        retypePass = findViewById(R.id.editTextPass2);
        cancel = findViewById(R.id.buttonCancel);
        signup = findViewById(R.id.buttonSignUp2);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                sFName = fname.getText().toString();
                sLName = lname.getText().toString();
                sEmail = email.getText().toString();
                sPass = pass.getText().toString();
                sRetype = retypePass.getText().toString();

                Log.d(TAG, "onClick12: " + sFName.length());

                if(sFName.equals("")) {
                    Toast.makeText(SignupActivity.this,"Enter First Name!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sLName.equals("")) {
                    Toast.makeText(SignupActivity.this,"Enter Last Name!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sEmail.equals("")) {
                    Toast.makeText(SignupActivity.this,"Enter Email!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sPass.equals("")) {
                    Toast.makeText(SignupActivity.this,"Enter Password!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sRetype.equals("")) {
                    Toast.makeText(SignupActivity.this,"Retype the Password!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!sPass.equals(sRetype)){
                    Toast.makeText(SignupActivity.this,"Passwords Do not Match!",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d(TAG, "Performing Sign Up");
                performSignUp(String.valueOf(1), sFName, sLName, sEmail, sPass);
            }
        });
    }

    private void performSignUp(String id, String sFName, String sLName, String sEmail, String sPass) {
        Users user = new Users(id, sEmail, sPass, sFName, sLName);
        mDatabase.child("users").child(id).setValue(user);
    }
}
