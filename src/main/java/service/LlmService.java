package service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.ConfigLoader;

/**
 * Servicio para interactuar con la API de OpenRouter (basada en GPT) y obtener
 * respuestas generadas a partir de un texto de usuario.
 * 
 * @author Esteban Martín González
 * @version 1.0
 * @since 2025
 */
public class LlmService {

    /**
     * Envía un texto al API de OpenRouter para generar una respuesta con GPT.
     * <p>
     * Obtiene la clave API desde la configuración, construye una petición HTTP POST
     * con el texto proporcionado, y procesa la respuesta JSON para extraer el
     * contenido generado o los mensajes de error.
     * </p>
     * 
     * @param texto Texto que se envía como prompt al modelo GPT.
     * @return Respuesta generada por el modelo, mensaje de error de la API, error
     *         HTTP o "ERROR" en caso de excepción.
     * @throws RuntimeException Si la variable de entorno OPENROUTER_API_KEY no está
     *                          definida.
     */
    public String oraculoDigital(String texto) {

        // Obtener la clave API desde configuración o variables de entorno
        String apiKey = ConfigLoader.getProperty("OPENROUTER_API_KEY");

        // Validar que la clave API no esté vacía o nula, si no, lanzar excepción
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("La variable de entorno OPENROUTER_API_KEY no está definida.");
        }

        // URL del endpoint para solicitudes de chat completions
        String url = "https://openrouter.ai/api/v1/chat/completions";

        // Construir el cuerpo JSON de la petición, con el prompt enviado por parámetro
        // Se reemplazan comillas dobles en el texto para evitar errores JSON
        String jsonInput = String.format("""
                {
                  "model": "openai/gpt-4o",
                  "messages": [{"role": "user", "content": "%s"}],
                  "max_tokens": 1024
                }
                """, texto.replace("\"", "\\\""));

        try {
            // Abrir conexión HTTP POST con la URL del API
            HttpURLConnection conexion = (HttpURLConnection) new URL(url).openConnection();

            conexion.setRequestMethod("POST"); // Método POST
            conexion.setRequestProperty("Authorization", "Bearer " + apiKey); // Header con clave API
            conexion.setRequestProperty("Content-Type", "application/json");   // Tipo de contenido JSON
            conexion.setDoOutput(true); // Permitir enviar datos en el cuerpo de la petición

            // Escribir el cuerpo JSON en la conexión (enviar la solicitud)
            try (OutputStream output = conexion.getOutputStream()) {
                output.write(jsonInput.getBytes("UTF-8"));
            }

            // Obtener el código de respuesta HTTP
            int responseCode = conexion.getResponseCode();

            // Seleccionar el flujo de datos para leer: inputStream si éxito, errorStream si no
            InputStream responseStream = (responseCode >= 200 && responseCode < 300)
                    ? conexion.getInputStream()
                    : conexion.getErrorStream();

            // Leer la respuesta del API línea por línea
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"))) {
                StringBuilder respuesta = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    respuesta.append(linea.trim());
                }

                // Parsear la respuesta JSON
                JsonObject json = JsonParser.parseString(respuesta.toString()).getAsJsonObject();

                // Si la respuesta fue exitosa (código 2xx), extraer el contenido generado
                if (responseCode >= 200 && responseCode < 300) {
                    return "Contenido:\n\n" +
                            json.getAsJsonArray("choices")
                                .get(0).getAsJsonObject()
                                .getAsJsonObject("message")
                                .get("content").getAsString();
                }
                // Si hay un error reportado en el JSON, devolver mensaje de error
                else if (json.has("error")) {
                    return "Error de la API: " +
                            json.getAsJsonObject("error")
                                .get("message").getAsString();
                }
                // Si no, devolver un error HTTP genérico con código y respuesta
                else {
                    return "Error HTTP: código " + responseCode + "\n\nRespuesta: " + respuesta.toString();
                }
            }

        } catch (Exception e) {
            // En caso de cualquier excepción, imprimir el mensaje de error y devolver "ERROR"
            System.err.println("Excepción capturada: " + e.getMessage());
            return "ERROR";
        }
    }

}