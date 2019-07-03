package com.JASoft.GUI.SIPY;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesGraficos.Calendario;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;

public class GUIEstadisticas extends ConfigurarText implements SelectionListener {

	private Shell sShell = null;
	private TabFolder pestañasEstadistica = null;
	private ConectarMySQL conectarMySQL = null;
	private ToolBar toolBar = null;
	private Group grupoProductos = null;
	private Label lCantidad = null;
	private Text tInversion = null;
	private Combo cInversion = null;
	private Label lDescripcion = null;
	private Group grupoCantidad = null;
	private Label label = null;
	private Text tStockComprado = null;
	private Label lStockvendido = null;
	private Text tStockVendido = null;
	private Text tStockActual = null;
	private Label lStockActual = null;
	private Button bVerStock = null;
	private Group grupoImporte = null;
	private Label lImporteComprado = null;
	private Text tImporteComprado = null;
	private Label lImportevendido = null;
	private Text tImporteVendido = null;
	private Text tImporteActual = null;
	private Label lImporteActual = null;
	private Button bVerVentas = null;
	private Group grupoGraficaCantidadvendidaPorMes = null;
	private Group grupoGraficavendidaPorMes1 = null;
	private Label lFecha = null;
	private Text tFecha = null;
	
	private Group grupoImporteEsperado = null;
	private Group grupoSaldosIniciales = null;
	private Label TotalSaldoIniciales = null;
	private Text tTotalSaldos = null;
	private Text tSaldosPagados = null;
	private Label lTotalSaldosCancelados = null;
	private Label lSaldospendientes = null;
	private Text tSaldoPendiente = null;
	private Button bVerListado = null;
	private Group grupoImporteEsperadoVentas = null;
	private Label lTotal = null;
	private Text tImporteTotalEsperado = null;
	private Group grupoPorcentajeVentas = null;
	private Label lTotal1 = null;
    private Label lImporteEsperado = null;
	private Text tImporteEsperado = null;
	private Label lImporteReal = null;
	private Text tImporteReal = null;
	private Group grupoCapital = null;
	private Label lCapitalTotal = null;
	private Text tTotalCapital = null;
	private Group grupoGananciaTotal = null;
	private Label lTotalGanancia = null;
	private Text tMaximaEsperada = null;
	private Label lTotalRecaudado = null;
	private Text tTotalRecaudado = null;
	private Label lMinEsperada = null;
	private Text tMinimaEsperada = null;
	private Label lRecaudada = null;
	private Text tGananciaRecaudada = null;
	private Label lTotalAlmacen;
	private Text tTotalAlmacen;
	private Group grupoSaldoTotalClientes;
	private Label lSaldoTotal;
	private Text tSaldoTotal;
	private Text tPorcentajeVendido;
	private Label lGananciaEsperada;
	private Text tGanaciaEsperada;
	private Label lGananciaReal;
	private Text tGanaciaReal;
	private Group grupoCategoriasMasRotadas;
	private Group grupoCategoriasConsultas;
	private Table tbVentasPorCategorias;
	private Label lCreditoUtilidad;
	private Text tCreditosutilidad;
	private Label lCreditos;
	private Text tTotalCreditos;

	
	
	
	/*
	 * Constructor general
	 */
	public GUIEstadisticas(ConectarMySQL conectarMySQL, Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);
		
		agregarEventos();
		
		centrarShell(sShell);
		
        generarGrafica();
		
		consultar();
		
		consultarEstadisticasResumen();
		
		
		

