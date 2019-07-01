package com.example.fitvending;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SuccessRetiroActivity extends AppCompatActivity {

    private int tiempo = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_retiro);

        Bundle datos = this.getIntent().getExtras();
        final String usuario = datos.getString("UserName");
        //Consulto stock nuevamente

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent main = new Intent(SuccessRetiroActivity.this,MainActivity.class);
                main.putExtra("UserName",usuario);
                startActivity(main);
                finish();
            };
        }, tiempo);

    }
}
