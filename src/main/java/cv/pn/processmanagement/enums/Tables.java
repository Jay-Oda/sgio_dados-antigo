package cv.pn.processmanagement.enums;

public enum Tables {

    SIGO_RECORD("SIGO_RECORD", "Tabela de Autos"),
    SIGO_OCCURRENCE("SIGO_OCCURRENCE", "Tabela de Ocorrencia");
    private final String code;
    private final String description;

    Tables(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
