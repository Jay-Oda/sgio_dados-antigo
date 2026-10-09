/*package cv.pn.processmanagement.business.pesquisarPessoa;


import com.nimbusds.oauth2.sdk.util.StringUtils;
import cv.pn.processmanagement.business.ocorrencia.model.SgoOcorrencia;
import cv.pn.processmanagement.business.ocorrencia.repository.SgoOcorrenciaRepository;
import cv.pn.processmanagement.business.pesquisarPessoa.specification.SgoOcorrenciaSpecification;
//import cv.pn.processmanagement.business.pesquisarPessoa.specification.SigoPersonSearchSpecification;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.model.SgoOcorrenciaPessoa;
import cv.pn.processmanagement.business.sigoPessoa.model.SigoPessoa;
import cv.pn.processmanagement.business.sigoPessoa.repository.SigoPessoaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import cv.pn.processmanagement.utilities.APIUtilities;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class SearchPersonServices implements ISearchPersonServices {

    private final SgoOcorrenciaRepository sgoOcorrenciaRepository;

    public SearchPersonServices(SgoOcorrenciaRepository sgoOcorrenciaRepository) {
        this.sgoOcorrenciaRepository = sgoOcorrenciaRepository;
    }

    @Override
    public List<SgoOcorrenciaPessoa> pesquisarPessoaEmOcorrencia(SearchPersonOccurrenceDto dto) {

        Specification<SgoOcorrenciaPessoa> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Subquery para buscar Pessoa
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<SigoPessoa> pessoaRoot = subquery.from(SigoPessoa.class);

            List<Predicate> pessoaPredicates = new ArrayList<>();

            // 🔴 Nome (obrigatório)
            String name = APIUtilities.replaceSpecialCharacters(
                    dto.getName().toLowerCase()
            );

            pessoaPredicates.add(
                    cb.equal(
                            cb.lower(
                                    cb.function("unaccent", String.class, pessoaRoot.get("name"))
                            ),
                            name
                    )
            );

            // 🟡 Pai
            if (StringUtils.isNotBlank(dto.getFatherName())) {
                String fatherName = APIUtilities.replaceSpecialCharacters(
                        dto.getFatherName().toLowerCase()
                );

                pessoaPredicates.add(
                        cb.equal(
                                cb.lower(
                                        cb.function("unaccent", String.class, pessoaRoot.get("fatherName"))
                                ),
                                fatherName
                        )
                );
            }

            // 🟡 Mãe
            if (StringUtils.isNotBlank(dto.getMotherName())) {
                String motherName = APIUtilities.replaceSpecialCharacters(
                        dto.getMotherName().toLowerCase()
                );

                pessoaPredicates.add(
                        cb.equal(
                                cb.lower(
                                        cb.function("unaccent", String.class, pessoaRoot.get("motherName"))
                                ),
                                mae
                        )
                );
            }

            // 🟡 Data nascimento
            if (dto.getDtNascimento() != null) {
                pessoaPredicates.add(
                        cb.equal(
                                pessoaRoot.get("dtNascimento"),
                                dto.getDtNascimento()
                        )
                );
            }

            // SELECT p.id FROM sigo_pessoa p WHERE ...
            subquery.select(pessoaRoot.get("id"))
                    .where(cb.and(pessoaPredicates.toArray(new Predicate[0])));

            // 🔗 op.idPessoa IN (subquery)
            predicates.add(
                    root.get("idPessoa").in(subquery)
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec);
    }
    }


    /*@Override
    public APIResponse createSearchPerson(SearchPersonDto dto) {

        List<SgoOcorrencia> ocorrencias =
                sgoOcorrenciaRepository.findAll(
                        SgoOcorrenciaSpecification.searchByPerson(dto)
                );

        SearchPersonResultDto result = new SearchPersonResultDto(
                dto.getName(),
                !ocorrencias.isEmpty(),
                ocorrencias.size()
        );

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Pesquisa realizada com sucesso")
                .setDetails(List.of(result)) // DTO vai aqui
                .builder();
    }

    }*/

