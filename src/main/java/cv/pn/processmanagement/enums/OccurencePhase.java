package cv.pn.processmanagement.enums;

public enum OccurencePhase {

    RE("RE", "Recolha"),
    VA("VA", "Validação"),
    DE("DE", "Despacho");
    private final String code;
    private final String description;

    OccurencePhase(String code, String description) {
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
