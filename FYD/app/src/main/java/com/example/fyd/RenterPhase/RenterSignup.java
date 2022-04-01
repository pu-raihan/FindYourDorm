package com.example.fyd.RenterPhase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RenterSignup extends AppCompatActivity {
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    EditText e6;
    EditText e7;
    EditText e8;
    EditText e9;
    EditText e10;
    EditText e11;
    EditText e12;
    EditText e13;
    EditText e14;
    TextView sendlogin;
    Button rregister;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    String userid;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_renter);
        mAuth = FirebaseAuth.getInstance();
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        e4 = (EditText) findViewById(R.id.editText4);
        e5 = (EditText) findViewById(R.id.editText5);
        e6 = (EditText) findViewById(R.id.editText6);
        e7 = (EditText) findViewById(R.id.editText7);
        e8 = (EditText) findViewById(R.id.editText8);
        e9 = (EditText) findViewById(R.id.editText9);
        e10 = (EditText) findViewById(R.id.editText10);
        e11 = (EditText) findViewById(R.id.editText11);
        e12 = (EditText) findViewById(R.id.editText12);
        e13 = (EditText) findViewById(R.id.editText13);
        e14 = (EditText) findViewById(R.id.fullname);
        rregister = (Button) findViewById(R.id.rregister);
        sendlogin = (TextView) findViewById(R.id.sendlogin);
        //When user clicks on register create a new account for user
        rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
        sendlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRenterToLoginActivity();
            }
        });
    }
    private void createNewAccount() {
        String email = e1.getText().toString().trim();
        String pwd = e2.getText().toString().trim();
        final String name = e3.getText().toString().trim();
        final String address = e4.getText().toString().trim();
        final String phone = e5.getText().toString().trim();
        final String place = e6.getText().toString().trim();
        final String size = e7.getText().toString().trim();
        final String bedrooms = e8.getText().toString().trim();
        final String bathrooms = e9.getText().toString().trim();
        final String rent = e10.getText().toString().trim();
        final String feature1 = e11.getText().toString().trim();
        final String feature2 = e12.getText().toString().trim();
        final String feature3 = e13.getText().toString().trim();
        final String fullname = e14.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(RenterSignup.this,"Please enter email id",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(RenterSignup.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //When both email and password are available create a new account

            mAuth.createUserWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())//If account creation successful print message and send user to Login Activity
                            {
                                database = FirebaseDatabase.getInstance();
                                user= FirebaseAuth.getInstance().getCurrentUser();
                                userid=user.getUid();
                                reference=database.getReference("user").child(userid).child("name");
                                reference.setValue(name);
                                reference=database.getReference("user").child(userid).child("type");
                                reference.setValue("renter");
                                Toast.makeText(RenterSignup.this, "Renter Account created successfully", Toast.LENGTH_SHORT).show();
                                reference=database.getReference("renter");
                                ref=reference.child(name).child("address");
                                ref.setValue(address);
                                ref=reference.child(name).child("bathrooms");
                                ref.setValue(bathrooms);
                                ref=reference.child(name).child("bedrooms");
                                ref.setValue(bedrooms);
                                ref=reference.child(name).child("feature1");
                                ref.setValue(feature1);
                                ref=reference.child(name).child("feature2");
                                ref.setValue(feature2);
                                ref=reference.child(name).child("feature3");
                                ref.setValue(feature3);
                                ref=reference.child(name).child("owner");
                                ref.setValue(fullname);
                                ref=reference.child(name).child("place");
                                ref.setValue(place);
                                ref=reference.child(name).child("size");
                                ref.setValue(size);
                                ref=reference.child(name).child("phone");
                                ref.setValue(phone);
                                ref=reference.child(name).child("rent");
                                ref.setValue(rent);
                                ref=reference.child(name).child("status");
                                ref.setValue("Available");
                                ref=reference.child(name).child("profile");
                                ref.setValue(name);
                                sendRenterToLoginActivity();
                            } else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(RenterSignup.this, "Error: " + msg, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }
    private void sendRenterToLoginActivity() {
        Intent loginIntent = new Intent(RenterSignup.this, RenterLogin.class);
        startActivity(loginIntent);
    }
}
