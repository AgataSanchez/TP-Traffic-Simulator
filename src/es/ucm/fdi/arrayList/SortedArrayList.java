package es.ucm.fdi.arrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.InputMismatchException;

public class SortedArrayList<E> extends ArrayList<E>{

	private Comparator<E> cmp;
	public SortedArrayList(Comparator<E> cmp) {
		this.cmp=cmp;
	}
	@Override
	public boolean add(E e) {
		if(!this.contains(e)) {
			super.add(e);
			return true;
		}
		else
			return false;

	}
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for(E e:c)
			add(e);
		return true;
	}

	public void add(int index, E element) {
		throw new InputMismatchException("No se puede insertar de esta forma");
	}

	public boolean addAll(int index, Collection<? extends E> e) {
		throw new InputMismatchException("No se puede insertar de esta forma");
	}

	public E set (int index, E element) {
		throw new InputMismatchException("No se puede insertar de esta forma");
	}
	// sobreescribir los m�todos que realizan operaciones de
	// inserci�n basados en un �ndice para que lancen excepcion.
	// Ten en cuenta que esta operaci�n romper�a la ordenaci�n.
	// estos m�todos son add(int index, E element),
	// addAll(int index, Colection<? Extends E>) y E set(int index, E element).
}
