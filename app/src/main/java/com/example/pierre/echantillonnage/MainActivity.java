package com.example.pierre.echantillonnage;

import android.content.Context;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gravity;
    private long lastUpdate = 0;
    private long lastUpdate2 = 0;

    private float last_x, last_y, last_z;


    private static final int SHAKE_THRESHOLD = 600;
    private float last_gx, last_gy, last_gz;

    static int counter = 0;
    private Writer writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        last_x = 0;
        last_y = 0;
        last_z = 0;


        /*last_gx = 0;
        last_gy = 0;
        last_gz = 0;*/

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, 2, SensorManager.SENSOR_DELAY_NORMAL);
//        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("data.txt"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
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
            writer.write(d);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER && counter <=100) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            Log.w("X = ", Float.toString(x));
            Log.w("Y = ", Float.toString(y));
            Log.w("Z = ", Float.toString(z));
            long curTime = System.currentTimeMillis();
            String print = Long.toString(curTime) + " ; " + Float.toString(x);
            writeFile(print);
//            Log.w("counter", Integer.toString(counter));
        }
    /*        double[] tabx = new double[100];
            double[] taby = new double[100];


            if (counter < 10){
                tabx[counter] = curTime;
                taby[counter] = x;
                counter++;
            }
            else if(counter == 10)
            {

                FFT fast = new FFT(10);
                fast.fft(tabx, taby);
                String time = "";
                for(double i : tabx)
                {
                    time += Double.toString(i) + " ";
                }
                String value = "";
                for(double i : taby)
                {
                    value += Double.toString(i) + " ";
                }
                Log.w("time = ", time);
                Log.w("value = ", value);
                counter = 0;
            }





            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

             *//*   float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                }*//*

                if (true || Math.abs(x - last_x) > 1 || Math.abs(y - last_y) > 1 || Math.abs(z - last_z) > 1) {
//                    Log.w("-----------------------", "---------------------");
//                    Log.w("x = ", String.valueOf(x));
//                    Log.w("y = ", String.valueOf(y));
//                    Log.w("z = ", String.valueOf(z));
//                    Log.w("+++++++++++++++++++++++", "+++++++++++++++++++++");
                    Context context = getApplicationContext();
                    CharSequence text = String.valueOf(x);
                    int duration = Toast.LENGTH_SHORT;

//                    Toast toast = Toast.makeText(context, text, duration);
//                    toast.show();

                    TextView textViewX = (TextView) findViewById(R.id.x);
                    TextView textViewY = (TextView) findViewById(R.id.y);
                    TextView textViewZ = (TextView) findViewById(R.id.z);
                    textViewX.setText(String.valueOf(x));
                    textViewY.setText(String.valueOf(y));
                    textViewZ.setText(String.valueOf(z));

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }*/


/*        if (mySensor.getType() == Sensor.TYPE_GRAVITY) {


            TextView textViewgx = (TextView) findViewById(R.id.gx);
            textViewgx.setText(String.valueOf(event.values[0]));
            TextView textViewgy = (TextView) findViewById(R.id.gy);
            textViewgy.setText(String.valueOf(event.values[1]));
            TextView textViewgz = (TextView) findViewById(R.id.gz);
            textViewgz.setText(String.valueOf(event.values[2]));
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate2) > 200) {
                lastUpdate2 = curTime;

                float gx = Math.abs(event.values[0]);
                float gy = Math.abs(event.values[1]);
                float gz = Math.abs(event.values[2]);


                float last_sum = Math.abs(last_gx) + Math.abs(last_gy) + Math.abs(last_gz);
                float sum = gx + gy + gz;
                TextView calc = (TextView) findViewById(R.id.calc);
                calc.setText(String.valueOf(last_sum - sum));

                if (last_sum - sum > 0.05) {
                    TextView dir = (TextView) findViewById(R.id.direction);
                    dir.setText("Up");
                } else if (last_sum - sum < 0.05) {
                    TextView dir = (TextView) findViewById(R.id.direction);
                    dir.setText("Down");
                } else {
                    TextView dir = (TextView) findViewById(R.id.direction);
                    dir.setText("...");
                }
                last_gx = gx;
                last_gy = gy;
                last_gz = gz;

            }


        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
