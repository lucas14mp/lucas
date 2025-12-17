package br.com.bb.cbe.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conexao {

    // Configurações do banco de dados local
    // Adicionei os parâmetros de timezone e encoding para evitar erros comuns no MySQL local
    private static final String URL = "jdbc:mysql://localhost:3306/banco_cbe_teste?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&sslMode=disabled";
    private static final String USUARIO = "root";
    private static final String SENHA = "Lucas8mp%";

    public static Connection conectar() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco local (método conectar): " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
    
    public static Connection conectarDbCosif() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Apontando também para o banco local 'poo' para garantir que tudo seja local
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco local (método conectarDbCosif): " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void fecharConexao(Connection conn, PreparedStatement pst, ResultSet rs) {
        if (pst != null) {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}