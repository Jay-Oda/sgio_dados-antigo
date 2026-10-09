package cv.pn.processmanagement.sgoPecaPessoa.dto;


public class SgoPecaPessoaDto {

    private Long oracleId;


    private String condicao;


    private String caracteristica;


    private Long idProcessoPeca;


    private Long idPessoa;


    private Long idProcesso;


    private Long idInstrucao;

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
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
