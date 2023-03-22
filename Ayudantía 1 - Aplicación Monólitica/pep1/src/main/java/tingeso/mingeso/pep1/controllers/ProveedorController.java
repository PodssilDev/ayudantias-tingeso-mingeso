package tingeso.mingeso.pep1.controllers;

import tingeso.mingeso.pep1.entities.ProveedorEntity;
import tingeso.mingeso.pep1.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/lista-proveedores")
    public String listar(Model model) {
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "index";
    }

    @GetMapping("/nuevo-proveedor")
    public String proveedor(){
        return "nuevo-proveedor";
    }
    @PostMapping("/nuevo-proveedor")
    public String nuevoProveedor(@RequestParam("codigo") String codigo,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("categoria") String categoria,
                                 @RequestParam("retencion") String retencion){
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/nuevo-proveedor";
    }
}
