package com.example.fyd.RenterPhase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class EditRenter extends AppCompatActivity {
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
    Button save;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    String userid;
    RadioGroup radioStatus;
    RadioButton radioButton;
    Task<Void> ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        radioStatus = (RadioGroup) findViewById(R.id.status);

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
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }
    private void updateData() {
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
        database = FirebaseDatabase.getInstance();
        String name=getIntent().getStringExtra("pro");
        int selectedId = radioStatus.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        String status= String.valueOf(radioButton.getText());
        reference = database.getReference("renter");
        ref = reference.child(name).child("address").setValue(address);
        ref = reference.child(name).child("bathrooms").setValue(bathrooms);
        ref = reference.child(name).child("bedrooms").setValue(bedrooms);
        ref = reference.child(name).child("feature1").setValue(feature1);
        ref = reference.child(name).child("feature2").setValue(feature2);
        ref = reference.child(name).child("feature3").setValue(feature3);
        ref = reference.child(name).child("place").setValue(place);
        ref = reference.child(name).child("size").setValue(size);
        ref = reference.child(name).child("phone").setValue(phone);
        ref = reference.child(name).child("rent").setValue(rent);
        ref = reference.child(name).child("status").setValue(status);
        ref = reference.child(name).child("owner").setValue(fullname);
        Toast.makeText(EditRenter.this, "Updated!", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(EditRenter.this,MyProfile.class);
        startActivity(i);
    }
}
