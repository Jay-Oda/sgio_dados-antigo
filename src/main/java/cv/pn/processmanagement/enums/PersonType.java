package cv.pn.processmanagement.enums;

public enum PersonType {

    SINGULAR("SINGULAR", "Singular"),
    COLECTIVA("COLECTIVA", "Coletivo");

    private final String code;
    private final String description;

    PersonType(String code, String description) {
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
