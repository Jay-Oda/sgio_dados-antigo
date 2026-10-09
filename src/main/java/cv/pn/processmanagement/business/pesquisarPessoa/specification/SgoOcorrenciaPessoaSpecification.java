package cv.pn.processmanagement.business.pesquisarPessoa.specification;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.model.SgoOcorrenciaPessoa;
import cv.pn.processmanagement.business.sigoPessoa.model.SigoPessoa;
import cv.pn.processmanagement.utilities.APIUtilities;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class SgoOcorrenciaPessoaSpecification {

    private SgoOcorrenciaPessoaSpecification() {
        // utility class
    }

    public static Specification<SgoOcorrenciaPessoa> filtrarPorPessoa(
            SearchPessoaOcorrenciaDto dto) {


        return (root, query, cb) -> {

            //if (dto == null) para evitar NullPointerException {
            // return cb.conjunction(); Retorna um predicado neutro quando o DTO é nulo Isso garante que a especificação funcione mesmo quando não há critérios de busca

            // EXISTS
            // Subquery<Integer> subquery = query.subquery(Integer.class);

            // Root<SigoPessoa> pessoaRoot = subquery.from(SigoPessoa.class);
            // Subquery<Integer> existsSubquery = query.subquery(Integer.class);
            // Root<SigoPessoa> pessoaRoot = existsSubquery.from(SigoPessoa.class);

            List<Predicate> predicates = new ArrayList<>();

            // List<Predicate> pessoaPredicates = new ArrayList<>();

            if (StringUtils.isNotBlank(dto.getNome())) {

                System.out.println("Entrou kelby");

                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<SigoPessoa> pessoaRoot = subquery.from(SigoPessoa.class);

                String nome = APIUtilities.replaceSpecialCharacters(
                        dto.getNome().toLowerCase()
                );

                Predicate nomePredicate = cb.equal(
                        cb.lower(
                                cb.function(
                                        "unaccent",
                                        String.class,
                                        pessoaRoot.get("nome")
                                )
                        ),
                        nome
                );


                List<Predicate> allPredicates = new ArrayList<>();
                allPredicates.add(nomePredicate);

               /* pessoaPredicates.add(
                        cb.like(
                                cb.upper(pessoaRoot.get("nome")),
                                dto.getNome().toUpperCase()
                        )
                );*/

                // 🟡 PAI (opcional)
                if (StringUtils.isNotBlank(dto.getNomePai())) {

                    String pai = APIUtilities.replaceSpecialCharacters(
                            dto.getNomePai().toLowerCase()
                    );

                    Predicate paiPredicate = cb.equal(
                            cb.lower(
                                    cb.function(
                                            "unaccent",
                                            String.class,
                                            pessoaRoot.get("pai")
                                    )
                            ),
                            pai
                    );

                    allPredicates.add(paiPredicate);
                }


           /* if (StringUtils.isNotBlank(dto.getNomePai())) {
                pessoaPredicates.add(
                        cb.like(
                                cb.upper(pessoaRoot.get("pai")),
                                 dto.getNomePai().toUpperCase()
                        )
                );
            }*/


                // 🟡 MÃE (opcional)
                if (StringUtils.isNotBlank(dto.getNomeMae())) {

                    String mae = APIUtilities.replaceSpecialCharacters(
                            dto.getNomeMae().toLowerCase()
                    );

                    Predicate maePredicate = cb.equal(
                            cb.lower(
                                    cb.function(
                                            "unaccent",
                                            String.class,
                                            pessoaRoot.get("mae")
                                    )
                            ),
                            mae
                    );

                    allPredicates.add(maePredicate);
                }
           /* if (StringUtils.isNotBlank(dto.getNomeMae())) {
                pessoaPredicates.add(
                        cb.like(
                                cb.upper(pessoaRoot.get("mae")),
                                dto.getNomeMae().toUpperCase()

                        )
                );
            }*/

                // 🟡 MÃE (opcional)
                if (StringUtils.isNotBlank(dto.getNum())) {

                    String num = APIUtilities.replaceSpecialCharacters(
                            dto.getNum().toLowerCase()
                    );

                    Predicate numPredicate = cb.equal(
                            cb.lower(
                                    cb.function(
                                            "unaccent",
                                            String.class,
                                            pessoaRoot.get("num")
                                    )
                            ),
                            num
                    );

                    allPredicates.add(numPredicate);
                }

           /* if (StringUtils.isNotBlank(dto.getNum())) {
                pessoaPredicates.add(
                        cb.like(
                                cb.upper(pessoaRoot.get("num")),
                                dto.getNum().toUpperCase()
                        )
                );
            }*/

           /* if (dto.getDtNascimento() != null) {
                pessoaPredicates.add(
                        cb.equal(
                                pessoaRoot.get("dtNascimento"),
                                java.sql.Date.valueOf(dto.getDtNascimento())
                        )
                );
            }*/

                // 🟡 DATA NASCIMENTO (opcional)
                if (dto.getDtNascimento() != null) {
                    Predicate dataPredicate = cb.equal(
                            pessoaRoot.get("dtNascimento"),
                            java.sql.Date.valueOf(dto.getDtNascimento())
                    );

                    allPredicates.add(dataPredicate);
                }

                allPredicates.add(
                        cb.equal(
                                pessoaRoot.get("idPessoa"),
                                root.get("idPessoa")
                        )
                );

                // CORRELAÇÃO CORRETA (Long = Long)
          /*  pessoaPredicates.add(
                    cb.equal(
                            pessoaRoot.get("idPessoa"),
                            root.get("idPessoa")
                    )
            );*/

                subquery
                        .select(cb.literal(1))
                        .where(cb.and(allPredicates.toArray(new Predicate[0])));

                predicates.add(cb.exists(subquery));

           /* existsSubquery
                    .select(cb.literal(1))
                    .where(cb.and(pessoaPredicates.toArray(new Predicate[0])));*/
               // return cb.exists(subquery);
                // return cb.exists(existsSubquery);
            /*subquery.select(pessoaRoot.get("idPessoa"))
                    .where(cb.and(pessoaPredicates.toArray(new Predicate[0])));

            return root.get("idPessoa").in(subquery);*/
            }

            // 🔴 RETORNO OBRIGATÓRIO DA SPECIFICATION
            return predicates.isEmpty()
                    ? cb.conjunction()
                    : cb.and(predicates.toArray(new Predicate[0]));
        };



        }


    }

