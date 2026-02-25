CREATE DATABASE IF NOT EXISTS pearlyDB;
USE pearlyDB;

-- 1. Tabla Usuarios
CREATE TABLE usuarios (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL
);

-- 2. Tabla Publicaciones
CREATE TABLE publicaciones (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               id_usuario INT NOT NULL,
                               texto TEXT NOT NULL,
                               fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
                               imagen LONGBLOB,
                               CONSTRAINT fk_pub_usuario FOREIGN KEY (id_usuario)
                                   REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 3. Tabla Comentarios
CREATE TABLE comentarios (
                             id_comentario INT AUTO_INCREMENT PRIMARY KEY,
                             id_publicacion INT NOT NULL,
                             id_usuario INT NOT NULL,
                             contenido TEXT NOT NULL,
                             CONSTRAINT fk_com_publicacion FOREIGN KEY (id_publicacion)
                                 REFERENCES publicaciones(id) ON DELETE CASCADE,
                             CONSTRAINT fk_com_usuario FOREIGN KEY (id_usuario)
                                 REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 4. Tabla Likes
CREATE TABLE likes (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       id_usuario INT NOT NULL,
                       id_publicacion INT NOT NULL,
                       UNIQUE KEY idx_like_unico (id_usuario, id_publicacion),
                       CONSTRAINT fk_like_usuario FOREIGN KEY (id_usuario)
                           REFERENCES usuarios(id) ON DELETE CASCADE,
                       CONSTRAINT fk_like_publicacion FOREIGN KEY (id_publicacion)
                           REFERENCES publicaciones(id) ON DELETE CASCADE
);

-- 5. Tabla Seguidores
CREATE TABLE seguidores (
                            id_seguimiento INT AUTO_INCREMENT PRIMARY KEY,
                            id_usuario INT NOT NULL,
                            id_seguido INT NOT NULL,
                            fecha_seguimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
                            UNIQUE KEY idx_seguimiento_unico (id_usuario, id_seguido),
                            CONSTRAINT fk_seg_usuario FOREIGN KEY (id_usuario)
                                REFERENCES usuarios(id) ON DELETE CASCADE,
                            CONSTRAINT fk_seg_seguido FOREIGN KEY (id_seguido)
                                REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 6. Tabla Retos
CREATE TABLE retos (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       titulo VARCHAR(255) NOT NULL,
                       descripcion TEXT NOT NULL,
                       puntos INT NOT NULL DEFAULT 0
);

-- 7. Tabla Progresos
CREATE TABLE progresos (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           id_usuario INT NOT NULL,
                           id_reto INT NOT NULL,
                           puntos_ganados INT NOT NULL,
                           fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
                           completado BOOLEAN DEFAULT TRUE,
                           CONSTRAINT fk_prog_usuario FOREIGN KEY (id_usuario)
                               REFERENCES usuarios(id) ON DELETE CASCADE,
                           CONSTRAINT fk_prog_reto FOREIGN KEY (id_reto)
                               REFERENCES retos(id) ON DELETE CASCADE
);

-- 8. Tabla Bloqueos
CREATE TABLE bloqueos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          id_bloqueador INT NOT NULL,
                          id_bloqueado INT NOT NULL,
                          UNIQUE KEY idx_bloqueo_unico (id_bloqueador, id_bloqueado),
                          CONSTRAINT fk_bloq_bloqueador FOREIGN KEY (id_bloqueador)
                              REFERENCES usuarios(id) ON DELETE CASCADE,
                          CONSTRAINT fk_bloq_bloqueado FOREIGN KEY (id_bloqueado)
                              REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 9. Tabla Soportes (Tickets)
CREATE TABLE soportes (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          id_usuario INT NOT NULL,
                          asunto VARCHAR(255) NOT NULL,
                          descripcion TEXT NOT NULL,
                          respuesta TEXT,
                          fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,
                          fecha_respuesta DATETIME,
                          CONSTRAINT fk_sop_usuario FOREIGN KEY (id_usuario)
                              REFERENCES usuarios(id) ON DELETE CASCADE
);