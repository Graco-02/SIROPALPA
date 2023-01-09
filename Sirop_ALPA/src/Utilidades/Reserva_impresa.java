package Utilidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;

import Entidades.Reservas;

public class Reserva_impresa {
	private Reservas rs;
	private String[] lista;
	
	public Reserva_impresa(Reservas reserva,String[] txt) {
		try {
			rs = reserva;
			
			if(txt != null){
				lista = txt;
			}
			
			imprimeArchivo();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public class ObjetoDeImpresion implements Printable{
	    
		public ObjetoDeImpresion() {}
	    
	    public int print(Graphics g, PageFormat f, int pageIndex){
	      if (pageIndex == 0){
	        int x,y;
	        x= 30;
	        y=100;
	        
	        
	        
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Reserva NO. ________________", x, y);
	    
	    	g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
	    	g.drawString(String.valueOf(rs.getId()), x+150, y-5);
	    
	    	y = 120;
	    	
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Fecha de Inicio. _________________________", x, y);
	    
	    	g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
	    	g.drawString(rs.getFecha_reserva().toString(), x+130, y-5);
	    	
	    
	    	y = 140;
	    	
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Hora Inicio. ___________________________", x, y);
	    
	    	g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
	    	g.drawString(rs.getHora_inicio().toString(), x+130, y-5);
	    	
	    	y = 160;
	    	
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Hora Fin. ___________________________________", x, y);
	    
	    	g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
	    	g.drawString(rs.getHora_fin().toString(), x+130, y-5);
	    	    
	    	y = 180;
	    	
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Solicitante. ______________________________________", x, y);
	    
	    	g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
	    	g.drawString(rs.getNombre_solicitante(), x+130, y-5);
	    	        
	    	y = 220;
	    	
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Areas Reservadas", x, y);
	    
	    	g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
	    	int cont = 1;
	    	y+=10;
	    	int yb =y;
	    	for(int i = 0;i<rs.get_lista_reservaciones().size();i++){
	    		if(cont > 3){
	    			x+=130;
	    			cont =1;
	    			y = yb;
	    		}
	    		
	        	g.drawString(rs.get_lista_reservaciones().get(i), x, y+=15);
	        	cont++;
	    	}
	    	
	    	y+=50;
	    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));// cambio la fuente
	    	g.drawString("Invitados.", x, y);
	    	
	    	cont = 1;
	    	y+=10;
	    	
	    	if(lista != null){
	    		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));// cambio la fuente
		    	
		    	for(int i = 0;i<lista.length;i++){
		    		if(cont > 5){
		    			x+=200;
		    			cont =1;
		    			y = yb;
		    		}
		    		System.out.println(lista[i]+i);
		        	g.drawString(lista[(i)], x, y+=15);
		        	cont++;
		    	}
	    	}
	    	
	        return 0;
	      }
	      
	      return 1;
	    
	    }
	  }
	  
	  public void imprimeArchivo(){
	    PrinterJob job = PrinterJob.getPrinterJob();
	    job.setPrintable(new ObjetoDeImpresion());
	    if (job.printDialog()) {
	      try
	      {
	        job.print();
	      }
	      catch (PrinterException e)
	      {
	        System.out.println(e);
	      }
	    }
	  }
	  
	  public void imprimeFast()
	  {
	    PrinterJob job = PrinterJob.getPrinterJob();
	    PageFormat pageFormat = job.defaultPage();
	    Paper paper = new Paper();
	    paper.setSize(600.0D, 800.0D);
	    paper.setImageableArea(20.0D, 20.0D, 650.0D, 750.0D);
	    pageFormat.setPaper(paper);
	    pageFormat.setOrientation(1);
	    
	    job.setPrintable(new ObjetoDeImpresion(), pageFormat);
	    try
	    {
	      job.print();
	    }
	    catch (PrinterException e)
	    {
	      System.out.println(e);
	    }
	  }

	  private String get_codigo(String id){
		 String cod="";
		  for(int i=0;i<(6-id.length());i++){
			  cod+="0";
		  }
		  return cod+id;
	  }
	  
}
