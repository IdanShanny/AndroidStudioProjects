package com.shanny.idan.dicee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = findViewById(R.id.rollButton);
        final ImageView leftDice = findViewById(R.id.image_leftDice);
        final ImageView rightDice = findViewById(R.id.image_rightDice);

        final int[] diceArray = {R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6};

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dicee", "Ze Button has been pressed!");
                Random rand = new Random();
                int number = rand.nextInt(6);
                Log.d("Dicee", "The random number is: " + number);

                leftDice.setImageResource(diceArray[number]);

                number = rand.nextInt(6);
                Log.d("Dicee", "The random number is: " + number);

                rightDice.setImageResource(diceArray[number]);

            }
        });



    }
}