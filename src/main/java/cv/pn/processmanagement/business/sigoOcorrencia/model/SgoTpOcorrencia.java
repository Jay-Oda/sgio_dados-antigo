package cv.pn.processmanagement.business.sigoOcorrencia.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "sgo_tp_ocorrencia")
public class SgoTpOcorrencia extends CommonsAttributes {

    @Column(name = "oracle_id", unique = true)
    private Long oracleId;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "NATURESA_CRIME")
    private String naturesaCrime;

    @Column(name = "ESPECIFICACAO")
    private String especificacao;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "ID_ENQ_LEG")
    private Long idEnqLeg;

    @Column(name = "ID_PAI")
    private Long idPai;

    @Column(name = "ARTIGO")
    private String artigo;

    @Column(name = "ESQUADRA")
    private String esquadra;

    @Column(name = "FISCAL")
    private String fiscal;

    @Column(name = "MARITIMO")
    private String maritimo;

    @Column(name = "TRANSITO")
    private String transito;

    @Column(name = "DEF")
    private String def;

    @Column(name = "ENQUADRAMENTO")
    private String enquadramento;

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
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

    public String getNaturesaCrime() {
        return naturesaCrime;
    }

    public void setNaturesaCrime(String naturesaCrime) {
        this.naturesaCrime = naturesaCrime;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdEnqLeg() {
        return idEnqLeg;
    }

    public void setIdEnqLeg(Long idEnqLeg) {
        this.idEnqLeg = idEnqLeg;
    }

    public Long getIdPai() {
        return idPai;
    }

    public void setIdPai(Long idPai) {
        this.idPai = idPai;
    }

    public String getArtigo() {
        return artigo;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public String getEsquadra() {
        return esquadra;
    }

    public void setEsquadra(String esquadra) {
        this.esquadra = esquadra;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public String getMaritimo() {
        return maritimo;
    }

    public void setMaritimo(String maritimo) {
        this.maritimo = maritimo;
    }

    public String getTransito() {
        return transito;
    }

    public void setTransito(String transito) {
        this.transito = transito;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getEnquadramento() {
        return enquadramento;
    }

    public void setEnquadramento(String enquadramento) {
        this.enquadramento = enquadramento;
    }
}
