# springboot-default-project
The basic structure for projects developed in spring boot

1. Add profiles in pom.xml
```xml
<profiles>
	<profile>
		<id>dev</id>
		<activation>
			<activeByDefault>true</activeByDefault>
		</activation>
		<properties>
			<spring.profiles.active>dev</spring.profiles.active>
		</properties>
		<dependencies>
			<!-- Dependências específicas para o perfil dev -->
		</dependencies>
	</profile>
	<profile>
		<id>qa</id>
		<properties>
			<spring.profiles.active>qa</spring.profiles.active>
		</properties>
		<dependencies>
			<!-- Dependências específicas para o perfil prod -->
		</dependencies>
	</profile>
	<profile>
		<id>prod</id>
		<properties>
			<spring.profiles.active>prod</spring.profiles.active>
		</properties>
		<dependencies>
			<!-- Dependências específicas para o perfil prod -->
		</dependencies>
	</profile>
</profiles>
```
2. Active profile active in application.properties
```yaml
spring.profiles.active=@spring.profiles.active@
```
3. Add Dockerfile in the project root
```text
# Use a imagem do openjdk como base
FROM openjdk:11

# Define o diretório de trabalho para /app
WORKDIR /app

# Copie o arquivo jar da aplicação e o arquivo mvnw para o diretório de trabalho
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Define as permissões de execução para o arquivo mvnw
RUN chmod +x ./mvnw

# Defina a variável de ambiente para o profile desejado
ARG profile=dev

# Execute o mvnw para construir a aplicação
RUN ./mvnw clean install -P $profile

# Copie o arquivo jar da aplicação para o diretório de trabalho
COPY target/*.jar msapplication.jar

EXPOSE 8082

# Execute o comando abaixo para iniciar a aplicação
CMD ["java", "-jar", "msapplication.jar"]
```
4. Build image docker with profile
```bash
docker build --build-arg profile=dev -t <image_name> .
```
5. To build image docker with env connect the database add
```text
# Defina a variável de ambiente para a conexao com base de dados postgres
# ARG DRIVE_CLASS
# ARG DB_IP
ARG DB_NAME
ARG DB_PORT
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://$DB_URL:$DB_PORT/$DB_NAME
ENV SPRING_DATASOURCE_USERNAME=$DB_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD

```
6. Build image docker with env connet database postgresql
```bash
docker build --build-arg profile=<profile> --build-arg DB_URL=<host> 
--build-arg DB_PORT=<port> --build-arg DB_NAME=<database_name> 
--build-arg DB_USERNAME=<username> --build-arg DB_PASSWORD=<password> 
-t <image_name> .
```
7. To build image docker with env connect the database add
```text
# Define a variavel de ambiente para username e password do basic authentication
ARG BASIC_USERNAME
ARG BASIC_PASS
ENV ACCESS_USERNAME = $BASIC_USERNAME
ENV ACCESS_PASSWORD = $BASIC_PASS

```
8. Build image docker with env connet database postgresql
```bash
docker build --build-arg profile=<profile> --build-arg DB_URL=<host> 
--build-arg DB_PORT=<port> --build-arg DB_NAME=<database_name> 
--build-arg DB_USERNAME=<username> --build-arg DB_PASSWORD=<password> 
--build-arg ACCESS_USERNAME=<username_base_auth> --build-arg ACCESS_PASSWORD=<password_base_auth> 
-t <image_name> .
```

9. Run docker image
```bash
docker run -p 8080:8082 <image_name>
```

