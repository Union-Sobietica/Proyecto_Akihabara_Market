package view;

import java.util.List;

import dao.ClienteDAO;
import dao.ProductoDAO;
import model.ClienteOtaku;
import model.ProductoOtaku;
import service.LlmService;

/**
 * Clase que representa la interfaz de usuario por consola para interactuar con
 * productos y clientes otaku. Permite agregar, buscar, listar, actualizar y eliminar
 * productos y clientes, así como funciones con IA.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class InterfazConsola {

    // Menú principal estático y formateado con texto multilínea
    private static final String MENU_PRINCIPAL = """
            ===== MENÚ PRINCIPAL =====
            1. Agregar nuevo producto
            2. Buscar producto por ID
            3. Listar todos los productos
            4. Actualizar producto
            5. Eliminar producto
            6. Buscar productos por nombre
            7. Generar Descripción de Producto con IA
            8. Sugerir Categoría para Producto con IA
            9. Menú de Clientes
            0. Salir
            """;

    // Menú de clientes estático y formateado con texto multilínea
    private static final String MENU_CLIENTES = """
            ===== MENÚ DE CLIENTES =====
            1. Agregar nuevo cliente
            2. Buscar cliente por ID
            3. Listar todos los clientes
            4. Actualizar cliente
            5. Eliminar cliente
            6. Buscar cliente por email
            0. Salir
            """;

    /**
     * Muestra el menú principal de productos por consola.
     */
    public void mostrarMenuPrincipal() {
        System.out.println(MENU_PRINCIPAL);
    }

    /**
     * Muestra el menú de clientes por consola.
     */
    public void mostrarMenuClientes() {
        System.out.println(MENU_CLIENTES);
    }

    /**
     * Solicita datos del usuario y agrega un nuevo producto a través del DAO.
     * 
     * @param dao Instancia de ProductoDAO para operaciones de persistencia
     */
    public void agregarProducto(ProductoDAO dao) {
        System.out.println("\n--- Agregar nuevo producto ---");
        ProductoOtaku nuevo = pedirDatosProducto();
        dao.agregarProducto(nuevo);
    }

    /**
     * Busca un producto por su ID y lo muestra si existe.
     * 
     * @param dao Instancia de ProductoDAO para operaciones de consulta
     */
    public void obtenerProductoPorId(ProductoDAO dao) {
        int id = Utilidades.pedirEntero("\n--- Buscar producto por ID ---\nIntroduce el ID del producto: ");
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        System.out.println(producto != null ? producto : "No se encontró el producto con ID " + id);
    }

    /**
     * Muestra todos los productos disponibles en la base de datos.
     * 
     * @param dao Instancia de ProductoDAO para operaciones de consulta
     */
    public void listarProductos(ProductoDAO dao) {
        System.out.println("\n--- Lista de todos los productos ---");
        imprimirProductos(dao.obtenerTodosLosProductos());
    }

    /**
     * Permite al usuario actualizar campos específicos de un producto por su ID.
     * 
     * @param dao Instancia de ProductoDAO para operaciones de actualización
     */
    public void actualizarProducto(ProductoDAO dao) {
        int id = Utilidades.pedirEntero("\n--- Actualizar producto ---\nID del producto a actualizar: ");
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        if (producto == null) {
            System.out.println("No existe un producto con ese ID");
            return;
        }
        System.out.println("Producto actual:\n" + producto);
        int opcion;
        do {
            System.out.println("""
                    ===== MENÚ DE ACTUALIZACIÓN =====
                    1. Actualizar nombre
                    2. Actualizar categoría
                    3. Actualizar precio
                    4. Actualizar stock
                    0. Salir
                    """);
            opcion = Utilidades.pedirEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1 -> producto.setNombre(Utilidades.pedirString("Nuevo nombre: "));
                case 2 -> producto.setCategoria(Utilidades.pedirString("Nueva categoría: "));
                case 3 -> producto.setPrecio(Utilidades.pedirDoublePositivo("Nuevo precio: "));
                case 4 -> producto.setStock(Utilidades.pedirEnteroPositivo("Nuevo stock: "));
                case 0 -> System.out.println("Guardando...");
                default -> System.out.println("Opción inválida");
            }
            System.out.println();
        } while (opcion != 0);
        System.out.println(
            dao.actualizarProducto(producto) ? "Producto actualizado" : "No se pudo actualizar el producto"
        );
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     * 
     * @param dao Instancia de ProductoDAO para operaciones de eliminación
     */
    public void eliminarProducto(ProductoDAO dao) {
        int id = Utilidades.pedirEntero("\n--- Eliminar producto ---\nID del producto a eliminar: ");
        System.out.println(dao.eliminarProducto(id) ? "Producto eliminado" : "No se pudo eliminar el producto");
    }

    /**
     * Busca y muestra productos que coincidan parcialmente con el nombre ingresado.
     * 
     * @param dao Instancia de ProductoDAO para operaciones de búsqueda
     */
    public void buscarPorNombre(ProductoDAO dao) {
        String nombre = Utilidades.pedirString("\n--- Buscar productos por nombre ---\nNombre del producto: ");
        imprimirProductos(dao.buscarProductosPorNombre(nombre));
    }

    /**
     * Genera una descripción breve de producto usando IA.
     * 
     * @param dao Instancia de ProductoDAO para obtener datos del producto
     */
    public void descripcionIa(ProductoDAO dao) {
        int id = Utilidades.pedirEntero(
            "\n--- Generar Descripción de Producto con IA ---\nIntroduce el ID del producto: "
        );
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        if (producto != null) {
            String prompt = "En español: Genera una descripción de marketing breve de no más de 15 palabras y atractiva para el producto otaku: "
                    + producto.getNombre() + " de la categoría " + producto.getCategoria() + ".";
            ejecutarIA(prompt, "Generando descripción...");
        } else {
            System.out.println("No se encontró el producto con ID " + id);
        }
    }

    /**
     * Sugiere una categoría para un producto dado su nombre utilizando IA.
     */
    public void categoriaIa() {
        String nombre = Utilidades.pedirString(
            "\n--- Sugerir Categoría para Producto con IA ---\nIntroduce el nombre de un nuevo producto: "
        );
        String prompt = "En español: Para un producto otaku llamado " + nombre
                + ", sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro. "
                + "Responde en este formato: Categoría: 'Nombre de la categoría'. "
                + "Comprueba que si el nombre del producto es extraño (ej. djhdftj, etc) la respuesta sea Desconocido.";
        ejecutarIA(prompt, "Escogiendo categoría...");
    }

    /**
     * Solicita datos del usuario y agrega un nuevo cliente a través del DAO.
     * 
     * @param dao Instancia de ClienteDAO para operaciones de persistencia
     */
    public void agregarCliente(ClienteDAO dao) {
        System.out.println("\n--- Agregar nuevo cliente ---");
        ClienteOtaku nuevo = pedirDatosCliente();
        if (dao.verificarEmail(nuevo.getEmail())) {
            System.out.println("El email ya está registrado.");
        } else {
            dao.agregarCliente(nuevo);
        }
    }

    /**
     * Busca un cliente por su ID y lo muestra si existe.
     * 
     * @param dao Instancia de ClienteDAO para operaciones de consulta
     */
    public void obtenerClientePorId(ClienteDAO dao) {
        int id = Utilidades.pedirEntero("\n--- Buscar cliente por ID ---\nIntroduce el ID del cliente: ");
        ClienteOtaku cliente = dao.obtenerClientePorId(id);
        System.out.println(cliente != null ? cliente : "No se encontró el cliente con ID " + id);
    }

    /**
     * Muestra todos los clientes disponibles en la base de datos.
     * 
     * @param dao Instancia de ClienteDAO para operaciones de consulta
     */
    public void listarClientes(ClienteDAO dao) {
        System.out.println("\n--- Lista de todos los clientes ---");
        imprimirCliente(dao.obtenerTodosLosClientes());
    }

    /**
     * Permite al usuario actualizar campos específicos de un cliente por su ID.
     * 
     * @param dao Instancia de ClienteDAO para operaciones de actualización
     */
    public void actualizarCliente(ClienteDAO dao) {
        int id = Utilidades.pedirEntero("\n--- Actualizar cliente ---\nID del cliente a actualizar: ");
        ClienteOtaku cliente = dao.obtenerClientePorId(id);
        if (cliente == null) {
            System.out.println("No existe un cliente con ese ID");
            return;
        }
        System.out.println("Cliente actual:\n" + cliente);
        int opcion;
        do {
            System.out.println("""
                    ===== MENÚ DE ACTUALIZACIÓN =====
                    1. Actualizar nombre
                    2. Actualizar email
                    3. Actualizar telefono
                    0. Salir
                    """);
            opcion = Utilidades.pedirEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1 -> cliente.setNombre(Utilidades.pedirString("Nuevo nombre: "));
                case 2 -> {
                    String nuevoEmail = Utilidades.pedirEmail("Nuevo email: ");
                    if (dao.verificarEmail(nuevoEmail) && !nuevoEmail.equalsIgnoreCase(cliente.getEmail())) {
                        System.out.println("Ese email ya está registrado.");
                    } else {
                        cliente.setEmail(nuevoEmail);
                    }
                }
                case 3 -> cliente.setTelefono(Utilidades.pedirEnteroPositivo("Nuevo telefono: "));
                case 0 -> System.out.println("Guardando...");
                default -> System.out.println("Opción inválida");
            }
            System.out.println();
        } while (opcion != 0);
        System.out.println(
            dao.actualizarCliente(cliente) ? "Cliente actualizado" : "No se pudo actualizar el cliente"
        );
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     * 
     * @param dao Instancia de ClienteDAO para operaciones de eliminación
     */
    public void eliminarCliente(ClienteDAO dao) {
        int id = Utilidades.pedirEntero("\n--- Eliminar cliente ---\nID del cliente a eliminar: ");
        System.out.println(dao.eliminarCliente(id) ? "Cliente eliminado" : "No se pudo eliminar el cliente");
    }

    /**
     * Busca y muestra clientes que coincidan parcialmente con el email ingresado.
     * 
     * @param dao Instancia de ClienteDAO para operaciones de búsqueda
     */
    public void buscarPorEmail(ClienteDAO dao) {
        String email = Utilidades.pedirString("\n--- Buscar cliente por email ---\nEmail del cliente: ");
        imprimirCliente(dao.buscarPorEmail(email));
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    /**
     * Solicita al usuario los datos necesarios para crear un nuevo producto.
     * 
     * @return Instancia de ProductoOtaku con los datos ingresados
     */
    private ProductoOtaku pedirDatosProducto() {
        String nombre = Utilidades.pedirString("Nombre: ");
        String categoria = Utilidades.pedirString("Categoría: ");
        double precio = Utilidades.pedirDoublePositivo("Precio: ");
        int stock = Utilidades.pedirEnteroPositivo("Stock: ");
        return new ProductoOtaku(nombre, categoria, precio, stock);
    }

    /**
     * Imprime una lista de productos en consola.
     * 
     * @param productos Lista de ProductoOtaku a imprimir
     */
    private void imprimirProductos(List<ProductoOtaku> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos que mostrar.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    /**
     * Encapsula la llamada al servicio de IA y muestra la respuesta generada.
     * 
     * @param prompt         Texto de entrada para el modelo de IA
     * @param mensajeInicial Mensaje a mostrar antes de la respuesta
     */
    private void ejecutarIA(String prompt, String mensajeInicial) {
        System.out.println(mensajeInicial);
        LlmService ia = new LlmService();
        System.out.println(ia.oraculoDigital(prompt));
    }

    /**
     * Solicita al usuario los datos necesarios para crear un nuevo cliente.
     * 
     * @return Instancia de ClienteOtaku con los datos ingresados
     */
    private ClienteOtaku pedirDatosCliente() {
        String nombre = Utilidades.pedirString("Nombre: ");
        String email = Utilidades.pedirEmail("Email: ");
        int telefono = Utilidades.pedirEntero("Telefono: ");
        return new ClienteOtaku(nombre, email, telefono);
    }

    /**
     * Imprime una lista de clientes en consola.
     * 
     * @param clientes Lista de ClienteOtaku a imprimir
     */
    private void imprimirCliente(List<ClienteOtaku> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    /**
     * Ejecuta el menú de clientes permitiendo las operaciones disponibles.
     * 
     * @param dao Instancia de ClienteDAO para gestionar las acciones
     */
    public void ejecutarMenuClientes(ClienteDAO dao) {
        int opcion;
        do {
            System.out.println(MENU_CLIENTES);
            opcion = Utilidades.pedirEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1 -> agregarCliente(dao);
                case 2 -> obtenerClientePorId(dao);
                case 3 -> listarClientes(dao);
                case 4 -> actualizarCliente(dao);
                case 5 -> eliminarCliente(dao);
                case 6 -> buscarPorEmail(dao);
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida");
            }
            System.out.println();
        } while (opcion != 0);
    }

}