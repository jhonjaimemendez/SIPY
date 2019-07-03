package com.JASoft.componentes.GUI;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class PestañaUtilidades extends Composite {

	private Group grupoImporteEsperado = null;
	private Group grupoSaldosIniciales = null;
	private Label TotalSaldoIniciales = null;
	private Text tTotalSaldos = null;
	private Text tSaldosPagados = null;
	private Label lTotalSaldosCancelados = null;
	private Label lSaldospendientes = null;
	private Text tSaldoPendiente = null;
	private Button bVerListado = null;
	private Group grupoImporteEsperadoVentas = null;
	private Label lTotal = null;
	private Text tImporteTotalEsperado = null;
	private Group grupoPorcentajeVentas = null;
	private Label lTotal1 = null;
	private Text tPorcentajeVendido = null;
	private Label lImporteEsperado = null;
	private Text tImporteEsperado = null;
	private Label lImporteReal = null;
	private Text tImporteReal = null;
	private Group grupoCapital = null;
	private Label lCapitalTotal = null;
	private Text tTotalCapital = null;
	private Group grupoGananciaTotal = null;
	private Label lTotalGanancia = null;
	private Text tMaximaEsperada = null;
	private Label lMinEsperada = null;
	private Text tMinimaEsperada = null;
	private Label lRecaudada = null;
	private Text tGananciaRecaudada = null;
	private Label lTotalRecaudado = null;
	private Text tTotalRecaudado = null;
	private Label lTotalAlmacen = null;
	private Text tTotalAlmacen = null;
	private Group grupoSaldoTotalClientes = null;
	private Label lSaldoTotal = null;
	private Text tSaldoTotal = null;
	private Label lGananciaEsperada = null;
	private Text tGanaciaEsperada = null;
	private Label lGananciaReal = null;
	private Text tGanaciaReal = null;
	private Label lCreditos = null;
	private Text tTotalCreditos = null;
	private Label lCreditoUtilidad = null;
	private Text tCreditosutilidad = null;
	public PestañaUtilidades(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		createGroup();
		setSize(new Point(753, 490));
		setLayout(null);
	}

	/**
	 * This method initializes group	
	 *
	 */
	private void createGroup() {
		grupoImporteEsperado = new Group(this, SWT.NONE);
		grupoImporteEsperado.setLayout(null);
		createGrupoSaldosIniciales();
		createGrupoImporteEsperadoVentas();
		createGrupoPorcentajeVentas();
		createGrupoCapital();
		createGrupoGananciaTotal();
		createGrupoSaldoTotalClientes();
		grupoImporteEsperado.setBounds(new Rectangle(2, -2, 753, 490));
	}

	/**
	 * This method initializes grupoSaldosIniciales	
	 *
	 */
	private void createGrupoSaldosIniciales() {
		grupoSaldosIniciales = new Group(grupoImporteEsperado, SWT.NONE);
		grupoSaldosIniciales.setLayout(null);
		grupoSaldosIniciales.setText("Saldos Iniciales");
		grupoSaldosIniciales.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoSaldosIniciales.setBounds(new Rectangle(19, 18, 247, 158));
		TotalSaldoIniciales = new Label(grupoSaldosIniciales, SWT.NONE);
		TotalSaldoIniciales.setBounds(new Rectangle(63, 28, 33, 15));
		TotalSaldoIniciales.setText("Total :");
		tTotalSaldos = new Text(grupoSaldosIniciales, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalSaldos.setBounds(new Rectangle(104, 24, 115, 23));
		tSaldosPagados = new Text(grupoSaldosIniciales, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldosPagados.setBounds(new Rectangle(104, 56, 115, 23));
		lTotalSaldosCancelados = new Label(grupoSaldosIniciales, SWT.NONE);
		lTotalSaldosCancelados.setBounds(new Rectangle(37, 60, 59, 15));
		lTotalSaldosCancelados.setText("Cancelado:");
		lSaldospendientes = new Label(grupoSaldosIniciales, SWT.NONE);
		lSaldospendientes.setBounds(new Rectangle(40, 90, 56, 15));
		lSaldospendientes.setText("Pendiente:");
		tSaldoPendiente = new Text(grupoSaldosIniciales, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldoPendiente.setBounds(new Rectangle(104, 86, 115, 23));
		bVerListado = new Button(grupoSaldosIniciales, SWT.NONE);
		bVerListado.setBounds(new Rectangle(71, 120, 120, 28));
		bVerListado.setText("Ver Listado");
	}

	/**
	 * This method initializes grupoImporteEsperadoVentas	
	 *
	 */
	private void createGrupoImporteEsperadoVentas() {
		grupoImporteEsperadoVentas = new Group(grupoImporteEsperado, SWT.NONE);
		grupoImporteEsperadoVentas.setLayout(null);
		grupoImporteEsperadoVentas.setText("Importe Total Esperado en Ventas");
		grupoImporteEsperadoVentas.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoImporteEsperadoVentas.setBounds(new Rectangle(19, 194, 247, 69));
		lTotal = new Label(grupoImporteEsperadoVentas, SWT.NONE);
		lTotal.setBounds(new Rectangle(42, 35, 34, 15));
		lTotal.setText("Total:");
		tImporteTotalEsperado = new Text(grupoImporteEsperadoVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tImporteTotalEsperado.setBounds(new Rectangle(78, 31, 109, 23));
	}

	/**
	 * This method initializes grupoPorcentajeVentas	
	 *
	 */
	private void createGrupoPorcentajeVentas() {
		grupoPorcentajeVentas = new Group(grupoImporteEsperado, SWT.NONE);
		grupoPorcentajeVentas.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoPorcentajeVentas.setLayout(null);
		grupoPorcentajeVentas.setText("Importe y Utilidad en Ventas realizadas");
		grupoPorcentajeVentas.setBounds(new Rectangle(19, 280, 247, 201));
		lTotal1 = new Label(grupoPorcentajeVentas, SWT.NONE);
		lTotal1.setBounds(new Rectangle(59, 33, 66, 15));
		lTotal1.setText("% Vendido:");
		tPorcentajeVendido = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tPorcentajeVendido.setBounds(new Rectangle(131, 29, 39, 23));
		lImporteEsperado = new Label(grupoPorcentajeVentas, SWT.NONE);
		lImporteEsperado.setBounds(new Rectangle(29, 65, 96, 15));
		lImporteEsperado.setText("Importe Esperado:");
		tImporteEsperado = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tImporteEsperado.setBounds(new Rectangle(131, 61, 97, 23));
		lImporteReal = new Label(grupoPorcentajeVentas, SWT.NONE);
		lImporteReal.setBounds(new Rectangle(55, 97, 70, 15));
		lImporteReal.setText("Importe Real:");
		tImporteReal = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tImporteReal.setBounds(new Rectangle(131, 93, 97, 23));
		lGananciaEsperada = new Label(grupoPorcentajeVentas, SWT.NONE);
		lGananciaEsperada.setBounds(new Rectangle(31, 128, 94, 15));
		lGananciaEsperada.setText("Utilidad Esperada:");
		tGanaciaEsperada = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tGanaciaEsperada.setBounds(new Rectangle(131, 124, 97, 23));
		lGananciaReal = new Label(grupoPorcentajeVentas, SWT.NONE);
		lGananciaReal.setBounds(new Rectangle(56, 165, 69, 15));
		lGananciaReal.setText("Utilidad Real:");
		tGanaciaReal = new Text(grupoPorcentajeVentas, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tGanaciaReal.setBounds(new Rectangle(131, 161, 97, 23));
	}

	/**
	 * This method initializes grupoCapital	
	 *
	 */
	private void createGrupoCapital() {
		grupoCapital = new Group(grupoImporteEsperado, SWT.NONE);
		grupoCapital.setLayout(null);
		grupoCapital.setText("Capital ");
		grupoCapital.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoCapital.setBounds(new Rectangle(425, 18, 247, 158));
		lCapitalTotal = new Label(grupoCapital, SWT.NONE);
		lCapitalTotal.setBounds(new Rectangle(64, 129, 30, 15));
		lCapitalTotal.setText("Total:");
		tTotalCapital = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalCapital.setBounds(new Rectangle(101, 125, 110, 23));
		lTotalRecaudado = new Label(grupoCapital, SWT.NONE);
		lTotalRecaudado.setBounds(new Rectangle(32, 93, 62, 15));
		lTotalRecaudado.setText("Recaudado:");
		tTotalRecaudado = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalRecaudado.setBounds(new Rectangle(101, 89, 110, 23));
		lTotalAlmacen = new Label(grupoCapital, SWT.NONE);
		lTotalAlmacen.setBounds(new Rectangle(44, 19, 50, 15));
		lTotalAlmacen.setText("Almacen:");
		tTotalAlmacen = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalAlmacen.setBounds(new Rectangle(101, 15, 110, 23));
		lCreditos = new Label(grupoCapital, SWT.NONE);
		lCreditos.setBounds(new Rectangle(52, 58, 42, 15));
		lCreditos.setText("Credito:");
		tTotalCreditos = new Text(grupoCapital, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tTotalCreditos.setBounds(new Rectangle(101, 54, 110, 23));
	}

	/**
	 * This method initializes grupoGananciaTotal	
	 *
	 */
	private void createGrupoGananciaTotal() {
		grupoGananciaTotal = new Group(grupoImporteEsperado, SWT.NONE);
		grupoGananciaTotal.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoGananciaTotal.setLayout(null);
		grupoGananciaTotal.setText("Utilidad");
		grupoGananciaTotal.setBounds(new Rectangle(425, 280, 247, 201));
		lTotalGanancia = new Label(grupoGananciaTotal, SWT.NONE);
		lTotalGanancia.setBounds(new Rectangle(21, 33, 78, 15));
		lTotalGanancia.setText("Max. Esperada:");
		tMaximaEsperada = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tMaximaEsperada.setBounds(new Rectangle(115, 29, 110, 23));
		lMinEsperada = new Label(grupoGananciaTotal, SWT.NONE);
		lMinEsperada.setBounds(new Rectangle(24, 72, 78, 15));
		lMinEsperada.setText("Min. Esperada:");
		tMinimaEsperada = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tMinimaEsperada.setBounds(new Rectangle(116, 68, 110, 23));
		lRecaudada = new Label(grupoGananciaTotal, SWT.NONE);
		lRecaudada.setBounds(new Rectangle(46, 149, 61, 15));
		lRecaudada.setText("Recaudada:");
		tGananciaRecaudada = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tGananciaRecaudada.setBounds(new Rectangle(116, 145, 110, 23));
		lCreditoUtilidad = new Label(grupoGananciaTotal, SWT.NONE);
		lCreditoUtilidad.setBounds(new Rectangle(61, 111, 41, 15));
		lCreditoUtilidad.setText("Credito:");
		tCreditosutilidad = new Text(grupoGananciaTotal, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tCreditosutilidad.setBounds(new Rectangle(116, 107, 110, 23));
	}

	/**
	 * This method initializes grupoSaldoTotalClientes	
	 *
	 */
	private void createGrupoSaldoTotalClientes() {
		grupoSaldoTotalClientes = new Group(grupoImporteEsperado, SWT.NONE);
		grupoSaldoTotalClientes.setFont(new Font(Display.getDefault(), "Segoe UI", 9, SWT.BOLD));
		grupoSaldoTotalClientes.setLayout(null);
		grupoSaldoTotalClientes.setText("Saldo  Clientes");
		grupoSaldoTotalClientes.setBounds(new Rectangle(425, 194, 247, 69));
		lSaldoTotal = new Label(grupoSaldoTotalClientes, SWT.NONE);
		lSaldoTotal.setBounds(new Rectangle(42, 35, 34, 15));
		lSaldoTotal.setText("Total:");
		tSaldoTotal = new Text(grupoSaldoTotalClientes, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		tSaldoTotal.setBounds(new Rectangle(78, 31, 109, 23));
	}

}
