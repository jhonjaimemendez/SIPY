package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;


public class GUIProveedor extends ConfigurarText implements FocusListener,SelectionListener{

	private Shell sShell = null;
	private Group Identificacion = null;
	private Label lTipo = null;
	private Label lRazonSocial = null;
	private Text tRazonSocial = null;
	private Text tNumeroNit = null;
	private Group gDatosPersonales = null;
	private Label lFax = null;
	private Text tFax = null;
	private Combo cDepartamentos = null;
	private Label lDepartamento = null;
	private Label lCiudadMunicipio = null;
	private Text tCiudadMunicipio = null;
	private Text tEmail = null;
	private Label lEmail = null;
	private Text tDireccion = null;
	private Label lDireccion = null;
	private Text tTelefono = null;
	private Label lPaginaWeb = null;
	private Text tPaginaWeb = null;
	private Label lTelefono = null;
	private Label lPais = null;
	private Combo cPais = null;
	private Group grupoContactos = null;
	private Table tbContactos = null;
	private Button bAgregar = null;
	private Button bEditar = null;
	private Button bEliminar = null;
	private ArrayList<Text> arrayTextRequeridos= null;  //  @jve:decl-index=0:
	private ConectarMySQL conectarMySQL= null;
	private PreparedStatement consultar;  //  @jve:decl-index=0:
	private String divisionPolitica = null;
	private ToolBar toolBar = null;
	private Shell shellPadre;
	
	
	/*
	 * Constructor general
	 */
	public GUIProveedor(ConectarMySQL conectarMySQL,Shell shellPadre) {
		
		this.conectarMySQL = conectarMySQL;
		
		createSShell(shellPadre);
		
		centrarShell(sShell);
		
		tNumeroNit.forceFocus(); //Se asigna el foco al primer componente navegable
		
		agregarConfiguraciones();
		
		agregarEventos();
		
		crearArrayRequeridos();
		
		sShell.open();

	}

	
	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */
	
