package cv.pn.processmanagement.enums;

public enum ActorType {

    ARGUIDO("ARGUIDO", "ARGUIDO"),
    QEIXOSO("QEIXOSO", "QEIXOSO"),
    DENUNCIANTE("DENUNCIANTE", "DENUNCIANTE"),
    DENUNCIADO("DENUNCIADO", "DENUNCIADO"),
    TESTIMUNHA("TESTIMUNHA", "TESTIMUNHA"),
    VITIMA("VITIMA", "VITIMA");

    private final String code;
    private final String descricption;

    ActorType(String code, String descricption) {
        this.code = code;
        this.descricption = descricption;
    }
}
