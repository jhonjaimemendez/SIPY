package com.JASoft.componentes.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;

import com.JASoft.componentesGraficos.ToolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

public class GUIListaProducto {

	private Shell sShell = null;
	private Group grupoTablaLista = null;
	private Table tbListaValores = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Listado de Productos");
		createGrupoTablaLista();
		sShell.setLayout(null);
		sShell.setBounds(new Rectangle(25, 25, 800, 600));
	}

	/**
	 * This method initializes grupoTablaLista	
	 *
	 */
	private void createGrupoTablaLista() {
		grupoTablaLista = new Group(sShell, SWT.NONE);
		grupoTablaLista.setLayout(null);
		grupoTablaLista.setBounds(new Rectangle(12, 66, 758, 483));
		tbListaValores = new Table(grupoTablaLista, SWT.NONE);
		tbListaValores.setHeaderVisible(true);
		tbListaValores.setLinesVisible(true);
		tbListaValores.setBounds(new Rectangle(10, 21, 741, 451));
	}

}
