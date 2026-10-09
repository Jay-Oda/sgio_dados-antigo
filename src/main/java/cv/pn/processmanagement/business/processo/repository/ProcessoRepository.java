package cv.pn.processmanagement.business.processo.repository;

import cv.pn.processmanagement.business.processo.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, String> {
}
