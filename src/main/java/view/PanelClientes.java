package view;

import model.ClienteOtaku;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase que crea y gestiona la interfaz gráfica para la sección de clientes.
 * Proporciona una tabla con CRUD completo, filtros por ID y email, y manejo
 * de visualización y edición directa en la tabla.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class PanelClientes {

    /**
     * Crea y devuelve un JPanel con la interfaz para gestionar clientes.
     * Incluye tabla con clientes, filtros, botones para CRUD y edición en línea.
     *
     * @param frame          ventana principal donde se mostrará el panel
     * @param controlador    instancia del controlador para acceder a datos
     * @return JPanel configurado con la interfaz de clientes
     */
    public static JPanel crear(JFrame frame, ControllerGrafico controlador) {

        // Definición de columnas para la tabla clientes (agregamos "Fecha")
        String[] columnas = { "ID", "Nombre", "Email", "Teléfono", "Fecha" };

        // Crear modelo de tabla con las columnas definidas (solo algunas columnas son editables)
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo permitir edición en columnas Nombre, Email y Teléfono
                return column == 1 || column == 2 || column == 3;
            }
        };

        // Crear la tabla JTable con el modelo
        JTable tabla = new JTable(modelo);

        // Formateador de fecha para mostrar LocalDate como string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Cargar todos los clientes en la tabla al inicio
        MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosClientes(),
                c -> new Object[] {
                        c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(),
                        c.getFechaRegistro().format(formatter)
                });

        // Panel principal con layout BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 123, 182));  // Cambiar color de fondo del panel principal
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);  // Añadir la tabla con scroll al centro

        // Panel superior para filtros y botones
        JPanel filtros = new JPanel();
        filtros.setBackground(new Color(45, 123, 182));  // Color de fondo del panel de filtros
        JTextField txtId = new JTextField(5), txtEmail = new JTextField(10);
        JButton buscarId = new JButton("Buscar ID"), buscarEmail = new JButton("Buscar Email"),
                mostrarTodos = new JButton("Mostrar Todo"), agregar = new JButton("Agregar");

        // Estilo para botones
        Color btnColor = new Color(30, 60, 120);
        Color btnTexto = Color.WHITE;
        for (JButton btn : new JButton[]{buscarId, buscarEmail, mostrarTodos, agregar}) {
            btn.setBackground(btnColor);
            btn.setForeground(btnTexto);
            btn.setFocusPainted(false);
        }

        // Estilo para campos de texto
        Color campoFondo = new Color(220, 230, 245);
        Color campoTexto = Color.DARK_GRAY;
        for (JTextField txt : new JTextField[]{txtId, txtEmail}) {
            txt.setBackground(campoFondo);
            txt.setForeground(campoTexto);
        }

        // Añadir componentes al panel de filtros
        filtros.add(new JLabel("ID:")); filtros.add(txtId); filtros.add(buscarId);
        filtros.add(new JLabel("Email:")); filtros.add(txtEmail); filtros.add(buscarEmail);
        filtros.add(mostrarTodos); filtros.add(agregar);
        panel.add(filtros, BorderLayout.NORTH);

        // Acción para buscar cliente por ID
        buscarId.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                MetodosInterfaz.buscarPorId(id, modelo, controlador::obtenerClientePorId,
                        c -> new Object[] {
                                c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(),
                                c.getFechaRegistro().format(formatter)
                        },
                        "Cliente no encontrado.", frame);
            } catch (NumberFormatException ex) {
                MetodosInterfaz.mostrarMensaje("ID debe ser numérico.", frame);
            }
        });

        // Acción para buscar clientes por Email
        buscarEmail.addActionListener(e -> {
            List<ClienteOtaku> res = controlador.buscarClientePorEmail(txtEmail.getText().trim());
            MetodosInterfaz.actualizarTabla(modelo, res,
                    c -> new Object[] {
                            c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(),
                            c.getFechaRegistro().format(formatter)
                    });
        });

        // Acción para mostrar todos los clientes
        mostrarTodos.addActionListener(e -> MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosClientes(),
                c -> new Object[] {
                        c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(),
                        c.getFechaRegistro().format(formatter)
                }));

        // Acción para agregar un nuevo cliente a través de un formulario
        agregar.addActionListener(e -> {
            FormularioCliente form = new FormularioCliente(frame);
            form.setVisible(true);
            if (form.isConfirmado()) {
                controlador.agregarCliente(new ClienteOtaku(form.getNombre(), form.getEmail(), form.getTelefono()));
                // Actualizar tabla tras agregar el nuevo cliente
                MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosClientes(),
                        c -> new Object[] {
                                c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(),
                                c.getFechaRegistro().format(formatter)
                        });
                MetodosInterfaz.mostrarMensaje("Cliente agregado correctamente.", frame);
            }
        });

        // Listener para detectar ediciones en la tabla y actualizar el cliente correspondiente
        modelo.addTableModelListener(e -> {
            if (e.getType() != TableModelEvent.UPDATE) return;

            int fila = e.getFirstRow(), col = e.getColumn();
            int id = (int) modelo.getValueAt(fila, 0);
            ClienteOtaku c = controlador.obtenerClientePorId(id);

            try {
                switch (col) {
                    case 1 -> {
                        String nombre = ((String) modelo.getValueAt(fila, col)).trim();
                        if (nombre.isEmpty())
                            throw new IllegalArgumentException("El nombre no puede estar vacío");
                        c.setNombre(nombre);
                    }
                    case 2 -> {
                        String email = ((String) modelo.getValueAt(fila, col)).trim();
                        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
                            throw new IllegalArgumentException("Email inválido");
                        c.setEmail(email);
                    }
                    case 3 -> {
                        String telStr = modelo.getValueAt(fila, col).toString().trim();
                        if (!telStr.matches("\\d+"))
                            throw new IllegalArgumentException("Teléfono solo debe contener números");
                        int telefono = Integer.parseInt(telStr);
                        if (telefono <= 0)
                            throw new IllegalArgumentException("Teléfono debe ser positivo");
                        c.setTelefono(telefono);
                    }
                }
                // Guardar cambios
                controlador.actualizarCliente(c);
            } catch (Exception ex) {
                MetodosInterfaz.mostrarMensaje("Error al actualizar cliente: " + ex.getMessage(), frame);
                MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosClientes(),
                        cli -> new Object[] {
                                cli.getId(), cli.getNombre(), cli.getEmail(), cli.getTelefono(),
                                cli.getFechaRegistro().format(formatter)
                        });
            }
        });

        // Botón para eliminar cliente seleccionado
        JButton eliminar = new JButton("Eliminar");
        eliminar.setBackground(btnColor);
        eliminar.setForeground(btnTexto);
        eliminar.setFocusPainted(false);
        eliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                MetodosInterfaz.mostrarMensaje("Selecciona un cliente para eliminar.", frame);
                return;
            }
            UIManager.put("OptionPane.yesButtonText", "Si");
            UIManager.put("OptionPane.noButtonText", "No");
            int id = (int) modelo.getValueAt(fila, 0);
            if (JOptionPane.showConfirmDialog(frame, "¿Eliminar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                controlador.eliminarCliente(id);
                MetodosInterfaz.actualizarTabla(modelo, controlador.obtenerTodosClientes(),
                        c -> new Object[] {
                                c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(),
                                c.getFechaRegistro().format(formatter)
                        });
            }
        });

        // Panel inferior para colocar el botón eliminar
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