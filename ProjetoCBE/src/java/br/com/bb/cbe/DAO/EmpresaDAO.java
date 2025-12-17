package br.com.bb.cbe.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.bb.cbe.conexao.*;
import br.com.bb.cbe.Bean.Empresa;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.PaisController;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaDAO {

    public static void create(Empresa empresa) {

        String sql = "INSERT INTO empresa (id_pais, nome_empresa, empresa_transaciona, cdnr, relacao_declarante, numero_empregados, atividade_economica, detalhe_atvdd_econo, chave) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, empresa.getPais().getId());
            pst.setString(2, empresa.getNome());
            pst.setBoolean(3, empresa.isTransacionaPaisesDiferentes());
            pst.setInt(4, empresa.getCdnr());
            pst.setString(5, empresa.getRelacaoDeclarante());
            pst.setInt(6, empresa.getNumeroEmpregados());
            pst.setString(7, empresa.getAtividadeEconomica());
            pst.setString(8, empresa.getDetalhamentoAtividadeEconomica());
            pst.setString(9, empresa.getFuncionario().getChave());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Empresa empresa) {

        String sql = "UPDATE empresa SET id_pais = ?, nome_empresa = ?, empresa_transaciona = ?, cdnr = ?, relacao_declarante = ?, numero_empregados = ?, atividade_economica = ?, detalhe_atvdd_econo = ? WHERE id_empresa = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, empresa.getPais().getId());
            pst.setString(2, empresa.getNome());
            pst.setBoolean(3, empresa.isTransacionaPaisesDiferentes());
            pst.setInt(4, empresa.getCdnr());
            pst.setString(5, empresa.getRelacaoDeclarante());
            pst.setInt(6, empresa.getNumeroEmpregados());
            pst.setString(7, empresa.getAtividadeEconomica());
            pst.setString(8, empresa.getDetalhamentoAtividadeEconomica());
            pst.setInt(9, empresa.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM empresa WHERE id_empresa = ?";
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

//    public static Empresa getEmpresaById(int id) {
//        Empresa empresa = new Empresa();
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//        String query = "SELECT id_empresa, nome_empresa FROM empresa WHERE id_empresa = " + id;
//
//        try {
//            conn = Conexao.conectar();
//            pst = conn.prepareStatement(query);
//            rs = pst.executeQuery();
//            rs.next();
//            empresa.setId(rs.getInt("id_empresa"));
//            empresa.setNome(rs.getString("nome_empresa"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            Conexao.fecharConexao(conn, pst, rs);
//        }
//        return empresa;
//    }
//    
    
    public static Empresa getEmpresaById(int id) {
        String query = "SELECT * FROM empresa WHERE id_empresa = " + id;
        Empresa empresa = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id_empresa"));
                empresa.setNome(rs.getString("nome_empresa"));
                empresa.setTransacionaPaisesDiferentes(rs.getBoolean("empresa_transaciona"));
                empresa.setCdnr(rs.getInt("cdnr"));
                empresa.setRelacaoDeclarante(rs.getString("relacao_declarante"));
                empresa.setNumeroEmpregados(rs.getInt("numero_empregados"));
                empresa.setAtividadeEconomica(rs.getString("atividade_economica"));
                empresa.setDetalhamentoAtividadeEconomica(rs.getString("detalhe_atvdd_econo"));
                empresa.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                empresa.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return empresa;
    }

    public static List<Empresa> getAllEmpresas() {

        String query = "SELECT empresa.*, pais.* FROM empresa LEFT JOIN pais ON empresa.id_pais = pais.id_pais WHERE id_empresa IS NOT NULL ORDER BY nome, nome_empresa;";
        List<Empresa> listaEmpresas = new ArrayList<>();
        Connection connection = null;
        Empresa empresa = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        PaisController paisController = new PaisController();
        FuncionarioController funcionarioController = new FuncionarioController();

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id_empresa"));
                empresa.setNome(rs.getString("nome_empresa"));
                empresa.setTransacionaPaisesDiferentes(rs.getBoolean("empresa_transaciona"));
                empresa.setCdnr(rs.getInt("cdnr"));
                empresa.setRelacaoDeclarante(rs.getString("relacao_declarante"));
                empresa.setNumeroEmpregados(rs.getInt("numero_empregados"));
                empresa.setAtividadeEconomica(rs.getString("atividade_economica"));
                empresa.setDetalhamentoAtividadeEconomica(rs.getString("detalhe_atvdd_econo"));
                empresa.setPais(paisController.getPaisById(rs.getInt("id_pais")));
                empresa.setFuncionario(funcionarioController.getFuncionarioByChave(rs.getString("chave")));
                listaEmpresas.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return listaEmpresas;
    }
    public static Empresa getEmpresaByNome(String nome) {
        Empresa empresa;
        int id;
                switch (nome) {
                    case "BB Américas":
                        id = 54;
                        empresa = getEmpresaById(id);
                        return empresa;
                    case "Banco Patagonia":
                        id = 58;
                        empresa = getEmpresaById(id);
                        return empresa;
                    case "BB AG Viena":
                        id = 63;
                        empresa = getEmpresaById(id);       
                        return empresa;
                    case "BB Cayman Islands Holding":
                        id = 37;
                        empresa = getEmpresaById(id);
                        return empresa;
                    case "BB Assunção":
                        id = 45;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Nova Iorque":
                        id = 46;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Frankfurt":
                        id = 47;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Londres":
                        id = 48;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Tóquio":
                        id = 49;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Miami":
                        id = 51;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Xangai":
                        id = 52;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Banco do Brasil Americas Inc. (BB Americas)":
                        id = 54;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Banco do Brasil Securities LLC":
                        id = 55;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB USA Holding Company":
                        id = 56;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Securities Limited":
                        id = 57;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Banco Patagonia S.A.":
                        id = 58;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Banco Patagonia (Uruguay) S.A.I.F.E.":
                        id = 59;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "GPAT Compañía Financiera S.A.":
                        id = 60;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Patagonia Inversora S.A. Sociedad Gerente Fondos Comunes de Inversión":
                        id = 61;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Patagonia Valores S.A. Sociedad de Bolsa":
                        id = 62;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "Banco do Brasil Aktiengesellschaft (BB AG)":
                        id = 63;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Buenos Aires":
                        id = 64;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Santa Cruz de La Sierra":
                        id = 65;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB Grand Cayman":
                        id = 66;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    case "BB USA Holding Company, Inc.":
                        id = 56;
                        empresa = getEmpresaById(id);
                        return empresa; 
                    default:
                        System.out.println("Nome não encontrado.");
                        return null;
            }
    }
}
