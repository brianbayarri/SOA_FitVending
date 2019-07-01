package com.example.fitvending;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.fitvending.Datos.DBHandler;
import com.example.fitvending.Datos.UsuarioDAO;
import com.example.fitvending.MainActivity;
import com.example.fitvending.R;
import com.example.fitvending.entidades.Usuario;

public class LoginActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(v.getContext());
                Usuario user = new Usuario();
                UsuarioDAO userDao = new UsuarioDAO();

                user.setNombreUsuario(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                if(user.getNombreUsuario().isEmpty() || user.getPassword().isEmpty())
                {
                    Toast.makeText(v.getContext(),"El usuario y/o la contraseña están vacíos",Toast.LENGTH_LONG).show();
                }
                else {
                    int rta = userDao.checkIfRecordExist(db,user.getNombreUsuario(), user.getPassword());
                    Intent main;
                    switch (rta)
                    {
                        case 0:
                            Toast.makeText(v.getContext(),"Bienvenido nuevamente a FitVending",Toast.LENGTH_LONG).show();

                            main = new Intent(v.getContext(), MainActivity.class);
                            main.putExtra("UserName",user.getNombreUsuario());
                            startActivity(main);
                            break;
                        case 1:
                            Toast.makeText(v.getContext(),"El usuario y/o la contraseña son incorrectas",Toast.LENGTH_LONG).show();

                            break;
                        case 2:
                            Toast.makeText(v.getContext(),"Hubo un error",Toast.LENGTH_LONG).show();

                            break;
                    }



                }
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(v.getContext());
                Usuario user = new Usuario();
                UsuarioDAO userDao = new UsuarioDAO();

                user.setNombreUsuario(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                if(user.getNombreUsuario().isEmpty() || user.getPassword().isEmpty())
                {
                    Toast.makeText(v.getContext(),"El usuario y/o la contraseña están vacíos",Toast.LENGTH_LONG).show();
                }
                else {
                    if(! userDao.checkIfUserExist(db,user.getNombreUsuario()))
                    {
                        if(userDao.registrarUsuario(db,user))
                        {
                            Toast.makeText(v.getContext(),"Bienvenido a FitVending",Toast.LENGTH_LONG).show();
                            Intent main = new Intent(v.getContext(), MainActivity.class);
                            main.putExtra("UserName",user.getNombreUsuario());
                            startActivity(main);
                        }
                        else
                        {
                            Toast.makeText(v.getContext(),"Hubo un error",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(v.getContext(),"El usuario ya existe",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }


}
