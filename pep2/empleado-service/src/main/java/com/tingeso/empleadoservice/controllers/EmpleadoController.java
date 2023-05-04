package com.tingeso.empleadoservice.controllers;

import com.tingeso.empleadoservice.entities.EmpleadoEntity;
import com.tingeso.empleadoservice.models.AutorizacionModel;
import com.tingeso.empleadoservice.models.JustificativoModel;
import com.tingeso.empleadoservice.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoEntity>> obtenerEmpleados(){
        List<EmpleadoEntity> empleados = empleadoService.obtenerEmpleados();
        if(empleados.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(empleados);
    }
    @GetMapping("/{rut}")
    public ResponseEntity<EmpleadoEntity> obtenerPorRut(@PathVariable("rut") String rut){
        EmpleadoEntity empleado = empleadoService.findByRut(rut);
        if(empleado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public void guardarEmpleado(@RequestBody EmpleadoEntity empleado){
        empleadoService.guardarEmpleado(empleado);
    }

    @GetMapping("/eliminar")
    public void eliminarEmpleados(){
        empleadoService.eliminarEmpleados();
    }

    @GetMapping("/justificativos/{rut}")
    public ResponseEntity<List<JustificativoModel>> obtenerJustificativos(@PathVariable("rut") String rut) {
        EmpleadoEntity empleado = empleadoService.findByRut(rut);
        if(empleado == null)
            return ResponseEntity.notFound().build();
        List<JustificativoModel> justificativos = empleadoService.obtenerJustificativos(rut);
        return ResponseEntity.ok(justificativos);
    }

    @GetMapping("/autorizaciones/{rut}")
    public ResponseEntity<List<AutorizacionModel>> obtenerAutorizaciones(@PathVariable("rut") String rut) {
        EmpleadoEntity empleado = empleadoService.findByRut(rut);
        if(empleado == null)
            return ResponseEntity.notFound().build();
        List<AutorizacionModel> autorizaciones = empleadoService.obtenerAutorizaciones(rut);
        return ResponseEntity.ok(autorizaciones);
    }
}
