package view;

import javax.swing.*;

import dao.ClienteDAO;

import java.awt.*;

/**
 * Formulario de diálogo para ingresar datos de un nuevo cliente.
 * Proporciona campos para nombre, email y teléfono, y valida la entrada
 * antes de confirmarla o cancelarla.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class FormularioCliente extends JDialog {

    private ClienteDAO cliente = new ClienteDAO();

    // Campos de texto para ingresar los datos del cliente
    private JTextField txtNombre = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    private JTextField txtTelefono = new JTextField(10);

    // Variable para saber si el usuario confirmó el formulario
    private boolean confirmado = false;

    /**
     * Crea un diálogo modal para agregar un nuevo cliente.
     * Configura el layout, estilos y acciones de los componentes.
     * 
     * @param parent Ventana padre sobre la que se centra el diálogo
     */
    public FormularioCliente(JFrame parent) {
        super(parent, "Agregar Cliente", true);
        setLayout(new GridLayout(4, 2, 10, 10));
        getContentPane().setBackground(new Color(45, 123, 182));

        // Etiquetas y estilo
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblNombre.setForeground(Color.WHITE);
        lblEmail.setForeground(Color.WHITE);
        lblTelefono.setForeground(Color.WHITE);

        // Estilo de campos de texto
        Color campoFondo = new Color(220, 230, 245);
        Color textoCampo = Color.DARK_GRAY;
        txtNombre.setBackground(campoFondo);
        txtNombre.setForeground(textoCampo);
        txtEmail.setBackground(campoFondo);
        txtEmail.setForeground(textoCampo);
        txtTelefono.setBackground(campoFondo);
        txtTelefono.setForeground(textoCampo);

        add(lblNombre);
        add(txtNombre);
        add(lblEmail);
        add(txtEmail);
        add(lblTelefono);
        add(txtTelefono);

        // Botones
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.setBackground(new Color(30, 60, 120));
        btnAceptar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(30, 60, 120));
        btnCancelar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);
        btnCancelar.setFocusPainted(false);

        btnAceptar.addActionListener(e -> {
            if (validarCampos()) {
                confirmado = true;
                setVisible(false);
            }
        });
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
     * Comprueba que nombre y email no estén vacíos, que el email tenga un formato válido
     * y no exista ya en la base de datos, y que el teléfono sea un entero positivo.
     * 
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefonoStr = txtTelefono.getText().trim();

        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y Email son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            JOptionPane.showMessageDialog(this, "Email no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (cliente.verificarEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email no es válido, el email ya esta existe.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int telefono = Integer.parseInt(telefonoStr);
            if (telefono <= 0) {
                JOptionPane.showMessageDialog(this, "Teléfono debe ser un número entero positivo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Teléfono debe ser un número entero válido.", "Error",
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
     * Obtiene el email ingresado en el formulario.
     * 
     * @return Email ingresado, sin espacios al inicio ni al final
     */
    public String getEmail() {
        return txtEmail.getText().trim();
    }

    /**
     * Obtiene el teléfono ingresado en el formulario.
     * 
     * @return Teléfono ingresado como entero
     */
    public int getTelefono() {
        return Integer.parseInt(txtTelefono.getText().trim());
    }
}