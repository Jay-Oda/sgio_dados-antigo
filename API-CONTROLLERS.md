# Sigo API — Inventário de Controllers

Documentação do que cada controller faz, o que recebe e o que devolve.
Perfil `local`: porta **8084**, Basic Auth `sigo:sigo`, BD `jdbc:postgresql://localhost:5435/sigo`.

Swagger UI: `http://localhost:8084/api/swagger-ui/index.html` (api-docs: `http://localhost:8084/api-docs`).

> **Estado a 2026-10-20 — "Sigo dados - antigo"**: o título do Swagger passou para
> **Sigo dados - antigo** e os 32 controllers listados no final deste ficheiro
> (agente, assinatura, comando, comarca, esquadra, geografia, health-check,
> intervenientes, processo, pessoa-enviado, test, tipos-auto, tipo-crime e todos
> os `sgo-*`) ficaram **comentados no código** — desaparecem do Swagger e os
> endpoints deixam de responder. Só continuam activos `AntecedenteController`
> (`/api/sigo/v1/pessoa/...`) e `OcorrenciaNacionalController`
> (`/api/sigo/v1/Ocorrencia...`). Para reativar: remover o prefixo `// ` de cada
> linha do ficheiro do controller (cada um tem no topo a nota "CONTROLADOR
> COMENTADO A PEDIDO").

Todas as respostas de `APIResponse` têm o formato:
```json
{ "timestamp": "dd-MM-yyyy HH:mm:ss", "status": true|false, "statusText": "mensagem", "details": [ ... ] }
```

---

## 1. Controllers de consulta (GET) — testados, OK

| Endpoint | O que faz | Recebe | Devolve (details) |
|---|---|---|---|
| `GET /api/agente?nome=` | Lista agentes por nome (LIKE, case-insensitive) | `nome` opcional | `[AgenteResumo]` — id, idNacional, numero, nome, esquadra, comando, temFoto |
| `GET /api/agente/autocomplete?nome=` | Autocomplete de agentes | `nome` obrigatório | `[AgenteResumo]` |
| `GET /api/agente/numero/{numero}` | Busca por número (ex. `PN024600`); se o valor não for numérico, **fallback por nome** | path `numero` | `[AgenteResumo]` |
| `GET /api/agente/nome/{nome}` | Busca por nome completo | path `nome` | `[AgenteResumo]` |
| `GET /api/agente/detalhe/{id}` | Detalhe por UUID, idNacional (numérico) ou nome completo | path `id` | `[AgenteDetalhe]` — dados do agente + esquadra/comando |
| `GET /api/agente/{id}/queixas` | Queixas do agente — **apenas Autos de Denúncia** (`tp_auto='1'`), com `envolvidos` (contra quem) | path `id` (UUID/número/nome) | `{ queixas: [ {oracleId, numOcorrencia, data, natureza, auto, condicao, origem, envolvidos:[{nome, condicao}]} ] }` |
| `GET /api/agente/{id}/ocorrencias` | Ocorrências registadas + associadas ao agente, **todos os Autos** (denúncia, notícia, detenção, etc.), incl. participação (dedup por oracleId), com `envolvidos` | path `id` | `{ ocorrencias: [...], quantidadeRegistadas, quantidadeAssociadas }` |
| `GET /api/agente/{id}/pessoas` | Pessoas envolvidas nas ocorrências do agente | path `id` | `{ pessoas: [ {oracleIdPessoa, nome, ...} ] }` |
| `GET /api/SgoOcorrencia/motivo?num=` | Motivo/descrição de uma ocorrência | `num` (número da ocorrência, ex. `3273/2023/EPPT`) | `{ motivo, status, numOcorrencia }` |
| `GET /api/sgopessoas/oracle-ids` | Todos os oracle ids de `sigo_pessoa` | — | `[Long...]` (lista grande ~1.8 MB) |
| `GET /api/sigoOcorrenciaPessoa/oracle-ids` | Oracle ids de pessoas presentes em ocorrências (distinct, ordenado) | — | `[Long...]` (~1.4 MB) |
| `GET /api/sigoOcorrenciaPessoa/pessoa/{oracleId}` | Pessoa por id oracle | path `oracleId` | `[PessoaDetalhe]` |
| `GET /api/sigoOcorrenciaPessoa/pessoa/{oracleId}/antecedentes` | Antecedentes da pessoa, **agrupados por queixa** (`numOcorrencia` único), **só onde a pessoa é acusada/detida**, com motivo e circunstâncias | path `oracleId`; `auto` opcional (tipo de auto, ex. `detenção`/`denúncia`/código 1-5) | `{ queixas: [ {numOcorrencia, oracleIdOcorrencia, autoPrincipal, dataRegisto, unidade, fase, naturezas[], condicoes[], motivo, pontoReferencia, circunstancias:[{circustancia,resposta}]} ], quantidade, possuiOcorrencia, mensagem }` |
| `GET /api/sigoOcorrenciaPessoa/pessoa/todas?page=&size=` | Listagem paginada de pessoas | `page`, `size` | `{ pessoas: [PessoaResumo], ... }` |
| `GET /api/sigoOcorrenciaPessoa/pessoa/pesquisar` | Pesquisa de pessoas (query params) | `nome`, `alcunha`, `pai`, `mae`, `dataNascimento` (opcionais) | `[PessoaResumo]` — inclui `quantidadeOcorrencias` |
| `GET /api/sigoOcorrenciaPessoa/pessoa/pesquisar/antecedentes` | Pesquisa de pessoas + antecedentes agrupados por queixa, com filtro de auto | `nome`, `alcunha`, `pai`, `mae`, `dataNascimento`, `auto` (opcionais) | `[PessoaResumo]` + `antecedentes` (`queixas[]` com motivo/circunstâncias), `quantidade`, `tiposAuto`, `condicoes`, `mensagemAntecedentes` por pessoa |
| `GET /health` | Health check | — | `{ "status": "UP" }` |
| `GET /api/test/ping` | Ping | — | `OK 8084` |

Observações de consulta:
- Pesquisa por nome é **case-insensitive** (`JAILSON ALMEIDA` = `jailson almeida`).
- Datas usam formato fixo **`yyyy-MM-dd`** (ex. `1985-10-15`); datas inválidas devolvem erro claro.
- `/api/agente/{id}/...` e `/detalhe/{id}` aceitam **UUID, idNacional numérico ou nome completo** (ex. `GET /api/agente/FILOMENO DE BRITO MARQUES/queixas`).

---

## 2. Controllers de criação (POST)

> **Correção desta sessão**: todos os endpoints de criação rejeitam corpo vazio (`{}` ou DTO sem nenhum campo preenchido) com `status:false` e a mensagem **"Dados em falta: corpo vazio ou sem campos preenchidos"** — **não inserem nenhuma linha**. A validação é genérica (`utilities/DtoValidator`), rejeita qualquer DTO cujo objeto tenha todos os getters a `null`.

| Endpoint | Tabela | Recebe (DTO) |
|---|---|---|
| `POST /api/agente` | `agente` | `AgenteDto` |
| `POST /api/agente/params?numero=&nome=` | `agente` | form params `numero`, `nome` |
| `POST /api/assinatura` | `assinatura` | `AssinaturaDto` |
| `POST /api/comandos` | `comando` | `ComandoDto` |
| `POST /api/comarca` | `comarca` | `ComarcaDto` |
| `POST /api/esquadra` | `esquadra` | `EsquadraDto` |
| `POST /api/geografia` | `glb_geografica` | `GeografiaDto` |
| `POST /api/intervenientes` | `intervinientes` | `IntervenientesDto` |
| `POST /api/processos` | `processo` | `ProcessoDto` |
| `POST /api/SgoOcorrencia` | `sgo_ocorrencia` | `SgoOcorrenciaDto` |
| `POST /api/pecas` | `sgo_pecas` | `SgoPecaDto` |
| `POST /api/sgoPecaIntervenente` | `sgo_peca_intervenente` | DTO |
| `POST /api/sgo-peca-pessoa` | `sgo_peca_pessoa` | `SgoPecaPessoaDto` |
| `POST /api/sgopessoas` | `sigo_pessoa` | `SigoPessoaDto` |
| `POST /api/sigoOcorrenciaAnexo` | `sgo_ocorrencia_anexo` | DTO |
| `POST /api/sigoOcorrenciaAnexos` | `sgo_ocorrencia_anexos` | DTO |
| `POST /api/sigoOcorrenciaCentroSaude` | `sgo_ocorrencia_centro_saude` | DTO |
| `POST /api/sigoOcorrenciaCircustancias` | `sgo_ocorrencia_circustancias` | DTO |
| `POST /api/sigoOcorrenciaMeios` | `sgo_ocorrencia_meios` | DTO |
| `POST /api/sigoOcorrenciaParentesco` | `sgo_ocorrencia_parentesco` | DTO |
| `POST /api/sigoOcorrenciaRisco` | `sgo_ocorrencia_risco` | DTO |
| `POST /api/sigoOcorrenciaServicoApoio` | `sgo_ocorrencia_servico_apoio` | DTO |
| `POST /api/sigoTpOcorrencias` | `sgo_tp_ocorrencia` | DTO |
| `POST /api/sigoTpOcorrenciasEntrada` | `sgo_tp_ocorrencia_entrada` | DTO |
| `POST /api/tiposauto` | `tipo_auto` | `TiposAutoDto` |
| `POST /api/ocorrencias-tp_ocorrencias` | `sgo_ocorrencia_tp_ocorrencia` | DTO |
| `POST /api/tipocrime-tipocorrencia` | `tp_crime_tp_ocorrencia` | DTO |
| `POST /api/historico-autor-vbg` | `sgo_historico_autor_vbg` | DTO |
| `POST /api/historico-vitima-vbg` | `sgo_historico_vitima_vbg` | DTO |
| `POST /api/sigoOcorrenciaPessoa` | `sgo_ocorrencia_pessoa` | `SgoOcorrenciaPessoaDto` |

Todos devolvem `APIResponse` com `status:true` + entidade salva em `details`, ou `status:false` + mensagem de erro em `details`.

---

## 3. Controllers de pesquisa (POST) — testados, OK

| Endpoint | Recebe | Devolve |
|---|---|---|
| `POST /api/sgopessoas/pessoa/pesquisar-avancado` | `PessoaFiltroRequest` (ex. `{"nome":"FILOMENO"}`) | `[PessoaResumo]` com antecedentes (127 pessoas no teste) |
| `POST /api/sigoOcorrenciaPessoa/pessoa/pesquisar` | `PessoaAntecedenteRequest` (ex. `{"nome":"FILOMENO"}`) | `[PessoaResumo]` (200 por página) |

---

## 4. Correções feitas nesta sessão

1. **Validação de corpo vazio em todos os 30 services de criação** — novo `cv.pn.processmanagement.utilities.DtoValidator`: um POST de criação com DTO sem nenhum campo preenchido devolve `status:false` + "Dados em falta: corpo vazio ou sem campos preenchidos", **sem escrever na BD**. Antes, `{}` inseria uma linha com todos os campos nulos.
2. **`AssinaturaService.java`** — mensagem de sucesso errada: "Comarca criada com sucesso" → **"Assinatura criada com sucesso"** (bug de copy-paste).
3. **`SgoOcorrenciaPessoaController.java`** — `GET /api/sigoOcorrenciaPessoa/oracle-ids` devolvia sempre `[]` porque mapeava `oracle_id` da linha (null em todas). Agora devolve **`oracleIdPessoa` distintos e ordenados**.
4. **`SgoOcorrenciaRepository.java`** — `buscarOcorrenciasAssociadasAoAgente` filtrava por `op.id_agente IN (:ids)`, mas `sgo_ocorrencia_pessoa.id_agente` está **sempre NULL** → `GET /api/agente/{id}/ocorrencias` nunca devolvia as associadas. Agora filtra por **`op.oracle_id_pessoa IN (:ids)`**. O mesmo em `buscarPessoasDasOcorrenciasDoAgente` (`op.id_agente` → `op.oracle_id_pessoa`). Ex.: AIRTON CÉSAR LOPES CHANTRE passou de 0 para 148 ocorrências.
5. **`AntecedenteService.java`** — antecedentes agora são **agrupados por queixa** (`num_ocorrencia`): cada queixa aparece uma vez com `naturezas[]` e `condicoes[]` (papéis da pessoa) agregados. Resposta usa `queixas[]` em vez da lista plana `ocorrencias[]` (que duplicava a mesma queixa por natureza).
6. **`AgenteService.java` + `SgoOcorrenciaRepository.java`** — `GET /api/agente/{id}/queixas` passou a devolver **apenas Autos de Denúncia** (`tp_auto='1'`), nova query `buscarQueixasPorAgente`; `origem` = `QUEIXA_AUTO_DENUNCIA`. `GET /api/agente/{id}/ocorrencias` mantém **todos os Autos** (registadas + associadas/participação), `autoPrincipal` nomeado (ex. `Auto de Detenção`).
7. **`AntecedenteService.java` + `IAntecedenteService.java` + `AntecedenteController.java` + `SgoOcorrenciaPessoaRepository.java`** — novo parâmetro opcional `auto` em `GET /pessoa/{oracleId}/antecedentes` e `GET /pessoa/pesquisar/antecedentes` para filtrar por tipo de Auto (código 1-5 ou palavra "denúncia", "notícia", "detenção", "vbg", "identificação"). Resposta inclui **`mensagem`** (ex.: "Esta pessoa possui 145 antecedente(s)." / "Esta pessoa não possui antecedentes policiais."). `buscarAntecedentesPorPessoa` agora recebe `tpAuto` (filtro `AND (:tpAuto IS NULL OR o.tp_auto = :tpAuto)`).
8. **`AgenteService.java` + `AgenteOcorrenciaDto.java` + `SgoOcorrenciaPessoaRepository.java`** — nova query `buscarEnvolvidosDasOcorrencias` e campo **`envolvidos`** (contra quem) nas queixas e ocorrências do agente: lista de `{nome, condicao}` (papel) das pessoas ligadas a cada ocorrência, via `sgo_ocorrencia_pessoa` + `sigo_pessoa`. Ex.: queixa 892/2025/EICCSV → `ORLANDINO MARTINS DOS SANTOS [Denunciante]` + `PATRICK MARTINS FORTES GOTO [Denunciado]`.
9. **Antecedentes — filtro por condição relevante** (`AntecedenteService` + `SgoOcorrenciaPessoaRepository.buscarAntecedentesPorPessoa`/`existsAntecedenteReal`/`contarAntecedentesReais`): os antecedentes mostram **só queixas onde a pessoa é o acusado/detido** — Auto de Denúncia e VBG só `condicao='3'` (Denunciado/Denunciado/Autor), Auto de Detenção só `condicao IN ('5','6')` (Detido/Detentor), Auto de Notícia só `condicao='3'`, Auto de Identificação só `condicao='2'` (Identificado). Testemunhas/vítimas/denunciantes ficam de fora. Ex.: AQUELINO (8532) passou de 19 para 13; RUI (75570) de 45 para 11 (só Detentor); pessoa só-Testemunha → "não possui antecedentes policiais".
10. **Antecedentes — novos campos por queixa** (`SgoOcorrenciaPessoaRepository` + projeções `OcorrenciaResumoProjection`/`OcorrenciaCircunstanciaProjection`): cada queixa passa a incluir `oracleIdOcorrencia`, **`motivo`** (narrativa completa dos factos, `sgo_ocorrencia.motivo`, presente em ~99,99% das ocorrências), **`pontoReferencia`** e **`circunstancias`** (`sgo_ocorrencia_circustancias`, questionário armas/álcool/drogas ligado por `id_ocorrencia = oracle_id`). Ex.: 1379/2025/EPAEL → motivo com o relato + 5 circunstâncias.
11. (Sessões anteriores) Agente por nome completo, datas `yyyy-MM-dd` com validação, remoção de `/nacional`, antecedentes com `tiposAuto`/`condicoes`/`quantidade`.

---

## 5. Problemas conhecidos / decisões pendentes

- **Validação é genérica (todos-os-campos-nulos), não há campos obrigatórios por DTO.** Corpo `{}` é rejeitado, mas um corpo com um único campo não-nulo é aceite mesmo que a entidade não tenha dados suficientes. Se necessário, definir `@NotNull`/`@NotBlank` por DTO e usar `@Valid` nos controllers.
- Mensagens de sucesso com erros de concordância/tipos ("Ocorrencia  criada", "Intervinientes criada", "geografica criada", "Comando criada") — apenas cosmético.
- `GET /api/sgopessoas/oracle-ids` e `GET /api/sigoOcorrenciaPessoa/oracle-ids` devolvem listas muito grandes (1.4–1.8 MB) — considerar paginação.
- `agente.foto` (oid LOB) tem stubs corrompidos: listagens/detalhes usam projections escalares e só expõem `temFoto` (nunca carregam o blob).
- `POST /api/agente/params` usa `@RequestParam` (form), não JSON — 400 esperado com corpo JSON.
- **Dados órfãos do sistema antigo**: ~42.7k ligações em `sgo_ocorrencia_pessoa` (16.6%) referenciam `oracle_id_ocorrencia` que não existe em `sgo_ocorrencia` (ex.: ADILSON SILVA SOARES ALMEIDA, pessoa 73010, tem 2 ligações para 50284/52661). Essas pessoas aparecem com 0 queixas/antecedentes, apesar de existirem ligações. As queixas antigas não têm registo local (num_ocorrencia, datas) — não é possível exibi-las sem os dados de origem.

---

## 6. Como testar

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
.\mvnw.cmd -q -o compile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
# Swagger: http://localhost:8084/api/swagger-ui/index.html
# Auth:  sigo / sigo
```
