package com.example.fyd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyd.RenterPhase.RMainActivity;

public class Launcher extends AppCompatActivity {
    Button cust;
    Button rent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        cust=(Button)findViewById(R.id.toCustomer);
        cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent icust=new Intent(Launcher.this,CMainActivity.class);
                startActivity(icust);
            }
        });
        rent=(Button)findViewById(R.id.toRenter);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irent=new Intent(Launcher.this, RMainActivity.class);
                startActivity(irent);
            }
        });
    }
}
