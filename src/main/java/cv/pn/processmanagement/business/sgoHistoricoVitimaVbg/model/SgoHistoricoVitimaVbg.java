package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_historico_vitima_vbg")
public class SgoHistoricoVitimaVbg {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "id_ocorrencia")
    private Long idOcorrencia;

    @Column(name = "id_ocorrencias_sistem")
    private Long idOcorrenciasSistem;

    @Column(name = "numero_auto")
    private String numeroAuto;

    @Column(name = "esquadra")
    private String esquadra;

    @Column(name = "data")
    private String data;

    @Column(name = "id_tp_desfecho")
    private Long idTpDespacho;

    @Column(name = "mesmo_autor")
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
