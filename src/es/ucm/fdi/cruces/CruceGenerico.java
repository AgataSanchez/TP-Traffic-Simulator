package es.ucm.fdi.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.simulacion.Carretera;
import es.ucm.fdi.simulacion.ObjetoSimulacion;
import es.ucm.fdi.simulacion.Vehiculo;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {
	protected int indiceSemaforoVerde;
	protected List<T> carreterasEntrantes;
	protected Map<String,T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;
	
	public CruceGenerico(String id) {
		// TODO Auto-generated constructor stub
		super(id);
		this.indiceSemaforoVerde=-1;
		this.carreterasEntrantes= new ArrayList<T>();
		this.mapaCarreterasEntrantes= new HashMap<String, T>();
		this.carreterasSalientes= new HashMap<CruceGenerico<?>, Carretera>();
		
	}
	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) {
		
		return this.carreterasSalientes.get(cruce);
	}
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		T ri = creaCarreteraEntrante(carretera);
		mapaCarreterasEntrantes.put(idCarretera, ri);
		carreterasEntrantes.add(ri);
		
	}
	
	abstract protected T creaCarreteraEntrante(Carretera carretera);
	
	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carr) {
		carreterasSalientes.put(destino, carr);
		
	}
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo){
		mapaCarreterasEntrantes.get(idCarretera).colaVehiculos.add(vehiculo);
	}
	@Override
	public void avanza() throws ErrorDeSimulacion {    
		
		if(!carreterasEntrantes.isEmpty()) {
			if(this.indiceSemaforoVerde==-1) {
				this.actualizaSemaforos();
				
			}else {
				this.carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
				this.actualizaSemaforos();
			}			
		}
		

	}
	abstract protected void actualizaSemaforos();
	public List<T> getCarreterasEntrantes() {
		return carreterasEntrantes;
	}
	public Map<CruceGenerico<?>, Carretera> getCarreterasSalientes() {
		return carreterasSalientes;
	}

	


}
