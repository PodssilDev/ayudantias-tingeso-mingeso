# Amazon Web Services

```
https://aws.amazon.com/
```

Amazon Web Services solo requiere de una tarjeta de credito, ya sea MATCH, Tempo, entre otras. **La cuenta rut no funciona para registrarse correctamente en AWS.**
Tras el registro, por 1 año tendremos un beneficio para poder crear instancias sin costo adicional.

## Usuario de AWS y permisos asociados

En primer lugar, tras registrarnos en AWS y confirmar el registro, vamos a buscar **IAM** y acceder a **Administrar el acceso a los recursos de AWS**. En el menú
de la izquierda vamos a seleccionar **Usuarios**. Seleccionamos la opción de agregar usuarios, para crear un usuario de AWS y darle los permisos correspondientes.

Como nombre de usuario, vamos a colocar **root** y proporcionar acceso de usuario a la consola de administración de AWS. Seleccionamos "Quiero crear un usuario de IAM" y continuamos.

En la siguiente ventana se nos pedirá configurar los permisos. Seleccionamos la opción "Adjuntar políticas directamente" y debemos seleccionar los siguientes permisos:

* AdministratorAccess
* AmazonEC2FullAccess

Luego continuamos a la siguiente ventana. Confirmamos que nuestro usuario tenga de nombre root y que además de los dos permisos anteriores, tenga uno llamado **IAMUserChangePassword**.
Si es así, seleccionamos "Crear usuario" y volvemos a la lista de usuarios. Accedemos al usuario, seleccionamos "Credenciales de seguridad" y bajamos hasta donde dice
**Credenciales de acceso**. Seleccionamos "Crear clave de acceso" y seleccionamos la opción "Otro". Continuamos a la siguiente ventana y seleccionamos la opción "Crear clave de acceso".
Podemos observar una clave de acceso y una clave de acceso secreta. **Debemos guardar ambas en un lugar seguro, de tal forma de que se puedan copiar y pegar**. En la misma
página se nos da una opción para descargar un .csv con ambas claves, por lo que podemos hacer eso si lo deseamos. Seleccionamos listo y ya nuestro usuario tiene los
permisos y configuraciones necesarias para acceder a AWS.

## Terraform y main.tf

Lo primero que haremos en Ubuntu es crear una nueva carpeta. Esta puede tener cualquier nombre, como por ejemplo, **terraform-tingeso**. Debemos guardar las claves de acceso que obtuvimos del 
paso anterior, por lo que dentro de esta carpeta, abrimos una terminal y escribimos:

```
export AWS_ACCESS_KEY_ID=(CLAVE DE ACCESO)
```

Donde **(CLAVE DE ACCESO)** es la primera clave de acceso que obtuvimos. Luego, repetimos el proceso para la clave de acceso secreta.

```
export AWS_SECRET_ACCESS_KEY=(CLAVE DE ACCESO SECRETA)
```

Donde **(CLAVE DE ACCESO SECRETA)** es la segunda clave de acceso que obtuvimos. Ahora escribimos el siguiente comando en la terminal:

```
code main.tf
```

Esto deberia abrir Visual Studio Code y el archivo **main.tf**. En este archivo es donde colocaremos la configuración principal de Terraform para indicarle que debe utilizar los servicios de 
Amazon Web Services y de como queremos que sea nuestro servidor (tamaño Ram, memoria, etc).

Ahora, **main.tf** debe tener lo siguiente:

```
terraform {
	required_providers {
		aws = {
			source  = "hashicorp/aws" # Indica el servicio del proveedor a utilizar
			version = "~> 4.16"
    }
}

  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "us-west-2"
}

resource "aws_instance" "app-simple-aws" {
  ami           = "ami-0c2ab3b8efb09f272" # Imagen de la maquina virtual / depende de la región
  instance_type = "t2.micro" # Capacidad de la maquina virtual
  key_name      = aws_key_pair.kp.key_name # Indicar key pair de la instancia de EC2 a crear

  tags = {
    Name = "AppSimpleAwsEC2Instance"
  }
}

resource "tls_private_key" "pk" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "kp" {
  key_name   = "key"       # Crear key pair de acceso a EC2
  public_key = tls_private_key.pk.public_key_openssh

  provisioner "local-exec" { # Crear "key.pem"
    command = "echo '${tls_private_key.pk.private_key_pem}' > ./key.pem"
  }
}
```

