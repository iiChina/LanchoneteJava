package br.com.lanchonete.rdn;

import br.com.lanchonete.modelo.Pedido;
import br.com.lanchonete.modelo.Produto;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.util.Calendar;

/**
 *
 * @author leonardo.chinarelo
 */
public class PedidoRdn {

    public Boolean inserirNovoPedido(Pedido pedido) {

        try {

            StringBuilder str = new StringBuilder();
            
            str.append("INSERT INTO Pedido (                ");
            str.append("            ped_dtHr, ped_vlrTotal  ");
            str.append(") OUTPUT INSERTED.ped_id            "); 
            str.append("VALUES (                            ");
            str.append("            ?, ?                    ");
            str.append(")                                   ");                                              
           
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement ps = conn.prepareStatement(str.toString(), Statement.RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(pedido.getDataHoraPedido()));
            ps.setDouble(2, pedido.getValorTotal());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            
            
            int lastId = 0;
            if(rs.next()) {
                lastId = rs.getInt(1);
                
                if(lastId == 0)
                    return false;
            }
            
            str.setLength(0);
            str.append("INSERT INTO PedidoProduto (    ");
            str.append("    ped_id, prod_id            ");
            str.append(") VALUES ");
            
            for(int i=0;i<pedido.getProdutos().size();i++) {
                str.append("( ?, ?) ");
                
                ps.setInt(1, lastId);
                ps.setInt(1, pedido.getProdutos().get(i).getId());
                
                ps = conn.prepareStatement(str.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
            }
            
            ps.close();
            conn.close();

            return true;

        } catch (SQLException ex) {
            
            System.out.println("ERRO: " + ex.getMessage());
            return false;
            
        }
    }

    public Boolean alterarPedido(Pedido cli) {

        /*
        try {
            

        } catch (SQLException ex) {
            
        }
        */
        
        return true;
    }

    public Boolean excluirPedido(int id) {
        try {

            Connection conn = ConnectionFactory.getConnection();
            
            String str = "DELETE FROM PedidoProduto WHERE ped_id = ?";
            PreparedStatement ps = conn.prepareStatement(str.toString());
            ps.setInt(1, id);

            ps.executeUpdate();
            
            str = "DELETE FROM Pedido WHERE ped_id = ?";            
            ps = conn.prepareStatement(str.toString());
            ps.setInt(1, id);
            
            int linhasAfetadas = ps.executeUpdate();

            ps.close();
            conn.close();

            return linhasAfetadas > 0;

        } catch (Exception ex) {
            
            System.out.println("Erro: " + ex.getMessage());
            return false;
            
        }

    }

    public List<Pedido> obterTodos() {
        try {

            List<Pedido> lstPedido = new ArrayList<Pedido>();

            String query = "SELECT ped_id, ped_vlrTotal FROM Pedido ";

            Connection conn = ConnectionFactory.getConnection();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
             
            while (rs.next()) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("DATANASCIMENTO"));
                
                Pedido pedido = new Pedido(rs.getInt("ped_id"), rs.getDouble("ped_vlrTotal"));

                lstPedido.add(pedido);

            }
            
            return lstPedido;

        } catch (SQLException ex) {

            System.out.println("ERRO:" + ex.getMessage());
            return null;
        }
    }
    
    public Pedido obterPorId(int id) {
        try {
            
            StringBuilder str = new StringBuilder();

            str.append("SELECT ped_id, ped_vlrTotal, prod_descricao         ");
            str.append("FROM Pedido a                                       ");
            str.append("INNER JOIN PedidoProduto b ON (a.ped_id = b.ped_id) ");
            str.append("INNER JOIN Produto c ON (b.prod_id = c.prod_id)     ");
            str.append("WHERE = ?                                   ");

            
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement state = conn.prepareStatement(str.toString());
            state.setInt(1, id);
            
            ResultSet rs = state.executeQuery();
            
            rs.next();
            Pedido pedido = new Pedido(rs.getInt("ped_id"), rs.getDouble("ped_vlrTotal"));
            
            ArrayList<Produto> produtos = new ArrayList<Produto>();
            
            while (rs.next()) {
                
                Produto produto = new Produto(rs.getString("prod_descricao"));
                
                produtos.add(produto);
            }
            
            pedido.adcProduto(produtos);
            
            return pedido;

        } catch (SQLException ex) {

            System.out.println("ERRO:" + ex.getMessage());
            return null;
        }
    }

}
