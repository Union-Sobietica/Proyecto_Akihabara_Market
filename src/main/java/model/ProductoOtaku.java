package model;

/**
 * Representa un producto con atributos básicos como nombre, categoría, precio y stock.
 * Proporciona métodos para acceder y modificar dichos atributos, así como una representación
 * legible mediante el método toString().
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class ProductoOtaku {

    // Atributos privados de la clase

    private int id;            // Identificador único del producto
    private String nombre;     // Nombre del producto
    private String categoria;  // Categoría a la que pertenece el producto
    private double precio;     // Precio del producto
    private int stock;         // Cantidad disponible en inventario

    /**
     * Constructor vacío por defecto.
     */
    public ProductoOtaku() {
    }

    /**
     * Constructor que inicializa el producto con nombre, categoría, precio y stock.
     *
     * @param nombre    Nombre del producto
     * @param categoria Categoría a la que pertenece el producto
     * @param precio    Precio del producto
     * @param stock     Cantidad disponible en inventario
     */
    public ProductoOtaku(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Obtiene el ID del producto.
     *
     * @return Identificador único del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el ID del producto.
     *
     * @param id Identificador único a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del producto.
     *
     * @param nombre Nuevo nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return Categoría a la que pertenece el producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Asigna la categoría del producto.
     *
     * @param categoria Nueva categoría del producto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return Precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Asigna el precio del producto.
     *
     * @param precio Nuevo precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el stock disponible del producto.
     *
     * @return Cantidad de unidades disponibles en inventario
     */
    public int getStock() {
        return stock;
    }

    /**
     * Asigna el stock disponible del producto.
     *
     * @param stock Nueva cantidad de unidades en inventario
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Representación en cadena del objeto ProductoOtaku en formato legible.
     *
     * @return Cadena con los datos del producto formateados
     */
    @Override
    public String toString() {
        return String.format(
            "\n--- Producto Otaku ---\n" +
            "ID:        %d\n" +
            "Nombre:    %s\n" +
            "Categoría: %s\n" +
            "Precio:    €%.2f\n" +
            "Stock:     %d unidades\n",
            id, nombre, categoria, precio, stock
        );
    }

}