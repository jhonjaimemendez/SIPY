package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
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
import com.JASoft.componentesAccesoDatos.ResultSetSerializable;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ListarValor;
import com.JASoft.componentesGraficos.ToolBar;

public class GUIEstadoCuentaCliente extends ConfigurarText implements SelectionListener,FocusListener,TraverseListener{

	private Shell sShell = null;
	private Group grupoClientes = null;
	private Label label = null;
	private Combo cTipo = null;
	private Label Identificación = null;
	private Text tNumero = null;
	private Label lNombres = null;
	private Text tNombres = null;
	private Group grupoMovimientos = null;
	private Table tbMovimientos = null;
	private Label lMovimientos = null;
	private Combo cMovimientos = null;
	private ToolBar toolBar = null;
	private ConectarMySQL conectarMySQL = null;
	private Text textoComodin;
	private ListarValor listarValor;
	private Group grupoCreditos = null;
	private Label lSaldo = null;
	private Text tSaldo = null;
	private Label lCupo = null;
	private Text tCupo = null;



	/*
	 * Constructor general
	 */
	public GUIEstadoCuentaCliente(ConectarMySQL conectarMySQL, Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);
		
		centrarShell(sShell);

		tNumero.forceFocus(); // Se asigna el foco al primer componente navegable

		agregarEventos();

		agregarConfiguraciones();
		
