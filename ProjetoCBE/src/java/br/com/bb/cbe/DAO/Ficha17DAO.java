package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha17;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.conexao.Conexao;
import br.com.bb.cbe.controllers.EmpresaController;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.StatusController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha17DAO {

    public static void create(Ficha17 ficha) {

        String sql = "INSERT INTO ficha17 (prazo_divida, valor_mercado, juros_recebidos, data_criacao, trimestre, id_moeda, chave, id_empresa, id_status) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getPrazoDivida());
            pst.setDouble(2, ficha.getValorMercado());
            pst.setDouble(3, ficha.getJurosRecebidos());
            pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(5, DataUtils.validaTrimestre());
            pst.setInt(6, ficha.getMoeda().getId());
            pst.setString(7, ficha.getFuncionario().getChave());
            pst.setInt(8, ficha.getEmpresa().getId());
            pst.setInt(9, ficha.getStatus().getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Ficha17 ficha) {

        String sql = "UPDATE ficha17 SET prazo_divida = ?, valor_mercado = ?, juros_recebidos = ?, id_moeda = ?, id_empresa = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getPrazoDivida());
            pst.setDouble(2, ficha.getValorMercado());
            pst.setDouble(3, ficha.getJurosRecebidos());
            pst.setInt(4, ficha.getMoeda().getId());
            pst.setInt(5, ficha.getEmpresa().getId());
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
        String sql = "DELETE FROM ficha17 WHERE id = ?";
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

    public static List<Ficha17> getAllFichas() {
        String query = "SELECT * FROM ficha17";
        List<Ficha17> listaFichas = new ArrayList<Ficha17>();
        Connection connection = null;
        Ficha17 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        EmpresaController empresaController = new EmpresaController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha17();
                ficha.setId(rs.getInt("id"));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
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
    
    // MANTER O SWITCH ORIGINAL AQUI (LÓGICA DO BANCO)
    public static List<Ficha17> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha17 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha17> listaFichas = new ArrayList<Ficha17>();
        Connection connection = null;
        Ficha17 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        EmpresaController empresaController = new EmpresaController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha17();
                ficha.setId(rs.getInt("id"));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
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

    public static Optional<Ficha17> getFichaById(int id) {
        String query = "SELECT * FROM ficha17 WHERE id = " + id;
        Connection connection = null;
        Ficha17 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        EmpresaController empresaController = new EmpresaController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha17();
                ficha.setId(rs.getInt("id"));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha17 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha17 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha17 ORDER BY trimestre ASC";
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
    public static List<Ficha17> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha17 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha17> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha17 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        EmpresaController empresaController = new EmpresaController();
        StatusController statusController = new StatusController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha17();
                ficha.setId(rs.getInt("id"));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
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
    public static List<Ficha17> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT * FROM ficha17 WHERE trimestre = " + trimestre;
        List<Ficha17> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha17 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        EmpresaController empresaController = new EmpresaController();
        StatusController statusController = new StatusController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha17();
                ficha.setId(rs.getInt("id"));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
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