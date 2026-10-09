package cv.pn.processmanagement.business.crimeOcorrencia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoCrimeTipoOcorrenciaDto {

    private Long id; // ID da tabela Oracle
    @JsonProperty("id_tipo_crime")
    private Long idTipoCrime;
    @JsonProperty("id_tp_ocorrencia")
    private Long idTpOcorrencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoCrime() {
        return idTipoCrime;
    }

    public void setIdTipoCrime(Long idTipoCrime) {
        this.idTipoCrime = idTipoCrime;
    }

    public Long getIdTpOcorrencia() {
        return idTpOcorrencia;
    }

    public void setIdTpOcorrencia(Long idTpOcorrencia) {
        this.idTpOcorrencia = idTpOcorrencia;
    }
}
