package com.example.fitvending;

public class Alimento {

    private String id;
    private String nombre;
    private int porcion;
    private double calorias;

    public Alimento(String id, String nombre, int porcion, double calorias) {
        this.id = id;
        this.nombre = nombre;
        this.porcion = porcion;
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

    public int getPorcion() {
        return porcion;
    }

    public void setPorcion(int porcion) {
        this.porcion = porcion;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }
}
