# PerlyAPI

API REST desarrollada con Ktor, Exposed y MySQL para una plataforma social con retos,

publicaciones, seguidores y comentarios.

# Tecnologías

- Kotlin
- Ktor (Backend)
- Exposed (ORM)
- MySQL
- kotlinx.serialization
- Gradle
- Postman (testing)

# Base de datos

### La base de datos utilizada es MySQL con el nombre:

proyecto

Incluye las tablas:

- usuarios
- retos
- publicaciones
- progreso
- comentarios
- mensajes
- soporte
- seguidores
- likes

El script completo de creación e inserción inicial se encuentra en:

```
/database/proyecto.sql
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

# Endpoints principales

### 📍 Retos

Método Endpoint Descripción
```
GET /retos Obtener todos los retos

GET /retos/{id} Obtener reto por ID
```

### 📍 Comentarios

Método Endpoint Descripción
```
GET /comentarios/{id} Obtener comentario por ID

GET /comentarios/publicacion/{id} Comentarios de una publicación

POST /comentarios Crear comentario

DELETE /comentarios/{id} Eliminar comentario
```

### 📍 Seguidores
Método Endpoint Descripción
```
POST /seguidores Seguir a un usuario

DELETE /seguidores Dejar de seguir

GET /seguidores/{id\_usuario} Listar seguidores
```

# Pruebas en Postman

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

❌ 415 Unsupported Media Type

### Causa

- Campo obligatorio faltante en el DTO
- Content-Type incorrecto

### Solución

- Usar Content-Type: application/json
- Hacer campos opcionales (? = null) en DTOs de entrada

❌ 500 Internal Server Error

### Causa

- Columnas que no coinciden con la BD
- IDs inexistentes (FK)
- Uso de !!

### Solución

- Validar parámetros
- Revisar nombres exactos de columnas
- Controlar null y devolver 404/400

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
http://localhost:8080
```
