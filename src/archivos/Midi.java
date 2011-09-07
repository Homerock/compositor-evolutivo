package archivos;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Midi {

	Sequence seq;
	Sequencer sequencer;
	
	
	/**---------------------------------------------------------------------------
	 * Reproducir
	 *---------------------------------------------------------------------------*/
	public Sequencer abrir(String archivo) {
		
		try {
	        // From file
		 	this.seq = MidiSystem.getSequence(new File(archivo));
	    
	        // Create a sequencer for the sequence
	        this.sequencer = MidiSystem.getSequencer();
	        this.sequencer.open();
	        this.sequencer.setSequence(this.seq);
	        return this.sequencer;
	        
	    } catch (MalformedURLException e) {
	    } catch (IOException e) {
	    } catch (MidiUnavailableException e) {
	    } catch (InvalidMidiDataException e) {
	    }
		return null;
		
	}
	
	/**---------------------------------------------------------------------------
	 * Reproducir
	 *---------------------------------------------------------------------------*/
	public void reproducir(Sequencer sequencer){
		
        sequencer.start();
	}
	
	/**---------------------------------------------------------------------------
	 * Detener
	 *---------------------------------------------------------------------------*/
	public void detener(Sequencer sequencer) {
		
		sequencer.stop();
		
	}
}
