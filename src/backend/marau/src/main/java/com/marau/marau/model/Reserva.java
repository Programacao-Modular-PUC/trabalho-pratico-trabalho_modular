package com.marau.marau.model;

import com.marau.marau.enums.StatusReserva;
import com.marau.marau.enums.TipoTarifa;
import com.marau.marau.tarifa.ContextoTarifa;
import com.marau.marau.tarifa.GerenciadorTarifas;
import jakarta.persistence.*;
import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private TipoTarifa tipoTarifa = TipoTarifa.PADRAO;

    public Reserva() {}

    public void calcularValorTotal() {
        ContextoTarifa contexto = new ContextoTarifa(
                imovel.getPrecoNoite(),
                checkin,
                checkout,
                tipoTarifa);

        this.valorTotal = GerenciadorTarifas.getInstance().calcularValorTotal(contexto);
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
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    public StatusReserva getStatus() { return status; }
    public void setStatus(StatusReserva status) { this.status = status; }
    public TipoTarifa getTipoTarifa() { return tipoTarifa; }
    public void setTipoTarifa(TipoTarifa tipoTarifa) { this.tipoTarifa = tipoTarifa == null ? TipoTarifa.PADRAO : tipoTarifa; }
}
