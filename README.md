# Documentación del Backend API - Livingnet

**Versión:** 2.0.0  
**Fecha:** [01-10-2025]  
**Autores:** Jorge Cueva

## 1. Introducción

El presente documento describe la documentación completa del backend API desarrollado para Livingnet. Esta API está construida con Spring Boot y proporciona servicios RESTful para la gestión de reportes de trabajo, usuarios, autenticación JWT, y manejo de imágenes. Incluye clases para reportes completos y "vacíos" (incompletos), permitiendo flexibilidad en el flujo de trabajo.

La API soporta operaciones CRUD, filtrado dinámico, paginación, carga de imágenes, autenticación de usuarios, y validación de datos. Está diseñada para ser escalable y mantenible, siguiendo patrones de arquitectura DAO, Gestión (lógica de negocio), y Servicios (controladores REST).


## 2. Despligue

Con respecto al despliegue del backend, hecho en el servidor instalado dentro de la empresa, y mediante conexión ssh se realiza lo siguiente.

### 2.1 Conexión ssh

#### las credenciales serán dadas en la misma empresa.

#### comando apra ir a la carpeta con los archivos.
 cd BackendApp 
#### en caso de actualizaciones
 cd back
 git pull origin main
 cd ..
#### para desplegar 
sudo docker compose down
sudo docker compose build
sudo docker compose up -d

#### Para acceder a la base de datos
psql -d livingnet -U postgres -h localhost
#### Para ver logs del backend en caso ed errores
sudo docker logs backend

#### para finalizar se agrega una aplicación para generar un archivo csv con todos los reportes, en caso de emergencia. 

python3 generatecsv.py

#### y un comando para copiar las carpetas con imagenes y firmas.

#### Copiar imágenes y firmas por SSH
scp -r livingnet@172.16.0.10:~/BackendApp/back/uploads ./imagenes

#### Copiar csv de reportes por SSH

scp livingnet@172.16.0.10:~/reportes.csv ./reportes.csv

## 3. Descripción

El backend es una aplicación Spring Boot que gestiona reportes de actividades laborales para técnicos de Livingnet. Permite crear reportes vacíos (iniciales) que luego se completan, convirtiéndose en reportes finales. Incluye autenticación JWT, manejo de imágenes (fotos y firmas), y datos de ubicación.

Tecnologías principales:
- **Spring Boot**: Framework principal.
- **JPA/Hibernate**: ORM para persistencia.
- **PostgreSQL**: Base de datos.
- **JWT**: Autenticación y autorización.
- **Multipart**: Carga de archivos (imágenes).

## 4. Organización

El proyecto sigue una arquitectura en capas para facilitar el mantenimiento:
- **DAO (Data Access Object)**: Acceso directo a la base de datos, consultas SQL y operaciones de persistencia.
- **Gestión**: Lógica de negocio, validaciones, y coordinación entre DAO y servicios.
- **Servicios**: Controladores REST que manejan solicitudes HTTP y devuelven respuestas.
- **Model**: Entidades JPA y clases de solicitud/respuesta.
- **JWT**: Manejo de tokens para autenticación.
- **Otros**: Utilidades como procesamiento de imágenes y manejo de excepciones.

## 5. Clases Modelo

### 5.1 ReporteModel
Clase persistida en la tabla `reportes`. Representa reportes completos de actividades laborales.

**Atributos principales:**
- `id` (Long): Identificador único.
- `fecha` (Date): Fecha del reporte (no nulo).
- `horainicio`, `horafin` (Date): Horas de inicio y fin (no nulos).
- Campos de actividad: `agencia`, `actividad`, `cuadrilla`, `jefe_cuadrilla`, `tipo_actividad`, `formato_actividad`, `complejidad_actividad`, `estado_actividad`, `clima` (String, no nulos).
- Campos opcionales: `ayudante_tecnico`, `motivo_retraso`, `observaciones` (String, nulos).
- Datos numéricos: `kilometraje_inicio`, `kilometraje_fin` (double, no nulos); contadores de equipos (`router`, `onu`, etc., int, no nulos).
- `foto_url` (String): URL de la imagen subida.
- `firmaUrl` (String): URL de la firma.
- Relación: `usuario` (UsuarioModel, opcional).

