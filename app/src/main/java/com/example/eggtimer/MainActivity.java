package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView;
    SeekBar timerSeekBar;
    Button startButton;
    boolean active = false;
    CountDownTimer countDownTimer;

    public void resetCountDown() {
        timeTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        startButton.setText("GO!");
        active = false;
    }

    public void startCountdown(View view) {
        if(active) {
            resetCountDown();
        }

        else {
            active = true;
            timerSeekBar.setEnabled(false);
            startButton.setText("STOP");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetCountDown();
                }
            }.start();
        }
    }

    public void updateTimer(int secLeft) {
        int minutes = secLeft/60;
        int seconds = secLeft - (minutes*60);
        String secString, minString;

        if(seconds < 10)
            secString = "0" + Integer.toString(seconds);
        else
            secString = Integer.toString(seconds);

        String dispTime = Integer.toString(minutes) + ":" + secString;
        timeTextView.setText(dispTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        startButton = (Button) findViewById(R.id.startButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}