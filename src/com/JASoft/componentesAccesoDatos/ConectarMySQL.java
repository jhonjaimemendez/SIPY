/**
 * Clase: ConecterMySQL
 * 
 * @version  1.1
 * 
 * @since 19-10-2005
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentesAccesoDatos;

import java.net.InetAddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;


/**
 * Esta clase permite conectar a una base de datos Mysql y ademas ejecutar sentencias DML
 */


final public class ConectarMySQL {
 
  //** Declaracion de variables
  private Connection conexion;
  
  private boolean generarAuditorias ;
  
  private String nombreTerminal;
  
  private String nombreUsuario;
  
  protected Statement sentencia;
   
  
  /** 
   *Constructor general que se conecta a la base de datos dependiendo de los parametros
   *
   *@param servidorNombre Nombre del servidor o direccion IP
   *@param nombreBD  nombre de la base de datos
   *@param usuario Usuario autorizado
   *@param  password
   *
   */
  
  public ConectarMySQL(String servidorNombre,String nombreBD,String usuario,String password) throws Exception{
  	 
  	 //** Se carga el driver para conectarse a la base de datos
  	 
  	 try {
  		 Class.forName("com.mysql.jdbc.Driver").newInstance();

  	 }catch  (Exception e) {
  		 
  	 	System.out.println("Error"+e);
  	 }
  	 
  	    
    // Se conecta a la base de datos
    // Se crea un URL hacia la maquina y la base de datos
 	String url= "jdbc:mysql://" + servidorNombre + "/" + nombreBD; 
    
    //se crea la conexion a la base de datos
    conexion=DriverManager.getConnection(url,usuario,password);
    
    conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
   
    conexion.setAutoCommit(false);
   
    sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    
      
   
  }
  
  //Otros metodos
  
  
  /** 
   * Busca los parametros de configuracin
   */
  final public HashMap <String,String> buscarParametros() {
    
       HashMap <String,String> resultadoHashMap = new HashMap <String,String>();
    
    	// Se busca los parametros
    	final String sentenciaSQL = "Select NomParam,Valor "+
 		                            "From  Parametros "+
 		                            "Where CodParam Not in ('21','22')";
 		                                     
 		
 	    generarAuditorias = false;
 		 
 		 try {
 		 
 		   // Se realiza la consulta en la base de datos para buscar la placa
 		   ResultSet resultado = sentencia.executeQuery(sentenciaSQL);
 		   
 		   resultado.next();
 		   
 		   String resultadoAuditorias = resultado.getString(2);
 		   
 		   resultadoHashMap.put(resultado.getString(1),resultadoAuditorias);
      	   
      	   if (resultadoAuditorias.equals("true"))
      	      
      	      generarAuditorias = true;
      	      
 		  
           while (resultado.next()) //Se almacenan los resultados en el objeto HashMap
               
               resultadoHashMap.put(resultado.getString(1),resultado.getString(2));
      	   
 		       	   
  		}catch (Exception e) {
  			
  			System.out.println("Error "+e);
  		}
  		
  		return resultadoHashMap;
  		
  }
  
  
  /** 
   *Permite ejecutar una sentencia SQL de tipo DML
   *
   *@param sentenciaSQL Sentencia SQL que pueder ser un Insert, Update, Delete
   *@param fechaServidor  Fecha del servidor
   *@param conMySQL Conexion de la base d edato
   *
   */
  final public void ejecutaSentencia(String sentenciaSQL, String fechaServidor , ConectarMySQL conMySQL) throws Exception {
  
  	 // Se ejecutan las sentencias DML que llegan como parametro
  	 sentencia.execute(sentenciaSQL);
  	 
  	 //Se valida de que se halla configurado las auditorias y se general las auditorias
  	 if (generarAuditorias) {
  	 
  	 	String tipoSentencia;
  	 	String tabla ="";
  	 	
  	 	sentenciaSQL = sentenciaSQL.replace('\'',' ');
  	 	
  	   	switch (sentenciaSQL.charAt(0)) {
  	 	  
  	 	 case 'I': 
  	 	    tipoSentencia	= "INSERCION";
  	 	    tabla = sentenciaSQL.substring(12,sentenciaSQL.indexOf("Values")-1).toUpperCase();
  	 	 break;
  	 	 
  	 	 case 'D':   
  	 	    tipoSentencia	= "ELIMINACION";
  	 	    tabla = sentenciaSQL.substring(11,sentenciaSQL.indexOf("Where")-1).toUpperCase();
  	 	 break;
  	 	 
  	 	 default :
  	 	    tipoSentencia	= "ACTUALIZACION";
  	 	    tabla = sentenciaSQL.substring(7,sentenciaSQL.lastIndexOf("Set")-1).toUpperCase();
  	 	 break;
  	 	}  
  	 	
  	 	if (nombreTerminal == null)
  	 	    nombreTerminal = getObtenerNombreMaquina();
  	 	    
  	 	StringBuffer sentenciaSQLAuditoria = 	new StringBuffer("Insert Into AuditoriasTransacciones values(null,'"+nombreUsuario+"','Tramites','"+fechaServidor+"','"+nombreTerminal+"','"+
  	 	                                                          tabla+"','"+ tipoSentencia + "','"+sentenciaSQL+"')");  

                
  	     //Se inserta la sentecia	                                
  	    sentencia.execute(sentenciaSQLAuditoria.toString());
  		
  	 }
  	 

  }
  
