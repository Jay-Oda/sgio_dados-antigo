package cv.pn.processmanagement.enums;

public enum Gender {

    M("M", "Masculino"),
    F("F", "Feminino");

    private final String code;
    private final String description;

    Gender(String code, String description) {
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
