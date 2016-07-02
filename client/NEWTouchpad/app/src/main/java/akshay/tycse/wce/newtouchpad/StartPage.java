package akshay.tycse.wce.newtouchpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity implements View.OnClickListener{

    Button touchpad,keypad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        touchpad=(Button)findViewById(R.id.tpad);
        keypad=(Button)findViewById(R.id.kpad);
        touchpad.setOnClickListener(this);
        keypad.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.tpad)
        {
            Intent i=new Intent(StartPage.this,MouseActivity.class);
            startActivity(i);
        }
        else
        {
            Intent i=new Intent(StartPage.this,GamingConsole.class);
            startActivity(i);
        }
    }
}
