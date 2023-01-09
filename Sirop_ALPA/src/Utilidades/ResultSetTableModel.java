package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel
  extends AbstractTableModel
{
  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;
  private ResultSetMetaData metaData;
  private int numberOfRows;
  private boolean connectedToDatabase = false;
  
  public ResultSetTableModel()
    throws IllegalStateException, SQLException
  {
    DBconeccion conection = new DBconeccion();
    
    this.statement = conection.getcEnlace().createStatement();
    
    this.connectedToDatabase = true;
  }
  
  public ResultSetTableModel(String query)
    throws IllegalStateException, SQLException
  {
    DBconeccion conection = new DBconeccion();
    
    this.statement = conection.getcEnlace().createStatement();
    
    this.connectedToDatabase = true;
    
    setQuery(query);
  }
  
  public ResultSetTableModel(String driver, String url, String query)
    throws SQLException, ClassNotFoundException
  {
    Class.forName(driver);
    
    this.connection = DriverManager.getConnection(url);
    
    this.statement = this.connection.createStatement(
      1004, 
      1007);
    
    this.connectedToDatabase = true;
    
    setQuery(query);
  }
  
  public Class getColumnClass(int column)
    throws IllegalStateException
  {
    if (!this.connectedToDatabase) {
      throw new IllegalStateException("Not Connected to Database");
    }
    try
    {
      String className = this.metaData.getColumnClassName(column + 1);
      
      return Class.forName(className);
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    return Object.class;
  }
  
  public String getColumnName(int column)
    throws IllegalStateException
  {
    if (!this.connectedToDatabase) {
      throw new IllegalStateException("Not Connected to Database");
    }
    try
    {
      return this.metaData.getColumnName(column + 1);
    }
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
    return "";
  }
  
  public int getColumnCount()
  {
    if (!this.connectedToDatabase) {
      throw new IllegalStateException("Not Connected to Database");
    }
    try
    {
      return this.metaData.getColumnCount();
    }
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
    return 0;
  }
  
  public int getRowCount()
  {
    if (!this.connectedToDatabase) {
      throw new IllegalStateException("Not Connected to Database");
    }
    return this.numberOfRows;
  }
  
  public Object getValueAt(int row, int column)
  {
    if (!this.connectedToDatabase) {
      throw new IllegalStateException("Not Connected to Database");
    }
    try
    {
      this.resultSet.absolute(row + 1);
      return this.resultSet.getObject(column + 1);
    }
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
    return "";
  }
  
  public void setQuery(String query)
    throws SQLException, IllegalStateException
  {
    if (!this.connectedToDatabase) {
      throw new IllegalStateException("Not Connected to Database");
    }
    this.resultSet = this.statement.executeQuery(query);
    
    this.metaData = this.resultSet.getMetaData();
    
    this.resultSet.last();
    this.numberOfRows = this.resultSet.getRow();
    
    fireTableStructureChanged();
  }
  
  /* Error */
  public void disconnectFromDatabase()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 27	Utilidades/ResultSetTableModel:connectedToDatabase	Z
    //   4: ifne +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: getfield 42	Utilidades/ResultSetTableModel:statement	Ljava/sql/Statement;
    //   12: invokeinterface 155 1 0
    //   17: aload_0
    //   18: getfield 71	Utilidades/ResultSetTableModel:connection	Ljava/sql/Connection;
    //   21: invokeinterface 158 1 0
    //   26: goto +24 -> 50
    //   29: astore_1
    //   30: aload_1
    //   31: invokevirtual 107	java/sql/SQLException:printStackTrace	()V
    //   34: aload_0
    //   35: iconst_0
    //   36: putfield 27	Utilidades/ResultSetTableModel:connectedToDatabase	Z
    //   39: goto +16 -> 55
    //   42: astore_2
    //   43: aload_0
    //   44: iconst_0
    //   45: putfield 27	Utilidades/ResultSetTableModel:connectedToDatabase	Z
    //   48: aload_2
    //   49: athrow
    //   50: aload_0
    //   51: iconst_0
    //   52: putfield 27	Utilidades/ResultSetTableModel:connectedToDatabase	Z
    //   55: return
    // Line number table:
    //   Java source line #182	-> byte code offset #0
    //   Java source line #183	-> byte code offset #7
    //   Java source line #188	-> byte code offset #8
    //   Java source line #189	-> byte code offset #17
    //   Java source line #191	-> byte code offset #29
    //   Java source line #193	-> byte code offset #30
    //   Java source line #197	-> byte code offset #34
    //   Java source line #196	-> byte code offset #42
    //   Java source line #197	-> byte code offset #43
    //   Java source line #198	-> byte code offset #48
    //   Java source line #197	-> byte code offset #50
    //   Java source line #199	-> byte code offset #55
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	ResultSetTableModel
    //   29	2	1	sqlException	SQLException
    //   42	7	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   8	26	29	java/sql/SQLException
    //   8	34	42	finally
  }
}
