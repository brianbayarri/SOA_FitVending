package com.example.fitvending;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {

    EditText lbl_altura, lbl_edad, lbl_peso;
    Spinner sp_sexo, sp_ejercicio;
    TextView cal_num;
    Button btn_act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        lbl_altura=findViewById(R.id.editlbl_Altura);
        lbl_edad=findViewById(R.id.editlbl_edad);
        lbl_peso=findViewById(R.id.editlbl_Peso);
        sp_sexo=findViewById(R.id.sp_sexo);
        sp_ejercicio=findViewById(R.id.sp_ejercicio);
        cal_num=findViewById(R.id.lbl_CaloriasNum_P);
        btn_act=findViewById(R.id.btn_act);
        final ArrayAdapter<String> adap_sexo;
        final ArrayAdapter<String> adap_ejercicio;

        ArrayList<String> l_sexo = new ArrayList<>();
        l_sexo.add("H");
        l_sexo.add("M");
        adap_sexo = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,l_sexo);
        sp_sexo.setAdapter(adap_sexo);

        ArrayList<String> l_ejercicio = new ArrayList<>();
        l_ejercicio.add("1: Poco o ningún ejercicio");
        l_ejercicio.add("2: Ejercicio ligero (1 a 3 días a la semana)");
        l_ejercicio.add("3: Ejercicio moderado (3 a 5 días a la semana)");
        l_ejercicio.add("4: Deportista (6 -7 días a la semana)");
        l_ejercicio.add("5: Atleta (Entrenamientos mañana y tarde)");
        adap_ejercicio = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,l_ejercicio);
        sp_ejercicio.setAdapter(adap_ejercicio);

        btn_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcularCal();
            }
        });
    }

    private void calcularCal() {

        double cal_diarias=0.0, ej=0.0;
        double altura_f=0.0, peso_f=0.0;
        int edad_f=0;


        String peso=lbl_peso.getText().toString();
        String altura=lbl_altura.getText().toString();
        String edad=lbl_edad.getText().toString();
        String sex_sel=sp_sexo.getSelectedItem().toString();
        String ejer_sel=sp_ejercicio.getSelectedItem().toString().substring(0,1);

        switch(ejer_sel) {

            case "1":
                ej=1.2;
                break;

            case "2":
                ej=1.375;
                break;

            case "3":
                ej=1.55;
                break;

            case "4":
                ej=1.72;
                break;

            case "5":
                ej=1.9;
                break;
        }

        if(!altura.equals("") && !peso.equals("") && !edad.equals("") && !ejer_sel.equals("")) {

            peso_f=Double.parseDouble(peso);
            altura_f=Double.parseDouble(altura);
            edad_f=Integer.parseInt(edad);

            if(sex_sel.equals("H")) {
                cal_diarias=(66.0 + (13.7 * peso_f) + (5 * altura_f) - (6.75 * edad_f))*ej; }
            else if (sex_sel.equals("M")){
                cal_diarias=(655 + (9.6* peso_f) + (1.8 * altura_f) - (4.7 * edad_f))*ej; }

        }
        cal_num.setText("0.0/"+String.valueOf(cal_diarias));
    }

}

