package cv.pn.processmanagement.business.intervinientes.repository;

import cv.pn.processmanagement.business.intervinientes.model.Intervenientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntervenientesRepository extends JpaRepository<Intervenientes, String> {

    //Optional<Intervenientes> findByOracleId(Long oracleId);

  //  Optional<Intervenientes> findById(Long id);
}
