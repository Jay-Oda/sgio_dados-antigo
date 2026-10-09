package cv.pn.processmanagement.business.ocorrencia.repository;

import cv.pn.processmanagement.business.ocorrencia.model.SgoOcorrencia;
import cv.pn.processmanagement.business.projection.AgenteOcorrenciaProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaHistoricoProjection;
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

    /*
     * Listagem nacional: primeiro escolhe as ocorrências da página (CTE "pagina") e só depois
     * lê as tabelas auxiliares (tipos, assinaturas, processos) uma única vez para essas linhas.
     * Antes, cada tabela auxiliar era percorrida uma vez por cada linha da página.
     */
@Query(value = """
WITH pagina AS MATERIALIZED (
    SELECT o.oracle_id, o.num_ocorrencia, o.tp_auto, o.data_registo, o.num_processo_sij,
           o.descricao_etapa, o.id_agente,
           COALESCE(e.nome, '') AS unidade,
           COALESCE(cmd.nome, '') AS comando
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
          ) = ANY(string_to_array(:estado, ',')))
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
      AND (:comandoSigla IS NULL OR cmd.sigla = :comandoSigla)
      AND (:unidadeSigla IS NULL OR e.sigla = :unidadeSigla)
    ORDER BY o.data_registo DESC, o.oracle_id DESC
    OFFSET :offset
    LIMIT :size
),
tps AS MATERIALIZED (
    SELECT x.id_ocorrencia,
           t.nome AS tipologia,
           t.naturesa_crime,
           COALESCE(tp_pai.nome, t.nome) AS natureza
    FROM sgo_ocorrencia_tp_ocorrencia x
    JOIN sgo_tp_ocorrencia t ON t.oracle_id = x.id_tp_ocorrencia
    LEFT JOIN sgo_tp_ocorrencia tp_pai ON tp_pai.oracle_id = t.id_pai
    WHERE x.id_ocorrencia IN (SELECT oracle_id FROM pagina)
),
autuantes AS MATERIALIZED (
    SELECT DISTINCT ON (a.id_agente) a.id_agente, a.nome
    FROM assinatura a
    WHERE a.tipo_assinante = 'AUTUANTE'
      AND a.id_agente IN (SELECT id_agente FROM pagina)
    ORDER BY a.id_agente
),
processos AS MATERIALIZED (
    SELECT pr.id_ocorrencia, pr.num_ocorrencia, pr.prazo, pr.data_registo
    FROM processo pr
    WHERE pr.prazo IS NOT NULL AND pr.prazo <> ''
      AND (pr.id_ocorrencia IN (SELECT oracle_id FROM pagina)
           OR pr.num_ocorrencia IN (SELECT num_ocorrencia FROM pagina))
),
prazos AS (
    SELECT DISTINCT ON (p.oracle_id) p.oracle_id, ps.prazo
    FROM pagina p
    JOIN processos ps ON ps.id_ocorrencia = p.oracle_id OR ps.num_ocorrencia = p.num_ocorrencia
    ORDER BY p.oracle_id, ps.data_registo DESC
)
SELECT
    p.oracle_id AS oracleIdOcorrencia,
    p.num_ocorrencia AS numOcorrencia,
    p.tp_auto AS tpAuto,
    CASE p.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    p.data_registo AS dataRegisto,
    p.num_processo_sij AS numProcesso,
    p.unidade AS unidade,
    p.comando AS comando,
    COALESCE(p.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    CASE
        WHEN UPPER(TRIM(COALESCE(p.descricao_etapa, ''))) = 'ARQUIVADO' THEN 'Arquivado'
        WHEN UPPER(TRIM(COALESCE(p.descricao_etapa, ''))) IN ('ENVIADO AO MP', 'ENVIADO AO CRSSM', 'ENVIADO AO EICC') THEN 'Fechado'
        WHEN UPPER(TRIM(COALESCE(p.descricao_etapa, ''))) IN ('AGUARDA ANÁLISE', 'PENDENTE ANÁLISE') THEN 'Em análise'
        ELSE 'Aberto'
    END AS estado,
    CASE
        WHEN UPPER(TRIM(COALESCE(p.descricao_etapa, ''))) = 'ARQUIVADO' THEN 'R'
        WHEN UPPER(TRIM(COALESCE(p.descricao_etapa, ''))) IN ('ENVIADO AO MP', 'ENVIADO AO CRSSM', 'ENVIADO AO EICC') THEN 'F'
        WHEN UPPER(TRIM(COALESCE(p.descricao_etapa, ''))) IN ('AGUARDA ANÁLISE', 'PENDENTE ANÁLISE') THEN 'E'
        ELSE 'A'
    END AS estadoCodigo,
    COALESCE(au.nome, '') AS registradoPor,
    COALESCE(pz.prazo, '') AS prazo,
    COALESCE((
        SELECT CASE t.naturesa_crime
            WHEN 'PUBLICO' THEN 'Público'
            WHEN 'SEMIPUBLICO' THEN 'Semi-público'
            WHEN 'PARTICULAR' THEN 'Particular'
            ELSE 'Criminal'
        END
        FROM tps t
        WHERE t.id_ocorrencia = p.oracle_id
          AND t.naturesa_crime IS NOT NULL AND t.naturesa_crime <> ''
        LIMIT 1
    ), 'Criminal') AS tipo,
    COALESCE((
        SELECT string_agg(DISTINCT t.natureza, ', ' ORDER BY t.natureza)
        FROM tps t
        WHERE t.id_ocorrencia = p.oracle_id
    ), '') AS naturezas,
    COALESCE((
        SELECT string_agg(DISTINCT t.tipologia, ', ' ORDER BY t.tipologia)
        FROM tps t
        WHERE t.id_ocorrencia = p.oracle_id
    ), '') AS tipologias
FROM pagina p
LEFT JOIN autuantes au ON au.id_agente = p.id_agente
LEFT JOIN prazos pz ON pz.oracle_id = p.oracle_id
ORDER BY p.data_registo DESC, p.oracle_id DESC
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
            @Param("comandoSigla") String comandoSigla,
            @Param("unidadeSigla") String unidadeSigla,
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
      ) = ANY(string_to_array(:estado, ',')))
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
  AND (:comandoSigla IS NULL OR cmd.sigla = :comandoSigla)
  AND (:unidadeSigla IS NULL OR e.sigla = :unidadeSigla)
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
            @Param("unidade") Long unidade,
            @Param("comandoSigla") String comandoSigla,
            @Param("unidadeSigla") String unidadeSigla);

    @Query(value = """
SELECT
    o.num_ocorrencia AS numOcorrencia,
    o.descricao_etapa AS descricaoEtapa,
    o.data_registo AS dataRegisto,
    (
        SELECT a.nome
        FROM assinatura a
        WHERE a.id_agente = o.id_agente AND a.tipo_assinante = 'AUTUANTE'
        LIMIT 1
    ) AS registradoPor,
    o.data_analise AS dataAnalise,
    o.user_analise AS userAnalise,
    o.tipo_analise AS tipoAnalise,
    o.obs_analise AS obsAnalise,
    o.data_despacho AS dataDespacho,
    o.user_despacho AS userDespacho,
    o.tipo_despacho AS tipoDespacho,
    o.obs_despacho AS obsDespacho,
    CAST(o.data_enviado AS varchar) AS dataEnviado,
    CAST(o.user_enviado AS varchar) AS userEnviado,
    o.data_envio_sij AS dataEnvioSij,
    o.data_arquiva AS dataArquiva,
    o.user_arquiva AS userArquiva,
    o.motivo_arquiva AS motivoArquiva,
    o.data_fecho AS dataFecho,
    o.user_fecho AS userFecho,
    o.tipo_fecho AS tipoFecho
FROM sgo_ocorrencia o
WHERE o.num_ocorrencia = :numOcorrencia
LIMIT 1
""", nativeQuery = true)
    OcorrenciaHistoricoProjection buscarHistoricoOcorrencia(@Param("numOcorrencia") String numOcorrencia);

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
