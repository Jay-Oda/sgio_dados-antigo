package cv.pn.processmanagement.business.sigoOcorrenciaPessoa.repository;

import cv.pn.processmanagement.business.projection.AssinaturaAutuanteProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaBuscaProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaCircunstanciaProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaDetalheProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaResumoProjection;
import cv.pn.processmanagement.business.projection.OutroAutoProjection;
import cv.pn.processmanagement.business.projection.PessoaEnvolvedaProjection;
import cv.pn.processmanagement.business.projection.PessoaOcorrenciaProjection;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.model.SgoOcorrenciaPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


public interface SgoOcorrenciaPessoaRepository extends JpaRepository<SgoOcorrenciaPessoa, String> {

  /*  @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO sgo_ocorrencia_pessoa (
            condicao,
            id_pessoa,
            id_ocorrencia,
            oracle_id_pessoa,
            oracle_id_ocorrencia,
            caracteristica,
            id_agente,
            user_create
        )
        VALUES (
            :condicao,
            :idPessoa,
            :idOcorrencia,
            :oracleIdPessoa,
            :oracleIdOcorrencia,
            :caracteristica,
            :idAgente,
            :userCreate
        )
    """, nativeQuery = true)
    void insertOcorrenciaPessoa(
            @Param("condicao") String condicao,
            @Param("idPessoa") Long idPessoa,
            @Param("idOcorrencia") Long idOcorrencia,
            @Param("oracleIdPessoa") Long oracleIdPessoa,
            @Param("oracleIdOcorrencia") Long oracleIdOcorrencia,
            @Param("caracteristica") String caracteristica,
            @Param("idAgente") Long idAgente,
            @Param("userCreate") String userCreate
    );*/
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO sgo_ocorrencia_pessoa (
            condicao,
            id_pessoa,
            id_ocorrencia,
            oracle_id_pessoa,
            oracle_id_ocorrencia,
            caracteristica,
            id_agente,
            user_create
        )
        VALUES (
            :condicao,
            :idPessoa,
            :idOcorrencia,
            :oracleIdPessoa,
            :oracleIdOcorrencia,
            :caracteristica,
            :idAgente,
            :userCreate
        )
        ON CONFLICT (oracle_id_pessoa, oracle_id_ocorrencia, condicao) DO NOTHING
    """, nativeQuery = true)
    void insertIgnoreDuplicate(
            @Param("condicao") String condicao,
            @Param("idPessoa") Long idPessoa,
            @Param("idOcorrencia") Long idOcorrencia,
            @Param("oracleIdPessoa") Long oracleIdPessoa,
            @Param("oracleIdOcorrencia") Long oracleIdOcorrencia,
            @Param("caracteristica") String caracteristica,
            @Param("idAgente") Long idAgente,
            @Param("userCreate") String userCreate
    );
   // List<SgoOcorrenciaPessoa> findByOracleIdPessoa(Long oracleIdPessoa);
    //1 boolean existsByOracleIdPessoa(Long idPessoa);

  //  boolean existsByOracleIdPessoa(Long oracleIdPessoa);


    /*@Query(value = """
        SELECT
            o.num_ocorrencia           AS numOcorrencia,
            COALESCE(tp.nome, '')     AS natureza,
            COALESCE(o.tp_auto, '')   AS autoPrincipal,
            COALESCE(op.condicao, '') AS condicao,
            COALESCE(tp.nome, '')     AS tipologiaCrime,
            o.data_registo            AS dataRegisto,
            COALESCE(e.nome, '')     AS unidade,
            COALESCE(o.estado, '')   AS fase
        FROM sgo_ocorrencia_pessoa op
        JOIN sgo_ocorrencia o
          ON o.oracle_id = op.oracle_id_ocorrencia

        LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
          ON otp.id_ocorrencia = o.oracle_id

        LEFT JOIN sgo_tp_ocorrencia tp
          ON tp.oracle_id = otp.id_tp_ocorrencia

        LEFT JOIN esquadra e
          ON e.oracle_id = o.id_esquadra

        WHERE op.oracle_id_pessoa = :oracleIdPessoa
        ORDER BY o.data_registo DESC
    """, nativeQuery = true)
    List<OcorrenciaResumoProjection> listarOcorrenciasDaPessoa(
            @Param("oracleIdPessoa") Long oracleIdPessoa
    );*/

    //esse query resulta
   /* @Query(value = """
    SELECT
        o.num_ocorrencia           AS numOcorrencia,
        COALESCE(tp.nome, '')     AS natureza,
        COALESCE(o.tp_auto, '')   AS autoPrincipal,
        COALESCE(op.condicao, '') AS condicao,
        COALESCE(tp.nome, '')     AS tipologiaCrime,
        o.data_registo            AS dataRegisto,
        COALESCE(e.nome, '')     AS unidade,
        COALESCE(o.estado, '')   AS fase
    FROM sgo_ocorrencia_pessoa op
    JOIN sgo_ocorrencia o
      ON o.oracle_id = op.oracle_id_ocorrencia

    LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
      ON otp.id_ocorrencia = o.oracle_id

    LEFT JOIN sgo_tp_ocorrencia tp
      ON tp.oracle_id = otp.id_tp_ocorrencia

    LEFT JOIN esquadra e
      ON e.oracle_id = o.id_esquadra

    WHERE op.oracle_id_pessoa = :oracleIdPessoa
    ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<OcorrenciaResumoProjection> listarOcorrenciasDaPessoa(
            @Param("oracleIdPessoa") Long oracleIdPessoa
    );*/

//isto stava a funcionar bem
 /*@Query(value = """
SELECT
    o.num_ocorrencia AS numOcorrencia,
    COALESCE(tp.nome, '') AS natureza,

    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    
    
    COALESCE(spp.condicao, 'Desconhecido') AS condicao,

  
    o.data_registo AS dataRegisto,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase

FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
    
 LEFT JOIN sgo_peca_pessoa spp
   ON spp.id_pessoa = op.oracle_id_pessoa
                                                                          

LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id

LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia

LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra

WHERE op.oracle_id_pessoa = :oracleIdPessoa
ORDER BY o.data_registo DESC
""", nativeQuery = true)
 List<OcorrenciaResumoProjection> listarOcorrenciasDaPessoa(
         @Param("oracleIdPessoa") Long oracleIdPessoa
 );*/

