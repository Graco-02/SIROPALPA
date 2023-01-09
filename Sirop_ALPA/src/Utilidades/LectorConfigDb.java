package Utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LectorConfigDb
{
  public String[] ObtenerConfiguaracion()
  {
    String[] configuracioDb = new String[20];
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    try
    {
      File archivo1 = new File("DBConfig.txt");
      fr = new FileReader(archivo1);
      BufferedReader br1 = new BufferedReader(fr);
      int indx = 0;
      String linea;
      while ((linea = br1.readLine()) != null)
      {
        configuracioDb[(indx++)] = linea;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    try
    {
      if (fr != null) {
        fr.close();
      }
    }
    catch (Exception e2)
    {
      e2.printStackTrace();
    }
    try
    {
      if (fr != null) {
        fr.close();
      }
    }
    catch (Exception e2)
    {
      e2.printStackTrace();
    }
    try
    {
      if (fr != null) {
        fr.close();
      }
    }
    catch (Exception e2)
    {
      e2.printStackTrace();
    }
    return configuracioDb;
  }
}
