package cv.pn.processmanagement.business.sgoOcorrenciaMeios.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_ocorrencia_meios")
public class SgoOcorrenciaMeios extends CommonsAttributes {

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ID_MEIOS")
    private Long idMeios;

    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdMeios() {
        return idMeios;
    }

    public void setIdMeios(Long idMeios) {
        this.idMeios = idMeios;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }
}
