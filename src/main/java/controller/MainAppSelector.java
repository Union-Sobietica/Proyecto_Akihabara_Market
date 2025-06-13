package controller;

import view.InterfazConsola;
import view.InterfazGrafica;
import view.Utilidades;

import dao.ClienteDAO;
import dao.ProductoDAO;

import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Clase que permite seleccionar entre la interfaz de consola o gráfica para
 * interactuar con la aplicación. Ofrece un menú inicial para elegir la interfaz
 * y controla el ciclo de ejecución de cada una.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class MainAppSelector {

    /**
     * Método principal que muestra el menú de selección de interfaz y lanza la
     * correspondiente según la elección del usuario.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        int opcion;

        do {
            // Mostrar menú inicial para seleccionar la interfaz a usar
            System.out.println("""
                -----------------------------
                ¿Qué interfaz deseas usar?
                1. Interfaz de Consola
                2. Interfaz Gráfica
                0. Salir
                -----------------------------
            """);

            // Pedir al usuario que ingrese la opción deseada
            opcion = Utilidades.pedirEntero("Selecciona una opción: ");

            // Procesar la opción ingresada
            switch (opcion) {
                case 1 -> lanzarConsola();   // Iniciar interfaz por consola
                case 2 -> lanzarGrafica();   // Iniciar interfaz gráfica
                case 0 -> System.out.println("Saliendo del programa..."); // Salir del programa
                default -> System.out.println("Opción no válida."); // Opción incorrecta
            }

            System.out.println(); // Línea en blanco para separar iteraciones

        } while (opcion != 0); // Repetir mientras no se elija salir (0)
    }

    /**
     * Método para iniciar y manejar la interfaz de consola con su menú y acciones.
     */
    private static void lanzarConsola() {
        InterfazConsola vista = new InterfazConsola();  // Crear instancia de la vista por consola
        ProductoDAO dao = new ProductoDAO();            // DAO para productos
        ClienteDAO cDao = new ClienteDAO();             // DAO para clientes
        int opcion;

        do {
            vista.mostrarMenuPrincipal();               // Mostrar menú principal en consola
            opcion = Utilidades.pedirEntero("Selecciona una opción: ");  // Pedir opción al usuario

            // Ejecutar la acción según opción elegida
            switch (opcion) {
                case 1 -> vista.agregarProducto(dao);
                case 2 -> vista.obtenerProductoPorId(dao);
                case 3 -> vista.listarProductos(dao);
                case 4 -> vista.actualizarProducto(dao);
                case 5 -> vista.eliminarProducto(dao);
                case 6 -> vista.buscarPorNombre(dao);
                case 7 -> vista.descripcionIa(dao);
                case 8 -> vista.categoriaIa();
                case 9 -> vista.ejecutarMenuClientes(cDao);  // Submenú para clientes
                case 0 -> System.out.println("Saliendo de la consola...");
                default -> System.out.println("Opción inválida");
            }

            System.out.println(); // Línea en blanco para separar iteraciones

        } while (opcion != 0);  // Repetir mientras no se elija salir (0)
    }

    /**
     * Método para iniciar la interfaz gráfica y esperar hasta que la ventana se
     * cierre antes de continuar.
     */
    private static void lanzarGrafica() {
        CountDownLatch latch = new CountDownLatch(1);  // Crear un latch para esperar

        SwingUtilities.invokeLater(() -> {
            InterfazGrafica interfaz = new InterfazGrafica();
            
            // Agregar un listener para detectar cuando se cierra la ventana
            interfaz.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            interfaz.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    latch.countDown();  // Liberar el latch cuando se cierre la ventana
                }
            });

            interfaz.setVisible(true);  // Mostrar la ventana
        });

        try {
            latch.await();  // Esperar hasta que la ventana gráfica se cierre
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restaurar el estado del hilo
            System.out.println("Esperando cierre de ventana interrumpido.");
        }
    }
}