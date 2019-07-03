package com.JASoft.componentes.GUI;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class GUIDialogoCategorias {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Group grupoCategorias = null;
	private Label lCodigo = null;
	private Text tCodigo = null;
	private Label lDescripcion = null;
	private Text tDescripcion = null;
	private Button bAceptar = null;
	private Button bCancelar = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Categorias");
		createGrupoCategorias();
		sShell.setSize(new Point(410, 200));
		sShell.setLayout(null);
		bAceptar = new Button(sShell, SWT.NONE);
		bAceptar.setBounds(new Rectangle(56, 124, 109, 27));
		bAceptar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/OK.gif"));
		bAceptar.setText("Aceptar");
		bCancelar = new Button(sShell, SWT.NONE);
		bCancelar.setBounds(new Rectangle(229, 124, 109, 27));
		bCancelar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/NO.gif"));
		bCancelar.setText("Cancelar");
	}

	/**
	 * This method initializes grupoCategorias	
	 *
	 */
	private void createGrupoCategorias() {
		grupoCategorias = new Group(sShell, SWT.NONE);
		grupoCategorias.setLayout(null);
		grupoCategorias.setBounds(new Rectangle(19, 7, 359, 101));
		lCodigo = new Label(grupoCategorias, SWT.NONE);
		lCodigo.setBounds(new Rectangle(21, 34, 64, 15));
		lCodigo.setText("Código");
		tCodigo = new Text(grupoCategorias, SWT.BORDER);
		tCodigo.setBounds(new Rectangle(21, 50, 64, 23));
		lDescripcion = new Label(grupoCategorias, SWT.NONE);
		lDescripcion.setBounds(new Rectangle(98, 34, 68, 15));
		lDescripcion.setText("Descripción");
		tDescripcion = new Text(grupoCategorias, SWT.BORDER);
		tDescripcion.setBounds(new Rectangle(98, 50, 227, 23));
	}

}
