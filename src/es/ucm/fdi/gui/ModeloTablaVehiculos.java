package es.ucm.fdi.gui;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Vehiculo;

public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {

	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
		case 0: s = this.lista.get(indiceFil).getId(); break;
		case 1: if(this.lista.get(indiceFil).getCarretera()!=null) s=this.lista.get(indiceFil).getCarretera().getId();else s = "arrived"; break;
		case 2: s = this.lista.get(indiceFil).getLocalizacion(); break;
		case 3: s = this.lista.get(indiceFil).getVelocidadActual(); break;
		case 4: s = this.lista.get(indiceFil).getKilometraje(); break;
		case 5: s = this.lista.get(indiceFil).getTiempoDeInfraccion(); break;
		case 6: s = this.lista.get(indiceFil).getItinerario(); break;
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
		
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.lista.clear();
		this.fireTableStructureChanged();

	}

}
