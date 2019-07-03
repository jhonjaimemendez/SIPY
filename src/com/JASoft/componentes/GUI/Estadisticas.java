package com.JASoft.componentes.GUI;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;

public class Estadisticas {

	private Shell sShell = null;
	private TabFolder pesta�asEstadistica = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Estadisticas");
		createPesta�asEstadistica();
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(null);
	}

	/**
	 * This method initializes pesta�asEstadistica	
	 *
	 */
	private void createPesta�asEstadistica() {
		pesta�asEstadistica = new TabFolder(sShell, SWT.BORDER);
		pesta�asEstadistica.setLayout(null);
		pesta�asEstadistica.setBounds(new Rectangle(5, 18, 772, 532));
		
		TabItem tabResumen= new TabItem(pesta�asEstadistica, SWT.NULL);
		tabResumen.setText("Resumen ");
	    
	    TabItem tabProductos = new TabItem(pesta�asEstadistica, SWT.NULL);
	    tabProductos.setText("Productos");


	}

}
