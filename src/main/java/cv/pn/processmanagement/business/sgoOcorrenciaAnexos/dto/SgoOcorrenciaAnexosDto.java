package cv.pn.processmanagement.business.sgoOcorrenciaAnexos.dto;


import java.sql.Blob;
import java.util.Date;

public class SgoOcorrenciaAnexosDto {

    private String nome;

    private String descricao;

    private Blob anexo;

    private Long idOcorrencia;

    private String mimeType;

    private Date data;

    private Long idUser;

    private Long userEdita;

    private String idUserAnexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Blob getAnexo() {
        return anexo;
    }

    public void setAnexo(Blob anexo) {
        this.anexo = anexo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getUserEdita() {
        return userEdita;
    }

    public void setUserEdita(Long userEdita) {
        this.userEdita = userEdita;
    }

    public String getIdUserAnexo() {
        return idUserAnexo;
    }

    public void setIdUserAnexo(String idUserAnexo) {
        this.idUserAnexo = idUserAnexo;
    }
}
