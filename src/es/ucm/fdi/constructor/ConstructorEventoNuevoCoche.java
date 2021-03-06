package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoNuevoCoche;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventos {
	protected String [] itinerario;
	protected String nombre;
	ConstructorEventoNuevoCoche(){
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "itinerary", "max_speed", "type",
				"resistance", "fault_probability", "max_fault_duration", "seed" };
		this.itinerario= new String[] {"","",};
		this.nombre="car";
		this.valoresPorDefecto = new String[] {"","","","",this.nombre,"","","",""};
	}
	@Override
	public Evento parser(IniSection section) {
		// TODO Auto-generated method stub
		if (!section.getTag().equals(this.etiqueta) ||
				!section.getValue("type").equals(this.nombre)) return null;
		else
			return new EventoNuevoCoche(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.identificadorValidoIti(section, "itinerary"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed",0),
					ConstructorEventos.identificadorValido(section, "type"),
					ConstructorEventos.parseaIntNoNegativo(section, "resistance",0),
					ConstructorEventos.parseaDoubleNoNegativo(section, "fault_probability", 0.0),
					ConstructorEventos.parseaInt(section, "max_fault_duration"),
					ConstructorEventos.parseaLong(section, "seed", System.currentTimeMillis()));

	}
	@Override
	public String toString() {
		return super.toString();
	}

}
