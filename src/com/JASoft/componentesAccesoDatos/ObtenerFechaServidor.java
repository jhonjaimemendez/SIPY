package com.JASoft.componentesAccesoDatos;
/**
 * Clase: ObtenerFechaServidor
 * 
 * @version  1.0
 * 
 * @since 01-05-2006
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

 
import java.sql.ResultSet;


/**
 * Esta clase permite obtener la fecha del servidor de base de datos
 */

final public class ObtenerFechaServidor {
	
	
	 private static String a�o ;
	 private static String mes ;
	 private static String dia ;
	 private static String fechaSistemaFormatoAplicacion;
	
	
	/**
	 * Devuelve la fecha del sistema del servidor de base de datos
	 *
	 *@param conMySQL Conexion de la base de datos con la Interfaz
	 *
	 *@return String Fecha en formato caracter
	 */
	
	final static public String getObtenerFechaCompleta(ConectarMySQL conMySQL) {
	
	    final String setenciaSQL = "Select SysDate()";
	    String resultadoFecha = null;
		                   
		try {   
		     // Se busca el nit
		     ResultSet resultado = conMySQL.buscarRegistro(setenciaSQL);
		                                        
		 	 // Se verifica si tiene datos
		 	 if (resultado!=null)	
                
                if (resultado.next()) 
		 	    
		 	      	 resultadoFecha = resultado.getString(1);
			 	   	  
		 	 
		 } catch (Exception e) {}
		 
		 return resultadoFecha;
 	
	}
	
	
	/**
	 * Devuelve la fecha del sistema del servidor de base de datos en formato aaaa-mm-dd
	 *
	 *@param conMySQL Conexion de la base de datos con la Interfaz
	 *
	 *@return String Fecha en formato caracter
	 */
	
	final static public String getObtenerFecha(ConectarMySQL conMySQL) {
	
        if (fechaSistemaFormatoAplicacion == null) {
	 	
			    final String setenciaSQL = "Select SysDate()";
			                       
				try {   
				     // Se busca el nit
				     ResultSet resultado = conMySQL.buscarRegistro(setenciaSQL);
				                                        
				 	 // Se verifica si tiene datos
				 	 if (resultado!=null)	
		                
		                if (resultado.next()) 
				 	    
				 	      	 fechaSistemaFormatoAplicacion = resultado.getString(1).substring(0,10);
					 	   	  
				 	 
				 } catch (Exception e) {}
		 
		 }
		 
		 return fechaSistemaFormatoAplicacion;
 	
	}
	
    	
	/**
	 * Devuelve el a�o actual
	 *
	 *@param conMySQL Conexion de la base de datos con la Interfaz
	 *
	 *@return String A�o actual
	 */
	
	final static public String getObtenerA�o(ConectarMySQL conMySQL) {
	
	    if (a�o == null) {
		    
		    final String setenciaSQL = "Select SysDate()";
		                       
			try {   
			     // Se busca el nit
			     ResultSet resultado = conMySQL.buscarRegistro(setenciaSQL);
			                                        
			 	 // Se verifica si tiene datos
			 	 if (resultado!=null)	
	                
	                if (resultado.next()) 
			 	    
			 	      	 a�o = resultado.getString(1).substring(0,4);
				 	   	  
			 	 
			 } catch (Exception e) {}
			 
		}	 
		 
		 return a�o;
 	
	}
	
	
	/**
	 * Devuelve el mes actual
	 *
	 *@param conMySQL Conexion de la base de datos con la Interfaz
	 *
	 *@return String Mes actual
	 */
	
	final static public String getObtenerMes(ConectarMySQL conMySQL) {
	
	    if (mes == null) {
		    
		    final String setenciaSQL = "Select SysDate()";
		                       
			try {   
			     // Se busca el nit
			     ResultSet resultado = conMySQL.buscarRegistro(setenciaSQL);
			                                        
			 	 // Se verifica si tiene datos
			 	 if (resultado!=null)	
	                
	                if (resultado.next()) 
			 	    
			 	      	 mes = resultado.getString(1).substring(5,7);
				 	   	  
			 	 
			 } catch (Exception e) {}
			 
		}	 
		 
		 return mes;
 	
	}
	
	
	/**
	 * Devuelve el dia actual
	 *
	 *@param conMySQL Conexion de la base de datos con la Interfaz
	 *
	 *@return String Dia actual
	 */
	
	final static public String getObtenerDia(ConectarMySQL conMySQL) {
	
	    if (dia == null) {
		    
		    final String setenciaSQL = "Select SysDate()";
		                       
			try {   
			     // Se busca el nit
			     ResultSet resultado = conMySQL.buscarRegistro(setenciaSQL);
			                                        
			 	 // Se verifica si tiene datos
			 	 if (resultado!=null)	
	                
	                if (resultado.next()) 
			 	    
			 	      	 dia = resultado.getString(1).substring(8,10);
				 	   	  
			 	 
			 } catch (Exception e) {}
			 
		}	 
		 
		 return dia;
 	
	}
	
	
}