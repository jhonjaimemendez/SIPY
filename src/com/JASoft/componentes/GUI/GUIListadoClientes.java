package com.JASoft.componentes.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import com.JASoft.componentesGraficos.ToolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GUIListadoClientes {

	private Shell sShell = null;
	private Group grupoListadoClientes = null;
	private Table tbListadoClientes = null;
	private Label lNumeroClientes = null;
	private Text tNumeroCliente = null;
	private Text tTotalSaldo = null;
	private Label lTotalSaldo = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Listado de Clientes");
		createGrupoListadoClientes();
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(null);
	}

	/**
	 * This method initializes grupoListadoClientes	
	 *
	 */
	private void createGrupoListadoClientes() {
		grupoListadoClientes = new Group(sShell, SWT.NONE);
		grupoListadoClientes.setLayout(null);
		grupoListadoClientes.setBounds(new Rectangle(9, 63, 768, 489));
		tbListadoClientes = new Table(grupoListadoClientes, SWT.NONE);
		tbListadoClientes.setHeaderVisible(true);
		tbListadoClientes.setLinesVisible(true);
		tbListadoClientes.setBounds(new Rectangle(9, 20, 747, 369));
		lNumeroClientes = new Label(grupoListadoClientes, SWT.NONE);
		lNumeroClientes.setBounds(new Rectangle(549, 409, 120, 15));
		lNumeroClientes.setText("N�mero de  Clientes:");
		tNumeroCliente = new Text(grupoListadoClientes, SWT.BORDER);
		tNumeroCliente.setBounds(new Rectangle(676, 405, 77, 23));
		tTotalSaldo = new Text(grupoListadoClientes, SWT.BORDER);
		tTotalSaldo.setBounds(new Rectangle(676, 438, 77, 23));
		lTotalSaldo = new Label(grupoListadoClientes, SWT.NONE);
		lTotalSaldo.setBounds(new Rectangle(589, 446, 80, 15));
		lTotalSaldo.setText("Total Saldos :");
	}

}
