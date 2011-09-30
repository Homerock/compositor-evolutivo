package midiPlayer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.Sequence;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utiles.Constantes;

import GUI.Imagenes;
import GUI.JPanelBackground;
import GUI.Pantalla.ManejadorEventos;

public class MIDIPlayer extends JPanelBackground {

	Imagenes misImagenes = new Imagenes();
	ManejadorEventosMidi manejador = new ManejadorEventosMidi();
	private JButton botonPlay;
	private JButton botonStop;
	private JButton botonPause;
	private JButton botonAvanzado;
	
	public PlayingDevice device;
	public AdvancedControls ac;
    public SequenceItem sequenceItemActual;
    public Checker checker;
    public PositSlider slid;
    //public AdvancedControls ac;
    private Vector<MIDIPlayerListener> events;

	
	public MIDIPlayer()
    {
		device = new PlayingDevice();
        sequenceItemActual = null;
        events = new Vector<MIDIPlayerListener>();
        
        //this.setLayout(new GridLayout(2,1));
        
        BoxLayout panelMidiLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
        this.setLayout(panelMidiLayout);
        
        slid = new PositSlider(device.getSequencer(), this);
        checker = new Checker(device.getSequencer(), slid);
        
        JPanelBackground buttnz = new JPanelBackground();
        buttnz.setLayout(new FlowLayout());
        
        botonPlay = getJButtonPlay();
        botonPlay.addActionListener(manejador);
		botonPlay.setActionCommand(Constantes.REPRODUCIR_CANCION);
		
		botonStop = getJButtonStop();
        botonStop.addActionListener(manejador);
		botonStop.setActionCommand(Constantes.DETENER_CANCION);
		
		botonPause = getJButtonPause();
        botonPause.addActionListener(manejador);
		botonPause.setActionCommand(Constantes.PAUSAR_CANCION);
		
		botonAvanzado = getJButtonAvanzado();
        botonAvanzado.addActionListener(manejador);
		botonAvanzado.setActionCommand(Constantes.OPCIONES_AVANZADAS);
        
		buttnz.add(botonAvanzado);
        buttnz.add(botonPlay);
        buttnz.add(botonPause);
        buttnz.add(botonStop);
        
        this.add(slid);
        this.add(buttnz);
        ac = new AdvancedControls(this);
      
        try {
        	buttnz.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
        
        setVisible(true);

    }
	
	private JButton getJButtonPlay() {
		if (botonPlay == null) {
			botonPlay = new JButton();
			try {
				botonPlay.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_PLAY)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			botonPlay.setVisible(true);
		}
		return botonPlay;
	}
	
	private JButton getJButtonStop() {
		if (botonStop == null) {
			botonStop = new JButton();
			try {
				botonStop.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_STOP)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			botonStop.setVisible(true);
		}
		return botonStop;
	}
	
	private JButton getJButtonPause() {
		if (botonPause == null) {
			botonPause = new JButton();
			try {
				botonPause.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_PAUSE)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			botonPause.setVisible(true);
		}
		return botonPause;
	}
	
	private JButton getJButtonAvanzado() {
		if (botonAvanzado == null) {
			botonAvanzado = new JButton();
			try {
				botonAvanzado.setIcon(new ImageIcon(misImagenes.getImagenURL(Constantes.BOTON_AVANZADO)));
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
			botonAvanzado.setVisible(true);
		}
		return botonAvanzado;
	}
	
	
	public void addSong(File f)
    {
  
                SequenceItem si = new SequenceItem(f);
                sequenceItemActual = si;
                System.out.println(si.toString());
                Sequence seq;
				try {
					seq = PlayingDevice.loadSequence(f);
					checker.setSong(seq.getTickLength());
					device.setSequence(seq);
	                for(Iterator<MIDIPlayerListener> i = events.iterator(); i.hasNext();)
	                {
	                    MIDIPlayerListener cur = i.next();
	                    cur.songChanged(this);
	                }
				} catch (IOException e) {
					e.printStackTrace();
				}
    }
    
    public void songEnded()
    {
    	device.stop();
    }
    
    public void playSong()
    {
       
        SequenceItem item = sequenceItemActual;
        Sequence seq = null;
        try
        {
            seq = PlayingDevice.loadSequence(item.getFile());
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, new JLabel("Error loading MIDI file, "+item.getFile().getName()), "File Error", JOptionPane.ERROR_MESSAGE);
        }
        if(seq == null)
            return;
        checker.setSong(seq.getTickLength());
        device.setSequence(seq);
        for(Iterator<MIDIPlayerListener> i = events.iterator(); i.hasNext();)
        {
            MIDIPlayerListener cur = i.next();
            cur.songChanged(this);
        }
    }
    public void slideAdvanced(boolean show)
    {
    	
    	//JOptionPane.showMessageDialog(this,ac,"Acerca de...", -1);
    	JOptionPane.showOptionDialog(this, ac, "Pistas de la canci\u00F3n", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

    	/*
        acShow = show;
        Point location = getLocation();
        ac.setLocation(location.x+(getWidth()-ac.getWidth())/2, location.y+getHeight());
        ac.setVisible(show);
        */
    }
    //------------------------------------
    public void addPlayerListener(MIDIPlayerListener lll)
    {
        events.add(lll);
    }
   
    public Sequence getCurrentSequence()
    {
        return device.getSequence();
    }

	public PlayingDevice getDevice() {
		return device;
	}

	public void setDevice(PlayingDevice device) {
		this.device = device;
	}
	
	/**
	 * 
	 *
	 */
	public class ManejadorEventosMidi implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand() == Constantes.REPRODUCIR_CANCION) {
				getDevice().play();
			}
			
			if (e.getActionCommand() == Constantes.PAUSAR_CANCION) {
				getDevice().pause();
			}
			
			if (e.getActionCommand() == Constantes.DETENER_CANCION) {
				getDevice().stop();
			}
			
			if (e.getActionCommand() == Constantes.OPCIONES_AVANZADAS) {
				slideAdvanced(true);
			}
			
		}
	}
	
}
