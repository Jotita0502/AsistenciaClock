# ASISTENCIACLOCK (API REST)

AsistenciaClock es una API RESTful desarrollada en Java para la gestión de asistencia, control de tiempos, workspaces, proyectos, tareas y etiquetas. Está inspirada en herramientas como Clockify.

Este proyecto utiliza **Spark Java** como micro-framework web, **JWT** para la autenticación y **MySQL** gestionado a través de procedimientos almacenados para un acceso a datos eficiente y seguro.

---

## 🛠️ Tecnologías

- Java 11
- Spark Java
- Maven
- MySQL 8
- JWT
- BCrypt
- Gson
- Postman
- Git
- GitHub

## 🏛️ Arquitectura
API REST basada en arquitectura por capas:
### Flujo principal

```text
Cliente / Postman / Frontend
          ↓
       Routes
          ↓
         DAO
          ↓
 Stored Procedures
          ↓
        MySQL
```

### Flujo de seguridad

```text
Request HTTP
     ↓
JWT Middleware
     ↓
Validación de Roles
     ↓
Endpoint Protegido
```
## 🏗️ Estructura General del Proyecto

El proyecto sigue una arquitectura organizada y modularizada:

- **`com.mycompany.asistencia.api`**: Contiene la clase principal `AsistenciaApi.java`, punto de entrada de la aplicación, configuración de puerto (4567) y el middleware (filtros) de autenticación JWT.
- **`routes/`**: Controladores de la aplicación. Definen los endpoints de la API y manejan las peticiones HTTP (`GET`, `POST`, `PUT`, `DELETE`), así como la validación de roles y datos de entrada.
- **`dao/`**: (Data Access Object) Contiene la lógica de comunicación con la base de datos MySQL, ejecutando las llamadas a los Procedimientos Almacenados.
- **`model/`**: Clases Java que representan las entidades del negocio (Usuario, Workspace, Proyecto, Respuestas de Éxito/Error).
- **`db/`**: Scripts de base de datos.
  - `schema.sql`: Estructura de tablas e índices.
  - `stored_procedures.sql`: Toda la lógica de negocio a nivel de base de datos.
- **`utils/`**: Clases de utilidad, como `JwtUtil` para la generación y validación de tokens.

---

## 🚀 Ejecución del Proyecto

### 1. Requisitos Previos

- **Java Development Kit (JDK)** 8 o superior (recomendado 11+).
- **Maven** para la gestión de dependencias.
- **MySQL 8.0** (Local o en la nube como Aiven).
- Entorno de desarrollo como NetBeans, IntelliJ o Eclipse.

### 2. Configuración de Base de Datos

1. Abre tu gestor de base de datos (Ej: MySQL Workbench, DBeaver).
2. Ejecuta el archivo `src/main/java/db/schema.sql` completo para crear la base de datos `asistenciaGM` y sus tablas.
3. Ejecuta el archivo `src/main/java/db/stored_procedures.sql` para crear todos los procedimientos almacenados necesarios para el funcionamiento del backend.
4. _(Nota)_: Asegúrate de configurar tus credenciales de base de datos en la clase de conexión (presumiblemente `db.Conexion`).

### 3. Compilación y Ejecución

1. Abre el proyecto en **NetBeans** (o tu IDE de preferencia).
2. Descarga las dependencias de Maven.
3. Ejecuta la clase principal: `com.mycompany.asistencia.api.AsistenciaApi`.
4. Verás en la consola el mensaje: `🔥 API INICIADA 🔥`.
5. El servidor estará escuchando por defecto en: `http://localhost:4567`

---

## 🔐 Autenticación

La API está protegida por **JSON Web Tokens (JWT)**.
A excepción de las rutas públicas (`/login`, `/test`, `/test-conexion`), todas las peticiones deben incluir un encabezado HTTP de autorización:

```http
Authorization: Bearer <tu_token_jwt>
```

### Sistema de Roles

El sistema maneja tres tipos de roles que controlan el acceso a los diferentes endpoints:

- **ADMIN**: Acceso total a la creación, actualización y eliminación de usuarios, workspaces y proyectos.
- **MANAGER**: Acceso a la gestión de workspaces y proyectos, pero no de usuarios a nivel sistema.
- **EMPLEADO**: Acceso restringido. Solo puede consumir listados y gestionar su propio tiempo (timers).

---

## 📡 Endpoints

A continuación se detallan los principales recursos expuestos por la API. Todas las peticiones a rutas protegidas requieren el envío de **Headers Bearer Token** (`Authorization: Bearer <token>`). Para la creación y modificación de recursos se utilizan **Bodies JSON**, y todas las respuestas se emiten en formato `application/json`.

