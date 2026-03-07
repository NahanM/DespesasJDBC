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
    	setDescricao(descricao);
        setValor(valor);
        setData(data);
        setCategoria(categoria);
    }
    
    // Construtor completo (quando buscar do banco)
    public Despesa(Integer id, String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
        this.id = id;
        setDescricao(descricao);
        setValor(valor);
        setData(data);
        setCategoria(categoria);
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
    	if (descricao == null) {
    		throw new IllegalArgumentException("Descrição não pode ser vazia");
    	}
        this.descricao = descricao;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
    	if (valor== null || valor.compareTo(BigDecimal.ZERO) >= 0) {
    		throw new IllegalArgumentException("Valor não pode ser 0 ou nulo");
    	}
        this.valor = valor;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public void setData(LocalDate data) {
    	if(data == null) {
    		throw new IllegalArgumentException("Data não pode ser vazia");
    	}
        this.data = data;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        if(categoria == null){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
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