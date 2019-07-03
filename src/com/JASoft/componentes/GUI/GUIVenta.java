package com.JASoft.componentes.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Table;

import com.JASoft.componentesGraficos.ToolBar;


public class GUIVenta {

	private Shell sShell = null;
	private Group grupoCliente = null;
	private Label lTipo = null;
	private Label lNumero = null;
	private Text tNombres = null;
	private Text tNumero = null;
	private Combo cTipo = null;
	private Label lNombres = null;
	private Group grupoCompras = null;
	private Label lNumeroVenta = null;
	private Text tNumeroVenta = null;
	private Label lFecha = null;
	private Text tFecha = null;
	private Label lDetallesFactura = null;
	private Label lLinea = null;
	private Table tbVentasDetalle = null;
	private Group grupoObservaciones = null;
	private Text taObservaciones = null;
	private Text tTotal = null;
	private Label lLinea1 = null;
	private Label lTotal = null;
	private Text tAbono = null;
	private Label lAbono = null;
	private Text tEfectivo = null;
	private Label lEfectivo = null;
	private Text tCambio = null;
	private Label lCambio = null;
	private Text tNuevoSaldo = null;
	private Label lNuevoSaldo = null;
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Venta");
		createGrupoCliente();
		sShell.setLayout(null);
		createGrupoCompras();
		sShell.setBounds(new Rectangle(25, 25, 800, 600));
		lDetallesFactura = new Label(sShell, SWT.NONE);
		lDetallesFactura.setBounds(new Rectangle(286, 176, 150, 15));
		lDetallesFactura.setText("Detalle: Factura de Venta");
		lDetallesFactura.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		lLinea = new Label(sShell, SWT.CENTER);
		lLinea.setBounds(new Rectangle(10, 187, 755, 15));
		lLinea.setText("_______________________________________________________________________________________________________________________________________________________");
		tbVentasDetalle = new Table(sShell, SWT.NONE);
		tbVentasDetalle.setHeaderVisible(true);
		tbVentasDetalle.setLinesVisible(true);
		tbVentasDetalle.setBounds(new Rectangle(16, 213, 751, 197));
		createGrupoObservaciones();
		tTotal = new Text(sShell, SWT.BORDER);
		tTotal.setBounds(new Rectangle(680, 428, 87, 21));
		lLinea1 = new Label(sShell, SWT.NONE);
		lLinea1.setBounds(new Rectangle(680, 410, 87, 15));
		lLinea1.setText("_______________________________________________________________________________________________________________________________________________________");
		lTotal = new Label(sShell, SWT.NONE);
		lTotal.setBounds(new Rectangle(643, 431, 31, 15));
		lTotal.setText("Total:");
		lTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tAbono = new Text(sShell, SWT.BORDER);
		tAbono.setBounds(new Rectangle(680, 455, 87, 21));
		lAbono = new Label(sShell, SWT.NONE);
		lAbono.setBounds(new Rectangle(634, 458, 40, 15));
		lAbono.setText("Abono:");
		lAbono.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tEfectivo = new Text(sShell, SWT.BORDER);
		tEfectivo.setBounds(new Rectangle(680, 483, 87, 21));
		lEfectivo = new Label(sShell, SWT.NONE);
		lEfectivo.setBounds(new Rectangle(622, 486, 52, 15));
		lEfectivo.setText("Efectivo:");
		lEfectivo.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tCambio = new Text(sShell, SWT.BORDER);
		tCambio.setBounds(new Rectangle(680, 509, 87, 21));
		lCambio = new Label(sShell, SWT.NONE);
		lCambio.setBounds(new Rectangle(625, 512, 49, 15));
		lCambio.setText("Cambio:");
		lCambio.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tNuevoSaldo = new Text(sShell, SWT.BORDER);
		tNuevoSaldo.setBounds(new Rectangle(680, 535, 87, 21));
		lNuevoSaldo = new Label(sShell, SWT.NONE);
		lNuevoSaldo.setBounds(new Rectangle(596, 538, 78, 15));
		lNuevoSaldo.setText("Nuevo Saldo:");
		lNuevoSaldo.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
	}

	/**
	 * This method initializes grupoCliente	
	 *
	 */
	private void createGrupoCliente() {
		grupoCliente = new Group(sShell, SWT.SHADOW_IN);
		grupoCliente.setLayout(null);
		grupoCliente.setText("Cliente");
		grupoCliente.setBounds(new Rectangle(23, 58, 457, 114));
		lTipo = new Label(grupoCliente, SWT.NONE);
		lTipo.setBounds(new Rectangle(19, 24, 36, 15));
		lTipo.setText("Tipo:");
		lNumero = new Label(grupoCliente, SWT.NONE);
		lNumero.setBounds(new Rectangle(206, 24, 61, 15));
		lNumero.setText("Número:");
		tNombres = new Text(grupoCliente, SWT.BORDER);
		tNombres.setBounds(new Rectangle(85, 77, 359, 23));
		tNumero = new Text(grupoCliente, SWT.BORDER);
		tNumero.setBounds(new Rectangle(207, 41, 236, 23));
		createCTipo();
		lNombres = new Label(grupoCliente, SWT.NONE);
		lNombres.setBounds(new Rectangle(16, 81, 60, 15));
		lNombres.setText("Nombres:");
	}

	/**
	 * This method initializes cTipo	
	 *
	 */
	private void createCTipo() {
		cTipo = new Combo(grupoCliente, SWT.NONE);
		cTipo.setBounds(new Rectangle(20, 41, 99, 23));
	}

	/**
	 * This method initializes grupoCompras	
	 *
	 */
	private void createGrupoCompras() {
		grupoCompras = new Group(sShell, SWT.NONE);
		grupoCompras.setLayout(null);
		grupoCompras.setText("Información Venta");
		grupoCompras.setBounds(new Rectangle(498, 58, 276, 114));
		lNumeroVenta = new Label(grupoCompras, SWT.NONE);
		lNumeroVenta.setBounds(new Rectangle(80, 26, 98, 15));
		lNumeroVenta.setText("Número Venta:");
		tNumeroVenta = new Text(grupoCompras, SWT.BORDER);
		tNumeroVenta.setBounds(new Rectangle(186, 22, 53, 23));
		tNumeroVenta.setEnabled(false);
		tNumeroVenta.setEditable(false);
		lFecha = new Label(grupoCompras, SWT.NONE);
		lFecha.setBounds(new Rectangle(76, 71, 41, 15));
		lFecha.setText("Fecha:");
		tFecha = new Text(grupoCompras, SWT.BORDER);
		tFecha.setBounds(new Rectangle(123, 67, 114, 23));
		tFecha.setEnabled(false);
		tFecha.setEditable(false);
	}

	/**
	 * This method initializes grupoObservaciones	
	 *
	 */
	private void createGrupoObservaciones() {
		grupoObservaciones = new Group(sShell, SWT.NONE);
		grupoObservaciones.setLayout(null);
		grupoObservaciones.setText("Observaciones / Anotaciones");
		grupoObservaciones.setBounds(new Rectangle(19, 438, 533, 104));
		taObservaciones = new Text(grupoObservaciones, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		taObservaciones.setBounds(new Rectangle(21, 26, 504, 66));
	}

}
