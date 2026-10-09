package cv.pn.processmanagement.business.sigoOcorrencia.repository;

import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SgoTpOcorrenciaRepository extends JpaRepository<SgoTpOcorrencia, String> {

    Optional<SgoTpOcorrencia> findByOracleId(Long oracleId);

}
