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
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesAccesoDatos.SentenciaPreparada;
import com.JASoft.componentesGraficos.ConfigurarText;
import com.JASoft.componentesGraficos.ToolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

public class GUIAbono extends ConfigurarText implements FocusListener,
SelectionListener,TraverseListener {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Group grupoCliente = null;
	private Label lTipo = null;
	private Label lNumero = null;
	private Text tNombres = null;
	private Text tNumero = null;
	private Combo cTipo = null;
	private Label lNombres = null;
	private Label lSaldo = null;
	private Text tSaldo = null;
	private Label lAbono = null;
	private Label lSaldo1 = null;
	private Text tAbono = null;
	private Text tNuevoSaldo = null;
	private ConectarMySQL conectarMySQL = null;
	private PreparedStatement consultar; // @jve:decl-index=0:
	private ArrayList<Text> arrayTextRequeridos = null; // @jve:decl-index=0:
	private Text taObservaciones;
	private ToolBar toolBar = null;
	
	
	/*
	 * Constructor general
	 */
	public GUIAbono(ConectarMySQL conectarMySQL, Shell shellPadre) {

		this.conectarMySQL = conectarMySQL;

		createSShell(shellPadre);

		tNumero.forceFocus(); // Se asigna el foco al primer componente  navegable
		
		agregarEventos();

		crearArrayRequeridos();

		sShell.open();

	}
	
	
	/*
	 * Metodo Limpiar: Limpia los datos de la GUI clientes
	 */

	private void limpiar(boolean comodin) {

		cTipo.select(0);
		tNombres.setText("");
		tNuevoSaldo.setText("");
		tSaldo.setText("");
		tAbono.setText("");
		tNumero.setText("");
		
		tNumero.forceFocus();
	}
	
	/*
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void guardar() {

		if (validarRegistro(arrayTextRequeridos, sShell)) {

			try {


				PreparedStatement llamarProcedimientoAlmacenado = SentenciaPreparada.getEjecutarProcedimietoAlmacenadoAbonos(conectarMySQL.getConexion());


				 // Para insertar la imagen se extraeran los byte del archivo
			
				llamarProcedimientoAlmacenado.setInt(1, 1);
				llamarProcedimientoAlmacenado.setString(2, getTipoDocumento());
				llamarProcedimientoAlmacenado.setString(3, tNumero.getText());
				llamarProcedimientoAlmacenado.setInt(4,new Integer(tAbono.getText()));
				llamarProcedimientoAlmacenado.setString(5,null);
				llamarProcedimientoAlmacenado.setString(6, taObservaciones.getText());
				llamarProcedimientoAlmacenado.setString(7,null);
				
				llamarProcedimientoAlmacenado.execute(); //Se ejecuta el procedimiento almacenado

				conectarMySQL.commit();

				mensajeInformacion(sShell,"Abono ha sido registrado");
				limpiar(true);



			} catch (Exception e) {

				mensajeError(sShell,
						"Error al guardar el registro de clientes " + e);
			}
		}
	}

	
	/*
	 * Metodo eliminar: Se consulta un cliente
	 */
	private void consultar() {

		try {

			String sentenciaSQL = "Select Nombres,Apellidos,Saldo "
				+ "From Clientes C, Municipioscorregimientos m "
				+ "Where c.tipoId= ?  and c.idCliente = ? and c.divisionPolitica = m.divisionPolitica ";

			if (consultar == null)

				consultar = conectarMySQL.getConexion().prepareStatement(
						sentenciaSQL);

			consultar.setString(1, getTipoDocumento());
			consultar.setString(2, tNumero.getText());

			ResultSet resultado = consultar.executeQuery();

			if (resultado.next()) { // Se extraen los elementos

				tNombres.setText(devuelveCadenaVaciaParaNulo(resultado.getString(1) + resultado.getString(2)));
				tSaldo.setText(resultado.getString(3));
				

			} 

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
	 * Metodo crearArrayRequeridos: configura una Array con los Text requeridos
	 */
	private void crearArrayRequeridos() {

		arrayTextRequeridos = new ArrayList<Text>();

		arrayTextRequeridos.add(tNumero);
		arrayTextRequeridos.add(tAbono);
		

	}
	
	/*
	 * Metodo agregarEventos: agregar eventos a os componentes de la GUI
	 */
	private void agregarEventos(){

		//Se agregar eventos de focos a tNumeroCelular,tNumeroCelular1 para validar la catidad de numeros
		tNumero.addFocusListener(this);
        tAbono.addFocusListener(this);
        
        tNumero.addVerifyListener(getValidarEntradaNumeros());
        tAbono.addVerifyListener(getValidarEntradaNumeros());
        
        tAbono.addTraverseListener(this);
        
        tSaldo.setEnabled(false);
        tNuevoSaldo.setEnabled(false);
        
        
        //Se agregan los eventos a los botones de la Toolbar
		toolBar.getbLimpiar().addSelectionListener(this);
		toolBar.getbGuardar().addSelectionListener(this);
		toolBar.getbEliminar().addSelectionListener(this);
		toolBar.getbSalir().addSelectionListener(this);
	

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
	
	// **************************** Eventos de accion *******************************************************

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
					if (fuente == toolBar.getbSalir())

						sShell.close();

	}
	
	//********************************* Eventos de validacion ***************************************************
	public void keyTraversed(TraverseEvent e) {
		
		if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS ) {
			
			Object fuente = e.getSource();
			
			if (fuente == tAbono) {
				
				if (!tAbono.getText().isEmpty()) {
					
					int abono = new Integer(tAbono.getText());
					int saldo = new Integer(tSaldo.getText());
					int nuevoSaldo = saldo -abono;
					
					if (nuevoSaldo < 0) {
						
						mensajeError(sShell, "El abono no puede superar el saldo");
						e.doit = false;
						
					} else
						
						tNuevoSaldo.setText(String.valueOf(nuevoSaldo));
						
						
						
					
					
				}
				
			}
			
			
		}
	}
	/**************************************************************************************************************
	 * ******************************* Interfaz Grafica **********************************************************
	 * ************************************************************************************************************/
	 
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell(Shell shellPadre) {
		sShell = new Shell(shellPadre);
		toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Abonos");
		createGrupoCliente();
		sShell.setLayout(null);
		sShell.setBounds(new Rectangle(150, 150, 800, 600));
	}
	/**
	 * This method initializes grupoCliente	
	 *
	 */
	private void createGrupoCliente() {
		grupoCliente = new Group(sShell, SWT.SHADOW_IN);
		grupoCliente.setLayout(null);
		grupoCliente.setText("Cliente");
		grupoCliente.setBounds(new Rectangle(27, 76, 743, 344));
		createCTipo();
		lTipo = new Label(grupoCliente, SWT.NONE);
		lTipo.setBounds(new Rectangle(31, 24, 36, 15));
		lTipo.setText("Tipo:");
		lNumero = new Label(grupoCliente, SWT.NONE);
		lNumero.setBounds(new Rectangle(173, 24, 61, 15));
		lNumero.setText("Número:");
		tNombres = new Text(grupoCliente, SWT.BORDER);
		tNombres.setBounds(new Rectangle(335, 41, 374, 23));
		tNumero = new Text(grupoCliente, SWT.BORDER);
		tNumero.setBounds(new Rectangle(173, 41, 131, 23));
		lNombres = new Label(grupoCliente, SWT.NONE);
		lNombres.setBounds(new Rectangle(335, 24, 60, 15));
		lNombres.setText("Nombres:");
		lSaldo = new Label(grupoCliente, SWT.NONE);
		lSaldo.setBounds(new Rectangle(101, 139, 39, 15));
		lSaldo.setText("Saldo:");
		tSaldo = new Text(grupoCliente, SWT.BORDER| SWT.RIGHT);
		tSaldo.setBounds(new Rectangle(101, 156, 113, 21));
		lAbono = new Label(grupoCliente, SWT.NONE);
		lAbono.setBounds(new Rectangle(315, 139, 46, 15));
		lAbono.setText("Abono:");
		lSaldo1 = new Label(grupoCliente, SWT.NONE);
		lSaldo1.setBounds(new Rectangle(529, 139, 86, 15));
		lSaldo1.setText("Nuevo Saldo:");
		tAbono = new Text(grupoCliente, SWT.BORDER | SWT.RIGHT);
		tAbono.setBounds(new Rectangle(315, 156, 113, 21));
		tNuevoSaldo = new Text(grupoCliente, SWT.BORDER | SWT.RIGHT);
		tNuevoSaldo.setBounds(new Rectangle(529, 156, 113, 21));
		taObservaciones = new Text(grupoCliente, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		taObservaciones.setBounds(new Rectangle(86, 219, 569, 114));
		Label lObservaciones = new Label(grupoCliente, SWT.NONE);
		lObservaciones.setBounds(new Rectangle(86, 200, 89, 15));
		lObservaciones.setText("Observaciones:");
	}
	/**
	 * This method initializes cTipo	
	 *
	 */
	private void createCTipo() {
		cTipo = new Combo(grupoCliente, SWT.NONE);
		cTipo.setBounds(new Rectangle(31, 41, 111, 23));
		cTipo.add("Cedula de Ciudadania");
		cTipo.add("Tarjeta de Identidad");
		cTipo.add("Pasaporte");
		cTipo.add("Cedula de Extranjeria");
		cTipo.select(0);
	}

}
