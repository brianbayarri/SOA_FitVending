package com.example.fitvending.entidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioDAO.CREATE_USUARIO_TABLE);
        //Agregar el resto de tablas
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioDAO.CREATE_USUARIO_TABLE);
        //Agregar el resto de tablas
        onCreate(db);
    }

    public Context getContext() {
        return context;
    }
}
