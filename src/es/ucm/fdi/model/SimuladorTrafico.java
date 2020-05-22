package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeEvento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.Observador;
import es.ucm.fdi.gui.ObservadorSimuladorTrafico;
import es.ucm.fdi.arrayList.SortedArrayList;

public class SimuladorTrafico implements
Observador<ObservadorSimuladorTrafico>{
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;

	private List<ObservadorSimuladorTrafico> observadores;

	public SimuladorTrafico() {
		this.observadores = new ArrayList<>();
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {
			@Override
			public int compare(Evento o1, Evento o2) {
				// TODO Auto-generated method stub
				if(o1.getTiempo()<o2.getTiempo())
					return -1;
				else if(o1.getTiempo()>o2.getTiempo())
					return 1;
				else
					return 0;	
			}
		};
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por �tiempo�
	}
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws IOException {
		int limiteTiempo = this.contadorTiempo + pasosSimulacion -1;
		String informe="";
		try {
			while (this.contadorTiempo <= limiteTiempo) {
				for(Evento e:eventos)
					if(e.getTiempo()==contadorTiempo)
						e.ejecuta(mapa);
				mapa.actualizar();
				this.contadorTiempo++;
				//this.notificaAvanza();
				informe+=mapa.generateReport(contadorTiempo);
				this.notificaAvanza();
				byte[] fin=informe.getBytes();
				if(informe!=null)
					ficheroSalida.write(fin);
				informe="";
			}
			actualizaEventos();
		}catch(ErrorDeSimulacion e) {
			System.out.println(e.getMessage());
			this.notificaError(e);
		}

	}
	private void actualizaEventos() {
		// TODO Auto-generated method stub
		if(!this.eventos.isEmpty()) {
			for(int i = eventos.size()-1; i >= 0; i--) {
				if(eventos.get(i).getTiempo() <= this.contadorTiempo)
					eventos.remove(i);
			}
		}
		
	}
	private void notificaAvanza() {
		// TODO Auto-generated method stub
		
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.avanza(this.contadorTiempo, this.mapa, this.eventos);
		}
		
	}
	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		if (e != null) {
			if (e.getTiempo() < this.contadorTiempo) {
				ErrorDeSimulacion err = new ErrorDeSimulacion("ERROR al insertar un evento");
				this.notificaError(err);
				throw err;
			} else {
				this.eventos.add(e);
				this.notificaNuevoEvento(); // se notifica a los observadores
			}
		} else {
			ErrorDeSimulacion err = new ErrorDeSimulacion("ERROR, el evento no es valido");
			this.notificaError(err); // se notifica a los observadores
			throw err;
		}
		
		// inserta un evento en �eventos�, controlando que el tiempo de
		// ejecuci�n del evento sea menor que �contadorTiempo�
	}

	private void notificaError(ErrorDeSimulacion err) {
		// TODO Auto-generated method stub
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.errorSimulador(this.contadorTiempo,this.mapa,this.eventos, err);
		}
		
	}
	
	private void notificaNuevoEvento() {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.addEvento(this.contadorTiempo,this.mapa,this.eventos);
		}
	}
	
	private void notificaReinicia() {
		// TODO Auto-generated method stub
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.reinicia(this.contadorTiempo,this.mapa,this.eventos);
		}
		
	}
	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		// TODO Auto-generated method stub
		if (o != null && !this.observadores.contains(o)) {
			this.observadores.add(o);
		}
	}
	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		// TODO Auto-generated method stub
		if (o != null && this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}
	public void reinicia() {
		// TODO Auto-generated method stub
		this.mapa.reinicia();
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {
			@Override
			public int compare(Evento o1, Evento o2) {
				// TODO Auto-generated method stub
				if(o1.getTiempo()<o2.getTiempo())
					return -1;
				else if(o1.getTiempo()>o2.getTiempo())
					return 1;
				else
					return 0;	
			}
		};
		this.eventos.clear();
		
		this.notificaReinicia();
	}
	
}
