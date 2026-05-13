# 🧠 StrongMemoryAPI

API REST desenvolvida com Java e Spring Boot para suporte à aplicação **Strong Memory (SM)**, uma plataforma web gamificada voltada para o treinamento da memória muscular e cognitiva.

O sistema fornece toda a infraestrutura backend necessária para execução do mini-game, acompanhamento de desempenho, autenticação de usuários, gerenciamento de palavras e administração da plataforma.

<br/>

## 🚀 Sobre o Projeto

O **SM** é uma aplicação interativa que desafia o usuário a memorizar e reproduzir sequências de palavras sob condições de tempo e dificuldade progressiva.

A proposta central é estimular:

* Memória
* Concentração
* Precisão
* Agilidade cognitiva

A API foi projetada para ser **escalável, segura e performática**, permitindo futuras expansões como análise profunda de desempenho e personalização da experiência do usuário.

<br/>

## 🎮 Funcionalidades Principais

### 🧠 Mini-Game (Core do Sistema)

* Execução de partidas com palavras aleatórias por dificuldade
* Controle de tempo para exibição e digitação
* Validação automática das respostas
* Feedback imediato de vitória ou derrota
* Exibição de pontuação ao final da partida
* Reinício rápido do jogo

### 📊 Histórico e Dados para Dashboards

* Registro do histórico completo de partidas
* Consulta de partidas por usuário
* Estatísticas de acertos, erros e precisão
* Histórico de pontuação por período
* Quantidade total de partidas jogadas
* Tempo médio de partida
* Desempenho por dificuldade
* Evolução de desempenho do jogador
* Dados para gráficos de desempenho e progresso
* Registro de sequência de palavras sorteadas
* Estatísticas de desistências e partidas concluídas
* Ranking de melhores partidas
* Filtros por data, dificuldade e modo de jogo

### 👤 Gestão de Usuários

* Cadastro de jogadores
* Autenticação com JWT
* Alteração de senha
* Dashboard com informações do usuário e desempenho
<!-- * Exclusão de conta -->

### 📝 Gerenciamento de Palavras

* Listagem de palavras por dificuldade (com paginação)
* Geração de listas aleatórias para o mini-game
* Cadastro, edição e exclusão de palavras (admin)
  
### 💡 Sugestão de Palavras

* Usuários podem sugerir novas palavras
* Administradores podem:

  * Visualizar sugestões
  * Filtrar por período
  * Aprovar ou excluir sugestões

<br/>

## 🛠️ Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* MySQL
* Redis (cache)
* Docker

<br/>

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida:

* **Controller** → Entrada da API (requisições HTTP)
* **Domain** → Domínio da aplicação e do négocio
* **DTO** → Transferência de dados entre camadas
* **Exception** → Tratamento de erros e exceções
* **Repository** → Acesso ao banco de dados
* **Security** → Segurança e proteção da aplicação
* **Service** → Regras de negócio

<br/>

### Princípios aplicados:

* Separação de responsabilidades
<!-- * Clean Code -->
* Organização modular
* Facilidade de manutenção e escalabilidade

---

## 🔐 Segurança

* Autenticação baseada em JWT
* Controle de acesso por papéis (PLAYER e ADMINISTRATOR)
* Proteção de endpoints sensíveis
* Criptografia de senhas

<br/>

<!--

## ⚙️ Como Executar o Projeto

### 📋 Pré-requisitos

* Java 21
* Docker
* MySQL (caso não utilize container)

---

### 🔧 Passos

```bash
# Clonar repositório
git clone https://github.com/seu-usuario/strong-memory-api.git

# Entrar na pasta
cd strong-memory-api

# Subir containers (caso utilize docker)
docker-compose up -d

# Rodar aplicação
./mvnw spring-boot:run
```

---
-->

## 🔐 Variáveis de Ambiente

```env
SPRING_PROFILES_ACTIVE=

SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

SPRING_REDIS_HOST=
SPRING_REDIS_PORT=

JWT_SECRET_KEY=

CORS_ALLOWED_ORIGINS=
```

<br/>

## 📚 Principais Endpoints

## 🔑 Autenticação

| Método   | Rota         | Descrição                |
|----------|--------------| ------------------------ |
| POST     | /api/auth    | Autenticação de usuários |

### Query Params

| Parâmetro   | Tipo | Obrigatório   | Valores Permitidos        |
|-------------|------|---------------|---------------------------|
| role        | Enum | Sim           | ROLE_PLAYER, ROLE_ADMIN   |

## 🎯 Dificuldades de Jogo

| Método   | Rota                | Descrição                   |
|----------| ------------------- | --------------------------- |
| GET      | /api/difficulty/all | Lista todas as dificuldades |
| GET      | /api/difficulty     | Busca dificuldade por nome  |

### Query Params

| Parâmetro   | Tipo   | Obrigatório   |
|-------------| ------ |---------------|
| name        | string | Sim           |

## 🎮 Controle de Jogo

| Método   | Rota                                  | Descrição                        |
|----------| ------------------------------------- | -------------------------------- |
| POST     | /api/game/start                       | Inicia uma partida               |
| POST     | /api/game/gave-up                     | Finaliza partida por desistência |
| POST     | /api/game/finish                      | Finaliza uma partida             |
| POST     | /api/game/more-random-words/{matchId} | Busca mais palavras aleatórias   |

### Path Params

| Parâmetro   | Tipo   | Descrição     |
|-------------| ------ | ------------- |
| matchId     | number | ID da partida |

### Query Params

