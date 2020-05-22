package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoNuevoCruce;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruce extends ConstructorEventos{

	public ConstructorEventoNuevoCruce() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		this.valoresPorDefecto = new String[] { "", "", };
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null) return null;
		else
			return new EventoNuevoCruce(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValido(section, "id"));
	}
	@Override
	public String toString() {
		return "New Junction";
	}
}
