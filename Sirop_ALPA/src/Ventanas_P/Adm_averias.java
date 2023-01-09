package Ventanas_P;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Averias_formulario;
import Controladores.Averias_control;
import Entidades.Averias;

public class Adm_averias extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Averias averia;

	private ResultSetTableModel resultado;

	private JTable tabla;
	
	public Adm_averias() {
		this.setTitle("ADMINISTRADOR DE AVERIAS");
        this.setSize(700,500);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_panel_cont());
        this.setVisible(true);
	}
	
	private JPanel get_panel_cont(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(), BorderLayout.CENTER);
		panel.add(get_panel_funciones(),BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_panel_funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_add());
		panel.add(get_boton_contra());
		panel.add(get_boton_Limpiar());
		return panel;
	}
	
	private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_Averias();
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
						
	            		averia = new Averias_control().get_averia(
								Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0)))
								);
	            		
						new Averias_formulario(averia);
						set_limpiar();
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
	
	private JButton get_boton_add(){
		JButton boton = new JButton("ADD", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/adduser.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Averias_formulario(averia);
				set_limpiar();
			}
		});
		return boton;
	}
	
	private JButton get_boton_contra(){
		 
	     JButton boton_averias_contratistas = new JButton("CONTRATISTAS", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/contratista.jpg", 50, 50)));
	    	boton_averias_contratistas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	    			new Adm_contratistas();
				}
			});
	    	
	    	return boton_averias_contratistas;
	   }
	
	private JButton get_boton_Limpiar(){
    	JButton boton = new JButton("Limpiar",
    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/limpiar1.png", 50, 50)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					set_limpiar();
			}
		});
    	
    	return boton;
    }
	
	private void set_limpiar(){
		try {
			resultado.setQuery(new ManejadorReportes().get_query_averias());
			averia = null;
			
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
	}
}
