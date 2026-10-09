package cv.pn.processmanagement.utilities;

public final class AutoTipoUtil {

    private AutoTipoUtil() {
    }

    public static String normalizar(String auto) {
        if (auto == null || auto.isBlank()) {
            return null;
        }
        String v = auto.trim().toLowerCase();
        if (v.contains("denúncia vbg") || v.contains("denuncia vbg")) {
            return "4";
        }
        if (v.equals("2") || v.contains("notícia") || v.contains("noticia")) {
            return "2";
        }
        if (v.equals("3") || v.contains("detenção") || v.contains("detencao")) {
            return "3";
        }
        if (v.equals("4") || v.contains("vbg")) {
            return "4";
        }
        if (v.equals("5") || v.contains("identificação") || v.contains("identificacao")) {
            return "5";
        }
        if (v.equals("1") || v.contains("denúncia") || v.contains("denuncia")) {
            return "1";
        }
        return null;
    }

    public static boolean isModeloDeAuto(String auto) {
        if (auto == null || auto.isBlank()) {
            return false;
        }
        String v = auto.trim().toLowerCase();
        return v.contains("apreensão") || v.contains("apreensao")
                || v.contains("recolha")
                || v.contains("exame")
                || v.contains("peritagem")
                || v.contains("declaração") || v.contains("declaracao");
    }

    public static String descricao(String tpAuto) {
        if (tpAuto == null) {
            return null;
        }
        switch (tpAuto) {
            case "1":
                return "Auto de Denúncia";
            case "2":
                return "Auto de Notícia";
            case "3":
                return "Auto de Detenção";
            case "4":
                return "Auto de Denúncia VBG";
            case "5":
                return "Auto de Identificação";
            default:
                return null;
        }
    }
}
