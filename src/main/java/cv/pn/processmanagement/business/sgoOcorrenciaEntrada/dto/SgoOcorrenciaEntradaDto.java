package cv.pn.processmanagement.business.sgoOcorrenciaEntrada.dto;


public class SgoOcorrenciaEntradaDto {


    private Long idEntrada;

    private Long idTpOcorrencia;

    private String situacao;

    private String especificidade;

    public Long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Long getIdTpOcorrencia() {
        return idTpOcorrencia;
    }

    public void setIdTpOcorrencia(Long idTpOcorrencia) {
        this.idTpOcorrencia = idTpOcorrencia;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getEspecificidade() {
        return especificidade;
    }

    public void setEspecificidade(String especificidade) {
        this.especificidade = especificidade;
    }
}
