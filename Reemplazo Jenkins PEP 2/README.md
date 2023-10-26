# Scripts de Reemplazo para Jenkins en PEP 2

Es sabido que configurar Jenkins no es una tarea facil, especialmente si se está trabajando con un repositorio de Github privado. Pero, ¿que pasa cuando tenemos microservicios?

Un reemplazo fácil y rápido de Jenkins es crear 3 scripts .bat que automatizan el proceso de buildear y pushear la imágen de Docker al repositorio de DockerHub.

El primer script, script1.bat, sirve para hacer el proceso de maven clean install. Se debe reemplazar **CARPETA** por el nombre de la carpeta del microservicio. Por ejemplo:

```
start /wait /D backend mvnw clean install -f pom.xml
```

Donde backend corresponde a un microservicio creado con Springboot y que utiliza Maven.

El segundo script, script2.bat, sirve para hacer el proceso de Docker Build. Se debe reemplazar USERNAME por el nombre de usuario en DockerHub y PROYECTO_DOCKERHUB por el nombre del proyecto de DockerHub asociado a ese microservicio (Recordar que en Microservicios, cada microservicio tendrá su propio DockerFile y su propio repositorio de DockerHub, incluyendo el Frontend). Por ejemplo:

```
docker build -f backend/Dockerfile -t johnserrano159/backend:latest ./backend
docker build -f frontend/Dockerfile -t johnserrano159/frontend:latest ./frontend
```

El último script, script3.bat, sirve para hacer el proceso de Docker Push. Similar al script 2, un ejemplo es:

```
docker push johnserrano159/backend:latest
docker push johnserrano159/frontend:latest
```

Por lo tanto, en primer lugar, se debe ejecutar el primer Script para hacer el proceso de Maven clean install. Esto creará la última versión del archivo .JAR de nuestros microservicios. Luego, **con Docker encendido**, se debe ejecutar script2.bat. Esto va a realizar el build de cada microservicio y frontend. Por último, ejecutar script3.bat hará el proceso de Docker Push y se pushearan una por una las imágenes creadas a Docker Hub. 

En conclusión, esta es una forma rápida y sencilla de replicar el procedimiento que hacia Jenkins, sin necesidad de dar permisos o hacer nuestro repositorio público (Funciona en repositorios privados).