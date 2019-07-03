/**
 * Clase: GUICliente
 * 
 * @version  1.0
 * 
 * @since 19-11-2010
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ListarValor;
import com.JASoft.componentesGraficos.ToolBar;


/*
 *  Esta clase permite gestionar los clientes contra la base de datos
 */

public class GUICliente extends ConfigurarText implements FocusListener,
SelectionListener {

	private Shell sShell = null;
	private Group Identificacion = null;
	private Label lTipo = null;
	private Combo cTipo = null;
	private Label lNumero = null;
	private Text tNumero = null;
	private Group gDatosPersonales = null;
	private Label label = null;
	private Text tNombres = null;
	private Label lApellidos = null;
	private Text tApellidos = null;
	private Label lSexo = null;
	private Combo cSexo = null;
	private Label lDireccion = null;
	private Text tDireccion = null;
	private Combo cDepartamentos = null;
	private Label lDepartamento = null;
	private Label lCiudadMunicipio = null;
	private Text tCiudadMunicipio = null;
	private Group gContactos = null;
	private Label lCelular = null;
	private Label lCelular1 = null;
	private Text tNumeroCelular = null;
	private Text tNumeroCelular1 = null;
	private Text tEmpresa = null;
	private Label lEmpresa = null;
	private Text tDireccionEmpresa = null;
	private Label lDireccionEmpresa = null;
	private Label lCorreoElectronico = null;
	private Text tCorreoElectronico = null;
	private Label lCuentaFacebook = null;
	private Text tFaceBook = null;
	private Label lCuentaTwiter = null;
	private Text tTwiter = null;
	private Group grupoCredito = null;
	private Label lSaldo = null;
	private Text tSaldo = null;
	private Label lCupo = null;
	private Text tCupo = null;
	private Text tTelefonoEmpresa = null;
	private Label lTelefonoEmpresa = null;
	private Label lLugarResidencia = null;
	private Text tLugarResidencia = null;
	private Text tTelefonoResidencia = null;
	private Label lTelefono = null;
	private ArrayList<Text> arrayTextRequeridos = null; // @jve:decl-index=0:
	private Combo cAbonos = null;
	private Label lAbonos = null;
	private ToolBar toolBar = null;
	private ConectarMySQL conectarMySQL = null;
	private PreparedStatement consultar; // @jve:decl-index=0:
	private String divisionPolitica = null;
	private Shell shellPadre;
	private ListarValor listarValor;
	private boolean retornarVentasProductos = false;

	/*
	 * Constructor general
	 */
	public GUICliente(ConectarMySQL conectarMySQL, Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		tNumero.forceFocus(); // Se asigna el foco al primer componente navegable

		agregarEventos();
		
		agregarConfiguraciones();

		crearArrayRequeridos();

		sShell.open();

	}
	
	
	/*
	 * Constructor general
	 */
	public GUICliente(ConectarMySQL conectarMySQL, Shell shellPadre,int tipoId, String numeroID) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		cTipo.select(tipoId);
		
		tNumero.setText(numeroID);
		
		tNombres.forceFocus();
		
		agregarEventos();

		crearArrayRequeridos();
		
		retornarVentasProductos = true;
		
		consultar();

		sShell.open();

	}

	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	private void limpiar(boolean comodin) {

		cSexo.select(1);
		cDepartamentos.select(0);
		cAbonos.select(1);
		cDepartamentos.select(27);

		tNombres.setText("");
		tApellidos.setText("");
		tLugarResidencia.setText("");
		tDireccion.setText("");
		tCiudadMunicipio.setText("");

		tNumeroCelular.setText("");
		tNumeroCelular1.setText("");
		tEmpresa.setText("");
		tDireccionEmpresa.setText("");
		tCorreoElectronico.setText("");
		tFaceBook.setText("");
		tTwiter.setText("");
		tTelefonoEmpresa.setText("");
		tTelefonoResidencia.setText("");
		tNumeroCelular.setText("");
		tNumeroCelular1.setText("");
		tSaldo.setText("");
		tCiudadMunicipio.setText("SINCELEJO");
		divisionPolitica = getDepartamentos()[cDepartamentos
		                                      .getSelectionIndex()][2];

		if (comodin) {
			cTipo.select(0);
			tNumero.setText("");
			tNumero.forceFocus();
		}

	}

	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void guardar() {

		if (validarRegistro(arrayTextRequeridos, sShell)) {

			try {

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada
				.getEjecutarProcedimietoAlmacenadoCliente(conectarMySQL
						.getConexion());

				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, cTipo
						.getSelectionIndex() == 0 ? "C" : cTipo
								.getSelectionIndex() == 1 ? "T" : cTipo
										.getSelectionIndex() == 2 ? "P" : "E");
				llamarProcedimientoAlmacenado.setString(3, tNumero.getText());
				llamarProcedimientoAlmacenado.setString(4, tNombres.getText());
				llamarProcedimientoAlmacenado
				.setString(5, tApellidos.getText());

				llamarProcedimientoAlmacenado
				.setString(6, tDireccion.getText());
				llamarProcedimientoAlmacenado.setString(7, tDireccionEmpresa
						.getText());
				llamarProcedimientoAlmacenado.setString(8, tTelefonoResidencia
						.getText());
				llamarProcedimientoAlmacenado.setString(9, tTelefonoEmpresa
						.getText());

				llamarProcedimientoAlmacenado.setString(10, tNumeroCelular
						.getText());
				llamarProcedimientoAlmacenado.setString(11, tNumeroCelular1
						.getText());
				llamarProcedimientoAlmacenado.setString(12, tCorreoElectronico
						.getText());
				llamarProcedimientoAlmacenado
				.setString(13, tFaceBook.getText());
				llamarProcedimientoAlmacenado.setString(14, tTwiter.getText());
				llamarProcedimientoAlmacenado.setString(15, tCupo.getText());
				llamarProcedimientoAlmacenado.setString(16, tSaldo.getText());
				llamarProcedimientoAlmacenado.setString(17, String
						.valueOf(cSexo.getText().charAt(0)));
				llamarProcedimientoAlmacenado.setInt(18, 0);
				llamarProcedimientoAlmacenado.setString(19, String
						.valueOf(cAbonos.getText().charAt(0)));
				llamarProcedimientoAlmacenado.setString(20, tLugarResidencia
						.getText());
				llamarProcedimientoAlmacenado.setString(21, tEmpresa.getText());

				llamarProcedimientoAlmacenado.execute(); // Se ejecuta el
				// procedimiento
				// almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell, "Cliente ha sido registrado");
				
				if (retornarVentasProductos)
					
					sShell.close();
				
				else
					
				   limpiar(true);

			} catch (Exception e) {

				mensajeError(sShell,
						"Error al guardar el registro de clientes " + e);
			}
		}
	}

	/*
	 * Metodo eliminar: Elimina un cliente
	 */
	private void eliminar() {

		if (!tNumero.getText().isEmpty()) {

			try {

				mensajeConfirmacion(sShell,"Esta seguro que desea eliminar este cliente?");

				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada
				.getEjecutarProcedimietoAlmacenadoCliente(conectarMySQL
						.getConexion());

				llamarProcedimientoAlmacenado.setInt(1, 0);
				llamarProcedimientoAlmacenado.setString(2, getTipoDocumento());
				llamarProcedimientoAlmacenado.setString(3, null);
				llamarProcedimientoAlmacenado.setString(4, null);
				llamarProcedimientoAlmacenado.setString(5, null);

				llamarProcedimientoAlmacenado.setString(6, null);
				llamarProcedimientoAlmacenado.setString(7, null);
				llamarProcedimientoAlmacenado.setString(8, null);
				llamarProcedimientoAlmacenado.setString(9, null);

				llamarProcedimientoAlmacenado.setString(10, null);
				llamarProcedimientoAlmacenado.setString(11, null);
				llamarProcedimientoAlmacenado.setString(12, null);
				llamarProcedimientoAlmacenado.setString(13, null);
				llamarProcedimientoAlmacenado.setString(14, null);
				llamarProcedimientoAlmacenado.setString(15, null);
				llamarProcedimientoAlmacenado.setString(16, null);
				llamarProcedimientoAlmacenado.setString(17, null);
				llamarProcedimientoAlmacenado.setInt(18, 0);
				llamarProcedimientoAlmacenado.setString(19, null);
				llamarProcedimientoAlmacenado.setString(20, null);
				llamarProcedimientoAlmacenado.setString(21, null);

				llamarProcedimientoAlmacenado.execute(); // Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell, "Cliente ha sido eliminado");

				limpiar(true);

			} catch (Exception e) {

				mensajeError(
						sShell,
						"Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas"
						+ e);
			}

		}

	}

	/*
	 * Metodo eliminar: Se consulta un cliente
	 */
	private void consultar() {

		try {

			String sentenciaSQL = "Select Nombres,Apellidos,DireccionResidencia,DireccionLaboral,TelefonoResidencia,TelefonoLaboral,NumeroCelular,NumeroCelular1,CorreoElectronico,CuentaFacebook,CuentaTwiter,Saldo,Sexo,c.DivisionPolitica,m.municipio,TiempoAbonos,LugarResidencia,Empresa "
				+ "From Clientes C, Municipioscorregimientos m "
				+ "Where c.tipoId= ?  and c.idCliente = ? and c.divisionPolitica = m.divisionPolitica ";

			if (consultar == null)

				consultar = conectarMySQL.getConexion().prepareStatement(
						sentenciaSQL);

			consultar.setString(1, getTipoDocumento());
			consultar.setString(2, tNumero.getText());

			ResultSet resultado = consultar.executeQuery();

			if (resultado.next()) { // Se extraen los elementos

				tNombres.setText(devuelveCadenaVaciaParaNulo(resultado.getString(1)));
				tApellidos.setText(devuelveCadenaVaciaParaNulo(resultado.getString(2)));
				tDireccion.setText(devuelveCadenaVaciaParaNulo(resultado.getString(3)));
				tDireccionEmpresa.setText(devuelveCadenaVaciaParaNulo(resultado.getString(4)));
				tTelefonoResidencia.setText(devuelveCadenaVaciaParaNulo(resultado.getString(5)));
				tTelefonoEmpresa.setText(devuelveCadenaVaciaParaNulo(resultado.getString(6)));
				tNumeroCelular.setText(devuelveCadenaVaciaParaNulo(resultado.getString(7)));
				tNumeroCelular1.setText(devuelveCadenaVaciaParaNulo(resultado.getString(8)));
				tCorreoElectronico.setText(devuelveCadenaVaciaParaNulo(resultado.getString(9)));
				tFaceBook.setText(devuelveCadenaVaciaParaNulo(resultado.getString(10)));
				tTwiter.setText(devuelveCadenaVaciaParaNulo(resultado.getString(11)));
				tSaldo.setText(getFormatoMoneda(resultado.getInt(12)));
				cSexo.select(resultado.getString(13).equals("M") ? 0 : 1);
				divisionPolitica = devuelveCadenaVaciaParaNulo(resultado.getString(14));
				tCiudadMunicipio.setText(devuelveCadenaVaciaParaNulo(resultado.getString(15)));
				cAbonos.select(getTiempoAbono(devuelveCadenaVaciaParaNulo(resultado.getString(16))));
				tLugarResidencia.setText(devuelveCadenaVaciaParaNulo(resultado.getString(17)));
				tEmpresa.setText(devuelveCadenaVaciaParaNulo(resultado.getString(18)));
				

			} else

				limpiar(false);

		} catch (SQLException e) {

			mensajeError(
					sShell,
					"Este cliente tiene asociadas un conjunto de facturas, si desea eliminarlos primero debe eliminar las facturas asociadas"
					+ e);
		}

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
	 * Metodo getTipoDocumento: Devuelve el caracter del tipo de documento como
	 * se especifico en el tabla
	 */
	private int getTiempoAbono(String pivote) {

		int resultado = pivote.equals("Q") ? 0 : pivote.equals("M") ? 1
				: pivote.equals("M") ? 2 : 3;

		return resultado;

	}

	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void crearArrayRequeridos() {

		arrayTextRequeridos = new ArrayList<Text>();

		arrayTextRequeridos.add(tNumero);
		arrayTextRequeridos.add(tNombres);
		arrayTextRequeridos.add(tApellidos);
		arrayTextRequeridos.add(tLugarResidencia);
		arrayTextRequeridos.add(tDireccion);
		arrayTextRequeridos.add(tCiudadMunicipio);

	}

	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	private void agregarEventos(){

		//Se agrega un evento al combo departamentos
		cDepartamentos.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				tCiudadMunicipio.setText(getDepartamentos()[cDepartamentos.getSelectionIndex()][3]);
				divisionPolitica = getDepartamentos()[cDepartamentos.getSelectionIndex()][2];
				tCiudadMunicipio.selectAll();

			}
		});
		
		
		// Se configuran los parametros para la lista de valores
		String sentenciaSQL = "Select TipoId Tipo,idCliente Identificacion,Nombres,Apellidos From Clientes Where Nombres ";

		
		int anchoColumnas[] = { 40, 100, 160, 160 };
		
		Object[][] objetosRetorno = new Object[3][2];
		objetosRetorno[0][0] = tNumero;
		objetosRetorno[0][1] = 1;
		objetosRetorno[1][0] = tNombres;
		objetosRetorno[1][1] = 2;
		objetosRetorno[2][0] = tApellidos;
		objetosRetorno[2][1] = 3;
		

		listarValor.setListarValor(conectarMySQL.getSentencia(), sentenciaSQL,objetosRetorno,
				null, anchoColumnas);
		
		//tNombres.addKeyListener(listarValor);


		//Se agregar eventos de focos a tNumeroCelular,tNumeroCelular1 para validar la catidad de numeros
		tNumeroCelular.addFocusListener(this);
		tNumeroCelular1.addFocusListener(this);


		tCorreoElectronico.addFocusListener(this);
		tTwiter.addFocusListener(this);
		tFaceBook.addFocusListener(this);

		//Se agregar eventos de focos a tEmpresa para que consulte el cliente
		tNumero.addFocusListener(this);
		tNombres.addFocusListener(this);
		tApellidos.addFocusListener(this);
		tDireccion.addFocusListener(this);
		tDireccionEmpresa.addFocusListener(this);
		tEmpresa.addFocusListener(this);
		tLugarResidencia.addFocusListener(this);
		tTelefonoEmpresa.addFocusListener(this);
		tTelefonoResidencia.addFocusListener(this);

		//Se agregan los eventos de validacion
		tNumeroCelular.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

					if (!tNumeroCelular.getText().isEmpty()) {

						if (!validaNumeroCelular( tNumeroCelular.getText())) {

							mensajeError(sShell, "El número del celular debe contener  10 números");
							e.doit = false;

						} else

							if (tNumeroCelular.getText().equals(tNumeroCelular1.getText())) {

								mensajeError(sShell, "Los numeros de celulares deben ser diferentes");
								e.doit = false;

							}

					}

				}
			}
		});

		tNumeroCelular1.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

					if (!tNumeroCelular1.getText().isEmpty()) {

						if (!validaNumeroCelular( tNumeroCelular1.getText())) {

							mensajeError(sShell, "El número del celular debe contener  10 números");
							e.doit = false;		
						} else

							if (tNumeroCelular.getText().equals(tNumeroCelular1.getText())) {

								mensajeError(sShell, "Los numeros de celulares deben ser diferentes");
								e.doit = false;

							}

					}

				}
			}
		});


		tCorreoElectronico.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

					if (!tCorreoElectronico.getText().isEmpty()) {

						if (!esEmail(tCorreoElectronico.getText())) {

							mensajeError(sShell, "Cuenta de correo " +tCorreoElectronico.getText() + " no valida");
							e.doit = false;


						}

					}

				}
			}
		});


		tFaceBook.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {

					if (!tFaceBook.getText().isEmpty()) {

						if (!esEmail(tFaceBook.getText())) {

							mensajeError(sShell, "Cuenta Facebook " +tFaceBook.getText() + " no valida");
							e.doit = false;

						}
					}	
				}
			}
		});




		//Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);


	}

	/*
	 * Metodo agregarConfiguraciones(): se agregan cofiguraciones adicionales a los componentes
	 */
	private void agregarConfiguraciones() {
		
		tCupo.setBackground(getVisualatributodeshabilidatocomponente());
		tCupo.setForeground(getColorRojo());
		tSaldo.setBackground(getVisualatributodeshabilidatocomponente());
		tSaldo.setForeground(getColorRojo());
		
		sShell.setImage(new Image(Display.getCurrent(), "imagenes/Clientes.gif"));
		
		
	}
	
	// **************************** Eventos de foco ********************************************************

	@Override
	public void focusGained(FocusEvent a) {

		Object fuente = a.getSource();

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

		if (fuente == tNumero && !tNumero.getText().isEmpty())

			consultar();
		
		// Se agrega este evento para cambiar de color al componente que tiene el evento
		if (fuente.getClass().toString().contains("Text")) {

			Text fuenteTexto = ((Text) fuente);
			fuenteTexto.setBackground(getVisualAtributoPierdeFocoComponentes());

		}

	}

	// **************************** Eventos de accion
	// *******************************************************

	@Override
	public void widgetDefaultSelected(SelectionEvent a) {
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
					if (fuente == toolBar.getbSalir())

						sShell.close();

	}

	/******************************
	 * Interfaz Grafica *******************************************************
	 * 
	 * 
	 * /** This method initializes sShell
	 * 
	 * @param shellPadre
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		// Se agrega la toolbar
		toolBar = new ToolBar(sShell, SWT.BORDER);
		toolBar.setBounds(new Rectangle(0, 0, 782, 48));
		sShell.setText("Clientes");
		createIdentificacion();
		sShell.setLayout(null);
		createGDatosPersonales();
		createGContactos();
		createGrupoCredito();
		sShell.setBounds(new Rectangle(125, 125, 800, 600));
		centrarShell(sShell);
	}

	/**
	 * This method initializes Identificacion
	 * 
	 */
	private void createIdentificacion() {
		Identificacion = new Group(sShell, SWT.SHADOW_IN);
		Identificacion.setLayout(null);
		Identificacion.setText("Identificación");
		Identificacion.setBounds(new Rectangle(232, 65, 363, 72));

		lTipo = new Label(Identificacion, SWT.NONE);
		lTipo.setBounds(new Rectangle(28, 20, 36, 15));
		lTipo.setText("Tipo:");
		createCTipo();
		lNumero = new Label(Identificacion, SWT.NONE);
		lNumero.setBounds(new Rectangle(211, 19, 64, 15));
		lNumero.setText("Número:");
		tNumero = new Text(Identificacion, SWT.BORDER);
		tNumero.setTextLimit(12);
		tNumero.addVerifyListener(getValidarEntradaNumeros());
		tNumero.setBounds(new Rectangle(211, 34, 141, 22));
		tNumero.setToolTipText("Digite el Número de identificación del cliente");
	}

	/**
	 * This method initializes cTipo
	 * 
	 */
	private void createCTipo() {
		cTipo = new Combo(Identificacion, SWT.READ_ONLY);
		cTipo.setToolTipText("Escoga el Tipo Identificación del cliente");
		cTipo.setBounds(new Rectangle(28, 35, 141, 23));
		cTipo.add("Cedula de Ciudadania");
		cTipo.add("Tarjeta de Identidad");
		cTipo.add("Pasaporte");
		cTipo.add("Cedula de Extranjeria");
		cTipo.select(0);

	}

	/**
	 * This method initializes gDatosPersonales
	 * 
	 */
	private void createGDatosPersonales() {
		gDatosPersonales = new Group(sShell, SWT.NONE);
		gDatosPersonales.setLayout(null);
		gDatosPersonales.setText("Datos Personales");
		gDatosPersonales.setBounds(new Rectangle(21, 156, 735, 244));
		
		
		label = new Label(gDatosPersonales, SWT.NONE);
		label.setText("Nombres");
		label.setBounds(new Rectangle(16, 27, 55, 15));

		tNombres = new Text(gDatosPersonales, SWT.BORDER);
		tNombres.setBounds(new Rectangle(16, 43, 295, 22));
		tNombres.setToolTipText("Digite el Primer y Segundo nombre del cliente");
		tNombres.setTextLimit(45);
		tNombres.addVerifyListener(getConvertirMayusculaLetras());
		tNombres.addFocusListener(this);

		 // Se instancia aqui para que salga al frente la lista de valores
		listarValor = new ListarValor(gDatosPersonales,16,50,500);
		

		lApellidos = new Label(gDatosPersonales, SWT.NONE);
		lApellidos.setBounds(new Rectangle(325, 27, 60, 15));
		lApellidos.setText("Apellidos:");
		tApellidos = new Text(gDatosPersonales, SWT.BORDER);
		tApellidos.setBounds(new Rectangle(325, 42, 295, 22));
		tApellidos.setToolTipText("Digite el Primer y Segundo apellido del cliente");
		tApellidos.setTextLimit(45);
		tApellidos.addVerifyListener(getConvertirMayusculaLetras());
		lSexo = new Label(gDatosPersonales, SWT.NONE);
		lSexo.setBounds(new Rectangle(630, 25, 49, 15));
		lSexo.setText("Sexo:");
		createCSexo();

		tEmpresa = new Text(gDatosPersonales, SWT.BORDER);
		tEmpresa.setBounds(new Rectangle(20, 99, 288, 22));
		tEmpresa.setToolTipText("Digite el nombre de la empresa donde labora el cliente (Opcional)");
		tEmpresa.addVerifyListener(getConvertirMayusculaLetras());

		lEmpresa = new Label(gDatosPersonales, SWT.NONE);
		lEmpresa.setBounds(new Rectangle(20, 83, 48, 15));
		lEmpresa.setText("Empresa:");

		tDireccionEmpresa = new Text(gDatosPersonales, SWT.BORDER);
		tDireccionEmpresa.setBounds(new Rectangle(320, 99, 290, 22));
		tDireccionEmpresa.setToolTipText("Digite la dirección de la empresa donde labora el cliente (Opcional)");
		tDireccionEmpresa.setTextLimit(60);
		tDireccionEmpresa.addVerifyListener(getConvertirMayusculaLetras());

		lDireccionEmpresa = new Label(gDatosPersonales, SWT.NONE);
		lDireccionEmpresa.setBounds(new Rectangle(320, 83, 104, 15));
		lDireccionEmpresa.setText("Dirección: ");

		tTelefonoEmpresa = new Text(gDatosPersonales, SWT.BORDER);
		tTelefonoEmpresa.setTextLimit(12);
		tTelefonoEmpresa.setBounds(new Rectangle(630, 99, 90, 21));
		tTelefonoEmpresa.setToolTipText("Digite el teléfono de la empresa donde labora el cliente (Opcional)");
		tTelefonoEmpresa.addVerifyListener(getValidarEntradaNumeros());

		lTelefonoEmpresa = new Label(gDatosPersonales, SWT.NONE);
		lTelefonoEmpresa.setBounds(new Rectangle(631, 83, 70, 15));
		lTelefonoEmpresa.setText("Teléfono");

		lLugarResidencia = new Label(gDatosPersonales, SWT.NONE);
		lLugarResidencia.setBounds(new Rectangle(24, 138, 101, 15));
		lLugarResidencia.setText("Lugar Residencia:");
		tLugarResidencia = new Text(gDatosPersonales, SWT.BORDER);
		tLugarResidencia.setBounds(new Rectangle(24, 154, 288, 21));
		tLugarResidencia.setTextLimit(40);
		tLugarResidencia
		.setToolTipText("Digite el lugar (Barrio) de residencia del cliente");
		tLugarResidencia.addVerifyListener(getConvertirMayusculaLetras());

		lDireccion = new Label(gDatosPersonales, SWT.NONE);
		lDireccion.setBounds(new Rectangle(322, 138, 74, 15));
		lDireccion.setText("Dirección");

		tDireccion = new Text(gDatosPersonales, SWT.BORDER);
		tDireccion.setBounds(new Rectangle(322, 154, 290, 22));
		tDireccion
		.setToolTipText("Digite la direccion de residencia del cliente");
		tDireccion.setTextLimit(60);
		tDireccion.addVerifyListener(getConvertirMayusculaLetras());

		tTelefonoResidencia = new Text(gDatosPersonales, SWT.BORDER);
		tTelefonoResidencia.setBounds(new Rectangle(633, 154, 90, 21));
		tTelefonoResidencia
		.setToolTipText("Digite el teléfono de la residencia del cliente (Opcional)");
		tTelefonoResidencia.setTextLimit(12);
		tTelefonoResidencia.addVerifyListener(getValidarEntradaNumeros());

		createCDepartamentos();

		lDepartamento = new Label(gDatosPersonales, SWT.NONE);
		lDepartamento.setBounds(new Rectangle(24, 190, 88, 15));
		lDepartamento.setText("Departamento");
		lCiudadMunicipio = new Label(gDatosPersonales, SWT.NONE);
		lCiudadMunicipio.setBounds(new Rectangle(254, 190, 113, 15));
		lCiudadMunicipio.setText("Ciudad / Municipio:");
		tCiudadMunicipio = new Text(gDatosPersonales, SWT.BORDER);
		tCiudadMunicipio.setText("SINCELEJO");
		tCiudadMunicipio.setBounds(new Rectangle(254, 206, 258, 23));
		tCiudadMunicipio
		.setToolTipText("Seleccione la ciudad/municipio donde reside el cliente");
		tCiudadMunicipio.addVerifyListener(getConvertirMayusculaLetras());

		lTelefono = new Label(gDatosPersonales, SWT.NONE);
		lTelefono.setBounds(new Rectangle(634, 140, 81, 15));
		lTelefono.setText("Teléfono:");
		createCAbonos();
		lAbonos = new Label(gDatosPersonales, SWT.NONE);
		lAbonos.setBounds(new Rectangle(563, 191, 51, 15));
		lAbonos.setText("Pagos:");
	}

	/**
	 * This method initializes cSexo
	 * 
	 */
	private void createCSexo() {
		cSexo = new Combo(gDatosPersonales, SWT.READ_ONLY);
		cSexo.setToolTipText("Escoga el sexo del cliente");
		cSexo.setBounds(new Rectangle(631, 41, 91, 23));
		cSexo.add("Masculino");
		cSexo.add("Femenino");
		cSexo.select(1);

	}

	/**
	 * This method initializes cDepartamentos
	 * 
	 */
	private void createCDepartamentos() {

		cDepartamentos = new Combo(gDatosPersonales, SWT.READ_ONLY);
		cDepartamentos.setToolTipText("Departamento donde reside el cliente");
		cDepartamentos.setBounds(new Rectangle(24, 206, 158, 23));

		cargarDepatamentos(cDepartamentos); // Se cargan los departamentos

		cDepartamentos.select(27); // Se selecciona por defecto sucre

	}

	/**
	 * This method initializes gContactos
	 * 
	 */
	private void createGContactos() {
		gContactos = new Group(sShell, SWT.NONE);
		gContactos.setLayout(null);
		gContactos.setText("Datos de Contacto");
		gContactos.setBounds(new Rectangle(30, 417, 480, 130));
		lCelular = new Label(gContactos, SWT.NONE);
		lCelular.setBounds(new Rectangle(38, 28, 90, 15));
		lCelular.setText("Número Celular:");
		lCelular1 = new Label(gContactos, SWT.NONE);
		lCelular1.setBounds(new Rectangle(265, 27, 91, 15));
		lCelular1.setText("Número Celular :");
		tNumeroCelular = new Text(gContactos, SWT.BORDER);
		tNumeroCelular.setBounds(new Rectangle(142, 24, 83, 23));
		tNumeroCelular
		.setToolTipText("Número de celular del cliente (Opcional)");
		tNumeroCelular.setTextLimit(12);
		tNumeroCelular.addVerifyListener(getValidarEntradaNumeros());

		tNumeroCelular1 = new Text(gContactos, SWT.BORDER);
		tNumeroCelular1.setBounds(new Rectangle(368, 24, 83, 23));
		tNumeroCelular1
		.setToolTipText("Otro número de celular del cliente, en caso que lo posea (Opcional)");
		tNumeroCelular1.setTextLimit(12);
		tNumeroCelular1.addVerifyListener(getValidarEntradaNumeros());

		tTelefonoResidencia.addVerifyListener(getValidarEntradaNumeros());
		lCorreoElectronico = new Label(gContactos, SWT.NONE);
		lCorreoElectronico.setBounds(new Rectangle(6, 64, 35, 15));
		lCorreoElectronico.setText("Email:");
		tCorreoElectronico = new Text(gContactos, SWT.BORDER);
		tCorreoElectronico.setBounds(new Rectangle(46, 60, 176, 23));
		tCorreoElectronico
		.setToolTipText("Digite el correo electronico del cliente (Opcional)");
		tCorreoElectronico.setTextLimit(60);
		lCuentaFacebook = new Label(gContactos, SWT.NONE);
		lCuentaFacebook.setBounds(new Rectangle(233, 64, 59, 15));
		lCuentaFacebook.setText("FaceBook:");
		tFaceBook = new Text(gContactos, SWT.BORDER);
		tFaceBook.setBounds(new Rectangle(294, 60, 176, 23));
		tFaceBook
		.setToolTipText("Digite la cuenta de la red social FaceBook (Opcional)");
		tFaceBook.setTextLimit(60);
		lCuentaTwiter = new Label(gContactos, SWT.NONE);
		lCuentaTwiter.setBounds(new Rectangle(129, 100, 39, 15));
		lCuentaTwiter.setText("Twiter:");
		tTwiter = new Text(gContactos, SWT.BORDER);
		tTwiter.setBounds(new Rectangle(181, 96, 209, 23));
		tTwiter.setToolTipText("Digite la cuenta Twitter (Opcional)");
		tTwiter.setTextLimit(60);

	}

	/**
	 * This method initializes grupoCredito
	 * 
	 */
	private void createGrupoCredito() {
		grupoCredito = new Group(sShell, SWT.NONE);
		grupoCredito.setLayout(null);
		grupoCredito.setText("Creditos");
		grupoCredito.setBounds(new Rectangle(530, 417, 226, 130));
		lSaldo = new Label(grupoCredito, SWT.NONE);
		lSaldo.setText("Saldo:");
		lSaldo.setBounds(new Rectangle(9, 30, 43, 15));
		tSaldo = new Text(grupoCredito, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY );
		tSaldo.setBounds(new Rectangle(54, 27, 145, 20));
		tSaldo.setText("0");
	
		tSaldo.setToolTipText("Saldo del Cliente");
		lCupo = new Label(grupoCredito, SWT.NONE);
		lCupo.setBounds(new Rectangle(10, 77, 37, 15));
		lCupo.setText("Cupo: ");
		tCupo = new Text(grupoCredito, SWT.BORDER | SWT.RIGHT | SWT.READ_ONLY );
		tCupo.setText("0");
		tCupo.setBounds(new Rectangle(52, 74, 148, 21));
		tCupo.setToolTipText("Bonos del cliente (Actualmente no aplica)");
		
	}

	/**
	 * This method initializes cAbonos
	 * 
	 */
	private void createCAbonos() {
		cAbonos = new Combo(gDatosPersonales, SWT.READ_ONLY);
		cAbonos.setBounds(new Rectangle(563, 206, 160, 23));
		cAbonos.add("Quincenales");
		cAbonos.add("Mensuales");
		cAbonos.add("Trimestrales");
		cAbonos.add("Libres");
		cAbonos.select(1);

	}

}
