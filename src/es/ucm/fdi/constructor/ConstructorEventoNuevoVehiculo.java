package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoNuevoVehiculo;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos{
	private String [] itinerario;
	
	public ConstructorEventoNuevoVehiculo() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "itinerary", "max_speed" };
		this.itinerario= new String[] {"","",};

	}
	@Override
	public Evento parser(IniSection section) {
		// TODO Auto-generated method stub
		if (!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null) return null;
		else
			return new EventoNuevoVehiculo(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
					ConstructorEventos.identificadorValidoIti(section, "itinerary"));	
	}
	@Override
	public String toString() {
		return "New Vehicle";
	}

}
