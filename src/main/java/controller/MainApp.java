package controller;

import dao.ClienteDAO;
import dao.ProductoDAO;
import view.InterfazConsola;
import view.Utilidades;

/**
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class MainApp {

	/**
	 * Método principal que inicia la aplicación y controla el flujo del menú.
	 * 
	 * @param args Parámetros de entrada desde la línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {

		// Crear instancia de la interfaz de consola para interacción con el usuario
		InterfazConsola vista = new InterfazConsola();

		// Crear instancia del DAO para acceder a la base de datos de productos
		ProductoDAO dao = new ProductoDAO();
		
		ClienteDAO cDao = new ClienteDAO();

		int opcion; // Variable para almacenar la opción del menú seleccionada por el usuario

		// Bucle principal que se ejecuta hasta que el usuario elija salir (opción 0)
		do {

			// Mostrar el menú principal en consola
			vista.mostrarMenuPrincipal();

			// Pedir al usuario que seleccione una opción (entero)
			opcion = Utilidades.pedirEntero("Selecciona una opción: ");

			// Ejecutar la acción correspondiente según la opción ingresada
			switch (opcion) {

			case 1 -> vista.agregarProducto(dao);                      // Agregar nuevo producto
			case 2 -> vista.obtenerProductoPorId(dao);                 // Buscar producto por ID
			case 3 -> vista.listarProductos(dao);                      // Listar todos los productos
			case 4 -> vista.actualizarProducto(dao);                   // Actualizar producto existente
			case 5 -> vista.eliminarProducto(dao);                     // Eliminar producto
			case 6 -> vista.buscarPorNombre(dao);                      // Buscar productos por nombre
			case 7 -> vista.descripcionIa(dao);                        // Generar descripción de producto con IA
			case 8 -> vista.categoriaIa();                             // Sugerir categoría con IA
			case 9 -> vista.ejecutarMenuClientes(cDao);                // Abre el menú de clientes
			case 0 -> System.out.println("Saliendo del programa...");  // Salir de la aplicación
			default -> System.out.println("Opción inválida");          // Opción no reconocida

			}

			System.out.println(); // Línea en blanco para separar cada ciclo del menú

		} while (opcion != 0); // Continuar hasta que el usuario elija salir (opción 0)

	}

}