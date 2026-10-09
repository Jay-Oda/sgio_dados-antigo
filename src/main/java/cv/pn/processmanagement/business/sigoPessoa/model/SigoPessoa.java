package cv.pn.processmanagement.business.sigoPessoa.model;


import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "sigo_pessoa")
public class SigoPessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ID", nullable = false)
    private Long id; // ID NUMÉRICO DO ORACLE (BIGINT)
    @Column(name = "ORACLE_ID")
    private Long oracleId;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "NUM")
    private String num;
    @Column(name = "PAI")
    private String pai;
    @Column(name = "MAE")
    private String mae;

    @Column(name = "DT_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;

    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    @Column(name = "TP_IDENTIFICACAO")
    private String tpIdenificacao;
    @Column(name = "NATURALIDADE")
    private String naturalidade;
    @Column(name = "MORADA")
    private String morada;
    @Column(name = "NACIONALIDADE")
    private String nacionalidade;
    @Column(name = "ALCUNHAS")
    private String alcunhas;
    @Column(name = "TIPO_PESSOA")
    private String tipoPessoa;

    @Column(name = "FOTO")
    @Lob
    private byte[] foto;

    @Column(name = "FOTO_BASE64")
    @Transient
    private String fotoBase64;

    @Column(name = "CONTACTO")
    private String contacto;
    @Column(name = "IDADE")
    private String idade;
    @Column(name = "PROFISSAO")
    private String profissao;
    @Column(name = "LOCAL_TRABALHO")
    private String localTrabalho;
    @Column(name = "SITUACAO_LABORAL")
    private String situacaoLaboral;
    @Column(name = "HABILITACAO_LITERARIA")
    private String habilitacaoLiteraria;
    @Column(name = "INDETERMINADO")
    private String indeterminado;
    @Column(name = "DESCONHECIDO")
    private String desconhecido;
    @Column(name = "CATEGORIA_EFETIVO")
    private String categoriaEfetivo;
    @Column(name = "ESQUADRA_EFETIVO")
    private String esquadraEfetivo;
    @Column(name = "ID_ESQUADRA_EFETIVO")
    private Long idEsquadraEfetivo;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "MG_ID")
    private String mgid;

   // @Column(name = "ID_PESSOA")
  //  private Long idPessoa;


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

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTpIdenificacao() {
        return tpIdenificacao;
    }

    public void setTpIdenificacao(String tpIdenificacao) {
        this.tpIdenificacao = tpIdenificacao;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getAlcunhas() {
        return alcunhas;
    }

    public void setAlcunhas(String alcunhas) {
        this.alcunhas = alcunhas;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getSituacaoLaboral() {
        return situacaoLaboral;
    }

    public void setSituacaoLaboral(String situacaoLaboral) {
        this.situacaoLaboral = situacaoLaboral;
    }

    public String getHabilitacaoLiteraria() {
        return habilitacaoLiteraria;
    }

    public void setHabilitacaoLiteraria(String habilitacaoLiteraria) {
        this.habilitacaoLiteraria = habilitacaoLiteraria;
    }

    public String getIndeterminado() {
        return indeterminado;
    }

    public void setIndeterminado(String indeterminado) {
        this.indeterminado = indeterminado;
    }

    public String getDesconhecido() {
        return desconhecido;
    }

    public void setDesconhecido(String desconhecido) {
        this.desconhecido = desconhecido;
    }

    public String getCategoriaEfetivo() {
        return categoriaEfetivo;
    }

    public void setCategoriaEfetivo(String categoriaEfetivo) {
        this.categoriaEfetivo = categoriaEfetivo;
    }

    public String getEsquadraEfetivo() {
        return esquadraEfetivo;
    }

    public void setEsquadraEfetivo(String esquadraEfetivo) {
        this.esquadraEfetivo = esquadraEfetivo;
    }

    public Long getIdEsquadraEfetivo() {
        return idEsquadraEfetivo;
    }

    public void setIdEsquadraEfetivo(Long idEsquadraEfetivo) {
        this.idEsquadraEfetivo = idEsquadraEfetivo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMgid() {
        return mgid;
    }

    public void setMgid(String mgid) {
        this.mgid = mgid;
    }


}
