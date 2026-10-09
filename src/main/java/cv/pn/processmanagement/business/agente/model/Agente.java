package cv.pn.processmanagement.business.agente.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "agente")
public class Agente extends CommonsAttributes {

    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Lob
    @Column(name = "FOTO")
    private byte [] foto;
    @Column(name = "ID_ESQUADRA")
    private Long idEsquadra;
    @Column(name = "MIME_TYPE")
    private String mimeType;
    @Column(name = "ID_COMANDO")
    private Long idComando;

    private String assinatura;
    private Long idNacional;
    private String assinaturas;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Long getIdEsquadra() {
        return idEsquadra;
    }

    public void setIdEsquadra(Long idEsquadra) {
        this.idEsquadra = idEsquadra;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getIdComando() {
        return idComando;
    }

    public void setIdComando(Long idComando) {
        this.idComando = idComando;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public Long getIdNacional() {
        return idNacional;
    }

    public void setIdNacional(Long idNacional) {
        this.idNacional = idNacional;
    }

    public String getAssinaturas() {
        return assinaturas;
    }

    public void setAssinaturas(String assinaturas) {
        this.assinaturas = assinaturas;
    }
}
