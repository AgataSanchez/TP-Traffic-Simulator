package es.ucm.fdi.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.fdi.constructor.ParserEventos;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.ObservadorSimuladorTrafico;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.SimuladorTrafico;

public class Controlador {
	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasosSimulacion;

	public Controlador(SimuladorTrafico simulador, int pasosSimulacion, InputStream ficheroEntrada, OutputStream ficheroSalida) {
		this.simulador=simulador;
		this.pasosSimulacion=pasosSimulacion;
		this.ficheroEntrada=ficheroEntrada;
		this.ficheroSalida=ficheroSalida;
	}
	public void ejecuta() throws IOException, ErrorDeSimulacion{
		this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(pasosSimulacion,this.ficheroSalida);
	}

	public void ejecuta(int pasos) throws IOException {
		this.simulador.ejecuta(pasos,this.ficheroSalida);
	}

	public void reinicia() { this.simulador.reinicia(); }

	public void addObserver(ObservadorSimuladorTrafico o) {
		this.simulador.addObservador(o);
	}
	public void removeObservador(ObservadorSimuladorTrafico o) {
		// TODO Auto-generated method stub
		this.simulador.removeObservador(o);
	}

	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		Ini ini = null;
		try {
			// lee el fichero y carga su atributo iniSections
			ini = new Ini(inStream);
		}
		catch (IOException e) {
			throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		}
		// recorremos todas los elementos de iniSections para generar el evento
		// correspondiente
		for (IniSection sec : ini.getSections()) {
			// parseamos la secci�n para ver a que evento corresponde
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null) this.simulador.insertaEvento(e);
			else
				
				throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		}
	}
	
	public int getPasosSimulacion() {
		return pasosSimulacion;
	}
}
