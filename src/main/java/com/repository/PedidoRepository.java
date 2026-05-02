package com.repository;

import com.model.*;
import com.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    private final Conexion conexion = Conexion.getInstancia();
private final ProductoRepository productoRepo = new ProductoRepository();
    public List<Pedido> findAll() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id_pedido, p.subtotal, p.descuento, p.total, " +
                     "p.direccion_entrega, p.notas, p.enviado_whatsapp, " +
                     "p.fecha_pedido, p.fecha_modificacion, " +
                     "u.id_usuario, u.username, u.nombre AS usu_nombre, u.email, " +
                     "u.telefono, u.direccion AS usu_dir, u.activo AS usu_activo, " +
                     "u.intentos_fallidos, u.bloqueado, u.fecha_creacion AS usu_fc, " +
                     "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_desc, " +
                     "e.id_estado, e.nombre AS est_nombre, e.descripcion AS est_desc, " +
                     "m.id_metodo, m.nombre AS met_nombre, m.activo AS met_activo " +
                     "FROM Pedidos p " +
                     "INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario " +
                     "INNER JOIN Roles r ON u.id_rol = r.id_rol " +
                     "INNER JOIN EstadosPedido e ON p.id_estado = e.id_estado " +
                     "LEFT JOIN MetodosPago m ON p.id_metodo_pago = m.id_metodo";

        try (Statement st = conexion.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Pedido findById(int idPedido) throws SQLException {
        String sql = "SELECT p.id_pedido, p.subtotal, p.descuento, p.total, " +
                     "p.direccion_entrega, p.notas, p.enviado_whatsapp, " +
                     "p.fecha_pedido, p.fecha_modificacion, " +
                     "u.id_usuario, u.username, u.nombre AS usu_nombre, u.email, " +
                     "u.telefono, u.direccion AS usu_dir, u.activo AS usu_activo, " +
                     "u.intentos_fallidos, u.bloqueado, u.fecha_creacion AS usu_fc, " +
                     "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_desc, " +
                     "e.id_estado, e.nombre AS est_nombre, e.descripcion AS est_desc, " +
                     "m.id_metodo, m.nombre AS met_nombre, m.activo AS met_activo " +
                     "FROM Pedidos p " +
                     "INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario " +
                     "INNER JOIN Roles r ON u.id_rol = r.id_rol " +
                     "INNER JOIN EstadosPedido e ON p.id_estado = e.id_estado " +
                     "LEFT JOIN MetodosPago m ON p.id_metodo_pago = m.id_metodo " +
                     "WHERE p.id_pedido = ?";

        Pedido pedido = null;
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedido = mapear(rs);
                }
            }
        }
        if (pedido != null) {
            pedido.setDetalles(findDetallesByPedido(idPedido));
        }
        return pedido;
    }

    public List<DetallePedido> findDetallesByPedido(int idPedido) throws SQLException {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT dp.id_detalle, dp.id_pedido, dp.cantidad, dp.precio_unitario, " +
                     "dp.id_producto FROM DetallePedido dp WHERE dp.id_pedido = ?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = productoRepo.findById(rs.getInt("id_producto"));
                    DetallePedido det = new DetallePedido(
                            rs.getInt("id_detalle"),
                            rs.getInt("id_pedido"),
                            producto,
                            rs.getInt("cantidad"),
                            rs.getBigDecimal("precio_unitario")
                    );
                    detalles.add(det);
                }
            }
        }
        return detalles;
    }

    public int save(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedidos (id_usuario, id_estado, id_metodo_pago, " +
                     "direccion_entrega, notas, enviado_whatsapp) " +
                     "VALUES (?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";

        int idGenerado = -1;
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, pedido.getUsuario().getIdUsuario());
            ps.setInt(2, pedido.getEstado().getIdEstado());
            if (pedido.getMetodoPago() != null) {
                ps.setInt(3, pedido.getMetodoPago().getIdMetodo());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, pedido.getDireccionEntrega());
            ps.setString(5, pedido.getNotas());
            ps.setBoolean(6, pedido.isEnviadoWhatsapp());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    pedido.setIdPedido(idGenerado);
                }
            }
        }

        if (idGenerado > 0 && pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                saveDetalle(idGenerado, det);
            }
        }
        return idGenerado;
    }

    private void saveDetalle(int idPedido, DetallePedido det) throws SQLException {
        String sql = "INSERT INTO DetallePedido (id_pedido, id_producto, cantidad, precio_unitario) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.setInt(2, det.getProducto().getIdProducto());
            ps.setInt(3, det.getCantidad());
            ps.setBigDecimal(4, det.getPrecioUnitario());
            ps.executeUpdate();
        }
    }

    public boolean updateEstado(int idPedido, int idEstado) throws SQLException {
        String sql = "UPDATE Pedidos SET id_estado = ?, fecha_modificacion = GETDATE() WHERE id_pedido = ?";
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idEstado);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idPedido) throws SQLException {
        String sqlDet = "DELETE FROM DetallePedido WHERE id_pedido = ?";
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sqlDet)) {
            ps.setInt(1, idPedido);
            ps.executeUpdate();
        }
        String sqlPed = "DELETE FROM Pedidos WHERE id_pedido = ?";
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sqlPed)) {
            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
        }
    }

    private Pedido mapear(ResultSet rs) throws SQLException {
        Rol rol = new Rol(
                rs.getInt("id_rol"),
                rs.getString("rol_nombre"),
                rs.getString("rol_desc")
        );
        Usuario usuario = new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("username"),
                null,
                rs.getString("usu_nombre"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("usu_dir"),
                rol,
                rs.getBoolean("usu_activo"),
                rs.getInt("intentos_fallidos"),
                rs.getBoolean("bloqueado"),
                rs.getTimestamp("usu_fc").toLocalDateTime(),
                null
        );
        EstadoPedido estado = new EstadoPedido(
                rs.getInt("id_estado"),
                rs.getString("est_nombre"),
                rs.getString("est_desc")
        );
        MetodoPago metodo = null;
        int idMetodo = rs.getInt("id_metodo");
        if (!rs.wasNull()) {
            metodo = new MetodoPago(idMetodo, rs.getString("met_nombre"), rs.getBoolean("met_activo"));
        }
        Timestamp fechaMod = rs.getTimestamp("fecha_modificacion");
        return new Pedido(
                rs.getInt("id_pedido"),
                usuario,
                estado,
                metodo,
                rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                fechaMod != null ? fechaMod.toLocalDateTime() : null,
                rs.getBigDecimal("subtotal"),
                rs.getBigDecimal("descuento"),
                rs.getBigDecimal("total"),
                rs.getString("direccion_entrega"),
                rs.getString("notas"),
                rs.getBoolean("enviado_whatsapp")
        );
    }
}