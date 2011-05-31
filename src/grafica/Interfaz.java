package grafica;


import java.awt.*;

import javax.swing.*;

import archivos.Reconocedor;

import compositor.Aprendiz;

import sun.security.action.GetLongAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;


public class Interfaz implements ItemListener{
	//eventos
	private static final String SALIR = "salir";
	private static final String ACERCA_DE = "acerca_de";
	private static final String COMPONER = "componer";
	private static final String APRENDER = "aprender";
	
	private static final int OK_ACEPTAR = -1;
	private static final int SI_NO_OPCION = 0;
	private static final int OPCION_SI = 0;
	private static final int OPCION_NO = 1;
	private static final int TABS_POSICION = 3;	//tres = abajo
	
	private static final String TIPO_BASICO = "Básica";
	private static final String TIPO_INTERMEDIO = "Intermedia";
	private static final String TIPO_AVANZADO = "Avanzada";
	// labels
	private static final String LABEL_TONICA = "Tónica: ";
	private static final String LABEL_ESTILO = "Estilo: ";
	private static final String LABEL_CANT_COMPASES = "Cantidad de Compases: ";
	private static final String LABEL_TEMPO = "Tempo: ";
	private static final String LABEL_TIPO_CANCION = "Tipo de Canción: ";
	
	private Aprendiz aprendiz;
	
	private JFrame framePrincipal;
	private JMenuBar menuPrincipal;
	private JMenu opcion1;	//aprender
	private JMenu opcion2;	//base de datos
	private JMenu opcion3;	//log
	private JMenu opcion4;	//about
	private JMenuItem subOpcion1_1;	//aprender
	private JMenuItem subOpcion1_2;	//salir
	private JMenuItem subOpcion2_1; //actualizar base
	private JMenuItem subOpcion2_2;	//limpiar base y memoria
	private JMenuItem subOpcion3_1;	//ver log
	private JMenuItem subOpcion4_1;	//about
	private JTabbedPane pestania;
	private JButton botonComponer;
	
	private JPanel card;
	private JPanel panelComponer;	//Panel componer
	private JPanel panelEditar;	//Panel Ver/Editar
	
	private JLabel label2;
	
	private JComboBox jComboTipoComposicion;
	private JComboBox jComboEstiloBasico;
	private JComboBox jComboEstiloIntermedio;
	private JComboBox jComboEstiloAvanzado;
	//input de text
	private JTextField tonicaBasicoText;
	private JTextField tonicaIntermedioText;
	private JTextField tonicaAvanzadoText;
	private JTextField cantCompasesIntermedioText;
	private JTextField tempoIntermedioText;
	private JTextField cantCompasesAvanzadoText;
	private JTextField tempoAvanzadoText;
	
	public Interfaz(Aprendiz miAprendiz) {
		
		framePrincipal = new JFrame("Homerock -1.0");
		menuPrincipal = new JMenuBar();
		opcion1 = new JMenu("Archivo");
		opcion2 = new JMenu("Base de datos");
		opcion3 = new JMenu("Log");
		opcion4 = new JMenu("Ayuda");
		subOpcion1_1 = new JMenuItem("Cargar (Aprender)");
		subOpcion1_2 = new JMenuItem("Salir");
		subOpcion2_1 = new JMenuItem("Actualizar");
		subOpcion2_2 = new JMenuItem("Limpiar");
		subOpcion3_1 = new JMenuItem("Ver log");
		subOpcion4_1 = new JMenuItem("Acerca de...");
		pestania = new JTabbedPane(TABS_POSICION);
		aprendiz = miAprendiz;
		aprendiz.setInterfaz(this);
		crearFrame();
	}
	
	public void crearFrame() {
		
		ManejadorEventos manejador = new ManejadorEventos();
		framePrincipal.setSize(600, 400);
//		framePrincipal.setLayout(new GridLayout(1,1));
		
		this.armarMenuPrincipal(manejador);
		framePrincipal.setJMenuBar(menuPrincipal);
		
		this.armarPanelComponer(manejador);
		this.armarPanelEditar();
		
		pestania.addTab("Componer",panelComponer);
		pestania.addTab("Ver/Editar",panelEditar);
		framePrincipal.add(pestania);
		//framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.addWindowListener(new Cruz());
	}
	
