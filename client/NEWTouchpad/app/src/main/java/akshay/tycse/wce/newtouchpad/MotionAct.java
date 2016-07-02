package akshay.tycse.wce.newtouchpad;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.PrintStream;

public class MotionAct extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    Sensor acc;
    PrintStream ps;
    float initx=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);
        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        ps=MainActivity.getPrintStream();
        if(sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null)
        {
            acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sm.registerListener(this,acc,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            displayMsg("sensor not supported");
        }

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        float arr[]=sensorEvent.values;
        float diff=arr[0]-initx;
        if(diff >5 || diff <-5)
            ps.println("keypad:move:"+diff);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    void displayMsg(String msg)
    {
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT).show();
    }
}
