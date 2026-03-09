package br.com.bb.cbe.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.bb.cbe.controllers.StatusController;
import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.Bean.Ficha08;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.PaisController;
import java.util.List;
import java.util.Optional;

public class Ficha08DAO {

    public static void create(Ficha08 ficha) {
        String sql = "INSERT INTO ficha08(id_moeda, id_pais, saldo_database, rendimentos, data_criacao, trimestre, chave, id_status, justificativa_gestor) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, ficha.getMoeda().getId());
            pst.setInt(2, ficha.getPais().getId());
            pst.setDouble(3, ficha.getSaldoDatabase());
            pst.setDouble(4, ficha.getRendimentos());
            if (ficha.getDataCriacao() != null) {
                pst.setDate(5, new java.sql.Date(ficha.getDataCriacao().getTime()));
            } else {
                pst.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
            }
            pst.setInt(6, ficha.getTrimestre());
            if (ficha.getFuncionario() != null) {
                pst.setString(7, ficha.getFuncionario().getChave());
            } else {
                pst.setString(7, null);
            }
            pst.setInt(8, ficha.getStatus().getId());
            pst.setString(9, ficha.getJustificativaGestor());
            
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void update(Ficha08 ficha) {
        String sql = "UPDATE ficha08 SET id_moeda=?, id_pais=?, saldo_database=?, rendimentos=?, data_criacao=?, chave=?, id_status=?, justificativa_gestor=? WHERE id=?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            
            pst.setInt(1, ficha.getMoeda().getId());
            pst.setInt(2, ficha.getPais().getId());
            pst.setDouble(3, ficha.getSaldoDatabase());
            pst.setDouble(4, ficha.getRendimentos());
            
            if (ficha.getDataCriacao() != null) {
                pst.setDate(5, new java.sql.Date(ficha.getDataCriacao().getTime()));
            } else {
                pst.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
            }
            
            if (ficha.getFuncionario() != null) {
                pst.setString(6, ficha.getFuncionario().getChave());
            } else {
                pst.setString(6, null);
            }
            
            pst.setInt(7, ficha.getStatus().getId());
            
            pst.setString(8, ficha.getJustificativaGestor());
            
            pst.setInt(9, ficha.getId()); // WHERE id
            
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM ficha08 WHERE id = ?";
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

    // MÉTODOS DE CONSULTA

    public static List<Ficha08> getAllFichas() {
        String query = "SELECT * FROM ficha08";
        List<Ficha08> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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

    public static List<Ficha08> getAllFichasByTrimestreAno(int trimestre, int ano) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; ano = ano + 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha08 WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;
        List<Ficha08> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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

    public static List<Ficha08> getAllFichasByAno(int ano) {
        String query = "SELECT * FROM ficha08 WHERE YEAR(data_criacao) = " + ano;
        List<Ficha08> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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

    public static List<Ficha08> getAllFichasByTrimestre(int trimestre) {
        switch (trimestre) {
            case 1: trimestre = 2; break;
            case 2: trimestre = 3; break;
            case 3: trimestre = 4; break;
            case 4: trimestre = 1; break;
            default: break;
        }
        String query = "SELECT * FROM ficha08 WHERE trimestre = " + trimestre;
        List<Ficha08> listaFichas = new ArrayList<>();
        Connection connection = null;
        Ficha08 ficha = null;
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
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
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

    public static Optional<Ficha08> getFichaById(int id) {
        String query = "SELECT * FROM ficha08 WHERE id = " + id;
        Connection connection = null;
        Ficha08 ficha = null;
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
            if (rs.next()) {
                ficha = new Ficha08();
                ficha.setId(rs.getInt("id"));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                ficha.setSaldoDatabase(rs.getDouble("saldo_database"));
                ficha.setRendimentos(rs.getDouble("rendimentos"));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                // Carrega a justificativa na edição
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
        StringBuilder sql = new StringBuilder("UPDATE ficha08 SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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
        String sql = "SELECT DISTINCT YEAR(data_criacao) as ano FROM ficha08 ORDER BY ano DESC";
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
        String sql = "SELECT DISTINCT trimestre FROM ficha08 ORDER BY trimestre ASC";
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

    // MÉTODO DE VALIDAÇÃO
    public static boolean verificarNecessidadeJustificativa(double valorInformadoOriginal, int trimestreFicha, int anoFicha) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean precisaJustificar = false;

        int triReferencia = trimestreFicha - 1;
        int anoReferencia = anoFicha;
        if (triReferencia == 0) {
            triReferencia = 4;
            anoReferencia = anoFicha - 1;
        }

        try {
            connection = Conexao.conectar();

            // A query soma automaticamente TODOS os COSIFs vinculados à ficha '8' na tabela consolidado
            // Assim, se houver 2 ou mais COSIFs, eles são somados antes da comparação.
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT SUM(COALESCE(r.CONSOLIDADO, 0)) as total_consolidado ");
            sql.append("FROM consolidado c ");
            sql.append("LEFT JOIN planilha4010 r ");
            sql.append("  ON r.CD_CT_PLN = c.cosif ");
            sql.append("  AND r.CD_IOR = c.CD_IOR ");
            sql.append("  AND r.CD_RBC = c.CD_RBC ");
            sql.append("  AND QUARTER(r.DT_EVD) = ? ");
            sql.append("  AND YEAR(r.DT_EVD) = ? ");
            sql.append("WHERE c.ficha = '8' "); // Ficha 8

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, triReferencia);
            pst.setInt(2, anoReferencia);
            
            rs = pst.executeQuery();

            double valorPlanilhaBrl = 0.0;
            if (rs.next()) {
                valorPlanilhaBrl = rs.getDouble("total_consolidado");
            }

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
        String sql = "UPDATE ficha08 SET id_status = ? WHERE id = ?";
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
    
    public static double getSomaPorDependencia(int trimestreRef, int anoRef, Integer idDependencia) {
        double totalBrl = 0.0;
        double totalRend = 0.0;
        
        // 1. Lógica do Trimestre da Ficha (T+1 em relação ao Contábil)
        int triBusca = trimestreRef + 1;
        int anoBusca = anoRef;
        
        if (triBusca > 4) {
            triBusca = 1;
            anoBusca = anoRef + 1;
        }

        // 2. SQL
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f.saldo_database, f.rendimentos, f.id_moeda ");
        sql.append("FROM ficha08 f ");
        sql.append("INNER JOIN funcionario func ON f.chave = func.chave ");
        sql.append("WHERE f.trimestre = ? ");
        sql.append("  AND YEAR(f.data_criacao) = ? ");
        
        // Se idDependencia for nulo, não filtra (ou filtra 'is null' se fosse a regra, 
        // mas aqui deixaremos aberto para somar tudo caso não venha o ID)
        if (idDependencia != null) {
            sql.append("  AND func.id_dependencia = ? ");
        }

        try (Connection con = Conexao.conectar();
             PreparedStatement pst = con.prepareStatement(sql.toString())) {

            pst.setInt(1, triBusca);
            pst.setInt(2, anoBusca);
            
            if (idDependencia != null) {
                pst.setInt(3, idDependencia);
            }

            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                double saldo = rs.getDouble("saldo_database");
                double rendimentos = rs.getDouble("rendimentos");
                int idMoeda = rs.getInt("id_moeda");
                
                // 3. Conversão PTAX (Usa o Trimestre de REFERÊNCIA contábil)
                double taxa = PtaxDAO.getTaxaCompra(idMoeda, trimestreRef, anoRef);
                
                totalRend = (rendimentos * taxa);
                
    System.out.printf(
        "Moeda=%d | Taxa=%.6f | Rendimentos(orig)=%.2f -> Rendimentos(BRL)=%.2f%n",
        idMoeda, taxa, rendimentos, totalRend
    );

                totalBrl += ((saldo + totalRend) * taxa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
        
        return totalBrl;
    }
    
}