package cv.pn.processmanagement.business.sigoPessoa.services;



import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.repository.SgoOcorrenciaPessoaRepository;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.projection.PessoaResumoProjection;
import cv.pn.processmanagement.business.sigoPessoa.PessoaFiltroRequest;
import cv.pn.processmanagement.business.sigoPessoa.dto.SigoPessoaDto;
import cv.pn.processmanagement.business.sigoPessoa.repository.SigoPessoaRepository;
import cv.pn.processmanagement.business.sigoPessoa.model.SigoPessoa;

import cv.pn.processmanagement.sgoPecaPessoa.repository.SgoPecaPessoaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SigoPessoaService implements ISigoPessoaService {

    private final SigoPessoaRepository sigoPessoaRepository;
    private final SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository;
   //private final SgoPecaPessoaRepository sgoPecaPessoaRepository;
    // private static final Logger log = LoggerFactory.getLogger(SigoPessoaService.class);

    public SigoPessoaService(SigoPessoaRepository sigoPessoaRepository, SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository, SgoPecaPessoaRepository sgoPecaPessoaRepository) {
        this.sigoPessoaRepository = sigoPessoaRepository;
        this.sgoOcorrenciaPessoaRepository = sgoOcorrenciaPessoaRepository;
       // this.sgoPecaPessoaRepository = sgoPecaPessoaRepository;
    }

    @Override
    public APIResponse createSigoPessoa(SigoPessoaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {
            System.out.println("DTO RECEBIDO:");

            SigoPessoa sigoPessoa = new SigoPessoa();
            BeanUtils.copyProperties(dto, sigoPessoa);
            //sigoPessoa.setUserCreate("ADMIN");
            //sigoPessoaRepository.save(sigoPessoa);
           // sigoPessoa.setNome(dto.getNome());
           // sigoPessoa.setSexo(dto.getSexo());

            if (dto.getFotoBase64() != null) {
                sigoPessoa.setFoto(Base64.getDecoder().decode(dto.getFotoBase64()));
            }

           // sigoPessoaRepository.save(sigoPessoa);
            SigoPessoa saved = sigoPessoaRepository.saveAndFlush(sigoPessoa);
           // System.out.println("SALVO NO POSTGRES COM ID = " + saved.getId());
            System.out.println("SALVO: Postgres ID=" + saved.getId()
                    + " | Oracle ID=" + saved.getOracleId());

            System.out.println("ENTITY APÓS SAVE:");
            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("SigoPessoa criada com sucesso")
                    .setDetails(Collections.singletonList(sigoPessoa))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar SigoPessoa")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse pesquisarPessoaComFiltro(PessoaFiltroRequest req) {

        String nome = normalizar(req.getNome());
        String num  = normalizar(req.getNum());
        String pai  = normalizar(req.getPai());
        String mae  = normalizar(req.getMae());

        Date dtNasc = null;
        if (req.getDtNascimento() != null && !req.getDtNascimento().isBlank()) {
            try {
                dtNasc = new SimpleDateFormat("yyyy-MM-dd")
                        .parse(req.getDtNascimento().trim());
            } catch (Exception e) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Data de nascimento inválida. Use yyyy-MM-dd (ex: 2026-01-20).")
                        .setDetails(List.of(e.getMessage()))
                        .builder();
            }
        }


      /*  Date dtNasc = null;
        if (normalizar(req.getDtNascimento()) != null) {
            try {
                dtNasc = new SimpleDateFormat("yyyy-MM-dd")
                        .parse(req.getDtNascimento().trim());

            } catch (Exception e) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Data de nascimento inválida. Use yyyy-MM-dd (ex: 1980-12-25).")
                        .setDetails(List.of(e.getMessage()))
                        .builder();
            }
        }*/

        if (nome == null && num == null && pai == null && mae == null && dtNasc == null) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Informe pelo menos um filtro (nome, filiação, documento ou data nascimento).")
                    .builder();
        }

       /* List<SigoPessoa> pessoas =
                sigoPessoaRepository.buscarPorFiltro(nome, num, pai, mae, dtNasc);*/

        // 🔑 ESCOLHA CORRETA DA QUERY (aqui está a correção real)
        List<PessoaResumoProjection> pessoas;

        if (dtNasc == null) {
            pessoas = sigoPessoaRepository.buscarSemData(
                    nome, num, pai, mae
            );
        } else {
            pessoas = sigoPessoaRepository.buscarComData(
                    nome, num, pai, mae, dtNasc
            );
        }

        if (pessoas.isEmpty()) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Nenhuma pessoa encontrada com antecedentes policiais.")
                    .builder();
        }

        List<Map<String, Object>> resp = pessoas.stream()

                // FILTRO CRÍTICO
                /*.filter(p ->
                        sgoOcorrenciaPessoaRepository
                                .existsByOracleIdPessoa(p.getOracleId())
                )*/

                .filter(p ->
                        sgoOcorrenciaPessoaRepository
                                .existsAntecedenteReal(p.getOracleId(), null)
                )






                .map(p -> {
                    //Optional<SgoPecaPessoa> pessoatipo = sgoPecaPessoaRepository.findByIdPessoa(p.getId());
                    Map<String, Object> m = new HashMap<>();
            m.put("id", p.getId());
            m.put("oracleId", p.getOracleId());
            m.put("nome", p.getNome());
            m.put("numDocumento", p.getNum());
            m.put("pai", p.getPai());
            m.put("mae", p.getMae());
            m.put("dataNascimento", p.getDtNascimento());
            /*m.put("dtNascimento", p.getDtNascimento());
                    m.put(
                            "condicao",
                            pessoatipo
                                    .map(SgoPecaPessoa::getCondicao)
                                    .orElse(null)
                    );*/
           // m.put("condiçao", pessoatipo.get().getCondicao());
           /* m.put("possuiOcorrencia",
                    sgoOcorrenciaPessoaRepository.existsByOracleIdPessoa(p.getOracleId()));
            return m;*/

           /* boolean possui = sgoOcorrenciaPessoaRepository
                    .existsByOracleIdPessoa(p.getOracleId());

            m.put("possuiOcorrencia", possui);
            m.put("mensagemOcorrencia",
                    possui
                            ? "Está associada a ocorrências policiais."
                            : "Não está associada a ocorrências policiais.");

            return m;*/

                    m.put("possuiOcorrencia", true);
                    m.put("mensagemOcorrencia",
                            "Está associada a ocorrências policiais.");

                    return m;
        }).toList();

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Encontradas " + resp.size() + " pessoas com antecedentes.")
                .setDetails(Collections.singletonList(resp))

                .builder();
        // melhor mostrar quantidade de ocorrencia e nao quantidade de pessoa
    }

    private String normalizar(String v) {
        if (v == null) return null;
        v = v.trim();
        return v.isEmpty() ? null : v;
    }
}
