package com.example.rahaf.safeheart1;

import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class User_Activity extends AppCompatActivity {
    ImageButton doctor_button, family_button, location, notification_button;
    TextView txtArduino,patient_name;
    String patientID;
    double heartRateAV = 0.0;
    ArrayList<Double> rates = new ArrayList<>();
    int counter = 0;
    private static final String TAG = "bluetooth2";
    Handler h;
    final int RECIEVE_MESSAGE = 1;		// Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    //private ConnectedThread mConnectedThread;
    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "00:21:13:04:8F:EB";

    String first_name, last_name, age, doctor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //BluetoothConnection();
        doctor_button = (ImageButton) findViewById(R.id.doctor);
        family_button = (ImageButton) findViewById(R.id.family);
        location = (ImageButton) findViewById(R.id.map);
        notification_button = (ImageButton) findViewById(R.id.notification);
        patient_name = (TextView) findViewById(R.id.patient_name);
        txtArduino = (TextView) findViewById(R.id.textView2);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientID = extras.getString("ID");
            first_name = extras.getString("firstName");
            last_name = extras.getString("lastName");
            age =  extras.getString("age");
            doctor_id =  extras.getString("doctor_id");

        }
        patient_name.setText(first_name);

        //after bluetooth connection


    }
    public void DoctorActivity(View view)
    {
        doctor_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),DoctorActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }


    public void FamilyActivity(View view)
    {
        family_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),FamilyActivity.class);
                        intent.putExtra("ID",patientID);
                        startActivity(intent);
                    }
                }
        );
    }


    public void MapsActivity (View view)
    {
        location.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),MapsActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    public void NotificationActivity()
    {
        notification_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),NotificatinActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    // Get reading from Arduino


    private class TimeService extends Service
    {
        public static final long NOTIFY_INTERVAL = 30 * 1000; //30 SECONDS
        private Handler mHandler = new Handler();
        private Timer mTimer = null;
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate()
        {
            if(mTimer != null)
            {
                mTimer.cancel();
            }else
            {
                mTimer = new Timer();
            }
            mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL );
            sendDataToServer();

        }
        class TimeDisplayTimerTask extends TimerTask
        {

            String data = String.valueOf(txtArduino.getText());
            double heartRate = Double.parseDouble(data);


            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // send data to server;
                        //update the heart rate read and Time
                        counter = 0;
                        rates = new ArrayList<>();
                        while( checkHeartRate(heartRate))
                        {
                            rates.add(heartRate);
                            counter ++;

                        }
                    }
                });


            }


        }
        public void sendDataToServer()
        {
            heartRateAV = sumOfRates()/counter;








        }
        public double sumOfRates()
        {
            double sum =0.0;
            for(int i=0;i<rates.size();i++)
                sum+=rates.get(i);

            return sum;
        }

            //function to get time
            private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }



    }

    public boolean checkHeartRate(double reading)
    {
        //for adult ages
        if(reading == 0.0)
        {
            //Call emergency, send location by message

        }
        else if(reading < 60)
        {
            //send notification message "Alarm" to family and doctor

        }
        else if(reading > 100)
        {
            //send notification to family and doctor

        }
        return true;


    }




}
