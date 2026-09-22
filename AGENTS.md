# AGENTS.md — Controle de Tarefas

## 1. Objetivo

Este projeto é uma avaliação técnica com prazo curto.

Prioridades:

1. Funcionar.
2. Atender aos requisitos.
3. Código organizado.
4. Segurança básica.
5. Docker funcionando.
6. Evitar complexidade desnecessária.

Stack:

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Flyway
- Bootstrap
- Docker Compose

Não alterar a stack sem solicitação explícita.

---

## 2. Economia de tokens e contexto

SEJA ECONÔMICO.

Não leia arquivos sem necessidade.

Antes de alterar algo:

- identifique os arquivos diretamente relacionados;
- leia somente esses arquivos;
- não faça varredura completa do projeto sem necessidade;
- não reabra arquivos que já foram analisados e continuam no contexto;
- não mostre conteúdos enormes no terminal;
- não liste toda a árvore do projeto repetidamente.

Prefira buscas direcionadas.

Exemplos:

```bash
rg "UsuarioService" src
rg "SecurityConfig" src
find src/main/java -name "*Usuario*"

## 21. Logs e saída de terminal

Economizar contexto ao analisar logs.

NUNCA despejar logs completos sem necessidade.

Evitar comandos como:

```bash
docker compose logs
cat application.log
journalctl
mvn test -X
mvn -X