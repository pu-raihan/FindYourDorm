package com.example.fyd;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyd.RenterPhase.RenterSignup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CustSignup extends AppCompatActivity {
    EditText e1;
    EditText e2;
    TextView sendlogin;
    Button cregister;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    String userid;
    private FirebaseAuth mAuth;//Used for firebase authentication


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_cust);
        mAuth = FirebaseAuth.getInstance();
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        cregister = (Button) findViewById(R.id.cregister);
        sendlogin = (TextView) findViewById(R.id.sendlogin);
        //When user clicks on register create a new account for user
        cregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
        sendlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLoginActivity();
            }
        });
    }
    private void createNewAccount() {
        String email = e1.getText().toString().trim();
        String pwd = e2.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(CustSignup.this,"Please enter email id",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(CustSignup.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //When both email and password are available create a new account

            mAuth.createUserWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())//If account creation successful print message and send user to Login Activity
                            {
                                database = FirebaseDatabase.getInstance();
                                user= FirebaseAuth.getInstance().getCurrentUser();
                                userid=user.getUid();
                                reference=database.getReference("user").child(userid).child("type");
                                reference.setValue("customer");
                                sendUserToLoginActivity();
                                Toast.makeText(CustSignup.this,"Customer Account created successfully",Toast.LENGTH_SHORT).show();
                            }
                            else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(CustSignup.this,"Error: "+msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void sendUserToLoginActivity() {
        //This is to send user to Login Activity.
        Intent loginIntent = new Intent(CustSignup.this, CustLogin.class);
        startActivity(loginIntent);
        Toast.makeText(CustSignup.this, "Please Login", Toast.LENGTH_SHORT).show();
    }
}
