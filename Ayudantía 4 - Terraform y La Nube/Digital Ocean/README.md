# Digital Ocean

```
https://www.digitalocean.com/
```

Digital Ocean tiene un costo inicial de $5 USD (5 dolares). Es importante que nos registremos con nuestra cuenta de GitHub, ya que se nos dará un credito de $200 los cuales tendrán una duración de 3 meses, y esto evitará que se tenga que pagar al utilizar los servicios de Digital Ocean mientras duren los 3 meses.

## Terraform

Lo primero que haremos en Ubuntu es crear una nueva carpeta. Esta puede tener cualquier nombre, como por ejemplo, **terraform-tingeso**. Dentro de esta carpeta, abrimos una terminal y escribimos:

```
code main.tf
```

Esto deberia abrir Visual Studio Code y el archivo **main.tf**. En este archivo es donde colocaremos la configuración principal de Terraform para indicarle que debe utilizar los servicios de Digital Ocean y de como queremos que sea nuestro servidor (tamaño Ram, memoria, etc).

Dentro de Digital Ocean, vamos a crear un proyecto en caso de que no tengamos uno. La configuración de este proyecto no importa. Luego, vamos a acceder a **API** y vamos a generar una nueva Token seleccionando **Generate New Token**. Le colocamos cualquier nombre y aceptamos. Abajo del nombre se nos generó un Token, por lo que lo copiamos y lo guardamos en algún lado, ya que lo necesitaremos para **main.tf**

Ahora, **main.tf** debe tener lo siguiente:

```

```
