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
    private boolean gameOver=false;
    private boolean ai=false;
    private int xScore,oScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tic_tac_toe);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            xScore = extras.getInt("xScore");
            oScore = extras.getInt("oScore");
            xTurn = extras.getBoolean("xTurn");
            ai = extras.getBoolean("ai");
        }

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        getTurn();
        getAI();
        if(ai && !xTurn)doAI();

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

        Button ai_button = (Button) findViewById(R.id.playai);
        ai_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toggleAI();
            }
        });
    }

    private void toggleAI()
    {
        ai=!ai;
        getAI();
        if(ai && !xTurn)doAI();
    }

    private void getAI()
    {
        if(ai) {
            TextView playai = (TextView) findViewById(R.id.playai);
            playai.setText("Play Human");
        }
        else
        {
            TextView playai = (TextView) findViewById(R.id.playai);
            playai.setText("Play A.I.");
        }
    }

    private void reset()
    {
        View contentView = findViewById(R.id.fullscreen_content);
        Intent myIntent = new Intent(contentView.getContext(), TicTacToe.class);
        myIntent.putExtra("xScore",xScore);
        myIntent.putExtra("oScore",oScore);
        myIntent.putExtra("xTurn",xTurn);
        myIntent.putExtra("ai",ai);
        startActivityForResult(myIntent, 0);
        finish();
    }

    private void setTurn()
    {
        xTurn=!xTurn;
        getTurn();
    }

    private void getTurn()
    {
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
        gameOver=true;
        ++xScore;
        ImageView myLine = (ImageView) findViewById(R.id.textbg);
        myLine.setVisibility(View.VISIBLE);
        TextView theWinner = (TextView) findViewById(R.id.winner);
        theWinner.setText("X Player Wins!");
    }

    private void oWin()
    {
        gameOver=true;
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

    private void doAI()
    {
        View temp;
        if(tl==0 && tc==0 && tr==0 && ml==0 && mc==0 && mr==0 && bl==0 && bc==0 && br==0)
        {
            temp = findViewById(R.id.midCenter);
            chosen(temp);
        }
        else if(tl==tc && tc!=0 && tr==0)
        {
            temp = findViewById(R.id.topRight);
            chosen(temp);
        }
        else if(tr==tc && tc!=0 && tl==0)
        {
            temp = findViewById(R.id.topLeft);
            chosen(temp);
        }
        else if(tr==tl && tl!=0 && tc==0)
        {
            temp = findViewById(R.id.topCenter);
            chosen(temp);
        }
        else if(ml==mc && mc!=0 && mr==0)
        {
            temp = findViewById(R.id.midRight);
            chosen(temp);
        }
        else if(mr==mc && mc!=0 && ml==0)
        {
            temp = findViewById(R.id.midLeft);
            chosen(temp);
        }
        else if(mr==ml && ml!=0 && mc==0)
        {
            temp = findViewById(R.id.midCenter);
            chosen(temp);
        }
        else if(bl==bc && bc!=0 && br==0)
        {
            temp = findViewById(R.id.bottomRight);
            chosen(temp);
        }
        else if(br==bc && bc!=0 && bl==0)
        {
            temp = findViewById(R.id.bottomLeft);
            chosen(temp);
        }
        else if(br==bl && bl!=0 && bc==0)
        {
            temp = findViewById(R.id.bottomCenter);
            chosen(temp);
        }
        else if(tl==ml && ml!=0 && bl==0)
        {
            temp = findViewById(R.id.bottomLeft);
            chosen(temp);
        }
        else if(ml==bl && bl!=0 && tl==0)
        {
            temp = findViewById(R.id.topLeft);
            chosen(temp);
        }
        else if(bl==tl && tl!=0 && ml==0)
        {
            temp = findViewById(R.id.midLeft);
            chosen(temp);
        }
        else if(tc==mc && mc!=0 && bc==0)
        {
            temp = findViewById(R.id.bottomCenter);
            chosen(temp);
        }
        else if(bc==mc && mc!=0 && tc==0)
        {
            temp = findViewById(R.id.topCenter);
            chosen(temp);
        }
        else if(bc==tc && tc!=0 && mc==0)
        {
            temp = findViewById(R.id.midCenter);
            chosen(temp);
        }
        else if(tr==mr && mr!=0 && br==0)
        {
            temp = findViewById(R.id.bottomRight);
            chosen(temp);
        }
        else if(br==mr && mr!=0 && tr==0)
        {
            temp = findViewById(R.id.topRight);
            chosen(temp);
        }
        else if(br==tr && tr!=0 && mr==0)
        {
            temp = findViewById(R.id.midRight);
            chosen(temp);
        }
        else if(tl==mc && mc!=0 && br==0)
        {
            temp = findViewById(R.id.bottomRight);
            chosen(temp);
        }
        else if(mc==br && br!=0 && tl==0)
        {
            temp = findViewById(R.id.topLeft);
            chosen(temp);
        }
        else if(br==tl && tl!=0 && mc==0)
        {
            temp = findViewById(R.id.midCenter);
            chosen(temp);
        }
        else if(bl==mc && mc!=0 && tr==0)
        {
            temp = findViewById(R.id.topRight);
            chosen(temp);
        }
        else if(tr==mc && mc!=0 && bl==0)
        {
            temp = findViewById(R.id.bottomLeft);
            chosen(temp);
        }
        else if(bl==tr && tr!=0 && mc==0)
        {
            temp = findViewById(R.id.midCenter);
            chosen(temp);
        }
        else if(mc==0)
        {
            temp = findViewById(R.id.midCenter);
            chosen(temp);
        }
        else if(tl==0)
        {
            temp = findViewById(R.id.topLeft);
            chosen(temp);
        }
        else if(tr==0)
        {
            temp = findViewById(R.id.topRight);
            chosen(temp);
        }
        else if(bl==0)
        {
            temp = findViewById(R.id.bottomLeft);
            chosen(temp);
        }
        else if(br==0)
        {
            temp = findViewById(R.id.bottomRight);
            chosen(temp);
        }
        else if(tc==0)
        {
            temp = findViewById(R.id.topCenter);
            chosen(temp);
        }
        else if(ml==0)
        {
            temp = findViewById(R.id.midLeft);
            chosen(temp);
        }
        else if(mr==0)
        {
            temp = findViewById(R.id.midRight);
            chosen(temp);
        }
        else if(bc==0)
        {
            temp = findViewById(R.id.bottomCenter);
            chosen(temp);
        }
    }

    public void chosen(View view)
    {
        if(gameOver)return;
        Random r = new Random();
        int c;
        if(xTurn)c=1;
        else c=2;
        int x = r.nextInt(4);
        ImageView myImage = (ImageView) view;
        switch(myImage.getId())
        {
            case R.id.topLeft:
                if(tl==0)tl=c;
                else return;
                break;
            case R.id.topCenter:
                if(tc==0)tc=c;
                else return;
                break;
            case R.id.topRight:
                if(tr==0)tr=c;
                else return;
                break;
            case R.id.midLeft:
                if(ml==0)ml=c;
                else return;
                break;
            case R.id.midCenter:
                if(mc==0)mc=c;
                else return;
                break;
            case R.id.midRight:
                if(mr==0)mr=c;
                else return;
                break;
            case R.id.bottomLeft:
                if(bl==0)bl=c;
                else return;
                break;
            case R.id.bottomCenter:
                if(bc==0)bc=c;
                else return;
                break;
            case R.id.bottomRight:
                if(br==0)br=c;
                else return;
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
            checkWin();
            setTurn();
            if(ai && !xTurn)doAI();
        }
    }
}
