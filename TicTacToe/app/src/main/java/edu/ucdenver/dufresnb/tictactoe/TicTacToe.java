package edu.ucdenver.dufresnb.tictactoe;

import edu.ucdenver.dufresnb.tictactoe.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class TicTacToe extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private boolean xTurn=true;
    private int tl,tc,tr,ml,mc,mr,bl,bc,br = 0;
    private int xScore,oScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tic_tac_toe);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        Button final_button = (Button) findViewById(R.id.final_score_button);
        final_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(contentView.getContext(), endGame.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });


        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

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
        findViewById(R.id.final_score_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void setTurn()
    {
        xTurn=!xTurn;
        TextView o = (TextView) findViewById(R.id.turno);
        TextView x = (TextView) findViewById(R.id.turnx);
        if(xTurn)
        {
            x.setVisibility(View.VISIBLE);
            o.setVisibility(View.GONE);
        }
        else
        {
            o.setVisibility(View.VISIBLE);
            x.setVisibility(View.GONE);
        }
    }

    private void topWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void midHorWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void botWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void leftWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void midVertWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void rightWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void topLeftBotRightWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void topRightBotLeftWin()
    {
        if(tl==1)++xScore;
        else ++oScore;
    }
    private void tieGame()
    {

    }

    private void checkWin()
    {
        if(tl!=0 && tl==tc && tc==tr)topWin();
        else if(ml!=0 && ml==mc && mc==mr)midHorWin();
        else if(bl!=0 && bl==bc && bc==br)botWin();
        else if(tl!=0 && tl==ml && ml==bl)leftWin();
        else if(tc!=0 && tc==mc && mc==bc)midVertWin();
        else if(tr!=0 && tr==mr && mr==br)rightWin();
        else if(tl!=0 && tl==mc && mc==br)topLeftBotRightWin();
        else if(tr!=0 && tr==mc && mc==bl)topRightBotLeftWin();
        else if(tl!=0 && tc!=0 && tr!=0 && ml!=0 && mc!=0 && mr!=0 && bl!=0 && bc!=0 && br!=0)tieGame();
    }

    public void chosen(View view)
    {
        Random r = new Random();
        int c;
        if(xTurn)c=1;
        else c=2;
        int x = r.nextInt(4);
        ImageView myImage = (ImageView) view;
        switch(myImage.getId())
        {
            case R.id.topLeft:
                tl=c;
                break;
            case R.id.topCenter:
                tc=c;
                break;
            case R.id.topRight:
                tr=c;
                break;
            case R.id.midLeft:
                ml=c;
                break;
            case R.id.midCenter:
                mc=c;
                break;
            case R.id.midRight:
                mr=c;
                break;
            case R.id.bottomLeft:
                bl=c;
                break;
            case R.id.bottomCenter:
                bc=c;
                break;
            case R.id.bottomRight:
                br=c;
                break;
        }
        if (myImage.getTag() == null) {
            switch (x) {
                case 0:
                    if (xTurn) myImage.setImageResource(R.mipmap.x1);
                    else myImage.setImageResource(R.mipmap.o1);
                    break;
                case 1:
                    if (xTurn) myImage.setImageResource(R.mipmap.x2);
                    else myImage.setImageResource(R.mipmap.o2);
                    break;
                case 2:
                    if (xTurn) myImage.setImageResource(R.mipmap.x3);
                    else myImage.setImageResource(R.mipmap.o3);
                    break;
                case 3:
                    if (xTurn) myImage.setImageResource(R.mipmap.x4);
                    else myImage.setImageResource(R.mipmap.o4);
                    break;
            }
            myImage.setTag(R.mipmap.ic_launcher);
            checkWin();
            setTurn();
        }
    }
}