		sShell.open();

	}

	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	private void limpiar() {

		cTipo.select(0);
		cMovimientos.select(0);
		tNombres.setText("");		
		tNumero.setText("");
		tbMovimientos.removeAll();
		
		listarValor.ocultarListaValor();
		
			
		tNumero.forceFocus();

	}

	/*
	 * Metodo eliminar: Se consulta un cliente
	 */
	private boolean consultar() {

		boolean resultadoBoolean = false;
		
		try {


			String sentenciaSQL = "Select Concat_WS(' ',Nombres,Apellidos) Nombres,Saldo,Cupo " 
				+"From Clientes "
				+"Where TipoId = ? and idCliente = ?";

			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);

			consultar.setString(1, getTipoDocumento());
			consultar.setString(2, tNumero.getText());

			ResultSet resultado = consultar.executeQuery();

			if (resultado.next()) {

				resultadoBoolean = true;
				
				tNombres.setText(resultado.getString(1));
				tSaldo.setText(getFormatoMoneda(resultado.getString(2)));
				tCupo.setText(resultado.getString(3));


				//se cierra el resultSet
				resultado.close();
				tbMovimientos.removeAll();
				
				consultarVentasAbonos();

				
			}
		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}

		return resultadoBoolean;
	}



	/*
	 * Metodo getTipoDocumento: Devuelve el caracter del tipo de documento como
	 * se especifico en el tabla
	 */
	private String getTipoDocumento() {

		String resultado = cTipo.getSelectionIndex() == 0 ? "C" : cTipo
				.getSelectionIndex() == 1 ? "T"
						: cTipo.getSelectionIndex() == 2 ? "P" : "E";

		return resultado;

	}



	/*
	 * Metodo agregarConfiguraciones: agregar configuraciones para la GUI
	 */
	private  void agregarConfiguraciones(){


		//Se configuran las columnas
		TableColumn clTipoMovimiento = new TableColumn(tbMovimientos, SWT.CENTER);
		TableColumn clFecha = new TableColumn(tbMovimientos, SWT.CENTER);
		TableColumn clHora = new TableColumn(tbMovimientos, SWT.CENTER);
		TableColumn clValor = new TableColumn(tbMovimientos, SWT.CENTER);
		TableColumn clSaldo = new TableColumn(tbMovimientos, SWT.CENTER);
		TableColumn clObservaciones = new TableColumn(tbMovimientos, SWT.CENTER);
		TableColumn clIdAbono = new TableColumn(tbMovimientos, SWT.CENTER);
		

		//sShell.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Clientes.gif"));

		//Se configuran los nombres de columnas
		clTipoMovimiento.setText("Movimiento");
		clFecha.setText("Fecha");
		clHora.setText("Hora");
		clValor.setText("Valor");
		clObservaciones.setText("Observaciones");
		clSaldo.setText("Saldo");


		
		
		//Se configuran los tamaños  de columnas
		clTipoMovimiento.setWidth(88);
		clFecha.setWidth(73);
		clHora.setWidth(67);
		clValor.setWidth(76);
		clObservaciones.setWidth(348);
		clSaldo.setWidth(76);
		
		tSaldo.setForeground(getColorRojo());
		tCupo.setForeground(getColorRojo());


	}		
    
	private void consultarVentasAbonos() {

		try {

			//Se borra la tabla
			tbMovimientos.removeAll();
			
			//se especifica la sentencia para buscas los abonos del cliente

			String sentenciaSQL  = "Select 'Venta',DATE_FORMAT(fecha, '%Y-%c-%d') Fecha,DATE_FORMAT(fecha,'%l:%i:%p') Hora,total,Saldo,Observaciones,numeroVenta "
				+"From VentasEncabezado "
				+"Where TipoId = ? and idCliente = ? "
				+"  Union "
				+"Select 'Abono',DATE_FORMAT(fechaAbono, '%Y-%c-%d') Fecha,DATE_FORMAT(fechaAbono,'%l:%i:%p') Hora,Valor,Saldo,Observaciones,idAbono "
				+" From AbonosClientes "
				+"Where TipoId = ? and idCliente = ? "
				+ "Order by 1 desc,2 desc ";


			PreparedStatement consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);

			consultar.setString(1, getTipoDocumento());
			consultar.setString(2, tNumero.getText());
			consultar.setString(3, getTipoDocumento());
			consultar.setString(4, tNumero.getText());

			

			ResultSet resultado = consultar.executeQuery();
              
			
			while (resultado.next()) {
				
				TableItem tableItem = new TableItem(tbMovimientos,SWT.NONE);
				tableItem.setText(new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3), getFormatoMoneda(resultado.getString(4)),resultado.getString(5),resultado.getString(6),resultado.getString(7)});
				
			}
		
			

		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}
	}
	
	
	
	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	private void agregarEventos(){

		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbSalir().addSelectionListener(this);
		toolBar.getbLimpiar().addSelectionListener(this);
		
		
		textoComodin = new Text(sShell,SWT.NONE);
		
		//Se agrega un evento de validacion en el nombres
		tNumero.addTraverseListener(this);
		tNombres.addTraverseListener(this);
		
		tNumero.addFocusListener(this);
		tNombres.addFocusListener(this);
		
		tNumero.addVerifyListener(getValidarEntradaNumeros());
		tNombres.addVerifyListener(getConvertirMayusculaLetras());
		
		
		tbMovimientos.addListener(SWT.MouseDoubleClick, new Listener() {
	        public void handleEvent(Event event) {
	        	
	        	TableItem item = tbMovimientos.getItem(tbMovimientos.getSelectionIndex());
	        	
	        	if (item.getText(0).equals("Venta")){
					
					GUIDialogoVentaDetalleRealizada gUIDialogoVentaDetalleRealizada = new GUIDialogoVentaDetalleRealizada(sShell,conectarMySQL,item.getText(6),item.getText(3));
					
				} 
	        
	        }
	      });
		
		
		// Se configuran los parametros para la lista de valores
		String sentenciaSQL = "Select Case TipoID  When 'C' Then 'Cedula' When 'T' Then 'Tarjeta'When 'E' Then 'Extranjeria' End Tipo,idCliente as Identificación,Concat_WS(' ',Nombres, Apellidos) Nombres,Saldo,Cupo " +
				               "From Clientes Where Nombres ";

		
		int anchoColumnas[] = { 55, 90,340,0,0 };
		
		Object[][] objetosRetorno = new Object[5][2];
		objetosRetorno[0][0] = tNumero;
		objetosRetorno[0][1] = 1;
		objetosRetorno[1][0] = tNombres;
		objetosRetorno[1][1] = 2;
		objetosRetorno[2][0] = textoComodin;
		objetosRetorno[2][1] = 0;
		objetosRetorno[3][0] = tSaldo;
		objetosRetorno[3][1] = 3;
		objetosRetorno[4][0] = tCupo;
		objetosRetorno[4][1] = 4;

		listarValor.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL,objetosRetorno,
				cMovimientos, anchoColumnas);

		tNombres.addKeyListener(listarValor);
		
		//se agrega este evento para cambiarle el formato cuando el cliente escoje con las lista de valores el cliente
		tSaldo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
			
				tSaldo.setText(getFormatoMoneda(tSaldo.getText()));
				
			}
		});
		
		//Se agrega este elemento como comodin para agregar el tipo de documeto
		textoComodin.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				
				consultarVentasAbonos();

				if (textoComodin.equals("Cedula")) 

					cTipo.select(0);
				else

					if (textoComodin.equals("Tarjeta")) 

						cTipo.select(1);
					else
						if (textoComodin.equals("Pasaporte")) 

							cTipo.select(2);
						else
							if (textoComodin.equals("Extranjeria")) 

								cTipo.select(3);

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
		
		else
			if (fuente == toolBar.getbLimpiar())
				
				limpiar();


	}

	//**************************** Eventos de foco ********************************************************

	@Override
	public void focusGained(FocusEvent a) {

		Object fuente = a.getSource();
		
		if (fuente == tNombres) {
			
			if (tNumero.getText().isEmpty())
				
				listarValor.mostrarListaValores("");
			
		}
		

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

	//********************************* Eventos de validacion ***************************************************
	public void keyTraversed(TraverseEvent e) {

		if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

			Object fuente = e.getSource();

			if (fuente == tNumero  && !tNumero.getText().isEmpty()) {

				if (!consultar()) {

					int opcion = mensajeConfirmacion(sShell, "Cliente " + tNumero.getText() + " no ha sido registrado \n Desea buscarlo a traves de una lista de valores?");

					if (opcion != 32) 

						e.doit = false;
					
					else {
						
						tNumero.setText("");
						
					}

				}

			} else
				if (fuente == tNombres) {
					
					listarValor.ocultarListaValor();
					
				}
				
		}


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
		

		 // Se instancia aqui para que salga al frente la lista de valores
		listarValor = new ListarValor(sShell,100,170,500);
		
		
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Estado de Cuenta de Clientes");
		createGrupoCreditos();
		createGrupoClientes();
		sShell.setLayout(null);
		createGrupoMovimientos();
		sShell.setBounds(new Rectangle(138, 138, 800, 600));
	}

	/**
	 * This method initializes grupoClientes	
	 *
	 */
	private void createGrupoClientes() {
		grupoClientes = new Group(sShell, SWT.NONE);
		grupoClientes.setLayout(null);
		grupoClientes.setText("Datos Clientes");
		grupoClientes.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoClientes.setBounds(new Rectangle(20, 61, 481, 132));
		label = new Label(grupoClientes, SWT.NONE);
		label.setText("Tipo Identificación");
		label.setBounds(new Rectangle(8, 20, 108, 15));
		createCTipo();
		Identificación = new Label(grupoClientes, SWT.NONE);
		Identificación.setBounds(new Rectangle(257, 20, 99, 15));
		Identificación.setText("Identificación");
		tNumero = new Text(grupoClientes, SWT.BORDER);
		tNumero.setBounds(new Rectangle(257, 40, 177, 23));
		lNombres = new Label(grupoClientes, SWT.NONE);
		lNombres.setBounds(new Rectangle(11, 88, 60, 15));
		lNombres.setText("Nombres:");
		tNombres = new Text(grupoClientes, SWT.BORDER);
		tNombres.setBounds(new Rectangle(80, 83, 356, 23));
	}

	private void createGrupoCreditos() {
		grupoCreditos = new Group(sShell, SWT.NONE);
		grupoCreditos.setLayout(null);
		grupoCreditos.setText("Datos credito");
		grupoCreditos.setBounds(new Rectangle(528, 61, 235, 132));
		lSaldo = new Label(grupoCreditos, SWT.NONE);
		lSaldo.setBounds(new Rectangle(32, 32, 39, 15));
		lSaldo.setText("Saldo:");
		tSaldo = new Text(grupoCreditos, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldo.setBounds(new Rectangle(79, 28, 125, 23));
		lCupo = new Label(grupoCreditos, SWT.NONE);
		lCupo.setBounds(new Rectangle(40, 83, 32, 15));
		lCupo.setText("Cupo:");
		tCupo = new Text(grupoCreditos, SWT.BORDER| SWT.READ_ONLY | SWT.RIGHT);
		tCupo.setBounds(new Rectangle(79, 79, 125, 23));
	}
	/**
	 * This method initializes cTipo	
	 *
	 */
	private void createCTipo() {
		cTipo = new Combo(grupoClientes, SWT.READ_ONLY);
		cTipo.setBounds(new Rectangle(8, 40, 179, 23));
		cTipo.add("Cedula de Ciudadania");
		cTipo.add("Tarjeta de Identidad");
		cTipo.add("Pasaporte");
		cTipo.add("Cedula de Extranjeria");
		cTipo.select(0);
	}

	/**
	 * This method initializes grupoMovimientos	
	 *
	 */
	private void createGrupoMovimientos() {
		grupoMovimientos = new Group(sShell, SWT.NONE);
		grupoMovimientos.setLayout(null);
		grupoMovimientos.setBounds(new Rectangle(21, 203, 750, 352));
		tbMovimientos = new Table(grupoMovimientos, SWT.VIRTUAL | SWT.FULL_SELECTION);
		tbMovimientos.setHeaderVisible(true);
		tbMovimientos.setLinesVisible(true);
		tbMovimientos.setBounds(new Rectangle(8, 50, 730, 289));
		lMovimientos = new Label(grupoMovimientos, SWT.NONE);
		lMovimientos.setBounds(new Rectangle(213, 24, 84, 15));
		lMovimientos.setText("Movimientos.");
		createCMovimientos();
	}

	/**
	 * This method initializes cMovimientos	
	 *
	 */
	private void createCMovimientos() {
		cMovimientos = new Combo(grupoMovimientos, SWT.READ_ONLY);
		cMovimientos.setBounds(new Rectangle(8, 20, 198, 23));
		cMovimientos.add("Ultimos 10 Movimientos");
		cMovimientos.add("Todos");
		cMovimientos.select(0);

	}

}
