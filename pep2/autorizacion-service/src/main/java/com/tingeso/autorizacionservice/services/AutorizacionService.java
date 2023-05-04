package com.tingeso.autorizacionservice.services;

import com.tingeso.autorizacionservice.entities.AutorizacionEntity;
import com.tingeso.autorizacionservice.repositories.AutorizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorizacionService {

    @Autowired
    private AutorizacionRepository autorizacionRepository;

    public List<AutorizacionEntity> obtenerAutorizaciones(){
        return autorizacionRepository.findAll();
    }

    public void guardarAutorizacion(String fecha, String rut){
        AutorizacionEntity autorizacion = new AutorizacionEntity();
        autorizacion.setFecha(fecha);
        autorizacion.setRut(rut);
        autorizacionRepository.save(autorizacion);
    }

    public AutorizacionEntity buscarAutorizacion(String rut, String fecha){
        return this.autorizacionRepository.buscarAutorizacion(rut, fecha);
    }

    public void eliminarAutorizacion(AutorizacionEntity autorizacion){
        this.autorizacionRepository.delete(autorizacion);
    }

    public void eliminarAutorizaciones(){
        this.autorizacionRepository.deleteAll();
    }
}