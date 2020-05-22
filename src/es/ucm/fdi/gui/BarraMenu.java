package es.ucm.fdi.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.main.VentanaPrincipal;

public class BarraMenu extends JMenuBar {

	private Thread t = null;
	JMenu menuFicheros = null;
	JMenu menuSimulador = null;
	JMenu menuReport = null;

	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		// MANEJO DE FICHEROS
		menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros,mainWindow);
		// SIMULADOR
		menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador,controlador,mainWindow);
		// INFORMES
		menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport,mainWindow);
	}

	public void deshabilitar() {
		menuFicheros.setEnabled(false);
		menuSimulador.setEnabled(false);
		menuReport.setEnabled(false);
	}

	public void habilitar() {
		menuFicheros.setEnabled(true);
		menuSimulador.setEnabled(true);
		menuReport.setEnabled(true);
	}

	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow) {
		// TODO Auto-generated method stub
		JMenuItem generaInformes = new JMenuItem("Generar");
		generaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// OPCIONAL
				mainWindow.generaInformes();
			}
		});
		menuReport.add(generaInformes);

		JMenuItem limpiaAreaInformes = new JMenuItem("Clear");
		limpiaAreaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiarInformes();
			}

		});
		menuReport.add(limpiaAreaInformes);
	}

	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow) {
		// TODO Auto-generated method stub
		JMenuItem ejecuta = new JMenuItem("Ejecuta");
		ejecuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pasos = mainWindow.getSteps();
				int delay = mainWindow.getDelay();
				try {
					mainWindow.ejecuta(pasos, delay);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JMenuItem reinicia = new JMenuItem("Reinicia");
		reinicia.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controlador.reinicia();
				InputStream in= new ByteArrayInputStream(mainWindow.getTextoEditorEventos().getBytes());//AÑADIDO
				try {
					controlador.cargaEventos(in);
				} catch (ErrorDeSimulacion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		menuSimulador.add(ejecuta);
		menuSimulador.add(reinicia);

	}

	private void creaMenuFicheros(JMenu menuFicheros, VentanaPrincipal mainWindow) {
		// TODO Auto-generated method stub
		JMenuItem cargar = new JMenuItem("Carga Eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		//Guardar
		JMenuItem salvar = new JMenuItem("Salva Eventos");
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaFichero();
			}
		});
		//SalvarInformes
		JMenuItem salvarInformes = new JMenuItem("Salva Informes");
		salvarInformes.setMnemonic(KeyEvent.VK_R);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		salvarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaInformes();
			}
		});

		//Salir
		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salir();
			}
		});

		menuFicheros.add(cargar);
		menuFicheros.add(salvar);
		menuFicheros.addSeparator();
		menuFicheros.add(salvarInformes);
		menuFicheros.addSeparator();
		menuFicheros.add(salir);


	}
}
