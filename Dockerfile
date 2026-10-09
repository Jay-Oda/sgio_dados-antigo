FROM maven:3.9-eclipse-temurin-21 AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN apk add dumb-init
RUN mkdir /app
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser


# Defina a variável de ambiente para o profile desejado

# Defina a variável de ambiente para a conexao com base de dados postgres
# ARG DRIVE_CLASS
# ARG DB_IP
ENV SPRING_DB_NAME=sigo
ENV SPRING_DB_USER=user_tre
ENV SPRING_DB_PASS=OV@sVIu*
ENV SPRING_DB_HOST=10.72.209.22
ENV SPRING_DB_PORT=5432
ENV SPRING_PROFILE=dev

# Define a variavel de ambiente para username e password do basic authentication
ENV SPRING_ACCESS_USERNAME=sigo
ENV SPRING_ACCESS_PASSWORD=sigo
ENV SPRING_ACCESS_HOST_SWAGGER=http://localhost:8080/api/swagger-ui/index.html

COPY --from=build /project/target/msoccurrence.jar /app/msoccurrence.jar
WORKDIR /app
#ARG profile=dev

EXPOSE 8080

RUN chown -R javauser:javauser /app
USER javauser
CMD "dumb-init" "java" "-Dspring.profiles.active=${SPRING_PROFILE}" "-jar" "msoccurrence.jar"
