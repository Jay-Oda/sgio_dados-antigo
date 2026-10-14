package cv.pn.processmanagement.business.ocorrencia.controller;

import cv.pn.processmanagement.business.ocorrencia.services.IOcorrenciaNacionalService;
import cv.pn.processmanagement.integrations.pdf.PdfSigoAntigoService;
import cv.pn.processmanagement.utilities.APIResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigo/v1")
public class OcorrenciaNacionalController {

    private final IOcorrenciaNacionalService ocorrenciaNacionalService;
    private final PdfSigoAntigoService pdfSigoAntigoService;

    public OcorrenciaNacionalController(IOcorrenciaNacionalService ocorrenciaNacionalService,
                                        PdfSigoAntigoService pdfSigoAntigoService) {
        this.ocorrenciaNacionalService = ocorrenciaNacionalService;
        this.pdfSigoAntigoService = pdfSigoAntigoService;
    }

    @GetMapping("/Ocorrencia")
    public ResponseEntity<APIResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) String numOcorrencia,
            @RequestParam(required = false) String tpAuto,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam(required = false) String natureza,
            @RequestParam(required = false) String tipologia,
            // Nome da tipologia escolhida no SIGOIC: aceita as tipologias do SIGO antigo com nome semelhante
            @RequestParam(required = false) String tipologiaSemelhante,
            @RequestParam(required = false) String enquadramento,
            @RequestParam(required = false) Long comando,
            @RequestParam(required = false) Long unidade,
            @RequestParam(required = false) String comandoSigla,
            @RequestParam(required = false) String unidadeSigla,
            // "Nome / Alcunha do Interveniente": como no SIGOIC, ocorrências com algum interveniente cujo nome
            // ou alcunha contenha este texto (sem acentos nem maiúsculas)
            @RequestParam(required = false) String interveniente,
            @RequestParam(required = false) Integer offset) {

        return ResponseEntity.ok(ocorrenciaNacionalService.listarOcorrencias(
                page, size, numOcorrencia, tpAuto, estado, dataInicio, dataFim,
                natureza, tipologia, tipologiaSemelhante, enquadramento, comando, unidade,
                comandoSigla, unidadeSigla, interveniente, offset));
    }

    @GetMapping("/Ocorrencia/opcoes")
    public ResponseEntity<?> opcoes() {
        return ResponseEntity.ok(ocorrenciaNacionalService.obterOpcoes());
    }

    @GetMapping("/Ocorrencia/cache")
    public ResponseEntity<?> estadoCache() {
        return ResponseEntity.ok(ocorrenciaNacionalService.estadoCache());
    }

    @GetMapping("/Ocorrencia/historico")
    public ResponseEntity<APIResponse> historico(@RequestParam String numOcorrencia) {
        return ResponseEntity.ok(ocorrenciaNacionalService.obterHistorico(numOcorrencia));
    }

    /**
     * PDF do auto (Auto(s) Registado(s) na página de detalhes), gerado no GeneratorPdfJasperRiporter
     * (api.host-pdf); details[0].pdfContent em base64. O número segue em numOcorrencia porque contém "/".
     */
    @GetMapping("/Ocorrencia/{nunemro}/pdf")
    public ResponseEntity<APIResponse> pdf(
            @Parameter(description = "Não usado; o número vai em numOcorrencia (ex.: 0)") @PathVariable String nunemro,
            @Parameter(description = "Número da ocorrência (ex.: 3616/2025/EPB)") @RequestParam String numOcorrencia) {
        return ResponseEntity.ok(pdfSigoAntigoService.gerarPdfOcorrencia(numOcorrencia));
    }
}