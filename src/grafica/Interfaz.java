package grafica;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import canciones.Cancion;
import canciones.Compas;
import canciones.Estrofa;

import excepciones.ORMException;
import excepciones.PersistenciaException;

import nucleo.Controlador;
import utiles.Constantes;
import archivos.Reconocedor;


public class Interfaz implements ItemListener{
	//eventos
	private static final String SALIR = "salir";
	private static final String ACERCA_DE = "acerca_de";
	private static final String COMPONER = "componer";
	private static final String APRENDER = "aprender";
	private static final String ACTUALIZAR = "actualizar";
	private static final String MODIFICAR_CANCION ="modificar_cancion";

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

	private Controlador controlador;

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
	private JButton botonModificarCancion;

	private JPanel card;
	private JPanel panelComponer;	//Panel componer
	private JPanel panelEditar;	//Panel Ver/Editar
	private JPanel panelCancion;	//Panel Ver/Editar

	private JLabel labelEditar;

	private JComboBox jComboTipoComposicion;
	private JComboBox jComboEstiloBasico;
	private JComboBox jComboEstiloIntermedio;
	private JComboBox jComboEstiloAvanzado;
	private JComboBox jComboEstructuraAvanzado;
	//input de text
	private JTextField tonicaBasicoText;
	private JTextField tonicaIntermedioText;
	private JTextField tonicaAvanzadoText;
	private JTextField cantCompasesIntermedioText;
	private JTextField tempoIntermedioText;
	private JTextField tempoAvanzadoText;



	public Interfaz(Controlador miControlador) {

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
		controlador = miControlador;
		crearFrame();
	}

