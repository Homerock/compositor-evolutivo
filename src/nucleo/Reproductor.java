package nucleo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Reproductor {
	
	private  Sequence sequence = null;//MidiSystem.getSequence(new File(archivo));

    private  Sequencer sequencer = null;//MidiSystem.getSequencer();
	
	public Reproductor (){}
	
	
	public Reproductor (String archivo) throws InvalidMidiDataException, IOException, MidiUnavailableException {
	 		
	
			this.sequence = MidiSystem.getSequence(new File(archivo));
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			 
	        sequencer.setSequence(sequence);
	
	}
	
	public void reproducir(){
        // Start playing
	
		if (sequencer.getMicrosecondPosition() == sequencer.getMicrosecondLength()) {
			detener();
		}
        sequencer.start();
    
	}

	public void pausar(){
        // Start playing
	
        sequencer.stop();
    
	}
	
	public void detener() {
	
		sequencer.stop();
		sequencer.setMicrosecondPosition(0);
	
	}
	

}
