package cv.pn.processmanagement.business.sgoOcorrenciaRisco.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "sgo_ocorrencia_risco")
public class SgoOcorrenciaRisco extends CommonsAttributes {

    @Column(name = "ID_OCORRENCIA")
    private Long idOcorrencia;

    @Column(name = "ID_QUISTIONARIO")
    private Long idQuistionario;

    @Column(name = "RESPOSTA")
    private String resposta;

    @Column(name = "PONTUACAO")
    private Long pontuacao;

    @Column(name = "QUANTIDADE")
    private Long qualidade;

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Long getIdQuistionario() {
        return idQuistionario;
    }

    public void setIdQuistionario(Long idQuistionario) {
        this.idQuistionario = idQuistionario;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Long getQualidade() {
        return qualidade;
    }

    public void setQualidade(Long qualidade) {
        this.qualidade = qualidade;
    }
}
