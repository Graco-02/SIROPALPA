/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entidades.Contacto;
import Entidades.Lugar;
import Entidades.Persona;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Persona_control {
	
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Persona_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Persona "
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
	
	public void set_insert(Persona persona){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Persona ("
	        + "Nombres,"
	        + "Apellidos,"
	        + "identificacion,"
	        + "Tipo_identificacion,"
	        + "id_contacto,"
	        + "id_foto) "
	        + "VALUES(?,?,?,?,?,?)");
	      
	      db_execute.setString(1, persona.getNombres());
	      db_execute.setString(2, persona.getApellidos());
	      db_execute.setString(3, persona.getIdentificacion());
	      db_execute.setInt(4, persona.getTipo_ident());
	      
	      if( persona.getContacto()!=null){
		      db_execute.setInt(5, persona.getContacto().get_id());
	      }else{
		      db_execute.setInt(5, 0);
	      }
	      
	      if( persona.getContacto()!=null){
	          db_execute.setInt(6, persona.getFoto().get_id());
	      }else{
	          db_execute.setInt(6, 0);
	      }
	
		  
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
	
	public void set_modificar(Persona persona){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Persona SET "
		        + "Nombres = ?, "
		    	+ "Apellidos = ?, "
		    	+ "identificacion = ?, "
		    	+ "Tipo_identificacion = ? "
		    	+ " WHERE id = ?");
		      
		      db_execute.setString(1, persona.getNombres());
		      db_execute.setString(2, persona.getApellidos());
		      db_execute.setString(3, persona.getIdentificacion());
		      db_execute.setInt(4, persona.getTipo_ident());
		      db_execute.setInt(5, persona.getId());
		       
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	
	public Persona get_persona(int id){
		Persona persona = null;
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM Persona"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  persona = new Persona();
		    	  
		    	  persona.setId(id);
		    	  persona.setNombres(rs.getString("Nombres"));
		    	  persona.setApellidos(rs.getString("Apellidos"));
		    	  persona.setIdentificacion(rs.getString("Identificacion"));
		    	  persona.setTipo_ident(rs.getInt("Tipo_identificacion"));
		    	  
		    	  persona.setContacto(new Contacto_control().get_contacto(rs.getInt("Id_contacto")));
		    	  persona.setFoto(new Foto_control().get_foto(rs.getInt("Id_foto")));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return persona;
	}
	
	public void set_eliminar(int id){
		 try
		    {
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        
		    		  "DELETE pers, cont, tel, corr, foto, lug, reg " 
		    		  +"FROM persona pers "
		    		  +"LEFT JOIN contacto cont "
		    		  +"ON pers.Id_contacto = cont.id " 
		    		  +"LEFT JOIN Telefono Tel "
		    		  +"ON cont.id = tel.id_contacto " 
		    		  +"LEFT JOIN Correo corr "
		    		  +"ON cont.id = corr.id_contacto "
		    		  +"LEFT JOIN Foto foto "
		    		  +"ON pers.id_foto = foto.id "
		    		  +"LEFT JOIN reg_habitantes reg "
		    		  +"ON pers.id = reg_habitantes.id_persona "
		    		  +"LEFT JOIN Lugares lug "
		    		  +"ON reg_habitantes.id_lugar = lugares.id "
		    		  +"WHERE lug.id = ? ");
		      
		      this.db_execute.setInt(1, id);
		      
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
	
	public void set_persona_validar_identificacion(Persona persona){
		
		try{
		      String selectSQL = "SELECT *"
		      					+" FROM Persona "
		      					+ "WHERE identificacion = "+"'"+persona.getIdentificacion()+"'"; 
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		     
		      if (rs.next()){
		    	 //si encuentra algo en la BBDD no hace nada 
		      }else{
		    	  //si no encuentra nada lo inserta como uno nuevo
		    	  
		    	  if(persona.getFoto()!=null){
		    	  new Foto_control().set_insert(persona.getFoto());
		    	  }
		    	  if(persona.getContacto()!=null){
		    	  new Contacto_control().set_insert(persona.getContacto());
		    	  }
		    	  set_insert(persona);
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
	}//fin del metodo que valida la identificacion
	
}
