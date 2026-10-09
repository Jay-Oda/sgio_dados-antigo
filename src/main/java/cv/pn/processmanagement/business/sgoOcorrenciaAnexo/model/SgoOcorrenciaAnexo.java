package cv.pn.processmanagement.business.sgoOcorrenciaAnexo.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_ocorrencia_anexo")
public class SgoOcorrenciaAnexo extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Column(name = "NOME_FILE")
    private String nomeFile;

    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;

    @Column(name = "ID_USER_ANEXO")
    private String idUserAnexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public String getIdUserAnexo() {
        return idUserAnexo;
    }

    public void setIdUserAnexo(String idUserAnexo) {
        this.idUserAnexo = idUserAnexo;
    }
}
