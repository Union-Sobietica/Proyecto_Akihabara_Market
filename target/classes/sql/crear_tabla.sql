-- Elimina la base de datos si ya existe, para evitar errores al crearla nuevamente
DROP DATABASE IF EXISTS akihabara_db;

-- Crea una nueva base de datos llamada 'akihabara_db'
CREATE DATABASE akihabara_db;

-- Selecciona la base de datos recién creada para trabajar con ella
USE akihabara_db;

-- Crea la tabla 'productos' con los campos básicos
CREATE TABLE productos (
    id INT PRIMARY KEY AUTO_INCREMENT,    -- ID único, autoincrementable, clave primaria
    nombre VARCHAR(255) NOT NULL,         -- Nombre del producto, obligatorio
    categoria VARCHAR(100),               -- Categoría del producto (opcional)
    precio DECIMAL(10,2),                 -- Precio del producto con dos decimales
    stock INT                             -- Número de unidades disponibles en stock
);

-- Crea la tabla 'clientes' para almacenar información de los clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,            -- ID único del cliente
    nombre VARCHAR(255) NOT NULL,                 -- Nombre del cliente, obligatorio
    email VARCHAR(255) NOT NULL UNIQUE,           -- Email del cliente, obligatorio y único
    telefono VARCHAR(20),                         -- Número de teléfono del cliente (opcional)
    fecha_registro DATE DEFAULT (CURRENT_DATE)    -- Fecha de registro, por defecto la actual
);

-- Crea un nuevo usuario de MySQL llamado 'usuario_akihabara' con contraseña 'proyecto_akihabara'
CREATE USER 'usuario_akihabara'@'%' IDENTIFIED BY 'proyecto_akihabara';

-- Concede todos los privilegios sobre la base de datos 'akihabara_db' al nuevo usuario
GRANT ALL PRIVILEGES ON akihabara_db.* TO 'usuario_akihabara'@'%';

-- Aplica los cambios de privilegios inmediatamente
FLUSH PRIVILEGES;