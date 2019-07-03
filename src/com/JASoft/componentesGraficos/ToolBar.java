package com.JASoft.componentesGraficos;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Button;

public class ToolBar extends Composite {

	private Label lTitulo = null;
	private Button bGuardar = null;
	private Button bLimpiar = null;
	private Button bEliminar = null;
	private Button bBuscar = null;
	private Button bImprimir = null;
	private Button bSalir = null;

	public ToolBar(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		lTitulo = new Label(this, SWT.NONE);
		lTitulo.setBounds(new Rectangle(153, 3, 291, 32));
		lTitulo.setImage(new Image(Display.getCurrent(), "imagenes/SITY.gif"));
		lTitulo.setText("");
		bGuardar = new Button(this, SWT.BORDER);
		bGuardar.setBounds(new Rectangle(515, 3, 40, 38));
		bGuardar.setToolTipText("Guardar Alt + G");
		bGuardar.setImage(new Image(Display.getCurrent(), "imagenes/Guardar.gif"));
		bLimpiar = new Button(this, SWT.BORDER);
		bLimpiar.setBounds(new Rectangle(559, 3, 40, 38));
		bLimpiar.setImage(new Image(Display.getCurrent(), "imagenes/Limpiar.gif"));
		bLimpiar.setToolTipText("Limpiar Alt + L");
		bEliminar = new Button(this, SWT.BORDER);
		bEliminar.setBounds(new Rectangle(606, 3, 40, 38));
		bEliminar.setImage(new Image(Display.getCurrent(), "imagenes/Eliminar.gif"));
		bEliminar.setToolTipText("Eliminar Alt + E");
		bBuscar = new Button(this, SWT.BORDER);
		bBuscar.setBounds(new Rectangle(649, 3, 40, 38));
		bBuscar.setImage(new Image(Display.getCurrent(), "imagenes/Buscar.gif"));
		bBuscar.setToolTipText("Buscar Alt + B");
		bImprimir = new Button(this, SWT.BORDER);
		bImprimir.setBounds(new Rectangle(692, 3, 40, 38));
		bImprimir.setImage(new Image(Display.getCurrent(), "imagenes/Imprimir.gif"));
		bImprimir.setToolTipText("Buscar Alt + B");
		bSalir = new Button(this, SWT.BORDER);
		bSalir.setBounds(new Rectangle(735, 3, 40, 38));
		bSalir.setImage(new Image(Display.getCurrent(), "imagenes/Salir.gif"));
		bSalir.setToolTipText("Salir Alt + S");
		setLayout(null);
		this.setBounds(new Rectangle(0, 0, 782, 48));
	}

	
	public Button getbGuardar() {
		return bGuardar;
	}

	public Button getbLimpiar() {
		return bLimpiar;
	}

	public Button getbEliminar() {
		return bEliminar;
	}

	public Button getbBuscar() {
		return bBuscar;
	}

	public Button getbImprimir() {
		return bImprimir;
	}

	public Button getbSalir() {
		return bSalir;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="3,10"
