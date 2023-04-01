# Ayudantía 2.5: SonarQube

## Contenidos

- Prerrequisitos a SonarQube y Variables de Entorno
- Instalación y uso de SonarQube
- La interfaz de SonarQube y su significado
- Aumentar el porcentaje de cobertura con sonar exclusions


## Paso a paso

**IMPORTANTE:** Si por alguna razón algo falla, especialmente el punto 4 de aumentar el porcentajde de cobertura con Sonar Exclusions, asegurarse de que el pom.xml esté **similar** al [pom del proyecto ejemplo](https://github.com/PodssilDev/ayudantias-tingeso-mingeso/blob/main/pep1/pom.xml)
### Prerrequisitos
Para ejecutar SonarQube de manera local, necesitamos tener Apache Maven dentro de nuestro dispositivo.

```
https://maven.apache.org/download.cgi
```

Se recomienda dejar la carpeta de Apache Maven dentro de la carpeta "Archivos de Programa". Es necesario tener en consideración la ruta de esta carpeta, ya que se debe agregar a las rutas de la variable de entorno Path.


[Se necesita de un JDK 11 o 17](https://jdk.java.net/archive/), ya que SonarQube no va a funcionar con otra versión de JDK distinta a 11 o 17. Si ya tenemos un JDK distinto a los anteriores, debemos descargar JDK 11 o 17 y [editar las variables de entorno](https://youtu.be/A5FHcR3cE-w). En el caso de la variable JAVA_HOME, se recomienda **borrarla y luego volver a crearla, en vez de solamente editarla**.

Podemos comprobar si tenemos un JDK 11 o 17 escribiendo el siguiente comando dentro de la CMD:

```
java --version
```

Lo mismo para Apache Maven, podemos ver si es que la variable de entorno quedó correctamente configurada escribiendo:

```
mvn --version
```

### Instalación de SonarQube y ejecución

[Desde el sitio web oficial de SonarQube](https://www.sonarsource.com/products/sonarqube/downloads/) debemos descargar **SonarQube Community Edition**. Dejamos la carpeta en algún lado que no nos estorbe y la abrimos. Dentro de la carpeta **bin**, seleccionamos nuestro Sistema Operativo y luego abrimos una CMD dentro de la carpeta (Para el caso de Windows, windows-x86-64). Y escribimos: 

```
StartSonar.bat
```

Si efectivamente tenemos un JDK 11 o 17, SonarQube comenzará a ejecutarse. La primera vez puede demorar, pero si aparece **SonarQube is operational** entonces SonarQube ya estará corriendo en http://localhost:9000/

Si vamos a la ruta, nos aparecerá una especie de logueo. Por defecto, el usuario es **admin** y la contraseña también es **admin**. La primera vez que ingresemos, nos pedirá cambiar la contraseña. Luego de eso, ya estaremos dentro del menú principal de SonarQube. Nos pedirá ahora como queremos crear nuestro proyecto de SonarQube, por lo que debemos bajar donde dice "Manually" y hacemos click. Colocamos un nombre simple como nombre del proyecto (Ej: pep1) y dejamos todo lo demás igual. Seleccionamos "Set Up" y estaremos dentro del menú principal de nuestro proyecto de SonarQube.

Ahora, bajamos donde dice **"Locally"** y como nombre de Token, colocamos el mismo nombre del proyecto y que expire en 90 días. SonarQube nos dará una especie de Token y luego nos pedirá seleccionar en que está construido nuestro proyecto. Seleccionamos **maven** y aceptamos. Luego de esto, SonarQube nos mostrará un comando, el cual debemos ejecutar dentro de la carpeta de nuestro proyecto para realizar el análisis de nuestro código. Para que el comando se pueda ejecutar correctamente, debemos quitar todos los \.

(Ejemplo)

```
mvn clean verify sonar:sonar  -Dsonar.projectKey=pep1  -Dsonar.host.url=http://localhost:9000  -Dsonar.login=sqp_fc56b1ba154bd3b0bf2aaa644210b01404548520
```

Se recomienda guardar el comando anterior dentro de un lugar seguro. **Es importante que la Base de Datos esté funcionando antes de ejecutar el comando anterior, ya que prácticamente se ejecutará el proyecto entero para realizar el análisis**

### La interfaz de SonarQube

Una vez que se ejecute el comando anterior y aparezca el mensaje **BUILD SUCCESS**, en la linea que dice **ANALYSIS SUCCESSFUL** se mostrará una ruta, la cual si vamos allí, podremos acceder a la interfaz que nos muestra el resultado del análisis de nuestro proyecto. 

Esto nos mostrará el análisis de mantenibilidad, confiabilidad y seguridad de nuestro proyecto, junto con los Code Smells del proyecto y el porcentaje de cobertura total del proyecto. **Recordar que para la evaluación se pide que este porcentaje llegue a 90%**.

### Aumentar el porcentaje de cobertura

Si bien podemos aumentar el porcentaje de cobertura realizando Tests Unitarios, por defecto SonarQube también considerará a los Controllers, Entities y Repositories. Ya que solo se pide realizar tests para los Services, podemos colocar que SonarQube ignore completamente a los Entities, Controllers y Repositories colocando dentro en nuestro pom.xml lo siguiente dentro de **Properties** (abajo donde se especifica la versión de java):

(Ejemplo, las rutas podrian cambiar dependiendo del nombre del proyecto)
```
		<sonar.exclusions>
			src/main/java/tingeso/mingeso/pep1/entities/*,
			src/main/java/tingeso/mingeso/pep1/controllers/*,
			src/main/java/tingeso/mingeso/pep1/repositories/*
		</sonar.exclusions>
```

Luego solo queda volver a realizar el análisis, ejecutando nuevamente el comando grande mencionado anteriormente. Cabe destacar de que al realizar un segundo análisis, SonarQube agregará una pestaña nueva llamada **New Code**, donde se comparará que tanto cambió el código luego del análisis más reciente comparadom con al anterior. Para volver a ver el porcentaje de cobertura, debemos seleccionar la pestaña **Overall Code**.
