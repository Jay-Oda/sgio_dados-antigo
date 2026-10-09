package cv.pn.processmanagement.business.sigoOcorrenciaPessoa.model;


import javax.persistence.*;


@Entity
@Table(name = "sgo_ocorrencia_pessoa")
public class SgoOcorrenciaPessoa{

   @Id
   @Column(name = "id")
   private String id;


    @Column(name = "oracle_id")
    private Long oracleId;
    @Column(name = "ORACLE_ID_PESSOA")
    private Long oracleIdPessoa;
    @Column(name = "ORACLE_ID_OCORRENCIA")
    private Long oracleIdOcorrencia;

    @Column(name = "ID_PESSOA")
    private Long idPessoa;          // ID do Postgres da pessoa
    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;      // ID do Postgres da ocorrência
    @Column(name = "CONDICAO")
    private String condicao;


    @Column(name = "CARACTERISTICA")
    private String caracteristica;

    @Column(name = "ID_AGENTE")
    private Long idAgente;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
    }

    public Long getOracleIdPessoa() {
        return oracleIdPessoa;
    }

    public void setOracleIdPessoa(Long oracleIdPessoa) {
        this.oracleIdPessoa = oracleIdPessoa;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public Long getOracleIdOcorrencia() {
        return oracleIdOcorrencia;
    }

    public void setOracleIdOcorrencia(Long oracleIdOcorrencia) {
        this.oracleIdOcorrencia = oracleIdOcorrencia;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Long getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Long idAgente) {
        this.idAgente = idAgente;
    }
}
