package com.model;

public class MetodoPago {

    private int idMetodo;
    private String nombre;
    private boolean activo;

    public MetodoPago() {
        this.activo = true;
    }

    public MetodoPago(int idMetodo, String nombre, boolean activo) {
        this.idMetodo = idMetodo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public MetodoPago(String nombre) {
        this();
        this.nombre = nombre;
    }

    public int getIdMetodo() { return idMetodo; }
    public void setIdMetodo(int idMetodo) { this.idMetodo = idMetodo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "MetodoPago{idMetodo=" + idMetodo + ", nombre='" + nombre + "'}";
    }
}