# Ayudantía 3.5: Nginx

## Contenidos

- Modificación al Docker-Compose y 3 replicas
- Configuración de Nginx
- Ejecución del proyecto con Nginx

## Paso a Paso:

### Modificación al Docker-Compose y 3 replicas

En la evaluación, se pide **Los componentes para desplegar son: Base de Datos (MySQL), Aplicación Web (3réplicas), y Nginx como balanceador de carga (load balancer). 
Todos estos componentes deben se desplegados desde sus imágenes Docker respectivas.**

Primero, vamos a agregar Nginx a nuestro docker-compose. Debemos agregarlo como un servicio más, siguiendo la siguiente estructura:

```
nginx:
    image: nginx:latest
    container_name: nginx-proxy
    restart: always
    ports:
      - "80:80"
    volumes:
      - ./nginx/conf.d:/etc/nginx/conf.d
    depends_on:
      - app1
      - app2
      - app3
```
Al igual que otros servicios, estamos creando un contenedor que utiliza el puerto 80 (Puerto HTTP) y que obtiene la última imágen de nginx desde DockerHub. Tambien,
se puede observar que se menciona **conf.d**, lo cual está relacionado con la configuración de Nginx y el archivo que vamos a crear en el siguiente paso. Y si observamos
bien, notaremos que nginx depende de app1, app2 y app3. Pero, ¿que son app1, app2 y app3?

Recordemos que nuestro Docker-Compose tenia nuestra aplicación SpringBoot, definida como **app** dentro de los servicios. Ahora, vamos a cambiar el nombre de **app**
a **app1** y modificaremos el puerto interno de 8090 a **8091**. También es importante cambiar el nombre, para que el contenedor no tenga conflicto con los demás contenedores.
**app2** utilizará el puerto interno **8092** y **app3** el puerto interno **8093**. Nuestro **docker-compose.yml** deberia quedar similar al siguiente ejemplo:

```
version: "3.8"
services:
  nginx:
    image: nginx:latest
    container_name: nginx-proxy
    restart: always
    ports:
      - "80:80"
    volumes:
      - ./nginx/conf.d:/etc/nginx/conf.d
    depends_on:
      - app1
      - app2
      - app3

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

  app1:
    container_name: proyecto_docker1
    image: johnserrano159/proyecto_docker
    ports:
      - "8091:8090"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/tingeso2023?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=password
    deploy:
      restart_policy:
        condition: on-failure
    depends_on:
      - mysql-db

  app2:
    container_name: proyecto_docker2
    image: johnserrano159/proyecto_docker
    ports:
      - "8092:8090"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/tingeso2023?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=password
    deploy:
      restart_policy:
        condition: on-failure
    depends_on:
      - mysql-db

  app3:
    container_name: proyecto_docker3
    image: johnserrano159/proyecto_docker
    ports:
      - "8093:8090"
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

Es importante notar que el servicio y los puertos correspondientes a la base de datos no deben ser modificados.


### Configuración de Nginx

Dentro de la raíz de nuestro proyecto, vamos a crear una carpeta llamada **nginx**. Dentro de esta carpeta, creamos otra llamada **conf.d** y dentro de esta carpeta vamos 
a crear un archivo llamado **nombre_app**.conf (Recordar que nombre_app se define dentro de application.properties o application.yml)

El archivo deberia tener la siguiente estructura:

```
upstream nombre_app {
    server nombre_contenedor1:8090;
    server nombre_contenedor2:8090;
    server nombre_contenedor3:8090;
}

server {
    listen 80;
    charset utf-8;
    access_log off;

    location / {
        proxy_pass http://nombre_app;
        proxy_set_header Host $host:$server_port;
        proxy_set_header X-Forwarded-Host $server_name;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    location /static {
        access_log   off;
        expires      30d;

        alias /app/static;
    }
}
```

### Ejecución del proyecto con Nginx

Ahora, lo unico que falta es crear la imágen de Docker y subirla a DockerHub (Recordar que este proceso puede ser automático con Jenkins!). Y luego ejecutar

```
docker-compose up
```

Si todo resultó bien, notaremos que se han levantado 5 contenedores:

- La Base de Datos
- Nginx
- Replica App 1
- Replica App 2
- Replica App 3

Si bien la consola dice que la aplicación se ha levantado en el puerto 8090 (o el que hayamos decidido) la realidad es que ahora podemos acceder a la aplicación
simplemente utilizando http://localhost/ . Notemos que lo anterior no lleva puerto (Esto es porque se está utilizando el puerto HTTP!)

También, si observamos la consola de Docker, observaremos que cada cierto la aplicación va a ir cambiando la replica que se está usando, por lo que, por ejemplo,
a veces algunas instrucciones realizadas con la app 1 se realizarán con la app 2 o 3, y vice versa.

