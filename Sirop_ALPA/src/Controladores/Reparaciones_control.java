package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Reparaciones;
import Utilidades.DBconeccion;

public class Reparaciones_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Reparaciones_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Reparaciones "
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
	
	public void set_insert(Reparaciones reparacion){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into reparaciones (id_averia,"
	        + "id_contratista,"
	        + "Fecha_ini,"
	        + "fecha_fin ) "
	        + "VALUES(?,?,?,?)");
	      
	      db_execute.setInt(1, reparacion.getAveria().getId());
	      db_execute.setInt(2, reparacion.getContratista().getId());
	      db_execute.setDate(3, reparacion.getFecha_ini());
	      db_execute.setDate(4, reparacion.getFecha_fin());
	      
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
	
	public void set_modificar(Reparaciones rep){
		System.out.println("rep mod recivida.:"+rep.getId());
		System.out.println("rep Estado recivida.:"+rep.getEstado());
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "UPDATE reparaciones SET estado = ? "
	        + "WHERE id = ? ");
	      
	      
	      db_execute.setInt(1, rep.getEstado());
	      db_execute.setInt(2, rep.getId());
	      
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
	
	public Reparaciones get_reparaciones(int id){
		System.out.println("averia recivida.:"+id);
		
		Reparaciones rep = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM reparaciones"
		      		+ " WHERE id_averia = " + 
		        id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	rep = new Reparaciones();
		    	rep.setId(rs.getInt("id"));
		    	rep.setAveria(new Averias_control().get_averia(rs.getInt("id_averia")));
		    	rep.setContratista(new Contratista_control().get_contratista(rs.getInt("id_contratista")));
		    	rep.setFecha_fin(rs.getDate("fecha_fin"));
		    	rep.setFecha_ini(rs.getDate("fecha_ini"));
		    	rep.setEstado(rs.getInt("estado"));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return rep;
	}

}
