CREATE DATABASE IF NOT EXISTS pearlyDB;
USE pearlyDB;

-- 1. Tabla Usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    bio TEXT,
    avatar LONGTEXT, 
    achievements INT DEFAULT 0,
    puntos_energia INT DEFAULT 0,
    racha_actual INT DEFAULT 0,
    followers_count INT DEFAULT 0,
    following_count INT DEFAULT 0,
    is_private BOOLEAN DEFAULT FALSE,
    only_followers_messages BOOLEAN DEFAULT FALSE,
    ultima_conexion DATETIME NULL
);

-- 2. Tabla Retos
CREATE TABLE retos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    categoria ENUM('mental', 'physical', 'mindfulness', 'nutrition') NOT NULL,
    puntos INT NOT NULL DEFAULT 0,
    es_diario BOOLEAN DEFAULT FALSE
);

-- 3. Tabla Publicaciones
-- Sincronizada con PublicacionesDAO: usa id_usuario e id_reto_vinculado
CREATE TABLE publicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    texto TEXT NOT NULL,
    fecha DATETIME NOT NULL, 
    imagen LONGTEXT, 
    id_reto_vinculado INT NULL,
    CONSTRAINT fk_pub_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_pub_reto FOREIGN KEY (id_reto_vinculado) REFERENCES retos(id) ON DELETE SET NULL
);

-- 4. Tabla Likes
CREATE TABLE likes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_publicacion INT NOT NULL,
    fecha_like DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY idx_like_unico (id_usuario, id_publicacion),
    CONSTRAINT fk_like_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_like_publicacion FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id) ON DELETE CASCADE
);

-- 5. Tabla Comentarios
CREATE TABLE comentarios (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    id_publicacion INT NOT NULL,
    id_usuario INT NOT NULL,
    texto TEXT NOT NULL,
    fecha DATETIME NOT NULL,
    CONSTRAINT fk_com_publicacion FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id) ON DELETE CASCADE,
    CONSTRAINT fk_com_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 6. Tabla Seguidores
CREATE TABLE seguidores (
    id_seguimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_seguidor INT NOT NULL,
    id_seguido INT NOT NULL,
    fecha_seguimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY idx_seguimiento_unico (id_seguidor, id_seguido),
    CONSTRAINT fk_seg_seguidor FOREIGN KEY (id_seguidor) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_seg_seguido FOREIGN KEY (id_seguido) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 7. Tabla Bloqueos
CREATE TABLE bloqueos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_bloqueador INT NOT NULL,
    id_bloqueado INT NOT NULL,
    tipo ENUM('block', 'mute') DEFAULT 'block',
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY idx_bloqueo_unico (id_bloqueador, id_bloqueado, tipo),
    CONSTRAINT fk_bloq_bloqueador FOREIGN KEY (id_bloqueador) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_bloq_bloqueado FOREIGN KEY (id_bloqueado) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 8. Tabla Soportes
CREATE TABLE soportes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    respuesta TEXT,
    estado ENUM('open', 'in-progress', 'resolved', 'closed') DEFAULT 'open',
    fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta DATETIME,
    CONSTRAINT fk_sop_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 9. Tabla Progresos
CREATE TABLE progresos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_reto INT NOT NULL,
    puntos_ganados INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prog_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_prog_reto FOREIGN KEY (id_reto) REFERENCES retos(id) ON DELETE CASCADE
);

-- 10. Índices de rendimiento
CREATE INDEX idx_pub_fecha ON publicaciones(fecha DESC);
CREATE INDEX idx_com_pub ON comentarios(id_publicacion);