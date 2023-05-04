package com.tingeso.autorizacionservice.repositories;

import com.tingeso.autorizacionservice.entities.AutorizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorizacionRepository extends JpaRepository<AutorizacionEntity,Integer> {

    @Query(value = "select * from autorizacion as e where e.rut = :rut and e.fecha =:fecha limit 1",
            nativeQuery = true)
    AutorizacionEntity  buscarAutorizacion(@Param("rut") String rut, @Param("fecha") String fecha);
}