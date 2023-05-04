package com.tingeso.marcasrelojservice.controllers;

import com.tingeso.marcasrelojservice.entities.MarcasRelojEntity;
import com.tingeso.marcasrelojservice.services.MarcasRelojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ws.rs.Path;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/marcas-reloj")
public class MarcasRelojController {

    @Autowired
    private MarcasRelojService marcasRelojService;

    @GetMapping
    public ResponseEntity<ArrayList<MarcasRelojEntity>> obtenerData() {
        ArrayList<MarcasRelojEntity> data = marcasRelojService.obtenerData();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/rut")
    public ResponseEntity<List<String>> obtenerRutsDeData(){
        List<String> ruts = marcasRelojService.obtenerRuts();
        if(ruts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ruts);
    }

    @GetMapping("/entrada/{rut}/{fecha}")
    public ResponseEntity<MarcasRelojEntity> obtenerHoraEntradaPorRut(
            @PathVariable("rut") String rut, @PathVariable("fecha") String fecha)
    {
        MarcasRelojEntity marca_entrada = marcasRelojService.obtenerEspecifico(rut, fecha);
        return ResponseEntity.ok(marca_entrada);
    }

    @GetMapping("/primeraasistencia/{rut}")
    public ResponseEntity<String> obtenerPrimeraAsistencia(@PathVariable("rut") String rut){
     String fecha = marcasRelojService.obtenerFechaRut(rut);
     if (fecha.isEmpty()){
         return ResponseEntity.noContent().build();
     }
     return ResponseEntity.ok(fecha);
    }

    @GetMapping("/salida/{rut}/{fecha}")
    public ResponseEntity<MarcasRelojEntity> obtenerHoraSalidaPorRut(
            @PathVariable("rut") String rut, @PathVariable("fecha") String fecha)
    {
        MarcasRelojEntity marca_salida = marcasRelojService.obtenerEspecifico2(rut, fecha);
        return ResponseEntity.ok(marca_salida);
    }

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException{
        marcasRelojService.guardar(file);
        marcasRelojService.leerTxt("Data.txt");
    }
}
