package cv.pn.processmanagement.business.sgoOcorrenciaMeios.dto;


public class SgoOcorrenciaMeiosDto {

    private String descricao;

    private Long idMeios;

    private Long idOcorrencia;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdMeios() {
        return idMeios;
    }

    public void setIdMeios(Long idMeios) {
        this.idMeios = idMeios;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }
}
