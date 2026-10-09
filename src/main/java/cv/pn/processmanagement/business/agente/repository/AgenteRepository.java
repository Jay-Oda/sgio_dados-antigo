package cv.pn.processmanagement.business.agente.repository;

import cv.pn.processmanagement.business.agente.model.Agente;
import cv.pn.processmanagement.business.projection.AgenteDetalheProjection;
import cv.pn.processmanagement.business.projection.AgenteResumoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgenteRepository extends JpaRepository<Agente, String> {

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria
        FROM agente a
        WHERE a.numero = :numero
    """, nativeQuery = true)
    Optional<AgenteResumoProjection> findResumoByNumeroExact(@Param("numero") String numero);

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria
        FROM agente a
        WHERE a.numero LIKE concat('%', :numero, '%')
        ORDER BY a.nome ASC
    """, nativeQuery = true)
    List<AgenteResumoProjection> findResumoByNumeroContaining(@Param("numero") String numero);

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria
        FROM agente a
        WHERE a.id_nacional = :idNacional
        ORDER BY a.nome ASC
    """, nativeQuery = true)
    List<AgenteResumoProjection> findResumoByIdNacional(@Param("idNacional") Long idNacional);

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria
        FROM agente a
        WHERE upper(a.nome) LIKE upper(concat('%', :nome, '%'))
        ORDER BY a.nome ASC
    """, nativeQuery = true)
    List<AgenteResumoProjection> findResumoByNomeContaining(@Param("nome") String nome);

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria
        FROM agente a
        WHERE upper(a.nome) LIKE upper(concat('%', :nome, '%'))
        ORDER BY a.nome ASC
        LIMIT 20
    """, nativeQuery = true)
    List<AgenteResumoProjection> findTop20ResumoByNomeContaining(@Param("nome") String nome);

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria
        FROM agente a
        ORDER BY a.nome ASC
    """, nativeQuery = true)
    List<AgenteResumoProjection> findAllResumoOrderByNomeAsc();

    @Query(value = """
        SELECT a.id AS id,
               a.id_nacional AS idNacional,
               a.numero AS numero,
               a.nome AS nome,
               a.categoria AS categoria,
               a.id_esquadra AS idEsquadra,
               a.id_comando AS idComando,
               a.mime_type AS mimeType,
               a.assinatura AS assinatura,
               a.assinaturas AS assinaturas,
               (a.foto IS NOT NULL AND a.foto <> 0) AS temFoto
        FROM agente a
        WHERE a.id = :id
    """, nativeQuery = true)
    Optional<AgenteDetalheProjection> findDetalheById(@Param("id") String id);
}
