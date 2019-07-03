package com.JASoft.componentes.GUI;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

public class PestañaCategoriasMasVendidas extends Composite {

	private Group grupoCategoriasMasRotadas = null;
	private Group grupoCategoriasConsultas = null;
	private Table tbVentasPorCategorias = null;

	public PestañaCategoriasMasVendidas(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		createGrupoCategoriasMasRotadas();
		setSize(new Point(753, 490));
		setLayout(null);
	}

	/**
	 * This method initializes grupoCategoriasMasRotadas	
	 *
	 */
	private void createGrupoCategoriasMasRotadas() {
		grupoCategoriasMasRotadas = new Group(this, SWT.NONE);
		grupoCategoriasMasRotadas.setLayout(null);
		createGrupoCategoriasConsultas();
		grupoCategoriasMasRotadas.setBounds(new Rectangle(0, 0, 753, 490));
	}

	/**
	 * This method initializes grupoCategoriasConsultas	
	 *
	 */
	private void createGrupoCategoriasConsultas() {
		grupoCategoriasConsultas = new Group(grupoCategoriasMasRotadas, SWT.NONE);
		grupoCategoriasConsultas.setLayout(null);
		grupoCategoriasConsultas.setText("Listado de Ventas por Categorias ");
		grupoCategoriasConsultas.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoCategoriasConsultas.setBounds(new Rectangle(7, 18, 373, 456));
		tbVentasPorCategorias = new Table(grupoCategoriasConsultas, SWT.NONE);
		tbVentasPorCategorias.setHeaderVisible(true);
		tbVentasPorCategorias.setLinesVisible(true);
		tbVentasPorCategorias.setBounds(new Rectangle(16, 22, 343, 423));
	}

}
