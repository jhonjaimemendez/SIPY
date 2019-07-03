package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.ObtenerFechaServidor;
import com.JASoft.componentesAccesoDatos.ResultSetSerializable;
import com.JASoft.componentesGraficos.Calendario;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;

public class GUIConsultaVentas extends ConfigurarText implements SelectionListener {

	private Shell sShell = null;
	private Group grupoVentasEncabezado = null;
	private Group grupoVentasDetalle = null;
	private Table tbComprasEncabezado = null;
	private Group grupoCriterioBusqueda = null;
	private Combo cCriterio = null;
	private Label lFechaInicial = null;
	private Label lFechaFinal = null;
	private Text tFechaInicial = null;
	private Text tFechaFinal = null;
	private Button bCalendario = null;
	private Button bCalendario1 = null;
	private Table tbDetalle = null;
	private Label lNumeroVentas = null;
	private Text tNumeroventa = null;
	private static PreparedStatement  consultarEncabezado;  //  @jve:decl-index=0:
	private static PreparedStatement  consultarDetalles;  //  @jve:decl-index=0:
	private ConectarMySQL conectarMySQL = null;
	private ToolBar toolBar = null;
	private String fechaInicial = null;  //  @jve:decl-index=0:
	private String fechaFinal = null;  //  @jve:decl-index=0:
	private Calendar calendar;
	
	/*
	 * Constructor general
	 */
	public GUIConsultaVentas(ConectarMySQL conectarMySQL, Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);
		
		centrarShell(sShell);

		cCriterio.forceFocus(); // Se asigna el foco al primer componente navegable

		agregarEventos();
		
		agregarConfiguraciones();
		
		configurarCriteriosBusqueda();
		
		consultarEncabezados();

