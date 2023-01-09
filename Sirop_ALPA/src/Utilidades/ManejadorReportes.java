package Utilidades;

import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import Entidades.Lugar;
import Entidades.Persona;
import Entidades.Usuario;
import Entidades.visita;

public class ManejadorReportes
{
  private DefaultTableModel tabla;
  
  
  public ResultSetTableModel getReporte_Lugares()
    throws IllegalStateException, SQLException
  {
    ResultSetTableModel tabla = null;
    
    tabla = new ResultSetTableModel("SELECT id,titulo"
    		+ " from lugares");
    
    return tabla;
  }
  
  public ResultSetTableModel getReporte_Lugares_filt(int opc)
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT id,titulo"
		    		+ " from lugares where Nivel_acceso = "+opc);
		    
		    return tabla;
		  }  
  
  public ResultSetTableModel getReporte_Usuarios()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT id as Num,"
		    		+ "usuario,"
		    		+  "case llave  when 0 then 'Administrador' "
		    		+ " when 1 then 'Recepcion' "
		    		+ " when 2 then 'Condomino' end as tipo "
		    		+ " from Usuario");
		    
		    return tabla;
		  }
  
  
  public ResultSetTableModel getReporte_Reservas()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT reservas.id,"
		    		+ "lugares.titulo,"
		    		+ "reservas.nombre,"
		    		+ "reservas.fecha_reserva "
		    		+ " from Reservas "
		    		+ "Inner Join lugares ON Lugares.id = reservas.id_solicitante "
		    		+ "WHERE reservas.estado = 1 " );
		    
		    return tabla;
		  }
  
    public ResultSetTableModel getReporte_Averias()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT averias.id,"
		    		+ "lugares.titulo,"
		    		+ "averias.titulo AS Subject,"
		    		+ "case averias.estado  when 0 then 'REPORTADA'  when 1 then 'REPARANDO' when 2 then 'TERMINADA' end as tipo,"
			 		+ "averias.fecha "
		    		+ " from averias "
		    		+ "Inner Join lugares ON Lugares.id = averias.id_apart ");
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_Reservas_adm()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT reservas.id,"
		    		+ "lugares.titulo,"
		    		+ "reservas.nombre,"
		    		+ "reservas.fecha_reserva "
		    		+ " from Reservas "
		    		+ "Inner Join lugares ON Lugares.id = reservas.id_solicitante "
		    	);
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_habitantes(int id_lugar)
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT lugares.id AS NoLugar,"
			 		+ "lugares.titulo As Lugar,"
			 		+ "persona.id As NoPersona,"
			 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante"
					+ " FROM reg_habitantes "
					+ "Inner Join lugares "
					+ "ON lugares.id = reg_habitantes.id_lugar "
					+ "Inner Join Persona "
					+ "On persona.id = reg_habitantes.id_persona "
					+ "WHERE lugares.id = "+id_lugar + " AND reg_habitantes.estado = 0");
		    
		    return tabla;
		  }

  public ResultSetTableModel getReporte_visitas()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT reg_visitas.id,"
		    		+ "lugares.titulo As Lugar,"
			 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante,"
			 		+ "case reg_visitas.tipo  when 0 then 'Contratista'  when 1 then 'Privada' end as tipo,"
			 		+ "reg_visitas.fecha "
			 		+ " FROM reg_visitas "
					+ "Inner Join lugares "
					+ "ON lugares.id = reg_visitas.id_lugar "
					+ "Inner Join Persona "
					+ "On persona.id = reg_visitas.id_persona "
					+ "WHERE reg_visitas.fecha >= curdate() "
					+ "Order by lugares.titulo,lugares.nivel_acceso");
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_Contratistas()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT contratista.id,"
		    		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Contratista,"
			 		+ "contratista.descripcion" 
		    		+ " FROM contratista "
					+ "Inner Join Persona "
					+ "On persona.id = contratista.id_persona ");
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_Contratistas_p()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT contratista.id_persona,contratista.id,"
		    		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Contratista,"
			 		+ "contratista.descripcion" 
		    		+ " FROM contratista "
					+ "Inner Join Persona "
					+ "On persona.id = contratista.id_persona ");
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_Betados()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT Lista_negra.id,"+"CONCAT(p1.Nombres,',',p1.Apellidos) As Betado,"
		    		+ "l.Titulo As Lugar,"
		    		+ "Motivo,"
		    		+  "case Estado when 0 then 'Reportado'  when 1 then 'Retencion' when 2 then 'Libre' end as tipo,"
		    		+  "case Nivel_bql when 0 then 'Unico'  when 1 then 'Total' end as Bloqueo "
		    		+ " from Lista_negra "
		    		+ "Inner Join Persona p1 "
		    		+ "On p1.id = lista_negra.id_persona "
		    		+ "Inner Join Lugares l "
		    		+ "On l.id = lista_negra.id_lugar");
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_paquetes()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel(
		    		"SELECT "
		    		
		    		+ "reg_paquetes.id,"
		    		+ "CONCAT(p1.Nombres,',',p1.Apellidos) As Despositante,"
			 		+ "CONCAT(p2.Nombres,',',p2.Apellidos) As Receptor,"
			 		+ "reg_paquetes.fecha_entrada,"
			 		+ "reg_paquetes.descripcion,"
			 		+ "case reg_paquetes.estado  when 0 then 'ALMACENADO'  when 1 then 'ENTREGADO' end as ESTADO,"
			 		+ "reg_paquetes.id_usuario "
			 		
			 		+ " FROM reg_paquetes "
					
			 		+ "Inner Join Persona p1 "
					+ "On p1.id = reg_paquetes.id_depositante "
					+ "Inner Join Persona p2 "
					+ "On p2.id = reg_paquetes.id_receptor "
					
					+ "WHERE reg_paquetes.estado = 0 ");
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_Personas()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT id,"
		    		+ "CONCAT(Nombres,',',Apellidos) As Persona,"
			 		+ "identificacion "
			 		+ " FROM persona "
					);
		    
		    return tabla;
		  }
  
  public ResultSetTableModel getReporte_Habitantes()
		    throws IllegalStateException, SQLException
		  {
		    ResultSetTableModel tabla = null;
		    
		    tabla = new ResultSetTableModel("SELECT persona.id,"
		    		+ "lugares.titulo As Lugar,"
			 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante,"
		    		+ "reg_habitantes.id As id_hab"
			 		+ " FROM reg_habitantes "
					+ "Inner Join lugares "
					+ "ON lugares.id = reg_habitantes.id_lugar "
					+ "Inner Join Persona "
					+ "On persona.id = reg_habitantes.id_persona "
					+ " WHERE reg_habitantes.estado = 0"
					+ " Order by lugares.titulo");
		    
		    return tabla;
		  }
  
  public String set_queryReporte_lugares(String criterios,int opc){
		 return "SELECT id,titulo"
					+ " from lugares "
				+ "Where Titulo Like "+"'%"+criterios+"%' " + "AND Nivel_acceso = " + opc;
  }
  
  public String set_queryReporte_Habitantes_lug(int id_lugar){
		 return "SELECT lugares.id AS NoLugar,"
		 		+ "lugares.titulo As Lugar,"
		 		+ "persona.id As NoPersona, "
		 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante "
				+ " from reg_habitantes "
				+ "Inner Join lugares "
				+ "ON lugares.id = reg_habitantes.id_lugar "
				+ "Inner Join Persona "
				+ "On persona.id = reg_habitantes.id_persona "
				+ "WHERE lugares.id = "+id_lugar + " AND reg_habitantes.estado = 0";
  }

  
  public String get_query_visitas(){
	  return "SELECT reg_visitas.id,"
	    		+ "lugares.titulo As Lugar,"
		 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante,"
		 		+ "case reg_visitas.tipo  when 0 then 'Contratista'  when 1 then 'Privada' end as tipo,"
		 		+ "reg_visitas.fecha "
		 		+ " FROM reg_visitas "
				+ "Inner Join lugares "
				+ "ON lugares.id = reg_visitas.id_lugar "
				+ "Inner Join Persona "
				+ "On persona.id = reg_visitas.id_persona "
				+ "WHERE reg_visitas.fecha >= curdate() "
				+ "Order by lugares.titulo,lugares.nivel_acceso";
  }
  
  public String get_query_visitas(String fecha_i,String fecha_f){
	  return "SELECT reg_visitas.id,"
	    		+ "lugares.titulo As Lugar,"
		 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante,"
		 		+ "case reg_visitas.tipo  when 0 then 'Contratista'  when 1 then 'Privada' end as tipo,"
		 		+ "reg_visitas.fecha "
		 		+ " FROM reg_visitas "
				+ "Inner Join lugares "
				+ "ON lugares.id = reg_visitas.id_lugar "
				+ "Inner Join Persona "
				+ "On persona.id = reg_visitas.id_persona "
				+ "WHERE reg_visitas.fecha BETWEEN " + "'"+fecha_i+"' AND " + "'"+fecha_f+"'"
				+ " Order by lugares.titulo,lugares.nivel_acceso";
  }
  
  
  public String get_query_habitantes(){
	  return "SELECT persona.id,"
	    		+ "lugares.titulo As Lugar,"
		 		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Habitante"
		 		+ " FROM reg_habitantes "
				+ "Inner Join lugares "
				+ "ON lugares.id = reg_habitantes.id_lugar "
				+ "Inner Join Persona "
				+ "On persona.id = reg_habitantes.id_persona "
				+ " WHERE reg_habitantes.estado = 0"
				+ " Order by lugares.titulo";
  }
  
  public String get_query_paquetes(){
	  return 
			  "SELECT "
			  	+ "reg_paquetes.id,"
	    		+ "CONCAT(p1.Nombres,',',p1.Apellidos) As Despositante,"
		 		+ "CONCAT(p2.Nombres,',',p2.Apellidos) As Receptor,"
		 		+ "reg_paquetes.fecha_entrada,"
		 		+ "reg_paquetes.descripcion,"
		 		+ "case reg_paquetes.estado  when 0 then 'ALMACENADO'  when 1 then 'ENTREGADO' end as ESTADO,"
		 		+ "reg_paquetes.id_usuario "
		 		
		 		+ " FROM reg_paquetes "
				
		 		+ "Inner Join Persona p1 "
				+ "On p1.id = reg_paquetes.id_depositante "
				+ "Inner Join Persona p2 "
				+ "On p2.id = reg_paquetes.id_receptor "
				
				+ "WHERE reg_paquetes.estado = 0 ";
  }
  
  public String get_query_paquetes(String f1,String f2){
	  return 
			  "SELECT "
			  	+ "reg_paquetes.id,"
	    		+ "CONCAT(p1.Nombres,',',p1.Apellidos) As Despositante,"
		 		+ "CONCAT(p2.Nombres,',',p2.Apellidos) As Receptor,"
		 		+ "reg_paquetes.fecha_entrada,"
		 		+ "reg_paquetes.descripcion,"
		 		+ "case reg_paquetes.estado  when 0 then 'ALMACENADO'  when 1 then 'ENTREGADO' end as ESTADO,"
		 		+ "reg_paquetes.id_usuario "
		 		
		 		+ " FROM reg_paquetes "
				
		 		+ "Inner Join Persona p1 "
				+ "On p1.id = reg_paquetes.id_depositante "
				+ "Inner Join Persona p2 "
				+ "On p2.id = reg_paquetes.id_receptor "
				
				+ "WHERE reg_paquetes.fecha_entrada BETWEEN " + "'"+f1+"' AND " + "'"+f2+"'"
				;
  }
  
  public String get_query_betados(){
	  return
			  "SELECT Lista_negra.id,"+"CONCAT(p1.Nombres,',',p1.Apellidos) As Betado,"
	    		+ "l.Titulo As Lugar,"
	    		+ "Motivo,"
	    		+  "case Estado when 0 then 'Reportado'  when 1 then 'Retencion' when 2 then 'Libre' end as tipo,"
	    		+  "case Nivel_bql when 0 then 'Unico'  when 1 then 'Total' end as Bloqueo "
	    		+ " from Lista_negra "
	    		+ "Inner Join Persona p1 "
	    		+ "On p1.id = lista_negra.id_persona "
	    		+ "Inner Join Lugares l "
	    		+ "On l.id = lista_negra.id_lugar";
  }
  
  public String get_query_usuarios(){
	  return 
			  "SELECT id as Num,"
	    		+ "usuario,"
	    		+  "case llave  when 0 then 'Administrador' "
	    		+ " when 1 then 'Recepcion' "
	    		+ " when 2 then 'Condomino' end as tipo "
	    		+ " from Usuario";
	    		
  }
  
  public String get_query_reservas(){
	  
	  if(Usuario.getLlave() == 0){
		  return "SELECT reservas.id,lugares.titulo,"
		    		+ "reservas.nombre,"
		    		+ "reservas.fecha_reserva "
		    		+ " from Reservas "
		    		+ "Inner Join lugares ON Lugares.id = reservas.id_solicitante ";
		    	
	  }else{
		 return  "SELECT reservas.id,lugares.titulo,"
		    		+ "reservas.nombre,"
		    		+ "reservas.fecha_reserva "
		    		+ " from Reservas "
		    		+ "Inner Join lugares ON Lugares.id = reservas.id_solicitante "
		    		+ "WHERE reservas.estado = 0";
		    	
	  }
	  
  }
  
  public String get_query_contratista(){
	  return 
	  "SELECT contratista.id,"
	    		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Contratista,"
		 		+ "contratista.descripcion" 
	    		+ " FROM contratista "
				+ "Inner Join Persona "
				+ "On persona.id = contratista.id_persona ";
  }
  
  public String get_query_contratista_p(){
	  return 
	  "SELECT contratista.id_persona,contratista.id,"
	    		+ "CONCAT(persona.Nombres,',',persona.Apellidos) As Contratista,"
		 		+ "contratista.descripcion" 
	    		+ " FROM contratista "
				+ "Inner Join Persona "
				+ "On persona.id = contratista.id_persona ";
  }
  
  
  public String get_query_averias(){
	  return "SELECT averias.id,"
	    		+ "lugares.titulo,"
	    		+ "averias.titulo,"
	    		+ "case averias.estado  when 0 then 'REPORTADA'  when 1 then 'REPARANDO' when 2 then 'TERMINADA' end as tipo,"
		 		+ "averias.fecha "
	    		+ " from averias "
	    		+ "Inner Join lugares ON Lugares.id = averias.id_apart ";
  }
}
