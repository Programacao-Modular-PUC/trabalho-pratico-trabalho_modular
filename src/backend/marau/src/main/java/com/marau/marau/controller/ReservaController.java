package com.marau.marau.controller;

import com.marau.marau.enums.StatusReserva;
import com.marau.marau.exception.CapacidadeExcedidaException;
import com.marau.marau.exception.DataInvalidaException;
import com.marau.marau.model.Imovel;
import com.marau.marau.model.Reserva;
import com.marau.marau.model.Usuario;
import com.marau.marau.repository.ImovelRepository;
import com.marau.marau.repository.ReservaRepository;
import com.marau.marau.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImovelRepository imovelRepository;

    public ReservaController(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, ImovelRepository imovelRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.imovelRepository = imovelRepository;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    @GetMapping("/usuario/{id}")
    public List<Reserva> listarPorUsuario(@PathVariable Long id) {
        return reservaRepository.findByUsuarioId(id);
    }

    @GetMapping("/anfitriao/{id}")
    public List<Reserva> listarPorAnfitriao(@PathVariable Long id) {
        return reservaRepository.findByImovelAnfitriaoId(id);
    }

    @PostMapping
    public ResponseEntity<Reserva> reservar(@RequestBody Reserva reserva) {
        if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário não informado.");
        }
        if (reserva.getImovel() == null || reserva.getImovel().getId() == null) {
            throw new IllegalArgumentException("Imóvel não informado.");
        }

        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
        Imovel imovel = imovelRepository.findById(reserva.getImovel().getId())
                .orElseThrow(() -> new NoSuchElementException("Imóvel não encontrado."));

        if (reserva.getQuantidadeHospedes() > imovel.getHospedes()) {
            throw new CapacidadeExcedidaException("Quantidade de hóspedes maior que a capacidade do imóvel.");
        }
        if (reserva.getCheckout() == null || reserva.getCheckin() == null || !reserva.getCheckout().isAfter(reserva.getCheckin())) {
            throw new DataInvalidaException("Datas inválidas para reserva.");
        }

        reserva.setUsuario(usuario);
        reserva.setImovel(imovel);
        reserva.setStatus(StatusReserva.CONFIRMADA);
        reserva.calcularValorTotal();

        return ResponseEntity.ok(reservaRepository.save(reserva));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva não encontrada."));
        reserva.setStatus(StatusReserva.CANCELADA);
        return ResponseEntity.ok(reservaRepository.save(reserva));
    }
}
