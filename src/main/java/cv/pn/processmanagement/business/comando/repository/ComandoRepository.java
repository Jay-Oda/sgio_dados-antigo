package cv.pn.processmanagement.business.comando.repository;

import cv.pn.processmanagement.business.comando.model.Comando;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComandoRepository extends JpaRepository<Comando, String> {

    @Query("SELECT c FROM Comando c WHERE c.id_nacional = :idNacional")
    Optional<Comando> findByIdNacional(@Param("idNacional") Number idNacional);
}
