package cv.pn.processmanagement.enums;

public enum MaritalStatus {

    S("S", "Solteiro(a)"),
    C("C", "Casado(a)"),
    V("V", "Viúvo(a)"),
    U("U", "União de Factos"),
    D("D", "Divorciado(a)");
    private final String code;
    private final String description;

    MaritalStatus(String code, String description) {
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
