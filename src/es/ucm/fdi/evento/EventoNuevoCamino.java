package es.ucm.fdi.evento;

import es.ucm.fdi.carreteras.Camino;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Carretera;

public class EventoNuevoCamino extends EventoNuevaCarretera {

	protected String tipo;

	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud,
			String tipo) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.tipo=tipo;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento {
		CruceGenerico<?> cOrigen=mapa.getCruce(this.cruceOrigenId);
		CruceGenerico<?> cDest=mapa.getCruce(this.cruceDestinoId);
		
		Camino c = (Camino) creaCarretera(cOrigen, cDest);//Esta bien con el CAST???
		mapa.addCarretera(id, cOrigen, c, cDest);
		
		// obten cruce origen y cruce destino utilizando el mapa
		// crea la carretera
		// a�ade al mapa la carretera
	}
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Camino(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}
	@Override
	public String toString(){
		return super.toString() + "type= " + this.tipo;

	}

}
