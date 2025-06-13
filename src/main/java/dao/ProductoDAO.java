package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.ProductoOtaku;

/**
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class ProductoDAO extends DatabaseConnection {

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto Objeto ProductoOtaku que contiene los datos a insertar.
     */
    public void agregarProducto(ProductoOtaku producto) {

        String query = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            // Asignar valores a los parámetros del PreparedStatement
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            // Ejecutar la inserción y obtener número de filas afectadas
            int affected = stmt.executeUpdate();

            System.out.println(affected > 0 ? "Se ha añadido el producto" : "NO se ha realizado");

        } catch (SQLException e) {
            System.out.println("Error al añadir producto: " + e.getMessage());
        }

    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id Identificador único del producto.
     * @return ProductoOtaku encontrado o null si no existe.
     */
    public ProductoOtaku obtenerProductoPorId(int id) {

        String query = "SELECT * FROM productos WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                // Si hay resultado, construir y devolver el objeto ProductoOtaku
                if (rs.next()) {
                    return construirProducto(rs);
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al buscar producto por ID: " + e.getMessage());
        }

        // Retornar null si no se encuentra el producto
        return null;

    }

    /**
     * Obtiene todos los productos almacenados en la base de datos.
     *
     * @return Lista de todos los productos.
     */
    public List<ProductoOtaku> obtenerTodosLosProductos() {

        List<ProductoOtaku> productos = new ArrayList<>();

        String query = "SELECT * FROM productos";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Recorrer los resultados y agregar a la lista
            while (rs.next()) {
                productos.add(construirProducto(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }

        return productos;

    }

    /**
     * Actualiza un producto existente en la base de datos.
     *
     * @param producto Objeto ProductoOtaku con datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarProducto(ProductoOtaku producto) {

        String query = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            // Establecer valores para el UPDATE
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());

            // Ejecutar actualización y retornar true si se actualizó algún registro
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }

    }

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param id Identificador del producto a eliminar.
     * @return true si se eliminó correctamente, false si ocurrió un error.
     */
    public boolean eliminarProducto(int id) {

        String query = "DELETE FROM productos WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, id);

            // Ejecutar eliminación y retornar true si se eliminó algún registro
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }

    }

    /**
     * Busca productos cuyo nombre contenga la cadena dada (búsqueda parcial).
     *
     * @param nombre Cadena a buscar dentro del nombre del producto.
     * @return Lista de productos que coinciden con la búsqueda.
     */
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {

        List<ProductoOtaku> productos = new ArrayList<>();

        String query = "SELECT * FROM productos WHERE nombre LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            // Usar comodines para búsqueda parcial
            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                // Añadir cada producto encontrado a la lista
                while (rs.next()) {
                    productos.add(construirProducto(rs));
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al buscar por nombre: " + e.getMessage());
        }

        return productos;

    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    /**
     * Construye un objeto ProductoOtaku a partir de un ResultSet.
     *
     * @param rs ResultSet con los datos del producto.
     * @return Objeto ProductoOtaku construido.
     * @throws SQLException En caso de error al acceder al ResultSet.
     */
    private ProductoOtaku construirProducto(ResultSet rs) throws SQLException {

        ProductoOtaku p = new ProductoOtaku();

        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setCategoria(rs.getString("categoria"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("stock"));

        return p;

    }

}