    @Query(value = """
SELECT
    o.num_ocorrencia AS numOcorrencia,
    COALESCE(tp.nome, '') AS natureza,
    o.oracle_id AS oracleIdOcorrencia,
    o.motivo AS motivo,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,

    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,
    
        
  --  CONDIÇÃO INTELIGENTE
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'

        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'

        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'

        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'

        ELSE 'Desconhecido'
    END AS condicao,
   
    o.data_registo AS dataRegisto,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(pa.nome, '') AS autuante
    
    
FROM sgo_ocorrencia_pessoa op


 JOIN sgo_ocorrencia o
       ON o.oracle_id = op.oracle_id_ocorrencia
                                                     
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
LEFT JOIN sigo_pessoa pa
    ON pa.oracle_id = o.id_agente
    
    

WHERE op.oracle_id_pessoa = :oracleId
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
  AND (
        (o.tp_auto = '1' AND op.condicao = '3')
        OR (o.tp_auto = '2' AND op.condicao = '3')
        OR (o.tp_auto = '3' AND op.condicao IN ('5','6'))
        OR (o.tp_auto = '4' AND op.condicao = '3')
        OR (o.tp_auto = '5' AND op.condicao = '2')
  )
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<OcorrenciaResumoProjection> buscarAntecedentesPorPessoa(
            @Param("oracleId") Long oracleId,
            @Param("tpAuto") String tpAuto);

    @Query(value = """
SELECT
    o.num_ocorrencia AS numOcorrencia,
    COALESCE(tp.nome, '') AS natureza,
    o.oracle_id AS oracleIdOcorrencia,
    o.motivo AS motivo,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,

    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END AS autoPrincipal,

    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '3' AND op.condicao = '5' THEN 'Detido'
        ELSE 'Desconhecido'
    END AS condicao,

    o.data_registo AS dataRegisto,
    COALESCE(e.nome, '') AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(pa.nome, '') AS autuante


FROM sgo_ocorrencia_pessoa op

 JOIN sgo_ocorrencia o
       ON o.oracle_id = op.oracle_id_ocorrencia

LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
LEFT JOIN sigo_pessoa pa
    ON pa.oracle_id = o.id_agente

WHERE op.oracle_id_pessoa = :oracleId
  AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)
  AND (
        (o.tp_auto = '1' AND op.condicao = '3')
        OR (o.tp_auto = '3' AND op.condicao = '5')
        OR (o.tp_auto = '4' AND op.condicao = '3')
  )
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<OcorrenciaResumoProjection> buscarAntecedentesSujeitoPorPessoa(
            @Param("oracleId") Long oracleId,
            @Param("tpAuto") String tpAuto);

    @Query(value = """
SELECT DISTINCT
    op.oracle_id_ocorrencia AS oracleIdOcorrencia,
    COALESCE(p.nome, 'DESCONHECIDO') AS nome
FROM sgo_ocorrencia_pessoa op
LEFT JOIN sigo_pessoa p
    ON p.oracle_id = op.oracle_id_pessoa
WHERE op.condicao = '6'
  AND op.oracle_id_ocorrencia IN (:ids)
ORDER BY oracleIdOcorrencia, nome
""", nativeQuery = true)
    List<PessoaOcorrenciaProjection> buscarDetentoresDasOcorrencias(
            @Param("ids") List<Long> ids);

    @Query("""
    SELECT COUNT(op) > 0
    FROM SgoOcorrenciaPessoa op
    JOIN SgoOcorrencia o
       ON o.oracleId = op.oracleIdOcorrencia
    WHERE op.oracleIdPessoa = :oracleIdPessoa
      AND (:tpAuto IS NULL OR o.tpAuto = :tpAuto)
      AND (
            (o.tpAuto = '1' AND op.condicao = '3')
            OR (o.tpAuto = '2' AND op.condicao = '3')
            OR (o.tpAuto = '3' AND op.condicao IN ('5','6'))
            OR (o.tpAuto = '4' AND op.condicao = '3')
            OR (o.tpAuto = '5' AND op.condicao = '2')
      )
""")
    boolean existsAntecedenteReal(@Param("oracleIdPessoa") Long oracleIdPessoa,
                                  @Param("tpAuto") String tpAuto);

    @Query("""
    SELECT COUNT(DISTINCT o.oracleId)
    FROM SgoOcorrenciaPessoa op
    JOIN SgoOcorrencia o
       ON o.oracleId = op.oracleIdOcorrencia
    WHERE op.oracleIdPessoa = :oracleIdPessoa
      AND (:tpAuto IS NULL OR o.tpAuto = :tpAuto)
      AND (
            (o.tpAuto = '1' AND op.condicao = '3')
            OR (o.tpAuto = '2' AND op.condicao = '3')
            OR (o.tpAuto = '3' AND op.condicao IN ('5','6'))
            OR (o.tpAuto = '4' AND op.condicao = '3')
            OR (o.tpAuto = '5' AND op.condicao = '2')
      )
""")
    long contarAntecedentesReais(@Param("oracleIdPessoa") Long oracleIdPessoa,
                                 @Param("tpAuto") String tpAuto);

    @Query("""
    SELECT COUNT(op) > 0
    FROM SgoOcorrenciaPessoa op
    JOIN SgoOcorrencia o
       ON o.oracleId = op.oracleIdOcorrencia
    WHERE op.oracleIdPessoa = :oracleIdPessoa
      AND (:tpAuto IS NULL OR o.tpAuto = :tpAuto)
      AND (
            (o.tpAuto = '1' AND op.condicao = '3')
            OR (o.tpAuto = '3' AND op.condicao = '5')
            OR (o.tpAuto = '4' AND op.condicao = '3')
      )
""")
    boolean existsAntecedenteSujeitoReal(@Param("oracleIdPessoa") Long oracleIdPessoa,
                                         @Param("tpAuto") String tpAuto);

