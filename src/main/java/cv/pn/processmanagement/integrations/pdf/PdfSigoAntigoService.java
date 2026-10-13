package cv.pn.processmanagement.integrations.pdf;

import com.fasterxml.jackson.databind.ObjectMapper;
import cv.pn.processmanagement.business.antecedentes.IAntecedenteService;
import cv.pn.processmanagement.utilities.APIResponse;
import cv.pn.processmanagement.utilities.ExternalApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Gera o PDF do auto de uma ocorrência do SIGO antigo no GeneratorPdfJasperRiporter, como faz o SIGOIC
 * (PdfServices): o backend monta os dados e chama {api.host-pdf}detalhe/ com "Basic {api.token-pdf}".
 * O SIGO antigo é só de consulta, por isso o PDF é devolvido em base64 e não é guardado.
 */
@Service
public class PdfSigoAntigoService {

    private final Logger LOG = LoggerFactory.getLogger(PdfSigoAntigoService.class);

    private final WebClient webFluxClient;
    private final ExternalApi externalApi;
    private final ObjectMapper objectMapper;
    private final IAntecedenteService iAntecedenteService;

    public PdfSigoAntigoService(@Qualifier("webClient") WebClient webFluxClient, ExternalApi externalApi,
                                ObjectMapper objectMapper, IAntecedenteService iAntecedenteService) {
        this.webFluxClient = webFluxClient;
        this.externalApi = externalApi;
        this.objectMapper = objectMapper;
        this.iAntecedenteService = iAntecedenteService;
    }

    /** Detalhe da ocorrência (o mesmo do GET /pessoa/ocorrencia/{n}/detalhe) enviado ao gerador; devolve details[0].pdfContent. */
    public APIResponse gerarPdfOcorrencia(String numOcorrencia) {

        if (externalApi.getHostPdf() == null || externalApi.getHostPdf().isBlank()) {
            return erro("Gerador de PDF não configurado (api.host-pdf).");
        }

        APIResponse detalhe = (APIResponse) iAntecedenteService.detalharOcorrencia(numOcorrencia);
        if (!detalhe.isStatus()) {
            return detalhe;
        }
        Object ocorrencia = primeiraOcorrencia(detalhe);
        if (ocorrencia == null) {
            return erro("0 registos de ocorrência");
        }

        try {
            // Serializado com o ObjectMapper da aplicação: o gerador recebe o mesmo JSON que a interface recebe no detalhe
            String corpo = objectMapper.writeValueAsString(ocorrencia);

            ResponseEntity<Map> responseEntity = webFluxClient.post()
                    .uri(externalApi.getHostPdf() + "detalhe/") // URL do endpoint de destino
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + externalApi.getTokenPdf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(corpo))
                    .retrieve()
                    .toEntity(Map.class)
                    .block();

            Map<?, ?> resposta = responseEntity != null ? responseEntity.getBody() : null;
            boolean status = resposta != null && Boolean.TRUE.equals(resposta.get("status"));
            Object details = resposta != null ? resposta.get("details") : null;
            LOG.info("[PdfSigoAntigoService] - PDF da ocorrência {}: status {}", numOcorrencia, status);

            if (!status || !(details instanceof List) || ((List<?>) details).isEmpty()) {
                return erro("Não foi possível gerar o documento desta ocorrência.");
            }

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText(String.valueOf(resposta.get("statusText")))
                    .setDetails(Collections.singletonList(((List<?>) details).get(0)))
                    .builder();

        } catch (Exception e) {
            LOG.error("[PdfSigoAntigoService] - Erro ao gerar o PDF da ocorrência {}: {}", numOcorrencia, e.getMessage());
            return erro("Serviço de geração de documentos indisponível.");
        }
    }

    // O detalhe vem como details = [ [ ocorrencia ] ]
    private Object primeiraOcorrencia(APIResponse detalhe) {
        List<Object> details = detalhe.getDetails();
        if (details == null || details.isEmpty()) return null;
        Object primeiro = details.get(0);
        if (primeiro instanceof List) {
            List<?> lista = (List<?>) primeiro;
            return lista.isEmpty() ? null : lista.get(0);
        }
        return primeiro;
    }

    private APIResponse erro(String mensagem) {
        return new APIResponse.buildAPIResponse()
                .setStatus(false)
                .setStatusText(mensagem)
                .builder();
    }
}
