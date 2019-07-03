/**
 * Clase: ConfigurarText
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

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * Esta clase permite definir parametros comunes para la creacion de un Text a
 * partir de ella, asi como metodos para la configuracion de componentes
 * graficos entre otros
 */

public class ConfigurarText extends Validador {

	private static String dptoDivisionPolitica;
	private static String codigoDptoDivisionPolitica;
	private static String municipioDivisionPolitica;
	private static String codigoDivisionPolitica;
	
	
	private  static String departamentos[][];    /* Almacena los codigos de departamento, 
	                                             * los nombres, los codigos de las capitales
	                                             *   y los nombres
	                                             */  


	/**
	 *  Formato para moneda
	 */
	private static DecimalFormat moneda = null;
	
	

	/**
	 * Metodo que muestra una caja de mensajes para los errores
	 * 
	 * @param shell
	 *            Shell 
	 *@param  mensaje
	 *            Mensaje de error           
	 */
	public static void mensajeError(Shell shell, String mensaje) {

		MessageBox casillaTextoError = new MessageBox(shell, SWT.ICON_ERROR);
		casillaTextoError.setText("Reporte de Error");
		casillaTextoError.setMessage(mensaje);
		casillaTextoError.open();

	}

	/**
	 * Metodo que muestra una caja de mensajes para la información
	 * 
	 * @param shell
	 *            Shell 
	 *@param  mensaje
	 *            Mensaje de informacion           
	 */
	public static void mensajeInformacion(Shell shell, String mensaje) {


		MessageBox casillaTextoInformacion = new MessageBox(shell, SWT.ICON_INFORMATION);
		casillaTextoInformacion.setText("Reporte de Información");
     	casillaTextoInformacion.setMessage(mensaje);
		casillaTextoInformacion.open();

	}

	/**
	 * Metodo que muestra una caja de mensajes de confirmacion y devuelve la opcion escogida
	 * 
	 * @param shell
	 *            Shell 
	 *@param  mensaje
	 *            Mensaje de confirmacion           
	 */
	public  static int  mensajeConfirmacion(Shell shell, String mensaje) {

		MessageBox casillaTextoConfirmacion = new MessageBox(shell, SWT.OK | SWT.CANCEL);
		casillaTextoConfirmacion.setText("Reporte de Confirmación");
		casillaTextoConfirmacion.setMessage(mensaje);
		return casillaTextoConfirmacion.open();

	}

	/**
	 * Metodo que bloquea el Shell, hasta que el usuario cierra la ventana
	 * 
	 * @param shell
	 *            Shell 
	 */

	public static void bloquearShell(Shell sShell) {

		sShell.open();
		while (!sShell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch())
				Display.getCurrent().sleep();
		}

