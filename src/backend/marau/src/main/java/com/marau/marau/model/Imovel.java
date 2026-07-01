package com.marau.marau.model;

import jakarta.persistence.*;

@Entity
@Table(name = "imoveis")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String cidade;
    private String bairro;
    private String endereco;
    @Lob
    @Column(name = "imagem_url", columnDefinition = "LONGTEXT")
    private String imagemUrl;
    private String tipo;
    private int quartos;
    private int banheiros;
    private int camas;
    private int hospedes;
    private double precoNoite;
    private boolean wifi;
    private boolean piscina;
    private boolean arCondicionado;
    private boolean estacionamento;
    private boolean petFriendly;
    private boolean ativo = true;

    @ManyToOne
    private Usuario anfitriao;

    public Imovel() {}

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getQuartos() { return quartos; }
    public void setQuartos(int quartos) { this.quartos = quartos; }
    public int getBanheiros() { return banheiros; }
    public void setBanheiros(int banheiros) { this.banheiros = banheiros; }
    public int getCamas() { return camas; }
    public void setCamas(int camas) { this.camas = camas; }
    public int getHospedes() { return hospedes; }
    public void setHospedes(int hospedes) { this.hospedes = hospedes; }
    public double getPrecoNoite() { return precoNoite; }
    public void setPrecoNoite(double precoNoite) { this.precoNoite = precoNoite; }
    public boolean isWifi() { return wifi; }
    public void setWifi(boolean wifi) { this.wifi = wifi; }
    public boolean isPiscina() { return piscina; }
    public void setPiscina(boolean piscina) { this.piscina = piscina; }
    public boolean isArCondicionado() { return arCondicionado; }
    public void setArCondicionado(boolean arCondicionado) { this.arCondicionado = arCondicionado; }
    public boolean isEstacionamento() { return estacionamento; }
    public void setEstacionamento(boolean estacionamento) { this.estacionamento = estacionamento; }
    public boolean isPetFriendly() { return petFriendly; }
    public void setPetFriendly(boolean petFriendly) { this.petFriendly = petFriendly; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public Usuario getAnfitriao() { return anfitriao; }
    public void setAnfitriao(Usuario anfitriao) { this.anfitriao = anfitriao; }
}
