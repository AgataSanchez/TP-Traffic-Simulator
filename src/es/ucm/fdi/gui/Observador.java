package es.ucm.fdi.gui;

public interface Observador<T> {
	public void addObservador(T o);
	 public void removeObservador(T o);
}
