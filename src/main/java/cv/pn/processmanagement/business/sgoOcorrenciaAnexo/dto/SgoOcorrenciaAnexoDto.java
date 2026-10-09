package cv.pn.processmanagement.business.sgoOcorrenciaAnexo.dto;



public class SgoOcorrenciaAnexoDto {

    private String nome;

    private String mimeType;

    private String nomeFile;

    private Long idOcorrencia;

    private String idUserAnexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public String getIdUserAnexo() {
        return idUserAnexo;
    }

    public void setIdUserAnexo(String idUserAnexo) {
        this.idUserAnexo = idUserAnexo;
    }
}
