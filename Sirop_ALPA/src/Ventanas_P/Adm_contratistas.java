package Ventanas_P;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controladores.Action_log;
import Controladores.Contratista_control;
import Entidades.Contratista;
import Entidades.log_accion;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Contratista_formulario;

public class Adm_contratistas extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private log_accion log_action;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private Contratista contratista;
	
	
	public Adm_contratistas() {
		log_action = new log_accion();
		
		this.setTitle("ADMINISTRADOR DE CONTRATISTAS");
        this.setSize(700,500);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_contend());
        this.setVisible(true);
	}
	
	private JPanel get_contend(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(), BorderLayout.CENTER);
		panel.add(get_funciones(),BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.add(get_boton_add());
		
		return panel;
	}
	
	private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_Contratistas();
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
						contratista = new Contratista_control().get_contratista(
								Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0)))
								);
	            		
						new Contratista_formulario(contratista);
						resultado.setQuery(new ManejadorReportes().get_query_contratista());
					      
	             	} catch (IllegalStateException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
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
				new Contratista_formulario(contratista);
				try {
					resultado.setQuery(new ManejadorReportes().get_query_contratista());
				} catch (IllegalStateException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		return boton;
	}	
	
	protected void set_log(){
		log_action.setFecha(new Date(new Fecha().getTime()));
		log_action.setHora(new Time(new Fecha().getTime()));
		new Action_log().set_insert(log_action);
	}

}