### 🛠️ Pruebas y Auth (Rutas Públicas)

| Método | Endpoint         | Descripción                                                          |
| ------ | ---------------- | -------------------------------------------------------------------- |
| `POST` | `/login`         | Autentica un usuario con email y contraseña y devuelve un token JWT. |
| `GET`  | `/test`          | Endpoint de prueba para verificar que la API está levantada.         |
| `GET`  | `/test-conexion` | Verifica el estado de conexión con la base de datos MySQL.           |

### 👥 Usuarios (`/usuarios`)

| Método   | Endpoint        | Rol Requerido | Descripción                                                                                |
| -------- | --------------- | ------------- | ------------------------------------------------------------------------------------------ |
| `GET`    | `/usuarios`     | **ADMIN**     | Lista todos los usuarios activos del sistema.                                              |
| `POST`   | `/usuarios`     | **ADMIN**     | Crea un nuevo usuario. Valida nombre, correo, longitud de contraseña (min 6) y rol válido. |
| `PUT`    | `/usuarios/:id` | **ADMIN**     | Actualiza los datos de un usuario existente.                                               |
| `DELETE` | `/usuarios/:id` | **ADMIN**     | Realiza una baja lógica (desactiva) a un usuario por ID.                                   |

### 🏢 Workspaces (`/workspaces`)

| Método   | Endpoint          | Rol Requerido          | Descripción                                                                             |
| -------- | ----------------- | ---------------------- | --------------------------------------------------------------------------------------- |
| `GET`    | `/workspaces`     | **ADMIN**, **MANAGER** | Devuelve la lista de espacios de trabajo.                                               |
| `POST`   | `/workspaces`     | **ADMIN**, **MANAGER** | Crea un nuevo workspace. Valida nombre, descripción y el ID del propietario (owner_id). |
| `PUT`    | `/workspaces/:id` | **ADMIN**, **MANAGER** | Modifica la información básica de un workspace.                                         |
| `DELETE` | `/workspaces/:id` | **ADMIN**              | Elimina permanentemente un workspace específico.                                        |

### 📁 Proyectos (`/proyectos`)

| Método   | Endpoint         | Rol Requerido          | Descripción                                                        |
| -------- | ---------------- | ---------------------- | ------------------------------------------------------------------ |
| `GET`    | `/proyectos`     | Autenticado            | Lista los proyectos no archivados, incluyendo su cliente y color.  |
| `POST`   | `/proyectos`     | **ADMIN**, **MANAGER** | Crea un proyecto asociado a un workspace. Requiere nombre y color. |
| `PUT`    | `/proyectos/:id` | **ADMIN**, **MANAGER** | Actualiza las propiedades de un proyecto.                          |
| `DELETE` | `/proyectos/:id` | **ADMIN**              | Archiva o elimina lógicamente un proyecto por su ID.               |

### 📋 Tareas (`/tareas`)

Implementa un **CRUD completo de Tasks**.

| Método   | Endpoint                | Rol Requerido | Descripción                                                           |
| -------- | ----------------------- | ------------- | --------------------------------------------------------------------- |
| `GET`    | `/tareas`               | **ADMIN**, **MANAGER**   | Lista todas las tareas del sistema.                                   |
| `GET`    | `/proyectos/:id/tareas` | Autenticado   | Lista las tareas asociadas a un proyecto específico.                  |
| `GET`    | `/tareas/:id`           | Autenticado   | Obtiene el detalle de una tarea por su ID.                            |
| `POST`   | `/tareas`               | **ADMIN**, **MANAGER**   | Crea una nueva tarea asociada a un proyecto. Requerido body JSON.     |
| `PUT`    | `/tareas/:id`           | **ADMIN**, **MANAGER**   | Actualiza los datos de una tarea (nombre, etc.). Requerido body JSON. |
| `DELETE` | `/tareas/:id`           | **ADMIN**, **MANAGER**   | Realiza una baja lógica (archiva) de la tarea.                        |

### 🏷️ Etiquetas (`/tags` y Relaciones)

Implementa un **CRUD completo de Tags** y permite la **Relación registro-etiqueta**.

