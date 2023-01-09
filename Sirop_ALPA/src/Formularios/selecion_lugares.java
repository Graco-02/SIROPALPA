package Formularios;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import Controladores.Lugar_control;
import Utilidades.DBconeccion;

public class selecion_lugares extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBconeccion coneccion;
	private JCheckBox[] seleciones_ch;
	private ArrayList<String> seleccion_list;
	private String[] selecciones;
	
	public selecion_lugares() {
		this.coneccion = new DBconeccion();
		seleccion_list = new ArrayList<String>();
		
		this.setLayout(new GridLayout(10, 3));
		this.setBackground(Color.GREEN);
		
		selecciones = get_Titulos();
		
		this.set_selecciones();
	}
	
	public selecion_lugares(String[] selecciones) {
		this.coneccion = new DBconeccion();
		this.seleccion_list = new ArrayList<String>();
		
		this.setLayout(new GridLayout(10, 3));
		this.setBackground(Color.GREEN);
		
		System.out.println("Sleccion size.:"+selecciones.length);
		this.selecciones = selecciones;
		
		this.set_selecciones();
	}
	
	
	private void set_selecciones(){
		seleciones_ch = new JCheckBox[selecciones.length];
		
		for(int i = 0;i<seleciones_ch.length;i++){
			 JCheckBox ss = new JCheckBox(selecciones[i]);
			 ss.addActionListener(this);
			 seleciones_ch[i] = ss;
		}
		
		for(int i = 0;i<seleciones_ch.length;i++){
			this.add(seleciones_ch[i]);   
		}
		
	}
	
	
	private String[] get_Titulos(){
		selecciones = null;
		
			try{
			      String selectSQL = "SELECT *"
			      		+ " FROM Lugares"
			      		+ " WHERE Nivel_acceso = 0 "
			      		;
			      
			      PreparedStatement preparedStatement = this.coneccion.getcEnlace().prepareStatement(selectSQL);
			      ResultSet rs = preparedStatement.executeQuery(selectSQL);
			     
			      selecciones = new String[new Lugar_control().get_Num()];
			      
			      int i =0;
			      while(rs.next()){
			    	selecciones[i++] = rs.getString("Titulo");
			      }
			      
			      rs.close();
			 }catch (SQLException e){
			    e.printStackTrace();
			 }
		
		return selecciones;
	}
	
	public ArrayList<String> get_lista(){
		return seleccion_list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		seleccion_list.add(e.getActionCommand());
	}

	

}
