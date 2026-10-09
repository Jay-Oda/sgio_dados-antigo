package cv.pn.processmanagement.business.sgoOcorrenciaParentesco.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_ocorrencia_parentesco")
public class SgoOcorrenciaParentesco extends CommonsAttributes {

    @Column(name = "ID_PARENTESCO")
    private String idParentesco;

    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;

    @Column(name = "ID_PESSOA1")
    private Long idPessoa1;

    @Column(name = "ID_PESSOA2")
    private Long idPessoa2;

    public String getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(String idParentesco) {
        this.idParentesco = idParentesco;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdPessoa1() {
        return idPessoa1;
    }

    public void setIdPessoa1(Long idPessoa1) {
        this.idPessoa1 = idPessoa1;
    }

    public Long getIdPessoa2() {
        return idPessoa2;
    }

    public void setIdPessoa2(Long idPessoa2) {
        this.idPessoa2 = idPessoa2;
    }
}
