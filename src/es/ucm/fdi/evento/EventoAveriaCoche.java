package es.ucm.fdi.evento;


import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.Vehiculo;

public class EventoAveriaCoche extends Evento{

	protected String []averias;
	protected int duracion;
	
	public EventoAveriaCoche(int tiempo, String [] averias, int duracion) {
		super(tiempo);
		this.averias=averias;
		this.duracion=duracion;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeEvento {
		// TODO Auto-generated method stub
		for(String a:averias) {
			Vehiculo v=mapa.getVehiculo(a);
			v.setTiempoAveria(this.duracion);
		}
		
	}
	@Override
	public String toString(){
		return "[make_vehicle_faulty]" + "\n" +"time = "+ this.getTiempo() + "\n" + "vehicles = " + averias +
		"\n" + "duration = " + this.duracion;

	}

	@Override
	public String Nombre() {
		// TODO Auto-generated method stub
		String vehiculos="";
		for(String a:averias)
			vehiculos+=a;
		return "Break vehicles [" + vehiculos +"]" ;
		
	}

}
