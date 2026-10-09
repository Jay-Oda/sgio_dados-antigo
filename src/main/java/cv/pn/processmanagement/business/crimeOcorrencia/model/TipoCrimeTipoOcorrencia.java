package cv.pn.processmanagement.business.crimeOcorrencia.model;




import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TP_crime_TP_ocorrencia")
public class TipoCrimeTipoOcorrencia {

    @Id
    @Column(name = "ID")   // vem do oracle
    private Long id;

    @Column(name = "ID_TIPO_CRIME")
    private Long idTipoCrime;

    @Column(name = "ID_TP_OCORRENCIA")
    private Long idTpOcorrencia;

    @Column(name = "USER_CREATE", nullable = false)
    private String userCreate;

    @Column(name = "DATE_CREATE", nullable = false)
    private LocalDateTime dateCreate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoCrime() {
        return idTipoCrime;
    }

    public void setIdTipoCrime(Long idTipoCrime) {
        this.idTipoCrime = idTipoCrime;
    }

    public Long getIdTpOcorrencia() {
        return idTpOcorrencia;
    }

    public void setIdTpOcorrencia(Long idTpOcorrencia) {
        this.idTpOcorrencia = idTpOcorrencia;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
