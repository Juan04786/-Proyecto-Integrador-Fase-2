package com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcionCorta;
    private String descripcionLarga;
    private BigDecimal precio;
    private int stock;
    private String peso;
    private String rutaImagen;
    private Categoria categoria;
    private Marca marca;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public Producto() {
        this.activo = true;
        this.stock = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Producto(int idProducto, String nombre, String descripcionCorta, String descripcionLarga,
                    BigDecimal precio, int stock, String peso, String rutaImagen,
                    Categoria categoria, Marca marca, boolean activo,
                    LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcionCorta = descripcionCorta;
        this.descripcionLarga = descripcionLarga;
        this.precio = precio;
        this.stock = stock;
        this.peso = peso;
        this.rutaImagen = rutaImagen;
        this.categoria = categoria;
        this.marca = marca;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Producto(String nombre, String descripcionCorta, String descripcionLarga,
                    BigDecimal precio, int stock, String peso, String rutaImagen,
                    Categoria categoria, Marca marca) {
        this();
        this.nombre = nombre;
        this.descripcionCorta = descripcionCorta;
        this.descripcionLarga = descripcionLarga;
        this.precio = precio;
        this.stock = stock;
        this.peso = peso;
        this.rutaImagen = rutaImagen;
        this.categoria = categoria;
        this.marca = marca;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcionCorta() { return descripcionCorta; }
    public void setDescripcionCorta(String descripcionCorta) { this.descripcionCorta = descripcionCorta; }

    public String getDescripcionLarga() { return descripcionLarga; }
    public void setDescripcionLarga(String descripcionLarga) { this.descripcionLarga = descripcionLarga; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return "Producto{idProducto=" + idProducto + ", nombre='" + nombre +
               "', precio=" + precio + ", stock=" + stock + "}";
    }
}