package com.example.fitvending.entidades;

import java.util.Date;

public class Historico {
    private String nombreUsuario;
    private String  logro;
    private double calorias;
    private String date;

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLogro(String logro) {
        this.logro = logro;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public double getCalorias() {
        return calorias;
    }

    public String getDate() {
        return date;
    }

    public String getLogro() {
        return logro;
    }
}