  /** 
   *Permite ejecutar una sentencia SQL de tipo DML
   *
   *@param sentenciaSQL Sentencia SQL que pueder ser un Insert, Update, Delete
   *
   */
  
   public void ejecutaSentencia(String sentenciaSQL) throws Exception {
     
      // Se ejecutan las sentencias DML que llegan como parametro
  	  sentencia.execute(sentenciaSQL);
  }
  
  
  /** 
   *Permite buscar un/unos registro(s) en la base de datos
   *
   *@param sentenciaSQL Sentencia SQL para buscar un registro
   *@return ResultSet Registro encontrado
   *
   */

  public  ResultSet buscarRegistro (String sentenciaSQL) throws Exception {
  	
  	// Se hace la busqueda del registro en la B.D
  	ResultSet resultado = sentencia.executeQuery(sentenciaSQL);

  
  	return resultado;
  
  }	
  
  
  /** 
   *Permite configurar el nombre de usuario para las auditorias
   *
   *@param nombreUsuario Nombre de usuario 
   *
   */

  public  void setNombreUsuario (String nombreUsuario) throws Exception {
  	
      this.nombreUsuario = nombreUsuario; 
  }	
  
  
  /** 
   *Permite validar si un registro se encuentra en la  base de datos
   *
   *@param nombreTabla Nombre de la tabla
   *@return boolean true: Se encontro false: No encontrado
   *
   */
   public boolean validarRegistro (String nombreTabla,String condicion)  {
  	
  	  boolean resultadoBoolean = false;
  	  
  	  try {
  	        String sentenciaSQL = "Select 'x' "+ 
  	                              "From "+ nombreTabla +
  	                              " Where "+ condicion;
  	              
  	       	// Se hace la busqueda del registro en la B.D
		  	ResultSet resultado = sentencia.executeQuery(sentenciaSQL);
		  	
		  	if (resultado!=null)
		  	 
		  	  
		  	  if (resultado.next())
		        
		         resultadoBoolean = true;
		      
		       
	  } catch (Exception e) {}	
	  
	  
	  
	   return resultadoBoolean;       
   }	
   
  
  /** 
   *Devuelve la direccion IP de una maquina
   *
   *@return String Direccion IP
   *
   */
  public static String getObtenerNombreMaquina() {
  	 
  	 String resultado = null;
  	 
  	 try {
  	 
  	    InetAddress addr = InetAddress.getLocalHost();
  	   
  	    resultado =  addr.getHostName();
  	 
  	 } catch (Exception e)  {
  	    
  	    System.out.println("Error " +e)	;
  	 	
  	 } 
  	 
  	 return resultado;
  }
  
  
  /** 
   * Devuelve el objeto que permite la conexion a la base de datos
   *
   * @return Connection Conexion a la base de datos
   *
   */
  
   public Connection getConexion() {
  	
  	 return conexion;
   }
  
  
  /** 
   * Devuelve un objeto de tipo Statement para realizar sentencias SQL contra la BD
   *
   * @return Statement Sentencia de conexion
   *
   */
  
  public Statement getSentencia(){
  	
  	 return sentencia;
  	
  }
  
  
  /** 
   * Permite hacer los cambios permanentes en la BD
   *
   */
  
  public void commit() throws Exception {
  	
  	 conexion.commit();
  }
  
  
  
  /** 
   * Permite deshacer  cambios en la BD antes del ultimo commit
   *
   */
  
  public void rollback() {
  	
  	 try {
  	 	
  	 	conexion.rollback();
  	 	
  	 } catch (Exception e) {
  	 	
  	 	System.out.println("Error "+ e);
  	 }
  }
  
  	
}


