package es.ucm.fdi.vehiculos;

import java.util.List;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulacion.Vehiculo;

public class Bicicleta extends Vehiculo{

	public Bicicleta(String id,  List <CruceGenerico<?>> itinerario, int velocidadMaxima) throws ErrorDeSimulacion {
		super(id,velocidadMaxima,itinerario);

	}

	@Override
	public void setTiempoAveria(Integer duracionAveria) {
		if(this.velocidadActual>velocidadMaxima/2)
			super.setTiempoAveria(duracionAveria);

	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "bike");
		super.completaDetallesSeccion(is);
		

	}

}
