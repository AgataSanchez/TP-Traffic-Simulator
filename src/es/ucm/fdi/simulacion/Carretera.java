package es.ucm.fdi.simulacion;

import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.arrayList.SortedArrayList;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.ini.IniSection;

public class Carretera extends ObjetoSimulacion{
	protected int longitud; // longitud de la carretera
	protected int velocidadMaxima; // velocidad m�xima
	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public void setVelocidadMaxima(int velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	protected CruceGenerico<?> cruceOrigen; // cruce del que parte la carretera
	protected CruceGenerico<?> cruceDestino; // cruce al que llega la carretera
	protected List<Vehiculo> vehiculos; // lista ordenada de veh�culos en la
	// carretera (ordenada por localizaci�n)
	protected Comparator<Vehiculo> comparadorVehiculo; // orden entre veh�culos

	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		super(id);
		this.longitud=length;
		this.velocidadMaxima=maxSpeed;
		this.cruceOrigen=src;
		this.cruceDestino=dest;
		this.comparadorVehiculo= new Comparator<Vehiculo>() {

			@Override
			public int compare(Vehiculo arg0, Vehiculo arg1) {
				if(arg0.getLocalizacion()>arg1.getLocalizacion())
					return -1;
				else if(arg0.getLocalizacion()<arg1.getLocalizacion())
					return 1;
				else
					return 0;
			}

		};
		this.vehiculos= new SortedArrayList<Vehiculo>(comparadorVehiculo);

		// se inicializan los atributos de acuerdo con los par�metros.
		// se fija el orden entre los veh�culos: (inicia comparadorVehiculo)
		//- la localizaci�n 0 es la menor
	}

	public void avanza() {
		// calcular velocidad base de la carretera
		// inicializar obst�culos a 0
		// Para cada veh�culo de la lista �vehiculos�:
		//1. Si el veh�culo est� averiado se incrementa el n�mero de obstaculos.
		//2. Se fija la velocidad actual del veh�culo
		//3. Se pide al veh�culo que avance.
		// ordenar la lista de veh�culos
		int velocidadBase=calculaVelocidadBase();
		int obstaculos=0;
		int velocidad;
		for(Vehiculo v:vehiculos) {
			if(v.tiempoAveria>0) {
				obstaculos++;
				velocidad=0;
			}
			else if(this.longitud!=v.getLocalizacion())
				velocidad=velocidadBase/calculaFactorReduccion(obstaculos);
			else
				velocidad=0;
			v.setVelocidadActual(velocidad);
			v.avanza();
		}
		vehiculos.sort(comparadorVehiculo);


	}
	public void entraVehiculo(Vehiculo vehiculo) {
		// Si el veh�culo no existe en la carretera, se a�ade a la lista de veh�culos y
		// se ordena la lista.
		// Si existe no se hace nada.
		if(!vehiculos.contains(vehiculo)) {
			vehiculos.add(vehiculo);
			vehiculos.sort(comparadorVehiculo);
		}
	}
	public void saleVehiculo(Vehiculo vehiculo) {
		// elimina el veh�culo de la lista de veh�culos
		vehiculos.remove(vehiculo);
	}
	public void entraVehiculoAlCruce(Vehiculo v) {
		// a�ade el veh�culo al �cruceDestino� de la carretera�
		cruceDestino.entraVehiculoAlCruce(this.id, v);
	}
	protected int calculaVelocidadBase() {

		return Integer.min(velocidadMaxima, (velocidadMaxima/(Integer.max(vehiculos.size(), 1)))+1);

	}
	protected int calculaFactorReduccion(int obstaculos) {
		int factorReduccion;
		if(obstaculos>0)
			factorReduccion=2;
		else
			factorReduccion=1;

		return factorReduccion;
	}
	@Override
	protected String getNombreSeccion() {
		return "road_report";

	}
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String lista="";

		for(Vehiculo v:vehiculos) {
			lista+="(" + v.getId() + "," + v.getLocalizacion() + ")";
			if(vehiculos.indexOf(v) !=vehiculos.size()-1)
				lista+=",";

		}
		is.setValue("state", lista);


		// TODO Auto-generated method stub

	}
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	public CruceGenerico<?> getCruceOrigen() {
		return cruceOrigen;
	}
	public void setCruceOrigen(CruceGenerico<?> cruceOrigen) {
		this.cruceOrigen = cruceOrigen;
	}
	public CruceGenerico<?> getCruceDestino() {
		return cruceDestino;
	}
	public void setCruceDestino(CruceGenerico<?> cruceDestino) {
		this.cruceDestino = cruceDestino;
	}

	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	

}
