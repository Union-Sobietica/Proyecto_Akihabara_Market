package model;

import java.time.LocalDate;

/**
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class ClienteOtaku {
	
    // Atributos privados de la clase

    private int id;                     // Identificador único del cliente
    private String nombre;              // Nombre del cliente
    private String email;               // Email del cliente
    private int telefono;               // Teléfono del cliente
    private LocalDate fechaRegistro;    // Fecha de registro del cliente

    /**
     * Constructor vacío por defecto.
     */
    public ClienteOtaku() {
    }

    /**
     * Constructor que inicializa un cliente con nombre, email y teléfono.
     * 
     * @param nombre   Nombre del cliente
     * @param email    Email del cliente
     * @param telefono Teléfono del cliente
     */
    public ClienteOtaku(String nombre, String email, int telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Obtiene el ID del cliente.
     * 
     * @return Identificador único del cliente
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el ID del cliente.
     * 
     * @param id Identificador único a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente.
     * 
     * @return Nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del cliente.
     * 
     * @param nombre Nuevo nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el email del cliente.
     * 
     * @return Email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asigna el email del cliente.
     * 
     * @param email Nuevo email del cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el teléfono del cliente.
     * 
     * @return Teléfono del cliente
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Asigna el teléfono del cliente.
     * 
     * @param telefono Nuevo teléfono del cliente
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de registro del cliente.
     * 
     * @return Fecha de registro del cliente
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Asigna la fecha de registro del cliente.
     * 
     * @param fechaRegistro Nueva fecha de registro
     */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Representación en String del objeto ClienteOtaku en formato legible.
     * 
     * @return Cadena con los datos del cliente formateados
     */
    @Override
    public String toString() {
        return String.format(
            "\n--- Cliente Otaku ---\n" +
            "ID:                  %d\n" +
            "Nombre:              %s\n" +
            "Email:               %s\n" +
            "Teléfono:            %s\n" +
            "Fecha de Registro:   %s\n",
            id, nombre, email, telefono, fechaRegistro
        );
    }

}