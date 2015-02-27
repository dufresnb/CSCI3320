package edu.ucdenver.dufresnb.tictactoe;

import edu.ucdenver.dufresnb.tictactoe.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class TicTacToe extends Activity {

    private boolean xTurn=true;
    private int tl,tc,tr,ml,mc,mr,bl,bc,br = 0;
    public int xScore,oScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tic_tac_toe);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            xScore = extras.getInt("xScore");
            oScore = extras.getInt("oScore");
        }

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        Button final_button = (Button) findViewById(R.id.final_score_button);
        final_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(contentView.getContext(), endGame.class);
                myIntent.putExtra("xScore",xScore);
                myIntent.putExtra("oScore",oScore);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });

        Button replay_button = (Button) findViewById(R.id.replay);
        replay_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });

    }

    private void reset()
    {
        View contentView = findViewById(R.id.fullscreen_content);
        Intent myIntent = new Intent(contentView.getContext(), TicTacToe.class);
        myIntent.putExtra("xScore",xScore);
        myIntent.putExtra("oScore",oScore);
        startActivityForResult(myIntent, 0);
        finish();
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
        if(tl==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.top);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void midHorWin()
    {
        if(ml==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.midHor);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void botWin()
    {
        if(bl==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.bot);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void leftWin()
    {
        if(tl==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.left);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void midVertWin()
    {
        if(tc==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.midVert);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void rightWin()
    {
        if(tr==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.right);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void topLeftBotRightWin()
    {
        if(tl==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.topLeftToBot);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void topRightBotLeftWin()
    {
        if(tr==1)
        {
            xWin();
        }
        else
        {
            oWin();
        }
        ImageView myLine = (ImageView) findViewById(R.id.topRightToBot);
        myLine.setVisibility(View.VISIBLE);
        replayButton();
    }

    private void xWin()
    {
        ++xScore;
        ImageView myLine = (ImageView) findViewById(R.id.textbg);
        myLine.setVisibility(View.VISIBLE);
        TextView theWinner = (TextView) findViewById(R.id.winner);
        theWinner.setText("X Player Wins!");
    }

    private void oWin()
    {
        ++oScore;
        ImageView myLine = (ImageView) findViewById(R.id.textbg);
        myLine.setVisibility(View.VISIBLE);
        TextView theWinner = (TextView) findViewById(R.id.winner);
        theWinner.setText("O Player Wins!");
    }

    private void replayButton()
    {
        View myButton = (Button) findViewById(R.id.replay);
        myButton.setVisibility(View.VISIBLE);
    }

    private void tieGame()
    {
        replayButton();
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
                    if (xTurn) myImage.setImageResource(R.drawable.x1);
                    else myImage.setImageResource(R.drawable.o1);
                    break;
                case 1:
                    if (xTurn) myImage.setImageResource(R.drawable.x2);
                    else myImage.setImageResource(R.drawable.o2);
                    break;
                case 2:
                    if (xTurn) myImage.setImageResource(R.drawable.x3);
                    else myImage.setImageResource(R.drawable.o3);
                    break;
                case 3:
                    if (xTurn) myImage.setImageResource(R.drawable.x4);
                    else myImage.setImageResource(R.drawable.o4);
                    break;
            }
            myImage.setTag(R.mipmap.ic_launcher);
            checkWin();
            setTurn();
        }
    }
}
