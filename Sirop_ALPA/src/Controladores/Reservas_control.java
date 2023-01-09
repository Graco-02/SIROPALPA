/**
 * 
 */
package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entidades.Reservas;
import Utilidades.DBconeccion;

/**
 * @author FFerr
 *
 */
public class Reservas_control {

	/**
	 * 
	 */
	
	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Reservas_control() {
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Reservas "
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
	
	public void set_insert(Reservas reserva){
		
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Reservas (id_Solicitante" //1
	        + ",Fecha_solicitud"//2
	        + ",lista_invitados"//3
	        + ",fecha_Reserva"//4
	        + ",Hora_ini"//5
	        + ",Hora_fin"//6
	        + ",Nombre"//7
	        + ",estado) "//8
	        + "VALUES(?,?,?,?,?,?,?,?)");
	      
	      
	      db_execute.setInt(1, reserva.getId_solicitante());
	      db_execute.setDate(2, reserva.getFecha_solicitud());
	      db_execute.setBytes(3, reserva.getLista_invitados());
	      db_execute.setDate(4, reserva.getFecha_reserva());
	      db_execute.setTime(5, reserva.getHora_inicio());
	      db_execute.setTime(6, reserva.getHora_fin());
	      db_execute.setString(7, reserva.getNombre_solicitante());
	      db_execute.setInt(8, 0);
	      
	      
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
		
		System.out.println("size lista.:"+reserva.get_lista_reservaciones().size());
		for(int i= 0;i<reserva.get_lista_reservaciones().size();i++){
			set_insert_det(reserva.getId(), reserva.get_seleccion(i));
		}
		
	}//
	
	
	private void set_insert_det(int id,String ss){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into Reservas_det (id_reserva" //1
	        + ",seleccion) "//2
	        + "VALUES(?,?)");
	      
	      db_execute.setInt(1, id);
	      db_execute.setString(2, ss);
	      
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
	
	
	public Reservas get_reserva_by_ID(int id){
		Reservas reserva=null;
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM reservas"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  
		    	reserva = new Reservas();
		    	reserva.setId(id);
		    	reserva.setEstado(rs.getInt("Estado"));
		    	reserva.setFecha_reserva(rs.getDate("Fecha_reserva"));
		    	reserva.setFecha_solicitud(rs.getDate("Fecha_Solicitud"));
		    	reserva.setLista_invitados(rs.getBytes("lista_invitados"));
		    	reserva.setHora_inicio(rs.getTime("Hora_Ini"));
		    	reserva.setHora_fin(rs.getTime("Hora_fin"));
		    	reserva.setNombre_solicitante(rs.getString("Nombre"));
		    	reserva.setId_solicitante(rs.getInt("Id_solicitante"));
		    	reserva.setInvitados(rs.getString("Invitados"));
		    	reserva.set_lista_seleeciones(get_selecciones(reserva.getId()));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return reserva;
	}//

	
	private ArrayList<String> get_selecciones(int id){
		ArrayList<String> lista = new ArrayList<String>();
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM reservas_det"
		      		+ " WHERE id_reserva = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      while(rs.next()){
		    	  lista.add(rs.getString("seleccion"));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		return lista;
	}
	
	public void set_modificar(Reservas reserva){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE reservas SET "
		    + "estado = ? "//7
		    + " WHERE id = ?");//8
		  
		      
		      db_execute.setInt(1, reserva.getEstado());
		      
		      db_execute.setInt(2, reserva.getId());
		      
		      
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
