package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha16;
import br.com.bb.cbe.conexao.Conexao;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.PaisController;
import br.com.bb.cbe.controllers.StatusController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha16DAO {

    public static void create(Ficha16 ficha) {

        String sql = "INSERT INTO ficha16 (id_pais, id_moeda, tipo_outros_direitos, valor_database, data_criacao, trimestre, chave, id_status, justificativa_gestor) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, ficha.getPais().getId());
            pst.setInt(2, ficha.getMoeda().getId());
            pst.setString(3, ficha.getTipoOutrosDireito());
            pst.setDouble(4, ficha.getValorDatabase());
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

    public static void update(Ficha16 ficha) {

        String sql = "UPDATE ficha16 SET id_pais = ?, id_moeda = ?, tipo_outros_direitos = ?, valor_database = ?, chave = ?, data_criacao = ? justificativa_gestor = ?  WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, ficha.getPais().getId());
            pst.setInt(2, ficha.getMoeda().getId());
            pst.setString(3, ficha.getTipoOutrosDireito());
            pst.setDouble(4, ficha.getValorDatabase());
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
        String sql = "DELETE FROM ficha16 WHERE id = ?";
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

    public static List<Ficha16> getAllFichas() {
        String query = "SELECT * FROM ficha16";
        List<Ficha16> listaFichas = new ArrayList<Ficha16>();
        Connection connection = null;
        Ficha16 ficha = null;
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
                ficha = new Ficha16();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setTipoOutrosDireito(rs.getString("tipo_outros_direitos"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
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
    public static List<Ficha16> getAllFichasByTrimestreAno(int trimestre, int ano) {
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
        String query = "SELECT * FROM ficha16 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha16> listaFichas = new ArrayList<Ficha16>();
        Connection connection = null;
        Ficha16 ficha = null;
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
                ficha = new Ficha16();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setTipoOutrosDireito(rs.getString("tipo_outros_direitos"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
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

    public static Optional<Ficha16> getFichaById(int id) {
        String query = "SELECT * FROM ficha16 WHERE id = " + id;
        Connection connection = null;
        Ficha16 ficha = null;
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
                ficha = new Ficha16();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setTipoOutrosDireito(rs.getString("tipo_outros_direitos"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha16 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha16 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha16 ORDER BY trimestre ASC";
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
    public static List<Ficha16> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha16 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha16> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha16 ficha = null;
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
                ficha = new Ficha16();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setTipoOutrosDireito(rs.getString("tipo_outros_direitos"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
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
    public static List<Ficha16> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }

        String query = "SELECT * FROM ficha16 WHERE trimestre = " + trimestre;
        List<Ficha16> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha16 ficha = null;
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
                ficha = new Ficha16();
                ficha.setId(rs.getInt("id"));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setTipoOutrosDireito(rs.getString("tipo_outros_direitos"));
                ficha.setValorDatabase(rs.getDouble("valor_database"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
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
            sql.append("WHERE c.ficha = '16' ");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, triReferencia);
            pst.setInt(2, anoReferencia);
            rs = pst.executeQuery();

            double valorPlanilhaBrl = 0.0;
            if (rs.next()) {
                valorPlanilhaBrl = rs.getDouble("total_consolidado");
            }

            // Só validamos a diferença SE o banco tiver algum valor histórico (> 0).
            // Se o banco retornar 0 (novo aporte ou sem dados), aceitamos o valor informado sem pedir justificativa.
            if (valorPlanilhaBrl != 0) {
                double diferenca = Math.abs(valorInformadoOriginal - valorPlanilhaBrl);
                // Se a diferença for maior que 0.5%
                if ((diferenca / valorPlanilhaBrl) * 100 > 0.5) {
                    precisaJustificar = true;
                }
            }
            // REMOVIDO O "else if (valorInformadoOriginal != 0)" QUE FORÇAVA A JUSTIFICATIVA

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return precisaJustificar;
    }
    
    public static void alterarStatus(int id, int novoStatus) {
        String sql = "UPDATE ficha16 SET id_status = ? WHERE id = ?";
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