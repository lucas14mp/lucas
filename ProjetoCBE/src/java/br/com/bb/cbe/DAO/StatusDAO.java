package br.com.bb.cbe.DAO;

import br.com.bb.cbe.Bean.Status;
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusDAO {

    public static Status getStatusById(int idStatus) {
        String query = "SELECT * FROM status_ficha WHERE id = " + idStatus;
        Connection connection = null;
        Status status = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            connection = Conexao.conectar();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                status = new Status();
                status.setId(rs.getInt("id"));
                status.setStatus(rs.getString("status"));
                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.fecharConexao(connection, pst, rs);
        }
        return null;
    }

}
