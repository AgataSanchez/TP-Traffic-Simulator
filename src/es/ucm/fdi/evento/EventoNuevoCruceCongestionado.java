package es.ucm.fdi.evento;

import es.ucm.fdi.cruces.CruceCongestionado;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce {
	private String tipo;
	public EventoNuevoCruceCongestionado(int time, String id, String tipo) {
		// TODO Auto-generated constructor stub
		super(time,id);
		this.tipo=tipo;
		
	}

	protected CruceGenerico<?> creaCruce() {
		return new CruceCongestionado(this.id);
	} 
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion {
		// TODO Auto-generated method stub
		CruceCongestionado c= (CruceCongestionado) creaCruce();
		mapa.addCruce(id, c);

	}
	@Override
	public String toString() {
		return super.toString() + "\n" + "type = " + this.tipo;
	}

}
