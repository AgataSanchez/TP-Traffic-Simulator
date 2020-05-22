package es.ucm.fdi.gui;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;

public class ModeloTablaEventos extends ModeloTabla<Evento> {

	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);

		// TODO Auto-generated constructor stub
	}

	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
		case 0: s = indiceFil; break;
		case 1:s = this.lista.get(indiceFil).getTiempo(); break;
		case 2: s = this.lista.get(indiceFil).Nombre(); break;
		default: assert (false);
		}
		return s;
	}
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.lista=eventos;
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub	
		this.lista=eventos;
		this.fireTableStructureChanged();

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.lista = new ArrayList<Evento>();
		this.fireTableStructureChanged();
	}

}
