# Guia de Execução — Projeto Maraú

## Descrição
Sistema web para gerenciamento de hospedagens em Maraú utilizando:

- Frontend: HTML, CSS e JavaScript
- Backend: Spring Boot
- Banco de Dados: MySQL
- Persistência: JPA / Hibernate

O sistema permite:

- Cadastro de residências
- Cadastro de quartos
- Cadastro de clientes
- Cadastro de aluguéis
- Regras específicas por tipo de quarto
- Integração entre frontend e backend

---

## Tecnologias utilizadas

- Java 26
- Spring Boot
- Maven
- MySQL
- HTML
- CSS
- JavaScript
- Thunder Client

---

## Pré-requisitos

Instalar:

- Java JDK 26
- MySQL Server
- MySQL Workbench
- VSCode
- Extensão Live Server
- Extensão Thunder Client

---

## Configuração do banco de dados

Abrir MySQL Workbench.

Executar:

```sql
CREATE DATABASE marau;
```

---

## Configuração application.properties

Arquivo:

src/main/resources/application.properties

Configuração:

```properties
spring.application.name=marau

spring.datasource.url=jdbc:mysql://localhost:3306/marau
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Troque a senha conforme sua configuração local.

---

## Executando o backend

Abra terminal na pasta:

```
src/backend/marau
```

Execute:

```bash
mvnw.cmd spring-boot:run
```

Se tudo estiver correto aparecerá:

```txt
Started MarauApplication
```

Backend:

```txt
http://localhost:8080
```

---

## Endpoints disponíveis

### Residências

GET

```txt
/residencias
```

POST

```txt
/residencias
```

---

### Quartos

GET

```txt
/quartos
```

POST

```txt
/quartos
```

Exemplo:

```json
{
 "valorBase":200,
 "possuiAR":true,
 "possuiHidro":false,
 "quantidadeCamas":3,
 "possuiBerco":false,
 "capacidadeHospedes":0,
 "tipo":"INDIVIDUAL"
}
```

---

### Clientes

GET

```txt
/clientes
```

POST

```txt
/clientes
```

Exemplo:

```json
{
 "nome":"Lucas",
 "email":"lucas@gmail.com",
 "telefone":"31999999999"
}
```

---

### Aluguéis

GET

```txt
/alugueis
```

POST

```txt
/alugueis
```

Exemplo:

```json
{
 "cliente":{
   "id":1
 },
 "quarto":{
   "id":1
 },
 "dataEntrada":"2026-05-20",
 "dataSaida":"2026-05-25"
}
```

---

## Executando o frontend

Abrir:

```txt
src/front/pages
```

Abrir:

```txt
hospedagem.html
```

Usar:

```txt
Open with Live Server
```

O frontend consome dados do backend automaticamente.

---

## Observações

As tabelas do banco são criadas automaticamente pelo Hibernate.

Tabelas:

- alugueis
- clientes
- quartos
- residencias

Não é necessário criar tabelas manualmente.



