package com.example.fitvending;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PantallaDeCarga extends AppCompatActivity {

    private int tiempo = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        //Aca poner los permisos

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación

                Intent intent = new Intent(PantallaDeCarga.this, MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, tiempo);
    }
}
