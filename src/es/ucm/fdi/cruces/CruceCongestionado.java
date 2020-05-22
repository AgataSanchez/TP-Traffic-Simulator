package es.ucm.fdi.cruces;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulacion.Carretera;


public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {
	// no tiene atributos

	public CruceCongestionado(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override   
	protected void actualizaSemaforos() {
		
		CarreteraEntranteConIntervalo ri=new CarreteraEntranteConIntervalo(null,0);
		CarreteraEntranteConIntervalo rj=new CarreteraEntranteConIntervalo(null,0);
		
		for(CarreteraEntranteConIntervalo ca:this.carreterasEntrantes) {
			
			if(ca.colaVehiculos.size() > rj.colaVehiculos.size() &&  !ca.equals(this.carreterasEntrantes.get(this.indiceSemaforoVerde))) {
				
				rj=ca;
			}
		}
		if (this.indiceSemaforoVerde == -1)
		{
			if(this.carreterasEntrantes.indexOf(rj) == -1) this.indiceSemaforoVerde = 0;
			else this.indiceSemaforoVerde = this.carreterasEntrantes.indexOf(rj);
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).setIntervaloDeTiempo(Math.max(rj.colaVehiculos.size() / 2, 1));
		}
		else
		{
			
			ri = this.carreterasEntrantes.get(this.indiceSemaforoVerde);
			
			if (ri.tiempoConsumido())
			{
				ri.ponSemaforo(false);
				ri.setUnidadesDeTiempoUsadas(0);
				ri.setUsoCompleto(true);
				ri.setUsadaPorUnVehiculo(false);
				this.indiceSemaforoVerde++;
				if(this.carreterasEntrantes.indexOf(rj) == -1) this.indiceSemaforoVerde = (this.indiceSemaforoVerde) % this.carreterasEntrantes.size();
				else this.indiceSemaforoVerde = this.carreterasEntrantes.indexOf(rj);
				this.carreterasEntrantes.get(indiceSemaforoVerde).setIntervaloDeTiempo(Math.max(rj.colaVehiculos.size() / 2, 1));
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
			}
		}
		
	}
	//- Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
	//selecciona la carretera que tenga más vehículos en su cola de vehículos.
	//- Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=
	//-1) entonces:
	//1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"): 
	//1.1. Se pone el semáforo de "ri" a rojo.
	//1.2. Se inicializan los atributos de "ri".
	//1.3. Se busca la posición "max" que ocupa la primera carretera entrante
	//distinta de "ri" con el mayor número de vehículos en su cola.
	//1.4. "indiceSemaforoVerde" se pone a "max".  
	//1.5. Se pone el semáforo de la carretera entrante en la posición "max" ("rj")
	//a verde y se inicializan los atributos de "rj", entre ellos el 
	//"intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1).




	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		// TODO Auto-generated method stub
		return new CarreteraEntranteConIntervalo (carretera, 0);
	}


	@Override
	protected void completaDetallesSeccion(IniSection is) {
		// TODO Auto-generated method stub
		
	
		String queues="";
		if(carreterasEntrantes.isEmpty())
			is.setValue("queues","");
		for(CarreteraEntranteConIntervalo ca:carreterasEntrantes) {
			queues+= ca.toString();
			if(carreterasEntrantes.indexOf(ca) !=carreterasEntrantes.size()-1)
				queues+=",";
		}
		is.setValue("queues", queues);
		is.setValue("type", "mc");

	}


	@Override
	protected String getNombreSeccion() {
		// TODO Auto-generated method stub
		return "junction_report";
	} 
}