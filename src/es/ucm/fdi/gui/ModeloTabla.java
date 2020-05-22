package es.ucm.fdi.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ucm.fdi.control.Controlador;

public abstract class ModeloTabla<T> extends DefaultTableModel implements ObservadorSimuladorTrafico{
	protected String [] columnIds;
	protected List<T> lista;

	public ModeloTabla(String[]columnIdEventos, Controlador ctrl) {
		this.lista = null;
		this.columnIds = columnIdEventos;
		this.lista = new ArrayList<T>();
		ctrl.addObserver(this);
	}
	@Override
	public String getColumnName(int col) { return this.columnIds[col]; }
	@Override
	public int getColumnCount() {return this.columnIds.length;}
	@Override
	public int getRowCount() {return this.lista == null ? 0 :
		this.lista.size();
	}
}
