package com.model;

import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private String username;
    private String claveHash;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Rol rol;
    private boolean activo;
    private int intentosFallidos;
    private boolean bloqueado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public Usuario() {
        this.activo = true;
        this.intentosFallidos = 0;
        this.bloqueado = false;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Usuario(int idUsuario, String username, String claveHash, String nombre,
                   String email, String telefono, String direccion, Rol rol,
                   boolean activo, int intentosFallidos, boolean bloqueado,
                   LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.claveHash = claveHash;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
        this.activo = activo;
        this.intentosFallidos = intentosFallidos;
        this.bloqueado = bloqueado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Usuario(String username, String claveHash, String nombre,
                   String email, String telefono, String direccion, Rol rol) {
        this();
        this.username = username;
        this.claveHash = claveHash;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getClaveHash() { return claveHash; }
    public void setClaveHash(String claveHash) { this.claveHash = claveHash; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario + ", username='" + username + "', rol=" + rol + "}";
    }
}