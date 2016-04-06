package com.example.pierre.echantillonnage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gravity;

    private float last_x, last_y, last_z;


    private static final int SHAKE_THRESHOLD = 600;
    private float last_gx, last_gy, last_gz;



    private FileOutputStream fstream;

    final long startTime = System.currentTimeMillis();


    private int counter;

    private int nb_val = 0;
    private long last_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        last_x = 0;
        last_y = 0;
        last_z = 0;


        last_gx = 0;
        last_gy = 0;
        last_gz = 0;

        File dir = new File (Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/echantillonnage");

        dir.mkdirs();
        File file = new File(dir, "data.txt");
        try {
            fstream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        counter = 0;
    }

    //adb shell
    //cd /storage/emulated/0/echantillonnage
    //data.txt
    //adb pull /storage/emulated/0/echantillonnage/data.txt ./

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, 5000);
//        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void writeFile(String d)
    {
        try {
            fstream.write(d.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis() - startTime;
            writeFile(Long.toString(curTime)+  "\n");
   /*         if(nb_val == 0)
            {
                last_time = curTime;
            }
            nb_val++;
            if(Math.abs(last_time - curTime) > 1000)
            {
                writeFile(Long.toString(nb_val) + "\n");
                nb_val = 0;
            }*/
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];

//            Log.w("X = ", Float.toString(x));
//            Log.w("Y = ", Float.toString(y));
//            Log.w("Z = ", Float.toString(z));
//            String print = Long.toString(curTime) + " ; " + Float.toString(x) + "\n";
//            writeFile(print);
//            Log.w("counter", Integer.toString(counter));
           /* counter++;
            TextView textViewX = (TextView) findViewById(R.id.x);
            TextView textViewY = (TextView) findViewById(R.id.y);
            TextView textViewZ = (TextView) findViewById(R.id.z);
            textViewX.setText(String.valueOf(x));
            textViewY.setText(String.valueOf(y));
            textViewZ.setText(String.valueOf(z));


            last_x = x;
            last_y = y;
            last_z = z;*/
        }


        if (mySensor.getType() == Sensor.TYPE_GRAVITY) {


            TextView textViewgx = (TextView) findViewById(R.id.gx);
            textViewgx.setText(String.valueOf(event.values[0]));
            TextView textViewgy = (TextView) findViewById(R.id.gy);
            textViewgy.setText(String.valueOf(event.values[1]));
            TextView textViewgz = (TextView) findViewById(R.id.gz);
            textViewgz.setText(String.valueOf(event.values[2]));


            float gx = Math.abs(event.values[0]);
            float gy = Math.abs(event.values[1]);
            float gz = Math.abs(event.values[2]);

            last_gx = gx;
            last_gy = gy;
            last_gz = gz;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
