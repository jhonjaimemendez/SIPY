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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;

public class GUIConsultaVentas {

	private Shell sShell = null;
	private Group grupoVentasEncabezado = null;
	private Group grupoVentasDetalle = null;
	private Table tComprasEncabezado = null;
	private Group grupoCriterioBusqueda = null;
	private Combo cCriterio = null;
	private Label lFechaInicial = null;
	private Label lFechaFinal = null;
	private Text tFechaInicial = null;
	private Text tFechaFinal = null;
	private Button bCalendario = null;
	private Button bCalendario1 = null;
	private Table tbDetalle = null;
	private Label lNumeroVentas = null;
	private Text tNumeroventa = null;
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Consulta Ventas");
		createGrupoVentasEncabezado();
		sShell.setLayout(null);
		createGrupoVentasDetalle();
		createGrupoCriterioBusqueda();
		sShell.setBounds(new Rectangle(184, 184, 800, 600));
	}

	/**
	 * This method initializes grupoVentasEncabezado	
	 *
	 */
	private void createGrupoVentasEncabezado() {
		grupoVentasEncabezado = new Group(sShell, SWT.NONE);
		grupoVentasEncabezado.setLayout(null);
		grupoVentasEncabezado.setText("Encabezado Ventas");
		grupoVentasEncabezado.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoVentasEncabezado.setBounds(new Rectangle(11, 151, 768, 196));
		tComprasEncabezado = new Table(grupoVentasEncabezado, SWT.NONE);
		tComprasEncabezado.setHeaderVisible(true);
		tComprasEncabezado.setLinesVisible(true);
		tComprasEncabezado.setBounds(new Rectangle(11, 21, 743, 140));
		lNumeroVentas = new Label(grupoVentasEncabezado, SWT.NONE);
		lNumeroVentas.setBounds(new Rectangle(603, 171, 91, 15));
		lNumeroVentas.setText("Número Ventas:");
		tNumeroventa = new Text(grupoVentasEncabezado, SWT.BORDER);
		tNumeroventa.setBounds(new Rectangle(698, 166, 54, 24));
	}

	/**
	 * This method initializes grupoVentasDetalle	
	 *
	 */
	private void createGrupoVentasDetalle() {
		grupoVentasDetalle = new Group(sShell, SWT.NONE);
		grupoVentasDetalle.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoVentasDetalle.setLayout(null);
		grupoVentasDetalle.setText("Detalle Ventas");
		grupoVentasDetalle.setBounds(new Rectangle(11, 359, 768, 189));
		tbDetalle = new Table(grupoVentasDetalle, SWT.NONE);
		tbDetalle.setHeaderVisible(true);
		tbDetalle.setLinesVisible(true);
		tbDetalle.setBounds(new Rectangle(10, 22, 746, 156));
	}

	/**
	 * This method initializes grupoCriterioBusqueda	
	 *
	 */
	private void createGrupoCriterioBusqueda() {
		grupoCriterioBusqueda = new Group(sShell, SWT.NONE);
		grupoCriterioBusqueda.setLayout(null);
		grupoCriterioBusqueda.setText("Criterios Busqueda");
		grupoCriterioBusqueda.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		createCCriterio();
		grupoCriterioBusqueda.setBounds(new Rectangle(198, 57, 416, 95));
		lFechaInicial = new Label(grupoCriterioBusqueda, SWT.NONE);
		lFechaInicial.setBounds(new Rectangle(10, 64, 73, 15));
		lFechaInicial.setText("Fecha Inicial:");
		lFechaFinal = new Label(grupoCriterioBusqueda, SWT.NONE);
		lFechaFinal.setBounds(new Rectangle(219, 64, 63, 15));
		lFechaFinal.setText("Fecha Final:");
		tFechaInicial = new Text(grupoCriterioBusqueda, SWT.BORDER);
		tFechaInicial.setBounds(new Rectangle(82, 60, 96, 23));
		tFechaFinal = new Text(grupoCriterioBusqueda, SWT.BORDER);
		tFechaFinal.setBounds(new Rectangle(284, 60, 96, 23));
		bCalendario = new Button(grupoCriterioBusqueda, SWT.NONE);
		bCalendario.setBounds(new Rectangle(180, 60, 28, 23));
		bCalendario.setImage(new Image(Display.getCurrent(), "C:/DESARROLLOS DE SOFTWARE/SIPY/imagenes/Calendario.gif"));
		bCalendario1 = new Button(grupoCriterioBusqueda, SWT.NONE);
		bCalendario1.setBounds(new Rectangle(381, 59, 28, 23));
		bCalendario1.setImage(new Image(Display.getCurrent(), "C:/DESARROLLOS DE SOFTWARE/SIPY/imagenes/Calendario.gif"));
	}

	/**
	 * This method initializes cCriterio	
	 *
	 */
	private void createCCriterio() {
		cCriterio = new Combo(grupoCriterioBusqueda, SWT.READ_ONLY);
		cCriterio.setBounds(new Rectangle(121, 24, 158, 23));
		cCriterio.add("Primeras 10 Ventas");
		cCriterio.add("Ultimas 10 Ventas");
		cCriterio.add("Todas");
		cCriterio.add("Rango de Fechas");
		cCriterio.select(1);
	}

}
