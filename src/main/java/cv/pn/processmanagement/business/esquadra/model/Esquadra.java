package cv.pn.processmanagement.business.esquadra.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "esquadra")

public class Esquadra extends CommonsAttributes {

    @Column(name = "oracle_id" , unique = true)
    private Long oracleId;

    @Column(name = "NOME")
    private String nome;
    @Column(name = "COMADANTE")
    private String comandante;
    @Column(name = "LOCALIZACAO")
    private String localizacao;
    @Column(name = "CONTACTO")
    private String contacto;
    @Column(name = "ID_COMANDO")
    private Long idComando;
    @Column(name = "COORDENADA")
    private String coordenada;
    @Column(name = "AREA")
    private String area;
    @Column(name = "COR_AREA")
    private String corArea;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "SELF_ID")
    private Long   selfId;
    @Column(name = "DATA_OCORRENCIA")
    private String dataOcorrencia;
    @Column(name = "LOCAL")
    private String local;
    @Column(name = "PONTO_REFERENCIA")
    private String pontoReferencia;
    @Column(name = "ID_COMARCA")
    private Long idComarca;
    @Column(name = "ID_SIG_COMARCA")
    private Long idSigComarca;
    @Column(name = "ID_UNIDADE_SIG")
    private Long idUnidadeSig;
    @Column(name = "ID_UNIDADE_IC")
    private Long idUnidadeIc;
    @Column(name = "ESPECIFICIDADE")
    private String especificidade;
    @Column(name = "TIPO_UNIDADE")
    private String tipoUnidade;
    @Column(name = "ID_CONCELHO")
    private Long idConcelho;
    @Column(name = "ASSINATURA_CMD")
    private String assinaturaCmd;
    @Column(name = "MIME_TYPE")
    private String mimeType;
    @Column(name = "TELEFONE_NOTIFICACAO1")
    private String telefoneNotificacao1;
    @Column(name = "TELEFONE_NOTIFICACAO2")
    private String telefoneNotificacao2;
    @Column(name = "EMAIL_NOTIFICACAO1")
    private String emailNotificacao1;
    @Column(name = "EMAIL_NOTIFICACAO2")
    private String emailNotificacao2;

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
    }

    public String getEmailNotificacao2() {
        return emailNotificacao2;
    }

    public void setEmailNotificacao2(String emailNotificacao2) {
        this.emailNotificacao2 = emailNotificacao2;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComandante() {
        return comandante;
    }

    public void setComandante(String comandante) {
        this.comandante = comandante;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Long getIdComando() {
        return idComando;
    }

    public void setIdComando(Long idComando) {
        this.idComando = idComando;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCorArea() {
        return corArea;
    }

    public void setCorArea(String corArea) {
        this.corArea = corArea;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Long getSelfId() {
        return selfId;
    }

    public void setSelfId(Long selfId) {
        this.selfId = selfId;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public Long getIdComarca() {
        return idComarca;
    }

    public void setIdComarca(Long idComarca) {
        this.idComarca = idComarca;
    }

    public Long getIdSigComarca() {
        return idSigComarca;
    }

    public void setIdSigComarca(Long idSigComarca) {
        this.idSigComarca = idSigComarca;
    }

    public Long getIdUnidadeSig() {
        return idUnidadeSig;
    }

    public void setIdUnidadeSig(Long idUnidadeSig) {
        this.idUnidadeSig = idUnidadeSig;
    }

    public Long getIdUnidadeIc() {
        return idUnidadeIc;
    }

    public void setIdUnidadeIc(Long idUnidadeIc) {
        this.idUnidadeIc = idUnidadeIc;
    }

    public String getEspecificidade() {
        return especificidade;
    }

    public void setEspecificidade(String especificidade) {
        this.especificidade = especificidade;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public Long getIdConcelho() {
        return idConcelho;
    }

    public void setIdConcelho(Long idConcelho) {
        this.idConcelho = idConcelho;
    }

    public String getAssinaturaCmd() {
        return assinaturaCmd;
    }

    public void setAssinaturaCmd(String assinaturaCmd) {
        this.assinaturaCmd = assinaturaCmd;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTelefoneNotificacao1() {
        return telefoneNotificacao1;
    }

    public void setTelefoneNotificacao1(String telefoneNotificacao1) {
        this.telefoneNotificacao1 = telefoneNotificacao1;
    }

    public String getTelefoneNotificacao2() {
        return telefoneNotificacao2;
    }

    public void setTelefoneNotificacao2(String telefoneNotificacao2) {
        this.telefoneNotificacao2 = telefoneNotificacao2;
    }

    public String getEmailNotificacao1() {
        return emailNotificacao1;
    }

    public void setEmailNotificacao1(String emailNotificacao1) {
        this.emailNotificacao1 = emailNotificacao1;
    }
}
