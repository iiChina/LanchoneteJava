package br.com.lanchonete.modelo;

public class Produto {

    private int id;
    private String descricao;
    private double preco;
    
    public Produto(String descricao) {
        this.descricao = descricao;
    }
    
    public Produto(String descricao, double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
}