		Display.getCurrent().dispose();

	}

	/**
	 * Metodo que cierra un shell
	 * 
	 * @param shell
	 *            Shell 
	 */
	public static void cerrarShell(Shell sShell) {

		sShell.close();

	}

	/**
	 * Metodo que permite centrar el shell del argumento
	 * 
	 * @param shell
	 *            Shell a centrar
	 */
	public static void centrarShell(Shell shell) {

		Monitor primario = Display.getCurrent().getPrimaryMonitor(); // Se  obtiene el monitor del Display

		Rectangle limitesDisplay = primario.getBounds(); // Se obtiene los limites del display

		Rectangle limitesShell = shell.getBounds(); // Se obtiene los limites del shell

		/*
		 * Se obtiene las posiciones en X y Y para centrar el shell
		 */
		int x = limitesDisplay.x + (limitesDisplay.width - limitesShell.width)/ 2;
		int y = limitesDisplay.y + (limitesDisplay.height - limitesShell.height) / 2;

		shell.setLocation(x, y); // Se ubica el shell
	}


	/**
	 *  Metodo que devuelve adiciona en un JComBoBox los departamentos de colombia
	 *  @param cDepartamentos Combo que se le adicionan los departamentos
	 *  
	 */

	final static public void cargarDepatamentos(Combo cDepartamentos)  {

		if (departamentos == null) {

			departamentos = new String[33][4];


			departamentos[0][0] = "28";
			departamentos[0][1] = "AMAZONAS";
			departamentos[0][2] = "91001000";
			departamentos[0][3] = "LETICIA";

			departamentos[1][0] = "1";
			departamentos[1][1] = "ANTIOQUIA";
			departamentos[1][2] = "5001000";
			departamentos[1][3] =	"MEDELLIN";	

			departamentos[2][0] = "24";
			departamentos[2][1] = "ARAUCA";
			departamentos[2][2] = "81001000";
			departamentos[2][3] = "ARAUCA";

			departamentos[3][0] = "2";
			departamentos[3][1] = "ATLANTICO";
			departamentos[3][2] = "8001000";
			departamentos[3][3] =	"BARRANQUILLA";


			departamentos[4][0] = "3";
			departamentos[4][1] = "BOLIVAR";
			departamentos[4][2] = "13001000";
			departamentos[4][3] =	"CARTAGENA";


			departamentos[5][0] = "4";
			departamentos[5][1] = "BOYACA";
			departamentos[5][2] = "15001000";
			departamentos[5][3] =	"TUNJA";


			departamentos[6][0] = "5";
			departamentos[6][1] = "CALDAS";
			departamentos[6][2] = "17001000";
			departamentos[6][3] =	"MANIZALES";


			departamentos[7][0] = "6";
			departamentos[7][1] = "CAQUETA";
			departamentos[7][2] = "18001000";
			departamentos[7][3] =	"FLORENCIA";

			departamentos[8][0] = "25";
			departamentos[8][1] = "CASANARE";
			departamentos[8][2] = "85001000";
			departamentos[8][3] = "YOPAL";


			departamentos[9][0] = "7";
			departamentos[9][1] = "CAUCA";
			departamentos[9][2] = "19001000";
			departamentos[9][3] =	"POPAYAN";


			departamentos[10][0] = "8";
			departamentos[10][1] = "CESAR";
			departamentos[10][2] = "20001000";
			departamentos[10][3] = "VALLEDUPAR";

			departamentos[11][0] = "11";
			departamentos[11][1] = "CHOCO";
			departamentos[11][2] = "27001000";
			departamentos[11][3] = "QUIBDO";

			departamentos[12][0] = "9";
			departamentos[12][1] = "CORDOBA";
			departamentos[12][2] = "23001000";
			departamentos[12][3] = "MONTERIA";

			departamentos[13][0] = "10";
			departamentos[13][1] = "CUNDINAMARCA";
			departamentos[13][2] = "11001000";
			departamentos[13][3] = "BOGOTA";


			departamentos[14][0] = "29";
			departamentos[14][1] = "GUAINIA";
			departamentos[14][2] = "94001000";
			departamentos[14][3] = "INIRIDA";


			departamentos[15][0] = "13";
			departamentos[15][1] = "GUAJIRA";
			departamentos[15][2] = "44001000";
			departamentos[15][3] = "RIOHACHA";


			departamentos[16][0] = "30";
			departamentos[16][1] = "GUAVIARE";
			departamentos[16][2] = "95001000";
			departamentos[16][3] = "SAN JOSE DEL GUAVIARE";


			departamentos[17][0] = "12";
			departamentos[17][1] = "HUILA";
			departamentos[17][2] = "41001000";
			departamentos[17][3] = "NEIVA";


			departamentos[18][0] = "14";
			departamentos[18][1] = "MAGDALENA";
			departamentos[18][2] = "47001000";
			departamentos[18][3] = "SANTA MARTA";


			departamentos[19][0] = "15";
			departamentos[19][1] = "META";
			departamentos[19][2] = "50001000";
			departamentos[19][3] = "VILLAVICENCIO";


			departamentos[20][0] = "16";
			departamentos[20][1] = "NARIÑO";
			departamentos[20][2] = "52001000";
			departamentos[20][3] = "PASTO";


			departamentos[21][0] = "17";
			departamentos[21][1] = "NORTE DE SANTANDER";
			departamentos[21][2] = "54001000";
			departamentos[21][3] = "CUCUTA";


			departamentos[22][0] = "26";
			departamentos[22][1] = "PUTUMAYO";
			departamentos[22][2] = "86001000";
			departamentos[22][3] = "MOCOA";


			departamentos[23][0] = "18";
			departamentos[23][1] = "QUINDIO";
			departamentos[23][2] = "63001000";
			departamentos[23][3] = "ARMENIA";


			departamentos[24][0] = "19";
			departamentos[24][1] = "RISARALDA";
			departamentos[24][2] = "66001000";
			departamentos[24][3] = "PEREIRA";


			departamentos[25][0] = "27";
			departamentos[25][1] = "SAN ANDRES";
			departamentos[25][2] = "88001000";
			departamentos[25][3] = "SAN ANDRES";


			departamentos[26][0] = "20";
			departamentos[26][1] = "SANTANDER";
			departamentos[26][2] = "68001000";
			departamentos[26][3] = "BUCARAMANGA";


			departamentos[27][0] = "21";
			departamentos[27][1] = "SUCRE";
			departamentos[27][2] = "70001000";
			departamentos[27][3] = "SINCELEJO";


			departamentos[28][0] = "22";
			departamentos[28][1] = "TOLIMA";
			departamentos[28][2] = "73001000";
			departamentos[28][3] = "IBAGUE";


			departamentos[29][0] = "23";
			departamentos[29][1] = "VALLE";
			departamentos[29][2] = "76001000";
			departamentos[29][3] = "CALI";


			departamentos[30][0] = "31";
			departamentos[30][1] = "VAUPES";
			departamentos[30][2] = "97001000";
			departamentos[30][3] = "MITU";


			departamentos[31][0] = "32";
			departamentos[31][1] = "VICHADA";
			departamentos[31][2] = "99001000";
			departamentos[31][3] = "PUERTO CARRENO";

			departamentos[32][0] = "0";
			departamentos[32][1] = "NO ESPECIFICADA";
			departamentos[32][2] = "0";
			departamentos[32][3] = "NO ESPECIFICADO";


		}  

		for (int i = 0 ; i < departamentos.length; i++)

			cDepartamentos.add(departamentos[i][1]);


	}



	/**
	 * 
	 * @param cadena Cadena
	 * @return String Retorna cadena vacia si esta en null, sino devuelve la cadena 
	 */

	public String devuelveCadenaVaciaParaNulo(String cadena) {

		String resultado = "";

		if (cadena != null)

			resultado = cadena;

		return resultado;

	}

	/**
	 * 
	 * @param Image image Imagen a escalar
	 * @param int ancho ancho de la imagen a escalar
	 * @param int alto alto de la imagen a escalar
	 * @return Image Imagen escalada 
	 */
	public Image getImagenEscalada(Image image, int ancho, int alto) {

		Image scaled = new Image(Display.getDefault(), ancho, alto);

		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
				image.getBounds().width, image.getBounds().height,
				0, 0, ancho, alto);
		gc.dispose();

		image.dispose();

		return scaled;
	}


	/**
	  * Metodo para redondear valores en Java
	  * @param numeroRedondear : Numero a redondear
	  * @param numeroDecimales : Numero decimales a redondear
	  * @return numero redondeado
	  */
	
	 public float redondearNumero(float numeroRedondear, int numeroDecimales) {
	      
	      float potenciaDiez = (float)Math.pow(10,numeroDecimales);
	     
	       numeroRedondear = numeroRedondear * potenciaDiez;
	       
	      float numeroRedondeado = Math.round(numeroRedondear);
	      
	      return (float)numeroRedondeado/potenciaDiez;
	      
    }

	 
	 public static String[][] getDepartamentos() {
		 return departamentos;
	 }


	 public String getFormatoMoneda(Integer numeroFormatear) {

		 if (moneda == null)

			 moneda = new DecimalFormat("$ ###,###"); 


		 return moneda.format(numeroFormatear);
	 }


	public static String getFormatoMoneda(String numeroFormatear) {

		String resultadoString = numeroFormatear;

		if (!numeroFormatear.isEmpty() && !numeroFormatear.contains("$")) {

			if (moneda == null)

				moneda = new DecimalFormat("$ ###,###");

			resultadoString = moneda.format(new Integer(numeroFormatear));
			
		}

		return resultadoString;
	}

	 public int getMonedaAEntero(String valorAcambiar) {

		 int resultadoInt =  0;


		 if (valorAcambiar.contains("$") && valorAcambiar.contains(".")) {

			 if (moneda == null)

				 moneda = new DecimalFormat("$ ###,###"); 


			 try {

				 resultadoInt = (moneda.parse(valorAcambiar)).intValue();

			 } catch (ParseException e) {

				 e.printStackTrace();

			 }

		 } else
			 
			 if (valorAcambiar.equals("$ 1"))
				 
				 resultadoInt = 1;
				 
			 else
				 
			 resultadoInt = new Integer(valorAcambiar);

		 return resultadoInt;
	 }


}
