package es.ucm.fdi.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.cruces.CruceGenerico;
import es.ucm.fdi.evento.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.gui.BarraMenu;
import es.ucm.fdi.gui.ComponenteMapa;
import es.ucm.fdi.gui.ModeloTablaCarreteras;
import es.ucm.fdi.gui.ModeloTablaCruces;
import es.ucm.fdi.gui.ModeloTablaEventos;
import es.ucm.fdi.gui.ModeloTablaVehiculos;
import es.ucm.fdi.gui.ObservadorSimuladorTrafico;
import es.ucm.fdi.gui.PanelAreaTexto;
import es.ucm.fdi.gui.PanelBarraEstado;
import es.ucm.fdi.gui.PanelEditorEventos;
import es.ucm.fdi.gui.PanelInformes;
import es.ucm.fdi.gui.PanelTabla;
import es.ucm.fdi.gui.ToolBar;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.simulacion.*;

public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico{

	public static Border bordePorDefecto =
			BorderFactory.createLineBorder(Color.black, 2);
	//SUPERIOR PANEL
	static private final String[] columnIdEventos =
		{ "#", "Tiempo", "Tipo" };
	private PanelAreaTexto panelEditorEventos;
	private PanelAreaTexto panelInformes;
	private PanelTabla<Evento> panelColaEventos;
	//MENU AND TOOL BAR
	private BarraMenu menuBar;
	private JFileChooser fc;
	private ToolBar toolbar;
	//GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	//STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;
	//INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera",
			"Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen",
			"Destino", "Longitud", "Vel. Max", "Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;
	//REPORT DIALOG
	//	private DialogoInformes dialogoInformes; // opcional
	//MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;
	private Controlador controlador;
	private Thread t;

	public VentanaPrincipal(String ficheroEntrada,Controlador ctrl) throws FileNotFoundException{
		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ?
				new File(ficheroEntrada) : null;
				this.initGUI();
				// añadimos la ventana principal como observadora
				ctrl.addObserver(this);

	}
	private void initGUI() {

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub

				salir();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
			// al salir pide confirmación
		});
		JPanel panelPrincipal = this.creaPanelPrincipal();
		this.setContentPane(panelPrincipal);

		// BARRA DE HERRAMIENTAS
		this.addToolBar(panelPrincipal);


		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior)
		JPanel panelCentral = this.createPanelCentral();
		panelPrincipal.add(panelCentral,BorderLayout.CENTER);

		this.createPanelSuperior(panelCentral);
		// MENU
		this.menuBar = new BarraMenu(this,this.controlador);

		this.setJMenuBar(menuBar);

		// PANEL INFERIOR
		this.createPanelInferior(panelCentral);
		// BARRA DE ESTADO INFERIOR
		// (contiene una JLabel para mostrar el estado delsimulador)
		this.addBarraEstado(panelPrincipal);
		// FILE CHOOSER
		this.fc = new JFileChooser();
		// REPORT DIALOG (OPCIONAL)
		this.pack();
		this.setVisible(true);
		//...
	}

	private void addToolBar(JPanel panelPrincipal) {
		// TODO Auto-generated method stub
		this.toolbar = new ToolBar(this, this.controlador);

		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);

	}
	private void addBarraEstado(JPanel panelPrincipal) {
		// TODO Auto-generated method stub1
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador!", this.controlador);
		panelPrincipal.add(this.panelBarraEstado,BorderLayout.PAGE_END);
	}
	private JPanel creaPanelPrincipal() {//CAMBIO
		// TODO Auto-generated method stub
		JPanel panelP=new JPanel();

		panelP.setLayout(new BoxLayout(panelP, BoxLayout.Y_AXIS));
		return panelP;
	}
	private void createPanelSuperior(JPanel panelCentral) {
		String texto = "";
		String titulo = "Events: ";
		try {
			texto = this.leeFichero(this.ficheroActual);
		} catch (FileNotFoundException e) {
			this.ficheroActual = null;
			this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
		}catch (IOException e) {
			JOptionPane.showMessageDialog(this,"OK");
		}
		titulo+=this.ficheroActual.getName();
		JPanel panel = new JPanel();
		panel.setLayout((new BoxLayout(panel, BoxLayout.LINE_AXIS)));
		this.panelEditorEventos = new PanelEditorEventos(titulo, true, texto, this);
		this.panelColaEventos = new PanelTabla<Evento>("Cola eventos: ",new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
		this.panelInformes = new PanelInformes("Informes: ", false, this.controlador);
		panel.add(this.panelEditorEventos);
		panel.add(this.panelColaEventos);
		panel.add(this.panelInformes);
		panelCentral.add(panel);

		this.pack();

		// TODO Auto-generated method stub

	}
	private void createPanelInferior(JPanel panelCentral) {
		JPanel inferior = new JPanel();
		JPanel izquierdo = new JPanel(new GridLayout(1,1));
		JPanel derecho = new JPanel(new GridLayout(3,1));
		inferior.setLayout((new BoxLayout(inferior, BoxLayout.LINE_AXIS)));
		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos", new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo, this.controlador));
		this.panelCarreteras = new PanelTabla<Carretera>("Carreteras", new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera, this.controlador));
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces", new ModeloTablaCruces(VentanaPrincipal.columnIdCruce, this.controlador));
		derecho.add(this.panelVehiculos);
		derecho.add(this.panelCarreteras);
		derecho.add(this.panelCruces);
		inferior.add(derecho);
		this.componenteMapa = new ComponenteMapa(this.controlador);
		izquierdo.add(new JScrollPane(this.componenteMapa, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		inferior.add(izquierdo);
		panelCentral.add(inferior);

		this.pack();
		// TODO Auto-generated method stub

	}
	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		// para colocar el panel superior e inferior
		panelCentral.setLayout(new GridLayout(2,1));
		return panelCentral;
	}
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		this.panelBarraEstado.setMensaje("Se ha producido un error en la simulacion "+ e.getMessage() );
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub1

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}
	public void cargaFichero() {
		// TODO Auto-generated method stub
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			try {
				String s = leeFichero(fichero);
				this.controlador.reinicia();
				this.ficheroActual = fichero;
				this.panelEditorEventos.setTexto(s);
				this.panelEditorEventos.setBorde("Events: " + this.ficheroActual.getName());
				this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() +
						" de eventos cargado into the editor");
			}
			catch (FileNotFoundException e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " +
						e.getMessage());
			}catch(IOException e) {
				JOptionPane.showMessageDialog(this, "OK");
			}
		}
	}
	private void muestraDialogoError(String string) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, string);

	}
	private String leeFichero(File fichero) throws IOException{
		// TODO Auto-generated method stub
		String cadena="";
		String resultado="";
		FileReader f = new FileReader(fichero);
		BufferedReader b= new BufferedReader(f);
		while((cadena = b.readLine())!= null) {
			resultado+=cadena+"\n";

		}
		b.close();

		return resultado;
	}
	public void salvaFichero() {
		// TODO Auto-generated method stub
		int returnVal = this.fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			String texto= this.panelEditorEventos.getTexto();
			guardaFichero(fichero,texto);
			this.controlador.reinicia();
			this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() +
					" de eventos salvado into the editor");
		}

	}
	private void guardaFichero(File fichero, String texto) {
		// TODO Auto-generated method stub

		try {
			FileWriter f = new FileWriter(fichero);
			BufferedWriter b= new BufferedWriter(f);
			b.write(texto);
			b.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		}

	}
	public void salvaInformes() {
		// TODO Auto-generated method stub
		int returnVal = this.fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			String texto= this.panelInformes.getTexto();
			guardaFichero(fichero,texto);
			this.controlador.reinicia();
			this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() +
					" de informes salvado into the editor");
		}

	}
	public void salir() {
		// TODO Auto-generated method stub
		int n = JOptionPane.showOptionDialog(this, "Estas seguro de que quieres salir?", "Salir",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) System.exit(0);

	}
	public int getSteps() {
		// TODO Auto-generated method stub
		return (Integer) this.toolbar.getSteps().getValue();
	}

	public int getDelay() {
		// TODO Auto-generated method stub
		return (Integer) this.toolbar.getDelay().getValue();
	}

	public void generaInformes() {//OPCIONAL
		// TODO Auto-generated method stub

	}
	public void limpiarInformes() {
		// TODO Auto-generated method stub
		this.panelInformes.limpiar();

	}

	public void limpiarEventos() {
		// TODO Auto-generated method stub
		this.panelEditorEventos.limpiar();

	}

	public String getTextoEditorEventos() {
		// TODO Auto-generated method stub
		return this.panelEditorEventos.getTexto();
	}
	public void setMensaje(String string) {
		// TODO Auto-generated method stub
		this.panelBarraEstado.setMensaje(string);
	}

	public void detiene() {
		this.t.interrupt();
	}

	public void ejecuta(int steps, int tiempo) throws IOException {		
		this.t=new Thread() {
			@Override
			public void run() {
				int cont = 1;

				try {
					menuBar.deshabilitar();
					toolbar.deshabilitar();

					while(!Thread.interrupted() && cont <=  steps) {
						controlador.ejecuta(1);
						Thread.sleep(tiempo);
						cont++;
					}
					menuBar.habilitar();
					toolbar.habilitar();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					panelBarraEstado.setMensaje(e.getMessage());

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					menuBar.habilitar();
					toolbar.habilitar();
				}
			}

		};
		this.t.start();
	}


}
