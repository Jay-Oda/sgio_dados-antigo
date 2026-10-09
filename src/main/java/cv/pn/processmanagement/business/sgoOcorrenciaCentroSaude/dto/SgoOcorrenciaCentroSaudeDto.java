package cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.dto;


public class SgoOcorrenciaCentroSaudeDto {

    private String acompanhamento;

    private Long idCentros;

    private Long idOcorrencia;

    public String getAcompanhamento() {
        return acompanhamento;
    }

    public void setAcompanhamento(String acompanhamento) {
        this.acompanhamento = acompanhamento;
    }

    public Long getIdCentros() {
        return idCentros;
    }

    public void setIdCentros(Long idCentros) {
        this.idCentros = idCentros;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }
}