### 5.2 ReporteVacioModel
Clase persistida en la tabla `reportes_vacios`. Similar a ReporteModel pero con todos los campos nulos por defecto, permitiendo reportes incompletos. Incluye datos de ubicación y cliente adicionales.

**Atributos adicionales:**
- `usuario` (UsuarioModel): Relación con el usuario asignado.
- Ubicación: `latitudInicio`, `longitudInicio`, `latitudFin`, `longitudFin` (String).
- Cliente: `nombreCliente`, `cedulaCliente`, `numeroContrato`, `telefonos`, `correo`, `plan`, `coordenadas`, `valorCobrar`, `firmaUrl` (String).

### 5.3 UsuarioModel
Clase persistida en la tabla `usuarios`. Gestiona credenciales y roles.

**Atributos:**
- `id` (Long): Identificador único.
- `mail` (String): Correo único (no nulo).
- `password` (String): Contraseña (no nula).
- `rol` (String): Rol del usuario (no nulo).

### 5.4 UsuarioRequest
Clase de solicitud para creación/modificación de usuarios (no persistida). Incluye confirmación de contraseña.

**Atributos:**
- `id`, `mail`, `password`, `rol`, `confirmPassword` (String).

### 5.5 UsuarioSend
Clase de respuesta para usuarios (no persistida). Excluye contraseña.

**Atributos:**
- `id`, `mail`, `rol` (String/Long).

### 5.6 LocationRequest
Clase de solicitud para ubicación (no persistida).

**Atributos:**
- `latitud`, `longitud` (String).

### 5.7 ReporteRequest
Clase de solicitud para filtrado y paginación (no persistida).

**Atributos:**
- `datos` (int): Cantidad de datos.
- `pagina` (int): Página.
- Parámetros de filtro: `fecha`, `agencia`, etc. (Date/String).
- `retraso` (boolean): Filtrado por retraso.

## 6. Clases DAO

### 6.1 ReportesDAO
Acceso a datos para ReporteModel.

**Métodos principales:**
- `findAll()`: Lista todos los reportes.
- `addReporte(ReporteModel)`: Persiste nuevo reporte.
- `updateReporte(ReporteModel)`: Actualiza reporte.
- `deleteReporte(Long id)`: Elimina reporte.
- `getCantidadReportes(ReporteRequest)`: Cuenta reportes filtrados.
- `getReportesFiltrado(ReporteRequest)`: Lista reportes filtrados/paginados.
- `deleteImagen(Long id)`: Elimina imagen asociada.
- Métodos para desplegables: `getAgencias()`, etc.

### 6.2 ReporteVacioDAO
Acceso a datos para ReporteVacioModel.

**Métodos principales:**
- `createReporte(Long idUsuario, LocationRequest, UsuarioModel)`: Crea reporte vacío con ubicación.
- `createReporte(ReporteVacioModel)`: Persiste reporte vacío.
- `getReporteVacio(Long usuario)`: Obtiene reporte vacío por usuario.
- `deleteReporteVacio(Long usuario)`: Elimina reporte vacío.
- `actualizarReporteVacio(ReporteVacioModel)`: Actualiza reporte vacío.
- `assignReporte(Long idReporte, LocationRequest, UsuarioModel)`: Asigna reporte a usuario.
- `findAll()`: Lista reportes vacíos sin usuario.
- `getReporteVacioById(Long)`: Obtiene por ID.
- `deleteReporteVacioSinUsuario(Long)`: Elimina sin usuario.
- `actualizarReporteVacioSinUsuario(ReporteVacioModel)`: Actualiza sin usuario.

### 6.3 UsuarioDAO
Acceso a datos para UsuarioModel.

**Métodos principales:**
- `buscarPorId(Long)`: Busca por ID.
- `buscarPorEmailYPassword(String, String)`: Busca por credenciales.
- `getUsuarios()`: Lista todos.
- `save(UsuarioModel)`: Persiste usuario.
- `updateUsuario(UsuarioModel)`: Actualiza.
- `deleteUsuario(Long)`: Elimina.

## 7. Clases Gestión

### 7.1 ReportesGestion
Lógica de negocio para ReporteModel.

