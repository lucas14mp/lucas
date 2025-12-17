package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoedaDAO {

    Conexao conexao = new Conexao();

    public static Moeda getMoedaById(int id) {
        Moeda moeda = new Moeda();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_moeda, nome, sigla, simbolo FROM Moeda WHERE id_moeda = " + id;

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            moeda.setId(rs.getInt("id_moeda"));
            moeda.setNome(rs.getString("nome"));
            moeda.setSigla(rs.getString("sigla"));
            moeda.setSimbolo(rs.getString("simbolo"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return moeda;
    }

    public static List<Moeda> getAllMoedas() {

        List<Moeda> listaMoedas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_moeda, nome, sigla, simbolo FROM Moeda ORDER BY nome";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Moeda moeda = new Moeda();
                moeda.setId(rs.getInt("id_moeda"));
                moeda.setNome(rs.getString("nome"));
                moeda.setSigla(rs.getString("sigla"));
                moeda.setSimbolo(rs.getString("simbolo"));
                listaMoedas.add(moeda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return listaMoedas;
    }
    public static List<Moeda> getAllMoedasEstrangeiras() {

        List<Moeda> listaMoedas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_moeda, nome, sigla FROM Moeda WHERE id_moeda != 16 ORDER BY nome";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Moeda moeda = new Moeda();
                moeda.setId(rs.getInt("id_moeda"));
                moeda.setNome(rs.getString("nome"));
                moeda.setSigla(rs.getString("sigla"));
                listaMoedas.add(moeda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return listaMoedas;
    }
    
    public static List<Moeda> getAllMoedasSimbolos() {

        List<Moeda> listaMoedas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT simbolo FROM Moeda";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Moeda moeda = new Moeda();
                moeda.setSimbolo(rs.getString("simbolo"));
                listaMoedas.add(moeda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return listaMoedas;
    }
    
    public static Moeda getMoedaByNome(String nome) {
        Moeda moeda = new Moeda();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_moeda, nome, sigla, simbolo FROM Moeda WHERE nome = ?";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            rs.next();
            moeda.setId(rs.getInt("id_moeda"));
            moeda.setNome(rs.getString("nome"));
            moeda.setSigla(rs.getString("sigla"));
            moeda.setSimbolo(rs.getString("simbolo"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return moeda;
    }
    
    public static Moeda getMoedaBySigla(String nome) {
        Moeda moeda = new Moeda();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_moeda, nome, sigla, simbolo FROM Moeda WHERE sigla = ?";
        System.out.println("GET: " + nome);
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            rs.next();
            moeda.setId(rs.getInt("id_moeda"));
            moeda.setNome(rs.getString("nome"));
            moeda.setSigla(rs.getString("sigla"));
            moeda.setSimbolo(rs.getString("simbolo"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return moeda;
    }
    
    public static boolean moedaExiste(String sigla){
        String query = "SELECT * FROM moeda";
    //    String queryAno = "SELECT data_criacao FROM ficha11_participacao_maior WHERE id_empresa = ?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        List<String> siglas = new ArrayList<>();
        try {
    //          PEGANDO OS ANOS DAS EMPRESAS COM O ID PASSADO  
                connection = Conexao.conectar();
                pst = connection.prepareStatement(query);
                rs = pst.executeQuery();
                while (rs.next()){
                    String moeda = rs.getString("sigla");
                    siglas.add(moeda);
                }
                for (String sig : siglas){
                    System.out.println("TESTE TAXA DA FICHA: " + sigla);
                    System.out.println("TESTE TAXA DA BASE:" + sig);
                    if (sigla.equals(sig)){
                        System.out.println("MOEDA JÁ ESTÁ REGISTRADA NA BASE");
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Conexao.fecharConexao(connection, pst, rs);
            }
        return false; // Retorna false se não encontrar nada
    }
}
