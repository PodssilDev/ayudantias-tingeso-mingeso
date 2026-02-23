# Ayudantía 2: Docker

## Contenidos

- Introducción a Docker y su instalación
- Creación de volumen para la base de datos
- Creación de contenedor para la base de datos
- Compilación del projecto (jar) y Dockerfile
- Dockerhub y creación de repositorio
- Subir la imagen del proyecto a Dockerhub
- Docker Compose

## Paso a paso

### Instalación de Docker Desktop

En primer lugar, debemos descargar Docker Desktop desde el siguiente link:

```
https://www.docker.com/products/docker-desktop/
```

El instalador solo pide permisos para crear un acceso rapido en el escritorio, pero debemos tener en consideración de que Docker quedará configurado para que automáticamente se encienda al encender el PC. Por lo tanto, para evitar que consuma recursos y bateria debemos desactivar esto en la configuración de nuestro sistema.

- **IMPORTANTE: Si al abrir Docker Desktop, aparece un mensaje que dice algo del estilo "WSL 2 update requiered", debemos [descargar e instalar la siguiente actualización para WSL](https://learn.microsoft.com/es-mx/windows/wsl/install-manual#step-4---download-the-linux-kernel-update-package) (el cual se instaló automáticamente al instalar Docker Desktop)**

### DockerHub

Es necesario registrarse y crear una cuenta en DockerHub. Luego crear un repositorio que idealmente debe tener el mismo nombre de nuestra aplicación web.

```
https://hub.docker.com/
```

### Crear volumen para persistencia de datos

Para mantener y guardar la información de nuestra Base de Datos cada vez que se eliminan los contenedores de Docker, es necesario crear algo que se conoce como **Volumen** para nuestra Base de Datos. Esto creará una carpeta llamada **data** dentro de nuestro proyecto, la cual es importante **no incluir dentro de nuestro repositorio Git**.

#### Limpiar volumenes anteriores

Lo primero que debemos hacer es limpiar los volumenes anteriores que se hayan creado, esto para que el proceso de creación sea más rapido y eficiente.

```
docker volume prune
```

#### Crear volumen de la Base de Datos

Ahora, podemos crear un volumen para nuestro base de datos. Este volumen debe tener un nombre, en este caso el nombre es **mysql-db-data**.

```
docker volume create mysql-db-data
```

#### Ver volumenes creados

Podemos ver los volumenes que se hayan creado ejecutando el siguiente comando:

```
docker volume ls
```

### Crear imagen del proyecto

En IntelliJ, en el lado derecho hay un boton que dice **Maven**. Al apretarlo se abrirá un menú lateral con varias opciones. Dentro de la opción lifecycle, se debe primero ejecutar la opción **clean**, una vez que el proceso acabe, ejecutar la opción **install**. Para esto, hay que asegurarse que nuestra base de datos esté activada y que **todos los tests sean exitosos**.

#### Creación de Dockerfile

Dentro de la carpeta target, hay un archivo.jar. Debemos tener en cuenta este nombre, ya que es necesario para la creación de un Dockerfile, un archivo que nos permitirá subir nuestra aplicación a Dockerhub. Este archivo debe ser creado en la raíz de nuestro proyecto y debe ser llamado simplemente **Dockerfile** y no tener extensión. **(Actualizado Febrero 2026!)**

```
FROM eclipse-temurin:25-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pep1-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/pep1-0.0.1-SNAPSHOT.jar"]
```

En el ejemplo anterior, en la primera linea se indica el jdk que se está utilizando. Luego en la tercera linea se indica el nombre del archivo .jar, junto con la extensión correspondiente. En la cuarta linea se indica el puerto de la aplicación y en la quinta linea nuevamente se indica el nombre del archivo .jar.

#### Logueo con Dockerhub y creación de imágen

En Docker Desktop, debemos iniciar sesión con nuestra cuenta de Dockerhub. De manera alternativa, podemos abrir una CMD y colocar el comando:

```
docker login
```

Este comando nos permitirá loguearnos en Dockerhub siguiendo las indicaciones que aparecen en pantalla.

Una vez realizado lo anterior, ya podemos crear la imágen de nuestro proyecto escribiendo el siguiente comando dentro de la raiz de nuestro proyecto y teniendo en consideración nuestro nombre de usuario de DockerHub y el nombre del repositorio que creamos en DockerHub:

```
docker build -t <nombre_usuario>/<nombre_repositorio> .
```

#### Subir imágen a Dockerhub

Si lo anterior fue exitoso, en la misma CMD podemos escribir el siguiente comando para subir la imágen a Dockerhub:

```
docker push <nombre_usuario>/<nombre_repositorio>
```

#### Ver imagenes creadas

Podemos ver las imagenenes creadas en nuestro sistema ejecutando el siguiente comando:

```
docker image ls
```

#### Eliminar imagen

Podemos eliminar una imagen al ejecutar:

```
docker rmi <nombre_imagen>
```

### Docker Compose y creación de contenedor de la aplicación

Para encapsular la imágen de nuestro proyecto y de la base de datos en un solo contenedor, podemos utilizar un archivo especial de nombre **docker-compose.yml**. Este archivo debe ser creado dentro de la raíz de nuestro proyecto (Mismo lugar donde creamos el Dockerfile anterior) y tener la siguiente estructura:

(Ejemplo)

```
version: "3.8"
services:
  mysql-db:
    image: mysql
    restart: always
    volumes:
      - ./data/db:/var/lib/mysql
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: tingeso2023
    ports:
      - 33060:3306
  app:
    container_name: proyecto_docker
    image: johnserrano159/proyecto_docker
    ports:
      - "8090:8090"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/tingeso2023?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=password
    deploy:
      restart_policy:
        condition: on-failure
    depends_on:
      - mysql-db
```

#### Levantar contenedores

En la raíz del proyecto, con el archivo **docker-compose.yml** ya creado, ejecutar el siguiente comando, el cual levantará el contenedor de la aplicación junto con el de la base de datos. Si todo fue exitoso, la aplicación podrá ser accedida en el puerto especificado mediante un navegador web.

```
docker-compose up
```

#### Ver contenedores

Mediante Docker Desktop podemos ver los contenedores que tenemos en nuestro dispositivo, pero también es posible verlos mediante CMD ejecutando el siguiente comando:

```
docker ps
```

### Eliminar contenedores

Mediante Docker Desktop podemos eliminar contenedores que ya no necesitamos, pero también es posible realizar esto mediante CMD ejecutando el siguiente comando:

```
docker rm -f <nombre_contenedor>
```

#### Terminar ejecución

Si bien al apretar CTRL + C en la consola donde se está ejecutando Docker Compose tiende a detener la ejecución de los contenedores, si por algún motivo no se detuvieron completamente, se puede ejecutar este comando para detenerlos completamente:

```
docker-compose down
```
