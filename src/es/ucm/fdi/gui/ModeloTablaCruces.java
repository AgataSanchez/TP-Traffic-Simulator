package es.ucm.fdi.gui;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.cruces.CarreteraEntrante;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;


public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>> {

	public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
		case 0: s = this.lista.get(indiceFil).getId(); break;
		case 1:
			if(!this.lista.get(indiceFil).getCarreterasEntrantes().isEmpty()) {
				for(CarreteraEntrante c:this.lista.get(indiceFil).getCarreterasEntrantes())
					if(c.tieneSemaforoVerde())
						s=c.toString();
			}else
				s= this.lista.get(indiceFil).getCarreterasEntrantes().toString();
			break;
		case 2:
			if(!this.lista.get(indiceFil).getCarreterasEntrantes().isEmpty()) {
				for(CarreteraEntrante c:this.lista.get(indiceFil).getCarreterasEntrantes())
					if(!c.tieneSemaforoVerde())
						s=c.toString();
			}else
				s =this.lista.get(indiceFil).getCarreterasEntrantes().toString(); 
			break;
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
		this.lista = mapa.getCruces();
		this.fireTableStructureChanged();

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.lista = mapa.getCruces();
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.lista = mapa.getCruces();
		this.fireTableStructureChanged();

	}

}
