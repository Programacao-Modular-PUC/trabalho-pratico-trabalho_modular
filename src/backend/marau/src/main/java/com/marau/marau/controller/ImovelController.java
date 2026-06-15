package com.marau.marau.controller;

import com.marau.marau.model.Imovel;
import com.marau.marau.model.Usuario;
import com.marau.marau.repository.ImovelRepository;
import com.marau.marau.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    private final ImovelRepository imovelRepository;
    private final UsuarioRepository usuarioRepository;

    public ImovelController(ImovelRepository imovelRepository, UsuarioRepository usuarioRepository) {
        this.imovelRepository = imovelRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Imovel> listar(@RequestParam(required = false) String cidade,
                               @RequestParam(required = false) String tipo) {
        boolean temCidade = cidade != null && !cidade.isBlank();
        boolean temTipo = tipo != null && !tipo.isBlank();

        if (temCidade && temTipo) {
            return imovelRepository.findByCidadeContainingIgnoreCaseAndTipoContainingIgnoreCaseAndAtivoTrue(cidade, tipo);
        }
        if (temCidade) {
            return imovelRepository.findByCidadeContainingIgnoreCaseAndAtivoTrue(cidade);
        }
        if (temTipo) {
            return imovelRepository.findByTipoContainingIgnoreCaseAndAtivoTrue(tipo);
        }
        return imovelRepository.findByAtivoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscar(@PathVariable Long id) {
        return imovelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/anfitriao/{id}")
    public List<Imovel> listarDoAnfitriao(@PathVariable Long id) {
        return imovelRepository.findByAnfitriaoId(id);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Imovel imovel) {
        if (imovel.getAnfitriao() == null || imovel.getAnfitriao().getId() == null) {
            return ResponseEntity.badRequest().body("Informe o anfitrião do imóvel.");
        }

        Usuario anfitriao = usuarioRepository.findById(imovel.getAnfitriao().getId()).orElse(null);
        if (anfitriao == null) {
            return ResponseEntity.badRequest().body("Anfitrião não encontrado.");
        }

        imovel.setAnfitriao(anfitriao);
        imovel.setAtivo(true);
        return ResponseEntity.ok(imovelRepository.save(imovel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Imovel dados) {
        Imovel imovel = imovelRepository.findById(id).orElse(null);
        if (imovel == null) return ResponseEntity.notFound().build();

        imovel.setTitulo(dados.getTitulo());
        imovel.setDescricao(dados.getDescricao());
        imovel.setCidade(dados.getCidade());
        imovel.setBairro(dados.getBairro());
        imovel.setEndereco(dados.getEndereco());
        imovel.setImagemUrl(dados.getImagemUrl());
        imovel.setTipo(dados.getTipo());
        imovel.setQuartos(dados.getQuartos());
        imovel.setBanheiros(dados.getBanheiros());
        imovel.setCamas(dados.getCamas());
        imovel.setHospedes(dados.getHospedes());
        imovel.setPrecoNoite(dados.getPrecoNoite());
        imovel.setWifi(dados.isWifi());
        imovel.setPiscina(dados.isPiscina());
        imovel.setArCondicionado(dados.isArCondicionado());
        imovel.setEstacionamento(dados.isEstacionamento());
        imovel.setPetFriendly(dados.isPetFriendly());

        return ResponseEntity.ok(imovelRepository.save(imovel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Imovel imovel = imovelRepository.findById(id).orElse(null);
        if (imovel == null) return ResponseEntity.notFound().build();

        imovel.setAtivo(false);
        imovelRepository.save(imovel);
        return ResponseEntity.ok("Imóvel removido da listagem.");
    }
}