Ahora ejecutamos el siguiente comando en la terminal:

```
terraform init
```

Esto instalará algunos directorios ocultos y todas las dependencias necesarias para poder utilizar Terraform correctamente en ese directorio. Si nos aparece el mensaje de **Terraform has been successfully initialized!** significa que ese directorio se configuró exitosamente como un directorio de Terraform, lo cual nos lleva al siguiente paso.

## Creación de la Instancia de AWS

Para asegurarse que no hayan errores en la creación de la Instancia de AWS, debemos escribir el siguiente comando:

```
terraform validate
```

Si muestra **success!** entonces estamos listos para iniciar la creación de la instancia. Ejecutamos:

```
terraform apply
```

Aceptamos y esperamos a que la instancia se cree. En la página de AWS, buscamos "EC2" y accedemos a la opción que dice "Servidores Virtuales en la Nube". 
Luego, seleccionamos "Grupos de seguridad" y en "Reglas de entrada" colocamos "Editar reglas de entrada". Este paso es importante ya que debemos habilitar el puerto 22
para así poder conectarnos con SSH y el puerto 80 para que nginx funcione correctamente. Agregamos una nueva regla de tipo "SSH" y automáticamente se colocará el puerto
22. Aceptamos y luego hacemos lo mismo pero de tipo "HTTP". Aceptamos. Guardamos las reglas y volvemos al menú de EC2. Ahora si, accedemos a las instancias y deberiamos
ver que la instancia fue creada.  Si por algún motivo no vemos nada y la terminal dice que la instancia se ha creado exitosamente, debemos asegurarnos de estar en
"Oregón". A la derecha arriba, a la izquierda de nuestro nombre de usuario, nos aseguramos que estemos en EE.UU. Oeste (Oregón) us-west-2. Y ahora si, deberiamos
ver nuestra instancia. Accedemos a ella y copiamos la IP pública (IPV4).

Volviendo a la terminal, ejecutamos el siguiente comando:

```
chmod 400 key.pem
```

Y ahora si, nos conectamos con ssh, utilizando el siguiente comando:

```
ssh -i key.pem ec2-user@(IP4 PUBLICA)
```

Donde (IP4 PUBLICA) es nuestra IP pública o IPV4 pública. Aceptamos y si todo salió bien, deberiamos acceder a la terminal de nuestra Instancia de AWS.

## Instalación de Docker en nuestra Instancia de AWS 

En primer lugar, debemos asegurarnos que nuestra instancia esté totalmente configurada. Ejecutamos el siguiente comando:

```
sudo yum update
```

Ahora instalamos Docker, ejecutando:

```
sudo yum install docker
```

Debemos darle permisos a nuestro usuario ec2 para que pueda utilizar Docker sin problemas. Ejecutamos los siguientes comandos:

```
sudo usermod -a -G docker ec2-user
```

```
id ec2-user
```

```
newgrp docker
```

Ahora debemos intalar Docker-Compose. Ejecutamos los siguientes comandos:

```
wget https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) 
```

```
sudo mv docker-compose-$(uname -s)-$(uname -m) /usr/local/bin/docker-compose
```

```
sudo chmod -v +x /usr/local/bin/docker-compose
```

Podemos comprobar que tanto docker y docker-compose esten instalados correctamente

```
docker -v
```

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
cd
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

Si bien es bonito tener una Instancia de AWS, siempre se recomienda eliminarlas cuando no se usen Para salir de la Maquina Virtual de nuestro servidor escribimos **exit** y esto nos devolverá a nuestro terminal normal de Ubuntu. Para eliminar la instancia, podemos escribir:

```
terraform destroy
```

Lo anterior nos pedirá confirmación. Esto también hará que la instancia se elimine de los servidores de AWS. **Es necesario hacer esto cuando ya finalicemos con la evaluación, para evitar el uso de recursos inecesarios y futuros costos monetarios**.
