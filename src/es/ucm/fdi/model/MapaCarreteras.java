package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.simulacion.Carretera;
import es.ucm.fdi.simulacion.Vehiculo;

public class MapaCarreteras {
	private List<Carretera> carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;
	// estructuras para agilizar la b�squeda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;

	public MapaCarreteras() {
		this.carreteras=new ArrayList<Carretera>();
		this.cruces=new ArrayList<CruceGenerico<?>>();
		this.vehiculos=new ArrayList<Vehiculo>();
		this.mapaDeCarreteras=new HashMap<String, Carretera>();
		this.mapaDeCruces=new HashMap<String, CruceGenerico<?>>();
		this.mapaDeVehiculos=new HashMap<String,Vehiculo>();
		// inicializa los atributos a sus constructoras por defecto.
		// Para carreteras, cruces y veh�culos puede usarse ArrayList.
		// Para los mapas puede usarse HashMap
	}
	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws ErrorDeEvento {
		if(!mapaDeCruces.containsKey(idCruce)){
			this.cruces.add(cruce);
			this.mapaDeCruces.put(idCruce, cruce);
		}else
			throw new ErrorDeEvento("El cruce " + idCruce +" ya existe");
		 
		// comprueba que �idCruce� no existe en el mapa.
		// Si no existe, lo a�ade a �cruces� y a �mapaDeCruces�.
		// Si existe lanza una excepci�n.
	}
	public void addVehiculo(String idVehiculo,Vehiculo vehiculo) throws ErrorDeEvento, ErrorDeSimulacion {
		if(!mapaDeVehiculos.containsKey(idVehiculo)){
			this.vehiculos.add(vehiculo);
			this.mapaDeVehiculos.put(idVehiculo, vehiculo);
			vehiculo.moverASiguienteCarretera();
		}else
			throw new ErrorDeEvento("El vehiculo " + idVehiculo + " ya existe");
		// comprueba que �idVehiculo� no existe en el mapa.
		// Si no existe, lo a�ade a �vehiculos� y a �mapaDeVehiculos�,
		// y posteriormente solicita al vehiculo que se mueva a la siguiente
		// carretera de su itinerario (moverASiguienteCarretera).
		// Si existe lanza una excepci�n.
	}
	public void addCarretera(String idCarretera,CruceGenerico<?> cOrigen,Carretera carretera,CruceGenerico<?> cDest) throws ErrorDeEvento {
		if(!mapaDeCarreteras.containsKey(idCarretera)){
			this.carreteras.add(carretera);
			this.mapaDeCarreteras.put(idCarretera,carretera);
			cOrigen.addCarreteraSalienteAlCruce(cDest, carretera);
			cDest.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}else
			throw new ErrorDeEvento("La carretera " + idCarretera + " ya existe");
		// comprueba que �idCarretera� no existe en el mapa.
		// Si no existe, lo a�ade a �carreteras� y a �mapaDeCarreteras�,
		// y posteriormente actualiza los cruces origen y destino como sigue:
		// - A�ade al cruce origen la carretera, como �carretera saliente�
		// - A�ade al crude destino la carretera, como �carretera entrante�
		// Si existe lanza una excepci�n.
	}
	public String generateReport(int time) {
		String report = "";
		
		for(CruceGenerico<?> cu: cruces) {
			report+=cu.generaInforme(time);
			report+="\n";
		}
	
		for(Carretera ca:carreteras) {
			report+=ca.generaInforme(time);
			report+="\n";
		}
		
		for(Vehiculo v: vehiculos) {
			report+=v.generaInforme(time);
			report+="\n";
		}
		
		// genera informe para cruces
		// genera informe para carreteras
		// genera informe para vehiculos
		return report;
	}
	public void actualizar() throws ErrorDeSimulacion {
		for(Carretera ca:this.carreteras)
			ca.avanza();

		for(CruceGenerico<?> c: this.cruces)
			c.avanza();


		// llama al m�todo avanza de cada cruce
		// llama al m�todo avanza de cada carretera
	}
	public CruceGenerico<?> getCruce(String id) throws ErrorDeEvento {
		CruceGenerico<?> c=mapaDeCruces.get(id);
		if(c!=null)
			return c;
		else
			throw new ErrorDeEvento("La carretera " + id + " no existe");
		// devuelve el cruce con ese �id� utilizando el mapaDeCruces.
		// sino existe el cruce lanza excepci�n.

	}
	public Vehiculo getVehiculo(String id) throws ErrorDeEvento {
		Vehiculo v=mapaDeVehiculos.get(id);
		if(v!=null)
			return v;
		else
			throw new ErrorDeEvento("El vehiculo " + id+ " no existe");
		// devuelve el veh�culo con ese �id� utilizando el mapaDeVehiculos.
		// sino existe el veh�culo lanza excepci�n.

	}
	public Carretera getCarretera(String id) throws ErrorDeEvento {
		Carretera ca=mapaDeCarreteras.get(id);
		if(ca!=null)
			return ca;
		else
			throw new ErrorDeEvento("La carretera " + id +" no existe");

		// devuelve la carretera con ese �id� utilizando el mapaDeCarreteras.
		// sino existe la carretra lanza excepci�n.

	}
	public List<CruceGenerico<?>> getCruces() {
		return cruces;
	}
	public List<Carretera> getCarreteras() {
		return carreteras;
	}
	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}
	
	 public void reinicia() {
			// TODO Auto-generated method stub
			this.vehiculos.clear();
			this.carreteras.clear();
			this.cruces.clear();
			this.mapaDeVehiculos.clear();
			this.mapaDeCarreteras.clear();
			this.mapaDeCruces.clear();
			
			
		}
	
}
