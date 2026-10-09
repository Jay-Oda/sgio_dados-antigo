-- ============================================================================
-- Migração de performance: pesquisa de ocorrência por número
-- ----------------------------------------------------------------------------
-- Contexto: o endpoint /api/sigo/v1/pessoa/ocorrencia/{numero} (buscarPorNumOcorrencia
-- em SgoOcorrenciaPessoaRepository) tornou-se extremamente lento (~25s) na query de
-- pesquisa. Causa raiz (confirmado com EXPLAIN ANALYZE):
--   a subquery correlacionada da coluna "prazo" fazia um Seq Scan completo na tabela
--   'processo' POR CADA linha de ocorrência encontrada (centenas de loops, milhares de
--   blocos lidos), porque 'processo' só tinha índice na PK (id).
--
-- Correção: criar índices para que a subquery use Index Scan em vez de Seq Scan.
-- Resultado medido: 25 000 ms -> ~130 ms (DB local).
--
-- Aplicar em todas as bases de dados (perfis local/dev/qa/prod), pois o projeto usa
-- spring.jpa.hibernate.ddl-auto=none e estes índices não são criados automaticamente.
-- ============================================================================

-- Filtro ILIKE da pesquisa por número de ocorrência
CREATE INDEX IF NOT EXISTS idx_sgo_ocorrencia_num ON sgo_ocorrencia (num_ocorrencia);

-- Subquery "prazo" (processo): procura por id_ocorrencia = o.oracle_id
CREATE INDEX IF NOT EXISTS idx_processo_id_ocorrencia ON processo (id_ocorrencia);

-- Subquery "prazo" (processo): procura por num_ocorrencia = o.num_ocorrencia
CREATE INDEX IF NOT EXISTS idx_processo_num_ocorrencia ON processo (num_ocorrencia);

-- Suporte ao ORDER BY pr.data_registo DESC LIMIT 1 (prazo mais recente)
CREATE INDEX IF NOT EXISTS idx_processo_prazo_data ON processo (num_ocorrencia, data_registo DESC);

-- ============================================================================
-- Migração de performance: listagem nacional de ocorrências (/api/sigo/v1/Ocorrencia)
-- ----------------------------------------------------------------------------
-- Ordenação da página por DATA_REGISTO DESC (recência), usada na listagem nacional.
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_sgo_ocorrencia_data_registo ON sgo_ocorrencia (data_registo DESC);

-- Join do "Registado Por" (autuante) na listagem nacional
CREATE INDEX IF NOT EXISTS idx_assinatura_autuante ON assinatura (id_agente, tipo_assinante);

-- Join da grelha N:N ocorrência-tipologia na listagem nacional
CREATE INDEX IF NOT EXISTS idx_sgo_ocorrencia_tp_ocorrencia_occ ON sgo_ocorrencia_tp_ocorrencia (id_ocorrencia);
