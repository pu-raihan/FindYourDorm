package com.example.fyd.RenterPhase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyd.PublicProfile;
import com.example.fyd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    String userid;
    TextView place;
    TextView size;
    TextView bedrooms;
    TextView bathrooms;
    TextView rent;
    TextView f1;
    TextView f2;
    TextView f3;
    TextView owner;
    TextView address;
    TextView phone;
    TextView status;
    Button edit;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        database = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        edit=(Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid=user.getUid();
                reference=database.getReference("user").child(userid);
                reference.child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {
                            String n = snapshot.getValue().toString();
                            Intent intent1 = new Intent(MyProfile.this, EditRenter.class);
                            intent1.putExtra("pro", n);
                            startActivity(intent1);
                        } catch (NullPointerException ignored) {
                            Toast.makeText(MyProfile.this, "user is not a renter", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        userid=user.getUid();
        reference=database.getReference("user").child(userid);
        reference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    database=FirebaseDatabase.getInstance();
                    reference= FirebaseDatabase.getInstance().getReference("renter");
                    place=(TextView) findViewById(R.id.place);
                    size=(TextView) findViewById(R.id.size);
                    bedrooms=(TextView) findViewById(R.id.bedrooms);
                    bathrooms=(TextView) findViewById(R.id.bathrooms);
                    rent=(TextView) findViewById(R.id.rent);
                    f1=(TextView) findViewById(R.id.f1);
                    f2=(TextView) findViewById(R.id.f2);
                    f3=(TextView) findViewById(R.id.f3);
                    owner=(TextView) findViewById(R.id.owner);
                    address=(TextView) findViewById(R.id.address);
                    phone=(TextView) findViewById(R.id.phone);
                    status=(TextView) findViewById(R.id.status);
                    img=(ImageView) findViewById(R.id.imageView1);
                    //String pro=getIntent().getStringExtra("pro");
                    String pro=snapshot.getValue().toString();
                    reference.child(pro).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String url=null;
                            try {
                                url= snapshot.child("img").getValue().toString();
                                Picasso.get().load(url).into(img);
                            }catch (NullPointerException ignored){}
                            String a= null;
                            try {
                                a= snapshot.child("place").getValue().toString();
                                place.setText(a);
                            }catch (NullPointerException ignored){}

                            String b= null;
                            try {
                                b= snapshot.child("size").getValue().toString();
                                size.setText(b);
                            }catch (NullPointerException ignored){}

                            String c= null;
                            try {
                                c= snapshot.child("bedrooms").getValue().toString();
                                bedrooms.setText(c);
                            }catch (NullPointerException ignored){}

                            String d= null;
                            try {
                                d= snapshot.child("bathrooms").getValue().toString();
                                bathrooms.setText(d);
                            }catch (NullPointerException ignored){}

                            String e= null;
                            try {
                                e= snapshot.child("rent").getValue().toString();
                                rent.setText(e);
                            }catch (NullPointerException ignored){}

                            String f= null;
                            try {
                                f=snapshot.child("feature1").getValue().toString();
                                f2.setText(f);
                            }catch (NullPointerException ignored){}

                            String g= null;
                            try {
                                g=snapshot.child("feature2").getValue().toString();
                                f2.setText(g);
                            }catch (NullPointerException ignored){}

                            String h= null;
                            try {
                                h=snapshot.child("feature3").getValue().toString();
                                f1.setText(h);
                            }catch (NullPointerException ignored){}

                            String i= null;
                            try {
                                i=snapshot.child("owner").getValue().toString();
                                owner.setText(i);
                            }catch (NullPointerException ignored){}

                            String j= null;
                            try {
                                j=snapshot.child("address").getValue().toString();
                                address.setText(j);
                            }catch (NullPointerException ignored){}

                            String k=null;
                            try {
                                k = snapshot.child("phone").getValue().toString();
                                phone.setText(k);
                            }catch (NullPointerException ignored){}

                            String l=null;
                            try {
                                l = snapshot.child("status").getValue().toString();
                                status.setText(l);
                            }catch (NullPointerException ignored){}
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MyProfile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                        }
                    });


                }catch (NullPointerException ignored){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyProfile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        user= FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent loginIntent = new Intent(MyProfile.this, RenterLogin.class);
            startActivity(loginIntent);
        }
    }
}