	private void armarPanelComponer(ManejadorEventos manejador) {
		
		card = new JPanel();
		panelComponer = new JPanel();
		
		jComboTipoComposicion = new JComboBox();
		jComboTipoComposicion.addItem(TIPO_BASICO);
		jComboTipoComposicion.addItem(TIPO_INTERMEDIO);
		jComboTipoComposicion.addItem(TIPO_AVANZADO);
		jComboTipoComposicion.setEditable(false);
		jComboTipoComposicion.addItemListener(this);
		
		JPanel panelBasico = new JPanel();
		JPanel panelIntermedio = new JPanel();
		JPanel panelAvanzado = new JPanel();
	
		//panel basico
		//panelBasico.setLayout(new GridLayout(5,2));
		panelBasico.setLayout(new GridBagLayout());
		GridBagConstraints confL = new GridBagConstraints();
		confL.weighty=0.05;
		confL.gridx= 0 ;
		confL.gridy = 0;
		panelBasico.add(new JLabel(LABEL_TONICA),confL);
		confL.gridx= 1 ;
		confL.gridy = 0;
		tonicaBasicoText = new JTextField("",5);
		panelBasico.add(tonicaBasicoText,confL);
		confL.gridx= 0;
		confL.gridy = 1;
		panelBasico.add(new JLabel(LABEL_ESTILO),confL);
		jComboEstiloBasico = new JComboBox();
		confL.gridx= 1;
		confL.gridy = 1;
		panelBasico.add(jComboEstiloBasico,confL);
		
		panelIntermedio.setLayout(new GridLayout(5,2));
		panelIntermedio.add(new JLabel(LABEL_TONICA));
		tonicaIntermedioText = new JTextField("",5);
		panelIntermedio.add(tonicaIntermedioText);
		panelIntermedio.add(new JLabel(LABEL_ESTILO));
		jComboEstiloIntermedio = new JComboBox();
		panelIntermedio.add(jComboEstiloIntermedio);
		panelIntermedio.add(new JLabel(LABEL_CANT_COMPASES));
		cantCompasesIntermedioText = new JTextField("",3);
		panelIntermedio.add(cantCompasesIntermedioText);
		panelIntermedio.add(new JLabel(LABEL_TEMPO));
		tempoIntermedioText = new JTextField("",3);
		panelIntermedio.add(tempoIntermedioText);
		
		panelAvanzado.setLayout(new GridLayout(5,2));
		panelAvanzado.add(new JLabel(LABEL_TONICA));
		tonicaAvanzadoText = new JTextField("",5);
		panelAvanzado.add(tonicaAvanzadoText);
		panelAvanzado.add(new JLabel(LABEL_ESTILO));
		jComboEstiloAvanzado = new JComboBox();
		panelAvanzado.add(jComboEstiloAvanzado);
		panelAvanzado.add(new JLabel(LABEL_CANT_COMPASES));
		cantCompasesAvanzadoText = new JTextField("",3);
		panelAvanzado.add(cantCompasesAvanzadoText);
		panelAvanzado.add(new JLabel(LABEL_TEMPO));
		tempoAvanzadoText = new JTextField("",3);
		panelAvanzado.add(tempoAvanzadoText);
		panelAvanzado.add(new JLabel(LABEL_TIPO_CANCION));
		
		card.setLayout(new CardLayout());
		card.add(TIPO_BASICO,panelBasico);
		card.add(TIPO_INTERMEDIO,panelIntermedio);
		card.add(TIPO_AVANZADO,panelAvanzado);
		
		//boton componer
		botonComponer = getJButtonComponer();
		botonComponer.addActionListener(manejador);
		botonComponer.setActionCommand(COMPONER);
		
		panelComponer.setLayout(new GridBagLayout());
		GridBagConstraints confLayout = new GridBagConstraints();
		confLayout.weighty=0.05;
		confLayout.gridx= 0 ;
		confLayout.gridy = 0;
		panelComponer.add(new JLabel("Tipo de Composición: "),confLayout);
		confLayout.gridx= 1 ;
		confLayout.gridy = 0;
		panelComponer.add(jComboTipoComposicion, confLayout);
		
		confLayout.weighty=0.9;
		confLayout.gridwidth=2;//columnas que ocupa
		confLayout.gridx= 0;
		confLayout.gridy = 1;
		panelComponer.add(card,confLayout);
		
		confLayout.gridwidth=2;//columnas que ocupa
		confLayout.gridx= 0;
		confLayout.gridy = 2;
		panelComponer.add(botonComponer,confLayout);
		
	}
	
	public void itemStateChanged(ItemEvent evt) {
		
		CardLayout c1 = (CardLayout) card.getLayout();
		c1.show(card, (String) evt.getItem());
	}	
	
	private void armarPanelEditar() {
	
		panelEditar = new JPanel();
		label2 = new JLabel("VER - EDITAR");
		panelEditar.add(label2);
	}
	
	private void armarMenuPrincipal(ManejadorEventos manejador){

		menuPrincipal.add(opcion1);
		menuPrincipal.add(opcion2);
		menuPrincipal.add(opcion3);
		menuPrincipal.add(opcion4);
		
		opcion1.add(subOpcion1_1);
		opcion1.add(subOpcion1_2);
		opcion2.add(subOpcion2_1);
		opcion2.add(subOpcion2_2);
		opcion3.add(subOpcion3_1);
		opcion4.add(subOpcion4_1);
		
		subOpcion4_1.addActionListener(manejador);
		subOpcion4_1.setActionCommand(ACERCA_DE);
		subOpcion1_1.addActionListener(manejador);
		subOpcion1_1.setActionCommand(APRENDER);
		subOpcion1_2.addActionListener(manejador);
		subOpcion1_2.setActionCommand(SALIR);
		
	}
	
