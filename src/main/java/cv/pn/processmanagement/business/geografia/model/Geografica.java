package cv.pn.processmanagement.business.geografia.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "glb_geografica")
public class Geografica extends CommonsAttributes {

    @Column(name = "NOME")
    private String nome;
    @Column(name = "ZONA")
    private String zona;
    @Column(name = "FREGUESIA")
    private String freguesia;
    @Column(name = "CONSELHO")
    private String concelho;
    @Column(name = "ILHA")
    private String ilha;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "GEOGRAFIA_ID")
    private Long geografia_id;
    @Column(name = "NIVEL_DETALHE")
    private String nivel_detalhe;
    @Column(name = "NACIONALIDADE")
    private String nacionalidade;
    @Column(name = "NOME_OFICIAL")
    private String nome_oficial;
    @Column(name = "FLAG_ALTER")
    private String flag_alter;
    @Column(name = "NOME_NORM")
    private String nome_norm;
    @Column(name = "TP_GEOG_CD")
    private String tp_geog_cd;
    @Column(name = "FLG_SITUADO")
    private String flg_situacao;
    @Column(name = "CODIGO_INE")
    private String codigo_ine;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "MEIO")
    private String meio;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFreguesia() {
        return freguesia;
    }

    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    public String getConcelho() {
        return concelho;
    }

    public void setConcelho(String concelho) {
        this.concelho = concelho;
    }

    public String getIlha() {
        return ilha;
    }

    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Long getGeografia_id() {
        return geografia_id;
    }

    public void setGeografia_id(Long geografia_id) {
        this.geografia_id = geografia_id;
    }

    public String getNivel_detalhe() {
        return nivel_detalhe;
    }

    public void setNivel_detalhe(String nivel_detalhe) {
        this.nivel_detalhe = nivel_detalhe;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNome_oficial() {
        return nome_oficial;
    }

    public void setNome_oficial(String nome_oficial) {
        this.nome_oficial = nome_oficial;
    }

    public String getFlag_alter() {
        return flag_alter;
    }

    public void setFlag_alter(String flag_alter) {
        this.flag_alter = flag_alter;
    }

    public String getNome_norm() {
        return nome_norm;
    }

    public void setNome_norm(String nome_norm) {
        this.nome_norm = nome_norm;
    }

    public String getTp_geog_cd() {
        return tp_geog_cd;
    }

    public void setTp_geog_cd(String tp_geog_cd) {
        this.tp_geog_cd = tp_geog_cd;
    }

    public String getFlg_situacao() {
        return flg_situacao;
    }

    public void setFlg_situacao(String flg_situacao) {
        this.flg_situacao = flg_situacao;
    }

    public String getCodigo_ine() {
        return codigo_ine;
    }

    public void setCodigo_ine(String codigo_ine) {
        this.codigo_ine = codigo_ine;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getMeio() {
        return meio;
    }

    public void setMeio(String meio) {
        this.meio = meio;
    }
}
