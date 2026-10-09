package cv.pn.processmanagement.business.pessoaEnviado.repository;

import cv.pn.processmanagement.business.pessoaEnviado.model.PessoaEnviado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaEnviadoRepository extends JpaRepository<PessoaEnviado, String> {
}
