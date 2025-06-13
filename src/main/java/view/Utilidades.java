package view;

import java.util.Scanner;

/**
 * Utilidades para lectura de datos desde consola. Proporciona métodos para
 * solicitar enteros, números decimales y cadenas con diferentes validaciones.
 * <p>
 * Utiliza un único Scanner para toda la clase.
 * </p>
 * 
 * @author Esteban Martín González
 * @version 1.1
 * @since 2025
 */
public class Utilidades {

    // Scanner para leer entrada desde consola (único para toda la clase)
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Solicita un entero al usuario mostrando un mensaje.
     * Repite la solicitud hasta que el usuario ingrese un entero válido y no vacío.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Entero ingresado por el usuario
     */
    public static int pedirEntero(String mensaje) {
        int valor = 0;
        boolean error;
        do {
            error = false;
            System.out.print(mensaje);
            String entrada = scan.nextLine();
            if (entrada.trim().isEmpty()) {
                System.out.println("[ERROR] El valor no puede estar vacío.\n");
                error = true;
            } else {
                try {
                    valor = Integer.parseInt(entrada);
                } catch (Exception e) {
                    System.out.println("[ERROR] Valor incorrecto\n");
                    error = true;
                }
            }
        } while (error);
        return valor;
    }

    /**
     * Solicita un entero positivo (>= 0) al usuario mostrando un mensaje.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Entero positivo ingresado por el usuario
     */
    public static int pedirEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = pedirEntero(mensaje);
            if (valor < 0) {
                System.out.println("[ERROR] El valor no puede ser negativo.\n");
            }
        } while (valor < 0);
        return valor;
    }

    /**
     * Solicita un número decimal (double) al usuario mostrando un mensaje.
     * Repite la solicitud hasta que el usuario ingrese un número válido y no vacío.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Double ingresado por el usuario
     */
    public static double pedirDouble(String mensaje) {
        double valor = 0;
        boolean error;
        do {
            error = false;
            System.out.print(mensaje);
            String entrada = scan.nextLine();
            if (entrada.trim().isEmpty()) {
                System.out.println("[ERROR] El valor no puede estar vacío.\n");
                error = true;
            } else {
                try {
                    valor = Double.parseDouble(entrada);
                } catch (Exception e) {
                    System.out.println("[ERROR] Valor incorrecto\n");
                    error = true;
                }
            }
        } while (error);
        return valor;
    }

    /**
     * Solicita un número decimal positivo (>= 0) al usuario mostrando un mensaje.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Double positivo ingresado por el usuario
     */
    public static double pedirDoublePositivo(String mensaje) {
        double valor;
        do {
            valor = pedirDouble(mensaje);
            if (valor < 0) {
                System.out.println("[ERROR] El valor no puede ser negativo.\n");
            }
        } while (valor < 0);
        return valor;
    }

    /**
     * Solicita una cadena de texto al usuario mostrando un mensaje.
     * Repite la solicitud si la entrada está vacía.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Cadena ingresada por el usuario
     */
    public static String pedirString(String mensaje) {
        String valor = "";
        boolean error;
        do {
            error = false;
            System.out.print(mensaje);
            try {
                valor = scan.nextLine();
                if (valor.trim().isEmpty()) {
                    System.out.println("[ERROR] El texto no puede estar vacío.\n");
                    error = true;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Valor incorrecto\n");
                error = true;
            }
        } while (error);
        return valor;
    }

    /**
     * Solicita texto que solo contenga letras y espacios al usuario mostrando un mensaje.
     * Repite la solicitud si está vacío o contiene caracteres inválidos.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Cadena solo con letras y espacios
     */
    public static String pedirText(String mensaje) {
        String valor = "";
        boolean error;
        do {
            error = false;
            System.out.print(mensaje);
            valor = scan.nextLine();
            if (valor.trim().isEmpty()) {
                System.out.println("[ERROR] El texto no puede estar vacío.\n");
                error = true;
            } else if (!valor.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
                System.out.println("[ERROR] Solo se permiten letras y espacios, no números ni caracteres especiales.\n");
                error = true;
            }
        } while (error);
        return valor;
    }

    /**
     * Solicita un email válido al usuario mostrando un mensaje.
     * Repite la solicitud mientras el formato no cumpla con un patrón básico de email o esté vacío.
     * 
     * @param mensaje Texto que se muestra antes de la lectura
     * @return Email validado por regex
     */
    public static String pedirEmail(String mensaje) {
        String email;
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        do {
            System.out.print(mensaje);
            email = scan.nextLine();
            if (email.trim().isEmpty()) {
                System.out.println("[ERROR] El email no puede estar vacío.\n");
            } else if (!email.matches(regex)) {
                System.out.println("[ERROR] Formato de email inválido. Intenta de nuevo.\n");
                email = "";
            }
        } while (email.trim().isEmpty() || !email.matches(regex));
        return email;
    }
}