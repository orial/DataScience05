/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Joaquin
 */
public class DBHelper {
    final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "recomendadordb";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String password = "root";
    
    
    public boolean existsNickname(String nickname){
        boolean res=false;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USER WHERE NAME = ?");
            stmt.setString(1, nickname);
            
            ResultSet rs = stmt.executeQuery();
            res=rs.next();
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
    public int insertUser(String name, String pass, String email){
        int res = 0;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("INSERT INTO USER (NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, pass);
            stmt.setString(3, email);
            
            res = stmt.executeUpdate();
            
            stmt.close();
        }catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
    public String checkLogin(String pass, String email){
        String res="";
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USER WHERE PASSWORD = ? AND EMAIL = ?");
            stmt.setString(1, pass);
            stmt.setString(2, email);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("NAME");
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
}
