CREATE DATABASE IF NOT EXISTS restaurante;
USE restaurante;

-- Primero eliminamos las tablas en orden inverso debido a las dependencias
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS mesas;
DROP TABLE IF EXISTS clientes;

-- Crear la tabla clientes primero
CREATE TABLE clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla mesas
CREATE TABLE mesas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    ubicacion VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'DISPONIBLE',
    CONSTRAINT chk_estado CHECK (estado IN ('DISPONIBLE', 'NO_DISPONIBLE', 'MANTENIMIENTO'))
);

-- Crear la tabla reservas (última porque depende de las otras dos)
CREATE TABLE reservas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    mesa_id INT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    num_personas INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'CONFIRMADA',
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (mesa_id) REFERENCES mesas(id),
    CONSTRAINT chk_estado_reserva CHECK (estado IN ('CONFIRMADA', 'CANCELADA', 'COMPLETADA'))
);

-- Insertar algunos datos de prueba
INSERT INTO clientes (nombre, apellidos, telefono, email) VALUES
('Juan', 'Pérez García', '123456789', 'juan@email.com'),
('María', 'López Martínez', '987654321', 'maria@email.com');

INSERT INTO mesas (numero, capacidad, ubicacion, estado) VALUES
(1, 2, 'Ventana', 'DISPONIBLE'),
(2, 4, 'Centro', 'DISPONIBLE'),
(3, 6, 'Terraza', 'DISPONIBLE'),
(4, 8, 'Salón Principal', 'DISPONIBLE');