package com.example.fitvending;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.fitvending.BtConnectionService.consultarStock;
import static com.example.fitvending.BtConnectionService.detenerBt;
import static com.example.fitvending.BtConnectionService.enviarDatosAArduino;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CronometroFragment.OnFragmentInteractionListener,
        MainFragment.OnFragmentInteractionListener,
        AlimentacionFragment.OnFragmentInteractionListener,
        LogrosFragment.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        RutinaFragment.OnFragmentInteractionListener
{

    private Toolbar toolbar;
    ImageButton chocoarroz,cereal;

    //VARIABLES USADAS PARA DEFINIR EL COLOR DE LOS PRODUCTOS
    public String colorSinStock = "#D31E1F29";
    public String colorHayStock = "#FFFFFF";
    public String userName;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle datos = this.getIntent().getExtras();
         userName = datos.getString("UserName");
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserName", userName);
        editor.commit();

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ////COMIENZA EL SERVICE DE LOS SENSORES
        Intent pasosService = new Intent(this,SensorsService.class);
        startService(pasosService);
        Fragment frag = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main, frag).commit();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onResume(){

        super.onResume();
        //Toast.makeText(getBaseContext(), BtConnectionService.Stock, Toast.LENGTH_LONG).show();

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
        Fragment myFragment = null;

        boolean seleccion = false;
        if (id == R.id.nav_home) {
            myFragment = new MainFragment();
            seleccion = true;
            toolbar.setTitle("Fit Vending");
        } else if (id == R.id.nav_gallery) {
            myFragment = new CronometroFragment();
            seleccion = true;
            toolbar.setTitle("Cronometro");
        } else if (id == R.id.nav_slideshow) {
            //intent = new Intent(this,Bluetoothactivity.class);
            //startActivity(intent);

        } else if (id == R.id.nav_tools) {

        }

         else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_Rutina) {
            myFragment = new RutinaFragment();
            seleccion = true;
            toolbar.setTitle("Rutina");
        } else if (id == R.id.nav_Alimentacion) {
            myFragment = new AlimentacionFragment();
            seleccion = true;
            toolbar.setTitle("Alimentacion");
        }  else if (id == R.id.nav_Perfil) {
            myFragment = new PerfilFragment();
            seleccion = true;
            toolbar.setTitle("Perfil");
        }   else if (id == R.id.nav_Desafios) {
            myFragment = new LogrosFragment();
            seleccion = true;
            toolbar.setTitle("Desafios");
        }

        if(seleccion)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*
    @Override
    public void onPause()
    {
        super.onPause();
        detenerBt();
    }*/

    @Override
    public void onStop()
    {
        super.onStop();
        //detenerBt();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        Intent restaurar = new Intent(this,PantallaDeCarga.class);
        startActivity(restaurar);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detenerBt();
        Intent servicioSensores = new Intent(this,SensorsService.class);
        stopService(servicioSensores);
    }


    public String getUserNameByFragment()
    {
        return userName;
    }


}
