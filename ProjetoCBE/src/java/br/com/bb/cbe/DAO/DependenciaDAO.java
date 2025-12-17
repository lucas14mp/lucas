
package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Dependencia;
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DependenciaDAO {

    Conexao conexao = new Conexao();

    public static Dependencia getDependenciaById(int id) {
        Dependencia dependencia = new Dependencia();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT id, nome FROM Dependencia WHERE id = " + id;

        try {
            conn = Conexao.conectar();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            dependencia.setId(rs.getInt("id"));
            dependencia.setNome(rs.getString("nome"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(conn, pst, rs);
        }
        return dependencia;
    }
    
}

