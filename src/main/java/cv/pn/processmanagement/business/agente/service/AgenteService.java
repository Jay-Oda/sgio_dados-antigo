package cv.pn.processmanagement.business.agente.service;


import cv.pn.processmanagement.business.agente.dto.AgenteDetalheDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.agente.dto.AgenteDto;
import cv.pn.processmanagement.business.agente.dto.AgenteOcorrenciaDto;
import cv.pn.processmanagement.business.agente.dto.AgenteResumoDto;
import cv.pn.processmanagement.business.agente.dto.PessoaOcorrenciaDto;
import cv.pn.processmanagement.business.agente.model.Agente;
import cv.pn.processmanagement.business.agente.repository.AgenteRepository;
import cv.pn.processmanagement.business.comando.repository.ComandoRepository;
import cv.pn.processmanagement.business.esquadra.model.Esquadra;
import cv.pn.processmanagement.business.esquadra.repository.EsquadraRepository;
import cv.pn.processmanagement.business.ocorrencia.repository.SgoOcorrenciaRepository;
import cv.pn.processmanagement.business.projection.AgenteDetalheProjection;
import cv.pn.processmanagement.business.projection.AgenteOcorrenciaProjection;
import cv.pn.processmanagement.business.projection.AgenteResumoProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaCircunstanciaProjection;
import cv.pn.processmanagement.business.projection.PessoaOcorrenciaProjection;
import cv.pn.processmanagement.business.projection.PessoaResumoProjection;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.repository.SgoOcorrenciaPessoaRepository;
import cv.pn.processmanagement.business.sigoPessoa.repository.SigoPessoaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import cv.pn.processmanagement.utilities.AutoTipoUtil;
import cv.pn.processmanagement.utilities.CondicaoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class AgenteService implements IAgenteService {

    private final AgenteRepository agenteRepository;
    private final SgoOcorrenciaRepository sgoOcorrenciaRepository;
    private final SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository;
    private final EsquadraRepository esquadraRepository;
    private final ComandoRepository comandoRepository;
    private final SigoPessoaRepository sigoPessoaRepository;

    public AgenteService(AgenteRepository agenteRepository,
                         SgoOcorrenciaRepository sgoOcorrenciaRepository,
                         SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository,
                         EsquadraRepository esquadraRepository,
                         ComandoRepository comandoRepository,
                         SigoPessoaRepository sigoPessoaRepository) {
        this.agenteRepository = agenteRepository;
        this.sgoOcorrenciaRepository = sgoOcorrenciaRepository;
        this.sgoOcorrenciaPessoaRepository = sgoOcorrenciaPessoaRepository;
        this.esquadraRepository = esquadraRepository;
        this.comandoRepository = comandoRepository;
        this.sigoPessoaRepository = sigoPessoaRepository;
    }

    @Override
    public APIResponse createAgente(AgenteDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            Agente agente = new Agente();
            BeanUtils.copyProperties(dto, agente);
            agente.setUserCreate("ADMIN");

            agenteRepository.save(agente);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Agente criada com sucesso")
                    .setDetails(Collections.singletonList(agente))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Agente")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse createAgenteParams(String numero, String nome) {

        try {

            Agente agente1 = new Agente();
            agente1.setNumero(numero);
            agente1.setNome(nome);
            agente1.setUserCreate("ADMIN");

            agenteRepository.save(agente1);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Agente numero, nome foi criada com sucesso")
                    .setDetails(Collections.singletonList(agente1))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Agente")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }


    }

    @Override
    public APIResponse buscarPorNumero(String numero) {

        try {
            if (numero == null || numero.trim().isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Informe o numero do agente.")
                        .builder();
            }

            Optional<AgenteResumoProjection> agente =
                    agenteRepository.findResumoByNumeroExact(numero.trim());

            if (agente.isPresent()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(true)
                        .setStatusText("Agente encontrado.")
                        .setDetails(Collections.singletonList(toDetalheDto(
                                agenteRepository.findDetalheById(agente.get().getId()).orElse(null))))
                        .builder();
            }

            List<AgenteResumoProjection> parciais =
                    agenteRepository.findResumoByNumeroContaining(numero.trim());

            if (!parciais.isEmpty()) {
                List<AgenteResumoDto> lista = parciais.stream().map(this::toResumoDto).toList();
                return new APIResponse.buildAPIResponse()
                        .setStatus(true)
                        .setStatusText("Numero exato nao encontrado. Resultados parciais (" + lista.size() + ").")
                        .setDetails(Collections.singletonList(lista))
                        .builder();
            }

            List<AgenteResumoProjection> porNome =
                    agenteRepository.findResumoByNomeContaining(numero.trim());

            if (!porNome.isEmpty()) {
                List<AgenteResumoDto> lista = porNome.stream().map(this::toResumoDto).toList();
                return new APIResponse.buildAPIResponse()
                        .setStatus(true)
                        .setStatusText("Nenhum agente encontrado pelo numero. Resultados por nome (" + lista.size() + ").")
                        .setDetails(Collections.singletonList(lista))
                        .builder();
            }

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Nenhum agente encontrado com o numero informado.")
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao pesquisar agente por numero")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse buscarPorNomeExato(String nome) {

        try {
            if (nome == null || nome.trim().isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Informe o nome completo do agente.")
                        .builder();
            }

            List<AgenteResumoProjection> candidatos =
                    agenteRepository.findResumoByNomeContaining(nome.trim());

            List<AgenteResumoProjection> exatos = candidatos.stream()
                    .filter(a -> a.getNome() != null
                            && a.getNome().trim().equalsIgnoreCase(nome.trim()))
                    .toList();

            List<AgenteResumoProjection> resultado = exatos.isEmpty() ? candidatos : exatos;

            if (resultado.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nenhum agente encontrado com esse nome.")
                        .builder();
            }

            if (resultado.size() == 1) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(true)
                        .setStatusText("Agente encontrado.")
                        .setDetails(Collections.singletonList(toDetalheDto(
                                agenteRepository.findDetalheById(resultado.get(0).getId()).orElse(null))))
                        .builder();
            }

            List<AgenteResumoDto> lista = resultado.stream().map(this::toResumoDto).toList();

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Encontrados " + lista.size() + " agentes. Refine a pesquisa.")
                    .setDetails(Collections.singletonList(lista))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao pesquisar agente por nome")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse buscarPorId(String id) {

        try {
            if (id == null || id.trim().isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Informe o id do agente.")
                        .builder();
            }

            AgenteDetalheProjection agente = agenteRepository.findDetalheById(id.trim()).orElse(null);

            if (agente == null) {
                // tenta pelo nome completo do agente
                List<AgenteResumoProjection> candidatos =
                        agenteRepository.findResumoByNomeContaining(id.trim());

                List<AgenteResumoProjection> exatos = candidatos.stream()
                        .filter(a -> a.getNome() != null
                                && a.getNome().trim().equalsIgnoreCase(id.trim()))
                        .toList();

                List<AgenteResumoProjection> resultado = exatos.isEmpty() ? candidatos : exatos;

                if (resultado.isEmpty()) {
                    return new APIResponse.buildAPIResponse()
                            .setStatus(false)
                            .setStatusText("Agente nao encontrado.")
                            .builder();
                }

                if (resultado.size() > 1) {
                    List<AgenteResumoDto> lista = resultado.stream().map(this::toResumoDto).toList();
                    return new APIResponse.buildAPIResponse()
                            .setStatus(true)
                            .setStatusText("Encontrados " + lista.size() + " agentes. Refine a pesquisa.")
                            .setDetails(Collections.singletonList(lista))
                            .builder();
                }

                agente = agenteRepository.findDetalheById(resultado.get(0).getId()).orElse(null);
                if (agente == null) {
                    return new APIResponse.buildAPIResponse()
                            .setStatus(false)
                            .setStatusText("Agente nao encontrado.")
                            .builder();
                }
            }

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Agente encontrado.")
                    .setDetails(Collections.singletonList(toDetalheDto(agente)))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao pesquisar agente por id")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse listarQueixasDoAgente(String agenteId, boolean todas) {

        try {
            ResolucaoAgente resolucao = resolverAgente(agenteId);
            if (!resolucao.encontrado()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Agente nao encontrado com o identificador informado.")
                        .builder();
            }

            if (resolucao.oracleIds.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nao foi possivel associar este agente a ocorrencias (sem id de pessoa correspondente).")
                        .builder();
            }

            List<AgenteOcorrenciaProjection> proj =
                    sgoOcorrenciaRepository.buscarQueixasPorAgente(resolucao.oracleIds, todas, null);

            if (proj.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText(todas
                                ? "Nenhuma queixa (Auto de Denúncia) encontrada."
                                : "Nenhuma queixa (Auto de Denúncia) registada por este agente.")
                        .builder();
            }

            Map<Long, AgenteOcorrenciaDto> queixas = new LinkedHashMap<>();
            for (AgenteOcorrenciaProjection p : proj) {
                AgenteOcorrenciaDto dto = toOcorrenciaDto(p, "AUTO_DENUNCIA");
                dto.setCondicao("Autuante");
                queixas.putIfAbsent(p.getOracleId(), dto);
            }

            List<AgenteOcorrenciaDto> lista = new ArrayList<>(queixas.values());
            preencherEnvolvidos(lista);
            preencherCircunstancias(lista);

            Map<String, Object> resp = new HashMap<>();
            resp.put("agenteId", agenteId);
            resp.put("nome", resolucao.nome);
            resp.put("todas", todas);
            resp.put("quantidade", lista.size());
            resp.put("queixas", lista);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText(todas
                            ? "Encontradas " + lista.size() + " queixa(s) (Auto de Denúncia) no sistema."
                            : "Encontradas " + lista.size() + " queixa(s) (Auto de Denúncia) registada(s) por este agente.")
                    .setDetails(Collections.singletonList(resp))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar queixas do agente")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse listarOcorrenciasDoAgente(String agenteId, String auto) {

        String tpAuto = AutoTipoUtil.normalizar(auto);

        try {
            if (auto != null && !auto.isBlank() && tpAuto == null && AutoTipoUtil.isModeloDeAuto(auto)) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nenhuma ocorrencia associada ao modelo de auto selecionado (" + auto + ").")
                        .builder();
            }

            ResolucaoAgente resolucao = resolverAgente(agenteId);
            if (!resolucao.encontrado()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Agente nao encontrado com o identificador informado.")
                        .builder();
            }

            if (resolucao.oracleIds.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nao foi possivel associar este agente a ocorrencias (sem id de pessoa correspondente).")
                        .builder();
            }

            List<AgenteOcorrenciaProjection> registadas =
                    sgoOcorrenciaRepository.buscarOcorrenciasRegistadasPorAgente(resolucao.oracleIds, tpAuto);

            List<AgenteOcorrenciaProjection> associadas =
                    sgoOcorrenciaRepository.buscarOcorrenciasAssociadasAoAgente(
                            resolucao.oracleIds, List.of("6", "7", "8"), tpAuto);

            Map<Long, AgenteOcorrenciaDto> map = new LinkedHashMap<>();

            for (AgenteOcorrenciaProjection p : registadas) {
                AgenteOcorrenciaDto dto = toOcorrenciaDto(p, "REGISTADA PELO AGENTE");
                dto.setCondicao(papelOperacionalDoAgente(p.getTpAuto()));
                map.putIfAbsent(p.getOracleId(), dto);
            }

            for (AgenteOcorrenciaProjection p : associadas) {
                AgenteOcorrenciaDto dto = toOcorrenciaDto(p, "ASSOCIADO AO AGENTE");
                dto.setCondicao(papelOperacionalDoAgente(p.getTpAuto()));
                map.putIfAbsent(p.getOracleId(), dto);
            }

            if (map.isEmpty()) {
                String nomeAuto = AutoTipoUtil.descricao(tpAuto);
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText(nomeAuto != null
                                ? "Nenhuma ocorrencia associada a este agente (autuante/detentor) para o filtro " + nomeAuto + "."
                                : "Nenhuma ocorrencia associada a este agente (autuante/detentor).")
                        .builder();
            }

            List<AgenteOcorrenciaDto> lista = new ArrayList<>(map.values());
            preencherEnvolvidos(lista);
            preencherCircunstancias(lista);

            Map<String, Object> resp = new HashMap<>();
            resp.put("agenteId", agenteId);
            resp.put("nome", resolucao.nome);
            resp.put("auto", auto);
            resp.put("quantidade", lista.size());
            resp.put("quantidadeRegistadas", registadas.size());
            resp.put("quantidadeAssociadas", associadas.size());
            resp.put("ocorrencias", lista);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Encontradas " + lista.size() + " ocorrencia(s) em que o agente e autuante/detentor.")
                    .setDetails(Collections.singletonList(resp))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar ocorrencias do agente")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse listarOcorrenciasEnvolvimentoDoAgente(String agenteId, String auto, String condicao) {

        String tpAuto = AutoTipoUtil.normalizar(auto);
        String codCondicao = CondicaoUtil.normalizar(condicao);

        try {
            if (auto != null && !auto.isBlank() && tpAuto == null && AutoTipoUtil.isModeloDeAuto(auto)) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nenhuma ocorrencia associada ao modelo de auto selecionado (" + auto + ").")
                        .builder();
            }

            ResolucaoAgente resolucao = resolverAgente(agenteId);
            if (!resolucao.encontrado()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Agente nao encontrado com o identificador informado.")
                        .builder();
            }

            if (resolucao.oracleIds.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nao foi possivel associar este agente a ocorrencias (sem id de pessoa correspondente).")
                        .builder();
            }

            List<String> condicoes = codCondicao == null
                    ? List.of("1", "2", "3", "4", "5", "6", "7", "8")
                    : List.of(codCondicao);

            List<AgenteOcorrenciaProjection> registadas =
                    sgoOcorrenciaRepository.buscarOcorrenciasRegistadasPorAgente(resolucao.oracleIds, tpAuto);

            List<AgenteOcorrenciaProjection> associadas =
                    sgoOcorrenciaRepository.buscarOcorrenciasAssociadasAoAgente(
                            resolucao.oracleIds, condicoes, tpAuto);

            Map<Long, AgenteOcorrenciaDto> map = new LinkedHashMap<>();

            for (AgenteOcorrenciaProjection p : registadas) {
                AgenteOcorrenciaDto dto = toOcorrenciaDto(p, "REGISTADA PELO AGENTE");
                dto.setCondicao(papelOperacionalDoAgente(p.getTpAuto()));
                map.putIfAbsent(p.getOracleId(), dto);
            }

            for (AgenteOcorrenciaProjection p : associadas) {
                map.putIfAbsent(p.getOracleId(), toOcorrenciaDto(p, "ASSOCIADO AO AGENTE"));
            }

            if (map.isEmpty()) {
                String nomeAuto = AutoTipoUtil.descricao(tpAuto);
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText(nomeAuto != null
                                ? "Nenhuma ocorrencia em que o agente esteja envolvido para o filtro " + nomeAuto + "."
                                : "Nenhuma ocorrencia em que o agente esteja envolvido.")
                        .builder();
            }

            List<AgenteOcorrenciaDto> lista = new ArrayList<>(map.values());
            preencherEnvolvidos(lista);
            preencherCircunstancias(lista);

            Map<String, Object> resp = new HashMap<>();
            resp.put("agenteId", agenteId);
            resp.put("nome", resolucao.nome);
            resp.put("auto", auto);
            resp.put("condicao", condicao);
            resp.put("quantidade", lista.size());
            resp.put("quantidadeRegistadas", registadas.size());
            resp.put("quantidadeAssociadas", associadas.size());
            resp.put("ocorrencias", lista);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Encontradas " + lista.size() + " ocorrencia(s) em que o agente esta envolvido.")
                    .setDetails(Collections.singletonList(resp))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar ocorrencias em que o agente esta envolvido")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    private String papelOperacionalDoAgente(String tpAuto) {
        return "3".equals(tpAuto) ? "Detentor" : "Autuante";
    }

    @Override
    public APIResponse listarPessoasDasOcorrenciasDoAgente(String agenteId) {

        try {
            ResolucaoAgente resolucao = resolverAgente(agenteId);
            if (!resolucao.encontrado()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Agente nao encontrado com o identificador informado.")
                        .builder();
            }

            if (resolucao.oracleIds.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nao foi possivel associar este agente a ocorrencias (sem id de pessoa correspondente).")
                        .builder();
            }

            List<PessoaOcorrenciaProjection> proj =
                    sgoOcorrenciaPessoaRepository.buscarPessoasDasOcorrenciasDoAgente(resolucao.oracleIds);

            if (proj.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nenhuma pessoa encontrada nas ocorrencias deste agente.")
                        .builder();
            }

            Map<Long, PessoaOcorrenciaDto> pessoas = new LinkedHashMap<>();

            for (PessoaOcorrenciaProjection p : proj) {
                PessoaOcorrenciaDto pessoa = pessoas.computeIfAbsent(
                        p.getOracleIdPessoa(),
                        k -> {
                            PessoaOcorrenciaDto dto = new PessoaOcorrenciaDto();
                            dto.setOracleIdPessoa(p.getOracleIdPessoa());
                            dto.setNome(p.getNome());
                            dto.setNum(p.getNum());
                            dto.setOcorrencias(new ArrayList<>());
                            return dto;
                        }
                );

                PessoaOcorrenciaDto.PessoaOcorrenciaItemDto item =
                        new PessoaOcorrenciaDto.PessoaOcorrenciaItemDto();
                item.setOracleIdOcorrencia(p.getOracleIdOcorrencia());
                item.setNumOcorrencia(p.getNumOcorrencia());
                item.setCondicao(p.getCondicao());
                item.setDataRegisto(toLocalDateTime(p.getDataRegisto()));
                pessoa.getOcorrencias().add(item);
            }

            pessoas.values().forEach(pp -> pp.setNumOcorrencias((long) pp.getOcorrencias().size()));

            Map<String, Object> resp = new HashMap<>();
            resp.put("agenteId", agenteId);
            resp.put("nome", resolucao.nome);
            resp.put("quantidadePessoas", pessoas.size());
            resp.put("pessoas", pessoas.values());

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Encontradas " + pessoas.size() + " pessoa(s) nas ocorrencias deste agente.")
                    .setDetails(Collections.singletonList(resp))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar pessoas das ocorrencias do agente")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public APIResponse listarOcorrenciasContraAgente(String agenteId, String auto) {

        String tpAuto = AutoTipoUtil.normalizar(auto);

        try {
            if (auto != null && !auto.isBlank() && tpAuto == null) {
                if (AutoTipoUtil.isModeloDeAuto(auto)) {
                    return new APIResponse.buildAPIResponse()
                            .setStatus(false)
                            .setStatusText("Nenhuma ocorrencia associada ao modelo de auto selecionado (" + auto + ").")
                            .builder();
                }
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Tipo de auto nao suportado para o filtro: " + auto + ".")
                        .builder();
            }

            ResolucaoAgente resolucao = resolverAgente(agenteId);
            if (!resolucao.encontrado()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Agente nao encontrado com o identificador informado.")
                        .builder();
            }

            if (resolucao.oracleIds.isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Nao foi possivel associar este agente a ocorrencias (sem id de pessoa correspondente).")
                        .builder();
            }

            List<AgenteOcorrenciaProjection> proj =
                    sgoOcorrenciaRepository.buscarOcorrenciasContraAgente(resolucao.oracleIds, tpAuto);

            if (proj.isEmpty()) {
                String nomeAuto = AutoTipoUtil.descricao(tpAuto);
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText(nomeAuto != null
                                ? "Nenhuma ocorrencia contra este agente (denunciado/detido) para o filtro " + nomeAuto + "."
                                : "Nenhuma ocorrencia contra este agente (denunciado/detido) encontrada.")
                        .builder();
            }

            Map<Long, AgenteOcorrenciaDto> ocorrencias = new LinkedHashMap<>();
            for (AgenteOcorrenciaProjection p : proj) {
                ocorrencias.putIfAbsent(p.getOracleId(), toOcorrenciaDto(p, "CONTRA O AGENTE"));
            }

            List<AgenteOcorrenciaDto> lista = new ArrayList<>(ocorrencias.values());
            preencherEnvolvidos(lista);
            preencherCircunstancias(lista);

            Map<String, Object> resp = new HashMap<>();
            resp.put("agenteId", agenteId);
            resp.put("nome", resolucao.nome);
            resp.put("auto", auto);
            resp.put("quantidade", lista.size());
            resp.put("ocorrencias", lista);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Encontradas " + lista.size() + " ocorrencia(s) contra este agente (denunciado/detido).")
                    .setDetails(Collections.singletonList(resp))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar ocorrencias contra o agente")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    private ResolucaoAgente resolverAgente(String agenteId) {

        if (agenteId == null || agenteId.trim().isEmpty()) {
            return ResolucaoAgente.naoEncontrado();
        }

        AgenteDetalheProjection detalhe = agenteRepository.findDetalheById(agenteId.trim()).orElse(null);

        Long idNacional = null;
        String nome = null;

        if (detalhe != null) {
            idNacional = detalhe.getIdNacional();
            nome = detalhe.getNome();
        } else if (agenteId.trim().matches("\\d+")) {
            List<AgenteResumoProjection> resumos =
                    agenteRepository.findResumoByIdNacional(Long.parseLong(agenteId.trim()));
            if (!resumos.isEmpty()) {
                AgenteResumoProjection resumo = resumos.get(0);
                idNacional = resumo.getIdNacional();
                nome = resumo.getNome();
            }
        }

        if (nome == null) {
            // tenta pelo nome completo do agente
            List<AgenteResumoProjection> candidatos =
                    agenteRepository.findResumoByNomeContaining(agenteId.trim());
            if (!candidatos.isEmpty()) {
                AgenteResumoProjection melhor = candidatos.get(0);
                for (AgenteResumoProjection c : candidatos) {
                    if (c.getNome() != null && c.getNome().trim().equalsIgnoreCase(agenteId.trim())) {
                        melhor = c;
                        break;
                    }
                }
                nome = melhor.getNome();
            }
        }

        if (detalhe == null && idNacional == null && nome == null) {
            return ResolucaoAgente.naoEncontrado();
        }

        Set<Long> oracleIds = new LinkedHashSet<>();

        if (idNacional != null && idNacional > 0) {
            oracleIds.add(idNacional);
        }

        if (nome != null && !nome.trim().isEmpty()) {
            List<PessoaResumoProjection> pessoas =
                    sigoPessoaRepository.buscarPorNomeNormalizado(nome.trim());
            for (PessoaResumoProjection p : pessoas) {
                if (p.getOracleId() != null) {
                    oracleIds.add(p.getOracleId());
                }
            }
        }

        return new ResolucaoAgente(true, nome, oracleIds);
    }

    private AgenteResumoDto toResumoDto(AgenteResumoProjection agente) {
        AgenteResumoDto dto = new AgenteResumoDto();
        dto.setId(agente.getId());
        dto.setIdNacional(agente.getIdNacional());
        dto.setNumero(agente.getNumero());
        dto.setNome(agente.getNome());
        dto.setCategoria(agente.getCategoria());
        return dto;
    }

    private AgenteDetalheDto toDetalheDto(AgenteDetalheProjection agente) {
        if (agente == null) {
            return null;
        }
        AgenteDetalheDto dto = new AgenteDetalheDto();
        dto.setId(agente.getId());
        dto.setIdNacional(agente.getIdNacional());
        dto.setNumero(agente.getNumero());
        dto.setNome(agente.getNome());
        dto.setCategoria(agente.getCategoria());
        dto.setIdEsquadra(agente.getIdEsquadra());
        dto.setIdComando(agente.getIdComando());
        dto.setMimeType(agente.getMimeType());
        dto.setAssinatura(agente.getAssinatura());
        dto.setAssinaturas(agente.getAssinaturas());
        dto.setTemFoto(Boolean.TRUE.equals(agente.getTemFoto()));

        if (agente.getIdEsquadra() != null) {
            try {
                esquadraRepository.findByOracleId(agente.getIdEsquadra())
                        .map(Esquadra::getNome)
                        .ifPresent(dto::setNomeEsquadra);
            } catch (Exception ignored) {
            }
        }

        if (agente.getIdComando() != null) {
            try {
                comandoRepository.findByIdNacional(agente.getIdComando())
                        .map(c -> c.getNome())
                        .ifPresent(dto::setNomeComando);
            } catch (Exception ignored) {
            }
        }

        return dto;
    }

    private void preencherEnvolvidos(List<AgenteOcorrenciaDto> lista) {

        List<Long> ids = lista.stream()
                .map(AgenteOcorrenciaDto::getOracleId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (ids.isEmpty()) {
            return;
        }

        List<PessoaOcorrenciaProjection> proj = new ArrayList<>();
        for (List<Long> bloco : dividirEmBlocos(ids, 1000)) {
            proj.addAll(sgoOcorrenciaPessoaRepository.buscarEnvolvidosDasOcorrencias(bloco));
        }

        Map<Long, List<Map<String, Object>>> porOcorrencia = new HashMap<>();

        for (PessoaOcorrenciaProjection p : proj) {
            Map<String, Object> pessoa = new HashMap<>();
            pessoa.put("oracleIdPessoa", p.getOracleIdPessoa());
            pessoa.put("nome", p.getNome());
            pessoa.put("condicao", p.getCondicao());
            porOcorrencia
                    .computeIfAbsent(p.getOracleIdOcorrencia(), k -> new ArrayList<>())
                    .add(pessoa);
        }

        for (AgenteOcorrenciaDto dto : lista) {
            dto.setEnvolvidos(porOcorrencia.getOrDefault(dto.getOracleId(), Collections.emptyList()));
        }
    }

    private void preencherCircunstancias(List<AgenteOcorrenciaDto> lista) {
        if (lista.isEmpty()) {
            return;
        }

        List<Long> ids = lista.stream()
                .map(AgenteOcorrenciaDto::getOracleId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (ids.isEmpty()) {
            return;
        }

        Map<Long, List<Map<String, Object>>> porOcorrencia = new HashMap<>();
        for (List<Long> bloco : dividirEmBlocos(ids, 1000)) {
            for (OcorrenciaCircunstanciaProjection c :
                    sgoOcorrenciaPessoaRepository.buscarCircunstanciasDasOcorrencias(bloco)) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("circustancia", c.getCircustancia());
                item.put("resposta", c.getResposta());
                porOcorrencia.computeIfAbsent(c.getOracleIdOcorrencia(), k -> new ArrayList<>()).add(item);
            }
        }

        for (AgenteOcorrenciaDto dto : lista) {
            dto.setCircunstancias(porOcorrencia.getOrDefault(dto.getOracleId(), Collections.emptyList()));
        }
    }

    private List<List<Long>> dividirEmBlocos(List<Long> ids, int tamanho) {
        List<List<Long>> blocos = new ArrayList<>();
        for (int i = 0; i < ids.size(); i += tamanho) {
            blocos.add(ids.subList(i, Math.min(i + tamanho, ids.size())));
        }
        return blocos;
    }

    private AgenteOcorrenciaDto toOcorrenciaDto(AgenteOcorrenciaProjection p, String origem) {
        AgenteOcorrenciaDto dto = new AgenteOcorrenciaDto();
        dto.setOracleId(p.getOracleId());
        dto.setNumOcorrencia(p.getNumOcorrencia());
        dto.setDataRegisto(toLocalDateTime(p.getDataRegisto()));
        dto.setNatureza(p.getNatureza());
        dto.setAutoPrincipal(p.getAutoPrincipal());
        dto.setTpAuto(p.getTpAuto());
        dto.setNumProcesso(p.getNumProcesso());
        dto.setPontoReferencia(p.getPontoReferencia());
        dto.setUnidade(p.getUnidade());
        dto.setFase(p.getFase());
        dto.setEstado(p.getEstado());
        dto.setMotivo(p.getMotivo());
        dto.setCondicao(p.getCondicao());
        dto.setOrigem(origem);
        return dto;
    }

    private LocalDateTime toLocalDateTime(java.util.Date date) {
        return date != null
                ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;
    }

    private static class ResolucaoAgente {

        private final boolean encontrado;
        private final String nome;
        private final List<Long> oracleIds;

        ResolucaoAgente(boolean encontrado, String nome, Set<Long> oracleIds) {
            this.encontrado = encontrado;
            this.nome = nome;
            this.oracleIds = new ArrayList<>(oracleIds);
        }

        static ResolucaoAgente naoEncontrado() {
            return new ResolucaoAgente(false, null, Collections.emptySet());
        }

        boolean encontrado() {
            return encontrado;
        }
    }
}
