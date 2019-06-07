package com.example.fitvending;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class RutinaActivity extends AppCompatActivity
                            implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button ButtonSalir, cancelar, guardar;
    Spinner sp_actividad;
    EditText lbl_minutos;
    TextView txt_calorias;

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
        protected void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        cancelar=findViewById(R.id.RutinaCancelarButton);
        guardar=findViewById(R.id.RutinaSaveButton);
        sp_actividad=findViewById(R.id.ActividadSpinner);
        lbl_minutos=findViewById(R.id.RutinaInputMinutos);
        txt_calorias=findViewById(R.id.lbl_CaloriasNum_R);

        ButtonSalir.setOnClickListener(this);
             Spinner Actividades = findViewById(R.id.ActividadSpinner);

             ArrayList<String> l_rutina = new ArrayList<>();
             l_rutina.add("Aerobic (Moderado)");
             l_rutina.add("Aerobic (Intenso)");
             l_rutina.add("Bailar");
             l_rutina.add("Bicicleta");
             l_rutina.add("Caminar lento (4 km/h)");
             l_rutina.add("Caminar rapido (7 km/h)");
             l_rutina.add("Correr lento (9 km/h");
             l_rutina.add("Correr rapido (12 km/h");
             l_rutina.add("Hockey");
             l_rutina.add("Nadar");
             l_rutina.add("Patinar");
             l_rutina.add("Pesas");
             l_rutina.add("Tenis");
             ArrayAdapter<String> adap_rutina = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,l_rutina);
             Actividades.setAdapter(adap_rutina);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rutina r1;
                String id,nombre;
                int minutos;
                double calorias=0.0;

                id = sp_actividad.getSelectedItemPosition() + sp_actividad.getSelectedItem().toString().substring(0,1);
                nombre = sp_actividad.getSelectedItem().toString();
                minutos = Integer.parseInt(lbl_minutos.getText().toString());

                switch(id) {

                    case "0A":
                        calorias = 7.3 * minutos;
                        break;

                    case "1A":
                        calorias = 11.0 * minutos;
                        break;

                    case "2B":
                        calorias = 3.16 * minutos;
                        break;

                    case "3B":
                        calorias = 8.1 * minutos;
                        break;

                    case "4C":
                        calorias = 3.9 * minutos;
                        break;

                    case "5C":
                        calorias = 7.3 * minutos;
                        break;

                    case "6C":
                        calorias = 11.7 * minutos;
                        break;

                    case "7C":
                        calorias = 15.8 * minutos;
                        break;

                    case "8H":
                        calorias = 4.6 * minutos;
                        break;

                    case "9N":
                        calorias = 6.66 * minutos;
                        break;

                    case "10P":
                        calorias = 5.33 * minutos;
                        break;

                    case "11P":
                        calorias = 4.16 * minutos;
                        break;

                    case "12T":
                        calorias = 5.83 * minutos;
                        break;
                }

                r1 = new Rutina(id,nombre,minutos,calorias);

                txt_calorias.setText(Double.toString(calorias));
            }
        });
    }



        public void onClick (View view){
        // Handle navigation view item clicks here.
            Intent intent;
        switch(view.getId()){

            /*case R.id.RutinaSalirButton:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);*/

        }

        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_Rutina){
            intent = new Intent(this,RutinaActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}