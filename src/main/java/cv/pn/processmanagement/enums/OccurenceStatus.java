package cv.pn.processmanagement.enums;

public enum OccurenceStatus {

    AR("AR", "A Recolher"),
    PV("PV", "Por Validar"),
    PD("PD", "Por Despachar"),
    CO("CO", "Concluído"),
    PE("PE", "Pendente"),
    EA("EA", "Em Aberto");
    private final String code;
    private final String description;

    OccurenceStatus(String code, String description) {
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
