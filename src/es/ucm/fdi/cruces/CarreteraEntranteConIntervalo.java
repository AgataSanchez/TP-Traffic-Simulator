package es.ucm.fdi.cruces;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.simulacion.Carretera;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante {

	private int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner
	// el semáforo de la carretera en rojo  
	private int unidadesDeTiempoUsadas; // Se incrementa cada vez que 
	// avanza un vehículo 
	private boolean usoCompleto; // Controla que en cada paso con el semáforo  
	// en verde, ha pasado un vehículo 
	private boolean usadaPorUnVehiculo;  // Controla que al menos ha pasado un
	// vehículo mientras el semáforo estaba 
	// en verde.
	protected CarreteraEntranteConIntervalo(Carretera carretera, int intervalTiempo) {
		super(carretera); 
		this.intervaloDeTiempo=intervalTiempo;
		this.unidadesDeTiempoUsadas=0;
		this.usoCompleto=true;
		this.usadaPorUnVehiculo=false;
	}
	
	@Override
	protected void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		this.unidadesDeTiempoUsadas++;
		if(this.colaVehiculos.isEmpty())
			usoCompleto=false;
		else {
			this.colaVehiculos.get(0).moverASiguienteCarretera();
			this.colaVehiculos.remove(0);
			usadaPorUnVehiculo=true;
		}
		// Incrementa unidadesDeTiempoUsadas
		// Actualiza usoCompleto:
		//   - Si “colaVehiculos” es vacía, entonces “usoCompleto=false”
		//   - En otro caso saca el primer vehículo “v” de la “colaVehiculos”,
		//     y le mueve a la siguiente carretera (“v.moverASiguienteCarretera()”)
		//     Pone “usadaPorUnVehiculo” a true.
	}
	
	public boolean tiempoConsumido() {
		return this.unidadesDeTiempoUsadas == this.intervaloDeTiempo;
		// comprueba si se ha agotado el intervalo de tiempo.
		// “unidadesDeTiempoUsadas >= “intervaloDeTiempo”
	}
	
	public boolean usoCompleto() {	// método get
		return usoCompleto;
	} 
	
	public void setUsoCompleto(boolean usoCompleto) {
		this.usoCompleto = usoCompleto;
	}

	public void setUsadaPorUnVehiculo(boolean usadaPorUnVehiculo) {
		this.usadaPorUnVehiculo = usadaPorUnVehiculo;
	}

	public boolean usada() {// método get
		return usadaPorUnVehiculo;

	}
	public int getIntervaloDeTiempo() {
		return intervaloDeTiempo;
	}

	public void setIntervaloDeTiempo(int intervaloDeTiempo) {
		this.intervaloDeTiempo = intervaloDeTiempo;
	}
	public int getUnidadesDeTiempoUsadas() {
		return unidadesDeTiempoUsadas;
	}

	public void setUnidadesDeTiempoUsadas(int unidadesDeTiempoUsadas) {
		this.unidadesDeTiempoUsadas = unidadesDeTiempoUsadas;
	}
	
	@Override
	public String toString() {
		String s="red";
		if(tieneSemaforoVerde())
			s="green:"+ (getIntervaloDeTiempo()- getUnidadesDeTiempoUsadas()) ;
		return "("+ this.carretera.getId() + "," + s + "," + colaVehiculos.toString() + ")";
	}
}

