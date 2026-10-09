package cv.pn.processmanagement.business.sigoPessoa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filtros para pesquisa avançada de pessoas")
public class PessoaFiltroRequest {

    @Schema(description = "Nome da pessoa")
    private String nome;
    @Schema(description = "Número de documento")
    private String num;
    @Schema(description = "Nome do pai")
    private String pai;
    @Schema(description = "Nome da mãe")
    private String mae;
    @Schema(description = "Data de nascimento no formato yyyy-MM-dd (ex.: 1985-10-15)")
    private String dtNascimento;

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

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }
}
