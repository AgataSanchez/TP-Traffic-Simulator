package es.ucm.fdi.simulacion;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;

public abstract class ObjetoSimulacion {
	
	protected String id;
	public ObjetoSimulacion(String id) {
		this.id=id;
	}
	public String getId(){
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	public String generaInforme(int tiempo) {
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completaDetallesSeccion(is);
		return is.toString();
	}
	protected abstract void completaDetallesSeccion(IniSection is);
	public abstract void avanza() throws ErrorDeSimulacion;
	protected abstract String getNombreSeccion();
}
