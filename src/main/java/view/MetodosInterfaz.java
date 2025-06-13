package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * Utilidades estáticas para configurar y manipular componentes de la interfaz gráfica.
 * Incluye métodos para búsqueda, actualización de tablas, estilos y cuadros de diálogo.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class MetodosInterfaz {

    static {
        // Colores azulados personalizados para la interfaz
        UIManager.put("OptionPane.background", new Color(225, 240, 255));
        UIManager.put("Panel.background", new Color(225, 240, 255));
        UIManager.put("OptionPane.messageForeground", new Color(0, 70, 130));
        UIManager.put("Button.background", new Color(190, 220, 250));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
    }

    /**
     * Busca un elemento por ID y lo muestra en el modelo de tabla dado.
     * 
     * @param <T>             Tipo del objeto a buscar
     * @param id              Identificador del objeto a buscar
     * @param modelo          Modelo de tabla donde mostrar el resultado
     * @param obtener         Función que recibe un ID y devuelve el objeto
     * @param mapper          Función que convierte el objeto en fila de tabla
     * @param msgNoEncontrado Mensaje a mostrar si no se encuentra el objeto
     * @param frame           Ventana padre para los cuadros de diálogo
     */
    public static <T> void buscarPorId(int id, DefaultTableModel modelo, Function<Integer, T> obtener,
                                       Function<T, Object[]> mapper, String msgNoEncontrado, JFrame frame) {
        modelo.setRowCount(0);
        T obj = obtener.apply(id);
        if (obj != null)
            modelo.addRow(mapper.apply(obj));
        else
            mostrarMensaje(msgNoEncontrado, frame);
    }

    /**
     * Actualiza el contenido de la tabla con la lista de objetos proporcionada.
     * 
     * @param <T>    Tipo de los objetos en la lista
     * @param modelo Modelo de tabla a actualizar
     * @param lista  Lista de objetos cuyos datos se mostrarán
     * @param mapper Función que convierte cada objeto en fila de tabla
     */
    public static <T> void actualizarTabla(DefaultTableModel modelo, List<T> lista, Function<T, Object[]> mapper) {
        modelo.setRowCount(0);
        lista.forEach(e -> modelo.addRow(mapper.apply(e)));
    }

    /**
     * Crea un modelo de tabla con las columnas especificadas.
     * Las celdas de la primera columna no serán editables.
     * 
     * @param columnas Nombres de las columnas del modelo
     * @return Instancia de DefaultTableModel configurada
     */
    public static DefaultTableModel crearModelo(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 0;
            }
        };
    }

    /**
     * Solicita al usuario un texto mediante un cuadro de diálogo estilizado.
     * 
     * @param msg   Mensaje que describe el texto a ingresar
     * @param frame Ventana padre para el cuadro de diálogo
     * @return Texto ingresado por el usuario, o null si se canceló o quedó vacío
     */
    public static String solicitarTexto(String msg, JFrame frame) {
        return estilizarInput(msg, frame);
    }

    /**
     * Muestra un mensaje informativo al usuario en un cuadro de diálogo.
     * 
     * @param msg   Mensaje a mostrar
     * @param frame Ventana padre para el cuadro de diálogo
     */
    public static void mostrarMensaje(String msg, JFrame frame) {
        JOptionPane.showMessageDialog(frame, msg, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Crea y muestra un cuadro de diálogo de entrada de texto con estilo personalizado.
     * 
     * @param msg   Mensaje que describe el campo de entrada
     * @param frame Ventana padre para el cuadro de diálogo
     * @return Texto ingresado, o null si se canceló o quedó vacío
     */
    private static String estilizarInput(String msg, JFrame frame) {
        JTextField campo = new JTextField();
        campo.setBackground(new Color(240, 250, 255));
        campo.setForeground(Color.BLACK);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        int result = JOptionPane.showConfirmDialog(frame, new Object[]{msg, campo},
                "Ingresar texto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String input = campo.getText();
            return (input == null || input.trim().isEmpty()) ? null : input.trim();
        }
        return null;
    }

    /**
     * Aplica estilos de color y fuente al encabezado y celdas de la tabla.
     * Centra el contenido y ajusta la altura de las filas.
     * 
     * @param tabla Instancia de JTable a estilizar
     */
    public static void aplicarEstiloTabla(JTable tabla) {
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(0, 120, 215));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(24);

        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
        }
    }
}