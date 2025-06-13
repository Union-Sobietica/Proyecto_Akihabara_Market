# Proyecto Akihabara Market

## Descripción

Este proyecto es una aplicación de consola y gráfica en Java diseñada para la gestión de clientes y productos, con integración de IA para generar descripciones y sugerencias de categorías para productos. Incluye funcionalidades completas para CRUD de clientes y productos, y está preparada para conectarse a una base de datos MySQL.

---

## Autor

**Nombre:** Esteban Martín González

---

## Tecnologías Utilizadas

- Java SE 21
- MySQL
- Interfaz Principal de Consola
- OpenRouter API
- JDBC para conexión con base de datos
- Herramientas de compilación: javac, java

---

## Estructura del Proyecto

/proyecto-raiz <br>
│ <br>
├── src/main/java <br>
│ ├── config/ **- Clases que recoge los valores de config.properties** <br>
│ ├── controller/ **- Lógica de negocio y acceso a datos** <br>
│ ├── dao/ **- Clases con los métodos (ClientesDAO, ProductosDAO)** <br>
│ ├── model/ **- Clases modelo de datos (ClientesOtaku, ProductosOtaku)** <br>
│ ├── service/ **- Clase para gestionar la API** <br>
│ ├── sql/ **- Scripts SQL para creación y configuración de BD** <br>
│ ├── util/ **- Clases con datos de registro de ejemplo** <br>
│ ├── view/ **- Clases para la interfaz y utilidades** <br>
│ <br>
├── src/test/java <br>
│ ├── proyecto_Akihabara_Market/ **- Clases de tipo Junit para hacer pruebas** <br>
│ <br>
├── config.properties **- Archivo de configuración para BD y API Key** <br>
├── pom.xml **- Archivo para añadir las librerías/dependencias** <br>
├── README.md **- Documentación del proyecto** <br>
 <br>
---

## Configuración Requerida

### Base de Datos MySQL

Detalles de configuración:  
 - **Tendrás que crear un archivo llamado `config.properties` dentro del proyecto, a la altura de las principales carpetas, en el que tendrás que poner lo siguiente:**

  ```
  dbUrl = "Conexion a la base de datos"
  dbUser = "Nombre del usuario que controle la base de datos"
  dbPassword = "Contraseña del usuario que controle la base de datos"
  ```

En el paquete "sql" encontrarás un archivo llamado "crear_tabla.sql".  
Este script contiene el diseño de la base de datos y la creación de un usuario con permisos para gestionarla.  

Para usarlo:  
 - Copia el contenido del script.  
 - Pégalo y ejecútalo en tu gestor MySQL.

---

### Configuración de la API Key de OpenRouter

La clave API debe estar preconfigurada en el archivo "config.properties", su formato será el siguiente: 

```
  OPENROUTER_API_KEY = "API KEY"
```

**El archivo "config.properties" deberá quedar así:**

```
  dbUrl = "Conexion a la base de datos"
  dbUser = "Nombre del usuario que controle la base de datos"
  dbPassword = "Contraseña del usuario que controle la base de datos"
  OPENROUTER_API_KEY = "API KEY"
```

---

### Librerías y JARs Externos

```
    <dependencies>

		<!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<version>9.3.0</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.13.1</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params -->
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter-params</artifactId>
			<version>5.13.0</version>
			<scope>test</scope>
		</dependency>

	</dependencies>
```
---

## Cómo Compilar y Ejecutar

1. Copia el contenido del script "crear_tabla.sql" del paquete "sql", luego pégalo y ejecútalo en tu gestor MySQL.

2. Crear el archivo "config.properties" con sus datos(El formato de dicho archivo se puede encontrar en la sección "Configuración de la API Key de OpenRouter")

3. (Opcional) Ejecuta "SetupDatos" almacenado en el paquete "util" para tener datos de prueba en la base de datos

4. Para ejecutar el programa hay que ejecutar el archivo "Akihabara Market(Dual).bat", desde el archivo podras escoger entre una interfaz por consola o una grafica

---

## Funcionalidades Implementadas

Actualmente, la aplicación cuenta con las funcionalidades extra solicitadas:

  - Interfaz Gráfica con Swing, incluyendo:

       - Todas las funcionalidades de Productos y Clientes.

  - Gestión de Clientes, incluyendo:

      - Creación de clientes nuevos con datos validados.

      - Búsqueda y listado de clientes.

      - Actualización de información de clientes existentes.

      - Eliminación de clientes.
