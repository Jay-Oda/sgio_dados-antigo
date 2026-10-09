package cv.pn.processmanagement.business.sigoOcorrenciaPessoa.dto;




public class SgoOcorrenciaPessoaDto {

    private String condicao;
    private Long oracleId;
    private Long oracleIdPessoa;
    private Long oracleIdOcorrencia;

    private Long idPessoa;

    private Long idOcorrencia;

    private String caracteristica;

    private Long idAgente;

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
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

    public Long getOracleIdOcorrencia() {
        return oracleIdOcorrencia;
    }

    public void setOracleIdOcorrencia(Long oracleIdOcorrencia) {
        this.oracleIdOcorrencia = oracleIdOcorrencia;
    }

    public String getCaracteristica() {
        return caracteristica;
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
