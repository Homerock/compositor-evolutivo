package GUI;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import midiPlayer.MIDIPlayer;
import nucleo.Controlador;
import nucleo.Reproductor;

import org.jdesktop.application.Application;

import utiles.Constantes;
import utiles.FuncionesComunes;
import archivos.Archivos;
import archivos.Reconocedor;
import canciones.Cancion;
import canciones.Compas;
import canciones.Estrofa;
import estructura.MatrizAcordes;
import excepciones.ORMException;
import excepciones.PersistenciaException;

public class Pantalla extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileLogs = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAyuda = new JMenuItem();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JTextField jTextNombre;
    private JTextArea jTextComentarios;
    private JTextField jTextNombreCanciones;
    private JTextArea jTextComentariosCanciones;
    private JCheckBox jCheckEstructura;
    private JCheckBox jCheckTempo;
    private JCheckBox jCheckCantCompases;
    //private JCheckBox jCheckEstilo;
    //private JCheckBox jCheckTonica;
    private JLabel jLabelTonica;
    private JLabel jLabelEstilo;
    //private JLabel statusBar = new JLabel();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JSplitPane jSplitPane2 = new JSplitPane();
    
    // 3 paneles principales
    private JPanel panelOpciones = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelDetalle = new JPanel();
    
    // paneles de la opcion aprender
    private JPanelBackground panelDetalleAprender;
    
    // paneles de la opcion componer
    private JPanel panelComponer = new JPanel();
    private JPanelBackground panelDetalleComponer;
    private JPanelBackground panelImagenComponer;
    private JPanelBackground panelImagenCanciones;
    private JPanelBackground panelComponerOpciones;
    private JPanelBackground panelComponerBotones;
    private JPanel panelTablaCancion;		//panel para ver la cancion que se esta componiendo
    
    // paneles de la opcion BD
    private JPanelBackground panelBD;
    private JPanelBackground panelDetalleBD;
    
    private CardLayout cardLayout1 = new CardLayout();
    
    private JPanel panelAprender;
    private JScrollPane panelArbolAprender = new JScrollPane();
    private JPanelBackground panelAprenderBotones;
    
    private JPanel panelCanciones;
    private JScrollPane panelArbolCanciones = new JScrollPane();
    private JPanelBackground panelCancionesBotones;
    
    private JScrollPane panelScrollEditar;
    
    private Arbol fileTree = new Arbol(new File(FuncionesComunes.getPathOS()));
    //private JTree fileTreeCanciones = new JTree();
    
	private JComboBox jComboEstilo;
	private JComboBox jComboTonica;
	private JComboBox jComboEstructuraAvanzado;
	//input de text
	//private JTextField tonicaText;
	private JTextField cantCompasesText;
	private JTextField tempoText;
	
	private JButton botonBD = new JButton();
	private JButton botonAprender = new JButton();
	private JButton botonComponer = new JButton();
	private JButton botonCanciones = new JButton();

	private JCheckBox jCheckGuardar;
	private JCheckBox jCheckGuardarCanciones;
	private JButton botonGuardar;
	private JButton botonGuardarCanciones;
	private JButton botonGenerarComposicion;
	private JButton botonModificarCancion;
	private JButton botonReproducirCancion;
	private JButton botonPausarCancion;
	private JButton botonDetenerCancion;
	private JButton botonAprenderCancion;
	private JButton botonGuardarEnBD;
	
	DefaultMutableTreeNode nodoPadre;
	DefaultTreeModel modelo;
	
	private Imagenes misImagenes;
	private Controlador controlador;
	Cancion cancionNueva;
	Cancion cancionModificada;
	
	//estrofas---->
	ArrayList<TablaAcordes> estrofas = new ArrayList<TablaAcordes>();
	
	boolean DEBUG=false;
	//Reproductor reproductor=null;
	private MIDIPlayer panelMidi;
	
	/**
	 * 
	 * @param miControlador
	 */
    public Pantalla(Controlador miControlador) {
    	
    	controlador = miControlador;
    	misImagenes = new Imagenes();  
	    
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 
     * @throws Exception
     */
    private void jbInit() throws Exception {
    	
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
        this.setSize(new Dimension(1000, 500));
        this.setTitle("Homerock");
        menuFile.setText("Archivo");
        
        menuFileLogs.setText("Ver logs");
        menuFileLogs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileLogs_ActionPerformed(ae);
            }
        });
        
        menuFileExit.setText("Salir");
        menuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileExit_ActionPerformed(ae);
            }
        });
        menuHelp.setText("Ayuda");
        
        menuHelpAyuda.setText("Ayuda");
        menuHelpAyuda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                helpAyuda_ActionPerformed(ae);
            }
        });
        
        menuHelpAbout.setText("Acerca de...");
        menuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                helpAbout_ActionPerformed(ae);
            }
        });
       // statusBar.setText("");
        panelOpciones.setLayout(cardLayout1);
        
        botonAprender.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_APRENDER)));
        botonAprender.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonAprender_actionPerformed(e);
                }
            });
        botonComponer.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_COMPONER)));
        botonComponer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonComponer_actionPerformed(e);
                }
            });
        botonCanciones.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_CANCIONES)));
        botonCanciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                botonCanciones_actionPerformed(e);
            }
        });
        botonBD.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_CEREBRO)));
        botonBD.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonBD_actionPerformed(e);
                }
            });

        menuFile.add(menuFileLogs);
        menuFile.add(menuFileExit);
        menuFileExit.setName("menuFileExit");
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAyuda);
        menuHelpAbout.setName("menuHelpAyuda");
        menuHelp.add(menuHelpAbout);
        menuHelpAbout.setName("menuHelpAbout");
        menuBar.add(menuHelp);
        menuHelp.setName("menuHelp");
        //this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        
        ManejadorEventos manejador = new ManejadorEventos();
        
        panelDetalle.setLayout(cardLayout1);
        armarPanelDetalleComponer(manejador);
        armarPanelDetalleAprender(manejador);
        armarPanelDetalleBD();
        panelDetalle.add(panelImagenComponer, "panelImagenComponer");
        panelDetalle.add(panelImagenCanciones, "panelImagenCanciones");
        panelDetalle.add(panelDetalleComponer, "panelDetalleComponer");
        panelDetalle.add(panelDetalleAprender, "panelDetalleAprender");
        panelDetalle.add(panelDetalleBD, "panelDetalleBD");
        
        armarPanelAprender(manejador);
        armarPanelComponer(manejador);
        armarPanelBD(manejador);
        armarPanelCanciones(manejador);

        panelOpciones.add(panelComponer, "panelComponer");
        panelOpciones.add(panelAprender, "panelAprender");
        panelOpciones.add(panelBD, "panelBD");
        panelOpciones.add(panelCanciones, "panelCanciones");
        
        jSplitPane2.add(panelOpciones, JSplitPane.LEFT);
        jSplitPane2.add(panelDetalle, JSplitPane.RIGHT);
        panelDetalle.setName("panelDetalle");
        jSplitPane1.add(jSplitPane2, JSplitPane.RIGHT);
        
        panelBotones.setLayout(new GridLayout(4,1));
        panelBotones.add(botonAprender, null);
        panelBotones.add(botonComponer, null);
        panelBotones.add(botonCanciones, null);
        panelBotones.add(botonBD, null);
        
        botonComponer.setBackground(Color.gray);
        
        jSplitPane1.add(panelBotones, JSplitPane.LEFT);
        this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
        Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
 
        panelMidi.checker.start();
    }

    /**
     * 
     */
    private void armarPanelAprender(ManejadorEventos manejador) {
    	
    	panelArbolAprender.getViewport().add(fileTree, null);
 
    	panelAprender = new JPanel();
    	//panelAprender.setLayout(new GridLayout(2,1));
    	BoxLayout panelAprenderLayout = new BoxLayout(panelAprender, javax.swing.BoxLayout.Y_AXIS);
    	panelAprender.setLayout(panelAprenderLayout);
    	panelAprender.add(panelArbolAprender);
    	
    	panelAprenderBotones = new JPanelBackground();
    	try {
    		panelAprenderBotones.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		botonAprenderCancion = getJButtonAprenderCancion();
		botonAprenderCancion.addActionListener(manejador);
		botonAprenderCancion.setActionCommand(Constantes.APRENDER_CANCION);
		panelAprenderBotones.add(botonAprenderCancion);
		botonAprenderCancion.setName("botonAprenderCancion");
		panelAprenderBotones.setName("panelDetalleAprenderBoton");
		
		panelAprender.add(panelAprenderBotones);
    }
    
    /**
     * 
     * @param manejador
     */
    private void armarPanelCanciones(ManejadorEventos manejador) {
    	
    	
    	nodoPadre = new DefaultMutableTreeNode("Canciones");
    	modelo = new DefaultTreeModel(nodoPadre);
    	JTree tree = new JTree(modelo);
    	
    	cargarArbolCanciones();
    	
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		      public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
		            .getPath().getLastPathComponent();
		        String tokens = archivos.Utiles.obtenerCadena(node.toString(), " ");
		        // busca la cancion, crea el archivo y la aprende
		        cancionNueva = controlador.buscarCancionSeleccionada(tokens);
		        if (cancionNueva == null) {
		        	return;
		        }
		        
		        jTextNombreCanciones.setText(cancionNueva.getNombreFantasia());
		        jTextComentariosCanciones.setText(cancionNueva.getComentario());
		        botonModificarCancion.setEnabled(true);
		        botonReproducirCancion.setEnabled(true);
		        botonPausarCancion.setEnabled(true);
		        botonDetenerCancion.setEnabled(true);
		        jCheckGuardarCanciones.setEnabled(true);
		        actualizarPanelEditar(cancionNueva);
		        
				File f = new File(cancionNueva.getNombreArchivo()+".mid");
				if (f.exists()) {
					if (panelMidi.getDevice().getSequencer() != null) {
						panelMidi.addSong(f);
					}
				}
				
		       /*
		        try {
		        	if (reproductor != null) {
						reproductor.detener();
					}
					reproductor = new Reproductor(cancionNueva.getNombreArchivo()+".mid");
				} catch (InvalidMidiDataException e1) {
					System.err.println(e1.getMessage());
					reproductor = null;
				} catch (IOException e1) {
					System.err.println(e1.getMessage());
					reproductor = null;
				} catch (MidiUnavailableException e1) {
					System.err.println(e1.getMessage());
					reproductor = null;
				}
				*/
				CardLayout c2 = (CardLayout) panelDetalle.getLayout();
		        c2.show(panelDetalle, "panelDetalleComponer");
		      }
		    }); 
		
    	panelArbolCanciones.getViewport().add(tree, null);
    	tree.setPreferredSize(new java.awt.Dimension(250, 461));
    	
    	panelCanciones = new JPanel();
    	panelCanciones.setLayout(new GridLayout(2,1));
    	panelCanciones.add(panelArbolCanciones);
    	
    	panelCancionesBotones = new JPanelBackground();
    	try {
    		panelCancionesBotones.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		BoxLayout panelCancionesBotonesLayout = new BoxLayout(panelCancionesBotones, javax.swing.BoxLayout.Y_AXIS);
		panelCancionesBotones.setLayout(panelCancionesBotonesLayout);
		
		//////////////////////////////////////////////////////////////////////
		{
			jCheckGuardarCanciones = new JCheckBox();
			panelCancionesBotones.add(jCheckGuardarCanciones);
			jCheckGuardarCanciones.setName("jCheckGuardarCanciones");
			;
			jCheckGuardarCanciones.setPreferredSize(new java.awt.Dimension(100, 18));
			jCheckGuardarCanciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckGuardarCanciones.isSelected()) {
						jTextNombreCanciones.setEnabled(true);
						jTextComentariosCanciones.setEnabled(true);
						botonGuardarCanciones.setEnabled(true);
					} else {
						jTextNombreCanciones.setEnabled(false);
						jTextComentariosCanciones.setEnabled(false);
						botonGuardarCanciones.setEnabled(false);
					}
					panelCancionesBotones.repaint();
				}
			});
		}
		
		panelCancionesBotones.add(getJTextNombreCanciones());
		JScrollPane scroll = new JScrollPane(getJTextComentariosCanciones());
		//scroll.setPreferredSize(new java.awt.Dimension(898, 393));
		panelCancionesBotones.add(scroll);

		botonGuardarCanciones = getJButtonGuardarCanciones();
		botonGuardarCanciones.addActionListener(manejador);
		botonGuardarCanciones.setActionCommand(Constantes.GUARDAR_CANCION_MODIFICADA);
		botonGuardarCanciones.setPreferredSize(new java.awt.Dimension(175, 27));
		botonGuardarCanciones.setName("botonGuardarCanciones");
		panelCancionesBotones.add(botonGuardarCanciones);
		//////////////////////////////////////////////////////////////////////
		
    	panelCanciones.add(panelCancionesBotones);
    }
    
    /**
     * 
     */
    private void cargarArbolCanciones() {
    	
    	String nombre;
    	DefaultMutableTreeNode nodoCancion;
    	
    	nodoPadre.removeAllChildren();
    	modelo.reload();
    	
    	Map<String, Cancion> canciones = controlador.getListaCanciones();
    	Iterator it = canciones.entrySet().iterator();
		//tengo que iterar para listar todas la matrices del map
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			Cancion miCancion = (Cancion) e.getValue();
			nombre = e.getKey() + " - " + miCancion.getNombreFantasia();
			nodoCancion=new DefaultMutableTreeNode(nombre);
	    	modelo.insertNodeInto(nodoCancion, nodoPadre, 0);
		}
    	
    }
    
    private void agregarCancionAlArbol(String nombre) {
    	
    	int cant = controlador.getListaCanciones().size()+1;
    	
    	DefaultMutableTreeNode nodoCancion=new DefaultMutableTreeNode(String.valueOf(cant)+" - "+nombre);
    	modelo.insertNodeInto(nodoCancion, nodoPadre, 0);
    	
    }
    
    /**
     * 
     * @param manejador
     */
    private void armarPanelBD(ManejadorEventos manejador) {
    	
    	panelBD = new JPanelBackground();
    	
        try {
        	panelBD.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		botonGuardarEnBD = getJButtonGuardarEnBD();
		botonGuardarEnBD.addActionListener(manejador);
		botonGuardarEnBD.setActionCommand(Constantes.ACTUALIZAR);
		panelBD.add(botonGuardarEnBD);
    }
    
    /**
     * 
     * @param manejador
     */
	private void armarPanelComponer(ManejadorEventos manejador) {

    	panelComponerOpciones = new JPanelBackground();
		panelComponer = new JPanel();
		
		try {
			panelComponerOpciones.setLayout(null);
			panelComponerOpciones.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		//tonicaText = new JTextField("",5);
		//panelComponerOpciones.add(tonicaText);
		//tonicaText.setBounds(95, 11, 57, 22);
		//tonicaText.setName("tonicaText");
		
		jComboTonica = new JComboBox();
		panelComponerOpciones.add(jComboTonica);
		//jComboTonica.setBounds(95, 11, 57, 22);
		jComboTonica.setBounds(95, 44, 80, 22);
		jComboTonica.setName("tonicaText");
		
		jComboEstilo = new JComboBox();
		panelComponerOpciones.add(jComboEstilo);
		//jComboEstilo.setBounds(95, 44, 149, 22);
		jComboEstilo.setBounds(95, 11, 150, 22);
		jComboEstilo.setName("jComboEstilo");
		jComboEstilo.addActionListener(manejador);
		jComboEstilo.setActionCommand(Constantes.CAMBIA_ESTILO);

		cantCompasesText = new JTextField("",3);
		panelComponerOpciones.add(cantCompasesText);
		cantCompasesText.setBounds(198, 106, 52, 22);
		cantCompasesText.setName("cantCompasesText");
		tempoText = new JTextField("",3);
		panelComponerOpciones.add(tempoText);
		tempoText.setBounds(95, 75, 57, 22);
		tempoText.setName("tempoText");

		jComboEstructuraAvanzado = new JComboBox();
		Pantalla.this.jComboEstructuraAvanzado.addItem(Constantes.ESTRUCTURA_A);
		Pantalla.this.jComboEstructuraAvanzado.addItem(Constantes.ESTRUCTURA_B);
		Pantalla.this.jComboEstructuraAvanzado.addItem(Constantes.ESTRUCTURA_C);
		panelComponerOpciones.add(jComboEstructuraAvanzado);
		jComboEstructuraAvanzado.setBounds(125, 141, 167, 22);
		jComboEstructuraAvanzado.setName("jComboEstructuraAvanzado");
		
		jLabelTonica = new JLabel();
		panelComponerOpciones.add(jLabelTonica);
		jLabelTonica.setBounds(31, 45, 74, 19);
		jLabelTonica.setName("jLabelTonica");
		;
				
		jLabelEstilo = new JLabel();
		panelComponerOpciones.add(jLabelEstilo);
		jLabelEstilo.setBounds(31, 12, 81, 19);
		jLabelEstilo.setName("jLabelEstilo");
		jLabelEstilo.setText("Estilo");
				
		{
			jCheckCantCompases = new JCheckBox();
			panelComponerOpciones.add(jCheckCantCompases);
			jCheckCantCompases.setBounds(9, 105, 183, 24);
			jCheckCantCompases.setName("jCheckCantCompases");
			jCheckCantCompases.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckCantCompases.isSelected()) {
						cantCompasesText.setEnabled(true);
						jComboEstructuraAvanzado.setEnabled(false);
						jCheckEstructura.setSelected(false);
					} else {
						cantCompasesText.setEnabled(false);
					}
					panelComponerOpciones.repaint();
				}
			});
		}
		{
			jCheckTempo = new JCheckBox();
			panelComponerOpciones.add(jCheckTempo);
			jCheckTempo.setBounds(9, 77, 82, 19);
			jCheckTempo.setName("jCheckTempo");
			jCheckTempo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckTempo.isSelected()) {
						tempoText.setEnabled(true);
					} else {
						tempoText.setEnabled(false);
					}
					panelComponerOpciones.repaint();
				}
			});
		}
		{
			jCheckEstructura = new JCheckBox();
			panelComponerOpciones.add(jCheckEstructura);
			jCheckEstructura.setBounds(9, 142, 111, 19);
			jCheckEstructura.setName("jCheckEstructura");
			jCheckEstructura.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckEstructura.isSelected()) {
						jComboEstructuraAvanzado.setEnabled(true);
						cantCompasesText.setEnabled(false);
						jCheckCantCompases.setSelected(false);
					} else {
						jComboEstructuraAvanzado.setEnabled(false);
					}
					panelComponerOpciones.repaint();
				}
			});
		}

		//boton componer

		panelComponerBotones = new JPanelBackground();
		BoxLayout panelComponerBotonesLayout = new BoxLayout(panelComponerBotones, javax.swing.BoxLayout.Y_AXIS);
		panelComponerBotones.setLayout(panelComponerBotonesLayout);
		panelComponerOpciones.setPreferredSize(new java.awt.Dimension(298, 221));
		
		botonGenerarComposicion = getJButtonGenerarComposicion();
		panelComponerBotones.add(botonGenerarComposicion);
		botonGenerarComposicion.setName("botonGenerarComposicion");
		botonGenerarComposicion.addActionListener(manejador);
		botonGenerarComposicion.setActionCommand(Constantes.COMPONER);
		botonGenerarComposicion.setPreferredSize(new java.awt.Dimension(208, 27));
		{
			jCheckGuardar = new JCheckBox();
			panelComponerBotones.add(jCheckGuardar);
			jCheckGuardar.setName("jCheckGuardar");
			jCheckGuardar.setPreferredSize(new java.awt.Dimension(100, 18));
			jCheckGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckGuardar.isSelected()) {
						jTextNombre.setEnabled(true);
						jTextComentarios.setEnabled(true);
						botonGuardar.setEnabled(true);
					} else {
						jTextNombre.setEnabled(false);
						jTextComentarios.setEnabled(false);
						botonGuardar.setEnabled(false);
					}
					panelComponerBotones.repaint();
				}
			});
		}
		
		panelComponerBotones.add(getJTextNombre());
		JScrollPane scroll = new JScrollPane(getJTextComentarios());
		
		panelComponerBotones.add(scroll);
		scroll.setPreferredSize(new java.awt.Dimension(898, 393));

		botonGuardar = getJButtonGuardarCancion();
		botonGuardar.setEnabled(false);
		panelComponerBotones.add(botonGuardar);
		botonGuardar.addActionListener(manejador);
		botonGuardar.setActionCommand(Constantes.GUARDAR_CANCION);
		botonGuardar.setPreferredSize(new java.awt.Dimension(175, 27));
		botonGuardar.setName("botonGuardarCancion");

		panelComponerBotones.setPreferredSize(new java.awt.Dimension(294, 221));
		try {
			panelComponerBotones.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		panelComponer.setLayout(new GridLayout(2,1));
		panelComponer.add(panelComponerOpciones);
		panelComponer.add(panelComponerBotones);
		
	}
	
	/**
	 * 
	 * @param manejador
	 */
	private void armarPanelDetalleAprender(ManejadorEventos manejador) {
		
    	panelDetalleAprender = new JPanelBackground();
    	//JPanelBackground panelDetalleAprenderBoton = new JPanelBackground();
    	
    	try {
    		panelDetalleAprender.setBackground(misImagenes.getImagenURL(Constantes.FONDO_1));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//BoxLayout panelDetalleAprenderLayout = new BoxLayout(panelDetalleAprender, javax.swing.BoxLayout.Y_AXIS);
		//panelDetalleAprender.setLayout(panelDetalleAprenderLayout);

		ImageIcon imagenAprender;
		try {
			imagenAprender = new ImageIcon(misImagenes.getImagenURL(Constantes.IMAGEN_APRENDER));
			JLabel etiqueta = new JLabel(imagenAprender);
			panelDetalleAprender.add(etiqueta);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
		
	}

	/**
	 * 
	 * @param manejador
	 */
	private void armarPanelDetalleComponer(ManejadorEventos manejador) {
		
		panelImagenComponer = new JPanelBackground();
		panelImagenCanciones = new JPanelBackground();
		
		ImageIcon imagenComponer;
		try {
			imagenComponer = new ImageIcon(misImagenes.getImagenURL(Constantes.IMAGEN_COMPONER));
			JLabel etiqueta = new JLabel(imagenComponer);
			panelImagenComponer.add(etiqueta);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
		
		ImageIcon imagenCanciones;
		try {
			imagenCanciones = new ImageIcon(misImagenes.getImagenURL(Constantes.IMAGEN_CANCIONES));
			JLabel etiqueta = new JLabel(imagenCanciones);
			panelImagenCanciones.add(etiqueta);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
		
		panelDetalleComponer = new JPanelBackground();
		BoxLayout panelDetalleComponerLayout = new BoxLayout(panelDetalleComponer, javax.swing.BoxLayout.Y_AXIS);
		panelDetalleComponer.setLayout(panelDetalleComponerLayout);
		
		JPanelBackground panelModificar = new JPanelBackground();
		panelModificar.setLayout(new FlowLayout());
		
		// panel para la cancion
		panelTablaCancion = new JPanel(); //Background();
		panelTablaCancion.setName("panelTablaCancion");
		
		//boton 
		botonModificarCancion = getJButtonModificarCancion();
		botonModificarCancion.addActionListener(manejador);
		botonModificarCancion.setActionCommand(Constantes.MODIFICAR_CANCION);
		
		panelModificar.add(botonModificarCancion);
		botonModificarCancion.setName("botonModificarCancion");

		panelTablaCancion.setLayout(new BoxLayout(panelTablaCancion,BoxLayout.Y_AXIS));
		
		
		JPanelBackground panelReproductor = new JPanelBackground();
		
		panelReproductor.setLayout(new FlowLayout());
		
		
		//boton REPTODUCIR 
		botonReproducirCancion = getJButtonReproducirCancion();
		botonReproducirCancion.addActionListener(manejador);
		botonReproducirCancion.setActionCommand(Constantes.REPRODUCIR_CANCION);
		panelReproductor.add(botonReproducirCancion);
		botonReproducirCancion.setName("botonReproducirCancion");

		//boton PAUSAR
		botonPausarCancion = getJButtonPausarCancion();
		botonPausarCancion.addActionListener(manejador);
		botonPausarCancion.setActionCommand(Constantes.PAUSAR_CANCION);
		panelReproductor.add(botonPausarCancion);
		botonPausarCancion.setName("botonPausarCancion");
		
		//boton DETENER
		botonDetenerCancion = getJButtonDetenerCancion();
		botonDetenerCancion.addActionListener(manejador);
		botonDetenerCancion.setActionCommand(Constantes.DETENER_CANCION);
		panelReproductor.add(botonDetenerCancion);
		botonDetenerCancion.setName("botonDetenerCancion");
		
		try {
			panelImagenComponer.setBackground(misImagenes.getImagenURL(Constantes.FONDO_1));
			panelImagenCanciones.setBackground(misImagenes.getImagenURL(Constantes.FONDO_1));
			panelModificar.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
			panelReproductor.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		panelScrollEditar = new JScrollPane();
		panelScrollEditar.setName("panelScrollEditar");
		panelScrollEditar.setViewportView(panelTablaCancion);
		
		panelDetalleComponer.add(panelModificar);
		panelDetalleComponer.add(panelScrollEditar);
		
/*		panelDetalleComponer.add(panelReproductor);
		*/
		panelMidi = new MIDIPlayer();
		panelDetalleComponer.add(panelMidi);
	}
		
	/**
	 * 
	 */
	private void armarPanelDetalleBD() {
		
		panelDetalleBD = new JPanelBackground();
		
		try {
			panelDetalleBD.setBackground(misImagenes.getImagenURL(Constantes.FONDO_1));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		ImageIcon imagenBase;
		try {
			imagenBase = new ImageIcon(misImagenes.getImagenURL(Constantes.IMAGEN_CEREBRO));
			JLabel etiqueta = new JLabel(imagenBase);
			panelDetalleBD.add(etiqueta);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	private JButton getJButtonGuardarEnBD() {
		if (botonGuardarEnBD == null) {
			botonGuardarEnBD = new JButton();
			botonGuardarEnBD.setText("Actualizar Base de Datos");
			botonGuardarEnBD.setVisible(true);
		}
		return botonGuardarEnBD;
	}
	
	/**
	 * 
	 * @return
	 */
    private JButton getJButtonGenerarComposicion() {
		if (botonGenerarComposicion == null) {
			botonGenerarComposicion = new JButton();
			;
			botonGenerarComposicion.setVisible(true);
		}
		return botonGenerarComposicion;
	}
    
    /**
     * 
     * @return
     */
    private JButton getJButtonModificarCancion() {
		if (botonModificarCancion == null) {
			botonModificarCancion = new JButton();
			botonModificarCancion.setVisible(true);
			;
			//botonModificarCancion.setIcon(new ImageIcon ("./img/botonModificar.png"));
			
		}
		return botonModificarCancion;
	}
	
    /**
     * 
     * @return
     */
    private JButton getJButtonGuardarCancion() {
		if (botonGuardar == null) {
			botonGuardar = new JButton();
			;
			botonGuardar.setVisible(true);
		}
		return botonGuardar;
	}
    
    /**
     * 
     * @return
     */
    private JButton getJButtonGuardarCanciones() {
		if (botonGuardarCanciones == null) {
			botonGuardarCanciones = new JButton();
			;
			botonGuardarCanciones.setVisible(true);
		}
		return botonGuardarCanciones;
	}

    /**
     * 
     * @return
     */
	private JButton getJButtonReproducirCancion() {
		
		if (botonReproducirCancion == null) {
			botonReproducirCancion = new JButton();
			try {
				botonReproducirCancion.setIcon(new ImageIcon (misImagenes.getImagenURL(Constantes.BOTON_PLAY)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			//botonReproducirCancion.setText("Reproducir");
			botonReproducirCancion.setVisible(true);
		}
		return botonReproducirCancion ;
	}
		
	/**
	 * 
	 * @return
	 */
	private JButton getJButtonPausarCancion() {
		
		if (botonPausarCancion == null) {
			botonPausarCancion= new JButton();
			try {
				botonPausarCancion.setIcon(new ImageIcon (misImagenes.getImagenURL(Constantes.BOTON_PAUSE)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			//botonPausarCancion.setText("Pausa");
			botonPausarCancion.setVisible(true);
		}
		return botonPausarCancion;
	}
	
	/**
	 * 
	 * @return
	 */
	private JButton getJButtonDetenerCancion() {
		
		if (botonDetenerCancion == null) {
			botonDetenerCancion= new JButton();
			try {
				botonDetenerCancion.setIcon(new ImageIcon (misImagenes.getImagenURL(Constantes.BOTON_STOP)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			botonDetenerCancion.setVisible(true);
		}
		return botonDetenerCancion;
	}
	
	/**
	 * 
	 * @return
	 */
	private JButton getJButtonAprenderCancion() {
		
		if (botonAprenderCancion == null) {
			botonAprenderCancion= new JButton();
			;
			botonAprenderCancion.setVisible(true);
		}
		return botonAprenderCancion;
	}

	
	/**
	 * 
	 * @param e
	 */
	void fileLogs_ActionPerformed(ActionEvent e) {
		getControlador().mostrarVentana();
		
    }

	/**
	 * 
	 * @param e
	 */
	void fileExit_ActionPerformed(ActionEvent e) {
		int resultado = JOptionPane.showConfirmDialog(Pantalla.this, "Desea salir?","Salir", Constantes.SI_NO_OPCION);
		if (resultado == Constantes.OPCION_SI) {
			System.exit(0);
		}
    }

	/**
	 * 
	 * @param e
	 */
    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new About(), "Acerca de...", JOptionPane.PLAIN_MESSAGE);
    }

    /**
	 * 
	 * @param e
	 */
    void helpAyuda_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new Ayuda(), "Ayuda", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 
     * @param e
     */
    private void botonAprender_actionPerformed(ActionEvent e) {
    	botonAprender.setBackground(Color.gray);
    	botonComponer.setBackground(null);
    	botonCanciones.setBackground(null);
    	botonBD.setBackground(null);
   
    	if (panelMidi.getDevice().getSequencer() != null) {
    		panelMidi.getDevice().stop();
    	}
/*    	if (reproductor != null) {
			reproductor.detener();
		}*/
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelAprender");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelDetalleAprender");
    }

    /**
     * 
     * @param e
     */
    private void botonComponer_actionPerformed(ActionEvent e) {
    	vaciarPanelDetalle();
    	vaciarPanelEditar();
    	vaciarPanelOpciones();
    	cargarCombo();
    	botonAprender.setBackground(null);
    	botonComponer.setBackground(Color.gray);
    	botonCanciones.setBackground(null);
    	botonBD.setBackground(null);
    	
    	if (panelMidi.getDevice().getSequencer() != null) {
    		panelMidi.getDevice().stop();
    	}
/*    	if (reproductor != null) {
			reproductor.detener();
		}*/
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelComponer");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelImagenComponer");
    }

    /**
     * 
     * @param e
     */
    private void botonBD_actionPerformed(ActionEvent e) {
    	botonAprender.setBackground(null);
    	botonComponer.setBackground(null);
    	botonCanciones.setBackground(null);
    	botonBD.setBackground(Color.gray);
    	
    	if (panelMidi.getDevice().getSequencer() != null) {
    		panelMidi.getDevice().stop();
    	}
/*    	if (reproductor != null) {
			reproductor.detener();
		}*/
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelBD");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelDetalleBD");
    }
    
    /**
     * 
     * @param e
     */
    private void botonCanciones_actionPerformed(ActionEvent e) {
    	vaciarPanelDetalle();
    	vaciarPanelEditar();
    	vaciarPanelOpcionesCanciones();
    	botonAprender.setBackground(null);
    	botonComponer.setBackground(null);
    	botonCanciones.setBackground(Color.gray);
    	botonBD.setBackground(null);
    	
    	if (panelMidi.getDevice().getSequencer() != null) {
    		panelMidi.getDevice().stop();
    	}
/*    	if (reproductor != null) {
			reproductor.detener();
		}*/
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelCanciones");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelImagenCanciones");
    }
    
    /**
     * 
     */
    public void cargarCombo() {
		ArrayList<String> lista = controlador.getComboEstilos();

		jComboEstilo.removeAllItems();
		for (String estilo : lista) { 
			Pantalla.this.jComboEstilo.addItem(estilo);
		}
	}
    
    /**
     * 
     */
    public void cargarComboTonicas() {
    	
    	String estilo = (String) Pantalla.this.jComboEstilo.getSelectedItem();
    	if (estilo == null) {
    		return;
    	}
    	
		ArrayList<String> lista = controlador.getComboTonicas(estilo);

		jComboTonica.removeAllItems();
		for (String tonica : lista) { 
			Pantalla.this.jComboTonica.addItem(tonica);
		}
	}
    
    /**
     * 
     * @param cancionNueva
     */
    private void actualizarPanelEditar(Cancion cancionNueva){

		panelTablaCancion.removeAll();//panel donde se muetran las etrofas
		estrofas.clear();//lista donde estan las estrofas
		JLabel labelTempo = new JLabel("Tempo: "+cancionNueva.getTempo());
		JLabel labelEspacio = new JLabel("\n");
		
		try {
			panelTablaCancion.add(labelTempo);
			panelTablaCancion.add(labelEspacio);
			for (Estrofa e : cancionNueva.getEstrofas()){
				TablaAcordes tablaAcordes = new TablaAcordes(e);
				
				estrofas.add(tablaAcordes);
				
				JLabel labelEstilo = new JLabel(e.getEstilo());//label del estilo
				labelEstilo.repaint();//ver
				panelTablaCancion.add(labelEstilo);
				panelTablaCancion.add(tablaAcordes);
				
			}
			//panelDetalleComponer.repaint();
			//panelScrollEditar.repaint(); 
			//panelTablaCancion.repaint();
			panelDetalle.updateUI();
		
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
		
	}
    
    /**
     * 
     */
    private void vaciarPanelEditar() {
    	
    	panelTablaCancion.removeAll();//panel donde se muetran las etrofas
		estrofas.clear();//lista donde estan las estrofas
		panelDetalle.updateUI();
    }
    
    /**
     * 
     */
    private void vaciarPanelOpciones() {
    	
    	//tonicaText.setText("");
    	cantCompasesText.setText("");
    	tempoText.setText("");
    	jTextComentarios.setText("Comentarios");
    	jTextComentarios.setEnabled(false);
    	jTextNombre.setText("Nombre");
    	jTextNombre.setEnabled(false);
    	botonGuardar.setEnabled(false);
    	jCheckGuardar.setEnabled(false);
    	jCheckGuardar.setSelected(false);
    	
    }
    
    /**
     * 
     */
    private void vaciarPanelOpcionesCanciones() {
    	
    	jTextComentariosCanciones.setText("Comentarios");
    	jTextComentariosCanciones.setEnabled(false);
    	jTextNombreCanciones.setText("Nombre");
    	jTextNombreCanciones.setEnabled(false);
    	botonGuardarCanciones.setEnabled(false);
    	jCheckGuardarCanciones.setEnabled(false);
    	jCheckGuardarCanciones.setSelected(false);
    }
    
    /**
     * 
     */
    private void vaciarPanelDetalle() {
    	
    	botonModificarCancion.setEnabled(false);
    	botonPausarCancion.setEnabled(false);
    	botonReproducirCancion.setEnabled(false);
    	botonDetenerCancion.setEnabled(false);
    	jCheckGuardarCanciones.setEnabled(false);
    }
    
    /**
     * 
     * @return
     */
    public JTextArea getJTextComentarios() {
    	if(jTextComentarios == null) {
    		jTextComentarios = new JTextArea();
    		jTextComentarios.setName("jTextComentarios");
    		jTextComentarios.setPreferredSize(new java.awt.Dimension(250, 210));
    	}
    	return jTextComentarios;
    }
    
    /**
     * 
     * @return
     */
    public JTextArea getJTextComentariosCanciones() {
    	if(jTextComentariosCanciones == null) {
    		jTextComentariosCanciones = new JTextArea();
    		jTextComentariosCanciones.setName("jTextComentariosCanciones");
    		jTextComentariosCanciones.setPreferredSize(new java.awt.Dimension(250,210));
    	}
    	return jTextComentariosCanciones;
    }
    
    /**
     * 
     * @return
     */
    public JTextField getJTextNombre() {
    	if(jTextNombre == null) {
    		jTextNombre = new JTextField();
    		jTextNombre.setName("jTextNombre");
    		jTextNombre.setPreferredSize(new java.awt.Dimension(250, 26));
    	}
    	return jTextNombre;
    }
    
    /**
     * 
     * @return
     */
    public JTextField getJTextNombreCanciones() {
    	if(jTextNombreCanciones == null) {
    		jTextNombreCanciones = new JTextField();
    		jTextNombreCanciones.setName("jTextNombreCanciones");
    		jTextNombreCanciones.setPreferredSize(new java.awt.Dimension(250, 26));
    	}
    	return jTextNombreCanciones;
    }

    public Controlador getControlador() {
		return controlador;
	}


	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

    
    public class ManejadorEventos implements ActionListener{

		//Cancion cancionNueva;
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand() == Constantes.CAMBIA_ESTILO) {
				cargarComboTonicas();
			}

			if (e.getActionCommand() == Constantes.SALIR) {
				int resultado = JOptionPane.showConfirmDialog(Pantalla.this, "Desea salir?","Salir", Constantes.SI_NO_OPCION);
				if (resultado == Constantes.OPCION_SI) {
					System.exit(0);
				}
			}

			if (e.getActionCommand() == Constantes.ACERCA_DE) {
				JOptionPane.showConfirmDialog(Pantalla.this, "Homerock llego... nada será igual","Acerca de...", Constantes.OK_ACEPTAR);
			}

			if (e.getActionCommand() == Constantes.ACTUALIZAR) {
				try {
					controlador.memoriaABaseDeDatos();
					JOptionPane.showConfirmDialog(Pantalla.this, "Se actualiz\u00F3 la base de datos","Cerebro", Constantes.OK_ACEPTAR);
				} catch (ORMException e1) {
					getControlador().logWarning(e1.getMessage());
				} catch (PersistenciaException e1) {
					getControlador().logWarning(e1.getMessage());
				}
			}

			if (e.getActionCommand() == Constantes.APRENDER_CANCION) {
				
				try {
					TreePath treePath = fileTree.tree.getSelectionPath();
					Object[] ob = treePath.getPath();
					Object[] obPadre = treePath.getParentPath().getPath();
					String padre="";
					int j = obPadre.length-1;
					padre = padre+obPadre[j];
					
					int cant = 0;
					String ruta="";
					int i = ob.length-1;
					ruta = ruta+ob[i];
					
					String path = ruta+"/";
					File folder = new File(path);
					File[] listOfFiles = folder.listFiles();
					if (listOfFiles == null) {
						cant = controlador.aprenderArchivo(padre+"/"+ruta);
					} else {
						cant = controlador.aprenderDirectorio(listOfFiles, path);
					}
			
					if (cant > 0) {
						if (cant == 1)
							JOptionPane.showConfirmDialog(Pantalla.this, "Canci\u00F3n aprendida!!","Aprender", Constantes.OK_ACEPTAR);
						else
							JOptionPane.showConfirmDialog(Pantalla.this, cant + " Canciones aprendidas!!","Aprender", Constantes.OK_ACEPTAR);
					} else {
						JOptionPane.showConfirmDialog(Pantalla.this, "El formato de los archivos es incorrecto","Aprender", Constantes.OK_ACEPTAR);
					}
					
				} catch (NullPointerException eNull) {
					JOptionPane.showConfirmDialog(Pantalla.this, "No se seleccion\u00F3 ningun archivo","Aprender", Constantes.OK_ACEPTAR);
				}
			}
			
			if (e.getActionCommand() == Constantes.COMPONER) {		
				
				String tonica = (String) Pantalla.this.jComboTonica.getSelectedItem();
				String estilo = (String) Pantalla.this.jComboEstilo.getSelectedItem();
				String tempo = Pantalla.this.tempoText.getText();
				String duracion = cantCompasesText.getText();
				String estructura = (String) Pantalla.this.jComboEstructuraAvanzado.getSelectedItem();

				if (tonica == null || estilo == null) {
					JOptionPane.showConfirmDialog(Pantalla.this, "Falta ingresar datos","Componer", Constantes.OK_ACEPTAR);
					return;
				}
				
				if (jCheckEstructura.isSelected()) {
					controlador.componerConEstructruras(tonica, estilo, tempo, estructura);
				} else {
					controlador.componer(tonica, estilo, tempo, duracion);
				}
								
				// cuando se termino la composicion
				// mostrar la cancion que se compuso en editar
				cancionNueva = controlador.getCancionNueva();
				
				if (cancionNueva == null){
					return;
				}
				actualizarPanelEditar(cancionNueva);
				if (Archivos.generarArchivo(cancionNueva)) {
					
					File f = new File(cancionNueva.getNombreArchivo()+".mid");
					if (f.exists()) {
						if (panelMidi.getDevice().getSequencer() != null) {
				        	panelMidi.addSong(f);
				        }
					}
				}
					
				botonModificarCancion.setEnabled(true);
				//botonGuardar.setEnabled(true);
				botonReproducirCancion.setEnabled(true);
				botonPausarCancion.setEnabled(true);
				botonDetenerCancion.setEnabled(true);
				jCheckGuardar.setEnabled(true);
				
				CardLayout c2 = (CardLayout) panelDetalle.getLayout();
		        c2.show(panelDetalle, "panelDetalleComponer");
			}
//----------------------------------------------------------------------------------
			if (e.getActionCommand() == Constantes.MODIFICAR_CANCION ) {
				
				if (cancionNueva!=null){
					ArrayList<Integer> estrofasModificadas = new ArrayList<Integer>();
		
				
					for(int i=0; i < estrofas.size();i++){
						 TablaAcordes t = estrofas.get(i);
						 int numRows = t.getTabla().getRowCount();
						 int numCols = t.getTabla().getColumnCount();
	
	
				            for (int j=0; j < numRows; j++) {
				            	
				            	if (DEBUG){
				            		System.out.print("    row " + j + ":");
				            	}
				            		
				                for (int k=0; k < numCols; k++) {
				                	if (DEBUG){
				                		System.out.print("  " + t.getTabla().data[j][k])	;
				                	}
				                    
				                    
				                    if(k==4){// boolean modificado
					                    if (((Boolean)t.getTabla().data[j][k]).booleanValue()==true){
					                    	cancionNueva.getEstrofaPorNumero(i+1).getCompasPorNumero(j+1).setModificarCompas(true);
					                    }	
				                    }
				                }   
				            }
					}

				}// fin si la cancionNueva no es nula
				cancionNueva = controlador.modificarCancion(cancionNueva);
				if (cancionNueva == null){
					return;
				}
				actualizarPanelEditar(cancionNueva);
				if (Archivos.generarArchivo(cancionNueva)) {
					File f = new File(cancionNueva.getNombreArchivo()+".mid");
					if (f.exists()) {
						if (panelMidi.getDevice().getSequencer() != null) {
							panelMidi.addSong(f);
						}
					}
				}

			}//fin MODIFICAR_CANCION
			
			if (e.getActionCommand() == Constantes.GUARDAR_CANCION){
				JOptionPane.showConfirmDialog(Pantalla.this, "Guardando","Guardar Cancion", Constantes.OK_ACEPTAR);
				boolean resultado = controlador.guardarCancionCompuesta(cancionNueva,Pantalla.this.getJTextNombre().getText(),Pantalla.this.getJTextComentarios().getText());
				agregarCancionAlArbol(cancionNueva.getNombreFantasia());
				if (resultado) {
					JOptionPane.showConfirmDialog(Pantalla.this, "Canci\u00F3n guardada en la base de datos","Componer", Constantes.OK_ACEPTAR);
				}
			}
			
			if (e.getActionCommand() == Constantes.GUARDAR_CANCION_MODIFICADA){
				
				boolean resultado = controlador.guardarCanciones(cancionNueva,Pantalla.this.getJTextNombreCanciones().getText(),Pantalla.this.getJTextComentariosCanciones().getText()); 
				agregarCancionAlArbol(cancionNueva.getNombreFantasia());
				if (resultado) {
					JOptionPane.showConfirmDialog(Pantalla.this, "Canci\u00F3n guardada en la base de datos","Canciones", Constantes.OK_ACEPTAR);
				}
			}
		}
	}
    
    class TablaAcordes extends JPanel {
		MiTablaAcordes tabla;
		public TablaAcordes(Estrofa laEstrofa) {
			//super(new GridLayout(1,0));

			
			tabla = new MiTablaAcordes();
			tabla.llenarAcordes(laEstrofa);
			JTable table = new JTable(tabla);
			table.setPreferredScrollableViewportSize(new Dimension(410, 80));
			
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

	        public void setValueAt(Object value, int row, int col) {
	          
	            data[row][col] = value;
	            fireTableCellUpdated(row, col);

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
				//return true;
				
				if (col < 4) {
					return false;
				} else {
					return true;
				}
				
			}

		}	//Fin de clase MiTablaAcordes
	}

}
 
