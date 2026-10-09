package cv.pn.processmanagement.business.sgoOcorrenciaAnexos.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "sgo_ocorrencia_anexos")
public class SgoOcorrenciaAnexos extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ANEXO")
    private Blob anexo;

    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Column(name = "DATA")
    private Date data;

    @Column(name = "ID_USER")
    private Long idUser;

    @Column(name = "USER_EDITA")
    private Long userEdita;

    @Column(name = "ID_USER_ANEXO")
    private String idUserAnexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Blob getAnexo() {
        return anexo;
    }

    public void setAnexo(Blob anexo) {
        this.anexo = anexo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getUserEdita() {
        return userEdita;
    }

    public void setUserEdita(Long userEdita) {
        this.userEdita = userEdita;
    }

    public String getIdUserAnexo() {
        return idUserAnexo;
    }

    public void setIdUserAnexo(String idUserAnexo) {
        this.idUserAnexo = idUserAnexo;
    }
}
