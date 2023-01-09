package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Contacto;
import Entidades.Foto;
import Entidades.Persona;
import Utilidades.DBconeccion;

public class Foto_control {
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Foto_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Foto "
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
	
	public void set_insert(Foto foto){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Foto (Ruta,archivo) "
	        + "VALUES(?,?)");
	      
	      
	      db_execute.setString(1, foto.get_ruta_foto());
	      db_execute.setBytes(2, foto.getArchivo());
	      
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
	
	public void set_modificar(Foto foto){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Foto SET "
		        + "Ruta = ?,"
		        + "archivo = ?"
		        + " WHERE id = ?");
		      
		      db_execute.setString(1, foto.get_ruta_foto());
		      db_execute.setBytes(2, foto.getArchivo());
		      db_execute.setInt(3, foto.get_id());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	public Foto get_foto(int id){
		Foto foto = null;
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM foto"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  foto = new Foto();
		    	  foto.set_id(id);
		    	  foto.set_ruta_foto(rs.getString("Ruta"));
		    	  foto.setArchivo(rs.getBytes("Archivo"));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return foto;
	}
	
}
