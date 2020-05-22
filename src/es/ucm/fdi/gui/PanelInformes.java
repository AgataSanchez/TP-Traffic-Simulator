package es.ucm.fdi.gui;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;

public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico{

	public PanelInformes(String titulo, boolean editable, Controlador ctrl) {
		super(titulo, editable);
		// TODO Auto-generated constructor stub
		ctrl.addObserver(this);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.setTexto(mapa.generateReport(tiempo));
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.limpiar();
	}

}
