package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.evento.EventoNuevoCamino;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventos {
	protected String nombre;
	ConstructorEventoNuevoCamino(){
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length", "type" };
		this.nombre="dirt";
		this.valoresPorDefecto = new String[] {"","","","","","",this.nombre};
	}
	@Override
	public Evento parser(IniSection section) {
		// TODO Auto-generated method stub
		if(!section.getTag().equals(this.etiqueta) ||
		!section.getValue("type").equals(this.nombre)) return null;
		else
			return new EventoNuevoCamino(
					// extrae el valor del campo �time� en la secci�n
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo �id� de la secci�n
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.identificadorValido(section, "src"),
					ConstructorEventos.identificadorValido(section, "dest"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed",0),
					ConstructorEventos.parseaIntNoNegativo(section, "length", 0),
					ConstructorEventos.identificadorValido(section, "type"));
	}
	@Override
	public String toString() {
		return super.toString();
	}

}
