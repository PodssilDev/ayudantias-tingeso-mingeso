# Ayudantía 4: Terraform y La Nube (AWS & Digital Ocean)

## Prerrequisitos

- Esta ayudantía es realizada en el Sistema Operativo Ubuntu Linux. Recordar que podemos utilizar una [Máquina Virtual](https://www.virtualbox.org/) para utilizar el SO. También es necesario que en Ubuntu tengamos Visual Studio Code instalado, para hacer el proceso más facil.
- Se necesita instalar Terraform, para ello vamos a ejecutar distintos comandos en la terminal de Ubuntu.
- Luego se debe elegir el Servidor Web a utilizar para subir la aplicación en la nube, teniendo en consideración el costo de cada uno, la dificultad, los tiempos, etc.

### Instalación de Terraform en Ubuntu Linux

En primer lugar, debemos ejecutar el siguiente comando:

```
sudo apt-get update && sudo apt-get install -y gnupg software-properties-common
```

Luego, debemos obtener la llave de HashiCorp, que nos permitirá obtener Terraform. Para ello, ejecutamos:

```
wget -O- https://apt.releases.hashicorp.com/gpg | \
gpg --dearmor | \
sudo tee /usr/share/keyrings/hashicorp-archive-keyring.gpg
```

Luego de eso, agregamos el repositorio oficial de HashiCorp. Para ello, ejecutamos:

```
echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] \
https://apt.releases.hashicorp.com $(lsb_release -cs) main" | \
sudo tee /etc/apt/sources.list.d/hashicorp.list
```

Con el repositorio ya agregado, debemos actualizar los paquetes. Ejecutamos:

```
sudo apt update
```

Finalmente, para instalar Terraform, ejecutamos este comando:

```
sudo apt-get install terraform
```

Podemos comprobar si es que Terraform se instaló correctamente ejecutando:

```
terraform -help
```

### El servidor para acceder a la nube

Ya con Terraform instalado, ahora toca realizar una decisión: ¿Amazon Web Services o Digital Ocean?

- Digital Ocean suele ser más rapido de hacer funcionar y no requiere de tantas cosas técnicas. Al registrarse con nuestro GitHub se nos dará un beneficio de 200 creditos que durará apróximadamente 3 meses. Esto evita tener que gastar dinero al utilizar los servicios de Digital Ocean. Sin embargo, para poder acceder a los servicios de Digital Ocean hay que pagar un costo inicial de $5 USD (5 dolares), los cuales se pueden pagar con Tarjeta de Credito o PayPal

- Amazon Web Services es un poco más complicado que Digital Ocean, pero no tiene ningún costo inicial y todo el proceso se puede realizar completamente gratis. Para poder acceder a AWS, necesitamos de alguna tarjeta de credito, esta puede ser MATCH, Tempo, etc (La cuenta RUT no funciona!).

Ambos servidores nos permiten realizar lo que se piden en la evaluación, por lo que la elección se debe realizar teniendo en consideración el costo y el proceso a realizar.
