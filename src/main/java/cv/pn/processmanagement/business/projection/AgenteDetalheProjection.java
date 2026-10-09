package cv.pn.processmanagement.business.projection;

public interface AgenteDetalheProjection {

    String getId();

    Long getIdNacional();

    String getNumero();

    String getNome();

    String getCategoria();

    Long getIdEsquadra();

    Long getIdComando();

    String getMimeType();

    String getAssinatura();

    String getAssinaturas();

    Boolean getTemFoto();
}
