package es.ucm.fdi.carreteras;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulacion.Carretera;

public class Autopista extends Carretera {

	private int numCarriles;

	public Autopista(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest, int carriles) {
		super(id,length, maxSpeed, src, dest);
		this.numCarriles=carriles;
	}

	@Override
	protected int calculaVelocidadBase() {
		return Integer.min(this.velocidadMaxima,((this.velocidadMaxima*this.numCarriles)/Integer.max(this.vehiculos.size(),1))+1);
	}

	@Override
	protected int calculaFactorReduccion(int obstacles) {
		return obstacles<this.numCarriles ? 1: 2;
	}

	@Override
	public void avanza() {
		super.avanza();
	}
	
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "lanes");
		super.completaDetallesSeccion(is);
			
	}

}
