package com.JASoft.componentes.GUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;


public class GUICompra extends ConfigurarText implements FocusListener,SelectionListener{

	private Shell sShell = null;
	private ToolBar toolBar;
	private Group lNit = null;
	private Label lTipo = null;
	private Label lRazonSocial = null;
	private Text tRazonSocial = null;
	private Text tNumeroNit = null;
	private Button bMostrarListaValores = null;
	private Group grupoCompras = null;
	private Label lNumeroCompra = null;
	private Text tNumeroCompra = null;
	private Label lNumeroFactura = null;
	private Text tNumeroFactura = null;
	private Label lFecha = null;
	private Text tFecha = null;
	private Label lLinea = null;
	private Label lDetallesFactura = null;
	private Table tbDetallesCompras = null;
	private Group grupoObservaciones = null;
	private Text taObservaciones = null;
	private Label lLinea1 = null;
	private Label lTotal = null;
	private Text tTotal = null;
	private Label lModena = null;
	private Combo cMoneda = null;
	private Text tCambioPesos = null;
	private Label lCambioPesos = null;
	private ConectarMySQL conectarMySQL= null;
	private ArrayList<Text> arrayTextRequeridos= null;
	private Label lTotalDolares = null;
	private Text tTotalDolares = null;  

	
	

	/*
	 * Constructor general
	 */
	public GUICompra(ConectarMySQL conectarMySQL) {
		
		this.conectarMySQL = conectarMySQL;
		
		createSShell();
		
		centrarShell(sShell);
		
		tNumeroNit.forceFocus(); //Se asigna el foco al primer componente navegable
		
		agregarConfiguraciones();
		
		agregarEventos();
		
		crearArrayRequeridos();
		
		bloquearShell(sShell);

	}

	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	public void crearArrayRequeridos(){
		
		arrayTextRequeridos = new ArrayList<Text>();
		
		arrayTextRequeridos.add(tNumeroNit);
		arrayTextRequeridos.add(tNumeroFactura);
		arrayTextRequeridos.add(tFecha);
		arrayTextRequeridos.add(tTotal);
		
		 
	}
	
	
	
	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */
	
