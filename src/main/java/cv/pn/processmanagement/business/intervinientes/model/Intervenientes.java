package cv.pn.processmanagement.business.intervinientes.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "intervinientes")
public class Intervenientes extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;
    @Column(name = "ID_AUTO")
    private String id_auto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId_auto() {
        return id_auto;
    }

    public void setId_auto(String id_auto) {
        this.id_auto = id_auto;
    }
}
