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
import Entidades.Lugar;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;

public class selector_lugar extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private Lugar lugar;
	private int opc;
	
	
	public selector_lugar(int opc){
		this.opc = opc;
		this.setTitle("Lugar Selector");
		this.setSize(700, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(getTabla());
		this.setVisible(true);
	}
	
	public selector_lugar() {
		this.setTitle("Lugar Selector");
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
	      if(opc != 0){	
	    	  resultado = new ManejadorReportes().getReporte_Lugares_filt(opc);
	      }else{
	    	  resultado = new ManejadorReportes().getReporte_Lugares();
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
						
	            		lugar = new Lugar_control().get_lugar(
	            				Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0)))
	            				);
	                	dispose();
		            	JOptionPane.showMessageDialog(null, "Lugar Seleccionado");
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
	
	public Lugar get_lugar(){
		this.dispose();
		return lugar;
	}
}