    @Query("""
    SELECT COUNT(DISTINCT o.oracleId)
    FROM SgoOcorrenciaPessoa op
    JOIN SgoOcorrencia o
       ON o.oracleId = op.oracleIdOcorrencia
    WHERE op.oracleIdPessoa = :oracleIdPessoa
      AND (:tpAuto IS NULL OR o.tpAuto = :tpAuto)
      AND (
            (o.tpAuto = '1' AND op.condicao = '3')
            OR (o.tpAuto = '3' AND op.condicao = '5')
            OR (o.tpAuto = '4' AND op.condicao = '3')
      )
""")
    long contarAntecedentesSujeitoReais(@Param("oracleIdPessoa") Long oracleIdPessoa,
                                        @Param("tpAuto") String tpAuto);

    @Query(value = """
SELECT
    id_ocorrencia AS oracleIdOcorrencia,
    COALESCE(circustancia, '') AS circustancia,
    COALESCE(resposta, '') AS resposta
FROM sgo_ocorrencia_circustancias
WHERE id_ocorrencia IN (:ids)
ORDER BY id_ocorrencia
""", nativeQuery = true)
    List<OcorrenciaCircunstanciaProjection> buscarCircunstanciasDasOcorrencias(
            @Param("ids") List<Long> ids
    );

