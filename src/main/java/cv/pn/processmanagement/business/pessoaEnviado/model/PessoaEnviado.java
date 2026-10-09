package cv.pn.processmanagement.business.pessoaEnviado.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa_enviado")
public class PessoaEnviado extends CommonsAttributes {

    private Long idOcorrencia;

    @Column(name = "ID_DE")
    private Long idDe;

    @Column(name = "ID_PARA")
    private Long idPara;

    @Column(name = "DATA")
    private String data;

    @Column(name = "DESCRICAO")
    private String descricao;

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdDe() {
        return idDe;
    }

    public void setIdDe(Long idDe) {
        this.idDe = idDe;
    }

    public Long getIdPara() {
        return idPara;
    }

    public void setIdPara(Long idPara) {
        this.idPara = idPara;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
