package cv.pn.processmanagement.business.antecedentes;

public interface IAntecedenteService {
    Object consultarAntecedentes(Long oracleId, String auto);

    Object pesquisarPessoas(PessoaAntecedenteRequest req);

    Object pesquisarAntecedentes(String nome, String alcunha, String pai, String mae, String dataNascimento, String auto);

    Object listarTodasPessoas(int page, int size, String auto);

    Object detalharPessoa(Long oracleId, String auto);

    Object buscarAntecedentesPorNome(String nome, String pai, String mae, String dataNascimento);

    Object pesquisarPessoaCompleta(String nome, String alcunha, String pai, String mae, String dataNascimento);

    Object pesquisarPorNumOcorrencia(String numOcorrencia);

    Object detalharOcorrencia(String numOcorrencia);
}
