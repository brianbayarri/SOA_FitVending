package com.example.fitvending.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FitVending.db";

    //SQLiteDatabase.CursorFactory factory
    public DBHandler(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioDAO.CREATE_USUARIO_TABLE);
        db.execSQL(HistoricoDAO.CREATE_HISTORICO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioDAO.DELETE_USUARIO_TABLE);
        db.execSQL(HistoricoDAO.DELETE_HISTORICO_TABLE);

        db.execSQL(UsuarioDAO.CREATE_USUARIO_TABLE);
        db.execSQL(HistoricoDAO.CREATE_HISTORICO_TABLE);
    }

    public Context getContext() {
        return context;
    }
}
