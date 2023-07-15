package backend.tingeso.facil.repositories;

import backend.tingeso.facil.entities.FacilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilRepository  extends JpaRepository<FacilEntity, Integer> {
}
