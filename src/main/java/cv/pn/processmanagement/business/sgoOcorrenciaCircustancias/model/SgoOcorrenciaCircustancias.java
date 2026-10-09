package cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_ocorrencia_circustancias")
public class SgoOcorrenciaCircustancias extends CommonsAttributes {

    @Column(name = "CIRCUSTANCIA")
    private String circustancia;

    @Column(name = "RESPOSTA")
    private String resposta;

    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;

    public String getCircustancia() {
        return circustancia;
    }

    public void setCircustancia(String circustancia) {
        this.circustancia = circustancia;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }
}
