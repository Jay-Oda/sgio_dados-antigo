package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sgo_historico_autor_vbg")
public class SgoHistoricoAutorVbg {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "id_ocorrencia")
    private Long idOcorrencia;

    @Column(name = "id_ocorrencias_sistem")
    private Long idOcorrenciasSistem;

    @Column(name = "numero_auto")
    private String numeroAuto;

    @Column(name = "data")
    private String data;

    @Column(name = "mesmo_vitima")
    private String mesmoVitima;

    @Column(name = "tipo_violencia")
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
