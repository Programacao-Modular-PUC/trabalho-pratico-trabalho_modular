# Projeto Maraú Stay atualizado

O projeto foi ampliado para funcionar como um sistema de hospedagens estilo Airbnb, com front-end em HTML/CSS/JS e back-end Java Spring Boot salvando no banco MySQL.

## Principais funções adicionadas

- Cadastro de usuário: cliente, anfitrião ou admin.
- Login simples pelo back-end.
- Cadastro de imóveis/apartamentos.
- Listagem de imóveis vindos do banco.
- Busca por cidade.
- Tela de reserva.
- Cálculo do valor total da reserva no back-end.
- Painel do usuário com reservas e imóveis cadastrados.
- Layout novo e mais moderno.

## Como rodar o back-end

Entre na pasta:

```bash
cd src/backend/marau
```

Confira o arquivo:

```bash
src/main/resources/application.properties
```

Ele está configurado assim:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marau
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
```

No MySQL, crie o banco:

```sql
CREATE DATABASE marau;
```

Depois rode:

```bash
./mvnw spring-boot:run
```

No Windows PowerShell:

```bash
.\mvnw.cmd spring-boot:run
```

## Como rodar o front-end

Abra no VS Code a pasta do projeto, vá em:

```bash
src/front/pages/index.html
```

Clique com botão direito e escolha **Open with Live Server** ou clique em **Go Live**.

## Ordem para testar

1. Rode o back-end.
2. Abra o front com Go Live.
3. Entre em `cadastro.html` e crie uma conta como `ANFITRIAO`.
4. Vá em `cadastrar-imovel.html` e cadastre um imóvel.
5. Vá em `hospedagem.html` e veja o imóvel listado.
6. Clique em reservar.
7. Faça a reserva.
8. Veja tudo em `dashboard.html`.

## Endpoints principais

- `POST /auth/cadastro`
- `POST /auth/login`
- `GET /imoveis`
- `GET /imoveis/{id}`
- `POST /imoveis`
- `PUT /imoveis/{id}`
- `DELETE /imoveis/{id}`
- `POST /reservas`
- `GET /reservas/usuario/{id}`
- `GET /reservas/anfitriao/{id}`

## Observação

O login foi feito de forma simples para trabalho acadêmico. A senha fica salva diretamente no banco, sem criptografia. Para projeto real, seria necessário usar Spring Security e senha criptografada.
