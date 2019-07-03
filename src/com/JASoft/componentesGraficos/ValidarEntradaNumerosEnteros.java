/**
 * Clase: ValidarEntradaNumeroEnteros
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


import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;


/**
  * Esta clase valida la entrada de datos de tipo numerico en un Text. Se agrega
  * como un evento de tipo addVerifyListener
  */

public class ValidarEntradaNumerosEnteros implements VerifyListener {

	public void verifyText(VerifyEvent e) {

		if (e.character != java.awt.event.KeyEvent.VK_BACK_SPACE && (e.keyCode != 0 
				&& (!Character.isDigit(e.character) )))
			
				
			// Se consume o borra el caracter digitado
			e.doit = false;

	}
}