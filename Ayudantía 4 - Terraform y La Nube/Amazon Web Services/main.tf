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