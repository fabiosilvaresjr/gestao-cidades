# API de Cidades

Projeto backend em Spring Boot para gerenciamento de cidades e comércios.

---

## Funcionalidades

- Buscar cidade por ID  
- Listar todas as cidades  
- Filtrar cidades por:
  - Nome
  - UF
  - Capitais  

---

## Tecnologias utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Hibernate  
- Banco H2  

---

## Como rodar o projeto

No IntelliJ ou terminal: 
mvn spring-boot:run

---

## Endpoints disponíveis

- GET /cidades  
- GET /cidades/{id}  
- GET /cidades/capitais  
- GET /cidades/nome/{nome}  
- GET /cidades/uf/{uf}  

---

## Banco H2 (console)

Acesse no navegador:

http://localhost:8080/placeti/h2-console

Dados de conexão:

- URL: jdbc:h2:mem:database  
- Usuário: admin  
- Senha: admin  

---

## Observações

Este projeto foi desenvolvido como prática de backend com Spring Boot, com foco em:

- criação de APIs REST  
- manipulação de dados com streams  
- organização em camadas (controller, service, repository)  
