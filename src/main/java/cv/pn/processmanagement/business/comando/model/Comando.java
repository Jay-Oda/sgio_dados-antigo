package cv.pn.processmanagement.business.comando.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "comando")
public class Comando extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;

    @Column(name = "COMANDANTE")
    private String comandante;

    @Column(name = "LOCALIZACAO")
    private String localizacao;

    @Column(name = "CONTACTO")
    private String contacto;

    @Column(name = "ID_NACIONAL")
    private Number id_nacional;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComandante() {
        return comandante;
    }

    public void setComandante(String comandante) {
        this.comandante = comandante;
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

    public Number getId_nacional() {
        return id_nacional;
    }

    public void setId_nacional(Number id_nacional) {
        this.id_nacional = id_nacional;
    }
}
