package br.com.bb.cbe.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.bb.cbe.controllers.StatusController;
import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.Bean.Ficha03;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import java.util.List;
import java.util.Optional;

public class Ficha03DAO {

    public static void create(Ficha03 ficha) {
        String sql = "INSERT INTO ficha03(valor_database, id_moeda, data_criacao, trimestre, chave, id_status, justificativa_gestor) VALUES (?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getValorDatabase());
            pst.setDouble(2, ficha.getMoeda().getId());
            pst.setDate(3, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(4, ficha.getTrimestre());
            pst.setString(5, ficha.getFuncionario().getChave());
            pst.setInt(6, ficha.getStatus().getId());
            pst.setString(7, ficha.getJustificativaGestor());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void update(Ficha03 ficha) {
        String sql = "UPDATE ficha03 SET valor_database = ?,  id_moeda = ?, chave = ?, data_criacao = ?, justificativa_gestor = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getValorDatabase());
            pst.setInt(2, ficha.getMoeda().getId());
            pst.setString(3, ficha.getFuncionario().getChave());
            pst.setDate(4, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(5, ficha.getId());
            pst.setString(6, ficha.getJustificativaGestor());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM ficha03 WHERE id = ?";
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

    public static List<Ficha03> getAllFichas() {
        String query = "SELECT * FROM ficha03";
        List<Ficha03> listaFichas = new ArrayList<Ficha03>();
        Connection connection = null;
        Ficha03 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha03();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
        return listaFichas;
    }

    // MANTER O SWITCH ORIGINAL AQUI
    public static List<Ficha03> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha03 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha03> listaFichas = new ArrayList<Ficha03>();
        Connection connection = null;
        Ficha03 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha03();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                listaFichas.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
        return listaFichas;
    }

    public static Optional<Ficha03> getFichaById(int id) {
        String query = "SELECT * FROM ficha03 WHERE id = " + id;
        Connection connection = null;
        Ficha03 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha03();
                ficha.setId(rs.getInt("id"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                ficha.setJustificativaGestor(rs.getString("justificativa_gestor"));
                return Optional.of(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
        return Optional.empty();
    }

    public static void validarFormularios(List<String> idsValidados, String chave) {
        if (idsValidados.isEmpty()) {
            return;
        }
        StringBuilder sql = new StringBuilder("UPDATE ficha03 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha03 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha03 ORDER BY trimestre ASC";
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
    public static List<Ficha03> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha03 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha03> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha03 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha03();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
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

    // Busca apenas por Trimestre (COM O SWITCH)
    public static List<Ficha03> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT * FROM ficha03 WHERE trimestre = " + trimestre;
        List<Ficha03> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha03 ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha03();
                ficha.setId(rs.getInt("id"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
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
    
    public static boolean verificarNecessidadeJustificativa(double valorInformadoOriginal, int trimestreFicha, int anoFicha) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean precisaJustificar = false;

        // Lógica do Período (Vigente -> Anterior)
        int triReferencia = trimestreFicha - 1;
        int anoReferencia = anoFicha;
        if (triReferencia == 0) {
            triReferencia = 4;
            anoReferencia = anoFicha - 1;
        }

        try {
            connection = Conexao.conectar();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT SUM(COALESCE(r.CONSOLIDADO, 0)) as total_consolidado ");
            sql.append("FROM consolidado c ");
            sql.append("LEFT JOIN planilha4010 r ");
            sql.append("  ON r.CD_CT_PLN = c.cosif ");
            sql.append("  AND r.CD_IOR = c.CD_IOR ");
            sql.append("  AND r.CD_RBC = c.CD_RBC ");
            sql.append("  AND QUARTER(r.DT_EVD) = ? ");
            sql.append("  AND YEAR(r.DT_EVD) = ? ");
            sql.append("WHERE c.ficha = '3' "); // <--- MUDANÇA IMPORTANTE: Ficha 3

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, triReferencia);
            pst.setInt(2, anoReferencia);

            rs = pst.executeQuery();

            double valorPlanilhaBrl = 0.0;
            if (rs.next()) {
                valorPlanilhaBrl = rs.getDouble("total_consolidado");
            }

            // Validação ( > 0.5% )
            if (valorPlanilhaBrl != 0) {
                double diferenca = Math.abs(valorInformadoOriginal - valorPlanilhaBrl);
                double percentual = (diferenca / valorPlanilhaBrl) * 100;
                if (percentual > 0.5) {
                    precisaJustificar = true;
                }
            } else if (valorInformadoOriginal != 0) {
                precisaJustificar = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }

        return precisaJustificar;
    }
    
    public static void alterarStatus(int id, int novoStatus) {
        String sql = "UPDATE ficha03 SET id_status = ? WHERE id = ?";
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