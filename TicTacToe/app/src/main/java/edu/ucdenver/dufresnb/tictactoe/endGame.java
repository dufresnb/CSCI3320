package edu.ucdenver.dufresnb.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

/**
 * Created by Brad on 2/27/2015.
 */
public class endGame extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int xScore=0;
        int oScore=0;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            xScore = extras.getInt("xScore");
            oScore = extras.getInt("oScore");
        }

        setContentView(R.layout.finalscores);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        TextView myScores = (TextView) findViewById(R.id.scores);
        myScores.setText("X Player: " + xScore + "\n\nO Player: " + oScore);

        Button final_button = (Button) findViewById(R.id.exitGame);
        final_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });



                }

        // Set up the user interaction to manually show or hide the system UI.
        /*
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       // findViewById(R.id.final_score_button).setOnTouchListener(mDelayHideTouchListener);
}

