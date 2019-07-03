package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;


public class GUICategoria extends ConfigurarText implements SelectionListener{

	private Shell sShell = null;
	private Group grupoCategorias = null;
	private Table tbCategorias = null;
	private Button bAgregar = null;
	private Button bEditar = null;
	private Button bEliminar = null;
	private ConectarMySQL conectarMySQL= null;
	private ToolBar toolBar;
	private String categoriasBorradas;

	/*
	 * Contructor General
	 */
	public GUICategoria(ConectarMySQL conectarMySQL,Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		centrarShell(sShell);

		agregarConfiguraciones();

		agregarEventos();

		consultar();

		sShell.open();


	}



	/*
	 * Metodo guardar: Realiza los cambios
	 */
	public void guardar(){


		try {

			PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoCategorias(conectarMySQL.getConexion());

			llamarProcedimientoAlmacenado.setString(1, getFormatearDatos());
			llamarProcedimientoAlmacenado.setString(2, categoriasBorradas == null ? null : (categoriasBorradas += ")"));

			llamarProcedimientoAlmacenado.execute();

			conectarMySQL.commit();

			mensajeInformacion(sShell,"Los cambios en las Categorias han sido registrado");


			categoriasBorradas = null;


		} catch (Exception e) {

			mensajeError(sShell, "Error al guardar el registro de categoria " +e);
		}
	}



	/*
	 * Metodo eliminar: Elimina una categoria
	 */
	public void eliminar(){


		try {

			int opcion  = mensajeConfirmacion(sShell, "Esta seguro que desea eliminar esta categoria?");


			if (opcion == SWT.OK) { //Si acepto

				if (tbCategorias.getSelectionIndex() >= 0){

					if (categoriasBorradas == null)

						categoriasBorradas = "(";

					else

						categoriasBorradas += ",";

					categoriasBorradas +=  tbCategorias.getItem(tbCategorias.getSelectionIndex()).getText(0);

					tbCategorias.remove(tbCategorias.getSelectionIndex());


				}else

					mensajeInformacion(sShell, "Se debe escoger la fila que se desea eliminar");

			}


		} catch (Exception e) {

			mensajeError(sShell, "Este producto tiene asociadas un conjunto de facturas, si desea eliminarlo primero debe eliminar las facturas asociadas" +e);
		}

	}


