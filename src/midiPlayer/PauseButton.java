package midiPlayer;

public class PauseButton extends PlayerButton
{
    public PauseButton(PlayingDevice p)
    {
        super(p, "img/pause_up.png", "img/pause_over.png", "img/pause_down.png");
        
    }
    public void doAction()
    {
        p.pause();
    }
}
