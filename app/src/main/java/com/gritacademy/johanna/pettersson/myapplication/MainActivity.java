package com.gritacademy.johanna.pettersson.myapplication;

import static android.util.Half.EPSILON;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.cos;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private SensorManager sensorManager;
private Sensor gyroSensor;
Button btn1,btn2,btn3;
TextView value;


    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(gyroListener, gyroSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, gyroSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sensorManager.registerListener(gyroListener, gyroSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        value = findViewById(R.id.values);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }
    public SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            value.setText("X: "+(int)x+" rad/s & "+"Y: "+(int)y+" rad/s & "+"Z: "+(int)z+" rad/s");

            btn1.setRotation(x);
            btn2.setRotation(y);
            btn3.setRotation(z);

            if (x<-0.1) {
                btn1.setBackgroundColor(Color.GREEN);
            } else if (x==0) {
                btn1.setBackgroundColor(Color.GRAY);
            }else if (x>5){
                String text = "The X axis gyro went above 5";
                Log.println(Log.DEBUG, "X Axis", text);
            }
            if (y<-0.1) {
                btn2.setBackgroundColor(Color.RED);
            }else if (y==0) {
                btn2.setBackgroundColor(Color.GRAY);
            } else if (y>5){
                String text = "The Y axis gyro went above 5";
                Log.println(Log.DEBUG, "Y Axis", text);
            }
            if (z<-0.1) {
                btn3.setBackgroundColor(Color.BLUE);
            }else if (z==1) {
                btn3.setBackgroundColor(Color.GRAY);
            }else if (z>5){
                String text = "The Z axis gyro went above 5";
                Log.println(Log.DEBUG, "Z Axis", text);
            }
        }
    };

}