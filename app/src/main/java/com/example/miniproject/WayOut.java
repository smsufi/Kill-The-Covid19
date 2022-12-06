package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WayOut extends AppCompatActivity {

    Button preMenuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_out);

        preMenuBtn = (Button) findViewById(R.id.preMenuBtn);
        preMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preMenu();
            }
        });
    }

    public void preMenu(){
        Intent intent = new Intent(WayOut.this, Main2Activity.class);
        startActivity(intent);
    }
}
