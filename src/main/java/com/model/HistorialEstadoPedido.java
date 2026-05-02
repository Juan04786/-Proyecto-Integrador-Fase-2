package com.model;

import java.time.LocalDateTime;

public class HistorialEstadoPedido {

    private int idHistorial;
    private int idPedido;
    private EstadoPedido estado;
    private Usuario usuarioResponsable;
    private LocalDateTime fechaCambio;
    private String observacion;

    public HistorialEstadoPedido() {
        this.fechaCambio = LocalDateTime.now();
    }

    public HistorialEstadoPedido(int idHistorial, int idPedido, EstadoPedido estado,
                                  Usuario usuarioResponsable, LocalDateTime fechaCambio,
                                  String observacion) {
        this.idHistorial = idHistorial;
        this.idPedido = idPedido;
        this.estado = estado;
        this.usuarioResponsable = usuarioResponsable;
        this.fechaCambio = fechaCambio;
        this.observacion = observacion;
    }

    public HistorialEstadoPedido(int idPedido, EstadoPedido estado,
                                  Usuario usuarioResponsable, String observacion) {
        this();
        this.idPedido = idPedido;
        this.estado = estado;
        this.usuarioResponsable = usuarioResponsable;
        this.observacion = observacion;
    }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public Usuario getUsuarioResponsable() { return usuarioResponsable; }
    public void setUsuarioResponsable(Usuario usuarioResponsable) { this.usuarioResponsable = usuarioResponsable; }

    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    @Override
    public String toString() {
        return "HistorialEstadoPedido{idHistorial=" + idHistorial + ", idPedido=" + idPedido +
               ", estado=" + estado + ", fechaCambio=" + fechaCambio + "}";
    }
}