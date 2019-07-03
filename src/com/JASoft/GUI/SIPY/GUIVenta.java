package com.JASoft.GUI.SIPY;

import java.io.ObjectInputStream.GetField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Table;

import com.JASoft.GUI.SIPY.GUICompra.GUIDialogoComprasEncabezado;
import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ListarValor;
import com.JASoft.componentesGraficos.ToolBar;


public class GUIVenta extends ConfigurarText implements FocusListener,SelectionListener,TraverseListener{

	private Shell sShell = null;
	private ToolBar toolBar;
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
	private ArrayList<Text> arrayTextRequeridos= null;  //  @jve:decl-index=0:
	private ListarValor listarValorSubproductos = null;
	private ConectarMySQL conectarMySQL = null;
	private PreparedStatement consultar;  //  @jve:decl-index=0:
	private ListarValor listarValor;
	private Text textoComodin; //Se utiliza para cambiar el combo con la lista de valores
	private Text tCodigoProductoTabla;
	private Text tCantidadTabla;
	private Text tPrecioProductoTabla;
	private Text tDescripcionProductoTabla;
	private Text tExistenciaTabla;
	private PreparedStatement consultarExistencia;  //  @jve:decl-index=0:
	private int saldoCliente;
	
	
	public GUIVenta(ConectarMySQL conectarMySQL,Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		centrarShell(sShell);

		tNumero.forceFocus(); //Se asigna el foco al primer componente navegable
		
		agregarConfiguraciones();	
		
		agregarEventos();

		crearArrayRequeridos();
		

		sShell.open();

	}



	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void crearArrayRequeridos(){

		arrayTextRequeridos = new ArrayList<Text>();

    }
	
	
	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	private void limpiar(boolean comodin) {

		tNuevoSaldo.setText("0");
		tAbono.setText("0");
		tTotal.setText("0");
		tCambio.setText("0");
		tEfectivo.setText("0");
		
		taObservaciones.setText("");
		tFecha.setText(getObtenerFecha(conectarMySQL));
		tNombres.setText("");
		saldoCliente = 0;
		
		listarValor.ocultarListaValor();
		listarValorSubproductos.ocultarListaValor();
		
		//Se borran los TableEditor de la tabla
		Control controles[] = tbVentasDetalle.getChildren();

		for (Control control : controles )

			control.dispose();


		//Se borran los items de la Tabla
		tbVentasDetalle.removeAll();
		
		
		if (comodin) {

			tNumero.setText("");
			tNumero.forceFocus();
			cTipo.select(0);

		}
        
		agregaItemTabla();
		listarValor.setConfiguraRetorno(tCodigoProductoTabla);


	}

	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void guardar(){

		if (validarRegistro(arrayTextRequeridos, sShell)){

			try {

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoVentas(conectarMySQL.getConexion());


				// Para insertar la imagen se extraeran los byte del archivo

				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, cTipo.getSelectionIndex() == 0 ? "C" : cTipo.getSelectionIndex() == 1 ? "T" : cTipo.getSelectionIndex() == 2 ? "P" : "E");
				llamarProcedimientoAlmacenado.setString(3, tNumero.getText());
				llamarProcedimientoAlmacenado.setInt(4,getMonedaAEntero(tTotal.getText()));
				llamarProcedimientoAlmacenado.setString(5,formatearDatosDetalles());
				llamarProcedimientoAlmacenado.setInt(6,getMonedaAEntero(tAbono.getText()));
				llamarProcedimientoAlmacenado.setString(7,taObservaciones.getText());
				llamarProcedimientoAlmacenado.setString(8,null);
                
				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Venta ha sido registrada");
				limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell, "Error al guardar el registro de ventas " +e);
			}
		}
	}	

	
	/*
	 * Metodo agregarEventos: agregar eventos a los componentes de la GUI
	 */
	private void agregarEventos(){


		//Se instancia el texto comodin
		textoComodin = new Text(sShell,SWT.BORDER);
		
		
		//Se agregan eventos de foco
		tNumero.addFocusListener(this);
		tNombres.addFocusListener(this);
		tEfectivo.addFocusListener(this);
		tAbono.addFocusListener(this);
		
		//Se agregan eventos de verificacion
		tNumero.addVerifyListener(getValidarEntradaNumeros());
		tEfectivo.addVerifyListener(getValidarEntradaNumeros());
		tAbono.addVerifyListener(getValidarEntradaNumeros());
		tNombres.addVerifyListener(getConvertirMayusculaLetras());
		taObservaciones.addVerifyListener(getConvertirMayusculaLetras());

		tNumero.addTraverseListener(this);
		tEfectivo.addTraverseListener(this);
		tAbono.addTraverseListener(this);
		
		// Se configuran los parametros para la lista de valores
		String sentenciaSQL = "Select Case TipoID  When 'C' Then 'Cedula' When 'T' Then 'Tarjeta'When 'E' Then 'Extranjeria' End Tipo,idCliente Identificacion,Concat_WS(' ',Nombres, Apellidos) Nombres " +
				               "From Clientes Where Nombres ";

		
		int anchoColumnas[] = { 55, 90,345 };
		
		Object[][] objetosRetorno = new Object[3][2];
		objetosRetorno[0][0] = tNumero;
		objetosRetorno[0][1] = 1;
		objetosRetorno[1][0] = tNombres;
		objetosRetorno[1][1] = 2;
		objetosRetorno[2][0] = textoComodin;
		objetosRetorno[2][1] = 0;
		
		

		listarValor.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL,objetosRetorno,
				tCodigoProductoTabla, anchoColumnas);

		tNombres.addKeyListener(listarValor);
		
		//Se agrega un evento de validacion en el nombres
		tNombres.addTraverseListener(this);

		//Se agrega este elemento como comodin para agregar el codigo del color en la tabla
		textoComodin.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {

				if (textoComodin.equals("Cedula")) 

					cTipo.select(0);
				else

					if (textoComodin.equals("Tarjeta")) 

						cTipo.select(1);
					else
						if (textoComodin.equals("Pasaporte")) 

							cTipo.select(2);
						else
							if (textoComodin.equals("Extranjeria")) 

								cTipo.select(3);

			}
		});
		
		 
		

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);


	}

	/*
	 * Metodo eliminar: Se consulta un cliente
	 */
	private boolean consultar() {
		
		boolean resultadoBoolean = false;

		try {

			String sentenciaSQL = "Select Nombres, Apellidos,Saldo "
							     + "From Clientes C "
								+ "Where c.tipoId= ?  and c.idCliente = ?";

			if (consultar == null)
				
				consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);
			
				consultar.setString(1, getTipoDocumento());
			
			consultar.setString(2, tNumero.getText());

			ResultSet resultado = consultar.executeQuery();

			if (resultado.next()) { // Se extraen los elementos

				tNombres.setText(devuelveCadenaVaciaParaNulo(resultado.getString(1) + " " + resultado.getString(2)));
				setSaldoCliente(resultado.getInt(3));
				resultadoBoolean = true;
		
			} else

				limpiar(false);

		} catch (SQLException e) {

			mensajeError(sShell,"Problemas al consultar en clentes"+ e);
		}
		
		
		return resultadoBoolean;

	}

	
	/*
	 * Metodo getTipoDocumento: Devuelve el caracter del tipo de documento como
	 * se especifico en el tabla
	 */
	private String getTipoDocumento() {

		String resultado = cTipo.getSelectionIndex() == 0 ? "C" : cTipo
				.getSelectionIndex() == 1 ? "T"
						: cTipo.getSelectionIndex() == 2 ? "P" : "E";

		return resultado;

	}
	
	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	private void agregarConfiguraciones(){

		//Se configura el limita de datos por Text
		tNumero.setTextLimit(10);
		tFecha.setText(getObtenerFecha(conectarMySQL));
		
		tNuevoSaldo.setText("0");
		tAbono.setText("0");
		tTotal.setText("0");
		tCambio.setText("0");
		

		cTipo.select(0);

		//Se configuran las columnas
		TableColumn clCodigoProducto = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clNombreProducto = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clCodigoExistencia = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clStock = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clTalla = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clMarca = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clColor = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clCantidad  = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clPrecioVenta  = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clSubtotal = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn precioMinimo = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn precioLista = new TableColumn(tbVentasDetalle, SWT.CENTER);
		
		//Se configuran los nombres de columnas
		clCodigoProducto.setText("Código");
		clNombreProducto.setText("Descripción");
		clCodigoExistencia.setText("Existencia");
		clStock.setText("Stock");
		clTalla.setText("Talla");
		clColor.setText("Color");
		clMarca.setText("Marca");
		clCantidad.setText("Cantidad");
		clPrecioVenta.setText("Precio");
		clSubtotal.setText("Subtotal");
		
		
		
		
		//Se configuran los tamaños  de columnas
		clCodigoProducto.setWidth(52);
		clNombreProducto.setWidth(166);
		clCodigoExistencia.setWidth(66);
		clStock.setWidth(41);
		clTalla.setWidth(39);
		clColor.setWidth(124);
		clMarca.setWidth(114);
		clCantidad.setWidth(61);
		clPrecioVenta.setWidth(56);
		clSubtotal.setWidth(65);
		
		agregarEditorTabla(new TableItem(tbVentasDetalle,SWT.NONE));
		
		sShell.setImage(new Image(Display.getCurrent(), "imagenes/VentasFacturacion.gif"));
		
		//Se agregan las configuraciones para los texto
		tTotal.setForeground(getColorRojo());
		tNuevoSaldo.setForeground(getColorRojo());

	}
	
	
	//******************************************* agregaItemTabla ***********************************************************
	private void agregaItemTabla() {

		TableItem item = new TableItem(tbVentasDetalle, SWT.NONE);
		agregarEditorTabla(item);

	}
	
	//******************************************* agregarEditorTabla ***********************************************************
	private void agregarEditorTabla(final TableItem item) {


		//Se definen los Text que se van agregan a la Tabla
		tCodigoProductoTabla = new Text(tbVentasDetalle, SWT.NONE );
		tCodigoProductoTabla.addFocusListener(this);

		tDescripcionProductoTabla = new Text(tbVentasDetalle, SWT.NONE);
		tDescripcionProductoTabla.addFocusListener(this);
		
		tExistenciaTabla  = new Text(tbVentasDetalle, SWT.CENTER);
		tExistenciaTabla.addFocusListener(this);

		tCantidadTabla = new Text(tbVentasDetalle, SWT.RIGHT) ;
		tCantidadTabla.addFocusListener(this);
		
		tPrecioProductoTabla = new Text(tbVentasDetalle, SWT.RIGHT);
		tPrecioProductoTabla.addFocusListener(this);
		
		//Se definen los eventos para el Text tCodigoProducto
		tCodigoProductoTabla.addVerifyListener(getValidarEntradaNumeros());
		tDescripcionProductoTabla.addVerifyListener(getConvertirMayusculaLetras());
		tExistenciaTabla.addVerifyListener(getConvertirMayusculaLetras());
		tCantidadTabla.addVerifyListener(getValidarEntradaNumeros());
		tPrecioProductoTabla.addVerifyListener(getValidarEntradaNumeros());
		
		//Se agregan los eventos
		tCodigoProductoTabla.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {

				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

					String producto = buscarInformacionProducto(tCodigoProductoTabla.getText());

					if (producto == null) {

						mensajeError(sShell, "Código de producto " + tCodigoProductoTabla.getText() + " no ha sido registrado");
						tCodigoProductoTabla.setText("");
						e.doit = false;

					} else {

						tDescripcionProductoTabla.setText(producto);
						item.setText(0, tCodigoProductoTabla.getText());

					}
				}
			}

		});

		
		tCantidadTabla.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {

				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {
					
					if (!item.getText(3).isEmpty()) {
					
						int stock = new Integer(item.getText(3));
						int cantidad = new Integer(tCantidadTabla.getText());
						
						if (cantidad > stock){
							
							mensajeError(sShell, "La cantidad a vender excede la existencia");
							e.doit = false;
							
						} else {
						
							item.setText(7, tCantidadTabla.getText());
						}
							
							
						
					} else {
						
						mensajeError(sShell, "Se debe especificar el producto y la existencia");
						tCodigoProductoTabla.forceFocus();
						
					}
					
					
					
					
				}

				

			}

		});

		tPrecioProductoTabla.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {

				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {
					
					if (!item.getText(10).isEmpty()) {
						
						int precioMinimo = new Integer(item.getText(10));
						int precioLista = new Integer(item.getText(11));
						int precioventa = getMonedaAEntero(tPrecioProductoTabla.getText());
						
						if (precioventa < precioMinimo){
							
							mensajeError(sShell, "El precio de venta debe ser superior al precio minimo");
							e.doit = false;
							
						} else {
							
							 if (!tPrecioProductoTabla.getText().contains("$"))
							    
								 tPrecioProductoTabla.setText(getFormatoMoneda(tPrecioProductoTabla.getText()));
							
							    item.setText(8, String.valueOf(getMonedaAEntero(tPrecioProductoTabla.getText())));
							    item.setText(9,getFormatoMoneda((new Integer(item.getText(7)) * new Integer(item.getText(8)))));
							    tTotal.setText(getFormatoMoneda(calcularTotal()));
							    agregaItemTabla();
							    
						}
							
							
						
					} else {
						
						mensajeError(sShell, "Error al consultar precio minimo");
						tCodigoProductoTabla.forceFocus();
						
					}
					
					
					
					
				}

				

			}

		});

		
		//Se configura la lista de valores para el los productos
		String sentenciaSQL = "Select P.IdProducto Codigo,P.Descripcion,P.Stock,E.Talla,C.Color,M.Marca "
                             +"From Productos P,ExistenciasPorProductos E, Marcas M,COlores c "
                             +"Where P.idProducto = E.idProducto and e.color = c.idColor and E.idMarca = M.idMarca and Descripcion ";
		
		
		Object camposRetorno[][] = new Object[2][2];
		camposRetorno[0][0] =  tCodigoProductoTabla;
		camposRetorno[0][1] =  "0";

		camposRetorno[1][0] =  tDescripcionProductoTabla;
		camposRetorno[1][1] =  "1";


		int[] anchoColumnas = {53,195,43,38,100,100}; 

		listarValorSubproductos.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL, camposRetorno, tExistenciaTabla, anchoColumnas);

		tDescripcionProductoTabla.addKeyListener(listarValorSubproductos);

		//Se agrega el evento a la existencia
		tExistenciaTabla.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {

				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {}

				if (!buscarExistenciaProducto(tCodigoProductoTabla.getText(),tExistenciaTabla.getText(),item)) {

					e.doit = false;

				} else {
					
					item.setText(2, tExistenciaTabla.getText());
				}

			}

		});


		TableEditor editorCodigoProductoTabla = new TableEditor(tbVentasDetalle);
		editorCodigoProductoTabla.grabHorizontal = true;
		editorCodigoProductoTabla.setEditor(tCodigoProductoTabla, item, 0);

		
		TableEditor editorDescripcionProducto = new TableEditor(tbVentasDetalle);
		editorDescripcionProducto.grabHorizontal = true;
		editorDescripcionProducto.setEditor(tDescripcionProductoTabla, item, 1);

		TableEditor editorExistenciaTabla = new TableEditor(tbVentasDetalle);
		editorExistenciaTabla.grabHorizontal = true;
		editorExistenciaTabla.setEditor(tExistenciaTabla, item, 2);

		
		TableEditor editorCantidadTabla = new TableEditor(tbVentasDetalle);
		editorCantidadTabla.grabHorizontal = true;
		editorCantidadTabla.setEditor(tCantidadTabla, item, 7);

		TableEditor editorPrecioTabla = new TableEditor(tbVentasDetalle);
		editorPrecioTabla.grabHorizontal = true;
		editorPrecioTabla.setEditor(tPrecioProductoTabla, item, 8);



	}

	//**************************** Metodo formatearDatosDetalles **********************************************
	private String formatearDatosDetalles() {

		String formatoEnviarBaseDatosDetallesProducto = "";

		String datosFormatedados = "(" ;

		TableItem items[] = tbVentasDetalle.getItems();

		for (TableItem item : items) {

			if (!item.getText(0).isEmpty()) {

					datosFormatedados += item.getText(0) + "," + item.getText(2)+ "," + item.getText(7) + "," + item.getText(8) +"," + getMonedaAEntero(item.getText(9))+")";

				formatoEnviarBaseDatosDetallesProducto =  formatoEnviarBaseDatosDetallesProducto + datosFormatedados;

				datosFormatedados = "(";

			}  

		}

	
		return formatoEnviarBaseDatosDetallesProducto;

	}
	//**************************** Metodo buscarExisteciaProducto ************************
	private boolean buscarExistenciaProducto(String codigoProducto,String codigoExistecia,TableItem item){

		boolean  resultadoBoolean = false;

		try {

			if (consultarExistencia == null)

				consultarExistencia = conectarMySQL.getConexion().prepareStatement("Select E.cantidad,E.Talla,M.Marca,C.Color,e.precioLista,e.precioMinimo " +
				"From ExistenciasPorProductos E, Marcas M,COlores c Where e.idProducto = ? and item = ? and e.color = c.idColor and E.idMarca = M.idMarca ");


			consultarExistencia.setString(1, codigoProducto);
			consultarExistencia.setString(2, codigoExistecia);
			
			ResultSet resultado = consultarExistencia.executeQuery();

			if (resultado.next()) {

				int cantidad = resultado.getInt(1);
				
				if (cantidad > 0) {
					
					item.setText(3,resultado.getString(1));
					item.setText(4,resultado.getString(2));
					item.setText(5,resultado.getString(3));
					item.setText(6,resultado.getString(4));
					item.setText(7,String.valueOf(cantidad));
					item.setText(8,resultado.getString(5));
					
					item.setText(10,resultado.getString(6));
					item.setText(11,resultado.getString(5));
					
					tCantidadTabla.setText(item.getText(7));
					tPrecioProductoTabla.setText(getFormatoMoneda(item.getText(8)));
					resultadoBoolean = true;

				} else {

					mensajeError(sShell, "Esta exitencia se encuentra agotada");
					
				}
				
				

			} else {
				
				mensajeError(sShell, "Código de existencia de producto " + tCodigoProductoTabla.getText() + " no ha sido registrada");
				
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultadoBoolean;



	}
	
	
	//**************************** Metodo calcularTotal **********************************************
	private int calcularTotal() {

		int resultadoInteger = 0;

		TableItem items[] = tbVentasDetalle.getItems();

		int abono = getMonedaAEntero(tAbono.getText());
		
		for (int i = 0; i < items.length; i++)
			
			if (!items[i].getText(9).isEmpty()) 

				resultadoInteger += getMonedaAEntero(items[i].getText(9));
		
		
		
		//Se calcula el saldo del cliente
		tNuevoSaldo.setText(getFormatoMoneda(resultadoInteger + getSaldoCliente() - abono));
		
		return resultadoInteger;

	}

	
	
	//**************************** Metodo buscarInformacionProducto ************************

	private Object getMoneda() {
		// TODO Auto-generated method stub
		return null;
	}



	private String buscarInformacionProducto(String idProducto){

		String  resultadoString = null;


		if (idProducto.isEmpty())

			resultadoString = "";

		else {
			final String sentenciaSQL = "Select Descripcion "+
			"From Productos " + 
			"Where idProducto = '" + idProducto +"'";


			try {

				ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

				if (resultado.next()) {

					resultadoString = resultado.getString(1);

				}    

			}  catch (Exception e) {

				mensajeError(sShell,"No existe ningun registro en la tabla productos");        

			}
		} 

		return resultadoString;

	}

	//************************************  setSaldoCliente ************************************************************************ 
	private void setSaldoCliente(int saldoCliente) {
		this.saldoCliente = saldoCliente;
	}

	
	//************************************  getSaldoCliente ************************************************************************
	private int getSaldoCliente() {
		return saldoCliente;
	}

	//**************************** Eventos de foco ********************************************************

	@Override
	public void focusGained(FocusEvent a) {

		Object fuente = a.getSource();
		
		if (fuente == tNombres && tNumero.getText().isEmpty())
			
			listarValor.mostrarListaValores("");

		// Se agrega este evento para cambiar de color al componente que tiene el evento
		if (fuente.getClass().toString().contains("Text")) {

			Text fuenteTexto = ((Text) fuente);
			fuenteTexto.selectAll();
			fuenteTexto.setBackground(getVisualAtributoGanaFocoComponentes());


		}

	}


	@Override
	public void focusLost(FocusEvent a) {

		Object fuente = a.getSource();
		
       if (fuente == tDescripcionProductoTabla) {
    	   
    	   listarValorSubproductos.ocultarListaValor();
    	   
       }
		

		
		// Se agrega este evento para cambiar de color al componente que tiene el evento
		if (fuente.getClass().toString().contains("Text")) {

			Text fuenteTexto = ((Text) fuente);
			fuenteTexto.setBackground(getVisualAtributoPierdeFocoComponentes());

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

		if (fuente == toolBar.getbLimpiar()) {

		
				limpiar(true);

		}else

			if (fuente == toolBar.getbGuardar())

				guardar();

			else
				if(fuente == toolBar.getbSalir())

					sShell.close();


	}
	
	

	//********************************* Eventos de validacion ***************************************************
	public void keyTraversed(TraverseEvent e) {
		
		if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {
			
			Object fuente = e.getSource();
			
			if (fuente == tNumero  && !tNumero.getText().isEmpty()) {
				
				if (!consultar()) {
					
					int opcion = mensajeConfirmacion(sShell, "Cliente " + tNumero.getText() + " no ha sido registrado \n Desea Registrarlo?");
					
					if (opcion == 32) {
					
					   new GUICliente(conectarMySQL, sShell,cTipo.getSelectionIndex(),tNumero.getText());
				  	   e.doit = false;
				  	   
					} else 
						
						tNumero.setText("");
						
						
					
				} 
				
			} else
				
				if (fuente == tNombres) {
					
					if (tNumero.getText().isEmpty()) {
						
						mensajeError(sShell, "Se debe especificar el cliente a quien se le realizara la venta");
						listarValor.mostrarListaValores("");
							
						
					}else {
						
						tCodigoProductoTabla.forceFocus();
						
					}
					
					
					
				} else
					
					if (fuente == tAbono) {
						
						if (!tAbono.getText().isEmpty() && !tAbono.getText().equals("0")) {
							
							tAbono.setText(getFormatoMoneda(tAbono.getText()));
							int nuevoSaldo = getMonedaAEntero(tNuevoSaldo.getText()) - getMonedaAEntero(tAbono.getText());
							
							if (nuevoSaldo >= 0)
							
							    tNuevoSaldo.setText(getFormatoMoneda(nuevoSaldo));
							else {
								
								mensajeError(sShell, "El valor de abono no debe superar el saldo total");
								e.doit = false;
								
							}
								
								
							
							
						}
						
					}else
						
						if (fuente == tEfectivo) {
							
							if (!tEfectivo.getText().isEmpty() && !tEfectivo.getText().equals("0")) {
								
							
								tCambio.setText(getFormatoMoneda(getMonedaAEntero(tEfectivo.getText()) - getMonedaAEntero(tAbono.getText()) ));
								tEfectivo.setText(getFormatoMoneda(tEfectivo.getText()));
								
							}
							
						}
			
			
	    } 
		
		
	}

	/**********************************************************************************************************
	 *********************************** Interfaz Grafica *****************************************************
	  **********************************************************************************************************/
	
	

	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Venta");
		
		 // Se instancia aqui para que salga al frente la lista de valores
		listarValor = new ListarValor(sShell,105,158,500);
		
		// Se instancia aqui para que salga al frente la lista de valores para los subproductos
		listarValorSubproductos = new ListarValor(sShell,250,240,540);

		createGrupoCompras();
		createGrupoCliente();
		sShell.setLayout(null);
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
		tbVentasDetalle.setBounds(new Rectangle(5, 213, 785, 197));
		createGrupoObservaciones();
		
		tTotal = new Text(sShell, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tTotal.setBounds(new Rectangle(680, 428, 87, 21));
		tTotal.setBackground(getVisualatributodeshabilidatocomponente());
		
		lLinea1 = new Label(sShell, SWT.NONE);
		lLinea1.setBounds(new Rectangle(680, 410, 87, 15));
		lLinea1.setText("_______________________________________________________________________________________________________________________________________________________");
		lTotal = new Label(sShell, SWT.NONE );
		lTotal.setBounds(new Rectangle(643, 431, 31, 15));
		lTotal.setText("Total:");
		lTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tAbono = new Text(sShell, SWT.BORDER | SWT.RIGHT);
		tAbono.setBounds(new Rectangle(680, 455, 87, 21));
		lAbono = new Label(sShell, SWT.NONE);
		lAbono.setBounds(new Rectangle(634, 458, 40, 15));
		lAbono.setText("Abono:");
		lAbono.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tEfectivo = new Text(sShell, SWT.BORDER | SWT.RIGHT);
		tEfectivo.setBounds(new Rectangle(680, 483, 87, 21));
		tEfectivo.setText("0");
		lEfectivo = new Label(sShell, SWT.NONE);
		lEfectivo.setBounds(new Rectangle(622, 486, 52, 15));
		lEfectivo.setText("Efectivo:");
		lEfectivo.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tCambio = new Text(sShell, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tCambio.setBounds(new Rectangle(680, 509, 87, 21));
		tCambio.setBackground(getVisualatributodeshabilidatocomponente());
		
		lCambio = new Label(sShell, SWT.NONE);
		lCambio.setBounds(new Rectangle(625, 512, 49, 15));
		lCambio.setText("Cambio:");
		lCambio.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tNuevoSaldo = new Text(sShell, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tNuevoSaldo.setBounds(new Rectangle(680, 535, 87, 21));
		tNuevoSaldo.setBackground(getVisualatributodeshabilidatocomponente());
		
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
		createCTipo();
		lNumero = new Label(grupoCliente, SWT.NONE);
		lNumero.setBounds(new Rectangle(206, 24, 61, 15));
		lNumero.setText("Número:");
		tNumero = new Text(grupoCliente, SWT.BORDER);
		tNumero.setBounds(new Rectangle(207, 41, 236, 23));
		tNombres = new Text(grupoCliente, SWT.BORDER);
		tNombres.setBounds(new Rectangle(85, 77, 359, 23));
	
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
		cTipo.setBounds(new Rectangle(20, 41, 141, 23));
		cTipo.add("Cedula de Ciudadania");
		cTipo.add("Tarjeta de Identidad");
		cTipo.add("Pasaporte");
		cTipo.add("Cedula de Extranjeria");

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
		lNumeroVenta = new Label(grupoCompras, SWT.NONE );
		lNumeroVenta.setBounds(new Rectangle(80, 26, 98, 15));
		lNumeroVenta.setText("Número Venta:");
		tNumeroVenta = new Text(grupoCompras, SWT.BORDER | SWT.READ_ONLY);
		tNumeroVenta.setBounds(new Rectangle(186, 22, 53, 23));
		lFecha = new Label(grupoCompras, SWT.CENTER);
		lFecha.setBounds(new Rectangle(76, 71, 41, 15));
		lFecha.setText("Fecha:");
		tFecha = new Text(grupoCompras, SWT.BORDER | SWT.CENTER | SWT.READ_ONLY);
		tFecha.setBounds(new Rectangle(123, 67, 114, 23));
		tFecha.setBackground(getVisualatributodeshabilidatocomponente());
		
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
