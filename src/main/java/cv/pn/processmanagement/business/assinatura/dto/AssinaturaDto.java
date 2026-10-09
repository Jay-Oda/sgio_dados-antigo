package cv.pn.processmanagement.business.assinatura.dto;

import java.util.Date;

public class AssinaturaDto {

    private String nome;
    private String tipo_assinante;
    private Number id_ocorrencia;
    private String anexo;
    private String assina;
    private String sabe_assinar;
    private String categoria;
    private Long id_pessoa;
    private Long id_agente;
    private String assinatura;
    private Date date;
    private String user_r;
    private Long id_ocortrencia_pessoa;
    private Long mgid;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_assinante() {
        return tipo_assinante;
    }

    public void setTipo_assinante(String tipo_assinante) {
        this.tipo_assinante = tipo_assinante;
    }

    public Number getId_ocorrencia() {
        return id_ocorrencia;
    }

    public void setId_ocorrencia(Number id_ocorrencia) {
        this.id_ocorrencia = id_ocorrencia;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getAssina() {
        return assina;
    }

    public void setAssina(String assina) {
        this.assina = assina;
    }

    public String getSabe_assinar() {
        return sabe_assinar;
    }

    public void setSabe_assinar(String sabe_assinar) {
        this.sabe_assinar = sabe_assinar;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public Long getId_agente() {
        return id_agente;
    }

    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser_r() {
        return user_r;
    }

    public void setUser_r(String user_r) {
        this.user_r = user_r;
    }

    public Long getId_ocortrencia_pessoa() {
        return id_ocortrencia_pessoa;
    }

    public void setId_ocortrencia_pessoa(Long id_ocortrencia_pessoa) {
        this.id_ocortrencia_pessoa = id_ocortrencia_pessoa;
    }

    public Long getMgid() {
        return mgid;
    }

    public void setMgid(Long mgid) {
        this.mgid = mgid;
    }
}
