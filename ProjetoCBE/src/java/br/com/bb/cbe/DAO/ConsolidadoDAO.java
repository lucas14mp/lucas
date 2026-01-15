package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Consolidado;
import br.com.bb.cbe.Utils.FichaUtils;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.conexao.Conexao;

import java.sql.*;
import java.util.*;

public class ConsolidadoDAO {

    public static List<Consolidado> getAllConsolidado() {
        List<Consolidado> cosifs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String query = "SELECT * FROM planilha4010";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Consolidado cosif = new Consolidado();
                cosif.setCosif(rs.getInt("CD_CT_PLN"));
                cosif.setSaldo(rs.getDouble("consolidado"));
                cosifs.add(cosif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return cosifs;
    }

    public static double getConsoliadoByCosif(int cosif) {
        double consolidado = 0;
        System.out.println("COSIF: " + cosif);
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String query = "SELECT CONSOLIDADO FROM planilha4010 WHERE CD_CT_PLN = ?";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            pst.setInt(1, cosif);
            rs = pst.executeQuery();
            if (rs.next()) {
                consolidado = rs.getDouble("CONSOLIDADO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return consolidado;
    }

    /**
     * Busca os dados consolidados filtrando por Ficha, Mês e Ano.
     * 1. Usa '=' no COSIF (sem LIKE) para evitar pegar finais 80/28.
     * 2. Filtra por IOR e RBC para pegar a linha exata e não somar duplicado.
     * 3. Filtra por Mês e Ano da tabela 4010 (DT_EVD).
     */
    public static List<Map<String, Object>> getConsolidadoByFicha(String ficha, int mes, int ano) {
        System.out.println("Buscando dados para a ficha: " + ficha + " | Mês: " + mes + " | Ano: " + ano);
        List<Map<String, Object>> resultados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String query =
                "SELECT\n" +
                "  c.cosif,\n" +
                "  c.nome_cosif,\n" +
                "  c.ficha,\n" +
                "  c.nome_ficha,\n" +
                "  SUM(COALESCE(r.CONSOLIDADO, 0)) AS total_consolidado\n" +
                "FROM consolidado c\n" +
                "LEFT JOIN planilha4010 r \n" +
                "  ON r.CD_CT_PLN = c.cosif \n" + // Igualdade estrita
                "  AND r.CD_IOR = c.CD_IOR\n" +   // Filtra IOR igual
                "  AND r.CD_RBC = c.CD_RBC\n" +   // Filtra RBC igual
                "  AND MONTH(r.DT_EVD) = ? \n" +  // Filtra o Mês exato
                "  AND YEAR(r.DT_EVD) = ? \n" +   // Filtra o Ano exato
                "WHERE c.ficha = ?\n" +
                "GROUP BY c.cosif, c.nome_cosif, c.ficha, c.nome_ficha;";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            pst.setInt(1, mes);
            pst.setInt(2, ano);
            pst.setString(3, ficha);
            rs = pst.executeQuery();

            while (rs.next()) {
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("consolidado", rs.getDouble("total_consolidado"));
                resultado.put("cosif", rs.getInt("cosif"));
                resultado.put("nomeFicha", rs.getString("nome_ficha"));
                resultado.put("nomeCosif", rs.getString("nome_cosif"));
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return resultados;
    }

    // Método antigo mantido por compatibilidade
    public static Map<String, Object> getSomaFichaByTrimestreAno(int numFicha, int trimestre, int ano) {
        return getSomaFichaByTrimestreAno(String.valueOf(numFicha), trimestre, ano);
    }

    // Método principal para soma do Gestor (Com lógica de shift de trimestre)
    public static Map<String, Object> getSomaFichaByTrimestreAno(String ficha, int trimestre, int ano) {
        
        // 1. Guardar os valores ORIGINAIS (Selecionados no filtro) para buscar a PTAX correta
        // A PTAX deve ser a do trimestre de REFERÊNCIA (ex: Tri 3), não o da competência (Tri 4)
        int triPtax = trimestre;
        int anoPtax = ano;

        // 2. Lógica de "Shift" do Gestor: Se veio tri 3, busca ficha do tri 4.
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano += 1; break;
            default: break;
        }

        Map<String, Object> resultado = new HashMap<>();

        int baseFicha;
        try {
            String base = (ficha != null && ficha.contains(".")) ? ficha.substring(0, ficha.indexOf('.')) : ficha;
            baseFicha = Integer.parseInt(base);
        } catch (Exception e) {
            baseFicha = 0; 
        }

        Map<String, String> valores = FichaUtils.getParametros(baseFicha);
        String tabela = valores.get("tabela");
        String coluna = valores.get("coluna");
        
        if (tabela == null || coluna == null) {
            return resultado;
        }

        String colunaSubficha = valores.getOrDefault("colunaSubficha", "ficha"); 
        boolean isSubficha = (ficha != null && ficha.contains("."));

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(CASE ")
          .append("WHEN f.id_moeda = 16 THEN f.").append(coluna).append(" ") // Se for Real (16), não converte
          .append("ELSE f.").append(coluna).append(" * p.compra ") // Se for outra moeda, usa a PTAX filtrada
          .append("END) AS total_convertido ")
          .append("FROM ").append(tabela).append(" f ")
          
          // Filtra a PTAX pelo Trimestre e Ano ORIGINAIS (triPtax e anoPtax)
          .append("LEFT JOIN ptax p ON f.id_moeda = p.id_moeda AND p.trimestre = ? AND YEAR(p.data_criacao) = ? ") 
          
          // Filtra a FICHA pelo Trimestre e Ano "SHIFTADOS" (+1)
          .append("WHERE f.trimestre = ? AND YEAR(f.data_criacao) = ? ");

        if (isSubficha) {
             sb.append("AND f.").append(colunaSubficha).append(" = ? ");
        }

        String query = sb.toString();

        try (Connection conn = Conexao.conectar();
             PreparedStatement pst = conn.prepareStatement(query)) {

            int index = 1;
            
            // 1. Parâmetros da PTAX (Referência selecionada no filtro)
            pst.setInt(index++, triPtax);
            pst.setInt(index++, anoPtax);
            
            // 2. Parâmetros da FICHA (Competência/Shiftada)
            pst.setInt(index++, trimestre);
            pst.setInt(index++, ano);
            
            // 3. Parâmetro opcional de subficha
            if (isSubficha) {
                pst.setString(index++, ficha);
            }

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    resultado.put("valorFicha", rs.getDouble("total_convertido"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Retorna os trimestres disponíveis baseados na tabela de dados (Contábil).
     */
    public static List<Map<String, Integer>> getPeriodosDisponiveis() {
        List<Map<String, Integer>> periodos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        // Pega Ano e Trimestre (baseado no mês) das datas que existem
        String query = "SELECT DISTINCT YEAR(DT_EVD) as ano, QUARTER(DT_EVD) as tri " +
                       "FROM planilha4010 " +
                       "ORDER BY ano DESC, tri DESC";

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Map<String, Integer> item = new HashMap<>();
                item.put("ano", rs.getInt("ano"));
                item.put("tri", rs.getInt("tri"));
                periodos.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return periodos;
    }
    
    public static void salvarValorBacen(int trimestre, int ano, String fichaNome, double valor) {
        // Tabela criada: valor_bacen
        String sql = "INSERT INTO valor_bacen (trimestre, ano, ficha_nome, valor) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE valor = ?";
        
        Connection con = null;
        PreparedStatement pst = null;
        
        try {
            con = Conexao.conectar();
            pst = con.prepareStatement(sql);
            
            pst.setInt(1, trimestre);
            pst.setInt(2, ano);
            pst.setString(3, fichaNome);
            pst.setDouble(4, valor);
            pst.setDouble(5, valor); // Update
            
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(con, pst, null);
        }
    }

    public static Map<String, Double> getValoresBacen(int trimestre, int ano) {
        Map<String, Double> mapa = new HashMap<>();
        String sql = "SELECT ficha_nome, valor FROM valor_bacen WHERE trimestre = ? AND ano = ?";
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = Conexao.conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, trimestre);
            pst.setInt(2, ano);
            
            rs = pst.executeQuery();
            while (rs.next()) {
                mapa.put(rs.getString("ficha_nome"), rs.getDouble("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(con, pst, rs);
        }
        return mapa;
    }
}