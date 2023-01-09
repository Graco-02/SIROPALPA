/**
 * 
 */
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Add_lista_negra;
import Ventanas_S.Add_visita;
import Controladores.Action_log;
import Controladores.Bloqueo_control;
import Controladores.Reg_Visitas_control;
import Entidades.bloqueo;
import Entidades.log_accion;
import Formularios.busqueda_visita_frm;

/**
 * @author FFerr
 *
 */
public class Gestor_Betados extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private JButton boton_betados;
	private bloqueo betado;
	protected log_accion log_action;

	/**
	 * 
	 */
	public Gestor_Betados() {
		log_action = new log_accion();
		
		this.setTitle("GESTOR DE BETADOS");
        this.setSize(1000,700);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_content());
        this.setVisible(true);
	}
	
	private JPanel get_content(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(get_Tabla(), BorderLayout.CENTER);
		panel.add(get_funciones(),BorderLayout.EAST);
		return panel;
	}

	private JPanel get_Tabla(){
	
		JPanel panel = new JPanel();
	
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(), BorderLayout.CENTER);
	
	
		return panel;
	}
	
	private JPanel get_funciones(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_Lista_negra());
		panel.add(get_salir());
		return panel;
	}
	
	private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_Betados();
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
	            		betado = new Bloqueo_control().get_betado( 
	            				Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0))));
	            		
	            		new Add_lista_negra(betado);
	            		resultado.setQuery(new ManejadorReportes().get_query_betados());
	            		
	            	} catch (IllegalStateException e1) {
	            		
	            	} catch (SQLException e1) {
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
	
	private JButton get_boton_Lista_negra(){
		 
    	boton_betados = new JButton("BETADOS", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/lisn.jpg", 50, 50)));
    	boton_betados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
					try {/*
						log_action.setLog("Acceso al sistema betados");
						log_action.setFecha(new Fecha().get_fecha_sql());
						log_action.setHora(new Fecha().get_hora_sql());
						set_log();
						*/
						
						new Add_lista_negra();
						resultado.setQuery(new ManejadorReportes().get_query_betados());
					} catch (IllegalStateException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
    	
    	return boton_betados;
   }
	
	 private JButton get_salir(){
			JButton boton = new JButton("Salir",
					new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/salir.png", 100, 100)));
	     	boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			return boton;
		}
	 
	 protected void set_log(){
			new Action_log().set_insert(log_action);
		}
	 
}
