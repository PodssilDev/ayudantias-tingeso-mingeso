# Digital Ocean

```
https://www.digitalocean.com/
```

Digital Ocean tiene un costo inicial de $5 USD (5 dolares). Es importante que nos registremos con nuestra cuenta de GitHub, ya que se nos dará un credito de $200 los cuales tendrán una duración de 3 meses, y esto evitará que se tenga que pagar al utilizar los servicios de Digital Ocean mientras duren los 3 meses.

## Terraform y main.tf

Lo primero que haremos en Ubuntu es crear una nueva carpeta. Esta puede tener cualquier nombre, como por ejemplo, **terraform-tingeso**. Dentro de esta carpeta, abrimos una terminal y escribimos:

```
code main.tf
```

Esto deberia abrir Visual Studio Code y el archivo **main.tf**. En este archivo es donde colocaremos la configuración principal de Terraform para indicarle que debe utilizar los servicios de Digital Ocean y de como queremos que sea nuestro servidor (tamaño Ram, memoria, etc).

Dentro de Digital Ocean, vamos a crear un proyecto en caso de que no tengamos uno. La configuración de este proyecto no importa. Luego, vamos a acceder a **API** y vamos a generar una nueva Token seleccionando **Generate New Token**. Le colocamos cualquier nombre y aceptamos. Abajo del nombre se nos generó un Token, por lo que lo copiamos y lo guardamos en algún lado, ya que lo necesitaremos para **main.tf**

Ahora, **main.tf** debe tener lo siguiente:

```
terraform {
  required_providers {
    digitalocean = {
      source = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "digitalocean" {
  token = "(TOKEN)"
}
```

Notese que (TOKEN) es le token que obtuvimos anteriormente mediante la página web de Digital Ocean. Guardamos los cambios en el archivo y en el mismo directorio donde se encuentra el archivo, ejecutamos:

```
terraform init
```

Esto instalará algunos directorios ocultos y todas las dependencias necesarias para poder utilizar Terraform correctamente en ese directorio. Si nos aparece el mensaje de **Terraform has been successfully initialized1** significa que ese directorio se configuró exitosamente como un directorio de Terraform, lo cual nos lleva al siguiente paso.

## Creación de Droplet 

En palabras simples, un Droplet corresponde a una Máquina Virtual de Digital Ocean. Primero, necesitamos una SSH Key, lo cual nos permitirá acceder correctamente a la Maquina Virtual utilizando SSH. En el mismo directorio donde tenemos el archivo main.tf, escribimos en la terminal:

```
ssh-keygen
```

Luego de un segundo nos pedirá una especie de input. Apretamos enter 3 veces (de tal forma que los 3 inputs queden vacios) y se nos mostrará la nueva llave generada. La llave se genera el directorio **.ssh**. Necesitamos esa llave para colocarla dentro de Digital Ocean, por lo que en la terminal escribimos:

```
cat ~/.ssh/id_rsa.pub
```

Tras ejecutar el comando anterior, se nos mostrará la llave por pantalla (Una combinación aleatoria de letras y números, donde la parte final es el nombre que tiene nuestra terminal de Ubuntu). La copiamos y dentro de la página de Digital Ocean vamos a Settings, luego Security y Add SSH Key. Copiamos la llave SSH donde dice SSH key content y le colocamos cualquier nombre. La añadimos y se nos mostrará la llave pero de forma encriptada. La copiamos y la usamos para agregar lo siguiente al archivo main.tf:

```
resource "digitalocean_droplet" "web" {
  image  = "ubuntu-18-04-x64"
  name   = "web-1"
  region = "nyc3"
  size   = "s-1vcpu-2gb"
  ssh_keys = ["(SSH-KEY)"]
}
```

Donde (SSH-KEY) corresponde a la llave encriptada obtenida mediante la página web de Dgital Ocean. 

Guardamos las modificaciones al archivo y en el mismo directorio donde se encuentra el archivo, ejecutamos:

```
terraform apply
```

Nos pedirá confirmar, escribimos **yes**. Esto finalmente creará el Droplet, el cual podemos observar en la opción "Droplets" de a página de Digital Ocean. Cuando se haya creado correctamente, ejecutamos:

```
terraform show
```

Esto nos mostrará información importante sobre nuestro Droplet creado, donde debemos tener en cuenta la **ipv4_address**. Para acceder a la consola del Droplet, ejecutamos:

```
ssh root@(IPV4_ADDRESS)
```

Donde (IPV4_ADDRESS) corresponde a la IP publica de nuestro Droplet. SI ejecutamos lo anterior y esperamos a que cargue, nos encontraremos dentro de nuestro Droplet.

## Instalación de Docker en nuestro Droplet

Ya dentro de nuestro droplet, notaremos que tenemos accedo a una consola de Ubuntu. Lo primero es ejecutar:

```
sudo apt-get update
```

Y luego:

```
sudo apt-get upgrade
```

