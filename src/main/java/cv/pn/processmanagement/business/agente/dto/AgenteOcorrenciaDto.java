package cv.pn.processmanagement.business.agente.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AgenteOcorrenciaDto {

    private Long oracleId;
    private String numOcorrencia;
    private LocalDateTime dataRegisto;
    private String natureza;
    private String autoPrincipal;
    private String unidade;
    private String fase;
    private String estado;
    private String motivo;
    private String condicao;
    private String origem;
    private String tpAuto;
    private String numProcesso;
    private String pontoReferencia;
    private List<Map<String, Object>> envolvidos;
    private List<Map<String, Object>> circunstancias;

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
    }

    public String getNumOcorrencia() {
        return numOcorrencia;
    }

    public void setNumOcorrencia(String numOcorrencia) {
        this.numOcorrencia = numOcorrencia;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getAutoPrincipal() {
        return autoPrincipal;
    }

    public void setAutoPrincipal(String autoPrincipal) {
        this.autoPrincipal = autoPrincipal;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getTpAuto() {
        return tpAuto;
    }

    public void setTpAuto(String tpAuto) {
        this.tpAuto = tpAuto;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public List<Map<String, Object>> getEnvolvidos() {
        return envolvidos;
    }

    public void setEnvolvidos(List<Map<String, Object>> envolvidos) {
        this.envolvidos = envolvidos;
    }

    public List<Map<String, Object>> getCircunstancias() {
        return circunstancias;
    }

    public void setCircunstancias(List<Map<String, Object>> circunstancias) {
        this.circunstancias = circunstancias;
    }
}
