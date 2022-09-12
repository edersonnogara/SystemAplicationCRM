/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systeminfo.dal;

import java.sql.*;

/**
 *
 * @author Nutrizon
 */
public class ModuloConexao {
//metodo responsavel pela conexao com o banco

    public static Connection conector() {
        java.sql.Connection conexao = null;
        // a linha abaixo chama o driver 
        String driver = "com.mysql.jdbc.Driver";
        // armazenar informações ref ao banco
        // String url = "jdbc:sqlserver://datapecvc:1433;databaseName=dbsystemcontrol";
        String url = "jdbc:mysql://127.0.0.1:3306/dbsysteminfo";
        //String url = "jdbc:mysql://172.30.0.100:3306/dbsysteminfo";
        String user = "root";
        String password = "";
////        //Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
//         // linha abaixo apoio para erro
            System.out.println(e);
            return null;
        }
    }
}
