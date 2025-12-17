package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.conexao.Conexao;
import br.com.bb.cbe.controllers.DependenciaController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    Conexao conexao = new Conexao();

    public static Funcionario getFuncionarioByChave(String chave) {
        Funcionario funcionario = new Funcionario();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT chave, nome, id_dependencia FROM funcionario WHERE chave = '" + chave + "'";
        DependenciaController dependenciaController = new DependenciaController();
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            funcionario.setChave(rs.getString("chave"));
            funcionario.setNome(rs.getString("nome"));
            funcionario.setDependencia(dependenciaController.getDependenciaById(rs.getInt("id_dependencia")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return funcionario;
    }

    public static void criarFuncionario(Funcionario funcionario) {

        String sql = "INSERT INTO funcionario (chave, nome, id_dependencia) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(sql);
            pst.setString(1, funcionario.getChave());
            pst.setString(2, funcionario.getNome());
            pst.setInt(3, funcionario.getDependencia().getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            Conexao.fecharConexao(connection, pst, null);
        }
    }

    public static boolean funcionarioExiste(String chave) {
        String sql = "SELECT * FROM funcionario WHERE chave = '" + chave + "'";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, null);
        }
        return false;
    }

}
