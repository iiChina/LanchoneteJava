package br.com.lanchonete.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pedido {

    public int id;
    public LocalDateTime dataHoraPedido;
    public LocalDateTime dataAlteracaoPedido;
    public ArrayList<Produto> produtos;
    public double valorTotal;
    
    public Pedido(int id, double valorTotal) {
        this.id = id;
        this.valorTotal = valorTotal;
    }
    
    public Pedido(ArrayList<Produto> produtos) {
        this.dataHoraPedido = LocalDateTime.now();
        this.produtos = produtos;
        
        for(int i=0;i<produtos.size();i++){
            this.valorTotal += produtos.get(i).getPreco();
        }
    }

    public LocalDateTime getDataHoraPedido() {
        return dataHoraPedido;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void adcProduto(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }

    private void altValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
}
