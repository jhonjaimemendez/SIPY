package com.JASoft.GUI.SIPY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesGraficos.AtributoVisual;
import com.JASoft.componentesGraficos.ConfigurarText;

public class GUIDialogoVentaDetalleRealizada extends Dialog implements SelectionListener {

	private Group grupoDetalles = null;
	private Table tbVentasDetalle = null;
	private Label lTotal = null;
	private Text tTotal = null;
	private Button bAceptar = null;
	private Shell shellDialogo;
	private static PreparedStatement  consultarDetalles;  //  @jve:decl-index=0:
	private ConectarMySQL conectarMySQL = null;
	

	/*
	 * Constructor
	 */


	public GUIDialogoVentaDetalleRealizada(Shell parent,ConectarMySQL conectarMySQL, String numeroVenta,String total) {

		super(parent);
		
		this.conectarMySQL =conectarMySQL;
		
		createSShell();
		
		ConfigurarText.centrarShell(shellDialogo);
		
		agregarConfiguraciones();
		
		tTotal.setText(total);
		
		consultar(numeroVenta);
		
		shellDialogo.open();
		
	}
	
	/*
	 * Metodo agregarConfiguraciones: Agrega configuraciones adicionales a los componentes
	 */
	private void agregarConfiguraciones(){

		//Se configuran las columnas
		TableColumn clCodigoProducto = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clNombreProducto = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clCodigoExistencia = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clTalla = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clMarca = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clColor = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clCantidad  = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clPrecioVenta = new TableColumn(tbVentasDetalle, SWT.CENTER);
		TableColumn clSubtotal = new TableColumn(tbVentasDetalle, SWT.CENTER);
		
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
		clCodigoProducto.setWidth(50);
		clNombreProducto.setWidth(164);
		clCodigoExistencia.setWidth(64);
		clTalla.setWidth(39);
		clColor.setWidth(124);
		clMarca.setWidth(114);
		clCantidad.setWidth(61);
		clPrecioVenta.setWidth(62);
		clSubtotal.setWidth(65);
		
		shellDialogo.setImage(new Image(Display.getCurrent(), "imagenes/VentasFacturacion.gif"));
		
		//Se agregan las configuraciones para los texto
		tTotal.setForeground(AtributoVisual.getColorRojo());
		
		//Se agregan los eventos a los botones
		bAceptar.addSelectionListener(this);
		


	}

	/*
	 * Metodo consultar: Se consulta Detalles de una factura
	 */
	private boolean consultar(String numeroVenta) {
		
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

				 TableItem item = new TableItem(tbVentasDetalle, SWT.NONE);
		
				 item.setText(new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6),resultado.getString(7),ConfigurarText.getFormatoMoneda(resultado.getString(8)),ConfigurarText.getFormatoMoneda(resultado.getString(9))});


			 }
			
			
			
		} catch (SQLException s) {
			
			s.printStackTrace();
		}
		
		return resultadoBoolean;
			
		
	}	
	//********************************** Interfaz Grafica**************************************************

			
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {

		shellDialogo = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		shellDialogo.setSize(new Point(793, 355));
		shellDialogo.setLayout(null);

		shellDialogo.setText("Detalle Compra");
		createGrupoDetalles();
		shellDialogo.setLayout(null);
		bAceptar = new Button(shellDialogo, SWT.NONE);
		bAceptar.setBounds(new Rectangle(344, 279, 130, 26));
		bAceptar.setImage(new Image(Display.getCurrent(), "C:/DESARROLLOS DE SOFTWARE/SIPY/imagenes/OK.gif"));
		bAceptar.setText("Aceptar");
	}

	//***************************************************** Eventos ***********************************************************
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent a) {
		
		Object fuente = a.getSource();

		if (fuente == bAceptar) 
             
			 shellDialogo.close();	
			

	}
	
	/**
	 * This method initializes grupoDetalles	
	 *
	 */
	private void createGrupoDetalles() {
		grupoDetalles = new Group(shellDialogo, SWT.NONE);
		grupoDetalles.setLayout(null);
		grupoDetalles.setBounds(new Rectangle(3, 8, 764, 257));
		tbVentasDetalle = new Table(grupoDetalles, SWT.FULL_SELECTION);
		tbVentasDetalle.setHeaderVisible(true);
		tbVentasDetalle.setLinesVisible(true);
		tbVentasDetalle.setBounds(new Rectangle(8, 16, 745, 196));
		lTotal = new Label(grupoDetalles, SWT.NONE);
		lTotal.setBounds(new Rectangle(5610, 222, 40, 15));
		lTotal.setText("Total:");
		tTotal = new Text(grupoDetalles, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotal.setBounds(new Rectangle(658, 218, 95, 23));
	}

}
