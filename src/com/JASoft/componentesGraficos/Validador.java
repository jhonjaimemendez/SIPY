/**
 * Clase: Validador
 * 
 * @version  1.0
 * 
 * @since 19-11-2010
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentesGraficos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.ObtenerFechaServidor;

/*
 *  Esta clase especifica metodos de validación de datos sobre la interfaz
 */

public abstract class Validador extends AtributoVisual{
	
	//Declaracion de variables
	private static ValidarEntradaNumerosEnteros validarEntradaNumerosEnteros;
	private static ValidarEntradaNumeroReales validarEntradaNumeroReales = null;
	private static ConvertirMayusculaLetras convertirMayusculaLetras = null;
	private static SimpleDateFormat formatoFecha;


	
	


	/**
	 * Recupera la clase que permite validar entrada de numero enteros
	 * 
	 * @return ValidarEntradaNumeros 
     *              ValidarEntradaNumeros   
	 */
	
	public static ValidarEntradaNumerosEnteros getValidarEntradaNumeros() {
		
		if (validarEntradaNumerosEnteros == null)
		   
			validarEntradaNumerosEnteros = new ValidarEntradaNumerosEnteros();
		
		return validarEntradaNumerosEnteros;
	}

	
	/**
	 * Recupera la clase que permite convertir las letras en mayusculas
	 * 
	 * @return ConvertirMayusculaLetras 
     *              ConvertirMayusculaLetras   
	 */
	public static ConvertirMayusculaLetras getConvertirMayusculaLetras() {
		
		if (convertirMayusculaLetras == null)
			   
			convertirMayusculaLetras = new ConvertirMayusculaLetras();
		
		return convertirMayusculaLetras;
	}

	/**
	 * Recupera la clase que permite validar entrada de numeros reales
	 * 
	 * @return ValidarEntradaNumerosReales 
     *              ValidarEntradaNumerosReales   
	 */
	public static ValidarEntradaNumeroReales getValidarEntradaNumeroReales() {
		
		if (validarEntradaNumeroReales == null)
			   
			validarEntradaNumeroReales = new ValidarEntradaNumeroReales();
		
		return validarEntradaNumeroReales;
	}
	
	
	/**
	 * Valida que el número del celular sea exactamente de 12 número
	 * 
	 * @return ConvertirMayusculaLetras 
     *              ConvertirMayusculaLetras   
	 */
	public boolean validaNumeroCelular(String numeroCelular) {
		
		return numeroCelular.length() == 10;
		
		
	}
	


	/**
	 * Valida un array de Text obligatorios si contienen datos
	 * 
	 * @param campos
	 *            array con los campos que se deben validar como obligatorios
	 * @return true Campos obligatorios todos completos, false falta por lo
	 *              menos uno sin datos
	 */

	public boolean validarRegistro(ArrayList<Text> arrayTextRequeridos,Shell sShell) {

		boolean resultado = true;

		int indice = 0;

		int tamañoArray = arrayTextRequeridos.size();

		Text casillaTextoValidar = null;

		/*
		 *  Se recorre el array de JTextField y se verifica si tiene por lo menos un carecter
		 *  o si el textField tiene el formato String "aaaa-mm-dd"
		 */
		
		while ((indice < tamañoArray) && resultado) {

			casillaTextoValidar = (Text) arrayTextRequeridos.get(indice);
			
			if (casillaTextoValidar.getText().isEmpty())

				resultado = false;

			else

				indice++;

		}

		// Se valida los campos requeridos
		if (!resultado) {

			MessageBox casillaTexto = new MessageBox(sShell, SWT.ICON_ERROR);
			casillaTexto.setMessage(casillaTextoValidar.getToolTipText());
			casillaTexto.setText("Reporte de Error");
			casillaTexto.open();
			casillaTextoValidar.forceFocus();
			
		}

		return resultado;
	}
	
	

	/**
	 * Retorna true si la fecha es correcta false si no
	 * @param fechaValidar Fecha a validar
	 * @return boolean true: es correcta false: es incorrecta 
	 */

	final public boolean esFecha(String fechaValidar) {

		boolean resultadoBoolean = false;

		//metodo para validar si la fecha es correcta
		try {

			if (formatoFecha == null) {


				formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
				formatoFecha.setLenient(false);

			}


			Date fecha = formatoFecha.parse(fechaValidar);

			resultadoBoolean = true;


		} catch (Exception e) {}

		return resultadoBoolean;
	}


	/**
	 * Retorna true si el correo es correcto false si no
	 * @param correo Correo a validar
	 * @return boolean true: es correcta false: es incorrecta 
	 */

	final public boolean esEmail(String correo) {


		boolean resultado = false; //Se valida que no tenga caracteres especiales

		if (!correo.isEmpty()) {
			
			Pattern pat = null;

			Matcher mat = null;   

			pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");

			mat = pat.matcher(correo);

			if (mat.find())

				resultado =  true;

		} else
				resultado =  true;

			return  resultado;  

	}
	
	/**
	   * Retorna la fecha del servidor  en formato aaaa-mm-dd
	   * @param conMySQL Conexion de la Base de datos
	   * @return String Fecha en fotmato aaaa-mm-dd
	   *	
	   */
	  
	   final public String getObtenerFecha(ConectarMySQL conMySQL) {
	   	 
	   	   return ObtenerFechaServidor.getObtenerFecha(conMySQL);
	   	 
	   }
	   
      /*
       * Devuelve el formato de la fecha YYYY-MM-DD
       */
	   public static SimpleDateFormat getFormatoFecha() {
		   
		   if (formatoFecha == null) {


				formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				formatoFecha.setLenient(false);

			}

			return formatoFecha;
		}

}
