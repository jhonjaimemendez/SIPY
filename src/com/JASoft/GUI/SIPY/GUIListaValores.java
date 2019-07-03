package com.JASoft.GUI.SIPY;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

public class GUIListaValores extends Composite {

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
		grupoListaValores.setBounds(new Rectangle(11, 4, 415, 192));
		tbListaValores = new Table(grupoListaValores, SWT.VIRTUAL);
		tbListaValores.setHeaderVisible(true);
		tbListaValores.setLinesVisible(true);
		tbListaValores.setBounds(new Rectangle(7, 17, 404, 169));
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
