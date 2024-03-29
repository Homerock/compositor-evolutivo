package midiPlayer;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

import javax.swing.JWindow;

import utiles.Constantes;

import GUI.Imagenes;
import GUI.JPanelBackground;

public class AdvancedControls extends JPanel
{
    MIDIPlayer p;
    Imagenes misImagenes = new Imagenes();
    
    public AdvancedControls(MIDIPlayer p)
    {
        this.p = p;
        
        JPanel mainP = new JPanel(new GridLayout(2,1));
        
        JPanel bottom = new JPanel(new FlowLayout());
        JLabel ll = new JLabel("Tempo: 120");
        bottom.add(new TempoSlider(p, ll));
        bottom.add(ll);
        
        JScrollPane scrollTracks = new JScrollPane(new TrackControls(p));
        scrollTracks.getVerticalScrollBar().setUnitIncrement(10);
        
        mainP.add(scrollTracks);
        mainP.add(bottom);
        
        add(mainP);
        setSize(600, 200);
    }
}
