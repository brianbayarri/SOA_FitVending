package com.example.fitvending.Datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.fitvending.entidades.Historico;
import com.example.fitvending.entidades.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoricoDAO {
    private static final String TABLE_NAME = "historico";
    private static final String NOMBRE = "nombreUsuario";
    private static final String LOGRO = "logro";
    private static final String CALORIAS = "calorias";
    private static final String FECHAHORA = "fechahora";
    private static final String ID = "ID";

    public static final String CREATE_HISTORICO_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" +  NOMBRE +
            " TEXT, " + LOGRO + " TEXT, " +  CALORIAS + " DOUBLE, " + FECHAHORA + " TEXT )";

    public static final String DELETE_HISTORICO_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public boolean registrarHistorico(DBHandler dbHandler, Historico historico) {
        try {
            SQLiteDatabase database = dbHandler.getWritableDatabase();
            ContentValues values = new ContentValues();

            long id = -1;

            values.put(CALORIAS, historico.getCalorias());

            values.put(LOGRO, historico.getLogro());

            values.put(NOMBRE, historico.getNombreUsuario());
            values.put(FECHAHORA, getCurrentDateTime());

            id = database.insert(TABLE_NAME, null, values);
            if (id >= 0) {

                database.close();
                return true;
            }
            database.close();
            return false;
        }catch(Exception e)
        {
            Toast.makeText(dbHandler.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public Historico selectAllRows(DBHandler dbHandler, String userValue) {
        Historico historico = new Historico();
        SQLiteDatabase objDatabase = dbHandler.getReadableDatabase();
        try {

            Cursor cursor = objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userValue + "'", null);

            if (cursor.moveToFirst()) {
                historico.setLogro(cursor.getString(cursor.getColumnIndex(LOGRO)));
                historico.setCalorias(cursor.getDouble(cursor.getColumnIndex(CALORIAS)));
                historico.setDate(cursor.getString(cursor.getColumnIndex(FECHAHORA)));
            }
            objDatabase.close();
            return historico;
        } catch (Exception errorException) {
            objDatabase.close();
            return null;
        }

    }

    public boolean selectRowsByCondition(DBHandler dbHandler, String userValue, String logro) {

        SQLiteDatabase objDatabase = dbHandler.getReadableDatabase();
        try {
            String fecha = getCurrentDateTime();
            Cursor cursor = objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE + "='" + userValue + "' AND " + LOGRO + "='" + logro + "' AND " + FECHAHORA + "='" + fecha + "'  ", null);

            if (cursor.moveToFirst()) {
                objDatabase.close();
                return true;
            }

            return false;
        } catch (Exception errorException) {
            objDatabase.close();
            return false;
        }

    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd ");
        String strDate =  mdformat.format(calendar.getTime());
        return strDate;
    }
}
