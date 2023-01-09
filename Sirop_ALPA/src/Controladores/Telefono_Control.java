package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entidades.Contacto;
import Entidades.Lugar;
import Entidades.Telefono;
import Utilidades.DBconeccion;

public class Telefono_Control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Telefono_Control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Telefono "
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
	
	public void set_insert(Telefono telefono){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Telefono (Telefono,Id_contacto) "
	        + "VALUES(?,?)");
	      
	      this.db_execute.setString(1, telefono.getTelefono());
	      this.db_execute.setInt(2, telefono.getId_contacto());
	      
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
	
	public void set_modificar(Telefono tel){
		System.out.println("MODIFICO TELEFONO");
		System.out.println("TELEFONO.:"+tel.getTelefono());
		System.out.println("TELEFONOID.:"+tel.get_id());
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Telefono SET "
		        + "Telefono = ?"
		        + " WHERE id = ?");
		      
		      db_execute.setString(1, tel.getTelefono());
		      db_execute.setInt(2, tel.get_id());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	public void set_eliminar(Telefono tel){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " DELETE FROM Telefono "
		        + " WHERE id = ?");
		      
		      db_execute.setInt(1, tel.get_id());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}	
	
	
	public ArrayList<Telefono> get_telefonos(int id){
		ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
		
			try{
			      String selectSQL = "SELECT *"
			      		+ " FROM Telefono"
			      		+ " WHERE id_contacto = " + 
			        id;
			      
			      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
			      ResultSet rs = preparedStatement.executeQuery(selectSQL);
			     
			      while(rs.next()){
			    	Telefono telefono = new Telefono();
			    	telefono.set_id(rs.getInt("Id"));
			    	telefono.setId_contacto(id);
			    	telefono.setTelefono(rs.getString("Telefono"));
			    	
			    	telefonos.add(telefono);
			      }
			      
			      rs.close();

				  coneccion.cierra_Coneccion();
			 }catch (SQLException e){
			    e.printStackTrace();
			 }
		
		return telefonos;
	}
}
