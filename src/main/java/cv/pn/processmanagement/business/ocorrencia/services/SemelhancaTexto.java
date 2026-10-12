package cv.pn.processmanagement.business.ocorrencia.services;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Set;

/**
 * Comparação de nomes vindos do SIGOIC com os do SIGO antigo (ex.: tipologias de crime).
 *
 * Os dois sistemas escrevem os mesmos nomes de forma ligeiramente diferente ("Injúria" / "injuria",
 * "Coacção" / "Coação"). Os nomes são comparados sem acentos, sem distinguir maiúsculas de minúsculas e
 * sem pontuação nem espaços repetidos; são considerados iguais quando a semelhança é de pelo menos 80%
 * (1 - distância de edição / tamanho do nome maior).
 */
public final class SemelhancaTexto {

    public static final double SEMELHANCA_MINIMA = 0.80;

    // Nomes com semelhança de 80% ou mais que são crimes diferentes (já normalizados, em qualquer ordem)
    private static final Set<Set<String>> NUNCA_SEMELHANTES = Set.of(
            Set.of("trafico de orgaos humanos", "trafico de seres humanos"),
            Set.of("usurpacao de coisa imovel", "usurpacao de coisa movel"));

    private SemelhancaTexto() {
    }

    public static boolean semelhantes(String a, String b) {
        String x = normalizar(a);
        String y = normalizar(b);
        if (!x.equals(y) && NUNCA_SEMELHANTES.contains(Set.of(x, y))) {
            return false;
        }
        return semelhanca(a, b) >= SEMELHANCA_MINIMA;
    }

    /** Semelhança entre 0 e 1 dos dois nomes normalizados. */
    public static double semelhanca(String a, String b) {
        String x = normalizar(a);
        String y = normalizar(b);
        if (x.isEmpty() || y.isEmpty()) {
            return 0;
        }
        if (x.equals(y)) {
            return 1;
        }
        return 1.0 - (double) distancia(x, y) / Math.max(x.length(), y.length());
    }

    /** Sem acentos, em minúsculas, só letras e algarismos separados por um espaço. */
    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String semEntidades = texto.replace("&quot;", "\"");
        String semAcentos = Normalizer.normalize(semEntidades, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        return semAcentos.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", " ").trim();
    }

    /** Distância de Levenshtein (inserções, remoções e trocas de letras). */
    private static int distancia(String a, String b) {
        int[] anterior = new int[b.length() + 1];
        int[] atual = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) {
            anterior[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            atual[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                int troca = anterior[j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1);
                atual[j] = Math.min(Math.min(anterior[j] + 1, atual[j - 1] + 1), troca);
            }
            int[] t = anterior;
            anterior = atual;
            atual = t;
        }
        return anterior[b.length()];
    }
}
