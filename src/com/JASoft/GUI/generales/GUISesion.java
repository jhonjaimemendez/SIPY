/**
 * Clase: GUISesion
 * 
 * @version  1.0
 * 
 * @since 19-11-2010
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.GUI.generales;

import java.sql.ResultSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.JASoft.componentesAccesoDatos.ConectarMySQL;
import com.JASoft.componentesGraficos.ConfigurarText;


/*
 * Esta clase permite autenticar el usuario contra la BD
 * 
 */

public class GUISesion {

	private Shell sShell = null;  
	private Group grupoSesion = null;
	private Label lUsuario = null;
	private Text tUsuario = null;
	private Text tPassword = null;
	private Label lPassword = null;
	private Button bAceptar = null;
	private Button bCancelar = null;
	private ConectarMySQL conectarMySQL;
	
	public GUISesion() {
		
		
		createSShell(); //Se configura la GUI
		
		//Se crea una conexion hacia la BD
		try {
			
		     conectarMySQL = new ConectarMySQL("127.0.0.1", "SIPYPRODUCCION", "root", "root");
		     
		     //Se agrega un evento al boton aceptar para cuando se presione
		     bAceptar.addSelectionListener(new SelectionListener() {

		         public void widgetSelected(SelectionEvent event) {
		        
		        	 validarUsuario(tUsuario.getText(), tPassword.getText());
		         }

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}

		       
		      });
		     
		     //Se agrega un evento al boton cancelar para cuando se presione
		     bCancelar.addSelectionListener(new SelectionListener() {

		         public void widgetSelected(SelectionEvent event) {
		        
		        	ConfigurarText.cerrarShell(sShell);
		         }

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}

		       
		      });
             
		    //Se agrega un evento al boton cancelar para cuando se de tipee Enter
		     tPassword.addListener(SWT.DefaultSelection, new Listener() {
		         public void handleEvent(Event e) {
		        	 
		        	 validarUsuario(tUsuario.getText(), tPassword.getText());
		        	 
		         }
		       });
		     
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ConfigurarText.bloquearShell(sShell);//Se bloque el Shell
	}
	

	//***********************************************************************************************************************************

	private final void validarUsuario(String usuario,String password) {

		usuario = "admin";
		password="manager";
		
		if (!usuario.isEmpty()) {

			if (!password.isEmpty()) {	


				// Se valida que el usuario exista en la bd

				String sentenciaSQL = "Select password,Tema "+
				"From Usuarios " +
				"Where IdUsuario ='"+usuario +"'";

				try {

					ResultSet resultado = conectarMySQL.buscarRegistro (sentenciaSQL);

					if (resultado.next()) { //Se verifica que exista el usuario

						/*
						 * Se valida que el password se encuentre correcto (Se hace esto debido a que Mysql 
						 * no es sensible a mayuscula o minscula)
						 */
						if (password.equals(resultado.getString(1))) { 

							ConfigurarText.cerrarShell(sShell);

							//Se habilida el menu
							new GUIMenuPrincipal(conectarMySQL);



						}  else {

							ConfigurarText.mensajeError(sShell, "Clave errada para el usuario " + tUsuario.getText());
							tPassword.setText("");
							tPassword.forceFocus();
						}

					} else {

						ConfigurarText.mensajeError(sShell, "Usuario " + tUsuario.getText() + " no ha sido registrado");
						tUsuario.setText("");
						tPassword.setText("");
						tUsuario.forceFocus();
					}

				} catch (Exception e) {

					System.out.println(""+e);

				}    

			} else {

				ConfigurarText.mensajeError(sShell, "Digite el clade del usuario");
				tPassword.forceFocus();

			}	

		} else {

			ConfigurarText.mensajeError(sShell, "Digite el nombre de usuario");
			tUsuario.forceFocus();

		}


	}
 	
 	//************************** Abajo configuración GUI ********************************************************
	

	/**
	 * This method initializes sShell
	 */
	private final void createSShell() {
		sShell = new Shell();
		sShell.setText("Iniciar Sesión");
		createGrupoSesion();
		sShell.setSize(new Point(331, 200));
		sShell.setLayout(null);
		bAceptar = new Button(sShell, SWT.PUSH);
		bAceptar.setBounds(new Rectangle(33, 122, 103, 31));
		bAceptar.setImage(new Image(Display.getCurrent(), "imagenes/OK.gif"));
		bAceptar.setToolTipText("Aceptar: Autentica el usuario");
		bAceptar.setText("&Aceptar");
		bCancelar = new Button(sShell, SWT.NONE);
		bCancelar.setBounds(new Rectangle(177, 122, 103, 31));
		bCancelar.setText("&Cancelar");
		bCancelar.setImage(new Image(Display.getCurrent(), "imagenes/NO.gif"));
		bCancelar.setToolTipText("Cancelar: Cierra la ventana");
		
		ConfigurarText.centrarShell(sShell); 
		
	}
	


	/**
	 * This method initializes grupoSesion	
	 *
	 */
	private final void createGrupoSesion() {
		grupoSesion = new Group(sShell, SWT.NONE);
		grupoSesion.setLayout(null);
		grupoSesion.setBounds(new Rectangle(27, 10, 259, 95));
		lUsuario = new Label(grupoSesion, SWT.NONE);
		lUsuario.setBounds(new Rectangle(37, 31, 48, 15));
		lUsuario.setText("Usuario:");
		tUsuario = new Text(grupoSesion, SWT.BORDER);
		tUsuario.setToolTipText("Digite el nombre del usuario");
		tUsuario.setBounds(new Rectangle(90, 27, 146, 23));
		tPassword = new Text(grupoSesion, SWT.BORDER | SWT.SINGLE | SWT.PASSWORD);
		tPassword.setToolTipText("Digite el nombre del usuario");
		tPassword.setBounds(new Rectangle(90, 62, 146, 23));
		tPassword.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				if (e.character == SWT.CR)
					
				   validarUsuario(tUsuario.getText(), tPassword.getText());
			}
		});
		lPassword = new Label(grupoSesion, SWT.NONE);
		lPassword.setBounds(new Rectangle(29, 65, 56, 15));
		lPassword.setText("Password:");
	}
	
	
	// Metodo Principal
	 public static void main(String[] args) {
		 
		 new GUISesion();
    }

}
