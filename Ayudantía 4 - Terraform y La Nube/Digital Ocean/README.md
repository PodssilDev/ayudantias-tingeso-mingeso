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
