package com.marau.marau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            QuartoIndisponivelException.class,
            CapacidadeExcedidaException.class,
            DataInvalidaException.class,
            RecursoNaoPermitidoException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, Object>> tratarRegraNegocio(RuntimeException ex) {
        return resposta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> tratarNaoEncontrado(NoSuchElementException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Registro não encontrado.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Dados inválidos na requisição.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErroJava(Exception ex) {
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> resposta(HttpStatus status, String mensagem) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("erro", status.getReasonPhrase());
        body.put("mensagem", mensagem);
        body.put("horario", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}
