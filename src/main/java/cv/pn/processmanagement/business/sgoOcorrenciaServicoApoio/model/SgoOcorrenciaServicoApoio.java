package cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.model;

import cv.pn.processmanagement.commons.CommonsAttributes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sgo_ocorrencia_servico_apoio")
public class SgoOcorrenciaServicoApoio extends CommonsAttributes {

    @Column(name = "ACOMPANHAMENTO")
    private String acompanhamento;

    @Column(name = "TIPO_ATENDIMENTO")
    private String tipoAtendimento;

    @Column(name = "AGENDAMENTO")
    private String agendamento;

    @Column(name = "ID_SERVICOS")
    private Long idServicos;

    @Column(name = "ID_OCORRENCIA")
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
