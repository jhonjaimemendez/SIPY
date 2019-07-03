

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import com.JASoft.componentesGraficos.ToolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GUIListadoClientes {

	private Shell sShell = null;
	private Group grupoListadoClientes = null;
	private Table tbListadoClientes = null;
	private Label lNumeroClientes = null;
	private Text tNumeroCliente = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		ToolBar toolBar = new ToolBar(sShell,SWT.BORDER);
		toolBar.setBounds(new Rectangle(5, 5, 779, 45));
		sShell.setText("Listado de Clientes");
		createGrupoListadoClientes();
		sShell.setSize(new Point(800, 600));
		sShell.setLayout(null);
	}

	/**
	 * This method initializes grupoListadoClientes	
	 *
	 */
	private void createGrupoListadoClientes() {
		grupoListadoClientes = new Group(sShell, SWT.NONE);
		grupoListadoClientes.setLayout(null);
		grupoListadoClientes.setBounds(new Rectangle(9, 63, 768, 473));
		tbListadoClientes = new Table(grupoListadoClientes, SWT.NONE);
		tbListadoClientes.setHeaderVisible(true);
		tbListadoClientes.setLinesVisible(true);
		tbListadoClientes.setBounds(new Rectangle(9, 20, 747, 401));
		lNumeroClientes = new Label(grupoListadoClientes, SWT.NONE);
		lNumeroClientes.setBounds(new Rectangle(574, 438, 120, 15));
		lNumeroClientes.setText("Número de  Clientes:");
		tNumeroCliente = new Text(grupoListadoClientes, SWT.BORDER);
		tNumeroCliente.setBounds(new Rectangle(699, 434, 57, 23));
	}

}
