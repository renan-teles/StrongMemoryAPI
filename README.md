# 🧠 StrongMemoryAPI

API REST desenvolvida com Java e Spring Boot para suporte à aplicação **Strong Memory (SM)**, uma plataforma web gamificada voltada para o treinamento da memória muscular e cognitiva.

O sistema fornece toda a infraestrutura backend necessária para execução do mini-game, autenticação de usuários, gerenciamento de palavras, controle de pontuação e administração da plataforma.

<br/>

## 🚀 Sobre o Projeto

O **Strong Memory (SM)** é uma aplicação interativa que desafia o usuário a memorizar e reproduzir sequências de palavras sob condições de tempo e dificuldade progressiva.

A proposta central é estimular:

* Memória
* Concentração
* Precisão
* Agilidade cognitiva

A API foi projetada para ser **escalável, segura e performática**, permitindo futuras expansões como análise de desempenho e personalização da experiência do usuário.

<br/>

## 🎮 Funcionalidades Principais

### 🧠 Mini-Game (Core do Sistema)

* Execução de partidas com palavras aleatórias por dificuldade
* Controle de tempo para exibição e digitação
* Validação automática das respostas
* Feedback imediato de vitória ou derrota
* Exibição de pontuação ao final da partida
* Reinício rápido do jogo

### 📊 Sistema de Pontuação

* Registro da maior pontuação por dificuldade
* Consulta de pontuação por usuário
* Atualização de pontuação após partidas

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
* **Service** → Regras de negócio
* **Repository** → Acesso ao banco de dados
* **DTO** → Transferência de dados entre camadas

<br/>

<!--
### Princípios aplicados:

* Separação de responsabilidades
* Clean Code
* Organização modular
* Facilidade de manutenção e escalabilidade

---
-->

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

## 🔐 Variáveis de Ambiente

```env
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

JWT_SECRET=

SPRING_REDIS_HOST=
SPRING_REDIS_PORT=
```

---
-->

## 📚 Principais Endpoints

### 🔑 Autenticação

| Método | Rota                    | Descrição                     |
| ------ | ----------------------- | ----------------------------- |
| POST   | /api/player/auth        | Autenticação do jogador       |
| POST   | /api/administrator/auth | Autenticação do administrador |

### 👤 Usuários

| Método | Rota                             | Descrição                 |
| ------ | -------------------------------- | ------------------------- |
| POST   | /api/player/register             | Cadastro de jogador       |
| PUT    | /api/player/update-password/{id} | Atualizar senha           |
| POST   | /api/administrator/register      | Cadastro de administrador |

### 🎮 Mini-Game / Palavras

| Método | Rota                        | Descrição                         |
| ------ | --------------------------- | --------------------------------- |
| GET    | /api/word/get-random-list   | Lista aleatória de palavras       |
| GET    | /api/word/get-by-difficulty | Listagem paginada por dificuldade |


### 📊 Pontuação

| Método | Rota                              | Descrição                 |
| ------ | --------------------------------- | ------------------------- |
| GET    | /api/score-record/get-user-scores | Pontuações do usuário     |
| GET    | /api/score-record/get-user-score  | Pontuação por dificuldade |
| PUT    | /api/score-record/update/{id}     | Atualizar pontuação       |

### 🧠 Dificuldades

| Método | Rota                     | Descrição           |
| ------ | ------------------------ | ------------------- |
| GET    | /api/difficulty/get-all  | Listar dificuldades |
| GET    | /api/difficulty/get/{id} | Buscar por ID       |


### 💡 Sugestões de Palavras

| Método | Rota                               | Descrição        |
| ------ | ---------------------------------- | ---------------- |
| POST   | /api/word-suggestion/register      | Criar sugestão   |
| GET    | /api/word-suggestion/get-all       | Listar sugestões |
| GET    | /api/word-suggestion/get-by-period | Filtrar por data |
| DELETE | /api/word-suggestion/delete/{id}   | Remover sugestão |

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

## 📌 Observações

Este projeto foi desenvolvido com foco em:

* Prática de desenvolvimento backend profissional
* Aplicação de boas práticas de arquitetura
* Simulação de um sistema real com regras de negócio bem definidas
<!--
---

💡 *Projeto ideal para portfólio, demonstrando domínio em APIs REST, autenticação, arquitetura em camadas e integração com banco de dados e cache.*
-->
