package cv.pn.processmanagement.business.ocorrencia.services;

import cv.pn.processmanagement.business.ocorrencia.repository.SgoOcorrenciaRepository;
import cv.pn.processmanagement.business.projection.OcorrenciaNacionalProjection;
import cv.pn.processmanagement.business.projection.OpcaoProjection;
import cv.pn.processmanagement.utilities.APIResponse;
import cv.pn.processmanagement.utilities.Pagination;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OcorrenciaNacionalService implements IOcorrenciaNacionalService {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final SgoOcorrenciaRepository sgoOcorrenciaRepository;

    public OcorrenciaNacionalService(SgoOcorrenciaRepository sgoOcorrenciaRepository) {
        this.sgoOcorrenciaRepository = sgoOcorrenciaRepository;
    }

    @Override
    public APIResponse listarOcorrencias(int page,
                                         int size,
                                         String numOcorrencia,
                                         String tpAuto,
                                         String estado,
                                         String dataInicio,
                                         String dataFim,
                                         String natureza,
                                         String tipologia,
                                         String enquadramento,
                                         Long comando,
                                         Long unidade) {

        try {

            int pagina = Math.max(page, 0);
            int tamanho = Math.min(Math.max(size, 1), 100);
            int offset = pagina * tamanho;

            String numFiltro = (numOcorrencia != null && !numOcorrencia.trim().isEmpty())
                    ? "%" + numOcorrencia.trim() + "%" : null;

            String dataInicioSql = normalizarDataHora(dataInicio);
            String dataFimSql = normalizarDataHora(dataFim);

            List<OcorrenciaNacionalProjection> registos = sgoOcorrenciaRepository.buscarOcorrenciasNacionais(
                    numFiltro, tpAuto, estado, dataInicioSql, dataFimSql,
                    natureza, tipologia, enquadramento, comando, unidade,
                    offset, tamanho);

            long total = sgoOcorrenciaRepository.countOcorrenciasNacionais(
                    numFiltro, tpAuto, estado, dataInicioSql, dataFimSql,
                    natureza, tipologia, enquadramento, comando, unidade);

            int pageCount = (int) Math.ceil((double) total / tamanho);

            List<Object> details = new ArrayList<>(registos);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrências encontradas com sucesso")
                    .setDetails(details)
                    .setPagination(new Pagination(pagina, tamanho, pageCount, total))
                    .builder();

        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar ocorrências: " + e.getMessage())
                    .setDetails(Collections.emptyList())
                    .builder();
        }
    }

    @Override
    public Map<String, Object> obterOpcoes() {
        Map<String, Object> opcoes = new LinkedHashMap<>();

        opcoes.put("tiposAutos", List.of(
                Map.of("codigo", "1", "nome", "Auto de Denúncia"),
                Map.of("codigo", "2", "nome", "Auto de Notícia"),
                Map.of("codigo", "3", "nome", "Auto de Detenção"),
                Map.of("codigo", "4", "nome", "Auto de Denúncia VBG"),
                Map.of("codigo", "5", "nome", "Auto de Identificação")
        ));

        opcoes.put("estados", List.of(
                Map.of("codigo", "A", "nome", "Aberto"),
                Map.of("codigo", "F", "nome", "Fechado"),
                Map.of("codigo", "E", "nome", "Em análise"),
                Map.of("codigo", "R", "nome", "Arquivado")
        ));

        opcoes.put("naturezas", sgoOcorrenciaRepository.buscarNaturezas());
        opcoes.put("tipologias", sgoOcorrenciaRepository.buscarTipologias());
        opcoes.put("enquadramentos", sgoOcorrenciaRepository.buscarEnquadramentos());

        List<Map<String, Object>> comandos = new ArrayList<>();
        for (OpcaoProjection cmd : sgoOcorrenciaRepository.buscarComandos()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("valor", cmd.getValor());
            m.put("nome", cmd.getNome());
            comandos.add(m);
        }
        opcoes.put("comandos", comandos);

        List<Map<String, Object>> unidades = new ArrayList<>();
        for (OpcaoProjection uni : sgoOcorrenciaRepository.buscarUnidades()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("valor", uni.getValor());
            m.put("nome", uni.getNome());
            unidades.add(m);
        }
        opcoes.put("unidades", unidades);

        return opcoes;
    }

    private String normalizarDataHora(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        String v = valor.trim();
        try {
            LocalDateTime dt = LocalDateTime.parse(v.replace("T", " "), FORMATO_DATA_HORA);
            return dt.format(FORMATO_DATA_HORA);
        } catch (Exception e) {
            return v;
        }
    }
}