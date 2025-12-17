/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Bean.Contabil;
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
public class ContabilDAO {

    public static List<Contabil> getAllCosifs(){
        List<Contabil> cosifs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT * FROM contabil";
        MoedaController moedactr = new MoedaController();
        
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {            
//                System.out.println("ENTROU ALLCOSIFS");
                Contabil cosif = new Contabil();
                cosif.setId(rs.getInt("id"));
                cosif.setCosif(rs.getString("cosif"));
                cosif.setNome(rs.getString("nome"));
                cosif.setSaldo(rs.getDouble("saldo"));
                cosif.setDataCriacao(rs.getDate("data_criacao"));
                cosifs.add(cosif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return cosifs;
    }
    
    public static Contabil getCosifByCosif(){
        Contabil cosif = new Contabil();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT * FROM contabil WHERE cosif = ?";
        MoedaController moedactr = new MoedaController();
        
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
//            System.out.println("ENTROU COSIFBYCOSIF");
            cosif.setId(rs.getInt("id"));
            cosif.setCosif(rs.getString("cosif"));
            cosif.setNome(rs.getString("nome"));
            cosif.setSaldo(rs.getDouble("saldo"));
            cosif.setDataCriacao(rs.getDate("data_criacao"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return cosif;
    }
    
}
