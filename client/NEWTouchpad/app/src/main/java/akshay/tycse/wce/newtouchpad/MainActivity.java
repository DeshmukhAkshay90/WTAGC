package akshay.tycse.wce.newtouchpad;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.ParcelUuid;
import android.view.View;

import android.bluetooth.BluetoothAdapter;

import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
public class MainActivity extends Activity implements View.OnClickListener{

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    static    BluetoothAdapter mBluetoothAdapter;
    static OutputStream outputStream;
    static InputStream inStream;
    BufferedWriter bwr;
    static PrintStream ps;
    BluetoothSocket socket;
    TextView msg;
    EditText name;
    boolean flag=false;
    Button ton;

    static public PrintStream getPrintStream()
    {
        return ps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.server_name);
        msg=(TextView)findViewById(R.id.textview_msg);
        ton=(Button)findViewById(R.id.turnon);
        ton.setOnClickListener(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null)
        {
            msg.setText("device not supported");
        }

    }




    public void setup()
    {

        mBluetoothAdapter.cancelDiscovery();

        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        String sername=name.getText().toString();
        if(sername.trim()==null)
        {
            sername="SERVER";
            displayMsg("please enter Server name");
        }

        for (BluetoothDevice device : devices)
        {

            if (device.getName().contains(sername))
            {
              //  displayMsg("device fnd "+device.getAddress()+" NAME ="+device.getName());

                try {
                    flag=true;
                    ParcelUuid[] uuids = device.getUuids();
                    socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    socket.connect();
                    Toast.makeText(getApplicationContext(), "CONNECTED",
                            Toast.LENGTH_LONG).show();


                    outputStream = socket.getOutputStream();
                    inStream = socket.getInputStream();
                    ps=new PrintStream(outputStream);

                    displayMsg( "STRAEMS CRTD");

                    break;
                }

                catch (Exception e)
                {
                    msg.setText("Exception at stream crt"+e);

                }

            }

        }

        if (flag && socket!=null &&socket.isConnected())
        {
            showMouseAndKeyboard();

        }
        else
        {
            displayMsg("Server device is not available     ");
            // msg.setText("Server device is not available");
        }
    }





    @Override
    public void onClick(View view)
    {
        if (!mBluetoothAdapter.isEnabled())
        {

            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        setup();
    }
    void showMouseAndKeyboard()
    {
        Intent i=new Intent(MainActivity.this,StartPage.class);
        startActivity(i);
    }
    void displayMsg(String msg)
    {
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT).show();
    }
}
