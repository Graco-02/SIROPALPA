/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entidades.Contacto;
import Entidades.Correo;
import Entidades.Telefono;
import Entidades.Usuario;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Contacto_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Contacto_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Contacto "
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
	
	public void set_insert(Contacto contacto){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Contacto (id) "
	        + "VALUES(?)");
	      
	      this.db_execute.setInt(1, contacto.get_id());
	      
	      this.db_execute.execute();
	      
	      set_tel_corre(contacto);
	      
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
	
	public void set_tel_corre(Contacto contacto){
		System.out.print(contacto.get_id());
		for(int i=0;i<contacto.get_lista_correos().size();i++){
		
			if(contacto.get_Correo(i).get_correo().length() >= 10){
				
				Correo correo = contacto.get_Correo(i);
				
				correo.setId_contacto(contacto.get_id());
				
				if(correo.get_id() == 0){// valido que su id sea diferente de 0 para saber si se seleciono un registro
					new Correo_control().set_insert(correo);
				
				}else{
					new Correo_control().set_modificar(correo);
				
				}// fin del if else
				
			}//fin del bucle for
		
		}
		
		for(int k = 0;k<contacto.get_lista_telefonos().size();k++){
			
			if(contacto.get_Telefono(k).getTelefono().length() >= 11){
				
				Telefono telefono = contacto.get_Telefono(k);
				
				telefono.setId_contacto(contacto.get_id());
				
				if(telefono.get_id() == 0){//valido que su id sea diferente de 0 para saber si se selecciono un registro
					
					new Telefono_Control().set_insert(telefono);
				
				}else{
					System.out.println(telefono.getTelefono());
					//new Telefono_Control().set_modificar(telefono);
				
				}//fin el if else
			}//fin del bucle for
		
		}
	}
	
	public Contacto get_contacto(int id){
		Contacto contacto = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM Contacto"
		      		+ " WHERE id = " + 
		        id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  contacto = new Contacto();
		    	  contacto.set_id(id);
		    	  contacto.set_lista_correos(new Correo_control().get_Correos(id));
		    	  contacto.set_lista_Telefonos(new Telefono_Control().get_telefonos(id));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		return contacto;
	}
	
	
}
