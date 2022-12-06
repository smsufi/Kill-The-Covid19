package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class Entry extends AppCompatActivity {

    Button nextStoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Toast.makeText(Entry.this,"Welcome", Toast.LENGTH_SHORT).show();
        Toast.makeText(Entry.this,"Click Next", Toast.LENGTH_SHORT).show();

        nextStoryBtn = (Button) findViewById(R.id.nextStoryBtn);
        nextStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStory();
            }
        });


    }

    public void nextStory(){
        Intent intent = new Intent(Entry.this, Story.class);
        startActivity(intent);
    }
}
