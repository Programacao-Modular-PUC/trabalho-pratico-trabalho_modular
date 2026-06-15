# Entrega de hoje - Exceções, JUnit e novos requisitos

## Objetivo atendido
O projeto foi adaptado para tratar exceções, lançar exceções personalizadas e incluir testes unitários com JUnit.

## Exceções personalizadas criadas
- `QuartoIndisponivelException`
- `CapacidadeExcedidaException`
- `DataInvalidaException`
- `RecursoNaoPermitidoException`

Também foi criado o `GlobalExceptionHandler`, que trata exceções personalizadas e exceções comuns do Java, retornando mensagens mais claras para o front-end.

## Regras implementadas no back-end
- Quarto individual não permite berço.
- Quarto duplo permite berço com acréscimo no valor.
- Quarto família calcula acréscimo conforme capacidade.
- Validação de datas de entrada e saída.
- Validação de limite de hóspedes.
- Validação de disponibilidade do quarto por período.
- Cancelamento de aluguel.
- Histórico de aluguéis por cliente.
- Filtro por tipo de quarto.

## Testes JUnit criados
Arquivo:
`src/backend/marau/src/test/java/com/marau/marau/service/HospedagemServiceTest.java`

Testa:
- Cálculo de diária por tipo de quarto.
- Regra de berço.
- Limite de hóspedes.
- Datas inválidas.
- Disponibilidade do quarto.

## Novos endpoints principais

### Filtrar quartos por tipo
`GET /quartos?tipo=INDIVIDUAL`

Também aceita:
- `DUPLO`
- `FAMILIA`

### Cancelar aluguel
`PUT /alugueis/{id}/cancelar`

### Histórico por cliente
`GET /alugueis/cliente/{clienteId}`

## Front-end atualizado
- Página de hospedagens com filtro por tipo.
- Painel com histórico de reservas do cliente.
- Botão de cancelar reserva.
- Página Sobre alterada para falar do objetivo de divulgar Maraú, sem citar tecnologias.
