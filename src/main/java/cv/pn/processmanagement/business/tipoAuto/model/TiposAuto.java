package cv.pn.processmanagement.business.tipoAuto.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tipo_auto")
public class TiposAuto extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "ESTRUTURA")
    private String estrutura;
    @Column(name = "ESTADO")
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date;
    @Column(name = "TIPO_OBJETO")
    private Long tipo_objeto;

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

    public String getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(String estrutura) {
        this.estrutura = estrutura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTipo_objeto() {
        return tipo_objeto;
    }

    public void setTipo_objeto(Long tipo_objeto) {
        this.tipo_objeto = tipo_objeto;
    }
}
