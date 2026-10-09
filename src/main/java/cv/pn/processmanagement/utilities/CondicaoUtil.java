package cv.pn.processmanagement.utilities;

public final class CondicaoUtil {

    private CondicaoUtil() {
    }

    public static String normalizar(String condicao) {
        if (condicao == null || condicao.isBlank()) {
            return null;
        }
        String v = condicao.trim().toLowerCase();
        if (v.equals("1") || v.contains("denunciante")) {
            return "1";
        }
        if (v.equals("2") || v.contains("ofendido")) {
            return "2";
        }
        if (v.equals("3") || v.contains("denunciado")) {
            return "3";
        }
        if (v.equals("4") || v.contains("testemunha")) {
            return "4";
        }
        if (v.equals("5") || v.contains("detido")) {
            return "5";
        }
        if (v.equals("6") || v.contains("detentor")) {
            return "6";
        }
        if (v.equals("7") || v.contains("atuante")) {
            return "7";
        }
        if (v.equals("8") || v.contains("participante")) {
            return "8";
        }
        return null;
    }
}
