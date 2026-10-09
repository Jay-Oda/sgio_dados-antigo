package cv.pn.processmanagement.business.ocorrencia.model;

import cv.pn.processmanagement.business.esquadra.model.Esquadra;
import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "sgo_ocorrencia")
public class SgoOcorrencia extends CommonsAttributes {


    @Column(name = "NUM_OCORRENCIA")
    private String numOcorrencia;

    @Column(name = "DATA")
    private String data;

    @Column(name = "LOCAL")
    private Long local;

    @Column(name = "PONTO_REFERENCIA")
    private String pontoReferencia;

    @Column(name = "COORDENADA")
    private String coordenada;

    @Column(name = "OBSERVACAO")
    private String observacao;

   /* @Column(name = "ID_TP_OCORRENCIA")
    private Long idTpOcorrencia;*/

    @ManyToOne
    @JoinColumn(name = "id_tp_ocorrencia", referencedColumnName = "oracle_id")
    private SgoTpOcorrencia tipoOcorrencia;


    @Column(name = "ID_AGENTE")
    private Long idAgente;

    @Column(name = "ID_USER_REGISTO")
    private Long idUserRegisto;

    @Column(name = "DATA_REGISTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegisto;

    @Column(name = "ID_LOCAL_CRIME")
    private Long idLocalCrime;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TP_AUTO")
    private String tpAuto;

   /* @Column(name = "ID_ESQUADRA")
    private Long idEsquadra;*/

    @ManyToOne
    @JoinColumn(name = "id_esquadra", referencedColumnName = "oracle_id")
    private Esquadra esquadra;

    @Column(name = "CONTROL")
    private String control;

    @Column(name = "MOTIVO", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "CONTADOR")
    private Long contador;

    @Column(name = "ENVIA_ANALISE")
    private String enviaAnalise;

    @Column(name = "MOTIVO_PENDENCIA")
    private String motivoPendencia;

    @Column(name = "ID_TIPO_LOCAL")
    private Long idTipoLocal;

    @Column(name = "REENSIDENCIA")
    private String reensidencia;

    @Column(name = "HORA_ENTRADA")
    private String horaEntrada;

    @Column(name = "HORA_SAIDA")
    private String horaSaida;

    @Column(name = "ID_ESQUADRA_REGISTO")
    private Long idEsquadraRegisto;

    @Column(name = "OBS_ESQUADRA_ORIGEM")
    private String obsEsquadraOrigem;

    @Column(name = "DATA_ENVIADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnviado;

    @Column(name = "USER_ENVIADO")
    private Long userEnviado;

    @Column(name = "ID_DONO")
    private Long idDono;

    @Column(name = "ESQUADRA_DENUNCIA_VBG")
    private String esquadraDenuncia_vbg;

    @Column(name = "DATA_DENUNCIA_VBG")
    private String dataDenunciaVbg;

    @Column(name = "CONHECE_DESFECHO_VBG")
    private String conheceDesfechoVbg;

    @Column(name = "DESFECHO_VBG")
    private String desfechoVbg;

    @Column(name = "SOFREU_VBG_ANTES")
    private String sofreuVbgAntes;

    @Column(name = "ID_USER_EDITA")
    private String idUserEdita;

    @Column(name = "DATA_EDITA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datadita;

    @Column(name = "PRIVADO")
    private String privado;

    @Column(name = "QUESTIONARIO_RISCO_VBG")
    private String questionarioRiscoVbg;

    @Column(name = "MOTIVO_NAO_QUEST_RISCO_VBG")
    private String motivoNaoQuestRiscoVbg;

    @Column(name = "ID_COMANDO")
    private Long idComando;

    @Column(name = "DATA_ANALISE")
    private String dataAnalise;

    @Column(name = "ID_USER_ANALISE")
    private Long idUserAnalise;

    @Column(name = "USER_ANALISE")
    private String userAnalise;

    @Column(name = "TIPO_ANALISE")
    private String tipoAnalise;

    @Column(name = "OBS_ANALISE")
    private String obsAnalise;

    @Column(name = "DATA_DESPACHO")
    private String dataDespacho;

    @Column(name = "ID_USER_DESPACHO")
    private Long idUserDespacho;

    @Column(name = "USER_DESPACHO")
    private String userDespacho;

    @Column(name = "TIPO_DESPACHO")
    private String tipoDespacho;

    @Column(name = "OBS_DESPACHO")
    private String obsDespacho;

    @Column(name = "DATA_FECHO")
    private String dataFecho;

    @Column(name = "TIPO_FECHO")
    private String tipoFecho;

    @Column(name = "USER_FECHO")
    private String userFecho;

    @Column(name = "DESCRICAO_ETAPA")
    private String descricaoEtapa;

    @Column(name = "APLICAOU_QUESTIONARIO_VBG")
    private String aplicaouQuestionarioVbg;

    @Column(name = "MOTIVO_NAO_QUEST_VBG")
    private String motivoNaoQuestVbg;

    @Column(name = "ID_PROCESSO_SIJ")
    private String idProcessoSij;

    @Column(name = "NUM_PROCESSO_SIJ")
    private String numProcessoSij;

    @Column(name = "DATA_ENVIO_SIJ")
    private String dataEnvioSij;

    @Column(name = "MOTIVO_ARQUIVA")
    private String motivoArquiva;

    @Column(name = "DATA_ARQUIVA")
    private String dataArquiva;

    @Column(name = "USER_ARQUIVA")
    private String userArquiva;

    @Column(name = "DT_REGISTO_TIME")
    private String dtRegistoTime;

    @Column(name = "MGID")
    private String mgid;

    @Column(name = "oracle_id")
    private Long oracleId;

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
    }

    public SgoTpOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(SgoTpOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public String getNumOcorrencia() {
        return numOcorrencia;
    }

    public void setNumOcorrencia(String numOcorrencia) {
        this.numOcorrencia = numOcorrencia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getLocal() {
        return local;
    }

    public void setLocal(Long local) {
        this.local = local;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(String coordenada) {
        this.coordenada = coordenada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }



    public Long getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Long idAgente) {
        this.idAgente = idAgente;
    }

    public Long getIdUserRegisto() {
        return idUserRegisto;
    }

    public void setIdUserRegisto(Long idUserRegisto) {
        this.idUserRegisto = idUserRegisto;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public Long getIdLocalCrime() {
        return idLocalCrime;
    }

    public void setIdLocalCrime(Long idLocalCrime) {
        this.idLocalCrime = idLocalCrime;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTpAuto() {
        return tpAuto;
    }

    public void setTpAuto(String tpAuto) {
        this.tpAuto = tpAuto;
    }

    public Esquadra getEsquadra() {
        return esquadra;
    }

    public void setEsquadra(Esquadra esquadra) {
        this.esquadra = esquadra;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }

    public String getEnviaAnalise() {
        return enviaAnalise;
    }

    public void setEnviaAnalise(String enviaAnalise) {
        this.enviaAnalise = enviaAnalise;
    }

    public String getMotivoPendencia() {
        return motivoPendencia;
    }

    public void setMotivoPendencia(String motivoPendencia) {
        this.motivoPendencia = motivoPendencia;
    }

    public Long getIdTipoLocal() {
        return idTipoLocal;
    }

    public void setIdTipoLocal(Long idTipoLocal) {
        this.idTipoLocal = idTipoLocal;
    }

    public String getReensidencia() {
        return reensidencia;
    }

    public void setReensidencia(String reensidencia) {
        this.reensidencia = reensidencia;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Long getIdEsquadraRegisto() {
        return idEsquadraRegisto;
    }

    public void setIdEsquadraRegisto(Long idEsquadraRegisto) {
        this.idEsquadraRegisto = idEsquadraRegisto;
    }

    public String getObsEsquadraOrigem() {
        return obsEsquadraOrigem;
    }

    public void setObsEsquadraOrigem(String obsEsquadraOrigem) {
        this.obsEsquadraOrigem = obsEsquadraOrigem;
    }

    public Date getDataEnviado() {
        return dataEnviado;
    }

    public void setDataEnviado(Date dataEnviado) {
        this.dataEnviado = dataEnviado;
    }

    public Long getUserEnviado() {
        return userEnviado;
    }

    public void setUserEnviado(Long userEnviado) {
        this.userEnviado = userEnviado;
    }

    public Long getIdDono() {
        return idDono;
    }

    public void setIdDono(Long idDono) {
        this.idDono = idDono;
    }

    public String getEsquadraDenuncia_vbg() {
        return esquadraDenuncia_vbg;
    }

    public void setEsquadraDenuncia_vbg(String esquadraDenuncia_vbg) {
        this.esquadraDenuncia_vbg = esquadraDenuncia_vbg;
    }

    public String getDataDenunciaVbg() {
        return dataDenunciaVbg;
    }

    public void setDataDenunciaVbg(String dataDenunciaVbg) {
        this.dataDenunciaVbg = dataDenunciaVbg;
    }

    public String getConheceDesfechoVbg() {
        return conheceDesfechoVbg;
    }

    public void setConheceDesfechoVbg(String conheceDesfechoVbg) {
        this.conheceDesfechoVbg = conheceDesfechoVbg;
    }

    public String getDesfechoVbg() {
        return desfechoVbg;
    }

    public void setDesfechoVbg(String desfechoVbg) {
        this.desfechoVbg = desfechoVbg;
    }

    public String getSofreuVbgAntes() {
        return sofreuVbgAntes;
    }

    public void setSofreuVbgAntes(String sofreuVbgAntes) {
        this.sofreuVbgAntes = sofreuVbgAntes;
    }

    public String getIdUserEdita() {
        return idUserEdita;
    }

    public void setIdUserEdita(String idUserEdita) {
        this.idUserEdita = idUserEdita;
    }

    public Date getDatadita() {
        return datadita;
    }

    public void setDatadita(Date datadita) {
        this.datadita = datadita;
    }

    public String getPrivado() {
        return privado;
    }

    public void setPrivado(String privado) {
        this.privado = privado;
    }

    public String getQuestionarioRiscoVbg() {
        return questionarioRiscoVbg;
    }

    public void setQuestionarioRiscoVbg(String questionarioRiscoVbg) {
        this.questionarioRiscoVbg = questionarioRiscoVbg;
    }

    public String getMotivoNaoQuestRiscoVbg() {
        return motivoNaoQuestRiscoVbg;
    }

    public void setMotivoNaoQuestRiscoVbg(String motivoNaoQuestRiscoVbg) {
        this.motivoNaoQuestRiscoVbg = motivoNaoQuestRiscoVbg;
    }

    public Long getIdComando() {
        return idComando;
    }

    public void setIdComando(Long idComando) {
        this.idComando = idComando;
    }

    public String getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(String dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public Long getIdUserAnalise() {
        return idUserAnalise;
    }

    public void setIdUserAnalise(Long idUserAnalise) {
        this.idUserAnalise = idUserAnalise;
    }

    public String getUserAnalise() {
        return userAnalise;
    }

    public void setUserAnalise(String userAnalise) {
        this.userAnalise = userAnalise;
    }

    public String getTipoAnalise() {
        return tipoAnalise;
    }

    public void setTipoAnalise(String tipoAnalise) {
        this.tipoAnalise = tipoAnalise;
    }

    public String getObsAnalise() {
        return obsAnalise;
    }

    public void setObsAnalise(String obsAnalise) {
        this.obsAnalise = obsAnalise;
    }

    public String getDataDespacho() {
        return dataDespacho;
    }

    public void setDataDespacho(String dataDespacho) {
        this.dataDespacho = dataDespacho;
    }

    public Long getIdUserDespacho() {
        return idUserDespacho;
    }

    public void setIdUserDespacho(Long idUserDespacho) {
        this.idUserDespacho = idUserDespacho;
    }

    public String getUserDespacho() {
        return userDespacho;
    }

    public void setUserDespacho(String userDespacho) {
        this.userDespacho = userDespacho;
    }

    public String getTipoDespacho() {
        return tipoDespacho;
    }

    public void setTipoDespacho(String tipoDespacho) {
        this.tipoDespacho = tipoDespacho;
    }

    public String getObsDespacho() {
        return obsDespacho;
    }

    public void setObsDespacho(String obsDespacho) {
        this.obsDespacho = obsDespacho;
    }

    public String getDataFecho() {
        return dataFecho;
    }

    public void setDataFecho(String dataFecho) {
        this.dataFecho = dataFecho;
    }

    public String getTipoFecho() {
        return tipoFecho;
    }

    public void setTipoFecho(String tipoFecho) {
        this.tipoFecho = tipoFecho;
    }

    public String getUserFecho() {
        return userFecho;
    }

    public void setUserFecho(String userFecho) {
        this.userFecho = userFecho;
    }

    public String getDescricaoEtapa() {
        return descricaoEtapa;
    }

    public void setDescricaoEtapa(String descricaoEtapa) {
        this.descricaoEtapa = descricaoEtapa;
    }

    public String getAplicaouQuestionarioVbg() {
        return aplicaouQuestionarioVbg;
    }

    public void setAplicaouQuestionarioVbg(String aplicaouQuestionarioVbg) {
        this.aplicaouQuestionarioVbg = aplicaouQuestionarioVbg;
    }

    public String getMotivoNaoQuestVbg() {
        return motivoNaoQuestVbg;
    }

    public void setMotivoNaoQuestVbg(String motivoNaoQuestVbg) {
        this.motivoNaoQuestVbg = motivoNaoQuestVbg;
    }

    public String getIdProcessoSij() {
        return idProcessoSij;
    }

    public void setIdProcessoSij(String idProcessoSij) {
        this.idProcessoSij = idProcessoSij;
    }

    public String getNumProcessoSij() {
        return numProcessoSij;
    }

    public void setNumProcessoSij(String numProcessoSij) {
        this.numProcessoSij = numProcessoSij;
    }

    public String getDataEnvioSij() {
        return dataEnvioSij;
    }

    public void setDataEnvioSij(String dataEnvioSij) {
        this.dataEnvioSij = dataEnvioSij;
    }

    public String getMotivoArquiva() {
        return motivoArquiva;
    }

    public void setMotivoArquiva(String motivoArquiva) {
        this.motivoArquiva = motivoArquiva;
    }

    public String getDataArquiva() {
        return dataArquiva;
    }

    public void setDataArquiva(String dataArquiva) {
        this.dataArquiva = dataArquiva;
    }

    public String getUserArquiva() {
        return userArquiva;
    }

    public void setUserArquiva(String userArquiva) {
        this.userArquiva = userArquiva;
    }

    public String getDtRegistoTime() {
        return dtRegistoTime;
    }

    public void setDtRegistoTime(String dtRegistoTime) {
        this.dtRegistoTime = dtRegistoTime;
    }

    public String getMgid() {
        return mgid;
    }

    public void setMgid(String mgid) {
        this.mgid = mgid;
    }
}
