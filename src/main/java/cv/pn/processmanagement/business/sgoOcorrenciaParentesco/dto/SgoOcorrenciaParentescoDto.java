package cv.pn.processmanagement.business.sgoOcorrenciaParentesco.dto;


public class SgoOcorrenciaParentescoDto {

    private String idParentesco;

    private Long idOcorrencia;

    private Long idPessoa1;

    private Long idPessoa2;

    public String getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(String idParentesco) {
        this.idParentesco = idParentesco;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdPessoa1() {
        return idPessoa1;
    }

    public void setIdPessoa1(Long idPessoa1) {
        this.idPessoa1 = idPessoa1;
    }

    public Long getIdPessoa2() {
        return idPessoa2;
    }

    public void setIdPessoa2(Long idPessoa2) {
        this.idPessoa2 = idPessoa2;
    }
}
