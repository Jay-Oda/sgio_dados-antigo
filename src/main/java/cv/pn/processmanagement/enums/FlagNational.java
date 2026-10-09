package cv.pn.processmanagement.enums;

public enum FlagNational {
    
    NACIONAL("NACIONAL", "Nacional"),
    ESTRANGEIRO("ESTRANGEIRO", "Estrangeiro");

    private final String code;
    private final String descripction;

    FlagNational(  String code, String descripction) {
        this.code = code;
        this.descripction = descripction;
    }


}
