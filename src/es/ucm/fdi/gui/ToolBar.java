package es.ucm.fdi.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.main.Hebra;
import es.ucm.fdi.main.VentanaPrincipal;
import es.ucm.fdi.model.MapaCarreteras;


public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {
	private JSpinner steps;
	private JSpinner delay;
	private JTextField time;
	JButton botonSalir2;
	JButton botonCargar;
	JButton botonGuardar;
	JButton botonLimpiar;
	JButton botonCheckIn;
	JButton botonEjecuta;
	JButton botonLimpiarInforme ;
	JButton botonResetear;
	private boolean cargado=false;
	public ToolBar(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		// TODO Auto-generated constructor stub
		super();
		controlador.addObserver(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//Cargar
		botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(new
				ImageIcon("iconos/open.png"));
		botonCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.cargaFichero();
			}
		});
		this.add(botonCargar);

		//Guardar
		botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guarda un fichero de eventos");
		botonGuardar.setIcon(new
				ImageIcon("iconos/save.png"));
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.salvaFichero();
			}
		});
		this.add(botonGuardar);

		//Limpiar
		botonLimpiar = new JButton();
		botonLimpiar.setToolTipText("Limpia el panel de eventos");
		botonLimpiar.setIcon(new
				ImageIcon("iconos/clear.png"));
		botonLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.limpiarEventos();
				ventanaPrincipal.setMensaje("Se ha vaciado el panel de eventos");
			}
		});
		this.add(botonLimpiar);

		//Eventos
		botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		botonCheckIn.setIcon(new
				ImageIcon("iconos/events.png"));
		botonCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
					byte[] contenido = ventanaPrincipal.getTextoEditorEventos().getBytes();
					controlador.cargaEventos(new ByteArrayInputStream(contenido));
					cargado = true;
					ventanaPrincipal.setMensaje("Eventos cargados al simulador!");
				} catch (ErrorDeSimulacion err) {
					ventanaPrincipal.setMensaje("Error al cargar eventos. " + err.getMessage()); 
					cargado = false;
				}

			}
		});
		this.add(botonCheckIn);

		//RESET
		botonResetear = new JButton();
		botonResetear.setToolTipText("Resetea el simulador");

		//LIMPIAR INFORMES
		botonLimpiarInforme = new JButton();

		//SALIR
		botonSalir2 = new JButton();

		//Play
		botonEjecuta = new JButton();
		botonEjecuta.setToolTipText("Ejecuta un paso en la simulacion");
		botonEjecuta.setIcon(new
				ImageIcon("iconos/play.png"));
		botonEjecuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(cargado)
						ventanaPrincipal.ejecuta((Integer) steps.getValue(), (Integer) delay.getValue());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		this.add(botonEjecuta);

		//Detener simulador

		JButton botonDetener = new JButton();
		botonDetener.setToolTipText("Detiene el simulador");
		botonDetener.setIcon(new
				ImageIcon("iconos/stop.png"));
		botonDetener.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.detiene();
				ventanaPrincipal.setMensaje("Se ha detenido el simulador");
			}
		});
		this.add(botonDetener);

		//Reset

		botonResetear.setIcon(new
				ImageIcon("iconos/reset.png"));
		botonResetear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reinicia();
				ventanaPrincipal.setMensaje("Se ha reiniciado el simulador");
			}
		});
		this.add(botonResetear);

		//Delay

		this.add(new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 0, 10000, 500));
		this.delay.setToolTipText("pasos a ejecutar: 1-1000");
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		this.delay.setValue(0);
		this.add(delay);


		//Steps
		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		this.add(steps);



		//Time
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);


		botonLimpiarInforme.setToolTipText("Limpia el panel de eventos");
		botonLimpiarInforme.setIcon(new
				ImageIcon("iconos/delete_report.png"));
		botonLimpiarInforme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.limpiarInformes();
				ventanaPrincipal.setMensaje("Se ha vaciado el panel de informes");
			}
		});
		this.add(botonLimpiarInforme);

		//Salir


		botonSalir2.setIcon(new
				ImageIcon("iconos/exit.png"));
		botonSalir2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPrincipal.salir();
			}
		});
		this.add(botonSalir2);


	}

	public void deshabilitar() {
		botonSalir2.setEnabled(false);
		botonCargar.setEnabled(false);
		botonGuardar.setEnabled(false);
		botonLimpiar.setEnabled(false);
		botonCheckIn.setEnabled(false);
		botonEjecuta.setEnabled(false);
		botonLimpiarInforme.setEnabled(false) ;
		botonResetear.setEnabled(false);
	}

	public void habilitar() {
		botonSalir2.setEnabled(true);
		botonCargar.setEnabled(true);
		botonGuardar.setEnabled(true);
		botonLimpiar.setEnabled(true);
		botonCheckIn.setEnabled(true);
		botonEjecuta.setEnabled(true);
		botonLimpiarInforme.setEnabled(true) ;
		botonResetear.setEnabled(true);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.time.setText(""+tiempo);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.steps.setValue(1);
		this.time.setText("0");

	}

	public JSpinner getSteps() {
		return steps;
	}

	public JSpinner getDelay() {
		return delay;
	}

}
