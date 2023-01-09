/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entidades.Lugar;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Lugar_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Lugar_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Lugares "
		      					+ "Order BY id DESC"; 
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		     
		      if (rs.next()){
		    	  return (rs.getInt(1)+1);
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		  return 1;
	}
	
	public int get_Num(){
		  try{
		      String selectSQL = "SELECT COUNT(*)"
		      					+" FROM Lugares "
		      					+ "WHERE nivel_acceso = 0"; 
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		     
		      if (rs.next()){
		    	  return (rs.getInt(1));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		  return 1;
	}
	
	public void set_insert(Lugar lugar){
		 try
		    {
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        "INSERT into Lugares ("
		        + "Titulo,"
		        + "Nivel_acceso,"
		        + "Hora_ini,"
		        + "Hora_fin,"
		        + "Id_contacto) "
		        + "VALUES(?,?,?,?,?)");
		      
		      this.db_execute.setString(1, lugar.getTitulo());
		      this.db_execute.setInt(2, lugar.getAcceso());
		      this.db_execute.setTime(3, lugar.getHora_ini());
		      this.db_execute.setTime(4, lugar.getHora_fin());
		      this.db_execute.setInt(5, lugar.getContacto().get_id());
		      
		      this.db_execute.execute();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		    catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	public Lugar get_lugar(int id){
		Lugar lugar = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM Lugares"
		      		+ " WHERE id = " + 
		        id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  lugar = new Lugar();
		    	  lugar.setId(id);
		    	  lugar.setTitulo(rs.getString("Titulo"));
		    	  lugar.setAcceso(rs.getInt("nivel_acceso"));
		    	  lugar.setHora_ini(rs.getTime("Hora_ini"));
		    	  lugar.setHora_fin(rs.getTime("Hora_fin"));
		    	  lugar.setContacto(new Contacto_control().get_contacto(rs.getInt("Id_contacto")));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		return lugar;
	}
	
	public void set_modificar(Lugar lugar){
		
			try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Lugares SET "
		        + "Titulo = ?,"
		        + " Nivel_acceso = ?,"
		        + " Hora_ini = ?,"
		        + " Hora_fin = ?"
		        + " WHERE id = ?");
		      
		      db_execute.setString(1, lugar.getTitulo());
		      db_execute.setInt(2, lugar.getAcceso());
		      db_execute.setTime(3, lugar.getHora_ini());
		      db_execute.setTime(4, lugar.getHora_fin());
			  
		      db_execute.setInt(5, lugar.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    
			}catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
			
	}
	
	public void set_Eliminar(Lugar lugar){
		 try
		    {
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        
		    		  "DELETE From Lugares "
				    		  +"WHERE id = ? ");
		      
		      this.db_execute.setInt(1, lugar.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();   
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		    catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}

	
	public Lugar get_lugar(String lug){
		Lugar lugar = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM Lugares"
		      		+ " WHERE titulo = " + "'"+lug+"'";
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  lugar = new Lugar();
		    	  lugar.setId(rs.getInt("id"));
		    	  lugar.setTitulo(rs.getString("Titulo"));
		    	  lugar.setAcceso(rs.getInt("nivel_acceso"));
		    	  lugar.setHora_ini(rs.getTime("Hora_ini"));
		    	  lugar.setHora_fin(rs.getTime("Hora_fin"));
		    	  lugar.setContacto(new Contacto_control().get_contacto(rs.getInt("Id_contacto")));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		return lugar;
	}	
	
	
	
	public void set_depliega_info(Lugar lugar){
		System.out.println("****************************");
		System.out.println("Lugar id.: "+ lugar.getId());
		System.out.println("lugar titulo.: "+lugar.getTitulo());
		System.out.println("Lugar nivel.: "+lugar.getAcceso());
		System.out.println("Lugar cont.: "+lugar.getContacto().get_id());
		System.out.println("Lugar hora_ini.: "+lugar.getHora_ini());
		System.out.println("Lugar hora_fin.: "+ lugar.getHora_fin());
		System.out.println("****************************");
	}
	
	
}
