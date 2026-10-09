package cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.dto;


public class SgoOcorrenciaServicoApoioDto {

    private String acompanhamento;

    private String tipoAtendimento;

    private String agendamento;

    private Long idServicos;

    private Long idOcorrencia;

    public String getAcompanhamento() {
        return acompanhamento;
    }

    public void setAcompanhamento(String acompanhamento) {
        this.acompanhamento = acompanhamento;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public String getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(String agendamento) {
        this.agendamento = agendamento;
    }

    public Long getIdServicos() {
        return idServicos;
    }

    public void setIdServicos(Long idServicos) {
        this.idServicos = idServicos;
    }

    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }
}
