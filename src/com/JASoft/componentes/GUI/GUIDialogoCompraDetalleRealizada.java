package com.JASoft.componentes.GUI;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class GUIDialogoCompraDetalleRealizada {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Group grupoDetalles = null;
	private Table table = null;
	private Label lTotal = null;
	private Text tTotal = null;
	private Button bAceptar = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Detalle Compra");
		createGrupoDetalles();
		sShell.setSize(new Point(793, 355));
		sShell.setLayout(null);
		bAceptar = new Button(sShell, SWT.NONE);
		bAceptar.setBounds(new Rectangle(344, 279, 130, 26));
		bAceptar.setImage(new Image(Display.getCurrent(), "C:/DESARROLLOS DE SOFTWARE/SIPY/imagenes/OK.gif"));
		bAceptar.setText("Aceptar");
	}

	/**
	 * This method initializes grupoDetalles	
	 *
	 */
	private void createGrupoDetalles() {
		grupoDetalles = new Group(sShell, SWT.NONE);
		grupoDetalles.setLayout(null);
		grupoDetalles.setBounds(new Rectangle(3, 8, 764, 257));
		table = new Table(grupoDetalles, SWT.NONE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(new Rectangle(8, 16, 745, 196));
		lTotal = new Label(grupoDetalles, SWT.NONE);
		lTotal.setBounds(new Rectangle(610, 222, 40, 15));
		lTotal.setText("Total:");
		tTotal = new Text(grupoDetalles, SWT.BORDER);
		tTotal.setBounds(new Rectangle(658, 218, 95, 23));
	}

}
