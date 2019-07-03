package com.JASoft.GUI.generales;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.JASoft.GUI.SIPY.GUIAbono;
import com.JASoft.GUI.SIPY.GUICategoria;
import com.JASoft.GUI.SIPY.GUICliente;
import com.JASoft.GUI.SIPY.GUICompra;
import com.JASoft.GUI.SIPY.GUIConsultaVentas;
import com.JASoft.GUI.SIPY.GUIEstadisticas;
import com.JASoft.GUI.SIPY.GUIEstadoCuentaCliente;
import com.JASoft.GUI.SIPY.GUIListaProducto;
import com.JASoft.GUI.SIPY.GUIListadoClientes;
import com.JASoft.GUI.SIPY.GUIMarca;
import com.JASoft.GUI.SIPY.GUIProducto;
import com.JASoft.GUI.SIPY.GUIProveedor;
import com.JASoft.GUI.SIPY.GUIVenta;
import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesGraficos.Calendario;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.Calendario;

public class GUIMenuPrincipal implements SelectionListener{

	private Shell sShell = null;

	//Menus
	private Menu parametros;
	private Menu ventas;
	private Menu consultas;
	private Menu reportes;
	private Menu utilidadesAyudas;
	private Menu salir;
	private Menu cambioUsuario;
	private Menu utilidades;
	private Menu compras;
	private Menu inventario;
	private Menu datosGenerales; 	
	private Menu comprasConsulta; 	
	private Menu utilidad; 
	private Menu devoluciones; 	
	private Menu contabilidad;


	//MenuItem
	private MenuItem estadisticas; 
	private MenuItem mIclientes;
	private MenuItem mIClientesConsulta;
	private MenuItem mIEstadoCuentas;
	private MenuItem mIproveedores;
	private MenuItem mICategorias;
	private MenuItem mIMarcas;
	private MenuItem mIproductos;

	private MenuItem mIVentas;
	private MenuItem mIParametrosGenerales;
	private MenuItem miFestivos;
	private MenuItem mICalendario;
	private MenuItem configurarBackup;
	private MenuItem cargarBackup;
	private MenuItem administrarCuenta;
	private MenuItem mantenimientoBD;
	private MenuItem mIDevolucionVentas;
	private MenuItem mIAnular;
	private MenuItem mICotizacion;
	private MenuItem mIListadoClientes;
	private MenuItem mIListadoProductos;
	private MenuItem mIPorProveedor;
	private MenuItem mIPorProducto;
	private MenuItem mIRegistrar;
	private MenuItem mIDevolucionCompras;
	private MenuItem mIAnularCompras;
	private MenuItem mIComprasAnuladas;
	private MenuItem mIUtilidadPorProductos;
	private MenuItem mIUtilidadPorPeriodos;
	private MenuItem mIListaPrecios;
	private MenuItem mICotizacionPorCliente; 	
	private MenuItem mICotizacionPorProducto;
	private MenuItem mIDevolucionPorCompra; 	
	private MenuItem mIDevolucionPorVenta; 	
	private MenuItem mIRotacionProductos; 	
	private MenuItem mICompararVentas; 	
	private MenuItem mIMarcasEstadisticas; 	
	private MenuItem mICategoriasEstadisticas;
	private MenuItem mIVentasClienteEstadistica; 	
	private MenuItem mIVentasClienteProveedor;
	private MenuItem mICatalogoProductos; 	
	private MenuItem mIPrivilegiosUsuario;
	private MenuItem mIVentasPorVendedor;
	private MenuItem mIKardex;
	private MenuItem mICompraPorPeriodo;
	private MenuItem mIVentaPorPeriodo;
	private MenuItem miAuditorias;
	private MenuItem miCaja;
	private MenuItem miConsultaCaja;
	private ConectarMySQL conectarMySQL;
	private Shell shellPadre;

	private MenuItem mIAbono;


