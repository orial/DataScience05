/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

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
            connection.eval("source('C:\\\\R\\\\FiltradoColaborativoBasadoUsuario.R')");
            connection.close();
        }catch(RserveException e){
              e.printStackTrace();
        }  
    }
    public void RecalcularClusterFiltradoColaborativoItem(){
        RConnection connection=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\R\\\\FiltradoColaborativoBasadoItem.R')");
            connection.close();
        }catch(RserveException e){
              e.printStackTrace();
        }  
    }
    public int[] FiltradoColaborativoBasadoUsuario(String usuarioID){
          RConnection connection=null;
          int[] movies=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\R\\\\PeliculasMejoresFiltradoColaboratiboBasadoUsuario.R')");
            movies =connection.eval("multi_return("+ usuarioID+")").asIntegers();
            connection.close();
        }catch(REXPMismatchException | RserveException e){
              e.printStackTrace();
        }  
         return movies;
     }
     public int[] FiltradoColaborativoBasadoItem(String usuarioID){
          RConnection connection=null;
          int[] movies=null;
        try{
            connection = new RConnection();
            connection.eval("source('C:\\\\R\\\\PeliculasMejoresFiltradoColaboratiboBasadoItem.R')");
            movies =connection.eval("multi_return("+ usuarioID+")").asIntegers();
            connection.close();
        }catch(REXPMismatchException | RserveException e){
              e.printStackTrace();
        }  
         return movies;
     }
}