/*
    Ryan Shee (rshee)
    Homework 1
*/
package com.dealfaro.luca.KitchenTImer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;

    // Saved times.
    private int save1 = 0;
    private int save2 = 0;
    private int save3 = 0;

    private boolean newTime = false; // Makes sure saved times are not continuations of previous start times.

    private int m, s; // Minutes and seconds.
    private Button b; // Button used for when button text needs to be changed.

    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        seconds += 60;
        newTime = true; // New time will be saved.
        displayTime();
    };

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        newTime = true; // New time will be saved.
        displayTime();
    };

    public void onReset(View v) {
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();

            // Record time if user inputs a new time.
            if (newTime) {
                saveTime(seconds);
                newTime = false;
            }
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        displayTime();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Sets first button.
    public void setSave1(int t) {
        save1 = t;
        m = save1 / 60;
        s = save1 % 60;
        b = (Button)findViewById(R.id.button_save1);
        b.setText(String.format("%d:%02d", m, s)); // Changes button text.
    }

    // Sets second button.
    public void setSave2(int t) {
        save2 = t;
        m = save2 / 60;
        s = save2 % 60;
        b = (Button)findViewById(R.id.button_save2);
        b.setText(String.format("%d:%02d", m, s)); // Changes button text.
    }

    // Sets third button.
    public void setSave3(int t) {
        save3 = t;
        m = save3 / 60;
        s = save3 % 60;
        b = (Button)findViewById(R.id.button_save3);
        b.setText(String.format("%d:%02d", m, s)); // Changes button text.
    }

    public void saveTime(int t) {
        // Saves first three times.
        if (save1 == 0) {
            setSave1(t);
        } else if (save2 == 0) {
            // Checks to make sure duplicate times aren't saved.
            if (t != save1) {
                setSave2(t);
            }
        } else if (save3 == 0) {
            // Checks to make sure duplicate times aren't saved.
            if (t != save2) {
                setSave3(t);
            }
        }
        // Moves saved times down one slot for the next new time.
        else {
            // Checks to make sure duplicate times aren't saved.
            if (t != save1 && t != save2 && t != save3) {
                setSave1(save2);
                setSave2(save3);
                setSave3(t);
            }
        }
    }

    // Sets timer to save1 and starts it.
    public void onClickSave1(View v) {
        if (save1 != 0) {
            if (timer != null) cancelTimer();
            seconds = save1;
            displayTime();
            onClickStart(null);
        }
    }

    // Sets timer to save2 and starts it.
    public void onClickSave2(View v) {
        if (save2 != 0) {
            if (timer != null) cancelTimer();
            seconds = save2;
            displayTime();
            onClickStart(null);
        }
    }

    // Sets timer to save3 and starts it.
    public void onClickSave3(View v) {
        if (save3 != 0) {
            if (timer != null) cancelTimer();
            seconds = save3;
            displayTime();
            onClickStart(null);
        }
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        m = seconds / 60;
        s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }
}
