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

public class Ficha14ControleDAO {

    public static void create(Ficha14Controle ficha) {

        String sql = "INSERT INTO ficha14_controle_empresa (nome, porcento_capital_social, patrimonio_liquido, valor_mercado, atividade_economica, final_cadeia_controle, data_criacao, trimestre, id_moeda, id_pais, id_ficha14, chave) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection connection = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getNome());
            pst.setDouble(2, ficha.getPorcentoCapitalSocial());
            pst.setDouble(3, ficha.getPatrimonioLiquido());
            pst.setDouble(4, ficha.getValorMercado());
            pst.setString(5, ficha.getAtividadeEcn());
            pst.setBoolean(6, ficha.isFinalCadeia());
            pst.setDate(7, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(8, DataUtils.validaTrimestre());
            pst.setInt(9, ficha.getMoeda().getId());
            pst.setInt(10, ficha.getPais().getId());
            pst.setInt(11, ficha.getFicha14Controladora().getId());
            pst.setString(12, ficha.getFuncionario().getChave());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Ficha14Controle ficha) {

        String sql = "UPDATE ficha14_controle_empresa SET nome = ?, porcento_capital_social = ?, patrimonio_liquido = ?, valor_mercado = ?, atividade_economica = ?, final_cadeia_controle = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getNome());
            pst.setDouble(2, ficha.getPorcentoCapitalSocial());
            pst.setDouble(3, ficha.getPatrimonioLiquido());
            pst.setDouble(4, ficha.getValorMercado());
            pst.setString(5, ficha.getAtividadeEcn());
            pst.setBoolean(6, ficha.isFinalCadeia());
            pst.setInt(7, ficha.getMoeda().getId());
            pst.setInt(8, ficha.getPais().getId());
            pst.setString(9, ficha.getFuncionario().getChave());
            pst.setDate(10, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(11, ficha.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
    
    public static void delete(int id) {
        String sql = "DELETE FROM ficha14_controle_empresa WHERE id = ?";
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
    
    public static List<Ficha14Controle> getAllFichas() {
        String query = "SELECT * FROM ficha14_controle_empresa";
        List<Ficha14Controle> listaFichas = new ArrayList<Ficha14Controle>();
        Connection connection = null;
        Ficha14Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha14MaiorController ficha14MaiorController = new Ficha14MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha14Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atividade_economica"));
                ficha.setPorcentoCapitalSocial(rs.getDouble("porcento_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha14Controladora(ficha14MaiorController.getFichaById(rs.getInt("id_ficha14")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }
    
    public static List<Ficha14Controle> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1:
                trimestre = 2;
                break;
            case 2:
                trimestre = 3;
                break;
            case 3:
                trimestre = 4;
                break;
            case 4:
                trimestre = 1;
                ano = ano + 1;
                break;
            default:
                break;
        }
        String query = "SELECT * FROM ficha14_controle_empresa WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha14Controle> listaFichas = new ArrayList<Ficha14Controle>();
        Connection connection = null;
        Ficha14Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha14MaiorController ficha14MaiorController = new Ficha14MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha14Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atividade_economica"));
                ficha.setPorcentoCapitalSocial(rs.getDouble("porcento_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha14Controladora(ficha14MaiorController.getFichaById(rs.getInt("id_ficha14")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }
    
    public static Optional<Ficha14Controle> getFichaById(int id) {
        String query = "SELECT * FROM ficha14_controle_empresa WHERE id = " + id;
        Connection connection = null;
        Ficha14Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha14MaiorController ficha14MaiorController = new Ficha14MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha14Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atividade_economica"));
                ficha.setPorcentoCapitalSocial(rs.getDouble("porcento_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha14Controladora(ficha14MaiorController.getFichaById(rs.getInt("id_ficha14")));
                return Optional.of(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return Optional.empty();
    }

    public static List<Ficha14Controle> getAllFichasByControladoraId(int idControladora) {
        String query = "SELECT * FROM ficha14_controle_empresa WHERE id_ficha14 = " + idControladora;
        List<Ficha14Controle> listaFichas = new ArrayList<Ficha14Controle>();
        Connection connection = null;
        Ficha14Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha14MaiorController ficha14MaiorController = new Ficha14MaiorController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha14Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atividade_economica"));
                ficha.setPorcentoCapitalSocial(rs.getDouble("porcento_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha14Controladora(ficha14MaiorController.getFichaById(rs.getInt("id_ficha14")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    public static void deleteAllEmpresasByControladoraId(int idControladora) {
        String sql = "DELETE FROM ficha14_controle_empresa WHERE id_ficha14 = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idControladora);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
    
    public static boolean existeEmpresa(int id) {
        String query = "SELECT * FROM ficha14_controle_empresa WHERE id_ficha14 = " + id;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, null);
        }
        return true;
    }
}
