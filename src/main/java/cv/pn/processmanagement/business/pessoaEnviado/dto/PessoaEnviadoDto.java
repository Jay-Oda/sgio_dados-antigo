package cv.pn.processmanagement.business.pessoaEnviado.dto;

public class PessoaEnviadoDto {

    private Long idOcorrencia;
    private Long idDe;
    private Long idPara;
    private String data;
    private String descricao;

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdDe() {
        return idDe;
    }

    public void setIdDe(Long idDe) {
        this.idDe = idDe;
    }

    public Long getIdPara() {
        return idPara;
    }

    public void setIdPara(Long idPara) {
        this.idPara = idPara;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
