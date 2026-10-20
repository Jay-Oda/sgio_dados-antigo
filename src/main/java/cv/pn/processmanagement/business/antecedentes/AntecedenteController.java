package cv.pn.processmanagement.business.antecedentes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Antecedentes da pessoa no SIGO antigo.
 *
 * <p>Padrão seguido por todos os endpoints deste controlador:</p>
 * <ul>
 *   <li>base fixa em {@code /api/sigo/v1/pessoa} (declarada só no {@code @RequestMapping},
 *       todos os {@code @GetMapping} começam com "/");</li>
 *   <li>os filtros de texto (nome, pai, mãe, alcunha, data) vão sempre na query string,
 *       com os mesmos nomes usados até agora;</li>
 *   <li>o número da ocorrência (ex.: {@code 14522/2024/EPPP}) vai sempre em
 *       {@code ?numOcorrencia=} porque contém "/" e por isso não cabe no path.
 *       O segmento {@code {numero}} que ainda aparece em alguns paths é apenas marcador de
 *       compatibilidade com os chamadores antigos: não é parâmetro e é ignorado.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/sigo/v1/pessoa")
@Tag(name = "Antecedentes", description = "Antecedentes da pessoa no SIGO antigo")
public class AntecedenteController {

    private static final String DESC_NUM_OCORRENCIA =
            "Número da ocorrência (ex.: 14522/2024/EPPP)";

    private static final String DESC_AUTO =
            "Filtro por tipo de auto";

    private final IAntecedenteService iAntecedenteService;
    private final AntecedentesPessoaCache antecedentesPessoaCache;

    public AntecedenteController(IAntecedenteService iAntecedenteService,
                                 AntecedentesPessoaCache antecedentesPessoaCache) {
        this.iAntecedenteService = iAntecedenteService;
        this.antecedentesPessoaCache = antecedentesPessoaCache;
    }

    /**
     * Antecedentes no SIGO antigo de uma pessoa vinda do SIGOIC/RNI. O nome, o nome do pai e o nome da mãe
     * são comparados sem acentos, maiúsculas, espaços repetidos nem ordem dos apelidos (ver AntecedentesPessoaCache).
     */
    @GetMapping("/antecedentes")
    @Operation(summary = "Antecedentes por nome e filiação (cache do SIGO antigo)")
    public ResponseEntity<?> antecedentesPorFiliacao(
            @Parameter(description = "Nome da pessoa") @RequestParam String nome,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento (ex.: 2002-01-22); não conta na comparação")
            @RequestParam(required = false) String dataNascimento) {
        return ResponseEntity.ok(antecedentesPessoaCache.pesquisar(nome, pai, mae, dataNascimento));
    }

    @GetMapping("/antecedentes/cache")
    @Operation(summary = "Estado da cache de antecedentes do SIGO antigo")
    public ResponseEntity<?> estadoCacheAntecedentes() {
        return ResponseEntity.ok(antecedentesPessoaCache.estado());
    }

    @GetMapping("/antecedentes/{oracleId}")
    @Operation(summary = "Antecedentes de uma pessoa pelo oracleId")
    public ResponseEntity<?> consultar(
            @Parameter(description = "Identificador Oracle da pessoa") @PathVariable Long oracleId,
            @Parameter(description = DESC_AUTO, schema = @Schema(type = "string",
                    allowableValues = {"Auto de Denúncia", "Auto de Notícia", "Auto de Detenção",
                            "Auto de Denúncia VBG", "Auto de Identificação"}))
            @RequestParam(required = false) String auto) {
        return ResponseEntity.ok(iAntecedenteService.consultarAntecedentes(oracleId, auto));
    }

    @GetMapping("/busca/antecidente/{nome}")
    @Operation(summary = "Antecedentes por nome, com filiação opcional")
    public ResponseEntity<?> buscarPorNome(
            @Parameter(description = "Nome da pessoa no path") @PathVariable("nome") String nomePath,
            @Parameter(description = "Nome da pessoa na query; quando vem preenchido, tem prioridade sobre o path")
            @RequestParam(value = "nome", required = false) String nomeQuery,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento no formato YYYY-MM-DD") @RequestParam(required = false) String dataNascimento) {
        return ResponseEntity.ok(iAntecedenteService.buscarAntecedentesPorNome(
                primeiroNaoVazio(nomeQuery, nomePath), pai, mae, dataNascimento));
    }

    @GetMapping({"/ocorrencia", "/ocorrencia/{numero}"})
    @Operation(summary = "Ocorrências da pessoa pelo número da ocorrência")
    public ResponseEntity<?> pesquisarPorNumOcorrencia(
            @Parameter(description = DESC_NUM_OCORRENCIA) @RequestParam(value = "numOcorrencia") String numOcorrencia) {
        return ResponseEntity.ok(iAntecedenteService.pesquisarPorNumOcorrencia(numOcorrencia));
    }

    @GetMapping("/ocorrencia/{numero}/detalhe")
    @Operation(summary = "Detalhe da ocorrência pelo número da ocorrência")
    public ResponseEntity<?> detalharOcorrencia(
            @Parameter(description = DESC_NUM_OCORRENCIA) @RequestParam(value = "numOcorrencia") String numOcorrencia) {
        return ResponseEntity.ok(iAntecedenteService.detalharOcorrencia(numOcorrencia));
    }

    @GetMapping("/pesquisa/{nome}")
    @Operation(summary = "Pesquisa de pessoa completa (nome, alcunha, filiação e data)")
    public ResponseEntity<?> pesquisarPessoaCompleta(
            @Parameter(description = "Nome da pessoa no path") @PathVariable("nome") String nomePath,
            @Parameter(description = "Nome da pessoa na query; quando vem preenchido, tem prioridade sobre o path")
            @RequestParam(value = "nome", required = false) String nomeQuery,
            @Parameter(description = "Alcunha") @RequestParam(required = false) String alcunha,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento no formato YYYY-MM-DD (ex.: 2026-01-20)")
            @RequestParam(required = false) String dataNascimento) {
        return ResponseEntity.ok(iAntecedenteService.pesquisarPessoaCompleta(
                primeiroNaoVazio(nomeQuery, nomePath), alcunha, pai, mae, dataNascimento));
    }

    /** Query primeiro (é o parâmetro obrigatório de sempre); o path serve de recurso. */
    private static String primeiroNaoVazio(String query, String path) {
        return (query != null && !query.isBlank()) ? query : path;
    }

    /*
     * Endpoints antigos desligados (mantidos para referência):
     *
     *   GET  /pesquisa/{nome}/antecidente   -> iAntecedenteService.pesquisarPessoas(req)
     *   GET  /pesquisa/antecedentes/filtro/{nome} -> iAntecedenteService.pesquisarAntecedentes(...)
     *   POST /pesquisa/{nome}               -> iAntecedenteService.pesquisarPessoas(req)
     *   GET  /todas                         -> iAntecedenteService.listarTodasPessoas(page, size, auto)
     *   GET  /{oracleId}                    -> iAntecedenteService.detalharPessoa(oracleId, auto)
     */
}
