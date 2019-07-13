package com.example.fitvending;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fitvending.Datos.DBHandler;
import com.example.fitvending.Datos.UsuarioDAO;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CronometroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CronometroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CronometroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final int VERDE_REF = Color.rgb(124, 213, 22);
    Button btn_iniciar, btn_detener, btn_reiniciar;
    ImageButton btn_caminar, btn_correr, btn_bicicleta, btn_nadar;
    Chronometer cronometro;
    Boolean empezar = false;
	double caloriasCaminar = 0.063;
	double caloriasCorrer = 0.151;
	double caloriasNadar = 0.173;
	double caloriasBicicleta = 0.120;
    String modo = "non";
    TextView calorias;
    long detenerse;
    Context contextoActual;
    View vista;

    private OnFragmentInteractionListener mListener;

    public CronometroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CronometroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CronometroFragment newInstance(String param1, String param2) {
        CronometroFragment fragment = new CronometroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_cronometro, container, false);
        contextoActual = inflater.getContext();
        btn_iniciar = vista.findViewById(R.id.btn_iniciar);
        btn_detener = vista.findViewById(R.id.btn_detener);
        btn_reiniciar = vista.findViewById(R.id.btn_reiniciar);
        btn_caminar = vista.findViewById(R.id.btn_caminar);
        btn_correr = vista.findViewById(R.id.btn_correr);
        btn_bicicleta = vista.findViewById(R.id.btn_bicicleta);
        btn_nadar = vista.findViewById(R.id.btn_nadar);
        cronometro = vista.findViewById(R.id.cronometro);
        calorias = vista.findViewById(R.id.txt_calorias);
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
                double calorias = detenerCronometro();
                //En este archivo tenemos el usuario guardado sin necesidad de pasar parametros
                MainActivity activity = (MainActivity) getActivity();
                SharedPreferences preferences = activity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                String userName_sp = preferences.getString("UserName", "");
                DBHandler db = new DBHandler(v.getContext());
                UsuarioDAO userDao = new UsuarioDAO();
                userDao.actualizarCalorias(db,calorias,userName_sp,1);
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
        return vista;
    }


    private void reiniciarCronometro() {
        cronometro.setBase(SystemClock.elapsedRealtime());
        detenerse = 0;
        calorias.setText("Calorias quemadas: ");
    }

    private double detenerCronometro() {
        if (empezar){

            cronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - cronometro.getBase();
            empezar = false;
            return (calcularCalorias());
        }
        return 0.0;
    }

    private void iniciarCronometro() {
        if(!empezar){
            cronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            cronometro.start();
            empezar = true;
            calorias.setText("Calorias quemadas: ");
        }
    }

    private double calcularCalorias(){

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
                calQuemadas = ((minutos + segundos) * caloriasCaminar)/1.00;
                break;
            case "correr":
                calQuemadas = ((minutos + segundos) * caloriasCorrer)/1.00;
                break;
            case "bicicleta":
                calQuemadas = ((minutos + segundos) * caloriasBicicleta)/1.00;
                break;
            case "nadar":
                calQuemadas = ((minutos + segundos) * caloriasNadar)/1.00;
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
            calorias.setText("Calorias quemadas: " +  String.valueOf(new DecimalFormat("#.##").format(calQuemadas)));
        }

        rc = new Rutina(id,modo, (int) (minutos+segundos)*60,calQuemadas);
       return calQuemadas;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
