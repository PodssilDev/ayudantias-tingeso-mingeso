package com.tingeso.oficinaRRHHservice.repositories;

import com.tingeso.oficinaRRHHservice.entities.OficinaRRHHEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OficinaRRHHRepository  extends JpaRepository<OficinaRRHHEntity, String> {

    public OficinaRRHHEntity findByRut(String rut);

    @Query(value = "insert into planilla_sueldos(rut) values(?)",
            nativeQuery = true)
    void insertarDatos(@Param("rut") String rut);
}