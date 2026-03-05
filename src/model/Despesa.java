package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {
    
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Categoria categoria;  
    
    public Despesa() {
    }
    
    // Construtor para criar despesa nova (sem ID)
    public Despesa(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }
    
    // Construtor completo (quando buscar do banco)
    public Despesa(Integer id, String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "Despesa [id=" + id + 
               ", descricao=" + descricao + 
               ", valor=" + valor + 
               ", data=" + data + 
               ", categoria=" + categoria.getNome() + "]";
    }
}