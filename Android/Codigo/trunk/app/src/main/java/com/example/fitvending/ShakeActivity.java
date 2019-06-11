package com.example.fitvending;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager shakeSensorManager;

    private float acelVal;
    private float acelLast;
    private float shake;

    private TextView tvShakeService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        shakeSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        shakeSensorManager.registerListener(this, shakeSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

       tvShakeService = findViewById(R.id.tvShake);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        acelLast=acelVal;
        acelVal= (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = acelVal-acelLast;
        shake = shake * 0.9f + delta;

        if(shake>12)
        {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            tvShakeService.setText("Me mareoooooooo");
            tvShakeService.setTextColor(color);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
