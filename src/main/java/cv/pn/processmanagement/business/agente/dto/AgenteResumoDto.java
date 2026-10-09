package cv.pn.processmanagement.business.agente.dto;

public class AgenteResumoDto {

    private String id;
    private Long idNacional;
    private String numero;
    private String nome;
    private String categoria;

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
}
