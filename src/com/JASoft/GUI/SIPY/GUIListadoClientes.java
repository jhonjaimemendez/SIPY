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
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.ResultSetSerializable;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class GUIListadoClientes extends ConfigurarText implements SelectionListener{

	private Shell sShell = null;
	private Group grupoListadoClientes = null;
	private Table tbListadoClientes = null;
	private Label lNumeroClientes = null;
	private Text tNumeroCliente = null;
	private ConectarMySQL conectarMySQL= null;
	private ToolBar toolBar;
	private Text tTotalSaldo;
	private Label lTotalSaldo;
	
	
	 /*
	 * Contructor General
	 */
	public GUIListadoClientes(ConectarMySQL conectarMySQL,Shell shellPadre) {

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
	private void consultar(){

		try {

			String sentenciaSQL =  "Select Case TipoID  When 'C' Then 'Cedula' When 'T' Then 'Tarjeta'When 'E' Then 'Extranjeria' End Tipo,idCliente Identificacion, Nombres,Apellidos,NumeroCelular,TelefonoResidencia,Saldo " +
                                   "From Clientes "
   	                               +"Order by 3";

			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);


			final ResultSetSerializable resultSetSerializable = new ResultSetSerializable(consultar.executeQuery());

			//Se configuran el numero filas de los datos
			tbListadoClientes.setItemCount(resultSetSerializable.getFilas());
			
			tNumeroCliente.setText(String.valueOf(resultSetSerializable.getFilas()));

			//Se agregan los datos
			tbListadoClientes.addListener(SWT.SetData, new Listener() {
				
				public void handleEvent(Event event) {

					TableItem item = (TableItem)event.item;
					int index = event.index;
					Vector datosFila = (Vector) resultSetSerializable.getDatosColumnas().elementAt(index);

					//Se extrean los datos de las filas
					for (int i = 0 ; i < resultSetSerializable.getColumnas(); i++ ) {

						if (i == 6) 

							item.setText(i, getFormatoMoneda(datosFila.elementAt(i).toString()));

						else
							item.setText(i, datosFila.elementAt(i).toString());

					}
					
					
				}
			});

		  
			//Se busca el total en saldos
		    sentenciaSQL =  "Select sum(saldo) From Clientes";

            consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);
           
            ResultSet resultado = consultar.executeQuery();
            
            if (resultado.next())
            	
            	tTotalSaldo.setText(getFormatoMoneda(resultado.getString(1)));
            

			
		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}

	}


	/*
	 * Metodo agregarConfiguraciones: agregar configuraciones para la GUI
	 */
	public void agregarConfiguraciones(){


		//Se configuran las columnas
		TableColumn clTipoIdentificacion = new TableColumn(tbListadoClientes, SWT.CENTER);
		TableColumn clIdentificacion = new TableColumn(tbListadoClientes, SWT.LEFT);
		TableColumn clNombre = new TableColumn(tbListadoClientes, SWT.CENTER);
		TableColumn clApellido = new TableColumn(tbListadoClientes, SWT.CENTER);
		TableColumn clCelular = new TableColumn(tbListadoClientes, SWT.CENTER);
		TableColumn clTelefono = new TableColumn(tbListadoClientes, SWT.CENTER);
		TableColumn clSaldo = new TableColumn(tbListadoClientes, SWT.CENTER);
		
		sShell.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));

		//Se configuran los nombres de columnas
		clTipoIdentificacion.setText("Tipo");
		clIdentificacion.setText("Identificación");
		clNombre.setText("Nombres");
		clApellido.setText("Apellidos");
		clSaldo.setText("Saldo");
		clCelular.setText("Celular");
		clTelefono.setText("Tel. Residencia");
		
		

		//Se configuran los tamaños  de columnas
		clTipoIdentificacion.setWidth(80);
		clIdentificacion.setWidth(90);
		clNombre.setWidth(148);
		clApellido.setWidth(148);
		clSaldo.setWidth(81);
		clCelular.setWidth(93);
		clTelefono.setWidth(90);
		
		
	}		

	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbSalir().addSelectionListener(this);
	
		tbListadoClientes.addListener(SWT.MouseDoubleClick, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub

				Rectangle clientArea = tbListadoClientes.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = tbListadoClientes.getTopIndex();
				while (index < tbListadoClientes.getItemCount()) {
					boolean visible = false;
					TableItem item = tbListadoClientes.getItem(index);
					for (int i = 0; i < tbListadoClientes.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {

							GUICliente guiCliente = new GUICliente(conectarMySQL, sShell,getTipoDocumento(item.getText(0)) , item.getText(1));

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
	 * Metodo getTipoDocumento: Devuelve el caracter del tipo de documento como
	 * se especifico en el tabla
	 */
	private int getTipoDocumento(String tipoDocumento) {

		int resultadoInt = tipoDocumento.equals("Cedula") ? 0 : tipoDocumento.equals("Tarjeta") ? 1 : tipoDocumento.equals("Pasaporte") ? 2 : 3;
		
		return resultadoInt;

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
		sShell.setText("Listado de Clientes");
		createGrupoListadoClientes();
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(null);
	}

	/**
	 * This method initializes grupoListadoClientes	
	 *
	 */
	private void createGrupoListadoClientes() {
		grupoListadoClientes = new Group(sShell, SWT.NONE);
		grupoListadoClientes.setLayout(null);
		grupoListadoClientes.setBounds(new Rectangle(9, 63, 768, 473));
		tbListadoClientes = new Table(grupoListadoClientes, SWT.VIRTUAL | SWT.FULL_SELECTION);
		tbListadoClientes.setHeaderVisible(true);
		tbListadoClientes.setLinesVisible(true);
		tbListadoClientes.setBounds(new Rectangle(9, 20, 747, 369));
		lNumeroClientes = new Label(grupoListadoClientes, SWT.NONE);
		lNumeroClientes.setBounds(new Rectangle(549, 409, 120, 15));
		lNumeroClientes.setText("Número de  Clientes:");
		tNumeroCliente = new Text(grupoListadoClientes, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tNumeroCliente.setBounds(new Rectangle(676, 405, 77, 23));
		tNumeroCliente.setForeground(getColorRojo());
		tTotalSaldo = new Text(grupoListadoClientes, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalSaldo.setBounds(new Rectangle(676, 438, 77, 23));
		tTotalSaldo.setForeground(getColorRojo());
		lTotalSaldo = new Label(grupoListadoClientes, SWT.NONE);
		lTotalSaldo.setBounds(new Rectangle(589, 446, 80, 15));
		lTotalSaldo.setText("Total Saldos :");	}

}
