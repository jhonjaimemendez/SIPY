package com.JASoft.componentes.GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class Toolbar {

	private static final String IMAGE_PATH = "imagenes"  //
	      + System.getProperty("file.separator");
	
	/*
	 *  Constructor General
	 */
	
	public Toolbar() {
		
		
		Display display = new Display();
		
	    Shell shell = new Shell(display);
	    
	    //Se configura la Toolbar
	    ToolBar toolBar = new ToolBar(shell, SWT.HORIZONTAL);
	    
	    //Se configuran los botones para la toolbar
	    
	    
	    ToolItem itemLimpiar = getToolItem(toolBar, SWT.PUSH, "", getImagen(shell, "Limpiar.gif"), getImagen(shell, "LimpiarS.GIF"), "Limpiar Alt + L");
	    ToolItem itemGuardar = getToolItem(toolBar, SWT.PUSH, "", getImagen(shell, "Guardar.GIF"), getImagen(shell, "GuardarS.GIF"), "Guardar Alt + G");
	    ToolItem itemEliminar = getToolItem(toolBar, SWT.PUSH, "", getImagen(shell, "Eliminar.gif"), getImagen(shell, "EliminarS.GIF"), "Eliminar Alt + E");
	    ToolItem itemBuscar = getToolItem(toolBar, SWT.PUSH, "", getImagen(shell, "Buscar.gif"), getImagen(shell, "BuscarS.GIF"), "Buscar Alt + B");
	    ToolItem itemImprimir = getToolItem(toolBar, SWT.PUSH, "", getImagen(shell, "Imprimir.gif"), getImagen(shell, "ImprimirS.GIF"), "Imprimir Alt + I");
	    ToolItem itemSalir = getToolItem(toolBar, SWT.PUSH, "", getImagen(shell, "Salir.gif"), getImagen(shell, "SalirS.GIF"), "Salir Alt + S");
	    ToolItem itemTitulo = getToolItem(toolBar, SWT.PUSH, "", null, null, "Sistema de Información Para Yessy");
	    itemTitulo.setImage(new Image(Display.getCurrent(), "D:/Proyectos Software/Azkaban/class/Imagenes/MOTORBIKE.gif"));
			    
	    toolBar.pack();
	    
	    
	  
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	   
		
	}
	
	
	  /**
	   * Crea la imagen y la devuelve
	   * 
	   * @param shell El shell padre
	   * @param nombreImagen Nombre de la imagen a agregar
	   * @return Imagen que se desee agregar a un Widgets
	   */
	private Image getImagen(Shell shell,String nombreImagen) {
		
		Image imagen = null;
		
		try {
			
			imagen = new Image(shell.getDisplay(), new FileInputStream(IMAGE_PATH + nombreImagen));
			
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
		return imagen;
		
	}
	
	
    /**
     * Facilita crear un item
     * 
     * @param Toolbar toolbar
     * @param Tipo Tipo de item creado
     * @param Texto texto para el Item
     * @param imagen la imagen
     * @param hotImage la imagen que se despliega cuando se paso el mouse
     * @param toolTipText ToolTipText
     * @return ToolItem
     */
    private ToolItem getToolItem(ToolBar toolbar, int tipo,String text,
    		                       Image imagen, Image hotImage, String toolTipText) {
    	
      ToolItem item = new ToolItem(toolbar, tipo);
      item.setText(text);
      item.setImage(imagen);
      item.setHotImage(hotImage);
      item.setToolTipText(toolTipText);
      return item;
      
    }
    
    public static void main(String arg[]) {
    	
    	new Toolbar();
    }

}
