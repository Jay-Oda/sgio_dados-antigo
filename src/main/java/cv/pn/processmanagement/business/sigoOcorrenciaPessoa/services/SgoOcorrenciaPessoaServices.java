package cv.pn.processmanagement.business.sigoOcorrenciaPessoa.services;



import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.dto.SgoOcorrenciaPessoaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.model.SgoOcorrenciaPessoa;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.repository.SgoOcorrenciaPessoaRepository;



import cv.pn.processmanagement.business.sigoPessoa.repository.SigoPessoaRepository;
import cv.pn.processmanagement.utilities.APIResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;




import java.util.*;


@Service
public class SgoOcorrenciaPessoaServices implements ISgoOcorrenciaPessoaServices {

    private final SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository;
    private final SigoPessoaRepository sigoPessoaRepository;
    private static final Logger log = LoggerFactory.getLogger(SgoOcorrenciaPessoaServices.class);
    private final SgoOcorrenciaPessoaRepository repo;

    public SgoOcorrenciaPessoaServices(SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository, SigoPessoaRepository sigoPessoaRepository, SgoOcorrenciaPessoaRepository repo) {
        this.sgoOcorrenciaPessoaRepository = sgoOcorrenciaPessoaRepository;
        this.sigoPessoaRepository = sigoPessoaRepository;
        this.repo = repo;
    }

