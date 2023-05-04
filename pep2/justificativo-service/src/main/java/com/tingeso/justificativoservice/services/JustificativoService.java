package com.tingeso.justificativoservice.services;

import com.tingeso.justificativoservice.entities.JustificativoEntity;
import com.tingeso.justificativoservice.repositories.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JustificativoService {

    @Autowired
    private JustificativoRepository justificativoRepository;

    public void guardarJustificativo(String fecha, String rut){
        JustificativoEntity justificativo = new JustificativoEntity();
        justificativo.setFecha(fecha);
        justificativo.setRut(rut);
        justificativoRepository.save(justificativo);
    }

    public ArrayList<JustificativoEntity> obtenerJustificativos(){
        return (ArrayList<JustificativoEntity>)justificativoRepository.findAll();

    }
    public JustificativoEntity buscarJustificativo(String rut, String fecha){
        return this.justificativoRepository.buscarJustificativo(rut, fecha);
    }

    public void eliminarJustificativo(JustificativoEntity justificativo){
        this.justificativoRepository.delete(justificativo);
    }

    public List<JustificativoEntity> obtenerJustificativosEmpleado(String rut){
        return justificativoRepository.buscarJustificativosPorRUT(rut);
    }

    public void eliminarJustificativos(){
        this.justificativoRepository.deleteAll();
    }

}