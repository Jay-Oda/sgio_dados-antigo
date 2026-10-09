package cv.pn.processmanagement.business.projection.dto;

import java.time.LocalDateTime;

public class OcorrenciaResumoDto {

    private String numOcorrencia;
    private String natureza;
    private String autoPrincipal;
    private String condicao;
    //ou sem private
    private LocalDateTime dataRegisto;
    private String unidade;
    private String fase;

    public String getNumOcorrencia() {
        return numOcorrencia;
    }

    public void setNumOcorrencia(String numOcorrencia) {
        this.numOcorrencia = numOcorrencia;
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

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }


    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
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
}
