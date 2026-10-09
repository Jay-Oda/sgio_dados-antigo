package cv.pn.processmanagement.sgoPecaPessoa.model;

import javax.persistence.*;


@Entity
@Table(name = "sgo_peca_pessoa")
public class SgoPecaPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oracle_id", unique = true)
    private Long oracleId;

    @Column(name = "condicao")
    private String condicao;

    @Column(name = "caracteristica")
    private String caracteristica;

    @Column(name = "id_processo_peca")
    private Long idProcessoPeca;

    @Column(name = "id_pessoa")
    private Long idPessoa;

    @Column(name = "id_processo")
    private Long idProcesso;

    @Column(name = "id_instrucao")
    private Long idInstrucao;

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Long getIdProcessoPeca() {
        return idProcessoPeca;
    }

    public void setIdProcessoPeca(Long idProcessoPeca) {
        this.idProcessoPeca = idProcessoPeca;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Long getIdInstrucao() {
        return idInstrucao;
    }

    public void setIdInstrucao(Long idInstrucao) {
        this.idInstrucao = idInstrucao;
    }
}
