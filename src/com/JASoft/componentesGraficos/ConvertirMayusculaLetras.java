/*
 * Clase: ConvetirMayCaracteres
 * 
 * @version  1.0
 * 
 * @since 2010-19-11
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */


package com.JASoft.componentesGraficos;


import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;



/*
 * Esta clase permite configurar un evento para validar que se conviertan las letras en mayusculas
 * 
 */

public class ConvertirMayusculaLetras implements VerifyListener{

	@Override
	public void verifyText(VerifyEvent v) {
		
		v.text = v.text.toUpperCase();
		
	}


}
