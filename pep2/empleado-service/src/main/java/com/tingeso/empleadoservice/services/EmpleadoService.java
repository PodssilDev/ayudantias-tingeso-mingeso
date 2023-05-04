package com.tingeso.empleadoservice.services;


import com.tingeso.empleadoservice.entities.EmpleadoEntity;
import com.tingeso.empleadoservice.models.AutorizacionModel;
import com.tingeso.empleadoservice.models.JustificativoModel;
import com.tingeso.empleadoservice.reporitories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<EmpleadoEntity> obtenerEmpleados(){
        return (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
    }

    public String obtenerCategoria(String rut){
        return empleadoRepository.findCategory(rut);
    }

    public EmpleadoEntity findByRut(String rut){
        return empleadoRepository.findByRut(rut);
    }

    public void guardarEmpleado(EmpleadoEntity empleado){
        empleadoRepository.save(empleado);
    }

    public void eliminarEmpleados(){
        empleadoRepository.deleteAll();
    }

    public List<JustificativoModel> obtenerJustificativos(String rut) {
        List<JustificativoModel> justificativos = restTemplate.getForObject("http://justificativo-service/justificativos/porempleados/" + rut, List.class);
        return justificativos;
    }
    public List<AutorizacionModel> obtenerAutorizaciones(String rut) {
        List<AutorizacionModel> autorizaciones = restTemplate.getForObject("http://autorizacion-service/autorizaciones/porempleados/" + rut, List.class);
        return autorizaciones;
    }
}