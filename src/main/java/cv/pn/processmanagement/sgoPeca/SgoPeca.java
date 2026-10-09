package cv.pn.processmanagement.sgoPeca;



import javax.persistence.*;

@Entity
@Table(name = "sgo_pecas")
public class SgoPeca{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "oracle_id", unique = true)
    private Long oracleId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo_processo")
    private String tipoProcesso;

    @Column(name = "pessoas")
    private String pessoas;

    @Column(name = "objeto")
    private String objeto;

    @Column(name = "assinatura")
    private String assinatura;

    @Column(name = "nome_auto")
    private String nomeAuto;

    @Lob
    @Column(name = "corpo")
    private String corpo;

    @Column(name = "descricao")
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOracleId() {
        return oracleId;
    }

    public void setOracleId(Long oracleId) {
        this.oracleId = oracleId;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoProcesso() {
        return tipoProcesso;
    }

    public void setTipoProcesso(String tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }

    public String getPessoas() {
        return pessoas;
    }

    public void setPessoas(String pessoas) {
        this.pessoas = pessoas;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public String getNomeAuto() {
        return nomeAuto;
    }

    public void setNomeAuto(String nomeAuto) {
        this.nomeAuto = nomeAuto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
