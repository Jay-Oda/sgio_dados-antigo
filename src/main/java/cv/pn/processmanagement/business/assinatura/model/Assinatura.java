package cv.pn.processmanagement.business.assinatura.model;


import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "assinatura")
public class Assinatura extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;
    @Column(name = "TIPO_ASSINANTE")
    private String tipo_assinante;
    @Column(name = "ID_OCORRENCIA")
    private Long id_ocorrencia;
    @Column(name = "ANEXO")
    private String anexo;
    @Column(name = "ASSINA")
    private String assina;
    @Column(name = "SABE_ASSINAR")
    private String sabe_assinar;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Column(name = "ID_PESSOA")
    private Long id_pessoa;
    @Column(name = "ID_AGENTE")
    private Long id_agente;
    @Column(name = "ASSINATURA")
    private String assinatura;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date;
    @Column(name = "USER_R")
    private String user_r;
    @Column(name = "ID_OCORRENCIA_PESSOA")
    private Long id_ocorrrencia_pessoa;
    @Column(name = "MG_ID")
    private Long mgid;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_assinante() {
        return tipo_assinante;
    }

    public void setTipo_assinante(String tipo_assinante) {
        this.tipo_assinante = tipo_assinante;
    }

    public Long getId_ocorrencia() {
        return id_ocorrencia;
    }

    public void setId_ocorrencia(Long id_ocorrencia) {
        this.id_ocorrencia = id_ocorrencia;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getAssina() {
        return assina;
    }

    public void setAssina(String assina) {
        this.assina = assina;
    }

    public String getSabe_assinar() {
        return sabe_assinar;
    }

    public void setSabe_assinar(String sabe_assinar) {
        this.sabe_assinar = sabe_assinar;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public Long getId_agente() {
        return id_agente;
    }

    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser_r() {
        return user_r;
    }

    public void setUser_r(String user_r) {
        this.user_r = user_r;
    }

    public Long getId_ocorrrencia_pessoa() {
        return id_ocorrrencia_pessoa;
    }

    public void setId_ocorrrencia_pessoa(Long id_ocorrrencia_pessoa) {
        this.id_ocorrrencia_pessoa = id_ocorrrencia_pessoa;
    }

    public Long getMgid() {
        return mgid;
    }

    public void setMgid(Long mgid) {
        this.mgid = mgid;
    }
}
