package es.ucm.fdi.main;

import java.io.IOException;

import es.ucm.fdi.control.Controlador;

public class Hebra extends Thread{
	private int tiempo;
	private int pasos;
	private int cont;
	private Controlador controlador;
	
	
	public Hebra(int tiempo, Controlador ctrl, int pasos) {
		this.tiempo = tiempo;
		this.controlador = ctrl;
		this.pasos = pasos;
		this.cont =1;
	}
	@Override
	public void run() {
		
		while(!Thread.interrupted() && cont <=pasos) {
			try {
				controlador.ejecuta(1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{
				Thread.sleep(tiempo);
			}catch(InterruptedException e) {
				
			}
			
			
			
		}
		
	}
}
