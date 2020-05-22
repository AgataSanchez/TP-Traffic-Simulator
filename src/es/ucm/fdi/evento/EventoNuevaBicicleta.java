package es.ucm.fdi.evento;

import java.util.List;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.vehiculos.Bicicleta;


public class EventoNuevaBicicleta extends EventoNuevoVehiculo{

	protected String tipo;
	public EventoNuevaBicicleta(int tiempo, String id, String[] itinerario, 
			int velocidadMaxima, String tipo ) {
		super(tiempo, id, velocidadMaxima, itinerario);
		this.tipo=tipo;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		if(iti!=null && iti.size()>=2) {
			Bicicleta b = new Bicicleta(id,iti,velocidadMaxima);
			mapa.addVehiculo(id, b);
		}else
			throw new ErrorDeEvento("O el itinerario es null o tiene menos de dos cruces");
		 

		// si iti es null o tiene menos de dos cruces lanzar excepci�n
		// en otro caso crear el veh�culo y a�adirlo al mapa.
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n" + "type = "+ this.tipo;
	}

	
}
