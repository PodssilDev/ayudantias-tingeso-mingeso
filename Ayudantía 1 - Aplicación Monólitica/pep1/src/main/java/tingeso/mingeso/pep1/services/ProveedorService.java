package tingeso.mingeso.pep1.services;

import tingeso.mingeso.pep1.entities.ProveedorEntity;
import tingeso.mingeso.pep1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public void guardarProveedor(String codigo, String nombre, String categoria, String retencion){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        proveedorRepository.save(proveedor);
    }
    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public String obtenerCategoria(String codigo){
        return proveedorRepository.findCategory(codigo);
    }

    public ProveedorEntity findByCodigo(String codigo){
        return proveedorRepository.findByCodigo(codigo);
    }
}
