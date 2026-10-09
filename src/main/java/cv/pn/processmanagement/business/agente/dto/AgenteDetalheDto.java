package cv.pn.processmanagement.business.agente.dto;

public class AgenteDetalheDto {

    private String id;
    private Long idNacional;
    private String numero;
    private String nome;
    private String categoria;
    private Long idEsquadra;
    private String nomeEsquadra;
    private Long idComando;
    private String nomeComando;
    private String mimeType;
    private String assinatura;
    private String assinaturas;
    private boolean temFoto;
    private String fotoBase64;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdNacional() {
        return idNacional;
    }

    public void setIdNacional(Long idNacional) {
        this.idNacional = idNacional;
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

    public Long getIdEsquadra() {
        return idEsquadra;
    }

    public void setIdEsquadra(Long idEsquadra) {
        this.idEsquadra = idEsquadra;
    }

    public String getNomeEsquadra() {
        return nomeEsquadra;
    }

    public void setNomeEsquadra(String nomeEsquadra) {
        this.nomeEsquadra = nomeEsquadra;
    }

    public Long getIdComando() {
        return idComando;
    }

    public void setIdComando(Long idComando) {
        this.idComando = idComando;
    }

    public String getNomeComando() {
        return nomeComando;
    }

    public void setNomeComando(String nomeComando) {
        this.nomeComando = nomeComando;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public String getAssinaturas() {
        return assinaturas;
    }

    public void setAssinaturas(String assinaturas) {
        this.assinaturas = assinaturas;
    }

    public boolean isTemFoto() {
        return temFoto;
    }

    public void setTemFoto(boolean temFoto) {
        this.temFoto = temFoto;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
