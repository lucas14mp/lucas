package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.*;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.controllers.*;
import br.com.bb.cbe.DAO.PtaxDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha11MenorDAO {

    public static void create(Ficha11Menor ficha) {

        String sql = "INSERT INTO ficha11_participacao_menor(metodo_valoracao, valor_participacao, lucro_distribuido, data_criacao, trimestre, id_moeda, id_pais, chave, id_status) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getMetodoValoracao());
            pst.setDouble(2, ficha.getValorParticipacao());
            pst.setDouble(3, ficha.getLucroDistribuido());
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

    public static void createBatch(List<Ficha11Menor> fichas) {
        System.out.println("ENTROU NO CREATE");
        String sql = "INSERT INTO ficha11_participacao_menor (metodo_valoracao, valor_participacao, lucro_distribuido, data_criacao, trimestre, id_moeda, id_pais, chave, id_status) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ficha11Menor ficha : fichas) {
                pst.setString(1, ficha.getMetodoValoracao());
                pst.setDouble(2, ficha.getValorParticipacao());
                pst.setDouble(3, ficha.getLucroDistribuido());
                pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(5, DataUtils.validaTrimestre());
                pst.setInt(6, ficha.getMoeda().getId());
                pst.setInt(7, ficha.getPais().getId());
                pst.setString(8, ficha.getFuncionario().getChave());
                pst.setInt(9, ficha.getStatus().getId());
                pst.addBatch();
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("CRIOU MENOR" );
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
    
    public static void update(Ficha11Menor ficha) {

        String sql = "UPDATE ficha11_participacao_menor SET metodo_valoracao = ?, valor_participacao = ?, lucro_distribuido = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getMetodoValoracao());
            pst.setDouble(2, ficha.getValorParticipacao());
            pst.setDouble(3, ficha.getLucroDistribuido());
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
        String sql = "DELETE FROM ficha11_participacao_menor WHERE id = ?";
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

    public static List<Ficha11Menor> getAllFichas() {
        String query = "SELECT * FROM ficha11_participacao_menor";
        List<Ficha11Menor> listaFichas = new ArrayList<Ficha11Menor>();
        Connection connection = null;
        Ficha11Menor ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Menor();
                ficha.setId(rs.getInt("id"));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorParticipacao(rs.getDouble("valor_participacao"));
                ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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
    
    public static List<Ficha11Menor> getAllFichasByTrimestreAno(int trimestre, int ano) {
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
        String query = "SELECT * FROM ficha11_participacao_menor WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha11Menor> listaFichas = new ArrayList<Ficha11Menor>();
        Connection connection = null;
        Ficha11Menor ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Menor();
                ficha.setId(rs.getInt("id"));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorParticipacao(rs.getDouble("valor_participacao"));
                ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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

    public static Optional<Ficha11Menor> getFichaById(int id) {
        String query = "SELECT * FROM ficha11_participacao_menor WHERE id = " + id;
        Connection connection = null;
        Ficha11Menor ficha = null;
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
                ficha = new Ficha11Menor();
                ficha.setId(rs.getInt("id"));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorParticipacao(rs.getDouble("valor_participacao"));
                ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha11_participacao_menor SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
    
    public static boolean paisExiste(int id){
        String query = "SELECT COUNT(*) FROM ficha11_participacao_menor WHERE id_pais = ?";
        Connection connection = null;
        Ficha11Menor ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
                connection = Conexao.conectar();
                pst = connection.prepareStatement(query);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("O NÚMERO DE PAISES COM ID : " + id + " é: " + count);
                    return count > 0; //Retorna true se encontrar alguma ficha registrada com o id da empresa passado 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Conexao.fecharConexao(connection, pst, rs);
            }
        return false; // Retorna false se não encontrar nada
    }
    
    public static double getSomaPorDependencia(int trimestre, int ano, int idDependencia) {
        double totalBrl = 0.0;
        
        // SQL Busca valor original e a moeda
        String sql = "SELECT f.valor_participacao, f.id_moeda " + 
                     "FROM ficha11_participacao_menor f " + 
                     "INNER JOIN funcionario func ON f.chave = func.chave " + 
                     "WHERE f.trimestre = ? AND YEAR(f.data_criacao) = ? AND func.id_dependencia = ?";

        System.out.println(">>> [DEBUG F11.1] Iniciando (Retornando em REAIS) para Dep: " + idDependencia);

        try (Connection con = Conexao.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, trimestre);
            pst.setInt(2, ano);
            pst.setInt(3, idDependencia);

            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                double valorOriginal = rs.getDouble("valor_participacao");
                int idMoeda = rs.getInt("id_moeda");
                
                // Converte da moeda original para REAIS (BRL)
                double taxaMoedaParaBrl = PtaxDAO.getTaxaCompra(idMoeda, trimestre, ano);
                double valorEmReais = valorOriginal * taxaMoedaParaBrl;
                
                totalBrl += valorEmReais;
                
                System.out.println(String.format(">>> [ITEM] Moeda %d: %.2f * %.4f = R$ %.2f", 
                        idMoeda, valorOriginal, taxaMoedaParaBrl, valorEmReais));
            }
            
            // --- CORREÇÃO: RETORNAMOS O TOTAL EM BRL DIRETAMENTE ---
            // Não dividimos mais pelo Dólar, pois a tela de conciliação é em Reais.
            
            System.out.println(">>> [TOTAL] Final em Reais: R$ " + totalBrl);
            return totalBrl;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