    @Override
    public APIResponse createOcorrenciaPessoa(SgoOcorrenciaPessoaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            System.out.println("DTO RECEBIDO:");
            SgoOcorrenciaPessoa sgoOcorrenciaPessoa = new SgoOcorrenciaPessoa();
            sgoOcorrenciaPessoa.setId(null);
            BeanUtils.copyProperties(dto, sgoOcorrenciaPessoa);
            System.out.println("Pessoa: " + sgoOcorrenciaPessoa.getOracleIdPessoa());
            System.out.println("Ocorrencia: " + sgoOcorrenciaPessoa.getOracleIdOcorrencia());
            System.out.println("Condicao: " + sgoOcorrenciaPessoa.getCondicao());
            //sgoOcorrenciaPessoa.setUserCreate("ADMIN");
            // sgoOcorrenciaPessoaRepository.save(sgoOcorrenciaPessoa);

            sgoOcorrenciaPessoaRepository.insertIgnoreDuplicate(
                    sgoOcorrenciaPessoa.getCondicao(),
                    sgoOcorrenciaPessoa.getIdPessoa(),
                    sgoOcorrenciaPessoa.getIdOcorrencia(),
                    sgoOcorrenciaPessoa.getOracleIdPessoa(),
                    sgoOcorrenciaPessoa.getOracleIdOcorrencia(),
                    sgoOcorrenciaPessoa.getCaracteristica(),
                    sgoOcorrenciaPessoa.getIdAgente(),
                    "MIGRACAO_ORACLE"
            );

            log.info("INSERT EXECUTADO PARA ORACLE_PESSOA={} ORACLE_OCORRENCIA={}",
                    sgoOcorrenciaPessoa.getOracleIdPessoa(),
                    sgoOcorrenciaPessoa.getOracleIdOcorrencia());

            System.out.println("oraclePessoa=" + sgoOcorrenciaPessoa.getOracleIdPessoa());
            System.out.println("oracleOcorrencia=" + sgoOcorrenciaPessoa.getOracleIdOcorrencia());
            System.out.println("condicao=" + sgoOcorrenciaPessoa.getCondicao());



            System.out.println("ENTITY APÓS SAVE:");

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("OcorrenciaPessoa criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaPessoa))
                    .builder();


        } catch (Exception e) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar sgoOcorrenciaPessoa")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }


   /* @Override
    public APIResponse verificarAssociacaoPorNome(String nome) {



        if (!StringUtils.hasText(nome)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("O nome é obrigatório para a pesquisa.")
                    .builder();
        }

        List<SigoPessoa> pessoas =
                sigoPessoaRepository.buscarPorNome(nome.trim());

        // Nenhuma pessoa encontrada
        if (pessoas.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Pessoa não encontrada no sistema.")
                    .builder();
        }

        // 🔎 MAIS DE UMA PESSOA (ex: JOSE)
        if (pessoas.size() > 1) {

            List<Map<String, Object>> resposta = pessoas.stream()
                    .map(p -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("id", p.getId());
                        m.put("nome", p.getNome());
                        m.put(
                                "possuiOcorrencia",
                                sgoOcorrenciaPessoaRepository.existsByOracleIdPessoa(p.getOracleId())
                        );
                        return m;
                    })
                    .toList();

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText(
                            "Foram encontradas " + pessoas.size() +
                                    " pessoas com esse nome. Refine a pesquisa."
                    )
                    .setDetails(Collections.singletonList(resposta))
                    .builder();
        }

        //  APENAS UMA PESSOA (ex: JOSE DE PINA)
        SigoPessoa pessoa = pessoas.get(0);

        boolean possuiOcorrencia =
                sgoOcorrenciaPessoaRepository.existsByOracleIdPessoa(pessoa.getOracleId());

        return new APIResponse.buildAPIResponse()
                .setStatus(possuiOcorrencia)
                .setStatusText(
                        possuiOcorrencia
                                ? "A pessoa ESTÁ associada a uma ocorrência."
                                : "A pessoa NÃO está associada a nenhuma ocorrência."
                )
                .setDetails(List.of(
                        Map.of(
                                "id", pessoa.getId(),
                                "nome", pessoa.getNome()
                        )
                ))
                .builder();

    }*/

   /* @Override
    public List<OcorrenciaResumoDto> listarOcorrenciasDaPessoa(Long oracleIdPessoa) {

        // 🔑 1. Descobrir todos os oracleIds da mesma pessoa real
        List<Long> oracleIds =
                sigoPessoaRepository.buscarOracleIdsEquivalentes(oracleIdPessoa);

        if (oracleIds.isEmpty()) {
            oracleIds = List.of(oracleIdPessoa);
        }

        // 🔑 2. Buscar ocorrências usando TODOS os ids
        return repo.listarOcorrenciasPorOracleIds(oracleIds)
                .stream()
                .map(p -> {
                    OcorrenciaResumoDto dto = new OcorrenciaResumoDto();
                    dto.setNumOcorrencia(p.getNumOcorrencia());
                    dto.setNatureza(p.getNatureza());
                    dto.setAutoPrincipal(p.getAutoPrincipal());
                    dto.setCondicao(p.getCondicao());
                    dto.setDataRegisto(
                            p.getDataRegisto() != null
                                    ? p.getDataRegisto().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                    : null
                    );
                    dto.setUnidade(p.getUnidade());
                    dto.setFase(p.getFase());
                    return dto;
                })
                .toList();*/

        //estba funcionando bem
      /*  return repo.listarOcorrenciasDaPessoa(oracleIdPessoa)
                .stream()
                .map(p -> {
                    OcorrenciaResumoDto dto = new OcorrenciaResumoDto();
                    dto.setNumOcorrencia(p.getNumOcorrencia());
                    dto.setNatureza(p.getNatureza());
                    dto.setAutoPrincipal(p.getAutoPrincipal());
                    dto.setCondicao(p.getCondicao());
                    //dto.setDataRegisto(p.getDataRegisto()); // 👈 direto pode ser assim se nao usamos private no dto
                    dto.setDataRegisto(
                            p.getDataRegisto() != null
                                    ? p.getDataRegisto().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                    : null
                    );
                   // dto.setDataRegisto(p.getDataRegisto());
                    dto.setUnidade(p.getUnidade());
                    dto.setFase(p.getFase());
                    return dto;
                })
                .toList();*/

}






