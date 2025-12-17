package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Ficha14Maior;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.conexao.Conexao;
import br.com.bb.cbe.controllers.EmpresaController;
import br.com.bb.cbe.controllers.Ficha14EmpresaController;
import br.com.bb.cbe.controllers.FuncionarioController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.StatusController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ficha14MaiorDAO {

    public static void create(Ficha14Maior ficha) {

        String sql = "INSERT INTO ficha14_participacao_maior (patrimonio_liquido, participacao_patrimonio, rendimentos_fundo, rendimentos_distribuidos, controla_empresas, data_criacao, trimestre, id_moeda, chave, id_empresa, id_status) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        Connection connection = null;

        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getPatrimonioLiquido());
            pst.setDouble(2, ficha.getParticipacaoPatrimonio());
            pst.setDouble(3, ficha.getRendimentosFundo());
            pst.setDouble(4, ficha.getRendimentosDistribuidos());
            pst.setBoolean(5, ficha.isControlaEmpresas());
            pst.setDate(6, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(7, DataUtils.validaTrimestre());
            pst.setInt(8, ficha.getMoeda().getId());
            pst.setString(9, ficha.getFuncionario().getChave());
            pst.setInt(10, ficha.getEmpresa().getId());
            pst.setInt(11, ficha.getStatus().getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void update(Ficha14Maior ficha) {

        String sql = "UPDATE ficha14_participacao_maior SET patrimonio_liquido = ?, participacao_patrimonio = ?, rendimentos_fundo = ?, rendimentos_distribuidos = ?, controla_empresas = ?, id_moeda = ?, id_empresa = ?, chave = ?, data_criacao = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setDouble(1, ficha.getPatrimonioLiquido());
            pst.setDouble(2, ficha.getParticipacaoPatrimonio());
            pst.setDouble(3, ficha.getRendimentosFundo());
            pst.setDouble(4, ficha.getRendimentosDistribuidos());
            pst.setBoolean(5, ficha.isControlaEmpresas());
            pst.setInt(6, ficha.getMoeda().getId());
            pst.setInt(7, ficha.getEmpresa().getId());
            pst.setString(8, ficha.getFuncionario().getChave());
            pst.setDate(9, new java.sql.Date(ficha.getDataCriacao().getTime()));
            pst.setInt(10, ficha.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }

    }

    public static void delete(int id) {
        Ficha14EmpresaController ficha14EmpresaController = new Ficha14EmpresaController();
        ficha14EmpresaController.deleteAllEmpresasByControladoraId(id);
        String sql = "DELETE FROM ficha14_participacao_maior WHERE id = ?";
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

    public static List<Ficha14Maior> getAllFichas() {

        String query = "SELECT * FROM ficha14_participacao_maior";
        List<Ficha14Maior> listaFichas = new ArrayList<Ficha14Maior>();
        Connection connection = null;
        Ficha14Maior ficha = null;
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
                ficha = new Ficha14Maior();
                ficha.setId(rs.getInt("id"));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setParticipacaoPatrimonio(rs.getDouble("participacao_patrimonio"));
                ficha.setRendimentosFundo(rs.getDouble("rendimentos_fundo"));
                ficha.setRendimentosDistribuidos(rs.getDouble("rendimentos_distribuidos"));
                ficha.setControlaEmpresas(rs.getBoolean("controla_empresas"));
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

    public static List<Ficha14Maior> getAllFichasByTrimestreAno(int trimestre, int ano) {
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
        String query = "SELECT * FROM ficha14_participacao_maior WHERE trimestre = " + trimestre + " AND YEAR(data_criacao) = " + ano;        List<Ficha14Maior> listaFichas = new ArrayList<Ficha14Maior>();
        Connection connection = null;
        Ficha14Maior ficha = null;
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
                ficha = new Ficha14Maior();
                ficha.setId(rs.getInt("id"));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                ficha.setMoeda(moedaController.getMoedaById(rs.getInt("id_moeda")));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setParticipacaoPatrimonio(rs.getDouble("participacao_patrimonio"));
                ficha.setRendimentosFundo(rs.getDouble("rendimentos_fundo"));
                ficha.setRendimentosDistribuidos(rs.getDouble("rendimentos_distribuidos"));
                ficha.setControlaEmpresas(rs.getBoolean("controla_empresas"));
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

    public static Optional<Ficha14Maior> getFichaById(int id) {
        String query = "SELECT * FROM ficha14_participacao_maior WHERE id = " + id;
        Connection connection = null;
        Ficha14Maior ficha = null;
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
                ficha = new Ficha14Maior();
                ficha.setId(rs.getInt("id"));
                ficha.setEmpresa(empresaController.getEmpresaById(rs.getInt("id_empresa")));
                ficha.setParticipacaoPatrimonio(rs.getDouble("participacao_patrimonio"));
                ficha.setPatrimonioLiquido(rs.getDouble("patrimonio_liquido"));
                ficha.setRendimentosFundo(rs.getDouble("rendimentos_fundo"));
                ficha.setRendimentosDistribuidos(rs.getDouble("rendimentos_distribuidos"));
                ficha.setControlaEmpresas(rs.getBoolean("controla_empresas"));
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
        StringBuilder sql = new StringBuilder("UPDATE ficha14_participacao_maior SET id_status = 2, chave = '" + chave + "' WHERE id IN (");
        if (!idsValidados.isEmpty()) {
            sql.append(idsValidados.get(0));
            for (int i = 1; i < idsValidados.size(); i++) {
                sql.append(",");
                sql.append(idsValidados.get(i));
            }
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
            pst = conn.prepareStatement("SHOW TABLE STATUS LIKE 'ficha14_participacao_maior'");
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

}