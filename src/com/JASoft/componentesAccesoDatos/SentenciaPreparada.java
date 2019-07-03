/**
 * Clase: SentenciaPreparada
 * 
 * @version  1.1
 * 
 * @since 19-11-2010
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentesAccesoDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 *  Esta clase mantendra en memoria las sentencias preparada para la ejecucion de procedimiento almacenado;
 */

public class SentenciaPreparada {
	
	private static PreparedStatement ejecutarProcedimietoAlmacenadoClientes;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoProveedores;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoProductos;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoCompras;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoCategorias;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoMarcas;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoVentas;
	private static PreparedStatement ejecutarProcedimietoAlmacenadoAbonos;
	
	
	

   /*
    *  Metodo que instancia una sola vez la sentencia, la conserve en memoria hasta que se baja la
    *  aplicacion y el usuario la puede ejecutar n veces
    */
	public static PreparedStatement getEjecutarProcedimietoAlmacenadoCliente(Connection conexion) throws SQLException  {
		
		if (ejecutarProcedimietoAlmacenadoClientes == null)
			
			ejecutarProcedimietoAlmacenadoClientes = conexion.prepareStatement("Call ClientesTransacciones(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		return ejecutarProcedimietoAlmacenadoClientes;
	}


	public static PreparedStatement getEjecutarProcedimietoAlmacenadoProveedores(Connection conexion) throws SQLException {
		
	if (ejecutarProcedimietoAlmacenadoProveedores == null)
			
		ejecutarProcedimietoAlmacenadoProveedores = conexion.prepareStatement("Call ProveedoresTransacciones(?,?,?,?,?,?,?,?,?,?,?)");
	
		return ejecutarProcedimietoAlmacenadoProveedores;
	}


	public static PreparedStatement getEjecutarProcedimietoAlmacenadoProductos(Connection conexion) throws SQLException {
		
		if (ejecutarProcedimietoAlmacenadoProductos == null)
			
			ejecutarProcedimietoAlmacenadoProductos = conexion.prepareStatement("Call ProductosTransacciones(?,?,?,?,?,?,?,?)");
		
		return ejecutarProcedimietoAlmacenadoProductos;
	}


	public static PreparedStatement getEjecutarProcedimietoAlmacenadoCompras(Connection conexion) throws SQLException {
		
		if (ejecutarProcedimietoAlmacenadoCompras == null)
			
			ejecutarProcedimietoAlmacenadoCompras = conexion.prepareStatement("Call ComprasTransacciones(?,?,?,?,?,?,?,?,?,?,?)");
		
		return ejecutarProcedimietoAlmacenadoCompras;
	}




	public static PreparedStatement getEjecutarProcedimietoAlmacenadoCategorias(Connection conexion) throws SQLException {
		

		if (ejecutarProcedimietoAlmacenadoCategorias == null)
			
			ejecutarProcedimietoAlmacenadoCategorias = conexion.prepareStatement("Call CategoriasTransacciones(?,?)");
		
		
		return ejecutarProcedimietoAlmacenadoCategorias;
	}



	public static PreparedStatement getEjecutarProcedimietoAlmacenadoMarcas(Connection conexion) throws SQLException {
		
		if (ejecutarProcedimietoAlmacenadoMarcas == null)
			
			ejecutarProcedimietoAlmacenadoMarcas = conexion.prepareStatement("Call MarcasTransacciones(?,?)");

		
		return ejecutarProcedimietoAlmacenadoMarcas;
	}



	public static PreparedStatement getEjecutarProcedimietoAlmacenadoVentas(Connection conexion) throws SQLException {
		
	if (ejecutarProcedimietoAlmacenadoVentas == null)
			
		ejecutarProcedimietoAlmacenadoVentas = conexion.prepareStatement("Call VentasTransacciones(?,?,?,?,?,?,?,?)");

		
		return ejecutarProcedimietoAlmacenadoVentas;
	}


	

	public static PreparedStatement getEjecutarProcedimietoAlmacenadoAbonos(Connection conexion) throws SQLException {
		
		if (ejecutarProcedimietoAlmacenadoAbonos == null)
				
			ejecutarProcedimietoAlmacenadoAbonos = conexion.prepareStatement("Call AbonosTransacciones(?,?,?,?,?,?,?)");

		return ejecutarProcedimietoAlmacenadoAbonos;
	}
	
	
	

}