| Método   | Endpoint                             | Rol Requerido | Descripción                                                                      |
| -------- | ------------------------------------ | ------------- | -------------------------------------------------------------------------------- |
| `GET`    | `/workspaces/:id/tags`               | Autenticado   | Lista las etiquetas pertenecientes a un workspace.                               |
| `GET`    | `/tags/:id`                          | Autenticado   | Obtiene el detalle de una etiqueta por su ID.                                    |
| `POST`   | `/tags`                              | **ADMIN**, **MANAGER**   | Crea una nueva etiqueta. Requiere body JSON (`workspace_id`, `nombre`, `color`). |
| `PUT`    | `/tags/:id`                          | **ADMIN**, **MANAGER**   | Actualiza el nombre o color de una etiqueta existente.                           |
| `DELETE` | `/tags/:id`                          | **ADMIN**, **MANAGER**   | Elimina permanentemente una etiqueta.                                            |
| `POST`   | `/registros/:registroId/tags/:tagId` | Autenticado   | Asocia una etiqueta a un registro de tiempo (**Relación registro-etiqueta**).    |
| `DELETE` | `/registros/:registroId/tags/:tagId` | Autenticado   | Elimina la asociación de una etiqueta a un registro de tiempo.                   |
| `GET`    | `/registros/:id/tags`                | Autenticado   | Lista las etiquetas asociadas a un registro de tiempo específico.                |

### ⏱️ Timers y Asistencia (Gestión de Tiempos)

| Método | Endpoint           | Rol Requerido | Descripción                                                                                                    |
| ------ | ------------------ | ------------- | -------------------------------------------------------------------------------------------------------------- |
| `GET`  | `/timer/active`    | Autenticado   | Devuelve el timer actualmente activo (en curso) del usuario. Retorna 404 si no hay ninguno.                    |
| `GET`  | `/timer/historial` | Autenticado   | Devuelve una lista con el historial de todos los timers finalizados del usuario ordenados por fecha.           |
| `POST` | `/timer/start`     | Autenticado   | Inicia un nuevo timer (y detiene el actual si lo hay). Se envía en el body el workspace y detalles opcionales. |
| `POST` | `/timer/stop`      | Autenticado   | Detiene el timer actualmente activo del usuario registrando su hora de finalización.                           |

---

## ✅ Validaciones Implementadas

La API implementa validaciones de entrada para garantizar integridad de datos:

- Body obligatorio.
- IDs numéricos.
- IDs mayores a cero.
- Correos válidos.
- Contraseñas mínimas de 6 caracteres.
- Roles válidos.
- workspace_id válido.
- proyecto_id válido.
- Color hexadecimal válido (#RRGGBB).
- JWT obligatorio para rutas protegidas.
- Restricción por roles.

## 📮 Colección Postman

El proyecto incluye una colección de Postman lista para pruebas manuales de todos los endpoints.

Ubicación:

docs/postman/AsistenciaClock_API_Backend.postman_collection.json

Environment:

docs/postman/AsistenciaClock_Local.postman_environment.json

### Variables principales

| Variable | Descripción |
|-----------|------------|
| base_url | URL base de la API |
| admin_token | JWT ADMIN |
| manager_token | JWT MANAGER |
| empleado_token | JWT EMPLEADO |
| workspace_id | Workspace de pruebas |
| project_id | Proyecto de pruebas |
| task_id | Task de pruebas |
| tag_id | Tag de pruebas |
| registro_id | Registro de tiempo de pruebas |

### Flujo recomendado

1. Ejecutar Login ADMIN.
2. Copiar JWT recibido.
3. Guardar JWT en admin_token.
4. Probar endpoints protegidos.
   
---   
## ⚠️ Manejo de Errores

La API utiliza un formato estándar para devolver respuestas en base a los estados HTTP:

- `200 OK` / `201 Created`: Operaciones exitosas (devuelve el objeto JSON o `SuccessResponse`).
- `400 Bad Request`: Validaciones de campos vacíos o incorrectos.
- `401 Unauthorized`: Token faltante, expirado o inválido.
- `403 Forbidden`: El usuario no tiene el rol necesario para esta acción.
- `500 Internal Server Error`: Errores controlados de base de datos o excepciones en el servidor.

**Ejemplo de Error (JSON):**

```json
{
  "error": "El nombre del proyecto es obligatorio"
}
```

## 📌 Estado del Proyecto

Versión actual: v1.0

Módulos implementados:

✅ Autenticación JWT
✅ Usuarios
✅ Workspaces
✅ Proyectos
✅ Tareas
✅ Etiquetas
✅ Timers
✅ Historial
✅ Validaciones
✅ Colección Postman

Próximamente:

🔄 Frontend Angular
🔄 Dashboard visual
🔄 Deploy en nube

## 👨‍💻 Autor

Joaquín Ricardo Conde Espíritu

Proyecto académico desarrollado como parte de la asignatura de Desarrollo Backend y Gestión de Tiempos.

GitHub:
https://github.com/Jotita0502
