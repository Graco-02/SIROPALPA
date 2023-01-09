package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Persona;
import Utilidades.DBconeccion;

public class Reg_habitantes_control {

	private DBconeccion coneccion;
	private PreparedStatement db_execute;
	
	public Reg_habitantes_control(){
		coneccion = new DBconeccion();
	}
	
	public void set_insert(int persona,int lugar){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "INSERT into reg_habitantes (id_persona,Id_lugar) "
	        + "VALUES(?,?)");
	      
	      
	      db_execute.setInt(1, persona);
	      db_execute.setInt(2, lugar);
	      
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
	
	public void set_modificar(int persona,int opc){
		try
	    {
	      db_execute = this.coneccion.getcEnlace().prepareStatement(
	        "UPDATE reg_habitantes SET  estado = ? "
	        + "WHERE id_persona = ? ");
	      
	      
	      db_execute.setInt(1, opc);
	      db_execute.setInt(2, persona);
	      
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
	
	public boolean get_valida_estado(int persona){

		try{
		      String selectSQL = "SELECT estado"
		      		+ " FROM reg_habitantes"
		      		+ " WHERE id_persona = " + persona;
		      
		      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
		      ResultSet rs = preparedStatement.executeQuery(selectSQL);
		      
		      if (rs.next()){
		    	if(rs.getInt("estado") == 0){
		    		return false;
		    	}
		      }
		      
		      rs.close();

			  coneccion.cierra_Coneccion();
		    }
		    catch (SQLException e){
		      e.printStackTrace();
		    }
		return true;
	}
}
