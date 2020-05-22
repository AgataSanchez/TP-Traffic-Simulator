package es.ucm.fdi.simulacion;


import es.ucm.fdi.cruces.CarreteraEntrante;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.ini.IniSection;

public class Cruce extends CruceGenerico<CarreteraEntrante>{
	
	
	public Cruce(String id) {
		super(id);
		
	}
	
	protected void actualizaSemaforos(){
		if(this.indiceSemaforoVerde == -1) {
			this.indiceSemaforoVerde = 0;
			carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
		}else {
			carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(false);
			this.indiceSemaforoVerde=(indiceSemaforoVerde+1)%carreterasEntrantes.size();
			carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);//Esa es la siguiente carretera entrante
			
		}
			
		// pone el sem�foro de la carretera actual a �rojo�, y busca la siguiente
		// carretera entrante para ponerlo a �verde�
	} 
	
	//@Override
	protected String getNombreSeccion() {
		return "junction_report";

	}
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String queues="";
		if(carreterasEntrantes.isEmpty())
			is.setValue("queues","");
		for(CarreteraEntrante ca:carreterasEntrantes) {
			queues+= ca.toString();
			if(carreterasEntrantes.indexOf(ca) !=carreterasEntrantes.size()-1)
				queues+=",";
		}
		is.setValue("queues", queues);
		
		// genera la secci�n queues = (r2,green,[]),...
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		// TODO Auto-generated method stub
		return new CarreteraEntrante(carretera);
	}
}