	/*
	 * Metodo consultar: Se consulta las categorias
	 */
	public void consultar(){

		try {

			String sentenciaSQL = "Select * From Categorias Order by 1";

			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);


			ResultSet resultado = consultar.executeQuery();

			while (resultado.next()) {


				TableItem item = new TableItem(tbCategorias,SWT.NONE);
				item.setText(0, resultado.getString(1));
				item.setText(1, resultado.getString(2));

			}


		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}



	}


	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);
		bAgregar.addSelectionListener(this);
		bEditar.addSelectionListener(this);
		bEliminar.addSelectionListener(this);

		tbCategorias.addListener(SWT.MouseDoubleClick, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub

				Rectangle clientArea = tbCategorias.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = tbCategorias.getTopIndex();
				while (index < tbCategorias.getItemCount()) {
					boolean visible = false;
					TableItem item = tbCategorias.getItem(index);
					for (int i = 0; i < tbCategorias.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {

							GUIDialogoCategorias gUIDialogoCategorias = new GUIDialogoCategorias(sShell,tbCategorias.getItem(tbCategorias.getSelectionIndex()));

						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible)
						return;
					index++;
				}

			}
		});

	}


	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarConfiguraciones(){


		//Se configuran las columnas
		TableColumn clCodigoProducto = new TableColumn(tbCategorias, SWT.CENTER);
		TableColumn clCategoria = new TableColumn(tbCategorias, SWT.LEFT);
		
		sShell.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/InventarioCategorias.gif"));

		//Se configuran los nombres de columnas
		clCodigoProducto.setText("Código");
		clCategoria.setText("Categoria");

		//Se configuran los tamaños  de columnas
		clCodigoProducto.setWidth(80);
		clCategoria.setWidth(548);
	}		


	//**************************** Metodo formatearDatosDetalles **********************************************
	public String getFormatearDatos() {

		String formatoEnviarBaseDatos = "";

		String datosFormatedados = "(" ;

		TableItem items[] = tbCategorias.getItems();

		for (TableItem item : items) {

			if (!item.getText(1).isEmpty()) {

				datosFormatedados += item.getText(0) + "," + item.getText(1) +")";

				formatoEnviarBaseDatos =  formatoEnviarBaseDatos + datosFormatedados;

				datosFormatedados = "(";

			}  

		}

		return formatoEnviarBaseDatos;

	}

	//**************************** Eventos de accion *******************************************************

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void widgetSelected(SelectionEvent a) {

		Object fuente = a.getSource();

		if (fuente == toolBar.getbGuardar())

			guardar();
		else
			if (fuente == toolBar.getbEliminar())

				eliminar();

			else
				if(fuente == toolBar.getbSalir())

					sShell.close();

				else
					if(fuente == bAgregar){

						GUIDialogoCategorias gUIDialogoCategorias = new GUIDialogoCategorias(sShell);

					} else
						if(fuente == bEliminar)

							eliminar();

						else
							if(fuente == bEditar)

								if (tbCategorias.getSelectionIndex() >= 0 ) {

									GUIDialogoCategorias gUIDialogoCategorias = new GUIDialogoCategorias(sShell,tbCategorias.getItem(tbCategorias.getSelectionIndex()));

								} else {

									mensajeError(sShell, "Se debe escoger la fila a editar");
								}


	}


	/******************************************************************************************************
	 * ************************************ Interfaz Grafica **********************************************
	 ******************************************************************************************************/

	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
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
		tbCategorias = new Table(grupoCategorias,  SWT.FULL_SELECTION);
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


	/******************************************************************************************************
	 * ************************************ Clase Interna  ************************************************
	 ******************************************************************************************************/

	class GUIDialogoCategorias extends Dialog implements SelectionListener  {


		private Group grupoCategorias = null;
		private Label lCodigo = null;
		private Text tCodigo = null;
		private Label lDescripcion = null;
		private Text tDescripcion = null;
		private Button bAceptar = null;
		private Button bCancelar = null;
		private Shell shellDialogo = null;
		private TableItem itemEdicion;
		private boolean edicion = false;


		/*
		 * Constructor
		 */

		public GUIDialogoCategorias(Shell parent) {

			super(parent);

			createSShell();

			agregarConfiguraciones();

			agregarEventos();


			while (!Display.getCurrent().isDisposed()) {
				if (!Display.getCurrent().readAndDispatch())
					Display.getCurrent().sleep();
			}



		}


		public GUIDialogoCategorias(Shell parent,TableItem itemEdicion) {

			super(parent);

			createSShell();

			agregarEventos();

			//Se adicionan los datos de la columna acambiar
			tCodigo.setText(itemEdicion.getText(0));
			tDescripcion.setText(itemEdicion.getText(1));
			tDescripcion.selectAll();

			this.itemEdicion = itemEdicion;

			edicion = true;


			while (!Display.getCurrent().isDisposed()) {
				if (!Display.getCurrent().readAndDispatch())
					Display.getCurrent().sleep();
			}



		}

		/*
		 * Metodo adicionarActualizarFilaTabla: Adiciona o actualiza filas de la tabla 
		 */
		public void adicionarActualizarFilaTabla() {

			if (edicion) {

				itemEdicion.setText(0, tCodigo.getText());
				itemEdicion.setText(1, tDescripcion.getText());

			} else {

				TableItem item = new TableItem(tbCategorias, SWT.NONE);
				item.setText(0, tCodigo.getText());
				item.setText(1, tDescripcion.getText());

			}

			shellDialogo.close();

		}

		/*
		 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
		 */
		public void agregarEventos(){


			tDescripcion.addVerifyListener(getConvertirMayusculaLetras());

			tDescripcion.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					if (e.character == SWT.CR)

						adicionarActualizarFilaTabla();
				}
			});



			//Se agregar eventos a los botones
			bAceptar.addSelectionListener(this);
			bCancelar.addSelectionListener(this);


		}


		/*
		 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
		 */
		public void agregarConfiguraciones(){

			tCodigo.setText(String.valueOf(tbCategorias.getItemCount() + 1));


		}	

		//***************************************************** Eventos ***********************************************************

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void widgetSelected(SelectionEvent a) {

			Object fuente = a.getSource();

			if (fuente == bAceptar) 

				adicionarActualizarFilaTabla();

			else

				if (fuente == bCancelar) 

					shellDialogo.close();	



		}

		/******************************************************************************************************
		 * ************************************ Interfaz Grafica **********************************************
		 ******************************************************************************************************/


		/**
		 * This method initializes sShell
		 */
		private void createSShell() {


			shellDialogo = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
			shellDialogo.setSize(new Point(410,200));
			shellDialogo.setLayout(null);

			ConfigurarText.centrarShell(shellDialogo);

			createGrupoCategorias();
			bAceptar = new Button(shellDialogo, SWT.NONE);
			bAceptar.setBounds(new Rectangle(56, 124, 109, 27));
			bAceptar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/OK.gif"));
			bAceptar.setText("Aceptar");
			bCancelar = new Button(shellDialogo, SWT.NONE);
			bCancelar.setBounds(new Rectangle(229, 124, 109, 27));
			bCancelar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/NO.gif"));
			bCancelar.setText("Cancelar");

			shellDialogo.open();


		}

		/**
		 * This method initializes grupoCategorias	
		 *
		 */
		private void createGrupoCategorias() {
			grupoCategorias = new Group(shellDialogo, SWT.NONE);
			grupoCategorias.setLayout(null);
			grupoCategorias.setBounds(new Rectangle(19, 7, 359, 101));
			lCodigo = new Label(grupoCategorias, SWT.NONE);
			lCodigo.setBounds(new Rectangle(21, 34, 64, 15));
			lCodigo.setText("Código");
			tCodigo = new Text(grupoCategorias, SWT.BORDER | SWT.CENTER);
			tCodigo.setBounds(new Rectangle(21, 50, 64, 23));
			tCodigo.setEnabled(false);
			lDescripcion = new Label(grupoCategorias, SWT.NONE);
			lDescripcion.setBounds(new Rectangle(98, 34, 68, 15));
			lDescripcion.setText("Descripción");
			tDescripcion = new Text(grupoCategorias, SWT.BORDER);
			tDescripcion.setBounds(new Rectangle(98, 50, 227, 23));
		}



	}

}
