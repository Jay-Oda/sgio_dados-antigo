package cv.pn.processmanagement.business.antecedentes;


import cv.pn.processmanagement.business.projection.AgenteResumoProjection;
import cv.pn.processmanagement.business.projection.AssinaturaAutuanteProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaBuscaProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaCircunstanciaProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaDetalheProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaResumoProjection;
import cv.pn.processmanagement.business.projection.PessoaDetalheProjection;
import cv.pn.processmanagement.business.projection.PessoaEnvolvedaProjection;
import cv.pn.processmanagement.business.projection.PessoaOcorrenciaProjection;
import cv.pn.processmanagement.business.projection.PessoaResumoProjection;
import cv.pn.processmanagement.business.ocorrencia.services.OcorrenciaNacionalCache;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.repository.SgoOcorrenciaPessoaRepository;
import cv.pn.processmanagement.business.sigoPessoa.repository.SigoPessoaRepository;
import cv.pn.processmanagement.sgoPecaPessoa.repository.SgoPecaPessoaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import cv.pn.processmanagement.utilities.AutoTipoUtil;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AntecedenteService implements IAntecedenteService {

    private final SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository;
    private final SgoPecaPessoaRepository sgoPecaPessoaRepository;
    private final SigoPessoaRepository sigoPessoaRepository;
    private final OcorrenciaNacionalCache ocorrenciaNacionalCache;

    public AntecedenteService(SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository,
                              SgoPecaPessoaRepository sgoPecaPessoaRepository,
                              SigoPessoaRepository sigoPessoaRepository,
                              OcorrenciaNacionalCache ocorrenciaNacionalCache) {
        this.sgoOcorrenciaPessoaRepository = sgoOcorrenciaPessoaRepository;
        this.sgoPecaPessoaRepository = sgoPecaPessoaRepository;
        this.sigoPessoaRepository = sigoPessoaRepository;
        this.ocorrenciaNacionalCache = ocorrenciaNacionalCache;
    }

    @Override
    public Map<String, Object> consultarAntecedentes(Long oracleId, String auto) {
        return consultarAntecedentesImpl(List.of(oracleId), auto, false);
    }

    private Map<String, Object> consultarAntecedentesSujeito(Long oracleId, String auto) {
        return consultarAntecedentesImpl(List.of(oracleId), auto, true);
    }

    private Map<String, Object> consultarAntecedentesImpl(List<Long> oracleIds, String auto, boolean sujeito) {

        String tpAuto = normalizarAuto(auto);

        List<Long> ids = oracleIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Map<String, Object> empty = new HashMap<>();
        empty.put("possuiOcorrencia", false);
        empty.put("quantidade", 0);
        empty.put("mensagem", "Esta pessoa nao possui antecedentes policiais.");
        empty.put("queixas", List.of());
        empty.put("tiposAuto", Set.of());
        empty.put("condicoes", Set.of());
        if (ids.isEmpty()) {
            return empty;
        }

        List<OcorrenciaResumoProjection> todas = new ArrayList<>();
        for (Long oid : ids) {
            if (sujeito) {
                todas.addAll(sgoOcorrenciaPessoaRepository.buscarAntecedentesSujeitoPorPessoa(oid, null));
            } else {
                todas.addAll(sgoOcorrenciaPessoaRepository.buscarAntecedentesPorPessoa(oid, null));
            }
        }

        List<Map<String, Object>> todasQueixas = agruparPorQueixa(todas);
        int total = todasQueixas.size();

        if (total == 0) {
            return empty;
        }

        Map<String, Integer> porTipo = new LinkedHashMap<>();
        for (Map<String, Object> q : todasQueixas) {
            String tipo = (String) q.get("autoPrincipal");
            if (tipo != null && !tipo.isBlank()) {
                porTipo.merge(tipo, 1, Integer::sum);
            }
        }
        String breakdown = porTipo.entrySet().stream()
                .map(e -> e.getValue() + " " + nomeAutoResumido(e.getKey()))
                .collect(Collectors.joining(" + "));

        Set<String> tiposAutoTotal = new LinkedHashSet<>(porTipo.keySet());

        Set<String> condicoesTotal = new LinkedHashSet<>();
        for (Map<String, Object> q : todasQueixas) {
            Object condsObj = q.get("condicoes");
            if (condsObj instanceof List<?> conds) {
                for (Object c : conds) {
                    if (c instanceof String s && !s.isBlank()) {
                        condicoesTotal.add(s);
                    }
                }
            }
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("possuiOcorrencia", true);
        resp.put("quantidade", total);
        resp.put("mensagem",
                "Esta pessoa possui " + total + " antecedente(s). " + breakdown);
        resp.put("tiposAuto", tiposAutoTotal);
        resp.put("condicoes", condicoesTotal);

        List<OcorrenciaResumoProjection> filtradas;
        if (tpAuto != null) {
            filtradas = new ArrayList<>();
            for (Long oid : ids) {
                if (sujeito) {
                    filtradas.addAll(sgoOcorrenciaPessoaRepository.buscarAntecedentesSujeitoPorPessoa(oid, tpAuto));
                } else {
                    filtradas.addAll(sgoOcorrenciaPessoaRepository.buscarAntecedentesPorPessoa(oid, tpAuto));
                }
            }
        } else {
            filtradas = todas;
        }

        List<Map<String, Object>> queixas = agruparPorQueixa(filtradas);
        preencherCircunstancias(queixas, filtradas);
        preencherAutuanteEDetentores(queixas, filtradas);
        resp.put("queixas", queixas);

        return resp;
    }

    private String nomeAutoResumido(String autoPrincipal) {
        if (autoPrincipal == null) {
            return "Desconhecido";
        }
        return autoPrincipal.replaceFirst("(?i)^Auto\\s+de\\s+", "").trim();
    }

    private List<List<PessoaResumoProjection>> agruparPorIdentidade(List<PessoaResumoProjection> pessoas) {
        Map<String, List<PessoaResumoProjection>> grupos = new LinkedHashMap<>();
        for (PessoaResumoProjection p : pessoas) {
            String chave = chaveIdentidade(p);
            grupos.computeIfAbsent(chave, k -> new ArrayList<>()).add(p);
        }
        return new ArrayList<>(grupos.values());
    }

    private String chaveIdentidade(PessoaResumoProjection p) {
        String pai = normalizar(p.getPai());
        String mae = normalizar(p.getMae());
        Date dt = p.getDtNascimento();

        if (pai == null || mae == null || dt == null) {
            return "ID:" + p.getOracleId();
        }

        return normalizarIdentidade(pai) + "|" + normalizarIdentidade(mae) + "|" + dt.getTime();
    }

    private String normalizarIdentidade(String s) {
        if (s == null) {
            return "";
        }
        return s.trim().toUpperCase().replaceAll("\\s+", " ");
    }

    private void preencherAutuanteEDetentores(List<Map<String, Object>> queixas,
                                              List<OcorrenciaResumoProjection> ocorrencias) {
        if (queixas.isEmpty() || ocorrencias.isEmpty()) {
            return;
        }

        Map<Long, String> autuantePorOcorrencia = new HashMap<>();
        for (OcorrenciaResumoProjection p : ocorrencias) {
            if (p.getOracleIdOcorrencia() == null) {
                continue;
            }
            String autuante = p.getAutuante();
            if (autuante != null && !autuante.isBlank()) {
                autuantePorOcorrencia.putIfAbsent(p.getOracleIdOcorrencia(), autuante);
            }
        }

        List<Long> ids = ocorrencias.stream()
                .map(OcorrenciaResumoProjection::getOracleIdOcorrencia)
                .filter(id -> id != null)
                .distinct()
                .toList();

        Map<Long, List<String>> detentoresPorOcorrencia = new HashMap<>();
        if (!ids.isEmpty()) {
            for (List<Long> bloco : dividirEmBlocos(ids, 1000)) {
                for (PessoaOcorrenciaProjection d :
                        sgoOcorrenciaPessoaRepository.buscarDetentoresDasOcorrencias(bloco)) {
                    if (d.getOracleIdOcorrencia() == null) {
                        continue;
                    }
                    String nome = d.getNome() != null && !d.getNome().isBlank()
                            ? d.getNome()
                            : "DESCONHECIDO";
                    detentoresPorOcorrencia
                            .computeIfAbsent(d.getOracleIdOcorrencia(), k -> new ArrayList<>())
                            .add(nome);
                }
            }
        }

        for (Map<String, Object> queixa : queixas) {
            Object oid = queixa.get("oracleIdOcorrencia");
            if (oid instanceof Long id) {
                queixa.put("autuante", autuantePorOcorrencia.getOrDefault(id, ""));
                queixa.put("detentores", detentoresPorOcorrencia.getOrDefault(id, List.of()));
            } else {
                queixa.put("autuante", "");
                queixa.put("detentores", List.of());
            }
        }
    }

    private List<List<Long>> dividirEmBlocos(List<Long> ids, int tamanho) {
        List<List<Long>> blocos = new ArrayList<>();
        for (int i = 0; i < ids.size(); i += tamanho) {
            blocos.add(ids.subList(i, Math.min(i + tamanho, ids.size())));
        }
        return blocos;
    }

    private String normalizarAuto(String auto) {
        return AutoTipoUtil.normalizar(auto);
    }

    private List<Map<String, Object>> agruparPorQueixa(List<OcorrenciaResumoProjection> ocorrencias) {

        Map<Long, Map<String, Object>> porQueixa = new LinkedHashMap<>();

        for (OcorrenciaResumoProjection p : ocorrencias) {
            Long idOcorrencia = p.getOracleIdOcorrencia();
            String num = p.getNumOcorrencia() != null && !p.getNumOcorrencia().isBlank()
                    ? p.getNumOcorrencia()
                    : "(sem numero)";

            Map<String, Object> queixa = porQueixa.computeIfAbsent(idOcorrencia, k -> {
                Map<String, Object> q = new LinkedHashMap<>();
                q.put("numOcorrencia", num);
                q.put("autoPrincipal", p.getAutoPrincipal());
                q.put("oracleIdOcorrencia", p.getOracleIdOcorrencia());
                q.put("motivo", p.getMotivo());
                q.put("pontoReferencia", p.getPontoReferencia());
                q.put("dataRegisto", toLocalDateTime(p.getDataRegisto()));
                q.put("unidade", p.getUnidade());
                q.put("fase", p.getFase());
                q.put("naturezas", new LinkedHashSet<String>());
                q.put("condicoes", new LinkedHashSet<String>());
                return q;
            });

            @SuppressWarnings("unchecked")
            Set<String> naturezas = (Set<String>) queixa.get("naturezas");
            @SuppressWarnings("unchecked")
            Set<String> condicoes = (Set<String>) queixa.get("condicoes");

            if (p.getNatureza() != null && !p.getNatureza().isBlank()) {
                naturezas.add(p.getNatureza());
            }
            if (p.getCondicao() != null && !p.getCondicao().isBlank()) {
                condicoes.add(p.getCondicao());
            }
        }

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Map<String, Object> queixa : porQueixa.values()) {
            queixa.put("naturezas", new ArrayList<>((Set<?>) queixa.get("naturezas")));
            queixa.put("condicoes", new ArrayList<>((Set<?>) queixa.get("condicoes")));
            lista.add(queixa);
        }
        return lista;
    }

    private void preencherCircunstancias(List<Map<String, Object>> queixas,
                                         List<OcorrenciaResumoProjection> ocorrencias) {
        if (queixas.isEmpty() || ocorrencias.isEmpty()) {
            return;
        }

        List<Long> ids = ocorrencias.stream()
                .map(OcorrenciaResumoProjection::getOracleIdOcorrencia)
                .filter(id -> id != null)
                .distinct()
                .toList();

        if (ids.isEmpty()) {
            return;
        }

        Map<Long, List<Map<String, Object>>> porOcorrencia = new HashMap<>();
        for (OcorrenciaCircunstanciaProjection c :
                sgoOcorrenciaPessoaRepository.buscarCircunstanciasDasOcorrencias(ids)) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("circustancia", c.getCircustancia());
            item.put("resposta", c.getResposta());
            porOcorrencia.computeIfAbsent(c.getOracleIdOcorrencia(), k -> new ArrayList<>()).add(item);
        }

        for (Map<String, Object> queixa : queixas) {
            Object oid = queixa.get("oracleIdOcorrencia");
            if (oid instanceof Long id) {
                queixa.put("circunstancias", porOcorrencia.getOrDefault(id, List.of()));
            } else {
                queixa.put("circunstancias", List.of());
            }
        }
    }

    private LocalDateTime toLocalDateTime(Date data) {
        if (data == null) return null;
        return new java.sql.Timestamp(data.getTime()).toLocalDateTime();
    }

    private String stripHtml(String html) {
        if (html == null) return null;
        return html.replaceAll("<[^>]+>", "").trim();
    }

    private String calcularPrazo(Date dataRegisto) {
        if (dataRegisto == null) return "Desconhecido";
        LocalDateTime inicio = toLocalDateTime(dataRegisto);
        LocalDateTime agora = LocalDateTime.now();
        java.time.Duration duracao = java.time.Duration.between(inicio, agora);
        long dias = duracao.toDays();
        long horas = duracao.toHours() % 24;
        long minutos = duracao.toMinutes() % 60;
        if (dias > 0) {
            return dias + "d " + horas + "h " + minutos + "min";
        }
        return horas + "h " + minutos + "min";
    }

    @Override
    public Object pesquisarPessoas(PessoaAntecedenteRequest req) {

        if (req == null) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Informe pelo menos um criterio de pesquisa.")
                    .builder();
        }

        Date dtNasc;
        try {
            dtNasc = parseDataNascimento(req.getDtNascimento());
        } catch (IllegalArgumentException e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText(e.getMessage())
                    .builder();
        }

        List<PessoaResumoProjection> pessoas = procurarPessoas(
                normalizar(req.getNome()),
                normalizar(req.getSobrenome()),
                normalizar(req.getAlcunha()),
                normalizar(req.getNum()),
                normalizar(req.getPai()),
                normalizar(req.getMae()),
                dtNasc
        );

        if (pessoas.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Nenhuma pessoa encontrada com os criterios informados.")
                    .builder();
        }

        List<List<PessoaResumoProjection>> grupos = agruparPorIdentidade(pessoas);
        List<Map<String, Object>> resp = new ArrayList<>();

        for (List<PessoaResumoProjection> grupo : grupos) {
            List<Long> oracleIds = grupo.stream()
                    .map(PessoaResumoProjection::getOracleId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();

            Map<String, Object> item = basePessoa(grupo.get(0));
            anexarResumoAntecedentes(item, consultarAntecedentesImpl(oracleIds, null, true));

            if (Boolean.TRUE.equals(item.get("possuiOcorrencia"))) {
                if (grupo.size() > 1) {
                    item.put("aliases", grupo.stream()
                            .map(PessoaResumoProjection::getNome)
                            .filter(Objects::nonNull)
                            .distinct()
                            .toList());
                    item.put("pessoaOracleIds", oracleIds);
                }
                resp.add(item);
            }
        }

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Encontradas " + resp.size() + " pessoa(s) com antecedentes.")
                .setDetails(Collections.singletonList(resp))
                .builder();
    }

    private Map<String, Object> basePessoa(PessoaResumoProjection p) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", p.getId());
        m.put("oracleId", p.getOracleId());
        m.put("nome", p.getNome());
        m.put("alcunhas", p.getAlcunhas());
        m.put("numDocumento", p.getNum());
        m.put("pai", p.getPai());
        m.put("mae", p.getMae());
        m.put("dataNascimento", p.getDtNascimento());
        return m;
    }

    private void anexarResumoAntecedentes(Map<String, Object> item, Object antecedentesObj) {
        if (!(antecedentesObj instanceof Map<?, ?> antecedentes)) {
            item.put("possuiOcorrencia", false);
            item.put("quantidade", 0);
            item.put("antecedentes", List.of());
            item.put("tiposAuto", Set.of());
            item.put("condicoes", Set.of());
            return;
        }

        item.put("possuiOcorrencia", antecedentes.get("possuiOcorrencia"));
        item.put("quantidade", antecedentes.get("quantidade"));
        item.put("mensagemAntecedentes", antecedentes.get("mensagem"));
        item.put("antecedentes", antecedentes.get("queixas"));
        Object tiposAuto = antecedentes.get("tiposAuto");
        item.put("tiposAuto", tiposAuto != null ? tiposAuto : Set.of());
        Object condicoes = antecedentes.get("condicoes");
        item.put("condicoes", condicoes != null ? condicoes : Set.of());
    }

    @Override
    public Object pesquisarAntecedentes(String nome, String alcunha, String pai, String mae, String dataNascimento, String auto) {

        Date dtNasc;
        try {
            dtNasc = parseDataNascimento(dataNascimento);
        } catch (IllegalArgumentException e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText(e.getMessage())
                    .builder();
        }

        List<PessoaResumoProjection> pessoas = procurarPessoas(
                normalizar(nome),
                null,
                normalizar(alcunha),
                null,
                normalizar(pai),
                normalizar(mae),
                dtNasc
        );

        if (pessoas.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Nenhuma pessoa encontrada com os criterios informados.")
                    .builder();
        }

        String tpAuto = normalizarAuto(auto);
        List<List<PessoaResumoProjection>> grupos = agruparPorIdentidade(pessoas);
        List<Map<String, Object>> lista = new ArrayList<>();

        for (List<PessoaResumoProjection> grupo : grupos) {
            List<Long> oracleIds = grupo.stream()
                    .map(PessoaResumoProjection::getOracleId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();

            Map<String, Object> item = basePessoa(grupo.get(0));
            anexarResumoAntecedentes(item, consultarAntecedentesImpl(oracleIds, tpAuto, false));

            if (Boolean.TRUE.equals(item.get("possuiOcorrencia"))) {
                if (grupo.size() > 1) {
                    item.put("aliases", grupo.stream()
                            .map(PessoaResumoProjection::getNome)
                            .filter(Objects::nonNull)
                            .distinct()
                            .toList());
                    item.put("pessoaOracleIds", oracleIds);
                }
                lista.add(item);
            }
        }

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Encontradas " + lista.size() + " pessoa(s) com antecedentes.")
                .setDetails(Collections.singletonList(lista))
                .builder();
    }

    private List<PessoaResumoProjection> procurarPessoas(
            String nome, String sobrenome, String alcunha, String num, String pai, String mae, Date dtNasc) {

        Map<Long, PessoaResumoProjection> unicos = new LinkedHashMap<>();

        if (alcunha != null) {
            sigoPessoaRepository.buscarPorAlcunha(alcunha).forEach(p -> {
                if (p.getOracleId() != null) {
                    unicos.putIfAbsent(p.getOracleId(), p);
                }
            });
        }

        // sobrenome e nominho sao pesquisados como trecho do nome
        String nomeBusca = nome != null ? nome : sobrenome;

        if (nomeBusca != null || num != null || pai != null || mae != null || dtNasc != null) {
            List<PessoaResumoProjection> encontradas;
            if (dtNasc == null) {
                encontradas = sigoPessoaRepository.buscarSemData(nomeBusca, num, pai, mae);
            } else {
                encontradas = sigoPessoaRepository.buscarComData(nomeBusca, num, pai, mae, dtNasc);
            }

            encontradas.forEach(p -> {
                if (p.getOracleId() != null) {
                    unicos.putIfAbsent(p.getOracleId(), p);
                }
            });
        }

        return new ArrayList<>(unicos.values());
    }

    private Date parseDataNascimento(String dtNascimento) {
        if (dtNascimento == null || dtNascimento.isBlank()) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            return df.parse(dtNascimento.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Data de nascimento invalida. Use YYYY-MM-DD (ex: 2026-01-20).");
        }
    }

    @Override
    public Object listarTodasPessoas(int page, int size, String auto) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 500);

        String tpAuto = normalizarAuto(auto);

        List<PessoaResumoProjection> pessoas =
                sigoPessoaRepository.listarTodasPaginado(safeSize, safePage * safeSize);
        long total = sigoPessoaRepository.contarTodas();

        List<Map<String, Object>> resp = pessoas.stream()
                .map(p -> toPessoaResumo(p, false, tpAuto))
                .toList();

        Map<String, Object> meta = new HashMap<>();
        meta.put("pagina", safePage);
        meta.put("tamanho", safeSize);
        meta.put("totalElementos", total);
        meta.put("totalPaginas", (int) Math.ceil((double) total / safeSize));

        Map<String, Object> respFinal = new HashMap<>();
        respFinal.put("paginacao", meta);
        respFinal.put("quantidade", resp.size());
        respFinal.put("pessoas", resp);

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Listagem de pessoas.")
                .setDetails(Collections.singletonList(respFinal))
                .builder();
    }

    @Override
    public Object detalharPessoa(Long oracleId, String auto) {

        Optional<PessoaDetalheProjection> pessoaOpt =
                sigoPessoaRepository.findDetalheByOracleId(oracleId);

        if (pessoaOpt.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Pessoa nao encontrada com o oracleId informado.")
                    .builder();
        }

        PessoaDetalheProjection p = pessoaOpt.get();

        String tpAuto = normalizarAuto(auto);

        Map<String, Object> resp = new HashMap<>();
        resp.put("id", p.getId());
        resp.put("oracleId", p.getOracleId());
        resp.put("nome", p.getNome());
        resp.put("alcunhas", p.getAlcunhas());
        resp.put("numDocumento", p.getNum());
        resp.put("pai", p.getPai());
        resp.put("mae", p.getMae());
        resp.put("dataNascimento", p.getDtNascimento());
        resp.put("sexo", p.getSexo());
        resp.put("estadoCivil", p.getEstadoCivil());
        resp.put("tipoIdentificacao", p.getTpIdentificacao());
        resp.put("naturalidade", p.getNaturalidade());
        resp.put("morada", p.getMorada());
        resp.put("nacionalidade", p.getNacionalidade());
        resp.put("contacto", p.getContacto());
        resp.put("profissao", p.getProfissao());
        resp.put("tipoPessoa", p.getTipoPessoa());
        resp.put("habilitacaoLiteraria", p.getHabilitacaoLiteraria());
        resp.put("idade", p.getIdade());

        Map<String, Object> antecedentes = consultarAntecedentesImpl(List.of(oracleId), tpAuto, false);
        resp.put("possuiOcorrencia", antecedentes.get("possuiOcorrencia"));
        resp.put("quantidadeOcorrencias", antecedentes.get("quantidade"));
        resp.put("mensagemAntecedentes", antecedentes.get("mensagem"));
        resp.put("antecedentes", antecedentes.get("queixas"));
        resp.put("tiposAuto", antecedentes.get("tiposAuto"));
        resp.put("condicoes", antecedentes.get("condicoes"));

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Pessoa encontrada.")
                .setDetails(Collections.singletonList(resp))
                .builder();
    }

    private Map<String, Object> toPessoaResumo(PessoaResumoProjection p, boolean sujeito, String tpAuto) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", p.getId());
        m.put("oracleId", p.getOracleId());
        m.put("nome", p.getNome());
        m.put("alcunhas", p.getAlcunhas());
        m.put("numDocumento", p.getNum());
        m.put("pai", p.getPai());
        m.put("mae", p.getMae());
        m.put("dataNascimento", p.getDtNascimento());

        if (p.getOracleId() == null) {
            m.put("possuiOcorrencia", false);
            m.put("quantidadeOcorrencias", 0L);
            return m;
        }

        boolean possui;
        long quantidade;
        if (sujeito) {
            possui = sgoOcorrenciaPessoaRepository.existsAntecedenteSujeitoReal(p.getOracleId(), tpAuto);
            quantidade = sgoOcorrenciaPessoaRepository.contarAntecedentesSujeitoReais(p.getOracleId(), tpAuto);
        } else {
            possui = sgoOcorrenciaPessoaRepository.existsAntecedenteReal(p.getOracleId(), tpAuto);
            quantidade = sgoOcorrenciaPessoaRepository.contarAntecedentesReais(p.getOracleId(), tpAuto);
        }
        m.put("possuiOcorrencia", possui);
        m.put("quantidadeOcorrencias", quantidade);
        return m;
    }

    private String normalizar(String v) {
        if (v == null) return null;
        v = v.trim();
        return v.isEmpty() ? null : v;
    }

    @Override
    public Object buscarAntecedentesPorNome(String nome, String pai, String mae, String dataNascimento) {

        if (nome == null || nome.isBlank()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Informe o nome da pessoa.")
                    .builder();
        }

        Date dtNasc;
        try {
            dtNasc = parseDataNascimento(dataNascimento);
        } catch (IllegalArgumentException e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText(e.getMessage())
                    .builder();
        }

        String dtNascParam = null;
        if (dtNasc != null) {
            dtNascParam = new SimpleDateFormat("yyyy-MM-dd").format(dtNasc);
        }

        List<OcorrenciaBuscaProjection> resultados = sgoOcorrenciaPessoaRepository.buscarAntecedentesPorNome(
                "%" + nome.trim() + "%",
                pai != null && !pai.isBlank() ? "%" + pai.trim() + "%" : null,
                mae != null && !mae.isBlank() ? "%" + mae.trim() + "%" : null,
                dtNascParam
        );

        Map<String, Map<String, Object>> porOcorrencia = new LinkedHashMap<>();

        for (OcorrenciaBuscaProjection r : resultados) {
            String num = r.getNumOcorrencia();
            Map<String, Object> ant = porOcorrencia.computeIfAbsent(num, k -> {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("numOcorrencia", num);
                m.put("naturezas", new ArrayList<String>());
                m.put("autoPrincipal", r.getAutoPrincipal());
                m.put("condicao", r.getCondicao());
                m.put("tipologiaCrime", new ArrayList<String>());
                m.put("dataRegisto", toLocalDateTime(r.getDataRegisto()));
                m.put("unidade", r.getUnidade());
                m.put("fase", r.getFase());
                return m;
            });

            if (r.getNatureza() != null && !r.getNatureza().isBlank()) {
                @SuppressWarnings("unchecked")
                List<String> nats = (List<String>) ant.get("naturezas");
                if (!nats.contains(r.getNatureza())) {
                    nats.add(r.getNatureza());
                }
            }

            if (r.getTipologiaCrime() != null && !r.getTipologiaCrime().isBlank()) {
                @SuppressWarnings("unchecked")
                List<String> tips = (List<String>) ant.get("tipologiaCrime");
                if (!tips.contains(r.getTipologiaCrime())) {
                    tips.add(r.getTipologiaCrime());
                }
            }
        }

        List<Map<String, Object>> lista = new ArrayList<>(porOcorrencia.values());

        if (lista.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Antecedente(s) SIGO\nAviso!\nNão foi encontrado nenhum registo(s) de antecedente(s).")
                    .builder();
        }

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Antecedentes de " + nome.trim())
                .setDetails(Collections.singletonList(lista))
                .builder();
    }

    @Override
    public Object pesquisarPessoaCompleta(String nome, String alcunha, String pai, String mae, String dataNascimento) {

        Date dtNasc;
        try {
            dtNasc = parseDataNascimento(dataNascimento);
        } catch (IllegalArgumentException e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText(e.getMessage())
                    .builder();
        }

        List<PessoaDetalheProjection> pessoas = sigoPessoaRepository.buscarDetalhePorFiltros(
                normalizar(nome),
                normalizar(alcunha),
                normalizar(pai),
                normalizar(mae),
                dtNasc
        );

        if (pessoas.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Nenhuma pessoa encontrada com os critérios informados.")
                    .builder();
        }

        List<Map<String, Object>> resp = new ArrayList<>();
        for (PessoaDetalheProjection p : pessoas) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", p.getId());
            item.put("oracleId", p.getOracleId());
            item.put("nome", p.getNome());
            item.put("idade", calcularIdade(p.getDtNascimento(), p.getIdade()));
            item.put("dataNascimento", formatarData(p.getDtNascimento()));
            item.put("estadoCivil", p.getEstadoCivil());
            item.put("profissao", p.getProfissao());
            item.put("pai", p.getPai());
            item.put("mae", p.getMae());
            item.put("sexo", p.getSexo());
            item.put("numDocumento", p.getNum());
            item.put("tipoIdentificacao", p.getTpIdentificacao());
            item.put("nacionalidade", p.getNacionalidade());
            item.put("naturalidade", p.getNaturalidade());
            item.put("tipoPessoa", p.getTipoPessoa());
            item.put("habilitacaoLiteraria", p.getHabilitacaoLiteraria());
            item.put("morada", p.getMorada());
            item.put("contacto", p.getContacto());
            resp.add(item);
        }

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Encontradas " + resp.size() + " pessoa(s).")
                .setDetails(Collections.singletonList(resp))
                .builder();
    }

    private String calcularIdade(Date dtNascimento, String idadeArmazenada) {
        if (dtNascimento == null) {
            return idadeArmazenada;
        }
        java.time.LocalDate nascimento = java.time.Instant.ofEpochMilli(dtNascimento.getTime())
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate hoje = java.time.LocalDate.now();
        long anos = java.time.temporal.ChronoUnit.YEARS.between(nascimento, hoje);
        if (nascimento.plusYears(anos).isAfter(hoje)) {
            anos--;
        }
        return String.valueOf(anos);
    }

    private String formatarData(Date data) {
        if (data == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    @Override
    public Object pesquisarPorNumOcorrencia(String numOcorrencia) {

        if (numOcorrencia == null || numOcorrencia.isBlank()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Informe o número da ocorrência.")
                    .builder();
        }

        List<OcorrenciaBuscaProjection> resultados =
                sgoOcorrenciaPessoaRepository.buscarPorNumOcorrencia(numOcorrencia.trim());

        if (resultados.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("0 registos de ocorrência")
                    .builder();
        }

        Map<String, Map<String, Object>> porOcorrencia = new LinkedHashMap<>();

        for (OcorrenciaBuscaProjection r : resultados) {
            String num = r.getNumOcorrencia();
            Map<String, Object> ant = porOcorrencia.computeIfAbsent(num, k -> {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("numOcorrencia", num);
                m.put("naturezas", new ArrayList<String>());
                m.put("autoPrincipal", r.getAutoPrincipal());
                m.put("condicao", r.getCondicao());
                m.put("tipologiaCrime", new ArrayList<String>());
                m.put("dataRegisto", toLocalDateTime(r.getDataRegisto()));
                m.put("unidade", r.getUnidade());
                m.put("registradoPor", r.getRegistradoPor() != null ? r.getRegistradoPor() : "");
                m.put("prazo", r.getPrazo() != null && !r.getPrazo().isBlank() ? r.getPrazo() : "0:0:0 horas");
                m.put("tipo", r.getTipo() != null ? r.getTipo() : "Criminal");
                m.put("fase", r.getFase());
                m.put("estado", r.getEstado() != null ? r.getEstado() : "Aberto");
                return m;
            });

            if (r.getNatureza() != null && !r.getNatureza().isBlank()) {
                @SuppressWarnings("unchecked")
                List<String> nats = (List<String>) ant.get("naturezas");
                if (!nats.contains(r.getNatureza())) {
                    nats.add(r.getNatureza());
                }
            }

            if (r.getTipologiaCrime() != null && !r.getTipologiaCrime().isBlank()) {
                @SuppressWarnings("unchecked")
                List<String> tips = (List<String>) ant.get("tipologiaCrime");
                if (!tips.contains(r.getTipologiaCrime())) {
                    tips.add(r.getTipologiaCrime());
                }
            }
        }

        List<Map<String, Object>> lista = new ArrayList<>(porOcorrencia.values());

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText(lista.size() + " Ocorrência(s) encontrada(s)")
                .setDetails(Collections.singletonList(lista))
                .builder();
    }

    @Override
    public Object detalharOcorrencia(String numOcorrencia) {

        if (numOcorrencia == null || numOcorrencia.isBlank()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Informe o número da ocorrência.")
                    .builder();
        }

        // Pelo oracle_id (com índice) quando a cache da listagem o conhece; senão pelo número, como antes
        Optional<Long> oracleId = ocorrenciaNacionalCache.oracleIdPorNumero(numOcorrencia.trim());

        List<OcorrenciaDetalheProjection> ocorrencias = oracleId.isPresent()
                ? sgoOcorrenciaPessoaRepository.detalharPorOracleId(oracleId.get())
                : sgoOcorrenciaPessoaRepository.detalharPorNumOcorrencia(numOcorrencia.trim());

        if (ocorrencias.isEmpty()) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("0 registos de ocorrência")
                    .builder();
        }

        List<Map<String, Object>> lista = new ArrayList<>();
        Set<String> vistos = new LinkedHashSet<>();

        for (OcorrenciaDetalheProjection r : ocorrencias) {
            Long idOcor = r.getOracleIdOcorrencia();
            if (idOcor == null) continue;
            if (!vistos.add(String.valueOf(idOcor))) continue;

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("oracleId", idOcor);
            item.put("numOcorrencia", r.getNumOcorrencia());
            item.put("dataRegisto", toLocalDateTime(r.getDataRegisto()));
            item.put("natureza", r.getNatureza() != null ? r.getNatureza() : "");
            item.put("autoPrincipal", r.getAutoPrincipal());
            item.put("unidade", r.getUnidade());
            item.put("comando", r.getComando() != null ? r.getComando() : "");
            item.put("motivo", r.getMotivo());
            item.put("pontoReferencia", r.getPontoReferencia());
            item.put("local", r.getLocal() != null ? r.getLocal() : "");

            List<Map<String, Object>> envolvidos = new ArrayList<>();
            List<PessoaEnvolvedaProjection> pessoasEnv = oracleId.isPresent()
                    ? sgoOcorrenciaPessoaRepository.buscarPessoasPorOracleId(oracleId.get())
                    : sgoOcorrenciaPessoaRepository.buscarPessoasPorNumOcorrencia(numOcorrencia.trim());

            for (PessoaEnvolvedaProjection pe : pessoasEnv) {
                String condicao = pe.getCondicao();
                boolean isAgenteRole = condicao != null
                        && (condicao.contains("Detentor")
                        || condicao.contains("Testemunha")
                        || condicao.contains("Agente")
                        || condicao.contains("Autuante"));

                Map<String, Object> pm = new LinkedHashMap<>();
                pm.put("condicao", condicao);
                pm.put("nome", pe.getNome());
                pm.put("alcunhas", pe.getAlcunhas());
                pm.put("pai", pe.getPai());
                pm.put("mae", pe.getMae());
                pm.put("dtNascimento", toLocalDateTime(pe.getDtNascimento()));
                pm.put("idade", pe.getIdade());
                pm.put("estadoCivil", pe.getEstadoCivil());
                pm.put("contacto", pe.getContacto());
                pm.put("morada", pe.getMorada());
                pm.put("nacionalidade", pe.getNacionalidade());
                pm.put("naturalidade", pe.getNaturalidade());
                pm.put("profissao", pe.getProfissao());
                pm.put("oracleIdPessoa", pe.getOracleId());

                if (isAgenteRole && pe.getCategoriaEfetivo() != null) {
                    pm.put("categoriaEfetivo", pe.getCategoriaEfetivo());
                    pm.put("esquadraEfetivo", pe.getEsquadraEfetivo() != null ? pe.getEsquadraEfetivo() : "");
                }

                if (pe.getTipoAssinante() != null) {
                    pm.put("tipoAssinante", stripHtml(pe.getTipoAssinante()));
                }

                pm.put("assinatura", pe.getAssinatura() != null ? pe.getAssinatura() : null);

                boolean ehAgenteAutuante = condicao != null
                        && !condicao.contains("Testemunha")
                        && (condicao.contains("Detentor")
                        || condicao.contains("Autuante")
                        || condicao.contains("Agente"));

                if (ehAgenteAutuante && pm.get("assinatura") == null && pe.getNome() != null) {
                    AssinaturaAutuanteProjection sig =
                            sgoOcorrenciaPessoaRepository.buscarAssinaturaAutuantePorNome(pe.getNome().trim());
                    if (sig != null && sig.getAssinatura() != null) {
                        pm.put("assinatura", sig.getAssinatura());
                        pm.put("tipoAssinante", "Autuante");
                        if (sig.getCategoria() != null) {
                            pm.put("categoriaAssinatura", sig.getCategoria());
                        }
                    }
                }

                envolvidos.add(pm);
            }
            item.put("envolvidos", envolvidos);

            if (r.getAutoPrincipal() != null && r.getAutoPrincipal().contains("VBG")) {
                Map<String, Object> vbg = new LinkedHashMap<>();

                List<Map<String, Object>> risco = sgoOcorrenciaPessoaRepository.buscarRiscoVbg(idOcor);
                if (!risco.isEmpty()) {
                    long pontuacaoTotal = risco.stream()
                            .mapToLong(m -> m.get("pontuacao") != null ? ((Number) m.get("pontuacao")).longValue() : 0L)
                            .sum();
                    long respondidoSim = risco.stream()
                            .filter(m -> "SIM".equals(m.get("resposta")))
                            .count();
                    vbg.put("risco", Map.of(
                            "perguntas", risco,
                            "pontuacaoTotal", pontuacaoTotal,
                            "totalSim", respondidoSim,
                            "totalPerguntas", (long) risco.size()
                    ));
                }

                List<Map<String, Object>> circ = sgoOcorrenciaPessoaRepository.buscarCircunstanciasVbg(idOcor);
                if (!circ.isEmpty()) vbg.put("circunstancias", circ);

                List<Map<String, Object>> meios = sgoOcorrenciaPessoaRepository.buscarMeiosVbg(idOcor);
                if (!meios.isEmpty()) vbg.put("meios", meios);

                List<Map<String, Object>> servApoio = sgoOcorrenciaPessoaRepository.buscarServicoApoioVbg(idOcor);
                if (!servApoio.isEmpty()) vbg.put("servicoApoio", servApoio);

                List<Map<String, Object>> centroSaude = sgoOcorrenciaPessoaRepository.buscarCentroSaudeVbg(idOcor);
                if (!centroSaude.isEmpty()) vbg.put("centroSaude", centroSaude);

                List<Map<String, Object>> parentesco = sgoOcorrenciaPessoaRepository.buscarParentescoVbg(idOcor);
                if (!parentesco.isEmpty()) vbg.put("parentesco", parentesco);

                List<Map<String, Object>> histAutor = sgoOcorrenciaPessoaRepository.buscarHistoricoAutorVbg(idOcor);
                if (!histAutor.isEmpty()) vbg.put("historicoAutor", histAutor);

                List<Map<String, Object>> histVitima = sgoOcorrenciaPessoaRepository.buscarHistoricoVitimaVbg(idOcor);
                if (!histVitima.isEmpty()) vbg.put("historicoVitima", histVitima);

                if (!vbg.isEmpty()) item.put("vbg", vbg);
            }

            lista.add(item);
        }

        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Detalhe da ocorrência " + numOcorrencia.trim())
                .setDetails(Collections.singletonList(lista))
                .builder();
    }

}
