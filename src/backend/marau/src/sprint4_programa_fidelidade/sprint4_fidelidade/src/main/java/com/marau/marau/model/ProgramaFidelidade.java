package com.marau.marau.model;

import com.marau.marau.enums.CategoriaFidelidade;
import com.marau.marau.fidelidade.GerenciadorFidelidade;
import jakarta.persistence.*;

/**
 * Entidade que armazena o histórico de fidelidade de um cliente.
 *
 * Cada cliente possui exatamente um registro de fidelidade. A categoria
 * é recalculada automaticamente com base no total de hospedagens concluídas,
 * delegando a lógica ao {@link GerenciadorFidelidade} (Singleton).
 */
@Entity
@Table(name = "programa_fidelidade")
public class ProgramaFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id", unique = true, nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private int totalHospedagens = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaFidelidade categoria = CategoriaFidelidade.BRONZE;

    public ProgramaFidelidade() {}

    public ProgramaFidelidade(Cliente cliente) {
        this.cliente = cliente;
        this.totalHospedagens = 0;
        this.categoria = CategoriaFidelidade.BRONZE;
    }

    // -------------------------------------------------------------------------
    // Lógica de domínio
    // -------------------------------------------------------------------------

    /**
     * Incrementa o contador de hospedagens e recalcula a categoria.
     * Deve ser chamado ao confirmar o check-out de uma hospedagem.
     */
    public void registrarHospedagem() {
        this.totalHospedagens++;
        this.categoria = GerenciadorFidelidade.getInstance()
                .determinarCategoria(this.totalHospedagens);
    }

    // -------------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------------

    public Long getId() { return id; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public int getTotalHospedagens() { return totalHospedagens; }

    public void setTotalHospedagens(int totalHospedagens) {
        this.totalHospedagens = totalHospedagens;
        this.categoria = GerenciadorFidelidade.getInstance()
                .determinarCategoria(this.totalHospedagens);
    }

    public CategoriaFidelidade getCategoria() { return categoria; }
}
