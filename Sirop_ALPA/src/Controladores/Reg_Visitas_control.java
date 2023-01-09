package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Persona;
import Entidades.Reparaciones;
import Entidades.visita;
import Utilidades.DBconeccion;

public class Reg_Visitas_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Reg_Visitas_control(){
		coneccion = new DBconeccion();
	}
	
	public int get_NextNum(){
		  try{
		      String selectSQL = "SELECT id"
		      					+" FROM Reg_visitas "
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
	
	public void set_insert(visita visita){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into reg_visitas ("
	        + "id_persona,"
	        + "id_lugar,"
	        + "fecha,"
	        + "hora_ent,"
	        + "nota,"
	        + "tipo) "
	        + "VALUES(?,?,?,?,?,?)");
	     
	      db_execute.setInt(1, visita.getPersona().getId());
	      db_execute.setInt(2, visita.getId_lugar());
	      db_execute.setDate(3, visita.getFecha());
	      db_execute.setTime(4, visita.getHora_ent());
	      db_execute.setString(5, visita.getNota());
	      db_execute.setInt(6, visita.getTipo());
	      
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
	}//fin del metodo insertar
	
	
	public void set_insert(Reparaciones reparacion){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into reg_visitas ("
	        + "id_persona,"
	        + "id_lugar,"
	        + "fecha,"
	        + "nota,"
	        + "tipo) "
	        + "VALUES(?,?,?,?,?)");
	     
	      System.out.println(reparacion.getContratista().getDatos_personales().getId());
	      db_execute.setInt(1, reparacion.getContratista().getDatos_personales().getId());
	      db_execute.setInt(2, reparacion.getAveria().getSolicitante().getId());
	      db_execute.setDate(3, reparacion.getFecha_ini());
	      db_execute.setString(4, reparacion.getAveria().getDescripcion());
	      db_execute.setInt(5, 0);
	      
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
	}//fin del metodo insertar
	
	
	public visita get_visita_by_id(int id){
		visita visita = null;
		
		try{
		      String selectSQL = "SELECT *"
		      		+ " FROM reg_visitas"
		      		+ " WHERE id = " + id;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	  visita = new visita();
		    	  
		    	  visita.setId(id);
		    	  visita.setNota(rs.getString("Nota"));
		    	  visita.setFecha(rs.getDate("fecha"));
		    	  visita.setHora_ent(rs.getTime("Hora_ent"));
		    	  visita.setHora_sal(rs.getTime("Hora_sal"));
		    	  visita.setTipo(rs.getInt("tipo"));
		    	  visita.setId_lugar(rs.getInt("id_lugar"));
		    	  visita.setPersona(new Persona_control().get_persona(rs.getInt("id_persona")));
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		
		return visita;
	}
	
	public void set_hora_salida(visita visita){
		try{
		      db_execute = this.coneccion.getcEnlace().prepareStatement(
		        " UPDATE reg_visitas SET "
		        + "Hora_sal = ?,"
		        + "estado = ?"
		    	+ " WHERE id = ?");
		      
		      this.db_execute.setTime(1, visita.getHora_sal());
		      this.db_execute.setInt(2, 1);
		      this.db_execute.setInt(3, visita.getId());
		      
		      this.db_execute.executeUpdate();
		      this.db_execute.close();

			  coneccion.cierra_Coneccion();
		    }catch (SQLException e){
		      e.printStackTrace();
		    }catch (NullPointerException ex){
		      ex.printStackTrace();
		    }
	}
	
	public boolean valida_estado(int id_persona){
		System.out.println("id .:" + id_persona);
		
		 try{
		      String selectSQL = "SELECT *"
		      					+" FROM Reg_visitas "
		      					+ "Where id_persona = " + id_persona
		      					+ " AND estado = " + 0;
		      	
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		     
		      if (rs.next()){
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
	
	public void set_desplegar(visita visita){
		System.out.println("----------------------------------------------");
			System.out.println(visita.getId_lugar());
			System.out.println(visita.getPersona().getId());
			System.out.println(visita.getFecha().toString());
			System.out.println(visita.getHora_ent().toString());
			System.out.println(visita.getHora_sal().toString());
			System.out.println(visita.getTipo());
			System.out.println(visita.getNota());
		System.out.println("----------------------------------------------");
	}
}
