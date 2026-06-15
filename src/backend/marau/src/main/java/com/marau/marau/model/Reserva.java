package com.marau.marau.model;

import com.marau.marau.enums.StatusReserva;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Imovel imovel;

    private LocalDate checkin;
    private LocalDate checkout;
    private int quantidadeHospedes;
    private double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.CONFIRMADA;

    public Reserva() {}

    public void calcularValorTotal() {
        long dias = ChronoUnit.DAYS.between(checkin, checkout);
        if (dias < 1) dias = 1;
        this.valorTotal = dias * imovel.getPrecoNoite();
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }
    public LocalDate getCheckin() { return checkin; }
    public void setCheckin(LocalDate checkin) { this.checkin = checkin; }
    public LocalDate getCheckout() { return checkout; }
    public void setCheckout(LocalDate checkout) { this.checkout = checkout; }
    public int getQuantidadeHospedes() { return quantidadeHospedes; }
    public void setQuantidadeHospedes(int quantidadeHospedes) { this.quantidadeHospedes = quantidadeHospedes; }
    public double getValorTotal() { return valorTotal; }
    public StatusReserva getStatus() { return status; }
    public void setStatus(StatusReserva status) { this.status = status; }
}
