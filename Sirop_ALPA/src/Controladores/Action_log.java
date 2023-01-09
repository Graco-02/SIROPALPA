/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entidades.Foto;
import Entidades.Usuario;
import Entidades.bloqueo;
import Entidades.log_accion;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Action_log {

	/**
	 * 
	 */
	
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Action_log() {
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM histori_log "
		      					+ "Order BY id DESC"; 
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		     
		      if (rs.next()){
		    	  return (rs.getInt(1)+1);
		      }
		      
		      rs.close();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		  return 1;
	}

	
	public void set_insert(log_accion log){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into histori_log (fecha,hora,log,id_usuario) "
	        + "VALUES(?,?,?,?)");
	      
	      db_execute.setDate(1, log.getFecha());
	      db_execute.setTime(2, log.getHora());
	      db_execute.setString(3, log.getLog());
	      db_execute.setInt(4, Usuario.getId());
	      
	      this.db_execute.execute();
	      this.db_execute.close();
	      
	    }
	    catch (SQLException e){
	      e.printStackTrace();
	    }
	    catch (NullPointerException ex){
	      ex.printStackTrace();
	    }
	}
	
	public log_accion get_log(int id){
		log_accion log = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM histori_log"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  log = new log_accion();
		    	  log.setId(id);
		    	  log.setFecha(rs.getDate("fecha"));
		    	  log.setHora(rs.getTime("hora"));
		    	  log.setLog(rs.getString("log"));
		    	  log.setUsuario(rs.getInt("id_usuario"));
		    	  
		    	  return log;
		      }
		      
		      rs.close();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		
		return log;
	}
	
		
}
