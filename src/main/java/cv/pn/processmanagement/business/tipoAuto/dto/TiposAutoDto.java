package cv.pn.processmanagement.business.tipoAuto.dto;

import java.util.Date;

public class TiposAutoDto {

    private String id;
    private String nome;
    private String descricao;
    private String estrutura;
    private String estado;
    private Date date;
    private Long tipoObjeto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(String estrutura) {
        this.estrutura = estrutura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(Long tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }
}
