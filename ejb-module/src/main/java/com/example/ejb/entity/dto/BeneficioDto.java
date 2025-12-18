package com.example.ejb.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BeneficioDto {

    private Integer id;

    @JsonProperty("nome")
    public String nome;

    @JsonProperty("descricao")
    public String descricao;

    @JsonProperty("valor")
    public BigDecimal valor;

    @JsonProperty("ativo")
    public String ativo;

    @JsonProperty("version")
    public String version;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getAtivo() {
        return ativo;
    }

    public String getVersion() {
        return version;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
