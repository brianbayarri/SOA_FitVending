package com.example.fitvending;

public class Rutina {

    private String id;
    private String nombre;
    private int minutos;
    private double calorias;

    public Rutina(String id, String nombre, int minutos, double calorias) {
        this.id = id;
        this.nombre = nombre;
        this.minutos = minutos;
        this.calorias = calorias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }
}
