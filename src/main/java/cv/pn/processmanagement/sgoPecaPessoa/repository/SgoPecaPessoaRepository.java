package cv.pn.processmanagement.sgoPecaPessoa.repository;


import cv.pn.processmanagement.sgoPecaPessoa.model.SgoPecaPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SgoPecaPessoaRepository extends JpaRepository<SgoPecaPessoa, Long> {

    Optional<SgoPecaPessoa> findByIdPessoa(Long idPessoa);
}
