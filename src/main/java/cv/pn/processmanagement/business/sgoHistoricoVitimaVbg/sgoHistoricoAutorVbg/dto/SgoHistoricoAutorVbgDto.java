package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.dto;


public class SgoHistoricoAutorVbgDto {

    private Long id;

    private Long idOcorrencia;

    private Long idOcorrenciasSistem;

    private String numeroAuto;

    private String data;

    private String mesmoVitima;

    private String tipoViolencia;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMesmoVitima() {
        return mesmoVitima;
    }

    public void setMesmoVitima(String mesmoVitima) {
        this.mesmoVitima = mesmoVitima;
    }

    public String getTipoViolencia() {
        return tipoViolencia;
    }

    public void setTipoViolencia(String tipoViolencia) {
        this.tipoViolencia = tipoViolencia;
    }
}