	/**
	 * This method initializes sShell
	 */
	 public GUIMenuPrincipal(final ConectarMySQL conectarMySQL) {

		 this.conectarMySQL = conectarMySQL;

		 sShell = new Shell(Display.getCurrent());
		 sShell.setText("Menu Prncipal - SIPY");
		 sShell.setLayout(null);
		 sShell.setBounds(new Rectangle(75, 75, 950, 720));
		 ConfigurarText.centrarShell(sShell);

		 Menu barraMenu = new Menu(sShell, SWT.BAR);

		 //Se configuran los Menus
		 MenuItem parametrosEncabezado = new MenuItem(barraMenu, SWT.CASCADE);
		 parametrosEncabezado.setText("&Parametros");

		 parametros = new Menu(barraMenu);
		 parametrosEncabezado.setMenu(parametros);

		 MenuItem datosGeneralesEncabezado = new MenuItem(barraMenu, SWT.CASCADE);
		 datosGeneralesEncabezado.setText("&Datos Generales");

		 datosGenerales = new Menu(barraMenu);
		 datosGeneralesEncabezado.setMenu(datosGenerales);

		 MenuItem comprasEncabezado = new MenuItem(barraMenu, SWT.CASCADE);
		 comprasEncabezado.setText("&Compras");

		 compras = new Menu(barraMenu);
		 comprasEncabezado.setMenu(compras);

		 MenuItem ventasEncabezado = new MenuItem(barraMenu, SWT.CASCADE);
		 ventasEncabezado.setText("&Ventas");

		 ventas = new Menu(barraMenu);
		 ventasEncabezado.setMenu(ventas);

		 MenuItem inventarioEncabezado = new MenuItem(barraMenu, SWT.CASCADE);
		 inventarioEncabezado.setText("&Inventario");

		 inventario = new Menu(barraMenu);
		 inventarioEncabezado.setMenu(inventario);

		 MenuItem consultasEncabezado = new MenuItem(barraMenu, SWT.CASCADE);
		 consultasEncabezado.setText("&Consultas");

		 consultas = new Menu(barraMenu);
		 consultasEncabezado.setMenu(consultas);



		 //Se configuran los Item para el menu parametros
		 mIParametrosGenerales = new MenuItem(parametros, SWT.CASCADE);
		 mIParametrosGenerales.setText("&Parametros");

		 miFestivos = new MenuItem(parametros, SWT.CASCADE);
		 miFestivos.setText("&Festivos");

		 administrarCuenta = new MenuItem(parametros, SWT.CASCADE);
		 administrarCuenta.setText("&Administrar Cuenta");

		 mIPrivilegiosUsuario = new MenuItem(parametros, SWT.CASCADE);
		 mIPrivilegiosUsuario.setText("&Privilegios");

		 //Se configuran los Item para el Menu datos generales
		 mIclientes = new MenuItem(datosGenerales, SWT.CASCADE);
		 mIclientes.setAccelerator('C');
		 mIclientes.setText("&Clientes");
		 mIclientes.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));


		 mIproveedores = new MenuItem(datosGenerales, SWT.CASCADE);
		 mIproveedores.setText("&Proveedores");
		 mIproveedores.setImage(new Image(Display.getCurrent(), "imagenes/Proveedores.gif"));


		 //Se configuran los Item para el Menu Ventas
		 mIVentas = new MenuItem(ventas, SWT.CASCADE);
		 mIVentas.setText("&Facturación");
		 mIVentas.setImage(new Image(Display.getCurrent(), "imagenes/VentasFacturacion.gif"));

		 mIDevolucionVentas = new MenuItem(ventas, SWT.CASCADE);
		 mIDevolucionVentas.setText("&Devolución");
		 mIDevolucionVentas.setImage(new Image(Display.getCurrent(), "imagenes/VentasDevolucion.gif"));


		 mIAnular = new MenuItem(ventas, SWT.CASCADE);
		 mIAnular.setText("&Anular");
		 mIAnular.setImage(new Image(Display.getCurrent(), "imagenes/VentasAnulacion.gif"));

		 mIAbono = new MenuItem(ventas, SWT.CASCADE);
		 mIAbono.setText("&Abono");
		 //mIAnular.setImage(new Image(Display.getCurrent(), "imagenes/VentasAnulacion.gif"));


		 //Se configuran los Item para el Menu Compras
		 mIRegistrar = new MenuItem(compras, SWT.CASCADE);
		 mIRegistrar.setText("&Registar");
		 mIRegistrar.setImage(new Image(Display.getCurrent(), "imagenes/ComprasRegistrar.gif"));

		 mIDevolucionCompras = new MenuItem(compras, SWT.CASCADE);
		 mIDevolucionCompras.setText("&Devolución");
		 mIDevolucionCompras.setImage(new Image(Display.getCurrent(), "imagenes/ComprasDevolucion.gif"));

		 mIAnularCompras = new MenuItem(compras, SWT.CASCADE);
		 mIAnularCompras.setText("&Anular");
		 mIAnularCompras.setImage(new Image(Display.getCurrent(), "imagenes/ComprasAnular.gif"));

		 //Se configura los item's JMenu de Inventario
		 mIproductos = new MenuItem(inventario, SWT.CASCADE);
		 mIproductos.setText("&Productos");
		 mIproductos.setImage(new Image(Display.getCurrent(), "imagenes/InventarioProductos.gif"));


		 mICategorias  = new MenuItem(inventario, SWT.CASCADE);
		 mICategorias.setText("&Categorias");
		 mICategorias.setImage(new Image(Display.getCurrent(), "imagenes/InventarioCategorias.gif"));

		 mIMarcas  = new MenuItem(inventario, SWT.CASCADE);
		 mIMarcas.setText("&Marcas");
		 mIMarcas.setImage(new Image(Display.getCurrent(), "imagenes/InventarioMarcas.gif"));

		 mIListadoProductos = new MenuItem(inventario, SWT.CASCADE);
		 mIListadoProductos.setText("&Listado Productos"); 
		 mIListadoProductos.setImage(new Image(Display.getCurrent(), "imagenes/ListadoProductos.gif"));


		 mIKardex = new MenuItem(inventario, SWT.CASCADE);
		 mIKardex.setText("&Kardex"); 
		 mIKardex.setImage(new Image(Display.getCurrent(), "imagenes/kardex.png"));


		 mIClientesConsulta = new MenuItem(consultas, SWT.CASCADE);
		 mIClientesConsulta.setText("&Clientes");

		 Menu submenuClientes = new Menu(sShell, SWT.DROP_DOWN);
		 mIClientesConsulta.setMenu(submenuClientes);


		 //MenuItem para el menu consultas
		 mIListadoClientes = new MenuItem(submenuClientes, SWT.CASCADE);
		 mIListadoClientes.setText("&Listado de Clientes");
		 mIListadoClientes.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));

		 mIEstadoCuentas = new MenuItem(submenuClientes, SWT.CASCADE);
		 mIEstadoCuentas.setText("&Estado de cuentas");
		 mIEstadoCuentas.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));

		 mIVentaPorPeriodo =  new MenuItem(consultas, SWT.CASCADE);
		 mIVentaPorPeriodo.setText("&Ventas");
		 mIVentaPorPeriodo.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));

		 estadisticas =  new MenuItem(consultas, SWT.CASCADE);
		 estadisticas.setText("&Estadisticas");
		 mIVentaPorPeriodo.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));
		 
		 
		 sShell.setMenuBar(barraMenu);


		 //Se agregan los eventos a los item
		 //Se agrega un evento al boton aceptar para cuando se presione
		 mIclientes.addSelectionListener(this);
		 mIproveedores.addSelectionListener(this);
		 mIproductos.addSelectionListener(this);
		 mIRegistrar.addSelectionListener(this);
		 mICategorias.addSelectionListener(this);
		 mIMarcas.addSelectionListener(this);
		 mIVentas.addSelectionListener(this);
		 mIAbono.addSelectionListener(this);
		 mIListadoProductos.addSelectionListener(this);
		 mIListadoClientes.addSelectionListener(this);
		 mIEstadoCuentas.addSelectionListener(this);
		 mIVentaPorPeriodo.addSelectionListener(this);
		 estadisticas.addSelectionListener(this);

		 ConfigurarText.bloquearShell(sShell);

	 }


	 @Override
	 public void widgetDefaultSelected(SelectionEvent arg0) {
		 // TODO Auto-generated method stub

	 }


	 @Override
	 public void widgetSelected(SelectionEvent a) {

		 Object fuente = a.getSource();

		 if (fuente == mIclientes)

			 new GUICliente(conectarMySQL,sShell);

		 else

			 if (fuente == mIproveedores)

				 new GUIProveedor(conectarMySQL,sShell);

			 else
				 if (fuente == mIproductos)

					 new GUIProducto(conectarMySQL,sShell);


				 else
					 if (fuente == mIRegistrar)

						 new GUICompra(conectarMySQL,sShell);

					 else
						 if (fuente == mICategorias)

							 new GUICategoria(conectarMySQL,sShell);

						 else

							 if (fuente == mIMarcas)

								 new GUIMarca(conectarMySQL,sShell);

							 else

								 if (fuente == mIVentas)

									 new GUIVenta(conectarMySQL,sShell);
								 else

									 if (fuente == mIAbono)

										 new GUIAbono(conectarMySQL,sShell);

									 else

										 if (fuente == mIListadoProductos)

											 new GUIListaProducto(conectarMySQL,sShell);

										 else
											 if(fuente == mIListadoClientes)

												 new GUIListadoClientes(conectarMySQL,sShell);

											 else

												 if(fuente == mIEstadoCuentas)

													 new GUIEstadoCuentaCliente(conectarMySQL,sShell);
		 
												 else
													 
													 if(fuente == mIVentaPorPeriodo)

														 new GUIConsultaVentas(conectarMySQL,sShell);
		 
													 else
														 
														 new GUIEstadisticas(conectarMySQL, sShell);
			 




	 }

}
