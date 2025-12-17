package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.*;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.controllers.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha10DAO {

    Conexao conexao = new Conexao();

    public static void create(Ficha10 ficha) {
        String sql = "INSERT INTO ficha10(metodo_valoracao, valor_database, data_criacao, trimestre, id_moeda, id_pais, chave, id_status) VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getMetodoValoracao());
            pst.setDouble(2, ficha.getValorDatabase());
            pst.setDate(3, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(4, DataUtils.validaTrimestre()); // Considere usar ficha.getTrimestre() se já estiver setado
            pst.setInt(5, ficha.getMoeda().getId());
            pst.setInt(6, ficha.getPais().getId());
            pst.setString(7, ficha.getFuncionario().getChave());
            pst.setInt(8, ficha.getStatus().getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void update(Ficha10 ficha) {
        String sql = "UPDATE ficha10 SET metodo_valoracao = ?, valor_database = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getMetodoValoracao());
            pst.setDouble(2, ficha.getValorDatabase());
            pst.setInt(3, ficha.getMoeda().getId());
            pst.setInt(4, ficha.getPais().getId());
            pst.setString(5, ficha.getFuncionario().getChave());
            pst.setDate(6, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(7, ficha.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM ficha10 WHERE id = ?";
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

    public static List<Ficha10> getAllFichas() {
        String query = "SELECT * FROM ficha10";
        List<Ficha10> listaFichas = new ArrayList<Ficha10>();
        Connection connection = null;
        Ficha10 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha10();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    // MANTER O SWITCH ORIGINAL AQUI
    public static List<Ficha10> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha10 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha10> listaFichas = new ArrayList<Ficha10>();
        Connection connection = null;
        Ficha10 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha10();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    public static Optional<Ficha10> getFichaById(int id) {
        String query = "SELECT * FROM ficha10 WHERE id = " + id;
        Connection connection = null;
        Ficha10 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        PaisController paisController = new PaisController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha10();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha10 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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

    // ========================================================================
    // NOVOS MÉTODOS PARA OS FILTROS PARCIAIS
    // ========================================================================

    public static List<Integer> getAnosExistentes() {
        List<Integer> anos = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha10 ORDER BY ano DESC";
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getObject("ano") != null) anos.add(rs.getInt("ano"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return anos;
    }

    public static List<Integer> getTrimestresExistentes() {
        List<Integer> trimestres = new ArrayList<>();
        String sql = "SELECT DISTINCT trimestre FROM ficha10 ORDER BY trimestre ASC";
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getObject("trimestre") != null) trimestres.add(rs.getInt("trimestre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return trimestres;
    }

    // Busca apenas por Ano
    public static List<Ficha10> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha10 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha10> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha10 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha10();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    // Busca apenas por Trimestre (COM O SWITCH)
    public static List<Ficha10> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT * FROM ficha10 WHERE trimestre = " + trimestre;
        List<Ficha10> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha10 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha10();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }
}