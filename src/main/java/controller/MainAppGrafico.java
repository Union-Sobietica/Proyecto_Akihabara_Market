package controller;

import javax.swing.SwingUtilities;
import view.InterfazGrafica;

/**
 * Clase principal que inicia la interfaz gráfica de usuario (GUI) de la aplicación.
 * Utiliza SwingUtilities.invokeLater para asegurar que la interfaz se ejecute en
 * el hilo adecuado (Event Dispatch Thread).
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class MainAppGrafico {

    /**
     * Método principal que lanza la interfaz gráfica de la aplicación.
     * Se asegura de que la GUI se construya y se manipule desde el hilo
     * de despacho de eventos de Swing para evitar problemas de concurrencia.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // Usamos invokeLater para asegurar que la creación y
        // actualización de la interfaz gráfica se realice en el hilo
        // de despacho de eventos de Swing (Event Dispatch Thread),
        // que es el hilo seguro para manipular componentes gráficos.
        SwingUtilities.invokeLater(() -> {
            // Crear una instancia de la ventana principal de la interfaz gráfica
            InterfazGrafica interfaz = new InterfazGrafica();
            
            // Mostrar la ventana principal al usuario
            interfaz.setVisible(true);
        });
    }
}