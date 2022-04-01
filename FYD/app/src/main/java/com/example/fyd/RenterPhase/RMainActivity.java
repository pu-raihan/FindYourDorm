package com.example.fyd.RenterPhase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyd.Model;
import com.example.fyd.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RMainActivity extends AppCompatActivity {
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication
    Button logout;
    TextView profile;
    String userid;
    RecyclerView recview;
    FirebaseDatabase database;
    DatabaseReference reference;
    String name;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter);
        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutConfirm();
            }
        });
        database = FirebaseDatabase.getInstance();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        /*FirebaseRecyclerOptions<Model> options=
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("renter").child("myprofiles"),Model.class)
                        .build();
        adapter=new RAdapter(options);
        recview.setAdapter(adapter);*/
        profile=(TextView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RMainActivity.this, MyProfile.class);
                startActivity(intent1);
            }
        });
        Intent intent1 = new Intent(RMainActivity.this, MyProfile.class);
        startActivity(intent1);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //adapter.startListening();
        //Check if user has already signed in if not send user to Login Activity
        if (currentUser == null) {
            sendToLoginActivity();
        }
    }

    private void sendToLoginActivity() {
        //To send user to Login Activity
        Intent loginIntent = new Intent(RMainActivity.this, RenterLogin.class);
        startActivity(loginIntent);

    }
    private void logoutConfirm() {
        Intent logoutIntent = new Intent(RMainActivity.this, RLogout.class);
        startActivity(logoutIntent);

    }
    @Override
    protected void onStop(){
        super.onStop();
        //adapter.stopListening();
    }
}