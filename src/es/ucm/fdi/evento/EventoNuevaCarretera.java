package es.ucm.fdi.evento;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Carretera;

public class EventoNuevaCarretera extends Evento{

	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;

	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud) {
		super(tiempo);
		this.id=id;
		this.cruceOrigenId=origen;
		this.cruceDestinoId=destino;
		this.velocidadMaxima=velocidadMaxima;
		this.longitud=longitud;

	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento {
		CruceGenerico<?> cOrigen=mapa.getCruce(cruceOrigenId);
		CruceGenerico<?> cDest=mapa.getCruce(cruceDestinoId);

		Carretera c =creaCarretera(cOrigen, cDest);
		mapa.addCarretera(id, cOrigen, c, cDest);

		// obten cruce origen y cruce destino utilizando el mapa
		// crea la carretera
		// a�ade al mapa la carretera
	}
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Carretera(this.id,this.longitud,this.velocidadMaxima, cruceOrigen, cruceDestino);

	}
	@Override
	public String toString(){
		return "[new_road]" + "\n" + "time = " + this.getTiempo() + "\n" + "id = " + this.id + "\n" +
				"\n" + "src = " + this.cruceOrigenId + "\n" + "dest =" + this.cruceDestinoId + "\n" + "max_speed = " + 
				this.velocidadMaxima + "\n" + "length = " + this.longitud;

	}
	@Override
	public String Nombre() {
		// TODO Auto-generated method stub
		return "New road "+ this.id;
	}

}
