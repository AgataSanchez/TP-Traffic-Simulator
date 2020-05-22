package es.ucm.fdi.vehiculos;

import java.util.List;
import java.util.Random;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulacion.Vehiculo;

public class Coche extends Vehiculo {

	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	
	public Coche(String id, int velocidadMaxima, int resistencia, double probabilidad, long semilla, 
			int duracionMaximaInfraccion, List<CruceGenerico<?>> itinerario) throws ErrorDeSimulacion {
		super(id,velocidadMaxima,itinerario);
		this.kmUltimaAveria=0;
		this.resistenciaKm=resistencia;
		this.duracionMaximaAveria=duracionMaximaInfraccion;
		this.probabilidadDeAveria=probabilidad;
		this.numAleatorio=new Random (semilla);
	}

	@Override
	public void avanza() {
		
		if(this.getTiempoDeInfraccion()>0)
			this.kmUltimaAveria=this.kilometraje;
		else if((this.kilometraje - this.kmUltimaAveria) >= this.resistenciaKm && numAleatorio.nextDouble()<probabilidadDeAveria) {
			int tiempo=numAleatorio.nextInt(duracionMaximaAveria)+1;
			this.setTiempoAveria(tiempo);
			
		}

		super.avanza();
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "car");
		super.completaDetallesSeccion(is);

	}

}
