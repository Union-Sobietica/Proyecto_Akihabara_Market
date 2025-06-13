package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ClienteOtaku;

/**
 * Clase DAO para manejar las operaciones de base de datos relacionadas con clientes.
 * Permite agregar, obtener, actualizar, eliminar y buscar clientes en la base de datos.
 * Extiende de DatabaseConnection para usar la conexión SQL.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class ClienteDAO extends DatabaseConnection {

    /**
     * Agrega un nuevo cliente a la base de datos.
     * 
     * @param cliente Objeto ClienteOtaku que contiene los datos a insertar.
     */
    public void agregarCliente(ClienteOtaku cliente) {

        String query = "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            // Asignar valores a los parámetros del PreparedStatement
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setInt(3, cliente.getTelefono());

            // Ejecutar la inserción y obtener número de filas afectadas
            int affected = stmt.executeUpdate();

            System.out.println(affected > 0 ? "Se ha añadido el cliente" : "NO se ha realizado");

        } catch (SQLException e) {
            System.out.println("Error al añadir cliente: " + e.getMessage());
        }
    }

    /**
     * Obtiene un cliente por su ID.
     * 
     * @param id Identificador único del cliente.
     * @return ClienteOtaku encontrado o null si no existe.
     */
    public ClienteOtaku obtenerClientePorId(int id) {

        String query = "SELECT * FROM clientes WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                // Si hay resultado, construir y devolver el objeto ClienteOtaku
                if (rs.next()) {
                    return construirCliente(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por ID: " + e.getMessage());
        }

        // Retornar null si no se encuentra el cliente
        return null;
    }

    /**
     * Obtiene todos los clientes almacenados en la base de datos.
     * 
     * @return Lista con todos los clientes.
     */
    public List<ClienteOtaku> obtenerTodosLosClientes() {

        List<ClienteOtaku> clientes = new ArrayList<>();

        String query = "SELECT * FROM clientes";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Recorrer los resultados y agregar a la lista
            while (rs.next()) {
                clientes.add(construirCliente(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Actualiza un cliente existente en la base de datos.
     * Si el email cambió, también actualiza el email.
     * 
     * @param cliente Objeto ClienteOtaku con datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarCliente(ClienteOtaku cliente) {

        StringBuilder query = new StringBuilder("UPDATE clientes SET nombre = ?, telefono = ?");
        boolean actualizarEmail = false;

        String emailActual = obtenerEmailActual(cliente.getId());

        if (!cliente.getEmail().equals(emailActual)) {
            query.append(", email = ?");
            actualizarEmail = true;
        }

        query.append(" WHERE id = ?");

        try (PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

            stmt.setString(1, cliente.getNombre());
            stmt.setInt(2, cliente.getTelefono());

            if (actualizarEmail) {
                stmt.setString(3, cliente.getEmail());
                stmt.setInt(4, cliente.getId());
            } else {
                stmt.setInt(3, cliente.getId());
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     * 
     * @param id Identificador del cliente a eliminar.
     * @return true si se eliminó correctamente, false si ocurrió un error.
     */
    public boolean eliminarCliente(int id) {

        String query = "DELETE FROM clientes WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);

            // Ejecutar eliminación y retornar true si se eliminó algún registro
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca clientes cuyo email contenga la cadena dada (búsqueda parcial).
     * 
     * @param email Cadena a buscar dentro del email.
     * @return Lista de clientes que coinciden con la búsqueda.
     */
    public List<ClienteOtaku> buscarPorEmail(String email) {

        List<ClienteOtaku> cliente = new ArrayList<>();

        String query = "SELECT * FROM clientes WHERE email LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            // Usar comodines para búsqueda parcial
            stmt.setString(1, "%" + email + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                // Añadir cada cliente encontrado a la lista
                while (rs.next()) {
                    cliente.add(construirCliente(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar por email: " + e.getMessage());
        }

        return cliente;
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    /**
     * Construye un objeto ClienteOtaku a partir de un ResultSet.
     * 
     * @param rs ResultSet con los datos del cliente.
     * @return Objeto ClienteOtaku construido.
     * @throws SQLException En caso de error al acceder al ResultSet.
     */
    private ClienteOtaku construirCliente(ResultSet rs) throws SQLException {

        ClienteOtaku c = new ClienteOtaku();

        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getInt("telefono"));
        c.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());

        return c;
    }

    /**
     * Verifica si un email ya existe en la base de datos.
     * 
     * @param email Email a verificar.
     * @return true si el email ya está registrado, false en caso contrario.
     */
    public boolean verificarEmail(String email) {

        String query = "SELECT 1 FROM clientes WHERE email = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {

                // Si hay algún resultado, el correo existe
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el email actual de un cliente dado su ID.
     * 
     * @param clienteId ID del cliente.
     * @return Email actual del cliente o null si no se encuentra o hay error.
     */
    private String obtenerEmailActual(int clienteId) {

        String email = null;

        String query = "SELECT email FROM clientes WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, clienteId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener email actual: " + e.getMessage());
        }

        return email;
    }

}