        sShell.open();

	}
	
	
	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	private void limpiar() {

		cCriterio.select(1);
		tbDetalle.removeAll();
		
	}
	
	/*
	 * Metodo eliminar: Se consulta un cliente
	 */
	private void consultarEncabezados() {

		try {

			String sentenciaSQL = "Select V.NumeroVenta,Case V.TipoID  When 'C' Then 'Cedula' When 'T' Then 'Tarjeta'When 'E' Then 'Extranjeria' End Tipo, "
							+ "V.idCliente identificacion,Concat_WS(' ',C.Nombres,Apellidos) Nombres, DATE_FORMAT(fecha, '%Y-%c-%d') Fecha,Total "
                            +"From VentasEncabezado V, Clientes C "
                            + "Where v.idCliente = C.idCliente and V.Tipoid = C.TipoID ";
                            
                            
            if (cCriterio.getSelectionIndex() == 2) {
            	
            	sentenciaSQL += " and fecha >= ? and fecha <= ?";
            	
            }
                            
                            
                            
			sentenciaSQL += "Order by 1";

			if (consultarEncabezado == null)

				consultarEncabezado = conectarMySQL.getConexion().prepareStatement(
						sentenciaSQL);

			
			final ResultSetSerializable resultSetSerializable = new ResultSetSerializable(consultarEncabezado.executeQuery());
			
			//Se configuran el numero filas de los datos
			tbComprasEncabezado.setItemCount(resultSetSerializable.getFilas());
			
			tNumeroventa.setText(String.valueOf(resultSetSerializable.getFilas()));
		
			//Se agregan los datos
			tbComprasEncabezado.addListener(SWT.SetData, new Listener() {
				
				public void handleEvent(Event event) {

					TableItem item = (TableItem)event.item;
					int index = event.index;
					Vector datosFila = (Vector) resultSetSerializable.getDatosColumnas().elementAt(index);
					
					//Se extrean los datos de las filas
					for (int i = 0 ; i < resultSetSerializable.getColumnas(); i++ ) {

						if (i== 5)

							item.setText(i, getFormatoMoneda(datosFila.elementAt(i).toString()));

						else	

							item.setText(i, datosFila.elementAt(i).toString());

					}

						
				}
			});

	
			consultarDetalles(tbComprasEncabezado.getItem(0).getText(0)); //Se consulta la primera fila
			tbComprasEncabezado.select(0);
			
		} catch (SQLException e) {

			mensajeError(
					sShell,
					"Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas"
					+ e);
		}

	}

	
	/*
	 * Metodo consultar: Se consulta Detalles de una factura
	 */
	private boolean consultarDetalles(String numeroVenta) {
		
		boolean resultadoBoolean = false;

		try {

			String sentenciaSQL = "Select V.IdProducto,P.Descripcion,V.Item,e.Talla,m.marca,c.color,V.Cantidad,V.Precio,V.Subtotal "
							     + "From VentasDetalle V,Productos P,Existenciasporproductos e,Colores C,Marcas M "
								+ "Where V.Numeroventa = ? and V.idproducto = P.idproducto and v.idProducto = e.idproducto and v.item = e.item "
								+ "  and e.color = c.idColor and e.idmarca = m.idmarca";

			if (consultarDetalles == null)
				
				consultarDetalles = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);
			
			consultarDetalles.setString(1, numeroVenta);
			
			ResultSet resultado = consultarDetalles.executeQuery();
			
			 while (resultado.next()) {

				 TableItem item = new TableItem(tbDetalle, SWT.NONE);
		
				 item.setText(new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6),resultado.getString(7),ConfigurarText.getFormatoMoneda(resultado.getString(8)),ConfigurarText.getFormatoMoneda(resultado.getString(9))});


			 }
			
			 
		} catch (SQLException s) {
			
			s.printStackTrace();
		}
		
		return resultadoBoolean;
			
		
	}	
	
	/*
	 * Metodo agregarConfiguraciones: agregar configuraciones para la GUI
	 */
	public void agregarConfiguraciones(){


		//Se configuran las columnas
		TableColumn clNumeroventa = new TableColumn(tbComprasEncabezado, SWT.CENTER);
		TableColumn clTipo = new TableColumn(tbComprasEncabezado, SWT.CENTER);
		TableColumn clIdentificacion = new TableColumn(tbComprasEncabezado, SWT.CENTER);
		TableColumn clNombres = new TableColumn(tbComprasEncabezado, SWT.CENTER);
		TableColumn clfecha = new TableColumn(tbComprasEncabezado, SWT.CENTER);
		TableColumn clTotal = new TableColumn(tbComprasEncabezado, SWT.CENTER);
		
		sShell.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));

		//Se configuran los nombres de columnas
		clNumeroventa.setText("Número venta");
		clTipo.setText("Tipo Id.");
		clIdentificacion.setText("Identificación");
		clNombres.setText("Nombres");
		clfecha.setText("Fecha");
		clTotal.setText("Total");
		
		
		
		//Se configuran los tamaños  de columnas
		clNumeroventa.setWidth(93);
		clTipo.setWidth(80);
		clIdentificacion.setWidth(118);
	    clNombres.setWidth(278);
		clfecha.setWidth(74);
		clTotal.setWidth(83);
		
		
		//Se configuran las columnas
		TableColumn clCodigoProducto = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clNombreProducto = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clCodigoExistencia = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clTalla = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clMarca = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clColor = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clCantidad  = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clPrecioVenta = new TableColumn(tbDetalle, SWT.CENTER);
		TableColumn clSubtotal = new TableColumn(tbDetalle, SWT.CENTER);
		
		//Se configuran los nombres de columnas
		clCodigoProducto.setText("Código");
		clNombreProducto.setText("Descripción");
		clCodigoExistencia.setText("Existencia");
		clTalla.setText("Talla");
		clColor.setText("Color");
		clMarca.setText("Marca");
		clCantidad.setText("Cantidad");
		clPrecioVenta.setText("Precio");
		clSubtotal.setText("Subtotal");
		
		
		
		//Se configuran los tamaños  de columnas
		clCodigoProducto.setWidth(55);
		clNombreProducto.setWidth(164);
		clCodigoExistencia.setWidth(64);
		clTalla.setWidth(39);
		clColor.setWidth(124);
		clMarca.setWidth(114);
		clCantidad.setWidth(61);
		clPrecioVenta.setWidth(62);
		clSubtotal.setWidth(70);
		
		tNumeroventa.setForeground(getColorRojo());
		
		cCriterio.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				
				configurarCriteriosBusqueda();
			}
			
		});
		
		
		
	}		
	
	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbSalir().addSelectionListener(this);
		toolBar.getbLimpiar().addSelectionListener(this);
		
		tbComprasEncabezado.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event event) {
	        	
	        	TableItem item = tbComprasEncabezado.getItem(tbComprasEncabezado.getSelectionIndex());
	        	
	        	tbDetalle.removeAll(); //se remueve las filas de las tablas del detalle
	        	
	        	consultarDetalles(item.getText(0));
	        
	        }
	      });
		
		bCalendario.addSelectionListener(this);
		bCalendario1.addSelectionListener(this);

	    
		
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
		
		else
			
			if(fuente == toolBar.getbLimpiar())
				
				limpiar();
		
			else
				if (fuente == bCalendario) {
					
					Calendario calendario = new Calendario(sShell,tFechaInicial);
					
				}else
					
					if (fuente == bCalendario1) {
						
						Calendario calendario = new Calendario(sShell,tFechaFinal);
						
					}


	}
	
	/*
	 * Merodo configurarCriteriosBusqueda: Configura la interfaz dependiendo de criterios de busqueda
	 */
	private void configurarCriteriosBusqueda() {

		if (cCriterio.getSelectionIndex() == 2) {

			cCriterio.setLocation(121, 24);

			if (fechaInicial == null) { // se le pregunta a la BD la fecha

				try {

					fechaFinal = ObtenerFechaServidor.getObtenerFecha(conectarMySQL);

					calendar = Calendar.getInstance();

					calendar.setTime(getFormatoFecha().parse(fechaFinal));

					calendar.add(Calendar.DAY_OF_MONTH, -15);

					fechaInicial = getFormatoFecha().format(calendar.getTime());


				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			tFechaInicial.setText(fechaFinal);
			tFechaFinal.setText(fechaInicial);
			ocultarWidget(true);

		} else {

			cCriterio.setLocation(121, 40);

			ocultarWidget(false);
		}


	}
	
	private void ocultarWidget(boolean bandera) {
		
		
		lFechaFinal.setVisible(bandera);
		lFechaInicial.setVisible(bandera);
		tFechaFinal.setVisible(bandera);
		tFechaInicial.setVisible(bandera);
		bCalendario.setVisible(bandera);
		bCalendario1.setVisible(bandera);
		
	}
	
	/**************************************************************************************************************
	 * *********************************** Interfaz ****************************************************************
	 * *************************************************************************************************************
	 */
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Consulta Ventas");
		createGrupoVentasEncabezado();
		sShell.setLayout(null);
		createGrupoVentasDetalle();
		createGrupoCriterioBusqueda();
		sShell.setBounds(new Rectangle(184, 184, 800, 600));
	}

	/**
	 * This method initializes grupoVentasEncabezado	
	 *
	 */
	private void createGrupoVentasEncabezado() {
		grupoVentasEncabezado = new Group(sShell, SWT.NONE);
		grupoVentasEncabezado.setLayout(null);
		grupoVentasEncabezado.setText("Encabezado Ventas");
		grupoVentasEncabezado.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoVentasEncabezado.setBounds(new Rectangle(11, 151, 768, 196));
		tbComprasEncabezado = new Table(grupoVentasEncabezado, SWT.VIRTUAL | SWT.FULL_SELECTION);
		tbComprasEncabezado.setHeaderVisible(true);
		tbComprasEncabezado.setLinesVisible(true);
		tbComprasEncabezado.setBounds(new Rectangle(11, 21, 743, 140));
		lNumeroVentas = new Label(grupoVentasEncabezado, SWT.NONE);
		lNumeroVentas.setBounds(new Rectangle(603, 171, 91, 15));
		lNumeroVentas.setText("Número Ventas:");
		tNumeroventa = new Text(grupoVentasEncabezado, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tNumeroventa.setBounds(new Rectangle(698, 166, 54, 24));
	}

	/**
	 * This method initializes grupoVentasDetalle	
	 *
	 */
	private void createGrupoVentasDetalle() {
		grupoVentasDetalle = new Group(sShell, SWT.NONE);
		grupoVentasDetalle.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoVentasDetalle.setLayout(null);
		grupoVentasDetalle.setText("Detalle Ventas");
		grupoVentasDetalle.setBounds(new Rectangle(11, 359, 770, 189));
		tbDetalle = new Table(grupoVentasDetalle, SWT.VIRTUAL | SWT.FULL_SELECTION);
		tbDetalle.setHeaderVisible(true);
		tbDetalle.setLinesVisible(true);
		tbDetalle.setBounds(new Rectangle(10, 22, 755, 156));
	}

	/**
	 * This method initializes grupoCriterioBusqueda	
	 *
	 */
	private void createGrupoCriterioBusqueda() {
		grupoCriterioBusqueda = new Group(sShell, SWT.NONE);
		grupoCriterioBusqueda.setLayout(null);
		grupoCriterioBusqueda.setText("Criterios Busqueda");
		grupoCriterioBusqueda.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		createCCriterio();
		grupoCriterioBusqueda.setBounds(new Rectangle(198, 57, 416, 95));
		lFechaInicial = new Label(grupoCriterioBusqueda, SWT.NONE );
		lFechaInicial.setBounds(new Rectangle(10, 64, 73, 15));
		lFechaInicial.setText("Fecha Inicial:");
		lFechaFinal = new Label(grupoCriterioBusqueda, SWT.NONE);
		lFechaFinal.setBounds(new Rectangle(219, 64, 63, 15));
		lFechaFinal.setText("Fecha Final:");
		tFechaInicial = new Text(grupoCriterioBusqueda, SWT.BORDER | SWT.CENTER);
		tFechaInicial.setBounds(new Rectangle(82, 60, 96, 23));
		tFechaFinal = new Text(grupoCriterioBusqueda, SWT.BORDER | SWT.CENTER);
		tFechaFinal.setBounds(new Rectangle(284, 60, 96, 23));
		bCalendario = new Button(grupoCriterioBusqueda, SWT.NONE);
		bCalendario.setBounds(new Rectangle(180, 60, 28, 23));
		bCalendario.setImage(new Image(Display.getCurrent(), "imagenes/Calendario.gif"));
		bCalendario1 = new Button(grupoCriterioBusqueda, SWT.NONE);
		bCalendario1.setBounds(new Rectangle(381, 59, 28, 23));
		bCalendario1.setImage(new Image(Display.getCurrent(), "imagenes/Calendario.gif"));
	}

	/**
	 * This method initializes cCriterio	
	 *
	 */
	private void createCCriterio() {
		cCriterio = new Combo(grupoCriterioBusqueda, SWT.READ_ONLY);
		cCriterio.setBounds(new Rectangle(121, 24, 158, 23));
		cCriterio.add("Primeras 10 Ventas");
		cCriterio.add("Ultimas 10 Ventas");
		cCriterio.add("Rango de Fechas");
		cCriterio.add("Por Clientes");
		cCriterio.add("Todas");
		
		cCriterio.select(1);
	}

}