    @Query(value = """
SELECT DISTINCT
    op.oracle_id_pessoa AS oracleIdPessoa,
    COALESCE(p.nome, 'DESCONHECIDO') AS nome,
    COALESCE(p.num, '') AS num,
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
            ELSE 'DESCONHECIDO'
        END,
        'DESCONHECIDO'
    ) AS condicao,
    o.oracle_id AS oracleIdOcorrencia,
    o.num_ocorrencia AS numOcorrencia,
    o.data_registo AS dataRegisto
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sigo_pessoa p
    ON p.oracle_id = op.oracle_id_pessoa
WHERE o.id_agente IN (:ids)
   OR op.oracle_id_pessoa IN (:ids)
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<PessoaOcorrenciaProjection> buscarPessoasDasOcorrenciasDoAgente(
            @Param("ids") List<Long> ids
    );

    @Query(value = """
SELECT
    op.oracle_id_ocorrencia AS oracleIdOcorrencia,
    op.oracle_id_pessoa AS oracleIdPessoa,
    COALESCE(p.nome, 'DESCONHECIDO') AS nome,
    COALESCE(p.num, '') AS num,
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
            ELSE 'DESCONHECIDO'
        END,
        'DESCONHECIDO'
    ) AS condicao
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sigo_pessoa p
    ON p.oracle_id = op.oracle_id_pessoa
WHERE op.oracle_id_ocorrencia IN (:ids)
ORDER BY op.oracle_id_ocorrencia, p.nome
""", nativeQuery = true)
    List<PessoaOcorrenciaProjection> buscarEnvolvidosDasOcorrencias(
            @Param("ids") List<Long> ids
    );
   /* List<OcorrenciaResumoProjection> buscarOcorrenciasPorOracleIds(
            @Param("oracleId") Long oracleId
    );*/

    //funcional
    /*LEFT JOIN sgo_peca_pessoa spp
    ON spp.id_pessoa = op.oracle_id_pessoa*/

    // funcional
    /* WHERE op.oracle_id_pessoa = :oracleId*/

    //ISTO E FUNCIONBAL
   /* FROM sgo_ocorrencia_pessoa op
    JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia*/
   //funcional
    /*COALESCE(spp.condicao, 'Desconhecido') AS condicao,*/
   //para grupar e contar
   /* GROUP BY
    o.num_ocorrencia, tp.nome, o.tp_auto, spp.condicao,
    o.data_registo, e.nome, o.descricao_etapa*/

/*   IN (:oracleIds)*/

   /* CASE o.estado
    WHEN 'A' THEN 'Enviado ao MP'
    WHEN 'P' THEN 'Enviado ao EICC '
    WHEN 'C' THEN 'Aguarda Análise'
    WHEN 'C' THEN 'Arquivado'
    WHEN 'C' THEN 'Pendente Análise'
    WHEN 'C' THEN 'Retornando ao Registo'
    ELSE 'Em análise'
    END AS fase*/

    /*--COALESCE(i.nome, 'Desconhecido') AS condicao,*/

/*    LEFT JOIN intervinientes i
    ON CAST(op.condicao AS VARCHAR) = i.id*/

   /* LEFT JOIN intervinientes i
    ON i.id = CAST(op.condicao AS VARCHAR)*/
/*
    LEFT JOIN sgo_peca_intervenente spi
    ON spi.id_peca = spp.id_processo_peca*/

    /*COALESCE(spi.condicao, 'Desconhecido') AS condicao,*/

    /* -- CONDIÇÃO REAL DA PESSOA
    CASE op.condicao
    WHEN '1' THEN 'O autado'
    WHEN '2' THEN 'Denunciante'
    WHEN '3' THEN 'Denunciado'
    WHEN '4' THEN 'Denunciante'
    WHEN '5' THEN 'Denunciado'
    WHEN '6' THEN 'Detentor'
    WHEN '7' THEN 'O Autuante'
    WHEN '8' THEN 'Detentor'
    WHEN '9' THEN 'Detido'
    WHEN '10' THEN 'Testemunha'
    ELSE 'Desconhecido'
    END AS condicao,*/

    @Query(value = """
SELECT DISTINCT
    p.oracle_id               AS oracleId,
    p.nome                    AS nome,
    p.pai                     AS pai,
    p.mae                     AS mae,
    p.dt_nascimento           AS dtNascimento,
    o.num_ocorrencia          AS numOcorrencia,
    COALESCE(tp_pai.nome, tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END                       AS autoPrincipal,
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'
        WHEN o.tp_auto = '3' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '4' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'
        ELSE 'Desconhecido'
    END                       AS condicao,
    COALESCE(tp.nome, '')     AS tipologiaCrime,
    o.data_registo            AS dataRegisto,
    COALESCE(e.nome, '')      AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(aut.nome, '')    AS registradoPor,
    CASE
        WHEN tp.naturesa_crime = 'PUBLICO' THEN 'Público'
        WHEN tp.naturesa_crime = 'SEMIPUBLICO' THEN 'Semi-público'
        WHEN tp.naturesa_crime = 'PARTICULAR' THEN 'Particular'
        ELSE 'Criminal'
    END                       AS tipo,
    CASE o.estado
        WHEN 'A' THEN 'Aberto'
        WHEN 'F' THEN 'Fechado'
        WHEN 'E' THEN 'Em análise'
        WHEN 'R' THEN 'Arquivado'
        ELSE COALESCE(o.estado, 'Aberto')
    END                       AS estado
FROM sigo_pessoa p
JOIN sgo_ocorrencia_pessoa op
    ON op.oracle_id_pessoa = p.oracle_id
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN sgo_tp_ocorrencia tp_pai
    ON tp_pai.oracle_id = tp.id_pai
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
LEFT JOIN assinatura aut
    ON aut.id_agente = o.id_agente AND aut.tipo_assinante = 'AUTUANTE'
WHERE p.nome ILIKE :nome
  AND (:pai IS NULL OR p.pai ILIKE :pai)
  AND (:mae IS NULL OR p.mae ILIKE :mae)
  AND (:dtNasc IS NULL OR p.dt_nascimento = CAST(:dtNasc AS date))
  AND (
        (o.tp_auto = '1' AND op.condicao = '3')
        OR (o.tp_auto = '3' AND op.condicao = '5')
        OR (o.tp_auto = '4' AND op.condicao = '3')
  )
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<OcorrenciaBuscaProjection> buscarAntecedentesPorNome(
            @Param("nome") String nome,
            @Param("pai") String pai,
            @Param("mae") String mae,
            @Param("dtNasc") String dtNasc);

    @Query(value = """
SELECT DISTINCT
    p.oracle_id               AS oracleId,
    p.nome                    AS nome,
    p.pai                     AS pai,
    p.mae                     AS mae,
    p.dt_nascimento           AS dtNascimento,
    o.num_ocorrencia          AS numOcorrencia,
    COALESCE(tp_pai.nome, tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END                       AS autoPrincipal,
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'
        WHEN o.tp_auto = '3' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '4' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'
        ELSE 'Desconhecido'
    END                       AS condicao,
    COALESCE(tp.nome, '')     AS tipologiaCrime,
    o.data_registo            AS dataRegisto,
    COALESCE(e.nome, '')      AS unidade,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    COALESCE(reg.nome, '')    AS registradoPor,
    CASE
        WHEN tp.naturesa_crime = 'PUBLICO' THEN 'Público'
        WHEN tp.naturesa_crime = 'SEMIPUBLICO' THEN 'Semi-público'
        WHEN tp.naturesa_crime = 'PARTICULAR' THEN 'Particular'
        ELSE 'Criminal'
    END                       AS tipo,
    CASE o.estado
        WHEN 'A' THEN 'Aberto'
        WHEN 'F' THEN 'Fechado'
        WHEN 'E' THEN 'Em análise'
        WHEN 'R' THEN 'Arquivado'
        ELSE COALESCE(o.estado, 'Aberto')
    END                       AS estado,
    COALESCE((
        SELECT pr.prazo
        FROM processo pr
        WHERE (pr.id_ocorrencia = o.oracle_id OR pr.num_ocorrencia = o.num_ocorrencia)
          AND pr.prazo IS NOT NULL AND pr.prazo <> ''
        ORDER BY pr.data_registo DESC
        LIMIT 1
    ), '') AS prazo
FROM sigo_pessoa p
JOIN sgo_ocorrencia_pessoa op
    ON op.oracle_id_pessoa = p.oracle_id
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN sgo_tp_ocorrencia tp_pai
    ON tp_pai.oracle_id = tp.id_pai
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
LEFT JOIN assinatura reg
    ON reg.id_agente = o.id_agente AND reg.tipo_assinante = 'AUTUANTE'
WHERE o.num_ocorrencia ILIKE :numOcorrencia
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<OcorrenciaBuscaProjection> buscarPorNumOcorrencia(
            @Param("numOcorrencia") String numOcorrencia);

    @Query(value = """
SELECT DISTINCT
    o.oracle_id               AS oracleIdOcorrencia,
    o.num_ocorrencia          AS numOcorrencia,
    COALESCE(tp_pai.nome, tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END                       AS autoPrincipal,
    COALESCE(e.nome, '')      AS unidade,
    COALESCE(com.nome, '')    AS comando,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    o.data_registo            AS dataRegisto,
    o.motivo                  AS motivo,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,
    COALESCE(loc.nome_oficial, loc.nome, '') AS local,
    COALESCE(sa.nome, '')     AS autuante,
    sa.categoria              AS categoriaAutuante
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN sgo_tp_ocorrencia tp_pai
    ON tp_pai.oracle_id = tp.id_pai
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
LEFT JOIN esquadra com
    ON com.oracle_id = e.self_id
LEFT JOIN glb_geografica loc
    ON loc.codigo = CAST(o.local AS VARCHAR)
LEFT JOIN assinatura sa
    ON sa.id_agente = o.id_agente
    AND sa.tipo_assinante = 'AUTUANTE'
    AND sa.nome IS NOT NULL
WHERE o.num_ocorrencia = :numOcorrencia
LIMIT 1
""", nativeQuery = true)
    List<OcorrenciaDetalheProjection> detalharPorNumOcorrencia(
            @Param("numOcorrencia") String numOcorrencia);

    @Query(value = """
(SELECT DISTINCT ON (p.oracle_id, op.condicao)
    p.oracle_id     AS oracleId,
    p.nome          AS nome,
    p.alcunhas      AS alcunhas,
    p.pai           AS pai,
    p.mae           AS mae,
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '5' THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'
        WHEN o.tp_auto = '3' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '4' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao = '3' THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '5' THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'
        WHEN o.tp_auto = '5' AND op.condicao = '8' THEN 'Agente Atuante'
        ELSE 'Desconhecido'
    END             AS condicao,
    p.dt_nascimento AS dtNascimento,
    p.idade         AS idade,
    p.estado_civil  AS estadoCivil,
    p.contacto      AS contacto,
    p.morada        AS morada,
    p.nacionalidade AS nacionalidade,
    p.naturalidade  AS naturalidade,
    p.profissao     AS profissao,
    ag.categoria    AS categoriaEfetivo,
    COALESCE(ge.nome, '') AS esquadraEfetivo,
    CASE
        WHEN asse.tipo_assinante IS NOT NULL THEN REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
            asse.tipo_assinante,
            '&lt;', '<'), '&gt;', '>'), '&amp;', '&'), '&quot;', '"'), '&#39;', ''''), '&nbsp;', ' ')
        ELSE NULL
    END             AS tipoAssinante,
    asse.categoria  AS categoriaAssinatura,
    asse.assina     AS assina,
    asse.sabe_assinar AS sabeAssinar,
    asse.assinatura AS assinatura
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sigo_pessoa p
    ON p.oracle_id = op.oracle_id_pessoa
LEFT JOIN LATERAL (
    SELECT a1.tipo_assinante, a1.categoria, a1.assina, a1.sabe_assinar, a1.assinatura, a1.date_create
    FROM assinatura a1
    WHERE a1.id_pessoa = p.oracle_id AND a1.nome IS NOT NULL
    ORDER BY a1.date_create DESC
    LIMIT 1
) asse ON true
LEFT JOIN agente ag
    ON ag.nome = p.nome
LEFT JOIN esquadra ge
    ON ge.oracle_id = ag.id_esquadra
WHERE o.num_ocorrencia = :numOcorrencia
ORDER BY p.oracle_id, op.condicao, asse.date_create DESC)
UNION ALL
(SELECT DISTINCT ON (UPPER(COALESCE(sp.nome, sa.nome)))
    COALESCE(sp.oracle_id, 0) AS oracleId,
    COALESCE(sp.nome, sa.nome) AS nome,
    sp.alcunhas        AS alcunhas,
    sp.pai             AS pai,
    sp.mae             AS mae,
    'Autuante'         AS condicao,
    sp.dt_nascimento   AS dtNascimento,
    sp.idade           AS idade,
    sp.estado_civil    AS estadoCivil,
    sp.contacto        AS contacto,
    sp.morada          AS morada,
    sp.nacionalidade   AS nacionalidade,
    sp.naturalidade    AS naturalidade,
    sp.profissao       AS profissao,
    ag2.categoria      AS categoriaEfetivo,
    COALESCE(ge2.nome, '') AS esquadraEfetivo,
    'Autuante'         AS tipoAssinante,
    sa.categoria       AS categoriaAssinatura,
    sa.assina          AS assina,
    sa.sabe_assinar    AS sabeAssinar,
    sa.assinatura      AS assinatura
FROM sgo_ocorrencia o
JOIN LATERAL (
    SELECT a2.id_agente, a2.nome, a2.categoria, a2.assina, a2.sabe_assinar, a2.assinatura, a2.date_create
    FROM assinatura a2
    WHERE a2.id_agente = o.id_agente
      AND a2.tipo_assinante = 'AUTUANTE'
      AND a2.nome IS NOT NULL
    ORDER BY a2.date_create DESC
    LIMIT 1
) sa ON true
LEFT JOIN sigo_pessoa sp
    ON UPPER(sp.nome) = UPPER(sa.nome)
LEFT JOIN agente ag2
    ON UPPER(ag2.nome) = UPPER(sa.nome)
LEFT JOIN esquadra ge2
    ON ge2.oracle_id = ag2.id_esquadra
WHERE o.num_ocorrencia = :numOcorrencia
AND NOT EXISTS (
    SELECT 1 FROM sgo_ocorrencia_pessoa op2
    JOIN sgo_ocorrencia o2 ON o2.oracle_id = op2.oracle_id_ocorrencia
    LEFT JOIN sigo_pessoa sp2 ON sp2.oracle_id = op2.oracle_id_pessoa
    WHERE o2.num_ocorrencia = :numOcorrencia
    AND UPPER(sp2.nome) = UPPER(sa.nome)
)
ORDER BY UPPER(COALESCE(sp.nome, sa.nome)), sp.oracle_id NULLS LAST, ag2.date_create NULLS LAST)
ORDER BY nome
""", nativeQuery = true)
    List<PessoaEnvolvedaProjection> buscarPessoasPorNumOcorrencia(
            @Param("numOcorrencia") String numOcorrencia);

    /*
     * Mesmas queries de detalharPorNumOcorrencia e buscarPessoasPorNumOcorrencia, mas filtradas pelo
     * oracle_id da ocorrência (com índice) em vez do número (sem índice): ~3 s em vez de ~27 s na base de teste.
     * Em detalharPorOracleId, autuante/categoriaAutuante vêm a NULL: não são usados no detalhe e a junção à
     * assinatura (sem índice em id_agente) lia a tabela inteira (~1 s).
     */
    @Query(value = """
SELECT DISTINCT
    o.oracle_id               AS oracleIdOcorrencia,
    o.num_ocorrencia          AS numOcorrencia,
    COALESCE(tp_pai.nome, tp.nome, '') AS natureza,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END                       AS autoPrincipal,
    COALESCE(e.nome, '')      AS unidade,
    COALESCE(com.nome, '')    AS comando,
    COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase,
    o.data_registo            AS dataRegisto,
    o.motivo                  AS motivo,
    COALESCE(o.ponto_referencia, '') AS pontoReferencia,
    COALESCE(loc.nome_oficial, loc.nome, '') AS local,
    CAST(NULL AS VARCHAR)     AS autuante,
    CAST(NULL AS VARCHAR)     AS categoriaAutuante
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp
    ON otp.id_ocorrencia = o.oracle_id
LEFT JOIN sgo_tp_ocorrencia tp
    ON tp.oracle_id = otp.id_tp_ocorrencia
LEFT JOIN sgo_tp_ocorrencia tp_pai
    ON tp_pai.oracle_id = tp.id_pai
LEFT JOIN esquadra e
    ON e.oracle_id = o.id_esquadra
LEFT JOIN esquadra com
    ON com.oracle_id = e.self_id
LEFT JOIN glb_geografica loc
    ON loc.codigo = CAST(o.local AS VARCHAR)
WHERE o.oracle_id = :oracleId
LIMIT 1
""", nativeQuery = true)
    List<OcorrenciaDetalheProjection> detalharPorOracleId(
            @Param("oracleId") Long oracleId);

    @Query(value = """
(SELECT DISTINCT ON (p.oracle_id, op.condicao)
    p.oracle_id     AS oracleId,
    p.nome          AS nome,
    p.alcunhas      AS alcunhas,
    p.pai           AS pai,
    p.mae           AS mae,
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '5' THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'
        WHEN o.tp_auto = '3' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '4' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao = '3' THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '5' THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'
        WHEN o.tp_auto = '5' AND op.condicao = '8' THEN 'Agente Atuante'
        ELSE 'Desconhecido'
    END             AS condicao,
    p.dt_nascimento AS dtNascimento,
    p.idade         AS idade,
    p.estado_civil  AS estadoCivil,
    p.contacto      AS contacto,
    p.morada        AS morada,
    p.nacionalidade AS nacionalidade,
    p.naturalidade  AS naturalidade,
    p.profissao     AS profissao,
    ag.categoria    AS categoriaEfetivo,
    COALESCE(ge.nome, '') AS esquadraEfetivo,
    CASE
        WHEN asse.tipo_assinante IS NOT NULL THEN REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
            asse.tipo_assinante,
            '&lt;', '<'), '&gt;', '>'), '&amp;', '&'), '&quot;', '"'), '&#39;', ''''), '&nbsp;', ' ')
        ELSE NULL
    END             AS tipoAssinante,
    asse.categoria  AS categoriaAssinatura,
    asse.assina     AS assina,
    asse.sabe_assinar AS sabeAssinar,
    asse.assinatura AS assinatura
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sigo_pessoa p
    ON p.oracle_id = op.oracle_id_pessoa
LEFT JOIN LATERAL (
    SELECT a1.tipo_assinante, a1.categoria, a1.assina, a1.sabe_assinar, a1.assinatura, a1.date_create
    FROM assinatura a1
    WHERE a1.id_pessoa = p.oracle_id AND a1.nome IS NOT NULL
    ORDER BY a1.date_create DESC
    LIMIT 1
) asse ON true
LEFT JOIN agente ag
    ON ag.nome = p.nome
LEFT JOIN esquadra ge
    ON ge.oracle_id = ag.id_esquadra
WHERE o.oracle_id = :oracleId
ORDER BY p.oracle_id, op.condicao, asse.date_create DESC)
UNION ALL
(SELECT DISTINCT ON (UPPER(COALESCE(sp.nome, sa.nome)))
    COALESCE(sp.oracle_id, 0) AS oracleId,
    COALESCE(sp.nome, sa.nome) AS nome,
    sp.alcunhas        AS alcunhas,
    sp.pai             AS pai,
    sp.mae             AS mae,
    'Autuante'         AS condicao,
    sp.dt_nascimento   AS dtNascimento,
    sp.idade           AS idade,
    sp.estado_civil    AS estadoCivil,
    sp.contacto        AS contacto,
    sp.morada          AS morada,
    sp.nacionalidade   AS nacionalidade,
    sp.naturalidade    AS naturalidade,
    sp.profissao       AS profissao,
    ag2.categoria      AS categoriaEfetivo,
    COALESCE(ge2.nome, '') AS esquadraEfetivo,
    'Autuante'         AS tipoAssinante,
    sa.categoria       AS categoriaAssinatura,
    sa.assina          AS assina,
    sa.sabe_assinar    AS sabeAssinar,
    sa.assinatura      AS assinatura
FROM sgo_ocorrencia o
JOIN LATERAL (
    SELECT a2.id_agente, a2.nome, a2.categoria, a2.assina, a2.sabe_assinar, a2.assinatura, a2.date_create
    FROM assinatura a2
    WHERE a2.id_agente = o.id_agente
      AND a2.tipo_assinante = 'AUTUANTE'
      AND a2.nome IS NOT NULL
    ORDER BY a2.date_create DESC
    LIMIT 1
) sa ON true
LEFT JOIN sigo_pessoa sp
    ON UPPER(sp.nome) = UPPER(sa.nome)
LEFT JOIN agente ag2
    ON UPPER(ag2.nome) = UPPER(sa.nome)
LEFT JOIN esquadra ge2
    ON ge2.oracle_id = ag2.id_esquadra
WHERE o.oracle_id = :oracleId
AND NOT EXISTS (
    SELECT 1 FROM sgo_ocorrencia_pessoa op2
    JOIN sgo_ocorrencia o2 ON o2.oracle_id = op2.oracle_id_ocorrencia
    LEFT JOIN sigo_pessoa sp2 ON sp2.oracle_id = op2.oracle_id_pessoa
    WHERE o2.oracle_id = :oracleId
    AND UPPER(sp2.nome) = UPPER(sa.nome)
)
ORDER BY UPPER(COALESCE(sp.nome, sa.nome)), sp.oracle_id NULLS LAST, ag2.date_create NULLS LAST)
ORDER BY nome
""", nativeQuery = true)
    List<PessoaEnvolvedaProjection> buscarPessoasPorOracleId(
            @Param("oracleId") Long oracleId);

    /*
     * Igual a buscarPessoasPorOracleId, mas a assinatura e a sigo_pessoa (sem índice em id_pessoa, id_agente e nome)
     * só são lidas nos candidatos indicados pelo IndiceAssinaturasSigoAntigo, pela chave primária. As outras
     * condições não mudam, por isso o resultado é o mesmo; passa de 5-11 s para menos de 1 s na base de teste.
     */
    @Query(value = """
