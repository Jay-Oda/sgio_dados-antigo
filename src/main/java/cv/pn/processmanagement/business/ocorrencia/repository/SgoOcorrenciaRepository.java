package cv.pn.processmanagement.business.ocorrencia.repository;

import cv.pn.processmanagement.business.ocorrencia.model.SgoOcorrencia;
import cv.pn.processmanagement.business.projection.AgenteOcorrenciaProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaMotivoProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaNacionalProjection;
import cv.pn.processmanagement.business.projection.OpcaoProjection;
import cv.pn.processmanagement.business.sigoPessoa.model.SigoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SgoOcorrenciaRepository extends JpaRepository<SgoOcorrencia, String> /*, JpaSpecificationExecutor<SgoOcorrencia>*/ {

    @Query(value = """
        SELECT
            o.num_ocorrencia AS numOcorrencia,
            o.motivo         AS motivo
        FROM sgo_ocorrencia o
        WHERE o.num_ocorrencia = :num
    """, nativeQuery = true)
    OcorrenciaMotivoProjection buscarMotivo(
            @Param("num") String num
    );

@Query(value = """
SELECT
    o.oracle_id AS oracleIdOcorrencia,
    o.num_ocorrencia AS numOcorrencia,
    o.tp_auto AS tpAuto,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    o.data_registo AS dataRegisto,
    o.num_processo_sij AS numProcesso,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(cmd.nome, '') AS comando,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    CASE
        WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) = 'ARQUIVADO' THEN 'Arquivado'
        WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('ENVIADO AO MP', 'ENVIADO AO CRSSM', 'ENVIADO AO EICC') THEN 'Fechado'
        WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('AGUARDA ANÁLISE', 'PENDENTE ANÁLISE') THEN 'Em análise'
        ELSE 'Aberto'
    END AS estado,
    CASE
        WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) = 'ARQUIVADO' THEN 'R'
        WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('ENVIADO AO MP', 'ENVIADO AO CRSSM', 'ENVIADO AO EICC') THEN 'F'
        WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('AGUARDA ANÁLISE', 'PENDENTE ANÁLISE') THEN 'E'
        ELSE 'A'
    END AS estadoCodigo,
    COALESCE((
        SELECT a.nome
        FROM assinatura a
        WHERE a.id_agente = o.id_agente AND a.tipo_assinante = 'AUTUANTE'
        LIMIT 1
    ), '') AS registradoPor,
    COALESCE((
        SELECT pr.prazo
        FROM processo pr
        WHERE (pr.id_ocorrencia = o.oracle_id OR pr.num_ocorrencia = o.num_ocorrencia)
          AND pr.prazo IS NOT NULL AND pr.prazo <> ''
        ORDER BY pr.data_registo DESC
        LIMIT 1
    ), '') AS prazo,
    COALESCE((
        SELECT CASE t.naturesa_crime
            WHEN 'PUBLICO' THEN 'Público'
            WHEN 'SEMIPUBLICO' THEN 'Semi-público'
            WHEN 'PARTICULAR' THEN 'Particular'
            ELSE 'Criminal'
        END
        FROM sgo_ocorrencia_tp_ocorrencia x
        JOIN sgo_tp_ocorrencia t ON t.oracle_id = x.id_tp_ocorrencia
        WHERE x.id_ocorrencia = o.oracle_id
          AND t.naturesa_crime IS NOT NULL AND t.naturesa_crime <> ''
        LIMIT 1
    ), 'Criminal') AS tipo,
    COALESCE((
        SELECT string_agg(DISTINCT COALESCE(tp_pai2.nome, tp2.nome), ', ' ORDER BY COALESCE(tp_pai2.nome, tp2.nome))
        FROM sgo_ocorrencia_tp_ocorrencia otp2
        JOIN sgo_tp_ocorrencia tp2 ON tp2.oracle_id = otp2.id_tp_ocorrencia
        LEFT JOIN sgo_tp_ocorrencia tp_pai2 ON tp_pai2.oracle_id = tp2.id_pai
        WHERE otp2.id_ocorrencia = o.oracle_id
    ), '') AS naturezas,
    COALESCE((
        SELECT string_agg(DISTINCT tp3.nome, ', ' ORDER BY tp3.nome)
        FROM sgo_ocorrencia_tp_ocorrencia otp3
        JOIN sgo_tp_ocorrencia tp3 ON tp3.oracle_id = otp3.id_tp_ocorrencia
        WHERE otp3.id_ocorrencia = o.oracle_id
    ), '') AS tipologias
FROM sgo_ocorrencia o
LEFT JOIN esquadra e ON e.oracle_id = o.id_esquadra
LEFT JOIN esquadra cmd ON cmd.oracle_id = o.id_comando
WHERE (:numOcorrencia IS NULL OR o.num_ocorrencia ILIKE :numOcorrencia)
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
  AND (:estado IS NULL OR (
        CASE
            WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) = 'ARQUIVADO' THEN 'R'
            WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('ENVIADO AO MP', 'ENVIADO AO CRSSM', 'ENVIADO AO EICC') THEN 'F'
            WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('AGUARDA ANÁLISE', 'PENDENTE ANÁLISE') THEN 'E'
            ELSE 'A'
        END
      ) = :estado)
  AND (:dataInicio IS NULL OR o.data_registo >= CAST(:dataInicio AS timestamp))
  AND (:dataFim IS NULL OR o.data_registo <= CAST(:dataFim AS timestamp))
  AND (:natureza IS NULL OR EXISTS (
        SELECT 1
        FROM sgo_ocorrencia_tp_ocorrencia otp
        JOIN sgo_tp_ocorrencia tp ON tp.oracle_id = otp.id_tp_ocorrencia
        LEFT JOIN sgo_tp_ocorrencia tp_pai ON tp_pai.oracle_id = tp.id_pai
        WHERE otp.id_ocorrencia = o.oracle_id
          AND COALESCE(tp_pai.nome, tp.nome) = :natureza
  ))
  AND (:tipologia IS NULL OR EXISTS (
        SELECT 1
        FROM sgo_ocorrencia_tp_ocorrencia tp_o
        JOIN sgo_tp_ocorrencia tp ON tp.oracle_id = tp_o.id_tp_ocorrencia
        WHERE tp_o.id_ocorrencia = o.oracle_id
          AND tp.nome = :tipologia
  ))
  AND (:enquadramento IS NULL OR EXISTS (
        SELECT 1
        FROM sgo_ocorrencia_tp_ocorrencia en_o
        JOIN sgo_tp_ocorrencia en ON en.oracle_id = en_o.id_tp_ocorrencia
        WHERE en_o.id_ocorrencia = o.oracle_id
          AND en.enquadramento = :enquadramento
  ))
  AND (:comando IS NULL OR cmd.oracle_id = :comando)
  AND (:unidade IS NULL OR e.oracle_id = :unidade OR o.id_comando = :unidade)
ORDER BY o.data_registo DESC
OFFSET :offset
LIMIT :size
""", nativeQuery = true)
    List<OcorrenciaNacionalProjection> buscarOcorrenciasNacionais(
            @Param("numOcorrencia") String numOcorrencia,
            @Param("tpAuto") String tpAuto,
            @Param("estado") String estado,
            @Param("dataInicio") String dataInicio,
            @Param("dataFim") String dataFim,
            @Param("natureza") String natureza,
            @Param("tipologia") String tipologia,
            @Param("enquadramento") String enquadramento,
            @Param("comando") Long comando,
            @Param("unidade") Long unidade,
            @Param("offset") int offset,
            @Param("size") int size);

    @Query(value = """
SELECT COUNT(DISTINCT o.oracle_id)
FROM sgo_ocorrencia o
LEFT JOIN esquadra e ON e.oracle_id = o.id_esquadra
LEFT JOIN esquadra cmd ON cmd.oracle_id = o.id_comando
WHERE (:numOcorrencia IS NULL OR o.num_ocorrencia ILIKE :numOcorrencia)
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
  AND (:estado IS NULL OR (
        CASE
            WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) = 'ARQUIVADO' THEN 'R'
            WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('ENVIADO AO MP', 'ENVIADO AO CRSSM', 'ENVIADO AO EICC') THEN 'F'
            WHEN UPPER(TRIM(COALESCE(o.descricao_etapa, ''))) IN ('AGUARDA ANÁLISE', 'PENDENTE ANÁLISE') THEN 'E'
            ELSE 'A'
        END
      ) = :estado)
  AND (:dataInicio IS NULL OR o.data_registo >= CAST(:dataInicio AS timestamp))
  AND (:dataFim IS NULL OR o.data_registo <= CAST(:dataFim AS timestamp))
  AND (:natureza IS NULL OR EXISTS (
        SELECT 1
        FROM sgo_ocorrencia_tp_ocorrencia otp
        JOIN sgo_tp_ocorrencia tp ON tp.oracle_id = otp.id_tp_ocorrencia
        LEFT JOIN sgo_tp_ocorrencia tp_pai ON tp_pai.oracle_id = tp.id_pai
        WHERE otp.id_ocorrencia = o.oracle_id
          AND COALESCE(tp_pai.nome, tp.nome) = :natureza
  ))
  AND (:tipologia IS NULL OR EXISTS (
        SELECT 1
        FROM sgo_ocorrencia_tp_ocorrencia tp_o
        JOIN sgo_tp_ocorrencia tp ON tp.oracle_id = tp_o.id_tp_ocorrencia
        WHERE tp_o.id_ocorrencia = o.oracle_id
          AND tp.nome = :tipologia
  ))
  AND (:enquadramento IS NULL OR EXISTS (
        SELECT 1
        FROM sgo_ocorrencia_tp_ocorrencia en_o
        JOIN sgo_tp_ocorrencia en ON en.oracle_id = en_o.id_tp_ocorrencia
        WHERE en_o.id_ocorrencia = o.oracle_id
          AND en.enquadramento = :enquadramento
  ))
  AND (:comando IS NULL OR cmd.oracle_id = :comando)
  AND (:unidade IS NULL OR e.oracle_id = :unidade OR o.id_comando = :unidade)
""", nativeQuery = true)
    long countOcorrenciasNacionais(
            @Param("numOcorrencia") String numOcorrencia,
            @Param("tpAuto") String tpAuto,
            @Param("estado") String estado,
            @Param("dataInicio") String dataInicio,
            @Param("dataFim") String dataFim,
            @Param("natureza") String natureza,
            @Param("tipologia") String tipologia,
            @Param("enquadramento") String enquadramento,
            @Param("comando") Long comando,
            @Param("unidade") Long unidade);

    @Query(value = """
SELECT DISTINCT COALESCE(tp_pai.nome, tp.nome) AS nome
FROM sgo_tp_ocorrencia tp
LEFT JOIN sgo_tp_ocorrencia tp_pai ON tp_pai.oracle_id = tp.id_pai
WHERE COALESCE(tp_pai.nome, tp.nome) IS NOT NULL
  AND COALESCE(tp_pai.nome, tp.nome) <> ''
ORDER BY 1
""", nativeQuery = true)
    List<String> buscarNaturezas();

    @Query(value = """
SELECT DISTINCT tp.nome AS nome
FROM sgo_tp_ocorrencia tp
WHERE tp.nome IS NOT NULL AND tp.nome <> ''
ORDER BY 1
""", nativeQuery = true)
    List<String> buscarTipologias();

    @Query(value = """
SELECT DISTINCT tp.enquadramento AS nome
FROM sgo_tp_ocorrencia tp
WHERE tp.enquadramento IS NOT NULL AND tp.enquadramento <> ''
ORDER BY 1
""", nativeQuery = true)
    List<String> buscarEnquadramentos();

    @Query(value = """
SELECT DISTINCT e.oracle_id AS valor, e.nome AS nome
FROM esquadra e
WHERE e.oracle_id IN (SELECT DISTINCT o.id_comando FROM sgo_ocorrencia o WHERE o.id_comando IS NOT NULL)
ORDER BY e.nome
""", nativeQuery = true)
    List<OpcaoProjection> buscarComandos();

    @Query(value = """
SELECT DISTINCT e.oracle_id AS valor, e.nome AS nome
FROM esquadra e
WHERE e.oracle_id IN (SELECT DISTINCT o.id_esquadra FROM sgo_ocorrencia o WHERE o.id_esquadra IS NOT NULL)
ORDER BY e.nome
""", nativeQuery = true)
    List<OpcaoProjection> buscarUnidades();

    @Query(value = """
SELECT
    o.oracle_id AS oracleId,
    o.num_ocorrencia AS numOcorrencia,
    o.data_registo AS dataRegisto,
    COALESCE(tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    o.tp_auto AS tpAuto,
    COALESCE(o.num_processo_sij, '') AS numProcesso,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(o.estado, '') AS estado,
    COALESCE(o.motivo, '') AS motivo,
    'REGISTADO PELO AGENTE' AS condicao
FROM sgo_ocorrencia o
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
WHERE o.id_agente IN (:ids)
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<AgenteOcorrenciaProjection> buscarOcorrenciasRegistadasPorAgente(
            @Param("ids") List<Long> ids,
            @Param("tpAuto") String tpAuto
    );

    @Query(value = """
SELECT
    o.oracle_id AS oracleId,
    o.num_ocorrencia AS numOcorrencia,
    o.data_registo AS dataRegisto,
    COALESCE(tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    o.tp_auto AS tpAuto,
    COALESCE(o.num_processo_sij, '') AS numProcesso,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(o.estado, '') AS estado,
    COALESCE(o.motivo, '') AS motivo,
    'QUEIXA_AUTO_DENUNCIA' AS condicao
FROM sgo_ocorrencia o
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
WHERE o.tp_auto IN ('1','4')
  AND (:todas = true OR o.id_agente IN (:ids))
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<AgenteOcorrenciaProjection> buscarQueixasPorAgente(
            @Param("ids") List<Long> ids,
            @Param("todas") boolean todas,
            @Param("tpAuto") String tpAuto
    );

    @Query(value = """
SELECT
    o.oracle_id AS oracleId,
    o.num_ocorrencia AS numOcorrencia,
    o.data_registo AS dataRegisto,
    COALESCE(tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    o.tp_auto AS tpAuto,
    COALESCE(o.num_processo_sij, '') AS numProcesso,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(o.estado, '') AS estado,
    COALESCE(o.motivo, '') AS motivo,
    COALESCE(
        CASE op.condicao
            WHEN '1' THEN 'Denunciante'
            WHEN '2' THEN 'Ofendido'
            WHEN '3' THEN 'Denunciado'
            WHEN '4' THEN 'Testemunha'
            WHEN '5' THEN 'Detido'
            WHEN '6' THEN 'Detentor'
            WHEN '7' THEN 'Agente Atuante'
            WHEN '8' THEN 'Participante'
            ELSE 'ASSOCIADO AO AGENTE'
        END,
        'ASSOCIADO AO AGENTE'
    ) AS condicao
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
WHERE op.oracle_id_pessoa IN (:ids)
  AND op.condicao IN (:condicoes)
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<AgenteOcorrenciaProjection> buscarOcorrenciasAssociadasAoAgente(
            @Param("ids") List<Long> ids,
            @Param("condicoes") List<String> condicoes,
            @Param("tpAuto") String tpAuto
    );

    @Query(value = """
SELECT
    o.oracle_id AS oracleId,
    o.num_ocorrencia AS numOcorrencia,
    o.data_registo AS dataRegisto,
    COALESCE(tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    o.tp_auto AS tpAuto,
    COALESCE(o.num_processo_sij, '') AS numProcesso,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(o.estado, '') AS estado,
    COALESCE(o.motivo, '') AS motivo,
    COALESCE(
        CASE op.condicao
            WHEN '1' THEN 'Denunciante'
            WHEN '2' THEN 'Ofendido'
            WHEN '3' THEN 'Denunciado'
            WHEN '4' THEN 'Testemunha'
            WHEN '5' THEN 'Detido'
            WHEN '6' THEN 'Detentor'
            WHEN '7' THEN 'Agente Atuante'
            WHEN '8' THEN 'Participante'
            ELSE 'ASSOCIADO AO AGENTE'
        END,
        'ASSOCIADO AO AGENTE'
    ) AS condicao
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
WHERE op.oracle_id_pessoa IN (:ids)
  AND (
        (o.tp_auto = '1' AND op.condicao = '3')
        OR (o.tp_auto = '4' AND op.condicao = '3')
        OR (o.tp_auto = '3' AND op.condicao = '5')
  )
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<AgenteOcorrenciaProjection> buscarOcorrenciasContraAgente(
            @Param("ids") List<Long> ids,
            @Param("tpAuto") String tpAuto
    );
}
