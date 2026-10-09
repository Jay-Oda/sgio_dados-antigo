package cv.pn.processmanagement.business.sgoOcorrenciaEntrada.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_tp_ocorrencia_entrada")
public class SgoOcorrenciaEntrada extends CommonsAttributes {

    @Column(name = "ID_ENTRADA")
    private Long idEntrada;

    @Column(name = "ID_TP_OCORRENCIA")
    private Long idTpOcorrencia;

    @Column(name = "SITUACAO")
    private String situacao;

    @Column(name = "ESPECIFICIDADE")
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
