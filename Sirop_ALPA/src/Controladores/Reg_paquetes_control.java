/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Paquete;
import Entidades.Persona;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Reg_paquetes_control {

	/**
	 * 
	 */
	
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Reg_paquetes_control() {
		coneccion = new DBconeccion();
	}
	
	public void set_insert(Paquete paq){
		
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into reg_paquetes (id_depositante" //1
	        + ",Id_receptor"//2
	        + ",id_usuario"//3
	        + ",fecha_entrada"//4
	        + ",descripcion"//5
	        + ",id_foto"//6
	        + ",estado) "//7
	        + "VALUES(?,?,?,?,?,?,?)");
	      
	      
	      db_execute.setInt(1, paq.getDepositante().getId());
	      db_execute.setInt(2, paq.getId_receptor());
	      db_execute.setInt(3, 0);
	      db_execute.setDate(4, paq.getFecha_entra());
	      db_execute.setString(5, paq.getDescripcion());
	      db_execute.setInt(6, paq.getFoto().get_id());
	      db_execute.setInt(7, 0);
	      
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
	}//
	
	
	public Paquete get_paquete_by_ID(int id){
		Paquete paq = null;
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM reg_paquetes"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  
		    	paq = new Paquete();
		    	
		    	paq.setId(id);
		    	paq.setDepositante(new Persona_control().get_persona(rs.getInt("id_depositante")));
		    	paq.setId_receptor(rs.getInt("Id_receptor"));
		    	paq.setDescripcion(rs.getString("Descripcion"));
		    	paq.setEstado(rs.getInt("Estado"));
		    	paq.setFecha_entra(rs.getDate("fecha_entrada"));
		    	paq.setFoto(new Foto_control().get_foto(rs.getInt("id_foto")));
		    	paq.setId_usuario(rs.getInt("id_usuario"));
		    	
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return paq;
	}//
	
	public void set_cerrar_caso(Paquete paq){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE reg_paquetes SET "
		        + "fecha_salida = ?, "
		    	+ "estado = ? "
		    	+ " WHERE id = ?");
		      
		      db_execute.setDate(1, paq.getFecha_sale());
		      db_execute.setInt(2, 1);
		      
		      db_execute.setInt(3, paq.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	

}
