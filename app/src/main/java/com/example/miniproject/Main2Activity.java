package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {

    // Frame
    private FrameLayout gameFrame;
    private int frameHeight, frameWidth, initialFrameWidth;
    private LinearLayout startLayout;

    // Image
    private ImageView human, covid19, soap, sanitizer;
    private Drawable imageHumanRight, imageHumanLeft;

    // Size
    private int humanSize;

    // Position
    private float humanX, humanY;
    private float covid19X, covid19Y;
    private float soapX, soapY;
    private float sanitizerX, sanitizerY;

    // Score
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount;
    private SharedPreferences settings;

    // Class
    private Timer timer;
    private Handler handler = new Handler();

    // Status
    private boolean start_flg = false;
    private boolean action_flg = false;
    private boolean sanitizer_flg = false;

    //Buttons
    Button aboutBtn, symptomsBtn, wayOutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toast.makeText(Main2Activity.this,"Main Menu", Toast.LENGTH_SHORT).show();

        aboutBtn = (Button) findViewById(R.id.aboutBtn);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about();
            }
        });

        symptomsBtn = (Button) findViewById(R.id.symptomsBtn);
        symptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                symptoms();
            }
        });

        wayOutBtn = (Button) findViewById(R.id.wayOutBtn);
        wayOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wayOut();
            }
        });

        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        human = findViewById(R.id.human);
        covid19 = findViewById(R.id.covid19);
        soap = findViewById(R.id.soap);
        sanitizer = findViewById(R.id.sanitizer);
        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        imageHumanLeft = getResources().getDrawable(R.drawable.human_left);
        imageHumanRight = getResources().getDrawable(R.drawable.human_right);

        // High Score
        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText("High Score : " + highScore);
    }


    public void about(){
        Intent intent = new Intent(Main2Activity.this, About.class);
        startActivity(intent);
    }

    public void symptoms(){
        Intent intent = new Intent(Main2Activity.this, Symptoms.class);
        startActivity(intent);
    }

    public void wayOut(){
        Intent intent = new Intent(Main2Activity.this, WayOut.class);
        startActivity(intent);
    }

    public void changePos() {

        // Add timeCount
        timeCount += 20;

        // Orange
        soapY += 12;

        float soapCenterX = soapX + soap.getWidth() / 2;
        float soapCenterY = soapY + soap.getHeight() / 2;

        if (hitCheck(soapCenterX, soapCenterY)) {
            soapY = frameHeight + 100;
            score += 10;
        }

        if (soapY > frameHeight) {
            soapY = -100;
            soapX = (float) Math.floor(Math.random() * (frameWidth - soap.getWidth()));
        }
        soap.setX(soapX);
        soap.setY(soapY);

        // Pink
        if (!sanitizer_flg && timeCount % 10000 == 0) {
            sanitizer_flg = true;
            sanitizerY = -20;
            sanitizerX = (float) Math.floor(Math.random() * (frameWidth - sanitizer.getWidth()));
        }

        if (sanitizer_flg) {
            sanitizerY += 20;

            float pinkCenterX = sanitizerX + sanitizer.getWidth() / 2;
            float pinkCenterY = sanitizerY + sanitizer.getWidth() / 2;

            if (hitCheck(pinkCenterX, pinkCenterY)) {
                sanitizerY = frameHeight + 30;
                score += 30;
                // Change FrameWidth
                if (initialFrameWidth > frameWidth * 110 / 100) {
                    frameWidth = frameWidth * 110 / 100;
                    changeFrameWidth(frameWidth);
                }
            }

            if (sanitizerY > frameHeight) sanitizer_flg = false;
            sanitizer.setX(sanitizerX);
            sanitizer.setY(sanitizerY);
        }

        // Black
        covid19Y += 18;

        float blackCenterX = covid19X + covid19.getWidth() / 2;
        float blackCenterY = covid19Y + covid19.getHeight() / 2;

        if (hitCheck(blackCenterX, blackCenterY)) {
            covid19Y = frameHeight + 100;

            // Change FrameWidth
            frameWidth = frameWidth * 80 / 100;
            changeFrameWidth(frameWidth);
            if (frameWidth <= humanSize) {
                Toast.makeText(Main2Activity.this,"Game Over!", Toast.LENGTH_SHORT).show();
                gameOver();
            }

        }

        if (covid19Y > frameHeight) {
            covid19Y = -100;
            covid19X = (float) Math.floor(Math.random() * (frameWidth - covid19.getWidth()));
        }

        covid19.setX(covid19X);
        covid19.setY(covid19Y);

        // Move Box
        if (action_flg) {
            // Touching
            humanX += 15;
            human.setImageDrawable(imageHumanRight);
        } else {
            // Releasing
            humanX -= 15;
            human.setImageDrawable(imageHumanLeft);
        }

        // Check box position.
        if (humanX < 0) {
            humanX = 0;
            human.setImageDrawable(imageHumanRight);
        }
        if (frameWidth - humanSize < humanX) {
            humanX = frameWidth - humanSize;
            human.setImageDrawable(imageHumanLeft);
        }

        human.setX(humanX);

        scoreLabel.setText("Score : " + score);

    }

    public boolean hitCheck(float x, float y) {
        if (humanX <= x && x <= humanX + humanSize &&
                humanY <= y && y <= frameHeight) {
            return true;
        }
        return false;
    }

    public void changeFrameWidth(int frameWidth) {
        ViewGroup.LayoutParams params = gameFrame.getLayoutParams();
        params.width = frameWidth;
        gameFrame.setLayoutParams(params);
    }

    public void gameOver() {
        Toast.makeText(Main2Activity.this, "Saving Progress", Toast.LENGTH_SHORT).show();
        // Stop timer.
        timer.cancel();
        timer = null;
        start_flg = false;



        changeFrameWidth(initialFrameWidth);

        startLayout.setVisibility(View.VISIBLE);
        human.setVisibility(View.INVISIBLE);
        covid19.setVisibility(View.INVISIBLE);
        soap.setVisibility(View.INVISIBLE);
        sanitizer.setVisibility(View.INVISIBLE);

        // Update High Score
        if (score > highScore) {
            highScore = score;
            highScoreLabel.setText("High Score : " + highScore);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", highScore);
            editor.commit();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (start_flg) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;

            }
        }
        return true;
    }

    public void startGame(View view) {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);
        Toast.makeText(Main2Activity.this,"Good Luck!", Toast.LENGTH_SHORT).show();

        if (frameHeight == 0) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            initialFrameWidth = frameWidth;

            humanSize = human.getHeight();
            humanX = human.getX();
            humanY = human.getY();
        }

        frameWidth = initialFrameWidth;

        human.setX(0.0f);
        covid19.setY(3000.0f);
        soap.setY(3000.0f);
        sanitizer.setY(3000.0f);

        covid19Y = covid19.getY();
        soapY = soap.getY();
        sanitizerY = sanitizer.getY();

        human.setVisibility(View.VISIBLE);
        covid19.setVisibility(View.VISIBLE);
        soap.setVisibility(View.VISIBLE);
        sanitizer.setVisibility(View.VISIBLE);

        timeCount = 0;
        score = 0;
        scoreLabel.setText("Score : 0");


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (start_flg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        }, 0, 20);
    }

    public void quitGame(View view) {

            //finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }

}
