package com.JASoft.componentes.GUI;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public class PestañaParaProductos {

	private Shell sShell = null;
	private Group grupoProductos = null;
	private Label lCantidad = null;
	private Text tInversion = null;
	private Combo cInversion = null;
	private Label lDescripcion = null;
	private Group grupoCantidad = null;
	private Label label = null;
	private Text tStockComprado = null;
	private Label lStockvendido = null;
	private Text tStockVendido = null;
	private Text tStockActual = null;
	private Label lStockActual = null;
	private Button bVerStock = null;
	private Group grupoImporte = null;
	private Label lImporteComprado = null;
	private Text tImporteComprado = null;
	private Label lImportevendido = null;
	private Text tImporteVendido = null;
	private Text tImporteActual = null;
	private Label lImporteActual = null;
	private Button bVerventas = null;
	private Group grupoGraficavendidaPorMes = null;
	private Group grupoGraficavendidaPorMes1 = null;
	private Label lFecha = null;
	private Text tFecha = null;
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Shell");
		createGrupoProductos();
		sShell.setSize(new Point(790, 580));
		sShell.setLayout(null);
	}
	/**
	 * This method initializes grupoProductos	
	 *
	 */
	private void createGrupoProductos() {
		grupoProductos = new Group(sShell, SWT.NONE);
		grupoProductos.setLayout(null);
		grupoProductos.setBounds(new Rectangle(0, 0, 753, 494));
		lCantidad = new Label(grupoProductos, SWT.NONE);
		lCantidad.setBounds(new Rectangle(13, 19, 75, 15));
		lCantidad.setText("Inversión N°: ");
		tInversion = new Text(grupoProductos, SWT.BORDER | SWT.READ_ONLY);
		tInversion.setBounds(new Rectangle(97, 49, 198, 23));
		createCInversion();
		lDescripcion = new Label(grupoProductos, SWT.NONE);
		lDescripcion.setBounds(new Rectangle(17, 53, 71, 15));
		lDescripcion.setText("Descripción :");
		createGrupoCantidad();
		createGrupoImporte();
		createGrupoGraficavendidaPorMes();
		createGrupoGraficavendidaPorMes1();
		lFecha = new Label(grupoProductos, SWT.NONE);
		lFecha.setBounds(new Rectangle(335, 53, 42, 15));
		lFecha.setText("Fecha :");
		tFecha = new Text(grupoProductos, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		tFecha.setBounds(new Rectangle(381, 49, 85, 23));
	}
	/**
	 * This method initializes cInversion	
	 *
	 */
	private void createCInversion() {
		cInversion = new Combo(grupoProductos, SWT.NONE);
		cInversion.setBounds(new Rectangle(98, 15, 64, 23));
	}
	/**
	 * This method initializes grupoCantidad	
	 *
	 */
	private void createGrupoCantidad() {
		grupoCantidad = new Group(grupoProductos, SWT.NONE);
		grupoCantidad.setLayout(null);
		grupoCantidad.setText("Stock");
		grupoCantidad.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoCantidad.setBounds(new Rectangle(12, 80, 215, 197));
		label = new Label(grupoCantidad, SWT.NONE);
		label.setText("Comprado:");
		label.setBounds(new Rectangle(50, 39, 60, 15));
		tStockComprado = new Text(grupoCantidad, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tStockComprado.setBounds(new Rectangle(115, 35, 75, 23));
		lStockvendido = new Label(grupoCantidad, SWT.NONE);
		lStockvendido.setBounds(new Rectangle(60, 72, 50, 15));
		lStockvendido.setText(" Vendido:");
		tStockVendido = new Text(grupoCantidad, SWT.BORDER | SWT.RIGHT| SWT.READ_ONLY);
		tStockVendido.setBounds(new Rectangle(115, 68, 75, 23));
		tStockActual = new Text(grupoCantidad, SWT.BORDER | SWT.RIGHT| SWT.READ_ONLY);
		tStockActual.setBounds(new Rectangle(115, 100, 75, 23));
		lStockActual = new Label(grupoCantidad, SWT.NONE);
		lStockActual.setBounds(new Rectangle(70, 104, 40, 15));
		lStockActual.setText(" Actual:");
		bVerStock = new Button(grupoCantidad, SWT.NONE);
		bVerStock.setBounds(new Rectangle(43, 153, 116, 29));
		bVerStock.setText("Ver Stock");
	}
	/**
	 * This method initializes grupoImporte	
	 *
	 */
	private void createGrupoImporte() {
		grupoImporte = new Group(grupoProductos, SWT.NONE);
		grupoImporte.setLayout(null);
		grupoImporte.setText("Importe");
		grupoImporte.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoImporte.setBounds(new Rectangle(14, 285, 212, 197));
		lImporteComprado = new Label(grupoImporte, SWT.NONE);
		lImporteComprado.setBounds(new Rectangle(50, 39, 60, 15));
		lImporteComprado.setText("Comprado:");
		tImporteComprado = new Text(grupoImporte, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tImporteComprado.setBounds(new Rectangle(115, 35, 75, 23));
		lImportevendido = new Label(grupoImporte, SWT.NONE);
		lImportevendido.setBounds(new Rectangle(60, 72, 50, 15));
		lImportevendido.setText(" Vendido:");
		tImporteVendido = new Text(grupoImporte, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tImporteVendido.setBounds(new Rectangle(115, 68, 75, 23));
		tImporteActual = new Text(grupoImporte, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tImporteActual.setBounds(new Rectangle(115, 100, 75, 23));
		lImporteActual = new Label(grupoImporte, SWT.NONE);
		lImporteActual.setBounds(new Rectangle(70, 104, 40, 15));
		lImporteActual.setText(" Actual:");
		bVerventas = new Button(grupoImporte, SWT.NONE);
		bVerventas.setBounds(new Rectangle(45, 140, 116, 29));
		bVerventas.setText("Ver Ventas");
	}
	/**
	 * This method initializes grupoGraficavendidaPorMes	
	 *
	 */
	private void createGrupoGraficavendidaPorMes() {
		grupoGraficavendidaPorMes = new Group(grupoProductos, SWT.NONE);
		grupoGraficavendidaPorMes.setLayout(new GridLayout());
		grupoGraficavendidaPorMes.setText("Cantidad de Productos Vendidos por Mes");
		grupoGraficavendidaPorMes.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoGraficavendidaPorMes.setBounds(new Rectangle(241, 80, 498, 197));
	}
	/**
	 * This method initializes grupoGraficavendidaPorMes1	
	 *
	 */
	private void createGrupoGraficavendidaPorMes1() {
		grupoGraficavendidaPorMes1 = new Group(grupoProductos, SWT.NONE);
		grupoGraficavendidaPorMes1.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoGraficavendidaPorMes1.setLayout(new GridLayout());
		grupoGraficavendidaPorMes1.setText("Importe de Productos Vendidos por Mes");
		grupoGraficavendidaPorMes1.setBounds(new Rectangle(242, 285, 494, 197));
	}

}
