package cv.pn.processmanagement.business.ocorrencia.services;

import java.util.Date;

/** Linha da listagem nacional servida a partir da cache (mesmos campos de OcorrenciaNacionalProjection). */
public class OcorrenciaNacionalDto {

    Long oracleIdOcorrencia;
    String numOcorrencia;
    Date dataRegisto;
    String tpAuto;
    String autoPrincipal;
    String numProcesso;
    String unidade;
    String comando;
    String fase;
    String estado;
    String estadoCodigo;
    String registradoPor;
    String prazo;
    String tipo;
    String naturezas;
    String tipologias;

    public Long getOracleIdOcorrencia() { return oracleIdOcorrencia; }
    public String getNumOcorrencia() { return numOcorrencia; }
    public Date getDataRegisto() { return dataRegisto; }
    public String getTpAuto() { return tpAuto; }
    public String getAutoPrincipal() { return autoPrincipal; }
    public String getNumProcesso() { return numProcesso; }
    public String getUnidade() { return unidade; }
    public String getComando() { return comando; }
    public String getFase() { return fase; }
    public String getEstado() { return estado; }
    public String getEstadoCodigo() { return estadoCodigo; }
    public String getRegistradoPor() { return registradoPor; }
    public String getPrazo() { return prazo; }
    public String getTipo() { return tipo; }
    public String getNaturezas() { return naturezas; }
    public String getTipologias() { return tipologias; }
}
