package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class ConfigLoader {

	// Nombre del archivo de configuración que contiene las propiedades
	private static final String CONFIG_FILE = "config.properties";

	/**
	 * Método para obtener el valor de una propiedad dada su clave (key).
	 * 
	 * @param key La clave de la propiedad que se desea obtener
	 * @return El valor de la propiedad o null si no se pudo cargar el archivo o no existe la propiedad
	 */
	public static String getProperty(String key) {

		Properties props = new Properties();

		// Intentamos cargar el archivo de configuración
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {

			// Cargar las propiedades desde el archivo
			props.load(fis);

			// Retornar el valor asociado a la clave solicitada
			return props.getProperty(key);

		} catch (IOException e) {
			
			// Capturar errores de lectura y mostrar mensaje de error
			System.err.println("Error al cargar config.properties: " + e.getMessage());

			// Retornar null si hay error
			return null;
			
		}

	}

}