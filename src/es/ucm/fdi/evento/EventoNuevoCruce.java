package es.ucm.fdi.evento;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Cruce;

public class EventoNuevoCruce extends Evento{

	protected String id;
	public EventoNuevoCruce(int time, String id) {
		super(time);
		this.id=id;
	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion {
		Cruce c= new Cruce(id) {};
		mapa.addCruce(id, c);
		// crea el cruce y se lo a�ade al mapa
	}

	protected CruceGenerico<?> creaCruce() {
		return new Cruce(this.id);
	}
	
	@Override
	public String toString() {
		return "[new_junction]" + "\n" + "time = " + this.getTiempo() + "\n" + "id = " + this.id;
	}
	@Override
	public String Nombre() {
		// TODO Auto-generated method stub
		return "New junction " + this.id;
	}

}
