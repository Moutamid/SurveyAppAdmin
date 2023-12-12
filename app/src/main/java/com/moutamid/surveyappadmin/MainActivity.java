package com.moutamid.surveyappadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.surveyappadmin.helper.Config;

public class MainActivity extends AppCompatActivity {
    Button vorabfragebogen, abschlussfragebogen, bewertung_der_fahrt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config.checkApp(MainActivity.this);
        bewertung_der_fahrt = findViewById(R.id.bewertung_der_fahrt);
        abschlussfragebogen = findViewById(R.id.abschlussfragebogen);
        vorabfragebogen = findViewById(R.id.vorabfragebogen);
        Config.checkApp(MainActivity.this);
        bewertung_der_fahrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, UsersActivity.class);
                intent.putExtra("type", "BewertungDerFahrt");
                startActivity(intent);
            }
        });
        vorabfragebogen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, UsersActivity.class);
                intent.putExtra("type", "Vorabfragebogen");
                startActivity(intent);
            }
        });
        abschlussfragebogen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                intent.putExtra("type", "Abschlussfragebogen");
                startActivity(intent);
            }
        });
    }
}