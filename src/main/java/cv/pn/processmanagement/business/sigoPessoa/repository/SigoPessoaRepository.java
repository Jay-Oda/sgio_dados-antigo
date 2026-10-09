package cv.pn.processmanagement.business.sigoPessoa.repository;

import cv.pn.processmanagement.business.projection.PessoaDetalheProjection;
import cv.pn.processmanagement.business.projection.PessoaResumoProjection;
import cv.pn.processmanagement.business.sigoPessoa.model.SigoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SigoPessoaRepository extends JpaRepository<SigoPessoa, Long> {

    @Query("""
    SELECT p.id AS id,
           p.oracleId AS oracleId,
           p.nome AS nome,
           p.alcunhas AS alcunhas,
           p.num AS num,
           p.pai AS pai,
           p.mae AS mae,
           p.dtNascimento AS dtNascimento
    FROM SigoPessoa p
    WHERE lower(trim(COALESCE(p.alcunhas, ''))) LIKE lower(concat('%', :alcunha, '%'))
    ORDER BY p.nome
""")
    List<PessoaResumoProjection> buscarPorAlcunha(@Param("alcunha") String alcunha);

    @Query("""
    SELECT p.id AS id,
           p.oracleId AS oracleId,
           p.nome AS nome,
           p.alcunhas AS alcunhas,
           p.num AS num,
           p.pai AS pai,
           p.mae AS mae,
           p.dtNascimento AS dtNascimento
    FROM SigoPessoa p
    WHERE upper(trim(p.nome)) = upper(trim(:nome))
    ORDER BY p.nome
""")
    List<PessoaResumoProjection> buscarPorNomeNormalizado(@Param("nome") String nome);

    @Query("""
    SELECT p.id AS id,
           p.oracleId AS oracleId,
           p.nome AS nome,
           p.alcunhas AS alcunhas,
           p.num AS num,
           p.pai AS pai,
           p.mae AS mae,
           p.dtNascimento AS dtNascimento
    FROM SigoPessoa p
    WHERE (:nome IS NULL OR lower(trim(p.nome)) LIKE lower(concat('%', :nome, '%')))
      AND (:num IS NULL OR trim(p.num) LIKE concat('%', :num, '%'))
      AND (:pai IS NULL OR lower(trim(p.pai)) LIKE lower(concat('%', :pai, '%')))
      AND (:mae IS NULL OR lower(trim(p.mae)) LIKE lower(concat('%', :mae, '%')))
      AND p.dtNascimento = :dtNasc
    ORDER BY p.nome
""")
    List<PessoaResumoProjection> buscarComData(
            @Param("nome") String nome,
            @Param("num") String num,
            @Param("pai") String pai,
            @Param("mae") String mae,
            @Param("dtNasc") Date dtNasc
    );

    @Query("""
    SELECT p.id AS id,
           p.oracleId AS oracleId,
           p.nome AS nome,
           p.alcunhas AS alcunhas,
           p.num AS num,
           p.pai AS pai,
           p.mae AS mae,
           p.dtNascimento AS dtNascimento
    FROM SigoPessoa p
    WHERE (:nome IS NULL OR lower(trim(p.nome)) LIKE lower(concat('%', :nome, '%')))
      AND (:num IS NULL OR trim(p.num) LIKE concat('%', :num, '%'))
      AND (:pai IS NULL OR lower(trim(p.pai)) LIKE lower(concat('%', :pai, '%')))
      AND (:mae IS NULL OR lower(trim(p.mae)) LIKE lower(concat('%', :mae, '%')))
    ORDER BY p.nome
""")
    List<PessoaResumoProjection> buscarSemData(
            @Param("nome") String nome,
            @Param("num") String num,
            @Param("pai") String pai,
            @Param("mae") String mae
    );

    @Query(value = """
    SELECT p.id AS id,
           p.oracle_id AS oracleId,
           p.nome AS nome,
           p.alcunhas AS alcunhas,
           p.num AS num,
           p.pai AS pai,
           p.mae AS mae,
           p.dt_nascimento AS dtNascimento,
           p.sexo AS sexo,
           p.estado_civil AS estadoCivil,
           p.tp_identificacao AS tpIdentificacao,
           p.naturalidade AS naturalidade,
           p.morada AS morada,
           p.nacionalidade AS nacionalidade,
           p.contacto AS contacto,
           p.profissao AS profissao,
           p.tipo_pessoa AS tipoPessoa,
           p.habilitacao_literaria AS habilitacaoLiteraria,
           p.idade AS idade
    FROM sigo_pessoa p
    WHERE (:nome IS NULL OR (
            SELECT count(*) FROM unnest(string_to_array(:nome, ' ')) t(term)
            WHERE lower(p.nome) LIKE '%' || lower(t.term) || '%'
          ) = array_length(string_to_array(:nome, ' '), 1))
      AND (:alcunha IS NULL OR lower(trim(COALESCE(p.alcunhas, ''))) LIKE lower('%' || :alcunha || '%'))
      AND (:pai IS NULL OR lower(trim(p.pai)) LIKE lower('%' || :pai || '%'))
      AND (:mae IS NULL OR lower(trim(p.mae)) LIKE lower('%' || :mae || '%'))
      AND (cast(:dtNasc as date) IS NULL OR p.dt_nascimento = cast(:dtNasc as date))
    ORDER BY p.nome
    """, nativeQuery = true)
    List<PessoaDetalheProjection> buscarDetalhePorFiltros(
            @Param("nome") String nome,
            @Param("alcunha") String alcunha,
            @Param("pai") String pai,
            @Param("mae") String mae,
            @Param("dtNasc") Date dtNasc
    );

    @Query(value = """
        SELECT p.id AS id,
               p.oracle_id AS oracleId,
               p.nome AS nome,
               p.alcunhas AS alcunhas,
               p.num AS num,
               p.pai AS pai,
               p.mae AS mae,
               p.dt_nascimento AS dtNascimento
        FROM sigo_pessoa p
        ORDER BY p.nome ASC
        LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<PessoaResumoProjection> listarTodasPaginado(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT count(*) FROM sigo_pessoa", nativeQuery = true)
    long contarTodas();

    @Query(value = """
        SELECT p.id AS id,
               p.oracle_id AS oracleId,
               p.nome AS nome,
               p.alcunhas AS alcunhas,
               p.num AS num,
               p.pai AS pai,
               p.mae AS mae,
               p.dt_nascimento AS dtNascimento,
               p.sexo AS sexo,
               p.estado_civil AS estadoCivil,
               p.tp_identificacao AS tpIdentificacao,
               p.naturalidade AS naturalidade,
               p.morada AS morada,
               p.nacionalidade AS nacionalidade,
               p.contacto AS contacto,
               p.profissao AS profissao,
               p.tipo_pessoa AS tipoPessoa,
               p.habilitacao_literaria AS habilitacaoLiteraria,
               p.idade AS idade
        FROM sigo_pessoa p
        WHERE p.oracle_id = :oracleId
    """, nativeQuery = true)
    Optional<PessoaDetalheProjection> findDetalheByOracleId(@Param("oracleId") Long oracleId);

    @Query("""
SELECT DISTINCT p2.oracleId
FROM SigoPessoa p1
JOIN SigoPessoa p2
  ON lower(trim(p1.nome)) = lower(trim(p2.nome))
 AND (
        (p1.dtNascimento IS NOT NULL AND p1.dtNascimento = p2.dtNascimento)
     OR (p1.num IS NOT NULL AND p1.num = p2.num)
     OR (
            p1.pai IS NOT NULL AND p2.pai IS NOT NULL
        AND p1.mae IS NOT NULL AND p2.mae IS NOT NULL
        AND lower(trim(p1.pai)) = lower(trim(p2.pai))
        AND lower(trim(p1.mae)) = lower(trim(p2.mae))
     )
 )
WHERE p1.oracleId = :oracleId
""")
    List<Long> buscarOracleIdsEquivalentes(@Param("oracleId") Long oracleId);
}
