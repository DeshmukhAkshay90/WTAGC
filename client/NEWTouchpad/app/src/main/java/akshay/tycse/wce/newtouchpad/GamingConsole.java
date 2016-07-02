package akshay.tycse.wce.newtouchpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.PrintStream;

public class GamingConsole extends AppCompatActivity implements View.OnTouchListener
{

    Button upkey,downkey,leftkey,rightkey,mot;
    PrintStream ps;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming_console);
        upkey=(Button)findViewById(R.id.upk);
        downkey=(Button)findViewById(R.id.downk);
        leftkey=(Button)findViewById(R.id.leftk);
        rightkey=(Button)findViewById(R.id.rightk);
        mot=(Button)findViewById(R.id.rightk);
        upkey.setOnTouchListener(this);
        downkey.setOnTouchListener(this);
        leftkey.setOnTouchListener(this);
        rightkey.setOnTouchListener(this);
      //  mot.setOnTouchListener(this);
        ps=MainActivity.getPrintStream();



    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {


        switch (view.getId())
        {
            case R.id.upk:

                ps.println("Keypad:up");


                break;
            case  R.id.downk:
                ps.println("Keypad:down");

                break;
            case R.id.leftk:
                ps.println("Keypad:left");

                break;
            case R.id.rightk:
                ps.println("Keypad:right");
          /*  case R.id.mt:
                Intent i=new Intent(GamingConsole.this,MotionAct.class);
                startActivity(i);
                break;*/
        }

        return  false;
    }
/*
    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.upk:
                upkey.setPressed(false);
                ps.println("Keypad:up");



                break;
            case  R.id.downk:
                downkey.setPressed(false);
                ps.println("Keypad:down");

                break;
            case R.id.leftk:
                leftkey.setPressed(false);
                ps.println("Keypad:left");

                break;
            case R.id.rightk:
                rightkey.setPressed(false);
                ps.println("Keypad:right");

                break;
        }

    }
*/

}
