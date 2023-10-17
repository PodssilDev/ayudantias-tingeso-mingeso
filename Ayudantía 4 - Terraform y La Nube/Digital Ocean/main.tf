terraform {
  required_providers {
    digitalocean = {
      source = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "digitalocean" {
  token = "dop_v1_e255103795b6740c0a7c8054f6e7605e7a086744630c21b09a7267606d9337b7"
}

# Create a new Web Droplet in the nyc2 region
# 16/10/2023: ubuntu-18-04-x64 ya no funciona. Usar ubuntu-20-04-x64 en su reemplazo
resource "digitalocean_droplet" "web" {
  image  = "ubuntu-20-04-x64"
  name   = "web-1"
  region = "nyc3"
  size   = "s-1vcpu-2gb"
  ssh_keys = ["5f:8d:ca:ad:73:85:a1:b1:34:c2:16:08:8a:8d:32:48"]
}
