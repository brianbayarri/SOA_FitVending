package com.example.fitvending;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlimentacionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlimentacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlimentacionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final int VERDE_REF = Color.rgb(124, 213, 22);
    public static final int GRIS_REF = Color.rgb(128, 128, 128);
    Switch sw_desayuno, sw_almuerzo, sw_cena, sw_colacion;
    Spinner sp_plato, sp_guarnicion, sp_bebida, sp_porcion1, sp_porcion2, sp_vasos;
    Button btn_cancelar, btn_guardar;
    TextView lbl_calorias;
    Context contextoActual;
    View vista;
    private OnFragmentInteractionListener mListener;

    public AlimentacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlimentacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlimentacionFragment newInstance(String param1, String param2) {
        AlimentacionFragment fragment = new AlimentacionFragment();
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
        vista = inflater.inflate(R.layout.fragment_alimentacion, container, false);
        contextoActual = inflater.getContext();
        btn_cancelar= vista.findViewById(R.id.btn_cancelar);
        btn_guardar= vista.findViewById(R.id.btn_guardar);
        sw_desayuno= vista.findViewById(R.id.sw_desayuno);
        sw_almuerzo= vista.findViewById(R.id.sw_almuerzo);
        sw_cena= vista.findViewById(R.id.sw_cena);
        sw_colacion= vista.findViewById(R.id.sw_colacion);
        sp_plato= vista.findViewById(R.id.sp_plato_principal);
        sp_guarnicion= vista.findViewById(R.id.sp_guarnicion);
        sp_bebida= vista.findViewById(R.id.sp_bebida);
        sp_porcion1= vista.findViewById(R.id.sp_porcion1);
        sp_porcion2= vista.findViewById(R.id.sp_porcion2);
        sp_vasos= vista.findViewById(R.id.sp_vasos);
        lbl_calorias= vista.findViewById(R.id.lbl_CaloriasNum_A);
        final ArrayAdapter<String> adap_desayuno;
        final ArrayAdapter<String> adap_plato;
        final ArrayAdapter<String> adap_colacion;
        final ArrayAdapter<String> adap_guarnicion;
        final ArrayAdapter<String> adap_bebida_des;
        final ArrayAdapter<String> adap_bebida_plato;
        final ArrayAdapter<String> adap_porcion;
        final ArrayAdapter<String> adap_cantidad;
        final ArrayAdapter<String> adap_vacio;
        ArrayList<String> l_desayuno = new ArrayList<>();
        l_desayuno.add("Huevos y panceta");
        l_desayuno.add("Medialunas");
        l_desayuno.add("Omelet");
        l_desayuno.add("Tostado");
        l_desayuno.add("NINGUNA");
        adap_desayuno = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_desayuno);

        ArrayList<String> l_plato = new ArrayList<>();
        l_plato.add("Carne");
        l_plato.add("Ensalada");
        l_plato.add("Pescado");
        l_plato.add("Pollo");
        l_plato.add("NINGUNA");
        adap_plato = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_plato);

        ArrayList<String> l_colacion = new ArrayList<>();
        l_colacion.add("Banana");
        l_colacion.add("Barra cereal");
        l_colacion.add("Sanguche JyQ");
        l_colacion.add("Yogurt");
        l_colacion.add("NINGUNA");
        adap_colacion = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_colacion);

        ArrayList<String> l_guarnicion = new ArrayList<>();
        l_guarnicion.add("Arroz blanco");
        l_guarnicion.add("Ensalada lechuga y tomate");
        l_guarnicion.add("Papas fritas");
        l_guarnicion.add("Pure de papas");
        l_guarnicion.add("NINGUNA");
        adap_guarnicion = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_guarnicion);

        ArrayList<String> l_bebida_des = new ArrayList<>();
        l_bebida_des.add("Cafe c/ leche");
        l_bebida_des.add("Chocolatada");
        l_bebida_des.add("Jugo exprimido");
        l_bebida_des.add("Te");
        l_bebida_des.add("NINGUNA");
        adap_bebida_des = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_bebida_des);

        ArrayList<String> l_bebida_plato = new ArrayList<>();
        l_bebida_plato.add("Agua");
        l_bebida_plato.add("Agua saborizada");
        l_bebida_plato.add("Cerveza");
        l_bebida_plato.add("Gaseosa Cola");
        l_bebida_plato.add("NINGUNA");
        adap_bebida_plato = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_bebida_plato);

        ArrayList<String> l_porcion = new ArrayList<>();
        l_porcion.add("Chica (100gr)");
        l_porcion.add("Mediana (150gr)");
        l_porcion.add("Grande (300gr");
        adap_porcion = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_porcion);

        ArrayList<String> l_cantidad = new ArrayList<>();
        l_cantidad.add("1");
        l_cantidad.add("2");
        l_cantidad.add("3");
        adap_cantidad = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_cantidad);

        ArrayList<String> l_vacio = new ArrayList<>();
        adap_vacio = new ArrayAdapter<String>(contextoActual, R.layout.support_simple_spinner_dropdown_item,l_vacio);

        sw_desayuno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if(on) {
                    sw_desayuno.setThumbTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_desayuno.setTrackTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_almuerzo.setChecked(false);
                    sw_cena.setChecked(false);
                    sw_colacion.setChecked(false);
                    sp_plato.setAdapter(adap_desayuno);
                    sp_porcion1.setAdapter(adap_porcion);
                    sp_bebida.setAdapter(adap_bebida_des);
                    sp_vasos.setAdapter(adap_cantidad);
                    sp_guarnicion.setAdapter(adap_vacio);
                    sp_porcion2.setAdapter(adap_vacio);

                }
                else {
                    sw_desayuno.setThumbTintList(ColorStateList.valueOf(GRIS_REF));
                    sw_desayuno.setTrackTintList(ColorStateList.valueOf(GRIS_REF));
                    sp_plato.setAdapter(adap_vacio);
                    sp_porcion1.setAdapter(adap_vacio);
                    sp_bebida.setAdapter(adap_vacio);
                    sp_vasos.setAdapter(adap_vacio);
                }
            }
        });

        sw_almuerzo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if(on) {
                    sw_almuerzo.setThumbTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_almuerzo.setTrackTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_desayuno.setChecked(false);
                    sw_cena.setChecked(false);
                    sw_colacion.setChecked(false);
                    sp_plato.setAdapter(adap_plato);
                    sp_porcion1.setAdapter(adap_porcion);
                    sp_guarnicion.setAdapter(adap_guarnicion);
                    sp_porcion2.setAdapter(adap_porcion);
                    sp_bebida.setAdapter(adap_bebida_plato);
                    sp_vasos.setAdapter(adap_cantidad);

                }
                else {
                    sw_almuerzo.setThumbTintList(ColorStateList.valueOf(GRIS_REF));
                    sw_almuerzo.setTrackTintList(ColorStateList.valueOf(GRIS_REF));
                    sp_plato.setAdapter(adap_vacio);
                    sp_porcion1.setAdapter(adap_vacio);
                    sp_guarnicion.setAdapter(adap_vacio);
                    sp_porcion2.setAdapter(adap_vacio);
                    sp_bebida.setAdapter(adap_vacio);
                    sp_vasos.setAdapter(adap_vacio);
                }
            }
        });

        sw_cena.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if(on) {
                    sw_cena.setThumbTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_cena.setTrackTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_desayuno.setChecked(false);
                    sw_almuerzo.setChecked(false);
                    sw_colacion.setChecked(false);
                    sp_plato.setAdapter(adap_plato);
                    sp_porcion1.setAdapter(adap_porcion);
                    sp_guarnicion.setAdapter(adap_guarnicion);
                    sp_porcion2.setAdapter(adap_porcion);
                    sp_bebida.setAdapter(adap_bebida_plato);
                    sp_vasos.setAdapter(adap_cantidad);
                }
                else {
                    sw_cena.setThumbTintList(ColorStateList.valueOf(GRIS_REF));
                    sw_cena.setTrackTintList(ColorStateList.valueOf(GRIS_REF));
                    sp_plato.setAdapter(adap_vacio);
                    sp_porcion1.setAdapter(adap_vacio);
                    sp_guarnicion.setAdapter(adap_vacio);
                    sp_porcion2.setAdapter(adap_vacio);
                    sp_bebida.setAdapter(adap_vacio);
                    sp_vasos.setAdapter(adap_vacio);
                }
            }
        });

        sw_colacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if(on) {
                    sw_colacion.setThumbTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_colacion.setTrackTintList(ColorStateList.valueOf(VERDE_REF));
                    sw_desayuno.setChecked(false);
                    sw_almuerzo.setChecked(false);
                    sw_cena.setChecked(false);
                    sp_plato.setAdapter(adap_colacion);
                    sp_porcion1.setAdapter(adap_cantidad);
                    sp_guarnicion.setAdapter(adap_vacio);
                    sp_porcion2.setAdapter(adap_vacio);
                    sp_bebida.setAdapter(adap_vacio);
                    sp_vasos.setAdapter(adap_vacio);
                }
                else {
                    sw_colacion.setThumbTintList(ColorStateList.valueOf(GRIS_REF));
                    sw_colacion.setTrackTintList(ColorStateList.valueOf(GRIS_REF));
                    sp_plato.setAdapter(adap_vacio);
                    sp_porcion1.setAdapter(adap_vacio);
                }
            }


        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reload();
                //Limpiar la pantalla a mano
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Alimento a1,a2,a3;
                String id, nombre;
                int porcion;
                double calorias=0.0;

                ///el id de cada alimento estara conformador por 3 caracteres: el primero dice el numero de posicion en el spinner
                ///                                                            el segundo es la primera letra del alimento
                ///                                                            el tercero es la categoria que pertence, P="plato principal", G="guarnicion", B="bebida"


                ///reuno los datos del alimento "plato principal"

                id = Integer.toString(sp_plato.getSelectedItemPosition()) + sp_plato.getSelectedItem().toString().substring(0,1) + "P";
                nombre = sp_plato.getSelectedItem().toString();
                porcion = sp_porcion1.getSelectedItemPosition()+1;

                switch(id) {

                    case "0HP":
                        calorias=300.0*porcion;
                        break;

                    case "1MP":
                        calorias=100.0*porcion;
                        break;

                    case "2OP":
                        calorias=250*porcion;
                        break;

                    case "3TP":
                        calorias=220*porcion;
                        break;

                    case "0CP":
                        calorias=220*porcion;
                        break;

                    case "1EP":
                        calorias=220*porcion;
                        break;

                    case "2PP":
                        calorias=220*porcion;
                        break;

                    case "3PP":
                        calorias=220*porcion;
                        break;

                    case "0BP":
                        calorias=220*porcion;
                        break;

                    case "1BP":
                        calorias=220*porcion;
                        break;

                    case "2SP":
                        calorias=220*porcion;
                        break;

                    case "3YP":
                        calorias=220*porcion;
                        break;

                    case "4NP":
                        id=null;
                        break;

                    case "":
                        id=null;
                        break;
                }

                a1 = new Alimento(id,nombre,porcion,calorias);

                ///reuno los datos del alimento "guarnicion";

                id=Integer.toString(sp_guarnicion.getSelectedItemPosition()) + sp_guarnicion.getSelectedItem().toString().substring(0,1) + "G";
                nombre = sp_guarnicion.getSelectedItem().toString();
                porcion = sp_porcion2.getSelectedItemPosition()+1;

                switch(id) {

                    case "0AG":
                        calorias=220*porcion;
                        break;

                    case "1EG":
                        calorias=220*porcion;
                        break;

                    case "2PG":
                        calorias=220*porcion;
                        break;

                    case "3PG":
                        calorias=220*porcion;
                        break;

                    case "4NG":
                        id=null;
                        break;

                    case "":
                        id=null;
                        break;

                }

                a2 = new Alimento(id,nombre,porcion,calorias);

                id=Integer.toString(sp_bebida.getSelectedItemPosition()) + sp_bebida.getSelectedItem().toString().substring(0,1) + "B";
                nombre = sp_bebida.getSelectedItem().toString();
                porcion = sp_vasos.getSelectedItemPosition()+1;

                switch(id) {

                    case "0CB":
                        calorias=220*porcion;
                        break;

                    case "1CB":
                        calorias=220*porcion;
                        break;

                    case "2JB":
                        calorias=220*porcion;
                        break;

                    case "3TB":
                        calorias=220*porcion;
                        break;

                    case "0AB":
                        calorias=220*porcion;
                        break;

                    case "1AB":
                        calorias=220*porcion;
                        break;

                    case "2CB":
                        calorias=220*porcion;
                        break;

                    case "3GB":
                        calorias=220*porcion;
                        break;

                    case "4NB":
                        id=null;
                        break;

                    case "":
                        id=null;
                        break;
                }

                a3 = new Alimento(id,nombre,porcion,calorias);

                lbl_calorias.setText(Double.toString(a1.getCalorias()+a2.getCalorias()+a3.getCalorias()));

            }
        });
        return vista;
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
