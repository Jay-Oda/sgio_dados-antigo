package cv.pn.processmanagement.enums;

public enum ActorsCharacteristics {

    CONHECIDO("CONHECIDO", "Conhecido"),
    DESCONHECIDO("DESCONHECIDO", "Desconhecido"),
    INDETERMINADO("INDETERMINADO", "Indeterminado");

    private final String code;
    private final String description;

    ActorsCharacteristics(String code, String description) {
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
