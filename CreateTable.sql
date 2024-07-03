-- Crear la base de datos
CREATE DATABASE paginationTest;

-- Seleccionar la base de datos
USE paginationTest;

-- Crear la tabla usuario con 8 campos variados
CREATE TABLE usuario
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    apellido         VARCHAR(100) NOT NULL,
    email            VARCHAR(100) NOT NULL UNIQUE,
    fecha_nacimiento DATE         NOT NULL,
    telefono         VARCHAR(20),
    direccion        VARCHAR(255),
    ciudad           VARCHAR(100),
    pais             VARCHAR(100)
);

DELIMITER $$

-- Crear el procedimiento almacenado
CREATE PROCEDURE InsertarUsuarios()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100
        DO
            INSERT INTO usuario (nombre, apellido, email, fecha_nacimiento, telefono, direccion, ciudad, pais)
            VALUES (CONCAT('Nombre', i),
                    CONCAT('Apellido', i),
                    CONCAT('usuario', i, '@example.com'),
                    DATE_SUB(CURDATE(), INTERVAL FLOOR(RAND() * 10000) DAY),
                    CONCAT('12345', LPAD(i, 5, '0')),
                    CONCAT('Direccion ', i),
                    CONCAT('Ciudad ', i),
                    CONCAT('Pais ', i));
            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

-- Llamar al procedimiento para insertar los 50 registros
CALL InsertarUsuarios();
