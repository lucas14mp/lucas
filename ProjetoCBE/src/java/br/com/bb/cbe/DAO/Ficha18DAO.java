package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha18;
import br.com.bb.cbe.conexao.Conexao;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.PaisController;
import br.com.bb.cbe.controllers.StatusController;
import br.com.bb.cbe.DAO.PtaxDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha18DAO {

    public static void create(Ficha18 ficha) {

        String sql = "INSERT INTO ficha18 (prazo_divida, valor_mercado, juros_recebidos, data_criacao, trimestre, id_moeda, id_pais, chave, id_status, justificativa_gestor) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getPrazoDivida());
            pst.setDouble(2, ficha.getValorMercado());
            pst.setDouble(3, ficha.getJurosRecebidos());
            pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(5, ficha.getTrimestre());
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

    public static void update(Ficha18 ficha) {
        
        String sql = "UPDATE ficha18 SET prazo_divida=?, valor_mercado=?, juros_recebidos=?, data_criacao=?, id_moeda=?, id_pais=?, chave=?, justificativa_gestor=? WHERE id=?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, ficha.getPrazoDivida());
            pst.setDouble(2, ficha.getValorMercado());
            pst.setDouble(3, ficha.getJurosRecebidos());
            pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(5, ficha.getMoeda().getId());
            pst.setInt(6, ficha.getPais().getId());
            pst.setString(7, ficha.getFuncionario().getChave());
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
        String sql = "DELETE FROM ficha18 WHERE id = ?";
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

    public static List<Ficha18> getAllFichas() {
        String query = "SELECT * FROM ficha18";
        List<Ficha18> listaFichas = new ArrayList<Ficha18>();
        Connection connection = null;
        Ficha18 ficha = null;
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
                ficha = new Ficha18();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
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
    
    // MANTER O SWITCH ORIGINAL AQUI
    public static List<Ficha18> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha18 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha18> listaFichas = new ArrayList<Ficha18>();
        Connection connection = null;
        Ficha18 ficha = null;
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
                ficha = new Ficha18();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
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

    public static Optional<Ficha18> getFichaById(int id) {
        String query = "SELECT * FROM ficha18 WHERE id = " + id;
        Connection connection = null;
        Ficha18 ficha = null;
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
                ficha = new Ficha18();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha18 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha18 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha18 ORDER BY trimestre ASC";
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
    public static List<Ficha18> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha18 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha18> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha18 ficha = null;
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
                ficha = new Ficha18();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
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

    // Busca apenas por Trimestre (COM O SWITCH)
    public static List<Ficha18> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT * FROM ficha18 WHERE trimestre = " + trimestre;
        List<Ficha18> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha18 ficha = null;
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
                ficha = new Ficha18();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setPrazoDivida(rs.getString("prazo_divida"));
                ficha.setValorMercado(rs.getDouble("valor_mercado"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setJurosRecebidos(rs.getDouble("juros_recebidos"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
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
            sql.append("SELECT SUM(COALESCE(r.CONSOLIDADO, 0)) as total_consolidado ");
            sql.append("FROM consolidado c ");
            sql.append("LEFT JOIN planilha4010 r ON r.CD_CT_PLN = c.cosif AND r.CD_IOR = c.CD_IOR AND r.CD_RBC = c.CD_RBC ");
            sql.append("AND QUARTER(r.DT_EVD) = ? AND YEAR(r.DT_EVD) = ? ");
            sql.append("WHERE c.ficha = '18' "); // Ficha 18

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, triReferencia);
            pst.setInt(2, anoReferencia);
            rs = pst.executeQuery();

            double valorPlanilhaBrl = 0.0;
            if (rs.next()) valorPlanilhaBrl = rs.getDouble("total_consolidado");

            if (valorPlanilhaBrl != 0) {
                double diferenca = Math.abs(valorInformadoOriginal - valorPlanilhaBrl);
                if ((diferenca / valorPlanilhaBrl) * 100 > 0.5) precisaJustificar = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return precisaJustificar;
    }
    
    public static void alterarStatus(int id, int novoStatus) {
        String sql = "UPDATE ficha18 SET id_status = ? WHERE id = ?";
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
    
    public static double getSomaTotalComJuros(int triBusca, int anoBusca, int triRef, int anoRef) {
        double totalBrl = 0.0;
        
        // SQL: Busca os valores no trimestre onde o dado foi GRAVADO (Busca)
        String sql = "SELECT f.valor_mercado, f.juros_recebidos, f.id_moeda " + 
                     "FROM ficha18 f " + 
                     "WHERE f.trimestre = ? AND YEAR(f.data_criacao) = ?";

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, triBusca);
            pst.setInt(2, anoBusca);

            rs = pst.executeQuery();
            
            while (rs.next()) {
                double valMercado = rs.getDouble("valor_mercado");
                // Garante que se juros for nulo, usa 0.0
                double valJuros = rs.getObject("juros_recebidos") != null ? rs.getDouble("juros_recebidos") : 0.0; 
                int idMoeda = rs.getInt("id_moeda");
                
                // 1. SOMA (Mercado + Juros) na moeda original
                double totalOriginal = valMercado + valJuros;
                
                // 2. Busca a PTAX do Trimestre de REFERÊNCIA (o do Balancete)
                double taxaMoeda = PtaxDAO.getTaxaCompra(idMoeda, triRef, anoRef);
                
                // 3. Converte
                totalBrl += (totalOriginal * taxaMoeda);
            }
            
            return totalBrl;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
    }
}