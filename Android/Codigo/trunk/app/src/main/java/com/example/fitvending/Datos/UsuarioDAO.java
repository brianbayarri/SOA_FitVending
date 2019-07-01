package com.example.fitvending.Datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.fitvending.entidades.Historico;
import com.example.fitvending.entidades.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UsuarioDAO {

    private static final String TABLE_NAME = "usuario";
    private static final String NOMBRE = "nombreUsuario";
    private static final String PASSWORD = "password";
    private static final String ALTURA = "altura";
    private static final String EDAD = "edad";
    private static final String PESO = "peso";
    private static final String SEXO = "sexo";
    private static final String MONEDAS = "monedas";
    private static final String EJERCICIO = "ejercicio";
    private static final String CALORIAS = "calorias";
    private static final String FECHAHORA = "fechahora";

    public static final String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + NOMBRE +
            " TEXT PRIMARY KEY, " + PASSWORD + " TEXT, " + ALTURA + " DOUBLE, " + EDAD + " INTEGER, " +
            PESO + " DOUBLE, " + SEXO + " TEXT, " + MONEDAS +
            " INTEGER, " + EJERCICIO + " INTEGER, " + CALORIAS + " DOUBLE, " + FECHAHORA + " STRING )";

    public static final String DELETE_USUARIO_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public boolean registrarUsuario(DBHandler dbHandler, Usuario user) {
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = -1;

        values.put(NOMBRE, user.getNombreUsuario());
        values.put(PASSWORD, user.getPassword());
        values.put(ALTURA, 0);
        values.put(EDAD, 0);
        values.put(PESO, 0);
        values.put(SEXO, "H");
        values.put(MONEDAS, 0);
        values.put(EJERCICIO, 0);
        values.put(CALORIAS, 0);
        values.put(FECHAHORA, getCurrentDate());

        id = database.insert(TABLE_NAME, null, values);
        if (id >= 0) {
            Toast.makeText(dbHandler.getContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            database.close();
            return true;
        }
        database.close();
        Toast.makeText(dbHandler.getContext(), "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
        return false;
    }

    public int checkIfRecordExist(DBHandler dbHandler, String userValue, String passValue) {
        SQLiteDatabase objDatabase = dbHandler.getWritableDatabase();
        try {

            Cursor cursor = objDatabase.rawQuery("SELECT " + NOMBRE + " FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userValue + "'", null);
            if (cursor.moveToFirst()) {
                cursor = objDatabase.rawQuery("SELECT " + PASSWORD + " FROM " + TABLE_NAME + " WHERE " + PASSWORD + "='" + passValue + "'", null);
                if (cursor.moveToFirst()) {
                    actualizarCalorias(dbHandler,0.0,userValue,2);
                    objDatabase.close();
                    return 0;
                }
                objDatabase.close();
                return 1;

            }

            objDatabase.close();
            return 1;
        } catch (Exception errorException) {
            objDatabase.close();
            return 2;
        }
    }

    //Chequea si el usuario existe antes de crearlo
    public boolean checkIfUserExist(DBHandler dbHandler, String userValue) {
        SQLiteDatabase objDatabase = dbHandler.getReadableDatabase();
        try {

            Cursor cursor = objDatabase.rawQuery("SELECT " + NOMBRE + " FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userValue + "'", null);
            if (cursor.moveToFirst()) {

                objDatabase.close();
                return true;

            }

            objDatabase.close();
            return false;
        } catch (Exception errorException) {
            objDatabase.close();
            return false;
        }

    }

    public Usuario selectAllRows(DBHandler dbHandler, String userValue) {
        Usuario user = new Usuario();
        SQLiteDatabase objDatabase = dbHandler.getReadableDatabase();
        try {

            Cursor cursor = objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userValue + "'", null);

            if (cursor.moveToFirst()) {
                user.setAltura(cursor.getDouble(cursor.getColumnIndex(ALTURA)));
                user.setEdad(cursor.getInt(cursor.getColumnIndex(EDAD)));
                user.setPeso(cursor.getDouble(cursor.getColumnIndex(PESO)));
                user.setSexo(cursor.getString(cursor.getColumnIndex(SEXO)));
                user.setMoneda(cursor.getInt(cursor.getColumnIndex(MONEDAS)));
                user.setCalorias(cursor.getDouble(cursor.getColumnIndex(CALORIAS)));
                user.setEjercicio(cursor.getInt(cursor.getColumnIndex(EJERCICIO)));
            }
            objDatabase.close();
            return user;
        } catch (Exception errorException) {
            objDatabase.close();
            return null;
        }

    }

    public boolean actualizarUsuario(DBHandler dbHandler, Usuario user) {
        try {
            SQLiteDatabase database = dbHandler.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(ALTURA, user.getAltura());
            newValues.put(EDAD, user.getEdad());
            newValues.put(PESO, user.getPeso());
            newValues.put(SEXO, user.getSexo());
            newValues.put(EJERCICIO, user.getEjercicio());
            newValues.put(CALORIAS, user.getCalorias());


            int error = database.update(TABLE_NAME, newValues, NOMBRE + "='" + user.getNombreUsuario() + "'", null);

            if (error != 0) {
                Toast.makeText(dbHandler.getContext(), "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show();
                database.close();
                return true;
            }
            database.close();
            Toast.makeText(dbHandler.getContext(), "No se pudo actualizar el usuario", Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            Toast.makeText(dbHandler.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return  false;
        }

    }

    public boolean actualizarCalorias(DBHandler dbHandler, double cal, String userName, int i) {
        try {
            SQLiteDatabase database = dbHandler.getWritableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userName + "'", null);
            double cal_total=0.0;
            if (cursor.moveToFirst()) {
                if((cursor.getString(cursor.getColumnIndex(FECHAHORA))).equals(getCurrentDate()))
                {
                    cal_total = cursor.getDouble(cursor.getColumnIndex(CALORIAS));
                }
                else
                {
                    cal_total = 0.0;
                }

            }
            switch(i)
            {
                case 0:
                    cal_total+=cal;
                    break;
                case 1:
                    cal_total-=cal;
                    break;
                    default:
                        break;
            }

            ContentValues newValues = new ContentValues();
            newValues.put(CALORIAS, cal_total);
            newValues.put(FECHAHORA, getCurrentDate());
            int error = database.update(TABLE_NAME, newValues, NOMBRE + "='" + userName + "'", null);

            if (error != 0) {
                Toast.makeText(dbHandler.getContext(), "Calorías actualizadas", Toast.LENGTH_SHORT).show();

                if(i < 2)
                {
                    Historico historico = new Historico();
                    historico.setLogro("");
                    historico.setCalorias(cal_total);
                    historico.setNombreUsuario(userName);

                    //Registar Historico
                    HistoricoDAO historicoDAO = new HistoricoDAO();
                    historicoDAO.registrarHistorico(dbHandler,historico);
                }


                database.close();
                return true;
            }
            database.close();
            Toast.makeText(dbHandler.getContext(), "No se pudo actualizar las calorías", Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            Toast.makeText(dbHandler.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return  false;
        }

    }
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd ");
        String strDate =  mdformat.format(calendar.getTime());
        return strDate;
    }

    public boolean actualizarMonedas(DBHandler dbHandler,  int money, String userName,int i) {
        try {
            SQLiteDatabase database = dbHandler.getWritableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userName + "'", null);
            int moneda=0;
            if (cursor.moveToFirst()) {
                moneda = cursor.getInt(cursor.getColumnIndex(MONEDAS));
            }
            switch(i)
            {
                case 0:
                    moneda+=money;
                    break;
                case 1:
                    moneda-=money;
                    break;
                default:
                    break;
            }

            ContentValues newValues = new ContentValues();

            newValues.put(MONEDAS, moneda);
            int error = database.update(TABLE_NAME, newValues, NOMBRE + "='" + userName + "'", null);

            if (error != 0) {
                Toast.makeText(dbHandler.getContext(), "Monedas actualizadas", Toast.LENGTH_SHORT).show();
                database.close();
                return true;
            }
            database.close();
            Toast.makeText(dbHandler.getContext(), "No se pudo actualizar las Monedas", Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            Toast.makeText(dbHandler.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return  false;
        }

    }
}


