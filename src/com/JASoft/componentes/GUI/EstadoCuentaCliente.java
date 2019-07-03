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

public class EstadoCuentaCliente {

	private Shell sShell = null;
	private Group grupoClientes = null;
	private Label label = null;
	private Combo cTipo = null;
	private Label Identificación = null;
	private Text tIdentificacion = null;
	private Label lNombres = null;
	private Text tNombre = null;

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
		grupoClientes.setBounds(new Rectangle(13, 62, 481, 132));
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

}
