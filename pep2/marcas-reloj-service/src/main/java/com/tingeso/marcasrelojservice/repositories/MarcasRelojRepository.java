package com.tingeso.marcasrelojservice.repositories;

import com.tingeso.marcasrelojservice.entities.MarcasRelojEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MarcasRelojRepository extends JpaRepository <MarcasRelojEntity, Integer>{

    @Query(value = "select * from marcasreloj as e where e.rut = :rut and e.fecha =:fecha limit 1",
            nativeQuery = true)
    MarcasRelojEntity buscarData(@Param("rut") String rut, @Param("fecha") String fecha);

    @Query(value = "select distinct rut from marcasreloj", nativeQuery = true)
    List<String> findDistinctRut();

    @Query(value = "select e.fecha from marcasreloj as e where e.rut = :rut limit 1", nativeQuery = true)
    String buscarFechaRut(@Param("rut")String rut);

    @Query(value = "select * from marcasreloj as e where e.rut = :rut and e.fecha = :fecha order by e.hora desc limit 1",  nativeQuery = true)
    MarcasRelojEntity buscarData2(@Param("rut")String rut, @Param("fecha") String fecha);

    @Query(value = "select *  from marcasreloj as e where e.rut = :rut", nativeQuery = true)
    ArrayList<MarcasRelojEntity> eliminarData(@Param("rut")String rut);
}