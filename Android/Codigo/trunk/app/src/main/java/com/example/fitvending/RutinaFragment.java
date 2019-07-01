package com.example.fitvending;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitvending.Datos.DBHandler;
import com.example.fitvending.Datos.UsuarioDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RutinaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RutinaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutinaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button cancelar, guardar;
    Spinner sp_actividad;
    EditText lbl_minutos;
    TextView txt_calorias;
    Context contextoActual;
    View vista;

    private OnFragmentInteractionListener mListener;

    public RutinaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutinaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RutinaFragment newInstance(String param1, String param2) {
        RutinaFragment fragment = new RutinaFragment();
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
        vista = inflater.inflate(R.layout.fragment_rutina, container, false);
        contextoActual = inflater.getContext();
        cancelar= vista.findViewById(R.id.RutinaCancelarButton);
        guardar= vista.findViewById(R.id.RutinaSaveButton);
        sp_actividad= vista.findViewById(R.id.ActividadSpinner);
        lbl_minutos= vista.findViewById(R.id.RutinaInputMinutos);
        txt_calorias= vista.findViewById(R.id.lbl_CaloriasNum_R);
        Spinner Actividades =  vista.findViewById(R.id.ActividadSpinner);

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
        final ArrayAdapter<String> adap_rutina = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_rutina);
        Actividades.setAdapter(adap_rutina);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp_actividad.setAdapter(adap_rutina);
                lbl_minutos.setText("");
                txt_calorias.setText("0.0/0.0");

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rutina r1;
                String id,nombre, minu_string;
                int minutos;
                double calorias=0.0;
                id = sp_actividad.getSelectedItemPosition() + sp_actividad.getSelectedItem().toString().substring(0,1);
                nombre = sp_actividad.getSelectedItem().toString();
                minu_string = lbl_minutos.getText().toString();

                if(!id.equals("") && !nombre.equals("") && !minu_string.equals("")) {

                    minutos = Integer.parseInt(minu_string);

                    switch (id) {

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

                    r1 = new Rutina(id, nombre, minutos, calorias);

                    txt_calorias.setText(Double.toString(calorias));

                    updateCalorias(view.getContext(), calorias);
                }
            }
        });
        return vista;
    }

    public void updateCalorias(Context context, double calorias)
    {
        //En este archivo tenemos el usuario guardado sin necesidad de pasar parametros
        MainActivity activity = (MainActivity) getActivity();
        SharedPreferences preferences = activity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String userName_sp = preferences.getString("UserName", "");
        DBHandler db = new DBHandler(context);
        UsuarioDAO userDao = new UsuarioDAO();
        userDao.actualizarCalorias(db,calorias,userName_sp,1);

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
