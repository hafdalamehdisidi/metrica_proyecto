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
                       numero INT NOT NULL,
                       capacidad INT NOT NULL
);

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