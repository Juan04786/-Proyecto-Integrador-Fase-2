package com.model;

import java.math.BigDecimal;

public class DetallePedido {

    private int idDetalle;
    private int idPedido;
    private Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;

    public DetallePedido() {}

    public DetallePedido(int idDetalle, int idPedido, Producto producto,
                         int cantidad, BigDecimal precioUnitario) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public DetallePedido(int idPedido, Producto producto, int cantidad, BigDecimal precioUnitario) {
        this.idPedido = idPedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotalLinea() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    @Override
    public String toString() {
        return "DetallePedido{idDetalle=" + idDetalle + ", producto=" + producto +
               ", cantidad=" + cantidad + ", subtotal=" + getSubtotalLinea() + "}";
    }
}
