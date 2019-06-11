package com.example.fitvending;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText lbl_altura, lbl_edad, lbl_peso;
    Spinner sp_sexo, sp_ejercicio;
    TextView cal_num;
    Button btn_act;
    Context contextoActual;
    View vista;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        contextoActual = inflater.getContext();
        lbl_altura= vista.findViewById(R.id.editlbl_Altura);
        lbl_edad= vista.findViewById(R.id.editlbl_edad);
        lbl_peso= vista.findViewById(R.id.editlbl_Peso);
        sp_sexo= vista.findViewById(R.id.sp_sexo);
        sp_ejercicio= vista.findViewById(R.id.sp_ejercicio);
        cal_num= vista.findViewById(R.id.lbl_CaloriasNum_P);
        btn_act= vista.findViewById(R.id.btn_act);
        final ArrayAdapter<String> adap_sexo;
        final ArrayAdapter<String> adap_ejercicio;

        ArrayList<String> l_sexo = new ArrayList<>();
        l_sexo.add("H");
        l_sexo.add("M");
        adap_sexo = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_sexo);
        sp_sexo.setAdapter(adap_sexo);

        ArrayList<String> l_ejercicio = new ArrayList<>();
        l_ejercicio.add("1: Poco o ningún ejercicio");
        l_ejercicio.add("2: Ejercicio ligero (1 a 3 días a la semana)");
        l_ejercicio.add("3: Ejercicio moderado (3 a 5 días a la semana)");
        l_ejercicio.add("4: Deportista (6 -7 días a la semana)");
        l_ejercicio.add("5: Atleta (Entrenamientos mañana y tarde)");
        adap_ejercicio = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_ejercicio);
        sp_ejercicio.setAdapter(adap_ejercicio);

        btn_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcularCal();
            }
        });
        return vista;
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
