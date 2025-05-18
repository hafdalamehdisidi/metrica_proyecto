CREATE DATABASE IF NOT EXISTS elrestaurante;
USE elrestaurante;

CREATE TABLE clientes (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(100) NOT NULL,
                          telefono VARCHAR(15),
                          email VARCHAR(100)
);

CREATE TABLE mesas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'DISPONIBLE',
    CONSTRAINT chk_capacidad CHECK (capacidad > 0)
);

-- Insertar algunas mesas de ejemplo
INSERT INTO mesas (numero, capacidad) VALUES 
(1, 2),  -- Mesa para dos personas
(2, 2),  -- Otra mesa para dos
(3, 4),  -- Mesa para cuatro personas
(4, 4),  -- Otra mesa para cuatro
(5, 6),  -- Mesa para seis personas
(6, 8),  -- Mesa para ocho personas
(7, 10); -- Mesa para diez personas

CREATE TABLE reservas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          cliente_id INT,
                          mesa_id INT,
                          fecha DATE NOT NULL,
                          hora TIME NOT NULL,
                          num_personas INT NOT NULL,
                          FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                          FOREIGN KEY (mesa_id) REFERENCES mesas(id)
);

INSERT INTO mesas (numero, capacidad) VALUES 
(1, 2),
(2, 2),
(3, 4),
(4, 4),
(5, 6),
(6, 6),
(7, 8),
(8, 8);