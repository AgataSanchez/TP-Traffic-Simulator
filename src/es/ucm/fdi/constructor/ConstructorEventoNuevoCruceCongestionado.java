package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoNuevoCruceCongestionado;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos {

	protected String [] valoresPorDefecto;
	protected String nombre;
	
	public ConstructorEventoNuevoCruceCongestionado() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id", "type"};
		this.valoresPorDefecto = new String[] { "", "", "mc"};
		this.nombre="mc";
	}
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				!section.getValue("type").equals(this.nombre)) return null;
		else
			return new EventoNuevoCruceCongestionado(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.identificadorValido(section, "type"));
	}
	@Override
	public String toString() {
		return "Nuevo Cruce Congestionado";
	}
}
