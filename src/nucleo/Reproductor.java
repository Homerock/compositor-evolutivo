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
    //private String archivo = "";
    // Create a sequencer for the sequence
    private  Sequencer sequencer = null;//MidiSystem.getSequencer();
	
	public Reproductor (){}
	
	
	public Reproductor (String archivo) {
		//archivo = archivo;
	 	try {
	 		
			this.sequence = MidiSystem.getSequence(new File(archivo));
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			 
	        sequencer.setSequence(sequence);
			
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reproducir(){
        // Start playing
        sequencer.start();
		
	}
	

	public void pausar(){
        // Start playing
        sequencer.stop();
		
	}
	
	
	

}
