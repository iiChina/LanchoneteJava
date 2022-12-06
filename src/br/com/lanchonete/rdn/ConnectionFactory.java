package br.com.lanchonete.rdn;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionFactory {       
    
    
    private static String URL = "jdbc:sqlserver://localhost:1433;databaseName=db_supermercado_reges;trustServerCertificate=true;integratedSecurity=true;";
    
    public static Connection getConnection(){
    
         try {
             
            return DriverManager.getConnection(URL);
            
        } catch (SQLException ex) {

            throw new RuntimeException(ex);
            
        }
    }
}
