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

public class Ficha14MenorDAO {
    
    public static void create(Ficha14Menor ficha){
        
        String sql = "INSERT INTO ficha14_participacao_menor (valor_participacao, rendimento_distribuido, data_criacao, trimestre, id_moeda, id_pais, chave, id_status) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection connection = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getValorParticipacao());
            pst.setDouble(2, ficha.getRendimentoDistribuido());
            pst.setDate(3, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(4, DataUtils.validaTrimestre());
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
    public static void update(Ficha14Menor ficha) {

        String sql = "UPDATE ficha14_participacao_menor SET valor_participacao = ?, rendimento_distribuido = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getValorParticipacao());
            pst.setDouble(2, ficha.getRendimentoDistribuido());
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
        String sql = "DELETE FROM ficha14_participacao_menor WHERE id = ?";
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
    
    public static List<Ficha14Menor> getAllFichas() {
        String query = "SELECT * FROM ficha14_participacao_menor";
        List<Ficha14Menor> listaFichas = new ArrayList<Ficha14Menor>();
        Connection connection = null;
        Ficha14Menor ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        EmpresaController empresaController = new EmpresaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha14Menor();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setValorParticipacao(rs.getDouble("valor_participacao"));
                ficha.setRendimentoDistribuido(rs.getDouble("rendimento_distribuido"));
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
    public static List<Ficha14Menor> getAllFichasByTrimestreAno(int trimestre, int ano) {
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
        String query = "SELECT * FROM ficha14_participacao_menor WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha14Menor> listaFichas = new ArrayList<Ficha14Menor>();
        Connection connection = null;
        Ficha14Menor ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        EmpresaController empresaController = new EmpresaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha14Menor();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setValorParticipacao(rs.getDouble("valor_participacao"));
                ficha.setRendimentoDistribuido(rs.getDouble("rendimento_distribuido"));
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
    public static Optional<Ficha14Menor> getFichaById(int id) {
        String query = "SELECT * FROM ficha14_participacao_menor WHERE id = " + id;
        Connection connection = null;
        Ficha14Menor ficha = null;
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
                ficha = new Ficha14Menor();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setValorParticipacao(rs.getDouble("valor_participacao"));
                ficha.setRendimentoDistribuido(rs.getDouble("rendimento_distribuido"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha14_participacao_menor SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