(SELECT DISTINCT ON (p.oracle_id, op.condicao)
    p.oracle_id     AS oracleId,
    p.nome          AS nome,
    p.alcunhas      AS alcunhas,
    p.pai           AS pai,
    p.mae           AS mae,
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '5' THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'
        WHEN o.tp_auto = '3' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '4' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao = '3' THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '5' THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'
        WHEN o.tp_auto = '5' AND op.condicao = '8' THEN 'Agente Atuante'
        ELSE 'Desconhecido'
    END             AS condicao,
    p.dt_nascimento AS dtNascimento,
    p.idade         AS idade,
    p.estado_civil  AS estadoCivil,
    p.contacto      AS contacto,
    p.morada        AS morada,
    p.nacionalidade AS nacionalidade,
    p.naturalidade  AS naturalidade,
    p.profissao     AS profissao,
    ag.categoria    AS categoriaEfetivo,
    COALESCE(ge.nome, '') AS esquadraEfetivo,
    CASE
        WHEN asse.tipo_assinante IS NOT NULL THEN REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
            asse.tipo_assinante,
            '&lt;', '<'), '&gt;', '>'), '&amp;', '&'), '&quot;', '"'), '&#39;', ''''), '&nbsp;', ' ')
        ELSE NULL
    END             AS tipoAssinante,
    asse.categoria  AS categoriaAssinatura,
    asse.assina     AS assina,
    asse.sabe_assinar AS sabeAssinar,
    asse.assinatura AS assinatura
FROM sgo_ocorrencia_pessoa op
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
LEFT JOIN sigo_pessoa p
    ON p.oracle_id = op.oracle_id_pessoa
LEFT JOIN LATERAL (
    SELECT a1.tipo_assinante, a1.categoria, a1.assina, a1.sabe_assinar, a1.assinatura, a1.date_create
    FROM assinatura a1
    WHERE a1.id IN (:assinaturas) AND a1.id_pessoa = p.oracle_id AND a1.nome IS NOT NULL
    ORDER BY a1.date_create DESC
    LIMIT 1
) asse ON true
LEFT JOIN agente ag
    ON ag.nome = p.nome
LEFT JOIN esquadra ge
    ON ge.oracle_id = ag.id_esquadra
WHERE o.oracle_id = :oracleId
ORDER BY p.oracle_id, op.condicao, asse.date_create DESC)
UNION ALL
(SELECT DISTINCT ON (UPPER(COALESCE(sp.nome, sa.nome)))
    COALESCE(sp.oracle_id, 0) AS oracleId,
    COALESCE(sp.nome, sa.nome) AS nome,
    sp.alcunhas        AS alcunhas,
    sp.pai             AS pai,
    sp.mae             AS mae,
    'Autuante'         AS condicao,
    sp.dt_nascimento   AS dtNascimento,
    sp.idade           AS idade,
    sp.estado_civil    AS estadoCivil,
    sp.contacto        AS contacto,
    sp.morada          AS morada,
    sp.nacionalidade   AS nacionalidade,
    sp.naturalidade    AS naturalidade,
    sp.profissao       AS profissao,
    ag2.categoria      AS categoriaEfetivo,
    COALESCE(ge2.nome, '') AS esquadraEfetivo,
    'Autuante'         AS tipoAssinante,
    sa.categoria       AS categoriaAssinatura,
    sa.assina          AS assina,
    sa.sabe_assinar    AS sabeAssinar,
    sa.assinatura      AS assinatura
FROM sgo_ocorrencia o
JOIN LATERAL (
    SELECT a2.id_agente, a2.nome, a2.categoria, a2.assina, a2.sabe_assinar, a2.assinatura, a2.date_create
    FROM assinatura a2
    WHERE a2.id IN (:assinaturas)
      AND a2.id_agente = o.id_agente
      AND a2.tipo_assinante = 'AUTUANTE'
      AND a2.nome IS NOT NULL
    ORDER BY a2.date_create DESC
    LIMIT 1
) sa ON true
LEFT JOIN sigo_pessoa sp
    ON sp.oracle_id IN (:pessoasAutuante)
    AND UPPER(sp.nome) = UPPER(sa.nome)
LEFT JOIN agente ag2
    ON UPPER(ag2.nome) = UPPER(sa.nome)
LEFT JOIN esquadra ge2
    ON ge2.oracle_id = ag2.id_esquadra
WHERE o.oracle_id = :oracleId
AND NOT EXISTS (
    SELECT 1 FROM sgo_ocorrencia_pessoa op2
    JOIN sgo_ocorrencia o2 ON o2.oracle_id = op2.oracle_id_ocorrencia
    LEFT JOIN sigo_pessoa sp2 ON sp2.oracle_id = op2.oracle_id_pessoa
    WHERE o2.oracle_id = :oracleId
    AND UPPER(sp2.nome) = UPPER(sa.nome)
)
ORDER BY UPPER(COALESCE(sp.nome, sa.nome)), sp.oracle_id NULLS LAST, ag2.date_create NULLS LAST)
ORDER BY nome
""", nativeQuery = true)
    List<PessoaEnvolvedaProjection> buscarPessoasPorOracleIdEntreCandidatos(
            @Param("oracleId") Long oracleId,
            @Param("assinaturas") List<String> assinaturas,
            @Param("pessoasAutuante") List<Long> pessoasAutuante);

    @Query(value = """
SELECT a.categoria AS categoria, a.assinatura AS assinatura
FROM assinatura a
WHERE UPPER(TRIM(a.nome)) = UPPER(TRIM(:nome))
  AND UPPER(a.tipo_assinante) = 'AUTUANTE'
  AND a.assinatura IS NOT NULL
ORDER BY a.date_create DESC
LIMIT 1
""", nativeQuery = true)
    AssinaturaAutuanteProjection buscarAssinaturaAutuantePorNome(@Param("nome") String nome);

    /** Igual a buscarAssinaturaAutuantePorNome, só entre as assinaturas candidatas (lidas pela chave primária). */
    @Query(value = """
SELECT a.categoria AS categoria, a.assinatura AS assinatura
FROM assinatura a
WHERE a.id IN (:assinaturas)
  AND UPPER(TRIM(a.nome)) = UPPER(TRIM(:nome))
  AND UPPER(a.tipo_assinante) = 'AUTUANTE'
  AND a.assinatura IS NOT NULL
ORDER BY a.date_create DESC
LIMIT 1
""", nativeQuery = true)
    AssinaturaAutuanteProjection buscarAssinaturaAutuantePorNomeEntreCandidatos(
            @Param("nome") String nome,
            @Param("assinaturas") List<String> assinaturas);

    /** Pessoas envolvidas na ocorrência (sgo_ocorrencia_pessoa tem índice em oracle_id_ocorrencia). */
    @Query(value = """
SELECT DISTINCT op.oracle_id_pessoa
FROM sgo_ocorrencia_pessoa op
WHERE op.oracle_id_ocorrencia = :oracleId
  AND op.oracle_id_pessoa IS NOT NULL
""", nativeQuery = true)
    List<Number> idsPessoasDaOcorrencia(@Param("oracleId") Long oracleId);

    @Query(value = "SELECT o.id_agente FROM sgo_ocorrencia o WHERE o.oracle_id = :oracleId LIMIT 1", nativeQuery = true)
    Number idAgenteDaOcorrencia(@Param("oracleId") Long oracleId);

    @Query(value = """
SELECT
    p.oracle_id               AS oracleId,
    p.nome                    AS nome,
    o.oracle_id               AS oracleIdOcorrencia,
    o.num_ocorrencia          AS numOcorrencia,
    CASE
        WHEN o.tp_auto = '1' AND op.condicao = '1' THEN 'Denunciante'
        WHEN o.tp_auto = '1' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '1' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '1' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao = '1' THEN 'Participante'
        WHEN o.tp_auto = '2' AND op.condicao = '2' THEN 'Denunciante'
        WHEN o.tp_auto = '2' AND op.condicao = '3' THEN 'Denunciado'
        WHEN o.tp_auto = '2' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '2' AND op.condicao IN ('5','6') THEN 'Detentor'
        WHEN o.tp_auto = '2' AND op.condicao = '8' THEN 'Agente Atuante'
        WHEN o.tp_auto = '3' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '3' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '3' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '3' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '4' AND op.condicao = '1' THEN 'Denunciante/Vitima'
        WHEN o.tp_auto = '4' AND op.condicao = '2' THEN 'Ofendido'
        WHEN o.tp_auto = '4' AND op.condicao = '3' THEN 'Denunciado/Autor'
        WHEN o.tp_auto = '4' AND op.condicao = '4' THEN 'Testemunha'
        WHEN o.tp_auto = '5' AND op.condicao = '2' THEN 'Identificado'
        WHEN o.tp_auto = '5' AND op.condicao IN ('3','5') THEN 'Detido'
        WHEN o.tp_auto = '5' AND op.condicao = '6' THEN 'Detentor'
        WHEN o.tp_auto = '5' AND op.condicao = '7' THEN 'Agente Identificador'
        WHEN o.tp_auto = '5' AND op.condicao = '8' THEN 'Agente Atuante'
        ELSE 'Desconhecido'
    END                       AS condicao,
    CASE o.tp_auto
        WHEN '1' THEN 'Auto de Denúncia'
        WHEN '2' THEN 'Auto de Notícia'
        WHEN '3' THEN 'Auto de Detenção'
        WHEN '4' THEN 'Auto de Denúncia VBG'
        WHEN '5' THEN 'Auto de Identificação'
        ELSE 'Outro'
    END                       AS autoPrincipal
FROM sigo_pessoa p
JOIN sgo_ocorrencia_pessoa op
    ON op.oracle_id_pessoa = p.oracle_id
JOIN sgo_ocorrencia o
    ON o.oracle_id = op.oracle_id_ocorrencia
WHERE p.oracle_id IN (:oracleIds)
  AND o.num_ocorrencia != :numOcorrencia
ORDER BY o.data_registo DESC
""", nativeQuery = true)
    List<OutroAutoProjection> buscarOutrosAutosPorPessoas(
            @Param("oracleIds") List<Long> oracleIds,
            @Param("numOcorrencia") String numOcorrencia);

    @Query(value = """
SELECT resposta, pontuacao, id_quistionario AS idQuestionario
FROM sgo_ocorrencia_risco
WHERE id_ocorrencia = :idOcorrencia
ORDER BY id_quistionario
""", nativeQuery = true)
    List<Map<String, Object>> buscarRiscoVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT circustancia, resposta
FROM sgo_ocorrencia_circustancias
WHERE id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarCircunstanciasVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT descricao, id_meios AS idMeios
FROM sgo_ocorrencia_meios
WHERE id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarMeiosVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT acompanhamento, agendamento, tipo_atendimento AS tipoAtendimento, id_servicos AS idServicos
FROM sgo_ocorrencia_servico_apoio
WHERE id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarServicoApoioVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT acompanhamento, id_centros AS idCentros
FROM sgo_ocorrencia_centro_saude
WHERE id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarCentroSaudeVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT p.id_parentesco AS idParentesco,
       p.id_pessoa1 AS idPessoa1,
       p.id_pessoa2 AS idPessoa2,
       sp1.nome AS nomePessoa1,
       sp2.nome AS nomePessoa2
FROM sgo_ocorrencia_parentesco p
LEFT JOIN sigo_pessoa sp1 ON sp1.oracle_id = p.id_pessoa1
LEFT JOIN sigo_pessoa sp2 ON sp2.oracle_id = p.id_pessoa2
WHERE p.id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarParentescoVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT DISTINCT numero_auto AS numeroAuto, tipo_violencia AS tipoViolencia,
       mesmo_vitima AS mesmoVitima, data
FROM sgo_historico_autor_vbg
WHERE id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarHistoricoAutorVbg(@Param("idOcorrencia") Long idOcorrencia);

    @Query(value = """
SELECT DISTINCT numero_auto AS numeroAuto, mesmo_autor AS mesmoAutor,
       id_tp_desfecho AS idTpDesfecho, data, esquadra
FROM sgo_historico_vitima_vbg
WHERE id_ocorrencia = :idOcorrencia
""", nativeQuery = true)
    List<Map<String, Object>> buscarHistoricoVitimaVbg(@Param("idOcorrencia") Long idOcorrencia);

}
