package cv.pn.processmanagement.business.agente.dto;

import javax.persistence.Lob;

public class AgenteDto {

    private String id;
    private String numero;
    private String nome;
    private String categoria;
    @Lob
    private byte [] foto;
    private Long idEsquadra;
    private String mimeType;
    private Long idComando;
    private String assinatura;
    private Long idNacional;
    private String assinaturas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
