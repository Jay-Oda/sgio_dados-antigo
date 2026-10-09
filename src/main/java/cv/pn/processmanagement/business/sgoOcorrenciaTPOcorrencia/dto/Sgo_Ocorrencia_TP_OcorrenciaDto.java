package cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.dto;

public class Sgo_Ocorrencia_TP_OcorrenciaDto {


    private Long idOcorrencia;

    private Long idTpOcorrencia;

    private String situacao;

    private String especificidade;

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
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
