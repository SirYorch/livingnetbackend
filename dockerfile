# Usa una imagen de Maven para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar pom.xml y bajar dependencias primero (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Imagen final para ejecutar la app
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el JAR generado
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto de tu backend
EXPOSE 8080

# Ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]
