package es.ucm.fdi.carreteras;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulacion.Carretera;

public class Camino extends Carretera {

	public Camino(String id, int longitud, int velocidadMaxima, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		super(id,longitud, velocidadMaxima, cruceOrigen, cruceDestino);
	}
	
	@Override
	protected int calculaVelocidadBase() {
		return this.velocidadMaxima;
	}
	
	@Override
	protected int calculaFactorReduccion(int obstacles) {
		return obstacles+1;
	}
	
	@Override
	public void avanza() {
		super.avanza();
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "dirt");
		super.completaDetallesSeccion(is);
		
	}
}
