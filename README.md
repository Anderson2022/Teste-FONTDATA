# Controle de Tarefas — Teste FONTDATA

Sistema web para cadastro, gerenciamento e atribuição de tarefas a usuários.
 A aplicação possui autenticação, controle de acesso por perfil e interface responsiva.

## Implementações
###  autorização

 Login com usuário e senha.
 Senhas armazenadas com BCrypt.
 Controle de acesso com Spring Security.
 Perfis `SUPERVISOR` e `OPERADOR`.
 Redirecionamento após o login de acordo com o perfil.
 Página personalizada para acesso negado.

### Perfil Supervisor

 Acesso ao Dashboard.
 Cadastro, edição, ativação e desativação de usuários.
 Cadastro, edição e exclusão de tarefas.
 Atribuição e remoção de tarefas por usuário.
 Possibilidade de atribuir a mesma tarefa a usuários diferentes.
 Visualização das tarefas atribuídas ao próprio usuário.

### Perfil Operador

 Acesso somente à página **Minhas tarefas**.
 Visualização apenas das tarefas vinculadas ao usuário autenticado.
 Sem acesso ao cadastro de usuários, gerenciamento de tarefas ou atribuições.

### Dashboard

 Quantidade de usuários.
 Quantidade de tarefas.
 Quantidade de atribuições.
 Quantidade de supervisores.
 Quantidade de operadores.
 Atalhos para as principais funcionalidades.

### Interface

 Layout responsivo para computador, tablet e celular.
 Menu lateral com indicação da página ativa.
 Cabeçalho com usuário autenticado e botão para sair.
 Ícones nas ações de editar, excluir e remover.
 Badges para perfil, status e tipo de tarefa.

## Tecnologias e Bibliotecas  utilizadas

= Java 21
= Spring Boot 3.5.6
= Spring MVC
= Spring Data JPA
= Spring Security
= Thymeleaf
= Bean Validation
= Flyway
= PostgreSQL 17
= Bootstrap 5
= Docker e Docker Compose
= Maven


## Arquitetura

O projeto está organizado na seguinte estrutura:


controller  = recebe as requisições HTTP e seleciona as páginas
service     = concentra as regras de negócio
repository  = realiza o acesso ao banco com Spring Data JPA
entity      = representa as tabelas do banco de dados
dto         = transporta e valida dados dos formulários
templates   = páginas HTML processadas pelo Thymeleaf


## Banco de dados

O schema é versionado com Flyway e dividido em três migrações de tabelas diferentes:

V1__create_usuarios.sql
V2__create_tarefas.sql
V3__create_usuario_tarefa.sql


A tabela `usuario_tarefa` registra separadamente cada vínculo entre usuário e tarefa. 
Assim, a mesma tarefa pode ser atribuída a usuários diferentes, mas não pode ser duplicada para o mesmo usuário.

## Executando com Docker

### Requisitos

- Docker
- Docker Compose

### Iniciar a aplicação

Na raiz do projeto, execute:

```bash
docker compose up --build -d
```

 acesso ao banco:

http://localhost:8080


docker compose logs -f app
docker compose down


O PostgreSQL fica disponível localmente em `127.0.0.1:5433`.

Configuração do banco:

Banco: controle_tarefas
Usuário: postgres
Senha: postgres
Porta externa: 5433


## Usuários 

 Perfil  Usuário | Senha |

| Supervisor | `admin` | `admin123` |
| Operador | `operador` | `operador123` |

Esses usuários são criados automaticamente na inicialização quando ainda não existem.

## Executando os testes

```bash
mvn test
```

Os testes cobrem regras dos serviços e restrições de acesso, incluindo a proibição de um Operador acessar áreas administrativas ou excluir tarefas.

## Principais rotas

| Rota | Finalidade | Acesso |
|---|---|---|
| `/login` | Autenticação | Público |
| `/dashboard` | Resumo do sistema | Supervisor |
| `/usuarios` | Gerenciamento de usuários | Supervisor |
| `/tarefas` | Gerenciamento de tarefas | Supervisor |
| `/atribuicoes` | Gerenciamento de atribuições | Supervisor |
| `/minhas-tarefas` | Tarefas do usuário autenticado | Supervisor e Operador |

## Implementações extras

As funcionalidades abaixo são sugestões para evolução futura e ainda não fazem parte da implementação atual:

1. **Cadastro de tipos de tarefa**
   - Substituir os tipos fixos por um cadastro administrável.
   - Permitir criar, editar, ativar e desativar tipos.

2. **Descrição da tarefa**
   - Adicionar uma coluna `descricao` à tabela de tarefas.
   - Incluir o campo no formulário de nova tarefa e de edição.
   - Exibir a descrição nas listagens e nas atribuições.

3. **Classificação da tarefa**
   - Permitir marcar uma tarefa como `INTERNA` ou `CLIENTE`.
   - Quando a opção `CLIENTE` for selecionada, exibir um campo para escolher o cliente.
   - Para tarefas internas, o cliente permanece vazio.

4. **Cadastro de clientes**
   - Criar um cadastro simples de clientes.
   - Permitir cadastrar, editar, listar e ativar ou desativar clientes.
   - Disponibilizar os clientes ativos no formulário de tarefas.