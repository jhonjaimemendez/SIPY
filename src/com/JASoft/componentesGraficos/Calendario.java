/**
 * Clase: Calendario
 * 
 * @version  1.0
 * 
 * @since 06-12-2010
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentesGraficos;




import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*
 *  Esta clase configura un calendario
 */

public class Calendario extends Dialog implements SelectionListener{
	
	private Shell shellDialogo;
	private Button bAceptar;
	private Button bCancelar;
	private DateTime calendario;
	private Text texto;
	

	public Calendario(Shell shellPadre, Text texto) {
		
		super(shellPadre);
		
		this.texto = texto;
		
		configurarInterfaz(null);
		
		ConfigurarText.centrarShell(shellDialogo);
		
		shellDialogo.open();
		
		
	}
	
	public Calendario(Shell shellPadre, Date fecha) {
		
		super(shellPadre);
		
		configurarInterfaz(fecha);
		
		
		
		shellDialogo.open();
	
		
	}
	
	@SuppressWarnings("deprecation")
	public void configurarInterfaz(Date fecha) {
		
		shellDialogo = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shellDialogo.setSize(260, 250);
		shellDialogo.setText("Calendario");
		shellDialogo.setLayout(null);
	
		calendario = new DateTime (shellDialogo, SWT.CALENDAR | SWT.BORDER);
		calendario.setBounds(10, 10, 240,170);
		
		if (fecha != null)
			
			calendario.setDate(fecha.getYear(), fecha.getMonth(), fecha.getDay());
		
	
		bAceptar = new Button(shellDialogo,SWT.PUSH);
		bAceptar.setText("Aceptar");
		bAceptar.setBounds(10, 185,103, 31);
		bAceptar.setImage(new Image(Display.getCurrent(), "imagenes/OK.gif"));
		bAceptar.addSelectionListener(this);
		
		bCancelar = new Button(shellDialogo,SWT.PUSH);
		bCancelar.setText("Cancelar");
		bCancelar.setBounds(147, 185,103, 31);
		bCancelar.setImage(new Image(Display.getCurrent(), "imagenes/NO.gif"));
		bCancelar.addSelectionListener(this);
		
		
	}
	


	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void widgetSelected(SelectionEvent a) {
		
		Object fuente = a.getSource();
		
		if (fuente == bAceptar) {
			
			texto.setText(calendario.getYear () +"-" + (calendario.getMonth () + 1) + "-" + calendario.getDay ());
			shellDialogo.close();
			
		} else
			
			if (fuente == bCancelar) {
				
				shellDialogo.close();
			}
		
		
	}
	

	
	

}
