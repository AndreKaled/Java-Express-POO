# Etapa 1: construir o projeto com Maven e JDK
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências antes do código — melhora o cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila o .jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: criar imagem final leve
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
