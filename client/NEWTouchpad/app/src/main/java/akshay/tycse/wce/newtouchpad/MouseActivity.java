package akshay.tycse.wce.newtouchpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.PrintStream;

public class MouseActivity extends AppCompatActivity implements View.OnTouchListener ,View.OnClickListener
{ ImageButton left,right;
    TextView msg;
    float x,y;
    BufferedWriter bwr;
    static PrintStream ps;
    ImageView iv;
    boolean flag=false;
    float initx=0;
    float inity=0;
    float disx=0;
    float disy=0;
    boolean mouseMoved=false;
    Display display;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_mouse);
           // displayMsg("getting printstream");
            ps = MainActivity.getPrintStream();
           // displayMsg("got printstream");
            left = (ImageButton) findViewById(R.id.left_button);
            right = (ImageButton) findViewById(R.id.right_button);
            iv = (ImageView) findViewById(R.id.image_view);
            iv.setOnTouchListener(this);
            left.setOnClickListener(this);
            right.setOnClickListener(this);
            displayMsg("okkk");
            display = getWindowManager().getDefaultDisplay();
        }
        catch (Exception e)
        {
            displayMsg("ExcpM: "+e);
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        String str="";


        x=motionEvent.getX();
        y=motionEvent.getY();

        //Toast.makeText(getApplicationContext(), x + " " + y, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),motionEvent.toString(),Toast.LENGTH_LONG).show();


        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //save X and Y positions when user touches the TextView
                initx =x;
                inity = y;
                mouseMoved=false;
                break;
            case MotionEvent.ACTION_MOVE:
                disx = x - initx; //Mouse movement in x direction
                disy = y - inity; //Mouse movement in y direction
                            /*set init to new position so that continuous mouse movement
                            is captured*/
                initx = x;
                inity = y;
                if (disx != 0 || disy != 0) {
                    str="pressed:"+String.valueOf(disx)+":"+String.valueOf(disy);
                    ps.println(str); //send mouse movement to server
                }
                mouseMoved=true;
                break;
            case MotionEvent.ACTION_UP:

                //consider a tap only if usr did not move mouse after ACTION_DOWN
                if(!mouseMoved){
                    ps.println("clickleft");
                }
                break;
        }



        return true;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.left_button)
        {
            //msg.append("mouse left bt pressed");

            ps.println("clickleft");
        }
        else
        {
            ps.println("clickright");
        }
    }
    void displayMsg(String msg)
    {
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT).show();
    }
}