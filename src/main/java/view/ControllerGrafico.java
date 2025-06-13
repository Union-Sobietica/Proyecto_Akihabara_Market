package view;

import dao.ClienteDAO;
import dao.ProductoDAO;
import model.ClienteOtaku;
import model.ProductoOtaku;

import java.util.List;

/**
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
/**
 * Clase controlador para la interacción gráfica (interfaz de usuario).
 * Actúa como intermediario entre la vista y el acceso a datos (DAO).
 * Proporciona métodos para gestionar productos y clientes.
 */
public class ControllerGrafico {

    // Instancia del DAO para productos, permite operar con la base de datos de productos
    private final ProductoDAO productoDAO = new ProductoDAO();
    
    // Instancia del DAO para clientes, permite operar con la base de datos de clientes
    private final ClienteDAO clienteDAO = new ClienteDAO();

    // -------------------- MÉTODOS PARA PRODUCTOS --------------------

    /**
     * Obtiene todos los productos disponibles en la base de datos.
     * 
     * @return lista de todos los productos
     */
    public List<ProductoOtaku> obtenerTodosProductos() {
        return productoDAO.obtenerTodosLosProductos();
    }

    /**
     * Obtiene un producto específico según su ID.
     * 
     * @param id ID del producto a buscar
     * @return producto con el ID dado o null si no existe
     */
    public ProductoOtaku obtenerProductoPorId(int id) {
        return productoDAO.obtenerProductoPorId(id);
    }
    
    /**
     * Busca productos cuyo nombre coincida con el parámetro dado.
     * 
     * @param nombre nombre o parte del nombre para buscar
     * @return lista de productos que coinciden con la búsqueda
     */
    public List<ProductoOtaku> buscarProductoPorNombre(String nombre) {
        return productoDAO.buscarProductosPorNombre(nombre);
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     * 
     * @param producto objeto ProductoOtaku con los datos a agregar
     */
    public void agregarProducto(ProductoOtaku producto) {
        productoDAO.agregarProducto(producto);
    }

    /**
     * Actualiza los datos de un producto existente en la base de datos.
     * 
     * @param producto objeto ProductoOtaku con datos actualizados
     */
    public void actualizarProducto(ProductoOtaku producto) {
        productoDAO.actualizarProducto(producto);
    }

    /**
     * Elimina un producto de la base de datos según su ID.
     * 
     * @param id ID del producto a eliminar
     */
    public void eliminarProducto(int id) {
        productoDAO.eliminarProducto(id);
    }

    // -------------------- MÉTODOS PARA CLIENTES --------------------

    /**
     * Obtiene todos los clientes registrados en la base de datos.
     * 
     * @return lista de todos los clientes
     */
    public List<ClienteOtaku> obtenerTodosClientes() {
        return clienteDAO.obtenerTodosLosClientes();
    }

    /**
     * Obtiene un cliente específico según su ID.
     * 
     * @param id ID del cliente a buscar
     * @return cliente con el ID dado o null si no existe
     */
    public ClienteOtaku obtenerClientePorId(int id) {
        return clienteDAO.obtenerClientePorId(id);
    }

    /**
     * Busca clientes cuyo email coincida con el parámetro dado.
     * 
     * @param email email o parte del email para buscar
     * @return lista de clientes que coinciden con la búsqueda
     */
    public List<ClienteOtaku> buscarClientePorEmail(String email) {
        return clienteDAO.buscarPorEmail(email);
    }

    /**
     * Agrega un nuevo cliente a la base de datos.
     * 
     * @param cliente objeto ClienteOtaku con los datos a agregar
     */
    public void agregarCliente(ClienteOtaku cliente) {
        clienteDAO.agregarCliente(cliente);
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     * 
     * @param cliente objeto ClienteOtaku con datos actualizados
     */
    public void actualizarCliente(ClienteOtaku cliente) {
        clienteDAO.actualizarCliente(cliente);
    }

    /**
     * Elimina un cliente de la base de datos según su ID.
     * 
     * @param id ID del cliente a eliminar
     */
    public void eliminarCliente(int id) {
        clienteDAO.eliminarCliente(id);
    }
    
}