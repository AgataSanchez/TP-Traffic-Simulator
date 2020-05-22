package es.ucm.fdi.evento;

import java.util.List;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.vehiculos.Coche;


public class EventoNuevoCoche extends EventoNuevoVehiculo{

	protected String tipo;
	protected int resistencia;
	protected double posibilidad_averia;
	protected int max_duracion_averia;
	protected long seed;

	public EventoNuevoCoche(int tiempo, String id, String[] itinerario,  int velocidadMaxima, String tipo, 
			int resistencia, double posibilidad_averia, int max_duracion_averia, long seed) {
		super(tiempo, id, velocidadMaxima, itinerario);
		this.tipo=tipo;
		this.resistencia=resistencia;
		this.posibilidad_averia=posibilidad_averia;
		this.max_duracion_averia=max_duracion_averia;
		this.seed=seed;

	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		if(iti!=null && iti.size()>=2) {
			Coche c = new Coche(id,velocidadMaxima, this.resistencia, this.posibilidad_averia, this.seed, this.max_duracion_averia,iti);
			mapa.addVehiculo(id, c);
		}else
			throw new ErrorDeEvento("O el itinerario es null o tiene menos de dos cruces");
		 

		// si iti es null o tiene menos de dos cruces lanzar excepci�n
		// en otro caso crear el veh�culo y a�adirlo al mapa.
	}
	@Override
	public String toString() {
		return super.toString() + "type = " + this.tipo + "\n" + "resistance = " + this.resistencia +
				"\n" + "faulty_duration = " + this.max_duracion_averia + "\n" + "seed = " + this.seed;
	}

}
