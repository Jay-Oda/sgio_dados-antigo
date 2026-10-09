package cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_ocorrencia_centro_saude")
public class SgoOcorrenciaCentroSaude extends CommonsAttributes {

    @Column(name = "ACOMPANHAMENTO")
    private String acompanhamento;

    @Column(name = "ID_CENTROS")
    private Long idCentros;

    @Column(name = "ID_OCORRENCIA")
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
