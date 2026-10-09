package cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.repository;

import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.model.Sgo_Ocorrencia_TP_Ocorrencia;
import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Sgo_Ocorrencia_TP_OcorrenciaRepository extends JpaRepository<Sgo_Ocorrencia_TP_Ocorrencia, String> {

    List<Sgo_Ocorrencia_TP_Ocorrencia> findByOcorrencia_OracleId(Long oracleId);

    List<Sgo_Ocorrencia_TP_Ocorrencia> findByTipoOcorrencia_OracleId(Long oracleId);
}
