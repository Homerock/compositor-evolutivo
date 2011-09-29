package midiPlayer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.sound.midi.Sequence;
import javax.swing.*;
import javax.swing.border.Border;


public class MIDIPlayerGUI extends JFrame
{
    
    private Vector<MIDIPlayerListener> events;
    private boolean acShow = false,
                    acActive = true;
    
    public MIDIPlayerGUI()
    {
        super("Java MIDI Player");
        initialize(); 
    }
    
    private void initialize()
    {

        events = new Vector<MIDIPlayerListener>();
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
               
                System.exit(0);
            }
        });
        
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
       
        MIDIPlayer midiP = new MIDIPlayer();
        
        pane.add(midiP,BorderLayout.CENTER);
        
        setSize(700, 600);
        setResizable(true);
        setVisible(true);
        
        midiP.checker.start();
    }
    
    
    public static void main(String[] args)
    {
           new MIDIPlayerGUI();
    }
}
