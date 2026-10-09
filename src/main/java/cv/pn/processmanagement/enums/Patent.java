package cv.pn.processmanagement.enums;

public enum Patent {

    A2("A2", "Agente 2º Classe"),
    A1("A1", "Agente 1º Classe"),
    AP("AP", "Agente Principal"),

    S2("S2", "Segundo Subchefe"),
    S1("S1", "Primeiro Subchefe"),
    SP("SP", "Subchefe Principal"),

    CE("CE", "Subchefe Esquadra"),
    SC("SC", "Subcomissário"),
    C("C", "Comissário"),

    SUBI("SUBI", "Sub Intendente"),
    I("I", "Intendente"),
    SUPI("SUPI", "Super Intendente"),
    SUPIG("SUPIG", "Super Intendente Geral");

    private final String code;
    private final String description;

    Patent(String code, String description) {
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
