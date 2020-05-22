package es.ucm.fdi.cruces;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.simulacion.Carretera;
import es.ucm.fdi.simulacion.Vehiculo;

public class CarreteraEntrante {
	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	protected boolean semaforo; 
	
	public CarreteraEntrante(Carretera carretera) {
		this.carretera=carretera;
		this.semaforo=false;
		this.colaVehiculos= new ArrayList <Vehiculo>();
		// inicia los atributos.
		// el sem�foro a rojo
	}
	public void ponSemaforo(boolean color) {
		this.semaforo=color;
	}

	protected void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		if(!colaVehiculos.isEmpty()) {
			Vehiculo v=colaVehiculos.get(0);
			colaVehiculos.remove(0);
			v.moverASiguienteCarretera();
		}
		// coge el primer vehiculo de la cola, lo elimina,
		// y le manda que se mueva a su siguiente carretera.
	}
	@Override
	public String toString() {
		String s="red";
		if(tieneSemaforoVerde())
			s="green";
		return "("+ this.carretera.getId() + "," + s + "," + colaVehiculos.toString() + ")";
	}
	public Carretera getCarretera() {
		// TODO Auto-generated method stub
		return this.carretera;
	}
	public boolean tieneSemaforoVerde() {
		// TODO Auto-generated method stub
		return semaforo;
	}
}

