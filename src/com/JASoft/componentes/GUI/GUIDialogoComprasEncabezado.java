package com.JASoft.componentes.GUI;

import java.awt.Color;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;

import com.JASoft.componentesGraficos.ConfigurarText;

public class GUIDialogoComprasEncabezado extends Dialog implements SelectionListener {

	private Group grupoExitenciasPorProducto = null;
	private Label lCantidad = null;
	private Text tCantidadTotal = null;
	private Table tbExistenciasPorProductos = null;
	private Button bAceptar = null;
	private Button bCancelar = null;
	private Text tTotal = null;
	private Label lTotal = null;
	private Shell shellDialogo = null;
	private TableItem itemPadre;
	private Text tCantidadPadre;
	private Text tCostoPadre;
	private Text tSubtotalPadre;



	/*
	 * Constructor
	 */

	
	public GUIDialogoComprasEncabezado(Shell parent, TableItem itemPadre, Text tCantidadPadre, Text tCostoPadre,
			Text tSubtotalPadre) {
		
		super(parent);
		this.itemPadre = itemPadre;
		this.tCantidadPadre = tCantidadPadre;
		this.tCostoPadre = tCostoPadre;
		this.tSubtotalPadre = tSubtotalPadre;
		
		initialize();
	}


	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){

		//Se agregar eventos a los botones
		bAceptar.addSelectionListener(this);
		bCancelar.addSelectionListener(this);

	}


	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	public void agregarConfiguraciones(){

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


		//Se configura a la tabla un Item y u editor
		TableItem item = new TableItem(tbExistenciasPorProductos,SWT.NONE); // Se renderiza para agregar un boton

		agregarEditorTabla(item);


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
		clColor .setWidth(129);
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
		agregarEditorTabla(item);

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
            tCostoPadre.setText(tTotal.getText());
			tSubtotalPadre.setText(String.valueOf (Float.parseFloat(tCantidadTotal.getText()) * Float.parseFloat(tTotal.getText())));
			shellDialogo.close();

		} else
			
			if (fuente == bCancelar) {
				
				shellDialogo.close();	
			}
			
	}
	
	//******************************************* agregarEditorTabla ***********************************************************
	private void agregarEditorTabla(final TableItem item) {


		//Se definen los Text que se van agregan a la Tabla
		final Text tTalla = new Text(tbExistenciasPorProductos, SWT.CENTER);
		tTalla.setTextLimit(2);

		final Text  tColor = new Text(tbExistenciasPorProductos, SWT.NONE);

		final Text  tCantidad = new Text(tbExistenciasPorProductos, SWT.RIGHT);
		tCantidad.setTextLimit(4);

		final Text  tCosto = new Text(tbExistenciasPorProductos, SWT.RIGHT);
		final Text  tPrecioMinimo = new Text(tbExistenciasPorProductos, SWT.RIGHT);

		final Text  tPorcentajeGananciaPrecioMinimo = new Text(tbExistenciasPorProductos, SWT.CENTER);
		tPorcentajeGananciaPrecioMinimo.setTextLimit(3);

		final Text  tPrecioLista = new Text(tbExistenciasPorProductos, SWT.RIGHT);

		final Text  tPorcentajeGananciaPrecioLista = new Text(tbExistenciasPorProductos, SWT.CENTER);
		tPorcentajeGananciaPrecioLista.setTextLimit(3);

		final Text  tSubtotal = new Text(tbExistenciasPorProductos, SWT.RIGHT);
		tSubtotal.setEditable(false);
		tSubtotal.setEnabled(false);

		Button bAgregarFoto = new Button(tbExistenciasPorProductos, SWT.PUSH);


		Button bEliminarFila = new Button(tbExistenciasPorProductos, SWT.PUSH); 

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

						if (esMismaTallayColor(tTalla.getText(), tColor.getText())) {

							ConfigurarText.mensajeError(shellDialogo, "La talla y el color del producto ya ha sido digitado ");
							e.doit = false; 

						} else   

							item.setText(1, tColor.getText());


				}
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
							tPorcentajeGananciaPrecioMinimo.setText(String.valueOf(porcentajePrecioMinimo));
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
							tPorcentajeGananciaPrecioLista.setText(String.valueOf(porcentajePrecioLista));
							item.setText(6, tPrecioLista.getText());


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
						tTotal.setText(String.valueOf(calcularTotal()));


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
	//*********************************************** Metodo esMismaTallayColor() ****************************************
	
	private boolean esMismaTallayColor(String talla,String color) {
		
		boolean resultadoBoolean = false;
		
		TableItem items[] = tbExistenciasPorProductos.getItems();
		
		int i = 0;
		
		while (!resultadoBoolean && i < (items.length - 1)) {
			
			
			String tallaTabla = items[i].getText(0);
			String colorTabla = items[i].getText(1);
			
			if(tallaTabla.equals(talla) && colorTabla.equals(color))
				
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
			
			resultadoFloat += new Float(items[i].getText(2));
		}	

		return resultadoFloat;

	}


	//*********************************************** Metodo calcularTotal() ****************************************

	private float calcularTotal() {

		float resultadoFloat = 0;

		TableItem items[] = tbExistenciasPorProductos.getItems();


		for (int i = 0; i < items.length; i++)

			resultadoFloat += new Float(items[i].getText(8)); 

		return resultadoFloat;

	}

	//********************************** Interfaz Grafica**************************************************

	private void initialize() {

		
		shellDialogo = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		shellDialogo.setSize(new Point(750, 340));
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

		agregarConfiguraciones();

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
		grupoExitenciasPorProducto.setBounds(new Rectangle(3, 15, 734, 256));
		lCantidad = new Label(grupoExitenciasPorProducto, SWT.NONE);
		lCantidad.setBounds(new Rectangle(84, 226, 90, 15));
		lCantidad.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		lCantidad.setText("Cantidad Total:");
		tCantidadTotal = new Text(grupoExitenciasPorProducto, SWT.BORDER);
		tCantidadTotal.setBounds(new Rectangle(177, 220, 64, 26));
		tCantidadTotal.setEnabled(false);
		tCantidadTotal.setEditable(false);
		tbExistenciasPorProductos = new Table(grupoExitenciasPorProducto, SWT.NONE);
		tbExistenciasPorProductos.setHeaderVisible(true);
		tbExistenciasPorProductos.setLinesVisible(true);
		tbExistenciasPorProductos.setBounds(new Rectangle(12, 19, 713, 193));
		tTotal = new Text(grupoExitenciasPorProducto, SWT.BORDER);
		tTotal.setBounds(new Rectangle(552, 220, 68, 26));
		tTotal.setEditable(false);
		tTotal.setEnabled(false);
		lTotal = new Label(grupoExitenciasPorProducto, SWT.NONE);
		lTotal.setBounds(new Rectangle(507, 226, 39, 15));
		lTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		lTotal.setText("Total:");
	}

	



}    
