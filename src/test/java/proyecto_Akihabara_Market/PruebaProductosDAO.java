package proyecto_Akihabara_Market;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.ProductoDAO;
import model.ProductoOtaku;

/**
 * Clase de pruebas unitarias para la clase ProductoDAO.
 * Verifica el correcto funcionamiento de las operaciones sobre productos
 * como agregar, buscar, actualizar y eliminar.
 */

class PruebaProductosDAO {

    // Instancia del DAO que se va a probar
	
    static ProductoDAO dao = new ProductoDAO();
    
    // Producto de prueba que se usará en cada test
    
    ProductoOtaku productoTest;

    /**
     * Se ejecuta antes de cada prueba.
     * Crea e inserta un producto de prueba en la base de datos.
     */
    
    @BeforeEach
    void setUp() {
    	
        productoTest = new ProductoOtaku();
        
        // Se añade un timestamp al nombre para evitar conflictos por duplicados
        
        productoTest.setNombre("Test Producto " + System.currentTimeMillis());
        
        productoTest.setCategoria("Test");
        
        productoTest.setPrecio(99.99);
        
        productoTest.setStock(10);

        // Agrega el producto a la BD
        
        dao.agregarProducto(productoTest);

        // Recupera el producto insertado para obtener su ID
        
        List<ProductoOtaku> encontrados = dao.buscarProductosPorNombre(productoTest.getNombre());
        
        if (!encontrados.isEmpty()) {
        	
            productoTest.setId(encontrados.get(0).getId());
            
        }
        
    }

    /**
     * Se ejecuta después de cada prueba.
     * Elimina el producto de prueba de la base de datos para mantener un entorno limpio.
     */
    
    @AfterEach
    void tearDown() {
    	
        if (productoTest.getId() > 0) {
        	
            dao.eliminarProducto(productoTest.getId());
            
        }
        
    }

    /**
     * Verifica que el producto fue agregado correctamente con un ID válido.
     */
    
    @Test
    void testAgregarProducto() {
    	
        assertTrue(productoTest.getId() > 0, "El producto debería haberse insertado con un ID válido");
        
    }

    /**
     * Verifica que se puede obtener un producto por su ID y que los datos coinciden.
     */
    
    @Test
    void testObtenerProductoPorId() {
    	
        ProductoOtaku obtenido = dao.obtenerProductoPorId(productoTest.getId());
        
        assertNotNull(obtenido, "El producto no debería ser null");
        
        assertEquals(productoTest.getNombre(), obtenido.getNombre(), "Los nombres deberían coincidir");
        
    }

    /**
     * Verifica que se puede obtener la lista de todos los productos.
     */
    
    @Test
    void testObtenerTodosLosProductos() {
    	
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
        
        assertNotNull(productos, "La lista de productos no debería ser null");
        
        assertTrue(productos.size() > 0, "Debería haber al menos un producto en la base de datos");
        
    }

    /**
     * Prueba que se puede actualizar un campo del producto correctamente (stock en este caso).
     */
    
    @Test
    void testActualizarProducto() {
    	
        productoTest.setStock(99);  // Cambia el stock del producto
        
        boolean actualizado = dao.actualizarProducto(productoTest);
        
        assertTrue(actualizado, "El producto debería actualizarse correctamente");

        ProductoOtaku actualizadoProducto = dao.obtenerProductoPorId(productoTest.getId());
        
        assertEquals(99, actualizadoProducto.getStock(), "El stock debería haberse actualizado");
        
    }

    /**
     * Verifica que se puede eliminar un producto y que ya no esté disponible en la base de datos.
     */
    
    @Test
    void testEliminarProducto() {
    	
        boolean eliminado = dao.eliminarProducto(productoTest.getId());
        
        assertTrue(eliminado, "El producto debería eliminarse correctamente");

        ProductoOtaku eliminadoProducto = dao.obtenerProductoPorId(productoTest.getId());
        
        assertNull(eliminadoProducto, "El producto eliminado no debería existir");

        // Para evitar que @AfterEach lo intente eliminar nuevamente
        
        productoTest.setId(-1);
        
    }

    /**
     * Prueba que se puede buscar un producto por su nombre.
     */
    
    @Test
    void testBuscarProductosPorNombre() {
    	
        List<ProductoOtaku> encontrados = dao.buscarProductosPorNombre(productoTest.getNombre());
        
        assertFalse(encontrados.isEmpty(), "La búsqueda por nombre debería devolver resultados");
        
        assertEquals(productoTest.getNombre(), encontrados.get(0).getNombre(), "El nombre debería coincidir");
        
    }
    
}