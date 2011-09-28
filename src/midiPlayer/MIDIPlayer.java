package midiPlayer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.Sequence;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MIDIPlayer extends JPanel {

	public PlayingDevice p;
	public AdvancedControls ac;
    public SequenceItem sequenceItemActual;
    public Checker checker;
    public PositSlider slid;
    //public AdvancedControls ac;
    private Vector<MIDIPlayerListener> events;
    private boolean acShow = false,
                    acActive = true;
	
	public MIDIPlayer()
    {
		p = new PlayingDevice();
        sequenceItemActual = null;
        events = new Vector<MIDIPlayerListener>();
        
        this.setLayout(new BorderLayout());
        slid = new PositSlider(p.getSequencer(), this);
        checker = new Checker(p.getSequencer(), slid);
        
        JPanel buttnz = new JPanel(new FlowLayout());
        buttnz.add(new AdvancedButton(this));
        buttnz.add(new PlayButton(p));
        buttnz.add(new PauseButton(p));
        buttnz.add(new StopButton(p));
     
        JPanel corner = new JPanel(new FlowLayout());
        
        corner.add(new BrowseButton(this));
        
        JPanel middle = new JPanel(new BorderLayout());
        middle.add(buttnz, BorderLayout.WEST);
        middle.add(corner, BorderLayout.EAST);
        
        this.add(slid, BorderLayout.NORTH);
        this.add(middle, BorderLayout.CENTER);
        ac = new AdvancedControls(this);
        this.add(ac, BorderLayout.SOUTH);
      
        setVisible(true);
        
        
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
	                p.setSequence(seq);
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
        p.stop();
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
        p.setSequence(seq);
        for(Iterator<MIDIPlayerListener> i = events.iterator(); i.hasNext();)
        {
            MIDIPlayerListener cur = i.next();
            cur.songChanged(this);
        }
    }
    public void slideAdvanced(boolean show)
    {
        acShow = show;
        Point location = getLocation();
        ac.setLocation(location.x+(getWidth()-ac.getWidth())/2, location.y+getHeight());
        ac.setVisible(show);
    }
    //------------------------------------
    public void addPlayerListener(MIDIPlayerListener lll)
    {
        events.add(lll);
    }
   
    public Sequence getCurrentSequence()
    {
        return p.getSequence();
    }
	
}