Lo anterior actualizará la Maquina Virtual de nuestro servidor. Ahora, para instalar Docker, debemos ejecutar los siguientes comandos:

```
sudo apt install curl
```

```
sudo curl -fsSL https://get.docker.com -o get-docker.sh
```

```
sudo sh ./get-docker.sh DRY_RUN=1 -o-
```

Esto instalará Docker y todo lo necesario en nuestro servidor. Podemos comprobar si Docker se instaló correctamente ejecutando:

```
docker -v
```

Ahora, para instalar docker-compose:

```
sudo apt install docker-compose
```

Nuevamente, comprobamos que se haya instalado correctamente:

```
docker-compose -v
```

Ahora debemos configurar que el servicio de Docker siempre esté activo, de tal forma de que cuando bajemos el servidor y lo volvamos a activar, no tengamos que configurar Docker manualmente. Para ello, ejecutamos los siguientes comandos:

```
sudo systemctl enable docker.service
```

```
sudo systemctl start docker.service
```

Podemos visualizar que se haya inicializado correctamente:

```
sudo systemctl status docker.service
```

## Docker-compose y Nginx

Ya casi estamos listos. Necesitamos tener nuestro docker-compose.yml y nuestra configuración de Nginx para poder levantar la aplicación. Primero, debemos de asegurar que tengamos nuestro docker-compose.yml disponible localmente, ya que así solo necesitamos copiarlo y modificarle algo muy pequeño. 

Ejecutamos el siguiente comando en nuestro servidor:

```
vi docker-compose.yml
```

Esto nos abrirá una terminal aparte. Apretamos la tecla *a* y esto nos permitirá editar. Pegamos el contenido de nuestro docker-compose.yml local y modificamos la versión de *3.8* a *3.5*. Esto evitará que suceda un error con la versión *3.8* de Docker Compose. Cuando ya hayamos realizado la modificación, apretamos la tecla **ESC** y escribimos **:wq** y apretamos **ENTER**. Esto cerrará la terminal que tiene el código de nuestro docker-compose y podemos comprobar que efectivamente se haya guardado exitosamente.

Si escribimos:

```
cat docker-compose.yml
```

Se mostrará por consola el contenido del docker-compose.yml que está en el servidor. Si algo no se guardó bien o hay algún error de escritura, podemos volver a escribir **vi docker-compose.yml** y luego apretar *a* para modificarlo.

Ahora, nos falta pasar la configuración de Nginx. Creamos un nuevo directorio llamado nginx, ejecutando:

```
mkdir nginx
```

Luego accedemos a el, utilizando:

```
cd nginx
```

Luego creamos otro directorio llamado conf.d:

```
mkdir conf.d
```

Accedemos a el:

```
cd conf.d
```
Y ahora creamos el nombre de nuestro archivo .conf, utilizando 

```
vi (NOMBRE_APP).conf
```

Donde (NOMBRE_APP) es el nombre de nuestra aplicación. Copiamos el contenido de nuestro archivo .conf local al archivo .conf creado dentro de nuestro servidor y guardamos. 

Ahora debemos volver a la raíz de nuestro servidor. Ejecutamos:

```
cd /root
```

## La hora de la verdad: Ejecutar Docker Compose

Todo lo ha anterior ha llevado a este momento: Nos falta solamente ejecutar Docker Compose.

Antes de hacerlo, podemos comprobar que tengamos el docker-compose.yml y la carpeta nginx en la raíz de nuestro servidor ejecutando el comando **ls**. Si es así, ejecutamos:

```
docker-compose --compatibility up
```

Compatibility hará que hayan menos probabilidades que nuestro docker-compose no falle. Esperamos a que se ejecute (Esto depende de la RAM de nuestro Ubuntu Local como también la RAM de nuestro servidor. Nuestro servidor deberia tener 2 GB de RAM, suficiente para ejecutar las 3 replicas).

Cuando ya todo haya iniciado correctamente, en cualquier navegador web accedemos a la ip pública de nuestro servidor y listo! Deberiamos poder interactuar con nuestra aplicación y si le pasamos la IP a cualquier persona, también deberia poder acceder, siempre y cuando el servidor esté encendido.

Cuando queramos bajar el docker-compose, apretamos CTRL + C y luego:

```
docker-compose down
```

## Matando a Terraform

Si bien es bonito tener un Droplet, este puede costar dinero si no se elimina a tiempo. Para salir de la Maquina Virtual de nuestro servidor escribimos **exit** y esto nos devolverá a nuestro terminal normal de Ubuntu. Para eliminar el Droplet, podemos escribir:

```
terraform destroy
```

LO anterior nos pedirá confirmación. **Es necesario hacer esto cuando ya finalicemos con la evaluación, para evitar costos monetarios y para así bajar y destruir el Droplet, ya que aunque no estemos ejecutando el Droplet en Ubuntu, este sigue activo en la página de Digital Ocean**.