| Parâmetro       | Tipo   | Obrigatório   |
|-----------------| ------ |---------------|
| startOrderIndex | number | Sim           |

## 📝 Palavras

| Método   | Rota               | Descrição                |
|----------| ------------------ | ------------------------ |
| POST     | /api/word          | Cadastra uma palavra     |
| PATCH    | /api/word/{wordId} | Atualiza uma palavra     |
| DELETE   | /api/word/{wordId} | Remove uma palavra       |
| GET      | /api/word          | Lista palavras paginadas |

### Path Params

| Parâmetro   | Tipo   | Descrição        |
|-------------| ------ | ---------------- |
| wordId      | number | ID da palavra    |

### Query Params

| Parâmetro        | Tipo    | Obrigatório   | Valores Permitidos |
| ---------------- | ------- |---------------| ------------------ |
| suggestionOrigin | boolean | Não           | true, false        |
| difficulty       | string  | Não           | —                  |
| page             | int     | Sim           | —                  |
| size             | int     | Sim           | —                  |
| sortBy           | string  | Sim           | —                  |
| direction        | Enum    | Não           | ASC, DESC          |

## 💡 Sugestões de Palavras

| Método   | Rota                                | Descrição                   |
|----------| ----------------------------------- | --------------------------- |
| GET      | /api/word-suggestion                | Lista sugestões de palavras |
| GET      | /api/word-suggestion/period         | Lista sugestões por período |
| DELETE   | /api/word-suggestion/{suggestionId} | Remove uma sugestão         |
| POST     | /api/word-suggestion                | Cria uma sugestão           |

### Path Params

| Parâmetro     | Tipo   | Descrição          |
|---------------| ------ | ------------------ |
| suggestionId  | number | ID da sugestão     |

### Query Params

| Parâmetro   | Tipo        | Obrigatório | Valores Permitidos |
|-------------| ----------- |-------------| ------------------ |
| difficulty  | string      | Não         | —                  |
| page        | int         | Sim         | —                  |
| size        | int         | Sim         | —                  |
| sortBy      | string      | Sim         | —                  |
| startDate   | string/date | Sim         | —                  |
| endDate     | string/date | Sim         | —                  |
| direction   | Enum        | Não         | ASC, DESC          |

## 🛡️ Administrador

| Método   | Rota                | Descrição                         |
|----------| ------------------- | --------------------------------- |
| POST     | /api/admin          | Cadastro de administrador         |
| PATCH    | /api/admin/password | Alteração de senha                |

## 👤 Jogador

| Método   | Rota                   | Descrição                    |
|----------|------------------------|------------------------------|
| POST     | /api/player            | Cadastro de jogador          |
| PATCH    | /api/player/password   | Alteração de senha           |

## 📊 Dashboard de Histórico de Partidas

| Método   | Rota                                                   | Descrição                                                      |
|----------| ------------------------------------------------------ |----------------------------------------------------------------|
| GET      | /api/dashboard/match-history/overview                  | Visão geral do histórico                                       |
| GET      | /api/dashboard/match-history/performance/accuracy      | Desempenho de precisão ao longo de um período de dias          |
| GET      | /api/dashboard/match-history/performance/scores        | Desempenho de pontuação ao longo de um período de dias         |
| GET      | /api/dashboard/match-history/performance/response-time | Desempenho de tempo de resposta ao longo de um período de dias |
| GET      | /api/dashboard/match-history/accurary/summary          | Resumo geral de precisão                                       |
| GET      | /api/dashboard/match-history/accurary/by-difficulty    | Precisão por dificuldade                                       |
| GET      | /api/dashboard/match-history/matches/results           | Distribuição dos resultados                                    |
| GET      | /api/dashboard/match-history/matches/duration          | Estatísticas de duração ao longo de um período de dias         |
| GET      | /api/dashboard/match-history/game/modes                | Estatísticas por modo de jogo                                  |
| GET      | /api/dashboard/match-history/game/highest-scores       | Maiores pontuações                                             |
| GET      | /api/dashboard/match-history/engagement                | Dados de engajamento ao longo de um período de dias            |

### Query Params

| Parâmetro   | Tipo   | Obrigatório   |
|-------------| ------ |---------------|
| days        | number | Não           |

<br/>

<!--

## 📈 Possíveis Melhorias Futuras

* Implementação de testes automatizados (JUnit / Mockito)
* Sistema de ranking global
* Histórico completo de partidas
* Monitoramento com Spring Actuator
* Deploy em cloud (AWS, Render, Railway)
* Sistema de conquistas (gamificação avançada)

-->

## 👨‍💻 Autor

**Renan Lopes Lima Teles**

* GitHub: https://github.com/renan-teles
<!-- * LinkedIn: https://linkedin.com/in/seu-perfil -->

<br/>

## 🐳 Executar o Projeto Completo

Este repositório representa apenas uma parte do sistema Strong Memory.

Para executar toda a aplicação integrada (frontend + backend + banco de dados), utilize o ambiente completo com Docker:

👉 https://github.com/renan-teles/strong-memory-docker

Isso permite rodar o sistema completo com um único comando.

<br/>

## 📌 Observações

Este projeto foi desenvolvido com foco em:

* Prática de desenvolvimento backend profissional
* Aplicação de boas práticas de arquitetura
* Simulação de um sistema real com regras de negócio bem definidas

<!--
---
💡 *Projeto ideal para portfólio, demonstrando domínio em APIs REST, autenticação, arquitetura em camadas e integração com banco de dados e cache.*
-->
