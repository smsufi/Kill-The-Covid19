package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class About extends AppCompatActivity {

    Button aboutToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toast.makeText(About.this,"About Game Characters", Toast.LENGTH_SHORT).show();

        aboutToMenu = (Button) findViewById(R.id.aboutToMenu);
        aboutToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutToMainMenu();
            }
        });
    }

    public void aboutToMainMenu(){
        Intent intent =new Intent (About.this, Main2Activity.class);
        startActivity(intent);
    }
}
