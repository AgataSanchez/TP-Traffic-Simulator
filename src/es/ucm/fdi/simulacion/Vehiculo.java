package es.ucm.fdi.simulacion;

import java.util.List;

import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;


public class Vehiculo extends ObjetoSimulacion{
	protected Carretera carretera; // carretera en la que est� el veh�culo
	protected int velocidadMaxima; // velocidad m�xima
	
	protected int velocidadActual; // velocidad actual
	protected int kilometraje; // distancia recorrida
	protected int localizacion; // localizaci�n en la carretera
	protected int tiempoAveria; // tiempo que estar� averiado
	protected List<CruceGenerico<?>> itinerario; // itinerario a recorrer (m�nimo 2)
	protected boolean haLlegado;
	protected boolean estEnCruce;
	protected int contadorCruces;

	public Vehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion {
		super(id);

		if(velocidadMaxima<0 && iti.size()<2)
			throw new ErrorDeSimulacion("La velocidad y/o el itinerario son erroneos");
		else{
			this.velocidadMaxima=velocidadMaxima;
			this.haLlegado=false;
			this.estEnCruce=false;
			this.carretera=null;
			this.contadorCruces=0;
			this.kilometraje=0;
			this.contadorCruces=0;
			this.velocidadActual=0;
			this.localizacion=0;
			this.tiempoAveria=0;
			this.itinerario=iti;
		}




		// comprobar que la velocidadMaxima es mayor o igual que 0, y
		// que el itinerario tiene al menos dos cruces.
		// En caso de no cumplirse lo anterior, lanzar una excepci�n.
		// inicializar los atributos teniendo en cuenta los par�metros.
		// al crear un veh�culo su �carretera� ser� inicalmene �null�.
	}
	public int getLocalizacion() {
		return localizacion;

	}
	
	public int getTiempoDeInfraccion() {
		return tiempoAveria;

	}
	public void setVelocidadActual(int velocidad) {
		if(velocidad<0)
			velocidadActual=0;
		else if(velocidad>velocidadMaxima)
			velocidadActual=velocidadMaxima;
		else
			velocidadActual=velocidad;
	}

	public void setTiempoAveria(Integer duracionAveria) {
		if(carretera!=null) {
			if(tiempoAveria>0)
				tiempoAveria+=duracionAveria;
			else
				tiempoAveria=duracionAveria;
			velocidadActual=0;
		}
		// Comprobar que �carretera� no es null.
		// Se fija el tiempo de aver�a de acuerdo con el enunciado.
		// Si el tiempo de aver�a es finalmente positivo, entonces
		// la �velocidadActual� se pone a 0
	}
	public Carretera getCarretera() {
		return carretera;
	}
	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}
	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}
	public void setVelocidadMaxima(int velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}
	public int getKilometraje() {
		return kilometraje;
	}
	public void setKilometraje(int kilometraje) {
		this.kilometraje = kilometraje;
	}
	public int getVelocidadActual() {
		return velocidadActual;
	}
	public List<CruceGenerico<?>> getItinerario() {
		return itinerario;
	}
	public void setItinerario(List<CruceGenerico<?>> itinerario) {
		this.itinerario = itinerario;
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("speed", this.velocidadActual);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" ://(r1:10)
			"(" + this.carretera + "," + this.getLocalizacion() + ")");
	}
	@Override
	public void avanza() {
		// TODO Auto-generated method stub
		// si el coche est� averiado, decrementar tiempoAveria
		// si el coche est� esperando en un cruce, no se hace nada.
		// en otro caso:
		//1. Actualizar su �localizacion�
		//2. Actualizar su �kilometraje�
		//3. Si el coche ha llegado a un cruce (localizacion >= carretera.getLength())
		//3.1. Poner la localizaci�n igual a la longitud de la carretera.
		//3.2. Corregir el kilometraje.
		//3.3. Indicar a la carretera que el veh�culo entra al cruce.
		//3.4. Marcar que �ste veh�culo est� en un cruce (this.estEnCruce = true)
		if(tiempoAveria>0)
			tiempoAveria--;
		else if(!estEnCruce){
			localizacion+=velocidadActual;
			kilometraje +=velocidadActual;
			if(localizacion>=carretera.getLongitud()) {
				kilometraje-=(localizacion-carretera.getLongitud()); 
				carretera.getCruceDestino().entraVehiculoAlCruce(carretera.getId(),this);
				localizacion=carretera.getLongitud();
				this.estEnCruce=true;
				this.velocidadActual=0;
			}

		}

	}
	public void moverASiguienteCarretera() throws ErrorDeSimulacion {
		// Si la carretera no es null, sacar el veh�culo de la carretera.
		// Si hemos llegado al �ltimo cruce del itinerario, entonces:
		// 1. Se marca que el veh�culo ha llegado (this.haLlegado = true).
		//2. Se pone su carretera a null.
		////3. Se pone su �velocidadActual� y �localizacion� a 0.
		// y se marca que el veh�culo est� en un cruce (this.estaEnCruce = true).
		// En otro caso:
		// 1. Se calcula la siguiente carretera a la que tiene que ir.
		//2. Si dicha carretera no existe, se lanza excepci�n.
		//3. En otro caso, se introduce el veh�culo en la carretera.
		//4. Se inicializa su localizaci�n.
		if(carretera!=null)
			carretera.saleVehiculo(this);
		if(contadorCruces==itinerario.size()-1) {
			carretera.saleVehiculo(this);
			this.haLlegado=true;
			this.carretera=null;
			this.velocidadActual=0;
			this.localizacion=0;
			this.velocidadMaxima=0;
			this.estEnCruce=true;
		}else {
			this.carretera=itinerario.get(contadorCruces).carreteraHaciaCruce(itinerario.get(contadorCruces+1));
			if(carretera!=null) {
				this.contadorCruces++;
				this.localizacion=0;
				carretera.entraVehiculo(this);
				estEnCruce=false;
			}else
					throw new ErrorDeSimulacion("La siguiente carretera no existe");
		}

	}
	@Override
	protected String getNombreSeccion() {

		return "vehicle_report";
	}
}