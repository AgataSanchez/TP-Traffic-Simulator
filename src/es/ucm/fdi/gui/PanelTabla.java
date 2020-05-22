package es.ucm.fdi.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

public class PanelTabla<T> extends JPanel{
	private ModeloTabla<T> modelo;
	public PanelTabla(String bordeId, ModeloTabla<T> modelo) {
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,3),bordeId));
		this.modelo = modelo;
		JTable tabla = new JTable(this.modelo);
		this.add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
	}
}
