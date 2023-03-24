package tingeso.mingeso.pep1.repositories;

import tingeso.mingeso.pep1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String>{

    @Query("select e from ProveedorEntity e where e.nombre = :nombre")
    ProveedorEntity findByNameCustomQuery(@Param("nombre") String nombre);

    @Query("select e.categoria from ProveedorEntity e where e.codigo = :codigo")
    String findCategory(@Param("codigo") String codigo);

    @Query("select e from ProveedorEntity e where e.codigo = :codigo")
    ProveedorEntity findByCodigo(@Param("codigo")String codigo);
}
