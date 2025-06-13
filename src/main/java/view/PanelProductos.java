package view;

import model.ProductoOtaku;
import service.LlmService;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clase que crea y devuelve un JPanel con la interfaz para gestionar productos.
 * Incluye tabla con productos, filtros, botones para CRUD y funciones IA.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class PanelProductos {

    /**
     * Crea y configura un JPanel para la sección de productos, con su tabla,
     * controles de búsqueda, botones para agregar, eliminar, actualizar
     * e invocar al servicio de IA para descripciones y sugerencias de categoría.
     *
     * @param frame         Ventana principal donde se mostrará el panel
     * @param controlador   Instancia del controlador para acceder a datos
     * @param llmService    Servicio para consultas con IA (chatbot, generación de texto)
     * @return JPanel       Panel completo listo para mostrar en la interfaz
     */
    public static JPanel crear(JFrame frame, ControllerGrafico controlador, LlmService llmService) {

        // Definición de las columnas de la tabla de productos
        String[] columnas = { "ID", "Nombre", "Categoría", "Precio", "Stock" };
        DefaultTableModel modelo = MetodosInterfaz.crearModelo(columnas);
        JTable tabla = new JTable(modelo);

        // Cargar todos los productos inicialmente
        MetodosInterfaz.actualizarTabla(modelo,
            controlador.obtenerTodosProductos(),
            p -> new Object[]{ p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock() }
        );

        // Panel principal con layout y estilo de fondo
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 123, 182));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de filtros y botones
        JPanel filtros = new JPanel();
        filtros.setBackground(new Color(45, 123, 182));
        JTextField txtId = new JTextField(5), txtNombre = new JTextField(10);
        JButton buscarId       = new JButton("Buscar ID"),
                buscarNombre   = new JButton("Buscar Nombre"),
                mostrarTodos   = new JButton("Mostrar Todo"),
                agregar        = new JButton("Agregar"),
                descIA         = new JButton("Descripción IA"),
                catIA          = new JButton("Sugerir Categoría IA");

        // Estilos de los botones
        Color btnColor = new Color(30, 60, 120), btnTexto = Color.WHITE;
        for (JButton btn : new JButton[]{buscarId, buscarNombre, mostrarTodos, agregar, descIA, catIA}) {
            btn.setBackground(btnColor);
            btn.setForeground(btnTexto);
            btn.setFocusPainted(false);
        }

        // Estilos de los campos de texto
        Color campoFondo = new Color(220, 230, 245), campoTexto = Color.DARK_GRAY;
        for (JTextField txt : new JTextField[]{txtId, txtNombre}) {
            txt.setBackground(campoFondo);
            txt.setForeground(campoTexto);
        }

        // Añadir componentes al panel de filtros
        filtros.add(new JLabel("ID:"));        filtros.add(txtId);        filtros.add(buscarId);
        filtros.add(new JLabel("Nombre:"));    filtros.add(txtNombre);    filtros.add(buscarNombre);
        filtros.add(mostrarTodos);             filtros.add(agregar);       filtros.add(descIA);
        filtros.add(catIA);
        panel.add(filtros, BorderLayout.NORTH);

        // Buscar por ID
        buscarId.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                MetodosInterfaz.buscarPorId(id, modelo, controlador::obtenerProductoPorId,
                    p -> new Object[]{ p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock() },
                    "Producto no encontrado.", frame);
            } catch (NumberFormatException ex) {
                MetodosInterfaz.mostrarMensaje("ID debe ser numérico.", frame);
            }
        });

        // Buscar por nombre
        buscarNombre.addActionListener(e -> {
            List<ProductoOtaku> res = controlador.buscarProductoPorNombre(txtNombre.getText().trim());
            MetodosInterfaz.actualizarTabla(modelo, res,
                p -> new Object[]{ p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock() });
        });

        // Mostrar todos
        mostrarTodos.addActionListener(e ->
            MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosProductos(),
                p -> new Object[]{ p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock() })
        );

        // Agregar nuevo producto
        agregar.addActionListener(e -> {
            FormularioProducto form = new FormularioProducto(frame);
            form.setVisible(true);
            if (form.isConfirmado()) {
                controlador.agregarProducto(new ProductoOtaku(
                    form.getNombre(), form.getCategoria(), form.getPrecio(), form.getStock()
                ));
                MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosProductos(),
                    p -> new Object[]{ p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock() });
                MetodosInterfaz.mostrarMensaje("Producto agregado correctamente.", frame);
            }
        });

        // Descripción IA
        descIA.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                MetodosInterfaz.mostrarMensaje("Selecciona un producto para generar la descripción.", frame);
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            ProductoOtaku p = controlador.obtenerProductoPorId(id);
            String prompt = "En español: Genera una descripción de marketing breve de no más de 15 palabras y atractiva para el producto otaku: "
                          + p.getNombre() + " de la categoría " + p.getCategoria() + ".";
            MetodosInterfaz.mostrarMensaje(llmService.oraculoDigital(prompt), frame);
        });

        // Sugerir categoría IA
        catIA.addActionListener(e -> {
            String nombre = MetodosInterfaz.solicitarTexto("Nombre del producto:", frame);
            if (nombre == null) return;
            String prompt = "En español: Para un producto otaku llamado " + nombre
                          + ", sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro. "
                          + "Responde en este formato: Categoría: 'Nombre de la categoría'. "
                          + "Comprueba que si el nombre del producto es extraño la respuesta sea Desconocido.";
            MetodosInterfaz.mostrarMensaje(llmService.oraculoDigital(prompt), frame);
        });

        // Manejo de ediciones en tabla para actualizar en la BD
        modelo.addTableModelListener(e -> {
            if (e.getType() != TableModelEvent.UPDATE) return;
            int fila = e.getFirstRow(), col = e.getColumn();
            int id = (int) modelo.getValueAt(fila, 0);
            ProductoOtaku p = controlador.obtenerProductoPorId(id);
            try {
                switch (col) {
                    case 1 -> {
                        String nombre = ((String) modelo.getValueAt(fila, col)).trim();
                        if (nombre.isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío");
                        p.setNombre(nombre);
                    }
                    case 2 -> {
                        String categoria = ((String) modelo.getValueAt(fila, col)).trim();
                        if (categoria.isEmpty()) throw new IllegalArgumentException("La categoría no puede estar vacía");
                        p.setCategoria(categoria);
                    }
                    case 3 -> {
                        double precio = Double.parseDouble(modelo.getValueAt(fila, col).toString());
                        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
                        p.setPrecio(precio);
                    }
                    case 4 -> {
                        int stock = Integer.parseInt(modelo.getValueAt(fila, col).toString());
                        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
                        p.setStock(stock);
                    }
                }
                controlador.actualizarProducto(p);
            } catch (Exception ex) {
                MetodosInterfaz.mostrarMensaje("Error al actualizar producto: " + ex.getMessage(), frame);
                MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosProductos(),
                    prod -> new Object[]{ prod.getId(), prod.getNombre(), prod.getCategoria(), prod.getPrecio(), prod.getStock() });
            }
        });

        // Botón eliminar producto
        JButton eliminar = new JButton("Eliminar");
        eliminar.setBackground(btnColor);
        eliminar.setForeground(btnTexto);
        eliminar.setFocusPainted(false);
        eliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                MetodosInterfaz.mostrarMensaje("Selecciona un producto para eliminar.", frame);
                return;
            }
            UIManager.put("OptionPane.yesButtonText", "Si");
            UIManager.put("OptionPane.noButtonText", "No");
            int id = (int) modelo.getValueAt(fila, 0);
            if (JOptionPane.showConfirmDialog(frame, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
                controlador.eliminarProducto(id);
                MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosProductos(),
                    p -> new Object[]{ p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock() });
            }
        });

        // Panel inferior con botón eliminar
        JPanel botones = new JPanel();
        botones.setBackground(new Color(45, 123, 182));
        botones.add(eliminar);
        panel.add(botones, BorderLayout.SOUTH);

        // Estilo visual de la tabla
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        tabla.getTableHeader().setBackground(new Color(30, 60, 120));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(100, 150, 200));
        tabla.setSelectionForeground(Color.WHITE);

        return panel;
    }
}