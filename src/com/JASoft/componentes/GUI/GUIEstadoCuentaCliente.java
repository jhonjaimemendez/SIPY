package com.JASoft.componentes.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;

import com.JASoft.componentesGraficos.ToolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;

public class GUIEstadoCuentaCliente {

	private Shell sShell = null;
	private Group grupoClientes = null;
	private Label label = null;
	private Combo cTipo = null;
	private Label Identificación = null;
	private Text tIdentificacion = null;
	private Label lNombres = null;
	private Text tNombre = null;
	private Group grupoMovimientos = null;
	private Table table = null;
	private Label lMovimientos = null;
	private Combo cMovimientos = null;
	private Group grupoCreditos = null;
	private Label lSaldo = null;
	private Text tSaldo = null;
	private Label lCupo = null;
	private Text tCupo = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Estado de Cuenta de Clientes");
		createGrupoClientes();
		sShell.setLayout(null);
		createGrupoMovimientos();
		createGrupoCreditos();
		sShell.setBounds(new Rectangle(138, 138, 800, 600));
	}

	/**
	 * This method initializes grupoClientes	
	 *
	 */
	private void createGrupoClientes() {
		grupoClientes = new Group(sShell, SWT.NONE);
		grupoClientes.setLayout(null);
		grupoClientes.setText("Datos Clientes");
		grupoClientes.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoClientes.setBounds(new Rectangle(20, 61, 481, 132));
		label = new Label(grupoClientes, SWT.NONE);
		label.setText("Tipo Identificación");
		label.setBounds(new Rectangle(8, 20, 108, 15));
		createCTipo();
		Identificación = new Label(grupoClientes, SWT.NONE);
		Identificación.setBounds(new Rectangle(257, 20, 99, 15));
		Identificación.setText("Identificación");
		tIdentificacion = new Text(grupoClientes, SWT.BORDER);
		tIdentificacion.setBounds(new Rectangle(257, 40, 177, 23));
		lNombres = new Label(grupoClientes, SWT.NONE);
		lNombres.setBounds(new Rectangle(11, 88, 60, 15));
		lNombres.setText("Nombres:");
		tNombre = new Text(grupoClientes, SWT.BORDER);
		tNombre.setBounds(new Rectangle(80, 83, 356, 23));
	}

	/**
	 * This method initializes cTipo	
	 *
	 */
	private void createCTipo() {
		cTipo = new Combo(grupoClientes, SWT.NONE);
		cTipo.setBounds(new Rectangle(8, 40, 179, 23));
	}

	/**
	 * This method initializes grupoMovimientos	
	 *
	 */
	private void createGrupoMovimientos() {
		grupoMovimientos = new Group(sShell, SWT.NONE);
		grupoMovimientos.setLayout(null);
		grupoMovimientos.setBounds(new Rectangle(21, 203, 750, 352));
		table = new Table(grupoMovimientos, SWT.NONE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(new Rectangle(8, 50, 730, 273));
		lMovimientos = new Label(grupoMovimientos, SWT.NONE);
		lMovimientos.setBounds(new Rectangle(213, 24, 84, 15));
		lMovimientos.setText("Movimientos.");
		createCMovimientos();
	}

	/**
	 * This method initializes cMovimientos	
	 *
	 */
	private void createCMovimientos() {
		cMovimientos = new Combo(grupoMovimientos, SWT.NONE);
		cMovimientos.setBounds(new Rectangle(8, 20, 198, 23));
	}

	/**
	 * This method initializes grupoCreditos	
	 *
	 */
	private void createGrupoCreditos() {
		grupoCreditos = new Group(sShell, SWT.NONE);
		grupoCreditos.setLayout(null);
		grupoCreditos.setText("Datos credito");
		grupoCreditos.setBounds(new Rectangle(528, 61, 235, 132));
		lSaldo = new Label(grupoCreditos, SWT.NONE);
		lSaldo.setBounds(new Rectangle(32, 32, 39, 15));
		lSaldo.setText("Saldo:");
		tSaldo = new Text(grupoCreditos, SWT.BORDER);
		tSaldo.setBounds(new Rectangle(79, 28, 125, 23));
		lCupo = new Label(grupoCreditos, SWT.NONE);
		lCupo.setBounds(new Rectangle(40, 83, 32, 15));
		lCupo.setText("Cupo:");
		tCupo = new Text(grupoCreditos, SWT.BORDER);
		tCupo.setBounds(new Rectangle(79, 79, 125, 23));
	}

}
