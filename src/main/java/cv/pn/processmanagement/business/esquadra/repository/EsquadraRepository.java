package cv.pn.processmanagement.business.esquadra.repository;

import cv.pn.processmanagement.business.esquadra.model.Esquadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EsquadraRepository extends JpaRepository<Esquadra, String>{

    Optional<Esquadra> findByOracleId(Long oracleId);
}
