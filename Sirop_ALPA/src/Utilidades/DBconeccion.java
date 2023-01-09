package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Entidades.Usuario;

public class DBconeccion
{
  private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private String db = "sirop_alpa";
  private String login = "root";
  private String clave = "0287";
  private String URL = "";
  private Connection cEnlace = null;
  
  public DBconeccion()
  {
    setConfiguracionDb();
    abreConeccionDb();
  }
  
  public void abreConeccionDb()
  {
    try
    {
      Class.forName(this.JDBC_DRIVER);
      this.cEnlace = DriverManager.getConnection(this.URL, this.login, this.clave);
      this.cEnlace.createStatement();
      System.out.println("CONECXION CON LA BASE DE DATOS EXITOSA");
    }
    catch (Exception e)
    {
      System.out.println("No conecto");
      JOptionPane.showMessageDialog(null, "El Usuario No esta registrado!","Error DB",JOptionPane.ERROR_MESSAGE, null);
    }
  }
  
  public void cierra_Coneccion(){
	  try {
		this.cEnlace.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public Connection getcEnlace()
  {
    return this.cEnlace;
  }
  
  public void setcEnlace(Connection cEnlace)
  {
    this.cEnlace = cEnlace;
  }
  
  private void setConfiguracionDb()
  {
    String[] config = new LectorConfigDb().ObtenerConfiguaracion();
    setLogin(config[0]);
    setURL(config[1]);
    //setClave(config[2]);
  }
  
  public String getJDBC_DRIVER()
  {
    return this.JDBC_DRIVER;
  }
  
  public void setJDBC_DRIVER(String jDBC_DRIVER)
  {
    this.JDBC_DRIVER = jDBC_DRIVER;
  }
  
  public String getDb()
  {
    return this.db;
  }
  
  public void setDb(String db)
  {
    this.db = db;
  }
  
  public String getLogin()
  {
    return this.login;
  }
  
  public void setLogin(String login)
  {
    this.login = login;
  }
  
  public String getClave()
  {
    return this.clave;
  }
  
  public void setClave(String clave)
  {
    this.clave = clave;
  }
  
  public String getURL()
  {
    return this.URL;
  }
  
  public void setURL(String uRL)
  {
    this.URL = uRL;
  }
}
