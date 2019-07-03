package com.JASoft.GUI.SIPY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.ResultSetSerializable;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;

public class GUIProducto extends ConfigurarText implements FocusListener,SelectionListener{

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="-6,9"
	private ToolBar toolBar;
	private Group grupoIdentificacion = null;
	private Label lCodigo = null;
	private Text tCodigo = null;
	private Label lDescripcion = null;
	private Text tDescripcion = null;
	private Label lCategoria = null;
	private Text tCodigoCategoria = null;
	private Text tCategoria = null;
	private Group grupoImagen = null;
	private Text taObservaciones = null;
	private Label lObservaciones = null;
	private Group grupoEjemplaresProdutos = null;
	private Table taEjemplares = null;
	private Group grupoStock = null;
	private Label lActual = null;
	private Label lMinimo = null;
	private Text tActual = null;
	private Text tMinimo = null;
	private Label lUnidades = null;
	private Label lUnidades1 = null;
	private Button chIVAIncluido = null;
	private Button bCaptar = null;
	private Button bExaminar = null;
	private ConectarMySQL conectarMySQL= null;
	private ArrayList<Text> arrayTextRequeridos= null;  //  @jve:decl-index=0:
	private PreparedStatement consultar;  //  @jve:decl-index=0:
	private PreparedStatement consultarExitencias;  //  @jve:decl-index=0:
	private FileDialog directorio;
	private String archivoSelecionado;
	private Label lFoto = null;
	private Shell shellPadre;
	private boolean retornarListaProducto = false;


	/*
	 * Constructor general
	 */
	public GUIProducto(ConectarMySQL conectarMySQL,Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		centrarShell(sShell);

		tCodigo.forceFocus(); //Se asigna el foco al primer componente navegable

		agregarConfiguraciones();

		agregarEventos();

		crearArrayRequeridos();

		sShell.open();

	}
	
	
	/*
	 * Constructor general
	 */
	public GUIProducto(ConectarMySQL conectarMySQL,Shell shellPadre,String idProducto) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		centrarShell(sShell);

		tCodigo.setText(idProducto);
		
		agregarConfiguraciones();

		agregarEventos();

		crearArrayRequeridos();
		
		consultar();
		
		tCodigo.forceFocus();
		
		retornarListaProducto = true;