	public void limpiar(boolean comodin) {

		tRazonSocial.setText("");
		tFax.setText("");
		tCiudadMunicipio.setText("");
		tEmail.setText("");
		tDireccion.setText("");
		tTelefono.setText("");
		tPaginaWeb.setText("");
		cPais.select(0);
		
		if (comodin) {
			
			tNumeroNit.setText("");
			tNumeroNit.forceFocus();
			
		}


	}
	
	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	public void guardar(){

		if (validarRegistro(arrayTextRequeridos, sShell)){

			try {

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoProveedores(conectarMySQL.getConexion());


				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, tNumeroNit.getText());
				llamarProcedimientoAlmacenado.setString(3, tRazonSocial.getText());
				llamarProcedimientoAlmacenado.setString(4, tDireccion.getText());
				llamarProcedimientoAlmacenado.setString(5, cPais.getText());
				llamarProcedimientoAlmacenado.setString(6, "0");
				llamarProcedimientoAlmacenado.setString(7, tTelefono.getText());
				llamarProcedimientoAlmacenado.setString(8, tFax.getText());
				llamarProcedimientoAlmacenado.setString(9, tPaginaWeb.getText());
				llamarProcedimientoAlmacenado.setString(10, tEmail.getText());
				llamarProcedimientoAlmacenado.setString(11, null);

				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Proveedor ha sido registrado");
				limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell, "Error al guardar el registro de proveedor " +e);
			}
		}
	}	

	/*
	 * Metodo eliminar: Elimina un cliente
	 */
	public void eliminar(){
		
		if (!tNumeroNit.getText().isEmpty()) {	

			try {


				System.out.println(mensajeConfirmacion(sShell, "Esta seguro que desea eliminar este proveedores?"));
				
				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoProveedores(conectarMySQL.getConexion());


				llamarProcedimientoAlmacenado.setInt(1, 0);
				llamarProcedimientoAlmacenado.setString(2, tNumeroNit.getText());
				llamarProcedimientoAlmacenado.setString(3, null);
				llamarProcedimientoAlmacenado.setString(4, null);
				llamarProcedimientoAlmacenado.setString(5, null);

				llamarProcedimientoAlmacenado.setString(6, null);
				llamarProcedimientoAlmacenado.setString(7, null);
				llamarProcedimientoAlmacenado.setString(8, null);
				llamarProcedimientoAlmacenado.setString(9, null);


				llamarProcedimientoAlmacenado.setString(10, null);
				llamarProcedimientoAlmacenado.setString(11, null);

				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Proveedor ha sido eliminado");

				limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell, "Este proveedor tiene asociadas un conjunto de facturas, si desea eliminarlo primero debe eliminar las facturas asociadas" +e);
			}

		}

	}
	
	
	/*
	 * Metodo consultar: Se consulta un cliente
	 */
	public void consultar(){
		
		try {

			String sentenciaSQL = "Select RazonSocial,Direccion,Pais,p.DivisionPolitica,m.municipio,Telefono,Fax,PaginaWeb,CorreoElectronico "+
                                  "From Proveedores p, Municipioscorregimientos m "+ 
                                  "Where p.idproveedor = ? and p.divisionPolitica = m.divisionPolitica";
			
			if (consultar == null)
				
				 consultar = conectarMySQL.getConexion().prepareStatement(sentenciaSQL);
			
			consultar.setString(1, tNumeroNit.getText());
			
			ResultSet resultado = consultar.executeQuery();
			
			if (resultado.next()) { //Se extraen los elementos
				
				tRazonSocial.setText(devuelveCadenaVaciaParaNulo(resultado.getString(1)));
				tDireccion.setText(devuelveCadenaVaciaParaNulo(resultado.getString(2)));
				cPais.select(resultado.getString(3).equals("Colombia") ? 0 : 1 );
				
				tCiudadMunicipio.setText(devuelveCadenaVaciaParaNulo(resultado.getString(5)));
				tTelefono.setText(devuelveCadenaVaciaParaNulo(resultado.getString(6)));
				tFax.setText(devuelveCadenaVaciaParaNulo(resultado.getString(7)));
				tPaginaWeb.setText(devuelveCadenaVaciaParaNulo(resultado.getString(7)));
				tEmail.setText(devuelveCadenaVaciaParaNulo(resultado.getString(9)));
				
				
			} else
				
				limpiar(false);
				

		} catch (SQLException e) {

			mensajeError(sShell, "Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas" +e);
		}

		
	
	}
	
	
	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	public void crearArrayRequeridos(){
		
		arrayTextRequeridos = new ArrayList<Text>();
		
		arrayTextRequeridos.add(tNumeroNit);
		arrayTextRequeridos.add(tRazonSocial);
		arrayTextRequeridos.add(tDireccion);

		 
	}
	
	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	public void agregarEventos(){
		
		//Se agrega un evento al combo departamentos
		cDepartamentos.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		        
		    	  tCiudadMunicipio.setText(getDepartamentos() [cDepartamentos.getSelectionIndex()][3]);
		    	  divisionPolitica = getDepartamentos() [cDepartamentos.getSelectionIndex()][2];
			      tCiudadMunicipio.selectAll();

		      }
		    });
		
		//Se agregar eventos de focos a tNumeroNit para que consulte el cliente
		tNumeroNit.addFocusListener(this);
		tNumeroNit.addVerifyListener(getValidarEntradaNumeros());
		
		tTelefono.addVerifyListener(getValidarEntradaNumeros());
		tFax.addVerifyListener(getValidarEntradaNumeros());
		
		//Se agregan eventos para que siempre escriba en mayuscula
		tCiudadMunicipio.addVerifyListener(getConvertirMayusculaLetras());
		tRazonSocial.addVerifyListener(getConvertirMayusculaLetras());
		tDireccion.addVerifyListener(getConvertirMayusculaLetras());
		
		
		tEmail.addFocusListener(this);
		

		
		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);
		
		
	}
	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	public void agregarConfiguraciones(){
		
		//Se configura el limita de datos por Text
		tNumeroNit.setTextLimit(10);
		tFax.setTextLimit(12);
		tCiudadMunicipio.setTextLimit(60);
		tEmail.setTextLimit(40);
		tDireccion.setTextLimit(60);
		tTelefono.setTextLimit(12);
		tPaginaWeb.setTextLimit(20);
		
		sShell.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Proveedores.gif"));
		
		//Se configuran las columnas
		TableColumn clNombres = new TableColumn(tbContactos, SWT.CENTER);
		TableColumn clApellidos= new TableColumn(tbContactos, SWT.CENTER);
		TableColumn clTelefono = new TableColumn(tbContactos, SWT.CENTER);
		TableColumn clCorreo = new TableColumn(tbContactos, SWT.CENTER);
		TableColumn clFacebook = new TableColumn(tbContactos, SWT.CENTER);
		TableColumn clTwitter = new TableColumn(tbContactos, SWT.CENTER);
		
		tbContactos.setHeaderVisible(true);

		    
		
	}

	//**************************** Eventos de foco ********************************************************

	@Override
	public void focusGained(FocusEvent a) {

		Object fuente = a.getSource();
		
		if (fuente.getClass().toString().contains("Text")) {

			Text fuenteTexto = ((Text) fuente);
			fuenteTexto.selectAll();
			fuenteTexto.setBackground(getVisualAtributoGanaFocoComponentes());

		}
		
	}


	@Override
	public void focusLost(FocusEvent a) {
		
		Object fuente = a.getSource();

		if (fuente == tNumeroNit && !tNumeroNit.getText().isEmpty()) 

			consultar();
		else

			if (fuente == tEmail) 

				if (!esEmail(tEmail.getText())) {

					mensajeError(sShell, "Cuenta de correo " +tEmail.getText() + " no valida");
					tEmail.setText("");
					tEmail.forceFocus();


				}
		
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

		if (fuente == toolBar.getbLimpiar())

			limpiar(true);

		else

			if (fuente == toolBar.getbGuardar())

				guardar();
			else
				if (fuente == toolBar.getbEliminar())

					eliminar();
				else
					if(fuente == toolBar.getbSalir())

						sShell.close();

	}
	
	
	
	//******************* Interfaz Grafica*****************************************
	/**
	 * This method initializes sShell
	 * @param shellPadre 
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		
		sShell.setText("Proveedor");
		createIdentificacion();
		sShell.setLayout(null);
		createGDatosPersonales();
		createGrupoContactos();
		sShell.setBounds(new Rectangle(175, 175, 800, 600));
	}

	/**
	 * This method initializes Identificacion	
	 *
	 */
	private void createIdentificacion() {
		Identificacion = new Group(sShell, SWT.SHADOW_IN);
		Identificacion.setLayout(null);
		Identificacion.setText("Identificación");
		Identificacion.setBounds(new Rectangle(202, 62, 410, 76));
		lTipo = new Label(Identificacion, SWT.NONE);
		lTipo.setBounds(new Rectangle(21, 20, 36, 15));
		lTipo.setText("Nit:");
		lRazonSocial = new Label(Identificacion, SWT.NONE);
		lRazonSocial.setBounds(new Rectangle(149, 20, 80, 15));
		lRazonSocial.setText("Razón Social:");
		tNumeroNit = new Text(Identificacion, SWT.BORDER);
		tNumeroNit.setBounds(new Rectangle(21, 35, 103, 23));
		tRazonSocial = new Text(Identificacion, SWT.BORDER);
		tRazonSocial.setBounds(new Rectangle(149, 35, 245, 23));
		
	}

	/**
	 * This method initializes gDatosPersonales	
	 *
	 */
	private void createGDatosPersonales() {
		gDatosPersonales = new Group(sShell, SWT.NONE);
		gDatosPersonales.setLayout(null);
		gDatosPersonales.setText("Datos Generales");
		gDatosPersonales.setBounds(new Rectangle(45, 148, 729, 220));
		
		tDireccion = new Text(gDatosPersonales, SWT.BORDER);
		tDireccion.setBounds(new Rectangle(69, 31, 417, 23));
		lDireccion = new Label(gDatosPersonales, SWT.NONE);
		lDireccion.setBounds(new Rectangle(9, 35, 57, 15));
		lDireccion.setText("Dirección: ");
		tTelefono = new Text(gDatosPersonales, SWT.BORDER);
		tTelefono.setBounds(new Rectangle(497, 31, 90, 23));
		lFax = new Label(gDatosPersonales, SWT.NONE);
		lFax.setBounds(new Rectangle(623, 13, 38, 15));
		lFax.setText("Fax:");
		tFax = new Text(gDatosPersonales, SWT.BORDER);
		tFax.setBounds(new Rectangle(621, 31, 90, 23));
		
		
		createCPais();

		createCDepartamentos();
		lDepartamento = new Label(gDatosPersonales, SWT.NONE);
		lDepartamento.setBounds(new Rectangle(232, 69, 88, 15));
		lDepartamento.setText("Departamento");
		lCiudadMunicipio = new Label(gDatosPersonales, SWT.NONE);
		lCiudadMunicipio.setBounds(new Rectangle(416, 69, 202, 15));
		lCiudadMunicipio.setText("Ciudad / Municipio:");
		tCiudadMunicipio = new Text(gDatosPersonales, SWT.BORDER);
		tCiudadMunicipio.setBounds(new Rectangle(416, 87, 293, 23));
		lPaginaWeb = new Label(gDatosPersonales, SWT.NONE);
		lPaginaWeb.setBounds(new Rectangle(189, 137, 68, 15));
		lPaginaWeb.setText("Pagina Web:");
		tPaginaWeb = new Text(gDatosPersonales, SWT.BORDER);
		tPaginaWeb.setBounds(new Rectangle(270, 133, 288, 23));
		
		tEmail = new Text(gDatosPersonales, SWT.BORDER);
		tEmail.setBounds(new Rectangle(269, 174, 288, 23));
		lEmail = new Label(gDatosPersonales, SWT.NONE);
		lEmail.setBounds(new Rectangle(221, 180, 36, 15));
		lEmail.setText("Email:");
		
		lTelefono = new Label(gDatosPersonales, SWT.NONE);
		lTelefono.setBounds(new Rectangle(497, 13, 81, 15));
		lTelefono.setText("Teléfono:");
		lPais = new Label(gDatosPersonales, SWT.NONE);
		lPais.setBounds(new Rectangle(37, 91, 32, 15));
		lPais.setText("País");
		}

	/**
	 * This method initializes cDepartamentos	
	 *
	 */
	private void createCDepartamentos() {
		cDepartamentos = new Combo(gDatosPersonales, SWT.READ_ONLY);
		cDepartamentos.setBounds(new Rectangle(232, 87, 156, 23));

		cargarDepatamentos(cDepartamentos); //Se cargan los departamentos

		cDepartamentos.select(27); //Se selecciona por defecto sucre

	}

	/**
	 * This method initializes cPais	
	 *
	 */
	private void createCPais() {
		cPais = new Combo(gDatosPersonales, SWT.READ_ONLY);
		cPais.setBounds(new Rectangle(73, 87, 132, 23));
		cPais.add("Colombia");
		cPais.add("Panama");
		cPais.select(0);
		
	}

	/**
	 * This method initializes grupoContactos	
	 *
	 */
	private void createGrupoContactos() {
		grupoContactos = new Group(sShell, SWT.NONE);
		grupoContactos.setLayout(null);
		grupoContactos.setText("Contactos");
		grupoContactos.setBounds(new Rectangle(46, 379, 723, 170));
		tbContactos = new Table(grupoContactos, SWT.NONE);
		tbContactos.setHeaderVisible(true);
		tbContactos.setLinesVisible(true);
		tbContactos.setBounds(new Rectangle(14, 30, 695, 92));
		bAgregar = new Button(grupoContactos, SWT.NONE);
		bAgregar.setBounds(new Rectangle(101, 132, 106, 30));
		bAgregar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Adicionar.gif"));
		bAgregar.setText("Agregar");
		bEditar = new Button(grupoContactos, SWT.NONE);
		bEditar.setBounds(new Rectangle(308, 132, 106, 30));
		bEditar.setText("Editar");
		bEditar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Editar.gif"));
		bEliminar = new Button(grupoContactos, SWT.NONE);
		bEliminar.setBounds(new Rectangle(515, 132, 106, 30));
		bEliminar.setText("Eliminar");
		bEliminar.setImage(new Image(Display.getCurrent(), "D:/Backup/workspace/SIPY/imagenes/Eliminar.gif"));
	}




}
