package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entidades.Correo;
import Entidades.Telefono;
import Utilidades.DBconeccion;

public class Correo_control {
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Correo_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Correo "
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
	
	public void set_insert(Correo correo){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Correo (Correo,Id_contacto) "
	        + "VALUES(?,?)");
	      System.out.println(correo.getId_contacto());
	      this.db_execute.setString(1, correo.get_correo());
	      this.db_execute.setInt(2, correo.getId_contacto());
	      
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
	
	public void set_modificar(Correo correo){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Correo SET "
		        + "Correo = ?"
		        + " WHERE id = ?");
		      
		      db_execute.setString(1, correo.get_correo());
		      db_execute.setInt(2, correo.get_id());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	public void set_eliminar(Correo correo){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " DELETE FROM Correo "
		        + " WHERE id = ?");
		      
		      db_execute.setInt(1, correo.get_id());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}		
	
	
	public ArrayList<Correo> get_Correos(int id){
		ArrayList<Correo> correos = new ArrayList<Correo>();
		
			try{
			      String selectSQL = "SELECT *"
			      		+ " FROM Correo"
			      		+ " WHERE id_contacto = " + 
			        id;
			      
			      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
			      ResultSet rs = preparedStatement.executeQuery(selectSQL);
			     
			      while(rs.next()){
			    	Correo correo = new Correo();
			    	correo.set_id(rs.getInt("Id"));
			    	correo.setId_contacto(rs.getInt("Id_contacto"));
			    	correo.set_correo(rs.getString("Correo"));
			    	
			    	correos.add(correo);
			      }
			      
			      rs.close();

				  coneccion.cierra_Coneccion();
			 }catch (SQLException e){
			    e.printStackTrace();
			 }
		
		return correos;
	}
	
}
