package cv.pn.processmanagement.business.comarca.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comarca")
public class Comarca extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;
    @Column(name = "RESPONSAVEL")
    private String responsavel;
    @Column(name = "LOCALIZACAO")
    private String localizacao;
    @Column(name = "CONTACTO")
    private String contacto;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "ID_SIG")
    private Long idSig;
    @Column(name = "ID_UNIDADE")
    private Long idUnidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Long getIdSig() {
        return idSig;
    }

    public void setIdSig(Long idSig) {
        this.idSig = idSig;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }
}
