/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.DAO;


import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.Utils.NumeroUtils;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T1092407
 */
public class JustificativaDAO {
    
    public static List<Justificativa> getAllJustificativas(){
        List<Justificativa> justificativas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT * FROM justificativa";
        MoedaController moedactr = new MoedaController();
        
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {            
//                System.out.println("ENTROU ALLCOSIFS");
                Justificativa justificativa = new Justificativa();
                justificativa.setId(rs.getInt("id"));
                justificativa.setJust(rs.getString("justificativa"));
                justificativa.setNumeroFicha(rs.getString("numero_ficha"));
                justificativa.setDataCriacao(rs.getDate("data_criacao"));
                justificativa.setSomatorio(rs.getDouble("somatorio"));
                justificativa.setContabil(rs.getDouble("contabil"));
                justificativa.setDiferenca(rs.getDouble("diferenca"));
                justificativas.add(justificativa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return justificativas;
    }
    
    public static void createBatchJustDAO(Justificativa just) {

        String sql = "INSERT INTO justificativa (justificativa, numero_ficha, data_criacao, somatorio, contabil, diferenca, chave) VALUES (?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);


            pst.setString(1, just.getJust());
            pst.setString(2, just.getNumeroFicha());
            pst.setDate(3, new java.sql.Date(just.getDataCriacao().getTime()));               
            pst.setDouble(4, just.getSomatorio());
            pst.setDouble(5, just.getContabil());
            pst.setDouble(6, just.getDiferenca());
            pst.setString(7, just.getFuncionario().getChave());
            pst.addBatch();

            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("CRIOU JUSTIFICATIVA" );
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Reverte a transação em caso de erro
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
    
}
