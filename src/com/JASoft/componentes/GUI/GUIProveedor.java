package com.JASoft.componentes.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.JASoft.componentesGraficos.ToolBar;


public class GUIProveedor {

	private Shell sShell = null;
	private Group Identificacion = null;
	private Label lTipo = null;
	private Label lRazonSocial = null;
	private Text tRazonSocial = null;
	private Text tNumeroNit = null;
	private Group gDatosPersonales = null;
	private Label lFax = null;
	private Text tFax = null;
	private Combo cDepartamentos = null;
	private Label lDepartamento = null;
	private Label lCiudadMunicipio = null;
	private Text tCiudadMunicipio = null;
	private Text tEmail = null;
	private Label lEmail = null;
	private Text tDireccion = null;
	private Label lDireccion = null;
	private Text tTelefono = null;
	private Label lPaginaWeb = null;
	private Text tPaginaWeb = null;
	private Label lTelefono = null;
	private Label lPais = null;
	private Combo cPais = null;
	private Group grupoContactos = null;
	private Table tbContactos = null;
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
		
		sShell.setText("Proveedor");
		createIdentificacion();
		sShell.setLayout(null);
		createGDatosPersonales();
		createGrupoContactos();
		sShell.setBounds(new Rectangle(175, 175, 800, 600));
	}

	/**
	 * This method initializes Identificacion	
	 *
	 */
	private void createIdentificacion() {
		Identificacion = new Group(sShell, SWT.SHADOW_IN);
		Identificacion.setLayout(null);
		Identificacion.setText("Identificación");
		Identificacion.setBounds(new Rectangle(202, 62, 410, 76));
		lTipo = new Label(Identificacion, SWT.NONE);
		lTipo.setBounds(new Rectangle(21, 20, 36, 15));
		lTipo.setText("Nit:");
		lRazonSocial = new Label(Identificacion, SWT.NONE);
		lRazonSocial.setBounds(new Rectangle(149, 20, 80, 15));
		lRazonSocial.setText("Razón Social:");
		tRazonSocial = new Text(Identificacion, SWT.BORDER);
		tRazonSocial.setBounds(new Rectangle(149, 35, 245, 23));
		tNumeroNit = new Text(Identificacion, SWT.BORDER);
		tNumeroNit.setBounds(new Rectangle(21, 35, 103, 23));
	}

	/**
	 * This method initializes gDatosPersonales	
	 *
	 */
	private void createGDatosPersonales() {
		gDatosPersonales = new Group(sShell, SWT.NONE);
		gDatosPersonales.setLayout(null);
		gDatosPersonales.setText("Datos Generales");
		gDatosPersonales.setBounds(new Rectangle(45, 148, 729, 220));
		lFax = new Label(gDatosPersonales, SWT.NONE);
		lFax.setBounds(new Rectangle(623, 13, 38, 15));
		lFax.setText("Fax:");
		tFax = new Text(gDatosPersonales, SWT.BORDER);
		tFax.setBounds(new Rectangle(621, 31, 90, 23));
		createCDepartamentos();
		lDepartamento = new Label(gDatosPersonales, SWT.NONE);
		lDepartamento.setBounds(new Rectangle(232, 69, 88, 15));
		lDepartamento.setText("Departamento");
		lCiudadMunicipio = new Label(gDatosPersonales, SWT.NONE);
		lCiudadMunicipio.setBounds(new Rectangle(416, 69, 202, 15));
		lCiudadMunicipio.setText("Ciudad / Municipio:");
		tCiudadMunicipio = new Text(gDatosPersonales, SWT.BORDER);
		tCiudadMunicipio.setBounds(new Rectangle(416, 87, 293, 23));
		tEmail = new Text(gDatosPersonales, SWT.BORDER);
		tEmail.setBounds(new Rectangle(269, 174, 288, 23));
		lEmail = new Label(gDatosPersonales, SWT.NONE);
		lEmail.setBounds(new Rectangle(221, 180, 36, 15));
		lEmail.setText("Email:");
		tDireccion = new Text(gDatosPersonales, SWT.BORDER);
		tDireccion.setBounds(new Rectangle(69, 31, 417, 23));
		lDireccion = new Label(gDatosPersonales, SWT.NONE);
		lDireccion.setBounds(new Rectangle(9, 35, 57, 15));
		lDireccion.setText("Dirección: ");
		tTelefono = new Text(gDatosPersonales, SWT.BORDER);
		tTelefono.setBounds(new Rectangle(497, 31, 90, 23));
		lPaginaWeb = new Label(gDatosPersonales, SWT.NONE);
		lPaginaWeb.setBounds(new Rectangle(189, 137, 68, 15));
		lPaginaWeb.setText("Pagina Web:");
		tPaginaWeb = new Text(gDatosPersonales, SWT.BORDER);
		tPaginaWeb.setBounds(new Rectangle(270, 133, 288, 23));
		lTelefono = new Label(gDatosPersonales, SWT.NONE);
		lTelefono.setBounds(new Rectangle(497, 13, 81, 15));
		lTelefono.setText("Teléfono:");
		lPais = new Label(gDatosPersonales, SWT.NONE);
		lPais.setBounds(new Rectangle(37, 91, 32, 15));
		lPais.setText("País");
		createCPais();
	}

	/**
	 * This method initializes cDepartamentos	
	 *
	 */
	private void createCDepartamentos() {
		cDepartamentos = new Combo(gDatosPersonales, SWT.NONE);
		cDepartamentos.setBounds(new Rectangle(232, 87, 156, 23));
	}

	/**
	 * This method initializes cPais	
	 *
	 */
	private void createCPais() {
		cPais = new Combo(gDatosPersonales, SWT.NONE);
		cPais.setBounds(new Rectangle(73, 87, 132, 23));
	}

	/**
	 * This method initializes grupoContactos	
	 *
	 */
	private void createGrupoContactos() {
		grupoContactos = new Group(sShell, SWT.NONE);
		grupoContactos.setLayout(null);
		grupoContactos.setText("Contactos");
		grupoContactos.setBounds(new Rectangle(46, 379, 723, 170));
		tbContactos = new Table(grupoContactos, SWT.NONE);
		tbContactos.setHeaderVisible(true);
		tbContactos.setLinesVisible(true);
		tbContactos.setBounds(new Rectangle(14, 30, 695, 92));
		bAgregar = new Button(grupoContactos, SWT.NONE);
		bAgregar.setBounds(new Rectangle(101, 132, 106, 30));
		bAgregar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Adicionar.gif"));
		bAgregar.setText("Agregar");
		bEditar = new Button(grupoContactos, SWT.NONE);
		bEditar.setBounds(new Rectangle(308, 132, 106, 30));
		bEditar.setText("Editar");
		bEditar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Editar.gif"));
		bEliminar = new Button(grupoContactos, SWT.NONE);
		bEliminar.setBounds(new Rectangle(515, 132, 106, 30));
		bEliminar.setText("Eliminar");
		bEliminar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Eliminar.gif"));
	}

}
