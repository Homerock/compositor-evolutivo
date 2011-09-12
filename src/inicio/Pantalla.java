package inicio;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import nucleo.Controlador;
import nucleo.Reproductor;

import org.jdesktop.application.Application;

import utiles.Constantes;
import archivos.Archivos;
import archivos.Reconocedor;
import canciones.Cancion;
import canciones.Compas;
import canciones.Estrofa;
import estructura.MatrizAcordes;
import excepciones.ORMException;
import excepciones.PersistenciaException;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Pantalla extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JTextField jTextNombre;
    private JTextArea jTextComentarios;
    private JTextField jTextNombreCanciones;
    private JTextArea jTextComentariosCanciones;
    private JCheckBox jCheckEstructura;
    private JCheckBox jCheckTempo;
    private JCheckBox jCheckCantCompases;
    private JCheckBox jCheckEstilo;
    private JCheckBox jCheckTonica;
    private JLabel statusBar = new JLabel();
    //private ImageIcon imageOpen = new ImageIcon(Pantalla.class.getResource("openfile.gif"));
    //private ImageIcon imageClose = new ImageIcon(Pantalla.class.getResource("closefile.gif"));
    //private ImageIcon imageHelp = new ImageIcon(Pantalla.class.getResource("help.gif"));
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
    private JPanelBackground panelComponerOpciones;
    private JPanelBackground panelComponerBotones;
    private JPanel panelTablaCancion;		//panel para ver la cancion que se esta componiendo
    
    // paneles de la opcion BD
    private JPanelBackground panelBD;
    private JPanelBackground panelDetalleBD;
    
    private CardLayout cardLayout1 = new CardLayout();
    private JScrollPane panelAprender = new JScrollPane();
    private JPanel panelCanciones;
    private JScrollPane panelArbolCanciones = new JScrollPane();
    private JPanelBackground panelCancionesBotones;
    
    private JScrollPane panelScrollEditar;
    
    private FileSystemModel fileSystemModel = new FileSystemModel();
    //private JTree fileTree = new JTree(fileSystemModel);
    private Arbol fileTree = new Arbol(new File("/home"));
    private JTree fileTreeCanciones = new JTree();
    
	private JComboBox jComboEstilo;
	private JComboBox jComboEstructuraAvanzado;
	//input de text
	private JTextField tonicaText;
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
	
	private Controlador controlador;
	Cancion cancionNueva;
	
	//estrofas---->
	ArrayList<TablaAcordes> estrofas = new ArrayList<TablaAcordes>();
	
	boolean DEBUG=false;
	Reproductor reproductor=null;
	
    public Pantalla(Controlador miControlador) {
    	
    	controlador = miControlador;
    	
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    	
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
        this.setSize(new Dimension(1000, 500));
        this.setTitle("Homerock");
        menuFile.setText("Archivo");
        menuFileExit.setText("Salir");
        menuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileExit_ActionPerformed(ae);
            }
        });
        menuHelp.setText("Ayuda");
        menuHelpAbout.setText("Acerca de...");
        menuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                helpAbout_ActionPerformed(ae);
            }
        });
        statusBar.setText("");
        //jPanel1.setLayout(verticalFlowLayout1);
        panelOpciones.setLayout(cardLayout1);
        
       // botonAprender.setText("Aprender");
        botonAprender.setIcon(new ImageIcon ("./img/boton1.png"));
        botonAprender.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonAprender_actionPerformed(e);
                }
            });
        //botonComponer.setText("Componer");
        botonComponer.setIcon(new ImageIcon ("./img/boton2.png"));
        botonComponer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonComponer_actionPerformed(e);
                }
            });
        //botonBD.setText("Base de datos");
        botonBD.setIcon(new ImageIcon ("./img/boton4.png"));
        botonBD.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonBD_actionPerformed(e);
                }
            });
        //botonCanciones.setText("Canciones");
        botonCanciones.setIcon(new ImageIcon ("./img/boton3.png"));
        botonCanciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                botonCanciones_actionPerformed(e);
            }
        });
        
        panelAprender.setAutoscrolls(true);
        menuFile.add(menuFileExit);
        menuFileExit.setName("menuFileExit");
        menuBar.add(menuFile);
        menuFile.setName("menuFile");
        menuHelp.add(menuHelpAbout);
        menuHelpAbout.setName("menuHelpAbout");
        menuBar.add(menuHelp);
        menuHelp.setName("menuHelp");
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        
        ManejadorEventos manejador = new ManejadorEventos();
        
        panelDetalle.setLayout(cardLayout1);
        armarPanelDetalleComponer(manejador);
        armarPanelDetalleAprender(manejador);
        armarPanelDetalleBD();
        panelDetalle.add(panelDetalleComponer, "panelDetalleComponer");
        panelDetalle.add(panelDetalleAprender, "panelDetalleAprender");
        panelDetalle.add(panelDetalleBD, "panelDetalleBD");
        
        armarPanelAprender();
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
        
        jSplitPane1.add(panelBotones, JSplitPane.LEFT);
        this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
        Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
    }

    private void armarPanelAprender() {
    	
    	panelAprender.getViewport().add(fileTree, null);
    }
    
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
		        
		        try {
					reproductor = new Reproductor(cancionNueva.getNombre()+".mid");
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
		      }
		    }); 
		
    	panelArbolCanciones.getViewport().add(tree, null);
    	tree.setPreferredSize(new java.awt.Dimension(250, 461));
    	
    	panelCanciones = new JPanel();
    	panelCanciones.setLayout(new GridLayout(2,1));
    	panelCanciones.add(panelArbolCanciones);
    	
    	panelCancionesBotones = new JPanelBackground();
    	try {
    		panelCancionesBotones.setBackground(new File("./img/fondoAzul.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BoxLayout panelCancionesBotonesLayout = new BoxLayout(panelCancionesBotones, javax.swing.BoxLayout.Y_AXIS);
		panelCancionesBotones.setLayout(panelCancionesBotonesLayout);
		
		//////////////////////////////////////////////////////////////////////
		{
			jCheckGuardarCanciones = new JCheckBox();
			panelCancionesBotones.add(jCheckGuardarCanciones);
			jCheckGuardarCanciones.setName("jCheckGuardarCanciones");
			jCheckGuardarCanciones.setText("Guardar canción");
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
			nombre = e.getKey() + " - " + miCancion.getNombre();
			nodoCancion=new DefaultMutableTreeNode(nombre);
	    	modelo.insertNodeInto(nodoCancion, nodoPadre, 0);
		}
    	
    }
    
    private void armarPanelBD(ManejadorEventos manejador) {
    	
    	panelBD = new JPanelBackground();
       
        try {
        	panelBD.setBackground(new File("./img/fondoAzul.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		botonGuardarEnBD = getJButtonGuardarEnBD();
		botonGuardarEnBD.addActionListener(manejador);
		botonGuardarEnBD.setActionCommand(Constantes.ACTUALIZAR);
		panelBD.add(botonGuardarEnBD);
		botonGenerarComposicion.setName("botonGenerarComposicion");
		botonGenerarComposicion.setName("botonGenerarComposicion");

    }
    
	private void armarPanelComponer(ManejadorEventos manejador) {

    	panelComponerOpciones = new JPanelBackground();
		panelComponer = new JPanel();
		
		try {
			panelComponerOpciones.setLayout(null);
			panelComponerOpciones.setBackground(new File("./img/fondoAzul.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		tonicaText = new JTextField("",5);
		panelComponerOpciones.add(tonicaText);
		tonicaText.setBounds(95, 11, 57, 22);
		tonicaText.setName("tonicaText");
		jComboEstilo = new JComboBox();
		panelComponerOpciones.add(jComboEstilo);
		jComboEstilo.setBounds(95, 44, 149, 22);
		jComboEstilo.setName("jComboEstilo");

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
		{
			jCheckTonica = new JCheckBox();
			panelComponerOpciones.add(jCheckTonica);
			jCheckTonica.setBounds(9, 12, 81, 19);
			jCheckTonica.setName("jCheckTonica");
			jCheckTonica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckTonica.isSelected()) {
						tonicaText.setEnabled(true);
					} else {
						tonicaText.setEnabled(false);
					}
					panelComponerOpciones.repaint();
				}
			});
		}
		{
			jCheckEstilo = new JCheckBox();
			panelComponerOpciones.add(jCheckEstilo);
			jCheckEstilo.setBounds(9, 45, 74, 19);
			jCheckEstilo.setName("jCheckEstilo");
			jCheckEstilo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (jCheckEstilo.isSelected()) {
						jComboEstilo.setEnabled(true);
					} else {
						jComboEstilo.setEnabled(false);
					}
					panelComponerOpciones.repaint();
				}
			});
		}
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
			panelComponerBotones.setBackground(new File("./img/fondoAzul.jpg"));
		} catch (IOException e) {
			System.err.println(e);
		}
		
		panelComponer.setLayout(new GridLayout(2,1));
		panelComponer.add(panelComponerOpciones);
		panelComponer.add(panelComponerBotones);
		
	}

	private void armarPanelDetalleAprender(ManejadorEventos manejador) {
		
    	panelDetalleAprender = new JPanelBackground();
    	
    	try {
    		panelDetalleAprender.setBackground(new File("./img/fondoAzul2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		//boton REPTODUCIR 
		botonAprenderCancion = getJButtonAprenderCancion();
		botonAprenderCancion.addActionListener(manejador);
		botonAprenderCancion.setActionCommand(Constantes.APRENDER_CANCION);
		panelDetalleAprender.add(botonAprenderCancion);
		
	}

	private void armarPanelDetalleComponer(ManejadorEventos manejador) {
		
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
			panelDetalleComponer.setBackground(new File("./img/fondoAzul2.jpg"));
			panelModificar.setBackground(new File("./img/fondoAzulInv2.jpg"));
			//panelTablaCancion.setBackground(new File("./img/fondoAzul2.jpg"));
			//panelTablaCancion.setPreferredSize(new java.awt.Dimension(525, 348));
			panelReproductor.setBackground(new File("./img/fondoAzulInv2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		panelScrollEditar = new JScrollPane();
		panelScrollEditar.setName("panelScrollEditar");
		panelScrollEditar.setViewportView(panelTablaCancion);
		
		panelDetalleComponer.add(panelModificar);
		panelDetalleComponer.add(panelScrollEditar);
		panelDetalleComponer.add(panelReproductor);
	}
		
	private void armarPanelDetalleBD() {
		
		panelDetalleBD = new JPanelBackground();
		
		try {
			panelDetalleBD.setBackground(new File("./img/fondoAzul2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JButton getJButtonGuardarEnBD() {
		if (botonGuardarEnBD == null) {
			botonGuardarEnBD = new JButton();
			botonGuardarEnBD.setText("Actualizar Base de Datos");
			botonGuardarEnBD.setVisible(true);
		}
		return botonGenerarComposicion;
	}
	
    private JButton getJButtonGenerarComposicion() {
		if (botonGenerarComposicion == null) {
			botonGenerarComposicion = new JButton();
			;
			botonGenerarComposicion.setVisible(true);
		}
		return botonGenerarComposicion;
	}
    
    private JButton getJButtonModificarCancion() {
		if (botonModificarCancion == null) {
			botonModificarCancion = new JButton();
			botonModificarCancion.setText("Modificar Canción");
			botonModificarCancion.setVisible(true);
		}
		return botonModificarCancion;
	}
	
    private JButton getJButtonGuardarCancion() {
		if (botonGuardar == null) {
			botonGuardar = new JButton();
			botonGuardar.setText("Guardar Canción");
			botonGuardar.setVisible(true);
		}
		return botonGuardar;
	}
    
    private JButton getJButtonGuardarCanciones() {
		if (botonGuardarCanciones == null) {
			botonGuardarCanciones = new JButton();
			botonGuardarCanciones.setText("Guardar Canción");
			botonGuardarCanciones.setVisible(true);
		}
		return botonGuardarCanciones;
	}

	private JButton getJButtonReproducirCancion() {
		
		if (botonReproducirCancion == null) {
			botonReproducirCancion = new JButton();
			botonReproducirCancion.setIcon(new ImageIcon ("./img/play.jpg"));
			//botonReproducirCancion.setText("Reproducir");
			botonReproducirCancion.setVisible(true);
		}
		return botonReproducirCancion ;
	}
		

	private JButton getJButtonPausarCancion() {
		
		if (botonPausarCancion == null) {
			botonPausarCancion= new JButton();
			botonPausarCancion.setIcon(new ImageIcon ("./img/pause.jpg"));
			//botonPausarCancion.setText("Pausa");
			botonPausarCancion.setVisible(true);
		}
		return botonPausarCancion;
	}
	
	private JButton getJButtonDetenerCancion() {
		
		if (botonDetenerCancion == null) {
			botonDetenerCancion= new JButton();
			botonDetenerCancion.setIcon(new ImageIcon ("./img/stop.jpg"));
			botonDetenerCancion.setVisible(true);
		}
		return botonDetenerCancion;
	}
	
	private JButton getJButtonAprenderCancion() {
		
		if (botonAprenderCancion == null) {
			botonAprenderCancion= new JButton();
			botonAprenderCancion.setText("Aprender canción");
			botonAprenderCancion.setVisible(true);
		}
		return botonAprenderCancion;
	}

	void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new About(), "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void botonAprender_actionPerformed(ActionEvent e) {
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelAprender");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelDetalleAprender");
    }

    private void botonComponer_actionPerformed(ActionEvent e) {
    	vaciarPanelDetalle();
    	vaciarPanelEditar();
    	vaciarPanelOpciones();
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelComponer");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelDetalleComponer");
    }

    private void botonBD_actionPerformed(ActionEvent e) {
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelBD");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelDetalleBD");
    }
    
    private void botonCanciones_actionPerformed(ActionEvent e) {
    	vaciarPanelDetalle();
    	vaciarPanelEditar();
    	vaciarPanelOpcionesCanciones();
        CardLayout c1 = (CardLayout) panelOpciones.getLayout();
        c1.show(panelOpciones, "panelCanciones");
        CardLayout c2 = (CardLayout) panelDetalle.getLayout();
        c2.show(panelDetalle, "panelDetalleComponer");
    }
    
    public void cargarCombo() {
		ArrayList<String> lista = controlador.getComboEstilos();

		jComboEstilo.removeAllItems();
		for (String estilo : lista) { 
			Pantalla.this.jComboEstilo.addItem(estilo);
		}
	}
    
    private void actualizarPanelEditar(Cancion cancionNueva){

		panelTablaCancion.removeAll();//panel donde se muetran las etrofas
		estrofas.clear();//lista donde estan las estrofas
		
		try {
		
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
    
    private void vaciarPanelEditar() {
    	
    	panelTablaCancion.removeAll();//panel donde se muetran las etrofas
		estrofas.clear();//lista donde estan las estrofas
		panelDetalle.updateUI();
    }
    
    private void vaciarPanelOpciones() {
    	
    	tonicaText.setText("");
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
    
    private void vaciarPanelOpcionesCanciones() {
    	
    	jTextComentariosCanciones.setText("Comentarios");
    	jTextComentariosCanciones.setEnabled(false);
    	jTextNombreCanciones.setText("Nombre");
    	jTextNombreCanciones.setEnabled(false);
    	botonGuardarCanciones.setEnabled(false);
    	jCheckGuardarCanciones.setEnabled(false);
    	jCheckGuardarCanciones.setSelected(false);
    }
    
    private void vaciarPanelDetalle() {
    	
    	botonModificarCancion.setEnabled(false);
    	botonPausarCancion.setEnabled(false);
    	botonReproducirCancion.setEnabled(false);
    	jCheckGuardarCanciones.setEnabled(false);
    }
    
    public JTextArea getJTextComentarios() {
    	if(jTextComentarios == null) {
    		jTextComentarios = new JTextArea();
    		jTextComentarios.setName("jTextComentarios");
    		jTextComentarios.setPreferredSize(new java.awt.Dimension(250, 210));
    	}
    	return jTextComentarios;
    }
    
    public JTextArea getJTextComentariosCanciones() {
    	if(jTextComentariosCanciones == null) {
    		jTextComentariosCanciones = new JTextArea();
    		jTextComentariosCanciones.setName("jTextComentariosCanciones");
    		jTextComentariosCanciones.setPreferredSize(new java.awt.Dimension(250,210));
    	}
    	return jTextComentariosCanciones;
    }
    
    public JTextField getJTextNombre() {
    	if(jTextNombre == null) {
    		jTextNombre = new JTextField();
    		jTextNombre.setName("jTextNombre");
    		jTextNombre.setPreferredSize(new java.awt.Dimension(250, 26));
    	}
    	return jTextNombre;
    }
    
    public JTextField getJTextNombreCanciones() {
    	if(jTextNombreCanciones == null) {
    		jTextNombreCanciones = new JTextField();
    		jTextNombreCanciones.setName("jTextNombreCanciones");
    		jTextNombreCanciones.setPreferredSize(new java.awt.Dimension(250, 26));
    	}
    	return jTextNombreCanciones;
    }

    public class ManejadorEventos implements ActionListener{

		//Cancion cancionNueva;
		
		public void actionPerformed(ActionEvent e) {

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
				} catch (ORMException e1) {
					System.err.println(e1.getMessage());
				} catch (PersistenciaException e1) {
					System.err.println(e1.getMessage());
				}
			}

			if (e.getActionCommand() == Constantes.APRENDER_CANCION) {

				System.out.println("Aprender");
				
				TreePath treePath = fileTree.tree.getSelectionPath();
				Object[] ob = treePath.getPath();
				Object[] obPadre = treePath.getParentPath().getPath();
				String padre="";
				int j = obPadre.length-1;
				padre = padre+obPadre[j];
				
				String ruta="";
				int i = ob.length-1;
				ruta = ruta+ob[i];
				
				String path = ruta+"/";
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				if (listOfFiles == null) {
					System.out.println("es archivo");
					controlador.aprenderArchivo(padre+"/"+ruta);
				} else {
					System.out.println("es directorio");
					controlador.aprenderDirectorio(listOfFiles, path);
				}
		
				cargarCombo();
				JOptionPane.showConfirmDialog(Pantalla.this, "Canción aprendida!!","Aprender", Constantes.OK_ACEPTAR);
				/*
				
				File file = (File) fileTree.getLastSelectedPathComponent();
				
				System.out.println(file.getPath());
		        
		        String path = file.getPath()+"/";
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				if (listOfFiles == null) {
					System.out.println("es archivo");
					controlador.aprenderArchivo(file.getPath());
				} else {
					System.out.println("es directorio");
					controlador.aprenderDirectorio(listOfFiles, path);
				}
		
				cargarCombo();
				JOptionPane.showConfirmDialog(Pantalla.this, "Canción aprendida!!","Aprender", Constantes.OK_ACEPTAR);
			*/
			
			}
			
			if (e.getActionCommand() == Constantes.COMPONER) {
				
				
				String tonica = Pantalla.this.tonicaText.getText();
				String estilo = (String) Pantalla.this.jComboEstilo.getSelectedItem();
				String tempo = Pantalla.this.tempoText.getText();
				String duracion = cantCompasesText.getText();
				String estructura = (String) Pantalla.this.jComboEstructuraAvanzado.getSelectedItem();

				if (tonica.trim().equals("") || estilo == null) {
					JOptionPane.showConfirmDialog(Pantalla.this, "Falta ingresar datos","Componer", Constantes.OK_ACEPTAR);
					return;
				}
				if (!Reconocedor.esAcordeValido(tonica)) {
					JOptionPane.showConfirmDialog(Pantalla.this, "El acorde de tonica es incorrecto","Componer", Constantes.OK_ACEPTAR);
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
				if (cancionNueva!=null){
					actualizarPanelEditar(cancionNueva);
				}
				botonModificarCancion.setEnabled(true);
				//botonGuardar.setEnabled(true);
				botonReproducirCancion.setEnabled(true);
				botonPausarCancion.setEnabled(true);
				jCheckGuardar.setEnabled(true);
			}

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
				controlador.modificarCancion(cancionNueva);
				Archivos.generarArchivo(cancionNueva);
				actualizarPanelEditar(cancionNueva);
				reproductor=null;

			}//fin MODIFICAR_CANCION
			
			if (e.getActionCommand() == Constantes.GUARDAR_CANCION){
				
				controlador.guardarCancion(cancionNueva,Pantalla.this.getJTextNombre().getText(),Pantalla.this.getJTextComentarios().getText());
			}
			
			if (e.getActionCommand() == Constantes.GUARDAR_CANCION_MODIFICADA){
				
				controlador.guardarCancion(cancionNueva,Pantalla.this.getJTextNombreCanciones().getText(),Pantalla.this.getJTextComentariosCanciones().getText());
				cargarArbolCanciones();
			}
			
			if (e.getActionCommand() == Constantes.REPRODUCIR_CANCION){
				if (reproductor != null) {
					reproductor.reproducir();
				}
			}//FIN REPRODUCIR_CANCION

			if (e.getActionCommand() == Constantes.PAUSAR_CANCION){
				if (reproductor != null) {
					reproductor.pausar();
				}
			}//FIN PAUSAR_CANCION
			
			if (e.getActionCommand() == Constantes.DETENER_CANCION){
				if (reproductor != null) {
					reproductor.detener();
				}
			}//FIN DETENER_CANCION
						
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
 
