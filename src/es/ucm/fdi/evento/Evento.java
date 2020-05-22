package es.ucm.fdi.evento;

import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;

public abstract class Evento {
	protected Integer tiempo;

	public Evento(int tiempo){
		this.tiempo=tiempo;
	}
	public int getTiempo(){
		return tiempo;
	}

	// cada clase que hereda implementa su m�todo ejecuta, que crear�
	// el correspondiente objeto de la simulaci�n.

	public abstract void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento, ErrorDeSimulacion;
	public abstract String Nombre();
}
