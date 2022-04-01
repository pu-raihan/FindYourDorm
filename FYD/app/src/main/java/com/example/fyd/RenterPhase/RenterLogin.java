package com.example.fyd.RenterPhase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyd.CLogout;
import com.example.fyd.CMainActivity;
import com.example.fyd.CustLogin;
import com.example.fyd.Launcher;
import com.example.fyd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RenterLogin extends AppCompatActivity {
    EditText userName,password;
    Button login;
    ImageButton home;
    TextView register;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth Auth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_renter);
        userName = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        Auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userName.getText().toString().trim();
                String pwd = password.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(RenterLogin.this,"Please enter email id",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(RenterLogin.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database=FirebaseDatabase.getInstance();
                    Auth.signInWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())//If account login successful print message and send user to main Activity
                                    {
                                        Toast.makeText(RenterLogin.this, "Please wait", Toast.LENGTH_SHORT).show();

                                        currentUser = Auth.getCurrentUser();
                                        userid=currentUser.getUid();
                                        reference=database.getReference("user").child(userid);
                                        reference.child("type").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                try {
                                                    String t=snapshot.getValue().toString();
                                                    if(t.equals("renter")){
                                                        sendToMainActivity();
                                                        Toast.makeText(RenterLogin.this, "Welcome to Find Your Dorm", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        FirebaseAuth.getInstance().signOut();
                                                        Toast.makeText(RenterLogin.this, "You are not a Renter", Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (NullPointerException ignored){}
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                    else//Print the error message incase of failure
                                    {
                                        String msg = task.getException().toString();
                                        Toast.makeText(RenterLogin.this,"Error: "+msg,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegister();
            }
        });
        home=(ImageButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser!=null) {
                    Intent logoutIntent = new Intent(RenterLogin.this, RLogout.class);
                    startActivity(logoutIntent);
                }
                else {
                    Intent homeIntent = new Intent(RenterLogin.this, Launcher.class);
                    startActivity(homeIntent);
                }
            }
        });
    }


    private void sendUserToRegister() {
        //When user wants to create a new account send user to Register Activity
        Intent registerIntent = new Intent(RenterLogin.this, RenterSignup.class);
        startActivity(registerIntent);
    }
    protected void onStart() {
        //Check if user has already signed in if yes send to mainActivity
        //This to avoid signing in everytime you open the app.
        super.onStart();
        if(currentUser!=null)
        {
            sendToMainActivity();
        }
    }

    private void sendToMainActivity() {
        //This is to send user to MainActivity
        Intent  MainIntent = new Intent(RenterLogin.this, RMainActivity.class);
        startActivity(MainIntent);
    }
}
