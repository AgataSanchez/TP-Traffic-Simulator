package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoAveriaCoche;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoAveriaCoche extends ConstructorEventos {
	private String [] vehiculos;
	public ConstructorEventoAveriaCoche() {
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] { "time", "vehicles", "duration" };
		this.vehiculos=new String [] {"","",};
		}
	@Override
	public Evento parser(IniSection section) {
		// TODO Auto-generated method stub
		if (!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null) return null;
		else
			return new EventoAveriaCoche(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValidoIti(section, "vehicles"),
					ConstructorEventos.parseaIntNoNegativo(section, "duration", 0));

	}
	@Override
	public String toString() {
		return "New Make Vehicle Faulty";
	}

}
