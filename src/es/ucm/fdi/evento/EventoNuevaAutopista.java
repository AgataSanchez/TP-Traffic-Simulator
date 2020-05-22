package es.ucm.fdi.evento;

import es.ucm.fdi.carreteras.Autopista;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Carretera;

public class EventoNuevaAutopista extends EventoNuevaCarretera {
	protected Integer numCarriles;
	protected String tipo;
	
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima,
			int longitud, int lanes, String tipo) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.numCarriles=lanes;
		this.tipo=tipo;
		
	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento {
		CruceGenerico<?> cOrigen=mapa.getCruce(cruceOrigenId);
		CruceGenerico<?> cDest=mapa.getCruce(cruceDestinoId);
		
		Autopista auto = (Autopista) creaCarretera(cOrigen, cDest);
		mapa.addCarretera(id, cOrigen, auto, cDest);
		
		// obten cruce origen y cruce destino utilizando el mapa
		// crea la carretera
		// a�ade al mapa la carretera
	}
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Autopista(this.id,this.longitud,this.velocidadMaxima, cruceOrigen, cruceDestino, this.numCarriles);
	}
	@Override
	public String toString(){
		return super.toString() + "lanes= "+ this.numCarriles + "\n" + "type= " + this.tipo;

	}

}
