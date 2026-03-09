package br.com.bb.cbe.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.Bean.Ficha01;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.PaisController;
import br.com.bb.cbe.controllers.StatusController;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.bb.cbe.conexao.Conexao;

public class Ficha01DAO {

    public static void create(Ficha01 ficha) {
        String sql = "INSERT INTO ficha01(id_moeda, id_pais, valor_database, dividendos, data_criacao, trimestre, chave, id_status, justificativa_gestor) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, ficha.getMoeda().getId());
            pst.setInt(2, ficha.getPais().getId());
            pst.setDouble(3, ficha.getValorDatabase());
            pst.setDouble(4, ficha.getDividendos());
            pst.setDate(5, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(6, ficha.getTrimestre());
            pst.setString(7, ficha.getFuncionario().getChave());
            pst.setInt(8, ficha.getStatus().getId());
            pst.setString(9, ficha.getJustificativaGestor());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void update(Ficha01 ficha) {
        String sql = "UPDATE ficha01 SET id_moeda = ?, id_pais = ?, valor_database = ?, dividendos = ?, chave = ?, data_criacao = ?, justificativa_gestor = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, ficha.getMoeda().getId());
            pst.setInt(2, ficha.getPais().getId());
            pst.setDouble(3, ficha.getValorDatabase());
            pst.setDouble(4, ficha.getDividendos());
            pst.setString(5, ficha.getFuncionario().getChave());
            pst.setDate(6, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(7, ficha.getId());
            pst.setString(8, ficha.getJustificativaGestor());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM ficha01 WHERE id = ?";
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

    public static List<Ficha01> getAllFichas() {
        String query = "SELECT * FROM ficha01";
        List<Ficha01> listaFichas = new ArrayList<Ficha01>();
        Connection connection = null;
        Ficha01 ficha = null;
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
                ficha = new Ficha01();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDividendos(rs.getDouble("dividendos"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }
    
    // MÉTODO ORIGINAL DE FILTRO COMPOSTO (NÃO MEXER NA LÓGICA DO SWITCH)
    public static List<Ficha01> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha01 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha01> listaFichas = new ArrayList<Ficha01>();
        Connection connection = null;
        Ficha01 ficha = null;
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
                ficha = new Ficha01();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDividendos(rs.getDouble("dividendos"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    };

    public static Optional<Ficha01> getFichaById(int id) {
        String query = "SELECT * FROM ficha01 WHERE id = " + id;
        Connection connection = null;
        Ficha01 ficha = null;
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
                ficha = new Ficha01();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDividendos(rs.getDouble("dividendos"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha01 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha01 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha01 ORDER BY trimestre ASC";
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

    // Busca apenas por Ano (Não precisa de Switch)
    public static List<Ficha01> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha01 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha01> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha01 ficha = null;
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
                ficha = new Ficha01();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDividendos(rs.getDouble("dividendos"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }

    // Busca apenas por Trimestre (COM O SWITCH PARA MANTER COMPATIBILIDADE COM O BANCO)
    public static List<Ficha01> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break; // Ano não importa aqui
            default: break;
        }

        String query = "SELECT * FROM ficha01 WHERE trimestre = " + trimestre;
        List<Ficha01> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha01 ficha = null;
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
                ficha = new Ficha01();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDividendos(rs.getDouble("dividendos"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaFichas;
    }
    
// Em Ficha01DAO.java

public static boolean verificarNecessidadeJustificativa(double valorInformadoOriginal, int trimestreFicha, int anoFicha) {
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean precisaJustificar = false;

    // 1. Lógica do Período: Ficha do Tri 4 (Vigente) -> Compara com Tri 3 (Referência)
    int triReferencia = trimestreFicha;
    int anoReferencia = anoFicha;

//    if (triReferencia == 0) {
//        triReferencia = 4;
//        anoReferencia = anoFicha - 1;
//    }

    System.out.println("===== VALIDACAO FICHA 01 =====");
    System.out.println("Ficha Atual: " + trimestreFicha + "/" + anoFicha);
    System.out.println("Busca no Banco (Referencia): Tri " + triReferencia + "/" + anoReferencia);

    try {
        connection = Conexao.conectar();

        // 2. Query Corrigida: Procura ficha = '1' em vez de '01'
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(COALESCE(r.CONSOLIDADO, 0)) as total_consolidado ");
        sql.append("FROM consolidado c ");
        sql.append("LEFT JOIN planilha4010 r ");
        sql.append("  ON r.CD_CT_PLN = c.cosif ");
        sql.append("  AND r.CD_IOR = c.CD_IOR ");
        sql.append("  AND r.CD_RBC = c.CD_RBC ");
        // Filtra pelo trimestre interpretando a data do banco
        sql.append("  AND QUARTER(r.DT_EVD) = ? ");
        sql.append("  AND YEAR(r.DT_EVD) = ? ");
        sql.append("WHERE c.ficha = '1' "); // <--- CORREÇÃO AQUI (Era '01')

        pst = connection.prepareStatement(sql.toString());
        pst.setInt(1, triReferencia);
        pst.setInt(2, anoReferencia);
        
        rs = pst.executeQuery();

        double valorPlanilhaBrl = 0.0;
        if (rs.next()) {
            valorPlanilhaBrl = rs.getDouble("total_consolidado");
        }

        System.out.println("Valor Informado: " + valorInformadoOriginal);
        System.out.println("Valor Banco (Tri " + triReferencia + "): " + valorPlanilhaBrl);

        // 3. Validação
        if (valorPlanilhaBrl != 0) {
            double diferenca = Math.abs(valorInformadoOriginal - valorPlanilhaBrl);
            double percentual = (diferenca / valorPlanilhaBrl) * 100;
            
            System.out.println("Diferenca Calculada: " + String.format("%.2f", percentual) + "%");

            if (percentual > 0.5) {
                precisaJustificar = true;
                System.out.println(">> STATUS: Diferença > 0.5%. Exige Justificativa.");
            } else {
                System.out.println(">> STATUS: OK (Dentro da margem).");
            }
        } else {
            // Se o banco retornou 0
            if (valorInformadoOriginal != 0) {
                precisaJustificar = true;
                System.out.println(">> ALERTA: Banco zerado para o periodo. Exige Justificativa.");
                
                // Diagnóstico rápido se não achar dados
                verificarDadosDisponiveis(connection);
                } else {
                    System.out.println(">> STATUS: OK (Ambos zerados).");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }

        return precisaJustificar;
    }

// Método auxiliar apenas para mostrar no log quais datas existem (ajuda muito a debuggar)
    private static void verificarDadosDisponiveis(Connection conn) {
        try {
            String sqlCheck = "SELECT r.DT_EVD FROM planilha4010 r "
                    + "JOIN consolidado c ON r.CD_CT_PLN = c.cosif "
                    + "WHERE c.ficha = '1' LIMIT 1"; // Busca qualquer registro da ficha 1
            PreparedStatement pst = conn.prepareStatement(sqlCheck);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("   [Diagnostico] Existem dados para Ficha 1 na data: " + rs.getDate("DT_EVD"));
                System.out.println("   -> Se essa data nao for a que voce busca, carregue a planilha do trimestre correto.");
            } else {
                System.out.println("   [Diagnostico] NENHUM dado encontrado para Ficha 1 em data nenhuma.");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
        }
    }
    
    public static void alterarStatus(int id, int novoStatus) {
        String sql = "UPDATE ficha01 SET id_status = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, novoStatus);
            pst.setInt(2, id);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
    
}