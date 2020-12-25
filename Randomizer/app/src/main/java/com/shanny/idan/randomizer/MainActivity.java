package com.shanny.idan.randomizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rollButton = (Button)findViewById(R.id.rollButton);
        TextView resultsTextView = (TextView)findViewById(R.id.resultsTextView);
        SeekBar seekBar = (SeekBar)findViewById(R.id.howManySeekBar);

        rollButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Random random = new Random();
                    int bound = seekBar.getProgress()==0? 1 : seekBar.getProgress();
                    resultsTextView.setText(String.valueOf(random.nextInt(bound)+1));
                }
            });

        }
}