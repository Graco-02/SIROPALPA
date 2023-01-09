/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.xml.ws.ServiceMode;

import Encript.Desencriptar;
import Encript.Encriptar;
import Entidades.Contacto;
import Entidades.Lugar;
import Entidades.Persona;
import Entidades.Usuario;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Usuario_control {
	
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Usuario_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Usuario "
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
	
	public void set_insert(Usuario usuario){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Usuario ("
	        + "Usuario,"
	        + "Clave,"
	        + "Llave,"
	        + "id_hab) "
	        + "VALUES(?,?,?,?)");
	      
	      
	      String clave = "";
	      if(usuario.getId_habitrante() != 0){
	    	  clave = usuario.getClave();
	      }else{
	    	  clave = new Encriptar().Encriptar_texto(usuario.getClave());
	      }
	      
	      db_execute.setString(1, usuario.getUser());
	      db_execute.setString(2, clave);
		  db_execute.setInt(3, usuario.getLlave());
		  db_execute.setInt(4, usuario.getId_habitrante());
		      
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
		
		//aparte de agregarlo a la tabla de registro de usuarios lo creo como usuario de la DB
		//sus privilegios dependen de su nivel de acceso
		//set_usuario_toDB(usuario);
	}
	
	public void set_modificar(Usuario user){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Usuario SET "
		        + "Usuario = ?, "
		    	+ "Clave = ?, "
		    	+ "llave = ?"
		    	+ " WHERE id = ?");
		      
		      db_execute.setString(1,user.getUser());
		      String clave = user.getClave();
		      if (user.getLlave()!=2){
		    	  clave = new Encriptar().Encriptar_texto(user.getClave());
		      }
		      db_execute.setString(2,clave );
		      
		      db_execute.setInt(3, user.getLlave());
		      
		      db_execute.setInt(4, user.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		      
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	

	public void set_modificar2(Usuario user){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Usuario SET "
		    	+ "id_hab = ?, "
		    	+ "llave = ?"
		    	+ " WHERE id = ?");
		      
		      db_execute.setInt(1,user.getId_habitrante());
		      db_execute.setInt(2, user.getLlave());
		      
		      db_execute.setInt(3, user.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}	
	
	
	public Usuario get_usuario(int id) throws Exception{
		System.out.println(id);
		Usuario user=null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM usuario"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	 user = new Usuario();
		    	 
		    	 user.setId(id);
		    	 user.setUser(rs.getString("Usuario"));
		    	 user.setClave(new Desencriptar().Desencriptar(rs.getString("Clave")));
		    	 user.setLlave(rs.getInt("Llave"));
		    	 
		    	 return user;
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		return user;
	}
	
	public boolean valida_usuario(){
		  try{
		      String selectSQL = "SELECT *"
		      		+ " FROM usuario"
		      		+ " WHERE usuario = " 
		      		+ "'" +Usuario.getUser() + "'" 
		      		+ " AND " + "clave = " 
		      		+ "'" + new Encriptar().Encriptar_texto( Usuario.getClave() ) + "'";
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  Usuario.setLlave(rs.getInt("llave"));
		    	  Usuario.setId(rs.getInt("id"));
		    	 return true;
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		  
		  return false;

	}
	
	public void set_Eliminar(Usuario user){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " DELETE FROM USUARIO "
		        + "WHERE id = ?");
		      
		      
		      db_execute.setInt(1, user.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	
	protected void set_Create_usuario(Usuario usuario){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "CREATE USER '"+usuario.getUser()+"'@'localhost' "
	        		+ "IDENTIFIED BY '"+usuario.getClave()+"' "
	        + ";");
	      
	      
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
	
	protected void set_Create_privilegios(Usuario usuario){
		String query="";
		
		switch (usuario.getLlave()) {
		case 0:
			System.out.println("ENTRO");
			query="GRANT ALL PRIVILEGES ON sirop_alpa.* TO '"+usuario.getUser()+"'@'localhost';";
			break;

		default:
			query="GRANT SELECT,UPDATE,INSERT ON sirop_alpa.* TO '"+usuario.getUser()+"'@'localhost';";
			break;
		}
		
		try
	    {
	      
			db_execute = this.coneccion.getcEnlace().prepareStatement(query);
	      
	      
	      this.db_execute.execute();
	      System.out.println("EJECUTO");
			
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
		
	
	protected void set_usuario_toDB(Usuario user){
		set_Create_usuario(user);
		set_Create_privilegios(user);
	}
}
