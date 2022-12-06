package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Symptoms extends AppCompatActivity {

    Button preMenuBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        preMenuBtn2 = (Button) findViewById(R.id.preMenuBtn2);
        preMenuBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preMenu2();
            }
        });
    }

    public void preMenu2(){
        Intent intent = new Intent(Symptoms.this, Main2Activity.class);
        startActivity(intent);
    }
}
