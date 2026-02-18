# PerlyAPI

PerlyAPI es una API REST desarrollada en Kotlin utilizando el framework Ktor, el ORM Exposed y una base de datos MySQL.
Este backend forma parte de una plataforma social orientada al bienestar y crecimiento personal, donde los usuarios pueden participar en retos, crear publicaciones, interactuar mediante comentarios y establecer relaciones de seguimiento entre ellos.

El objetivo principal de esta API es proporcionar una arquitectura clara, modular y escalable que permita a una aplicación cliente consumir los datos de forma segura y estructurada, aplicando los conocimientos adquiridos en el módulo de Acceso a Datos del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).

La API sigue el estilo arquitectónico REST, intercambiando información en formato JSON y utilizando los métodos HTTP estándar para la gestión de recursos.

# Tecnologías

- Kotlin – Lenguaje principal del backend
- Ktor – Framework para la creación de servidores y APIs REST
- Exposed – ORM para la gestión del acceso a datos
- MySQL – Sistema gestor de bases de datos relacional
- kotlinx.serialization – Serialización y deserialización de objetos JSON
- Gradle – Herramienta de automatización y gestión del proyecto
- Postman – Pruebas y verificación de los endpoints REST

# Base de datos

### La base de datos utilizada es MySQL con el nombre:

```
proyecto
```

Incluye las tablas:

- usuarios
- retos
- publicaciones
- progreso
- comentarios
- soporte
- seguidores
- likes

Cada tabla representa una entidad del sistema y se relaciona mediante claves primarias y foráneas, garantizando la integridad referencial.

El script completo de creación de la base de datos y las tablas se encuentra en:

```
/data/script.sql
```

# Conexión a la base de datos

### Archivo: core/ConexionDB.kt

```kotlin
Database.connect(
    url = "jdbc:mysql://localhost:3306/proyecto",
    driver = "com.mysql.cj.jdbc.Driver",
    user = "root",
    password = ""
)
```

Esta configuración permite que la aplicación se conecte de forma directa a la base de datos MySQL en un entorno local. En un entorno de producción, estos valores deberían externalizarse mediante variables de entorno.

# Endpoints principales

### Retos

Método Endpoint Descripción
```
GET /retos Obtener todos los retos

GET /retos/{id} Obtener reto por ID
```

### Comentarios

Método Endpoint Descripción
```
GET /comentarios/{id} Obtener comentario por ID

GET /comentarios/publicacion/{id} Comentarios de una publicación

POST /comentarios Crear comentario

DELETE /comentarios/{id} Eliminar comentario
```

### Seguidores
Método Endpoint Descripción
```
POST /seguidores Seguir a un usuario

DELETE /seguidores Dejar de seguir

GET /seguidores/{id\_usuario} Listar seguidores
```

# Pruebas en Postman

Las pruebas de los endpoints se han realizado utilizando Postman, permitiendo verificar el correcto funcionamiento de la API.

### POST /seguidores

### URL

```
http://localhost:8080/seguidores
```

### Headers

```
Content-Type: application/json
```

### Body

```JSON
{

    "idUsuario": 3,
    "idSeguido": 4

}
```

## Errores comunes y soluciones

415 Unsupported Media Type

### Causa

- Campo obligatorio faltante en el DTO
- Content-Type incorrecto

### Solución

- Usar Content-Type: application/json
- Hacer campos opcionales (? = null) en DTOs de entrada

500 Internal Server Error

### Causa

- Columnas que no coinciden con la BD
- IDs inexistentes (FK)
- Uso de !!

### Solución

- Validar parámetros antes de ejecutar operaciones
- Revisar los nombres exactos de las columnas
- Controlar valores nulos y devolver códigos 400 o 404 según corresponda

# Buenas prácticas aplicadas

- Separación por capas:
- routes → HTTP
- services → lógica
- data → acceso a datos
- domain → DTOs
- Uso de object para DAOs y Services
- Transacciones con Exposed
- Serialización con kotlinx.serialization

# Ejecución del proyecto

```
./gradlew run
```

La API estará disponible en:

```
http://127.0.0.1:8080
```
