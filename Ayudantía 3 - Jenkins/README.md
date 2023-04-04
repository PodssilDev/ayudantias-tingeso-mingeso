# Ayudantía 3: Jenkins

## Contenidos

- Prerrequisitos e instalación de Jenkins
- Uso de Jenkins y configuracion general
- Creación del pipeline de Jenkins (Jenkinsfile)
- Ejecución del Pipeline y Automatización


## Paso a paso

### Prerrequisitos para Jenkins
Para ejecutar Jenkins, debemos de asegurarnos de tener la carpeta **Git\usr\bin** dentro del PATH en las Variables de Entorno. Para ello, debemos tener Git instalado en nuestro dispositivo. La carpeta anterior se encuentra dentro de la carpeta **Archivos de Programa**

![image](https://user-images.githubusercontent.com/91446330/229558018-d66e145b-19c3-44be-8837-a123d5a5cd72.png)


Se recomienda reiniciar el computador una vez que se editen las variables de entorno, para asegurar que los cambios se reflejen correctamente.

También es necesario tener el JAVA_HOME configurado, pero a estas alturas del proyecto, ya deberian tener eso.

Debemos descargar **Jenkins 2.387.1 LTS for Generic Java package (war)** desde el siguiente sitio:

```
https://www.jenkins.io/download/
```

Lo anterior descargará un solo archivo llamado **jenkins.war**. Se recomienda guardar este archivo en algún lugar accesible, como la raíz del repositorio de Github, aunque si realizamos esto, debemos asegurarnos de configurar nuestro .gitignore de tal forma de que no se incluya jenkins.war dentro de los commits. Luego, abrimos una CMD dentro de la carpeta donde se encuentra el archivo y ejecutamos el siguiente comando

```
java -jar jenkins.war
```

La primera vez que se ejecute el comando, se demorará en cargar. Podemos notar que hay un mensaje que menciona una especie de clave secreta, esto lo debemos tener en consideración en unos minutos. 

![image](https://user-images.githubusercontent.com/91446330/229565458-5b518c72-7808-4761-87e2-a371ad9ff48f.png)

Cuando aparezca el mensaje **jenkins is fully up and running**, podemos acceder a http://localhost:8080/ y utilizar Jenkins.


### Uso de Jenkins y configuracion general

Al acceder a http://localhost:8080/, nos pedirá colocar el password que se mostró por consola. Lo copiamos y lo colocamos. Luego habrán dos opciones para instalar unos plugins. Seleccionamos la opción de la izquierda y dejamos que se complete la instalación.

Luego, Jenkins nos pedirá crear un usuario. Vamos a crear el usuario admin1 con contraseña admin1. Luego aceptamos la instance configuration y colocamos "Start using Jenkins".

### Creación del pipeline de Jenkins (Jenkinsfile)

Una vez ya dentro del menú principal de Jenkins, en el menú de la izqueirda debemos seleccionar **Nueva tarea**. Le colocamos de nombre **pipeline-jenkins** y seleccionamos la opción **Pipeline**. Apretamos ok y esto nos llevará a otro menú, donde podemos colocar cualquier descripción. Bajando, seleccionamos **GitHub project** y colocamos la URL de nuestro repositorio de GitHub. Dentro de **Build Triggers** seleccionamos **GitHub hook trigger for GITScm polling**. Luego, en **Pipeline** tenemos dos opciones. Podemos hacer el script del Pipeline en la página de Jenkins o bien, crear en la raíz de nuestro proyecto un script de nombre **Jenkinsfile**. Sea la opción que sea la que elijamos, primero guardamos, sin crear ningún pipeline. Vamos al panel de control de Jenkins, luego **Administrar Jenkins** y **Global Tool Configuration**. 

Bajamos a la opción **Maven** y seleccionamos "Añadir Maven". De nombre vamos a colocar **maven** y la versión 3.9.1. y guardamos. Ahora, entramos dentro del pipeline y luego configurar. Bajamos a **Pipeline syntax**. En Sample Step seleccionamos **checkout: Check out from version control**. Colocamos la URL de nuestro repositorio Git y cambriamos la branch de master a main (O lo dejamos como master si es que nuestra branch principal se llama así). Seleccionamos **Generate Pipeline Script** y esto nos mostrará una linea que debemos utilizar en nuestro Pipeline. 

Luego abrimos una copia de la pestaña y seleccionamos **withCredentials: Bind credentials to variables**. En Bindings seleccionamos **Secret text** y como nombre de variable colocamos **dckpass** y en credentials seleccionamos **Jenkins**. En Kind nuevamente seleccionamos **secret text**, en secret colocamos nuestra contraseña de Docker Hub y como ID colocamos **dckrhubpassword**. Apretamos add y nuevamente Generate Pipeline Script. Esto nos dará otra linea que debemos usar en nuestro Pipeline.

Finalmente, ya con estas dos lineas, debemos construir nuestro Pipeline de Jenkins principal, el cual principalmente deberia tener la siguiente estructura:

```
pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PodssilDev/ayudantias-tingeso-mingeso']])
                dir("pep1"){
                    sh "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("pep1"){
                    sh "mvn test"
                }
            }
        }
        stage("SonarQube Analysis"){
            steps{
                dir("pep1"){
                    sh "mvn sonar:sonar -Dsonar.projectKey=pep1 -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_fc56b1ba154bd3b0bf2aaa644210b01404548520"
                }
            }
        }
        stage("Build Docker Image"){
            steps{
                dir("pep1"){
                    sh "docker build -t johnserrano159/proyecto_docker ."
                }
            }
        }
        stage("Push Docker Image"){
            steps{
                dir("pep1"){
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]){
                        sh "docker login -u johnserrano159 -p ${dckpass}"
                        
                    }
                    sh "docker push johnserrano159/proyecto_docker"
                    
                }
                
            }
        }
    }
    post{
        always{
            dir("pep1"){
                sh "docker logout"
            }
        }
    }
}
```

Notemos que principalmente, en el pipeline tenemos diversas "etapas". **dir** nos permite acceder al directorio donde se encuentra nuestro proyecto de Springboot, en caso de que no solamente esté la carpeta de nuestro proyecto en nuestro repositorio. También debemos de nota que en la primera y penultima etapa utilizamos las lineas obtenidas anteriormente y que también todos los comandos están relacionados con comandos que hemos realizado anteriormente (Mvn, Docker Build, Docker Push, etc).

En el caso de querer utilizar un Jenkinsfile, en la configuración (donde escribimos el pipeline), debemos asegurarnos de copiar el pipeline construido a algun lugar seguro. Luego, seleccionamos **Pipeline script from SCM**. En SCM seleccionamos Git y luego, colocamos el URL de nuestro repositorio Git y luego cambiamos la branch en caso de ser necesario. En **Script Path** colocamos el Path donde está nuestro Jenkinsfile 

(En el caso del proyecto de ejemplo, el path es)

```
pep1/Jenkinsfile
```

Por último, seleccinamos Apply y Guardar.

### Ejecución del Pipeline y automatización

Para ejecutar el pipeline, seleccionamos **Construir ahora** en el menú de la izquierda. **Debemos tener en consideración que antes de ejecutar el Pipeline, debemos asegurarnos que tengamos encendido Docker Desktop, la base de datos de nuestro proyecto y Sonarqube**. Si todo lo anterior está activado, ejecutamos el Pipeline.

![image](https://user-images.githubusercontent.com/91446330/229623055-681ced2b-a9d4-41ea-8cec-65aec84c7964.png)

Si todo aparece con verde, entonces el pipepline se ejecutó correctamente. Esto implica que se ejecutaron todos los tests y que tuvieron exito, se generó el reporte de SonarQube y se subió la imagen del proyecto a DockerHub.