package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha01;
import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Bean.Pais;
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaisDAO {

    Conexao conexao = new Conexao();

    public static Pais getPaisById(int id) {
        Pais pais = new Pais();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_pais, nome FROM Pais WHERE id_pais = " + id;

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            pais.setId(rs.getInt("id_pais"));
            pais.setNome(rs.getString("nome"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return pais;
    }
    
    public static List<Pais> getAllPaises() {

        List<Pais> listaPaises = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_pais, nome FROM Pais ORDER BY nome";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Pais pais = new Pais();
                pais.setId(rs.getInt("id_pais"));
                pais.setNome(rs.getString("nome"));
                listaPaises.add(pais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }

        return listaPaises;
    }
    public static List<Pais> getAllPaisesEstrangeiros() {

        List<Pais> listaPaises = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_pais, nome FROM Pais WHERE id_pais != 26 ORDER BY nome";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Pais pais = new Pais();
                pais.setId(rs.getInt("id_pais"));
                pais.setNome(rs.getString("nome"));
                listaPaises.add(pais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }

        return listaPaises;
    }

    public static Pais getPaisByNome(String nome) {
        Pais pais = new Pais();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_pais, nome FROM Pais WHERE nome = ?";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            rs.next();
            pais.setId(rs.getInt("id_pais"));
            pais.setNome(rs.getString("nome"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return pais;
    }
}