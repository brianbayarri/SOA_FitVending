package com.example.fitvending;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitvending.Datos.DBHandler;
import com.example.fitvending.Datos.HistoricoDAO;
import com.example.fitvending.Datos.UsuarioDAO;
import com.example.fitvending.entidades.Historico;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogrosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogrosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context contextoActual;
    private View vista;
    private OnFragmentInteractionListener mListener;

    public static final int VERDE_REF = Color.rgb(124, 213, 22);

    public LogrosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogrosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogrosFragment newInstance(String param1, String param2) {
        LogrosFragment fragment = new LogrosFragment();
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
        vista = inflater.inflate(R.layout.fragment_logros, container, false);
        contextoActual = inflater.getContext();

        final CheckedTextView logro1, logro2, logro3, logro4;
        final TextView moneda1,moneda2,moneda3,moneda4;

        logro1 = vista.findViewById(R.id.check_Logro1);
        logro2 = vista.findViewById(R.id.check_Logro2);
        logro3 = vista.findViewById(R.id.check_Logro3);
        logro4 = vista.findViewById(R.id.check_Logro4);

        //ValorMonedas
        moneda1 = vista.findViewById(R.id.lbl_MonedasGanadasLogro1);
        moneda2 = vista.findViewById(R.id.lbl_MonedasGanadasLogro2);
        moneda3 = vista.findViewById(R.id.lbl_MonedasGanadasLogro3);
        moneda4 = vista.findViewById(R.id.lbl_MonedasGanadasLogro4);

        //Si existe los usuarios se setea los datos del usuario

        final HistoricoDAO historicoDAO = new HistoricoDAO();

        //En este archivo tenemos el usuario guardado sin necesidad de pasar parametros
        MainActivity activity = (MainActivity) getActivity();
        SharedPreferences preferences = activity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String userName_sp = preferences.getString("UserName", "");

        DBHandler db = new DBHandler(container.getContext());
        if(historicoDAO.selectRowsByCondition(db,userName_sp,logro1.getText().toString()))
        {
            logro1.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
            logro1.setChecked(true);
        }
       db = new DBHandler(container.getContext());
        if(historicoDAO.selectRowsByCondition(db,userName_sp,logro2.getText().toString()))
        {
            logro2.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
            logro2.setChecked(true);
        }
        db = new DBHandler(container.getContext());
        if(historicoDAO.selectRowsByCondition(db,userName_sp,logro3.getText().toString()))
        {
            logro3.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
            logro3.setChecked(true);
        }
        db = new DBHandler(container.getContext());
        if(historicoDAO.selectRowsByCondition(db,userName_sp,logro4.getText().toString()))
        {
            logro4.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
            logro4.setChecked(true);
        }


        logro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///actualizar monedas del usuario
                logro1.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
                logro1.setChecked(true);

                updateMoneyYRegistrarHistorico(view.getContext(), moneda1.getText().toString(),logro1.getText().toString());
            }
        });

        logro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///actualizar monedas del usuario
                logro2.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
                logro2.setChecked(true);

                updateMoneyYRegistrarHistorico(view.getContext(), moneda2.getText().toString(),logro2.getText().toString());

            }
        });

        logro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///actualizar monedas del usuario
                logro3.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
                logro3.setChecked(true);

                updateMoneyYRegistrarHistorico(view.getContext(), moneda3.getText().toString(),logro3.getText().toString());
            }
        });

        logro4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///actualizar monedas del usuario
                logro4.setCheckMarkTintList(ColorStateList.valueOf(VERDE_REF));
                logro4.setChecked(true);

                updateMoneyYRegistrarHistorico(view.getContext(), moneda4.getText().toString(),logro4.getText().toString());

            }
        });

        return vista;
    }

    public void updateMoneyYRegistrarHistorico(Context context, String moneda, String logro)
    {
        //En este archivo tenemos el usuario guardado sin necesidad de pasar parametros
        MainActivity activity = (MainActivity) getActivity();
        SharedPreferences preferences = activity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String userName_sp = preferences.getString("UserName", "");
        DBHandler db = new DBHandler(context);
        UsuarioDAO userDao = new UsuarioDAO();
        userDao.actualizarMonedas(db,Integer.parseInt(moneda),userName_sp,0);

        db = new DBHandler(context);
        HistoricoDAO historicodao = new HistoricoDAO();
        Historico historico = new Historico();
        historico = historicodao.selectAllRows(db, userName_sp);

        historico.setNombreUsuario(userName_sp);
        historico.setLogro(logro);


        db = new DBHandler(context);
        historicodao.registrarHistorico(db,historico);
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
