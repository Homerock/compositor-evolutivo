package midiPlayer;

import java.awt.*;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import javax.swing.event.*;

public class TempoSlider extends JSlider implements ChangeListener,
                                                    MIDIPlayerListener
{
    MIDIPlayer p;
    Sequencer seq;
    PositSlider slid;
    JLabel tempoLabel;
    int curTempo = 120;
    public TempoSlider(MIDIPlayer p, JLabel tempoLabel)
    {
        super(10, 240, 120);
        this.p = p;
        this.seq = p.getDevice().getSequencer();
        this.slid = p.slid;
        this.tempoLabel = tempoLabel;
        addChangeListener(this);
        p.addPlayerListener(this);
        
        setPreferredSize(new Dimension(400, 20));
    }
    public void stateChanged(ChangeEvent arg0)
    {
        //seq.setTempoInBPM(getValue());
        seq.setTempoFactor((float)getValue()/curTempo);
        tempoLabel.setText("Tempo: "+getValue());
        slid.setTempoFactor(getValue()/curTempo);
    }
    public void songChanged(MIDIPlayer p)
    {
        seq.setTempoFactor(1);
        curTempo = (int)seq.getTempoInBPM();
        setValue(curTempo);
    }
}
