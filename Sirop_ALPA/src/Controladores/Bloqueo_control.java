/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entidades.Foto;
import Entidades.bloqueo;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Bloqueo_control {

	/**
	 * 
	 */
	
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Bloqueo_control() {
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM lista_negra "
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

	
	public void set_insert(bloqueo bloqueo){
		System.out.println(bloqueo.getApt().getId());
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into lista_negra (id_persona,id_lugar,motivo,estado,nivel_bql) "
	        + "VALUES(?,?,?,?,?)");
	      
	      db_execute.setInt(1, bloqueo.getReportado().getId());
	      db_execute.setInt(2,bloqueo.getApt().getId());
	      db_execute.setString(3, bloqueo.getMotivo());
	      db_execute.setInt(4, bloqueo.getEstado());
	      db_execute.setInt(5, bloqueo.getBloqueo());
	      
	      
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
	
	public bloqueo get_betado(int id){
		bloqueo betado = null;
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM lista_negra"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  betado = new bloqueo();
		    	  betado.setApt(new Lugar_control().get_lugar(rs.getInt("Id_lugar")));
		    	  betado.setReportado(new Persona_control().get_persona(rs.getInt("Id_persona")));
		    	  betado.setMotivo(rs.getString("Motivo"));
		    	  betado.setEstado(rs.getInt("Estado"));
		    	  betado.setBloqueo(rs.getInt("Nivel_bql"));
		    	  betado.setNum(id);
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		
		return betado;
	}
	
	public void set_modificar(bloqueo betado){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE Lista_negra SET "
		        + "id_persona = ?,"
		        + " id_lugar = ?,"
		        + " motivo = ?,"
		        + " estado = ?,"
		        + " Nivel_bql = ?"
		        + " WHERE id = ?");
		      
		      db_execute.setInt(1, betado.getReportado().getId());
		      db_execute.setInt(2, betado.getApt().getId());
		      db_execute.setString(3, betado.getMotivo());
		      db_execute.setInt(4, betado.getEstado());
		      db_execute.setInt(5, betado.getBloqueo());
		      db_execute.setInt(6, betado.getNum());
		      
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion(); 
			}catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
		
	}
	
	
	public boolean get_validar(int num){
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM lista_negra"
		      		+ " WHERE id_persona = " + num;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){//verifico que haya encontrado data
		    	if(rs.getInt("Nivel_bql") == 1 && rs.getInt("Estado") == 1){//valido el nivel de bloqueo y estado
		    		//valido que este retenido y su nivel de bloqueo sea total
		    		
		    		JOptionPane.showMessageDialog(null, "Esta persona tiene una retencion total");
		    		
		    		return false;
		    	
		    	}else if(rs.getInt("Nivel_bql") == 1){//valido el nivel de bloqueo 
		    		
		    		JOptionPane.showMessageDialog(null,
		    				"Esta persona tiene una retencion para el apartamento "
		    		+"'"+new Lugar_control().get_lugar(rs.getInt("id_lugar")).getTitulo()+"'");
		    		
		    		return false;
		    	}else if(rs.getInt("Nivel_bql") == 0){//valido el nivel de bloqueo 
		    		
		    		JOptionPane.showMessageDialog(null,
		    				"Esta persona fue Reportada para el apartamento "
		    		+"'"+new Lugar_control().get_lugar(rs.getInt("id_lugar")).getTitulo()+"'");
		    		
		    		return false;
		    	} 
		      }
		      
		      rs.close();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
	
		return true;
	}
	
}
