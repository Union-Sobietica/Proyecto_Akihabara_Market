package view;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario de diálogo para ingresar datos de un nuevo producto.
 * Proporciona campos para nombre, categoría, precio y stock, y valida la entrada
 * antes de confirmarla o cancelarla.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class FormularioProducto extends JDialog {

    // Campos de texto para ingresar los datos del producto
    private JTextField txtNombre = new JTextField(15);
    private JTextField txtCategoria = new JTextField(15);
    private JTextField txtPrecio = new JTextField(10);
    private JTextField txtStock = new JTextField(5);
    
    // Variable para indicar si el formulario fue confirmado
    private boolean confirmado = false;

    /**
     * Constructor del formulario modal para agregar un producto.
     * Configura layout, estilos y acciones de los componentes.
     * 
     * @param parent Ventana padre sobre la que se centra el diálogo
     */
    public FormularioProducto(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(45, 123, 182));

        // Etiquetas con texto blanco para buen contraste
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblCategoria = new JLabel("Categoría:");
        JLabel lblPrecio = new JLabel("Precio:");
        JLabel lblStock = new JLabel("Stock:");
        lblNombre.setForeground(Color.WHITE);
        lblCategoria.setForeground(Color.WHITE);
        lblPrecio.setForeground(Color.WHITE);
        lblStock.setForeground(Color.WHITE);

        // Estilo de campos de texto
        Color campoFondo = new Color(220, 230, 245);
        Color textoCampo = Color.DARK_GRAY;
        txtNombre.setBackground(campoFondo);
        txtNombre.setForeground(textoCampo);
        txtCategoria.setBackground(campoFondo);
        txtCategoria.setForeground(textoCampo);
        txtPrecio.setBackground(campoFondo);
        txtPrecio.setForeground(textoCampo);
        txtStock.setBackground(campoFondo);
        txtStock.setForeground(textoCampo);

        add(lblNombre);
        add(txtNombre);
        add(lblCategoria);
        add(txtCategoria);
        add(lblPrecio);
        add(txtPrecio);
        add(lblStock);
        add(txtStock);

        // Botones para aceptar o cancelar la entrada de datos
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.setBackground(new Color(30, 60, 120));
        btnAceptar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(30, 60, 120));
        btnCancelar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);
        btnCancelar.setFocusPainted(false);

        // Acción para el botón Aceptar: valida los campos antes de cerrar
        btnAceptar.addActionListener(e -> {
            if (validarCampos()) {
                confirmado = true;
                setVisible(false);
            }
        });

        // Acción para el botón Cancelar: no confirma y cierra el diálogo
        btnCancelar.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        add(btnAceptar);
        add(btnCancelar);

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Valida los datos ingresados en el formulario.
     * Comprueba que nombre y categoría no estén vacíos, que el precio sea un número
     * decimal positivo y que el stock sea un entero positivo.
     * 
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        if (nombre.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y Categoría son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número positivo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio debe ser un número decimal válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock debe ser un número entero positivo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock debe ser un número entero válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Indica si el formulario fue confirmado con "Aceptar".
     * 
     * @return true si el usuario confirmó, false si canceló
     */
    public boolean isConfirmado() {
        return confirmado;
    }

    /**
     * Obtiene el nombre ingresado en el formulario.
     * 
     * @return Nombre ingresado, sin espacios al inicio ni al final
     */
    public String getNombre() {
        return txtNombre.getText().trim();
    }

    /**
     * Obtiene la categoría ingresada en el formulario.
     * 
     * @return Categoría ingresada, sin espacios al inicio ni al final
     */
    public String getCategoria() {
        return txtCategoria.getText().trim();
    }

    /**
     * Obtiene el precio ingresado en el formulario.
     * 
     * @return Precio ingresado como double
     */
    public double getPrecio() {
        return Double.parseDouble(txtPrecio.getText().trim());
    }

    /**
     * Obtiene el stock ingresado en el formulario.
     * 
     * @return Stock ingresado como entero
     */
    public int getStock() {
        return Integer.parseInt(txtStock.getText().trim());
    }
}