	public void crearFrame() {

		ManejadorEventos manejador = new ManejadorEventos();
		framePrincipal.setSize(600, 400);
		//		framePrincipal.setLayout(new GridLayout(1,1));

		this.armarMenuPrincipal(manejador);
		framePrincipal.setJMenuBar(menuPrincipal);

		this.armarPanelComponer(manejador);
		this.armarPanelEditar(manejador);

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
		panelAvanzado.add(new JLabel(LABEL_TEMPO));
		tempoAvanzadoText = new JTextField("",3);
		panelAvanzado.add(tempoAvanzadoText);
		panelAvanzado.add(new JLabel(LABEL_TIPO_CANCION));
		jComboEstructuraAvanzado = new JComboBox();
		Interfaz.this.jComboEstructuraAvanzado.addItem(Constantes.ESTRUCTURA_A);
		Interfaz.this.jComboEstructuraAvanzado.addItem(Constantes.ESTRUCTURA_B);
		Interfaz.this.jComboEstructuraAvanzado.addItem(Constantes.ESTRUCTURA_C);
		panelAvanzado.add(jComboEstructuraAvanzado);


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

	
	
	
	private void armarPanelEditar(ManejadorEventos manejador) {

		panelEditar = new JPanel();
		panelEditar.setLayout(new BoxLayout(panelEditar, BoxLayout.Y_AXIS));
		labelEditar = new JLabel("VER - EDITAR");


		panelEditar.add(labelEditar);

		
		
		//boton 
		botonModificarCancion = getJButtonModificarCancion();
		botonModificarCancion.addActionListener(manejador);
		botonModificarCancion.setActionCommand(MODIFICAR_CANCION);
		panelEditar.add(botonModificarCancion);
		
		
		// panel para la cancion
		panelCancion = new JPanel();
		panelCancion.setLayout(new BoxLayout(panelCancion,BoxLayout.Y_AXIS));
	
		panelEditar.add(panelCancion);

	}

	ArrayList<TablaAcordes> estrofas = new ArrayList<TablaAcordes>() ;
	
	private void actualizarPanelEditar(Cancion cancionNueva){

		panelCancion.removeAll();
		estrofas.clear();
		
		for (Estrofa e : cancionNueva.getEstrofas()){
			TablaAcordes tablaAcordes = new TablaAcordes(e);
			estrofas.add(tablaAcordes);
			panelCancion.add(tablaAcordes);
			
		}

	}
	
	
	class TablaAcordes extends JPanel {
		MiTablaAcordes tabla;
		public TablaAcordes(Estrofa laEstrofa) {
			//super(new GridLayout(1,0));

			tabla = new MiTablaAcordes();
			tabla.llenarAcordes(laEstrofa);
			JTable table = new JTable(tabla);
			table.setPreferredScrollableViewportSize(new Dimension(400, 80));
			
			table.setFillsViewportHeight(true);

			//Create the scroll pane and add the table to it.
			JScrollPane scrollPane = new JScrollPane(table);

			//Add the scroll pane to this panel.
			add(scrollPane);
		}
		
		public MiTablaAcordes getTabla(){
			return tabla;
		}
		
		class MiTablaAcordes extends AbstractTableModel{

			String[] columnNames = {"1er Acorde",
					"2do Acorde",
					"3er Acorde",
					"4to Acorde",
					"Modificar"
			};

			Object[][] data;

			public void llenarAcordes (Estrofa laEstrofa) {

				int cantCompases = laEstrofa.getCantidadCompases();
				int cantCol = 5;
				data = new Object[cantCompases][cantCol];

				for (int i =0 ; i<cantCompases;i++){
					Compas compases = laEstrofa.getListaDeCompases().get(i);
					for(int j=0;j<cantCol;j++){


						if (j>=compases.getAcordes().size()){
							if(j == cantCol-1){
								data[i][j]=new Boolean(false);
							} else {
								data[i][j] = "";
							}
						} else {
							data[i][j]=compases.getAcordes().get(j).getNombre();
						}
					}
				}
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				return data.length;
			}

	        public String getColumnName(int col) {
	            return columnNames[col];
	        }
	        
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}

			/*
			 * JTable uses this method to determine the default renderer/
			 * editor for each cell.  If we didn't implement this method,
			 * then the last column would contain text ("true"/"false"),
			 * rather than a check box.
			 */
			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			/*
			 * Don't need to implement this method unless your table's
			 * editable.
			 */
			
			public boolean isCellEditable(int row, int col) {
				//Note that the data/cell address is constant,
				//no matter where the cell appears onscreen.
				return true;
				/*
				if (col < 2) {
					return false;
				} else {
					return true;
				}
				*/
			}

		}	//Fin de clase MiTablaAcordes
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
		subOpcion2_1.addActionListener(manejador);
		subOpcion2_1.setActionCommand(ACTUALIZAR);

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

	private JButton getJButtonModificarCancion() {
		if (botonModificarCancion == null) {
			botonModificarCancion = new JButton();
			botonModificarCancion.setText("Modificar Canción");
			botonModificarCancion.setVisible(true);
		}
		return botonModificarCancion;
	}
	
	
	public void cargarCombo() {
		ArrayList<String> lista = controlador.getComboEstilos();

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

			if (e.getActionCommand() == ACTUALIZAR) {
				try {
					controlador.memoriaABaseDeDatos();
				} catch (ORMException e1) {
					System.err.println(e1.getMessage());
				} catch (PersistenciaException e1) {
					System.err.println(e1.getMessage());
				}
			}

			if (e.getActionCommand() == APRENDER) {

				controlador.aprender();
				cargarCombo();
			}
			
			if (e.getActionCommand() == MODIFICAR_CANCION ) {
				for(int i=0; i < estrofas.size();i++){
					 TablaAcordes t = estrofas.get(i);
					 int numRows = t.getTabla().getRowCount();
					 int numCols = 		 t.getTabla().getColumnCount();


			            for (int j=0; j < numRows; j++) {
			                System.out.print("    row " + j + ":");
			                for (int k=0; k < numCols; k++) {
			                	
			                    System.out.print("  " + t.getTabla().data[j][k]);
			                }
			                System.out.println();
			            }
			            System.out.println("--------------------------");

					 
					/*
					      int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
					 * */
				}
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
					controlador.componer(tonica, estilo);
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
					controlador.componer(tonica, estilo, tempo, cantCompases);
				}

				if (tipoComposicion.equals(TIPO_AVANZADO)){
					System.out.println("avanzada");
					String tonica = Interfaz.this.tonicaAvanzadoText.getText();
					String estilo = (String) Interfaz.this.jComboEstiloAvanzado.getSelectedItem();
					String tempo = Interfaz.this.tempoAvanzadoText.getText();
					String estructura = (String) Interfaz.this.jComboEstructuraAvanzado.getSelectedItem();

					if (tonica.trim().equals("") || estilo == null || tempo.trim().equals("")) {
						JOptionPane.showConfirmDialog(framePrincipal, "Falta ingresar datos","Componer", OK_ACEPTAR);
						return;
					}
					if (!Reconocedor.esAcordeValido(tonica)) {
						JOptionPane.showConfirmDialog(framePrincipal, "El acorde de tonica es incorrecto","Componer", OK_ACEPTAR);
						return;
					}
					System.out.println(estructura);

					controlador.componerConEstructruras(tonica, estilo, tempo, estructura);
				}	

				// cuando se termino la composicion
				// mostrar la cancion quese compuso en editar
				Cancion cancionNueva = controlador.getCancionNueva();
				if (cancionNueva!=null){
					actualizarPanelEditar(cancionNueva);
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