		sShell.open();

	}
	
	/*
	 * Metodo agregarConfiguraciones: agregar configuraciones en la GUI
	 */
	public void agregarConfiguraciones(){


		//Se configuran las columnas
		TableColumn clCodigoCategoria = new TableColumn(tbVentasPorCategorias, SWT.CENTER);
		TableColumn clCategoria = new TableColumn(tbVentasPorCategorias, SWT.LEFT);
		
	}		



	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbSalir().addSelectionListener(this);
		bVerStock.addSelectionListener(this);
		bVerVentas.addSelectionListener(this);
	
	}
	
	//**************************** Eventos de accion *******************************************************

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void widgetSelected(SelectionEvent a) {

		Object fuente = a.getSource();

		if (fuente == toolBar.getbSalir())

			sShell.close();

		else 
			if (fuente == bVerStock) {

			new GUIListaProducto(conectarMySQL, sShell);
			
		} else 
			if (fuente == bVerVentas) {

			new GUIConsultaVentas(conectarMySQL, sShell);
		}

	}
	
	/************************************************************************************************************
	 * ************************************** Interfaz Grafica *************************************************
	 */
	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Estadisticas");
		createPestañasEstadistica();
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(null);
	}

	/**
	 * This method initializes pestañasEstadistica	
	 *
	 */
	private void createPestañasEstadistica() {
		pestañasEstadistica = new TabFolder(sShell, SWT.None);
		pestañasEstadistica.setLayout(null);
		pestañasEstadistica.setBounds(new Rectangle(15, 55, 772, 517));
		
		TabItem tabResumen= new TabItem(pestañasEstadistica, SWT.NULL);
		tabResumen.setText("Resumen ");
		
		Composite compositeTabResumen = new Composite(pestañasEstadistica, SWT.NONE);
	    createGrupoResumen(compositeTabResumen);
	    tabResumen.setControl(compositeTabResumen);
		
		 
	    TabItem tabProductos = new TabItem(pestañasEstadistica, SWT.NULL);
	    tabProductos.setText("Productos");
	    
	    Composite compositeTabProductos = new Composite(pestañasEstadistica, SWT.NONE);
	    createGrupoProductos(compositeTabProductos);
	    tabProductos.setControl(compositeTabProductos);
		
	    TabItem tabCategorias = new TabItem(pestañasEstadistica, SWT.NULL);
	    tabCategorias.setText("Categorias");
	    
	    Composite compositeCategorias = new Composite(pestañasEstadistica, SWT.NONE);
	    createGrupoCategoriasMasRotadas(compositeCategorias);
	    tabCategorias.setControl(compositeCategorias);
		


	}
	
	/*
	 * Metodo consultarEstadisticasResumen: Se consultan las estadisticas para la pestaña resumen
	 */

	private void consultarEstadisticasResumen() {

		try {

			StringBuffer sentenciaSQL = new StringBuffer(
					"select 1,Sum(subtotal),2,3,4,5 From VentasDetalle where idProducto = '9999' "
							+ "Union "
							+ "select (select Sum(subtotal) From VentasDetalle where idProducto = '9999') - sum(if (a.valor > b.valor, b.valor,a.valor)), sum(if (a.valor > b.valor, b.valor,a.valor)),1,2,3,4 "
							+ "From (Select V.TipoId tipo,V.IdCliente idCliente,VD.Subtotal valor "
							+ "From VentasEncabezado V,VentasDetalle VD "
							+ "Where V.NumeroVenta = VD.NumeroVenta and VD.idProducto = '9999') A,"
							+ "(Select TipoId,IdCliente,sum(valor) valor  "
							+ "From AbonosClientes "
							+ "Group by TipoId,IdCliente) B "
							+ "Where A.tipo = B.tipoid and a.idcliente = b.idCliente "
							+ " union "
							+ "Select a.vendido,(a.cantidad/b.cantidad) * 100 ,1,2,3,4  "
							+ " From (Select sum(cantidad) cantidad ,sum(subtotal) vendido from VentasDetalle Where idproducto <> '9999') a, "
							+ " (Select sum(cantidad) cantidad from comprasdetalle) b"
							+ " union "
							+ " Select (Select Sum(cantidad*preciolista) From ExistenciasPorProductos ) + Sum(V.cantidad*E.PrecioLista),Sum(V.cantidad*E.PrecioLista),Sum(e.costoproducto * V.cantidad),(Select Sum(cantidad*precioMinimo) From ExistenciasPorProductos ) + Sum(V.cantidad*E.PrecioMinimo),Sum(subtotal) - Sum(v.cantidad * e.costoProducto),Sum(v.cantidad * e.precioLista) - Sum(v.cantidad * e.costoProducto) "
							+ "From ventasDetalle V, ExistenciasPorProductos E "
							+ "Where V.idproducto = e.idProducto and V.item = e.item and V.idProducto <> '9999' "
							+ "union "
							+ "Select 1,Sum(Costoproducto * cantidad),2,3,4,5  "
                            + "From ExistenciasPorproductos "
                            + "union "
							+ "Select 1,Sum(saldo),2,3,4,5  "
                            + "From Clientes "
                            +"union "
                            + "Select Sum(if(b.total > a.saldoInicial, b.total*(1 - (a.costo/a.total)) ,0)) Ganancia,Sum(if(b.total > a.saldoInicial, a.saldoInicial + (b.total * a.costo)/a.total,b.total)) Capital,1,2,3,4 "
                            + "From  (Select V.TipoID tipoID,V.idCliente idCliente,sum(if(VD.idProducto <> '9999',VD.subtotal,0)) Total,Sum(VD.cantidad * costoProducto) costo,sum(if(VD.idProducto = '9999',VD.subtotal,0)) SaldoInicial "
                            + "From ventasEncabezado V, VentasDetalle VD,ExistenciasPorProductos E "
                            + "Where V.numeroVenta = VD.Numeroventa and vD.Idproducto = E.idproducto and VD.item = e.item "
                            + "Group by TipoID,idCliente) a, " 
                            + "(Select TipoID,idCliente,sum(Valor) total "
                            + "From AbonosClientes "
                            + "Group by TipoID,idCliente) b "
                            + "Where a.tipoID = b.tipoid and a.idCliente = b.idCliente "
                            +"union "
                            + "Select  Sum(a.totalVendido - a.costo - if( saldoInicial > ifNull(b.totalAbono,0),0,  ifNull(b.totalAbono,0) * a.costo /a.totalVendido)) Ganancia, Sum(a.costo - if( saldoInicial > ifNull(b.totalAbono,0),0,  ifNull(b.totalAbono,0) * a.costo /a.totalVendido)) Capital,1,2,3,4 "
                            + "From  (Select V.TipoID tipoID,V.idCliente idCliente,sum(if(VD.idProducto <> '9999',VD.subtotal,0)) TotalVendido,Sum(VD.cantidad * costoProducto) costo,sum(if(VD.idProducto = '9999',VD.subtotal,0)) SaldoInicial "
                            + "From ventasEncabezado V, VentasDetalle VD,ExistenciasPorProductos E "
                            + "Where V.numeroVenta = VD.Numeroventa and vD.Idproducto = E.idproducto and VD.item = e.item "
                            + "Group by TipoID,idCliente) a left outer join " 
                            + "(Select TipoID,idCliente,sum(Valor) totalAbono "
                            + "From AbonosClientes "
                            + "Group by TipoID,idCliente) b on a.tipoID = b.tipoid and a.idCliente = b.idCliente "); 
                            
                            

		

			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL.toString());

			ResultSet resultado = consultar.executeQuery();

			if (resultado.next()) {

				tTotalSaldos.setText(getFormatoMoneda(resultado.getInt(2)));
				
				resultado.next();

				tSaldoPendiente.setText(getFormatoMoneda(resultado.getInt(1)));
				tSaldosPagados.setText(getFormatoMoneda(resultado.getInt(2)));
				
				resultado.next();
				
				tImporteReal.setText(getFormatoMoneda(resultado.getInt(1)));
		        tPorcentajeVendido.setText(String.valueOf(redondearNumero(resultado.getFloat(2), 2)));
                
		        resultado.next();
                
                tImporteTotalEsperado.setText(getFormatoMoneda(resultado.getInt(1)));
                tImporteEsperado.setText(getFormatoMoneda(resultado.getInt(2)));
                tMinimaEsperada.setText(getFormatoMoneda(resultado.getInt(4) - getMonedaAEntero(tImporteComprado.getText())));
                tGanaciaReal.setText(getFormatoMoneda(resultado.getInt(5)));
                tGanaciaEsperada.setText(getFormatoMoneda(resultado.getInt(6)));
                
                resultado.next();
                
                tTotalAlmacen.setText(getFormatoMoneda(resultado.getInt(2)));
                
                resultado.next();
                
                tSaldoTotal.setText(getFormatoMoneda(resultado.getInt(2)));
                
                tMaximaEsperada.setText(getFormatoMoneda(getMonedaAEntero(tImporteTotalEsperado.getText()) - getMonedaAEntero(tImporteComprado.getText())));
                
                resultado.next();
                
                tGananciaRecaudada.setText(getFormatoMoneda(resultado.getInt(1)));
                tTotalRecaudado.setText(getFormatoMoneda(resultado.getInt(2)));
                
                resultado.next();
                tCreditosutilidad.setText(getFormatoMoneda(resultado.getInt(1)));
                tTotalCreditos.setText(getFormatoMoneda(resultado.getInt(2)));
                
                
                tTotalCapital.setText(getFormatoMoneda(getMonedaAEntero(tSaldoPendiente.getText()) + getMonedaAEntero(tTotalCreditos.getText())));
                
			}

		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}

	}
	
	
	/*
	 * Metodo consultar: Se consultan las estadisticas
	 */
	private void consultar(){

		try {

			StringBuffer sentenciaSQL = new StringBuffer("select Sum(Cantidad),sum(subtotal) "
					                                    + "From ComprasEncabezado C,ComprasDetalle CD "
					                                    +"Where c.NumeroCompra = Cd.NumeroCompra and C.Estado = 'G' "
					                                    +"Union "
					                                    +"select Sum(VD.Cantidad),sum(subtotal) "
					                                    +"From VentasEncabezado V,VentasDetalle VD "
					                                    +"Where V.NumeroVenta = Vd.NumeroVenta and V.Estado = 'G' and Vd.idProducto <> '9999'"
					                                    +"Union "
					                                    +"Select Sum(Cantidad),Sum(Cantidad*precioLista) "
					                                    +"From ExistenciasPorProductos ");
				




			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL.toString());

       	    ResultSet resultado = consultar.executeQuery();

       	    if (resultado.next()) {
       	    	
       	    	tStockComprado.setText(resultado.getString(1));
       	    	tImporteComprado.setText(getFormatoMoneda(resultado.getString(2)));
       	    	
       	    	resultado.next();
       	    	
       	    	tStockVendido.setText(resultado.getString(1));
       	    	tImporteVendido.setText(getFormatoMoneda(resultado.getString(2)));
       	    	
       	    	resultado.next();
       	    	
       	    	tStockActual.setText(resultado.getString(1));
       	    	tImporteActual.setText(getFormatoMoneda(resultado.getString(2)));
       		    	
       	    	
       	    }
		

		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}



	}

	/*
	 * Metodo generarGrafica: Se generan las graficas
	 */
	private void generarGrafica() {

		try {

			// Se configura la grafica de las caantidades de productos vendidas por mes
			Composite chartComposite = new Composite(
					grupoGraficaCantidadvendidaPorMes, SWT.NONE);
			chartComposite.setLayoutData(null);

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			
			// Se realiza la consulta que me traiga las cantidades vendidas por mes
			StringBuffer sentenciaSQL = new StringBuffer(
					"Select m.numeroMes,Sum(if(Extract(Month FROM V.Fecha) = m.numeroMes,Cantidad,0)),Sum(if(Extract(Month FROM V.Fecha) = m.numeroMes,V.Total,0)) "
							+ "From VentasEncabezado V, VentasDetalle VD, mesesAnos m "
							+ "Where v.NumeroVenta = Vd.Numeroventa and VD.idProducto <> '9999' "
							+ "Group by m.numeroMes");

			PreparedStatement consultar = conectarMySQL.getConexion()
					.prepareStatement(sentenciaSQL.toString());

			ResultSet resultado = consultar.executeQuery();

			//se recupera los valores y se agrega a la grafica
			while (resultado.next())

				dataset.setValue(resultado.getInt(2), "Mes", resultado.getString(1));

			//Se configura la grafica
			JFreeChart chart = ChartFactory.createBarChart("", "","Cantidad Vendida", dataset, 
					PlotOrientation.VERTICAL,false, true, false);

			CategoryPlot categoryPlot = chart.getCategoryPlot();
			categoryPlot.setRangeGridlinePaint(Color.red);

			ChartComposite frame = new ChartComposite(
					grupoGraficaCantidadvendidaPorMes, SWT.NONE, chart, true);
			frame.setBounds(5, 20, 490, 160);

			
			
			// Se configura la grafica de el importe vendido por mes
			chartComposite = new Composite(grupoGraficavendidaPorMes1, SWT.NONE);
			chartComposite.setLayoutData(null);

			dataset = new DefaultCategoryDataset();

			sentenciaSQL = new StringBuffer("Select m.numeroMes,Sum(if(Extract(Month FROM V.Fecha) = m.numeroMes,VD.SubTotal,0)) Total "
							+ "From VentasEncabezado V,VentasDetalle VD, mesesAnos m "
							+ "Where  V.NumeroVenta = VD.Numeroventa and VD.idProducto <> '9999' "
							+ "Group by m.numeroMes");

			consultar = conectarMySQL.getConexion().prepareStatement(
					sentenciaSQL.toString());

			resultado = consultar.executeQuery();

			while (resultado.next())

				dataset.setValue(resultado.getInt(2), "Mes", resultado
						.getString(1));

			chart = ChartFactory.createBarChart("", "", "Importe Vendido",
					dataset, PlotOrientation.VERTICAL, false, true, false);

			categoryPlot = chart.getCategoryPlot();
			categoryPlot.setRangeGridlinePaint(Color.red);
			frame = new ChartComposite(grupoGraficavendidaPorMes1, SWT.NONE,
					chart, true);
			frame.setBounds(5, 20, 490, 160);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * This method initializes grupoProductos	
	 *
	 */
	private void createGrupoProductos(Composite composite) {
		grupoProductos = new Group(composite, SWT.NONE);
		grupoProductos.setLayout(null);
		grupoProductos.setBounds(new Rectangle(0, 0, 753, 490));
		lCantidad = new Label(grupoProductos, SWT.NONE);
		lCantidad.setBounds(new Rectangle(13, 19, 75, 15));
		lCantidad.setText("Inversión N°: ");
		tInversion = new Text(grupoProductos, SWT.BORDER | SWT.READ_ONLY);
		tInversion.setBounds(new Rectangle(97, 49, 198, 23));
		createCInversion();
		lDescripcion = new Label(grupoProductos, SWT.NONE);
		lDescripcion.setBounds(new Rectangle(17, 53, 71, 15));
		lDescripcion.setText("Descripción :");
		createGrupoCantidad();
		createGrupoImporte();
		createGrupoGraficavendidaPorMes();
		createGrupoGraficavendidaPorMes1();
		lFecha = new Label(grupoProductos, SWT.NONE);
		lFecha.setBounds(new Rectangle(335, 53, 42, 15));
		lFecha.setText("Fecha :");
		tFecha = new Text(grupoProductos, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		tFecha.setBounds(new Rectangle(381, 49, 85, 23));
	}
	/**
	 * This method initializes cInversion	
	 *
	 */
	private void createCInversion() {
		cInversion = new Combo(grupoProductos, SWT.NONE);
		cInversion.setBounds(new Rectangle(98, 15, 64, 23));
	}
	/**
	 * This method initializes grupoCantidad	
	 *
	 */
	private void createGrupoCantidad() {
		grupoCantidad = new Group(grupoProductos, SWT.NONE);
		grupoCantidad.setLayout(null);
		grupoCantidad.setText("Stock");
		grupoCantidad.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoCantidad.setBounds(new Rectangle(12, 80, 215, 197));
		label = new Label(grupoCantidad, SWT.NONE);
		label.setText("Comprado:");
		label.setBounds(new Rectangle(50, 39, 60, 15));
		tStockComprado = new Text(grupoCantidad, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tStockComprado.setBounds(new Rectangle(115, 35, 75, 23));
		lStockvendido = new Label(grupoCantidad, SWT.NONE);
		lStockvendido.setBounds(new Rectangle(60, 72, 50, 15));
		lStockvendido.setText(" Vendido:");
		tStockVendido = new Text(grupoCantidad, SWT.BORDER | SWT.RIGHT| SWT.READ_ONLY);
		tStockVendido.setBounds(new Rectangle(115, 68, 75, 23));
		tStockActual = new Text(grupoCantidad, SWT.BORDER | SWT.RIGHT| SWT.READ_ONLY);
		tStockActual.setBounds(new Rectangle(115, 100, 75, 23));
		lStockActual = new Label(grupoCantidad, SWT.NONE);
		lStockActual.setBounds(new Rectangle(70, 104, 40, 15));
		lStockActual.setText(" Actual:");
		bVerStock = new Button(grupoCantidad, SWT.NONE);
		bVerStock.setBounds(new Rectangle(43, 153, 116, 29));
		bVerStock.setText("Ver Stock");
	}
	/**
	 * This method initializes grupoImporte	
	 *
	 */
	private void createGrupoImporte() {
		grupoImporte = new Group(grupoProductos, SWT.NONE);
		grupoImporte.setLayout(null);
		grupoImporte.setText("Importe");
		grupoImporte.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoImporte.setBounds(new Rectangle(14, 285, 212, 197));
		lImporteComprado = new Label(grupoImporte, SWT.NONE);
		lImporteComprado.setBounds(new Rectangle(50, 39, 60, 15));
		lImporteComprado.setText("Comprado:");
		tImporteComprado = new Text(grupoImporte, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tImporteComprado.setBounds(new Rectangle(115, 35, 75, 23));
		lImportevendido = new Label(grupoImporte, SWT.NONE);
		lImportevendido.setBounds(new Rectangle(60, 72, 50, 15));
		lImportevendido.setText(" Vendido:");
		tImporteVendido = new Text(grupoImporte, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tImporteVendido.setBounds(new Rectangle(115, 68, 75, 23));
		tImporteActual = new Text(grupoImporte, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY);
		tImporteActual.setBounds(new Rectangle(115, 100, 75, 23));
		lImporteActual = new Label(grupoImporte, SWT.NONE);
		lImporteActual.setBounds(new Rectangle(70, 104, 40, 15));
		lImporteActual.setText(" Actual:");
		bVerVentas = new Button(grupoImporte, SWT.NONE);
		bVerVentas.setBounds(new Rectangle(45, 140, 116, 29));
		bVerVentas.setText("Ver Ventas");
	}
	/**
	 * This method initializes grupoGraficavendidaPorMes	
	 *
	 */
	private void createGrupoGraficavendidaPorMes() {
		grupoGraficaCantidadvendidaPorMes = new Group(grupoProductos, SWT.NONE);
		grupoGraficaCantidadvendidaPorMes.setLayout(new GridLayout());
		grupoGraficaCantidadvendidaPorMes.setText("Cantidad de Productos Vendidos por Mes");
		grupoGraficaCantidadvendidaPorMes.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoGraficaCantidadvendidaPorMes.setBounds(new Rectangle(241, 80, 498, 197));
	}
	/**
	 * This method initializes grupoGraficavendidaPorMes1	
	 *
	 */
	private void createGrupoGraficavendidaPorMes1() {
		grupoGraficavendidaPorMes1 = new Group(grupoProductos, SWT.NONE);
		grupoGraficavendidaPorMes1.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoGraficavendidaPorMes1.setLayout(new GridLayout());
		grupoGraficavendidaPorMes1.setText("Importe de Productos Vendidos por Mes");
		grupoGraficavendidaPorMes1.setBounds(new Rectangle(242, 285, 494, 197));
	}
	
	/**
	 * This method initializes group	
	 *
	 */
	private void createGrupoResumen(Composite composite) {
		grupoImporteEsperado = new Group(composite, SWT.NONE);
		grupoImporteEsperado.setLayout(null);
		createGrupoSaldosIniciales();
		createGrupoImporteEsperadoVentas();
		createGrupoPorcentajeVentas();
		createGrupoCapital();
		createGrupoGananciaTotal();
		createGrupoSaldoTotalClientes();
		grupoImporteEsperado.setBounds(new Rectangle(-7, -1, 753, 490));
	}

	/**
	 * This method initializes grupoSaldosIniciales	
	 *
	 */
	private void createGrupoSaldosIniciales() {
		grupoSaldosIniciales = new Group(grupoImporteEsperado, SWT.NONE);
		grupoSaldosIniciales.setLayout(null);
		grupoSaldosIniciales.setText("Saldos Iniciales");
		grupoSaldosIniciales.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoSaldosIniciales.setBounds(new Rectangle(19, 18, 247, 158));
		TotalSaldoIniciales = new Label(grupoSaldosIniciales, SWT.NONE);
		TotalSaldoIniciales.setBounds(new Rectangle(63, 28, 33, 15));
		TotalSaldoIniciales.setText("Total :");
		tTotalSaldos = new Text(grupoSaldosIniciales, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalSaldos.setBounds(new Rectangle(104, 24, 115, 23));
		tSaldosPagados = new Text(grupoSaldosIniciales, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldosPagados.setBounds(new Rectangle(104, 56, 115, 23));
		lTotalSaldosCancelados = new Label(grupoSaldosIniciales, SWT.NONE);
		lTotalSaldosCancelados.setBounds(new Rectangle(37, 60, 59, 15));
		lTotalSaldosCancelados.setText("Cancelado:");
		lSaldospendientes = new Label(grupoSaldosIniciales, SWT.NONE);
		lSaldospendientes.setBounds(new Rectangle(40, 90, 56, 15));
		lSaldospendientes.setText("Pendiente:");
		tSaldoPendiente = new Text(grupoSaldosIniciales, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldoPendiente.setBounds(new Rectangle(104, 86, 115, 23));
		bVerListado = new Button(grupoSaldosIniciales, SWT.NONE);
		bVerListado.setBounds(new Rectangle(71, 120, 120, 28));
		bVerListado.setText("Ver Listado");
	}

	/**
	 * This method initializes grupoImporteEsperadoVentas	
	 *
	 */
	private void createGrupoImporteEsperadoVentas() {
		grupoImporteEsperadoVentas = new Group(grupoImporteEsperado, SWT.NONE);
		grupoImporteEsperadoVentas.setLayout(null);
		grupoImporteEsperadoVentas.setText("Importe Total Esperado en Ventas");
		grupoImporteEsperadoVentas.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoImporteEsperadoVentas.setBounds(new Rectangle(19, 194, 247, 69));
		lTotal = new Label(grupoImporteEsperadoVentas, SWT.NONE);
		lTotal.setBounds(new Rectangle(42, 35, 34, 15));
		lTotal.setText("Total:");
		tImporteTotalEsperado = new Text(grupoImporteEsperadoVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tImporteTotalEsperado.setBounds(new Rectangle(78, 31, 109, 23));
	}

	/**
	 * This method initializes grupoPorcentajeVentas	
	 *
	 */
	private void createGrupoPorcentajeVentas() {
		grupoPorcentajeVentas = new Group(grupoImporteEsperado, SWT.NONE);
		grupoPorcentajeVentas.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoPorcentajeVentas.setLayout(null);
		grupoPorcentajeVentas.setText("Importe y Utilidad en Ventas realizadas");
		grupoPorcentajeVentas.setBounds(new Rectangle(19, 280, 247, 201));
		lTotal1 = new Label(grupoPorcentajeVentas, SWT.NONE);
		lTotal1.setBounds(new Rectangle(59, 33, 66, 15));
		lTotal1.setText("% Vendido:");
		tPorcentajeVendido = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tPorcentajeVendido.setBounds(new Rectangle(131, 29, 39, 23));
		lImporteEsperado = new Label(grupoPorcentajeVentas, SWT.NONE);
		lImporteEsperado.setBounds(new Rectangle(29, 65, 96, 15));
		lImporteEsperado.setText("Importe Esperado:");
		tImporteEsperado = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tImporteEsperado.setBounds(new Rectangle(131, 61, 97, 23));
		lImporteReal = new Label(grupoPorcentajeVentas, SWT.NONE);
		lImporteReal.setBounds(new Rectangle(55, 97, 70, 15));
		lImporteReal.setText("Importe Real:");
		tImporteReal = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tImporteReal.setBounds(new Rectangle(131, 93, 97, 23));
		lGananciaEsperada = new Label(grupoPorcentajeVentas, SWT.NONE);
		lGananciaEsperada.setBounds(new Rectangle(31, 128, 94, 15));
		lGananciaEsperada.setText("Utilidad Esperada:");
		tGanaciaEsperada = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tGanaciaEsperada.setBounds(new Rectangle(131, 124, 97, 23));
		lGananciaReal = new Label(grupoPorcentajeVentas, SWT.NONE);
		lGananciaReal.setBounds(new Rectangle(56, 165, 69, 15));
		lGananciaReal.setText("Utilidad Real:");
		tGanaciaReal = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tGanaciaReal.setBounds(new Rectangle(131, 161, 97, 23));
	}

	/**
	 * This method initializes grupoCapital	
	 *
	 */
	private void createGrupoCapital() {
		grupoCapital = new Group(grupoImporteEsperado, SWT.NONE);
		grupoCapital.setLayout(null);
		grupoCapital.setText("Capital ");
		grupoCapital.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoCapital.setBounds(new Rectangle(425, 18, 247, 158));
		lCapitalTotal = new Label(grupoCapital, SWT.NONE);
		lCapitalTotal.setBounds(new Rectangle(64, 129, 30, 15));
		lCapitalTotal.setText("Total:");
		tTotalCapital = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalCapital.setBounds(new Rectangle(101, 125, 110, 23));
		lTotalRecaudado = new Label(grupoCapital, SWT.NONE);
		lTotalRecaudado.setBounds(new Rectangle(32, 93, 62, 15));
		lTotalRecaudado.setText("Recaudado:");
		tTotalRecaudado = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalRecaudado.setBounds(new Rectangle(101, 89, 110, 23));
		lTotalAlmacen = new Label(grupoCapital, SWT.NONE);
		lTotalAlmacen.setBounds(new Rectangle(44, 19, 50, 15));
		lTotalAlmacen.setText("Almacen:");
		tTotalAlmacen = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalAlmacen.setBounds(new Rectangle(101, 15, 110, 23));
		lCreditos = new Label(grupoCapital, SWT.NONE);
		lCreditos.setBounds(new Rectangle(52, 58, 42, 15));
		lCreditos.setText("Credito:");
		tTotalCreditos = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalCreditos.setBounds(new Rectangle(101, 54, 110, 23));
	}

	/**
	 * This method initializes grupoGananciaTotal	
	 *
	 */
	private void createGrupoGananciaTotal() {
		grupoGananciaTotal = new Group(grupoImporteEsperado, SWT.NONE);
		grupoGananciaTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoGananciaTotal.setLayout(null);
		grupoGananciaTotal.setText("Utilidad");
		grupoGananciaTotal.setBounds(new Rectangle(425, 280, 247, 201));
		lTotalGanancia = new Label(grupoGananciaTotal, SWT.NONE);
		lTotalGanancia.setBounds(new Rectangle(21, 33, 78, 15));
		lTotalGanancia.setText("Max. Esperada:");
		tMaximaEsperada = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tMaximaEsperada.setBounds(new Rectangle(115, 29, 110, 23));
		lMinEsperada = new Label(grupoGananciaTotal, SWT.NONE);
		lMinEsperada.setBounds(new Rectangle(24, 72, 78, 15));
		lMinEsperada.setText("Min. Esperada:");
		tMinimaEsperada = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tMinimaEsperada.setBounds(new Rectangle(116, 68, 110, 23));
		lRecaudada = new Label(grupoGananciaTotal, SWT.NONE);
		lRecaudada.setBounds(new Rectangle(46, 149, 61, 15));
		lRecaudada.setText("Recaudada:");
		tGananciaRecaudada = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tGananciaRecaudada.setBounds(new Rectangle(116, 145, 110, 23));
		lCreditoUtilidad = new Label(grupoGananciaTotal, SWT.NONE);
		lCreditoUtilidad.setBounds(new Rectangle(61, 111, 41, 15));
		lCreditoUtilidad.setText("Credito:");
		tCreditosutilidad = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tCreditosutilidad.setBounds(new Rectangle(116, 107, 110, 23));
	}

	/**
	 * This method initializes grupoSaldoTotalClientes	
	 *
	 */
	private void createGrupoSaldoTotalClientes() {
		grupoSaldoTotalClientes = new Group(grupoImporteEsperado, SWT.NONE);
		grupoSaldoTotalClientes.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoSaldoTotalClientes.setLayout(null);
		grupoSaldoTotalClientes.setText("Saldo  Clientes");
		grupoSaldoTotalClientes.setBounds(new Rectangle(425, 194, 247, 69));
		lSaldoTotal = new Label(grupoSaldoTotalClientes, SWT.NONE);
		lSaldoTotal.setBounds(new Rectangle(42, 35, 34, 15));
		lSaldoTotal.setText("Total:");
		tSaldoTotal = new Text(grupoSaldoTotalClientes, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldoTotal.setBounds(new Rectangle(78, 31, 109, 23));
	}



	/**
	 * This method initializes grupoCategoriasMasRotadas	
	 *
	 */
	private void createGrupoCategoriasMasRotadas(Composite composite) {
		grupoCategoriasMasRotadas = new Group(composite, SWT.NONE);
		grupoCategoriasMasRotadas.setLayout(null);
		createGrupoCategoriasConsultas();
		grupoCategoriasMasRotadas.setBounds(new Rectangle(0, 0, 753, 490));
	}

	/**
	 * This method initializes grupoCategoriasConsultas	
	 *
	 */
	private void createGrupoCategoriasConsultas() {
		grupoCategoriasConsultas = new Group(grupoCategoriasMasRotadas, SWT.NONE);
		grupoCategoriasConsultas.setLayout(null);
		grupoCategoriasConsultas.setText("Listado de Ventas por Categorias ");
		grupoCategoriasConsultas.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoCategoriasConsultas.setBounds(new Rectangle(7, 18, 373, 456));
		tbVentasPorCategorias = new Table(grupoCategoriasConsultas, SWT.NONE);
		tbVentasPorCategorias.setHeaderVisible(true);
		tbVentasPorCategorias.setLinesVisible(true);
		tbVentasPorCategorias.setBounds(new Rectangle(16, 22, 343, 423));
	}

}
