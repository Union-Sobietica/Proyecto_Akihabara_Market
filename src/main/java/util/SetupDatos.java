package util;

import dao.ClienteDAO;
import dao.ProductoDAO;
import model.ClienteOtaku;
import model.ProductoOtaku;

/**
 * Clase de configuración para inicializar datos de ejemplo en la base de datos.
 * Crea productos y clientes de prueba y los inserta, luego muestra los registros.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class SetupDatos {
    
    /**
     * Método principal que inicializa datos de prueba en la base de datos.
     * <p>
     * Crea instancias de ProductoOtaku y ClienteOtaku para luego agregarlos
     * a la base de datos mediante sus respectivos DAOs. Finalmente muestra
     * por consola todos los productos y clientes almacenados.
     * </p>
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        
        // Crear instancia del DAO para gestionar los productos y clientes en la base de datos
        ProductoDAO productos = new ProductoDAO();
        ClienteDAO clientes = new ClienteDAO();
        
        // Crear nuevo producto tipo Manga con sus datos
        ProductoOtaku producto1 = new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20);
        // Agregar el producto 1 a la base de datos
        productos.agregarProducto(producto1);
        
        // Crear nuevo producto tipo Ropa con sus datos
        ProductoOtaku producto2 = new ProductoOtaku("Camiseta de The Witcher 3", "Ropa", 11.99, 15);
        // Agregar el producto 2 a la base de datos
        productos.agregarProducto(producto2);
        
        // Crear nuevo producto tipo Figura con sus datos
        ProductoOtaku producto3 = new ProductoOtaku("Figura de V Cyberpunk 2077", "Figura", 59.99, 10);
        // Agregar el producto 3 a la base de datos
        productos.agregarProducto(producto3);
        
        // Crear 3 nuevos clientes con sus datos
        ClienteOtaku cliente1 = new ClienteOtaku("Juan Carlos", "juancar@gmail.com", 675930485);
        ClienteOtaku cliente2 = new ClienteOtaku("Manuel García", "manu@hotmail.com", 625149784);
        ClienteOtaku cliente3 = new ClienteOtaku("Marta Montes", "marta@gmail.com", 635412481);
        
        // Agregar 3 clientes a la base de datos
        clientes.agregarCliente(cliente1);
        clientes.agregarCliente(cliente2);
        clientes.agregarCliente(cliente3);
        
        // Mostrar en consola todos los productos y clientes almacenados en la base de datos
        System.out.println(productos.obtenerTodosLosProductos());
        System.out.println("\n    --------    \n");
        System.out.println(clientes.obtenerTodosLosClientes());
        
    }
    
}