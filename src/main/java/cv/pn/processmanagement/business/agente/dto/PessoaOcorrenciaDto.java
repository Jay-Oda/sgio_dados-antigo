package cv.pn.processmanagement.business.agente.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PessoaOcorrenciaDto {

    private Long oracleIdPessoa;
    private String nome;
    private String num;
    private Long numOcorrencias;
    private List<PessoaOcorrenciaItemDto> ocorrencias;

    public Long getOracleIdPessoa() {
        return oracleIdPessoa;
    }

    public void setOracleIdPessoa(Long oracleIdPessoa) {
        this.oracleIdPessoa = oracleIdPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Long getNumOcorrencias() {
        return numOcorrencias;
    }

    public void setNumOcorrencias(Long numOcorrencias) {
        this.numOcorrencias = numOcorrencias;
    }

    public List<PessoaOcorrenciaItemDto> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<PessoaOcorrenciaItemDto> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public static class PessoaOcorrenciaItemDto {

        private Long oracleIdOcorrencia;
        private String numOcorrencia;
        private LocalDateTime dataRegisto;
        private String condicao;

        public Long getOracleIdOcorrencia() {
            return oracleIdOcorrencia;
        }

        public void setOracleIdOcorrencia(Long oracleIdOcorrencia) {
            this.oracleIdOcorrencia = oracleIdOcorrencia;
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

        public String getCondicao() {
            return condicao;
        }

        public void setCondicao(String condicao) {
            this.condicao = condicao;
        }
    }
}
