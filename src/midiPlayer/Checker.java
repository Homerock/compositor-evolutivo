package midiPlayer;

import javax.sound.midi.Sequencer;

public class Checker extends Thread
{
    Sequencer seq;
    PositSlider slid;
    long length = 1;
    boolean running = false;
    public Checker(Sequencer seq, PositSlider slid)
    {
        this.seq = seq;
        this.slid = slid;
    }
    public void run()
    {
        while(true)
        {
            boolean running2 = seq.isRunning();
            if(running2 && !running) //just started
                slid.runClock();
            else if(!running2 && running) //just stopped
            {
                slid.setPosition((double)seq.getTickPosition()/length);
                slid.haltClock();
            }
            running = running2;
            if(running)
                slid.setPosition((double)seq.getTickPosition()/length);
            try{ sleep(20); } catch(InterruptedException e) {}
        }
    }
    public void setSong(long length)
    {
        this.length = length;
        slid.setPosition(0);
    }
}