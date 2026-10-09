package cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.model;

import cv.pn.processmanagement.business.ocorrencia.model.SgoOcorrencia;
import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.*;

@Entity
@Table(name = "sgo_ocorrencia_tp_ocorrencia")
public class Sgo_Ocorrencia_TP_Ocorrencia extends CommonsAttributes {

    /*@Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;*/

   /* @Column(name = "ID_TP_OCORRENCIA")
    private Long idTpOcorrencia;*/

    @ManyToOne
    @JoinColumn(name = "id_ocorrencia", referencedColumnName = "oracle_id")
    private SgoOcorrencia ocorrencia;


    @ManyToOne
    @JoinColumn(name = "id_tp_ocorrencia", referencedColumnName = "oracle_id")
    private SgoTpOcorrencia tipoOcorrencia;


    @Column(name = "SITUACAO")
    private String situacao;

    @Column(name = "ESPECIFICIDADE")
    private String especificidade;

    public SgoOcorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(SgoOcorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public SgoTpOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(SgoTpOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
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
