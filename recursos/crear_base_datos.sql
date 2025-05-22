CREATE DATABASE IF NOT EXISTS restaurante;
USE restaurante;

-- Eliminar las tablas si existen (en orden inverso por las claves foráneas)
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS mesas;
DROP TABLE IF EXISTS clientes;

-- Crear tabla clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Crear tabla mesas
CREATE TABLE mesas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado VARCHAR(20) NOT NULL
);

-- Crear tabla reservas
CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    mesa_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    num_personas INT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (mesa_id) REFERENCES mesas(id)
);

-- Insertar algunos datos de prueba (opcional)
INSERT INTO clientes (nombre, apellidos, telefono, email) VALUES
    ('Juan', 'Pérez', '123456789', 'juan@email.com'),
    ('María', 'García', '987654321', 'maria@email.com');

INSERT INTO mesas (numero, capacidad, estado) VALUES
    (1, 4, 'DISPONIBLE'),
    (2, 2, 'DISPONIBLE'),
    (3, 6, 'DISPONIBLE'),
    (4, 4, 'DISPONIBLE');