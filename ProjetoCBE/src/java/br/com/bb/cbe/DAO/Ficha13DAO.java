package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha13;
import br.com.bb.cbe.Utils.DataUtils;
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

public class Ficha13DAO {

    public static void create(Ficha13 ficha) {

        String sql = "INSERT INTO ficha13 (prazo_emprestimo, saldo_database, juros_periodo_base, data_criacao, trimestre, id_moeda, id_pais, chave, id_status) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection connection = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getPrazoEmprestimo());
            pst.setDouble(2, ficha.getSaldoDatabase());
            pst.setDouble(3, ficha.getJurosPeriodoBase());
            pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(5, DataUtils.validaTrimestre());
            pst.setInt(6, ficha.getMoeda().getId());
            pst.setInt(7, ficha.getPais().getId());
            pst.setString(8, ficha.getFuncionario().getChave());
            pst.setInt(9, ficha.getStatus().getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Ficha13 ficha) {

        String sql = "UPDATE ficha13 SET prazo_emprestimo = ?, saldo_database = ?, juros_periodo_base = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getPrazoEmprestimo());
            pst.setDouble(2, ficha.getSaldoDatabase());
            pst.setDouble(3, ficha.getJurosPeriodoBase());
            pst.setInt(4, ficha.getMoeda().getId());
            pst.setInt(5, ficha.getPais().getId());
            pst.setString(6, ficha.getFuncionario().getChave());
            pst.setDate(7, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(8, ficha.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM ficha13 WHERE id = ?";
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

    public static List<Ficha13> getAllFichas() {
        String query = "SELECT * FROM ficha13";
        List<Ficha13> listaFichas = new ArrayList<Ficha13>();
        Connection connection = null;
        Ficha13 ficha = null;
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
                ficha = new Ficha13();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoEmprestimo(rs.getString("prazo_emprestimo"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setJurosPeriodoBase(rs.getDouble("juros_periodo_base"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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
    
    // MÉTODO ORIGINAL COM SWITCH (MANTIDO)
    public static List<Ficha13> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha13 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha13> listaFichas = new ArrayList<Ficha13>();
        Connection connection = null;
        Ficha13 ficha = null;
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
                ficha = new Ficha13();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoEmprestimo(rs.getString("prazo_emprestimo"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setJurosPeriodoBase(rs.getDouble("juros_periodo_base"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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

    public static Optional<Ficha13> getFichaById(int id) {
        String query = "SELECT * FROM ficha13 WHERE id = " + id;
        Connection connection = null;
        Ficha13 ficha = null;
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
            if (rs.next()) {
                ficha = new Ficha13();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoEmprestimo(rs.getString("prazo_emprestimo"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setJurosPeriodoBase(rs.getDouble("juros_periodo_base"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                return Optional.of(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return Optional.empty();
    }

    public static void validarFormularios(List<String> idsValidados, String chave) {
        if (idsValidados.isEmpty()) {
            return;
        }
        StringBuilder sql = new StringBuilder("UPDATE ficha13 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha13 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha13 ORDER BY trimestre ASC";
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
    public static List<Ficha13> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha13 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha13> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha13 ficha = null;
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
                ficha = new Ficha13();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoEmprestimo(rs.getString("prazo_emprestimo"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setJurosPeriodoBase(rs.getDouble("juros_periodo_base"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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
    public static List<Ficha13> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT * FROM ficha13 WHERE trimestre = " + trimestre;
        List<Ficha13> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha13 ficha = null;
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
                ficha = new Ficha13();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoEmprestimo(rs.getString("prazo_emprestimo"));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setJurosPeriodoBase(rs.getDouble("juros_periodo_base"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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