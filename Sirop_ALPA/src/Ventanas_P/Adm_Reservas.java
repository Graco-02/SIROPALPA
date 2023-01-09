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

import Controladores.Action_log;
import Controladores.Reservas_control;
import Entidades.Reservas;
import Entidades.Usuario;
import Entidades.log_accion;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.reserva_formulario;

public class Adm_Reservas extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected log_accion log_action;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private Reservas rs;
	
	public Adm_Reservas() {
		log_action = new log_accion();
		
		this.setTitle("GESTOR DE RESERVAS");
        this.setSize(1000,700);
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
		panel.add(get_panel_funciones(), BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel get_panel_funciones(){
		JPanel panel = new JPanel(new GridLayout(3, 0));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_add());
		return panel;
	}
	
	 private Box getTabla()
	  {
	    try
	    {
	      if(Usuario.getLlave() == 0){
	    	  resultado = new ManejadorReportes().getReporte_Reservas_adm();
	      }else{
	    	  resultado = new ManejadorReportes().getReporte_Reservas();
	      }
	    	
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            rs = new Reservas_control().get_reserva_by_ID(
	            		Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0)))
	            		);
	            new reserva_formulario(rs);
	            
	            try {
					resultado.setQuery(new ManejadorReportes().get_query_reservas());
				} catch (IllegalStateException | SQLException e1) {
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
			
			if(Usuario.getLlave() != 0){//solo si el usuario es administrador puede usar las funciones
				boton.setEnabled(false);
			}
			
			boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {	
					new reserva_formulario(rs);
					try {
						resultado.setQuery(new ManejadorReportes().get_query_reservas());
					} catch (IllegalStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			return boton;
		}
	 
	protected void set_log(){
		log_action.setFecha(new Fecha().get_fecha_sql());
		log_action.setHora(new Fecha().get_hora_sql());
		
		new Action_log().set_insert(log_action);
    }
}
