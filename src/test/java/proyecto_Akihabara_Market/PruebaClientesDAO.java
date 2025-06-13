package proyecto_Akihabara_Market;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.ClienteDAO;
import model.ClienteOtaku;

/**
 * Clase de pruebas unitarias para la clase ClienteDAO.
 * Usa JUnit 5 para probar las operaciones CRUD relacionadas con clientes.
 */

class PruebaClientesDAO {

    // DAO que se usará para interactuar con la base de datos
	
    static ClienteDAO dao = new ClienteDAO();
    
    // Cliente que se usará como objeto de prueba en cada test
    
    ClienteOtaku clienteTest;

    /**
     * Se ejecuta antes de cada prueba.
     * Crea un nuevo cliente y lo inserta en la base de datos para ser usado durante las pruebas.
     */
    
    @BeforeEach
    void setUp() {
    	
        clienteTest = new ClienteOtaku();
        
        clienteTest.setNombre("Test Cliente");
        
        // Se usa el timestamp para evitar duplicados en emails únicos
        
        clienteTest.setEmail("test.cliente." + System.currentTimeMillis() + "@mail.com");
        
        clienteTest.setTelefono(123456789);

        // Insertar cliente en la BD
        
        dao.agregarCliente(clienteTest);

        // Buscar el cliente por su email para obtener su ID generado automáticamente
        
        List<ClienteOtaku> encontrados = dao.buscarPorEmail(clienteTest.getEmail());
        
        if (!encontrados.isEmpty()) {
        	
            clienteTest.setId(encontrados.get(0).getId());
            
        }
        
    }

    /**
     * Se ejecuta después de cada prueba.
     * Elimina el cliente de prueba de la base de datos si aún existe.
     */
    
    @AfterEach
    void tearDown() {
    	
        if (clienteTest.getId() > 0) {
        	
            dao.eliminarCliente(clienteTest.getId());
            
        }
        
    }

    /**
     * Verifica que el cliente fue insertado correctamente y tiene un ID válido.
     */
    
    @Test
    void testAgregarCliente() {
    	
        assertTrue(clienteTest.getId() > 0, "El cliente debería haberse insertado con un ID válido");
        
    }

    /**
     * Prueba que se puede obtener un cliente por su ID y que los datos coinciden.
     */
    
    @Test
    void testObtenerClientePorId() {
    	
        ClienteOtaku obtenido = dao.obtenerClientePorId(clienteTest.getId());
        
        assertNotNull(obtenido, "El cliente obtenido no debería ser null");
        
        assertEquals(clienteTest.getEmail(), obtenido.getEmail(), "Los correos deberían coincidir");
        
    }

    /**
     * Verifica que se pueden obtener todos los clientes y que la lista no esté vacía.
     */
    
    @Test
    void testObtenerTodosLosClientes() {
    	
        List<ClienteOtaku> clientes = dao.obtenerTodosLosClientes();
        
        assertNotNull(clientes, "La lista de clientes no debería ser null");
        
        assertTrue(clientes.size() > 0, "La lista de clientes debería contener al menos un cliente");
        
    }

    /**
     * Prueba que se puede actualizar un campo del cliente correctamente.
     */
    
    @Test
    void testActualizarCliente() {
    	
        clienteTest.setTelefono(987654321); // Cambiamos el número de teléfono
        
        boolean actualizado = dao.actualizarCliente(clienteTest);
        
        assertTrue(actualizado, "La actualización del cliente debería ser exitosa");

        // Verifica que el valor actualizado se haya guardado correctamente
        
        ClienteOtaku actualizadoCliente = dao.obtenerClientePorId(clienteTest.getId());
        
        assertEquals(987654321, actualizadoCliente.getTelefono(), "El teléfono debería haberse actualizado");
        
    }

    /**
     * Prueba que un cliente puede ser eliminado correctamente.
     */
    
    @Test
    void testEliminarCliente() {
    	
        boolean eliminado = dao.eliminarCliente(clienteTest.getId());
        
        assertTrue(eliminado, "El cliente debería eliminarse correctamente");

        // Verifica que el cliente ya no exista
        
        ClienteOtaku eliminadoCliente = dao.obtenerClientePorId(clienteTest.getId());
        
        assertNull(eliminadoCliente, "El cliente debería ser null tras eliminarlo");

        // Para evitar que @AfterEach intente eliminarlo de nuevo
        
        clienteTest.setId(-1);
        
    }

    /**
     * Prueba que se puede buscar un cliente por su email.
     */
    
    @Test
    void testBuscarPorEmail() {
    	
        List<ClienteOtaku> lista = dao.buscarPorEmail(clienteTest.getEmail());
        
        assertFalse(lista.isEmpty(), "La búsqueda debería devolver al menos un cliente");
        
        assertEquals(clienteTest.getEmail(), lista.get(0).getEmail(), "El email debería coincidir");
        
    }

    /**
     * Prueba que se puede verificar si un email existe en la base de datos.
     */
    
    @Test
    void testVerificarEmail() {
    	
        boolean existe = dao.verificarEmail(clienteTest.getEmail());
        
        assertTrue(existe, "El email debería existir en la base de datos");
        
    }
    
}