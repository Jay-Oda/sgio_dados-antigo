package cv.pn.processmanagement.business.sgoOcorrenciaRisco.dto;


public class SgoOcorrenciaRiscoDto {

    private Long idOcorrencia;

    private Long idQuistionario;

    private String resposta;

    private Long pontuacao;

    private Long quantidade;

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdQuistionario() {
        return idQuistionario;
    }

    public void setIdQuistionario(Long idQuistionario) {
        this.idQuistionario = idQuistionario;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
