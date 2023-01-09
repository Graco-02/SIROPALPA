package Utilidades;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha
  extends Date
{
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  private int dia;
  private int mes;
  private int ano;
  private String hora;
  
  public Fecha(){
    setDia(getDate());
    setMes(getMonth() + 1);
    setAno(getYear() - 100 + 2000);
    
    hora = this.getHours()+":"+this.getMinutes()+":"+this.getSeconds();
  }
  
  public java.sql.Date get_fecha_sql(){
	  return new java.sql.Date(new Date().getTime());
  }
  
  public Time get_hora_sql(){
	  return new Time(new Date().getTime());
  }
  
  public String getFechaSystemaYYMMDD()
  {
    String fecha = String.valueOf(this.ano) + "-" + String.valueOf(this.mes) + "-" + String.valueOf(this.dia);
    
    return fecha;
  }
  
  public String getFechaSystemaDDMMYY()
  {
    String fecha = String.valueOf(this.dia) + "-" + String.valueOf(this.mes) + "-" + String.valueOf(this.ano);
    
    return fecha;
  }
  
  
  public int getTiempo_Transcurido_DIAS_YYYYMMDD(String fecha)
  {
    String[] fechaComoArray = fecha.split("-");
    
    int year = Integer.parseInt(fechaComoArray[0]);
    int mes  = Integer.parseInt(fechaComoArray[1]);
    int dia  = Integer.parseInt(fechaComoArray[2]);
    
    int resultado = ((this.ano - year) * 12 + (this.mes - mes)) * 30 + (this.dia - dia);
    
    return resultado;
  }

  public int getTiempo_Transcurido_DIAS_YYYYMMDD(String fecha,String fecha2)
  {
    String[] fechaComoArray = fecha.split("-");
    
    int year = Integer.parseInt(fechaComoArray[0]);
    int mes  = Integer.parseInt(fechaComoArray[1]);
    int dia  = Integer.parseInt(fechaComoArray[2]);
  
    fechaComoArray = fecha2.split("-");
    
    int year2 = Integer.parseInt(fechaComoArray[0]);
    int mes2  = Integer.parseInt(fechaComoArray[1]);
    int dia2  = Integer.parseInt(fechaComoArray[2]);
  
    
    int resultado = ((year2 - year) * 12 + (mes2 - mes)) * 30 + (dia2 - dia);
    
    return resultado;
  }
  
  public String suma_meses(String meses){
	  int mm = Integer.parseInt(meses);
	  String[] fechaComoArray = getFechaSystemaDDMMYY().split("-");
	    
	    int year = Integer.parseInt(fechaComoArray[2]);
	    int mes = Integer.parseInt(fechaComoArray[1]);
	    int dia = Integer.parseInt(fechaComoArray[0]);
	    
	    mes += mm;
	    year += mes / 12;
	    mes -= (12*(mes/12));
	    
	    return year+"-"+mes+"-"+dia;
	      
  }  
  
  public int getDia()
  {
    return this.dia;
  }
  
  public void setDia(int dia)
  {
    this.dia = dia;
  }
  
  public int getMes()
  {
    return this.mes;
  }
  
  public void setMes(int mes)
  {
    this.mes = mes;
  }
  
  public int getAno()
  {
    return this.ano;
  }
  
  public void setAno(int ano)
  {
    this.ano = ano;
  }
  
  public String getHora(){
    return this.hora;
  }
  
  public void setHora(String hora)
  {
    this.hora = hora;
  }
}
