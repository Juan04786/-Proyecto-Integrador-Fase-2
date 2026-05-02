package com.repository;

import com.model.Rol;
import com.model.Usuario;
import com.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private final Conexion conexion = Conexion.getInstancia();

    public List<Usuario> findAll() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.username, u.clave_hash, u.nombre, u.email, " +
                     "u.telefono, u.direccion, u.activo, u.intentos_fallidos, u.bloqueado, " +
                     "u.fecha_creacion, u.fecha_modificacion, " +
                     "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_desc " +
                     "FROM Usuarios u INNER JOIN Roles r ON u.id_rol = r.id_rol";

        try (Statement st = conexion.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Usuario findById(int idUsuario) throws SQLException {
        String sql = "SELECT u.id_usuario, u.username, u.clave_hash, u.nombre, u.email, " +
                     "u.telefono, u.direccion, u.activo, u.intentos_fallidos, u.bloqueado, " +
                     "u.fecha_creacion, u.fecha_modificacion, " +
                     "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_desc " +
                     "FROM Usuarios u INNER JOIN Roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.id_usuario = ?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Usuario findByUsername(String username) throws SQLException {
        String sql = "SELECT u.id_usuario, u.username, u.clave_hash, u.nombre, u.email, " +
                     "u.telefono, u.direccion, u.activo, u.intentos_fallidos, u.bloqueado, " +
                     "u.fecha_creacion, u.fecha_modificacion, " +
                     "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_desc " +
                     "FROM Usuarios u INNER JOIN Roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.username = ?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean save(Usuario u) throws SQLException {
        String sql = "INSERT INTO Usuarios (username, clave_hash, nombre, email, telefono, direccion, id_rol) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getClaveHash());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getDireccion());
            ps.setInt(7, u.getRol().getIdRol());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Usuario u) throws SQLException {
        String sql = "UPDATE Usuarios SET nombre=?, email=?, telefono=?, direccion=?, " +
                     "activo=?, fecha_modificacion=GETDATE() WHERE id_usuario=?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getDireccion());
            ps.setBoolean(5, u.isActivo());
            ps.setInt(6, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean registrarIntentoFallido(int idUsuario, int intentosActuales) throws SQLException {
        int nuevosIntentos = intentosActuales + 1;
        boolean bloquear = nuevosIntentos >= 3;
        String sql = "UPDATE Usuarios SET intentos_fallidos=?, bloqueado=? WHERE id_usuario=?";

        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, nuevosIntentos);
            ps.setBoolean(2, bloquear);
            ps.setInt(3, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean resetearIntentos(int idUsuario) throws SQLException {
        String sql = "UPDATE Usuarios SET intentos_fallidos=0, bloqueado=0 WHERE id_usuario=?";
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idUsuario) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Rol rol = new Rol(
                rs.getInt("id_rol"),
                rs.getString("rol_nombre"),
                rs.getString("rol_desc")
        );
        Timestamp fechaMod = rs.getTimestamp("fecha_modificacion");
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("username"),
                rs.getString("clave_hash"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("direccion"),
                rol,
                rs.getBoolean("activo"),
                rs.getInt("intentos_fallidos"),
                rs.getBoolean("bloqueado"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                fechaMod != null ? fechaMod.toLocalDateTime() : null
        );
    }
}