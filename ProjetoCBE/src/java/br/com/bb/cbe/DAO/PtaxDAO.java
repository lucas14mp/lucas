/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Bean.Ptax;
import br.com.bb.cbe.Utils.NumeroUtils;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 *
 * @author T1092407
 */
public class PtaxDAO {

    Conexao conexao = new Conexao();

    public static void createBatchTaxa(List<Ptax> taxas) {

        String sql = "INSERT INTO ptax (compra, venda, id_moeda, data_criacao, trimestre) VALUES (?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ptax taxa : taxas) {
                pst.setDouble(1, taxa.getCompra());
                pst.setDouble(2, taxa.getVenda());
                pst.setInt(3, taxa.getMoeda().getId());
                pst.setDate(4, new java.sql.Date(taxa.getData_criacao().getTime()));
                pst.setInt(5, taxa.getTrimestre());

                pst.addBatch();
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("CRIOU TAXA");
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

    public static void updateBatchPtax(List<Ptax> taxas) {
        System.out.println("ENTROU NO UPDATE TAXA");
        String sql = "UPDATE ptax SET compra = ?, venda = ?, data_criacao = ? WHERE id_moeda = ? AND trimestre = ? AND data_criacao = ?";
        String queryAno = "SELECT data_criacao FROM ptax WHERE id_moeda = ? AND trimestre = ?";

        Connection connection = null;
        PreparedStatement pst = null;
        PreparedStatement pstAno = null;
        ResultSet rsAno = null;

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ptax taxa : taxas) {
                System.out.println("FOR DAS TAXAS");
                List<String> datas = new ArrayList<>();
                Date dataFinal = null;

                SimpleDateFormat anoFormat = new SimpleDateFormat("yyyy");
                int anoTaxa = Integer.parseInt(anoFormat.format(taxa.getData_criacao()));

                System.out.println("TRIMESTRE: " + taxa.getTrimestre());

                pstAno = connection.prepareStatement(queryAno);
                pstAno.setInt(1, taxa.getMoeda().getId());
                /////LEMBRA DE TROCAR PARA TAXA.GETTRIMESTRE()
            pstAno.setInt(2, 1);
                rsAno = pstAno.executeQuery();

                while (rsAno.next()) {
                    String dataCriacao = rsAno.getString("data_criacao");
                    datas.add(dataCriacao);
                }

                for (String data : datas) {
                    System.out.println("FOR DAS DATAS");
                    int anoData = Integer.parseInt(data.split("-")[0]);
                    if (anoData == anoTaxa) {
                        try {
                            dataFinal = formato.parse(data);
                            System.out.println("Data convertida: " + data);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            continue; // Pula para a próxima data se houver erro
                        }

                        System.out.println("ATUALIZANDO A TAXA " + taxa.getMoeda().getNome() + " DO ANO " + anoTaxa + " E DO TRIMESTRE " + taxa.getTrimestre());

                        pst.setDouble(1, taxa.getCompra());
                        pst.setDouble(2, taxa.getVenda());
                        pst.setDate(3, new java.sql.Date(taxa.getData_criacao().getTime()));
                        pst.setInt(4, taxa.getMoeda().getId());
                        /////LEMBRA DE TROCAR PARA TAXA.GETTRIMESTRE()
                    pst.setInt(5, 1);
                        pst.setDate(6, new java.sql.Date(dataFinal.getTime()));
                        pst.addBatch();
                    }
                }

                // Fecha recursos usados na consulta de datas
                if (rsAno != null) {
                    rsAno.close();
                }
                if (pstAno != null) {
                    pstAno.close();
                }
            }

            if (pst != null) {
                pst.executeBatch(); // Executa todas as atualizações em lote
                connection.commit(); // Confirma a transação
                System.out.println("ATUALIZOU TAXA");
            }

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

    public static Ptax getPtaxById(int idMoeda) {
        Ptax ptax = new Ptax();
        MoedaController moeda;
        moeda = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id_ptax, valor, id_moeda FROM ptax WHERE id_moeda = " + idMoeda;

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            ptax.setIdPtax(rs.getInt("id"));
            ptax.setCompra(rs.getDouble("valor"));
            ptax.setMoeda(moeda.getMoedaById(rs.getInt("id_moeda")));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return ptax;
    }

    public static boolean taxaExiste(int id) {
        String query = "SELECT * FROM ptax";
        //    String queryAno = "SELECT data_criacao FROM ficha11_participacao_maior WHERE id_empresa = ?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        List<String> taxas = new ArrayList<>();
        try {
            //          PEGANDO OS ANOS DAS EMPRESAS COM O ID PASSADO  
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                String moeda = rs.getString("id_moeda");
                taxas.add(moeda);
            }
            for (String taxa : taxas) {
                int valor = NumeroUtils.stringParaInt(taxa);
                System.out.println("TESTE TAXA DA FICHA: " + id);
                System.out.println("TESTE TAXA DA BASE:" + taxa);
                if (id == valor) {
                    System.out.println("MOEDA JÁ ESTÁ REGISTRADA NA BASE");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return false; // Retorna false se não encontrar nada
    }

    public static List<Ptax> getAllTaxas() {
        List<Ptax> taxas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT * FROM ptax";
        MoedaController moedactr = new MoedaController();

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
//                System.out.println("ENTROU ALLTAXAS");
                Moeda moeda = new Moeda();
                Ptax taxa = new Ptax();
                taxa.setIdPtax(rs.getInt("id_ptax"));
                int id = rs.getInt("id_moeda");
                moeda = moedactr.getMoedaById(id);
                taxa.setMoeda(moeda);
                taxa.setCompra(rs.getDouble("compra"));
                taxa.setVenda(rs.getDouble("venda"));
                taxa.setData_criacao(rs.getDate("data_criacao"));
                taxa.setTrimestre(rs.getInt("trimestre"));
                taxas.add(taxa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return taxas;
    }

    public static void inserirHistoricoPtax(java.sql.Date data, int idMoeda, double compra, double venda, int trimestre) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexao.conectar();

            // 1. Remove duplicidade do dia
            String sqlDel = "DELETE FROM ptax WHERE data_criacao = ? AND id_moeda = ?";
            stmt = conn.prepareStatement(sqlDel);
            stmt.setDate(1, data);
            stmt.setInt(2, idMoeda);
            stmt.executeUpdate();
            stmt.close();

            // 2. Insere novo
            String sqlIns = "INSERT INTO ptax (data_criacao, id_moeda, compra, venda, trimestre) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlIns);
            stmt.setDate(1, data);
            stmt.setInt(2, idMoeda);
            stmt.setDouble(3, compra);
            stmt.setDouble(4, venda);
            stmt.setInt(5, trimestre);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fechar conexao
        }
    }

    public static List<Integer> getAnosDisponiveis() {
        List<Integer> anos = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ptax ORDER BY ano DESC";

        try (Connection conn = Conexao.conectar(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                anos.add(rs.getInt("ano"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anos;
    }

// Busca as taxas filtradas por Ano e Trimestre
    public static List<Ptax> getTaxasPorPeriodo(int ano, int trimestre) {
        List<Ptax> taxas = new ArrayList<>();
        String sql = "SELECT * FROM ptax WHERE YEAR(data_criacao) = ? AND trimestre = ?";
        MoedaController moedactr = new MoedaController();

        try (Connection conn = Conexao.conectar(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, ano);
            pst.setInt(2, trimestre);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Ptax taxa = new Ptax();
                    taxa.setIdPtax(rs.getInt("id_ptax"));
                    // Otimização: Se possível, faça JOIN no SQL para não chamar o banco N vezes aqui
                    taxa.setMoeda(moedactr.getMoedaById(rs.getInt("id_moeda")));
                    taxa.setCompra(rs.getDouble("compra"));
                    taxa.setVenda(rs.getDouble("venda"));
                    taxa.setData_criacao(rs.getDate("data_criacao"));
                    taxa.setTrimestre(rs.getInt("trimestre"));
                    taxas.add(taxa);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taxas;
    }
    
    // Adicione este método ao final de PtaxDAO.java (dentro da classe)

    public static double getTaxaCompra(int idMoeda, int trimestre, int ano) {
        // Se for Real (ID 16), a taxa é sempre 1
        if (idMoeda == 16) { 
            return 1.0; 
        }

        double taxa = 0.0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        // Busca a taxa de COMPRA para a moeda no trimestre/ano de referência
        String sql = "SELECT compra FROM ptax WHERE id_moeda = ? AND trimestre = ? AND YEAR(data_criacao) = ?";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idMoeda);
            pst.setInt(2, trimestre);
            pst.setInt(3, ano);
            
            rs = pst.executeQuery();
            if (rs.next()) {
                taxa = rs.getDouble("compra");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        
        // Se não achar taxa (0.0), retorna 1.0 para não zerar o valor, 
        // mas idealmente deveria ter taxa cadastrada.
        return (taxa > 0) ? taxa : 1.0; 
    }

}