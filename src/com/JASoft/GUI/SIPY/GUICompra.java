package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ListarValor;
import com.JASoft.componentesGraficos.ToolBar;


public class GUICompra extends ConfigurarText implements FocusListener,SelectionListener,TraverseListener{

	private Shell sShell = null;
	private ToolBar toolBar;
	private Group lNit = null;
	private Label lTipo = null;
	private Label lRazonSocial = null;
	private Text tRazonSocial = null;
	private Text tNumeroNit = null;
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
	private ArrayList<Text> arrayTextRequeridos= null;  //  @jve:decl-index=0:
	private String formatoEnviarBaseDatosSubProducto = "";  //  @jve:decl-index=0:
	private Shell shellPadre;
	private Label lTotalDolares;
	private Text tTotalDolares;
	private TableEditor editorBoton;
	private ListarValor listarValorSubproductos;




	/*
	 * Constructor general
	 */
	public GUICompra(ConectarMySQL conectarMySQL,Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		centrarShell(sShell);

		tNumeroNit.forceFocus(); //Se asigna el foco al primer componente navegable

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

		arrayTextRequeridos.add(tNumeroNit);
		arrayTextRequeridos.add(tNumeroFactura);
		arrayTextRequeridos.add(tFecha);
		arrayTextRequeridos.add(tTotal);


	}



	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	private void limpiar(boolean comodin) {

		tRazonSocial.setText("");
		tNumeroCompra.setText("");
		tNumeroFactura.setText("");
		tTotal.setText("");
		tTotalDolares.setText("");
		taObservaciones.setText("");
		//tCambioPesos.setText("");
		tFecha.setText(getObtenerFecha(conectarMySQL));
		cMoneda.select(0);
		formatoEnviarBaseDatosSubProducto = "";
		
	
		if (tbDetallesCompras.getColumnCount() == 8) {

			tbDetallesCompras.getColumn(5).dispose();

			tbDetallesCompras.getColumns()[0].setWidth(37);
			tbDetallesCompras.getColumns()[1].setWidth(115);
			tbDetallesCompras.getColumns()[2].setWidth(340);
			tbDetallesCompras.getColumns()[3] .setWidth(60);
			tbDetallesCompras.getColumns()[4].setWidth(58);
			tbDetallesCompras.getColumns()[5].setWidth(73);
			tbDetallesCompras.getColumns()[6].setWidth(71);

			lTotal.setBounds(619, 521, 34, 15);
			lTotal.setText("Total:");
			tTotal.setBounds(666, 517, 109, 23);
			lLinea1.setBounds(666, 492, 109, 15);
			lTotalDolares.setVisible(false);
			tTotalDolares.setVisible(false);

		}
		//Se borran los TableEditor de la tabla
		Control controles[] = tbDetallesCompras.getChildren();

		for (Control control : controles )

			control.dispose();


		//Se borran los items de la Tabla
		tbDetallesCompras.removeAll();

		if (comodin) {

			tNumeroNit.setText("");
			tNumeroNit.forceFocus();

		}

		agregaItemTablaPadre();



	}

	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void guardar(){

		if (validarRegistro(arrayTextRequeridos, sShell)){

			try {

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoCompras(conectarMySQL.getConexion());


				// Para insertar la imagen se extraeran los byte del archivo

				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, tNumeroNit.getText());
				llamarProcedimientoAlmacenado.setString(3, tNumeroFactura.getText());
				llamarProcedimientoAlmacenado.setInt(4,new Float(tTotal.getText()).intValue());
				llamarProcedimientoAlmacenado.setFloat(5,tTotalDolares.isVisible() ? (new Float(tTotalDolares.getText())) : 0);
				llamarProcedimientoAlmacenado.setString(6,cMoneda.getSelectionIndex() == 0 ? "P" : "D");
				llamarProcedimientoAlmacenado.setString(7, tCambioPesos.getText());
				llamarProcedimientoAlmacenado.setString(8, taObservaciones.getText());
				llamarProcedimientoAlmacenado.setString(9, formatearDatosDetalles());

				llamarProcedimientoAlmacenado.setString( 10, formatoEnviarBaseDatosSubProducto );

				llamarProcedimientoAlmacenado.setString( 11, null );
				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Compra ha sido registrada");
				limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell, "Error al guardar el registro de compras " +e);
			}
		}
	}	

	/*
	 * Metodo agregarEventos: agregar eventos a los componentes de la GUI
	 */
	private void agregarEventos(){


		//Se agregar eventos de focos a tNumeroNit para que consulte el cliente
		tNumeroNit.addFocusListener(this);
		tNumeroNit.addVerifyListener(getValidarEntradaNumeros());
		tNumeroFactura.addVerifyListener(getValidarEntradaNumeros());
		tCambioPesos.addVerifyListener(getValidarEntradaNumeros());
		taObservaciones.addVerifyListener(getConvertirMayusculaLetras());

		//Se valida el numero del nit
		tNumeroNit.addTraverseListener(this);
		tCambioPesos.addTraverseListener(this);


		//Se adicionan eventos de foco para seleccionar aquelos campos con datos
		tRazonSocial.addFocusListener(this);
		tNumeroCompra.addFocusListener(this);
		tNumeroFactura.addFocusListener(this);
		tTotal.addFocusListener(this);
		taObservaciones.addFocusListener(this);
		tCambioPesos.addFocusListener(this);

		//Se agrega un en evento a la moneda
		cMoneda.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				if (cMoneda.getSelectionIndex() == 0) {

					tCambioPesos.setEditable(false);
					tCambioPesos.setEnabled(false);
					tCambioPesos.setText("1890");

					if (tbDetallesCompras.getColumnCount() == 8) {

						tbDetallesCompras.getColumn(5).dispose();

						tbDetallesCompras.getColumns()[0].setWidth(37);
						tbDetallesCompras.getColumns()[1].setWidth(115);
						tbDetallesCompras.getColumns()[2].setWidth(340);
						tbDetallesCompras.getColumns()[3] .setWidth(60);
						tbDetallesCompras.getColumns()[4].setWidth(58);
						tbDetallesCompras.getColumns()[5].setWidth(73);
						tbDetallesCompras.getColumns()[6].setWidth(71);

						lTotal.setBounds(619, 521, 34, 15);
						lTotal.setText("Total:");
						tTotal.setBounds(666, 517, 109, 23);
						lLinea1.setBounds(666, 492, 109, 15);
						lTotalDolares.setVisible(false);
						tTotalDolares.setVisible(false);

					}

				} else {



					tCambioPesos.setEditable(true);
					tCambioPesos.setEnabled(true);
					tCambioPesos.setText("0");

					if (tbDetallesCompras.getColumnCount() == 7) {
						TableColumn clCostoDolares = new TableColumn(tbDetallesCompras, SWT.CENTER,5);
						clCostoDolares.setText("Costo D.");

						tbDetallesCompras.getColumns()[0].setWidth(37);
						tbDetallesCompras.getColumns()[1].setWidth(93);
						tbDetallesCompras.getColumns()[2].setWidth(298);
						tbDetallesCompras.getColumns()[3] .setWidth(60);
						tbDetallesCompras.getColumns()[4].setWidth(60);
						tbDetallesCompras.getColumns()[5].setWidth(63);
						tbDetallesCompras.getColumns()[6].setWidth(73);
						tbDetallesCompras.getColumns()[7].setWidth(71);

						editorBoton.setColumn(7);

						lTotalDolares.setVisible(true);
						tTotalDolares.setVisible(true);
						lTotal.setBounds(592, 510, 69, 15);
						lTotal.setText("Total Pesos:");
						tTotal.setBounds(666, 505, 109, 23);
						lLinea1.setBounds(667, 486, 109, 15);


					}

				}



			}
		});



		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);


	}

	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	private void agregarConfiguraciones(){

		//Se configura el limita de datos por Text
		tNumeroNit.setTextLimit(10);
		tNumeroFactura.setTextLimit(20);
		tCambioPesos.setTextLimit(4);
		tFecha.setText(getObtenerFecha(conectarMySQL));

		cMoneda.add("Pesos");
		cMoneda.add("Dolares");
		tCambioPesos.setEnabled(false);
		tCambioPesos.setEditable(true);
		cMoneda.select(0);

		//Se configuran las columnas
		TableColumn clItem = new TableColumn(tbDetallesCompras, SWT.CENTER);
		TableColumn clCodigoProducto = new TableColumn(tbDetallesCompras, SWT.CENTER);
		TableColumn clNombreProducto = new TableColumn(tbDetallesCompras, SWT.CENTER);
		TableColumn clCantidad  = new TableColumn(tbDetallesCompras, SWT.CENTER);
		TableColumn clCosto = new TableColumn(tbDetallesCompras, SWT.CENTER);
		TableColumn clSubtotal = new TableColumn(tbDetallesCompras, SWT.CENTER);
		TableColumn clBotonAgregarDetalles = new TableColumn(tbDetallesCompras, SWT.CENTER);

		//Se configuran los nombres de columnas
		clItem.setText("Item");
		clCodigoProducto.setText("Cod. Producto");
		clNombreProducto.setText("Descripción");
		clCosto.setText("Costo");
		clCantidad .setText("Cantidad");
		clSubtotal.setText("Subtotal");
		clBotonAgregarDetalles.setText("Existencias");

		//Se configuran los tamaños  de columnas
		clItem.setWidth(37);
		clCodigoProducto.setWidth(115);
		clNombreProducto.setWidth(340);
		clCantidad .setWidth(60);
		clCosto.setWidth(58);
		clSubtotal.setWidth(73);
		clBotonAgregarDetalles.setWidth(71);

		agregarEditorTablaPadre(new TableItem(tbDetallesCompras,SWT.NONE));

		sShell.setImage(new Image(Display.getCurrent(), "imagenes/ComprasRegistrar.gif"));

	}

	//** Agrega un Item a la tabla

	private void agregaItemTablaPadre() {

		TableItem item = new TableItem(tbDetallesCompras, SWT.NONE);
		agregarEditorTablaPadre(item);

	}

	//******************************************* agregarEditorTabla ***********************************************************
	private void agregarEditorTablaPadre(final TableItem item) {


		//Se definen los Text que se van agregan a la Tabla
		final Text tItem = new Text(tbDetallesCompras, SWT.CENTER | SWT.NONE);
		tItem.setBackground(getVisualatributodeshabilidatocomponente());
		tItem.setEditable(false);
		tItem.setEnabled(false);

		final Text tCodigoProducto = new Text(tbDetallesCompras, SWT.NONE );

		final Text  tDescripcionProducto = new Text(tbDetallesCompras, SWT.NONE);
		tDescripcionProducto.addFocusListener(this);

		final Text  tCantidad = new Text(tbDetallesCompras, SWT.RIGHT) ;
		tCantidad.setBackground(getVisualatributodeshabilidatocomponente());
		tCantidad.setEditable(false);
		tCantidad.setEnabled(false);

		final Text  tCosto = new Text(tbDetallesCompras, SWT.RIGHT);
		tCosto.setEditable(false);
		tCosto.setEnabled(false);
		tCosto.setBackground(getVisualatributodeshabilidatocomponente());
		

		if (cMoneda.getSelectionIndex() == 1) { // Moneda en dolares

			Text  tCostoDolares = new Text(tbDetallesCompras, SWT.RIGHT);
			tCostoDolares.setBackground(getVisualatributodeshabilidatocomponente());
			tCostoDolares.setEditable(false);
			tCostoDolares.setEnabled(false);

		}



		Button bDetalles = new Button(tbDetallesCompras, SWT.PUSH);


		//Se definen los eventos para el Text tCodigoProducto
		tCodigoProducto.addVerifyListener(getValidarEntradaNumeros());

		tCodigoProducto.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {

				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {}

				String producto = buscarInformacionProducto(tCodigoProducto.getText());

				if (producto == null) {

					mensajeError(sShell, "Código de producto " + tCodigoProducto.getText() + " no ha sido registrado");
					tCodigoProducto.setText("");
					e.doit = false;

				} else {

					tDescripcionProducto.setText(producto);
					item.setText(1, tCodigoProducto.getText());

				}

			}

		});

		//Se agrega para colcar el item
		tCodigoProducto.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {

				tCodigoProducto.setBackground(getVisualAtributoPierdeFocoComponentes());



			}

			@Override
			public void focusGained(FocusEvent arg0) {

				tItem.setText(String.valueOf(tbDetallesCompras.getItemCount()));
				item.setText(0,String.valueOf(tbDetallesCompras.getItemCount()));
				tCodigoProducto.setBackground(getVisualAtributoGanaFocoComponentes());

			}
		});

		//Se definen los eventos para el Text tDescripcionProducto
		tDescripcionProducto.addVerifyListener(getConvertirMayusculaLetras());
		tDescripcionProducto.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {

				tDescripcionProducto.setBackground(getVisualAtributoPierdeFocoComponentes());
				listarValorSubproductos.ocultarListaValor();



			}

			@Override
			public void focusGained(FocusEvent arg0) {


				tDescripcionProducto.setBackground(getVisualAtributoGanaFocoComponentes());

			}
		});

		//Se configura la lista de valores para el los productos
		String sentenciaSQL = "Select IdProducto Codigo,Descripcion,Stock From Productos Where Descripcion ";

		Object camposRetorno[][] = new Object[2][2];
		camposRetorno[0][0] =  tCodigoProducto;
		camposRetorno[0][1] =  "0";

		camposRetorno[1][0] =  tDescripcionProducto;
		camposRetorno[1][1] =  "1";


		int[] anchoColumnas = {70,290,48}; 

		listarValorSubproductos.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL, camposRetorno, bDetalles, anchoColumnas);

		tDescripcionProducto.addKeyListener(listarValorSubproductos);


		//Se egraga el evento al boton
		bDetalles.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (cMoneda.getSelectionIndex() == 0 || (cMoneda.getSelectionIndex() == 1 && !tCambioPesos.getText().equals("0") )) {

					if (!item.getText(1).isEmpty()) {

						GUIDialogoComprasEncabezado guiDialogoComprasEncabezado = new GUIDialogoComprasEncabezado(sShell,item,tCantidad,tCosto);

					}else {

						mensajeError(sShell, "Se debe especificar el producto a comprar");
						tCodigoProducto.forceFocus();
					}

				} else {


					mensajeError(sShell, "El cambio de dolares a pesos no puede ser igual 0");
					tCambioPesos.forceFocus();

				}


			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});


		TableEditor editorItem = new TableEditor(tbDetallesCompras);
		editorItem.grabHorizontal = true;
		editorItem.setEditor(tItem, item, 0);

		TableEditor editorProducto = new TableEditor(tbDetallesCompras);
		editorProducto.grabHorizontal = true;
		editorProducto.setEditor(tCodigoProducto, item, 1);


		TableEditor editorDescripcionProducto = new TableEditor(tbDetallesCompras);
		editorDescripcionProducto.grabHorizontal = true;
		editorDescripcionProducto.setEditor(tDescripcionProducto, item, 2);

		TableEditor editorCantidad = new TableEditor(tbDetallesCompras);
		editorCantidad.grabHorizontal = true;
		editorCantidad.setEditor(tCantidad, item, 3);

		TableEditor editorCosto = new TableEditor(tbDetallesCompras);
		editorCosto.grabHorizontal = true;
		editorCosto.setEditor(tCosto, item, 4);



		editorBoton = new TableEditor (tbDetallesCompras);
		editorBoton.minimumWidth = 23;
		editorBoton.horizontalAlignment = SWT.CENTER;
		editorBoton.setEditor(bDetalles, item, cMoneda.getSelectionIndex() == 0 ? 6 : 7);



	}



	//**************************** Metodo buscarInformacionProducto ************************

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

	//**************************** Metodo buscarInformacionProveedores ************************

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

	//**************************** Metodo formatearDatosSubProductos **********************************************
	private void formatearDatosSubProductos(Table tbExitenciasProductos,TableItem itemPadre) {

		String datosFormatedados = "(" + itemPadre.getText(1) +",";

		TableItem items[] = tbExitenciasProductos.getItems();

		for (TableItem item : items) {

			if (!item.getText(0).isEmpty()) {

				if (cMoneda.getSelectionIndex() == 0) {

					if (item.getText(11).isEmpty()) 

						item.setText(11, "-1");



					datosFormatedados += item.getText(0) + "," + item.getText(11)+ "," + item.getText(1)+ "," + item.getText(2) + "," + item.getText(4) +  "," + item.getText(6)+")";

				} else {


					if (item.getText(13).isEmpty()) 

						item.setText(13, "-1");

					if (item.getText(14).isEmpty()) 

						item.setText(14, "-1");


					datosFormatedados += item.getText(0) + "," + item.getText(13)+ "," + item.getText(1) + "," + item.getText(14) + ","   + item.getText(2) +
					                    "," + item.getText(3) + "," + item.getText(6) +  "," + item.getText(8)+ "," + item.getText(5)+  ")";

				}	

				formatoEnviarBaseDatosSubProducto =  formatoEnviarBaseDatosSubProducto + datosFormatedados;

				datosFormatedados = "(" + itemPadre.getText(1) +",";

			}  

		

		}
		
		System.out.println(formatoEnviarBaseDatosSubProducto);

	}

	//**************************** Metodo formatearDatosDetalles **********************************************
	private String formatearDatosDetalles() {

		String formatoEnviarBaseDatosDetallesProducto = "";

		String datosFormatedados = "(" ;

		TableItem items[] = tbDetallesCompras.getItems();

		for (TableItem item : items) {

			if (!item.getText(1).isEmpty()) {

				if (cMoneda.getSelectionIndex() == 0)

					datosFormatedados += item.getText(1) + "," + item.getText(0)+ "," + item.getText(3) + "," + item.getText(4) +  ",0," + item.getText(5)+")";

				else

					datosFormatedados += item.getText(1) + "," + item.getText(0)+ "," + item.getText(3) + "," + item.getText(4) +  "," + item.getText(5)+ "," + item.getText(6) + ")";

				formatoEnviarBaseDatosDetallesProducto =  formatoEnviarBaseDatosDetallesProducto + datosFormatedados;

				datosFormatedados = "(";

			}  

		}

		return formatoEnviarBaseDatosDetallesProducto;

	}

	//**************************** Metodo calcularTotalPadre **********************************************
	private float calcularTotalCostoPadre() {

		float resultadoFloat = 0;

		TableItem items[] = tbDetallesCompras.getItems();

		int columnaPivote = cMoneda.getSelectionIndex() == 0 ? 4 : 5;

		for (int i = 0; i < items.length; i++)
			
			if (!items[i].getText(columnaPivote).isEmpty())

			   resultadoFloat += new Float(items[i].getText(columnaPivote)); 

		return resultadoFloat;

	}

	

	//**************************** Eventos de foco ********************************************************

	@Override
	public void focusGained(FocusEvent a) {

		Object fuente = a.getSource();



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

	// Eventos transversal
	@Override
	public void keyTraversed(TraverseEvent e) {

		Object fuente = e.getSource();

		if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

			if (fuente == tNumeroNit) {

				if (!tNumeroNit.getText().isEmpty()) {

					if (!buscarInformacionProveedores()) {

						mensajeError(sShell, "Nit no ha sido registrado");
						e.doit = false;

					}
				}

			} else

				if (fuente == tCambioPesos) {


					if (!tCambioPesos.getText().isEmpty()) {

						if (tCambioPesos.getText().equals("0")) {
							mensajeError(sShell, "El cambio de dolares a pesos no puede ser cero");
							e.doit = false;

						}	

					} else {

						mensajeError(sShell, "Digite el cambio de dolares a pesos");
						e.doit = false;
					}



				}

		}
	}    

	// *********************************** Interfaz Grafica *****************************************************
	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Compras");
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));

		// Se instancia aqui para que salga al frente la lista de valores para los subproductos
		listarValorSubproductos = new ListarValor(sShell,340,280,420);


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
		tbDetallesCompras = new Table(sShell, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		tbDetallesCompras.setHeaderVisible(true);
		tbDetallesCompras.setLinesVisible(true);
		tbDetallesCompras.setBounds(new Rectangle(11, 251, 760, 232));
		createGrupoObservaciones();
		lLinea1 = new Label(sShell, SWT.NONE);
		lLinea1.setBounds(new Rectangle(666, 492, 109, 15));
		lLinea1.setText("_______________________________________________________________________________________________________________________________________________________");
		lTotal = new Label(sShell, SWT.NONE);
		lTotal.setBounds(new Rectangle(619, 521, 34, 15));
		lTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		lTotal.setText("Total:");
		tTotal = new Text(sShell, SWT.BORDER | SWT.RIGHT);
		tTotal.setBackground(getVisualatributodeshabilidatocomponente());
		tTotal.setBounds(new Rectangle(666, 517, 109, 23));
		tTotal.setEditable(false);
		tTotal.setEnabled(false);
		tTotal.setToolTipText("Total de la factura");
		lTotalDolares = new Label(sShell, SWT.NONE);
		lTotalDolares.setBounds(new Rectangle(584, 538, 77, 15));
		lTotalDolares.setText("Total Dolares:");
		lTotalDolares.setVisible(false);
		lTotalDolares.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		tTotalDolares = new Text(sShell, SWT.BORDER | SWT.RIGHT);
		tTotalDolares.setBounds(new Rectangle(666, 534, 109, 23));
		tTotalDolares.setBackground(getVisualatributodeshabilidatocomponente());
		tTotalDolares.setToolTipText("Total de la factura");
		tTotalDolares.setEditable(false);
		tTotalDolares.setEnabled(false);
		tTotalDolares.setVisible(false);
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
		tRazonSocial.setBounds(new Rectangle(120, 73, 273, 23));
		tRazonSocial.setEnabled(true);
		tRazonSocial.setToolTipText("Razón social");
		tRazonSocial.setEditable(true);
		tNumeroNit = new Text(lNit, SWT.BORDER);
		tNumeroNit.setBounds(new Rectangle(10, 73, 99, 23));
		tNumeroNit.setToolTipText("Digite el Nit de proveedor");
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
		tFecha = new Text(grupoCompras, SWT.BORDER | SWT.CENTER);
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
		cMoneda = new Combo(grupoCompras, SWT.READ_ONLY);
		cMoneda.setToolTipText("Moneda en que hizo las compras");
		cMoneda.setBounds(new Rectangle(69, 119, 85, 23));
	}


	/********************************************************************************************************
	 *************************************** Clase Interna **************************************************
	 ********************************************************************************************************
	 */

	class GUIDialogoComprasEncabezado extends Dialog implements SelectionListener,FocusListener {

		private Group grupoExitenciasPorProducto = null;
		private Label lCantidad = null;
		private Text tCantidadTotal = null;
		private Table tbExistenciasPorProductos = null;
		private Button bAceptar = null;
		private Button bCancelar = null;
		private Text tTotalHija = null;
		private Label lTotal = null;
		private Shell shellDialogo = null;
		private Text tCantidadPadre;
		private Text tCostoPadre;
		private TableItem itemPadre;
		private ListarValor listarValor;
		private ListarValor listarValorMarcas;
		private PreparedStatement consultarColor;



		/*
		 * Constructor
		 */


		public GUIDialogoComprasEncabezado(Shell parent, TableItem itemPadre, Text tCantidadPadre, Text tCostoPadre) {

			super(parent);
			this.tCantidadPadre = tCantidadPadre;
			this.tCostoPadre = tCostoPadre;
			this.itemPadre = itemPadre;

			inicializar(cMoneda.getText());
		}


		/*
		 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
		 */
		private void agregarEventos(){

			//Se agregar eventos a los botones
			bAceptar.addSelectionListener(this);
			bCancelar.addSelectionListener(this);

		}


		/*
		 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
		 */
		private void agregarConfiguracionesDolares(){

			//Se configuran las columnas
			TableColumn clTalla  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clColor  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clMarca  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCantidad  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCostoDolares = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCostoPesos = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);

			TableColumn clPrecioMinimo = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPorcentajePrecioMinimo = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPrecioLista = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPorcentajePrecioLista = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clSubtotal = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clBotonAgregarFotos = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clBotonEliminarFila = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCodigoColor = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCodigoMarca = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);

			//Se configura a la tabla un Item y u editor
			TableItem item = new TableItem(tbExistenciasPorProductos,SWT.NONE); // Se renderiza para agregar un boton

			agregarEditorTablaDolares(item);


			clTalla.setText("Talla");
			clColor.setText("Color");
			clMarca.setText("Marca");
			clCostoDolares.setText("Dolares");
			clCostoPesos.setText("Pesos");
			clPrecioMinimo.setText("P.Minimo");
			clPorcentajePrecioMinimo.setText("%");
			clPrecioLista.setText("P.Lista");
			clPorcentajePrecioLista.setText("%");
			clCantidad .setText("Cantidad");
			clSubtotal.setText("Subtotal");
			clBotonAgregarFotos.setText("(+)Foto");
			clBotonEliminarFila.setText("(-)Fila");


			clTalla.setWidth(38);
			clColor .setWidth(126);
			clMarca.setWidth(100);
			clCantidad .setWidth(61);
			clCostoDolares.setWidth(55);
			clCostoPesos.setWidth(55);
			clPrecioMinimo.setWidth(85);
			clPorcentajePrecioMinimo.setWidth(28);
			clPrecioLista.setWidth(85);
			clPorcentajePrecioLista.setWidth(27);
			clSubtotal.setWidth(72);
			//clBotonAgregarFotos.setWidth(52);
			//clBotonEliminarFila.setWidth(50);


		}


		/*
		 * Metodo agregarConfiguracionesPesos: Agrega configuraciones adicionales a los componentes cuando la moneda es Pesos
		 */
		private void agregarConfiguracionesPesos(){

			//Se configuran las columnas
			TableColumn clTalla  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clColor  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCantidad  = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCosto = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPrecioMinimo = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPorcentajePrecioMinimo = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPrecioLista = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clPorcentajePrecioLista = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clSubtotal = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clBotonAgregarFotos = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clBotonEliminarFila = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);
			TableColumn clCodigoColor = new TableColumn(tbExistenciasPorProductos, SWT.CENTER);


			//Se configura a la tabla un Item y u editor
			TableItem item = new TableItem(tbExistenciasPorProductos,SWT.NONE); // Se renderiza para agregar un boton

			agregarEditorTablaPesos(item);


			clTalla.setText("Talla");
			clColor.setText("Color");
			clCosto.setText("Costo");
			clPrecioMinimo.setText("P.Minimo");
			clPorcentajePrecioMinimo.setText("%");
			clPrecioLista.setText("P.Lista");
			clPorcentajePrecioLista.setText("%");
			clCantidad .setText("Cantidad");
			clSubtotal.setText("Subtotal");
			clBotonAgregarFotos.setText("(+)Foto");
			clBotonEliminarFila.setText("(-)Fila");


			clTalla.setWidth(38);
			clColor .setWidth(149);
			clCantidad .setWidth(61);
			clCosto.setWidth(85);
			clPrecioMinimo.setWidth(85);
			clPorcentajePrecioMinimo.setWidth(28);
			clPrecioLista.setWidth(85);
			clPorcentajePrecioLista.setWidth(27);
			clSubtotal.setWidth(72);
			clBotonAgregarFotos.setWidth(52);
			clBotonEliminarFila.setWidth(50);


		}


		//** Agrega un Item a la tabla

		private void agregaItemTabla() {

			TableItem item = new TableItem(tbExistenciasPorProductos, SWT.NONE);

			if (cMoneda.getText().equals("Pesos"))

				agregarEditorTablaPesos(item);

			else

				agregarEditorTablaDolares(item);


		}

		//******************************************* agregarEditorTablaPesos ***********************************************************
		private void agregarEditorTablaPesos(final TableItem item) {


			//Se definen los Text que se van agregan a la Tabla
			final Text tTalla = new Text(tbExistenciasPorProductos, SWT.CENTER);
			tTalla.setTextLimit(2);
			tTalla.addFocusListener(this);

			final Text  tColor = new Text(tbExistenciasPorProductos, SWT.NONE);
			tColor.addFocusListener(this);

			final Text  tCantidad = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tCantidad.setTextLimit(4);
			tCantidad.addFocusListener(this);


			final Text  tCosto = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tCosto.addFocusListener(this);

			final Text  tPrecioMinimo = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tPrecioMinimo.addFocusListener(this);

			final Text  tPorcentajeGananciaPrecioMinimo = new Text(tbExistenciasPorProductos, SWT.CENTER);
			tPorcentajeGananciaPrecioMinimo.setBackground(getVisualatributodeshabilidatocomponente());
			tPorcentajeGananciaPrecioMinimo.setTextLimit(3);
			tPorcentajeGananciaPrecioMinimo.addFocusListener(this);
			tPorcentajeGananciaPrecioMinimo.setEnabled(false);

			final Text  tPrecioLista = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tPrecioLista.addFocusListener(this);

			final Text  tPorcentajeGananciaPrecioLista = new Text(tbExistenciasPorProductos, SWT.CENTER);
			tPorcentajeGananciaPrecioLista.setBackground(getVisualatributodeshabilidatocomponente());
			tPorcentajeGananciaPrecioLista.setTextLimit(3);
			tPorcentajeGananciaPrecioLista.addFocusListener(this);
			tPorcentajeGananciaPrecioLista.setEnabled(false);

			final Text  tSubtotal = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tSubtotal.setBackground(getVisualatributodeshabilidatocomponente());
			tSubtotal.setEditable(false);
			tSubtotal.setEnabled(false);


			Button bAgregarFoto = new Button(tbExistenciasPorProductos, SWT.PUSH);

			Button bEliminarFila = new Button(tbExistenciasPorProductos, SWT.PUSH);

			//Se agrega este Text como comodin para agregar el codigo del color en la tabla
			final Text tCodigoColor = new Text(tbExistenciasPorProductos, SWT.RIGHT);


			//Se agregan los eventos
			tTalla.addVerifyListener(ConfigurarText.getConvertirMayusculaLetras());
			tColor.addVerifyListener(ConfigurarText.getConvertirMayusculaLetras());
			tCantidad.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tCosto.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPrecioMinimo.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPorcentajeGananciaPrecioMinimo.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPrecioLista.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPorcentajeGananciaPrecioLista.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());

			//Se agregan eventos de foco para validar los datos Talla no nula
			tTalla.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT
							|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {

						if (tTalla.getText().isEmpty()) {

							ConfigurarText.mensajeError(shellDialogo, "Digite la talla del producto");
							e.doit = false;

						} else {

							item.setText(0, tTalla.getText());

						}

					}
				}
			});

			//Se agregan eventos de foco para validar los datos COlor no nulo
			tColor.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT
							|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {

						if (tColor.getText().isEmpty()) {

							ConfigurarText.mensajeError(shellDialogo, "Digite el color del producto");
							e.doit = false;

						} else

							if (esMismaTallayColoryMarca(tTalla.getText(), tColor.getText(),"")) {

								ConfigurarText.mensajeError(shellDialogo, "La talla y el color del producto ya ha sido digitado ");
								e.doit = false; 

							} else {  

								if (item.getText(11).isEmpty()) { //Se busca si el color que se agrego se encuentra en la BD

									String idColor = existeColor(tColor.getText());

									if ( idColor == null) {

										int opcion = mensajeConfirmacion(sShell, "Este color no se encuentra registrado en la tabla colores.\n Desea registrarlo?");

										if (opcion == 32) {

											item.setText(11, "-1");
											item.setText(1, tColor.getText());
											listarValor.ocultarListaValor();

										} else

											e.doit = false;

									} else {

										item.setText(11, idColor);
										item.setText(1, tColor.getText());
										listarValor.ocultarListaValor();

									}


								} else {


									item.setText(1, tColor.getText());
									listarValor.ocultarListaValor();

								}

							}	


					}
				}
			});

			//Se configura la lista de valores para el color
			String sentenciaSQL = "Select idColor Codigo,color Color From Colores Where Color";

			Object camposRetorno[][] = new Object[2][2];
			camposRetorno[0][0] =  tColor;
			camposRetorno[0][1] =  "1";

			camposRetorno[1][0] =  tCodigoColor;
			camposRetorno[1][1] =  "0";


			int[] anchoColumnas = {80,293}; 

			listarValor.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL, camposRetorno, tCantidad, anchoColumnas);

			tColor.addKeyListener(listarValor);

			//Se agrega este elemento como comodin para agregar el codigo del color en la tabla
			tCodigoColor.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent arg0) {

					item.setText(11,tCodigoColor.getText());

				}
			});


			//Se agregan eventos de foco para validar los datos Cantidad no nula y mayor a 0
			tCantidad.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tCantidad.getText().isEmpty()) {

							if  (Integer.parseInt(tCantidad.getText()) <= 0) { 

								ConfigurarText.mensajeError(shellDialogo, "La cantidad debe ser mayor a 0");
								e.doit = false;

							}	else

								item.setText(2, tCantidad.getText());

						} else {

							ConfigurarText.mensajeError(shellDialogo, "Digite la cantidad del producto comprado");
							e.doit = false;

						}
					}
				}
			});

			//Se agregan eventos de foco para validar los datos costo mayor a 0
			tCosto.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tCosto.getText().isEmpty()) {

							if  (Integer.parseInt(tCosto.getText()) <= 0) { 

								ConfigurarText.mensajeError(shellDialogo, "El costo del producto debe ser mayor a 0");
								e.doit = false;

							}	else

								item.setText(3, tCosto.getText());

						} else {

							ConfigurarText.mensajeError(shellDialogo, "Digite el costo del producto comprado");
							e.doit = false;

						}
					}
				}
			});



			//Se agregan eventos de foco para validar los datos: Precio minimo mayor a costo
			tPrecioMinimo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPrecioMinimo.getText().isEmpty()) {

							float precioMinimo = Float.parseFloat(tPrecioMinimo.getText());
							float precioCosto = Float.parseFloat(tCosto.getText());

							if ( precioMinimo <= precioCosto) {

								ConfigurarText.mensajeError(shellDialogo, "El precio minimo debe venta debse ser mayor al costo del producto");
								e.doit = false;

							} else {

								float porcentajePrecioMinimo = ((precioMinimo - precioCosto)/precioCosto) * 100;
								tPorcentajeGananciaPrecioMinimo.setText(String.valueOf(redondearNumero(porcentajePrecioMinimo, 0)));
								item.setText(4, tPrecioMinimo.getText());


							}

						}
					}
				}
			});


			//Se agregan eventos de foco para validar los datos porcentaje  mayor a 0
			tPorcentajeGananciaPrecioMinimo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPorcentajeGananciaPrecioMinimo.getText().isEmpty()) {

							float precioMinimo = Float.parseFloat(tCosto.getText())* (1 + Float.parseFloat(tPorcentajeGananciaPrecioMinimo.getText()) / 100);
							tPrecioMinimo.setText(String.valueOf(precioMinimo));
							item.setText(4, tPrecioMinimo.getText());
							item.setText(5, tPorcentajeGananciaPrecioMinimo.getText());

						} else {

							ConfigurarText.mensajeError(shellDialogo, "El porcentaje de ganancia del precio minimo no puede ser igual a 0");
							e.doit = false;

						}
					}
				}
			});

			//Se agregan eventos de foco para validar los datos Precio Lista mayor a minimo
			tPrecioLista.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPrecioLista.getText().isEmpty()) {

							float precioMinimo = Float.parseFloat(tPrecioMinimo.getText());
							float precioLista = Float.parseFloat(tPrecioLista.getText());
							float precioCosto = Float.parseFloat(tCosto.getText());



							if ( precioLista <= precioMinimo) {

								ConfigurarText.mensajeError(shellDialogo, "El precio de lista debe ser mayor al precio minio");
								e.doit = false;

							} else {

								float porcentajePrecioLista = ((precioLista - precioCosto)/precioCosto) * 100;
								tPorcentajeGananciaPrecioLista.setText(String.valueOf(redondearNumero(porcentajePrecioLista,2)));
								tSubtotal.setText(String.valueOf(Float.parseFloat(tCosto.getText()) * Float.parseFloat(tCantidad.getText())));


								item.setText(6, tPrecioLista.getText());
								item.setText(7, tPorcentajeGananciaPrecioLista.getText());
								item.setText(8, tSubtotal.getText());

								tCantidadTotal.setText(String.valueOf(calcularCantidad()));
								tTotalHija.setText(String.valueOf(calcularTotal()));


								agregaItemTabla();



							}

						}
					}
				}
			});



			//Se agregan eventos de foco para validar los datos porcentaje  mayor a 0
			tPorcentajeGananciaPrecioLista.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPorcentajeGananciaPrecioLista.getText().isEmpty()) {

							float precioLista = Float.parseFloat(tCosto.getText())* (1 + Float.parseFloat(tPorcentajeGananciaPrecioLista.getText()) / 100);
							tPrecioLista.setText(String.valueOf(precioLista));
							tSubtotal.setText(String.valueOf(Float.parseFloat(tCosto.getText()) * Float.parseFloat(tCantidad.getText())));

							item.setText(6, tPrecioLista.getText());
							item.setText(7, tPorcentajeGananciaPrecioLista.getText());
							item.setText(8, tSubtotal.getText());

							tCantidadTotal.setText(String.valueOf(calcularCantidad()));
							tTotalHija.setText(String.valueOf(calcularTotal()));

							agregaItemTabla();

						} else {

							ConfigurarText.mensajeError(shellDialogo, "El porcentaje de ganancia del precio lista no puede ser igual a 0");
							e.doit = false;

						}
					}
				}
			});



			//Se definen los TableEditor para cada columna
			TableEditor editorTalla = new TableEditor(tbExistenciasPorProductos);
			editorTalla.grabHorizontal = true;
			editorTalla.setEditor(tTalla, item, 0);

			TableEditor editorColor = new TableEditor(tbExistenciasPorProductos);
			editorColor.grabHorizontal = true;
			editorColor.setEditor(tColor, item, 1);

			TableEditor editorCantidad = new TableEditor(tbExistenciasPorProductos);
			editorCantidad.grabHorizontal = true;
			editorCantidad.setEditor(tCantidad, item, 2);

			TableEditor editorCosto = new TableEditor(tbExistenciasPorProductos);
			editorCosto.grabHorizontal = true;
			editorCosto.setEditor(tCosto, item, 3);

			TableEditor editorPrecioMinimo = new TableEditor(tbExistenciasPorProductos);
			editorPrecioMinimo.grabHorizontal = true;
			editorPrecioMinimo.setEditor(tPrecioMinimo, item, 4);

			TableEditor editorPorcentajeGananciaPrecioMinimo = new TableEditor(tbExistenciasPorProductos);
			editorPorcentajeGananciaPrecioMinimo.grabHorizontal = true;
			editorPorcentajeGananciaPrecioMinimo.setEditor(tPorcentajeGananciaPrecioMinimo, item, 5);

			TableEditor editorPrecioLista  = new TableEditor(tbExistenciasPorProductos);
			editorPrecioLista.grabHorizontal = true;
			editorPrecioLista.setEditor(tPrecioLista, item, 6);

			TableEditor editorPorcentajeGananciaPrecioLista = new TableEditor(tbExistenciasPorProductos);
			editorPorcentajeGananciaPrecioLista.grabHorizontal = true;
			editorPorcentajeGananciaPrecioLista.setEditor(tPorcentajeGananciaPrecioLista, item, 7);

			TableEditor editorSubtotal = new TableEditor(tbExistenciasPorProductos);
			editorSubtotal.grabHorizontal = true;
			editorSubtotal.setEditor(tSubtotal, item, 8);


			TableEditor editor = new TableEditor (tbExistenciasPorProductos);
			editor.minimumWidth = 23;
			editor.horizontalAlignment = SWT.CENTER;
			editor.setEditor(bAgregarFoto, item, 9);

			editor = new TableEditor (tbExistenciasPorProductos);
			editor.minimumWidth = 23;
			editor.horizontalAlignment = SWT.CENTER;
			editor.setEditor(bEliminarFila, item, 10);


			tTalla.forceFocus();


		}

		//******************************************* agregarEditorTablaPesos ***********************************************************
		private void agregarEditorTablaDolares(final TableItem item) {


			//Se definen los Text que se van agregan a la Tabla
			final Text tTalla = new Text(tbExistenciasPorProductos, SWT.CENTER);
			tTalla.addFocusListener(this);
			tTalla.setTextLimit(2);

			final Text  tColor = new Text(tbExistenciasPorProductos, SWT.NONE);
			tColor.addFocusListener(this);
			
			
			final Text  tMarca = new Text(tbExistenciasPorProductos, SWT.NONE);
			tMarca.addFocusListener(this);
			
			

			final Text  tCantidad = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tCantidad.addFocusListener(this);
			tCantidad.setTextLimit(4);

			final Text  tCostoDolares = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tCostoDolares.addFocusListener(this);

			final Text  tCostoPesos = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tCostoPesos.setBackground(getVisualatributodeshabilidatocomponente());
			tCostoPesos.addFocusListener(this);
			tCostoPesos.setEnabled(false);

			final Text  tPrecioMinimo = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tPrecioMinimo.addFocusListener(this);

			final Text  tPorcentajeGananciaPrecioMinimo = new Text(tbExistenciasPorProductos, SWT.CENTER);
			tPorcentajeGananciaPrecioMinimo.setTextLimit(3);
			tPorcentajeGananciaPrecioMinimo.setEnabled(false);
			tPorcentajeGananciaPrecioMinimo.setBackground(getVisualatributodeshabilidatocomponente());

			final Text  tPrecioLista = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tPrecioLista.addFocusListener(this);

			final Text  tPorcentajeGananciaPrecioLista = new Text(tbExistenciasPorProductos, SWT.CENTER);
			tPorcentajeGananciaPrecioLista.setTextLimit(3);
			tPorcentajeGananciaPrecioLista.setEnabled(false);
			tPorcentajeGananciaPrecioLista.setBackground(getVisualatributodeshabilidatocomponente());


			final Text  tSubtotal = new Text(tbExistenciasPorProductos, SWT.RIGHT);
			tSubtotal.addFocusListener(this);
			tSubtotal.setEditable(false);
			tSubtotal.setEnabled(false);
			tSubtotal.setBackground(getVisualatributodeshabilidatocomponente());



			Button bAgregarFoto = new Button(tbExistenciasPorProductos, SWT.PUSH);


			Button bEliminarFila = new Button(tbExistenciasPorProductos, SWT.PUSH); 

			//Se agrega este Text como comodin para agregar el codigo del color en la tabla
			final Text tCodigoColor = new Text(tbExistenciasPorProductos, SWT.RIGHT);

			final Text tCodigoMarca = new Text(tbExistenciasPorProductos, SWT.RIGHT);

			
			//Se agregan los eventos
			tTalla.addVerifyListener(ConfigurarText.getConvertirMayusculaLetras());
			tColor.addVerifyListener(ConfigurarText.getConvertirMayusculaLetras());
			tMarca.addVerifyListener(ConfigurarText.getConvertirMayusculaLetras());
			tCantidad.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tCostoDolares.addVerifyListener(ConfigurarText.getValidarEntradaNumeroReales());
			tPrecioMinimo.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPorcentajeGananciaPrecioMinimo.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPrecioLista.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());
			tPorcentajeGananciaPrecioLista.addVerifyListener(ConfigurarText.getValidarEntradaNumeros());

			//Se agregan eventos de foco para validar los datos Talla no nula
			tTalla.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT
							|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {

						if (tTalla.getText().isEmpty()) {

							ConfigurarText.mensajeError(shellDialogo, "Digite la talla del producto");
							e.doit = false;

						} else {

							item.setText(0, tTalla.getText());

						}

					}
				}
			});

			//Se agregan eventos de foco para validar los datos COlor no nulo
			tColor.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT
							|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {

						if (tColor.getText().isEmpty()) {

							ConfigurarText.mensajeError(shellDialogo, "Digite el color del producto");
							e.doit = false;

						} else {  

								if (item.getText(13).isEmpty()) { //Se busca si el color que se agrego se encuentra en la BD

									String idColor = existeColor(tColor.getText());

									if ( idColor == null) {

										int opcion = mensajeConfirmacion(sShell, "Este color no se encuentra registrado en la tabla colores.\n Desea registrarlo?");

										if (opcion == 32) {

											item.setText(13, "-1");
											item.setText(1, tColor.getText());
											listarValor.ocultarListaValor();

										} else

											e.doit = false;

									} else {

										item.setText(13, idColor);
										item.setText(1, tColor.getText());
										listarValor.ocultarListaValor();

									}


								} else {


									item.setText(1, tColor.getText());
									listarValor.ocultarListaValor();

								}

							}	

					}
				}
			});
			
			//Se configura la lista de valores para el color
			String sentenciaSQL = "Select idColor Codigo,COlor  From COlores Where color";

			Object camposRetorno[][] = new Object[2][2];
			camposRetorno[0][0] =  tColor;
			camposRetorno[0][1] =  "1";

			camposRetorno[1][0] =  tCodigoColor;
			camposRetorno[1][1] =  "0";


			int[] anchoColumnas = {80,293}; 

			listarValor.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL, camposRetorno, tMarca, anchoColumnas);

			tColor.addKeyListener(listarValor);
			
			
			
			
			//Se agrega este elemento como comodin para agregar el codigo del color en la tabla
			tCodigoColor.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent arg0) {

					item.setText(1,tColor.getText());
					item.setText(13,tCodigoColor.getText());
					

				}
			});

			//Se agregan eventos de foco para validar los datos COlor no nulo
			tMarca.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT
							|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {

						if (tColor.getText().isEmpty()) {

							ConfigurarText.mensajeError(shellDialogo, "Digite la marca del producto");
							e.doit = false;

						} else {  
							if (esMismaTallayColoryMarca(tTalla.getText(), tColor.getText(),tMarca.getText())) {

								ConfigurarText.mensajeError(shellDialogo, "La talla y el color del producto ya ha sido digitado para la marca " + tMarca.getText());
								e.doit = false; 

							} else

								if (item.getText(14).isEmpty()) { //Se busca si el color que se agrego se encuentra en la BD

									String idMarca = existeMarca(tMarca.getText());

									if ( idMarca == null) {

										int opcion = mensajeConfirmacion(sShell, "Esta marca no se encuentra registrada en la tabla marcas.\n Desea registrarlo?");

										if (opcion == 32) {

											item.setText(14, "-1");
											item.setText(2, tMarca.getText());
											listarValorMarcas.ocultarListaValor();

										} else

											e.doit = false;

									} else {

										item.setText(14, idMarca);
										item.setText(2, tMarca.getText());
										listarValorMarcas.ocultarListaValor();

									}


								} else {


									item.setText(2, tMarca.getText());
									listarValorMarcas.ocultarListaValor();

								}

							}	

					}
				}
			});

			
			
		

			

			//Se configura la lista de valores para el color
			sentenciaSQL = "Select idMarca Codigo,marca  Marca From marcas Where marca";

			camposRetorno = new Object[2][2];
			camposRetorno[0][0] =  tMarca;
			camposRetorno[0][1] =  "1";

			camposRetorno[1][0] =  tCodigoMarca;
			camposRetorno[1][1] =  "0";

			
			listarValorMarcas.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL, camposRetorno, tCantidad, anchoColumnas);

			tMarca.addKeyListener(listarValorMarcas);

			//Se agrega este elemento como comodin para agregar el codigo del color en la tabla
			tCodigoMarca.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent arg0) {

					item.setText(2,tMarca.getText());
					item.setText(14,tCodigoMarca.getText());

				}
			});

			//Se agregan eventos de foco para validar los datos Cantidad no nula y mayor a 0
			tCantidad.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tCantidad.getText().isEmpty()) {

							if  (Integer.parseInt(tCantidad.getText()) <= 0) { 

								ConfigurarText.mensajeError(shellDialogo, "La cantidad debe ser mayor a 0");
								e.doit = false;

							}	else

								item.setText(3, tCantidad.getText());

						} else {

							ConfigurarText.mensajeError(shellDialogo, "Digite la cantidad del producto comprado");
							e.doit = false;

						}
					}
				}
			});

			//Se agregan eventos de foco para validar los datos costo mayor a 0
			tCostoDolares.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tCostoDolares.getText().isEmpty()) {

							float dolares = new Float(tCostoDolares.getText());

							if  (dolares <= 0) { 

								ConfigurarText.mensajeError(shellDialogo, "El costo del producto debe ser mayor a 0");
								e.doit = false;

							}	else


								tCostoPesos.setText(String.valueOf(dolares * new Float(tCambioPesos.getText())));
							item.setText(4, tCostoDolares.getText());
							item.setText(5, tCostoPesos.getText());

						} else {

							ConfigurarText.mensajeError(shellDialogo, "Digite el costo del producto comprado");
							e.doit = false;

						}
					}
				}
			});



			//Se agregan eventos de foco para validar los datos: Precio minimo mayor a costo
			tPrecioMinimo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPrecioMinimo.getText().isEmpty()) {

							float precioMinimo = Float.parseFloat(tPrecioMinimo.getText());
							float precioCosto = Float.parseFloat(tCostoPesos.getText());

							if ( precioMinimo <= precioCosto) {

								ConfigurarText.mensajeError(shellDialogo, "El precio minimo debe venta debse ser mayor al costo del producto");
								e.doit = false;

							} else {

								float porcentajePrecioMinimo = ((precioMinimo - precioCosto)/precioCosto) * 100;
								tPorcentajeGananciaPrecioMinimo.setText(String.valueOf(porcentajePrecioMinimo));
								item.setText(6, tPrecioMinimo.getText());


							}

						}
					}
				}
			});


			//Se agregan eventos de foco para validar los datos porcentaje  mayor a 0
			tPorcentajeGananciaPrecioMinimo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPorcentajeGananciaPrecioMinimo.getText().isEmpty()) {

							float precioMinimo = Float.parseFloat(tCostoPesos.getText())* (1 + Float.parseFloat(tPorcentajeGananciaPrecioMinimo.getText()) / 100);
							tPrecioMinimo.setText(String.valueOf(precioMinimo));
							item.setText(6, tPrecioMinimo.getText());
							item.setText(7, tPorcentajeGananciaPrecioMinimo.getText());

						} else {

							ConfigurarText.mensajeError(shellDialogo, "El porcentaje de ganancia del precio minimo no puede ser igual a 0");
							e.doit = false;

						}
					}
				}
			});

			//Se agregan eventos de foco para validar los datos Precio Lista mayor a minimo
			tPrecioLista.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPrecioLista.getText().isEmpty()) {

							float precioMinimo = Float.parseFloat(tPrecioMinimo.getText());
							float precioLista = Float.parseFloat(tPrecioLista.getText());
							float precioCosto = Float.parseFloat(tCostoPesos.getText());


							if ( precioLista <= precioMinimo) {

								ConfigurarText.mensajeError(shellDialogo, "El precio de lista debe ser mayor al precio minio");
								e.doit = false;

							} else {

								float porcentajePrecioLista = ((precioLista - precioCosto)/precioCosto) * 100;
								tPorcentajeGananciaPrecioLista.setText(String.valueOf(porcentajePrecioLista));
								item.setText(8, tPrecioLista.getText());
								tSubtotal.setText(String.valueOf(Float.parseFloat(tCostoPesos.getText()) * Float.parseFloat(tCantidad.getText())));
								item.setText(9, tPorcentajeGananciaPrecioLista.getText());
								item.setText(10, tSubtotal.getText());


								tCantidadTotal.setText(String.valueOf(calcularCantidad()));
								tTotalHija.setText(String.valueOf(calcularTotal()));


								agregaItemTabla();

							}

						}
					}
				}
			});



			//Se agregan eventos de foco para validar los datos porcentaje  mayor a 0
			tPorcentajeGananciaPrecioLista.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

						if (!tPorcentajeGananciaPrecioLista.getText().isEmpty()) {

							float precioLista = Float.parseFloat(tCostoPesos.getText())* (1 + Float.parseFloat(tPorcentajeGananciaPrecioLista.getText()) / 100);
							tPrecioLista.setText(String.valueOf(precioLista));
							tSubtotal.setText(String.valueOf(Float.parseFloat(tCostoPesos.getText()) * Float.parseFloat(tCantidad.getText())));
							item.setText(8, tPrecioLista.getText());
							item.setText(9, tPorcentajeGananciaPrecioLista.getText());
							item.setText(10, tSubtotal.getText());


							tCantidadTotal.setText(String.valueOf(calcularCantidad()));
							tTotalHija.setText(String.valueOf(calcularTotal()));


							agregaItemTabla();

						} else {

							ConfigurarText.mensajeError(shellDialogo, "El porcentaje de ganancia del precio lista no puede ser igual a 0");
							e.doit = false;

						}
					}
				}
			});



			//Se definen los TableEditor para cada columna
			TableEditor editorTalla = new TableEditor(tbExistenciasPorProductos);
			editorTalla.grabHorizontal = true;
			editorTalla.setEditor(tTalla, item, 0);

			TableEditor editorColor = new TableEditor(tbExistenciasPorProductos);
			editorColor.grabHorizontal = true;
			editorColor.setEditor(tColor, item, 1);

			TableEditor editorMarca = new TableEditor(tbExistenciasPorProductos);
			editorMarca.grabHorizontal = true;
			editorMarca.setEditor(tMarca, item, 2);

			
			TableEditor editorCantidad = new TableEditor(tbExistenciasPorProductos);
			editorCantidad.grabHorizontal = true;
			editorCantidad.setEditor(tCantidad, item, 3);

			TableEditor editorCosto = new TableEditor(tbExistenciasPorProductos);
			editorCosto.grabHorizontal = true;
			editorCosto.setEditor(tCostoDolares, item, 4);

			TableEditor editorCostoPesos = new TableEditor(tbExistenciasPorProductos);
			editorCostoPesos.grabHorizontal = true;
			editorCostoPesos.setEditor(tCostoPesos, item, 5);


			TableEditor editorPrecioMinimo = new TableEditor(tbExistenciasPorProductos);
			editorPrecioMinimo.grabHorizontal = true;
			editorPrecioMinimo.setEditor(tPrecioMinimo, item, 6);

			TableEditor editorPorcentajeGananciaPrecioMinimo = new TableEditor(tbExistenciasPorProductos);
			editorPorcentajeGananciaPrecioMinimo.grabHorizontal = true;
			editorPorcentajeGananciaPrecioMinimo.setEditor(tPorcentajeGananciaPrecioMinimo, item, 7);

			TableEditor editorPrecioLista  = new TableEditor(tbExistenciasPorProductos);
			editorPrecioLista.grabHorizontal = true;
			editorPrecioLista.setEditor(tPrecioLista, item, 8);

			TableEditor editorPorcentajeGananciaPrecioLista = new TableEditor(tbExistenciasPorProductos);
			editorPorcentajeGananciaPrecioLista.grabHorizontal = true;
			editorPorcentajeGananciaPrecioLista.setEditor(tPorcentajeGananciaPrecioLista, item, 9);

			TableEditor editorSubtotal = new TableEditor(tbExistenciasPorProductos);
			editorSubtotal.grabHorizontal = true;
			editorSubtotal.setEditor(tSubtotal, item, 10);


			TableEditor editor = new TableEditor (tbExistenciasPorProductos);
			editor.minimumWidth = 23;
			editor.horizontalAlignment = SWT.CENTER;
			editor.setEditor(bAgregarFoto, item, 11);

			editor = new TableEditor (tbExistenciasPorProductos);
			editor.minimumWidth = 23;
			editor.horizontalAlignment = SWT.CENTER;
			editor.setEditor(bEliminarFila, item, 12);


			tTalla.forceFocus();


		}

		//**************************** Metodo calcularTotalDolaresPadre **********************************************
		private float calcularTotalDolaresHija() {

			float resultadoFloat = 0;

			TableItem items[] = tbExistenciasPorProductos.getItems();

			for (int i = 0; i < items.length; i++) {

				if (!items[i].getText(3).isEmpty())
					
				  resultadoFloat += new Float(items[i].getText(3))  * new Float(items[i].getText(4));
				
			}	

			return resultadoFloat;

		}

		//********************************************** ExisteColor *****************************************************
		private String existeColor(String color) {

			String resultadoString = null;

			try {


				if ( consultarColor == null)

					consultarColor = conectarMySQL.getConexion().prepareStatement("Select idColor From Colores Where color = ?");


				consultarColor.setString(1, color);

				ResultSet resultado = consultarColor.executeQuery();

				if (resultado.next()) // Si existe el color

					resultadoString = resultado.getString(1);


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return resultadoString;
		}

		
		//********************************************** ExisteMarca *****************************************************
		private String existeMarca(String marca) {

			String resultadoString = null;

			try {


				if ( consultarColor == null)

					consultarColor = conectarMySQL.getConexion().prepareStatement("Select idmarca From Marcas Where marca = ?");


				consultarColor.setString(1, marca);

				ResultSet resultado = consultarColor.executeQuery();

				if (resultado.next()) // Si existe el color

					resultadoString = resultado.getString(1);


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return resultadoString;
		}
		//*********************************************** Metodo esMismaTallayColor() ****************************************

		private boolean esMismaTallayColoryMarca(String talla,String color,String marca) {

			boolean resultadoBoolean = false;

			TableItem items[] = tbExistenciasPorProductos.getItems();

			int i = 0;

			while (!resultadoBoolean && i < (items.length - 1)) {


				String tallaTabla = items[i].getText(0);
				String colorTabla = items[i].getText(1);
				String marcaTabla = items[i].getText(2);

				if(tallaTabla.equals(talla) && colorTabla.equals(color) && marcaTabla.equals(marca))

					resultadoBoolean = true;

				else

					i++;


			}

			return resultadoBoolean;


		}

		//*********************************************** Metodo calcularCantidad() ****************************************

		private float calcularCantidad() {

			float resultadoFloat = 0;

			TableItem items[] = tbExistenciasPorProductos.getItems();

			for (int i = 0; i < items.length; i++) {

				resultadoFloat += new Float(items[i].getText(3));
			}	

			return resultadoFloat;

		}



		//*********************************************** Metodo calcularTotal() ****************************************

		private float calcularTotal() {

			float resultadoFloat = 0;

			TableItem items[] = tbExistenciasPorProductos.getItems();

			int columnaPivote = cMoneda.getText().equals("Pesos") ? 9 : 10;

			for (int i = 0; i < items.length; i++)

				resultadoFloat += new Float(items[i].getText(columnaPivote)); 

			return resultadoFloat;

		}

		//*********************************************** Metodo calcularTotal() ****************************************

		private float calcularTotalDolares() {

			float resultadoFloat = 0;

			TableItem items[] = tbExistenciasPorProductos.getItems();


			for (int i = 0; i < items.length; i++)

				if (!items[i].getText(4).isEmpty())

					resultadoFloat += new Float(items[i].getText(4)); 



			return resultadoFloat;

		}

		
		//*********************************************** Metodo calcularCostoPadre() ****************************************

		private float calcularCostoPadre() {

			float resultadoFloat = 0;

			TableItem items[] = tbExistenciasPorProductos.getItems();


			for (int i = 0; i < items.length; i++)

				if (!items[i].getText(5).isEmpty())

					resultadoFloat += new Float(items[i].getText(5)); 



			return resultadoFloat;

		}
		// **************************** Eventos de foco ********************************************************

		@Override
		public void focusGained(FocusEvent a) {

			Object fuente = a.getSource();

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

			// Se agrega este evento para cambiar de color al componente que tiene el evento
			if (fuente.getClass().toString().contains("Text")) {

				Text fuenteTexto = ((Text) fuente);
				fuenteTexto.setBackground(getVisualAtributoPierdeFocoComponentes());

			}

		}

		//***************************************************** Eventos ***********************************************************
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void widgetSelected(SelectionEvent a) {

			Object fuente = a.getSource();

			if (fuente == bAceptar) {

				tCantidadPadre.setText(tCantidadTotal.getText());
				itemPadre.setText(3, tCantidadPadre.getText());

				

				if (cMoneda.getSelectionIndex() == 0) {


					itemPadre.setText(5, tTotalHija.getText());

				} else {

					tCostoPadre.setText(String.valueOf(new Float(tTotalHija.getText())/new Float(tCantidadTotal.getText())));
					itemPadre.setText(4, tCostoPadre.getText());
					itemPadre.setText(5, String.valueOf(new Float(calcularTotalDolaresHija())/new Float(tCantidadTotal.getText())));
					itemPadre.setText(6, tTotalHija.getText());
					tTotalDolares.setText(String.valueOf(calcularTotalDolaresHija()));

				}



				formatearDatosSubProductos(tbExistenciasPorProductos,itemPadre);
				tTotal.setText(tTotalHija.getText());
				agregaItemTablaPadre();
				shellDialogo.close();

			} else

				if (fuente == bCancelar) {

					shellDialogo.close();	
				}

		}



		//********************************** Interfaz Grafica**************************************************

		private void inicializar(String moneda) {


			shellDialogo = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
			shellDialogo.setSize(new Point(770, 340));
			shellDialogo.setLayout(null);

			ConfigurarText.centrarShell(shellDialogo);

			createGrupoExitenciasPorProducto();
			bAceptar = new Button(shellDialogo, SWT.NONE);
			bAceptar.setBounds(new Rectangle(202, 282, 113, 27));
			bAceptar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/OK.gif"));
			bAceptar.setText("Aceptar");
			bCancelar = new Button(shellDialogo, SWT.NONE);
			bCancelar.setBounds(new Rectangle(452, 282, 113, 27));
			bCancelar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/NO.gif"));
			bCancelar.setText("Cancelar");

			agregarEventos();

			if (moneda.equals("Pesos")) //Se llama la tabla dependiendo de la moneda

				agregarConfiguracionesPesos();

			else	

				agregarConfiguracionesDolares();

			shellDialogo.open();

			while (!Display.getCurrent().isDisposed()) {
				if (!Display.getCurrent().readAndDispatch())
					Display.getCurrent().sleep();
			}
		}

		/**
		 * This method initializes grupoExitenciasPorProducto	
		 *
		 */
		private void createGrupoExitenciasPorProducto() {
			grupoExitenciasPorProducto = new Group(shellDialogo, SWT.NONE);
			grupoExitenciasPorProducto.setLayout(null);
			grupoExitenciasPorProducto.setText("Existencias Por Productos");
			grupoExitenciasPorProducto.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
			grupoExitenciasPorProducto.setBounds(new Rectangle(3, 15, 754, 256));

			// Se instancia aqui para que salga al frente la lista de valores
			listarValor = new ListarValor(grupoExitenciasPorProducto,178,42,400,true);
			
			listarValorMarcas = new ListarValor(grupoExitenciasPorProducto,220,42,400,true);

			lCantidad = new Label(grupoExitenciasPorProducto, SWT.NONE);
			lCantidad.setBounds(new Rectangle(84, 226, 90, 15));
			lCantidad.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
			lCantidad.setText("Cantidad Total:");
			tCantidadTotal = new Text(grupoExitenciasPorProducto, SWT.BORDER | SWT.RIGHT);
			tCantidadTotal.setBounds(new Rectangle(177, 220, 64, 26));
			tCantidadTotal.setBackground(getVisualatributodeshabilidatocomponente());
			tCantidadTotal.setEnabled(false);
			tCantidadTotal.setEditable(false);
			tbExistenciasPorProductos = new Table(grupoExitenciasPorProducto, SWT.NONE);
			tbExistenciasPorProductos.setHeaderVisible(true);
			tbExistenciasPorProductos.setLinesVisible(true);
			tbExistenciasPorProductos.setBounds(new Rectangle(12, 19, 733, 193));
			tTotalHija = new Text(grupoExitenciasPorProducto, SWT.BORDER | SWT.RIGHT);
			tTotalHija.setBackground(getVisualatributodeshabilidatocomponente());
			tTotalHija.setBounds(new Rectangle(552, 220, 68, 26));
			tTotalHija.setEditable(false);
			tTotalHija.setEnabled(false);
			lTotal = new Label(grupoExitenciasPorProducto, SWT.NONE);
			lTotal.setBounds(new Rectangle(507, 226, 39, 15));
			lTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
			lTotal.setText("Total:");
		}



	}


}
