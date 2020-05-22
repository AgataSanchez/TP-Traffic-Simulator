package es.ucm.fdi.cruces;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulacion.Carretera;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> {
	protected int minValorIntervalo;
	protected int maxValorIntervalo;

	public CruceCircular(String id, int minValorIntervalo, int maxValorIntervalo) {
		// TODO Auto-generated constructor stub
		super(id);
		this.minValorIntervalo=minValorIntervalo;
		this.maxValorIntervalo=maxValorIntervalo;

	}


	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		// TODO Auto-generated method stub
		return new CarreteraEntranteConIntervalo (carretera, maxValorIntervalo);
	}

	@Override
	protected void actualizaSemaforos() {
		// TODO Auto-generated method stub
		if(indiceSemaforoVerde==-1) {
			this.carreterasEntrantes.get(0).ponSemaforo(true);
			indiceSemaforoVerde++;
		}
			

		else {

			CarreteraEntranteConIntervalo ri=carreterasEntrantes.get(indiceSemaforoVerde);
			//ri.ponSemaforo(true);
			if(ri.tiempoConsumido()) {
				ri.ponSemaforo(false);
				if(ri.usoCompleto())
					ri.setIntervaloDeTiempo(Math.min(ri.getIntervaloDeTiempo()+1,this.maxValorIntervalo));
				else if(!ri.usada())
					ri.setIntervaloDeTiempo(Math.max(ri.getIntervaloDeTiempo()-1,this.minValorIntervalo));
				indiceSemaforoVerde=(indiceSemaforoVerde+1) %carreterasEntrantes.size();//Hemos cambiado (+1)
				carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
				ri.setUnidadesDeTiempoUsadas(0);
				ri.setUsadaPorUnVehiculo(false);
				ri.setUsoCompleto(true);
			}

		}

		/*- Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
		selecciona la primera carretera entrante (la de la posición 0) y se pone su
		semáforo en verde.   
		- Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=
		-1) entonces:
		        1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
		         	1.1. Se pone el semáforo de "ri" a rojo.
		         	1.2. Si ha sido usada en todos los pasos (“ri.usoCompleto()”), se fija 
		         	el intervalo de tiempo a ... Sino, si no ha sido usada
		         	(“!ri.usada()”) se fija el intervalo de tiempo a ...
		         	1.3. Se coge como nueva carretera con semáforo a verde la inmediatamente
		         	Posterior a “ri”. */

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
		is.setValue("type", "rr");

	}


	@Override
	protected String getNombreSeccion() {
		// TODO Auto-generated method stub
		return "junction_report";
	}



}
