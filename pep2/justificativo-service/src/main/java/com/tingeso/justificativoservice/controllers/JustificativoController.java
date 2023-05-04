package com.tingeso.justificativoservice.controllers;

import com.tingeso.justificativoservice.entities.JustificativoEntity;
import com.tingeso.justificativoservice.services.JustificativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/justificativo")
public class JustificativoController {

    @Autowired
    JustificativoService justificativoService;

    @GetMapping
    public ResponseEntity<ArrayList<JustificativoEntity>> obtenerJustificativos(){
        ArrayList<JustificativoEntity> justificativos = justificativoService.obtenerJustificativos();
        if(justificativos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(justificativos);
    }

    @GetMapping("/porempleados/{rut}/{fecha}")
    public ResponseEntity<JustificativoEntity> obtenerJustificativosPorRut(@PathVariable("rut") String rut, @PathVariable("fecha") String fecha) {
        JustificativoEntity justificativos = justificativoService.buscarJustificativo(
                rut, fecha);
        return ResponseEntity.ok(justificativos);
    }

    @PostMapping
    public ResponseEntity<JustificativoEntity> guardarJustificativo(@RequestBody JustificativoEntity justificativo) {
        String rut = justificativo.getRut();
        String fecha = justificativo.getFecha();
        justificativoService.guardarJustificativo(fecha, rut);
        return ResponseEntity.ok(justificativo);
    }

    @GetMapping("/eliminar")
    public void eliminarJustificativos(){
        justificativoService.eliminarJustificativos();
    }
}