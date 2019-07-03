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

import com.JASoft.GUI.SIPY.GUICategoria.GUIDialogoCategorias;
import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;


public class GUIMarca extends ConfigurarText  implements SelectionListener {

	private Shell sShell = null;
	private Group grupoMarcas = null;
	private Table tbMarcas = null;
	private Button bAgregar = null;
	private Button bEditar = null;
	private Button bEliminar = null;
	private ToolBar toolBar;
	private String marcasBorradas;
	private ConectarMySQL conectarMySQL= null;




	public GUIMarca(ConectarMySQL conectarMySQL,Shell shellPadre) {

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

			PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoMarcas(conectarMySQL.getConexion());

			llamarProcedimientoAlmacenado.setString(1, getFormatearDatos());
			llamarProcedimientoAlmacenado.setString(2, marcasBorradas == null ? null : (marcasBorradas += ")"));

			llamarProcedimientoAlmacenado.execute();

			conectarMySQL.commit();

			mensajeInformacion(sShell,"Los cambios en las marcas han sido registrado");


			marcasBorradas = null;



		} catch (Exception e) {

			mensajeError(sShell, "Error al guardar el registro de categoria " +e);
		}
	}

	/*
	 * Metodo eliminar: Elimina una marca
	 */
	public void eliminar(){


		try {

			int opcion  = mensajeConfirmacion(sShell, "Esta seguro que desea eliminar esta marca?");


			if (opcion == SWT.OK) { //Si acepto

				if (tbMarcas.getSelectionIndex() >= 0){

					if (marcasBorradas == null)

						marcasBorradas = "(";

					else

						marcasBorradas += ",";

					marcasBorradas +=  tbMarcas.getItem(tbMarcas.getSelectionIndex()).getText(0);

					tbMarcas.remove(tbMarcas.getSelectionIndex());


				}else

					mensajeInformacion(sShell, "Se debe escoger la fila que se desea eliminar");

			}


		} catch (Exception e) {

			mensajeError(sShell, "Problemas al eliminar las categorias" +e);
		}

	}

	/*
	 * Metodo consultar: Se consulta las marcas
	 */
	public void consultar(){

		try {

			String sentenciaSQL = "Select * From Marcas Order by 1";

			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);


			ResultSet resultado = consultar.executeQuery();

			while (resultado.next()) {


				TableItem item = new TableItem(tbMarcas,SWT.NONE);
				item.setText(0, resultado.getString(1));
				item.setText(1, resultado.getString(2));

			}


		} catch (SQLException e) {

			mensajeError(sShell, "Problemas al eliminar marcas" +e);
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
		
		tbMarcas.addListener(SWT.MouseDoubleClick, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub

				Rectangle clientArea = tbMarcas.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = tbMarcas.getTopIndex();
				while (index < tbMarcas.getItemCount()) {
					boolean visible = false;
					TableItem item = tbMarcas.getItem(index);
					for (int i = 0; i < tbMarcas.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {

							GUIDialogoMarca gUIDialogoMarca = new GUIDialogoMarca(sShell,tbMarcas.getItem(tbMarcas.getSelectionIndex()));


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
		TableColumn clCodigoMarca = new TableColumn(tbMarcas, SWT.CENTER);
		TableColumn clMarca = new TableColumn(tbMarcas, SWT.LEFT);
		
		sShell.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/InventarioMarcas.gif"));

		//Se configuran los nombres de columnas
		clCodigoMarca.setText("Código");
		clMarca.setText("Marca");

		//Se configuran los tamaños  de columnas
		clCodigoMarca.setWidth(80);
		clMarca.setWidth(548);
	}		

	//**************************** Metodo formatearDatosDetalles **********************************************
	public String getFormatearDatos() {

		String formatoEnviarBaseDatos = "";

		String datosFormatedados = "(" ;

		TableItem items[] = tbMarcas.getItems();

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

						GUIDialogoMarca gUIDialogoMarca = new GUIDialogoMarca(sShell);

					} else
						if(fuente == bEliminar)

							eliminar();

						else
							if(fuente == bEditar)

								if (tbMarcas.getSelectionIndex() >= 0 ) {

									GUIDialogoMarca gUIDialogoMarca = new GUIDialogoMarca(sShell,tbMarcas.getItem(tbMarcas.getSelectionIndex()));

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
		sShell.setText("Marcas");
		createGrupoMarcas();
		sShell.setLayout(null);
		sShell.setBounds(new Rectangle(175, 175, 800, 600));
	}

	/**
	 * This method initializes grupoMarcas	
	 *
	 */
	private void createGrupoMarcas() {
		grupoMarcas = new Group(sShell, SWT.NONE);
		grupoMarcas.setLayout(null);
		grupoMarcas.setText("Marcas");
		grupoMarcas.setBounds(new Rectangle(61, 79, 674, 433));
		tbMarcas = new Table(grupoMarcas, SWT.FULL_SELECTION);
		tbMarcas.setHeaderVisible(true);
		tbMarcas.setLinesVisible(true);
		tbMarcas.setBounds(new Rectangle(27, 32, 628, 337));
		bAgregar = new Button(grupoMarcas, SWT.NONE);
		bAgregar.setBounds(new Rectangle(89, 385, 106, 30));
		bAgregar.setText("Agregar");
		bAgregar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Adicionar.gif"));
		bEditar = new Button(grupoMarcas, SWT.NONE);
		bEditar.setBounds(new Rectangle(284, 385, 106, 30));
		bEditar.setText("Editar");
		bEditar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Editar.gif"));
		bEliminar = new Button(grupoMarcas, SWT.NONE);
		bEliminar.setBounds(new Rectangle(479, 385, 106, 30));
		bEliminar.setText("Eliminar");
		bEliminar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Eliminar.gif"));
	}


	/******************************************************************************************************
	 * ************************************ Clase Interna  ************************************************
	 ******************************************************************************************************/

	class GUIDialogoMarca extends Dialog implements SelectionListener  {


		private Group grupoMarcas = null;
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

		public GUIDialogoMarca(Shell parent) {

			super(parent);

			createSShell();

			agregarConfiguraciones();

			agregarEventos();


			while (!Display.getCurrent().isDisposed()) {
				if (!Display.getCurrent().readAndDispatch())
					Display.getCurrent().sleep();
			}



		}


		public GUIDialogoMarca(Shell parent,TableItem itemEdicion) {

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

				TableItem item = new TableItem(tbMarcas, SWT.NONE);
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

			tCodigo.setText(String.valueOf(tbMarcas.getItemCount() + 1));

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
			grupoMarcas = new Group(shellDialogo, SWT.NONE);
			grupoMarcas.setLayout(null);
			grupoMarcas.setBounds(new Rectangle(19, 7, 359, 101));
			lCodigo = new Label(grupoMarcas, SWT.NONE);
			lCodigo.setBounds(new Rectangle(21, 34, 64, 15));
			lCodigo.setText("Código");
			tCodigo = new Text(grupoMarcas, SWT.BORDER | SWT.CENTER);
			tCodigo.setBounds(new Rectangle(21, 50, 64, 23));
			tCodigo.setEnabled(false);
			lDescripcion = new Label(grupoMarcas, SWT.NONE);
			lDescripcion.setBounds(new Rectangle(98, 34, 68, 15));
			lDescripcion.setText("Descripción");
			tDescripcion = new Text(grupoMarcas, SWT.BORDER);
			tDescripcion.setBounds(new Rectangle(98, 50, 227, 23));
		}



	}


}
