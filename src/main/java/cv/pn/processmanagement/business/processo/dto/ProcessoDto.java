package cv.pn.processmanagement.business.processo.dto;

import java.util.Date;

public class ProcessoDto {

    private String id;
    private String data;
    private String tipo_processo;
    private String estado;
    private String descricao_despacho;
    private Date data_registo;
    private String user_registo;
    private Long id_user_registo;
    private String data_fecho;
    private String user_fecho;
    private Long id_user_fecho;
    private String obs_fecho;
    private Long id_unidade;
    private String descricao_estado;
    private String numero_entrada;
    private Long contador;
    private Long id_ocorrencia;
    private Long id_processo_sij;
    private String num_ocorrencia;
    private String num_processo_sij;
    private Long id_unidade_origem;
    private Long id_comarca_origem;
    private String tipo_origem;
    private String fechar_processo;
    private Long id_entrada;
    private Long id_despacho;
    private String nome_procurador;
    private String prazo;
    private String folhas;
    private String descricao;
    private String obs_arquivo;
    private String enviado_averiguacao_sumaria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo_processo() {
        return tipo_processo;
    }

    public void setTipo_processo(String tipo_processo) {
        this.tipo_processo = tipo_processo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescricao_despacho() {
        return descricao_despacho;
    }

    public void setDescricao_despacho(String descricao_despacho) {
        this.descricao_despacho = descricao_despacho;
    }

    public Date getData_registo() {
        return data_registo;
    }

    public void setData_registo(Date data_registo) {
        this.data_registo = data_registo;
    }

    public String getUser_registo() {
        return user_registo;
    }

    public void setUser_registo(String user_registo) {
        this.user_registo = user_registo;
    }

    public Long getId_user_registo() {
        return id_user_registo;
    }

    public void setId_user_registo(Long id_user_registo) {
        this.id_user_registo = id_user_registo;
    }

    public String getData_fecho() {
        return data_fecho;
    }

    public void setData_fecho(String data_fecho) {
        this.data_fecho = data_fecho;
    }

    public String getUser_fecho() {
        return user_fecho;
    }

    public void setUser_fecho(String user_fecho) {
        this.user_fecho = user_fecho;
    }

    public Long getId_user_fecho() {
        return id_user_fecho;
    }

    public void setId_user_fecho(Long id_user_fecho) {
        this.id_user_fecho = id_user_fecho;
    }

    public String getObs_fecho() {
        return obs_fecho;
    }

    public void setObs_fecho(String obs_fecho) {
        this.obs_fecho = obs_fecho;
    }

    public Long getId_unidade() {
        return id_unidade;
    }

    public void setId_unidade(Long id_unidade) {
        this.id_unidade = id_unidade;
    }

    public String getDescricao_estado() {
        return descricao_estado;
    }

    public void setDescricao_estado(String descricao_estado) {
        this.descricao_estado = descricao_estado;
    }

    public String getNumero_entrada() {
        return numero_entrada;
    }

    public void setNumero_entrada(String numero_entrada) {
        this.numero_entrada = numero_entrada;
    }

    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }

    public Long getId_ocorrencia() {
        return id_ocorrencia;
    }

    public void setId_ocorrencia(Long id_ocorrencia) {
        this.id_ocorrencia = id_ocorrencia;
    }

    public Long getId_processo_sij() {
        return id_processo_sij;
    }

    public void setId_processo_sij(Long id_processo_sij) {
        this.id_processo_sij = id_processo_sij;
    }

    public String getNum_ocorrencia() {
        return num_ocorrencia;
    }

    public void setNum_ocorrencia(String num_ocorrencia) {
        this.num_ocorrencia = num_ocorrencia;
    }

    public String getNum_processo_sij() {
        return num_processo_sij;
    }

    public void setNum_processo_sij(String num_processo_sij) {
        this.num_processo_sij = num_processo_sij;
    }

    public Long getId_unidade_origem() {
        return id_unidade_origem;
    }

    public void setId_unidade_origem(Long id_unidade_origem) {
        this.id_unidade_origem = id_unidade_origem;
    }

    public Long getId_comarca_origem() {
        return id_comarca_origem;
    }

    public void setId_comarca_origem(Long id_comarca_origem) {
        this.id_comarca_origem = id_comarca_origem;
    }

    public String getTipo_origem() {
        return tipo_origem;
    }

    public void setTipo_origem(String tipo_origem) {
        this.tipo_origem = tipo_origem;
    }

    public String getFechar_processo() {
        return fechar_processo;
    }

    public void setFechar_processo(String fechar_processo) {
        this.fechar_processo = fechar_processo;
    }

    public Long getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(Long id_entrada) {
        this.id_entrada = id_entrada;
    }

    public Long getId_despacho() {
        return id_despacho;
    }

    public void setId_despacho(Long id_despacho) {
        this.id_despacho = id_despacho;
    }

    public String getNome_procurador() {
        return nome_procurador;
    }

    public void setNome_procurador(String nome_procurador) {
        this.nome_procurador = nome_procurador;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public String getFolhas() {
        return folhas;
    }

    public void setFolhas(String folhas) {
        this.folhas = folhas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObs_arquivo() {
        return obs_arquivo;
    }

    public void setObs_arquivo(String obs_arquivo) {
        this.obs_arquivo = obs_arquivo;
    }

    public String getEnviado_averiguacao_sumaria() {
        return enviado_averiguacao_sumaria;
    }

    public void setEnviado_averiguacao_sumaria(String enviado_averiguacao_sumaria) {
        this.enviado_averiguacao_sumaria = enviado_averiguacao_sumaria;
    }
}
