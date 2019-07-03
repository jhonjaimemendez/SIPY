/**
 * Clase: AtributoVisual
 * 
 * @version  1.0
 * 
 * @since 16-04-2007
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentesGraficos;



import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

/**
 * Esta clase permite definir parametros visuales comunes a todas las interfaces
 * de JASoft sobre SWT, tales como: Color de fondo de los componentes que obtienen el foco, 
 * Justificacion de los elementos de una tabla, renderizacion y bordes.
 */


public abstract class AtributoVisual {

	
	private static Device device = Display.getCurrent ();



	/**
	 * Color de un componente cuando obtiene el foco
	 */
	private  static Color visualAtributoGanaFocoComponente;


	/**
	 * Color de un componente cuando pierde el foco
	 */
	private  final static Color visualAtributoPierdeFocoComponente = new Color(device,255,255,255);
	
	/**
	 * Color de un componente deshabilitado para que aparezca en blanco
	 */
	
	private  final static Color visualAtributoDeshabilidatoComponente = new Color(device,255,255,250);

	/**
	 * Color casilla de texto con lista de valores
	 */
	private  static Color visualAtributoComponenteConLista;
	
	/*
	 * Color para los textos especiales 
	 */
	private static Color colorRojo = new Color(device, 242,38,2);


	/**
	 * Obtiene el color para configurar el fondo de un componente
	 * cuando obtiene el foco
	 *
	 * @return Color
	 */
	final public Color getVisualAtributoGanaFocoComponentes() {


		if (visualAtributoGanaFocoComponente == null)

			visualAtributoGanaFocoComponente = new Color(device,255,255,200);

		return  visualAtributoGanaFocoComponente;

	}

	/**
	 * Obtiene el color para configurar el fondo de un componente
	 * cuando pierde el foco
	 *
	 * @return Color
	 */
	final public Color getVisualAtributoPierdeFocoComponentes() {

		return  visualAtributoPierdeFocoComponente;

	}
 
	/**
	 * Obtiene el color para configurar el fondo de un componente que
	 * posee una lista de valores
	 *
	 * @return Color
	 */
	final public Color getVisualAtributoComponenteConLista() {

		if (visualAtributoComponenteConLista == null)

			visualAtributoComponenteConLista = new Color(device,240,240,255);

		return visualAtributoComponenteConLista;

	}

	public static Color getVisualatributodeshabilidatocomponente() {
		return visualAtributoDeshabilidatoComponente;
	}

	
	public static Color getColorRojo() {
		return colorRojo;
	}

}