	private JButton getJButtonComponer() {
		if (botonComponer == null) {
			botonComponer = new JButton();
			//botonComponer.setBounds(new Rectangle(30, 120, 100, 50));
			botonComponer.setText("Componer Canción");
			botonComponer.setVisible(true);
		}
		return botonComponer;
	}
	
	private void cargarCombo() {
		ArrayList<String> lista = aprendiz.getComboEstilos();
		
		jComboEstiloBasico.removeAllItems();
		for (String estilo : lista) { 
			Interfaz.this.jComboEstiloBasico.addItem(estilo);
			Interfaz.this.jComboEstiloIntermedio.addItem(estilo);
			Interfaz.this.jComboEstiloAvanzado.addItem(estilo);
		}
	}
	
	class ManejadorEventos implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand() == SALIR) {
				int resultado = JOptionPane.showConfirmDialog(framePrincipal, "Desea salir?","Salir", SI_NO_OPCION);
				if (resultado == OPCION_SI) {
					System.exit(0);
				}
			}
			
			if (e.getActionCommand() == ACERCA_DE) {
				JOptionPane.showConfirmDialog(framePrincipal, "Homerock llego... nada será igual","Acerca de...", OK_ACEPTAR);
			}
			
			if (e.getActionCommand() == APRENDER) {
				
				aprendiz.iniciar();
				cargarCombo();
			}
			
			if (e.getActionCommand() == COMPONER) {
				String tipoComposicion = (String) jComboTipoComposicion.getSelectedItem();
				
				if (tipoComposicion.equals(TIPO_BASICO)){
					System.out.println("basico");
					String tonica = Interfaz.this.tonicaBasicoText.getText();
					String estilo = (String) Interfaz.this.jComboEstiloBasico.getSelectedItem();
					if (tonica.trim().equals("") || estilo == null) {
						JOptionPane.showConfirmDialog(framePrincipal, "Falta ingresar datos","Componer", OK_ACEPTAR);
						return;
					}
					if (!Reconocedor.esAcordeValido(tonica)) {
						JOptionPane.showConfirmDialog(framePrincipal, "El acorde de tonica es incorrecto","Componer", OK_ACEPTAR);
						return;
					}
					aprendiz.componer(tonica, estilo);	//lanus campeon - lunes 30-05 18:21Hs
				}
				
				if (tipoComposicion.equals(TIPO_INTERMEDIO)){
					System.out.println("Intermedio");
					String tonica = Interfaz.this.tonicaIntermedioText.getText();
					String estilo = (String) Interfaz.this.jComboEstiloIntermedio.getSelectedItem();
					String tempo = Interfaz.this.tempoIntermedioText.getText();
					String cantCompases = Interfaz.this.cantCompasesIntermedioText.getText();
					if (tonica.trim().equals("") || estilo == null || tempo.trim().equals("") || cantCompases.trim().equals("")) {
						JOptionPane.showConfirmDialog(framePrincipal, "Falta ingresar datos","Componer", OK_ACEPTAR);
						return;
					}
					if (!Reconocedor.esAcordeValido(tonica)) {
						JOptionPane.showConfirmDialog(framePrincipal, "El acorde de tonica es incorrecto","Componer", OK_ACEPTAR);
						return;
					}
					aprendiz.componer(tonica, estilo, tempo, cantCompases);
				}

				if (tipoComposicion.equals(TIPO_AVANZADO)){
					System.out.println("avanzada");
					String tonica = Interfaz.this.tonicaAvanzadoText.getText();
					String estilo = (String) Interfaz.this.jComboEstiloAvanzado.getSelectedItem();
					String tempo = Interfaz.this.tempoAvanzadoText.getText();
					String cantCompases = Interfaz.this.cantCompasesAvanzadoText.getText();
					// FALTA LA ESTRUCTURA
					//********************
					if (tonica.trim().equals("") || estilo == null || tempo.trim().equals("") || cantCompases.trim().equals("")) {
						JOptionPane.showConfirmDialog(framePrincipal, "Falta ingresar datos","Componer", OK_ACEPTAR);
						return;
					}
					if (!Reconocedor.esAcordeValido(tonica)) {
						JOptionPane.showConfirmDialog(framePrincipal, "El acorde de tonica es incorrecto","Componer", OK_ACEPTAR);
						return;
					}
					aprendiz.componer(tonica, estilo, tempo, cantCompases);
				}	
			}
		}
	}
	
	class Cruz implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
		
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			System.exit(0);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			
		}
	}
	
	/*public static void main(String arg[]) {
		Interfaz miInterfaz = new Interfaz();
		miInterfaz.crearFrame();
	}*/
	
}

