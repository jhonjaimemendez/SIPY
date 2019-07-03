package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;

import com.JASoft.GUI.SIPY.GUICategoria.GUIDialogoCategorias;
import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.ResultSetSerializable;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class GUIListaProducto extends ConfigurarText implements SelectionListener{

	private Shell sShell = null;
	private Group grupoTablaLista = null;
	private Table tbListaProductos = null;
	private ConectarMySQL conectarMySQL= null;
	private ToolBar toolBar;
	
    
    /*
	 * Contructor General
	 */
	public GUIListaProducto(ConectarMySQL conectarMySQL,Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		centrarShell(sShell);

		agregarConfiguraciones();

		agregarEventos();

		consultar();

		sShell.open();


	}

	
	/*
	 * Metodo consultar: Se consulta los productos
	 */
	public void consultar(){

		try {

			String sentenciaSQL = "Select P.idProducto,P.descripcion,o.CantidadComprada, ifnull(v.cantidadVendida,0) CantidadVendida,p.Stock,c.categoria " 
			                + "From ((Productos P join Categorias C on C.idCategoria=P.idCategoria) join (Select idProducto,Sum(cantidad) CantidadComprada "
					         + "From ComprasDetalle Group by idproducto) O on o.idProducto = P.idProducto)  Left join (Select idProducto,Sum(Cantidad) cantidadVendida from VentasDetalle Group by idProducto) V on v.idProducto = p.idProducto "
					         + "Where P.IdProducto <> '9999'"
                  	       +"Order by 2";

			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);


			final ResultSetSerializable resultSetSerializable = new ResultSetSerializable(consultar.executeQuery());

			//Se configuran el numero filas de los datos
			tbListaProductos.setItemCount(resultSetSerializable.getFilas());

			//Se agregan los datos
			tbListaProductos.addListener(SWT.SetData, new Listener() {
				public void handleEvent(Event event) {

					TableItem item = (TableItem)event.item;
					int index = event.index;
					Vector datosFila = (Vector) resultSetSerializable.getDatosColumnas().elementAt(index);
					//Se extrean los datos de las filas
					for (int i = 0 ; i < resultSetSerializable.getColumnas(); i++ ) {

						item.setText(i, datosFila.elementAt(i).toString());
						
					}
			
				}
			});


		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}

	}

	/*
	 * Metodo agregarConfiguraciones: agregar configuraciones para la GUI
	 */
	public void agregarConfiguraciones(){


		//Se configuran las columnas
		TableColumn clCodigoProducto = new TableColumn(tbListaProductos, SWT.CENTER);
		TableColumn clDescripcion = new TableColumn(tbListaProductos, SWT.LEFT);
		TableColumn clComprada = new TableColumn(tbListaProductos, SWT.CENTER);
		TableColumn clCantidadVendida = new TableColumn(tbListaProductos, SWT.CENTER);
		TableColumn clStock = new TableColumn(tbListaProductos, SWT.CENTER);
		TableColumn clCategoria = new TableColumn(tbListaProductos, SWT.LEFT);
		
		sShell.setImage(new Image(Display.getCurrent(), "imagenes/ListadoProductos.gif"));

		//Se configuran los nombres de columnas
		clCodigoProducto.setText("Código");
		clDescripcion.setText("Descripción");
		clStock.setText("Stock");
		clCantidadVendida.setText("Vendidos");
		clComprada.setText("Comprados");
		clCategoria.setText("Categoria");
		
	
		//Se configuran los tamaños  de columnas
		clCodigoProducto.setWidth(80);
		clDescripcion.setWidth(268);
		clStock.setWidth(47);
		clCategoria.setWidth(181);
		clCantidadVendida.setWidth(83);
		clComprada.setWidth(79);
		
	}		
	
	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbSalir().addSelectionListener(this);
	
		tbListaProductos.addListener(SWT.MouseDoubleClick, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub

				Rectangle clientArea = tbListaProductos.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = tbListaProductos.getTopIndex();
				while (index < tbListaProductos.getItemCount()) {
					boolean visible = false;
					TableItem item = tbListaProductos.getItem(index);
					for (int i = 0; i < tbListaProductos.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {

							GUIProducto guiProducto = new GUIProducto(conectarMySQL, sShell,item.getText(0));

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


	//**************************** Eventos de accion *******************************************************

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void widgetSelected(SelectionEvent a) {

		Object fuente = a.getSource();
		
		if(fuente == toolBar.getbSalir())

			sShell.close();


	}


	/****************************************************************************************************************
	 * *********************************** Interfaz Grafica *********************************************************
	 * **************************************************************************************************************
	 */
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Listado de Productos");
		createGrupoTablaLista();
		sShell.setLayout(null);
		sShell.setBounds(new Rectangle(25, 25, 800, 600));
	}

	/**
	 * This method initializes grupoTablaLista	
	 *
	 */
	private void createGrupoTablaLista() {
		grupoTablaLista = new Group(sShell, SWT.NONE);
		grupoTablaLista.setLayout(null);
		grupoTablaLista.setBounds(new Rectangle(12, 66, 758, 483));
		tbListaProductos = new Table(grupoTablaLista, SWT.VIRTUAL |	 SWT.FULL_SELECTION);
		tbListaProductos.setHeaderVisible(true);
		tbListaProductos.setLinesVisible(true);
		tbListaProductos.setBounds(new Rectangle(10, 21, 741, 444));
	}

}
