package es.ucm.fdi.evento;

import es.ucm.fdi.cruces.CruceCircular;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;

public class EventoNuevoCruceCircular extends EventoNuevoCruce {

	protected Integer maxValorIntervalo;  
	protected Integer minValorIntervalo;
	private String tipo;
	public EventoNuevoCruceCircular(int time, String id,
			int max_time_slice, int min_time_slice, String tipo) {
		// TODO Auto-generated constructor stub
		super(time,id);
		this.maxValorIntervalo=max_time_slice;
		this.minValorIntervalo=min_time_slice;
		this.tipo=tipo;

	}
	@Override  
	protected CruceGenerico<?> creaCruce() {
		return new CruceCircular(this.id, this.minValorIntervalo, this.maxValorIntervalo);
	} 

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion {
		// TODO Auto-generated method stub
		CruceCircular c= (CruceCircular) creaCruce();
		mapa.addCruce(id, c);

	}
	@Override
	public String toString() {
		return super.toString() + "\n" + "type = " + this.tipo +"\n" + "max_time_slice = " + this.maxValorIntervalo
				+"\n" + "min_time_slice = " + this.minValorIntervalo;
	}

}
