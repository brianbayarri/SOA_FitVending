package com.example.fitvending;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SensorsService extends Service implements SensorEventListener {

    private SensorManager sensorManager;

    //PASOS
    private int pasosDados;

    //SHAKE
    private float acelVal;
    private float acelLast;
    private float shake;

    //PROXIMIDAD
    private int modificaciones;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        modificaciones = 0;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor shakeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Contador de pasos no disponible", Toast.LENGTH_LONG).show();
        }

        if (shakeSensor != null) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Acelerometro no disponible", Toast.LENGTH_LONG).show();
        }

        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor de proximidad no disponible", Toast.LENGTH_LONG).show();
        }

        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            float pasos = event.values[0];
            String pasostext = String.valueOf(pasos);
            Toast.makeText(this, pasostext, Toast.LENGTH_LONG).show();
        }
        else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast=acelVal;
            acelVal= (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal-acelLast;
            shake = shake * 0.9f + delta;

            if(shake>18) {
               // Toast.makeText(this, "Usted me enciende, digo agita", Toast.LENGTH_LONG).show();
                BtConnectionService.enviarDatosAArduino("5");

            }

        }
        else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            modificaciones++;
            if(modificaciones == 10){
               // Toast.makeText(this, "Ha pulsado el sensor por 10 veces", Toast.LENGTH_LONG).show();
                BtConnectionService.enviarDatosAArduino("6");
                modificaciones=0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
