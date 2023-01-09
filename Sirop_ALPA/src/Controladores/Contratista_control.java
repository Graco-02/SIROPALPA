package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Contratista;
import Entidades.Lugar;
import Utilidades.DBconeccion;

public class Contratista_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Contratista_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM contratista "
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
	
	public void set_insert(Contratista contratista){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into contratista (id_persona,descripcion) "
	        + "VALUES(?,?)");
	      
	      
	      db_execute.setInt(1, contratista.getDatos_personales().getId());
	      db_execute.setString(2, contratista.getDescripcion());
	      
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
	
	public void set_modificar(Contratista contratista){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "UPDATE contratista SET  descripcion = ? "
	        + "WHERE id_persona = ? ");
	      
	      
	      db_execute.setString(1, contratista.getDescripcion());
	      db_execute.setInt(2, contratista.getDatos_personales().getId());
	      
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
	
	public Contratista get_contratista(int id){
		Contratista contra = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM contratista"
		      		+ " WHERE id = " + 
		        id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	contra = new Contratista();
		    	contra.setDescripcion(rs.getString("Descripcion"));
		    	contra.setDatos_personales(new Persona_control().get_persona(rs.getInt("id_persona")));
		    	contra.setId(id);
		    	
		    	return contra;
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return contra;
	}
	
	public Contratista get_contratista_by_persona(int id){
		Contratista contra = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM contratista"
		      		+ " WHERE id_persona = " + 
		        id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	contra = new Contratista();
		    	contra.setDescripcion(rs.getString("Descripcion"));
		    	contra.setDatos_personales(new Persona_control().get_persona(rs.getInt("id_persona")));
		    	contra.setId(rs.getInt("id"));
		    	
		    	return contra;
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return contra;
	}

}
