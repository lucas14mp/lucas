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

        String sql = "INSERT INTO ficha11_participacao_menor(metodo_valoracao, valor_participacao, lucro_distribuido, data_criacao, trimestre, id_moeda, id_pais, chave, id_status, justificativa_gestor) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
            pst.setString(10, ficha.getJustificativaGestor());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }
//
public static void createBatch(List<Ficha11Menor> listaFichas) throws SQLException { // Adicionado throws SQLException
        String sql = "INSERT INTO ficha11_participacao_menor "
                   + "(metodo_valoracao, valor_participacao, lucro_distribuido, data_criacao, trimestre, id_moeda, id_pais, chave, id_status, justificativa_gestor) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false); // Inicia transação
            pst = conn.prepareStatement(sql);

            for (Ficha11Menor ficha : listaFichas) {
                pst.setString(1, ficha.getMetodoValoracao());
                pst.setDouble(2, ficha.getValorParticipacao());
                pst.setDouble(3, ficha.getLucroDistribuido());
                pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(5, ficha.getTrimestre());
                pst.setInt(6, ficha.getMoeda().getId());
                pst.setInt(7, ficha.getPais().getId());
                pst.setString(8, ficha.getFuncionario().getChave());
                pst.setInt(9, ficha.getStatus().getId());
                
                if (ficha.getJustificativaGestor() != null && !ficha.getJustificativaGestor().isEmpty()) {
                    pst.setString(10, ficha.getJustificativaGestor());
                } else {
                    pst.setNull(10, java.sql.Types.VARCHAR);
                }

                pst.addBatch();
            }

            pst.executeBatch();
            conn.commit(); // Confirma a gravação

        } catch (SQLException e) {
            // Se der erro, desfaz tudo
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace(); 
            // AQUI ESTÁ A CORREÇÃO: LANÇAR O ERRO PARA CIMA
            throw new SQLException("Erro ao salvar lote no banco: " + e.getMessage()); 
        } finally {
            Conexao.fecharConexao(conn, pst, null);
        }
    }
//    
    public static void update(Ficha11Menor ficha) {

        String sql = "UPDATE ficha11_participacao_menor SET metodo_valoracao = ?, valor_participacao = ?, lucro_distribuido = ?, id_moeda = ?, id_pais = ?, chave = ?, data_criacao = ?, justificativa_gestor = ? WHERE id = ?";
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
            pst.setString(8, ficha.getJustificativaGestor());            
            pst.setInt(9, ficha.getId());
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
    
    // Método de Validação com a Planilha 4010
    // Filtra consolidado por '11.1' (Participação Menor/Renda Variável)
    public static boolean verificarNecessidadeJustificativa(double valorInformadoOriginal, int trimestreFicha, int anoFicha) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean precisaJustificar = false;

        int triReferencia = trimestreFicha - 1;
        int anoReferencia = anoFicha;

        // Ajusta se for primeiro trimestre (vira 4º tri do ano anterior)
        if (triReferencia == 0) {
            triReferencia = 4;
            anoReferencia = anoFicha - 1;
        }

        try {
            connection = Conexao.conectar();
            StringBuilder sql = new StringBuilder();
            
            // Soma o valor da Tabela 4010 para as contas associadas à Ficha 11 Menor (11.1)
            sql.append("SELECT SUM(COALESCE(r.CONSOLIDADO, 0)) as total_consolidado ");
            sql.append("FROM consolidado c ");
            sql.append("LEFT JOIN planilha4010 r ON r.CD_CT_PLN = c.cosif AND r.CD_IOR = c.CD_IOR AND r.CD_RBC = c.CD_RBC ");
            sql.append("AND QUARTER(r.DT_EVD) = ? AND YEAR(r.DT_EVD) = ? ");
            sql.append("WHERE c.ficha = '11.1' "); 

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, triReferencia);
            pst.setInt(2, anoReferencia);
            rs = pst.executeQuery();

            double valorPlanilhaBrl = 0.0;
            if (rs.next()) {
                valorPlanilhaBrl = rs.getDouble("total_consolidado");
            }

            // Lógica de comparação (Exemplo: Tolerância de 0.5%)
            // Se o banco não tiver dados (0.0), geralmente aceita-se o valor novo sem travar,
            // mas se houver valor histórico, valida a divergência.
            if (valorPlanilhaBrl != 0) {
                double diferenca = Math.abs(valorInformadoOriginal - valorPlanilhaBrl);
                if ((diferenca / valorPlanilhaBrl) * 100 > 0.5) {
                    precisaJustificar = true;
                }
            } 
            // Se quiser forçar justificativa para novos aportes (valorPlanilha == 0 e valorInformado > 0), descomente:
            // else if (valorInformadoOriginal > 0) { precisaJustificar = true; }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return precisaJustificar;
    }
    
    public static List<Integer> getAnosExistentes() {
        List<Integer> anos = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(data_criacao) AS ano FROM ficha11_participacao_menor ORDER BY ano DESC";
        try (Connection con = Conexao.conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) { anos.add(rs.getInt("ano")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return anos;
    }

    public static List<Integer> getTrimestresExistentes() {
        List<Integer> trims = new ArrayList<>();
        String sql = "SELECT DISTINCT trimestre FROM ficha11_participacao_menor ORDER BY trimestre DESC";
        try (Connection con = Conexao.conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) { trims.add(rs.getInt("trimestre")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return trims;
    }

    public static List<Ficha11Menor> readComFiltros(String trimestre, String ano) {
        List<Ficha11Menor> fichas = new ArrayList<>();
        
        // 1. Instanciamos os controllers necessários aqui dentro (A causa do erro)
        MoedaController moedaController = new MoedaController();
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();

        // 2. Mudamos para f.* para não dar conflito de ID
        StringBuilder sql = new StringBuilder(
            "SELECT f.* FROM ficha11_participacao_menor f WHERE 1=1 "
        );

        boolean temTrimestre = (trimestre != null && !trimestre.isEmpty() && !trimestre.equals("todos"));
        boolean temAno = (ano != null && !ano.isEmpty() && !ano.equals("todos"));

        if (temTrimestre) sql.append(" AND f.trimestre = ? ");
        if (temAno) sql.append(" AND YEAR(f.data_criacao) = ? ");
        sql.append(" ORDER BY f.data_criacao DESC");

        try (Connection con = Conexao.conectar(); PreparedStatement pst = con.prepareStatement(sql.toString())) {
            int p = 1;
            if (temTrimestre) pst.setInt(p++, Integer.parseInt(trimestre));
            if (temAno) pst.setInt(p++, Integer.parseInt(ano));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // 3. Declaramos a variável e adicionamos os dados
                    Ficha11Menor ficha = new Ficha11Menor();
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
                    ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                    
                    // 4. Adicionamos na lista local correta
                    fichas.add(ficha);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return fichas;
    }
    
}