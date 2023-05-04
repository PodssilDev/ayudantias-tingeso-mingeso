package com.tingeso.justificativoservice.repositories;

import com.tingeso.justificativoservice.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificativoRepository extends JpaRepository<JustificativoEntity,Integer> {

    @Query(value = "select * from justificativo as e where e.rut = :rut and e.fecha =:fecha limit 1",
            nativeQuery = true)
    JustificativoEntity buscarJustificativo(@Param("rut") String rut, @Param("fecha") String fecha);

    @Query(value = "select * from justificativo as e where e.rut = :rut", nativeQuery = true)
    List<JustificativoEntity> buscarJustificativosPorRUT(@Param("rut")String rut);
}