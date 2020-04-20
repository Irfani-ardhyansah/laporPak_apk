package com.example.project_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private ImageView btnLapor, btnKeluh, btnData, btnLogout, btnDataKeluh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        btnLapor = (ImageView) findViewById(R.id.btnLapor);
        btnKeluh = (ImageView) findViewById(R.id.btnKeluh);
        btnData = (ImageView)findViewById(R.id.btnData);
        btnLogout = (ImageView)findViewById(R.id.btnLogout);
        btnDataKeluh = (ImageView)findViewById(R.id.btnDataKeluh);

        btnLapor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iLapor = new Intent(Home.this,Lapor.class);
                startActivity(iLapor);
            }
        });

        btnKeluh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iKeluh = new Intent(Home.this,Keluh.class);
                startActivity(iKeluh);
            }
        });

        btnData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iData = new Intent(Home.this,Data.class);
                startActivity(iData);
            }
        });

        btnDataKeluh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iDataKeluh = new Intent(Home.this,DataKeluh.class);
                startActivity(iDataKeluh);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iLogout = new Intent(Home.this,MainActivity.class);
                startActivity(iLogout);
            }
        });
    }

}
