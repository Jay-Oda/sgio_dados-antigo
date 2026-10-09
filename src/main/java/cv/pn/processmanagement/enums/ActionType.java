package cv.pn.processmanagement.enums;

public enum ActionType {

    POST("POST", "Criado"),
    PUT("PUT", "Atualizado");
    private final String code;
    private final String description;

    ActionType(String code, String description) {
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
