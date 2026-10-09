package cv.pn.processmanagement.business.antecedentes;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filtros para pesquisa de pessoas/antecedentes")
public class PessoaAntecedenteRequest {

    @Schema(description = "Nome da pessoa")
    private String nome;
    @Schema(description = "Sobrenome da pessoa")
    private String sobrenome;
    @Schema(description = "Alcunha")
    private String alcunha;
    @Schema(description = "Número de documento")
    private String num;
    @Schema(description = "Nome do pai")
    private String pai;
    @Schema(description = "Nome da mãe")
    private String mae;
    @Schema(description = "Data de nascimento no formato YYYY-MM-DD (ex.: 2026-02-28)")
    private String dtNascimento;
    @Schema(description = "Filtro por tipo de auto (ex.: Auto de Denúncia, Auto de Notícia, Auto de Detenção, Auto de Denúncia VBG, Auto de Identificação)")
    private String auto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
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

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }
}
