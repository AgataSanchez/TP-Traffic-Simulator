package es.ucm.fdi.constructor;

import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.ini.*;

public abstract class ConstructorEventos {
	// cada clase dar� los valores correspondientes a estos atributos
	// en la constructora
	protected String etiqueta; // etiqueta de la entrada (�new_road�, etc..)
	protected String[] claves; // campos de la entrada (�time�, �vehicles�, etc.)
	protected String[] valoresPorDefecto;

	ConstructorEventos() {
		this.etiqueta = null;
		this.claves = null;
		this.valoresPorDefecto=null; 
	}

	public abstract Evento parser(IniSection section);

	protected static String identificadorValido(IniSection seccion, String clave) {
		String s = seccion.getValue(clave);
		if (!esIdentificadorValido(s))
			throw new IllegalArgumentException("El valor " + s + " para " + clave +
					" no es un ID valido");
		else return s;
	}
	protected static String [] identificadorValidoIti(IniSection seccion, String clave) {
		String s = seccion.getValue(clave);
		String [] total=s.split(",");
		
		for(String a:total) {
			if (!esIdentificadorValido(a))
				throw new IllegalArgumentException("El valor " + a + " para " + clave +
						" no es un ID valido");
		}
		return total;

	}
	// identificadores v�lidos
	// s�lo pueden contener letras, n�meros y subrayados
	private static boolean esIdentificadorValido(String id) {
		return id != null && id.matches("[a-z0-9_]+");
	}
	protected static int parseaInt(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " +
					clave);
		else return Integer.parseInt(seccion.getValue(clave));
	}
	protected static int parseaInt(IniSection seccion, String clave,int valorPorDefecto) {
		String v = seccion.getValue(clave);
		return (v != null) ? Integer.parseInt(seccion.getValue(clave)) :
			valorPorDefecto;
	}
	protected static double parseaDouble(IniSection seccion, String clave,double valorPorDefecto) {
		String v = seccion.getValue(clave);
		return (v != null) ? Double.parseDouble(seccion.getValue(clave)) : valorPorDefecto;
	}
	protected static long parseaLong(IniSection seccion, String clave,long valorPorDefecto) {
		String v = seccion.getValue(clave);
		return (v != null) ? Long.parseLong(seccion.getValue(clave)) : valorPorDefecto;
		
	}
	protected static int parseaIntNoNegativo(IniSection seccion, String clave, int valorPorDefecto) {
		int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave +
					" no es un ID valido");
		else return i;
	}
	protected static double parseaDoubleNoNegativo(IniSection seccion, String clave, double valorPorDefecto) {
		double i = ConstructorEventos.parseaDouble(seccion, clave, valorPorDefecto);
		if (i < 0.0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave +
					" no es un ID valido");
		else return i;
	}
	
	
}

