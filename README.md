# Vote API

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/viniciuslj/vote-api/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/viniciuslj/vote-api/tree/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=viniciuslj_vote-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=viniciuslj_vote-api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=viniciuslj_vote-api&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=viniciuslj_vote-api)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=viniciuslj_vote-api&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=viniciuslj_vote-api)

Uma API de vontação desenvolvida como desafio em um curso de Spring Boot.

---
### Documentação Swagger

[https://viniciuslj-vote-api.herokuapp.com/swagger-ui/](https://viniciuslj-vote-api.herokuapp.com/swagger-ui/)

---
### Requisitos

1. Cadastrar uma nova pauta 
2. Abrir uma sessão de votação para uma pauta 
   - A sessão de votação deve ficar aberta por um tempo definido na abertura ou por 1 minuto 
3. Receber votos dos associados em pautas
   - Os votos são apenas Sim ou Não
   - Cada associado é identificado por um ID único
   - Pode votar apenas uma vez por pauta
4. Contabilizar os votos e dar o resultado da votação na pauta
5. A segurança da API pode ser abstraída e qualquer chamada pode ser considerada autorizada

#### Requisitos Complementares

1. Integração com sistemas externos
   - A partir do CPF do associado, verificar se ele pode votar
   - API [https://user-info.herokuapp.com/users/{cpf}](https://user-info.herokuapp.com/users/08200217094)
2. Mensageria e filas
   - Utilizar alguma plataforma de mensageria para notificar o resultado no término da votação

---

### Modelo Relacional

![Modelo Relacional](https://github.com/viniciuslj/vote-api/raw/assets/modelo-relacional.png)

---

### Configuração de Desenvolvimento

#### Ambiente

* Java 18.0.2
* SpringBoot 2.7.3
* Docker 20.10.17
* Postgres 14.5
* Kafka (Confluent platform Kafka 7.2.1)

#### Download do projeto

``git clone https://github.com/viniciuslj/vote-api.git``

#### Download e execução dos containeres

``cd vote-api``

``docker-compose -f docker\docker-compose.yml up -d``

Verificar se os containeres foram iniciados

``docker ps``

```
confluentinc/cp-kafka
confluentinc/cp-zookeeper
postgres:14.5
```

#### Execução da aplicação 

* Windows 

  ``mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev``


* Linux 
  
  ``./mvnw spring-boot:run -Dspring-boot.run.profiles=dev``


Acesse: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

#### Testes

* Windows

  ``mvnw.cmd test``


* Linux

  ``./mvnw test``

---

### Integração Contínua

![Diagrama da Integração Contínua](https://github.com/viniciuslj/vote-api/raw/assets/diagrama-ci.png)