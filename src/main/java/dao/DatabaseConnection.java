package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.ConfigLoader;

/**
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class DatabaseConnection {

    /**
     * Objeto Connection protegido para que las clases hijas puedan usarlo.
     */
    protected Connection conexion;

    /**
     * Constructor que establece la conexión con la base de datos.
     * <p>
     * Carga el driver JDBC de MySQL y obtiene la conexión a la base de datos
     * utilizando las propiedades definidas en el archivo de configuración.
     * </p>
     */
    public DatabaseConnection() {
        try {
            // Cargar el driver JDBC de MySQL (asegura que esté disponible)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Obtener la conexión a la base de datos usando datos de config.properties
            conexion = DriverManager.getConnection(
                    ConfigLoader.getProperty("dbUrl"),      // URL de la base de datos
                    ConfigLoader.getProperty("dbUser"),     // Usuario
                    ConfigLoader.getProperty("dbPassword")  // Contraseña
            );

        } catch (ClassNotFoundException | SQLException ex) {
            // Capturar y mostrar errores al cargar el driver o conectar con la BD
            System.out.println("Error al conectar: " + ex);
        }
    }

}