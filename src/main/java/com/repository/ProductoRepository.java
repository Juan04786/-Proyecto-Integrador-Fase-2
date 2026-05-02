package com.repository;

import com.model.Categoria;
import com.model.Marca;
import com.model.Producto;
import com.util.Conexion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    private final Conexion conexion = Conexion.getInstancia();

    public List<Producto> findAll() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion_corta, p.descripcion_larga, " +
                     "p.precio, p.stock, p.peso, p.ruta_imagen, p.activo, " +
                     "p.fecha_creacion, p.fecha_modificacion, " +
                     "c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.activo AS cat_activo, " +
                     "m.id_marca, m.nombre AS mar_nombre, m.descripcion AS mar_desc, m.activo AS mar_activo " +
                     "FROM Productos p " +
                     "INNER JOIN Categorias c ON p.id_categoria = c.id_categoria " +
                     "INNER JOIN Marcas m ON p.id_marca = m.id_marca";

        try (Statement st = conexion.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Producto> findByCategoria(int idCategoria) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion_corta, p.descripcion_larga, " +
                     "p.precio, p.stock, p.peso, p.ruta_imagen, p.activo, " +
                     "p.fecha_creacion, p.fecha_modificacion, " +
                     "c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.activo AS cat_activo, " +
                     "m.id_marca, m.nombre AS mar_nombre, m.descripcion AS mar_desc, m.activo AS mar_activo " +
                     "FROM Productos p " +
                     "INNER JOIN Categorias c ON p.id_categoria = c.id_categoria " +
                     "INNER JOIN Marcas m ON p.id_marca = m.id_marca " +
                     "WHERE p.id_categoria = ?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public Producto findById(int idProducto) throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion_corta, p.descripcion_larga, " +
                     "p.precio, p.stock, p.peso, p.ruta_imagen, p.activo, " +
                     "p.fecha_creacion, p.fecha_modificacion, " +
                     "c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.activo AS cat_activo, " +
                     "m.id_marca, m.nombre AS mar_nombre, m.descripcion AS mar_desc, m.activo AS mar_activo " +
                     "FROM Productos p " +
                     "INNER JOIN Categorias c ON p.id_categoria = c.id_categoria " +
                     "INNER JOIN Marcas m ON p.id_marca = m.id_marca " +
                     "WHERE p.id_producto = ?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean save(Producto p) throws SQLException {
        String sql = "INSERT INTO Productos (nombre, descripcion_corta, descripcion_larga, " +
                     "precio, stock, peso, ruta_imagen, id_categoria, id_marca, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcionCorta());
            ps.setString(3, p.getDescripcionLarga());
            ps.setBigDecimal(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getPeso());
            ps.setString(7, p.getRutaImagen());
            ps.setInt(8, p.getCategoria().getIdCategoria());
            ps.setInt(9, p.getMarca().getIdMarca());
            ps.setBoolean(10, p.isActivo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Producto p) throws SQLException {
        String sql = "UPDATE Productos SET nombre=?, descripcion_corta=?, descripcion_larga=?, " +
                     "precio=?, stock=?, peso=?, ruta_imagen=?, id_categoria=?, id_marca=?, " +
                     "activo=?, fecha_modificacion=GETDATE() WHERE id_producto=?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcionCorta());
            ps.setString(3, p.getDescripcionLarga());
            ps.setBigDecimal(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getPeso());
            ps.setString(7, p.getRutaImagen());
            ps.setInt(8, p.getCategoria().getIdCategoria());
            ps.setInt(9, p.getMarca().getIdMarca());
            ps.setBoolean(10, p.isActivo());
            ps.setInt(11, p.getIdProducto());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idProducto) throws SQLException {
        String sql = "DELETE FROM Productos WHERE id_producto = ?";
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("cat_nombre"),
                rs.getString("cat_desc"),
                rs.getBoolean("cat_activo")
        );
        Marca marca = new Marca(
                rs.getInt("id_marca"),
                rs.getString("mar_nombre"),
                rs.getString("mar_desc"),
                rs.getBoolean("mar_activo")
        );
        Timestamp fechaMod = rs.getTimestamp("fecha_modificacion");
        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getString("descripcion_corta"),
                rs.getString("descripcion_larga"),
                rs.getBigDecimal("precio"),
                rs.getInt("stock"),
                rs.getString("peso"),
                rs.getString("ruta_imagen"),
                cat,
                marca,
                rs.getBoolean("activo"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                fechaMod != null ? fechaMod.toLocalDateTime() : null
        );
    }
}