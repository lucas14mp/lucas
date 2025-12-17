package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha19;
import br.com.bb.cbe.conexao.Conexao;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.PaisController;
import br.com.bb.cbe.controllers.StatusController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha19DAO {

    public static void create(Ficha19 ficha) {

        String sql = ("INSERT INTO ficha19 (exporta_mercadoria, data_criacao, trimestre, chave, id_status) VALUES (?,?,?,?,?)");
        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setBoolean(1, ficha.isExportaMercadoria());
            pst.setDate(2, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(3, ficha.getTrimestre());
            pst.setString(4, ficha.getFuncionario().getChave());
            pst.setInt(5, ficha.getStatus().getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Ficha19 ficha) {

        String sql = "UPDATE ficha19 SET exporta_mercadoria = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setBoolean(1, ficha.isExportaMercadoria());
            pst.setString(2, ficha.getFuncionario().getChave());
            pst.setDate(3, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(4, ficha.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM ficha19 WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static List<Ficha19> getAllFichas() {
        String query = "SELECT * FROM ficha19";
        List<Ficha19> listaFichas = new ArrayList<Ficha19>();
        Connection connection = null;
        Ficha19 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha19();
                ficha.setId(rs.getInt("id"));
                ficha.setExportaMercadoria(rs.getBoolean("exporta_mercadoria"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
        return listaFichas;
    }

    public static Optional<Ficha19> getFichaById(int id) {
        String query = "SELECT * FROM ficha19 WHERE id = " + id;
        Connection connection = null;
        Ficha19 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha19();
                ficha.setId(rs.getInt("id"));
                ficha.setExportaMercadoria(rs.getBoolean("exporta_mercadoria"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                return Optional.of(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
        return Optional.empty();
    }

    public static void validarFormularios(List<String> idsValidados, String chave) {
        if (idsValidados.isEmpty()) {
            return;
        }
        StringBuilder sql = new StringBuilder("UPDATE ficha19 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
        sql.append(idsValidados.get(0));
        for (int i = 1; i < idsValidados.size(); i++) {
            sql.append(",");
            sql.append(idsValidados.get(i));
        }
        sql.append(")");

        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql.toString());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
}
