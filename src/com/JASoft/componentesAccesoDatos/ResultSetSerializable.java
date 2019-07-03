package com.JASoft.componentesAccesoDatos;

import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Vector;


/**
 * Esta clase simula la serializacion de objetos de tipo ResulSet
 */


final public class ResultSetSerializable implements Serializable {

	//Se especifica el serial para la serializacion
	static final long serialVersionUID = 19781203;

	private int filas = 0; 

	private int columnas = 0;

	private Vector < String > nomColumnas = new Vector < String >();

	private Vector  < Vector > datosColumnas = new Vector< Vector >();

	public ResultSetSerializable(ResultSet resultado) {

		try {
			int i;

			//** Se define un objeto de tipo Metadata para buscar las columnas de la consulta
			ResultSetMetaData metadata=resultado.getMetaData();

			//** Se almacena el numero de columnas
			columnas=metadata.getColumnCount();

			//** Se almacena los nombres de las columnas
			for(i=0;i<columnas;i++)

				nomColumnas.addElement(metadata.getColumnName(i+1));

			//** Se define un Vector para almacenar las filas
			Vector < String > datos ;

			//** Se sacan los valores del Resultset
			while(resultado.next()) {

				datos = new Vector < String >();

				for(i = 1;i <= columnas; i++)
					datos.addElement(resultado.getString(i));

				//** Se almacena la fila en el vector resultante
				datosColumnas.add(datos);

				//** Se cuenta la fila
				filas++;	    
			}

			resultado.close();

		}catch (SQLException s) {
			System.out.println("Error"+s);
		}
	}



	public int getFilas() {

		return filas;

	}


	public int getColumnas() {

		return columnas;

	}  


	public Vector getNombresColumnas() {

		return nomColumnas;
	}


	public Vector getDatosColumnas() {

		return datosColumnas;
	}
}
