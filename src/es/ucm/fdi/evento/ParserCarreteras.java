package es.ucm.fdi.evento;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.model.MapaCarreteras;

public class ParserCarreteras {
	String [] itinerario;
	MapaCarreteras mapa;
	
	public ParserCarreteras(String [] itinerario, MapaCarreteras mapa) {
		this.itinerario=itinerario;
		this.mapa=mapa;
	}
	public static List<CruceGenerico<?>> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) throws ErrorDeEvento {
		// TODO Auto-generated method stub
		 List<CruceGenerico<?>> cruces = new ArrayList<CruceGenerico<?>>();
		 for(String idCruce:itinerario) {
			 CruceGenerico<?> c=mapa.getCruce(idCruce);
			 cruces.add(c);
		 }
		return cruces;
	}

}
