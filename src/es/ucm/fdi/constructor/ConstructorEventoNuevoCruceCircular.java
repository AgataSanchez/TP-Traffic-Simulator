package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoNuevoCruceCircular;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos {
	
	protected String [] valoresPorDefecto;
	protected String nombre;
	public ConstructorEventoNuevoCruceCircular() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id", "max_time_slice", "min_time_slice", "type" };
		this.valoresPorDefecto = new String[] { "", "", "rr", "",""};
		this.nombre="rr";
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				!section.getValue("type").equals(this.nombre)) return null;
		else
			return new EventoNuevoCruceCircular(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaInt(section, "max_time_slice"),
					ConstructorEventos.parseaInt(section, "min_time_slice"),
					ConstructorEventos.identificadorValido(section, "type"));
	}
	@Override
	public String toString() {
		return "Nuevo Cruce Circular";
	}

}
