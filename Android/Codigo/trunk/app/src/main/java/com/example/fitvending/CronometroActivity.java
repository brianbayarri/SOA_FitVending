package com.example.fitvending;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class CronometroActivity extends AppCompatActivity {

    public static final int VERDE_REF = Color.rgb(124, 213, 22);
    Button btn_iniciar, btn_detener, btn_reiniciar;
    ImageButton btn_caminar, btn_correr, btn_bicicleta, btn_nadar;
    Chronometer cronometro;
    Boolean empezar = false;
    String modo = "non";
    TextView calorias;
    long detenerse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        btn_iniciar = findViewById(R.id.btn_iniciar);
        btn_detener = findViewById(R.id.btn_detener);
        btn_reiniciar = findViewById(R.id.btn_reiniciar);
        btn_caminar = findViewById(R.id.btn_caminar);
        btn_correr = findViewById(R.id.btn_correr);
        btn_bicicleta = findViewById(R.id.btn_bicicleta);
        btn_nadar = findViewById(R.id.btn_nadar);
        cronometro = findViewById(R.id.cronometro);
        calorias = findViewById(R.id.txt_calorias);
        btn_caminar.setBackgroundResource(android.R.drawable.btn_default);
        btn_correr.setBackgroundResource(android.R.drawable.btn_default);
        btn_bicicleta.setBackgroundResource(android.R.drawable.btn_default);
        btn_nadar.setBackgroundResource(android.R.drawable.btn_default);

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarCronometro();
            }
        });

        btn_detener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detenerCronometro();
            }
        });

        btn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarCronometro();
            }
        });

        btn_caminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modo = "caminar";
                btn_caminar.setBackgroundColor(VERDE_REF);
                btn_correr.setBackgroundResource(android.R.drawable.btn_default);
                btn_bicicleta.setBackgroundResource(android.R.drawable.btn_default);
                btn_nadar.setBackgroundResource(android.R.drawable.btn_default);
                reiniciarCronometro();
            }
        });

        btn_correr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modo = "correr";
                btn_correr.setBackgroundColor(VERDE_REF);
                btn_caminar.setBackgroundResource(android.R.drawable.btn_default);
                btn_bicicleta.setBackgroundResource(android.R.drawable.btn_default);
                btn_nadar.setBackgroundResource(android.R.drawable.btn_default);
                reiniciarCronometro();
            }
        });

        btn_bicicleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modo = "bicicleta";
                btn_bicicleta.setBackgroundColor(VERDE_REF);
                btn_caminar.setBackgroundResource(android.R.drawable.btn_default);
                btn_correr.setBackgroundResource(android.R.drawable.btn_default);
                btn_nadar.setBackgroundResource(android.R.drawable.btn_default);
                reiniciarCronometro();
            }
        });

        btn_nadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modo = "nadar";
                btn_nadar.setBackgroundColor(VERDE_REF);
                btn_caminar.setBackgroundResource(android.R.drawable.btn_default);
                btn_bicicleta.setBackgroundResource(android.R.drawable.btn_default);
                btn_correr.setBackgroundResource(android.R.drawable.btn_default);
                reiniciarCronometro();
            }
        });
    }

    private void reiniciarCronometro() {
        cronometro.setBase(SystemClock.elapsedRealtime());
        detenerse = 0;
        calorias.setText("Calorias quemadas: ");
    }

    private void detenerCronometro() {
        if (empezar){

            cronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - cronometro.getBase();
            empezar = false;
            calcularCalorias();
        }
    }

    private void iniciarCronometro() {
        if(!empezar){
            cronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            cronometro.start();
            empezar = true;
            calorias.setText("Calorias quemadas: ");
        }
    }

    private void calcularCalorias(){

        Rutina rc;
        String id = modo.substring(0,2) + "_C";
        String [] partes;
        String msgError = "Upss!";
        double minutos, segundos, calQuemadas = 0.0;
        Boolean noEleccion = false;
        partes = cronometro.getText().toString().split(":");
        minutos = Double.parseDouble(partes[0]);
        segundos = (Double.parseDouble(partes[1])*0.016)/1.00;
        switch(modo)
        {
            case "caminar":
                calQuemadas = ((minutos + segundos) * 0.063)/1.00;
                break;
            case "correr":
                calQuemadas = ((minutos + segundos) * 0.151)/1.00;
                break;
            case "bicicleta":
                calQuemadas = ((minutos + segundos) * 0.120)/1.00;
                break;
            case "nadar":
                calQuemadas = ((minutos + segundos) * 0.173)/1.00;
                break;
            case "non":
                noEleccion = true;
                break;
        }
        if(noEleccion)
        {
            calorias.setText("Calorias quemadas: " +  msgError);
        }
        else
        {
            calorias.setText("Calorias quemadas: " +  String.valueOf(calQuemadas));
        }

        rc = new Rutina(id,modo, (int) (minutos+segundos)*60,calQuemadas);
    }
}
