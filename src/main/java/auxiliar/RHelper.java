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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.rosuda.REngine.Rserve.*;
import org.rosuda.REngine.*;
/**
 *
 * @author Pulgy
 */
public class RHelper {
    final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "recomendadordb";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String password = "root";
    private DBHelper db = new DBHelper();
    
    public void RecalcularClusterFiltradoColaborativoUser(){
        RConnection connection=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\FiltradoColaborativoBasadoUsuario.R')");
            connection.close();
        }catch(Exception e){
              e.printStackTrace();
        }  
    }
    public void RecalcularClusterFiltradoColaborativoItem(){
        RConnection connection=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\FiltradoColaborativoBasadoItem.R')");
            connection.close();
        }catch(Exception e){
              e.printStackTrace();
        }  
    }
     public int[] FiltradoColaborativoBasadoUsuario(String usuarioID){
          RConnection connection=null;
          int[] movies=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\PeliculasMejoresFiltradoColaboratiboBasadoUsuario.R')");
            movies =connection.eval("multi_return("+ usuarioID+")").asIntegers();
            connection.close();
        }catch(Exception e){
              e.printStackTrace();
        }  
         return movies;
     }
     public int[] FiltradoColaborativoBasadoItem(String usuarioID){
          RConnection connection=null;
          int[] movies=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\PeliculasMejoresFiltradoColaboratiboBasadoItem.R')");
            movies =connection.eval("multi_return("+ usuarioID+")").asIntegers();
            connection.close();
        }catch(Exception e){
              e.printStackTrace();
        }  
         return movies;
     }
}
