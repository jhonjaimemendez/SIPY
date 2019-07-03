/*
 * Clase: ListarValor
 * 
 * Version : 1.0
 * 
 * Fecha: 18-10-2005
 * 
 * Copyright: Ing.  Jhon Mendez
 */

package com.JASoft.componentesGraficos;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import com.JASoft.GUI.SIPY.GUIListaValores;



/**
 * Esta clase configura los eventos para un Text que posea
 * Lista de valores
 */


final public class ListarValor extends KeyAdapter {

	private Object [][] camposRetornos;

	private String sentenciaSQL; 

	private Control componenteObtieneFoco;

	private int anchoContenedor;

	private int[] anchoColumnas;

	private Statement sentenciaBD;

	private GUIListaValores guiListaValores;

	private boolean patronCompleto = false;

	private Text texto;

	private int numeroColumnas = 0;


	/**
	 * Constructor General
	 */

	public ListarValor(Group grupo,int posX, int posY, int anchoContenedor) {


		this.anchoContenedor = anchoContenedor;

		//Se agrega el composite de primero para que se muestre en el frente, se agrega no visible
		guiListaValores = new GUIListaValores(grupo, SWT.None);
		guiListaValores.setBounds(posX,posY,anchoContenedor, 160);
		guiListaValores.moveAbove(grupo);
		guiListaValores.setVisible(false);




	}

	public ListarValor(Shell shell,int posX, int posY, int anchoContenedor) {


		this.anchoContenedor = anchoContenedor;

		//Se agrega el composite de primero para que se muestre en el frente, se agrega no visible
		guiListaValores = new GUIListaValores(shell, SWT.None);
		guiListaValores.setBounds(posX,posY,anchoContenedor, 160);
		guiListaValores.moveAbove(shell);
		guiListaValores.setVisible(false);




	}

	public ListarValor(Group grupo,int posX, int posY, int anchoContenedor,boolean patronCompleto) {


		this.anchoContenedor = anchoContenedor;
		this.patronCompleto = patronCompleto;

		//Se agrega el composite de primero para que se muestre en el frente, se agrega no visible
		guiListaValores = new GUIListaValores(grupo, SWT.None);
		guiListaValores.setBounds(posX,posY,anchoContenedor, 160);
		guiListaValores.moveAbove(grupo);
		guiListaValores.setVisible(false);




	}


	/**
	 * @param sentencia Sentencia SQL para la consulta que se va mostrar en la lista de valores incremental, se debe especificar la clausula solo con el nombre de la columna criterio
	 * @param camposRetornos Array Bidimensional donde la primera posicion especifica el campo a retornar
	 *                       y la segunda posicion la columna de la tabla que se desea recuperar
	 * @param componenteObtieneFoco Componente que obtiene el foco despues de recuperar los valores
	 * @param ancho Ancho del dialogo que mostrara la lista de valores
	 * 
	 */      
	public void setListarValor(Statement sentenciaBD, String sentenciaSQL,
			Object[][] camposRetornos, Control componenteObtieneFoco,
			int anchoColumnas[]) {

		//Se obtiene la posicion del componente con respecto a la pantalla

		this.camposRetornos = camposRetornos;
		this.sentenciaSQL = sentenciaSQL;
		this.sentenciaBD = sentenciaBD;
		this.anchoColumnas = anchoColumnas;
		this.componenteObtieneFoco = componenteObtieneFoco;


	} 



	public void keyPressed(KeyEvent k) {}
	
	

	public void keyReleased(KeyEvent k) { //Se utiliza para una lista incremental

		if (k.character != SWT.CR && k.character != SWT.TAB && k.character != SWT.ESC) {

			texto = (Text)k.getSource();

			mostrarListaValores(texto.getText());

		} else {

			if (k.character == SWT.TAB || k.character == SWT.ESC) {

				guiListaValores.setVisible(false);

			} else

				guiListaValores.getTbListaValores().forceFocus();
		}


	}

