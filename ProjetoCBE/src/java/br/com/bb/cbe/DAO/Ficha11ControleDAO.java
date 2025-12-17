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

public class Ficha11ControleDAO {

    public static void create(Ficha11Controle ficha) {

        String sql = "INSERT INTO ficha11_controle_empresa(nome, atvdd_economica, participacao_capital_social, patrimonio_liquido, valor_mercado, final_cadeia_controle, data_criacao, trimestre, id_moeda, id_pais, id_ficha11, chave) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getNome());
            pst.setString(2, ficha.getAtividadeEcn());
            pst.setDouble(3, ficha.getParticipacaoCapital());
            pst.setDouble(4, ficha.getPatrimonioLiquido());
            pst.setDouble(5, ficha.getValorMercado());
            pst.setBoolean(6, ficha.isFinalCadeia());
            pst.setDate(7, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(8, DataUtils.validaTrimestre());
            pst.setInt(9, ficha.getMoeda().getId());
            pst.setInt(10, ficha.getPais().getId());
            pst.setInt(11, ficha.getFicha11Controladora().getId());
            pst.setString(12, ficha.getFuncionario().getChave());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }
    
    public static void createBatch(List<Ficha11Controle> fichas) {
        System.out.println("ENTROU NO CREATE CONTROLE");
        String sql = "INSERT INTO ficha11_controle_empresa (nome, atvdd_economica, participacao_capital_social, patrimonio_liquido, valor_mercado, final_cadeia_controle, data_criacao, trimestre, id_moeda, id_pais, chave, id_ficha11) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ficha11Controle ficha : fichas) {
                pst.setString(1, ficha.getNome());
                pst.setString(2, ficha.getAtividadeEcn());
                pst.setDouble(3, ficha.getParticipacaoCapital());
                pst.setDouble(4, ficha.getPatrimonioLiquido());
                pst.setDouble(5, ficha.getValorMercado());     
                pst.setBoolean(6, ficha.isFinalCadeia()); 
                pst.setDate(7, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(8, DataUtils.validaTrimestre());
                pst.setInt(9, ficha.getMoeda().getId());
                pst.setInt(10, ficha.getPais().getId());
                pst.setString(11, ficha.getFuncionario().getChave());
                pst.setInt(12, ficha.getFicha11Controladora().getId());
                pst.addBatch();
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("CRIOU CONTROLE" );
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Reverte a transação em caso de erro
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
    
    public static void update(Ficha11Controle ficha) {

        String sql = "UPDATE ficha11_controle_empresa SET nome = ?, atvdd_economica = ?, participacao_capital_social = ?, patrimonio_liquido = ?, valor_mercado = ?, final_cadeia_controle = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getNome());
            pst.setString(2, ficha.getAtividadeEcn());
            pst.setDouble(3, ficha.getParticipacaoCapital());
            pst.setDouble(4, ficha.getPatrimonioLiquido());
            pst.setDouble(5, ficha.getValorMercado());
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
        String sql = "DELETE FROM ficha11_controle_empresa WHERE id = ?";
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

    public static List<Ficha11Controle> getAllFichas() {
        String query = "SELECT ficha11_controle_empresa.*, pais.* FROM ficha11_controle_empresa LEFT JOIN pais ON ficha11_controle_empresa.id_pais = pais.id_pais WHERE ficha11_controle_empresa.id IS NOT NULL ORDER BY pais.nome";
        List<Ficha11Controle> listaFichas = new ArrayList<Ficha11Controle>();
        Connection connection = null;
        Ficha11Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha11MaiorController ficha11MaiorController = new Ficha11MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atvdd_economica"));
                ficha.setParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha11Controladora(ficha11MaiorController.getFichaById(rs.getInt("id_ficha11")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    public static List<Ficha11Controle> getAllFichasByTrimestreAno(int trimestre, int ano) {
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
        String query = "SELECT ficha11_controle_empresa.*, pais.* FROM ficha11_controle_empresa "
                + "LEFT JOIN pais ON ficha11_controle_empresa.id_pais = pais.id_pais "
                + "WHERE ficha11_controle_empresa.id IS NOT NULL "
                + "AND trimestre = '" + trimestre + "' "
                + "AND YEAR(data_criacao) = '" + ano + "' "
                + "ORDER BY pais.nome";
        List<Ficha11Controle> listaFichas = new ArrayList<Ficha11Controle>();
        Connection connection = null;
        Ficha11Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha11MaiorController ficha11MaiorController = new Ficha11MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atvdd_economica"));
                ficha.setParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha11Controladora(ficha11MaiorController.getFichaById(rs.getInt("id_ficha11")));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    public static Optional<Ficha11Controle> getFichaById(int id) {
        String query = "SELECT * FROM ficha11_controle_empresa WHERE id = " + id;
        Connection connection = null;
        Ficha11Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha11MaiorController ficha11MaiorController = new Ficha11MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha11Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atvdd_economica"));
                ficha.setParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha11Controladora(ficha11MaiorController.getFichaById(rs.getInt("id_ficha11")));
                return Optional.of(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return Optional.empty();
    }

    public static List<Ficha11Controle> getAllFichasByControladoraId(int idControladora) {
        String query = "SELECT * FROM ficha11_controle_empresa WHERE id_ficha11 = " + idControladora;
        List<Ficha11Controle> listaFichas = new ArrayList<Ficha11Controle>();
        Connection connection = null;
        Ficha11Controle ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        Ficha11MaiorController ficha11MaiorController = new Ficha11MaiorController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Controle();
                ficha.setId(rs.getInt("id"));
                ficha.setNome(rs.getString("nome"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setAtividadeEcn(rs.getString("atvdd_economica"));
                ficha.setParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setFinalCadeia(rs.getBoolean("final_cadeia_controle"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setFicha11Controladora(ficha11MaiorController.getFichaById(rs.getInt("id_ficha11")));
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
        String sql = "DELETE FROM ficha11_controle_empresa WHERE id_ficha11 = ?";
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
        String query = "SELECT * FROM ficha11_controle_empresa WHERE id_ficha11 = " + id;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, null);
        }
        return true;
    }
    
    public static boolean nomeExiste(String nome){
    String query = "SELECT COUNT(*) FROM ficha11_controle_empresa WHERE nome = ?";
    Connection connection = null;
    Ficha11Menor ficha = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("O NÚMERO DE EMPRESAS COM O NOME : " + nome + " é: " + count);
                return count > 0; //Retorna true se encontrar alguma ficha registrada com o id da empresa passado 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
    return false; // Retorna false se não encontrar nada
    }
}
