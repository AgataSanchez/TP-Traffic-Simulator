package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.ini.*;

public class ParserEventos {
	private static ConstructorEventos[] eventos = {
		new ConstructorEventoNuevoCruce(),
		new ConstructorEventoNuevaCarretera(),
		new ConstructorEventoNuevoVehiculo(),
		new ConstructorEventoAveriaCoche(),
		new ConstructorEventoNuevaAutopista(),
		new ConstructorEventoNuevoCamino(),
		new ConstructorEventoNuevoCoche(),
		new ConstructorEventoNuevaBicicleta(),
		new ConstructorEventoNuevoCruceCircular(),
		new ConstructorEventoNuevoCruceCongestionado() 
	};
	// bucle de prueba y error
	public static Evento parseaEvento(IniSection sec) {
		int i = 0;
		boolean seguir = true;
		Evento e = null;
		while (i < ParserEventos.eventos.length && seguir ) {
			// ConstructorEventos contiene el m�todo parse(sec)
			e = ParserEventos.eventos[i].parser(sec);
			if (e!=null) seguir = false;
			else i++;
		}
		return e;
	}
}
