package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL      = "jdbc:sqlserver://localhost:1433;databaseName=PetStoreElMono;encrypt=false";
    private static final String USUARIO  = "sa";
    private static final String CLAVE    = "tu_clave_aqui";

    private static Conexion instancia;
    private Connection connection;

    private Conexion() {}

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USUARIO, CLAVE);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver SQL Server no encontrado: " + e.getMessage());
            }
        }
        return connection;
    }

    public void cerrar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}