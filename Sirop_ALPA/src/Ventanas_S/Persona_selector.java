/**
 * 
 */
package Ventanas_S;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controladores.Lugar_control;
import Controladores.Persona_control;
import Entidades.Lugar;
import Entidades.Persona;
import Entidades.Usuario;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;

/**
 * @author FFerr
 *
 */
public class Persona_selector extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private Persona persona;
	private int swit;
	
	public Persona_selector(){
		this.setTitle("Personas Selector");
		this.setSize(700, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(getTabla());
		this.setVisible(true);
	}
	
	public  Persona_selector(int i){
		swit = i;
		
		this.setTitle("Personas Selector");
		this.setSize(700, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(getTabla());
		
		this.setVisible(true);
	}

	private Box getTabla()
	  {
	    try
	    {
	    	
	    
	    	switch (swit) {
		
	    	case 1:
			
	    		resultado = new ManejadorReportes().getReporte_Habitantes();
		      
			break;

	    	case 2:
				
	    		resultado = new ManejadorReportes().getReporte_Contratistas_p();
		      
			break;
			
	    	default:
			
			
	    		resultado = new ManejadorReportes().getReporte_Personas();
		      
			break;
	    	}
	    	
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
						
	            		persona = new Persona_control().get_persona(
	            				Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0)))
	            				);
	            		
	            		persona.setApart_inf( 
	            				new Lugar_control().get_lugar(String.valueOf(tabla.getValueAt(row, 1)))
	            				);
	            		if(swit==1){
	            		Usuario.setId_habitante(
	            				Integer.parseInt(String.valueOf(tabla.getValueAt(row, 3)))
	            				);
	            		}
	            		dispose();
		            	
	                	JOptionPane.showMessageDialog(null, "Persona Seleccionado");
	            	
	            	} catch (IllegalStateException e1) {
						e1.printStackTrace();
					}
	          }//
	        }
	      });
	    }
	    catch (IllegalStateException e)
	    {
	      e.printStackTrace();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    Box box = Box.createHorizontalBox(); 
	    JScrollPane barra = new JScrollPane(tabla);
	    box.add(barra);
	    box.setBorder(BorderFactory.createTitledBorder(""));
	    return box;
	  }
	
	public Persona get_persona(){
		this.dispose();
		return persona;
	}
}
