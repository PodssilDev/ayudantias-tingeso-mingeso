package backend.tingeso.facil.services;

import backend.tingeso.facil.entities.FacilEntity;
import backend.tingeso.facil.repositories.FacilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FacilService {
    @Autowired
    private FacilRepository facilRepository;

    public ArrayList<FacilEntity> obtenerData(){
        return (ArrayList<FacilEntity>) facilRepository.findAll();
    }
}
