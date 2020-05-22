package es.ucm.fdi.evento;

import java.util.List;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Vehiculo;

public class EventoNuevoVehiculo extends Evento{

	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;

	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo);
		this.id=id;
		this.velocidadMaxima=velocidadMaxima;
		this.itinerario=itinerario;
	}
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		if(iti!=null && iti.size()>=2) {
			Vehiculo v = new Vehiculo(id,velocidadMaxima,iti);
			mapa.addVehiculo(id, v);
		}else
			throw new ErrorDeEvento("O el itinerario es null o tiene menos de dos cruces");
		 

		// si iti es null o tiene menos de dos cruces lanzar excepci�n
		// en otro caso crear el veh�culo y a�adirlo al mapa.
	}
	
	@Override
	public String toString() {
		return "[new_vehicle]" + "\n"+ "time =" + this.getTiempo()  + "\n" + "id = " + this.id  + "\n" + "itinerary =" +
		 this.itinerario + "\n" + "max_speed = " + this.velocidadMaxima;
	}
	@Override
	public String Nombre() {
		// TODO Auto-generated method stub
		return "New vehicle "+ this.id;
	}

}
