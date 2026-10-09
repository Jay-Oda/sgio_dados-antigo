package cv.pn.processmanagement.enums;

public enum ForeignType {

    R("R", "Residente"),
    T("T", "Turista"),
    N("N", "Diplomata");

    private final String code;
    private final String description;

    ForeignType(String code, String description) {
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
