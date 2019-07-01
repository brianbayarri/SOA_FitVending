package com.example.fitvending;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitvending.Datos.DBHandler;
import com.example.fitvending.Datos.UsuarioDAO;
import com.example.fitvending.entidades.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context contextoActual;
    private View vista;
    private BluetoothAdapter btAdapter = null;

    Button Producto1,Producto2,Producto3,Producto4;
    TextView sinStocklblP1,sinStocklblP2,sinStocklblP3,sinStocklblP4,txtMonedas, txtCalorias;
    private boolean stockP1,stockP2,stockP3,stockP4;
    String userName;

    public String colorSinStock = "#D31E1F29";
    public String colorHayStock = "#FFFFFF";
    private String stockDisp;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_main, container, false);
        contextoActual = inflater.getContext();
        //Se parsea el stock recibido desde Arduino.

        if(BtConnectionService.Stock == null)
            parsearStock("1-0-1-1");
        else
            parsearStock(BtConnectionService.Stock);
        MainActivity activity = (MainActivity) getActivity();
        userName = activity.getUserNameByFragment();

        sinStocklblP1 = vista.findViewById(R.id.lbl_sinStockP1);
        sinStocklblP2 = vista.findViewById(R.id.lbl_sinStockP2);
        sinStocklblP3 = vista.findViewById(R.id.lbl_sinStockP3);
        sinStocklblP4 = vista.findViewById(R.id.lbl_sinStockP4);

        Producto1 = vista.findViewById(R.id.btn_imgChocoarroz);
        Producto2 = vista.findViewById(R.id.btn_imgCereal);
        Producto3 = vista.findViewById(R.id.btn_imgFrutos);
        Producto4 = vista.findViewById(R.id.btn_imgAlfajor);


        txtMonedas = vista.findViewById(R.id.txt_money);
        txtCalorias =  vista.findViewById(R.id.lbl_CaloriasNum_P);

        Usuario datos= new Usuario();
        datos = selectData(container.getContext());
        if(datos != null)
        {
            txtMonedas.setText(String.valueOf(datos.getMoneda()));
            txtCalorias.setText(String.valueOf(new DecimalFormat("#.##").format(datos.getCalorias())));
        }
        else
        {
            txtMonedas.setText(String.valueOf(0));
            txtCalorias.setText(String.valueOf(0.0));
        }



        if(stockP1)
            sinStocklblP1.setBackgroundColor(Color.TRANSPARENT);
        else
            sinStocklblP1.setBackgroundColor(Color.parseColor(colorSinStock));

        if(stockP2)
            sinStocklblP2.setBackgroundColor(Color.TRANSPARENT);
        else
            sinStocklblP2.setBackgroundColor(Color.parseColor(colorSinStock));


        if(stockP3)
            sinStocklblP3.setBackgroundColor(Color.TRANSPARENT);
        else
            sinStocklblP3.setBackgroundColor(Color.parseColor(colorSinStock));


        if(stockP4)
            sinStocklblP4.setBackgroundColor(Color.TRANSPARENT);
        else
            sinStocklblP4.setBackgroundColor(Color.parseColor(colorSinStock));


        Producto1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                DBHandler db;
                db = new DBHandler(container.getContext());
                UsuarioDAO userDao = new UsuarioDAO();
                Usuario infoUser = new Usuario();

                infoUser = userDao.selectAllRows(db,userName);

                if(infoUser.getEdad()!=0) {

                    if (stockP1) {
                        if (infoUser.getCalorias() <= 99) {

                            userDao.actualizarCalorias(db, 99, userName, 0);
                            BtConnectionService.enviarDatosAArduino("1");
                            Toast.makeText(getContext(), "Calorias actualizadas", Toast.LENGTH_LONG).show();
                            //Me dirijo a la pantalla de exito
                            Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                            startActivity(intent);
                        } else if (infoUser.getMoneda() >= 30) {
                            userDao.actualizarMonedas(db, 30, userName, 1);
                            BtConnectionService.enviarDatosAArduino("1");
                            Toast.makeText(getContext(), "Monedas actualizadas", Toast.LENGTH_LONG).show();
                            //Me dirijo a la pantalla de exito
                            Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getContext(), "No puedes obtener el producto, quema calorias o consigue monedas!", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getContext(), "No hay stock de este producto", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "Complete su perfil antes de seleccionar un producto", Toast.LENGTH_LONG).show();
            }
        });

        Producto2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(stockP2) {

                    //Me dirijo a la pantalla de exito
                    BtConnectionService.consultarStock();
                    Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                    intent.putExtra("UserName",userName);
                    startActivity(intent);
                    //Toast.makeText(getContext(),BtConnectionService.Stock,Toast.LENGTH_LONG).show();
                     //BtConnectionService.enviarDatosAArduino("2");
                }
                else
                    Toast.makeText(getContext(),"No hay stock de este producto",Toast.LENGTH_LONG).show();

            }
        });

        Producto3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHandler db;
                db = new DBHandler(container.getContext());
                UsuarioDAO userDao = new UsuarioDAO();
                Usuario infoUser = new Usuario();

                infoUser = userDao.selectAllRows(db, userName);

                if (infoUser.getEdad() != 0) {

                    if (stockP3) {
                        if (infoUser.getCalorias() <= 99) {

                            userDao.actualizarCalorias(db, 99, userName, 0);
                            BtConnectionService.enviarDatosAArduino("3");
                            Toast.makeText(getContext(), "Calorias actualizadas", Toast.LENGTH_LONG).show();
                            //Me dirijo a la pantalla de exito
                            Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                            startActivity(intent);
                        } else if (infoUser.getMoneda() >= 40) {
                            userDao.actualizarMonedas(db, 40, userName, 1);
                            BtConnectionService.enviarDatosAArduino("3");
                            Toast.makeText(getContext(), "Monedas actualizadas", Toast.LENGTH_LONG).show();
                            //Me dirijo a la pantalla de exito
                            Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getContext(), "No puedes obtener el producto, quema calorias o consigue monedas!", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getContext(), "No hay stock de este producto", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "Complete su perfil antes de seleccionar un producto", Toast.LENGTH_LONG).show();
            }
        });

        Producto4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHandler db;
                db = new DBHandler(container.getContext());
                UsuarioDAO userDao = new UsuarioDAO();
                Usuario infoUser = new Usuario();

                infoUser = userDao.selectAllRows(db, userName);

                if (infoUser.getEdad() != 0) {

                    if (stockP4) {
                        if (infoUser.getCalorias() <= 99) {

                            userDao.actualizarCalorias(db, 99, userName, 0);
                            BtConnectionService.enviarDatosAArduino("4");
                            Toast.makeText(getContext(), "Calorias actualizadas", Toast.LENGTH_LONG).show();
                            //Me dirijo a la pantalla de exito
                            Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                            startActivity(intent);
                        } else if (infoUser.getMoneda() >= 50) {
                            userDao.actualizarMonedas(db, 50, userName, 1);
                            BtConnectionService.enviarDatosAArduino("4");
                            Toast.makeText(getContext(), "Monedas actualizadas", Toast.LENGTH_LONG).show();
                            //Me dirijo a la pantalla de exito
                            Intent intent = new Intent(getActivity(),SuccessRetiroActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getContext(), "No puedes obtener el producto, quema calorias o consigue monedas!", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getContext(), "No hay stock de este producto", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "Complete su perfil antes de seleccionar un producto", Toast.LENGTH_LONG).show();
            }
        });


        return vista;
    }

    public Usuario selectData(Context context)
    {
        try {
            //En este archivo tenemos el usuario guardado sin necesidad de pasar parametros
            MainActivity activity = (MainActivity) getActivity();
            SharedPreferences preferences = activity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            String userName_sp = preferences.getString("UserName", "");
            DBHandler db = new DBHandler(context);
            UsuarioDAO userDao = new UsuarioDAO();
            Usuario info = new Usuario();
            info = userDao.selectAllRows(db, userName_sp);
            if (info != null) {
                return info;
            }
        }catch(Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return null;

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



    public void parsearStock(String stock){

        String DstockP1,DstockP2,DstockP3,DstockP4;

        String[] parse = stock.split("-");

        DstockP1 = parse[0];
        DstockP2 = parse[1];
        DstockP3 = parse[2];
        DstockP4 = parse[3];

        if(DstockP1.equals("0"))
            stockP1 = true;
        else
            stockP1=false;

        if(DstockP2.equals("0"))
            stockP2 = true;
        else
            stockP2=false;

        if(DstockP3.equals("0"))
            stockP3 = true;
        else
            stockP3=false;

        if(DstockP4.equals("0"))
            stockP4 = true;
        else
            stockP4=false;

    }

}
