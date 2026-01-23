-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS proyecto
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

-- Uso de la base de datos
USE proyecto;

-- Tablas principales
CREATE TABLE IF NOT EXISTS usuarios (
                                        id_usuario INT PRIMARY KEY AUTO_INCREMENT,
                                        nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN','TECNICO','CLIENTE') DEFAULT 'CLIENTE',
    fecha_registro DATE,
    foto_perfil VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS retos (
                                     id_reto INT PRIMARY KEY AUTO_INCREMENT,
                                     titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(50),
    dificultad INT,
    puntos INT
    );

-- Tablas dependientes
CREATE TABLE IF NOT EXISTS publicaciones (
                                             id_publicacion INT PRIMARY KEY AUTO_INCREMENT,
                                             id_usuario INT,
                                             texto TEXT,
                                             imagen VARCHAR(255),
    fecha DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
    );

CREATE TABLE IF NOT EXISTS progreso (
                                        id_progreso INT PRIMARY KEY AUTO_INCREMENT,
                                        id_usuario INT,
                                        id_reto INT,
                                        fecha DATE,
                                        completado BOOLEAN,
                                        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_reto) REFERENCES retos(id_reto)
    );

CREATE TABLE IF NOT EXISTS comentarios (
                                           id_comentario INT PRIMARY KEY AUTO_INCREMENT,
                                           id_publicacion INT,
                                           id_usuario INT,
                                           texto TEXT,
                                           fecha DATETIME,
                                           FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id_publicacion),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
    );

CREATE TABLE IF NOT EXISTS mensajes (
                                        id_mensaje INT PRIMARY KEY AUTO_INCREMENT,
                                        id_emisor INT,
                                        id_receptor INT,
                                        mensaje TEXT,
                                        fecha DATETIME,
                                        leido BOOLEAN DEFAULT FALSE,
                                        FOREIGN KEY (id_emisor) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_receptor) REFERENCES usuarios(id_usuario)
    );

CREATE TABLE IF NOT EXISTS soporte (
                                       id_ticket INT PRIMARY KEY AUTO_INCREMENT,
                                       id_usuario INT,
                                       asunto VARCHAR(100),
    descripcion TEXT,
    estado ENUM('ABIERTO','EN_PROCESO','CERRADO') DEFAULT 'ABIERTO',
    respuesta TEXT,
    fecha_apertura DATETIME,
    fecha_respuesta DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
    );

CREATE TABLE IF NOT EXISTS seguidores (
                                          id_seguidor INT PRIMARY KEY AUTO_INCREMENT,
                                          id_usuario INT,
                                          id_seguido INT,
                                          fecha DATETIME,
                                          FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_seguido) REFERENCES usuarios(id_usuario),
    UNIQUE KEY unico_seguimiento (id_usuario, id_seguido)
    );

CREATE TABLE IF NOT EXISTS likes (
                                     id_like INT PRIMARY KEY AUTO_INCREMENT,
                                     id_usuario INT,
                                     id_publicacion INT,
                                     fecha DATETIME,
                                     FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id_publicacion),
    UNIQUE KEY unico_like (id_usuario, id_publicacion)
    );
