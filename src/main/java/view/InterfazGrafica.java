package view;

import service.LlmService;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Interfaz gráfica principal de la aplicación Gestor Otaku.
 * Proporciona dos pestañas para gestionar productos y clientes,
 * y utiliza un servicio de IA para funciones avanzadas de descripción
 * y categorización de productos.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class InterfazGrafica extends JFrame {

    // Controlador para manejar la lógica y acceso a datos
    private final ControllerGrafico controlador = new ControllerGrafico();
    // Servicio para generar contenido con IA (modelo de lenguaje)
    private final LlmService llmService = new LlmService();

    /**
     * Construye y configura la ventana principal de la aplicación.
     * <p>
     * Ajusta título, tamaño, comportamiento de cierre, y crea un
     * panel con pestañas para acceder a las secciones de productos
     * y clientes, aplicando estilos personalizados.
     * </p>
     */
    public InterfazGrafica() {
        // Configuración básica de la ventana principal

        setTitle("Gestor Otaku"); // Título de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cerrar aplicación al cerrar ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tamaño inicial de la ventana
        setMinimumSize(new Dimension(1000, 300)); // tamaño mínimo
        setLocationRelativeTo(null); // Centrar ventana en pantalla
        setAlwaysOnTop(true);  // Mantener encima de otras ventanas
        toFront();             // Llevar al frente
        requestFocus();        // Solicitar el foco
        setAlwaysOnTop(false); // Restaurar comportamiento normal si ya está al frente

        // Crear un panel con pestañas para organizar la interfaz
        JTabbedPane tabs = new JTabbedPane();

        // Cambiar apariencia del fondo del tabbedpane
        tabs.setBackground(new Color(30, 60, 120));
        tabs.setForeground(Color.WHITE); // color texto pestañas

        // Cambiar apariencia de las pestañas
        tabs.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = new Color(70, 130, 180); // azul claro al pasar mouse
                lightHighlight = highlight;
                shadow = new Color(20, 40, 80); // sombra azul oscuro
                darkShadow = new Color(10, 20, 40);
            }

            @Override
            protected void paintTabBackground(java.awt.Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(new Color(70, 130, 180)); // Pestaña activa
                } else {
                    g.setColor(new Color(30, 60, 120)); // Pestaña inactiva
                }
                g.fillRect(x, y, w, h);
            }
        });

        // Agregar pestaña "Productos" con su respectivo panel, pasando el frame, controlador y servicio IA
        tabs.addTab("Productos", PanelProductos.crear(this, controlador, llmService));
        // Agregar pestaña "Clientes" con su respectivo panel, pasando frame y controlador
        tabs.addTab("Clientes", PanelClientes.crear(this, controlador));

        // Añadir el panel de pestañas a la ventana principal
        add(tabs);

        // Cambiar el color de fondo de la ventana
        getContentPane().setBackground(new Color(45, 123, 182));

        // Hacer visible la ventana principal
        setVisible(true);
    }
}