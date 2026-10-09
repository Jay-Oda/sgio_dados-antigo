package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.dto;

public class SgoHistoricoVitimaVbgDTO {

    private Long id;
    private Long idOcorrencia;
    private Long idOcorrenciasSistem;
    private String numeroAuto;
    private String esquadra;
    private String data;
    private Long idTpDespacho;
    private String mesmoAutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdOcorrenciasSistem() {
        return idOcorrenciasSistem;
    }

    public void setIdOcorrenciasSistem(Long idOcorrenciasSistem) {
        this.idOcorrenciasSistem = idOcorrenciasSistem;
    }

    public String getNumeroAuto() {
        return numeroAuto;
    }

    public void setNumeroAuto(String numeroAuto) {
        this.numeroAuto = numeroAuto;
    }

    public String getEsquadra() {
        return esquadra;
    }

    public void setEsquadra(String esquadra) {
        this.esquadra = esquadra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getIdTpDespacho() {
        return idTpDespacho;
    }

    public void setIdTpDespacho(Long idTpDespacho) {
        this.idTpDespacho = idTpDespacho;
    }

    public String getMesmoAutor() {
        return mesmoAutor;
    }

    public void setMesmoAutor(String mesmoAutor) {
        this.mesmoAutor = mesmoAutor;
    }
}