		sShell.open();

	}


	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	public void crearArrayRequeridos(){

		arrayTextRequeridos = new ArrayList<Text>();

		arrayTextRequeridos.add(tCodigo);
		arrayTextRequeridos.add(tDescripcion);
		arrayTextRequeridos.add(tCodigoCategoria);
		


	}



	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	public void limpiar(boolean comodin) {

		tDescripcion.setText("");
		tCodigoCategoria.setText("");
		tCategoria.setText("");
		taObservaciones.setText("");
		tActual.setText("0");
		tMinimo.setText("0");
		lFoto.setImage(null);
		chIVAIncluido.setSelection(true);
		taEjemplares.removeAll();

		if (comodin) {

			tCodigo.setText("");
			tCodigo.forceFocus();

		}


	}

	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	public void guardar(){

		if (validarRegistro(arrayTextRequeridos, sShell)){
			

			try {

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoProductos(conectarMySQL.getConexion());

				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, tCodigo.getText());
				llamarProcedimientoAlmacenado.setString(3, tDescripcion.getText());
				llamarProcedimientoAlmacenado.setInt(4, chIVAIncluido.getSelection() ? 1 : 0);
				llamarProcedimientoAlmacenado.setString(5, tMinimo.getText());
				llamarProcedimientoAlmacenado.setString(6, tCodigoCategoria.getText());
				llamarProcedimientoAlmacenado.setString(7, taObservaciones.getText());
				
				if (lFoto.getImage() != null) {
					
					/*
					 *  Para insertar la imagen se extraeran los byte del archivo
					 *  que se desea insertar
					 */
					File imagen = new File( archivoSelecionado );

					FileInputStream canalEntrada = new FileInputStream( imagen );
					
					llamarProcedimientoAlmacenado.setBinaryStream( 8, canalEntrada, (int)imagen.length() );
					
				} else
					
					llamarProcedimientoAlmacenado.setString( 8, null );
				

				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Los cambios al producto han sido registrado satisfactoriamente");
				
				if (retornarListaProducto)

					sShell.close();

				else

					limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell, "Error al guardar el registro de producto " +e);
			}
		}
	}	

	/*
	 * Metodo eliminar: Elimina un cliente
	 */
	public void eliminar(){

		for (int i = 0; i< taEjemplares.getColumnCount(); i++)
			System.out.println(taEjemplares.getColumn(i).getWidth());
			

		try {


			mensajeConfirmacion(sShell, "Esta seguro que desea eliminar este producto?");

			PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoProductos(conectarMySQL.getConexion());


			llamarProcedimientoAlmacenado.setInt(1, 0);
			llamarProcedimientoAlmacenado.setString(2, tCodigo.getText());
			llamarProcedimientoAlmacenado.setString(3, null);
			llamarProcedimientoAlmacenado.setString(4, null);
			llamarProcedimientoAlmacenado.setString(5, null);

			llamarProcedimientoAlmacenado.setString(6, null);
			llamarProcedimientoAlmacenado.setString(7, null);
			llamarProcedimientoAlmacenado.setString(8, null);
			llamarProcedimientoAlmacenado.setBinaryStream( 9, null );

			llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

			conectarMySQL.commit();

			mensajeInformacion(sShell,"Producto ha sido eliminado");

			limpiar(true);



		} catch (Exception e) {

			mensajeError(sShell, "Este producto tiene asociadas un conjunto de facturas, si desea eliminarlo primero debe eliminar las facturas asociadas" +e);
		}

	}




	/*
	 * Metodo consultar: Se consulta un cliente
	 */
	public void consultar(){

		try {

			String sentenciaSQL = "Select P.Descripcion,P.IvaIncluido,P.StockMinimo,P.Stock,P.IdCategoria,C.Categoria,P.Observaciones,P.Foto "
				+ "From Productos P, Categorias C "
				+ "Where idProducto = ? and C.IdCategoria = P.IdCategoria";

			if (consultar == null)

				consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);

			consultar.setString(1, tCodigo.getText());

			 ResultSet resultado = consultar.executeQuery();

			if (resultado.next()) { //Se extraen los elementos

				tDescripcion.setText(devuelveCadenaVaciaParaNulo(resultado.getString(1)));
				chIVAIncluido.setSelection(resultado.getBoolean(2));
				tMinimo.setText(devuelveCadenaVaciaParaNulo(resultado.getString(3)));
				tActual.setText(devuelveCadenaVaciaParaNulo(resultado.getString(4)));

				tCodigoCategoria.setText(devuelveCadenaVaciaParaNulo(resultado.getString(5)));
				tCategoria.setText(devuelveCadenaVaciaParaNulo(resultado.getString(6)));
				taObservaciones.setText(devuelveCadenaVaciaParaNulo(resultado.getString(7)));
				Blob imagenBaseDatos  = resultado.getBlob(8);

				if (imagenBaseDatos != null) {

					try {

						byte[] imgData = imagenBaseDatos.getBytes(1,(int)imagenBaseDatos.length());


						File file = new File("Imagen.jpg"); // Se crea un objeto de tipo file
						
						FileOutputStream canalSalida = new FileOutputStream(file); //Se crea un canal de salida para obtener los bytes 
						canalSalida.write(imgData);
						lFoto.setImage(getImagenEscalada(new Image(Display.getCurrent(),file.getPath()),285,135));	// Se cambia la imagen
						canalSalida.close();// Se cierra el canal

					} catch (Exception e) {

						e.printStackTrace();
					}

				}
				
				resultado.close();
				taEjemplares.removeAll();
				
				//Se consultan las exitencias
				 sentenciaSQL = "Select e.item,e.talla,C.color,m.marca,e.cantidad,e.precioMinimo,e.costoProducto,e.precioLista "
					+ "From ExistenciasPorProductos E, Marcas M,Colores c "
					+ "Where idProducto = ? and E.idMarca = M.IdMarca and E.color = c.idColor "
					+ "Order by 3,4,2";
				 
				
				 
				 if (consultarExitencias == null)
					 
					 consultarExitencias  = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);
					 
				 consultarExitencias.setString(1, tCodigo.getText());
				 
				 ResultSet resultadoExistencias = consultarExitencias.executeQuery(); //Se obtiene la matriz de datos

				 while (resultadoExistencias.next()) {

					 TableItem item = new TableItem(taEjemplares, SWT.NONE);

					 String porcentajeGananciaMinima = String.valueOf(redondearNumero((resultadoExistencias.getFloat(6)/resultadoExistencias.getFloat(7) - 1 )*100, 0));
					 String porcentajeGananciaLista =  String.valueOf(redondearNumero((resultadoExistencias.getFloat(8)/resultadoExistencias.getFloat(7) - 1 )*100, 0));

					 item.setText(new String[]{resultadoExistencias.getString(1),resultadoExistencias.getString(2),resultadoExistencias.getString(3),resultadoExistencias.getString(4),resultadoExistencias.getString(5),resultadoExistencias.getString(6),porcentajeGananciaMinima,resultadoExistencias.getString(8),porcentajeGananciaLista});


				 }
				
				

			
				




			} else

				limpiar(false);


		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}



	}



	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	private void agregarEventos(){


		//Se agregar eventos de focos a tNumeroNit para que consulte el cliente
		tCodigo.addFocusListener(this);
		tCodigo.addVerifyListener(getValidarEntradaNumeros());
		tCodigoCategoria.addVerifyListener(getValidarEntradaNumeros());
		tCodigoCategoria.addFocusListener(this);
		tMinimo.addVerifyListener(getValidarEntradaNumeros());

		//Se agregan eventos para que siempre escriba en mayuscula
		tDescripcion.addVerifyListener(getConvertirMayusculaLetras());
		tCategoria.addVerifyListener(getConvertirMayusculaLetras());
		taObservaciones.addVerifyListener(getConvertirMayusculaLetras());

		//Se agregan unos eventos de focos para marcar el compononene que lo tiene
		tDescripcion.addFocusListener(this);
		tMinimo.addFocusListener(this);
		tCodigoCategoria.addFocusListener(this);
		tCategoria.addFocusListener(this);
		taObservaciones.addFocusListener(this);
		

		//Se agregan eventos para validar los codigos de marcas y categorias
		tCodigoCategoria.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

					if (!tCodigoCategoria.getText().isEmpty()) {
						String categoria = buscarInformacion("Categorias", "idCategoria = " + tCodigoCategoria.getText());

						if (categoria != null) {

							tCategoria.setText(categoria);
							

						} else {

							mensajeInformacion(sShell, "Código de categoria no registrado");
							tCodigoCategoria.setText("");
							e.doit = false;
						}   
					}
				}
			}
		});

		

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);
		bExaminar.addSelectionListener(this);


	}

	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	private void agregarConfiguraciones(){

		//Se configura el limita de datos por Text
		tCodigo.setTextLimit(20);
		tDescripcion.setTextLimit(60);
		tMinimo.setTextLimit(4);
		tCodigoCategoria.setTextLimit(4);
		tCategoria.setTextLimit(60);
		
		
		sShell.setImage(new Image(Display.getCurrent(), "imagenes/InventarioProductos.gif"));

		tMinimo.setText("0");

		//Se configuran las columnas
		TableColumn clItem = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clTalla = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clColor = new TableColumn(taEjemplares, SWT.LEFT);
		TableColumn clMarca = new TableColumn(taEjemplares, SWT.LEFT);
		TableColumn clStock = new TableColumn(taEjemplares, SWT.RIGHT);
		TableColumn clMinimo = new TableColumn(taEjemplares, SWT.RIGHT);
		TableColumn clPorcentajeGanaciaPrecioMinimo = new TableColumn(taEjemplares, SWT.CENTER);
		TableColumn clPrecioLista = new TableColumn(taEjemplares, SWT.RIGHT);
		TableColumn clPorcentajeGanaciaPrecioLista = new TableColumn(taEjemplares, SWT.CENTER);

		clItem.setText("Item");
		clTalla.setText("Talla");
		clColor.setText("Color");
		clMarca.setText("Marca");
		clStock.setText("Cantidad");
		clMinimo.setText("P. Minimo");
		clPorcentajeGanaciaPrecioMinimo.setText("% Gan.");
		clPrecioLista.setText("P. Lista");
		clPorcentajeGanaciaPrecioLista.setText("% Gan.");
		
		
		
		
		clItem.setWidth(37);
		clTalla.setWidth(39);
		clColor.setWidth(165);
		clMarca.setWidth(165);
		clStock.setWidth(71);
		clMinimo.setWidth(68);
		clPorcentajeGanaciaPrecioMinimo.setWidth(68);
		clPrecioLista.setWidth(51);
		clPorcentajeGanaciaPrecioLista.setWidth(51);

	}


	//**************************** Metodo buscarInformacion ************************

	private String buscarInformacion(String tabla,String condicion){

		String resultadoString = null;

		final String sentenciaSQL = "Select * "+
		"From " + tabla + 
		" Where " + condicion;


		try {

			ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

			if (resultado.next()) 

				// Se asigna el valor
				resultadoString = resultado.getString(2);

		}  catch (Exception e) {

			mensajeError(sShell,"No existe ningun registro en la tabla");        

		}

		return resultadoString;

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

		if (fuente == tCodigo && !tCodigo.getText().isEmpty()) 

			consultar();

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

		if (fuente == toolBar.getbLimpiar()){

			
			limpiar(true);

		}else

			if (fuente == toolBar.getbGuardar())

				guardar();
			else
				if (fuente == toolBar.getbEliminar())

					eliminar();

				else
					if(fuente == toolBar.getbSalir())

						sShell.close();
					else

						if (fuente == bExaminar) {


							if (directorio == null) {

								directorio = new FileDialog(sShell);
								directorio.setText("Abrir");

								String[] filterExt = { "*.jpg", "*.jpeg", ".gif", "*.png" ,"*.*"};
								directorio.setFilterExtensions(filterExt);

							}	

							archivoSelecionado = directorio.open();


							lFoto.setImage(getImagenEscalada(new Image(Display.getCurrent(), archivoSelecionado),285,135));

						}

	}



	/****************************** Interfaz Grafica *******************************************************

	/**
	 * This method initializes sShell
	 * @param shellPadre 
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Producto");
		sShell.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		createGrupoIdentificacion();
		createGrupoImagen();
		createGrupoEjemplaresProdutos();
		createGrupoStock();
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(null);
	}

	/**
	 * This method initializes grupoIdentificacion	
	 *
	 */
	private void createGrupoIdentificacion() {
		grupoIdentificacion = new Group(sShell, SWT.NONE);
		grupoIdentificacion.setLayout(null);
		grupoIdentificacion.setText("Identificación");
		grupoIdentificacion.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoIdentificacion.setBounds(new Rectangle(12, 57, 435, 220));
		lCodigo = new Label(grupoIdentificacion, SWT.NONE);
		lCodigo.setBounds(new Rectangle(46, 26, 46, 15));
		lCodigo.setText("Código: ");
		tCodigo = new Text(grupoIdentificacion, SWT.BORDER);
		tCodigo.setBounds(new Rectangle(101, 22, 110, 23));
		tCodigo.setToolTipText("Digite el código del producto");
		lDescripcion = new Label(grupoIdentificacion, SWT.NONE);
		lDescripcion.setBounds(new Rectangle(26, 56, 66, 15));
		lDescripcion.setText("Descripción:: ");
		tDescripcion = new Text(grupoIdentificacion, SWT.BORDER);
		tDescripcion.setBounds(new Rectangle(101, 52, 313, 23));
		tDescripcion.setToolTipText("Digite la descripción del producto");
		lCategoria = new Label(grupoIdentificacion, SWT.NONE);
		lCategoria.setBounds(new Rectangle(32, 87, 60, 15));
		lCategoria.setText("Categoria:");
		tCodigoCategoria = new Text(grupoIdentificacion, SWT.BORDER);
		tCodigoCategoria.setBounds(new Rectangle(101, 83, 51, 23));
		tCodigoCategoria.setToolTipText("Digite el código de la categoria");
		tCategoria = new Text(grupoIdentificacion, SWT.BORDER);
		tCategoria.setBounds(new Rectangle(153, 83, 261, 23));
		tCategoria.setToolTipText("Digite la categoria o seleccione de la lista de valores el nombre de la categoria");
		taObservaciones = new Text(grupoIdentificacion, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		taObservaciones.setBounds(new Rectangle(101, 116, 319, 94));
		taObservaciones.setToolTipText("Observaciones Varias para el Producto");
		lObservaciones = new Label(grupoIdentificacion, SWT.NONE);
		lObservaciones.setBounds(new Rectangle(9, 116, 81, 15));
		lObservaciones.setText("Observaciones:");
	}

	/**
	 * This method initializes grupoImagen	
	 *
	 */
	private void createGrupoImagen() {
		grupoImagen = new Group(sShell, SWT.NONE);
		grupoImagen.setLayout(null);
		grupoImagen.setText("Imagen");
		grupoImagen.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoImagen.setBounds(new Rectangle(458, 57, 313, 220));
		bCaptar = new Button(grupoImagen, SWT.NONE);
		bCaptar.setBounds(new Rectangle(27, 169, 116, 27));
		bCaptar.setText("Captar Imagen");
		bExaminar = new Button(grupoImagen, SWT.NONE);
		bExaminar.setBounds(new Rectangle(170, 169, 116, 27));
		bExaminar.setText("Examinar");
		lFoto = new Label(grupoImagen, SWT.BORDER);
		lFoto.setBounds(new Rectangle(14, 21, 285, 135));
		lFoto.setText("");
	}

	/**
	 * This method initializes grupoEjemplaresProdutos	
	 *
	 */
	private void createGrupoEjemplaresProdutos() {
		grupoEjemplaresProdutos = new Group(sShell, SWT.NONE);
		grupoEjemplaresProdutos.setLayout(null);
		grupoEjemplaresProdutos.setText("Ejemplares");
		grupoEjemplaresProdutos.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoEjemplaresProdutos.setBounds(new Rectangle(16, 282, 761, 212));
		taEjemplares = new Table(grupoEjemplaresProdutos, SWT.BORDER |
				 SWT.FULL_SELECTION);
		taEjemplares.setHeaderVisible(true);
		taEjemplares.setLinesVisible(true);
		taEjemplares.setBounds(new Rectangle(20, 26, 725, 174));


	}

	/**
	 * This method initializes grupoStock	
	 *
	 */
	private void createGrupoStock() {
		grupoStock = new Group(sShell, SWT.NONE);
		grupoStock.setLayout(null);
		grupoStock.setText("Stock Actual y Minimo");
		grupoStock.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoStock.setBounds(new Rectangle(126, 496, 580, 60));
		lActual = new Label(grupoStock, SWT.NONE);
		lActual.setBounds(new Rectangle(45, 30, 42, 15));
		lActual.setText("Actual:");
		lMinimo = new Label(grupoStock, SWT.NONE);
		lMinimo.setBounds(new Rectangle(248, 28, 49, 15));
		lMinimo.setText("Minimo:");
		tActual = new Text(grupoStock, SWT.BORDER);
		tActual.setBounds(new Rectangle(96, 26, 48, 23));
		tActual.setEditable(false);
		tActual.setEnabled(false);
		tActual.setToolTipText("Stock actual del Producto");
		tMinimo = new Text(grupoStock, SWT.BORDER);
		tMinimo.setBounds(new Rectangle(305, 24, 48, 23));
		tMinimo.setToolTipText("Stock Minimo del Producto");
		lUnidades = new Label(grupoStock, SWT.NONE);
		lUnidades.setBounds(new Rectangle(154, 30, 28, 15));
		lUnidades.setText("Und.");
		lUnidades1 = new Label(grupoStock, SWT.NONE);
		lUnidades1.setBounds(new Rectangle(363, 28, 25, 15));
		lUnidades1.setText("Und.");
		chIVAIncluido = new Button(grupoStock, SWT.CHECK);
		chIVAIncluido.setBounds(new Rectangle(445, 27, 82, 16));
		chIVAIncluido.setText("IVA incluido");
		chIVAIncluido.setToolTipText("IVA incluido para el producto");
		chIVAIncluido.setSelection(true);
	}

}
