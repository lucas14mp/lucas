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
                
                // 3. Patrimônio Total (Do Excel)
                pst.setDouble(3, ficha.getPatrimonioTotal());
                
                // 4. Participação Capital (Do Excel)
                pst.setDouble(4, ficha.getPorcentoParticipacaoCapital());
                
                // 5. Porcento Poder Voto (Do Excel)
                pst.setDouble(5, ficha.getPorcentoPoderVoto());
                
                // 6. Ativo Data-base (Do Excel)
                pst.setDouble(6, ficha.getAtivoDatabase());
                
                // 7. Passivo Exigível (Do Excel)
                pst.setDouble(7, ficha.getPassivoExigivel());
                
                // 8. Result Liq Itens Não Recorrentes (Do Excel)
                pst.setDouble(8, ficha.getResultadoLiquidoItensNaoRecorrentes());
                
                // 9. Result Liq Reavaliações (Do Excel)
                pst.setDouble(9, ficha.getResultadoLiquidoReavaliacoes());
                
                // 10. Lucro Distribuído (Do Excel)
                pst.setDouble(10, ficha.getLucroDistribuido());
                
                // 11. Controla Empresas (Do Excel)
                pst.setBoolean(11, ficha.isControlaEmpresa()); 
                
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

                // --- CAMPOS NÃO PREENCHIDOS PELO EXCEL (DEFINIR PADRÃO) ---
                
                // 18. Possui Cotação (Padrão: False)
                pst.setBoolean(18, false); 
                
                // 19. Método Valoração (Padrão: "Não Informado")
                pst.setString(19, "Não Informado via Excel"); 
                
                // 20. Valor Empresa (Padrão: 0.0)
                pst.setDouble(20, 0.0);
                
                // 21. Valor Total Lucro/Prejuízo Líquido (Padrão: 0.0)
                pst.setDouble(21, 0.0);
                
                // 22. Resultado Liq Variação Cambial (Padrão: 0.0)
                pst.setDouble(22, 0.0);

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
        System.out.println("ENTROU NO CREATE");
        String sql = "INSERT INTO ficha11_participacao_maior (lucro_distribuido, valor_empresa, patrimonio_total, participacao_capital_social, porcento_poder_voto, ativo_database, passivo_exigivel, valor_total_lucro_preju_liquido, result_liq_reavaliacoes, result_liq_variacao_cambial, controla_empresas, data_criacao, trimestre, id_moeda, chave, id_empresa, id_status, metodo_valoracao) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ficha11Maior ficha : fichas) {
                pst.setDouble(1, ficha.getLucroDistribuido());
                pst.setDouble(2, ficha.getValorEmpresa());
                pst.setDouble(3, ficha.getPatrimonioTotal());
                pst.setDouble(4, ficha.getPorcentoParticipacaoCapital());
                pst.setDouble(5, ficha.getPorcentoPoderVoto());
                pst.setDouble(6, ficha.getAtivoDatabase());
                pst.setDouble(7, ficha.getPassivoExigivel());
                pst.setDouble(8, ficha.getValorTotalLucroPrejuizo());
                pst.setDouble(9, ficha.getResultadoLiquidoReavaliacoes());
                pst.setDouble(10, ficha.getResultadoLiquidoVariacaoCambial());
                pst.setBoolean(11, ficha.isControlaEmpresa());
                pst.setDate(12, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(13, ficha.getTrimestre());
                pst.setInt(14, ficha.getMoeda().getId());
                pst.setString(15, ficha.getFuncionario().getChave());
                pst.setInt(16, ficha.getEmpresa().getId());
                pst.setInt(17, ficha.getStatus().getId());
                pst.setString(18, ficha.getMetodoValoracao());
                pst.addBatch();
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("CRIOU COGER");
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
     
    public static void createBatchUpe(List<Ficha11Maior> fichas) {
        System.out.println("ENTROU NO CREATE");
        String sql = "INSERT INTO ficha11_participacao_maior (id_moeda, participacao_capital_social, porcento_poder_voto, possui_cotacao_em_bolsa, metodo_valoracao, result_liq_itens_nao_recorrentes, lucro_distribuido, controla_empresas, data_criacao, trimestre, chave, id_empresa, id_status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ficha11Maior ficha : fichas) {
                pst.setInt(1, ficha.getMoeda().getId());
                pst.setDouble(2, ficha.getPorcentoParticipacaoCapital());
                pst.setDouble(3, ficha.getPorcentoPoderVoto());
                pst.setBoolean(4, ficha.isPossuiCotacaoEmBolsa());
                pst.setString(5, ficha.getMetodoValoracao());
                pst.setDouble(6, ficha.getResultadoLiquidoItensNaoRecorrentes());
                pst.setDouble(7, ficha.getLucroDistribuido());
                pst.setBoolean(8, ficha.isControlaEmpresa());
                pst.setDate(9, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setInt(10, ficha.getTrimestre());
                pst.setString(11, ficha.getFuncionario().getChave());
                pst.setInt(12, ficha.getEmpresa().getId());
                pst.setInt(13, ficha.getStatus().getId());
                pst.addBatch();
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("CRIOU UPE" );
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

    
    public static void create(Ficha11Maior ficha) {

        String sql = "INSERT INTO ficha11_participacao_maior(possui_cotacao_em_bolsa, metodo_valoracao, valor_empresa, patrimonio_total, participacao_capital_social, porcento_poder_voto, ativo_database, passivo_exigivel, valor_total_lucro_preju_liquido, result_liq_itens_nao_recorrentes, result_liq_reavaliacoes, result_liq_variacao_cambial, lucro_distribuido, controla_empresas, data_criacao, trimestre, id_moeda, chave, id_empresa, id_status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setBoolean(1, ficha.isPossuiCotacaoEmBolsa());
            pst.setString(2, ficha.getMetodoValoracao());
            pst.setDouble(3, ficha.getValorEmpresa());
            pst.setDouble(4, ficha.getPatrimonioTotal());
            pst.setDouble(5, ficha.getPorcentoParticipacaoCapital());
            pst.setDouble(6, ficha.getPorcentoPoderVoto());
            pst.setDouble(7, ficha.getAtivoDatabase());
            pst.setDouble(8, ficha.getPassivoExigivel());
            pst.setDouble(9, ficha.getValorTotalLucroPrejuizo());
            pst.setDouble(10, ficha.getResultadoLiquidoItensNaoRecorrentes());
            pst.setDouble(11, ficha.getResultadoLiquidoReavaliacoes());
            pst.setDouble(12, ficha.getResultadoLiquidoVariacaoCambial());
            pst.setDouble(13, ficha.getLucroDistribuido());
            pst.setBoolean(14, ficha.isControlaEmpresa());
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
            pst.setBoolean(1, ficha.isPossuiCotacaoEmBolsa());
            pst.setString(2, ficha.getMetodoValoracao());
            pst.setDouble(3, ficha.getValorEmpresa());
            pst.setDouble(4, ficha.getPatrimonioTotal());
            pst.setDouble(5, ficha.getPorcentoParticipacaoCapital());
            pst.setDouble(6, ficha.getPorcentoPoderVoto());
            pst.setDouble(7, ficha.getAtivoDatabase());
            pst.setDouble(8, ficha.getPassivoExigivel());
            pst.setDouble(9, ficha.getValorTotalLucroPrejuizo());
            pst.setDouble(10, ficha.getResultadoLiquidoItensNaoRecorrentes());
            pst.setDouble(11, ficha.getResultadoLiquidoReavaliacoes());
            pst.setDouble(12, ficha.getResultadoLiquidoVariacaoCambial());
            pst.setDouble(13, ficha.getLucroDistribuido());
            pst.setBoolean(14, ficha.isControlaEmpresa());
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
        System.out.println("ENTROU NO UPDATE COGER");
        String sql = "UPDATE ficha11_participacao_maior SET lucro_distribuido = ?, valor_empresa = ?, patrimonio_total = ?"+
                ", participacao_capital_social = ?, porcento_poder_voto = ?, ativo_database = ?, passivo_exigivel = ?,"+
                " valor_total_lucro_preju_liquido = ?, result_liq_reavaliacoes = ?, result_liq_variacao_cambial = ?,"+
                " controla_empresas = ?, id_moeda = ?, chave = ?, data_criacao = ?, metodo_valoracao = ? WHERE id_empresa = ? AND trimestre = ? AND data_criacao = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        
        String queryAno = "SELECT data_criacao FROM ficha11_participacao_maior WHERE id_empresa = ? AND trimestre = ?";
        ResultSet rsAno = null;
        PreparedStatement pstAno = null;
        List<String> datas = new ArrayList<>();
        Date dataFinal = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            
            for (Ficha11Maior ficha : fichas) {
                SimpleDateFormat anoFormat = new SimpleDateFormat("yyyy");
                int anoFicha = Integer.parseInt(anoFormat.format(ficha.getDataCriacao()));
                connection = Conexao.conectar();
                pstAno = connection.prepareStatement(queryAno);
                pstAno.setInt(1, ficha.getEmpresa().getId());
                pstAno.setInt(2, ficha.getTrimestre());
                rsAno = pstAno.executeQuery();
                while (rsAno.next()){
                    String dataCriacao = rsAno.getString("data_criacao");
                    datas.add(dataCriacao);
                }
                for (String data : datas){
                    System.out.println("DATA: " + data);
                    int anoData = Integer.parseInt(data.split("-")[0]);

                    if (anoData == anoFicha){
                        connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
                        pst = connection.prepareStatement(sql);
                        try {
                            // Converte a String para um objeto Date
                            dataFinal = formato.parse(data);
                            System.out.println("Data convertida: " + data);
                        } catch (ParseException e) {
                            e.printStackTrace(); // Tratamento de erro caso a String seja inválida
                        }
                        System.out.println("ATUALIZANDO A EMPRESA " + ficha.getEmpresa().getNome() + " DO ANO " + anoFicha + " E DO TRIMESTRE" + ficha.getTrimestre());
                        pst.setDouble(1, ficha.getLucroDistribuido());
                        pst.setDouble(2, ficha.getValorEmpresa());
                        pst.setDouble(3, ficha.getPatrimonioTotal());
                        pst.setDouble(4, ficha.getPorcentoParticipacaoCapital());
                        pst.setDouble(5, ficha.getPorcentoPoderVoto());
                        pst.setDouble(6, ficha.getAtivoDatabase());
                        pst.setDouble(7, ficha.getPassivoExigivel());
                        pst.setDouble(8, ficha.getValorTotalLucroPrejuizo());
                        pst.setDouble(9, ficha.getResultadoLiquidoReavaliacoes());
                        pst.setDouble(10, ficha.getResultadoLiquidoVariacaoCambial());
                        pst.setBoolean(11, ficha.isControlaEmpresa());
                        pst.setInt(12, ficha.getMoeda().getId());
                        pst.setString(13, ficha.getFuncionario().getChave());
                        pst.setDate(14, new java.sql.Date(ficha.getDataCriacao().getTime()));
                        pst.setString(15, ficha.getMetodoValoracao());
                        pst.setInt(16, ficha.getEmpresa().getId());
                        pst.setInt(17, ficha.getTrimestre());
                        pst.setDate(18, new java.sql.Date(dataFinal.getTime()));
                        pst.addBatch();
                    }
                }
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("ATUALIZOU COGER");
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
    
    public static void updateBatchUpe(List<Ficha11Maior> fichas) {
        System.out.println("ENTROU NO UPDATE UPE");
        String sql = "UPDATE ficha11_participacao_maior SET participacao_capital_social = ?, porcento_poder_voto = ?, controla_empresas = ?, possui_cotacao_em_bolsa = ?, metodo_valoracao = ?, result_liq_itens_nao_recorrentes = ?, lucro_distribuido = ?, data_criacao = ?, chave = ? WHERE id_empresa = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            connection.setAutoCommit(false); // Desabilita o auto-commit para usar transações
            pst = connection.prepareStatement(sql);

            for (Ficha11Maior ficha : fichas) {
                pst.setDouble(1, ficha.getPorcentoParticipacaoCapital());
                pst.setDouble(2, ficha.getPorcentoPoderVoto());
                pst.setBoolean(3, ficha.isControlaEmpresa());
                pst.setBoolean(4, ficha.isPossuiCotacaoEmBolsa());
                pst.setString(5, ficha.getMetodoValoracao());
                pst.setDouble(6, ficha.getResultadoLiquidoItensNaoRecorrentes());
                pst.setDouble(7, ficha.getLucroDistribuido());
                pst.setDate(8, new java.sql.Date(ficha.getDataCriacao().getTime()));
                pst.setString(9, ficha.getFuncionario().getChave());
                pst.setInt(10, ficha.getEmpresa().getId());
                pst.addBatch();
            }
            pst.executeBatch(); // Executa todas as inserções em lote
            connection.commit(); // Confirma a transação
            System.out.println("ATUALIZOU UPE");
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
                ficha.setPossuiCotacaoEmBolsa(rs.getBoolean("possui_cotacao_em_bolsa"));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorEmpresa(rs.getDouble("valor_empresa"));
                ficha.setPatrimonioTotal(rs.getDouble("patrimonio_total"));
                ficha.setPorcentoParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setPorcentoPoderVoto(rs.getDouble("porcento_poder_voto"));
                ficha.setAtivoDatabase(rs.getDouble("ativo_database"));
                ficha.setPassivoExigivel(rs.getDouble("passivo_exigivel"));
                ficha.setValorTotalLucroPrejuizo(rs.getDouble("valor_total_lucro_preju_liquido"));
                ficha.setResultadoLiquidoItensNaoRecorrentes(rs.getDouble("result_liq_itens_nao_recorrentes"));
                ficha.setResultadoLiquidoReavaliacoes(rs.getDouble("result_liq_reavaliacoes"));
                ficha.setResultadoLiquidoVariacaoCambial(rs.getDouble("result_liq_variacao_cambial"));
                ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                ficha.setControlaEmpresa(rs.getBoolean("controla_empresas"));
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
                ficha.setPossuiCotacaoEmBolsa(rs.getBoolean("possui_cotacao_em_bolsa"));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorEmpresa(rs.getDouble("valor_empresa"));
                ficha.setPatrimonioTotal(rs.getDouble("patrimonio_total"));
                ficha.setPorcentoParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setPorcentoPoderVoto(rs.getDouble("porcento_poder_voto"));
                ficha.setAtivoDatabase(rs.getDouble("ativo_database"));
                ficha.setPassivoExigivel(rs.getDouble("passivo_exigivel"));
                ficha.setValorTotalLucroPrejuizo(rs.getDouble("valor_total_lucro_preju_liquido"));
                ficha.setResultadoLiquidoItensNaoRecorrentes(rs.getDouble("result_liq_itens_nao_recorrentes"));
                ficha.setResultadoLiquidoReavaliacoes(rs.getDouble("result_liq_reavaliacoes"));
                ficha.setResultadoLiquidoVariacaoCambial(rs.getDouble("result_liq_variacao_cambial"));
                ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                ficha.setControlaEmpresa(rs.getBoolean("controla_empresas"));
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
                ficha.setPossuiCotacaoEmBolsa(rs.getBoolean("possui_cotacao_em_bolsa"));
                ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                ficha.setValorEmpresa(rs.getDouble("valor_empresa"));
                ficha.setPatrimonioTotal(rs.getDouble("patrimonio_total"));
                ficha.setPorcentoParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                ficha.setPorcentoPoderVoto(rs.getDouble("porcento_poder_voto"));
                ficha.setAtivoDatabase(rs.getDouble("ativo_database"));
                ficha.setPassivoExigivel(rs.getDouble("passivo_exigivel"));
                ficha.setValorTotalLucroPrejuizo(rs.getDouble("valor_total_lucro_preju_liquido"));
                ficha.setResultadoLiquidoItensNaoRecorrentes(rs.getDouble("result_liq_itens_nao_recorrentes"));
                ficha.setResultadoLiquidoReavaliacoes(rs.getDouble("result_liq_reavaliacoes"));
                ficha.setResultadoLiquidoVariacaoCambial(rs.getDouble("result_liq_variacao_cambial"));
                ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                ficha.setControlaEmpresa(rs.getBoolean("controla_empresas"));
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
        // Copia os dados financeiros exatos do registro da COGER para o da UPE
        String sql = "UPDATE ficha11_participacao_maior upe " +
                     "JOIN ficha11_participacao_maior coger " +
                     "  ON upe.id_empresa = coger.id_empresa " +
                     " AND upe.trimestre = coger.trimestre " +
                     " AND YEAR(upe.data_criacao) = YEAR(coger.data_criacao) " +
                     "SET " +
                     "  upe.valor_empresa = coger.valor_empresa, " +
                     "  upe.patrimonio_total = coger.patrimonio_total, " +
                     "  upe.ativo_database = coger.ativo_database, " +
                     "  upe.passivo_exigivel = coger.passivo_exigivel, " +
                     "  upe.valor_total_lucro_preju_liquido = coger.valor_total_lucro_preju_liquido, " +
                     "  upe.result_liq_itens_nao_recorrentes = coger.result_liq_itens_nao_recorrentes, " +
                     "  upe.result_liq_reavaliacoes = coger.result_liq_reavaliacoes, " +
                     "  upe.result_liq_variacao_cambial = coger.result_liq_variacao_cambial, " +
                     "  upe.lucro_distribuido = coger.lucro_distribuido " +
                     "WHERE upe.diretoria = 'UPE' " +
                     "  AND coger.diretoria = 'COGER' " +
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
            int qtdeAlterada = pst.executeUpdate();
            
            if (qtdeAlterada > 0) {
                System.out.println(">>> SINCRONIZADO: Valores financeiros da COGER injetados na ficha da UPE (Empresa ID: " + idEmpresa + ")");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao sincronizar COGER -> UPE: " + e.getMessage());
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
                    ficha.setPossuiCotacaoEmBolsa(rs.getBoolean("possui_cotacao_em_bolsa"));
                    ficha.setMetodoValoracao(rs.getString("metodo_valoracao"));
                    ficha.setValorEmpresa(rs.getDouble("valor_empresa"));
                    ficha.setPatrimonioTotal(rs.getDouble("patrimonio_total"));
                    ficha.setPorcentoParticipacaoCapital(rs.getDouble("participacao_capital_social"));
                    ficha.setPorcentoPoderVoto(rs.getDouble("porcento_poder_voto"));
                    ficha.setAtivoDatabase(rs.getDouble("ativo_database"));
                    ficha.setPassivoExigivel(rs.getDouble("passivo_exigivel"));
                    ficha.setValorTotalLucroPrejuizo(rs.getDouble("valor_total_lucro_preju_liquido"));
                    ficha.setResultadoLiquidoItensNaoRecorrentes(rs.getDouble("result_liq_itens_nao_recorrentes"));
                    ficha.setResultadoLiquidoReavaliacoes(rs.getDouble("result_liq_reavaliacoes"));
                    ficha.setResultadoLiquidoVariacaoCambial(rs.getDouble("result_liq_variacao_cambial"));
                    ficha.setLucroDistribuido(rs.getDouble("lucro_distribuido"));
                    ficha.setControlaEmpresa(rs.getBoolean("controla_empresas"));
                    
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