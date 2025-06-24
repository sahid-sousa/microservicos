![Java 17+](https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-%207.x-02303A?logo=gradle&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)
[![Continuous Integration With Github Actions](https://github.com/sahid-sousa/rest-with-spring-boot/actions/workflows/continuous-integration.yml/badge.svg)](https://github.com/sahid-sousa/rest-with-spring-boot/actions/workflows/continuous-integration.yml)

# 🐳 Ambiente de Microsserviços com Docker

Este repositório define um ambiente completo de microsserviços com suporte a mensageria, persistência em banco de dados e integração via SFTP, utilizando **Docker**.

## 📦 Serviços Incluídos

| Serviço                | Porta(s)     | Descrição                                   |
|------------------------|--------------|---------------------------------------------|
| **RabbitMQ**           | 5672 / 15672 | Broker de mensagens com painel web          |
| **PostgreSQL**         | 5432         | Banco de dados relacional                   |
| **SFTP (Atmoz)**       | 2222         | Servidor de arquivos via SFTP               |
| **Pedido Producer**    | 8080         | Envia mensagens para o RabbitMQ             |
| **Pedido Consumer**    | 8085         | Consome mensagens e grava no banco de dados |
| **Venda Producer**     | 8098         | Envia mensagens e arquivos via SFTP         |
| **Venda Consumer**     | 8097         | Consome mensagens e grava no banco de dados |
| **Transação Producer** | 8095         | Envia mensagens para o RabbitMQ             |
| **Transação Consumer** | 8090         | Consome mensagens e grava no banco de dados |

---

## ⚙️ Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## 🚀 Como executar

### 🔨 1. Gere os JARs dos serviços com Gradle

Antes de executar os containers, é necessário compilar os projetos dos serviços produtores e consumidores utilizando o Gradle.
Certifique-se de que o Java 17 esteja configurado como padrão no sistema, pois os microsserviços foram desenvolvidos com essa versão.

1. **No dirétorio microservicos execute:**

```
./gradlew clean build
```

> Em sistemas Windows, utilize `gradlew.bat` ao invés de `./gradlew`.

Exemplo:

```
./gradlew clean build -x test
```

2. **Verifique a estrutura de diretórios**

Certifique-se de que os diretórios abaixo existem:

```
.
├── docker-compose.yml
├── pedido-producer/
├── pedido-consumer/
├── venda-producer/
├── venda-consumer/
├── transacao-producer/
├── transacao-consumer/
├── upload/                      # Diretório compartilhado via SFTP
└── init-db/                    # Scripts SQL para inicialização do banco
```

3. **Suba os containers**

```
docker-compose up --build -d
```

O parâmetro `--build` força a reconstrução das imagens customizadas.

---

## 🔗 Acessos Rápidos

- **RabbitMQ UI:** [http://localhost:15672](http://localhost:15672)
  - Usuário: `guest`
  - Senha: `guest`

- **PostgreSQL:**
  - Host: `localhost`
  - Porta: `5432`
  - Usuário: `postgres`
  - Senha: `postgres`

- **SFTP:**
  - Host: `localhost`
  - Porta: `2222`
  - Usuário: `development`
  - Senha: `12345`
  - Caminho: `/home/development/out/gateway`

---

## 🧼 Como parar os serviços

```
docker-compose down
```

Se desejar também remover os volumes (banco de dados, arquivos persistidos, etc):

```
docker-compose down -v
```

---

## 📝 Observações

- Os serviços `pedido-consumer`, `venda-consumer` e `transacao-consumer` dependem de PostgreSQL e RabbitMQ e só iniciarão quando os mesmos estiverem saudáveis (`healthcheck`).
- O diretório `upload/` é utilizado pelo container SFTP para disponibilizar arquivos via login SFTP.
- O diretório `init-db/` pode conter scripts `.sql` que serão executados automaticamente na inicialização do PostgreSQL.