	public void limpiar(boolean comodin) {

		tRazonSocial.setText("");
		tNumeroCompra.setText("");
		tNumeroFactura.setText("");
		tTotal.setText("");
		taObservaciones.setText("");
		cMoneda.select(0);
		
		if (comodin) {
			
			tNumeroNit.setText("");
			tNumeroNit.forceFocus();
			
		}


	}
	
	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	public void guardar(){

		if (validarRegistro(arrayTextRequeridos, sShell)){

			try {

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoProductos(conectarMySQL.getConexion());


				 // Para insertar la imagen se extraeran los byte del archivo
			
				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, tNumeroNit.getText());
				llamarProcedimientoAlmacenado.setString(3, tNumeroFactura.getText());
				llamarProcedimientoAlmacenado.setInt(4, new Integer(tTotal.getText()));
				llamarProcedimientoAlmacenado.setString(5,cMoneda.getSelectionIndex() == 0 ? "C" : "P");
				llamarProcedimientoAlmacenado.setString(6, tCambioPesos.getText());
				llamarProcedimientoAlmacenado.setString(7, taObservaciones.getText());
				llamarProcedimientoAlmacenado.setString(7, null);
				
				llamarProcedimientoAlmacenado.setBinaryStream( 9, null );
               
				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Compra ha sido registrado");
				limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell, "Error al guardar el registro de compras " +e);
			}
		}
	}	
	
	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){
		
		
		//Se agregar eventos de focos a tNumeroNit para que consulte el cliente
		tNumeroNit.addFocusListener(this);
		tNumeroNit.addVerifyListener(getValidarEntradaNumeros());
		tNumeroFactura.addVerifyListener(getValidarEntradaNumeros());
		tCambioPesos.addVerifyListener(getValidarEntradaNumeros());
		taObservaciones.addVerifyListener(getConvertirMayusculaLetras());
		

		
		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		
		
	}
	
	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	public void agregarConfiguraciones(){
		
		//Se configura el limita de datos por Text
		tNumeroNit.setTextLimit(10);
		tNumeroFactura.setTextLimit(20);
		tCambioPesos.setTextLimit(4);
		
		cMoneda.add("Colombia");
		cMoneda.add("Panama");
		//Se configuran las columnas
		/*TableColumn clTalla = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clColor = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clStock = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clMinimo = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clPorcentajeGanaciaPrecioMinimo = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clPrecioLista = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clPorcentajeGanaciaPrecioLista = new TableColumn(taEjemplares, SWT.CENTER);
		
		clTalla.setText("Talla");
		clColor.setText("Color");
		clStock.setText("Stock");
		clMinimo.setText("Precio Minimo");
		clPorcentajeGanaciaPrecioMinimo.setText("% Ganancia");
		clPrecioLista.setText("Precio Lista");
		clPorcentajeGanaciaPrecioLista.setText("% Ganancia");
		
		clTalla.setWidth(39);
		clColor.setWidth(256);
		clStock.setWidth(53);
		clMinimo.setWidth(98);
		clPorcentajeGanaciaPrecioMinimo.setWidth(89);
		clPrecioLista.setWidth(94);
		clPorcentajeGanaciaPrecioLista.setWidth(92);*/
		
	}

	  //**************************** Metodo buscarInformacion ************************

	private boolean buscarInformacionProveedores(){

		boolean resultadoBoolean = false;

		final String sentenciaSQL = "Select razonSocial "+
		                            "From Proveedores " + 
		                            "Where idProveedor = '" + tNumeroNit.getText() +"'";


		try {

			ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

			if (resultado.next()) {

				// Se asigna el valor
				tRazonSocial.setText(resultado.getString(1));
				resultadoBoolean = true;

			}    

		}  catch (Exception e) {

			mensajeError(sShell,"No existe ningun registro en la tabla proveedores");        

		}

		return resultadoBoolean;

	}
	
	
	//**************************** Eventos de foco ********************************************************

	@Override
	public void focusGained(FocusEvent a) {


	}


	@Override
	public void focusLost(FocusEvent a) {

		Object fuente = a.getSource();

		if (fuente == tNumeroNit && !tNumeroNit.getText().isEmpty()) {

			if (!buscarInformacionProveedores()) {
				
				mensajeError(sShell, "No encontrado");
				
			}

		}
				 
	}

	//**************************** Eventos de accion *******************************************************

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void widgetSelected(SelectionEvent a) {

		Object fuente = a.getSource();

		if (fuente == toolBar.getbLimpiar())

			limpiar(true);

		else

			if (fuente == toolBar.getbGuardar())

				guardar();
			
	}


	
	// *********************************** Interfaz Grafica *****************************************************
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Compras");
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		
		sShell.setLayout(null);
		createLNit();
		createGrupoCompras();
		sShell.setBounds(new Rectangle(75, 75, 800, 600));
		lLinea = new Label(sShell, SWT.CENTER);
		lLinea.setBounds(new Rectangle(11, 233, 759, 14));
		lLinea.setText("_______________________________________________________________________________________________________________________________________________________");
		lDetallesFactura = new Label(sShell, SWT.NONE);
		lDetallesFactura.setBounds(new Rectangle(295, 218, 156, 13));
		lDetallesFactura.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		lDetallesFactura.setText("Detalle: Factura de Compra");
		tbDetallesCompras = new Table(sShell, SWT.NONE);
		tbDetallesCompras.setHeaderVisible(true);
		tbDetallesCompras.setLinesVisible(true);
		tbDetallesCompras.setBounds(new Rectangle(11, 251, 760, 232));
		createGrupoObservaciones();
		lLinea1 = new Label(sShell, SWT.NONE);
		lLinea1.setBounds(new Rectangle(667, 486, 109, 15));
		lLinea1.setText("_______________________________________________________________________________________________________________________________________________________");
		lTotal = new Label(sShell, SWT.NONE);
		lTotal.setBounds(new Rectangle(592, 510, 69, 15));
		lTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		lTotal.setText("Total Pesos:");
		tTotal = new Text(sShell, SWT.BORDER);
		tTotal.setBounds(new Rectangle(666, 505, 109, 23));
		tTotal.setEditable(false);
		tTotal.setEnabled(false);
		tTotal.setToolTipText("Total de la factura");
		lTotalDolares = new Label(sShell, SWT.NONE);
		lTotalDolares.setBounds(new Rectangle(584, 538, 77, 15));
		lTotalDolares.setText("Total Dolares:");
		lTotalDolares.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tTotalDolares = new Text(sShell, SWT.BORDER);
		tTotalDolares.setBounds(new Rectangle(666, 534, 109, 23));
		tTotalDolares.setToolTipText("Total de la factura");
		tTotalDolares.setEditable(false);
		tTotalDolares.setEnabled(false);
	}
	/**
	 * This method initializes lNit	
	 *
	 */
	private void createLNit() {
		lNit = new Group(sShell, SWT.SHADOW_IN);
		lNit.setLayout(null);
		lNit.setText("Proveedor");
		lNit.setBounds(new Rectangle(15, 53, 408, 155));
		lTipo = new Label(lNit, SWT.NONE);
		lTipo.setBounds(new Rectangle(12, 57, 36, 15));
		lTipo.setText("Nit:");
		lRazonSocial = new Label(lNit, SWT.NONE);
		lRazonSocial.setBounds(new Rectangle(121, 57, 80, 15));
		lRazonSocial.setText("Razón Social:");
		tRazonSocial = new Text(lNit, SWT.BORDER);
		tRazonSocial.setBounds(new Rectangle(120, 73, 243, 23));
		tRazonSocial.setEnabled(false);
		tRazonSocial.setToolTipText("Razón social");
		tRazonSocial.setEditable(false);
		tNumeroNit = new Text(lNit, SWT.BORDER);
		tNumeroNit.setBounds(new Rectangle(10, 73, 99, 23));
		tNumeroNit.setToolTipText("Digite el Nit de proveedor");
		bMostrarListaValores = new Button(lNit, SWT.NONE);
		bMostrarListaValores.setBounds(new Rectangle(370, 73, 26, 23));
		bMostrarListaValores.setText("...");
	}
	/**
	 * This method initializes grupoCompras	
	 *
	 */
	private void createGrupoCompras() {
		grupoCompras = new Group(sShell, SWT.NONE);
		grupoCompras.setLayout(null);
		grupoCompras.setBounds(new Rectangle(462, 53, 315, 155));
		lNumeroCompra = new Label(grupoCompras, SWT.NONE);
		lNumeroCompra.setBounds(new Rectangle(143, 22, 98, 15));
		lNumeroCompra.setText("Número Compra:");
		tNumeroCompra = new Text(grupoCompras, SWT.BORDER);
		tNumeroCompra.setBounds(new Rectangle(249, 18, 53, 23));
		tNumeroCompra.setEnabled(false);
		tNumeroCompra.setToolTipText("Número de factura de compra Generado automaticamente por el sistema");
		tNumeroCompra.setEditable(false);
		lNumeroFactura = new Label(grupoCompras, SWT.NONE);
		lNumeroFactura.setBounds(new Rectangle(51, 88, 74, 15));
		lNumeroFactura.setText("Factura  Nro:");
		tNumeroFactura = new Text(grupoCompras, SWT.BORDER);
		tNumeroFactura.setBounds(new Rectangle(130, 84, 172, 23));
		tNumeroFactura.setToolTipText("Número de factura");
		lFecha = new Label(grupoCompras, SWT.NONE);
		lFecha.setBounds(new Rectangle(139, 56, 41, 15));
		lFecha.setText("Fecha:");
		tFecha = new Text(grupoCompras, SWT.BORDER);
		tFecha.setBounds(new Rectangle(186, 52, 115, 23));
		tFecha.setEnabled(false);
		tFecha.setToolTipText("Fecha de la compra, la genera automaticamente el sistema");
		tFecha.setEditable(false);
		lModena = new Label(grupoCompras, SWT.NONE);
		lModena.setBounds(new Rectangle(7, 123, 55, 15));
		lModena.setText("Moneda:");
		createCMoneda();
		tCambioPesos = new Text(grupoCompras, SWT.BORDER);
		tCambioPesos.setBounds(new Rectangle(226, 120, 76, 23));
		tCambioPesos.setToolTipText("Digite el valor del cambio de dolares, Solo aplica para cuando la monda sea dolares");
		lCambioPesos = new Label(grupoCompras, SWT.NONE);
		lCambioPesos.setBounds(new Rectangle(170, 124, 51, 15));
		lCambioPesos.setText("Vr. Pesos");
	}
	/**
	 * This method initializes grupoObservaciones	
	 *
	 */
	private void createGrupoObservaciones() {
		grupoObservaciones = new Group(sShell, SWT.NONE);
		grupoObservaciones.setLayout(null);
		grupoObservaciones.setText("Observaciones / Anotaciones");
		grupoObservaciones.setBounds(new Rectangle(11, 489, 538, 64));
		taObservaciones = new Text(grupoObservaciones, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		taObservaciones.setBounds(new Rectangle(23, 17, 504, 39));
		taObservaciones.setToolTipText("Observaciones de la factura");
	}
	/**
	 * This method initializes cMoneda	
	 *
	 */
	private void createCMoneda() {
		cMoneda = new Combo(grupoCompras, SWT.NONE);
		cMoneda.setToolTipText("Moneda en que hizo las compras");
		cMoneda.setBounds(new Rectangle(69, 119, 85, 23));
	}

}