**Métodos principales:**
- `getReportes()`: Lista reportes.
- `addReporte(ReporteModel)`: Agrega reporte.
- `updateReporte(ReporteModel)`: Actualiza.
- `deleteReporte(Long)`: Elimina con imagen.
- `getReportesFiltros(ReporteRequest)`: Lista filtrada.
- `getCantidadReportes(ReporteRequest)`: Cuenta.
- `getDesplegables()`: Mapa de valores únicos.
- `generarReporte(ReporteVacioModel, Long, ReporteModel)`: Convierte vacío a completo.

### 7.2 ReporteVacioGestion
Lógica de negocio para ReporteVacioModel.

**Métodos principales:**
- `generarReporteVacio(ReporteVacioModel)`: Crea vacío sin usuario.
- `generarReporteVacio(Long, LocationRequest)`: Crea con usuario y ubicación.
- `getReporteVacio(Long)`: Obtiene por usuario.
- `eliminarReporteVacio(Long)`: Elimina con imagen.
- `actualizarReporteVacio(ReporteVacioModel, Long, String)`: Actualiza con imagen.
- `assignarReporteVacio(Long, LocationRequest, Long)`: Asigna a usuario.
- `eliminarReporteSinUsuario(Long)`: Elimina sin usuario.
- `editReport(ReporteVacioModel)`: Edita.

### 7.3 UsuarioGestion
Lógica de negocio para UsuarioModel.

**Métodos principales:**
- `buscarPorId(Long)`: Busca por ID.
- `buscarPorEmailYPassword(String, String)`: Busca por credenciales.
- `getUsuarios()`: Lista.
- `addUsuario(UsuarioModel)`: Agrega.
- `updateUsuario(UsuarioModel)`: Actualiza.
- `deleteUsuario(Long)`: Elimina.

## 8. Clases Servicios

### 8.1 LoginService
Autenticación de usuarios.

**Ruta base:** `/`
**Métodos:**
- `POST /login`: Valida credenciales y devuelve JWT.

### 8.2 ReportesService
Gestión de reportes completos.

**Ruta base:** `/reports`
**Métodos:**
- `GET /all`: Lista todos.
- `POST /filters`: Lista filtrada.
- `POST /quantity`: Cuenta filtrada.
- `POST /`: Agrega reporte.
- `PUT /`: Actualiza.
- `DELETE /{id}`: Elimina.
- `GET /deployables`: Valores únicos.

### 8.3 ReporteVacioService
Gestión de reportes vacíos.

**Ruta base:** `/generate`
**Métodos:**
- `GET /{idUsuario}`: Obtiene vacío por usuario.
- `PUT /update`: Actualiza vacío.
- `POST /assign/{idUsuario}/{idReporte}`: Asigna vacío.
- `POST /`: Crea vacío sin usuario.
- `GET /empty`: Lista vacíos sin usuario.
- `POST /{idUsuario}`: Crea vacío con usuario.
- `DELETE /report/{idReporte}`: Elimina vacío sin usuario.
- `DELETE /{idUsuario}`: Elimina vacío.
- `PUT /{idUsuario}`: Actualiza con imagen.
- `POST /reports/{idUsuario}`: Convierte vacío a reporte completo con imagen.

### 8.4 UsuariosService
Gestión de usuarios.

**Ruta base:** `/users`
**Métodos:**
- `GET /`: Lista usuarios (sin contraseña).
- `POST /`: Agrega usuario.
- `PUT /`: Actualiza.
- `DELETE /{id}`: Elimina.

### 8.5 Validation
Validación de JWT.

**Ruta base:** `/validate`
**Métodos:**
- `GET /`: Valida token.

## 9. Clases JWT

- **JwtUtil**: Genera y valida tokens.
- **JwtFilter**: Filtro para solicitudes, verifica JWT.
- **FilterConfig**: Configura filtros.
- **WebConfig**: Configura CORS.
- **UpperCaseListener**: Listener para convertir campos a mayúsculas.

## 10. Otras Clases

- **ImageProcessing**: Manejo de carga y eliminación de imágenes.
- **GlobalExceptionHandler**: Manejo global de excepciones.
- **DataLoader**: Carga inicial de datos.
- **BackApplication**: Clase principal.

