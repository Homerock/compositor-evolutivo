package midiPlayer;

import java.io.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class PlayingDevice
{
    Sequencer seq;
    Sequence tape = null;
    Checker checker;
    public void openMidiResources()
    {
        try
        {
            seq = MidiSystem.getSequencer();
            seq.open();
        }
        catch (Exception e)
        {
        	System.err.println(e.getMessage());
            return;
        }
    }
    public PlayingDevice()
    {
        openMidiResources();
    }
    public void setSequence(Sequence tape)
    {
        boolean playing = seq.isRunning();
        stop();
        this.tape = tape;
        try
        {
            seq.setSequence(tape);
        }
        catch(InvalidMidiDataException ex) { ex.printStackTrace(); }
        //if(playing)
            play();
    }
    public Sequence getSequence()
    {
        return seq.getSequence();
    }
    public static Sequence loadSequence(File f) throws IOException
    {
        Sequence sequence = null;
        try
        {
            sequence = MidiSystem.getSequence(f);
        }
        catch(InvalidMidiDataException ex)
        {
            return null;
        }
        return sequence;
    }
    public Sequencer getSequencer()
    {
        return seq;
    }
    public void close()
    {
        seq.stop();
        seq.close();
    }
    public void play()
    {
        if(seq.getSequence() != null)
            seq.start();
    }
    public void pause()
    {
        seq.stop();
    }
    public void stop()
    {
        seq.stop();
        seq.setTickPosition(0);
    }
    //**************************************************
    public static void main(String[] args)
    {
        new PlayingDevice();
        
    }
}
