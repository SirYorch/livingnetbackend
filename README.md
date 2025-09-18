# livingnetbackend

## Descripción General
Este proyecto es un servicio backend para la gestión de reportes de programación de trabajos. Está construido con Spring Boot y proporciona APIs RESTful para crear, leer, actualizar y eliminar reportes relacionados con actividades laborales. El servicio también soporta filtrado, paginación, carga de imágenes y autenticación de usuarios mediante JWT.

## Funcionalidades
- Operaciones CRUD para reportes de programación de trabajos
- Filtrado dinámico y paginación de reportes
- Carga y gestión de imágenes con limpieza automática de imágenes no usadas
- Gestión de usuarios y autenticación con tokens JWT
- Provisión de valores distintos para desplegables que facilitan el filtrado en el frontend
- Uso de PostgreSQL como base de datos con JPA/Hibernate para ORM

## Instrucciones de Configuración

### Requisitos Previos
- Java 24 (se recomienda usar la versión LTS 21 para producción)
- Maven 3.x
- Base de datos PostgreSQL
- Python (opcional, para integración con script de procesamiento de imágenes)

### Configuración
- Actualizar las credenciales de la base de datos en `src/main/resources/application.properties`:
  ```
  spring.datasource.url=jdbc:postgresql://localhost:5432/livingnet
  spring.datasource.username=postgres
  spring.datasource.password=123
  ```
- Cambiar la clave secreta JWT en `JwtUtil.java` por seguridad.
- Las imágenes subidas vía API se almacenan en el directorio `uploads/` por defecto. Esto puede cambiarse en el servicio `ImageProcessing`.

### Compilación y Ejecución
- Compilar el proyecto con Maven:
  ```
  mvn clean install
  ```
- Ejecutar la aplicación Spring Boot:
  ```
  mvn spring-boot:run
  ```

## Endpoints de la API

### Reportes
- `GET /reports/all` - Obtener todos los reportes
- `POST /reports/filters` - Obtener reportes filtrados (acepta criterios de filtro en el cuerpo de la solicitud)
- `POST /reports/quantity` - Obtener la cantidad de reportes que coinciden con los filtros
- `POST /reports` - Agregar un nuevo reporte
- `PUT /reports` - Actualizar un reporte existente
- `DELETE /reports/{id}` - Eliminar un reporte por ID
- `GET /reports/deployables` - Obtener valores distintos para filtros desplegables

### Imágenes
- `POST /image/upload` - Subir una imagen (multipart/form-data)
- `GET /image/{filename}` - Obtener una imagen subida

## Pruebas
- Para las pruebas de funcionamiento se comparte una carpeta realizada en postman con pruebas básicas de las solicitudes, notese cambiar los tokens jwt en el header con la key Authorization

## Notas
- El proyecto usa `spring.jpa.hibernate.ddl-auto=update` para la gestión del esquema, adecuado para desarrollo pero no recomendado para producción. Usar herramientas de migración como Flyway o Liquibase en producción.
- La clave JWT y las credenciales de la base de datos deben externalizarse usando variables de entorno o almacenes seguros en producción.
- La función de carga de imágenes incluye una limpieza asíncrona que elimina imágenes no asociadas a ningún reporte después de 15 minutos.
- La integración con script Python para procesamiento de imágenes está presente pero actualmente comentada.
- La documentación con swagger openAPI se ve implementada en el archivo de docs.yaml.
- Mejorar la validación usando Bean Validation (JSR 380).

## Mejoras Futuras

- Mejorar el manejo de errores y el registro de logs.
- Asegurar la configuración sensible usando variables de entorno.
- Refactorizar código repetitivo para mejor mantenibilidad.


