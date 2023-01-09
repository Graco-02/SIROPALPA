package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Averias;
import Entidades.Contratista;
import Entidades.Lugar;
import Utilidades.DBconeccion;

public class Averias_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Averias_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Averias "
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
	
	public void set_insert(Averias averia){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into averias (id_apart,"
	        + "descripcion,"
	        + "titulo,"
	        + "fecha ) "
	        + "VALUES(?,?,?,?)");
	      
	      db_execute.setInt(1, averia.getSolicitante().getId());
	      db_execute.setString(2, averia.getDescripcion());
	      db_execute.setString(3, averia.getTitulo());
	      db_execute.setDate(4, averia.getFecha());
	      
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
	
	public void set_modificar(Averias averia){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "UPDATE averias SET estado = ? "
	        + "WHERE id = ? ");
	      
	      
	      db_execute.setInt(1, averia.getEstado());
	      db_execute.setInt(2, averia.getId());
	      
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
	
	public Averias get_averia(int id){
		Averias averia = null;
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM averias"
		      		+ " WHERE id = " + 
		        id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	 averia = new Averias();
		    	 averia.setId(id);
		    	 averia.setTitulo(rs.getString("titulo"));
		    	 averia.setDescripcion(rs.getString("descripcion"));
		    	 averia.setSolicitante(new Lugar_control().get_lugar(rs.getInt("id_apart")));
		    	 averia.setFecha(rs.getDate("fecha"));
		    	 averia.setEstado(rs.getInt("estado"));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return averia;
	}

}
