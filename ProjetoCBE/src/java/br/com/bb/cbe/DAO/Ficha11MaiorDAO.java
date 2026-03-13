package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.controllers.*;
import br.com.bb.cbe.DAO.PtaxDAO;
import br.com.bb.cbe.Utils.NumeroUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Ficha11MaiorDAO {
    
    public static void createBatch(List<Ficha11Maior> listaFichas) throws SQLException {
        // SQL completo com TODAS as colunas da tabela para evitar erros de "Field 'x' doesn't have a default value"
        String sql = "INSERT INTO ficha11_participacao_maior "
                   + "(id_empresa, id_moeda, patrimonio_total, participacao_capital_social, porcento_poder_voto, "
                   + "ativo_database, passivo_exigivel, result_liq_itens_nao_recorrentes, result_liq_reavaliacoes, lucro_distribuido, "
                   + "controla_empresas, diretoria, data_criacao, trimestre, chave, id_status, justificativa_gestor, "
                   + "possui_cotacao_em_bolsa, metodo_valoracao, valor_empresa, valor_total_lucro_preju_liquido, result_liq_variacao_cambial) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false); // Inicia transação
            pst = conn.prepareStatement(sql);

            for (Ficha11Maior ficha : listaFichas) {
                // 1. ID Empresa
                pst.setInt(1, ficha.getEmpresa().getId());
                
                // 2. ID Moeda
                pst.setInt(2, ficha.getMoeda().getId());
                
                pst.setObject(3, ficha.getPatrimonioTotal(), java.sql.Types.DOUBLE);
                pst.setObject(4, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
                pst.setObject(5, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
                pst.setObject(6, ficha.getAtivoDatabase(), java.sql.Types.DOUBLE);
                pst.setObject(7, ficha.getPassivoExigivel(), java.sql.Types.DOUBLE);
                pst.setObject(8, ficha.getResultadoLiquidoItensNaoRecorrentes(), java.sql.Types.DOUBLE);
                pst.setObject(9, ficha.getResultadoLiquidoReavaliacoes(), java.sql.Types.DOUBLE);
                pst.setObject(10, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
                pst.setObject(11, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN); 
                
                // 12. Diretoria / UPE (Do Select na tela)
                if (ficha.getDiretoria() != null && !ficha.getDiretoria().isEmpty()) {
                    pst.setString(12, ficha.getDiretoria());
                } else {
                    pst.setNull(12, java.sql.Types.VARCHAR);
                }
                
                // 13. Data Criação
                pst.setDate(13, new java.sql.Date(ficha.getDataCriacao().getTime()));
                
                // 14. Trimestre
                pst.setInt(14, ficha.getTrimestre());
                
                // 15. Chave Funcionário
                pst.setString(15, ficha.getFuncionario().getChave());
                
                // 16. ID Status
                pst.setInt(16, ficha.getStatus().getId());
                
                // 17. Justificativa
                if (ficha.getJustificativaGestor() != null && !ficha.getJustificativaGestor().isEmpty()) {
                    pst.setString(17, ficha.getJustificativaGestor());
                } else {
                    pst.setNull(17, java.sql.Types.VARCHAR);
                }
                
                pst.setObject(18, ficha.getPossuiCotacaoEmBolsa(), java.sql.Types.BOOLEAN);
                pst.setString(19, ficha.getMetodoValoracao() != null && !ficha.getMetodoValoracao().isEmpty() ? ficha.getMetodoValoracao() : "Não Informado via Excel");
                pst.setObject(20, ficha.getValorEmpresa(), java.sql.Types.DOUBLE);
                pst.setObject(21, ficha.getValorTotalLucroPrejuizo(), java.sql.Types.DOUBLE);
                pst.setObject(22, ficha.getResultadoLiquidoVariacaoCambial(), java.sql.Types.DOUBLE);

                pst.addBatch();
            }

            pst.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            throw new SQLException("Erro ao salvar lote Maior 10%: " + e.getMessage());
        } finally {
            Conexao.fecharConexao(conn, pst, null);
        }
    }

    public static void createBatchCoger(List<Ficha11Maior> fichas) {
        String sql = "INSERT INTO ficha11_participacao_maior (lucro_distribuido, valor_empresa, patrimonio_total, participacao_capital_social, porcento_poder_voto, ativo_database, passivo_exigivel, valor_total_lucro_preju_liquido, result_liq_reavaliacoes, result_liq_variacao_cambial, controla_empresas, data_criacao, trimestre, id_moeda, chave, id_empresa, id_status, metodo_valoracao) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); 
            pst = connection.prepareStatement(sql);

            for (Ficha11Maior ficha : fichas) {
                pst.setObject(1, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
                pst.setObject(2, ficha.getValorEmpresa(), java.sql.Types.DOUBLE);
                pst.setObject(3, ficha.getPatrimonioTotal(), java.sql.Types.DOUBLE);
                pst.setObject(4, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
                pst.setObject(5, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
                pst.setObject(6, ficha.getAtivoDatabase(), java.sql.Types.DOUBLE);
                pst.setObject(7, ficha.getPassivoExigivel(), java.sql.Types.DOUBLE);
                pst.setObject(8, ficha.getValorTotalLucroPrejuizo(), java.sql.Types.DOUBLE);
                pst.setObject(9, ficha.getResultadoLiquidoReavaliacoes(), java.sql.Types.DOUBLE);
                pst.setObject(10, ficha.getResultadoLiquidoVariacaoCambial(), java.sql.Types.DOUBLE);
                pst.setObject(11, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN);
                pst.setDate(12, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(13, ficha.getTrimestre());
                pst.setInt(14, ficha.getMoeda().getId());
                pst.setString(15, ficha.getFuncionario().getChave());
                pst.setInt(16, ficha.getEmpresa().getId());
                pst.setInt(17, ficha.getStatus().getId());
                pst.setString(18, ficha.getMetodoValoracao());
                pst.addBatch();
            }
            pst.executeBatch(); 
            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
     
    public static void createBatchUpe(List<Ficha11Maior> fichas) {
        // SQL com a coluna 'diretoria' adicionada no final
        String sql = "INSERT INTO ficha11_participacao_maior (id_moeda, participacao_capital_social, porcento_poder_voto, possui_cotacao_em_bolsa, metodo_valoracao, result_liq_itens_nao_recorrentes, lucro_distribuido, controla_empresas, data_criacao, trimestre, chave, id_empresa, id_status, diretoria) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); 
            pst = connection.prepareStatement(sql);

            for (Ficha11Maior ficha : fichas) {
                pst.setInt(1, ficha.getMoeda().getId());
                pst.setObject(2, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
                pst.setObject(3, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
                pst.setObject(4, ficha.getPossuiCotacaoEmBolsa(), java.sql.Types.BOOLEAN);
                pst.setString(5, ficha.getMetodoValoracao());
                pst.setObject(6, ficha.getResultadoLiquidoItensNaoRecorrentes(), java.sql.Types.DOUBLE);
                pst.setObject(7, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
                pst.setObject(8, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN);
                pst.setDate(9, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(10, ficha.getTrimestre());
                pst.setString(11, ficha.getFuncionario().getChave());
                pst.setInt(12, ficha.getEmpresa().getId());
                pst.setInt(13, ficha.getStatus().getId());
                
                // Setando a diretoria
                pst.setString(14, "UPE");
                pst.addBatch();
            }
            pst.executeBatch(); 
            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    
    public static void create(Ficha11Maior ficha) {

        String sql = "INSERT INTO ficha11_participacao_maior(possui_cotacao_em_bolsa, metodo_valoracao, valor_empresa, patrimonio_total, participacao_capital_social, porcento_poder_voto, ativo_database, passivo_exigivel, valor_total_lucro_preju_liquido, result_liq_itens_nao_recorrentes, result_liq_reavaliacoes, result_liq_variacao_cambial, lucro_distribuido, controla_empresas, data_criacao, trimestre, id_moeda, chave, id_empresa, id_status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            
            pst.setObject(1, ficha.getPossuiCotacaoEmBolsa(), java.sql.Types.BOOLEAN);
            pst.setString(2, ficha.getMetodoValoracao());
            pst.setObject(3, ficha.getValorEmpresa(), java.sql.Types.DOUBLE);
            pst.setObject(4, ficha.getPatrimonioTotal(), java.sql.Types.DOUBLE);
            pst.setObject(5, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
            pst.setObject(6, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
            pst.setObject(7, ficha.getAtivoDatabase(), java.sql.Types.DOUBLE);
            pst.setObject(8, ficha.getPassivoExigivel(), java.sql.Types.DOUBLE);
            pst.setObject(9, ficha.getValorTotalLucroPrejuizo(), java.sql.Types.DOUBLE);
            pst.setObject(10, ficha.getResultadoLiquidoItensNaoRecorrentes(), java.sql.Types.DOUBLE);
            pst.setObject(11, ficha.getResultadoLiquidoReavaliacoes(), java.sql.Types.DOUBLE);
            pst.setObject(12, ficha.getResultadoLiquidoVariacaoCambial(), java.sql.Types.DOUBLE);
            pst.setObject(13, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
            pst.setObject(14, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN);   
            pst.setDate(15, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(16, DataUtils.validaTrimestre());
            pst.setInt(17, ficha.getMoeda().getId());
            pst.setString(18, ficha.getFuncionario().getChave());
            pst.setInt(19, ficha.getEmpresa().getId());
            pst.setInt(20, ficha.getStatus().getId());
            
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Ficha11Maior ficha) {

        String sql = "UPDATE ficha11_participacao_maior SET possui_cotacao_em_bolsa = ?, metodo_valoracao = ?, valor_empresa = ?, patrimonio_total = ?, participacao_capital_social = ?, porcento_poder_voto = ?, ativo_database = ?, passivo_exigivel = ?, valor_total_lucro_preju_liquido = ?, result_liq_itens_nao_recorrentes = ?, result_liq_reavaliacoes = ?, result_liq_variacao_cambial = ?, lucro_distribuido = ?, controla_empresas = ?, id_moeda = ?, id_empresa = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            
            pst.setObject(1, ficha.getPossuiCotacaoEmBolsa(), java.sql.Types.BOOLEAN);
            pst.setString(2, ficha.getMetodoValoracao());
            pst.setObject(3, ficha.getValorEmpresa(), java.sql.Types.DOUBLE);
            pst.setObject(4, ficha.getPatrimonioTotal(), java.sql.Types.DOUBLE);
            pst.setObject(5, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
            pst.setObject(6, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
            pst.setObject(7, ficha.getAtivoDatabase(), java.sql.Types.DOUBLE);
            pst.setObject(8, ficha.getPassivoExigivel(), java.sql.Types.DOUBLE);
            pst.setObject(9, ficha.getValorTotalLucroPrejuizo(), java.sql.Types.DOUBLE);
            pst.setObject(10, ficha.getResultadoLiquidoItensNaoRecorrentes(), java.sql.Types.DOUBLE);
            pst.setObject(11, ficha.getResultadoLiquidoReavaliacoes(), java.sql.Types.DOUBLE);
            pst.setObject(12, ficha.getResultadoLiquidoVariacaoCambial(), java.sql.Types.DOUBLE);
            pst.setObject(13, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
            pst.setObject(14, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN);
            
            pst.setInt(15, ficha.getMoeda().getId());
            pst.setInt(16, ficha.getEmpresa().getId());
            pst.setString(17, ficha.getFuncionario().getChave());
            pst.setDate(18, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(19, ficha.getId());
            
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }
    
    public static void updateBatchCoger(List<Ficha11Maior> fichas) {
        String sql = "UPDATE ficha11_participacao_maior SET " +
                "lucro_distribuido = COALESCE(?, lucro_distribuido), " +
                "valor_empresa = COALESCE(?, valor_empresa), " +
                "patrimonio_total = COALESCE(?, patrimonio_total), " +
                "participacao_capital_social = COALESCE(?, participacao_capital_social), " +
                "porcento_poder_voto = COALESCE(?, porcento_poder_voto), " +
                "ativo_database = COALESCE(?, ativo_database), " +
                "passivo_exigivel = COALESCE(?, passivo_exigivel), " +
                "valor_total_lucro_preju_liquido = COALESCE(?, valor_total_lucro_preju_liquido), " +
                "result_liq_reavaliacoes = COALESCE(?, result_liq_reavaliacoes), " +
                "result_liq_variacao_cambial = COALESCE(?, result_liq_variacao_cambial), " +
                "controla_empresas = COALESCE(?, controla_empresas), " +
                "id_moeda = ?, chave = ?, data_criacao = ?, " +
                "metodo_valoracao = COALESCE(?, metodo_valoracao) " +
                "WHERE id_empresa = ? AND trimestre = ? AND data_criacao = ?";
        
        Connection connection = null;
        PreparedStatement pst = null;
        
        String queryAno = "SELECT data_criacao FROM ficha11_participacao_maior WHERE id_empresa = ? AND trimestre = ?";
        ResultSet rsAno = null;
        PreparedStatement pstAno = null;
        List<String> datas = new ArrayList<>();
        java.util.Date dataFinal = null;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
        
        try {
            for (Ficha11Maior ficha : fichas) {
                java.text.SimpleDateFormat anoFormat = new java.text.SimpleDateFormat("yyyy");
                int anoFicha = Integer.parseInt(anoFormat.format(ficha.getDataCriacao()));
                connection = Conexao.conectar();
                pstAno = connection.prepareStatement(queryAno);
                pstAno.setInt(1, ficha.getEmpresa().getId());
                pstAno.setInt(2, ficha.getTrimestre());
                rsAno = pstAno.executeQuery();
                while (rsAno.next()){
                    datas.add(rsAno.getString("data_criacao"));
                }
                for (String data : datas){
                    int anoData = Integer.parseInt(data.split("-")[0]);

                    if (anoData == anoFicha){
                        connection.setAutoCommit(false); 
                        pst = connection.prepareStatement(sql);
                        try { dataFinal = formato.parse(data); } catch (Exception e) {}
                        
                        pst.setObject(1, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
                        pst.setObject(2, ficha.getValorEmpresa(), java.sql.Types.DOUBLE);
                        pst.setObject(3, ficha.getPatrimonioTotal(), java.sql.Types.DOUBLE);
                        pst.setObject(4, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
                        pst.setObject(5, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
                        pst.setObject(6, ficha.getAtivoDatabase(), java.sql.Types.DOUBLE);
                        pst.setObject(7, ficha.getPassivoExigivel(), java.sql.Types.DOUBLE);
                        pst.setObject(8, ficha.getValorTotalLucroPrejuizo(), java.sql.Types.DOUBLE);
                        pst.setObject(9, ficha.getResultadoLiquidoReavaliacoes(), java.sql.Types.DOUBLE);
                        pst.setObject(10, ficha.getResultadoLiquidoVariacaoCambial(), java.sql.Types.DOUBLE);
                        pst.setObject(11, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN);
                        pst.setInt(12, ficha.getMoeda().getId());
                        pst.setString(13, ficha.getFuncionario().getChave());
                        pst.setDate(14, new java.sql.Date(ficha.getDataCriacao().getTime()));
                        
                        String metodo = ficha.getMetodoValoracao();
                        if (metodo != null && (metodo.trim().isEmpty() || metodo.equalsIgnoreCase("Não informado"))) metodo = null;
                        pst.setString(15, metodo);
                        
                        pst.setInt(16, ficha.getEmpresa().getId());
                        pst.setInt(17, ficha.getTrimestre());
                        pst.setDate(18, new java.sql.Date(dataFinal.getTime()));
                        pst.addBatch();
                    }
                }
            }
            pst.executeBatch(); 
            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) try { connection.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }
    
    public static void updateBatchUpe(List<Ficha11Maior> fichas) {
        String sql = "UPDATE ficha11_participacao_maior SET " +
                "participacao_capital_social = COALESCE(?, participacao_capital_social), " +
                "porcento_poder_voto = COALESCE(?, porcento_poder_voto), " +
                "controla_empresas = COALESCE(?, controla_empresas), " +
                "possui_cotacao_em_bolsa = COALESCE(?, possui_cotacao_em_bolsa), " +
                "metodo_valoracao = COALESCE(?, metodo_valoracao), " +
                "result_liq_itens_nao_recorrentes = COALESCE(?, result_liq_itens_nao_recorrentes), " +
                "lucro_distribuido = COALESCE(?, lucro_distribuido), " +
                "data_criacao = ?, chave = ?, diretoria = 'UPE' " +
                "WHERE id_empresa = ? AND trimestre = ?";
                
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); 
            pst = connection.prepareStatement(sql);

            for (Ficha11Maior ficha : fichas) {
                pst.setObject(1, ficha.getPorcentoParticipacaoCapital(), java.sql.Types.DOUBLE);
                pst.setObject(2, ficha.getPorcentoPoderVoto(), java.sql.Types.DOUBLE);
                pst.setObject(3, ficha.getControlaEmpresa(), java.sql.Types.BOOLEAN);
                pst.setObject(4, ficha.getPossuiCotacaoEmBolsa(), java.sql.Types.BOOLEAN);
                
                String metodo = ficha.getMetodoValoracao();
                if (metodo != null && (metodo.trim().isEmpty() || metodo.equalsIgnoreCase("Não informado"))) metodo = null;
                pst.setString(5, metodo);
                
                pst.setObject(6, ficha.getResultadoLiquidoItensNaoRecorrentes(), java.sql.Types.DOUBLE);
                pst.setObject(7, ficha.getLucroDistribuido(), java.sql.Types.DOUBLE);
                pst.setDate(8, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setString(9, ficha.getFuncionario().getChave());
                pst.setInt(10, ficha.getEmpresa().getId());
                pst.setInt(11, ficha.getTrimestre()); 
                pst.addBatch();
            }
            pst.executeBatch(); 
            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) try { connection.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        Ficha11EmpresaController ficha11EmpresaController = new Ficha11EmpresaController();
        ficha11EmpresaController.deleteAllEmpresasByControladoraId(id);
        String sql = "DELETE FROM ficha11_participacao_maior WHERE id = ?";
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

    public static List<Ficha11Maior> getAllFichas() {
        String query = "SELECT ficha11_participacao_maior.*, empresa.* FROM ficha11_participacao_maior LEFT JOIN empresa ON ficha11_participacao_maior.id_empresa = empresa.id_empresa WHERE ficha11_participacao_maior.id IS NOT NULL ORDER BY nome_empresa";
        List<Ficha11Maior> listaFichas = new ArrayList<Ficha11Maior>();
        Connection connection = null;
        Ficha11Maior ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        EmpresaController empresaController = new EmpresaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Maior();
                ficha.setId(rs.getInt("id"));
                
                boolean possuiCotacao = rs.getBoolean("possui_cotacao_em_bolsa");
                ficha.setPossuiCotacaoEmBolsa(rs.wasNull() ? null : possuiCotacao);
                
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                
                double valEmpresa = rs.getDouble("valor_empresa");
                ficha.setValorEmpresa(rs.wasNull() ? null : valEmpresa);
                
                double patTotal = rs.getDouble("patrimonio_total");
                ficha.setPatrimonioTotal(rs.wasNull() ? null : patTotal);
                
                double pctCap = rs.getDouble("participacao_capital_social");
                ficha.setPorcentoParticipacaoCapital(rs.wasNull() ? null : pctCap);
                
                double pctVoto = rs.getDouble("porcento_poder_voto");
                ficha.setPorcentoPoderVoto(rs.wasNull() ? null : pctVoto);
                
                double ativo = rs.getDouble("ativo_database");
                ficha.setAtivoDatabase(rs.wasNull() ? null : ativo);
                
                double passivo = rs.getDouble("passivo_exigivel");
                ficha.setPassivoExigivel(rs.wasNull() ? null : passivo);
                
                double lucro = rs.getDouble("valor_total_lucro_preju_liquido");
                ficha.setValorTotalLucroPrejuizo(rs.wasNull() ? null : lucro);
                
                double resNaoRec = rs.getDouble("result_liq_itens_nao_recorrentes");
                ficha.setResultadoLiquidoItensNaoRecorrentes(rs.wasNull() ? null : resNaoRec);
                
                double resReav = rs.getDouble("result_liq_reavaliacoes");
                ficha.setResultadoLiquidoReavaliacoes(rs.wasNull() ? null : resReav);
                
                double resCamb = rs.getDouble("result_liq_variacao_cambial");
                ficha.setResultadoLiquidoVariacaoCambial(rs.wasNull() ? null : resCamb);
                
                double lucroDist = rs.getDouble("lucro_distribuido");
                ficha.setLucroDistribuido(rs.wasNull() ? null : lucroDist);
                
                boolean controla = rs.getBoolean("controla_empresas");
                ficha.setControlaEmpresa(rs.wasNull() ? null : controla);
                
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
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

    public static List<Ficha11Maior> getAllFichasByTrimestreAno(int trimestre, int ano) {
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
        String query = "SELECT ficha11_participacao_maior.*, empresa.* FROM ficha11_participacao_maior "
                + "LEFT JOIN empresa ON ficha11_participacao_maior.id_empresa = empresa.id_empresa "
                + "WHERE ficha11_participacao_maior.id IS NOT NULL "
                + "AND trimestre = '" + trimestre + "' "
                + "AND YEAR(data_criacao) = '" + ano + "' "
                + "ORDER BY nome_empresa";
        List<Ficha11Maior> listaFichas = new ArrayList<Ficha11Maior>();
        Connection connection = null;
        Ficha11Maior ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        EmpresaController empresaController = new EmpresaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ficha = new Ficha11Maior();
                ficha.setId(rs.getInt("id"));
                
                boolean possuiCotacao = rs.getBoolean("possui_cotacao_em_bolsa");
                ficha.setPossuiCotacaoEmBolsa(rs.wasNull() ? null : possuiCotacao);
                
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                
                double valEmpresa = rs.getDouble("valor_empresa");
                ficha.setValorEmpresa(rs.wasNull() ? null : valEmpresa);
                
                double patTotal = rs.getDouble("patrimonio_total");
                ficha.setPatrimonioTotal(rs.wasNull() ? null : patTotal);
                
                double pctCap = rs.getDouble("participacao_capital_social");
                ficha.setPorcentoParticipacaoCapital(rs.wasNull() ? null : pctCap);
                
                double pctVoto = rs.getDouble("porcento_poder_voto");
                ficha.setPorcentoPoderVoto(rs.wasNull() ? null : pctVoto);
                
                double ativo = rs.getDouble("ativo_database");
                ficha.setAtivoDatabase(rs.wasNull() ? null : ativo);
                
                double passivo = rs.getDouble("passivo_exigivel");
                ficha.setPassivoExigivel(rs.wasNull() ? null : passivo);
                
                double lucro = rs.getDouble("valor_total_lucro_preju_liquido");
                ficha.setValorTotalLucroPrejuizo(rs.wasNull() ? null : lucro);
                
                double resNaoRec = rs.getDouble("result_liq_itens_nao_recorrentes");
                ficha.setResultadoLiquidoItensNaoRecorrentes(rs.wasNull() ? null : resNaoRec);
                
                double resReav = rs.getDouble("result_liq_reavaliacoes");
                ficha.setResultadoLiquidoReavaliacoes(rs.wasNull() ? null : resReav);
                
                double resCamb = rs.getDouble("result_liq_variacao_cambial");
                ficha.setResultadoLiquidoVariacaoCambial(rs.wasNull() ? null : resCamb);
                
                double lucroDist = rs.getDouble("lucro_distribuido");
                ficha.setLucroDistribuido(rs.wasNull() ? null : lucroDist);
                
                boolean controla = rs.getBoolean("controla_empresas");
                ficha.setControlaEmpresa(rs.wasNull() ? null : controla);
                
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
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

    public static Optional<Ficha11Maior> getFichaById(int id) {
        String query = "SELECT * FROM ficha11_participacao_maior WHERE id = " + id;
        Connection connection = null;
        Ficha11Maior ficha = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        MoedaController moedaController = new MoedaController();
        EmpresaController empresaController = new EmpresaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                ficha = new Ficha11Maior();
                ficha.setId(rs.getInt("id"));
                
                boolean possuiCotacao = rs.getBoolean("possui_cotacao_em_bolsa");
                ficha.setPossuiCotacaoEmBolsa(rs.wasNull() ? null : possuiCotacao);
                
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                
                double valEmpresa = rs.getDouble("valor_empresa");
                ficha.setValorEmpresa(rs.wasNull() ? null : valEmpresa);
                
                double patTotal = rs.getDouble("patrimonio_total");
                ficha.setPatrimonioTotal(rs.wasNull() ? null : patTotal);
                
                double pctCap = rs.getDouble("participacao_capital_social");
                ficha.setPorcentoParticipacaoCapital(rs.wasNull() ? null : pctCap);
                
                double pctVoto = rs.getDouble("porcento_poder_voto");
                ficha.setPorcentoPoderVoto(rs.wasNull() ? null : pctVoto);
                
                double ativo = rs.getDouble("ativo_database");
                ficha.setAtivoDatabase(rs.wasNull() ? null : ativo);
                
                double passivo = rs.getDouble("passivo_exigivel");
                ficha.setPassivoExigivel(rs.wasNull() ? null : passivo);
                
                double lucro = rs.getDouble("valor_total_lucro_preju_liquido");
                ficha.setValorTotalLucroPrejuizo(rs.wasNull() ? null : lucro);
                
                double resNaoRec = rs.getDouble("result_liq_itens_nao_recorrentes");
                ficha.setResultadoLiquidoItensNaoRecorrentes(rs.wasNull() ? null : resNaoRec);
                
                double resReav = rs.getDouble("result_liq_reavaliacoes");
                ficha.setResultadoLiquidoReavaliacoes(rs.wasNull() ? null : resReav);
                
                double resCamb = rs.getDouble("result_liq_variacao_cambial");
                ficha.setResultadoLiquidoVariacaoCambial(rs.wasNull() ? null : resCamb);
                
                double lucroDist = rs.getDouble("lucro_distribuido");
                ficha.setLucroDistribuido(rs.wasNull() ? null : lucroDist);
                
                boolean controla = rs.getBoolean("controla_empresas");
                ficha.setControlaEmpresa(rs.wasNull() ? null : controla);
                
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                ficha.setDataCriacao(rs.getDate("data_criacao"));
                ficha.setTrimestre(rs.getInt("trimestre"));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha11_participacao_maior SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
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

    public static int getIdIncrementado() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement("SHOW TABLE STATUS LIKE 'ficha11_participacao_maior'");
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("Auto_increment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
            return id;
        }
    }
    
    public static boolean empresaExiste(int id, int tri, int anoFicha){
        String query = "SELECT data_criacao FROM ficha11_participacao_maior WHERE id_empresa = ? AND trimestre = ?";
    //    String queryAno = "SELECT data_criacao FROM ficha11_participacao_maior WHERE id_empresa = ?";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ResultSet rsAno = null;
        PreparedStatement pstAno = null;
        List<String> datas = new ArrayList<>();
        try {
    //          PEGANDO OS ANOS DAS EMPRESAS COM O ID PASSADO  
                connection = Conexao.conectar();
                pst = connection.prepareStatement(query);
                pst.setInt(1, id);
                pst.setInt(2, tri);
                rs = pst.executeQuery();
                while (rs.next()){
                    String dataCriacao = rs.getString("data_criacao");
                    datas.add(dataCriacao);
                }
                for (String data : datas){
                    int anoData = Integer.parseInt(data.split("-")[0]);
                    System.out.println("TESTE ANO DA FICHA: " + anoFicha);
                    System.out.println("TESTE ANO DA BASE:" + anoData);
                    if (anoData == anoFicha){
                        System.out.println("EMPRESA JÁ ESTÁ REGISTRADA NO MESMO TRIMESTRE E ANO");
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
    
    public static double getSomaPatrimonioPonderado(int trimestreRef, int anoRef, int idDependencia) {
        double totalBrl = 0.0;
        
        // 1. Lógica do Trimestre (Mantida da última versão correta)
        int triBusca = trimestreRef + 1;
        int anoBusca = anoRef;
        
        if (triBusca > 4) {
            triBusca = 1;
            anoBusca = anoRef + 1;
        }
        
        // 2. SQL COM FILTRO DE DIRETORIA
        // Adicionamos: AND (f.diretoria IS NULL OR f.diretoria <> 'UPE')
        // Isso remove a UPE da soma, mantendo o resto.
        
        String sql = "SELECT f.patrimonio_total, f.participacao_capital_social, f.id_moeda " + 
                     "FROM ficha11_participacao_maior f " + 
                     "INNER JOIN funcionario func ON f.chave = func.chave " +
                     "WHERE f.trimestre = ? " +
                     "  AND YEAR(f.data_criacao) = ? " +
                     "  AND func.id_dependencia = ? " +
                     "  AND (f.diretoria IS NULL OR f.diretoria <> 'UPE')"; 

        System.out.println(">>> [DEBUG F11.2] Buscando T" + triBusca + "/" + anoBusca + 
                           " (Ref T" + trimestreRef + ") - Excluindo UPE");

        try (Connection con = Conexao.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, triBusca);
            pst.setInt(2, anoBusca);
            pst.setInt(3, idDependencia); // 9568

            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                double patrimonio = rs.getDouble("patrimonio_total");
                double participacaoPct = rs.getDouble("participacao_capital_social");
                int idMoeda = rs.getInt("id_moeda");
                
                // Conversão PTAX (Usa Tri de Referência)
                double taxaMoeda = PtaxDAO.getTaxaCompra(idMoeda, trimestreRef, anoRef);
                
                double patrimonioReais = patrimonio * taxaMoeda;
                double valorFinalLinha = patrimonioReais * (participacaoPct / 100.0);
                
                totalBrl += valorFinalLinha;
            }
            
            System.out.println(">>> [F11.2] Total Final (Sem UPE): R$ " + totalBrl);
            return totalBrl;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    public static double getSomaPatrimonioPonderadoUPE(int trimestreRef, int anoRef, int idDependencia) {
        double totalBrl = 0.0;
        
        // 1. Lógica do Trimestre de Busca (Mesma da 11.2)
        int triBusca = trimestreRef + 1;
        int anoBusca = anoRef;
        
        if (triBusca > 4) {
            triBusca = 1;
            anoBusca = anoRef + 1;
        }
        
        // 2. SQL FILTRANDO APENAS UPE
        // Diferença aqui: AND f.diretoria = 'UPE'
        
        String sql = "SELECT f.patrimonio_total, f.participacao_capital_social, f.id_moeda " + 
                     "FROM ficha11_participacao_maior f " + 
                     "INNER JOIN funcionario func ON f.chave = func.chave " +
                     "WHERE f.trimestre = ? " +
                     "  AND YEAR(f.data_criacao) = ? " +
                     "  AND func.id_dependencia = ? " +
                     "  AND f.diretoria = 'UPE'"; 

        System.out.println(">>> [DEBUG F11.4 UPE] Buscando T" + triBusca + "/" + anoBusca + 
                           " (Ref T" + trimestreRef + ") - Somente UPE");

        try (Connection con = Conexao.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, triBusca);
            pst.setInt(2, anoBusca);
            pst.setInt(3, idDependencia); // 9568

            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                double patrimonio = rs.getDouble("patrimonio_total");
                double participacaoPct = rs.getDouble("participacao_capital_social");
                int idMoeda = rs.getInt("id_moeda");
                
                // Conversão PTAX (Usa Tri de Referência)
                double taxaMoeda = PtaxDAO.getTaxaCompra(idMoeda, trimestreRef, anoRef);
                
                double patrimonioReais = patrimonio * taxaMoeda;
                double valorFinalLinha = patrimonioReais * (participacaoPct / 100.0);
                
                totalBrl += valorFinalLinha;
            }
            
            System.out.println(">>> [F11.4 UPE] Total Final: R$ " + totalBrl);
            return totalBrl;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    public static boolean verificarNecessidadeJustificativa(double valorConvertido, int trimestre, int ano) {
        // Lógica similar à Ficha 16/11 Menor. 
        // Normalmente para Ficha 11 Maior (Dependências), compara-se com as contas de Investimento (11.2, 11.3)
        // Se precisar de lógica específica por COSIF, ajuste aqui.
        return false; // Por padrão falso, implemente a query do consolidado se necessário.
    }
    
    public static void sincronizarValoresCogerParaUpe(int idEmpresa, int trimestre, int ano) {
        String sql = "UPDATE ficha11_participacao_maior upe " +
                     "JOIN ficha11_participacao_maior coger " +
                     "  ON upe.id_empresa = coger.id_empresa " +
                     " AND upe.trimestre = coger.trimestre " +
                     " AND YEAR(upe.data_criacao) = YEAR(coger.data_criacao) " +
                     "SET " +
                     "  upe.valor_empresa = IF(upe.valor_empresa IS NULL OR upe.valor_empresa = -0.01 OR upe.valor_empresa = 0, coger.valor_empresa, upe.valor_empresa), " +
                     "  upe.patrimonio_total = IF(upe.patrimonio_total IS NULL OR upe.patrimonio_total = -0.01 OR upe.patrimonio_total = 0, coger.patrimonio_total, upe.patrimonio_total), " +
                     "  upe.ativo_database = IF(upe.ativo_database IS NULL OR upe.ativo_database = -0.01 OR upe.ativo_database = 0, coger.ativo_database, upe.ativo_database), " +
                     "  upe.passivo_exigivel = IF(upe.passivo_exigivel IS NULL OR upe.passivo_exigivel = -0.01 OR upe.passivo_exigivel = 0, coger.passivo_exigivel, upe.passivo_exigivel), " +
                     "  upe.valor_total_lucro_preju_liquido = IF(upe.valor_total_lucro_preju_liquido IS NULL OR upe.valor_total_lucro_preju_liquido = -0.01 OR upe.valor_total_lucro_preju_liquido = 0, coger.valor_total_lucro_preju_liquido, upe.valor_total_lucro_preju_liquido), " +
                     "  upe.result_liq_itens_nao_recorrentes = IF(upe.result_liq_itens_nao_recorrentes IS NULL OR upe.result_liq_itens_nao_recorrentes = -0.01 OR upe.result_liq_itens_nao_recorrentes = 0, coger.result_liq_itens_nao_recorrentes, upe.result_liq_itens_nao_recorrentes), " +
                     "  upe.result_liq_reavaliacoes = IF(upe.result_liq_reavaliacoes IS NULL OR upe.result_liq_reavaliacoes = -0.01 OR upe.result_liq_reavaliacoes = 0, coger.result_liq_reavaliacoes, upe.result_liq_reavaliacoes), " +
                     "  upe.result_liq_variacao_cambial = IF(upe.result_liq_variacao_cambial IS NULL OR upe.result_liq_variacao_cambial = -0.01 OR upe.result_liq_variacao_cambial = 0, coger.result_liq_variacao_cambial, upe.result_liq_variacao_cambial), " +
                     "  upe.lucro_distribuido = IF(upe.lucro_distribuido IS NULL OR upe.lucro_distribuido = -0.01, coger.lucro_distribuido, upe.lucro_distribuido), " +
                     "  upe.participacao_capital_social = IF(upe.participacao_capital_social IS NULL OR upe.participacao_capital_social = -0.01 OR upe.participacao_capital_social = 0, coger.participacao_capital_social, upe.participacao_capital_social), " +
                     "  upe.porcento_poder_voto = IF(upe.porcento_poder_voto IS NULL OR upe.porcento_poder_voto = -0.01 OR upe.porcento_poder_voto = 0, coger.porcento_poder_voto, upe.porcento_poder_voto), " +
                     "  upe.metodo_valoracao = IF(upe.metodo_valoracao IS NULL OR upe.metodo_valoracao = '' OR upe.metodo_valoracao = 'Não informado', coger.metodo_valoracao, upe.metodo_valoracao), " +
                     "  upe.possui_cotacao_em_bolsa = COALESCE(upe.possui_cotacao_em_bolsa, coger.possui_cotacao_em_bolsa), " +
                     "  upe.controla_empresas = COALESCE(upe.controla_empresas, coger.controla_empresas) " +
                     "WHERE upe.diretoria = 'UPE' " +
                     "  AND (coger.diretoria IS NULL OR coger.diretoria != 'UPE') " +
                     "  AND upe.id_empresa = ? " +
                     "  AND upe.trimestre = ? " +
                     "  AND YEAR(upe.data_criacao) = ?";

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idEmpresa);
            pst.setInt(2, trimestre);
            pst.setInt(3, ano);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, null);
        }
    }
    
    public static List<Integer> getAnosExistentes() {
        List<Integer> anos = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(data_criacao) AS ano FROM ficha11_participacao_maior ORDER BY ano DESC";
        try (Connection con = Conexao.conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) { anos.add(rs.getInt("ano")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return anos;
    }

    public static List<Integer> getTrimestresExistentes() {
        List<Integer> trims = new ArrayList<>();
        String sql = "SELECT DISTINCT trimestre FROM ficha11_participacao_maior ORDER BY trimestre DESC";
        try (Connection con = Conexao.conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) { trims.add(rs.getInt("trimestre")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return trims;
    }

    public static List<Ficha11Maior> readComFiltros(String trimestre, String ano, String idEmpresa) {
        List<Ficha11Maior> fichas = new ArrayList<>();
        
        // Instancia os controllers que buscam os objetos relacionados (igual no seu getAllFichas)
        MoedaController moedaController = new MoedaController();
        EmpresaController empresaController = new EmpresaController();
        FuncionarioController funcionarioController = new FuncionarioController();
        StatusController statusController = new StatusController();

        // Monta o SQL dinamicamente baseando-se no que foi preenchido
        StringBuilder sql = new StringBuilder(
            "SELECT f.* FROM ficha11_participacao_maior f " +
            "WHERE f.id IS NOT NULL "
        );

        boolean temTrimestre = (trimestre != null && !trimestre.isEmpty() && !trimestre.equals("todos"));
        boolean temAno = (ano != null && !ano.isEmpty() && !ano.equals("todos"));
        boolean temEmpresa = (idEmpresa != null && !idEmpresa.isEmpty() && !idEmpresa.equals("todos"));

        if (temTrimestre) sql.append(" AND f.trimestre = ? ");
        if (temAno) sql.append(" AND YEAR(f.data_criacao) = ? ");
        if (temEmpresa) sql.append(" AND f.id_empresa = ? ");
        
        sql.append(" ORDER BY f.data_criacao DESC");

        try (Connection con = Conexao.conectar(); PreparedStatement pst = con.prepareStatement(sql.toString())) {
            int p = 1;
            if (temTrimestre) pst.setInt(p++, Integer.parseInt(trimestre));
            if (temAno) pst.setInt(p++, Integer.parseInt(ano));
            if (temEmpresa) pst.setInt(p++, Integer.parseInt(idEmpresa));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Ficha11Maior ficha = new Ficha11Maior();
                    ficha.setId(rs.getInt("id"));
                    
                    boolean possuiCotacao = rs.getBoolean("possui_cotacao_em_bolsa");
                    ficha.setPossuiCotacaoEmBolsa(rs.wasNull() ? null : possuiCotacao);
                    
                    ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                    
                    double valEmpresa = rs.getDouble("valor_empresa");
                    ficha.setValorEmpresa(rs.wasNull() ? null : valEmpresa);
                    
                    double patTotal = rs.getDouble("patrimonio_total");
                    ficha.setPatrimonioTotal(rs.wasNull() ? null : patTotal);
                    
                    double pctCap = rs.getDouble("participacao_capital_social");
                    ficha.setPorcentoParticipacaoCapital(rs.wasNull() ? null : pctCap);
                    
                    double pctVoto = rs.getDouble("porcento_poder_voto");
                    ficha.setPorcentoPoderVoto(rs.wasNull() ? null : pctVoto);
                    
                    double ativo = rs.getDouble("ativo_database");
                    ficha.setAtivoDatabase(rs.wasNull() ? null : ativo);
                    
                    double passivo = rs.getDouble("passivo_exigivel");
                    ficha.setPassivoExigivel(rs.wasNull() ? null : passivo);
                    
                    double lucro = rs.getDouble("valor_total_lucro_preju_liquido");
                    ficha.setValorTotalLucroPrejuizo(rs.wasNull() ? null : lucro);
                    
                    double resNaoRec = rs.getDouble("result_liq_itens_nao_recorrentes");
                    ficha.setResultadoLiquidoItensNaoRecorrentes(rs.wasNull() ? null : resNaoRec);
                    
                    double resReav = rs.getDouble("result_liq_reavaliacoes");
                    ficha.setResultadoLiquidoReavaliacoes(rs.wasNull() ? null : resReav);
                    
                    double resCamb = rs.getDouble("result_liq_variacao_cambial");
                    ficha.setResultadoLiquidoVariacaoCambial(rs.wasNull() ? null : resCamb);
                    
                    double lucroDist = rs.getDouble("lucro_distribuido");
                    ficha.setLucroDistribuido(rs.wasNull() ? null : lucroDist);
                    
                    boolean controla = rs.getBoolean("controla_empresas");
                    ficha.setControlaEmpresa(rs.wasNull() ? null : controla);
                    
                    // Preenche os objetos usando os controllers
                    ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                    ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                    ficha.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                    ficha.setStatus(statusController.getStatusById(rs.getInt("id_status")));
                    
                    ficha.setDataCriacao(rs.getDate("data_criacao"));
                    ficha.setTrimestre(rs.getInt("trimestre"));
                    
                    fichas.add(ficha);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return fichas;
    }
    
}