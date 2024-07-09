-- Crear la base de datos
CREATE DATABASE paginationTest;

-- Seleccionar la base de datos
USE paginationTest;

-- Crear la tabla usuario con 10 campos variados
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
    pais             VARCHAR(100),
    activo           BOOLEAN      NOT NULL DEFAULT TRUE,
    hora_registro    TIME         NOT NULL
);

DELIMITER $$

DELIMITER $$

-- Crear el procedimiento almacenado
CREATE PROCEDURE InsertarUsuarios()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE random_seconds INT;

    WHILE i <= 10000
        DO
            -- Generar un número aleatorio de segundos en un día (0 a 86399)
            SET random_seconds = FLOOR(RAND() * 86400);

            INSERT INTO usuario (nombre, apellido, email, fecha_nacimiento, telefono, direccion, ciudad, pais, activo,
                                 hora_registro)
            VALUES (CONCAT('Nombre', i),
                    CONCAT('Apellido', i),
                    CONCAT('usuario', i, '@example.com'),
                    DATE_SUB(CURDATE(), INTERVAL FLOOR(RAND() * 10000) DAY),
                    CONCAT('12345', LPAD(i, 5, '0')),
                    CONCAT('Direccion ', i),
                    CONCAT('Ciudad ', i),
                    CONCAT('Pais ', i),
                    IF(RAND() > 0.5, TRUE, FALSE),
                    SEC_TO_TIME(random_seconds));
            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

-- Llamar al procedimiento para insertar los 10000 registros
CALL InsertarUsuarios();

