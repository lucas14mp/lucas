package br.com.bb.cbe.DAO;

import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.Bean.*;
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

public class Ficha08DAO {

    Conexao conexao = new Conexao();

    public static void create(Ficha08 ficha) {
        String sql = "INSERT INTO ficha08(saldo_database, rendimentos, data_criacao, trimestre, id_moeda, id_pais, chave, id_status) VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getSaldoDatabase());
            pst.setDouble(2, ficha.getRendimentos());
            pst.setDate(3, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(4, ficha.getTrimestre());
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

    public static void update(Ficha08 ficha) {
        String sql = "UPDATE ficha08 SET id_pais = ?, id_moeda =?, saldo_database = ?, rendimentos = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, ficha.getPais().getId());
            pst.setInt(2, ficha.getMoeda().getId());
            pst.setDouble(3, ficha.getSaldoDatabase());
            pst.setDouble(4, ficha.getRendimentos());
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
        String sql = "DELETE FROM ficha08 WHERE id = ?";
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

    public static List<Ficha08> getAllFichas() {
        String query = "SELECT ficha08.*, pais.* FROM ficha08 LEFT JOIN pais ON ficha08.id_pais = pais.id_pais WHERE ficha08.id IS NOT NULL ORDER BY nome;";
        List<Ficha08> listaFichas = new ArrayList<Ficha08>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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
    public static List<Ficha08> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT ficha08.* FROM ficha08 "
                + "LEFT JOIN pais ON ficha08.id_pais = pais.id_pais "
                + "WHERE ficha08.id IS NOT NULL "
                + "AND ficha08.trimestre = '" + trimestre + "' "
                + "AND YEAR(ficha08.data_criacao) = '" + ano + "' "
                + "ORDER BY pais.nome";
        List<Ficha08> listaFichas = new ArrayList<Ficha08>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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

    public static Optional<Ficha08> getFichaById(int id) {
        String query = "SELECT * FROM ficha08 WHERE id = " + id;
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha08 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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

    public static List<Integer> getAnosExistentes() {
        List<Integer> anos = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha08 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha08 ORDER BY trimestre ASC";
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

    // Busca apenas por Ano (Mantendo JOIN para ordenação)
    public static List<Ficha08> getAllFichasByAno(int ano) {
        String query = "SELECT ficha08.* FROM ficha08 "
                + "LEFT JOIN pais ON ficha08.id_pais = pais.id_pais "
                + "WHERE ficha08.id IS NOT NULL "
                + "AND YEAR(ficha08.data_criacao) = " + ano + " "
                + "ORDER BY pais.nome";
        List<Ficha08> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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

    // Busca apenas por Trimestre (COM O SWITCH E JOIN)
    public static List<Ficha08> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT ficha08.* FROM ficha08 "
                + "LEFT JOIN pais ON ficha08.id_pais = pais.id_pais "
                + "WHERE ficha08.id IS NOT NULL "
                + "AND ficha08.trimestre = " + trimestre + " "
                + "ORDER BY pais.nome";
        List<Ficha08> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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