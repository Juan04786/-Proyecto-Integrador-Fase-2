package com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int idPedido;
    private Usuario usuario;
    private EstadoPedido estado;
    private MetodoPago metodoPago;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaModificacion;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;
    private String direccionEntrega;
    private String notas;
    private boolean enviadoWhatsapp;
    private List<DetallePedido> detalles;

    public Pedido() {
        this.fechaPedido = LocalDateTime.now();
        this.subtotal = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.enviadoWhatsapp = false;
        this.detalles = new ArrayList<>();
    }

    public Pedido(int idPedido, Usuario usuario, EstadoPedido estado, MetodoPago metodoPago,
                  LocalDateTime fechaPedido, LocalDateTime fechaModificacion,
                  BigDecimal subtotal, BigDecimal descuento, BigDecimal total,
                  String direccionEntrega, String notas, boolean enviadoWhatsapp) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.fechaPedido = fechaPedido;
        this.fechaModificacion = fechaModificacion;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.direccionEntrega = direccionEntrega;
        this.notas = notas;
        this.enviadoWhatsapp = enviadoWhatsapp;
        this.detalles = new ArrayList<>();
    }

    public Pedido(Usuario usuario, EstadoPedido estado, String direccionEntrega, String notas) {
        this();
        this.usuario = usuario;
        this.estado = estado;
        this.direccionEntrega = direccionEntrega;
        this.notas = notas;
    }

    public void recalcularTotales() {
        this.subtotal = detalles.stream()
                .map(DetallePedido::getSubtotalLinea)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal.subtract(this.descuento == null ? BigDecimal.ZERO : this.descuento);
    }

    public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        recalcularTotales();
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public boolean isEnviadoWhatsapp() { return enviadoWhatsapp; }
    public void setEnviadoWhatsapp(boolean enviadoWhatsapp) { this.enviadoWhatsapp = enviadoWhatsapp; }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }

    @Override
    public String toString() {
        return "Pedido{idPedido=" + idPedido + ", usuario=" + usuario +
               ", estado=" + estado + ", total=" + total + "}";
    }
}