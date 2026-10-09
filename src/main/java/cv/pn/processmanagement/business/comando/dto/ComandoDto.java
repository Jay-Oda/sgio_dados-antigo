package cv.pn.processmanagement.business.comando.dto;

public class ComandoDto {

    private String id;
    private String nome;
    private String comandante;
    private String localizacao;
    private String contacto;
    private Number id_nacional;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComandante() {
        return comandante;
    }

    public void setComandante(String comandante) {
        this.comandante = comandante;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Number getId_nacional() {
        return id_nacional;
    }

    public void setId_nacional(Number id_nacional) {
        this.id_nacional = id_nacional;
    }
}
