package com.JASoft.componentes.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.JASoft.componentesGraficos.ToolBar;


public class GUICategoria {

	private Shell sShell = null;
	private Group grupoCategorias = null;
	private Table tbCategorias = null;
	private Button bAgregar = null;
	private Button bEditar = null;
	private Button bEliminar = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Categorias");
		createGrupoCategorias();
		sShell.setLayout(null);
		sShell.setBounds(new Rectangle(150, 150, 800, 600));
	}

	/**
	 * This method initializes grupoCategorias	
	 *
	 */
	private void createGrupoCategorias() {
		grupoCategorias = new Group(sShell, SWT.NONE);
		grupoCategorias.setLayout(null);
		grupoCategorias.setText("Categorias");
		grupoCategorias.setBounds(new Rectangle(57, 77, 661, 433));
		tbCategorias = new Table(grupoCategorias, SWT.NONE);
		tbCategorias.setHeaderVisible(true);
		tbCategorias.setLinesVisible(true);
		tbCategorias.setBounds(new Rectangle(15, 31, 628, 337));
		bAgregar = new Button(grupoCategorias, SWT.NONE);
		bAgregar.setBounds(new Rectangle(89, 385, 106, 30));
		bAgregar.setText("Agregar");
		bAgregar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Adicionar.gif"));
		bEditar = new Button(grupoCategorias, SWT.NONE);
		bEditar.setBounds(new Rectangle(284, 385, 106, 30));
		bEditar.setText("Editar");
		bEditar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Editar.gif"));
		bEliminar = new Button(grupoCategorias, SWT.NONE);
		bEliminar.setBounds(new Rectangle(479, 385, 106, 30));
		bEliminar.setText("Eliminar");
		bEliminar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Eliminar.gif"));
	}

}