	/*
	 * Metodo que adiciona los datos en la tabla
	 */
	public void mostrarListaValores(String condicion) {


		try {

			String sentenciaSQLPatrones = null;


			//Se pasa el resultado del resultSet a Vectores
			if (patronCompleto)

				sentenciaSQLPatrones = sentenciaSQL + " like '%" + condicion + "%'";

			else

				sentenciaSQLPatrones = sentenciaSQL + " like '" + condicion + "%'";


			ResultSet resultado = sentenciaBD.executeQuery(sentenciaSQLPatrones);

			//Se agregan las columnas siempre una sola vez
			if (numeroColumnas == 0)

				agregarColumnasTabla(resultado);


			guiListaValores.getTbListaValores().removeAll();

			while (resultado.next()) {

				TableItem tableItem  = new TableItem(guiListaValores.getTbListaValores(),SWT.BORDER);
				String[] registro = new String[numeroColumnas];

				for (int i = 0; i < numeroColumnas; i++ )
					
					registro[i] = resultado.getString(i+1);
				
				tableItem.setText(registro);

			}

			guiListaValores.getTbListaValores().addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					if (e.character == SWT.CR) {

						int indice = guiListaValores.getTbListaValores().getSelectionIndex();

						if (indice < 0)

							indice = 0;

						TableItem item = guiListaValores.getTbListaValores().getItem(indice);
						setValoresCamposRetorno(item);

					} 

				}
			});


			guiListaValores.getTbListaValores().addListener(SWT.MouseDoubleClick, new Listener() {
				public void handleEvent(Event event) {

					TableItem item = guiListaValores.getTbListaValores().getItem(guiListaValores.getTbListaValores().getSelectionIndex());

					setValoresCamposRetorno(item);

				}
			});


			guiListaValores.setVisible(true);

		} catch (SQLException e) {

			e.printStackTrace();

		}


	}


	/*
	 * Metodo agregarColumnasTabla: Obtiene el numero y nombre de las columnas de la consulta
	 */
	private void agregarColumnasTabla(ResultSet resultado) {
		try {
			ResultSetMetaData resultadoMetaData =  resultado.getMetaData();


			numeroColumnas = resultadoMetaData.getColumnCount();



			//Se agregan las columnas en la tabla
			for (int i = 0; i < numeroColumnas; i++) {

				TableColumn columna = new TableColumn(guiListaValores.getTbListaValores(), SWT.CENTER);
				columna.setText(resultadoMetaData.getColumnName(i+1));
				columna.setWidth(anchoColumnas[i]);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}


	/*
	 * Metodo que configura los datos de retorno
	 */
	private void setValoresCamposRetorno(TableItem item) {


		Text TextFieldRetorno = null;
		int posicionTablaElementoRetorno = 0;


		for (int j = 0; j < camposRetornos.length ; j++) {

			TextFieldRetorno = (Text) camposRetornos[j][0];
			posicionTablaElementoRetorno = Integer.valueOf(camposRetornos[j][1].toString());
			TextFieldRetorno.setText(item.getText(posicionTablaElementoRetorno));

		}


		guiListaValores.setVisible(false);
		componenteObtieneFoco.forceFocus();

	}


	/*
	 * Oculta la lista de valores
	 */
	public void ocultarListaValor() {

		guiListaValores.setVisible(false);

	}


	/*
	 * Metodo que configura el Widget de retorno
	 */
	public void setConfiguraRetorno(Control componenteObtieneFoco){

		this.componenteObtieneFoco = componenteObtieneFoco;

	}


	/***************************************************************************************************************
	 ******************************** Clase Interna *************************************************************
	 ***************************************************************************************************************/

	class GUIListaValores extends Composite {

		private Group grupoListaValores = null;

		public Group getGrupoListaValores() {
			return grupoListaValores;
		}

		private Table tbListaValores = null;

		public Table getTbListaValores() {
			return tbListaValores;
		}

		public GUIListaValores(Composite parent, int style) {
			super(parent, style);
			initialize();

		}

		private void initialize() {
			createGrupoListaValores();
			setSize(new Point(437, 208));
			setLayout(null);
		}

		/**
		 * This method initializes grupoListaValores	
		 *
		 */
		private void createGrupoListaValores() {
			grupoListaValores = new Group(this, SWT.ON_TOP);
			grupoListaValores.setLayout(null);
			grupoListaValores.setBounds(0, 0, anchoContenedor, 160);
			tbListaValores = new Table(grupoListaValores, SWT.FULL_SELECTION);
			tbListaValores.setHeaderVisible(true);
			tbListaValores.setLinesVisible(true);
			tbListaValores.setBounds(10, 10, anchoContenedor - 10, 138);

		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"


}
