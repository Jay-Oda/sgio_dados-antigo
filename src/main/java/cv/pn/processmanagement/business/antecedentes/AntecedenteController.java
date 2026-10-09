package cv.pn.processmanagement.business.antecedentes;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/sigo/v1/pessoa")
public class AntecedenteController {

    private final IAntecedenteService iAntecedenteService;
    private final AntecedentesPessoaCache antecedentesPessoaCache;

    public AntecedenteController(IAntecedenteService iAntecedenteService, AntecedentesPessoaCache antecedentesPessoaCache) {
        this.iAntecedenteService = iAntecedenteService;
        this.antecedentesPessoaCache = antecedentesPessoaCache;
    }

    /**
     * Antecedentes no SIGO antigo de uma pessoa vinda do SIGOIC/RNI. O nome, o nome do pai e o nome da mãe
     * são comparados sem acentos, maiúsculas, espaços repetidos nem ordem dos apelidos (ver AntecedentesPessoaCache).
     */
    @GetMapping("/antecedentes")
    public ResponseEntity<?> antecedentesPorFiliacao(
            @Parameter(description = "Nome da pessoa") @RequestParam String nome,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento (ex.: 2002-01-22); se as duas existirem, têm de ser iguais")
            @RequestParam(required = false) String dataNascimento) {
        return ResponseEntity.ok(antecedentesPessoaCache.pesquisar(nome, pai, mae, dataNascimento));
    }

    @GetMapping("/antecedentes/cache")
    public ResponseEntity<?> estadoCacheAntecedentes() {
        return ResponseEntity.ok(antecedentesPessoaCache.estado());
    }


    @GetMapping("/antecedentes/{oracleId}")
    public ResponseEntity<?> consultar(
            @PathVariable Long oracleId,
            @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string", allowableValues = {"Auto de Denúncia", "Auto de Notícia", "Auto de Detenção", "Auto de Denúncia VBG", "Auto de Identificação"}))
            @RequestParam(required = false) String auto) {
        return ResponseEntity.ok(iAntecedenteService.consultarAntecedentes(oracleId, auto));
    }

   /* @GetMapping("/pesquisa/{nome}/antecidente")
    public ResponseEntity<?> pesquisar(
            @Parameter(description = "Nome da pessoa") @RequestParam(required = false) String nome,
            @Parameter(description = "Alcunha") @RequestParam(required = false) String alcunha,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento no formato YYYY-MM-DD (ex.: 2026-01-20)") @RequestParam(required = false) String dataNascimento) {
        PessoaAntecedenteRequest req = new PessoaAntecedenteRequest();
        req.setNome(nome);
        req.setAlcunha(alcunha);
        req.setPai(pai);
        req.setMae(mae);
        req.setDtNascimento(dataNascimento);
        return ResponseEntity.ok(iAntecedenteService.pesquisarPessoas(req));
    }

    */

  /*  @GetMapping("/pesquisa/antecedentes/filtro/{nome}")
    public ResponseEntity<?> pesquisarAntecedentes(
            @Parameter(description = "Nome da pessoa") @RequestParam(required = false) String nome,
            @Parameter(description = "Alcunha") @RequestParam(required = false) String alcunha,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento no formato YYYY-MM-DD (ex.: 2026-01-20)") @RequestParam(required = false) String dataNascimento,
            @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string", allowableValues = {"Auto de Denúncia", "Auto de Notícia", "Auto de Detenção", "Auto de Denúncia VBG", "Auto de Identificação"})) @RequestParam(required = false) String auto) {
        return ResponseEntity.ok(
                iAntecedenteService.pesquisarAntecedentes(nome, alcunha, pai, mae, dataNascimento, auto));
    }
*/

    /* @PostMapping("/pesquisa/{nome}")
     public ResponseEntity<?> pesquisar(@RequestBody PessoaAntecedenteRequest req) {
         return ResponseEntity.ok(iAntecedenteService.pesquisarPessoas(req));
     }
/*
     @GetMapping("/todas")
     public ResponseEntity<?> todas(
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "100") int size,
             @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string", allowableValues = {"Auto de Denúncia", "Auto de Notícia", "Auto de Detenção", "Auto de Denúncia VBG", "Auto de Identificação"})) @RequestParam(required = false) String auto) {
         return ResponseEntity.ok(iAntecedenteService.listarTodasPessoas(page, size, auto));
     }

    /* @GetMapping("/{oracleId}")
     public ResponseEntity<?> detalhe(
             @PathVariable Long oracleId,
             @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string", allowableValues = {"Auto de Denúncia", "Auto de Notícia", "Auto de Detenção", "Auto de Denúncia VBG", "Auto de Identificação"})) @RequestParam(required = false) String auto) {
         return ResponseEntity.ok(iAntecedenteService.detalharPessoa(oracleId, auto));
     }
 */
    @GetMapping("/busca/antecidente/{nome}")
    public ResponseEntity<?> buscarPorNome(
            @Parameter(description = "Nome completo da pessoa (obrigatório)") @RequestParam String nome,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento no formato YYYY-MM-DD") @RequestParam(required = false) String dataNascimento) {
        return ResponseEntity.ok(iAntecedenteService.buscarAntecedentesPorNome(nome, pai, mae, dataNascimento));
    }

    @GetMapping("pesquisa/{nome}")
    public ResponseEntity<?> pesquisarPessoaCompleta(
            @PathVariable String nome,
            @Parameter(description = "Alcunha") @RequestParam(required = false) String alcunha,
            @Parameter(description = "Nome do pai") @RequestParam(required = false) String pai,
            @Parameter(description = "Nome da mãe") @RequestParam(required = false) String mae,
            @Parameter(description = "Data de nascimento no formato YYYY-MM-DD (ex.: 2026-01-20)") @RequestParam(required = false) String dataNascimento) {
        return ResponseEntity.ok(iAntecedenteService.pesquisarPessoaCompleta(nome, alcunha, pai, mae, dataNascimento));
    }

    @GetMapping("/ocorrencia/{nunemro}")
    public ResponseEntity<?> pesquisarPorNumOcorrencia(
            @Parameter(description = "Número da ocorrência (ex.: 14522/2024/EPPP)") @RequestParam String numOcorrencia) {
        return ResponseEntity.ok(iAntecedenteService.pesquisarPorNumOcorrencia(numOcorrencia));
    }

    @GetMapping("/ocorrencia/{nunemro}/detalhe")
    public ResponseEntity<?> detalharOcorrencia(
            @Parameter(description = "Número da ocorrência (ex.: 14522/2024/EPPP)") @RequestParam String numOcorrencia) {
        return ResponseEntity.ok(iAntecedenteService.detalharOcorrencia(numOcorrencia));
    }
}
